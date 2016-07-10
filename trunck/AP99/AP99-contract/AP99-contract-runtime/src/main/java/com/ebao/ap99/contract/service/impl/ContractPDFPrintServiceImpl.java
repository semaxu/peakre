/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.contract.model.BusinessModel.ContractBO;
import com.ebao.ap99.contract.model.BusinessModel.ContractPDFVOList;
import com.ebao.ap99.contract.model.PDFModel.ContractPDFVO;
import com.ebao.ap99.contract.service.ContractDS;
import com.ebao.ap99.file.model.ItemVO;
import com.ebao.ap99.file.service.PrintBizService;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * Date: Mar 29, 2016 8:33:17 PM
 * 
 * @author Chuan.ye
 */

public class ContractPDFPrintServiceImpl implements PrintBizService {
    private static Logger logger = LoggerFactory.getLogger();

    @Autowired
    private ContractDS contractDS;

    /**
     * load Business Object by param map.
     * 
     * @param param
     * @return object
     * @throws Exception
     */

    public ItemVO loadBusinessVO(ItemVO itemVO) throws Exception {
        Long contCompId = 0L;
        if (itemVO.getParam() != null) {
            contCompId = Long.parseLong((String) itemVO.getParam().get("ContCompId"));
        } else {
            return itemVO;
        }

        ContractBO bo = contractDS.loadContractBO(contCompId, true);
        ContractPDFVO contractPDFVO = contractDS.convertContractBOToPDFVO(bo);
        ContractPDFVOList contractPDFVOList = new ContractPDFVOList();
        contractPDFVOList.addNewObject(contractPDFVO);
        itemVO.setBizVO(contractPDFVOList);
        return itemVO;
    }

}
