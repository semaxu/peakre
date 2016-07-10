/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.restful;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.restlet.resource.ServerResource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.AccountingService;
import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.accounting.UI.model.TRiAcctEstimateItemHisVO;
import com.ebao.ap99.accounting.entity.TRiAcctEstimateItemHis;
import com.ebao.ap99.accounting.estimation.EstimateItem;
import com.ebao.ap99.accounting.estimation.EstimateQuarter;
import com.ebao.ap99.accounting.model.UwViewVO;
import com.ebao.ap99.accounting.service.impl.EstimateDSImpl;
import com.ebao.ap99.accounting.util.AccountingCst;
import com.ebao.ap99.accounting.util.ChangeReasonTool;
import com.ebao.ap99.accounting.util.MapKeyComparator;
import com.ebao.ap99.arap.vo.FnViewVO;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;

/**
 * Date: Jan 13, 2016 2:15:56 PM
 * 
 * @author xiaoyu.yang
 */
@Module(com.ebao.ap99.parent.constant.Module.ACCOUNTING)
@Path("/estimate")
public class EstimateRestfulService extends ServerResource {

    @Autowired
    private EstimateDSImpl estimateDSImpl;

    @Autowired
    private AccountingService accountingService;

    @Autowired
    public ContractService contractService;

    @GET
    @Path("/contractInforce/{sectionId}")
    public void testContractInforce(@PathParam("sectionId") Long sectionId) throws Exception {
        List<Long> sectionIds = new ArrayList<Long>();
        sectionIds.add(sectionId);
        accountingService.afterTreatySubmit(sectionIds, false);
    }

    @GET
    @Path("/recalculateForecast/{level}/{contCompId}")
    public void recalculateForecast(@PathParam("level") Long level, @PathParam("contCompId") Long contCompId)
            throws Exception {
        ChangeReasonTool.getChangeReason().set("Forecast Recalculation");
        if (level == 1) {
            estimateDSImpl.recalculateForecastByContractId(contCompId);
        } else {
            estimateDSImpl.recalculateForecast(contCompId);
        }
    }

    @GET
    @Path("/loadContractStructure/{contractCode}/{uwYear}")
    public List<TreeModel> loadContractStructure(@PathParam("contractCode") String contractCode,
                                                 @PathParam("uwYear") Long uwYear) throws Exception {
        return contractService.getContractStructureByCode(contractCode, uwYear, true);
    }

    @GET
    @Path("/loadUwViews/{level}/{contCompId}")
    public UwViewVO loadUwViews(@PathParam("level") Long level, @PathParam("contCompId") Long contCompId)
            throws Exception {
        Date queryDate = AppContext.getSysDate();
        return estimateDSImpl.loadUwView(level, contCompId, AccountingCst.SECTION_MAIN_CURRENCY, queryDate);
    }

    @GET
    @Path("/loadUwViews/{level}/{contCompId}/{currency}/{date}")
    public UwViewVO loadUwViews(@PathParam("level") Long level, @PathParam("contCompId") Long contCompId,
                                @PathParam("currency") String currency, @PathParam("date") String date)
                                        throws Exception {
        Date queryDate = null;
        if (StringUtils.isNotBlank(date) && !"null".equals(date)) {
            String[] format = { "yyyy-MM-dd hh:mm:ss" };
            queryDate = DateUtils.parseDate(date, format);
        } else {
            queryDate = AppContext.getSysDate();
        }
        return estimateDSImpl.loadUwView(level, contCompId, currency, queryDate);
    }

    @GET
    @Path("/loadFnViews/{contCompId}")
    public FnViewVO loadFnViews(@PathParam("contCompId") Long contCompId) throws Exception {
        return estimateDSImpl.loadFnView(contCompId);
    }

    @GET
    @Path("/transDetail/{contCompId}/{entryCode}/{cedentQuarter}/{status}")
    public Object loadTransactionDetail(@PathParam("contCompId") Long contCompId,
                                        @PathParam("entryCode") String entryCode,
                                        @PathParam("cedentQuarter") String cedentQuarter,
                                        @PathParam("status") int status) throws Exception {
        YearQuarter yearQuarter = new YearQuarter(cedentQuarter);
        if (status == 5) {//Actual Detail Page
            return estimateDSImpl.loadActualDetails(contCompId, entryCode, yearQuarter.getYear(),
                    yearQuarter.getQuarter());
        } else {//Estimation And Reversal Detail Page
            List<TRiAcctEstimateItemHis> list = estimateDSImpl.loadBusinessDetails(contCompId, entryCode,
                    yearQuarter.getYear(), yearQuarter.getQuarter(), status);
            List<TRiAcctEstimateItemHisVO> retList = new ArrayList<TRiAcctEstimateItemHisVO>();
            for (TRiAcctEstimateItemHis temp : list) {
                TRiAcctEstimateItemHisVO estimateItemHisVO = new TRiAcctEstimateItemHisVO();
                BeanUtils.copyProperties(temp, estimateItemHisVO);
                retList.add(estimateItemHisVO);
            }
            return retList;
        }
    }

    @GET
    @Path("/loadQuarterNeedPostE/{sectionId}")
    public List<EstimateQuarter> getQuarterWhichNeedPostEstimation(@PathParam("sectionId") Long sectionId)
            throws Exception {
        return estimateDSImpl.getQuarterWhichNeedPostEstimation(sectionId);
    }

    @GET
    @Path("/postEstimation/{sectionId}/{quarters}")
    public Map<String, String> postEstimationBySectionIdAndEstimateIds(@PathParam("sectionId") Long sectionId,
                                                                       @PathParam("quarters") String quarters)
                                                                               throws Exception {
        YearQuarter fnQuarter = accountingService.currentFinancialQuarter();
        ChangeReasonTool.getChangeReason().set("Post estimation of UY" + fnQuarter.toString());
        Map<String, String> map = new HashMap<String, String>();
        map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_FAIL);
        String[] strs = quarters.split(",");
        if (strs != null && strs.length != 0) {
            List<Long> estimateIds = new ArrayList<Long>();
            for (int i = 0; i < strs.length; i++) {
                estimateIds.add(Long.parseLong(strs[i]));
            }
            estimateDSImpl.postEstimation(sectionId, estimateIds);
            map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_SUCCESS);
        }
        return map;
    }

    @GET
    @Path("/adjustEstimation/{sectionId}/{adjustedEstimateItems}")
    public Map<String, String> adjustEstimation(@PathParam("sectionId") Long sectionId,
                                                @PathParam("adjustedEstimateItems") String adjustedEstimateItems)
                                                        throws Exception {
        YearQuarter fnQuarter = accountingService.currentFinancialQuarter();
        ChangeReasonTool.getChangeReason().set("Estimation adjustment of UY" + fnQuarter.toString());
        Map<String, String> map = new HashMap<String, String>();
        map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_FAIL);
        if (StringUtils.isNotEmpty(adjustedEstimateItems)) {
            //key:cedentQuarter ,value:List<EstimateItem>
            Map<String, List<EstimateItem>> estimateItemMap = new TreeMap<String, List<EstimateItem>>();
            String[] eachQuarterStr = adjustedEstimateItems.split("&");
            for (int i = 0; i < eachQuarterStr.length; i++) {
                String str1 = eachQuarterStr[i];
                String[] keyValue = str1.split("@");
                String[] entry = keyValue[1].split(",");
                List<EstimateItem> estimateItems = new ArrayList<EstimateItem>();
                for (int j = 0; j < entry.length; j++) {
                    String each = entry[j];
                    String[] str = each.split(":");
                    EstimateItem estimateItem = new EstimateItem();
                    estimateItem.setEntryCode(str[0]);
                    estimateItem.setAmount(new BigDecimal(str[1]));
                    estimateItems.add(estimateItem);
                }
                estimateItemMap.put(keyValue[0], estimateItems);
            }
            estimateDSImpl.adjustEstimation(sectionId, estimateItemMap);
            if (accountingService.inClosingPeriod()) {
                estimateDSImpl.recalculateForecast(sectionId);
            }
            map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_SUCCESS);
        }
        return map;
    }

    @GET
    @Path("/adjustUprDac/{sectionId}/{adjustedActualItems}")
    public Map<String, String> adjustUprDac(@PathParam("sectionId") Long sectionId,
                                            @PathParam("adjustedActualItems") String adjustedActualItems)
                                                    throws Exception {
        YearQuarter fnQuarter = accountingService.currentFinancialQuarter();
        ChangeReasonTool.getChangeReason().set("UPR/DAC adjustment of UY" + fnQuarter.toString());
        Map<String, String> map = new HashMap<String, String>();
        map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_FAIL);
        if (StringUtils.isNotEmpty(adjustedActualItems)) {
            //key:cedentQuarter ,value:List<EstimateItem>
            Map<String, List<EstimateItem>> estimateItemMap = new TreeMap<String, List<EstimateItem>>();
            String[] eachQuarterStr = adjustedActualItems.split("&");
            for (int i = 0; i < eachQuarterStr.length; i++) {
                String str1 = eachQuarterStr[i];
                String[] keyValue = str1.split("@");
                String[] entry = keyValue[1].split(",");
                List<EstimateItem> estimateItems = new ArrayList<EstimateItem>();
                for (int j = 0; j < entry.length; j++) {
                    String each = entry[j];
                    String[] str = each.split(":");
                    EstimateItem estimateItem = new EstimateItem();
                    estimateItem.setEntryCode(str[0]);
                    estimateItem.setAmount(new BigDecimal(str[1]));
                    estimateItems.add(estimateItem);
                }
                estimateItemMap.put(keyValue[0], estimateItems);
            }
            Map<String, List<EstimateItem>> resultMap = sortMapByKey(estimateItemMap);
            estimateDSImpl.adjustUprDac(sectionId, resultMap);
            if (accountingService.inClosingPeriod()) {
                estimateDSImpl.adjustForecastForUPR$DACAdjust(sectionId);
            }
            map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_SUCCESS);
        }
        return map;
    }

    /**
     * 使用 Map按key进行排序
     * 
     * @param map
     * @return
     */
    public static Map<String, List<EstimateItem>> sortMapByKey(Map<String, List<EstimateItem>> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, List<EstimateItem>> sortMap = new TreeMap<String, List<EstimateItem>>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

}
