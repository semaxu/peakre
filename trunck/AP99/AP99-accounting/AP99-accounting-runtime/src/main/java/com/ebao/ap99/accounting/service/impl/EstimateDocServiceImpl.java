/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.dao.TRiAcctEstimateItemHisDao;
import com.ebao.ap99.accounting.model.TechnicalResultVO;
import com.ebao.ap99.file.service.CalService;

/**
 * Date: Mar 28, 2016 7:36:18 PM
 * 
 * @author xiaoyu.yang
 */
public class EstimateDocServiceImpl implements CalService {

    @Autowired
    private TRiAcctEstimateItemHisDao acctEstimateItemHisDao;

    @Override
    public <T> List bizProcess(Map model) {
        Long sectionId = Long.parseLong(model.get("sectionId").toString());
        List<TechnicalResultVO> list = acctEstimateItemHisDao.loadTechnicalResultList(sectionId);
        return list;
    }

}
