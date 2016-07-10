package com.ebao.ap99.contract.restful;

import java.lang.reflect.InvocationTargetException;
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

import com.ebao.ap99.contract.entity.ContractModel;
import com.ebao.ap99.contract.entity.ContractSearchModel;
import com.ebao.ap99.contract.model.ContractVO;
import com.ebao.ap99.contract.model.LinkContractVO;
import com.ebao.ap99.contract.model.BusinessModel.ContractBO;
import com.ebao.ap99.contract.model.PDFModel.ContractPDFVO;
import com.ebao.ap99.contract.service.ContractDS;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author elvira.du
 */
@Path("/contractStructure")
@Module(com.ebao.ap99.parent.constant.Module.CONTRACT)
public class ContractRestfulService {
	private static Logger logger = LoggerFactory.getLogger();

	@Autowired
	private ContractService contractService;
	@Autowired
	private ContractDS contractDS;

	@GET
	@Path("/getContractStructure/{contractCode}/{uwYear}/{inforceFlag}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TreeModel> loadContractStrue(@PathParam("contractCode") String contractCode,
			@PathParam("uwYear") Long uwYear, @PathParam("inforceFlag") boolean inforceFlag) throws Exception {
		logger.debug("ContractRestfulService.loadContractStrue contractCode=" + contractCode + ",uwYear=" + uwYear
				+ ",inforceFlag=" + inforceFlag);
		List<TreeModel> list = contractService.getContractStructureByCode(contractCode, uwYear, inforceFlag);
		return list;
	}

	@GET
	@Path("/getContractStructure/{contractCode}/{uwYear}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TreeModel> loadContractStrue(@PathParam("contractCode") String contractCode,
			@PathParam("uwYear") Long uwYear) throws Exception {
		logger.debug("ContractRestfulService.loadContractStrue contractCode=" + contractCode + ",uwYear=" + uwYear);
		List<TreeModel> list = contractService.getContractStructureByCode(contractCode, uwYear);
		return list;
	}

	@GET
	@Path("/getLinkList/{cedent}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getLinkList(@PathParam("cedantId") String cedent) {
		logger.info("ContractRestfulService.loadContractStrue");
		List<String> list = contractDS.getLinkedListByCedent(cedent);
		return list;
	}

	@GET
	@Path("/getLinkStructure/{contractCode}/{linkName}/{uwYear}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<LinkContractVO> loadContractStrue(@PathParam("linkName") String linkName,
			@PathParam("contractCode") String contractCode, @PathParam("uwYear") Long uwYear) {
		logger.info("ContractRestfulService.loadContractStrue");
		List<LinkContractVO> list = contractDS.getLinkContractList(linkName, contractCode, uwYear);
		return list;
	}


	@GET
	@Path("/getContractCode")
	public ContractVO getContractCode() throws Exception {
		logger.info("ContractRestfulService.getContractCode");
		ContractVO vo = new ContractVO();
		String contractCode = contractDS.getContractCode();
		vo.setContractCode(contractCode);
		return vo;
	}

	/**
	 * Get info for pdf by contract id.
	 * 
	 * @param contCompId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/loadContractPDFVO")
	public ContractPDFVO loadContractPDFVO(@QueryParam("ContCompId") Long contCompId) throws Exception {
		logger.debug("ContractHomeRestfulService.loadContractPDFVO for loading contract info, contract id=",
				contCompId);
		ContractBO bo = contractDS.loadContractBO(contCompId, true);
		ContractPDFVO vo = contractDS.convertContractBOToPDFVO(bo);
		return vo;
	}
}
