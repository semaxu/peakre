/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.service;

import java.util.List;

import com.ebao.ap99.contract.entity.TRiContractStructure;
import com.ebao.ap99.contract.model.ContractSectionVO;
import com.ebao.ap99.contract.model.ContractSubsectionVO;
import com.ebao.ap99.contract.model.ContractVO;
import com.ebao.ap99.contract.model.TerminationVO;
import com.ebao.ap99.parent.Page;
import com.ebao.ap99.partner.entity.MessageVO;
import com.ebao.unicorn.platform.foundation.cfg.Env;

/**
 * Date: Jan 7, 2016 4:38:58 PM
 * 
 * @author weiping.wang
 */
public interface ContractHomeDS {

	public static final String SYSTEM_HOME_URL = Env.getParameter("system.home.url");
	public static final String CONTRACT_URL = SYSTEM_HOME_URL
			+ "/Treaty/contractHome.html?ContCompId=:contCompId&OperateType=:operateType";

	public ContractVO saveContractInfo(ContractVO model, boolean isSubmit) throws Exception;

	public ContractSectionVO saveSectionInfo(ContractSectionVO model) throws Exception;

	public ContractSubsectionVO saveSubsectionInfo(ContractSubsectionVO model) throws Exception;

	public ContractVO loadContractInfo(Long contCompId) throws Exception;

	/**
	 * if sectionId is not null, load section info by sectionId. otherwise
	 * initialize section info by parent info
	 * 
	 * @param sectionId
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public ContractSectionVO loadSectionInfo(Long sectionId, Long parentId) throws Exception;

	/**
	 * if subsectionId is not null, load subsection info by subsectionId.
	 * otherwise initialize subsection info by parent info
	 * 
	 * @param subsectionId
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public ContractSubsectionVO loadSubsectionInfo(Long subsectionId, Long parentId) throws Exception;

	/**
	 * copy&renew a contract,then create a new one.
	 * 
	 * @param contId
	 * @param isRenewal
	 * @return
	 */
	public ContractVO copyOrRenewContract(Long contId, boolean isRenewal) throws Exception;

	Page<ContractVO> findCurrentPage(ContractVO condition, int start, int countPerPage);

	/**
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public ContractVO contractEntry(ContractVO vo) throws Exception;

	public ContractVO contractUwReview(ContractVO vo) throws Exception;

	public ContractVO contractEndo(ContractVO vo) throws Exception;

	/**
	 * Get renewal status by renewalId
	 * 
	 * @param renewalId
	 * @return
	 * @throws Exception
	 */
	public boolean getRenewalStatus(Long renewalId) throws Exception;

	public ContractVO contractEndoSave(ContractVO model) throws Exception;

	public Long saveStructure(Long parentId, Long currentId, String name, String type) throws Exception;

	/**
	 * Get contract infomation by contract id, operateId, contract status from
	 * log. Then revert the contract.
	 * 
	 * @param contractId
	 * @param operateId
	 * @param contractStatus
	 * @return
	 * @throws Exception
	 */
	public ContractVO revertContract(Long contractId, Long operateId, String contractStatus) throws Exception;

	/**
	 * Get termination information by contract id.
	 * 
	 * @param contractId
	 * @return
	 * @throws Exception
	 */
	public TerminationVO loadTermination(Long contractId) throws Exception;

	/**
	 * Save termination info. Back up contract info, then set contract status to
	 * "Cancelled".
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public TerminationVO saveTermination(TerminationVO vo) throws Exception;

	/**
	 * If pricingEstimation info is inputted.
	 * 
	 * @param contractId
	 * @return
	 * @throws Exception
	 */
	public boolean isPricingEstimated(Long contractId, String deleteSectionList) throws Exception;

	/**
	 * If retrocession info is inputted.
	 * 
	 * @param contractId
	 * @return
	 * @throws Exception
	 */
	public List<String> getNonRetroList(Long contractId) throws Exception;

	/**
	 * send Mail
	 * 
	 * @param vo
	 * @param mailType
	 * @return
	 * @throws Exception
	 */
	public void sendMail(ContractVO vo, String mailType) throws Exception;

	public ContractVO loadContractInfoForLog(Long contId, Long operateId) throws Exception;

	public ContractSectionVO loadSectionInfoForLog(Long sectionId, Long operateId) throws Exception;

	public ContractSubsectionVO loadSubsectionInfoForLog(Long subsectionId, Long operateId) throws Exception;

	/**
	 * AMLCheck.
	 * 
	 * @param ContractVO
	 * @return
	 * @throws Exception
	 */
	public List<MessageVO> AMLCheck(ContractVO model) throws Exception;

	public List<TRiContractStructure> getRetroRelatedSections(Long contCompId) throws Exception;
}
