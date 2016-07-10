/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.restful;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.contract.model.AdjustmentVO;
import com.ebao.ap99.contract.service.ContractAdjustmentDS;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * 
 * @author weiping.wang
 *
 */
@Path("/adjustment")
@Module(com.ebao.ap99.parent.constant.Module.CONTRACT)
public class ContractAdjustmentRestfulService {
	private static Logger logger = LoggerFactory.getLogger();

	@Autowired
	private ContractAdjustmentDS adjustmentDS;

	@GET
	@Path("/loadSUPIAdjustment")
	public AdjustmentVO loadAdjustment(@QueryParam("ContCompId") Long contractId) throws Exception {
		logger.debug("ContractAdjustmentRestfulService.loadSUPIAdjustment, contract id =", contractId);

		AdjustmentVO vo = adjustmentDS.getAdjustInfo(contractId);
		return vo;
	}

	@POST
	@Path("/saveSUPIAdjustment")
	public void saveAdjustment(AdjustmentVO vo) throws Exception {
		logger.debug("ContractAdjustmentRestfulService.saveAdjustment, adjustmentVO =", vo);

		adjustmentDS.saveAdjustment(vo);
	}
}
