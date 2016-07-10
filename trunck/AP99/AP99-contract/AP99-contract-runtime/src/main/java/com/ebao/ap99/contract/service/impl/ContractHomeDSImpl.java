/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.AccountingService;
import com.ebao.ap99.accounting.SoaService;
import com.ebao.ap99.claim.service.ClaimService;
import com.ebao.ap99.contract.convertor.BusinessConditionModelConvertor;
import com.ebao.ap99.contract.convertor.ContractModelConvertor;
import com.ebao.ap99.contract.convertor.ContractSectionModelConvertor;
import com.ebao.ap99.contract.convertor.ContractSubsectionModelConvertor;
import com.ebao.ap99.contract.dao.TRiContAccountDao;
import com.ebao.ap99.contract.dao.TRiContAccountLogDao;
import com.ebao.ap99.contract.dao.TRiContCancelDao;
import com.ebao.ap99.contract.dao.TRiContCancelLogDao;
import com.ebao.ap99.contract.dao.TRiContClaimDao;
import com.ebao.ap99.contract.dao.TRiContClaimItemDao;
import com.ebao.ap99.contract.dao.TRiContClaimItemLogDao;
import com.ebao.ap99.contract.dao.TRiContClaimLogDao;
import com.ebao.ap99.contract.dao.TRiContOperationDao;
import com.ebao.ap99.contract.dao.TRiContPricingDao;
import com.ebao.ap99.contract.dao.TRiContSectionInfoDao;
import com.ebao.ap99.contract.dao.TRiContSectionInfoLogDao;
import com.ebao.ap99.contract.dao.TRiContSubsectionInfoDao;
import com.ebao.ap99.contract.dao.TRiContSubsectionInfoLogDao;
import com.ebao.ap99.contract.dao.TRiContTerminationDao;
import com.ebao.ap99.contract.dao.TRiContractAreaperilDao;
import com.ebao.ap99.contract.dao.TRiContractAreaperilLogDao;
import com.ebao.ap99.contract.dao.TRiContractInfoDao;
import com.ebao.ap99.contract.dao.TRiContractInfoLogDao;
import com.ebao.ap99.contract.dao.TRiContractMoreInfoDao;
import com.ebao.ap99.contract.dao.TRiContractMoreInfoLogDao;
import com.ebao.ap99.contract.dao.TRiContractStructureDao;
import com.ebao.ap99.contract.dao.TRiContractStructureLogDao;
import com.ebao.ap99.contract.entity.TRiContAccount;
import com.ebao.ap99.contract.entity.TRiContAccountLog;
import com.ebao.ap99.contract.entity.TRiContCancel;
import com.ebao.ap99.contract.entity.TRiContCancelLog;
import com.ebao.ap99.contract.entity.TRiContClaim;
import com.ebao.ap99.contract.entity.TRiContClaimItem;
import com.ebao.ap99.contract.entity.TRiContClaimItemLog;
import com.ebao.ap99.contract.entity.TRiContClaimLog;
import com.ebao.ap99.contract.entity.TRiContOperation;
import com.ebao.ap99.contract.entity.TRiContPremiumItem;
import com.ebao.ap99.contract.entity.TRiContPricing;
import com.ebao.ap99.contract.entity.TRiContSectionInfo;
import com.ebao.ap99.contract.entity.TRiContSectionInfoLog;
import com.ebao.ap99.contract.entity.TRiContSubsectionInfo;
import com.ebao.ap99.contract.entity.TRiContSubsectionInfoLog;
import com.ebao.ap99.contract.entity.TRiContTermination;
import com.ebao.ap99.contract.entity.TRiContractAreaperil;
import com.ebao.ap99.contract.entity.TRiContractAreaperilLog;
import com.ebao.ap99.contract.entity.TRiContractInfo;
import com.ebao.ap99.contract.entity.TRiContractInfoLog;
import com.ebao.ap99.contract.entity.TRiContractMoreInfo;
import com.ebao.ap99.contract.entity.TRiContractMoreInfoLog;
import com.ebao.ap99.contract.entity.TRiContractStructure;
import com.ebao.ap99.contract.entity.TRiContractStructureLog;
import com.ebao.ap99.contract.model.BusinessConditionVO;
import com.ebao.ap99.contract.model.ContractClaimConditionItem;
import com.ebao.ap99.contract.model.ContractSectionVO;
import com.ebao.ap99.contract.model.ContractStatusLogVO;
import com.ebao.ap99.contract.model.ContractSubsectionVO;
import com.ebao.ap99.contract.model.ContractVO;
import com.ebao.ap99.contract.model.CurrencyVO;
import com.ebao.ap99.contract.model.DeductionsItemVO;
import com.ebao.ap99.contract.model.EndorsementVO;
import com.ebao.ap99.contract.model.LimitEventVO;
import com.ebao.ap99.contract.model.LimitItemVO;
import com.ebao.ap99.contract.model.PremiumItemVO;
import com.ebao.ap99.contract.model.PremiumVO;
import com.ebao.ap99.contract.model.RetrocessionItemVO;
import com.ebao.ap99.contract.model.TerminationVO;
import com.ebao.ap99.contract.model.BusinessModel.BusinessConditionBO;
import com.ebao.ap99.contract.model.BusinessModel.ContractBO;
import com.ebao.ap99.contract.model.BusinessModel.PremiumBO;
import com.ebao.ap99.contract.model.BusinessModel.SectionBO;
import com.ebao.ap99.contract.model.BusinessModel.SubsectionBO;
import com.ebao.ap99.contract.service.BusinessConditionDS;
import com.ebao.ap99.contract.service.ContractDS;
import com.ebao.ap99.contract.service.ContractEndorsementDS;
import com.ebao.ap99.contract.service.ContractHomeDS;
import com.ebao.ap99.contract.service.ContractRetrocessionDS;
import com.ebao.ap99.contract.util.ContractCst;
import com.ebao.ap99.file.service.PrintService;
import com.ebao.ap99.mail.MailService;
import com.ebao.ap99.mail.MailVO;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.ap99.parent.DataTypeConvertor;
import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.ap99.partner.entity.MessageVO;
import com.ebao.ap99.partner.service.CheckAmlService;
import com.ebao.ap99.partner.service.PartnerAPI;
import com.ebao.unicorn.platform.foundation.cfg.Env;
import com.ebao.unicorn.platform.foundation.security.entity.User;
import com.ebao.unicorn.platform.urpmgmt.service.UserService;

/**
 * Date: Jan 7, 2016 4:39:42 PM
 * 
 * @author weiping.wang
 */
public class ContractHomeDSImpl implements ContractHomeDS {
	@Autowired
	private ContractRetrocessionDS retrocessionDS;

	@Autowired
	private TRiContractInfoDao contractInfoDao;

	@Autowired
	private TRiContractInfoLogDao contractInfoLogDao;

	@Autowired
	private TRiContractMoreInfoDao moreInfoDao;

	@Autowired
	private TRiContractMoreInfoLogDao moreInfoLogDao;

	@Autowired
	private TRiContractAreaperilDao areaperilDao;

	@Autowired
	private TRiContractAreaperilLogDao areaperilLogDao;

	@Autowired
	private TRiContClaimDao claimConditionDao;

	@Autowired
	private TRiContClaimLogDao claimConditionLogDao;

	@Autowired
	private TRiContClaimItemDao claimConditionItemDao;

	@Autowired
	private TRiContClaimItemLogDao claimItemLogDao;

	@Autowired
	private TRiContAccountDao accountConditionDao;

	@Autowired
	private TRiContAccountLogDao accountLogDao;

	@Autowired
	private TRiContractStructureDao structureDao;

	@Autowired
	private TRiContractStructureLogDao structureLogDao;

	@Autowired
	private TRiContSectionInfoDao sectionInfoDao;

	@Autowired
	private TRiContSectionInfoLogDao sectionInfoLogDao;

	@Autowired
	private TRiContCancelDao cancelDao;

	@Autowired
	private TRiContCancelLogDao cancelLogDao;

	@Autowired
	private TRiContSubsectionInfoDao subsectionInfoDao;

	@Autowired
	private TRiContSubsectionInfoLogDao subsectionInfoLogDao;

	@Autowired
	private ContractDS contractDS;

	@Autowired
	private TRiContOperationDao operationDao;

	@Autowired
	private BusinessConditionDS businessDS;

	@Autowired
	private ContractRetrocessionDS retroDS;

	@Autowired
	private AccountingService accountingService;

	@Autowired
	private TRiContPricingDao pricingDao;

	@Autowired
	private TRiContTerminationDao terminationDao;

	@Autowired
	private ClaimService claimService;
	@Autowired
	private SoaService soaService;
	@Autowired
	private ContractEndorsementDS endorsementDS;
	@Autowired
	private PrintService printService;
	@Autowired
	private UserService userService;

	@Autowired
	private CheckAmlService checkAmlService;

	@Autowired
	private PartnerAPI partnerService;

	// save contractHome
	public ContractVO saveContractInfo(ContractVO model, boolean needBackUp) throws Exception {
		ContractBO bo = new ContractBO();
		model.setLastChanged(AppContext.getCurrentUser().getUserId());
		ContractModelConvertor.convertVOToBO(bo, model);
		contractDS.saveContractBO(bo, false);
		deleteSectionList(model.getDeleteSectionList());
		model.setDeleteSectionList(null);
		if (needBackUp) {
			backUpContractInfo(model);
		}
		ContractModelConvertor.convertBOToVO(bo, model);
		model.setLastChangedOn(bo.getUpdateTime());
		return model;
	}

	/**
	 * delete structure from DB, other info delete by trigger, the trigger is
	 * TRI_DELETE_SECTION_DATA
	 * 
	 * @param deleteContCompIds
	 */
	private void deleteSectionList(List<Long> deleteContCompIds) {
		if (null != deleteContCompIds) {
			for (Long contCompId : deleteContCompIds) {
				TRiContractStructure structure = structureDao.load(contCompId);
				structure.setIsActive("N");
				structureDao.save(structure);
			}
		}
	}

	/**
	 * backup contract info, include backup structure/contract home info/section
	 * info(include retrocession & business Info)/subsection info(include
	 * retrocession & business Info)
	 * 
	 * @param model
	 */
	private void backUpContractInfo(ContractVO model) throws Exception {
		Long contCompId = model.getContCompId();
		Long operateId = model.getOperateId();
		boolean needPricingBackup = false;
		if (ContractCst.CONTRACT_OPERATE_UW_REVIEW.equals(model.getOperateType())
				&& ContractCst.CONTRACT_REVIEW_APPROVE.equals(model.getReviewed())
				&& !contractDS.checkTheEntityHasBennInforce(contCompId)) {
			needPricingBackup = true;
		}
		backUpContractBasicInfo(contCompId, operateId, needPricingBackup);
		backUpContractOtherInfo(contCompId, operateId);
	}

	private void backUpContractBasicInfo(Long contCompId, Long operateId, boolean needPricingBackup) throws Exception {
		TRiContractInfo vo = contractInfoDao.load(contCompId);
		TRiContractInfoLog logVO = new TRiContractInfoLog();
		BeanUtils.copyPropertiesIngoreNull(logVO, vo);
		logVO.clearPrimaryKeyCascade();
		logVO.setOperateId(operateId);
		TRiContOperation operation = operationDao.load(operateId);
		logVO.setLatestStatus(operation.getStatus());
		contractInfoLogDao.insert(logVO);
		backUpStructureInfo(contCompId, operateId);
		businessDS.backupBusinessConditionInfo(contCompId, operateId);
		backUpSectionInfo(contCompId, operateId, needPricingBackup);
	}

	private void backUpStructureInfo(Long contCompId, Long operateId) {
		TRiContractStructure vo = structureDao.load(contCompId);
		TRiContractStructureLog logVO = new TRiContractStructureLog();
		BeanUtils.copyPropertiesIngoreNull(logVO, vo);
		logVO.clearPrimaryKeyCascade();
		logVO.setOperateId(operateId);
		structureLogDao.insert(logVO);
	}

	private void backUpSectionInfo(Long contCompId, Long operateId, boolean needPricingBackup) throws Exception {
		List<Long> sectionIds = structureDao.getChildrenIdList(contCompId);
		for (Long sectionId : sectionIds) {
			backUpStructureInfo(sectionId, operateId);
			TRiContSectionInfo vo = sectionInfoDao.load(sectionId);
			TRiContSectionInfoLog logVO = new TRiContSectionInfoLog();
			BeanUtils.copyPropertiesIngoreNull(logVO, vo);
			logVO.setOperateId(operateId);
			sectionInfoLogDao.insert(logVO);
			businessDS.backupBusinessConditionInfo(sectionId, operateId);
			retroDS.backupRetrocessionInfo(sectionId, operateId);
			backUpSubSectionInfo(sectionId, operateId);
			TRiContPricing pricingEntity = pricingDao.loadByContCompId(sectionId);
			if (needPricingBackup && null != pricingEntity && pricingEntity.getPricingId() != null) {
				pricingDao.saveLogForPricingInfo(pricingEntity, operateId);
			}
		}

	}

	private void backUpSubSectionInfo(Long contCompId, Long operateId) throws Exception {
		List<Long> sectionIds = structureDao.getChildrenIdList(contCompId);
		for (Long sectionId : sectionIds) {
			backUpStructureInfo(sectionId, operateId);
			TRiContSubsectionInfo vo = subsectionInfoDao.load(sectionId);
			TRiContSubsectionInfoLog logVO = new TRiContSubsectionInfoLog();
			BeanUtils.copyPropertiesIngoreNull(logVO, vo);
			logVO.setOperateId(operateId);
			subsectionInfoLogDao.insert(logVO);
			businessDS.backupBusinessConditionInfo(sectionId, operateId);
			retroDS.backupRetrocessionInfo(sectionId, operateId);
		}

	}

	private void backUpContractOtherInfo(Long contCompId, Long operateId) {
		// backup contract more info
		TRiContractMoreInfo info = moreInfoDao.load(contCompId);
		TRiContractMoreInfoLog logVO = new TRiContractMoreInfoLog();
		BeanUtils.copyPropertiesIngoreNull(logVO, info);
		logVO.clearPrimaryKeyCascade();
		logVO.setOperateId(operateId);
		moreInfoLogDao.insert(logVO);
		// backup Accounting Condition
		TRiContAccount account = accountConditionDao.load(contCompId);
		TRiContAccountLog accountlogVO = new TRiContAccountLog();
		BeanUtils.copyPropertiesIngoreNull(accountlogVO, account);
		accountlogVO.clearPrimaryKeyCascade();
		accountlogVO.setOperateId(operateId);
		accountLogDao.insert(accountlogVO);
		// backup Claim Condition
		TRiContClaim claim = claimConditionDao.load(contCompId);
		TRiContClaimLog claimlogVO = new TRiContClaimLog();
		BeanUtils.copyPropertiesIngoreNull(claimlogVO, claim);
		claimlogVO.clearPrimaryKeyCascade();
		claimlogVO.setOperateId(operateId);
		claimConditionLogDao.insert(claimlogVO);
		List<TRiContClaimItem> claimItem = claimConditionItemDao.loadByContCompId(contCompId);
		for (TRiContClaimItem itemVO : claimItem) {
			TRiContClaimItemLog claimItemlogVO = new TRiContClaimItemLog();
			BeanUtils.copyPropertiesIngoreNull(claimItemlogVO, itemVO);
			claimItemlogVO.clearPrimaryKeyCascade();
			claimItemlogVO.setOperateId(operateId);
			claimItemLogDao.insert(claimItemlogVO);
		}

		// backup Area&Peril
		TRiContractAreaperil areaPeril = areaperilDao.load(contCompId);
		TRiContractAreaperilLog areaPerillogVO = new TRiContractAreaperilLog();
		BeanUtils.copyPropertiesIngoreNull(areaPerillogVO, areaPeril);
		areaPerillogVO.setOperateId(operateId);
		areaperilLogDao.insert(areaPerillogVO);
		// backup cancellation
		TRiContCancel cancel = cancelDao.loadByContCompId(contCompId);
		TRiContCancelLog cancellogVO = new TRiContCancelLog();
		BeanUtils.copyPropertiesIngoreNull(cancellogVO, cancel);
		cancellogVO.clearPrimaryKeyCascade();
		cancellogVO.setOperateId(operateId);
		cancelLogDao.insert(cancellogVO);
	}

	// load contractInfo
	@Override
	public ContractVO loadContractInfo(Long contId) throws Exception {
		ContractVO vo = new ContractVO();
		if (contId != null) {
			ContractBO contractBO = contractDS.loadContractBOForView(contId);
			ContractModelConvertor.convertBOToVO(contractBO, vo);
			// get endorsement info
			EndorsementVO endoVO = endorsementDS.getLatestEndorsement(contId);
			if (null != endoVO) {
				vo.setEndoType(endoVO.getEndoType());
			}

			vo.setCreatedOn(contractBO.getInsertTime());
			vo.setLastChangedOn(contractBO.getUpdateTime());

			List<TRiContOperation> statusList = operationDao.loadContractForLog(contId);
			for (TRiContOperation statusVO : statusList) {
				ContractStatusLogVO logVO = new ContractStatusLogVO();
				BeanUtils.copyProperties(statusVO, logVO);
				vo.getLogList().add(logVO);
			}
		} else {
			// initial Created By & Last Changed By
			vo.setCreatedBy(AppContext.getCurrentUser().getUserId());
			vo.setLastChanged(AppContext.getCurrentUser().getUserId());
		}
		vo.setContCompId(contId);
		return vo;
	}

	@Override
	public ContractSectionVO loadSectionInfo(Long sectionId, Long parentId) throws Exception {
		ContractSectionVO vo = new ContractSectionVO();
		Long contractId = null;
		if (null != parentId) {
			contractId = parentId;
		} else {
			TRiContractStructure structureEntity = structureDao.load(sectionId);
			contractId = structureEntity.getParentId();
		}

		if (sectionId != null) {
			SectionBO sectionBO = contractDS.loadSectionBO(sectionId, true);
			// get endorsement info
			EndorsementVO endoVO = endorsementDS.getLatestEndorsement(contractId);
			if (null != endoVO) {
				sectionBO.setEndoType(endoVO.getEndoType());
			}
			ContractSectionModelConvertor.convertBOToVO(sectionBO, vo);
		} else {
			if (parentId != null) {
				ContractVO parentVO = loadContractInfo(parentId);
				BeanUtils.copyPropertiesIngoreNull(vo, parentVO);
				vo.setUwAreaList(DataTypeConvertor.transStringToSelectTree(parentVO.getCedentCountry()));
				vo.setParentId(parentId);

				// Copy the earliest section's business condition info to new
				// section
				List<Long> sectionIds = structureDao.getChildrenIdList(parentId);
				if (CollectionUtils.isNotEmpty(sectionIds)) {
					Long minSectionId = Collections.min(sectionIds);
					SectionBO minSectionBO = contractDS.loadSectionBO(minSectionId, false);
					if (null != minSectionBO && null != minSectionBO.getBusinessBO()) {
						BusinessConditionVO bcVO = new BusinessConditionVO();
						BusinessConditionModelConvertor.covertBuinessBOToVO(minSectionBO.getBusinessBO(), bcVO);
						if (bcVO != null) {
							bcVO.resetIds();
							bcVO.setContCompId(sectionId);
						}
						vo.setBusinessConditionVO(bcVO);
					}
					vo.setLoB(minSectionBO.getLoB());
					vo.setCoB(minSectionBO.getCoB());
					vo.setCovered(minSectionBO.getCovered());
					vo.setPeril(minSectionBO.getPeril());
				} else {
					vo.setLoB(parentVO.getMainclass());
					vo.setCoB(parentVO.getSubclass());
				}
				vo.setContCompId(null);
			}
		}

		// get contract level's information
		TRiContractInfo parentEntity = contractInfoDao.load(contractId);
		vo.setParentId(contractId);
		vo.setContractNature(parentEntity.getContractNature());
		vo.setContractCategory(parentEntity.getContractCategory());
		vo.setMainCurrency(parentEntity.getMainCurrency());
		vo.setReinsStarting(parentEntity.getReinsStarting());

		return vo;
	}

	// save sectionInfo
	@Override
	public ContractSectionVO saveSectionInfo(ContractSectionVO vo) throws Exception {
		List<Long> deleteSectionList = vo.getDeleteSubSections();
		this.deleteSectionList(deleteSectionList);
		vo.setDeleteSubSections(null);

		SectionBO sectionBO = new SectionBO();
		ContractSectionModelConvertor.convertVOToBO(sectionBO, vo);
		contractDS.saveSectionInfo(sectionBO, false);
		ContractSectionModelConvertor.convertBOToVO(sectionBO, vo);

		// if (vo.getBusinessConditionVO() != null) {
		// vo.getBusinessConditionVO().setContCompId(sectionBO.getContCompId());
		// BusinessConditionBO bcBO = new BusinessConditionBO();
		// BusinessConditionModelConvertor.convertBusinessVOToBO(bcBO,
		// vo.getBusinessConditionVO());
		// businessDS.saveBusinessBO(bcBO);
		// BusinessConditionModelConvertor.covertBuinessBOToVO(bcBO,
		// vo.getBusinessConditionVO());
		// }
		return vo;
	}

	// load subsectionInfo
	@Override
	public ContractSubsectionVO loadSubsectionInfo(Long subsectionId, Long parentId) throws Exception {
		ContractSubsectionVO vo = new ContractSubsectionVO();
		if (subsectionId != null) {
			SubsectionBO subsectionBO = contractDS.loadSubSectionBO(subsectionId, false);
			// // get endorsement info
			// EndorsementVO endoVO =
			// endorsementDS.getLatestEndorsement(parentId);
			// if (null != endoVO) {
			// subsectionBO.setEndoType(endoVO.getEndoType());
			// }
			ContractSubsectionModelConvertor.convertBOToVO(subsectionBO, vo);
		} else {
			if (parentId != null) {
				ContractSectionVO parentVO = loadSectionInfo(parentId, null);
				BeanUtils.copyPropertiesIngoreNull(vo, parentVO);
				vo.setParentId(parentId);
				vo.setContCompId(null);
				if (vo.getBusinessConditionVO() != null) {
					vo.getBusinessConditionVO().resetIds();
					vo.getBusinessConditionVO().setContCompId(subsectionId);
				}
			}
		}

		// get contract level's information
		Long contractId = null;
		if (null != parentId) {
			TRiContractStructure structureEntity = structureDao.load(parentId);
			contractId = structureEntity.getParentId();
		} else {
			TRiContractStructure structureEntityForSubsection = structureDao.load(subsectionId);
			parentId = structureEntityForSubsection.getParentId();
			TRiContractStructure structureEntityForSection = structureDao.load(parentId);
			contractId = structureEntityForSection.getParentId();
		}
		TRiContractInfo parentEntity = contractInfoDao.load(contractId);
		vo.setParentId(parentId);
		vo.setContractNature(parentEntity.getContractNature());
		vo.setContractCategory(parentEntity.getContractCategory());

		return vo;
	}

	// save subsectionInfo
	@Override
	public ContractSubsectionVO saveSubsectionInfo(ContractSubsectionVO vo) throws Exception {
		SubsectionBO sectionBO = new SubsectionBO();
		ContractSubsectionModelConvertor.convertVOToBO(sectionBO, vo);
		contractDS.saveSubsectionInfo(sectionBO, false);
		ContractSubsectionModelConvertor.convertBOToVO(sectionBO, vo);
		return vo;
	}

	/**
	 * Get contCompId from structure for section/subsection level.
	 *
	 * @param parentId
	 * @param currentId
	 * @param name
	 * @param type
	 * @return
	 */
	public Long saveStructure(Long parentId, Long currentId, String name, String type) throws Exception {
		TRiContractStructure structEntity = new TRiContractStructure();
		structEntity.setContCompId(currentId);
		structEntity.setParentId(parentId);
		structEntity.setName(name);
		structEntity.setType(type);

		TRiContractStructure parentModel = structureDao.load(parentId);
		String[] arrayStr = parentModel.getFullName().split(ContractCst.STRUCTURE_SEPARATOR);
		String fullName = "";
		for (int i = 0; i < arrayStr.length; i++) {
			if (i == 0) {
				fullName += arrayStr[i];
			} else {
				fullName += ContractCst.STRUCTURE_SEPARATOR + arrayStr[i];
				if (i == 1 && ContractCst.SECTION_LEVEL.equals(type)) {
					fullName += ContractCst.STRUCTURE_SEPARATOR + name;
				}
				if (i == 2 && ContractCst.SUB_SECTION_LEVEL.equals(type)) {
					fullName += ContractCst.STRUCTURE_SEPARATOR + name;
				}
			}
		}
		structEntity.setFullName(fullName);

		structEntity = structureDao.save(structEntity);

		return structEntity.getContCompId();
	}

	/**
	 * copy&renew a contract,then create a new one.
	 * 
	 * @param contId
	 * @param isRenewal
	 * @return
	 */
	@Override
	public ContractVO copyOrRenewContract(Long contId, boolean isRenewal) throws Exception {
		// load contract info
		ContractVO contractVO = new ContractVO();
		ContractBO contractBO = contractDS.loadContractBO(contId, true);
		ContractBO newContractBO = resetIdsForContractBO(contractBO, isRenewal);
		contractDS.saveContractBO(newContractBO, true);
		ContractModelConvertor.convertBOToVO(newContractBO, contractVO);
		return contractVO;
	}

	private ContractBO resetIdsForContractBO(ContractBO bo, boolean isRenewal) throws Exception {
		ContractVO contractVO = new ContractVO();
		Long contCompId = bo.getContCompId();
		List<SectionBO> sectionBOs = bo.getSectionList();
		// BusinessConditionBO bcBO = bo.getBusinessBO();
		ContractModelConvertor.convertBOToVO(bo, contractVO);
		bo = new ContractBO();

		contractVO.setContCompId(null);
		contractVO.setLatestStatus(ContractCst.CONTRACT_STATUS_DATA_INPUT);
		contractVO.setRemark(null);
		contractVO.setCoveredRemark(null);
		contractVO.setPerilRemark(null);
		contractVO.setRejectReason(null);
		if (isRenewal) {
			contractVO.setRenewalId(contCompId);
			// set period starting/ending time
			Calendar calStart = Calendar.getInstance();
			calStart.setTime(contractVO.getReinsEnding());
			calStart.add(Calendar.DATE, 1);
			contractVO.setReinsStarting(calStart.getTime());
			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(contractVO.getReinsEnding());
			calEnd.add(Calendar.YEAR, 1);
			contractVO.setReinsEnding(calEnd.getTime());
			contractVO.setUwYear(new Long(calStart.get(Calendar.YEAR)));
		} else {
			contractVO.setContractCode(contractDS.getContractCode());
		}
		contractVO.setReviewed(null);
		contractVO.setLinkName(null);
		if (CollectionUtils.isNotEmpty(contractVO.getContractClaimConditionItemList())) {
			for (ContractClaimConditionItem item : contractVO.getContractClaimConditionItemList()) {
				item.setContCompId(null);
				item.setItemId(null);
			}
		}
		contractVO.setSectionList(null);
		ContractModelConvertor.convertVOToBO(bo, contractVO);

		if (CollectionUtils.isNotEmpty(sectionBOs)) {
			List<SectionBO> sectionList = new ArrayList<SectionBO>();
			for (SectionBO sectionBO : sectionBOs) {
				sectionList.add(resetIdsForSectionBO(sectionBO, isRenewal));
			}
			bo.setSectionList(sectionList);
		}
		// if (bcBO != null) {
		// bo.setBusinessBO(resetBusinessCondition(bcBO, isRenewal));
		// }
		return bo;
	}

	private SectionBO resetIdsForSectionBO(SectionBO bo, boolean isRenewal) throws Exception {
		ContractSectionVO sectionVO = new ContractSectionVO();
		List<SubsectionBO> subsectionBOs = bo.getSubsectionList();
		BusinessConditionBO bcBO = bo.getBusinessBO();
		ContractSectionModelConvertor.convertBOToVO(bo, sectionVO);
		bo = new SectionBO();

		sectionVO.setContCompId(null);
		sectionVO.setSubsectionList(null);
		ContractSectionModelConvertor.convertVOToBO(bo, sectionVO);
		bo.setPricingBO(null);

		if (bcBO != null) {
			bo.setBusinessBO(resetBusinessCondition(bcBO, isRenewal));
		}
		if (CollectionUtils.isNotEmpty(subsectionBOs)) {
			List<SubsectionBO> subsectionList = new ArrayList<SubsectionBO>();
			for (SubsectionBO subsectionBO : subsectionBOs) {
				subsectionList.add(resetIdsForSubsectionBO(subsectionBO, isRenewal));
			}
			bo.setSubsectionList(subsectionList);
		}
		return bo;
	}

	private SubsectionBO resetIdsForSubsectionBO(SubsectionBO bo, boolean isRenewal) throws Exception {
		ContractSubsectionVO subsectionVO = new ContractSubsectionVO();
		BusinessConditionBO bcBO = bo.getBusinessBO();
		ContractSubsectionModelConvertor.convertBOToVO(bo, subsectionVO);
		bo = new SubsectionBO();

		subsectionVO.setContCompId(null);
		ContractSubsectionModelConvertor.convertVOToBO(bo, subsectionVO);
		if (bcBO != null) {
			bo.setBusinessBO(resetBusinessCondition(bcBO, isRenewal));
		}
		return bo;
	}

	private BusinessConditionBO resetBusinessCondition(BusinessConditionBO bo, boolean isRenewal) throws Exception {
		BusinessConditionVO bcVO = new BusinessConditionVO();
		BusinessConditionModelConvertor.covertBuinessBOToVO(bo, bcVO);
		bo = new BusinessConditionBO();

		bcVO.resetIds();
		bcVO.setPremiumRemark(null);
		bcVO.setRemarkPanel(null);
		bcVO.setRebateRemark(null);
		bcVO.setComments1(null);
		bcVO.setComments2(null);
		bcVO.setComments3(null);
		bcVO.setContCompId(null);
		if (bcVO.getDeductionsList() != null) {
			for (DeductionsItemVO item : bcVO.getDeductionsList()) {
				item.setRemarks(null);
			}
		}
		if (bcVO.getOtherDeductionList() != null) {
			for (DeductionsItemVO item : bcVO.getOtherDeductionList()) {
				item.setRemarks(null);
			}
		}
		if (bcVO.getCurrencyList() != null) {
			for (CurrencyVO item : bcVO.getCurrencyList()) {
				item.setCurrencyRemarks(null);
				item.setContCompId(null);
			}
		}
		if (bcVO.getLimitQsList() != null) {
			for (LimitItemVO item : bcVO.getLimitQsList()) {
				item.setRemark(null);
			}
		}
		if (bcVO.getLimitSurplusList() != null) {
			for (LimitItemVO item : bcVO.getLimitSurplusList()) {
				item.setRemark(null);
			}
		}
		if (bcVO.getLimitRegularList() != null) {
			for (LimitItemVO item : bcVO.getLimitRegularList()) {
				item.setRemark(null);
			}
		}
		if (bcVO.getLimitStopList() != null) {
			for (LimitItemVO item : bcVO.getLimitStopList()) {
				item.setRemark(null);
			}
		}
		if (bcVO.getLimitStopPerList() != null) {
			for (LimitItemVO item : bcVO.getLimitStopPerList()) {
				item.setRemark(null);
			}
		}
		if (bcVO.getLimitItemList() != null) {
			for (LimitItemVO item : bcVO.getLimitItemList()) {
				item.setRemark(null);
			}
		}
		if (bcVO.getLimitEventList() != null) {
			for (LimitEventVO item : bcVO.getLimitEventList()) {
				item.setCommRemark(null);
			}
		}

		BusinessConditionModelConvertor.convertBusinessVOToBO(bo, bcVO);
		return bo;
	}

	public Page<ContractVO> findCurrentPage(ContractVO condition, int pageIndex, int countPerPage) {
		Page<ContractVO> page = new Page<ContractVO>();
		try {
			Long results = contractInfoDao.getContractCount(condition);
			// page.setResults(results);
			List<TRiContractInfo> list = contractInfoDao.getCurrentPage(condition, pageIndex, countPerPage);
			List<ContractVO> contractVOList = BeanUtils.convertList(list, ContractVO.class);
			if (CollectionUtils.isNotEmpty(contractVOList)) {
				for (ContractVO item : contractVOList) {
					item.setCedent(partnerService.loadPartnerNameByPartnerCode(item.getCedent()));
					item.setBroker(partnerService.loadPartnerNameByPartnerCode(item.getBroker()));
				}
			}
			page.setRows(contractVOList);
			page.setPageIndex(pageIndex);
			Long pageCount = results % countPerPage == 0 ? results / countPerPage : results / countPerPage + 1;
			page.setPageCount(pageCount);
			if (results < countPerPage) {
				page.setCountPerPage(results.intValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public boolean getRenewalStatus(Long renewalId) throws Exception {
		return contractInfoDao.getRenewalStatusByRenewalId(renewalId);
	}

	public ContractVO contractEntry(ContractVO vo) throws Exception {
		String status = vo.getLatestStatus();
		Long operateId = generateOpertion(vo, ContractCst.CONTRACT_OPERATE_DATA_INPUT);
		vo.setOperateId(operateId);
		vo.setLatestStatus(ContractCst.CONTRACT_OPERATE_UW_REVIEW);
		this.saveContractInfo(vo, true);

		// Scenarios 1: send mail for UW Review (Data Input)
		ContractVO modelForMail = new ContractVO();
		BeanUtils.copyProperties(vo, modelForMail);
		modelForMail.setLatestStatus(status);
		this.sendMail(modelForMail, "1");

		return vo;
	}

	public ContractVO contractUwReview(ContractVO vo) throws Exception {
		Long operateId = generateOpertion(vo, ContractCst.CONTRACT_OPERATE_UW_REVIEW);
		vo.setOperateId(operateId);
		if (ContractCst.CONTRACT_REVIEW_APPROVE.equals(vo.getReviewed())) {
			vo.setLatestStatus(ContractCst.CONTRACT_STATUS_INFORCE);
			vo.setRejectReason(null);
		} else if (ContractCst.CONTRACT_REVIEW_REJECT.equals(vo.getReviewed())) {
			TRiContOperation operation = operationDao.loadPreContractOperation(vo.getContCompId());
			vo.setLatestStatus(operation.getStatus());

			// Scenario 2 When Review failed, email sent to technical accounting
			ContractVO modelForMail = new ContractVO();
			BeanUtils.copyProperties(vo, modelForMail);
			modelForMail.setLatestStatus(operation.getStatus());
			this.sendMail(modelForMail, "2");
		}
		this.saveContractInfo(vo, true);
		return vo;
	}

	public ContractVO contractEndoSave(ContractVO vo) throws Exception {
		Long operateId = generateOpertion(vo, ContractCst.CONTRACT_OPERATE_ENDORSEMENT);
		vo.setOperateId(operateId);
		vo.setLatestStatus(ContractCst.CONTRACT_STATUS_DATA_UPDATE);
		this.saveContractInfo(vo, true);
		return vo;
	}

	public ContractVO contractTerminationSave(ContractVO vo) throws Exception {
		Long operateId = generateOpertion(vo, ContractCst.CONTRACT_OPERATE_TERMINATION);
		vo.setOperateId(operateId);
		vo.setLatestStatus(ContractCst.CONTRACT_STATUS_CANCELLED);
		this.saveContractInfo(vo, true);
		// cancelled's log
		generateOpertion(vo, ContractCst.CONTRACT_OPERATE_TERMINATION);
		return vo;
	}

	public ContractVO contractEndo(ContractVO vo) throws Exception {
		String status = vo.getLatestStatus();
		List<EndorsementVO> endoList = endorsementDS.loadEndorsementList(vo.getContCompId());
		if (CollectionUtils.isNotEmpty(endoList)) {
			vo.setEndoId(endoList.get(endoList.size() - 1).getEndoId());
		}
		Long operateId = generateOpertion(vo, ContractCst.CONTRACT_OPERATE_ENDORSEMENT);
		vo.setOperateId(operateId);
		vo.setLatestStatus(ContractCst.CONTRACT_OPERATE_UW_REVIEW);
		this.saveContractInfo(vo, true);

		// Scenarios 1: send mail for UW Review (Data Update)
		ContractVO modelForMail = new ContractVO();
		BeanUtils.copyProperties(vo, modelForMail);
		modelForMail.setLatestStatus(status);
		this.sendMail(modelForMail, "1");
		// Scenario3: When Premium is changed via Endorsement, email sent to
		// remind for Pricing Estimation adjustment
		if (this.isPremiumChangedForEndo(vo.getContCompId())) {
			this.sendMail(modelForMail, "3");
		}

		return vo;
	}

	private Long generateOpertion(ContractVO vo, String operationType) {
		TRiContOperation operation = new TRiContOperation(vo.getContCompId(), operationType, vo.getEndoId(),
				vo.getLatestStatus());
		operationDao.insert(operation);
		return operation.getOperateId();
	}

	private boolean isPremiumChangedForEndo(Long contractId) throws Exception {
		boolean ret = false;
		ContractBO contractBO = contractDS.loadContractBOForView(contractId);
		ContractBO contractBOForLog = contractDS.loadContractBOForLog(contractId, null,
				ContractCst.CONTRACT_STATUS_INFORCE, false);
		for (SectionBO secBOForLog : contractBOForLog.getSectionList()) {
			for (SectionBO secBO : contractBO.getSectionList()) {
				if (secBO.getContCompId().equals(secBOForLog.getContCompId())) {
					// secBO.getBusinessBO().getPremiumBO()
					if (!this.isPremiumInfoEqual(secBO.getBusinessBO().getPremiumBO(),
							secBOForLog.getBusinessBO().getPremiumBO())) {
						ret = true;
						break;
					}
				}
			}
		}
		return ret;
	}

	private boolean isPremiumInfoEqual(PremiumBO bo, PremiumBO boForLog) {
		boolean ret = true;
		try {
			PremiumVO premiumVO = new PremiumVO();
			BeanUtils.copyProperties(bo, premiumVO);
			PremiumVO premiumVOForLog = new PremiumVO();
			BeanUtils.copyProperties(boForLog, premiumVOForLog);
			PremiumItemVO premiumItemVO = new PremiumItemVO();
			PremiumItemVO premiumItemVOForLog = new PremiumItemVO();
			if (!premiumVO.equals(premiumVOForLog)) {
				return false;
			}
			if (bo.getTRiContPremiumItems().size() != boForLog.getTRiContPremiumItems().size()) {
				return false;
			}
			for (TRiContPremiumItem item : bo.getTRiContPremiumItems()) {
				BeanUtils.copyProperties(item, premiumItemVO);
				boolean isLogExist = false;
				for (TRiContPremiumItem itemForLog : boForLog.getTRiContPremiumItems()) {
					if (item.getItemId().equals(itemForLog.getItemId())) {
						isLogExist = true;
						BeanUtils.copyProperties(itemForLog, premiumItemVOForLog);
						if (!premiumItemVO.equals(premiumItemVOForLog)) {
							ret = false;
							break;
						}
					}
				}
				if (!isLogExist) {
					ret = false;
					break;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return ret;
	}

	@Override
	public ContractVO revertContract(Long contractId, Long operateId, String contractStatus) throws Exception {
		ContractBO contractBO = contractDS.loadContractBOForLog(contractId, operateId, contractStatus, true);
		contractDS.saveContractBO(contractBO, true);

		// delete added sections/subsections
		if (CollectionUtils.isNotEmpty(contractBO.getDeleteSectionList())) {
			deleteSectionList(contractBO.getDeleteSectionList());
			contractBO.setDeleteSectionList(null);
		}
		for (SectionBO item : contractBO.getSectionList()) {
			if (CollectionUtils.isNotEmpty(item.getDeleteSubsectionList())) {
				deleteSectionList(item.getDeleteSubsectionList());
				item.setDeleteSubsectionList(null);
			}
		}

		ContractVO vo = new ContractVO();
		ContractModelConvertor.convertBOToVO(contractBO, vo);
		return vo;
	}

	@Override
	public TerminationVO loadTermination(Long contractId) throws Exception {
		TerminationVO termination = new TerminationVO();
		if (contractId != null) {
			TRiContTermination entity = terminationDao.getTermination(contractId);
			BeanUtils.copyPropertiesIngoreNull(termination, entity);
		}
		return termination;
	}

	@Override
	public TerminationVO saveTermination(TerminationVO vo) throws Exception {
		TRiContTermination entity = new TRiContTermination();
		BeanUtils.copyPropertiesIngoreNull(entity, vo);
		terminationDao.save(entity);

		ContractVO model = loadContractInfo(vo.getContCompId());
		if (vo.getTerminationId() == null) {
			model = contractTerminationSave(model);
		}
		BeanUtils.copyPropertiesIngoreNull(vo, entity);
		return vo;
	}

	@Override
	public boolean isPricingEstimated(Long contractId, String deleteSectionList) throws Exception {
		boolean ret = false;
		List<Long> sectionIdList = structureDao.getChildrenIdList(contractId);
		if (CollectionUtils.isNotEmpty(sectionIdList)) {
			for (Long sectionId : sectionIdList) {
				if (null == deleteSectionList || !deleteSectionList.contains(sectionId.toString())) {
					try {
						TRiContPricing pricing = pricingDao.loadByContCompId(sectionId);
						if (pricing != null && pricing.getPricingId() != null
								&& !CollectionUtils.isEmpty(pricing.getTRiContPricingItems())) {
							ret = true;
						}
					} catch (NoResultException e) {
						continue;
					}
				}
			}
		}
		return ret;
	}

	public List<String> getNonRetroList(@QueryParam("ContCompId") Long contractId) throws Exception {
		List<String> list = new ArrayList<String>();
		// get section id list
		List<Long> sectionIdList = structureDao.getChildrenIdList(contractId);
		if (CollectionUtils.isNotEmpty(sectionIdList)) {
			for (Long sectionId : sectionIdList) {
				List<RetrocessionItemVO> retroListForSec = retrocessionDS.loadRetrocessionList(sectionId);
				TRiContSectionInfo secEntity = sectionInfoDao.load(sectionId);
				if (!CollectionUtils.isNotEmpty(retroListForSec)) {
					list.add(secEntity.getSectionName());
				}

				// get subsection id list
				List<Long> subsectionIdList = structureDao.getChildrenIdList(sectionId);
				if (CollectionUtils.isNotEmpty(subsectionIdList)) {
					for (Long subsectionId : subsectionIdList) {
						List<RetrocessionItemVO> retroListForSubsec = retrocessionDS.loadRetrocessionList(subsectionId);
						if (!CollectionUtils.isNotEmpty(retroListForSubsec)) {
							TRiContSubsectionInfo subsecEntity = subsectionInfoDao.load(subsectionId);
							list.add(secEntity.getSectionName() + "->" + subsecEntity.getSubsectionName());
						}
					}
				}
			}
		}
		return list;
	}

	/**
	 * send Mail when submit contract.
	 * 
	 * @author chuan.ye
	 * @param vo
	 * @param mailType
	 * @return void
	 */
	public void sendMail(ContractVO vo, String mailType) throws Exception {

		MailVO mailVO = new MailVO();
		// get latest status
		String latestStatus = vo.getLatestStatus();
		// underwriter mail group
		String underWriterGroup = Env.getParameter("UW_EMAIL_GROUP");

		// get contract creator's mail address
		Long creatorId = vo.getCreatedBy();
		String creatorMail = null;
		User user = userService.load(creatorId);
		if (user != null) {
			creatorMail = user.getEmail();
		}

		String operateType = null;
		// Scenarios 1: send mail for UW Review (Data Input/ Data Update)
		if (mailType.equals("1")) {
			operateType = ContractCst.CONTRACT_OPERATE_UW_REVIEW;
			mailVO.setMailTitle("Underwriting Review Notification");
			mailVO.setMailType("underwrite");

			// mail's attachment
			String businessType = getBusinessType(vo.getContractNature());
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("ContCompId", String.valueOf(vo.getContCompId()));
			String filePath = printService.printPath(businessType, paramMap);
			mailVO.addAttachment(filePath);

			// latest status is 'Data Input', then mail to underwriter mail
			// group
			if (latestStatus.equals(ContractCst.CONTRACT_STATUS_DATA_INPUT)) {
				mailVO.setMailTo(underWriterGroup);
			} else if (latestStatus.equals(ContractCst.CONTRACT_STATUS_DATA_UPDATE)) {
				// latest status is 'Data Update', then send mail to contract
				// creator and underwriter mail group
				mailVO.setMailTo(creatorMail + "," + underWriterGroup);
			}

		} else if (mailType.equals("2")) {
			// Scenario 2 When Review failed, email sent to technical accounting

			mailVO.setMailTitle("Underwriting Data Update Request");
			mailVO.setMailType("technicalUnderwrite");
			// latest status is 'Data Input', then send mail to contract creator
			if (latestStatus.equals(ContractCst.CONTRACT_STATUS_DATA_INPUT)) {
				operateType = ContractCst.CONTRACT_OPERATE_DATA_INPUT;
				mailVO.setMailTo(creatorMail);
			} else if (latestStatus.equals(ContractCst.CONTRACT_STATUS_DATA_UPDATE)) {
				// latest status is 'Data Update', then send mail to contract
				// creator and endorsement's operator

				Long endoOperator = null;
				TRiContOperation operation = operationDao.loadPreContractOperation(vo.getContCompId());
				if (operation != null) {
					endoOperator = operation.getUpdateBy();
				}

				String endoOperatorMail = null;
				user = userService.load(endoOperator);
				if (user != null) {
					endoOperatorMail = user.getEmail();
				}

				operateType = ContractCst.CONTRACT_OPERATE_ENDORSEMENT;
				mailVO.setMailTo(creatorMail + "," + endoOperatorMail);
			}

		} else if (mailType.equals("3")) {
			// Scenario3: When Premium is changed via Endorsement, email sent to
			// remind for Pricing Estimation adjustment

			operateType = ContractCst.CONTRACT_OPERATE_UW_REVIEW;
			mailVO.setMailTitle("Pricing Estimate Adjustment Request");
			mailVO.setMailType("endorsement");
			mailVO.setMailTo(creatorMail + "," + underWriterGroup);
		}

		// set template parameter
		String url = CONTRACT_URL.replace(":contCompId", String.valueOf(vo.getContCompId())).replace(":operateType",
				operateType);
		mailVO.putTemplateParam("contractCode", vo.getContractCode());
		mailVO.putTemplateParam("uwYear", vo.getUwYear().toString());
		mailVO.putTemplateParam("url", url);

		try {
			MailService.sendEmail(mailVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getBusinessType(String contractNature) {
		String businessType = null;
		if (contractNature.equals("1")) {
			businessType = "14";
		} else {
			businessType = "13";
		}
		return businessType;
	}

	@Override
	public List<MessageVO> AMLCheck(ContractVO model) throws Exception {
		List<MessageVO> messageList = new ArrayList<>();
		// check cedent
		if (model.getCedent() != null && !"".equals(model.getCedent())) {
			MessageVO message = checkAmlService.amlCheck(model.getCedent(), "1");
			if (!message.isCheckFlag()) {
				messageList.add(message);
			}
		}
		// check broker
		if (model.getBroker() != null && !"".equals(model.getBroker())) {
			MessageVO message = checkAmlService.amlCheck(model.getBroker(), "2");
			if (!message.isCheckFlag()) {
				messageList.add(message);
			}
		}
		// check MGA
		if (model.getMga() != null && !"".equals(model.getMga())) {
			MessageVO message = checkAmlService.amlCheck(model.getMga(), "5");
			if (!message.isCheckFlag()) {
				messageList.add(message);
			}

		}
		// check insured
		if (model.getInsured() != null && !"".equals(model.getInsured())) {
			MessageVO message = checkAmlService.amlCheck(model.getInsured(), "3");
			if (!message.isCheckFlag()) {
				messageList.add(message);
			}

		}
		// check broker
		if (model.getCoBroker() != null && !"".equals(model.getCoBroker())) {
			MessageVO message = checkAmlService.amlCheck(model.getCoBroker(), "2");
			if (!message.isCheckFlag()) {
				messageList.add(message);
			}
		}
		return messageList;
	}

	// load contractInfo for log
	@Override
	public ContractVO loadContractInfoForLog(Long contId, Long operateId) throws Exception {
		ContractVO vo = new ContractVO();
		if (contId != null && operateId != null) {
			ContractBO contractBO = contractDS.loadContractBOForLog(contId, operateId, null, false);
			ContractModelConvertor.convertBOToVO(contractBO, vo);

			vo.setCreatedOn(contractBO.getInsertTime());
			vo.setLastChangedOn(contractBO.getUpdateTime());

			List<TRiContOperation> statusList = operationDao.loadContractForLogByOperateId(contId, operateId);
			for (TRiContOperation statusVO : statusList) {
				ContractStatusLogVO logVO = new ContractStatusLogVO();
				BeanUtils.copyProperties(statusVO, logVO);
				vo.getLogList().add(logVO);
			}
		}
		return vo;
	}

	// load sectionInfo for log
	@Override
	public ContractSectionVO loadSectionInfoForLog(Long sectionId, Long operateId) throws Exception {
		ContractSectionVO vo = new ContractSectionVO();
		TRiContractStructureLog structureLog = structureLogDao.loadStructureLog(sectionId, operateId);
		TRiContractInfoLog contractInfoLog = contractInfoLogDao.loadContractInfoLog(structureLog.getParentId(),
				operateId);
		if (sectionId != null && operateId != null) {
			SectionBO sectionBO = contractDS.loadSectionBOForLog(sectionId, operateId,
					contractInfoLog.getContractNature(), false);
			ContractSectionModelConvertor.convertBOToVO(sectionBO, vo);
		}

		// get contract level's information
		vo.setContractNature(contractInfoLog.getContractNature());
		vo.setContractCategory(contractInfoLog.getContractCategory());
		vo.setParentId(contractInfoLog.getContCompId());

		return vo;
	}

	// load subsectionInfo for log
	@Override
	public ContractSubsectionVO loadSubsectionInfoForLog(Long subsectionId, Long operateId) throws Exception {
		ContractSubsectionVO vo = new ContractSubsectionVO();
		TRiContractStructureLog structureLog = structureLogDao.loadStructureLog(subsectionId, operateId);
		TRiContractInfoLog contractInfoLog = contractInfoLogDao.loadContractInfoLog(
				structureLogDao.loadStructureLog(structureLog.getParentId(), operateId).getParentId(), operateId);
		if (subsectionId != null && operateId != null) {
			SubsectionBO subsectionBO = contractDS.loadSubsectionBOForLog(subsectionId, operateId,
					contractInfoLog.getContractNature(), false);
			ContractSubsectionModelConvertor.convertBOToVO(subsectionBO, vo);
		}

		// get contract level's information
		vo.setParentId(structureLog.getParentId());
		vo.setContractNature(contractInfoLog.getContractNature());
		vo.setContractCategory(contractInfoLog.getContractCategory());
		return vo;
	}

	@Override
	public List<TRiContractStructure> getRetroRelatedSections(Long contCompId) throws Exception {
		// TODO Auto-generated method stub
		List<TRiContractStructure> strctureList = new ArrayList<TRiContractStructure>();
		// get section id list
		List<Long> sectionIds = structureDao.getChildrenIdList(contCompId);
		for (Long sectionId : sectionIds) {
			List<RetrocessionItemVO> retroForSection = retrocessionDS.loadRetrocessionList(sectionId);
			if (CollectionUtils.isNotEmpty(retroForSection)) {
				strctureList.add(structureDao.load(sectionId));
			}
			// get subsection id list
			List<Long> subsectionIds = structureDao.getChildrenIdList(sectionId);
			for (Long subsectionId : subsectionIds) {
				List<RetrocessionItemVO> retroForSubSection = retrocessionDS.loadRetrocessionList(subsectionId);
				if (CollectionUtils.isNotEmpty(retroForSubSection)) {
					strctureList.add(structureDao.load(subsectionId));
				}
			}
		}

		return strctureList;
	}

}
