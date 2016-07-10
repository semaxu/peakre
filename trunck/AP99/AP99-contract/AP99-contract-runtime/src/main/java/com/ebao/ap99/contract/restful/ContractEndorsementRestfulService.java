/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.restful;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.contract.model.ContractVO;
import com.ebao.ap99.contract.model.EndorsementVO;
import com.ebao.ap99.contract.service.ContractEndorsementDS;
import com.ebao.ap99.contract.service.ContractHomeDS;
import com.ebao.ap99.contract.util.ContractCst;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * Date: Feb 3, 2016 4:25:55 PM
 * 
 * @author Weiping.Wang
 *
 */
@Path("/contractEndorsement")
@Module(com.ebao.ap99.parent.constant.Module.CONTRACT)
public class ContractEndorsementRestfulService {
	private static Logger logger = LoggerFactory.getLogger();

	@Autowired
	private ContractEndorsementDS endoDS;

	@Autowired
	private ContractHomeDS contractHomeDS;

	@GET
	@Path("/loadEndorsementList")
	public List<EndorsementVO> loadEndorsementList(@QueryParam("ContCompId") Long contId) throws Exception {
		logger.debug("ContractEndorsementRestfulService.loadEndorsementList, contract id =", contId);

		List<EndorsementVO> endorsementList = endoDS.loadEndorsementList(contId);
		return endorsementList;
	}

	@GET
	@Path("/loadEndorsement")
	public EndorsementVO loadEndorsement(@QueryParam("EndoId") Long endoId) throws Exception {
		logger.debug("ContractEndorsementRestfulService.loadEndorsement, endo id =", endoId);

		EndorsementVO endorsement = endoDS.loadEndorsement(endoId);
		return endorsement;
	}

	@POST
	@Path("/saveEndorsement")
	public EndorsementVO saveEndorsement(EndorsementVO vo) throws Exception {
		logger.debug("ContractEndorsementRestfulService.saveEndorsement");

		return endoDS.saveEndorsement(vo);
	}

	@POST
	@Path("/deleteEndorsement")
	public ContractVO deleteEndorsement(EndorsementVO vo) throws Exception {
		logger.debug("ContractEndorsementRestfulService.deleteEndorsement");

		endoDS.deleteEndorsement(vo);
		ContractVO contractVO = contractHomeDS.revertContract(vo.getContCompId(), null,
				ContractCst.CONTRACT_STATUS_INFORCE);
		return contractVO;
	}
}
