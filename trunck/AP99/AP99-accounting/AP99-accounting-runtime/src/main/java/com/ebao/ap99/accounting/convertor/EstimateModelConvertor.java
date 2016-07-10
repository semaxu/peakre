/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.convertor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.accounting.constant.EstimateStatusEnum;
import com.ebao.ap99.accounting.dao.TRiAcctEstimateTransDao;
import com.ebao.ap99.accounting.entity.TRiAcctEstimate;
import com.ebao.ap99.accounting.entity.TRiAcctEstimateItem;
import com.ebao.ap99.accounting.entity.TRiAcctEstimateItemHis;
import com.ebao.ap99.accounting.estimation.EstimateItem;
import com.ebao.ap99.accounting.estimation.EstimateModel;
import com.ebao.ap99.accounting.estimation.EstimateQuarter;
import com.ebao.ap99.parent.BeanUtils;

/**
 * Date: Jan 21, 2016 2:59:29 PM
 * 
 * @author xiaoyu.yang
 */
public class EstimateModelConvertor {

    @Autowired
    private TRiAcctEstimateTransDao acctEstimateTransDao;

    public List<TRiAcctEstimate> convertEstimateModelToEntity(EstimateModel estimateModel) {
        List<TRiAcctEstimate> list = new ArrayList<TRiAcctEstimate>();
        for (EstimateQuarter estimateQuarter : estimateModel.getEstQuarter()) {
            TRiAcctEstimate tRiAcctEstimate = convertToTRiAcctEstimate(estimateQuarter);
            tRiAcctEstimate.setContId(estimateModel.getContractId());
            tRiAcctEstimate.setContSectionId(estimateModel.getSectionId());
            list.add(tRiAcctEstimate);
        }
        return list;
    }

    public EstimateModel convertToEstimateModel(List<TRiAcctEstimate> estimateList) {
        List<EstimateQuarter> estimateQuarters = convertToEstimateQuarterList(estimateList);
        EstimateModel estimateModel = new EstimateModel(estimateQuarters);
        estimateModel.setContractId(estimateQuarters.get(0).getContId());
        estimateModel.setSectionId(estimateQuarters.get(0).getContSectionId());
        return estimateModel;
    }

    public List<EstimateQuarter> convertToEstimateQuarterList(List<TRiAcctEstimate> estimateList) {
        List<EstimateQuarter> list = new ArrayList<EstimateQuarter>();
        if (!estimateList.isEmpty()) {
            for (TRiAcctEstimate acctEstimate : estimateList) {
                list.add(convertToEstimateQuarter(acctEstimate));
            }
        }
        return list;
    }

    /**
     * Convert to TRiAcctEstimate to EstimateQuarter
     * 
     * @param tRiAcctEstimate
     * @return
     */
    public EstimateQuarter convertToEstimateQuarter(TRiAcctEstimate tRiAcctEstimate) {
        EstimateQuarter estimateQuarter = new EstimateQuarter();
        BeanUtils.copyProperties(tRiAcctEstimate, estimateQuarter);
        estimateQuarter.setQuarterStatus(EstimateStatusEnum.getEstimateStatusEnumByValue(tRiAcctEstimate.getStatus()));
        YearQuarter yearQuarter = new YearQuarter(tRiAcctEstimate.getCedentYear(), tRiAcctEstimate.getCedentQuarter());
        estimateQuarter.setYearQuarter(yearQuarter);
        List<TRiAcctEstimateItem> actualItems = loadActualItems(tRiAcctEstimate.getEstimateId());
        actualItems.addAll(tRiAcctEstimate.getTRiAcctEstimateItems());
        putEstimateItems(estimateQuarter, actualItems);
        return estimateQuarter;
    }

    private List<TRiAcctEstimateItem> loadActualItems(long estimateId) {
        return acctEstimateTransDao.loadActualItems(estimateId);
    }

    public List<TRiAcctEstimate> convertToTRiAcctEstimateList(List<EstimateQuarter> estimateQuarterList) {
        List<TRiAcctEstimate> list = new ArrayList<TRiAcctEstimate>();
        if (!estimateQuarterList.isEmpty()) {
            for (EstimateQuarter estimateQuarter : estimateQuarterList) {
                list.add(convertToTRiAcctEstimate(estimateQuarter));
            }
        }
        return list;
    }

    public TRiAcctEstimate convertToTRiAcctEstimate(EstimateQuarter estimateQuarter) {
        TRiAcctEstimate acctEstimate = new TRiAcctEstimate();
        BeanUtils.copyProperties(estimateQuarter, acctEstimate);
        acctEstimate.setStatus(estimateQuarter.getQuarterStatus().getValue());
        YearQuarter yearQuarter = estimateQuarter.getYearQuarter();
        acctEstimate.setCedentYear(yearQuarter.getYear());
        acctEstimate.setCedentQuarter(yearQuarter.getQuarter());
        acctEstimate.setTRiAcctEstimateItems(convertToTRiAcctEstimateItemList(estimateQuarter, acctEstimate));
        return acctEstimate;
    }

    public void putEstimateItems(EstimateQuarter estimateQuarter, List<TRiAcctEstimateItem> itemList) {
        if (!itemList.isEmpty()) {
            for (TRiAcctEstimateItem temp : itemList) {
                estimateQuarter.setEstItemByEntryCode(temp.getEntryCode(), convertToEstimateItem(temp),
                        EstimateStatusEnum.getEstimateStatusEnumByValue(temp.getStatus()));
            }
        }
    }

    public EstimateItem convertToEstimateItem(TRiAcctEstimateItem acctEstimateItem) {
        EstimateItem estimateItem = new EstimateItem();
        BeanUtils.copyProperties(acctEstimateItem, estimateItem);
        estimateItem.setStatus(EstimateStatusEnum.getEstimateStatusEnumByValue(acctEstimateItem.getStatus()));
        return estimateItem;
    }

    public List<TRiAcctEstimateItem> convertToTRiAcctEstimateItemList(EstimateQuarter estimateQuarter,
                                                                      TRiAcctEstimate tRiAcctEstimate) {
        List<TRiAcctEstimateItem> list = new ArrayList<TRiAcctEstimateItem>();
        List<EstimateItem> itemList = new ArrayList<EstimateItem>();
        for (EstimateStatusEnum tempStatus : EstimateStatusEnum.values()) {
            if (tempStatus != EstimateStatusEnum.Estimating && tempStatus != EstimateStatusEnum.Actual
                    && tempStatus != EstimateStatusEnum.Upr) {
                itemList.addAll(estimateQuarter.getEstMap(tempStatus).values());
            }
        }

        for (EstimateItem estimateItem : itemList) {
            TRiAcctEstimateItem tRiAcctEstimateItem = convertToTRiAcctEstimateItem(estimateItem, true);
            tRiAcctEstimateItem.setTRiAcctEstimate(tRiAcctEstimate);
            list.add(tRiAcctEstimateItem);
        }

        return list;
    }

    public TRiAcctEstimateItem convertToTRiAcctEstimateItem(EstimateItem estimateItem, boolean saveHistory) {
        TRiAcctEstimateItem acctEstimateItem = new TRiAcctEstimateItem();
        BeanUtils.copyProperties(estimateItem, acctEstimateItem);
        acctEstimateItem.setStatus(estimateItem.getStatus().getValue());
        if (saveHistory && estimateItem.getStatus() != EstimateStatusEnum.BaseForecast) {
            saveHistory(estimateItem, acctEstimateItem);
        }
        return acctEstimateItem;
    }

    private void saveHistory(EstimateItem estimateItem, TRiAcctEstimateItem acctEstimateItem) {
        TRiAcctEstimateItemHis estimateItemHis = new TRiAcctEstimateItemHis();
        estimateItemHis.setTRiAcctEstimateItem(acctEstimateItem);
        BeanUtils.copyProperties(acctEstimateItem, estimateItemHis);
        estimateItemHis.setChangeReason(estimateItem.getChangeReason());
        estimateItemHis.setCalcFormula(estimateItem.getCalcFormula());
        acctEstimateItem.getTRiAcctEstimateItemHisList().add(estimateItemHis);
    }

}
