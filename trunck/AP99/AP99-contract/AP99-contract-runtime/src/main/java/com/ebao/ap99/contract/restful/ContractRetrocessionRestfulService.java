/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.restful;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.contract.model.RetrocessionItemVO;
import com.ebao.ap99.contract.model.RetrocessionVO;
import com.ebao.ap99.contract.service.ContractRetrocessionDS;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.parent.DataTypeConvertor;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * Date: Jan 19, 2016 11:35:16 AM
 * 
 * @author weiping.wang
 *
 */
@Path("/contractRetrocession")
@Module(com.ebao.ap99.parent.constant.Module.CONTRACT)
public class ContractRetrocessionRestfulService {
	private static Logger logger = LoggerFactory.getLogger();

	@Autowired
	private ContractRetrocessionDS retrocessionDS;

	@Autowired
	private ContractService contractService;

	/**
	 * Get retrocession list by assumed section id.
	 * 
	 * @param compId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/loadAssumedRetrocession")
	public List<RetrocessionItemVO> loadRetrocessionList(@QueryParam("CompId") Long compId) throws Exception {
		logger.debug("ContractRetrocessionRestfulService.loadRetrocession For Assume, Assumed section id =", compId);
		List<RetrocessionItemVO> retrocessionList = retrocessionDS.loadRetrocessionList(compId);

		return retrocessionList;
	}

	/**
	 * Get CoveredSection list by contract id.
	 * 
	 * @param contId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/loadCoveredRetrocession")
	public List<RetrocessionItemVO> loadCoveredSectionList(@QueryParam("ContId") Long contId) throws Exception {
		logger.debug("ContractRetrocessionRestfulService.loadRetroSectionList, Covered contract id =", contId);
		List<RetrocessionItemVO> coveredSectionList = retrocessionDS.loadCoveredSectionList(contId);

		return coveredSectionList;
	}

	@POST
	@Path(value = "/saveRetrocession")
	public void saveRetrocession(RetrocessionVO vo) throws Exception {
		logger.debug(
				"ContractRetrocessionRestfulService.saveRetrocession For saving retro section infos, retrocessionVO =",
				vo);

		retrocessionDS.saveRetrocession(vo);
	}

	@GET
	@Path(value = "/generateRetrocession")
	public List<RetrocessionItemVO> generateRetrocession(@QueryParam("RetroCompIdTree") String retroCompIdTree)
			throws Exception {
		logger.debug("ContractRetrocessionRestfulService.generateRetrocession For Assume, retroCompIdTree =",
				retroCompIdTree);
		List<RetrocessionItemVO> retrocessionList = new ArrayList<RetrocessionItemVO>();
		List<String> retroCompIdTreeArr = DataTypeConvertor.transStringToSelectTree(retroCompIdTree);
		for (int i = 0; i < retroCompIdTreeArr.size(); i++) {
			RetrocessionItemVO vo = retrocessionDS.generateRetrocession(Long.valueOf(retroCompIdTreeArr.get(i)));
			retrocessionList.add(vo);
		}
		return retrocessionList;
	}

	@GET
	@Path("/getRetroContractStructure/{contractCode}/{uwYear}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TreeModel> loadRetroContractStrue(@PathParam("contractCode") String contractCode,
			@PathParam("uwYear") Long uwYear) throws Exception {
		logger.debug("ContractRetrocessionRestfulService.loadRetroContractStrue contractCode=" + contractCode
				+ ",uwYear=" + uwYear);
		List<TreeModel> list = retrocessionDS.getRetroContractStructure(contractCode, uwYear);
		return list;
	}

	@GET
	@Path("/loadAssumedRetrocessionForLog")
	public List<RetrocessionItemVO> loadRetrocessionListForLog(@QueryParam("CompId") Long compId,
			@QueryParam("OperateId") Long operateId) throws Exception {
		logger.debug("ContractRetrocessionRestfulService.loadRetrocessionListForLog For Assume, Assumed section id =",
				compId);
		List<RetrocessionItemVO> retrocessionList = retrocessionDS.loadRetrocessionListForLog(compId, operateId);

		return retrocessionList;
	}

	@GET
	@Path("/loadCoveredRetrocessionForLog")
	public List<RetrocessionItemVO> loadCoveredSectionListForLog(@QueryParam("ContId") Long contId,
			@QueryParam("OperateId") Long operateId) throws Exception {
		logger.debug("ContractRetrocessionRestfulService.loadCoveredSectionListForLog, Covered contract id =", contId);
		List<RetrocessionItemVO> coveredSectionList = retrocessionDS.loadCoveredSectionList(contId);

		return coveredSectionList;
	}
}
