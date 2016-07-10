/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.restful;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.commons.lang3.StringUtils;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.dao.ActualizationInfoDao;
import com.ebao.ap99.accounting.model.ActualizationInfoVO;
import com.ebao.ap99.accounting.service.impl.EstimateDSImpl;
import com.ebao.ap99.accounting.util.AccountingCst;
import com.ebao.ap99.accounting.util.ChangeReasonTool;
import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.util.Pagination;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * Date: Mar 31, 2016 3:25:12 PM
 * 
 * @author xiaoyu.yang
 */
@Module(com.ebao.ap99.parent.constant.Module.ACCOUNTING)
@Path("/actualization")
public class ActualizationRestfulService extends ServerResource {

    private static Logger logger = LoggerFactory.getLogger();

    @Autowired
    private ActualizationInfoDao actualizationInfoDao;

    @Autowired
    private EstimateDSImpl estimateDSImpl;

    @POST
    @Path("/page")
    public Page<ActualizationInfoVO> getPageList(ActualizationInfoVO condition) throws Exception {
        Page<ActualizationInfoVO> retPage = new Page<ActualizationInfoVO>();
        try {
            List<ActualizationInfoVO> list = actualizationInfoDao.findListByCondition(condition);
            Pagination<ActualizationInfoVO> pagination = new Pagination<ActualizationInfoVO>();
            retPage = pagination.pagination(list, condition);
        } catch (Exception e) {
            logger.error("ActualizationRestfulService.getPageList error:" + e.getMessage());
            throw e;
        }
        return retPage;
    }

    @GET
    @Path("/actualize/{contractIdStr}")
    public Map<String, String> actualizeContracts(@PathParam("contractIdStr") String contractIdStr) throws Exception {
        ChangeReasonTool.getChangeReason().set("Actualization");
        Map<String, String> map = new HashMap<String, String>();
        map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_FAIL);
        try {
            if (StringUtils.isNotEmpty(contractIdStr)) {
                List<Long> contractIdList = new ArrayList<Long>();
                String[] contractIds = contractIdStr.split(",");
                for (int i = 0; i < contractIds.length; i++) {
                    contractIdList.add(Long.parseLong(contractIds[i]));
                }
                for (Long contractId : contractIdList) {
                    estimateDSImpl.actualizedByContractId(contractId);
                }
            }
            map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return map;
    }

}
