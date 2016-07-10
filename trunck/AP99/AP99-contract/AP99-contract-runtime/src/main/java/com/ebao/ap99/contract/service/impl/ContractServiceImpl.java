package com.ebao.ap99.contract.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.ContractPremiumModel;
import com.ebao.ap99.contract.dao.TRiContAccountDao;
import com.ebao.ap99.contract.dao.TRiContClaimDao;
import com.ebao.ap99.contract.dao.TRiContOperationDao;
import com.ebao.ap99.contract.dao.TRiContPricingDao;
import com.ebao.ap99.contract.dao.TRiContRetroDao;
import com.ebao.ap99.contract.dao.TRiContSectionInfoDao;
import com.ebao.ap99.contract.dao.TRiContractInfoDao;
import com.ebao.ap99.contract.dao.TRiContractStructureDao;
import com.ebao.ap99.contract.entity.ContractModel;
import com.ebao.ap99.contract.entity.SectionModel;
import com.ebao.ap99.contract.entity.TRiContAccount;
import com.ebao.ap99.contract.entity.TRiContOperation;
import com.ebao.ap99.contract.entity.TRiContPricing;
import com.ebao.ap99.contract.entity.TRiContSectionInfo;
import com.ebao.ap99.contract.entity.TRiContractInfo;
import com.ebao.ap99.contract.entity.TRiContractStructure;
import com.ebao.ap99.contract.model.ContractVO;
import com.ebao.ap99.contract.service.ContractPricingDS;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.contract.util.ContractCst;
import com.ebao.ap99.parent.DataTypeConvertor;
import com.ebao.ap99.parent.constant.ReInsuranceCst;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

public class ContractServiceImpl implements ContractService {

	@PersistenceContext
	private EntityManager em;
	@Autowired
	private TRiContractStructureDao structureDao;
	@Autowired
	private TRiContractInfoDao contractInfoDao;
	@Autowired
	private TRiContAccountDao accountDao;
	@Autowired
	private TRiContSectionInfoDao sectionInfoDao;
	@Autowired
	private ContractPricingDS pricingDS;
	@Autowired
	private TRiContClaimDao claimDao;
	@Autowired
	private TRiContRetroDao retroDao;
	@Autowired
	private TRiContPricingDao pricingDao;
	@Autowired
	private TRiContOperationDao operationDao;

	@Override
	public List<TreeModel> getContractStructureByCode(String contractCode, Long uwYear) throws Exception {
		return this.getContractStructureByCode(contractCode, uwYear, true);
	}

	@Override
	public List<TreeModel> getContractStructureByCode(String contractCode, Long uwYear, boolean isInforce)
			throws Exception {
		List<TRiContractInfo> contractInfos = new ArrayList<TRiContractInfo>();
		if (isInforce) {
			contractInfos = contractInfoDao.getInForceEntityByCodeUWYear(contractCode, uwYear);
		} else {
			contractInfos = contractInfoDao.getEntityByContractCodeUWYear(contractCode, uwYear);
		}

		if (null == contractInfos || contractInfos.size() == 0) {
			return null;
		} else {
			List<TreeModel> contractStructureList = new ArrayList<TreeModel>();
			for (TRiContractInfo contractInfo : contractInfos) {
				Long contractId = contractInfo.getContCompId();
				TreeModel contractStructure = getChildrenItemsByParentId(contractId);
				contractStructureList.add(contractStructure);
			}
			return contractStructureList;
		}
	}

	@Override
	public List<TreeModel> getInforceAndCancelledContractStructureByCode(String contractCode, Long uwYear)
			throws Exception {
		TRiContractInfo contractInfo = new TRiContractInfo();
		contractInfo = contractInfoDao.getInForceAndCancelledEntityByCodeUWYear(contractCode, uwYear);
		if (null == contractInfo || contractInfo.getContCompId() == 0l) {
			return null;
		}
		Long contractId = contractInfo.getContCompId();
		List<TreeModel> contractStructureList = new ArrayList<TreeModel>();
		TreeModel contractStructure = getChildrenItemsByParentId(contractId);
		contractStructureList.add(contractStructure);
		return contractStructureList;
	}

	// @Override
	// public ContractModel getcontractIdByCode(String contractCode, Long
	// uwYear) throws Exception {
	// ContractModel model = new ContractModel();
	// TRiContractInfo contractInfo = new TRiContractInfo();
	// contractInfo = contractInfoDao.getInForceEntityByCodeUWYear(contractCode,
	// uwYear);
	// if (null == contractInfo || contractInfo.getContCompId() == 0l) {
	// return null;
	// }
	// BeanUtils.copyProperties(model, contractInfo);
	// return model;
	// }

	@Override
	public ContractModel getInforceAndCancelledContractIdByCode(String contractCode, Long uwYear) throws Exception {
		ContractModel model = new ContractModel();
		TRiContractInfo contractInfo = new TRiContractInfo();
		contractInfo = contractInfoDao.getInForceAndCancelledEntityByCodeUWYear(contractCode, uwYear);
		if (null == contractInfo || contractInfo.getContCompId() == 0l) {
			return null;
		}
		BeanUtils.copyProperties(model, contractInfo);
		// Get settlement days
		TRiContAccount account = accountDao.getEntityByContCompId(contractInfo.getContCompId());
		if (null != account) {
			model.setSettlementDays(account.getSettlementDays());
		}
		return model;
	}

	/**
	 * @param contCompId
	 * @return
	 */
	@Override
	public TreeModel getChildrenItemsByParentId(Long contCompId) {
		if (null == contCompId || contCompId == 0) {
			return null;
		}
		TreeModel contractStructure = new TreeModel();
		TRiContractStructure tRiContractStructure = structureDao.load(contCompId);
		contractStructure.setId(tRiContractStructure.getContCompId());
		contractStructure.setText(tRiContractStructure.getFullName());
		contractStructure.setLevel(tRiContractStructure.getType());
		if (structureDao.checkIsLeafComponent(contCompId)) {
			List<Long> children = structureDao.getChildrenIdList(contCompId);
			for (Long childId : children) {
				contractStructure.getChildren().add(this.getChildrenItemsByParentId(childId));
			}
		}
		return contractStructure;
	}

	@Override
	public ContractModel getContractInfoByCompId(Long contCompId) throws Exception {
		TRiContractStructure contractStructure = structureDao.load(contCompId);
		ContractModel contractVO = new ContractModel();
		contractVO.setFullName(contractStructure.getFullName());
		Long contractId = null;
		if (ContractCst.CONTRACT_LEVEL.equals(contractStructure.getType())) {
			contractId = contractStructure.getContCompId();
			List<TRiContractStructure> sections = structureDao.getChildList(contCompId);
			for (TRiContractStructure section : sections) {
				SectionModel sectionMode = getSectionModel(section.getContCompId());
				contractVO.getSectionModel().add(sectionMode);
			}
		} else if (ContractCst.SECTION_LEVEL.equals(contractStructure.getType())) {
			TRiContractStructure contractLevel = structureDao.load(contractStructure.getParentId());
			contractId = contractLevel.getContCompId();
		} else if (ContractCst.SUB_SECTION_LEVEL.equals(contractStructure.getType())) {
			TRiContractStructure sectionLevel = structureDao.load(contractStructure.getParentId());
			TRiContractStructure contractLevel = structureDao.load(sectionLevel.getParentId());
			contractId = contractLevel.getContCompId();
		}

		TRiContractInfo contractInfoVO = contractInfoDao.load(contractId);
		BeanUtils.copyProperties(contractVO, contractInfoVO);
		contractVO.setContCompId(contCompId);
		contractVO.setContractCode(contractInfoVO.getContractCode());
		contractVO.setContractId(contractId);
		contractVO.setName(contractStructure.getName());
		contractVO.setCurrency(contractInfoVO.getMainCurrency());
		// Get settlement days
		TRiContAccount account = accountDao.getEntityByContCompId(contractId);
		if (null != account) {
			contractVO.setSettlementDays(account.getSettlementDays());
		}
		return contractVO;
	}

	public SectionModel getSectionModel(Long sectionId) {
		SectionModel model = new SectionModel();
		TRiContSectionInfo section = sectionInfoDao.load(sectionId);
		BeanUtils.copyProperties(model, section);
		model.setSectionId(section.getContCompId());
		model.setCoB(DataTypeConvertor.transStringToSelectTree(section.getCoB()));
		model.setUwArea(DataTypeConvertor.transStringToSelectTree(section.getUwArea()));
		TRiContractStructure contractStructure = structureDao.load(sectionId);
		model.setSectionName(contractStructure.getFullName());
		return model;
	}

	@Override
	public List<Long> getContractUWYearByContractCode(String contractCode) {
		List<Long> uwYearList = contractInfoDao.getUWYearList(contractCode);
		return uwYearList;
	}

	@Override
	public Object getPremiumModelBySectionId(Long sectionId) throws Exception {
		Long contractId = structureDao.load(sectionId).getParentId();
		ContractPremiumModel premiumModel = pricingDS.generateAccountingPremiumModel(contractId, sectionId);
		return premiumModel;
	}

	@Override
	public boolean needPostingGL(Long contCompId) {
		TRiContractStructure structure = structureDao.load(contCompId);
		Long contractId = structure.getContCompId();
		if (ContractCst.SECTION_LEVEL.equals(structure.getType())) {
			contractId = structure.getParentId();
		} else if (ContractCst.SUB_SECTION_LEVEL.equals(structure.getType())) {
			contractId = structureDao.load(structure.getParentId()).getParentId();
		}
		return claimDao.needPostingContractClaim(contractId);
	}

	@Override
	public List<Long> getRetrocessionSectionIdList(Long contCompId) {
		return retroDao.getRetroSectionId(contCompId);
	}

	@Override
	public List<TreeModel> getContractStructure(ContractModel contractModel) throws Exception {
		ContractVO condition = new ContractVO();
		BeanUtils.copyProperties(condition, contractModel);
		List<TRiContractInfo> contractInfoList = contractInfoDao.getCurrentPage(condition, 1, 1);
		if (null != contractInfoList && contractInfoList.size() > 0) {
			Long contractId = contractInfoList.get(0).getContCompId();
			List<TreeModel> contractStructureList = new ArrayList<TreeModel>();
			TreeModel contractStructure = getChildrenItemsByParentId(contractId);
			contractStructureList.add(contractStructure);
			return contractStructureList;
		}
		return null;
	}

	/**
	 * 
	 */
	@Override
	public void resetTheCleanCutFlag(Long contCompId, boolean isCleanCut) {
		TRiContractStructure structure = structureDao.load(contCompId);
		structure.setIsCleanCut(isCleanCut);
		structureDao.save(structure);
	}

	public boolean isCleanCutContractSection(Long contCompId) {
		TRiContractStructure structure = structureDao.load(contCompId);
		return structure.getIsCleanCut() == null ? false : structure.getIsCleanCut().booleanValue();
	}

	@Override
	public String getContractCodeById(Long contCompId) throws Exception {
		TRiContractInfo contInfo = contractInfoDao.load(contCompId);
		if (contInfo != null) {
			return contInfo.getContractCode();
		}
		return null;
	}

	@Override
	public void actualizeByContractId(Long contractId) throws Exception {
		List<Long> sectionIdList = structureDao.getChildrenIdList(contractId);
		if (CollectionUtils.isNotEmpty(sectionIdList)) {
			for (Long sectionId : sectionIdList) {
				TRiContPricing entity = new TRiContPricing();
				try {
					TRiContPricing pricing = pricingDao.loadByContCompId(sectionId);
					BeanUtils.copyProperties(entity, pricing);
					entity.setTRiContPricingItems(pricing.getTRiContPricingItems());
					entity.setActualized(ReInsuranceCst.BASE_YES);
					pricingDao.save(entity);
				} catch (NoResultException e) {
					continue;
				}
			}
		}
	}

	@Override
	public List<Long> getLevelContCompIdList(Long contCompId, String level) {
		List<Long> compIdlist = new ArrayList<Long>();
		TRiContractStructure structure = structureDao.load(contCompId);
		if (level.equals(structure.getType())) {
			compIdlist.add(contCompId);
		} else if (level.equals(ContractCst.CONTRACT_LEVEL)) {
			if (structure.getType().equals(ContractCst.SECTION_LEVEL)) {
				compIdlist.add(structure.getParentId());
			} else if (structure.getType().equals(ContractCst.SUB_SECTION_LEVEL)) {
				compIdlist.add(structureDao.load(structure.getParentId()).getParentId());
			}
		} else if (level.equals(ContractCst.SECTION_LEVEL)) {
			if (structure.getType().equals(ContractCst.SUB_SECTION_LEVEL)) {
				compIdlist.add(structure.getParentId());
			} else if (structure.getType().equals(ContractCst.CONTRACT_LEVEL)) {
				compIdlist.addAll(structureDao.getChildrenIdList(contCompId));
			}
		} else {
			if (structure.getType().equals(ContractCst.SECTION_LEVEL)) {
				compIdlist.addAll(structureDao.getChildrenIdList(contCompId));
			} else if (structure.getType().equals(ContractCst.CONTRACT_LEVEL)) {
				List<Long> sectionIds = structureDao.getChildrenIdList(contCompId);
				for (Long sectionId : sectionIds) {
					compIdlist.addAll(structureDao.getChildrenIdList(sectionId));
				}
			}
		}
		return compIdlist;
	}

	@Override
	public boolean isInforceOrCancelledContract(Long contCompId) throws Exception {
		TRiContractStructure contractStructure = structureDao.load(contCompId);
		Long contractId = null;
		if (ContractCst.CONTRACT_LEVEL.equals(contractStructure.getType())) {
			contractId = contractStructure.getContCompId();
		} else if (ContractCst.SECTION_LEVEL.equals(contractStructure.getType())) {
			TRiContractStructure contractLevel = structureDao.load(contractStructure.getParentId());
			contractId = contractLevel.getContCompId();
		} else if (ContractCst.SUB_SECTION_LEVEL.equals(contractStructure.getType())) {
			TRiContractStructure sectionLevel = structureDao.load(contractStructure.getParentId());
			TRiContractStructure contractLevel = structureDao.load(sectionLevel.getParentId());
			contractId = contractLevel.getContCompId();
		}

		TRiContractInfo entity = contractInfoDao.load(contractId);
		return ContractCst.CONTRACT_STATUS_INFORCE.equals(entity.getLatestStatus())
				|| ContractCst.CONTRACT_STATUS_CANCELLED.equals(entity.getLatestStatus());
	}

	@Override
	public Long getSectionIdByContractCodeAndSectionInfo(String contractCode, Long uwYear, Date reinsStarting,
			String sectionName, String ShareType) throws Exception {
		Long sectionId = null;
		TRiContractInfo entity = contractInfoDao.getValidEntity(contractCode, uwYear, reinsStarting);
		if (null != entity) {
			boolean isValid = false;
			if (entity.getLatestStatus().equals(ContractCst.CONTRACT_STATUS_INFORCE)) {
				isValid = true;
			} else {
				List<TRiContOperation> hisList = operationDao.loadContractForLog(entity.getContCompId());
				for (TRiContOperation item : hisList) {
					if (item.getStatus().equals(ContractCst.CONTRACT_STATUS_INFORCE)) {
						isValid = true;
						break;
					}
				}
			}

			if (isValid) {
				// section level
				List<TRiContractStructure> secStructureList = structureDao.getChildList(entity.getContCompId());
				for (TRiContractStructure sec : secStructureList) {
					TRiContSectionInfo section = sectionInfoDao.load(sec.getContCompId());
					if (section.getShareType().equals(ShareType)) {
						if (section.getSectionName().equals(sectionName)) {
							if (null == sectionId) {
								sectionId = section.getContCompId();
							} else {
								throw new NonUniqueResultException("result returns more than one elements");
							}
						}
						// subsection level
						List<TRiContractStructure> subsecStructureList = structureDao.getChildList(sec.getContCompId());
						if (CollectionUtils.isNotEmpty(subsecStructureList)) {
							for (TRiContractStructure subsec : subsecStructureList) {
								if (subsec.getName().equals(sectionName)) {
									if (null == sectionId) {
										sectionId = subsec.getContCompId();
									} else {
										throw new NonUniqueResultException("result returns more than one elements");
									}
								}
							}
						}
					}
				}
			}
		}
		return sectionId;
	}
}
