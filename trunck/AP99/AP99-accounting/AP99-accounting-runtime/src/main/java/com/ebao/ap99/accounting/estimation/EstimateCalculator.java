package com.ebao.ap99.accounting.estimation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.constant.EstimateEntryEnum;
import com.ebao.ap99.accounting.constant.EstimateStatusEnum;
import com.ebao.ap99.accounting.entity.TRiAcctFee;
import com.ebao.ap99.accounting.entity.TRiAcctFeeDetail;
import com.ebao.ap99.accounting.service.impl.AccountingServiceImpl;
import com.ebao.ap99.accounting.util.ChangeReasonTool;
import com.ebao.ap99.arap.constant.BizTransactionType;
import com.ebao.ap99.parent.context.AppContext;

import scala.actors.threadpool.Arrays;

public class EstimateCalculator {
    public static final BigDecimal[] defaultDistributionPattern = { BigDecimal.valueOf(0.25), BigDecimal.valueOf(0.50),
            BigDecimal.valueOf(0.75), BigDecimal.valueOf(1) };

    public static final BigDecimal[] defaultUPRPattern = { new BigDecimal(7).divide(new BigDecimal(8)),
            new BigDecimal(5).divide(new BigDecimal(8)), new BigDecimal(3).divide(new BigDecimal(8)),
            new BigDecimal(1).divide(new BigDecimal(8)) };

    public static final String[] UPRPatternString = { "7/8", "5/8", "3/8", "1/8" };

    private static final String[] uprdacItems = { EstimateEntryEnum.UPR_Opening.getEntryCode(),
            EstimateEntryEnum.UPR_Closing.getEntryCode(), EstimateEntryEnum.DAC_Opening.getEntryCode(),
            EstimateEntryEnum.DAC_Closing.getEntryCode() };

    @Autowired
    private AccountingServiceImpl accountingServiceImpl;

    @SuppressWarnings("unchecked")
    public List<BigDecimal> getDistributePatternByContractId(Long contractId) {
        return Arrays.asList(defaultDistributionPattern);
        //import org.springframework.util.CollectionUtils;
        //return CollectionUtils.arrayToList(defaultDistributionPattern);
    }

    public void adjustForecastEvenly(String entryCode, EstimateModel estModel) throws Exception {
        assert estModel != null : "Estimate Model cannot be empty";
        List<EstimateQuarter> estimateQuarterList = estModel.getEstQuarter();
        //for treaty setup, initialize 
        if (CollectionUtils.isEmpty(estimateQuarterList))
            return;

        BigDecimal diff = BigDecimal.ZERO;
        List<EstimateQuarter> forecastQuarter = new ArrayList<EstimateQuarter>();
        for (EstimateQuarter estimateQuarter : estimateQuarterList) {
            if (estimateQuarter.isClosedQuarter()) {
                diff = diff.add(calcQuarterDiff(estimateQuarter, entryCode));
            } else if (estimateQuarter.isOpenQuarter()) {
                forecastQuarter.add(estimateQuarter);
            } else if (estimateQuarter.isUPRQuarter()) {
                // if no open quarter, convert first UPR quarter to Forecast
                if (forecastQuarter.isEmpty()) {
                    estimateQuarter.setQuarterStatus(EstimateStatusEnum.Forecast);
                    forecastQuarter.add(estimateQuarter);
                }
                break;
            } else {
                throw new Exception("Unknown quarter status: " + estimateQuarter.getQuarterStatus());
            }
        }

        BigDecimal quarterDiff = diff.divide(BigDecimal.valueOf(forecastQuarter.size()), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal remain = diff;
        EstimateItem forecastItem = null;
        EstimateItem baseForecast = null;
        //TODO distribution pattern will be set in contract setup in next phase.
        for (EstimateQuarter estq : forecastQuarter) {
            forecastItem = estq.getEstItemByEntryCode(entryCode, EstimateStatusEnum.Forecast);
            baseForecast = estq.getEstItemByEntryCode(entryCode, EstimateStatusEnum.BaseForecast);
            forecastItem.setAmount(baseForecast.getAmount().add(quarterDiff));
            forecastItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
            remain = remain.subtract(quarterDiff);
        }
        forecastItem.setAmount(forecastItem.getAmount().add(remain));
    }

    public BigDecimal calcQuarterDiff(EstimateQuarter estimateQuarter, String entryCode) {
        EstimateItem baseForecastItem = estimateQuarter.getEstItemByEntryCode(entryCode,
                EstimateStatusEnum.BaseForecast);
        EstimateItem forecastItem = estimateQuarter.getEstItemByEntryCode(entryCode, EstimateStatusEnum.Forecast);
        if (!forecastItem.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
                .equals(estimateQuarter.getLatestAmount(entryCode).setScale(2, BigDecimal.ROUND_HALF_UP))) {
            forecastItem.setChangeReason("Forecast Recalculation");
            forecastItem.setAmount(estimateQuarter.getLatestAmount(entryCode));
        }
        return baseForecastItem.getAmount().subtract(estimateQuarter.getLatestAmount(entryCode));
    }

    /**
     * Calculate Closing Item(As at): UPR/DAC/Earned Premium/Estimated Incurred
     * Loss.
     * 
     * @param estModel
     * @throws Exception
     */
    public void calcForecastForClosingItem(EstimateModel estModel) throws Exception {
        LinkedList<EstimateQuarter> quarterDesc = new LinkedList<EstimateQuarter>();
        BigDecimal prevClosingUPR = BigDecimal.ZERO;
        BigDecimal prevClosingDAC = BigDecimal.ZERO;
        BigDecimal prevClosingEP = BigDecimal.ZERO;
        //BigDecimal prevClosingReserve = BigDecimal.ZERO;

        BigDecimal accumulatedGWP = BigDecimal.ZERO;
        BigDecimal accumulateEROfClosingReserve = BigDecimal.ZERO;
        BigDecimal accumulateActualOfClosingReserve = BigDecimal.ZERO;
        int quarterIndex = 0;
        do {
            if (quarterIndex > estModel.getEstQuarter().size() - 1) {
                estModel.addUPRQuarter();
            }
            EstimateQuarter estimateQuarter = estModel.getEstQuarter().get(quarterIndex++);

            quarterDesc.addFirst(estimateQuarter);
            accumulatedGWP = accumulatedGWP.add(estimateQuarter.getLatestAmount(EstimateEntryEnum.GWP.getEntryCode()));
            BigDecimal closingUPR = null;
            BigDecimal closingDAC = null;
            BigDecimal closingEP = null;
            BigDecimal closingReserve = null;

            if (estimateQuarter.isClosedQuarter()) {
                closingUPR = estimateQuarter.getLatestAmount(EstimateEntryEnum.UPR_Closing.getEntryCode());
                closingDAC = estimateQuarter.getLatestAmount(EstimateEntryEnum.DAC_Closing.getEntryCode());
                closingEP = estimateQuarter.getLatestAmount(EstimateEntryEnum.EP_Closing.getEntryCode());
                //LossReserve
                EstimateItem reserveOpeningItem = estimateQuarter.getEstItemByEntryCode(
                        EstimateEntryEnum.LossReserve_Opening.getEntryCode(), EstimateStatusEnum.Forecast);
                if ((reserveOpeningItem.getItemId() == null && reserveOpeningItem.getAmount().equals(BigDecimal.ZERO))
                        || !reserveOpeningItem.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
                                .equals(accumulateEROfClosingReserve.add(accumulateActualOfClosingReserve).setScale(2,
                                        BigDecimal.ROUND_HALF_UP))) {
                    reserveOpeningItem.setAmount(accumulateEROfClosingReserve.add(accumulateActualOfClosingReserve));
                    reserveOpeningItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
                }
                reserveOpeningItem.setStatus(EstimateStatusEnum.Forecast);

                EstimateItem reserveClosingItem = estimateQuarter.getEstItemByEntryCode(
                        EstimateEntryEnum.LossReserve_Closing.getEntryCode(), EstimateStatusEnum.Forecast);
                if (!reserveClosingItem.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
                        .equals(reserveOpeningItem.getAmount()
                                .add(estimateQuarter.getEstimateAndReversalOfLossReserve())
                                .add(estimateQuarter.getActualOfLossReserve()).setScale(2, BigDecimal.ROUND_HALF_UP))) {
                    reserveClosingItem.setAmount(
                            reserveOpeningItem.getAmount().add(estimateQuarter.getEstimateAndReversalOfLossReserve())
                                    .add(estimateQuarter.getActualOfLossReserve()));
                    reserveClosingItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
                }
                reserveClosingItem.setStatus(EstimateStatusEnum.Forecast);
                accumulateEROfClosingReserve = accumulateEROfClosingReserve
                        .add(estimateQuarter.getEstimateAndReversalOfLossReserve());
                accumulateActualOfClosingReserve = accumulateActualOfClosingReserve
                        .add(estimateQuarter.getActualOfLossReserve());
            } else if (estimateQuarter.isOpenQuarter() || estimateQuarter.isUPRQuarter()) {
                closingUPR = BigDecimal.ZERO;
                for (int i = 0; i < EstimateCalculator.defaultUPRPattern.length && i < quarterDesc.size(); i++) {
                    BigDecimal tempGWP = quarterDesc.get(i).getLatestAmount(EstimateEntryEnum.GWP.getEntryCode());
                    closingUPR = closingUPR.add(EstimateCalculator.defaultUPRPattern[i].multiply(tempGWP));
                }
                //UPR * total expense rate
                closingDAC = closingUPR.multiply(estModel.getTotalExpenseRate());
                //Accumulated GWP - UPR
                closingEP = accumulatedGWP.subtract(closingUPR);
                //EP * loss ratio
                closingReserve = closingEP.multiply(estModel.getLossRatio()).subtract(accumulateActualOfClosingReserve);

                //UPR
                EstimateItem uprOpeningItem = estimateQuarter.getEstItemByEntryCode(
                        EstimateEntryEnum.UPR_Opening.getEntryCode(), EstimateStatusEnum.Forecast);
                if ((uprOpeningItem.getItemId() == null && uprOpeningItem.getAmount().equals(BigDecimal.ZERO))
                        || !uprOpeningItem.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
                                .equals(prevClosingUPR.setScale(2, BigDecimal.ROUND_HALF_UP))) {
                    uprOpeningItem.setAmount(prevClosingUPR);
                    uprOpeningItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
                }
                uprOpeningItem.setStatus(EstimateStatusEnum.Forecast);
                EstimateItem uprClosingItem = estimateQuarter.getEstItemByEntryCode(
                        EstimateEntryEnum.UPR_Closing.getEntryCode(), EstimateStatusEnum.Forecast);
                if (!uprClosingItem.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
                        .equals(closingUPR.setScale(2, BigDecimal.ROUND_HALF_UP))) {
                    uprClosingItem.setAmount(closingUPR);
                    uprClosingItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
                }
                uprClosingItem.setStatus(EstimateStatusEnum.Forecast);

                //UAC
                EstimateItem dacOpeningItem = estimateQuarter.getEstItemByEntryCode(
                        EstimateEntryEnum.DAC_Opening.getEntryCode(), EstimateStatusEnum.Forecast);
                if ((dacOpeningItem.getItemId() == null && dacOpeningItem.getAmount().equals(BigDecimal.ZERO))
                        || !dacOpeningItem.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
                                .equals(prevClosingDAC.setScale(2, BigDecimal.ROUND_HALF_UP))) {
                    dacOpeningItem.setAmount(prevClosingDAC);
                    dacOpeningItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
                }
                dacOpeningItem.setStatus(EstimateStatusEnum.Forecast);
                EstimateItem dacClosingItem = estimateQuarter.getEstItemByEntryCode(
                        EstimateEntryEnum.DAC_Closing.getEntryCode(), EstimateStatusEnum.Forecast);
                if (!dacClosingItem.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
                        .equals(closingDAC.setScale(2, BigDecimal.ROUND_HALF_UP))) {
                    dacClosingItem.setAmount(closingDAC);
                    dacClosingItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
                }
                dacClosingItem.setStatus(EstimateStatusEnum.Forecast);

                //EP
                EstimateItem epOpeningItem = estimateQuarter.getEstItemByEntryCode(
                        EstimateEntryEnum.EP_Opening.getEntryCode(), EstimateStatusEnum.Forecast);
                if ((epOpeningItem.getItemId() == null && epOpeningItem.getAmount().equals(BigDecimal.ZERO))
                        || !epOpeningItem.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
                                .equals(prevClosingEP.setScale(2, BigDecimal.ROUND_HALF_UP))) {
                    epOpeningItem.setAmount(prevClosingEP);
                    epOpeningItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
                }
                epOpeningItem.setStatus(EstimateStatusEnum.Forecast);
                EstimateItem epClosingItem = estimateQuarter.getEstItemByEntryCode(
                        EstimateEntryEnum.EP_Closing.getEntryCode(), EstimateStatusEnum.Forecast);
                if (!epClosingItem.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
                        .equals(closingEP.setScale(2, BigDecimal.ROUND_HALF_UP))) {
                    epClosingItem.setAmount(closingEP);
                    epClosingItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
                }
                epClosingItem.setStatus(EstimateStatusEnum.Forecast);

                //LossReserve
                EstimateItem reserveOpeningItem = estimateQuarter.getEstItemByEntryCode(
                        EstimateEntryEnum.LossReserve_Opening.getEntryCode(), EstimateStatusEnum.Forecast);
                if ((reserveOpeningItem.getItemId() == null && reserveOpeningItem.getAmount().equals(BigDecimal.ZERO))
                        || !reserveOpeningItem.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
                                .equals(accumulateEROfClosingReserve.setScale(2, BigDecimal.ROUND_HALF_UP))) {
                    reserveOpeningItem.setAmount(accumulateEROfClosingReserve);
                    reserveOpeningItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
                }
                reserveOpeningItem.setStatus(EstimateStatusEnum.Forecast);
                EstimateItem reserveClosingItem = estimateQuarter.getEstItemByEntryCode(
                        EstimateEntryEnum.LossReserve_Closing.getEntryCode(), EstimateStatusEnum.Forecast);
                if (!reserveClosingItem.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
                        .equals(closingReserve.setScale(2, BigDecimal.ROUND_HALF_UP))) {
                    reserveClosingItem.setAmount(closingReserve);
                    reserveClosingItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
                }
                reserveClosingItem.setStatus(EstimateStatusEnum.Forecast);
                accumulateEROfClosingReserve = accumulateEROfClosingReserve
                        .add(estimateQuarter.getForecastOfLossReserve());
            }
            //for next quarter
            prevClosingUPR = closingUPR;
            prevClosingDAC = closingDAC;
            prevClosingEP = closingEP;
        } while (prevClosingUPR.setScale(2, BigDecimal.ROUND_HALF_UP).abs().compareTo(BigDecimal.ZERO) > 0
                || quarterIndex < estModel.getEstQuarter().size());
    }

    /**
     * offset estimateItem with negate value in current quarter
     * 
     * @param estimateQuarter
     * @param acctFee
     */
    public void offsetEstimate(EstimateQuarter estimateQuarter, List<EstimateItem> adjustedEstimateItems,
                               TRiAcctFee acctFee) throws Exception {
        if (estimateQuarter.getQuarterStatus() == EstimateStatusEnum.Estimating) {
            Map<String, EstimateItem> estimateItems = estimateQuarter.getEstimateItems();
            for (EstimateItem temp : adjustedEstimateItems) {
                if (EstimateEntryEnum.LossReserve_Closing.getEntryCode().equals(temp.getEntryCode())) {
                    EstimateItem openingLossReserveItem = estimateItems
                            .get(EstimateEntryEnum.LossReserve_Opening.getEntryCode());
                    EstimateItem closingLossReserveItem = estimateItems
                            .get(EstimateEntryEnum.LossReserve_Closing.getEntryCode());
                    BigDecimal deltaLossReserve = closingLossReserveItem.getAmount()
                            .subtract(openingLossReserveItem.getAmount());
                    if (!deltaLossReserve.equals(temp.getAmount())) {
                        generateFeeInfo(acctFee, openingLossReserveItem, estimateQuarter, true);
                        generateFeeInfo(acctFee, closingLossReserveItem, estimateQuarter, true);
                    }
                } else {
                    EstimateItem estimateItem = estimateQuarter.getEstItemByEntryCode(temp.getEntryCode(),
                            EstimateStatusEnum.Estimated);
                    if (!estimateItem.getAmount().equals(temp.getAmount())) {
                        generateFeeInfo(acctFee, estimateItem, estimateQuarter, true);
                    }
                }
            }
        }
    }

    /**
     * offset estimateItem with negate value in current quarter
     * 
     * @param estimateQuarter
     * @param acctFee
     */
    public TRiAcctFee offsetUprDac(EstimateQuarter estimateQuarter) throws Exception {
        TRiAcctFee acctFee = new TRiAcctFee();
        acctFee.setBusiType(BizTransactionType.ESTIMATION.getType());
        Map<String, EstimateItem> estimateItems = estimateQuarter.getEstimateItems();
        EstimateItem openingUPRItem = estimateItems.get(EstimateEntryEnum.UPR_Opening.getEntryCode());
        EstimateItem closingUPRItem = estimateItems.get(EstimateEntryEnum.UPR_Closing.getEntryCode());
        generateFeeInfo(acctFee, openingUPRItem, estimateQuarter, true);
        generateFeeInfo(acctFee, closingUPRItem, estimateQuarter, true);

        EstimateItem openingDACItem = estimateItems.get(EstimateEntryEnum.DAC_Opening.getEntryCode());
        EstimateItem closingDACItem = estimateItems.get(EstimateEntryEnum.DAC_Closing.getEntryCode());
        generateFeeInfo(acctFee, openingDACItem, estimateQuarter, true);
        generateFeeInfo(acctFee, closingDACItem, estimateQuarter, true);
        return acctFee;
    }

    public TRiAcctFee generateEstimationOfUprDac(EstimateQuarter estimateQuarter) throws Exception {
        TRiAcctFee acctFee = new TRiAcctFee();
        acctFee.setBusiType(BizTransactionType.ESTIMATION.getType());
        Map<String, EstimateItem> forecastItems = estimateQuarter.getForecastItems();
        for (String entryCode : uprdacItems) {
            EstimateItem forecastItem = forecastItems.get(entryCode);
            EstimateItem estimateItem = estimateQuarter.getEstItemByEntryCode(entryCode, EstimateStatusEnum.Estimated);
            estimateItem.setAmount(forecastItem.getAmount());
            estimateItem.setChangeReason("Forecast Recalculation");
            //generate fee information
            generateFeeInfo(acctFee, estimateItem, estimateQuarter);
        }
        //clear other status Item map
        estimateQuarter.setForecastItems(new HashMap<String, EstimateItem>());
        estimateQuarter.setReverseItems(new HashMap<String, EstimateItem>());
        estimateQuarter.setActualItems(new HashMap<String, EstimateItem>());
        return acctFee;
    }

    /**
     * offset estimateItem with negate value in current quarter
     * 
     * @param estimateQuarter
     * @param acctFee
     */
    public void offsetUprDac(EstimateQuarter estimateQuarter, List<EstimateItem> adjustedEstimateItems,
                             TRiAcctFee acctFee) throws Exception {
        Map<String, EstimateItem> estimateItems = estimateQuarter.getEstimateItems();
        if (estimateQuarter.getQuarterStatus() == EstimateStatusEnum.Estimating) {
            for (EstimateItem temp : adjustedEstimateItems) {
                if (EstimateEntryEnum.UPR_Closing.getEntryCode().equals(temp.getEntryCode())) {
                    EstimateItem openingUPRItem = estimateItems.get(EstimateEntryEnum.UPR_Opening.getEntryCode());
                    EstimateItem closingUPRItem = estimateItems.get(EstimateEntryEnum.UPR_Closing.getEntryCode());
                    BigDecimal deltaUPR = closingUPRItem.getAmount().subtract(openingUPRItem.getAmount());
                    if (!deltaUPR.equals(temp.getAmount())) {
                        generateFeeInfo(acctFee, openingUPRItem, estimateQuarter, true);
                        generateFeeInfo(acctFee, closingUPRItem, estimateQuarter, true);
                    }
                } else if (EstimateEntryEnum.DAC_Closing.getEntryCode().equals(temp.getEntryCode())) {
                    EstimateItem openingDACItem = estimateItems.get(EstimateEntryEnum.DAC_Opening.getEntryCode());
                    EstimateItem closingDACItem = estimateItems.get(EstimateEntryEnum.DAC_Closing.getEntryCode());
                    BigDecimal deltaDAC = closingDACItem.getAmount().subtract(openingDACItem.getAmount());
                    if (!deltaDAC.equals(temp.getAmount())) {
                        generateFeeInfo(acctFee, openingDACItem, estimateQuarter, true);
                        generateFeeInfo(acctFee, closingDACItem, estimateQuarter, true);
                    }
                }
            }
        }
    }

    /**
     * offset estimateItem with negate value in current quarter
     * 
     * @param estimateQuarter
     * @param acctFee
     */
    public void offsetEstimate(EstimateQuarter estimateQuarter, TRiAcctFee acctFee) throws Exception {
        if (estimateQuarter.getQuarterStatus() == EstimateStatusEnum.Estimating) {
            List<EstimateItem> estimateItems = new ArrayList<EstimateItem>(
                    estimateQuarter.getEstMap(EstimateStatusEnum.Estimated).values());
            for (EstimateItem estimateItem : estimateItems) {
                generateFeeInfo(acctFee, estimateItem, estimateQuarter, true);
            }
        }
    }

    /**
     * generate estimate according to forecast
     * 
     * @param estimateQuarter
     * @param acctFee
     */
    public void generateEstimate(EstimateQuarter estimateQuarter, TRiAcctFee acctFee) throws Exception {
        //generate estimate of quarters whose status is forecast before current quarter
        if ((estimateQuarter.isOpenQuarter() || estimateQuarter.isUPRQuarter())
                && estimateQuarter.getYearQuarter().minus(accountingServiceImpl.currentFinancialQuarter()) <= 0) {
            //generate estimate according to forecast
            List<EstimateItem> forecastItems = new ArrayList<EstimateItem>(
                    estimateQuarter.getEstMap(EstimateStatusEnum.Forecast).values());
            Map<String, EstimateItem> actualMap = estimateQuarter.getEstMap(EstimateStatusEnum.Actual);
            for (EstimateItem forecastItem : forecastItems) {
                if (EstimateEntryEnum.getEstimateEntryEnumByEntryCode(forecastItem.getEntryCode()).needGenerateE()) {
                    EstimateItem estimateItem = estimateQuarter.getEstItemByEntryCode(forecastItem.getEntryCode(),
                            EstimateStatusEnum.Estimated);
                    EstimateItem actualItem = actualMap.get(forecastItem.getEntryCode());
                    //P = A + E =>E = P - A
                    if (actualItem == null) {
                        estimateItem.setAmount(forecastItem.getAmount());
                    } else {
                        estimateItem.setAmount(forecastItem.getAmount().subtract(actualItem.getAmount()));
                    }
                    estimateItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
                    //generate fee information
                    generateFeeInfo(acctFee, estimateItem, estimateQuarter);
                }
            }
            //change quarter status to Estimated
            estimateQuarter.setQuarterStatus(EstimateStatusEnum.Estimating);
        } else {
            estimateQuarter.setEstimateItems(new HashMap<String, EstimateItem>());
        }
        //clear other status Item map
        estimateQuarter.setForecastItems(new HashMap<String, EstimateItem>());
        estimateQuarter.setReverseItems(new HashMap<String, EstimateItem>());
        estimateQuarter.setActualItems(new HashMap<String, EstimateItem>());
    }

    /**
     * generate reverseItem by estimateItem
     * 
     * @param estimateQuarter
     * @param acctFee
     */
    public void reverseEstimate(EstimateQuarter estimateQuarter, TRiAcctFee acctFee) throws Exception {
        //reverse estimate of quarters whose status is estimating/estimated before current quarter
        if ((estimateQuarter.getQuarterStatus() == EstimateStatusEnum.Estimating
                || estimateQuarter.getQuarterStatus() == EstimateStatusEnum.Estimated)
                && estimateQuarter.getYearQuarter().minus(accountingServiceImpl.currentFinancialQuarter()) <= 0) {
            List<EstimateItem> estimateItems = new ArrayList<EstimateItem>(
                    estimateQuarter.getEstMap(EstimateStatusEnum.Estimated).values());
            for (EstimateItem estimateItem : estimateItems) {
                if (EstimateEntryEnum.getEstimateEntryEnumByEntryCode(estimateItem.getEntryCode()).needReverseE()) {
                    EstimateItem reverseItem = estimateQuarter.getEstItemByEntryCode(estimateItem.getEntryCode(),
                            EstimateStatusEnum.Reversed);
                    reverseItem.setEstimateId(estimateItem.getEstimateId());
                    reverseItem.setAmount(estimateItem.getAmount().negate());
                    reverseItem
                            .setChangeReason("Estimation reversal of UY" + estimateQuarter.getYearQuarter().toString()
                                    + "(" + ChangeReasonTool.getChangeReason().get() + ")");
                    //generate fee information
                    generateFeeInfo(acctFee, reverseItem, estimateQuarter);
                }
            }
            //change quarter status to Reversed
            estimateQuarter.setQuarterStatus(EstimateStatusEnum.Reversed);
        } else {
            estimateQuarter.setReverseItems(new HashMap<String, EstimateItem>());
        }
        //clear other status Item map
        estimateQuarter.setForecastItems(new HashMap<String, EstimateItem>());
        estimateQuarter.setEstimateItems(new HashMap<String, EstimateItem>());
        estimateQuarter.setActualItems(new HashMap<String, EstimateItem>());
    }

    public void generateFeeInfo(TRiAcctFee acctFee, EstimateItem estimateItem, EstimateQuarter quarterForecast) {
        generateFeeInfo(acctFee, estimateItem, quarterForecast, false);
    }

    public void generateFeeInfo(TRiAcctFee acctFee, EstimateItem estimateItem, EstimateQuarter quarterForecast,
                                boolean offset) {
        TRiAcctFeeDetail acctFeeDetail = new TRiAcctFeeDetail(acctFee);
        acctFeeDetail.setContCompId(quarterForecast.getContSectionId());
        acctFeeDetail.setCurrency(quarterForecast.getCurrency());
        acctFeeDetail.setEntryCode(estimateItem.getEntryCode());
        if (offset) {
            acctFeeDetail.setAmount(estimateItem.getAmount().negate());
        } else {
            acctFeeDetail.setAmount(estimateItem.getAmount());
        }
        acctFeeDetail.setPostingDate(AppContext.getSysDate());
    }

    public void adjustEstimation(EstimateQuarter estimateQuarter, List<EstimateItem> adjustedEstimateItems,
                                 TRiAcctFee acctFee) throws Exception {
        Map<String, EstimateItem> estimateItems = estimateQuarter.getEstimateItems();
        for (EstimateItem temp : adjustedEstimateItems) {
            if (EstimateEntryEnum.LossReserve_Closing.getEntryCode().equals(temp.getEntryCode())) {
                EstimateItem openingLossReserveItem = estimateItems
                        .get(EstimateEntryEnum.LossReserve_Opening.getEntryCode());
                EstimateItem closingLossReserveItem = estimateItems
                        .get(EstimateEntryEnum.LossReserve_Closing.getEntryCode());
                BigDecimal deltaLossReserve = closingLossReserveItem.getAmount()
                        .subtract(openingLossReserveItem.getAmount());
                if (!deltaLossReserve.equals(temp.getAmount())) {
                    closingLossReserveItem.setAmount(openingLossReserveItem.getAmount().add(temp.getAmount()));
                    closingLossReserveItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
                    generateFeeInfo(acctFee, openingLossReserveItem, estimateQuarter);
                    generateFeeInfo(acctFee, closingLossReserveItem, estimateQuarter);
                }
            } else {
                EstimateItem estimateItem = estimateItems.get(temp.getEntryCode());
                if (!estimateItem.getAmount().equals(temp.getAmount())) {
                    estimateItem.setAmount(temp.getAmount());
                    estimateItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
                    generateFeeInfo(acctFee, estimateItem, estimateQuarter);
                }
            }
        }
        estimateQuarter.setForecastItems(new HashMap<String, EstimateItem>());
        estimateQuarter.setReverseItems(new HashMap<String, EstimateItem>());
        estimateQuarter.setActualItems(new HashMap<String, EstimateItem>());
    }

    public void adjustUprDac(EstimateQuarter estimateQuarter, List<EstimateItem> adjustedEstimateItems,
                             TRiAcctFee acctFee) throws Exception {
        Map<String, EstimateItem> estimateItems = estimateQuarter.getEstimateItems();
        for (EstimateItem temp : adjustedEstimateItems) {
            if (EstimateEntryEnum.UPR_Closing.getEntryCode().equals(temp.getEntryCode())) {
                EstimateItem openingUPRItem = estimateItems.get(EstimateEntryEnum.UPR_Opening.getEntryCode());
                EstimateItem closingUPRItem = estimateItems.get(EstimateEntryEnum.UPR_Closing.getEntryCode());
                BigDecimal deltaUPR = closingUPRItem.getAmount().subtract(openingUPRItem.getAmount());
                if (!deltaUPR.equals(temp.getAmount())) {
                    closingUPRItem.setAmount(openingUPRItem.getAmount().add(temp.getAmount()));
                    closingUPRItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
                    generateFeeInfo(acctFee, openingUPRItem, estimateQuarter);
                    generateFeeInfo(acctFee, closingUPRItem, estimateQuarter);
                }
            } else if (EstimateEntryEnum.DAC_Closing.getEntryCode().equals(temp.getEntryCode())) {
                EstimateItem openingDACItem = estimateItems.get(EstimateEntryEnum.DAC_Opening.getEntryCode());
                EstimateItem closingDACItem = estimateItems.get(EstimateEntryEnum.DAC_Closing.getEntryCode());
                BigDecimal deltaDAC = closingDACItem.getAmount().subtract(openingDACItem.getAmount());
                if (!deltaDAC.equals(temp.getAmount())) {
                    closingDACItem.setAmount(openingDACItem.getAmount().add(temp.getAmount()));
                    closingDACItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
                    generateFeeInfo(acctFee, openingDACItem, estimateQuarter);
                    generateFeeInfo(acctFee, closingDACItem, estimateQuarter);
                }
            }
        }
        estimateQuarter.setForecastItems(new HashMap<String, EstimateItem>());
        estimateQuarter.setReverseItems(new HashMap<String, EstimateItem>());
        estimateQuarter.setActualItems(new HashMap<String, EstimateItem>());
    }

    public void actualization(EstimateQuarter estimateQuarter, TRiAcctFee acctFee) throws Exception {
        List<EstimateItem> estimateItemList = new ArrayList<EstimateItem>(estimateQuarter.getEstimateItems().values());
        if (CollectionUtils.isNotEmpty(estimateItemList)) {
            List<EstimateItem> reverseItemList = new ArrayList<EstimateItem>(
                    estimateQuarter.getReverseItems().values());
            if (CollectionUtils.isEmpty(reverseItemList) || (CollectionUtils.isNotEmpty(reverseItemList)
                    && reverseItemList.get(0).getAmount() == BigDecimal.ZERO)) {
                for (EstimateItem estimateItem : estimateItemList) {
                    EstimateItem reverseItem = estimateQuarter.getEstItemByEntryCode(estimateItem.getEntryCode(),
                            EstimateStatusEnum.Reversed);
                    reverseItem.setEstimateId(estimateItem.getEstimateId());
                    reverseItem.setAmount(estimateItem.getAmount().negate());
                    reverseItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
                    //generate fee information
                    generateFeeInfo(acctFee, reverseItem, estimateQuarter);
                }
                //change quarter status to Estimated
                estimateQuarter.setQuarterStatus(EstimateStatusEnum.Reversed);
            }
        }
    }

    public void withdrawSOA(EstimateQuarter estimateQuarter, TRiAcctFee acctFee) {
        //reverse estimate of quarters whose status is estimate before current quarter
        List<EstimateItem> reversedItems = new ArrayList<EstimateItem>(
                estimateQuarter.getEstMap(EstimateStatusEnum.Reversed).values());
        for (EstimateItem reversedItem : reversedItems) {
            generateFeeInfo(acctFee, reversedItem, estimateQuarter, true);
            reversedItem.setEstimateId(reversedItem.getEstimateId());
            reversedItem.setAmount(BigDecimal.ZERO);
            reversedItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
        }
        //clear other status Item map
        estimateQuarter.setQuarterStatus(EstimateStatusEnum.Estimated);
        estimateQuarter.setForecastItems(new HashMap<String, EstimateItem>());
        estimateQuarter.setEstimateItems(new HashMap<String, EstimateItem>());
        estimateQuarter.setActualItems(new HashMap<String, EstimateItem>());
    }

}
