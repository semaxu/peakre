/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ebao.ap99.accounting.AccountingService;
import com.ebao.ap99.accounting.ContractPremiumModel;
import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.accounting.constant.EstimateEntryEnum;
import com.ebao.ap99.accounting.constant.EstimateStatusEnum;
import com.ebao.ap99.accounting.constant.EventStatusEnum;
import com.ebao.ap99.accounting.constant.EventTypeEnum;
import com.ebao.ap99.accounting.convertor.EstimateModelConvertor;
import com.ebao.ap99.accounting.dao.TRiAcctEstimateDao;
import com.ebao.ap99.accounting.dao.TRiAcctEstimateItemHisDao;
import com.ebao.ap99.accounting.dao.TRiAcctEstimateTransDao;
import com.ebao.ap99.accounting.dao.TRiAcctFeeDao;
import com.ebao.ap99.accounting.dao.TRiAcctRecalForecastDao;
import com.ebao.ap99.accounting.entity.TRiAcctEstimate;
import com.ebao.ap99.accounting.entity.TRiAcctEstimateItemHis;
import com.ebao.ap99.accounting.entity.TRiAcctEstimateTrans;
import com.ebao.ap99.accounting.entity.TRiAcctFee;
import com.ebao.ap99.accounting.entity.TRiAcctFeeDetail;
import com.ebao.ap99.accounting.entity.TRiAcctRecalForecast;
import com.ebao.ap99.accounting.entity.TRiSoaSection;
import com.ebao.ap99.accounting.estimation.EstimateCalculator;
import com.ebao.ap99.accounting.estimation.EstimateItem;
import com.ebao.ap99.accounting.estimation.EstimateModel;
import com.ebao.ap99.accounting.estimation.EstimateQuarter;
import com.ebao.ap99.accounting.model.ActualItem;
import com.ebao.ap99.accounting.model.ClaimEntryVO;
import com.ebao.ap99.accounting.model.EntryItemVO;
import com.ebao.ap99.accounting.model.SoaEntry;
import com.ebao.ap99.accounting.model.UwViewVO;
import com.ebao.ap99.accounting.util.AccountingCst;
import com.ebao.ap99.accounting.util.ChangeReasonTool;
import com.ebao.ap99.arap.constant.BizTransactionType;
import com.ebao.ap99.arap.service.CurrencyExchangeService;
import com.ebao.ap99.arap.service.FinanceService;
import com.ebao.ap99.arap.vo.BusinessFee;
import com.ebao.ap99.arap.vo.BusinessFeeModel;
import com.ebao.ap99.arap.vo.FnViewVO;
import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.contract.entity.ContractModel;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.ap99.parent.model.TableColumn;

/**
 * Date: Jan 14, 2016 4:14:29 PM
 * 
 * @author xiaoyu.yang
 */
public class EstimateDSImpl {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EstimateModelConvertor estimateModelConvertor;

    @Autowired
    private TRiAcctEstimateDao acctEstimateDao;

    @Autowired
    private TRiAcctEstimateItemHisDao acctEstimateItemHisDao;

    @Autowired
    private TRiAcctEstimateTransDao acctEstimateTransDao;

    @Autowired
    private TRiAcctRecalForecastDao acctRecalForecastDao;

    @Autowired
    private AccountingService accountingService;

    @Autowired
    private TRiAcctFeeDao acctFeeDao;

    @Autowired
    private SoaBizServiceImpl soaBizServiceImpl;

    @Autowired
    private EstimateCalculator estimateCalculator;

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Autowired
    private FinanceService financeService;

    @Autowired
    private ContractService contractService;

    public EstimateQuarter getEstimateQuarter(long sectionId, YearQuarter cedentQuarter) throws Exception {
        TRiAcctEstimate tRiAcctEstimate = acctEstimateDao.findBySectionIdAndYearQuarter(sectionId,
                cedentQuarter.getYear(), cedentQuarter.getQuarter());
        acctEstimateDao.getEntityManager().detach(tRiAcctEstimate);
        tRiAcctEstimate = acctEstimateDao.findBySectionIdAndYearQuarter(sectionId, cedentQuarter.getYear(),
                cedentQuarter.getQuarter());
        EstimateQuarter estimateQuarter = estimateModelConvertor.convertToEstimateQuarter(tRiAcctEstimate);
        return estimateQuarter;
    }

    public List<EstimateQuarter> getEstimateQuarterListBySectionId(long sectionId) throws Exception {
        List<TRiAcctEstimate> acctEstimateList = acctEstimateDao.findListByCondition(new TRiAcctEstimate(sectionId));
        for (TRiAcctEstimate tRiAcctEstimate : acctEstimateList) {
            acctEstimateDao.getEntityManager().detach(tRiAcctEstimate);
        }
        acctEstimateList = acctEstimateDao.findListByCondition(new TRiAcctEstimate(sectionId));
        List<EstimateQuarter> estimateQuarterList = estimateModelConvertor
                .convertToEstimateQuarterList(acctEstimateList);
        return estimateQuarterList;
    }

    public void afterSOASubmit(Long soaId, boolean specialSubmit, YearQuarter cedentQuarter) throws Exception {
        YearQuarter currentFNQuarter = accountingService.currentFinancialQuarter();
        if (!accountingService.inClosingPeriod() || (accountingService.inClosingPeriod() && specialSubmit)) {
            // 1.get sectionId list of soa
            List<TRiSoaSection> sectionList = soaBizServiceImpl.findSoaSectionListBySoaId(soaId);
            // 2.update soa value
            updateActualValues(soaId, false);
            // 3.reverse Estimate
            reverseEstimation(cedentQuarter, sectionList);
            // 4.recalculateForecast
            if (specialSubmit) {
                for (TRiSoaSection soaSection : sectionList) {
                    recalculateForecast(soaSection.getContracCompId());
                    generateEstimate(soaSection.getContracCompId());
                }
            }
        } else {
            // add event of soa submit
            addEvent(null, EventTypeEnum.SOA.getValue(), currentFNQuarter.nextQuarter(), soaId);
        }
    }

    public void withdrawSOA(Long soaId, boolean specialSubmit, YearQuarter cedentQuarter) throws Exception {
        YearQuarter currentFNQuarter = accountingService.currentFinancialQuarter();
        if (!accountingService.inClosingPeriod() || (accountingService.inClosingPeriod() && specialSubmit)) {
            // 1.get sectionId list of soa
            List<TRiSoaSection> sectionList = soaBizServiceImpl.findSoaSectionListBySoaId(soaId);
            // 2.update soa value
            updateActualValues(soaId, true);
            // 3.reset reverse values
            resetReverseValues(cedentQuarter, sectionList);
            // 4.recalculateForecast
            if (specialSubmit) {
                for (TRiSoaSection soaSection : sectionList) {
                    recalculateForecast(soaSection.getContracCompId());
                    generateEstimate(soaSection.getContracCompId());
                }
            }
        } else {
            // add event of soa withdraw
            addEvent(null, EventTypeEnum.WITHDRAW.getValue(), currentFNQuarter.nextQuarter(), soaId);
        }
    }

    public void loadSOAofCurrentQuarter() throws Exception {
        YearQuarter currentQuarter = accountingService.currentFinancialQuarter();
        List<TRiAcctRecalForecast> taskList = acctRecalForecastDao.getEventListofSOA$Withdraw(currentQuarter);
        for (TRiAcctRecalForecast task : taskList) {
            if (task.getEventType() == EventTypeEnum.SOA.getValue()) {
                afterSOASubmit(task.getSoaId(), false, currentQuarter);
            } else if (task.getEventType() == EventTypeEnum.WITHDRAW.getValue()) {
                withdrawSOA(task.getSoaId(), false, currentQuarter);
            }
            finishSOAEvent(task.getSoaId(), task.getEventType(), currentQuarter);
        }
    }

    private void updateActualValues(Long soaId, boolean withdrawFlag) throws Exception {
        List<SoaEntry> compSectionList = soaBizServiceImpl.findSoaEntryListBySoaId(soaId);
        if (CollectionUtils.isEmpty(compSectionList)) {
            throw new RuntimeException("Can't get SOA info by soaId: " + soaId);
        }
        String soaText = saveTransaction(compSectionList, withdrawFlag);
        ChangeReasonTool.getChangeReason().set(soaText);
    }

    private String saveTransaction(List<SoaEntry> compSectionList, boolean withdrawFlag) throws Exception {
        String soaText = "";
        Map<String, EstimateQuarter> estimateIdMap = new HashMap<String, EstimateQuarter>();
        for (SoaEntry soaEntry : compSectionList) {
            if (StringUtils.isBlank(soaText)) {
                if (withdrawFlag) {
                    soaText = "Withdrawal of " + soaEntry.getSoaId() + " : " + soaEntry.getSoaText();
                } else {
                    soaText = soaEntry.getSoaId() + " : " + soaEntry.getSoaText();
                }
            }
            TRiAcctEstimateTrans transVO = new TRiAcctEstimateTrans();
            transVO.setTransType(1);
            String key = soaEntry.getSectionId() + "_" + soaEntry.getCedentYear() + "_" + soaEntry.getCedentQuarter();
            EstimateQuarter estimateQuarter = estimateIdMap.get(key);
            if (estimateQuarter == null) {
                estimateQuarter = getEstimateQuarter(soaEntry.getSectionId(),
                        new YearQuarter(soaEntry.getCedentYear(), soaEntry.getCedentQuarter()));
            }
            transVO.setEstimateId(estimateQuarter.getEstimateId());
            transVO.setCedentYear(soaEntry.getCedentYear());
            transVO.setCedentQuarter(soaEntry.getCedentQuarter());
            transVO.setContId(estimateQuarter.getContId());
            transVO.setSectionId(soaEntry.getSectionId());
            transVO.setEntryCode(soaEntry.getEntryCode());
            transVO.setCurrency(soaEntry.getCurrency());
            transVO.setMainCurrency(estimateQuarter.getCurrency());
            // exchange currency
            if (!soaEntry.getCurrency().equals(estimateQuarter.getCurrency())) {
                BigDecimal exchangeRate = currencyExchangeService.getExchangeRate(soaEntry.getCurrency(),
                        estimateQuarter.getCurrency());
                transVO.setToMainCurrencyRate(exchangeRate);
            } else {
                transVO.setToMainCurrencyRate(BigDecimal.ONE);
            }
            if (withdrawFlag) {
                transVO.setAmount(soaEntry.getAmount().negate());
            } else {
                transVO.setAmount(soaEntry.getAmount());
            }
            transVO.setText(soaText);
            transVO.setSoaId(soaEntry.getSoaId());
            transVO.setCalcFlag(false);
            acctEstimateTransDao.insert(transVO);
        }
        return soaText;
    }

    private void resetReverseValues(YearQuarter cedentQuarter, List<TRiSoaSection> sectionList) throws Exception {
        List<TRiAcctFee> acctFeeList = new ArrayList<TRiAcctFee>();
        for (TRiSoaSection soaSection : sectionList) {
            acctFeeList.add(resetReverseValues(cedentQuarter, soaSection.getContracCompId()));
        }
        for (TRiAcctFee acctFee : acctFeeList) {
            acctFeeDao.insert(acctFee);
        }
        postFinance(acctFeeList, true);
    }

    private TRiAcctFee resetReverseValues(YearQuarter cedentQuarter, Long sectionId) throws Exception {
        TRiAcctFee acctFee = new TRiAcctFee();
        acctFee.setBusiType(BizTransactionType.ESTIMATION.getType());
        EstimateQuarter estimateQuarter = getEstimateQuarter(sectionId, cedentQuarter);
        acctFee.setEstimateId(estimateQuarter.getEstimateId());
        estimateCalculator.withdrawSOA(estimateQuarter, acctFee);
        saveEstimateQuarter(estimateQuarter);
        return acctFee;
    }

    private void reverseEstimation(YearQuarter cedentQuarter, List<TRiSoaSection> sectionList) throws Exception {
        List<TRiAcctFee> acctFeeList = new ArrayList<TRiAcctFee>();
        for (TRiSoaSection soaSection : sectionList) {
            if (soaSection.isReversal()) {
                acctFeeList.add(reverseEstimation(cedentQuarter, soaSection.getContracCompId()));
            }
        }
        for (TRiAcctFee acctFee : acctFeeList) {
            acctFeeDao.insert(acctFee);
        }
        postFinance(acctFeeList, true);
    }

    private TRiAcctFee reverseEstimation(YearQuarter cedentQuarter, Long sectionId) throws Exception {
        TRiAcctFee acctFee = new TRiAcctFee();
        acctFee.setBusiType(BizTransactionType.ESTIMATION.getType());
        EstimateQuarter estimateQuarter = getEstimateQuarter(sectionId, cedentQuarter);
        acctFee.setEstimateId(estimateQuarter.getEstimateId());
        // if quarter status is Estimating/Estimated
        if (estimateQuarter.getQuarterStatus() == EstimateStatusEnum.Estimating
                || estimateQuarter.getQuarterStatus() == EstimateStatusEnum.Estimated) {
            estimateCalculator.reverseEstimate(estimateQuarter, acctFee);
            saveEstimateQuarter(estimateQuarter);
        }
        return acctFee;
    }

    public void afterTreatySubmit(List<Long> sectionIds, boolean specialSubmit) throws Exception {
        ChangeReasonTool.getChangeReason().set("Contract In-force");
        if (CollectionUtils.isNotEmpty(sectionIds)) {
            for (Long sectionId : sectionIds) {
                afterTreatySubmit(sectionId, specialSubmit);
            }
        }
    }

    private void afterTreatySubmit(Long sectionId, boolean specialSubmit) throws Exception {
        YearQuarter currentQuarter = accountingService.currentFinancialQuarter();
        if (accountingService.inClosingPeriod() && !specialSubmit) {
            addEvent(sectionId, EventTypeEnum.EPI.getValue(), currentQuarter.nextQuarter(), null);
        } else {
            addEvent(sectionId, EventTypeEnum.EPI.getValue(), currentQuarter, null);
        }
        generateForecastEstimation(sectionId);
    }

    public void afterPricingEstimation(Long sectionId) throws Exception {
        if (contractService.isCleanCutContractSection(sectionId)) {
            return;
        }
        //1.set changeReason
        ChangeReasonTool.getChangeReason().set("Pricing estimate adjustment at " + AppContext.getFormatDate());
        //2.get current fnQuarter and add event
        YearQuarter currentQuarter = accountingService.currentFinancialQuarter();
        addEvent(sectionId, EventTypeEnum.EPI.getValue(), currentQuarter, null);
        //3.generate forecast
        if (accountingService.inClosingPeriod()) {
            generateForecastEstimation(sectionId);
        }
    }

    public void afterClaimSubmit(List<ClaimEntryVO> claimEntryList) throws Exception {
        // save transVo
        saveClaimTransaction(claimEntryList);
    }

    public UwViewVO loadUwView(long level, long contCompId, String currency, Date queryDate) throws Exception {
        UwViewVO uwViewVO = new UwViewVO();
        List<EstimateQuarter> estimateQuarterList = null;
        ContractModel contractModel = contractService.getContractInfoByCompId(contCompId);
        if (contractModel != null) {
            uwViewVO.setMainCurrency(contractModel.getMainCurrency());
        }
        if (level == 1) {
            // Contract Level
            List<Long> sectionIds = acctEstimateDao.querySectionIdListByContractId(contCompId);
            boolean allCleanCut = true;
            Long sectionIdNotCleanCut = 0L;
            for (Long sectionId : sectionIds) {
                if (!contractService.isCleanCutContractSection(sectionId)) {
                    allCleanCut = false;
                    sectionIdNotCleanCut = sectionId;
                }
            }
            uwViewVO.setCleanCut(allCleanCut);
            estimateQuarterList = getEstimateQuarterListBySectionId(sectionIdNotCleanCut);
        } else {
            // Section Level
            estimateQuarterList = getEstimateQuarterListBySectionId(contCompId);
            if (contractService.isCleanCutContractSection(contCompId)) {
                uwViewVO.setCleanCut(true);
            }
        }
        if (CollectionUtils.isNotEmpty(estimateQuarterList)) {
            initialTableColumn(uwViewVO, estimateQuarterList);
            initialForecastTable(level, uwViewVO, contCompId, currency, queryDate, estimateQuarterList.size());
            initialEstimateTable(level, uwViewVO, contCompId, currency, queryDate, estimateQuarterList.size());
            initialReversalTable(level, uwViewVO, contCompId, currency, queryDate, estimateQuarterList.size());
            initialActualTable(level, uwViewVO, contCompId, currency, queryDate, estimateQuarterList.size());
            uwViewVO.setCurrentFNQuarter(accountingService.currentFinancialQuarter().toString());
            uwViewVO.setDate(queryDate);
        }
        return uwViewVO;
    }

    public FnViewVO loadFnView(long contCompId) throws Exception {
        return financeService.getFnView(contCompId);
    }

    public boolean allSectionsReversed(YearQuarter yearQuarter, List<Long> sectionIds) {
        boolean flag = true;
        List<TRiAcctEstimate> acctEstimates = acctEstimateDao.listByYearQuarterAndSectionIds(yearQuarter, sectionIds);
        if (CollectionUtils.isEmpty(acctEstimates)) {
            flag = true;
        } else {
            for (TRiAcctEstimate tRiAcctEstimate : acctEstimates) {
                if (tRiAcctEstimate.getStatus() != EstimateStatusEnum.Reversed.getValue()
                        && tRiAcctEstimate.getStatus() != EstimateStatusEnum.Actual.getValue()) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    public void regenerateUPR$DAC(Long sectionId) throws Exception {
        List<TRiAcctFee> acctFeeList = new ArrayList<TRiAcctFee>();
        YearQuarter currentFinancialQuarter = accountingService.currentFinancialQuarter();
        EstimateQuarter estimateQuarter = getEstimateQuarter(sectionId, currentFinancialQuarter);
        // offset original UPR&DAC
        acctFeeList.add(estimateCalculator.offsetUprDac(estimateQuarter));
        // generate new estimation of UPR&DAC
        acctFeeList.add(estimateCalculator.generateEstimationOfUprDac(estimateQuarter));
        // Post AcctFee
        for (TRiAcctFee acctFee : acctFeeList) {
            acctFeeDao.insert(acctFee);
        }
        postFinance(acctFeeList, false);
    }

    private void generateEstimate(Long sectionId) throws Exception {
        if (contractService.isCleanCutContractSection(sectionId)) {
            return;
        }
        // new AcctFee
        List<TRiAcctFee> acctFeeList = new ArrayList<TRiAcctFee>();
        List<EstimateQuarter> estimateQuarterList = getEstimateQuarterListBySectionId(sectionId);
        for (EstimateQuarter estimateQuarter : estimateQuarterList) {
            acctFeeList.add(generateEstimate(sectionId, estimateQuarter));
        }
        // Post AcctFee
        for (TRiAcctFee acctFee : acctFeeList) {
            acctFeeDao.insert(acctFee);
        }
        postFinance(acctFeeList, false);
    }

    private TRiAcctFee generateEstimate(Long sectionId, EstimateQuarter estimateQuarter) throws Exception {
        TRiAcctFee acctFee = new TRiAcctFee();
        acctFee.setBusiType(BizTransactionType.ESTIMATION.getType());
        acctFee.setEstimateId(estimateQuarter.getEstimateId());
        // if status is Estimated, offset
        //estimateCalculator.offsetEstimate(estimateQuarter, currentFinancialQuarter, acctFee);
        // Generate estimate from forecast
        estimateCalculator.generateEstimate(estimateQuarter, acctFee);
        saveEstimateQuarter(estimateQuarter);
        return acctFee;
    }

    private void postFinance(List<TRiAcctFee> acctFeeList, boolean isReversal) throws Exception {
        List<BusinessFeeModel> modelList = new ArrayList<BusinessFeeModel>();
        for (TRiAcctFee acctFee : acctFeeList) {
            BusinessFeeModel businessFeeModel = convertToBusinessFeeModel(acctFee, isReversal);
            if (businessFeeModel != null) {
                modelList.add(businessFeeModel);
            }
        }
        financeService.writeToFinance(modelList);
    }

    private BusinessFeeModel convertToBusinessFeeModel(TRiAcctFee tRiAcctFee, boolean isReversal) throws Exception {
        List<TRiAcctFeeDetail> feeDetailList = tRiAcctFee.getTRiAcctFeeDetails();
        if (CollectionUtils.isEmpty(feeDetailList)) {
            return null;
        }
        BusinessFeeModel businessFeeModel = new BusinessFeeModel();
        List<BusinessFee> feeList = new ArrayList<BusinessFee>();
        ContractModel contractModel = contractService.getContractInfoByCompId(feeDetailList.get(0).getContCompId());
        for (TRiAcctFeeDetail tRiAcctFeeDetail : feeDetailList) {
            feeList.add(convertToBusinessFee(tRiAcctFeeDetail, isReversal));
        }
        businessFeeModel.setContractCategory(Integer.parseInt(contractModel.getContractCategory()));
        businessFeeModel.setBizTransType(BizTransactionType.ESTIMATION.getType());
        businessFeeModel.setBizTransNo(contractModel.getContractId().toString());
        businessFeeModel.setBizTransId(tRiAcctFee.getEstimateId());
        businessFeeModel.setContractId(contractModel.getContractId());
        businessFeeModel.setPartnerCode(contractModel.getCedent());
        businessFeeModel.setFeeList(feeList);
        return businessFeeModel;
    }

    private BusinessFee convertToBusinessFee(TRiAcctFeeDetail tRiAcctFeeDetail, boolean isReversal) {
        BusinessFee businessFee = new BusinessFee();
        businessFee.setBizOperatorId(AppContext.getAppUser().getUserId());
        businessFee.setEntryCode(tRiAcctFeeDetail.getEntryCode());
        businessFee.setAmount(tRiAcctFeeDetail.getAmount());
        businessFee.setCurrencyCode(tRiAcctFeeDetail.getCurrency());
        businessFee.setCurrentPeriod(1);
        businessFee.setTotalPeriod(1);
        businessFee.setEstimation(true);
        businessFee.setBizRefId(tRiAcctFeeDetail.getContCompId());
        businessFee.setSectionId(tRiAcctFeeDetail.getContCompId());
        if (EstimateEntryEnum
                .getEstimateEntryEnumByEntryCode(tRiAcctFeeDetail.getEntryCode()) != EstimateEntryEnum.UPR_Closing
                && EstimateEntryEnum.getEstimateEntryEnumByEntryCode(
                        tRiAcctFeeDetail.getEntryCode()) != EstimateEntryEnum.DAC_Closing) {
            businessFee.setEstimation(true);
        }
        businessFee.setReversal(isReversal);
        return businessFee;
    }

    /**
     * finish EPI Event : update status to EventStatusEnum.Done(=2)
     * 
     * @param sectionId
     * @param yearQuarter
     */
    private void finishEPIEvent(Long sectionId, YearQuarter yearQuarter) {
        acctRecalForecastDao.finishEPIEvent(sectionId, yearQuarter);
    }

    /**
     * finish SOA/Withdraw Event : update status to EventStatusEnum.Done(=2)
     * 
     * @param soaId
     * @param eventType
     * @param yearQuarter
     */
    private void finishSOAEvent(Long soaId, Integer eventType, YearQuarter yearQuarter) {
        acctRecalForecastDao.finishSOAEvent(soaId, eventType, yearQuarter);
    }

    /**
     * load estimateModel by sectionId
     * 
     * @param sectionId
     * @return
     */
    public EstimateModel loadEstimateModel(Long sectionId) throws Exception {
        EstimateModel estimateModel = null;
        List<EstimateQuarter> estimateQuarters = getEstimateQuarterListBySectionId(sectionId);
        if (CollectionUtils.isNotEmpty(estimateQuarters)) {
            estimateModel = new EstimateModel(estimateQuarters);
            estimateModel.setContractId(estimateQuarters.get(0).getContId());
            estimateModel.setSectionId(estimateQuarters.get(0).getContSectionId());
        }
        return estimateModel;
    }

    private List<Integer> queryPendingRecalEvent(Long sectionId) throws Exception {
        List<Integer> eventTypeList = acctRecalForecastDao.getPendingEventTypeBySectionId(sectionId,
                accountingService.currentFinancialQuarter());
        return eventTypeList;
    }

    private void addEvent(Long sectionId, Integer eventTyp, YearQuarter calcQuarter, Long soaId) {
        // query pending event by Type
        TRiAcctRecalForecast condition = new TRiAcctRecalForecast();
        condition.setEventType(eventTyp);
        if (EventTypeEnum.EPI.getValue().equals(eventTyp)) {
            condition.setSectionId(sectionId);
        }
        if (EventTypeEnum.SOA.getValue().equals(eventTyp) || EventTypeEnum.WITHDRAW.getValue().equals(eventTyp)) {
            condition.setSoaId(soaId);
        }
        List<TRiAcctRecalForecast> taskList = acctRecalForecastDao.findListByCondition(condition);
        // to update trigger time or insert new.
        if (CollectionUtils.isNotEmpty(taskList)) {
            taskList.get(0).setStatus(EventStatusEnum.Pending.getValue());
            taskList.get(0).setUpdateTime(AppContext.getSysDate());
            taskList.get(0).setCalcQuarter(calcQuarter.toString());
        } else {
            condition.setStatus(EventStatusEnum.Pending.getValue());
            condition.setCalcQuarter(calcQuarter.toString());
            acctRecalForecastDao.insert(condition);
        }
    }

    private void initialTableColumn(UwViewVO uwViewVO, List<EstimateQuarter> estimateQuarterList) {
        List<TableColumn> tableColumns = new ArrayList<TableColumn>();
        int i = 1;
        for (EstimateQuarter estimateQuarter : estimateQuarterList) {
            TableColumn tableColumn = new TableColumn(estimateQuarter.getYearQuarter().toString(),
                    TableColumn.VALUE_KEY + i, estimateQuarter.getQuarterStatus().getValue());
            tableColumns.add(tableColumn);
            i++;
        }
        uwViewVO.setTableColumns(tableColumns);
    }

    private void initialForecastTable(long level, UwViewVO uwViewVO, long contCompId, String currency, Date queryDate,
                                      int quarterSize) throws Exception {
        List<EntryItemVO> estimationTable = acctEstimateItemHisDao.loadViewTable(level, contCompId,
                EstimateStatusEnum.Forecast.getValue(), queryDate, quarterSize);
        Collections.sort(estimationTable);
        handleData(uwViewVO, estimationTable, currency);
        uwViewVO.setForecastTable(estimationTable);
    }

    private void initialEstimateTable(long level, UwViewVO uwViewVO, long contCompId, String currency, Date queryDate,
                                      int quarterSize) throws Exception {
        List<EntryItemVO> estimationTable = acctEstimateItemHisDao.loadViewTable(level, contCompId,
                EstimateStatusEnum.Estimated.getValue(), queryDate, quarterSize);
        Collections.sort(estimationTable);
        handleData(uwViewVO, estimationTable, currency);
        uwViewVO.setEstimationTable(estimationTable);
    }

    private void initialReversalTable(long level, UwViewVO uwViewVO, long contCompId, String currency, Date queryDate,
                                      int quarterSize) throws Exception {
        List<EntryItemVO> reversalTable = acctEstimateItemHisDao.loadViewTable(level, contCompId,
                EstimateStatusEnum.Reversed.getValue(), queryDate, quarterSize);
        Collections.sort(reversalTable);
        handleData(uwViewVO, reversalTable, currency);
        uwViewVO.setReversalTable(reversalTable);
    }

    private void initialActualTable(long level, UwViewVO uwViewVO, long contCompId, String currency, Date queryDate,
                                    int quarterSize) throws Exception {
        List<EntryItemVO> actualTable = acctEstimateTransDao.loadActualTable(level, contCompId, queryDate, quarterSize);
        // List<EntryItemVO> actualTable =
        // acctEstimateItemHisDao.loadViewTable(level, contCompId,
        // EstimateStatusEnum.Actual.getValue(), queryDate, quarterSize);
        Collections.sort(actualTable);
        handleData(uwViewVO, actualTable, currency);
        uwViewVO.setActualTable(actualTable);
    }

    private void handleData(UwViewVO uwViewVO, List<EntryItemVO> entryItemTable, String currency) throws Exception {
        Collections.sort(entryItemTable);
        if (!currency.equals(AccountingCst.SECTION_MAIN_CURRENCY) && !currency.equals(uwViewVO.getMainCurrency())) {
            BigDecimal exchangeRate = currencyExchangeService.getExchangeRate(uwViewVO.getMainCurrency(), currency);
            for (EntryItemVO entryItemVO : entryItemTable) {
                entryItemVO.setExchangeRate(exchangeRate);
            }
        }
    }

    private void saveEstimateQuarter(EstimateQuarter estimateQuarter) throws Exception {
        TRiAcctEstimate tRiAcctEstimate = estimateModelConvertor.convertToTRiAcctEstimate(estimateQuarter);
        acctEstimateDao.saveOrUpdate(tRiAcctEstimate);
        acctEstimateDao.getEntityManager().detach(tRiAcctEstimate);
    }

    public void generateForecastEstimation(Long sectionId) throws Exception {
        List<Integer> recalEvent = queryPendingRecalEvent(sectionId);// order by
                                                                     // re-calc
                                                                     // time
        YearQuarter currentFinancialQuarter = accountingService.currentFinancialQuarter();
        if (recalEvent.contains(EventTypeEnum.EPI.getValue())) {// process
                                                                // 'Pricing
                                                                // Estimation'
            ContractPremiumModel premModel = (ContractPremiumModel) contractService
                    .getPremiumModelBySectionId(sectionId);
            accountingService.calcForecastByPattern(premModel);
            finishEPIEvent(sectionId, currentFinancialQuarter);
        }
        if (accountingService.inClosingPeriod()) {
            generateEstimate(sectionId);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void processCutoff(Long sectionId) throws Exception {
        List<Integer> recalEvent = queryPendingRecalEvent(sectionId);
        if (recalEvent.contains(EventTypeEnum.EPI.getValue())) {
            // process 'Pricing Estimation'
            ContractPremiumModel premModel = (ContractPremiumModel) contractService
                    .getPremiumModelBySectionId(sectionId);
            accountingService.calcForecastByPattern(premModel);
            finishEPIEvent(sectionId, accountingService.currentFinancialQuarter());
        }
        if (accountingService.inClosingPeriod()) {
            recalculateForecast(sectionId);
            generateEstimate(sectionId);
        }
    }

    public void recalculateForecast(Long sectionId) throws Exception {
        if (contractService.isCleanCutContractSection(sectionId)) {
            return;
        }
        List<Integer> recalEvent = queryPendingRecalEvent(sectionId);
        YearQuarter currentFinancialQuarter = accountingService.currentFinancialQuarter();
        if (recalEvent.contains(EventTypeEnum.EPI.getValue())) {
            // process 'Pricing Estimation'
            ContractPremiumModel premModel = (ContractPremiumModel) contractService
                    .getPremiumModelBySectionId(sectionId);
            accountingService.calcForecastByPattern(premModel);
            finishEPIEvent(sectionId, currentFinancialQuarter);
        }
        adjustForecastOfOpenQuarter(sectionId);
    }

    private void adjustForecastOfOpenQuarter(Long sectionId) throws Exception {
        ContractPremiumModel premModel = (ContractPremiumModel) contractService.getPremiumModelBySectionId(sectionId);
        //update calcFlag as 'Y' of estimateTrans (To Display Actual Value In Overview Table)
        acctEstimateTransDao.updateCalcFlag(sectionId);
        //update estimateQuarter status from Reversed to Actual
        acctEstimateDao.convertReversedToActual(sectionId);
        accountingService.adjustForecastOfOpenQuarter(premModel);
    }

    public void adjustForecastForUPR$DACAdjust(Long sectionId) throws Exception {
        ContractPremiumModel premModel = (ContractPremiumModel) contractService.getPremiumModelBySectionId(sectionId);
        accountingService.adjustForecastForUPR$DACAdjust(premModel);
    }

    public List<EstimateQuarter> getQuarterWhichNeedPostEstimation(Long sectionId) throws Exception {
        List<EstimateQuarter> list = new ArrayList<EstimateQuarter>();
        List<EstimateQuarter> estimateQuarterList = getEstimateQuarterListBySectionId(sectionId);
        for (EstimateQuarter estimateQuarter : estimateQuarterList) {
            if (estimateQuarter.isOpenQuarter()
                    && estimateQuarter.getYearQuarter().minus(accountingService.currentFinancialQuarter()) <= 0) {
                list.add(estimateQuarter);
            }
        }
        return list;
    }

    /**
     * manually post estimation before cut-off batch
     * 
     * @param sectionId
     * @param estimateIds
     * @throws Exception
     */
    public void postEstimation(long sectionId, List<Long> estimateIds) throws Exception {
        // new AcctFee
        List<TRiAcctFee> acctFeeList = new ArrayList<TRiAcctFee>();
        for (Long estimateId : estimateIds) {
            EstimateQuarter estimateQuarter = estimateModelConvertor
                    .convertToEstimateQuarter(acctEstimateDao.load(estimateId));
            acctFeeList.add(generateEstimate(sectionId, estimateQuarter));
        }
        // Post AcctFee
        for (TRiAcctFee acctFee : acctFeeList) {
            acctFeeDao.insert(acctFee);
        }
        postFinance(acctFeeList, false);
    }

    /**
     * manually adjust estimate of current fn quarter
     * 
     * @param sectionId
     * @param adjustedEstimateItems
     * @throws Exception
     */
    public void adjustEstimation(Long sectionId, Map<String, List<EstimateItem>> estimateItemMap) throws Exception {
        List<TRiAcctFee> acctFeeList = new ArrayList<TRiAcctFee>();
        Iterator<String> it = estimateItemMap.keySet().iterator();
        while (it.hasNext()) {
            String cedentQuarter = it.next();
            List<EstimateItem> adjustedEstimateItems = estimateItemMap.get(cedentQuarter);
            // 1.offset and adjust Estimation
            acctFeeList.add(adjustEstimation(sectionId, new YearQuarter(cedentQuarter), adjustedEstimateItems));
        }
        // 2.Post AcctFee
        for (TRiAcctFee acctFee : acctFeeList) {
            acctFeeDao.insert(acctFee);
        }
        postFinance(acctFeeList, false);
    }

    private TRiAcctFee adjustEstimation(Long sectionId, YearQuarter cedentQuarter,
                                        List<EstimateItem> adjustedEstimateItems) throws Exception {
        TRiAcctFee acctFee = new TRiAcctFee();
        acctFee.setBusiType(BizTransactionType.ESTIMATION.getType());
        EstimateQuarter estimateQuarter = getEstimateQuarter(sectionId, cedentQuarter);
        acctFee.setEstimateId(estimateQuarter.getEstimateId());
        // if status is Estimated, offset
        estimateCalculator.offsetEstimate(estimateQuarter, adjustedEstimateItems, acctFee);
        // adjust estimate
        estimateCalculator.adjustEstimation(estimateQuarter, adjustedEstimateItems, acctFee);
        saveEstimateQuarter(estimateQuarter);
        return acctFee;
    }

    public void adjustUprDac(Long sectionId, Map<String, List<EstimateItem>> estimateItemMap) throws Exception {
        List<TRiAcctFee> acctFeeList = new ArrayList<TRiAcctFee>();
        Iterator<String> it = estimateItemMap.keySet().iterator();
        while (it.hasNext()) {
            String cedentQuarter = it.next();
            List<EstimateItem> adjustedEstimateItems = estimateItemMap.get(cedentQuarter);
            // 1.offset and adjust Estimation
            acctFeeList.add(adjustUprDac(sectionId, new YearQuarter(cedentQuarter), adjustedEstimateItems));
        }
        // 2.Post AcctFee
        for (TRiAcctFee acctFee : acctFeeList) {
            acctFeeDao.insert(acctFee);
        }
        postFinance(acctFeeList, false);
    }

    private TRiAcctFee adjustUprDac(Long sectionId, YearQuarter cedentQuarter, List<EstimateItem> adjustedEstimateItems)
            throws Exception {
        TRiAcctFee acctFee = new TRiAcctFee();
        acctFee.setBusiType(BizTransactionType.ESTIMATION.getType());
        EstimateQuarter estimateQuarter = getEstimateQuarter(sectionId, cedentQuarter);
        acctFee.setEstimateId(estimateQuarter.getEstimateId());
        // if status is Estimated, offset
        estimateCalculator.offsetUprDac(estimateQuarter, adjustedEstimateItems, acctFee);
        // adjust estimate
        estimateCalculator.adjustUprDac(estimateQuarter, adjustedEstimateItems, acctFee);
        //        adjustOpeningItemOfNextForecastQuarter(sectionId, cedentQuarter, deltaAmountMap);
        saveEstimateQuarter(estimateQuarter);
        return acctFee;
    }

    public List<TRiAcctEstimateItemHis> loadBusinessDetails(Long contCompId, String entryCode, int cedentYear,
                                                            int cedentQuarter, int status) {
        return acctEstimateItemHisDao.loadBusinessDetails(contCompId, entryCode, cedentYear, cedentQuarter, status);
    }

    public List<ActualItem> loadActualDetails(Long contCompId, String entryCode, int cedentYear, int cedentQuarter)
            throws Exception {
        List<TRiAcctEstimateTrans> actualItemList = acctEstimateTransDao.loadTransactionDetail(contCompId, cedentYear,
                cedentQuarter, entryCode);
        List<ActualItem> list = new ArrayList<ActualItem>();
        for (TRiAcctEstimateTrans temp : actualItemList) {
            ActualItem actualItem = new ActualItem();
            BeanUtils.copyProperties(temp, actualItem);
            list.add(actualItem);
        }
        return list;
    }

    public void recalculateForecastByContractId(Long contractId) throws Exception {
        List<Long> sectionIdList = acctEstimateDao.querySectionIdListByContractId(contractId);
        for (Long sectionId : sectionIdList) {
            recalculateForecast(sectionId);
        }
    }

    public void actualizedByContractId(Long contractId) throws Exception {
        generateReverseAndFee(contractId);
        contractService.actualizeByContractId(contractId);
    }

    private void generateReverseAndFee(Long contractId) throws Exception {
        List<TRiAcctFee> acctFeeList = new ArrayList<TRiAcctFee>();
        List<Long> sectionIdList = acctEstimateDao.querySectionIdListByContractId(contractId);
        for (Long sectionId : sectionIdList) {
            acctFeeList.addAll(actualizedBySectionId(sectionId));
        }
        for (TRiAcctFee acctFee : acctFeeList) {
            acctFeeDao.insert(acctFee);
        }
        postFinance(acctFeeList, false);
    }

    private List<TRiAcctFee> actualizedBySectionId(Long sectionId) throws Exception {
        List<TRiAcctFee> accFeeList = new ArrayList<TRiAcctFee>();
        List<EstimateQuarter> estimateQuarterList = getEstimateQuarterListBySectionId(sectionId);
        for (EstimateQuarter estimateQuarter : estimateQuarterList) {
            TRiAcctFee acctFee = new TRiAcctFee();
            acctFee.setBusiType(BizTransactionType.ESTIMATION.getType());
            acctFee.setEstimateId(estimateQuarter.getEstimateId());
            estimateCalculator.actualization(estimateQuarter, acctFee);
            accFeeList.add(acctFee);
        }
        for (EstimateQuarter estimateQuarter : estimateQuarterList) {
            saveEstimateQuarter(estimateQuarter);
        }
        return accFeeList;
    }

    private void saveClaimTransaction(List<ClaimEntryVO> claimEntryList) throws Exception {
        String claimText = "";
        for (ClaimEntryVO claimEntry : claimEntryList) {
            if (claimEntry.getBizTransType().equals(1)) {

                if (claimEntry.getRefType().equals(ClaimConstant.REF_TYPE_CLAIM)) {
                    claimText = "Reserve update (Claim No.:" + claimEntry.getClaimNo() + ")";
                } else {
                    claimText = "Reserve update (Event Code:" + claimEntry.getClaimNo() + ")";
                }
            } else {
                if (claimEntry.getRefType().equals(ClaimConstant.REF_TYPE_CLAIM)) {
                    claimText = "Settlement: " + claimEntry.getSettlementNo() + " (Claim No.: "
                            + claimEntry.getClaimNo() + ")";
                } else {
                    claimText = "Settlement: " + claimEntry.getSettlementNo() + " (Event Code: "
                            + claimEntry.getClaimNo() + ")";
                }
            }

            TRiAcctEstimate acctEstimate = acctEstimateDao.findBySectionIdAndPeriod(claimEntry.getSectionId(),
                    claimEntry.getDateOfLoss());
            TRiAcctEstimateTrans transVO = new TRiAcctEstimateTrans();
            if (!claimEntry.getCurrency().equals(acctEstimate.getCurrency())) {
                BigDecimal exchangeRate = currencyExchangeService.getExchangeRate(claimEntry.getCurrency(),
                        acctEstimate.getCurrency());
                transVO.setToMainCurrencyRate(exchangeRate);
            } else {
                transVO.setToMainCurrencyRate(BigDecimal.ONE);
            }
            transVO.setTransType(2);
            transVO.setCedentYear(acctEstimate.getCedentYear());
            transVO.setCedentQuarter(acctEstimate.getCedentQuarter());
            transVO.setContId(acctEstimate.getContId());
            transVO.setSectionId(claimEntry.getSectionId());
            transVO.setEstimateId(acctEstimate.getEstimateId());
            transVO.setEntryCode(claimEntry.getEntryCode());
            transVO.setCurrency(claimEntry.getCurrency());
            transVO.setMainCurrency(acctEstimate.getCurrency());
            transVO.setAmount(claimEntry.getAmount());
            transVO.setText(claimText);
            transVO.setClaimNo(claimEntry.getClaimNo());
            transVO.setBizTransType(claimEntry.getBizTransType());
            transVO.setBizDirection(Integer.parseInt(claimEntry.getBusinessDirection()));
            transVO.setDateOfLoss(claimEntry.getDateOfLoss());
            transVO.setCalcFlag(false);
            acctEstimateTransDao.insert(transVO);
        }
    }

}
