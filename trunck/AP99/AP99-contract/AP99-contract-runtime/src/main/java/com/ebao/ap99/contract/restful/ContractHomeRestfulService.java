/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.restful;

import java.io.ByteArrayOutputStream;
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

import com.ebao.ap99.accounting.AccountingService;
import com.ebao.ap99.contract.dao.TRiContOperationDao;
import com.ebao.ap99.contract.dao.TRiContractInfoDao;
import com.ebao.ap99.contract.dao.TRiContractStructureDao;
import com.ebao.ap99.contract.entity.TRiContOperation;
import com.ebao.ap99.contract.entity.TRiContractStructure;
import com.ebao.ap99.contract.model.ContractSectionVO;
import com.ebao.ap99.contract.model.ContractSubsectionVO;
import com.ebao.ap99.contract.model.ContractVO;
import com.ebao.ap99.contract.model.TerminationVO;
import com.ebao.ap99.contract.model.BusinessModel.ContractBO;
import com.ebao.ap99.contract.model.BusinessModel.ContractBOList;
import com.ebao.ap99.contract.model.BusinessModel.ContractPDFVOList;
import com.ebao.ap99.contract.model.PDFModel.ContractPDFVO;
import com.ebao.ap99.contract.service.ContractDS;
import com.ebao.ap99.contract.service.ContractHomeDS;
import com.ebao.ap99.contract.service.ContractRetrocessionDS;
import com.ebao.ap99.contract.service.impl.ContractServiceImpl;
import com.ebao.ap99.contract.util.ContractCst;
import com.ebao.ap99.file.util.JaxbXMLParser;
import com.ebao.ap99.parent.Page;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * Date: Jan 7, 2016 8:33:17 PM
 * 
 * @author weiping.wang
 */
@Path("/contractHome")
@Module(com.ebao.ap99.parent.constant.Module.CONTRACT)
public class ContractHomeRestfulService {
	private static Logger logger = LoggerFactory.getLogger();
	@Autowired
	private ContractRetrocessionDS retrocessionDS;

	@Autowired
	private ContractHomeDS contractHomeDS;

	@Autowired
	private ContractServiceImpl contractImpl;

	@Autowired
	private ContractDS contractDS;

	@Autowired
	private TRiContractInfoDao contractInfoDao;;

	@Autowired
	private AccountingService accountingService;

	@Autowired
	private TRiContractStructureDao structureDao;

	@Autowired
	private TRiContOperationDao operationDao;

	@POST
	@Path("/saveContract")
	public ContractVO saveContractInfo(ContractVO model) throws Exception {
		logger.debug("ContractHomeRestfulService.saveContractInfo for saving contract info, model=", model);

		contractHomeDS.saveContractInfo(model, false);
		return model;
	}

	@POST
	@Path("/submitContractInfo")
	public ContractVO submitContractInfo(ContractVO model) throws Exception {
		if (ContractCst.CONTRACT_OPERATE_DATA_INPUT.equals(model.getOperateType())) {
			model = contractHomeDS.contractEntry(model);
		} else if (ContractCst.CONTRACT_OPERATE_UW_REVIEW.equals(model.getOperateType())) {
			model = contractHomeDS.contractUwReview(model);
			// if the contract status change to Inforce, then need post
			// accounting
			TRiContOperation operation = operationDao.loadPreContractOperation(model.getContCompId());
			if (ContractCst.CONTRACT_NATURE_PROPORTIONAL.equals(model.getContractNature())
					&& ContractCst.CONTRACT_REVIEW_APPROVE.equals(model.getReviewed())
					&& ContractCst.CONTRACT_STATUS_DATA_INPUT.equals(operation.getStatus())) {
				List<Long> sectionIds = structureDao.getChildrenIdList(model.getContCompId());
				accountingService.afterTreatySubmit(sectionIds, false);
			}
		} else if (ContractCst.CONTRACT_OPERATE_ENDORSEMENT.equals(model.getOperateType())) {
			model = contractHomeDS.contractEndo(model);
		}
		logger.debug("ContractHomeRestfulService.submitContractInfo success operateType=" + model.getOperateType()
				+ "&operateId=" + model.getOperateId());
		return model;
	}

	@POST
	@Path("/submitContractInfoWithSpecial")
	public ContractVO submitContractInfoWithSpecial(ContractVO model) throws Exception {
		model = contractHomeDS.contractUwReview(model);
		// if the contract status change to Inforce, then need post accounting
		TRiContOperation operation = operationDao.loadPreContractOperation(model.getContCompId());
		if (ContractCst.CONTRACT_NATURE_PROPORTIONAL.equals(model.getContractNature())
				&& ContractCst.CONTRACT_STATUS_DATA_INPUT.equals(operation.getStatus())) {
			List<Long> sectionIds = structureDao.getChildrenIdList(model.getContCompId());
			accountingService.afterTreatySubmit(sectionIds, true);
		}
		logger.debug("ContractHomeRestfulService.submitContractInfo success operateType=" + model.getOperateType()
				+ "&operateId=" + model.getOperateId());
		return model;
	}

	@POST
	@Path("/saveSection")
	public ContractSectionVO saveSectionInfo(ContractSectionVO model) throws Exception {
		logger.debug("ContractHomeRestfulService.saveSectionInfo for saving section info, model=", model);
		model = contractHomeDS.saveSectionInfo(model);
		return model;
	}

	@POST
	@Path("/saveSubsection")
	public ContractSubsectionVO saveSubsectionInfo(ContractSubsectionVO model) throws Exception {
		logger.debug("ContractHomeRestfulService.saveSubsectionInfo for saving subsection info, model=", model);

		contractHomeDS.saveSubsectionInfo(model);
		return model;
	}

	/**
	 * Get contractInfo by contract id.
	 * 
	 * @param contCompId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/loadContractInfo")
	public ContractVO loadContractInfo(@QueryParam("ContCompId") Long contCompId) throws Exception {
		logger.debug("ContractHomeRestfulService.loadContractInfo for loading contract info, contract id=", contCompId);
		ContractVO vo = contractHomeDS.loadContractInfo(contCompId);
		vo.setIsClosingPeriod(accountingService.inClosingPeriod());
		return vo;
	}

	/**
	 * Get contractBO by contract id.
	 * 
	 * @param contCompId
	 * @return map
	 * @throws Exception
	 */
	@GET
	@Path("/loadContractBO")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_XML)
	public String loadContractBO(@QueryParam("ContCompId") Long contCompId) throws Exception {

		ContractBO bo = contractDS.loadContractBO(contCompId, true);
		JaxbXMLParser jaxbXMLParser = new JaxbXMLParser();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ContractBOList contractBOList = new ContractBOList();
		contractBOList.addNewObject(bo);
		String xmlString = jaxbXMLParser.marshallXml(out, contractBOList, ContractBOList.class);
		if (logger.isDebugEnabled()) {
			logger.debug("ContractHomeRestfulService.loadContractBO for loading contract info, contract id=",
					contCompId + ",xml:" + xmlString);
		}

		return xmlString;
	}

	@GET
	@Path("/loadContractDate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_XML)
	public ContractVO loadContractDate(@QueryParam("ContCompId") Long contCompId, @QueryParam("Level") String level)
			throws Exception {

		List<Long> compIdList = contractImpl.getLevelContCompIdList(contCompId, "1");
		ContractVO vo = null;
		if (null != compIdList && compIdList.size() > 0) {
			for (Long contId : compIdList) {
				vo = contractHomeDS.loadContractInfo(contId);
			}
		}
		return vo;
	}

	/**
	 * Get contractPDFVO by contract id.
	 * 
	 * @param contCompId
	 * @return map
	 * @throws Exception
	 */
	@GET
	@Path("/loadContractPDFVO")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_XML)
	public String loadContractPDFVO(@QueryParam("ContCompId") Long contCompId) throws Exception {

		ContractBO bo = contractDS.loadContractBO(contCompId, true);
		ContractPDFVO contractPDFVO = contractDS.convertContractBOToPDFVO(bo);
		ContractPDFVOList contractPDFVOList = new ContractPDFVOList();
		contractPDFVOList.addNewObject(contractPDFVO);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JaxbXMLParser jaxbXMLParser = new JaxbXMLParser();
		String xmlString = jaxbXMLParser.marshallXml(out, contractPDFVOList, ContractPDFVOList.class);
		if (logger.isDebugEnabled()) {
			logger.debug("ContractHomeRestfulService.loadContractBO for loading contract info, contract id=",
					contCompId + ",xml:" + xmlString);
		}

		return xmlString;
	}

	/**
	 * Get sectionInfo by section id.
	 * 
	 * @param contCompId
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/loadSectionInfo")
	public ContractSectionVO loadSectionInfo(@QueryParam("ContCompId") Long contCompId,
			@QueryParam("ParentId") Long parentId) throws Exception {
		logger.debug("ContractHomeRestfulService.loadSectionInfo for loading section info, section id=", contCompId);
		ContractSectionVO vo = contractHomeDS.loadSectionInfo(contCompId, parentId);

		return vo;
	}

	/**
	 * Get subsectionInfo by subsection id.
	 * 
	 * @param contCompId
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/loadSubsectionInfo")
	public ContractSubsectionVO loadSubsectionInfo(@QueryParam("ContCompId") Long contCompId,
			@QueryParam("ParentId") Long parentId) throws Exception {
		logger.debug("ContractHomeRestfulService.loadSubsectionInfo for loading subsection info, subsection id=",
				contCompId);
		ContractSubsectionVO vo = contractHomeDS.loadSubsectionInfo(contCompId, parentId);

		return vo;
	}

	/**
	 * copy&renew a contract,then create new one.
	 * 
	 * @param contractId
	 * @param isRenewal
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/copyOrRenewContract")
	public ContractVO copyOrRenewContract(@QueryParam("ContCompId") Long contractId,
			@QueryParam("IsRenewal") boolean isRenewal) throws Exception {
		logger.debug("ContractHomeRestfulService.copyOrRenewContract for copying or renew contract, contract id=",
				contractId);
		ContractVO vo = contractHomeDS.copyOrRenewContract(contractId, isRenewal);
		return vo;
	}

	/**
	 * Search Contract Page Info
	 * 
	 * @param contractVO
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/page")
	public Page<ContractVO> queryContractInfos(ContractVO contractVO) throws Exception {
		Page<ContractVO> page = new Page<ContractVO>();
		page.setCountPerPage(contractVO.getCountPerPage());
		page.setPageIndex(contractVO.getPageIndex());
		// TRiContractInfo condition = new TRiContractInfo();
		// BeanUtils.copyProperties(contractVO, condition);
		page = contractHomeDS.findCurrentPage(contractVO, contractVO.getPageIndex(), contractVO.getCountPerPage());
		return page;
	}

	@GET
	@Path("/getRenewalStatus")
	public Boolean getRenewalStatus(@QueryParam("ContCompId") Long renewalId) throws Exception {
		logger.debug("ContractHomeRestfulService.getRenewalStatus, renewal id=", renewalId);
		return contractHomeDS.getRenewalStatus(renewalId);
	}

	@GET
	@Path("/loadTermination")
	public TerminationVO loadTermination(@QueryParam("ContCompId") Long contractId) throws Exception {
		logger.debug("ContractHomeRestfulService.loadTermination, contract id =", contractId);

		TerminationVO termination = contractHomeDS.loadTermination(contractId);
		return termination;
	}

	@POST
	@Path("/saveTermination")
	public TerminationVO saveTermination(TerminationVO vo) throws Exception {
		logger.debug("ContractHomeRestfulService.saveTermination");

		return contractHomeDS.saveTermination(vo);
	}

	@GET
	@Path("/isPricingEstimated")
	public boolean isPricingEstimated(@QueryParam("ContCompId") Long contractId,
			@QueryParam("DeleteSectionList") String deleteSectionList) throws Exception {
		logger.debug(
				"ContractHomeRestfulService.isPricingEstimated for judging weather pricingEstimation info is inputted, contract id=",
				contractId);
		boolean ret = contractHomeDS.isPricingEstimated(contractId, deleteSectionList);

		return ret;
	}

	@GET
	@Path("/getNonRetroList")
	public List<String> getNonRetroList(@QueryParam("ContCompId") Long contractId) throws Exception {
		logger.debug(
				"ContractHomeRestfulService.getNonRetroList for getting section/subsection name list which has no retrocession info, contract id=",
				contractId);
		List<String> list = contractHomeDS.getNonRetroList(contractId);
		return list;
	}

	@POST
	@Path("/sendMail/{mailType}")
	public void sendMail(ContractVO vo, @PathParam("mailType") String mailType) throws Exception {
		contractHomeDS.sendMail(vo, mailType);
	}

	/**
	 * Get contractInfo history by contract id and operate id.
	 * 
	 * @param contCompId
	 * @param operateId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/loadContractInfoForLog")
	public ContractVO loadContractInfoForLog(@QueryParam("ContCompId") Long contCompId,
			@QueryParam("OperateId") Long operateId) throws Exception {
		logger.debug("ContractHomeRestfulService.loadContractInfoForLog for loading contract info history, operate id=",
				operateId);
		ContractVO vo = contractHomeDS.loadContractInfoForLog(contCompId, operateId);
		return vo;
	}

	/**
	 * Get sectionInfo history by section id and operate id.
	 * 
	 * @param contCompId
	 * @param operateId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/loadSectionInfoForLog")
	public ContractSectionVO loadSectionInfoForLog(@QueryParam("ContCompId") Long contCompId,
			@QueryParam("OperateId") Long operateId) throws Exception {
		logger.debug("ContractHomeRestfulService.loadSectionInfoForLog for loading section info history, operate id=",
				operateId);
		ContractSectionVO vo = contractHomeDS.loadSectionInfoForLog(contCompId, operateId);

		return vo;
	}

	/**
	 * Get subsectionInfo history by subsection id and operate id.
	 * 
	 * @param contCompId
	 * @param operateId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/loadSubsectionInfoForLog")
	public ContractSubsectionVO loadSubsectionInfoForLog(@QueryParam("ContCompId") Long contCompId,
			@QueryParam("OperateId") Long operateId) throws Exception {
		logger.debug(
				"ContractHomeRestfulService.loadSubsectionInfoForLog for loading subsection info history, operate id=",
				operateId);
		ContractSubsectionVO vo = contractHomeDS.loadSubsectionInfoForLog(contCompId, operateId);

		return vo;
	}

	@POST
	@Path("/AMLCheck")
	public Object AMLCheck(ContractVO model) throws Exception {
		logger.debug(
				"ContractHomeRestfulService.AMLCheck for judging whether cendant, broker and MGA info is vaild, contract id=",
				model.getContCompId());
		return contractHomeDS.AMLCheck(model);
	}

	@GET
	@Path("/CategoryChange/{ContCompId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object categoryChange(@PathParam("ContCompId") Long contCompId) throws Exception {

		List<TRiContractStructure> strctureList = contractHomeDS.getRetroRelatedSections(contCompId);

		return strctureList;
	}

}
