package com.ebao.ap99.accounting.service.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.AccountingService;
import com.ebao.ap99.accounting.ContractPremiumModel;
import com.ebao.ap99.accounting.SectionPremiumModel;
import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.accounting.constant.ClosingStatusEnum;
import com.ebao.ap99.accounting.constant.EstimateStatusEnum;
import com.ebao.ap99.accounting.convertor.EstimateModelConvertor;
import com.ebao.ap99.accounting.dao.TRiAcctEstimateDao;
import com.ebao.ap99.accounting.dao.TRiAcctQuarterClosingDao;
import com.ebao.ap99.accounting.entity.TRiAcctEstimate;
import com.ebao.ap99.accounting.entity.TRiAcctQuarterClosing;
import com.ebao.ap99.accounting.estimation.EstimateCalculator;
import com.ebao.ap99.accounting.estimation.EstimateItem;
import com.ebao.ap99.accounting.estimation.EstimateModel;
import com.ebao.ap99.accounting.estimation.EstimateQuarter;
import com.ebao.ap99.accounting.model.ClaimEntryVO;
import com.ebao.ap99.accounting.util.ChangeReasonTool;
import com.ebao.ap99.parent.DataTypeConvertor;
import com.ebao.ap99.parent.context.AppContext;

public class AccountingServiceImpl implements AccountingService {

    @Autowired
    private EstimateModelConvertor estimateModelConvertor;

    @Autowired
    private EstimateDSImpl estimateDSImpl;

    @Autowired
    private TRiAcctEstimateDao acctEstimateDao;

    @Autowired
    private TRiAcctQuarterClosingDao tRiAcctQuarterClosingDao;

    EstimateCalculator calc = new EstimateCalculator();

    @Override
    public void calcForecastByPattern(ContractPremiumModel premModel) throws Exception {
        // for each section
        for (SectionPremiumModel sectionPremiumModel : premModel.getSectionPremList()) {
            Map<String, BigDecimal> flowItems = sectionPremiumModel.getFlowItems();
            if (flowItems != null && !flowItems.isEmpty()) {
                EstimateModel estimateModel = initEstimateModel(premModel, sectionPremiumModel);

                calcForecastByPattern_Section(flowItems, estimateModel);
                // convert and save result to DB
                List<TRiAcctEstimate> tRiAcctEstimateList = estimateModelConvertor
                        .convertEstimateModelToEntity(estimateModel);
                for (TRiAcctEstimate tRiAcctEstimate : tRiAcctEstimateList) {
                    acctEstimateDao.saveOrUpdate(tRiAcctEstimate);
                }
            }
        }
    }

    /**
     * @param changeReason
     * @param flowItems: <EntryCode, Amount>
     * @param estimateModel
     * @throws Exception
     */
    private void calcForecastByPattern_Section(Map<String, BigDecimal> flowItems, EstimateModel estimateModel)
            throws Exception {
        assert estimateModel != null : "Estimate Model cannot be empty";
        if (estimateModel.getEstQuarter() == null || estimateModel.getEstQuarter().isEmpty()) {
            estimateModel.initializeQuarter();
        }

        List<BigDecimal> dPattern = calc.getDistributePatternByContractId(estimateModel.getContractId());
        // for each flow item
        Iterator<String> it = flowItems.keySet().iterator();

        while (it.hasNext()) {
            String entryCode = it.next();

            BigDecimal totalAmount = flowItems.get(entryCode);
            BigDecimal curDistributed = BigDecimal.ZERO;

            for (EstimateQuarter estimateQuarter : estimateModel.getEstQuarter()) {
                BigDecimal quarterAmount = BigDecimal.ZERO;

                EstimateItem baseForecastItem = estimateQuarter.getEstItemByEntryCode(entryCode,
                        EstimateStatusEnum.BaseForecast);
                if (estimateQuarter.isOpenQuarter()) {
                    BigDecimal distributeRate = dPattern.get(estimateQuarter.getQuarterSeq() - 1);
                    BigDecimal accumulated = totalAmount.multiply(distributeRate);
                    // forecast can be changed.
                    quarterAmount = accumulated.subtract(curDistributed);

                    EstimateItem forecastItem = estimateQuarter.getEstItemByEntryCode(entryCode,
                            EstimateStatusEnum.Forecast);
                    forecastItem.setAmount(quarterAmount);
                    forecastItem.setStatus(EstimateStatusEnum.Forecast);
                    forecastItem.setCalcFormula(DataTypeConvertor.transAmoutToString(totalAmount) + "*" + distributeRate
                            + "-" + DataTypeConvertor.transAmoutToString(curDistributed));
                    forecastItem.setChangeReason(ChangeReasonTool.getChangeReason().get());
                } else if (estimateQuarter.isClosedQuarter()) {
                    // forecast cannot be changed
                    quarterAmount = estimateQuarter.getLatestAmount(entryCode);
                }
                baseForecastItem.setAmount(quarterAmount);
                baseForecastItem.setStatus(EstimateStatusEnum.BaseForecast);
                curDistributed = curDistributed.add(quarterAmount);

            } // end quarter
        } // end entry item
          // calculate UPR
        calc.calcForecastForClosingItem(estimateModel);
    }

    @Override
    public void adjustForecastOfOpenQuarter(ContractPremiumModel premModel) throws Exception {
        List<SectionPremiumModel> sectionPremModelList = premModel.getSectionPremList();
        // for each section
        for (SectionPremiumModel sectionPremiumModel : sectionPremModelList) {
            Map<String, BigDecimal> flowItems = sectionPremiumModel.getFlowItems();
            if (flowItems != null && !flowItems.isEmpty()) {
                EstimateModel estimateModel = initEstimateModel(premModel, sectionPremiumModel);
                // for each flow item
                Iterator<String> it = flowItems.keySet().iterator();
                while (it.hasNext()) {
                    String entryCode = it.next();
                    calc.adjustForecastEvenly(entryCode, estimateModel);
                }
                // calculate UPR
                calc.calcForecastForClosingItem(estimateModel);
                // convert and save result to DB
                List<TRiAcctEstimate> tRiAcctEstimateList = estimateModelConvertor
                        .convertEstimateModelToEntity(estimateModel);
                for (TRiAcctEstimate tRiAcctEstimate : tRiAcctEstimateList) {
                    acctEstimateDao.saveOrUpdate(tRiAcctEstimate);
                }
            }
        }
    }

    @Override
    public void adjustForecastForUPR$DACAdjust(ContractPremiumModel premModel) throws Exception {
        List<SectionPremiumModel> sectionPremModelList = premModel.getSectionPremList();
        // for each section
        for (SectionPremiumModel sectionPremiumModel : sectionPremModelList) {
            EstimateModel estimateModel = initEstimateModel(premModel, sectionPremiumModel);
            // calculate UPR
            calc.calcForecastForClosingItem(estimateModel);
            // convert and save result to DB
            List<TRiAcctEstimate> tRiAcctEstimateList = estimateModelConvertor
                    .convertEstimateModelToEntity(estimateModel);
            for (TRiAcctEstimate tRiAcctEstimate : tRiAcctEstimateList) {
                acctEstimateDao.saveOrUpdate(tRiAcctEstimate);
            }
        }
    }

    private EstimateModel initEstimateModel(ContractPremiumModel contractPremiumModel,
                                            SectionPremiumModel sectionPremiumModel) throws Exception {
        EstimateModel estimateModel = estimateDSImpl.loadEstimateModel(sectionPremiumModel.getSectionId());
        if (estimateModel == null) {
            estimateModel = convertToEstimateModel(contractPremiumModel, sectionPremiumModel);
        }
        BeanUtils.copyProperties(sectionPremiumModel, estimateModel);
        estimateModel.setTotalExpenseRate(sectionPremiumModel.getTotalExpenseRate());
        estimateModel.setContractId(contractPremiumModel.getContractId());
        return estimateModel;
    }

    private EstimateModel convertToEstimateModel(ContractPremiumModel contractPremiumModel,
                                                 SectionPremiumModel sectionPremiumModel) throws Exception {
        EstimateModel estimateModel = new EstimateModel(contractPremiumModel.getPeriodStart(),
                contractPremiumModel.getPeriodEnd(), sectionPremiumModel.getCurrency());
        return estimateModel;
    }

    private static YearQuarter currentFNQuarter = null;

    @Override
    public YearQuarter currentFinancialQuarter() throws Exception {
        if (null == currentFNQuarter)
            refreshClosingInfo();
        return currentFNQuarter;
    }

    private static ClosingStatusEnum currentFNQuarterStatus = null;

    @Override
    public boolean inClosingPeriod() throws Exception {
        if (null == currentFNQuarterStatus)
            refreshClosingInfo();
        return ClosingStatusEnum.Closing.equals(currentFNQuarterStatus);
    }

    @Override
    public void refreshClosingInfo() throws Exception {
        TRiAcctQuarterClosing currentQuarter = tRiAcctQuarterClosingDao.getCurrentQuarter();
        if (currentQuarter == null) {
            currentQuarter = new TRiAcctQuarterClosing();
            YearQuarter yq = new YearQuarter(AppContext.getSysDate());
            currentQuarter.setFinancialYear(yq.getYear());
            currentQuarter.setFinancialQuarter(yq.getQuarter());
            currentQuarter.setClosingStatus(ClosingStatusEnum.Open.getValue());
            currentQuarter.setClosingDate(yq.getClosingDate());
            tRiAcctQuarterClosingDao.insert(currentQuarter);
        }
        currentFNQuarter = new YearQuarter(currentQuarter.getFinancialYear(), currentQuarter.getFinancialQuarter());
        currentFNQuarterStatus = ClosingStatusEnum.getClosingStatusEnumByValue(currentQuarter.getClosingStatus());
        tRiAcctQuarterClosingDao.getEntityManager().detach(currentQuarter);
    }

    @Override
    public void afterTreatySubmit(List<Long> sectionIds, boolean specialSubmit) throws Exception {
        estimateDSImpl.afterTreatySubmit(sectionIds, specialSubmit);
    }

    @Override
    public void afterPricingEstimation(Long sectionId) throws Exception {
        estimateDSImpl.afterPricingEstimation(sectionId);
    }

    @Override
    public void afterClaimSubmit(List<ClaimEntryVO> claimEntryList) throws Exception {
        estimateDSImpl.afterClaimSubmit(claimEntryList);
    }

}
