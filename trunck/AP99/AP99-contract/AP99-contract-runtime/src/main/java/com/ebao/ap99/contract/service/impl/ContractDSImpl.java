package com.ebao.ap99.contract.service.impl;

import java.io.File;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.restlet.engine.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.ebao.ap99.arap.service.CurrencyExchangeService;
import com.ebao.ap99.contract.convertor.BusinessConditionModelConvertor;
import com.ebao.ap99.contract.convertor.ContractModelConvertor;
import com.ebao.ap99.contract.convertor.ContractSectionModelConvertor;
import com.ebao.ap99.contract.convertor.ContractSubsectionModelConvertor;
import com.ebao.ap99.contract.dao.TRiContCancelDao;
import com.ebao.ap99.contract.dao.TRiContPricingDao;
import com.ebao.ap99.contract.dao.TRiContRetroDao;
import com.ebao.ap99.contract.dao.TRiContRetroLogDao;
import com.ebao.ap99.contract.dao.TRiContSectionInfoDao;
import com.ebao.ap99.contract.dao.TRiContSectionInfoLogDao;
import com.ebao.ap99.contract.dao.TRiContSubsectionInfoDao;
import com.ebao.ap99.contract.dao.TRiContSubsectionInfoLogDao;
import com.ebao.ap99.contract.dao.TRiContractInfoDao;
import com.ebao.ap99.contract.dao.TRiContractInfoLogDao;
import com.ebao.ap99.contract.dao.TRiContractStructureDao;
import com.ebao.ap99.contract.dao.TRiContractStructureLogDao;
import com.ebao.ap99.contract.entity.TRiContCancel;
import com.ebao.ap99.contract.entity.TRiContClaimItem;
import com.ebao.ap99.contract.entity.TRiContDeductionsAttach;
import com.ebao.ap99.contract.entity.TRiContDeductionsCarried;
import com.ebao.ap99.contract.entity.TRiContDeductionsItem;
import com.ebao.ap99.contract.entity.TRiContLimitEvent;
import com.ebao.ap99.contract.entity.TRiContLimitItem;
import com.ebao.ap99.contract.entity.TRiContPremiumItem;
import com.ebao.ap99.contract.entity.TRiContPricing;
import com.ebao.ap99.contract.entity.TRiContPricingItem;
import com.ebao.ap99.contract.entity.TRiContReinstateItem;
import com.ebao.ap99.contract.entity.TRiContRetro;
import com.ebao.ap99.contract.entity.TRiContSectionInfo;
import com.ebao.ap99.contract.entity.TRiContSubsectionInfo;
import com.ebao.ap99.contract.entity.TRiContractInfo;
import com.ebao.ap99.contract.entity.TRiContractStructure;
import com.ebao.ap99.contract.model.BusinessConditionVO;
import com.ebao.ap99.contract.model.ContractSectionVO;
import com.ebao.ap99.contract.model.ContractSubsectionVO;
import com.ebao.ap99.contract.model.ContractVO;
import com.ebao.ap99.contract.model.CurrencyVO;
import com.ebao.ap99.contract.model.DeductionsAttachVO;
import com.ebao.ap99.contract.model.DeductionsItemVO;
import com.ebao.ap99.contract.model.LimitEventVO;
import com.ebao.ap99.contract.model.LimitItemVO;
import com.ebao.ap99.contract.model.LinkContractVO;
import com.ebao.ap99.contract.model.PremiumItemVO;
import com.ebao.ap99.contract.model.ReinstateItemVO;
import com.ebao.ap99.contract.model.RetrocessionItemVO;
import com.ebao.ap99.contract.model.TerminationVO;
import com.ebao.ap99.contract.model.BusinessModel.BusinessConditionBO;
import com.ebao.ap99.contract.model.BusinessModel.ContractBO;
import com.ebao.ap99.contract.model.BusinessModel.CurrencyBO;
import com.ebao.ap99.contract.model.BusinessModel.DeductionsBO;
import com.ebao.ap99.contract.model.BusinessModel.LimitBO;
import com.ebao.ap99.contract.model.BusinessModel.PremiumBO;
import com.ebao.ap99.contract.model.BusinessModel.PricingEstimateBO;
import com.ebao.ap99.contract.model.BusinessModel.SectionBO;
import com.ebao.ap99.contract.model.BusinessModel.ShareBO;
import com.ebao.ap99.contract.model.BusinessModel.SubsectionBO;
import com.ebao.ap99.contract.model.PDFModel.AreaVO;
import com.ebao.ap99.contract.model.PDFModel.AttachmentVO;
import com.ebao.ap99.contract.model.PDFModel.ContractPDFVO;
import com.ebao.ap99.contract.model.PDFModel.ForecastEstimateVO;
import com.ebao.ap99.contract.model.PDFModel.InstallmentDatesVO;
import com.ebao.ap99.contract.model.PDFModel.LimitsDeductiblesVO;
import com.ebao.ap99.contract.model.PDFModel.PerilsLimitsVO;
import com.ebao.ap99.contract.model.PDFModel.PremiumLimitsVO;
import com.ebao.ap99.contract.model.PDFModel.PremiumsVO;
import com.ebao.ap99.contract.model.PDFModel.ResultConditionDepVO;
import com.ebao.ap99.contract.model.PDFModel.ResultConditionIndepVO;
import com.ebao.ap99.contract.service.BusinessConditionDS;
import com.ebao.ap99.contract.service.ContractDS;
import com.ebao.ap99.contract.service.ContractHomeDS;
import com.ebao.ap99.contract.service.ContractPricingDS;
import com.ebao.ap99.contract.service.ContractRetrocessionDS;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.contract.util.ContractCst;
import com.ebao.ap99.file.entity.TDocument;
import com.ebao.ap99.file.service.DocumentService;
import com.ebao.ap99.parent.DataTypeConvertor;
import com.ebao.ap99.parent.constant.NumberingFactor;
import com.ebao.ap99.parent.constant.NumberingType;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.ap99.parent.service.UserDS;
import com.ebao.ap99.partner.service.PartnerAPI;
import com.ebao.unicorn.platform.data.code.CodeService;
import com.ebao.unicorn.platform.foundation.context.SpringContextUtils;
import com.ebao.unicorn.platform.foundation.numbering.NumberingService;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class ContractDSImpl implements ContractDS {
	private static Logger logger = LoggerFactory.getLogger();
	@Autowired
	private TRiContractInfoDao contractInfoDao;
	@Autowired
	private ContractService contractService;
	@Autowired
	private TRiContractStructureDao contractStructureDao;
	@Autowired
	private NumberingService numService;
	@Autowired
	private TRiContCancelDao cancelDao;
	@Autowired
	private BusinessConditionDS conditionDS;

	@Autowired
	private TRiContractStructureDao structureDao;

	@Autowired
	private TRiContractStructureLogDao structureLogDao;

	@Autowired
	private TRiContSectionInfoDao sectionInfoDao;

	@Autowired
	private TRiContSubsectionInfoDao subsectionInfoDao;

	@Autowired
	private TRiContRetroDao retrocessionDao;

	@Autowired
	private TRiContRetroLogDao retrocessionLogDao;

	@Autowired
	private TRiContractInfoLogDao contractInfoLogDao;

	@Autowired
	private TRiContSectionInfoLogDao sectionInfoLogDao;

	@Autowired
	private TRiContSubsectionInfoLogDao subsectionInfoLogDao;

	@Autowired
	private TRiContPricingDao pricingDao;

	@Autowired
	private ContractHomeDS contractHomeDS;

	@Autowired
	private CurrencyExchangeService currencyExchangeService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private CodeService codeTableService;
	@Autowired
	private UserDS userDS;
	@Autowired
	private PartnerAPI partnerAPI;

	@Autowired
	private BusinessConditionDS businessDS;
	@Autowired
	private ContractPricingDS pricingDS;
	@Autowired
	private ContractRetrocessionDS retrocessionDS;

	@Override
	public List<String> getLinkedListByCedent(String cedentId) {
		if (StringUtils.isNullOrEmpty(cedentId)) {
			return null;
		}
		List<String> linkList = contractInfoDao.getLinkedListByCedentId(cedentId);
		return linkList;
	}

	@Override
	public List<LinkContractVO> getLinkContractList(String linkName, String contractCode, Long uwYear) {
		List<LinkContractVO> linkContractList = new ArrayList<LinkContractVO>();

		if (StringUtils.isNullOrEmpty(linkName)) {
			return linkContractList;
		}

		List<TRiContractInfo> allContractInfo = contractInfoDao.getContractInfoByContractCode(contractCode);
		if (null != allContractInfo && allContractInfo.size() != 0) {
			for (TRiContractInfo contractInfo : allContractInfo) {
				LinkContractVO contractVO = new LinkContractVO();
				contractVO.setUwYear(contractInfo.getUwYear());
				if (contractInfo.getUwYear().longValue() == uwYear.longValue()) {
					contractVO.setLinkName(linkName);
				} else {
					contractVO.setLinkName(contractInfo.getLinkName());
				}
				List<Long> compIdList = contractInfoDao.getCompIdByLinkNameUWYear(contractVO.getLinkName(),
						contractVO.getUwYear(), contractInfo.getCedent());
				if (null == compIdList || compIdList.size() == 0) {
					TreeModel vo = contractService.getChildrenItemsByParentId(contractInfo.getContCompId());
					contractVO.getStructureVO().add(vo);
				} else {
					for (Long compId : compIdList) {
						TreeModel vo = contractService.getChildrenItemsByParentId(compId);
						contractVO.getStructureVO().add(vo);
					}
				}
				linkContractList.add(contractVO);
			}
		} else {
			LinkContractVO contractVO = new LinkContractVO();
			contractVO.setLinkName(linkName);
			contractVO.setUwYear(uwYear);
			TreeModel vo = new TreeModel();
			vo.setId(0l);
			vo.setText(contractCode);
			contractVO.getStructureVO().add(vo);
			linkContractList.add(contractVO);
		}
		return linkContractList;
	}

	@Override
	public void saveOrUpdate(TRiContractStructure contractStructure) {
		contractStructure = contractStructureDao.save(contractStructure);
	}

	@Override
	public void deleteSectionInfo(Long contCompId) {

	}

	@Override
	public String getContractCode() throws Exception {
		Date date = AppContext.getSysDate();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String formattedDate = simpleDateFormat.format(date);
		Map<String, String> factors = new HashMap<String, String>();
		factors.put(NumberingFactor.TRANS_YEAR, formattedDate.substring(2, 4));
		factors.put(NumberingFactor.TRANS_MONTH, formattedDate.substring(4, 6));
		// factors.put(NumberingFactor.TRANS_DAY, formattedDate.substring(6,
		// 8));

		String contractNumber = null;
		try {
			contractNumber = numService.generateNumber(NumberingType.RI_CONTRACT_NUMBER, factors);
		} catch (Exception e) {
			logger.error("generate contract number error.");
			throw new Exception("generate contract number error.", e);
		}

		logger.info("getContractCode=>" + contractNumber);
		return contractNumber;
	}

	public ContractBO loadContractBOForView(Long contCompId) throws Exception {
		return this.loadContractBO(contCompId, false);
	}

	public SectionBO loadSectionBOForView(Long sectionId) throws Exception {
		return this.loadSectionBO(sectionId, false);
	}

	public SubsectionBO loadSubSectionBOForView(Long subsectionId) throws Exception {
		return this.loadSubSectionBO(subsectionId, false);
	}

	@Override
	public ContractBO loadContractBO(Long contCompId, boolean includeDetail) throws Exception {
		ContractBO contractBO = new ContractBO();
		TRiContractInfo contractInfo = contractInfoDao.load(contCompId);
		BeanUtils.copyProperties(contractBO, contractInfo);
		if (null != contractInfo.getTRiContractMoreInfo()) {
			BeanUtils.copyProperties(contractBO.getMoreInfoBO(), contractInfo.getTRiContractMoreInfo());
		}
		if (null != contractInfo.getTRiContClaim()) {
			BeanUtils.copyProperties(contractBO.getClaimInfoBO(), contractInfo.getTRiContClaim());
			List<TRiContClaimItem> claimItems = contractInfo.getTRiContClaim().getTRiContClaimItems();
			if (null != claimItems && claimItems.size() > 0) {
				contractBO.getClaimInfoBO().setTRiContClaimItems(claimItems.stream().collect(Collectors.toList()));
			}
		}
		if (null != contractInfo.getTRiContAccount()) {
			BeanUtils.copyProperties(contractBO.getAccountingBO(), contractInfo.getTRiContAccount());
		}
		if (null != contractInfo.getTRiContractAreaperil()) {
			BeanUtils.copyProperties(contractBO.getAreaperilBO(), contractInfo.getTRiContractAreaperil());
		}
		TRiContCancel cancelInfo = cancelDao.loadByContCompId(contCompId);
		BeanUtils.copyProperties(contractBO.getCancelBO(), cancelInfo);
		// if (includeDetail) {
		// contractBO.setBusinessBO(conditionDS.loadBusinessConditionBO(contCompId));
		// }
		List<Long> sectionIds = structureDao.getChildrenIdList(contCompId);
		for (Long sectionId : sectionIds) {
			contractBO.getSectionList().add(loadSectionBO(sectionId, includeDetail));
		}

		return contractBO;
	}

	@Override
	public SectionBO loadSectionBO(Long sectionId, boolean includeDetail) throws Exception {
		SectionBO sectionBO = new SectionBO();
		TRiContSectionInfo sectionInfo = sectionInfoDao.load(sectionId);
		BeanUtils.copyProperties(sectionBO, sectionInfo);
		// sectionBO.setValidClaim(claimService.ifHasClaim(sectionId));
		// sectionBO.setValidAccount(soaService.validSoaBySectionId(sectionId));
		sectionBO.setHasInforce(checkTheEntityHasBennInforce(sectionId));
		sectionBO.setBusinessBO(conditionDS.loadBusinessConditionBO(sectionId));
		if (sectionBO.getBusinessBO() != null) {
			// main currency
			if (CollectionUtils.isNotEmpty(sectionBO.getBusinessBO().getCurrencyBO())) {
				for (CurrencyBO item : sectionBO.getBusinessBO().getCurrencyBO()) {
					if (item.getMainCurrency().equals("true")) {
						sectionBO.setMainCurrency(item.getCurrencyType());
					}
				}
			}
			// our share
			if (sectionBO.getBusinessBO().getShareBO() != null) {
				sectionBO.setOurShare(conditionDS.getOurShare(sectionBO.getBusinessBO().getShareBO()));
			}
			// premium
			if (sectionBO.getBusinessBO().getPremiumBO() != null) {
				sectionBO.setPremium(conditionDS.getPremium(sectionBO.getBusinessBO().getCurrencyBO(),
						sectionBO.getBusinessBO().getPremiumBO()));
			}
			// deductions
			if (sectionBO.getBusinessBO().getDeductionBO() != null) {
				sectionBO.setDeductions(
						conditionDS.getDeductions(sectionBO.getBusinessBO().getDeductionBO(), sectionBO.getPremium()));
			}
		}
		if (includeDetail) {
			List<TRiContRetro> retroList = retrocessionDao.getRetrocessionList(sectionId);
			List<TRiContRetro> coveredList = retrocessionDao.getCoveredSectionList(sectionId);
			retroList.addAll(coveredList);
			if (null != retroList) {
				sectionBO.setRetroList(retroList);
			}

			TRiContPricing pricing = pricingDao.loadByContCompId(sectionId);
			PricingEstimateBO pricingBO = new PricingEstimateBO();
			if (pricing != null) {
				BeanUtils.copyProperties(pricingBO, pricing);
				pricingBO.setTRiContPricingItems(pricing.getTRiContPricingItems());
			}
			sectionBO.setPricingBO(pricingBO);
		}
		List<Long> subSectionIds = structureDao.getChildrenIdList(sectionId);
		for (Long subsectionId : subSectionIds) {
			sectionBO.getSubsectionList().add(loadSubSectionBO(subsectionId, includeDetail));
		}
		return sectionBO;
	}

	@Override
	public SubsectionBO loadSubSectionBO(Long subsectionId, boolean includeDetail) throws Exception {
		SubsectionBO subsectionBO = new SubsectionBO();
		TRiContSubsectionInfo subsectionInfo = subsectionInfoDao.load(subsectionId);
		if (subsectionInfo != null) {
			BeanUtils.copyProperties(subsectionBO, subsectionInfo);
			// subsectionBO.setValidClaim(claimService.ifHasClaim(subsectionId));
			// subsectionBO.setValidAccount(soaService.validSoaBySectionId(subsectionId));
			subsectionBO.setHasInforce(checkTheEntityHasBennInforce(subsectionId));
			subsectionBO.setBusinessBO(conditionDS.loadBusinessConditionBO(subsectionId));
			if (subsectionBO.getBusinessBO() != null) {
				// main currency
				if (CollectionUtils.isNotEmpty(subsectionBO.getBusinessBO().getCurrencyBO())) {
					for (CurrencyBO item : subsectionBO.getBusinessBO().getCurrencyBO()) {
						if (item.getMainCurrency().equals("true")) {
							subsectionBO.setMainCurrency(item.getCurrencyType());
						}
					}
				}
				// our share
				if (subsectionBO.getBusinessBO().getShareBO() != null) {
					ShareBO shareBO = subsectionBO.getBusinessBO().getShareBO();
					subsectionBO.setOurShare(conditionDS.getOurShare(shareBO));
				}
				// premium
				if (subsectionBO.getBusinessBO().getPremiumBO() != null) {
					subsectionBO.setPremium(conditionDS.getPremium(subsectionBO.getBusinessBO().getCurrencyBO(),
							subsectionBO.getBusinessBO().getPremiumBO()));
				}
				// deductions
				if (subsectionBO.getBusinessBO().getDeductionBO() != null) {
					subsectionBO.setDeductions(conditionDS.getDeductions(subsectionBO.getBusinessBO().getDeductionBO(),
							subsectionBO.getPremium()));
				}
			}
			if (includeDetail) {
				List<TRiContRetro> tRiContRetroList = retrocessionDao.getRetrocessionList(subsectionId);
				if (null != tRiContRetroList) {
					subsectionBO.setRetroList(tRiContRetroList);
				}
			}
		}
		return subsectionBO;
	}

	public ContractBO generateStructureForBO(ContractBO bo) {
		TRiContractStructure structureBO = new TRiContractStructure();
		structureBO.setContCompId(bo.getContCompId());
		structureBO.setName(bo.getContractCode());
		Long uwYear = bo.getUwYear();
		String fullName = partnerAPI.loadPartnerNameByPartnerCode(bo.getCedent());
		if (bo.getBroker() != null) {
			fullName += ContractCst.STRUCTURE_SEPARATOR + partnerAPI.loadPartnerNameByPartnerCode(bo.getBroker());
		}
		structureBO.setFullName(bo.getContractCode() + ContractCst.STRUCTURE_SEPARATOR + uwYear
				+ ContractCst.STRUCTURE_SEPARATOR + fullName);
		structureBO.setType(ContractCst.CONTRACT_LEVEL);
		structureBO = structureDao.save(structureBO);
		bo.setContCompId(structureBO.getContCompId());
		for (SectionBO sectionBO : bo.getSectionList()) {
			sectionBO.setParentId(structureBO.getContCompId());
			TRiContractStructure sectionStructure = new TRiContractStructure();
			sectionStructure.setContCompId(sectionBO.getContCompId());
			sectionStructure.setName(sectionBO.getSectionName());
			sectionStructure.setType(ContractCst.SECTION_LEVEL);
			sectionStructure.setParentId(structureBO.getContCompId());
			sectionStructure.setFullName(
					bo.getContractCode() + ContractCst.STRUCTURE_SEPARATOR + uwYear + ContractCst.STRUCTURE_SEPARATOR
							+ sectionBO.getSectionName() + ContractCst.STRUCTURE_SEPARATOR + fullName);
			sectionStructure = structureDao.save(sectionStructure);
			sectionBO.setContCompId(sectionStructure.getContCompId());
			for (SubsectionBO subSectionBO : sectionBO.getSubsectionList()) {
				subSectionBO.setParentId(sectionBO.getContCompId());
				TRiContractStructure subSectionStructure = new TRiContractStructure();
				subSectionStructure.setContCompId(subSectionBO.getContCompId());
				subSectionStructure.setName(subSectionBO.getSubsectionName());
				subSectionStructure.setType(ContractCst.SUB_SECTION_LEVEL);
				subSectionStructure.setParentId(sectionBO.getContCompId());
				subSectionStructure.setFullName(bo.getContractCode() + ContractCst.STRUCTURE_SEPARATOR + uwYear
						+ ContractCst.STRUCTURE_SEPARATOR + sectionBO.getSectionName() + ContractCst.STRUCTURE_SEPARATOR
						+ subSectionBO.getSubsectionName() + ContractCst.STRUCTURE_SEPARATOR + fullName);
				subSectionStructure = structureDao.save(subSectionStructure);
				subSectionBO.setContCompId(subSectionStructure.getContCompId());
			}
		}
		return bo;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public void saveContractBO(ContractBO bo, boolean includeDetail) throws Exception {
		// Step1: save current structure
		bo = generateStructureForBO(bo);
		// Step2: save contract info
		TRiContractInfo contractInfo = new TRiContractInfo();
		ContractModelConvertor.convertBOToContractEntity(bo, contractInfo);
		contractInfo = contractInfoDao.save(contractInfo);
		BeanUtils.copyProperties(bo, contractInfo);
		bo.getClaimInfoBO().setTRiContClaimItems(contractInfo.getTRiContClaim().getTRiContClaimItems());
		if (includeDetail) {
			// Step3: save contract business info
			// if (bo.getBusinessBO() != null) {
			// bo.getBusinessBO().setContCompId(bo.getContCompId());
			// conditionDS.saveBusinessBO(bo.getBusinessBO());
			// }
			// Step4: save Section info
			for (SectionBO sectionBO : bo.getSectionList()) {
				this.saveSectionInfo(sectionBO, includeDetail);
			}
		}
	}

	public void saveSectionInfo(SectionBO bo, boolean includeDetail) throws Exception {
		TRiContSectionInfo sectionInfo = new TRiContSectionInfo();
		BeanUtils.copyProperties(sectionInfo, bo);
		sectionInfo.setContCompId(contractHomeDS.saveStructure(bo.getParentId(), sectionInfo.getContCompId(),
				sectionInfo.getSectionName(), ContractCst.SECTION_LEVEL));
		sectionInfoDao.save(sectionInfo);
		bo.setContCompId(sectionInfo.getContCompId());
		if (bo.getBusinessBO() != null) {
			bo.getBusinessBO().setContCompId(bo.getContCompId());
			conditionDS.saveBusinessBO(bo.getBusinessBO());
		}
		if (includeDetail) {
			if (bo.getPricingBO() != null) {
				bo.getPricingBO().setContCompId(sectionInfo.getContCompId());
				pricingDS.savePricingEstimateBO(bo.getPricingBO());
			}
			for (TRiContRetro retro : bo.getRetroList()) {
				retro.setCompId(bo.getContCompId());
				retro = retrocessionDao.save(retro);
			}
			for (SubsectionBO sectionBO : bo.getSubsectionList()) {
				this.saveSubsectionInfo(sectionBO, includeDetail);
			}
		}
		// artf218424
		this.updateContRetroInfo(sectionInfo.getContCompId());
	}

	public void saveSubsectionInfo(SubsectionBO bo, boolean includeDetail) throws Exception {
		TRiContSubsectionInfo sectionInfo = new TRiContSubsectionInfo();
		BeanUtils.copyProperties(sectionInfo, bo);
		sectionInfo.setContCompId(contractHomeDS.saveStructure(bo.getParentId(), sectionInfo.getContCompId(),
				sectionInfo.getSubsectionName(), ContractCst.SUB_SECTION_LEVEL));
		subsectionInfoDao.save(sectionInfo);
		bo.setContCompId(sectionInfo.getContCompId());

		if (bo.getBusinessBO() != null) {
			bo.getBusinessBO().setContCompId(bo.getContCompId());
			conditionDS.saveBusinessBO(bo.getBusinessBO());
		}
		if (includeDetail) {
			if (null != bo.getRetroList() && bo.getRetroList().size() != 0) {
				for (TRiContRetro retro : bo.getRetroList()) {
					retro.setCompId(bo.getContCompId());
					retro = retrocessionDao.save(retro);
				}
			}
		}
		// artf218424
		this.updateContRetroInfo(sectionInfo.getContCompId());

	}

	@SuppressWarnings("unused")
	private void saveContractBOByXML(ContractBO bo) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new File("src/main/resources/META-INF/ap99/mapping.xml"));
		Element foo = doc.getRootElement();
		List<Element> allChildren = foo.getChildren();
		String className = bo.getClass().getName();
		String xmlName = "com.ebao.ap99.contract.model.BusinessModel." + foo.getName();
		if (className.equals(xmlName)) {
			String entity = foo.getAttributeValue("entity");
			Object obj = Class.forName(entity).newInstance();
			BeanUtils.copyProperties(obj, bo);
			String entityDao = foo.getAttributeValue("entityDao");
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			Object entityDaoObj = wac.getBean(entityDao);
			Method m = entityDaoObj.getClass().getMethod("save", Class.forName(entity));
			m.invoke(entityDaoObj, obj);
			BeanUtils.copyProperties(bo, obj);
		}
	}

	@Override
	public ContractBO loadContractBOForLog(Long contCompId, Long operateId, String status, boolean isRevert)
			throws Exception {
		ContractBO contractBO = new ContractBO();
		ContractVO contractVO = contractInfoLogDao.getContractVOForLog(contCompId, operateId, status);
		ContractModelConvertor.convertVOToBO(contractBO, contractVO);

		/**
		 * remove contract level's businessCondition info
		 */
		// BusinessConditionBO bcBO = new BusinessConditionBO();
		// BusinessConditionVO bcVO =
		// contractInfoLogDao.getBusinessConditionVOForLog(contCompId,
		// contractVO.getOperateId());
		// BusinessConditionModelConvertor.convertBusinessVOToBO(bcBO, bcVO);
		// contractBO.setBusinessBO(bcBO);

		List<Long> sectionIdsForLog = structureLogDao.getChildrenIdList(contCompId, contractVO.getOperateId());
		if (isRevert) {
			// Set deleteSectionList with section which is not in log
			// information.
			List<Long> sectionIds = structureDao.getChildrenIdList(contCompId);
			if (CollectionUtils.isNotEmpty(sectionIds)) {
				List<Long> deleteSectionList = new ArrayList<Long>();
				for (Long sectionId : sectionIds) {
					if (!sectionIdsForLog.contains(sectionId)) {
						deleteSectionList.add(sectionId);
					}
				}
				contractBO.setDeleteSectionList(deleteSectionList);
			}
		}

		for (Long sectionId : sectionIdsForLog) {
			contractBO.getSectionList().add(loadSectionBOForLog(sectionId, contractVO.getOperateId(),
					contractVO.getContractNature(), isRevert));
		}

		return contractBO;
	}

	@Override
	public SectionBO loadSectionBOForLog(Long sectionId, Long operateId, String contractNature, boolean isRevert)
			throws Exception {
		SectionBO sectionBO = new SectionBO();
		ContractSectionVO sectionInVO = sectionInfoLogDao.getContractSectionVOForLog(sectionId, operateId);
		ContractSectionModelConvertor.convertVOToBO(sectionBO, sectionInVO);

		BusinessConditionBO bcBO = new BusinessConditionBO();
		BusinessConditionVO bcVO = contractInfoLogDao.getBusinessConditionVOForLog(sectionId, operateId,
				contractNature);
		if (isRevert) {
			this.setDeleteListForBusinessConditionVO(bcVO, sectionId);
		}
		BusinessConditionModelConvertor.convertBusinessVOToBO(bcBO, bcVO);
		if (isRevert) {
			BusinessConditionModelConvertor.convertBusinessVOToBOForDeductionsDetail(bcBO, bcVO);
		}
		sectionBO.setBusinessBO(bcBO);

		List<Long> subsectionIdsForLog = structureLogDao.getChildrenIdList(sectionId, operateId);
		if (isRevert) {
			List<TRiContRetro> retroList = retrocessionLogDao.getRetrocessionListForLog(sectionId, operateId);
			List<TRiContRetro> coveredList = retrocessionLogDao.getCoveredSectionListForLog(sectionId, operateId);
			retroList.addAll(coveredList);
			if (null != retroList) {
				sectionBO.setRetroList(retroList);
			}

			sectionBO.setPricingBO(null);

			// Set deleteSubsectionList with subsection which is not in log
			// information.
			List<Long> subsectionIds = structureDao.getChildrenIdList(sectionId);
			if (CollectionUtils.isNotEmpty(subsectionIds)) {
				List<Long> deleteSubsectionList = new ArrayList<Long>();
				for (Long subsectionId : subsectionIds) {
					if (!subsectionIdsForLog.contains(subsectionId)) {
						deleteSubsectionList.add(subsectionId);
					}
				}
				sectionBO.setDeleteSubsectionList(deleteSubsectionList);
			}
		} else {
			if (sectionBO.getBusinessBO() != null) {
				// main currency
				if (CollectionUtils.isNotEmpty(sectionBO.getBusinessBO().getCurrencyBO())) {
					for (CurrencyBO item : sectionBO.getBusinessBO().getCurrencyBO()) {
						if (item.getMainCurrency().equals("true")) {
							sectionBO.setMainCurrency(item.getCurrencyType());
						}
					}
				}
				// our share
				if (sectionBO.getBusinessBO().getShareBO() != null) {
					sectionBO.setOurShare(conditionDS.getOurShare(sectionBO.getBusinessBO().getShareBO()));
				}
				// premium
				if (sectionBO.getBusinessBO().getPremiumBO() != null) {
					sectionBO.setPremium(conditionDS.getPremium(sectionBO.getBusinessBO().getCurrencyBO(),
							sectionBO.getBusinessBO().getPremiumBO()));
				}
				// deductions
				if (sectionBO.getBusinessBO().getDeductionBO() != null) {
					sectionBO.setDeductions(conditionDS.getDeductions(sectionBO.getBusinessBO().getDeductionBO(),
							sectionBO.getPremium()));
				}
			}
		}

		for (Long subsectionId : subsectionIdsForLog) {
			sectionBO.getSubsectionList()
					.add(loadSubsectionBOForLog(subsectionId, operateId, contractNature, isRevert));
		}

		return sectionBO;
	}

	@Override
	public SubsectionBO loadSubsectionBOForLog(Long subsectionId, Long operateId, String contractNature,
			boolean isRevert) throws Exception {
		SubsectionBO subsectionBO = new SubsectionBO();
		ContractSubsectionVO subsectionInVO = subsectionInfoLogDao.getContractSubsectionVOForLog(subsectionId,
				operateId);
		ContractSubsectionModelConvertor.convertVOToBO(subsectionBO, subsectionInVO);

		BusinessConditionBO bcBO = new BusinessConditionBO();
		BusinessConditionVO bcVO = contractInfoLogDao.getBusinessConditionVOForLog(subsectionId, operateId,
				contractNature);
		if (isRevert) {
			this.setDeleteListForBusinessConditionVO(bcVO, subsectionId);
		}
		BusinessConditionModelConvertor.convertBusinessVOToBO(bcBO, bcVO);
		if (isRevert) {
			BusinessConditionModelConvertor.convertBusinessVOToBOForDeductionsDetail(bcBO, bcVO);
		}
		subsectionBO.setBusinessBO(bcBO);

		if (isRevert) {
			List<TRiContRetro> retroList = retrocessionLogDao.getRetrocessionListForLog(subsectionId, operateId);
			List<TRiContRetro> coveredList = retrocessionLogDao.getCoveredSectionListForLog(subsectionId, operateId);
			retroList.addAll(coveredList);
			if (null != retroList) {
				subsectionBO.setRetroList(retroList);
			}
		} else {
			if (subsectionBO.getBusinessBO() != null) {
				// main currency
				if (CollectionUtils.isNotEmpty(subsectionBO.getBusinessBO().getCurrencyBO())) {
					for (CurrencyBO item : subsectionBO.getBusinessBO().getCurrencyBO()) {
						if (item.getMainCurrency().equals("true")) {
							subsectionBO.setMainCurrency(item.getCurrencyType());
						}
					}
				}
				// our share
				if (subsectionBO.getBusinessBO().getShareBO() != null) {
					ShareBO shareBO = subsectionBO.getBusinessBO().getShareBO();
					subsectionBO.setOurShare(conditionDS.getOurShare(shareBO));
				}
				// premium
				if (subsectionBO.getBusinessBO().getPremiumBO() != null) {
					subsectionBO.setPremium(conditionDS.getPremium(subsectionBO.getBusinessBO().getCurrencyBO(),
							subsectionBO.getBusinessBO().getPremiumBO()));
				}
				// deductions
				if (subsectionBO.getBusinessBO().getDeductionBO() != null) {
					subsectionBO.setDeductions(conditionDS.getDeductions(subsectionBO.getBusinessBO().getDeductionBO(),
							subsectionBO.getPremium()));
				}
			}
		}

		return subsectionBO;
	}

	public ContractPDFVO convertContractBOToPDFVO(ContractBO bo) throws Exception {
		ContractPDFVO pdfVO = new ContractPDFVO();
		BeanUtils.copyProperties(pdfVO, bo);
		pdfVO.setCreatedOn(bo.getInsertTime());
		pdfVO.setLastChangedOn(bo.getUpdateTime());
		pdfVO.setBrokerCountry(pdfVO.getBroker() == null ? null : partnerAPI.getPartnerCountry(pdfVO.getBroker()));

		// Period Duration
		if (null != bo.getReinsStarting() && null != bo.getReinsEnding()) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			long to = df.parse(df.format(bo.getReinsEnding())).getTime();
			long from = df.parse(df.format(bo.getReinsStarting())).getTime();
			long between_days = (to - from) / (1000 * 3600 * 24);

			Calendar oneYearEnd = Calendar.getInstance();
			oneYearEnd.setTime(bo.getReinsStarting());
			oneYearEnd.add(Calendar.YEAR, 1);
			oneYearEnd.add(Calendar.DATE, -1);
			Date end = oneYearEnd.getTime();
			String period = null;
			if (bo.getReinsEnding().compareTo(end) == 1) {
				period = "More Than One Year(" + between_days + " Days)";
			} else if (bo.getReinsEnding().compareTo(end) == -1) {
				period = "Less Than One Year(" + between_days + " Days)";
			} else if (bo.getReinsEnding().compareTo(end) == 0) {
				period = "One Year";
			}
			pdfVO.setPeriodDuration(period);
		}

		// Treaty Termination Date
		if (bo.getLatestStatus().equals((ContractCst.CONTRACT_STATUS_CANCELLED))) {
			TerminationVO termination = contractHomeDS.loadTermination(bo.getContCompId());
			pdfVO.setTerminatedDate(termination == null ? null : termination.getTerminatedDate());
		}

		if (null != bo.getAccountingBO()) {
			pdfVO.setAccountingBasis(bo.getAccountingBO().getAccountingBasis());
			pdfVO.setAccountFrequency(bo.getAccountingBO().getAccountFrequency());
		}

		// attachment of contract
		List<TDocument> documentList = documentService.getDocumentList(bo.getContCompId());
		if (CollectionUtils.isNotEmpty(documentList)) {
			for (TDocument item : documentList) {
				AttachmentVO vo = new AttachmentVO();
				vo.setAttachFileName(item.getFileName());
				vo.setAttachName(item.getDocumentType() == null ? item.getDocumentTypeSelf()
						: this.getContractCodeDescription(800016, item.getDocumentType()));
				pdfVO.getAttachmentList().add(vo);
			}
		}

		List<SectionBO> sectionBOList = bo.getSectionList();
		int secNo = 0;
		for (SectionBO sec : sectionBOList) {
			secNo++;
			// premium
			BigDecimal premium = BigDecimal.ZERO;
			if (null != sec.getBusinessBO() && sec.getBusinessBO().getPremiumBO() != null) {
				premium = conditionDS.getPremium(sec.getBusinessBO().getCurrencyBO(),
						sec.getBusinessBO().getPremiumBO());
			}

			// convert sec's businessConditionBO
			BusinessConditionBO bcBO = businessDS.convertBusinessConditionBOWithMainCurrency(sec.getBusinessBO());
			sec.setBusinessBO(bcBO);

			// Treaty Clause (section level)
			pdfVO.getClauseList().addAll(getClauseList(sec.getBusinessBO()));
			// Get areaList: area covered of section
			pdfVO.getAreaList().addAll(getAreaVOList(sec, secNo));

			if (ContractCst.CONTRACT_NATURE_NON_PROPORTIONAL.equals(pdfVO.getContractNature())) {
				// Limits and Deductibles
				pdfVO.getLimitsDeductiblesList().add(getLimitsDeductiblesVO(sec, secNo));
				// Installment Dates
				pdfVO.getInstallmentDatesList().addAll(getInstallmentDatesVOList(sec, secNo));
				// Premiums
				pdfVO.getPremiumsList().add(getPremiumsVO(sec, secNo, pdfVO));
			} else if (ContractCst.CONTRACT_NATURE_PROPORTIONAL.equals(pdfVO.getContractNature())) {
				// Forecasting & Estimates
				if (sec.getPricingBO() != null) {
					if (CollectionUtils.isNotEmpty(sec.getPricingBO().getTRiContPricingItems())) {
						for (TRiContPricingItem item : sec.getPricingBO().getTRiContPricingItems()) {
							ForecastEstimateVO vo = new ForecastEstimateVO();
							vo.setSectionNo(String.valueOf(secNo));
							vo.setEpi(item.getEpi());
							vo.setCommission(item.getCommission());
							vo.setLossRatio(item.getLossRatio());
							BigDecimal dac = item.getCommission() == null
									? (item.getBrokerage() == null ? item.getTaxs()
											: item.getBrokerage()
													.add(item.getTaxs() == null ? BigDecimal.ZERO : item.getTaxs()))
									: item.getCommission()
											.add(item.getBrokerage() == null ? item.getTaxs()
													: item.getBrokerage().add(
															item.getTaxs() == null ? BigDecimal.ZERO : item.getTaxs()));
							vo.setDac(dac);
							pdfVO.getForcastEstimateList().add(vo);
						}
					}
				}
				// Premium and Limits
				pdfVO.getPremiumLimitsList().add(getPremiumLimitsVO(sec, secNo));
				// Perils and Limits
				pdfVO.getPerilsLimitsList().addAll(getPerilsLimitsVOList(sec, secNo));
			}

			// Result Dependent Conditions; Result Independent Conditions
			setResultConditionInfo(pdfVO, sec, secNo, premium);

			List<SubsectionBO> subsectionBOList = sec.getSubsectionList();
			for (SubsectionBO subsec : subsectionBOList) {
				// Treaty Clause (subsection level)
				pdfVO.getClauseList().addAll(getClauseList(subsec.getBusinessBO()));

			}
		}

		// format data
		formatContractPDFVO(pdfVO, secNo);
		// codeTable convert
		this.transCodeName(pdfVO, bo);

		return pdfVO;
	}

	private void formatContractPDFVO(ContractPDFVO pdfVO, int secNum) throws Exception {
		// remove duplicate data from clauseList
		HashSet<String> set = new HashSet<String>(pdfVO.getClauseList());
		pdfVO.getClauseList().clear();
		pdfVO.getClauseList().addAll(set);
		// merge area list
		HashMap<String, AreaVO> mapArea = new HashMap<String, AreaVO>();
		for (AreaVO areaVO : pdfVO.getAreaList()) {
			if (mapArea.containsKey(areaVO.getArea())) {
				areaVO.setSectionNo(mapArea.get(areaVO.getArea()).getSectionNo() + "/" + areaVO.getSectionNo());
			}
			mapArea.put(areaVO.getArea(), areaVO);
		}
		pdfVO.getAreaList().clear();
		pdfVO.getAreaList().addAll(mapArea.values());
		// sort area list
		Collections.sort(pdfVO.getAreaList(), new CompareArea());

		// merge resultConditionIndepList
		HashMap<String, ResultConditionIndepVO> mapResultIndep = new HashMap<String, ResultConditionIndepVO>();
		for (ResultConditionIndepVO resultIndepVO : pdfVO.getResultConditionIndepList()) {
			if (null != resultIndepVO.getRate()) {
				if (mapResultIndep.containsKey(resultIndepVO.getResultCondition())) {
					// if (null == resultIndepVO.getRate()) {
					// continue;
					// }
					// if (null ==
					// mapResultIndep.get(resultIndepVO.getResultCondition()).getRate())
					// {
					// resultIndepVO.setSectionNo(resultIndepVO.getSectionNo());
					// resultIndepVO.setRate(resultIndepVO.getRate());
					// } else {
					// resultIndepVO.setSectionNo(mapResultIndep.get(resultIndepVO.getResultCondition()).getSectionNo()
					// + "/" + resultIndepVO.getSectionNo());
					// resultIndepVO.setRate(mapResultIndep.get(resultIndepVO.getResultCondition()).getRate()
					// + "/"
					// + resultIndepVO.getRate());
					// }
					resultIndepVO.setSectionNo(mapResultIndep.get(resultIndepVO.getResultCondition()).getSectionNo()
							+ "/" + resultIndepVO.getSectionNo());
					resultIndepVO.setRate(mapResultIndep.get(resultIndepVO.getResultCondition()).getRate() + "/"
							+ resultIndepVO.getRate());
				}
				mapResultIndep.put(resultIndepVO.getResultCondition(), resultIndepVO);
			}
		}
		pdfVO.getResultConditionIndepList().clear();
		pdfVO.getResultConditionIndepList().addAll(mapResultIndep.values());
		for (ResultConditionIndepVO item : pdfVO.getResultConditionIndepList()) {
			// if (null != item.getSectionNo()) {
			// String sectionNo = formatStringWithSlash(item.getSectionNo(),
			// false);
			// item.setSectionNo(sectionNo.equals("-") ? null : sectionNo);
			// String rate = formatStringWithSlash(item.getRate(), true);
			// item.setRate(rate.equals("-") ? null : rate);
			// if (null == item.getRate()) {
			// item.setSectionNo(null);
			// }
			// }

			// if all sections's rate is same
			List<String> secNolist = DataTypeConvertor.splitStringToListBySlash(item.getSectionNo());
			if (secNolist.size() == secNum) {
				if (this.ElementIsAllSame(item.getRate())) {
					item.setSectionNo("All Sections");
					List<String> ratelist = DataTypeConvertor.splitStringToListBySlash(item.getRate());
					item.setRate(ratelist.get(0));
				}
			}
		}

		// merge resultConditionDepList
		HashMap<String, ResultConditionDepVO> mapResultDep = new HashMap<String, ResultConditionDepVO>();
		for (ResultConditionDepVO resultDepVO : pdfVO.getResultConditionDepList()) {
			if (null != resultDepVO.getSectionNo()) {
				if (mapResultDep.containsKey(resultDepVO.getResultCondition())) {
					resultDepVO.setSectionNo(mapResultDep.get(resultDepVO.getResultCondition()).getSectionNo() + "/"
							+ resultDepVO.getSectionNo());
					resultDepVO.setLcfPeriod(mapResultDep.get(resultDepVO.getResultCondition()).getLcfPeriod() + "/"
							+ resultDepVO.getLcfPeriod());
					resultDepVO.setComm(
							mapResultDep.get(resultDepVO.getResultCondition()).getComm() + "/" + resultDepVO.getComm());
					resultDepVO.setLr(
							mapResultDep.get(resultDepVO.getResultCondition()).getLr() + "/" + resultDepVO.getLr());
					resultDepVO.setRate(
							mapResultDep.get(resultDepVO.getResultCondition()).getRate() + "/" + resultDepVO.getRate());
					resultDepVO.setMgmtExp(mapResultDep.get(resultDepVO.getResultCondition()).getMgmtExp() + "/"
							+ resultDepVO.getMgmtExp());
				}
				mapResultDep.put(resultDepVO.getResultCondition(), resultDepVO);
			}
		}
		pdfVO.getResultConditionDepList().clear();
		pdfVO.getResultConditionDepList().addAll(mapResultDep.values());
		for (ResultConditionDepVO item : pdfVO.getResultConditionDepList()) {
			if (null != item.getSectionNo()) {
				item.setRate(formatStringWithSlash(item.getRate(), true));
				item.setComm(formatStringWithSlash(item.getComm(), true));
				item.setLr(formatStringWithSlash(item.getLr(), true));
				item.setMgmtExp(formatStringWithSlash(item.getMgmtExp(), true));

				// if all sections's LCF Period, Rate(%), Comm.(%), L.R.(%),
				// Mgmt. Exp.(%) is same
				List<String> secNolist = DataTypeConvertor.splitStringToListBySlash(item.getSectionNo());
				if (secNolist.size() == secNum) {
					if (this.ElementIsAllSame(item.getRate()) && this.ElementIsAllSame(item.getLcfPeriod())
							&& this.ElementIsAllSame(item.getLr()) && this.ElementIsAllSame(item.getMgmtExp())
							&& this.ElementIsAllSame(item.getComm())) {
						item.setSectionNo("All Sections");
						List<String> ratelist = DataTypeConvertor.splitStringToListBySlash(item.getRate());
						item.setRate(ratelist.get(0));
						List<String> lcfPeriodList = DataTypeConvertor.splitStringToListBySlash(item.getLcfPeriod());
						item.setLcfPeriod(lcfPeriodList.get(0));
						List<String> lrList = DataTypeConvertor.splitStringToListBySlash(item.getLr());
						item.setLr(lrList.get(0));
						List<String> mgmtExpList = DataTypeConvertor.splitStringToListBySlash(item.getMgmtExp());
						item.setMgmtExp(mgmtExpList.get(0));
						List<String> commList = DataTypeConvertor.splitStringToListBySlash(item.getComm());
						item.setComm(commList.get(0));
					}
				}
			}
		}

		// merge installmentDates list
		HashMap<String, InstallmentDatesVO> mapInstal = new HashMap<String, InstallmentDatesVO>();
		for (InstallmentDatesVO instaVO : pdfVO.getInstallmentDatesList()) {
			if (mapInstal.containsKey(instaVO.getSectionNo())) {
				instaVO.setInstalDueDate(
						mapInstal.get(instaVO.getSectionNo()).getInstalDueDate() + "/" + instaVO.getInstalDueDate());
			}
			mapInstal.put(instaVO.getSectionNo(), instaVO);
		}
		pdfVO.getInstallmentDatesList().clear();
		pdfVO.getInstallmentDatesList().addAll(mapInstal.values());

	}

	private boolean ElementIsAllSame(String str) throws Exception {
		boolean ret = true;
		// split str into list
		List<String> list = DataTypeConvertor.splitStringToListBySlash(str);

		// if all elements of list equal with the first element
		if (CollectionUtils.isNotEmpty(list)) {
			String firstElement = list.get(0);
			for (String item : list) {
				if (!item.equals(firstElement)) {
					ret = false;
					break;
				}
			}
		}

		return ret;
	}

	private List<String> getClauseList(BusinessConditionBO bo) throws Exception {
		List<String> clauseList = new ArrayList<String>();
		if (bo.getClausesBO() != null) {
			List<String> recommendList = DataTypeConvertor
					.transStringToSelectTree(bo.getClausesBO().getClausesRecommend());
			if (null != recommendList) {
				for (String clause : recommendList) {
					clause = this.getContractCodeDescription(ContractCst.CODE_TABLE_CLAUSE_RECOMMEND_TYPE, clause);
					clauseList.add(clause);
				}
			}
			List<String> requiredList = DataTypeConvertor
					.transStringToSelectTree(bo.getClausesBO().getClausesRequired());
			if (null != requiredList) {
				for (String clause : requiredList) {
					clause = this.getContractCodeDescription(ContractCst.CODE_TABLE_CLAUSE_REQUIRED_TYPE, clause);
					clauseList.add(clause);
				}
			}
		}
		return clauseList;
	}

	private List<AreaVO> getAreaVOList(SectionBO bo, int secNo) throws Exception {
		List<AreaVO> areaVOList = new ArrayList<AreaVO>();
		if (bo.getCovered() != null) {
			List<String> areaList = DataTypeConvertor.transStringToSelectTree(bo.getCovered());
			for (String area : areaList) {
				AreaVO vo = new AreaVO();
				vo.setArea(this.getContractCodeDescription(ContractCst.CODE_TABLE_CONTRACT_COUNTRYE, area));
				vo.setSectionNo(String.valueOf(secNo));
				areaVOList.add(vo);
			}
		}
		return areaVOList;
	}

	class CompareArea implements Comparator<AreaVO> {

		@Override
		public int compare(AreaVO o1, AreaVO o2) {
			if (o1.getSectionNo().length() > o2.getSectionNo().length()) {
				return -1;
			} else if (o1.getSectionNo().length() < o2.getSectionNo().length()) {
				return 1;
			}
			if (o1.getSectionNo().compareTo(o2.getSectionNo()) == 0) {
				return o1.getArea().compareTo(o2.getArea());
			}
			return o1.getSectionNo().compareTo(o2.getSectionNo());
		}

	}

	// Get Premium and Limits info
	private PremiumLimitsVO getPremiumLimitsVO(SectionBO bo, int secNo) throws Exception {
		PremiumLimitsVO vo = new PremiumLimitsVO();
		vo.setSectionNo(String.valueOf(secNo));
		vo.setSectionName(bo.getSectionName());
		vo.setSectionType(ContractCst.CONTRACT_LIMIT_COMB.equals(bo.getShareType()) ? "QS&SPL"
				: this.getContractCodeDescription(ContractCst.CODE_TABLE_CONT_SHARE_TYPE, bo.getShareType()));

		// 100% Premium (EPI)
		BigDecimal epi = null;
		// 100% Premium (Our Share)
		BigDecimal percentagePremium = null;
		// Maxium Liability / Retention
		BigDecimal maxLiabilityRetention = null;
		// Cession Limit
		BigDecimal cessionLimit = null;
		// Cession Limit (Our Share)
		BigDecimal ourShareCessionLimit = null;
		if (null != bo.getBusinessBO()) {
			// get main currency
			String mainCurrency = ContractCst.CONTRACT_DEFAULT_CURRENCY;
			if (CollectionUtils.isNotEmpty(bo.getBusinessBO().getCurrencyBO())) {
				for (CurrencyBO item : bo.getBusinessBO().getCurrencyBO()) {
					if (item.getMainCurrency().equals("true")) {
						mainCurrency = item.getCurrencyType();
					}
				}
			}

			if (null != bo.getBusinessBO().getPremiumBO()
					&& CollectionUtils.isNotEmpty(bo.getBusinessBO().getPremiumBO().getTRiContPremiumItems())) {
				List<TRiContPremiumItem> premiumItems = bo.getBusinessBO().getPremiumBO().getTRiContPremiumItems();
				for (TRiContPremiumItem premiumItem : premiumItems) {
					if (null != premiumItem.getItemType()
							&& premiumItem.getItemType().equals(ContractCst.CONTRACT_PREMIUM_EPI)) {
						// 100% Premium(EPI)
						epi = premiumItem.getAmount() == null ? epi
								: (epi == null ? BigDecimal.ZERO : epi).add(premiumItem.getAmount());
						// 100% Premium(Our Share)
						percentagePremium = premiumItem.getOurSignedShare() == null
								? (premiumItem.getOurWrittenShare() == null ? percentagePremium
										: (percentagePremium == null ? BigDecimal.ZERO : percentagePremium)
												.add(premiumItem.getOurWrittenShare()))
								: (percentagePremium == null ? BigDecimal.ZERO : percentagePremium)
										.add(premiumItem.getOurSignedShare());
					}
				}
			}
			if (null != bo.getBusinessBO().getShareBO()) {
				ShareBO shareBO = bo.getBusinessBO().getShareBO();
				// Signed Line
				vo.setSignedLine(shareBO.getSignedShares1() == null ? null : shareBO.getSignedShares1().toString());
				// Written Line
				vo.setWrittenLine(shareBO.getWrittenShare1() == null ? null : shareBO.getWrittenShare1().toString());
			}
			if (null != bo.getBusinessBO().getLimitBO()
					&& CollectionUtils.isNotEmpty(bo.getBusinessBO().getLimitBO().getTRiContLimitItems())) {
				List<TRiContLimitItem> limitItems = bo.getBusinessBO().getLimitBO().getTRiContLimitItems();
				for (TRiContLimitItem limitItem : limitItems) {
					if (null != limitItem.getItemType()
							&& ContractCst.CONTRACT_LIMIT_QS.equals(limitItem.getItemType())) {
						// Maxium Liability / Retention
						maxLiabilityRetention = limitItem.getSumInsured() == null ? maxLiabilityRetention
								: (maxLiabilityRetention == null ? BigDecimal.ZERO : maxLiabilityRetention)
										.add(limitItem.getSumInsured());
						// QS Ceded % / Lines
						if (mainCurrency.equals(limitItem.getCurrency())) {
							vo.setQsCeded(limitItem.getQs() == null ? null : formatPercentNumber(limitItem.getQs()));
						}
					} else if (null != limitItem.getItemType()
							&& ContractCst.CONTRACT_LIMIT_SURPLUS.equals(limitItem.getItemType())) {
						// Maxium Liability / Retention
						maxLiabilityRetention = limitItem.getRetente() == null ? maxLiabilityRetention
								: (maxLiabilityRetention == null ? BigDecimal.ZERO : maxLiabilityRetention)
										.add(limitItem.getRetente());
						// QS Ceded % / Lines
						if (mainCurrency.equals(limitItem.getCurrency())) {
							vo.setQsCeded(
									limitItem.getNoOfLines() == null ? null : (limitItem.getNoOfLines() + "Line(s)"));
						}
					} else if (null != limitItem.getItemType()
							&& ContractCst.CONTRACT_LIMIT_COMB.equals(limitItem.getItemType())) {
						// Maxium Liability / Retention
						maxLiabilityRetention = limitItem.getSumInsured() == null ? maxLiabilityRetention
								: (maxLiabilityRetention == null ? BigDecimal.ZERO : maxLiabilityRetention)
										.add(limitItem.getSumInsured());
						// QS Ceded % / Lines
						if (mainCurrency.equals(limitItem.getCurrency())) {
							vo.setQsCeded(limitItem.getQs() == null
									? (limitItem.getNoOfLines() == null ? null : (limitItem.getNoOfLines() + "Line(s)"))
									: limitItem.getNoOfLines() == null ? formatPercentNumber(limitItem.getQs())
											: formatPercentNumber(limitItem.getQs()) + "/" + limitItem.getNoOfLines()
													+ "Line(s)");
						}
					}
					// Cession Limit
					cessionLimit = limitItem.getLiability() == null ? cessionLimit
							: (cessionLimit == null ? BigDecimal.ZERO : cessionLimit).add(limitItem.getLiability());
					// Cession Limit (Our Share)
					ourShareCessionLimit = limitItem.getLiabilitySignedShare() == null
							? (limitItem.getLiabilityWrittenShare() == null ? ourShareCessionLimit
									: (ourShareCessionLimit == null ? BigDecimal.ZERO : ourShareCessionLimit)
											.add(limitItem.getLiabilityWrittenShare()))
							: (ourShareCessionLimit == null ? BigDecimal.ZERO : ourShareCessionLimit)
									.add(limitItem.getLiabilitySignedShare());
				}
				vo.setMaxLiabilityRetention(maxLiabilityRetention);
				vo.setCessionLimit(cessionLimit);
				vo.setOurShareCessionLimit(ourShareCessionLimit);
			}
		}
		vo.setEpi(epi);
		vo.setPercentagePremium(percentagePremium);

		return vo;
	}

	// Get Perils and Limits
	private List<PerilsLimitsVO> getPerilsLimitsVOList(SectionBO bo, int secNo) throws Exception {
		List<PerilsLimitsVO> perilsLimitsVOList = new ArrayList<PerilsLimitsVO>();
		if (null != bo.getBusinessBO()) {
			if (null != bo.getBusinessBO().getLimitBO()
					&& CollectionUtils.isNotEmpty(bo.getBusinessBO().getLimitBO().getTRiContLimitEvents())) {
				List<TRiContLimitEvent> limitEvents = bo.getBusinessBO().getLimitBO().getTRiContLimitEvents();
				for (TRiContLimitEvent event : limitEvents) {
					PerilsLimitsVO perilsLimitsVO = new PerilsLimitsVO();
					perilsLimitsVO.setSectionNo(String.valueOf(secNo));
					perilsLimitsVO.setEvent(this.getContractCodeDescription(ContractCst.CODE_TABLE_CONTRACT_PERIL_TYPE,
							event.getEvent()));
					perilsLimitsVO.setCategory("Limit(Loss Event)");
					perilsLimitsVO.setPercentageEventLimit(event.getLiability());
					perilsLimitsVO.setOurShareEventLimit(event.getLiabilitySignedShare() == null
							? event.getLiabilityWrittenShare() : event.getLiabilitySignedShare());
					perilsLimitsVOList.add(perilsLimitsVO);
				}

				// merge perilsLimitsVOList
				HashMap<String, PerilsLimitsVO> mapPerilsLimit = new HashMap<String, PerilsLimitsVO>();
				for (PerilsLimitsVO perilsLimitsVO : perilsLimitsVOList) {
					if (mapPerilsLimit.containsKey(perilsLimitsVO.getEvent())) {
						if (perilsLimitsVO.getPercentageEventLimit() != null) {
							perilsLimitsVO.setPercentageEventLimit(mapPerilsLimit.get(perilsLimitsVO.getEvent())
									.getPercentageEventLimit().add(perilsLimitsVO.getPercentageEventLimit()));
						}
						if (perilsLimitsVO.getOurShareEventLimit() != null) {
							perilsLimitsVO.setOurShareEventLimit(mapPerilsLimit.get(perilsLimitsVO.getEvent())
									.getOurShareEventLimit().add(perilsLimitsVO.getOurShareEventLimit()));
						}
					}
					mapPerilsLimit.put(perilsLimitsVO.getEvent(), perilsLimitsVO);
				}
				perilsLimitsVOList.clear();
				perilsLimitsVOList.addAll(mapPerilsLimit.values());

				int rank = 0;
				for (PerilsLimitsVO item : perilsLimitsVOList) {
					rank++;
					item.setRank(String.valueOf(rank));
				}
			}
		}
		return perilsLimitsVOList;
	}

	// Get Result Independent Conditions, Result Dependent Conditions
	private void setResultConditionInfo(ContractPDFVO pdfVO, SectionBO bo, int secNo, BigDecimal premium)
			throws Exception {
		List<ResultConditionIndepVO> resultConditionIndepVOList = new ArrayList<ResultConditionIndepVO>();
		// get contract level's information
		TRiContractStructure structureEntity = structureDao.load(bo.getContCompId());
		TRiContractInfo parentEntity = contractInfoDao.load(structureEntity.getParentId());

		if (parentEntity.getContractNature().equals(ContractCst.CONTRACT_NATURE_PROPORTIONAL)) {
			resultConditionIndepVOList.add(new ResultConditionIndepVO("Provisonal Commission"));
			resultConditionIndepVOList.add(new ResultConditionIndepVO("Commission"));
		}
		resultConditionIndepVOList.add(new ResultConditionIndepVO("Brokerage"));
		resultConditionIndepVOList.add(new ResultConditionIndepVO("Acquisition Costs"));
		resultConditionIndepVOList.add(new ResultConditionIndepVO("Administrative Expenses"));
		resultConditionIndepVOList.add(new ResultConditionIndepVO("Cover Holder Fee"));
		resultConditionIndepVOList.add(new ResultConditionIndepVO("Fire Brigade Tax"));
		resultConditionIndepVOList.add(new ResultConditionIndepVO("Fronting Fee"));
		resultConditionIndepVOList.add(new ResultConditionIndepVO("Interests on Accounts"));
		resultConditionIndepVOList.add(new ResultConditionIndepVO("Other Expenses"));
		resultConditionIndepVOList.add(new ResultConditionIndepVO("Overriding Commission"));
		resultConditionIndepVOList.add(new ResultConditionIndepVO("Tax Others"));

		if (null != bo.getBusinessBO() && null != bo.getBusinessBO().getDeductionBO()) {
			DeductionsBO deductionsBO = bo.getBusinessBO().getDeductionBO();
			// Get Result Independent Conditions
			for (ResultConditionIndepVO item : resultConditionIndepVOList) {
				item.setSectionNo(String.valueOf(secNo));
				// proportional
				if ((deductionsBO.getPercentOfPremium() != null || deductionsBO.getFixedAmountHunredPercent() != null)
						&& item.getResultCondition().equals("Brokerage")) {
					BigDecimal rate = deductionsBO.getPercentOfPremium() == null ? (premium == BigDecimal.ZERO
							? BigDecimal.ZERO
							: deductionsBO.getFixedAmountHunredPercent().divide(premium, 4, BigDecimal.ROUND_HALF_UP))
							: deductionsBO.getPercentOfPremium();
					item.setRate(rate == null ? null : formatPercentNumber(rate));
				} else if (null != deductionsBO.getRICommission() && deductionsBO.getRICommission().equals("1")
						&& null != deductionsBO.getRiPercentage() && item.getResultCondition().equals("Commission")) {
					item.setRate(formatPercentNumber(deductionsBO.getRiPercentage()));
				} else if (null != deductionsBO.getRICommission() && deductionsBO.getRICommission().equals("2")
						&& null != deductionsBO.getRiPercentage()
						&& item.getResultCondition().equals("Provisonal Commission")) {
					item.setRate(formatPercentNumber(deductionsBO.getRiPercentage()));
				}
				if (CollectionUtils.isNotEmpty(deductionsBO.getTRiContDeductionsItems())) {
					for (TRiContDeductionsItem deductionsItem : deductionsBO.getTRiContDeductionsItems()) {
						if (((item.getResultCondition().equals("Acquisition Costs")
								&& deductionsItem.getItem().equals("1"))
								|| (item.getResultCondition().equals("Administrative Expenses")
										&& deductionsItem.getItem().equals("2"))
								|| (item.getResultCondition().equals("Cover Holder Fee")
										&& deductionsItem.getItem().equals("3"))
								|| (item.getResultCondition().equals("Fire Brigade Tax")
										&& deductionsItem.getItem().equals("4"))
								|| (item.getResultCondition().equals("Fronting Fee")
										&& deductionsItem.getItem().equals("5"))
								|| (item.getResultCondition().equals("Interests on Accounts")
										&& deductionsItem.getItem().equals("6"))
								|| (item.getResultCondition().equals("Other Expenses")
										&& deductionsItem.getItem().equals("7"))
								|| (item.getResultCondition().equals("Overriding Commission")
										&& deductionsItem.getItem().equals("8"))
								|| (item.getResultCondition().equals("Tax Others")
										&& deductionsItem.getItem().equals("9")))
								&& (null != deductionsItem.getPercentage()
										|| null != deductionsItem.getAmountPercent())) {
							BigDecimal rate = deductionsItem.getPercentage() == null
									? (premium == BigDecimal.ZERO ? BigDecimal.ZERO
											: (premium == null ? null
													: deductionsItem.getAmountPercent().divide(premium, 4,
															BigDecimal.ROUND_HALF_UP)))
									: deductionsItem.getPercentage();
							item.setRate(rate == null ? null : formatPercentNumber(rate));
						}
					}
				}
				// non-proportional
				if ((deductionsBO.getBrokerage() != null || deductionsBO.getBrokerage2() != null)
						&& item.getResultCondition().equals("Brokerage")) {
					BigDecimal rate = deductionsBO.getBrokerage() == null ? (premium == BigDecimal.ZERO
							? BigDecimal.ZERO
							: (premium == null ? null
									: deductionsBO.getBrokerage2().divide(premium, 4, BigDecimal.ROUND_HALF_UP)))
							: deductionsBO.getBrokerage();
					item.setRate(rate == null ? null : formatPercentNumber(rate));
				}
			}
			// Get Result Dependent Conditions
			if (null != deductionsBO.getRICommission() && deductionsBO.getRICommission().equals("2")) {
				List<ResultConditionDepVO> resultConditionDepList = new ArrayList<ResultConditionDepVO>();
				resultConditionDepList.add(new ResultConditionDepVO("Sliding Scale Commission"));
				resultConditionDepList.add(new ResultConditionDepVO("Profit Commission"));
				for (ResultConditionDepVO item : resultConditionDepList) {
					if (item.getResultCondition().equals("Sliding Scale Commission")) {
						if (deductionsBO.getDeductionComm() != null) {
							item.setSectionNo(String.valueOf(secNo));
							// chuan.ye modify Comm & Lr for Sliding Scale
							// Commission,On 2016-5-6 begin
							item.setComm(deductionsBO.getDeductionComm().getMinimumRISs() == null ? ""
									: formatPercentNumber(deductionsBO.getDeductionComm().getMinimumRISs()) + " - "
											+ (deductionsBO.getDeductionComm().getMaximumRISs() == null ? ""
													: formatPercentNumber(
															deductionsBO.getDeductionComm().getMaximumRISs())));
							item.setLr(deductionsBO.getDeductionComm().getMinimumLossSs() == null ? ""
									: formatPercentNumber(deductionsBO.getDeductionComm().getMinimumLossSs()) + " - "
											+ (deductionsBO.getDeductionComm().getMaximumLossSs() == null ? ""
													: formatPercentNumber(
															deductionsBO.getDeductionComm().getMaximumLossSs())));
							// chuan.ye modify Comm & Lr for Sliding Scale
							// Commission,On 2016-5-6 end
							for (TRiContDeductionsCarried carriedItem : deductionsBO.getDeductionComm()
									.getTRiContDeductionsCarrieds()) {
								if (carriedItem.getName().equals("Loss")) {
									item.setLcfPeriod(
											null == carriedItem.getYn() ? null
													: carriedItem
															.getYn().equals(
																	"0") ? "0"
																			: (null == carriedItem.getExtinction()
																					? (carriedItem.getYears() == null
																							? null
																							: String.valueOf(carriedItem
																									.getYears()))
																					: carriedItem.getExtinction()
																							.equals("1")
																									? "Extin"
																									: (carriedItem
																											.getYears() == null
																													? null
																													: String.valueOf(
																															carriedItem
																																	.getYears()))));
								}
							}
						}
					} else if (item.getResultCondition().equals("Profit Commission")) {
						item.setSectionNo(String.valueOf(secNo));
						// chuan.ye add Comm & Lr process for Profit Commission
						// On 2016-5-6, begin
						if (deductionsBO.getDeductionPc() != null) {
							item.setComm(deductionsBO.getDeductionPc().getMinimumRIPc() == null ? ""
									: formatPercentNumber(deductionsBO.getDeductionPc().getMinimumRIPc()) + " - "
											+ (deductionsBO.getDeductionPc().getMaximumRIPc() == null ? ""
													: formatPercentNumber(
															deductionsBO.getDeductionPc().getMaximumRIPc())));
							item.setLr(deductionsBO.getDeductionPc().getMinimumLossPc() == null ? ""
									: formatPercentNumber(deductionsBO.getDeductionPc().getMinimumLossPc()) + " - "
											+ (deductionsBO.getDeductionPc().getMaximumLossPc() == null ? ""
													: formatPercentNumber(
															deductionsBO.getDeductionPc().getMaximumLossPc())));
						}
						// chuan.ye add Comm & Lr process for Profit Commission
						// On 2016-5-6, end
						if (deductionsBO.getDeficitCarryForward() == null) {
							item.setLcfPeriod("0");
						} else if (deductionsBO.getDeficitCarryForward().equals("1")) {
							item.setLcfPeriod("Extin");
						} else if (deductionsBO.getDeficitCarryForward().equals("2")) {
							item.setLcfPeriod(deductionsBO.getNumberOfYears() == null ? null
									: String.valueOf(deductionsBO.getNumberOfYears()));
						}
						item.setRate(deductionsBO.getProfitPercentage() == null ? null
								: formatPercentNumber(deductionsBO.getProfitPercentage()));
						item.setMgmtExp(deductionsBO.getExpensesPercentage() == null ? null
								: formatPercentNumber(deductionsBO.getExpensesPercentage()));
					}
				}
				pdfVO.getResultConditionDepList().addAll(resultConditionDepList);
			}
		}
		pdfVO.getResultConditionIndepList().addAll(resultConditionIndepVOList);
	}

	// Get premiums
	private PremiumsVO getPremiumsVO(SectionBO bo, int secNo, ContractPDFVO pdfVO) throws Exception {
		PremiumsVO vo = new PremiumsVO();
		vo.setSectionNo(String.valueOf(secNo));
		// GNPI
		BigDecimal gnpi = null;
		// (100%) Premium
		BigDecimal perPremium = null;
		// Minimum Premium
		BigDecimal minPremium = null;
		// Deposit Premium
		BigDecimal depPremium = null;
		// Premium (Our Share)
		BigDecimal premiumOurShare = null;
		// Minimum Premium (Our Share)
		BigDecimal minPremiumOurShare = null;
		// Deposit
		BigDecimal depositPer = null;

		if (null != bo.getBusinessBO()) {
			PremiumBO premiumBO = bo.getBusinessBO().getPremiumBO();
			if (null != premiumBO && CollectionUtils.isNotEmpty(premiumBO.getTRiContPremiumItems())) {
				List<TRiContPremiumItem> premiumItems = premiumBO.getTRiContPremiumItems();
				// GNPI
				for (TRiContPremiumItem premiumItem : premiumItems) {
					if (null != premiumItem.getItemType()
							&& premiumItem.getItemType().equals(ContractCst.CONTRACT_PREMIUM_SUPI)) {
						gnpi = premiumItem.getAmount() == null ? gnpi
								: (gnpi == null ? BigDecimal.ZERO : gnpi).add(premiumItem.getAmount());
					}
				}
				vo.setGnpi(gnpi);
				String premiumType = premiumBO.getPremiumType();
				if (null != premiumType) {
					// Rate
					if (premiumType.equals(ContractCst.PREMIUM_TYPE_FLAT)) {
						vo.setRate(null);
					} else if (premiumType.equals(ContractCst.PREMIUM_TYPE_FIXED)) {
						vo.setRate(premiumBO.getRate() == null ? null : String.valueOf(premiumBO.getRate()));
					} else if (premiumType.equals(ContractCst.PREMIUM_TYPE_SWING)) {
						vo.setRate(premiumBO.getProvisionalRate() == null ? null
								: String.valueOf(premiumBO.getProvisionalRate()));
					}

					for (TRiContPremiumItem premiumItem : premiumItems) {
						if (null != premiumItem.getItemType()
								&& (premiumItem.getItemType().equals(ContractCst.CONTRACT_PREMIUM_FLAT)
										|| premiumItem.getItemType().equals(ContractCst.CONTRACT_PREMIUM_FIXED)
										|| premiumItem.getItemType().equals(ContractCst.CONTRACT_PREMIUM_SWING))) {
							// (100%) Premium
							perPremium = premiumItem.getAmount() == null ? perPremium
									: (perPremium == null ? BigDecimal.ZERO : perPremium).add(premiumItem.getAmount());
							// Premium (Our Share)
							premiumOurShare = premiumItem.getOurSignedShare() == null
									? (premiumItem.getOurWrittenShare() == null ? premiumOurShare
											: (premiumOurShare == null ? BigDecimal.ZERO : premiumOurShare)
													.add(premiumItem.getOurWrittenShare()))
									: (premiumOurShare == null ? BigDecimal.ZERO : premiumOurShare)
											.add(premiumItem.getOurSignedShare());
						}

						if (premiumType.equals(ContractCst.PREMIUM_TYPE_FLAT)) {
							// Minimum Premium
							minPremium = null;
							// Minimum Premium (Our Share)
							minPremiumOurShare = null;
							// Deposit Premium
							depPremium = null;
						} else {
							if (null != premiumItem.getItemType() && (premiumItem.getItemType()
									.equals(ContractCst.CONTRACT_PREMIUM_MININUM_PREMIUM))) {
								minPremium = premiumItem.getAmount() == null ? minPremium
										: (minPremium == null ? BigDecimal.ZERO : minPremium)
												.add(premiumItem.getAmount());
								minPremiumOurShare = premiumItem.getOurSignedShare() == null
										? (premiumItem.getOurWrittenShare() == null ? minPremiumOurShare
												: (minPremiumOurShare == null ? BigDecimal.ZERO : minPremiumOurShare)
														.add(premiumItem.getOurWrittenShare()))
										: (minPremiumOurShare == null ? BigDecimal.ZERO : minPremiumOurShare)
												.add(premiumItem.getOurSignedShare());
							}
							if (null != premiumItem.getItemType() && (premiumItem.getItemType()
									.equals(ContractCst.CONTRACT_PREMIUM_DEPOSIT_PREMIUM))) {
								depPremium = premiumItem.getAmount() == null ? depPremium
										: (depPremium == null ? BigDecimal.ZERO : depPremium)
												.add(premiumItem.getAmount());
								if (secNo == 1 && premiumItem.getCurrency().equals(pdfVO.getMainCurrency())) {
									depositPer = premiumItem.getDepositAmount();
									pdfVO.setDepositPer(depositPer == null ? null : String.valueOf(depositPer));
								}

							}
						}
					}
					vo.setMinPremium(minPremium);
					vo.setMinPremiumOurShare(minPremiumOurShare);
					vo.setPerPremium(perPremium);
					vo.setPremiumOurShare(premiumOurShare);
					vo.setDepPremium(depPremium);
				}
				if (null != bo.getBusinessBO().getShareBO()) {
					ShareBO shareBO = bo.getBusinessBO().getShareBO();
					// Signed Line
					vo.setSignedLine(
							shareBO.getSignedShares1() == null ? null : String.valueOf(shareBO.getSignedShares1()));
					// Written Line
					vo.setWrittenLine(
							shareBO.getWrittenShare1() == null ? null : String.valueOf(shareBO.getWrittenShare1()));
				}
				if (null != bo.getBusinessBO().getReinBO()) {
					if (null != bo.getBusinessBO().getReinBO().getReinType()) {
						// No. of Reinstatements
						if (bo.getBusinessBO().getReinBO().getReinType().equals("1")) {
							vo.setReinNo("0");
						} else if (bo.getBusinessBO().getReinBO().getReinType().equals("2")) {
							vo.setReinNo(bo.getBusinessBO().getReinBO().getReinNum() == null ? "0"
									: String.valueOf(bo.getBusinessBO().getReinBO().getReinNum()));
						} else if (bo.getBusinessBO().getReinBO().getReinType().equals("3")) {
							vo.setReinNo("Unlimited");
						}
					}
				}
			}
		}
		return vo;
	}

	// Get Installment Dates
	private List<InstallmentDatesVO> getInstallmentDatesVOList(SectionBO bo, int secNo) throws Exception {
		List<InstallmentDatesVO> installmentDatesList = new ArrayList<InstallmentDatesVO>();
		if (null != bo.getBusinessBO()) {
			// get main currency
			String mainCurrency = ContractCst.CONTRACT_DEFAULT_CURRENCY;
			if (CollectionUtils.isNotEmpty(bo.getBusinessBO().getCurrencyBO())) {
				for (CurrencyBO item : bo.getBusinessBO().getCurrencyBO()) {
					if (item.getMainCurrency().equals("true")) {
						mainCurrency = item.getCurrencyType();
					}
				}
			}
			PremiumBO premiumBO = bo.getBusinessBO().getPremiumBO();
			if (null != premiumBO && CollectionUtils.isNotEmpty(premiumBO.getTRiContPremiumItems())) {
				List<TRiContPremiumItem> premiumItems = premiumBO.getTRiContPremiumItems();
				String premiumType = premiumBO.getPremiumType();
				for (TRiContPremiumItem item : premiumItems) {
					InstallmentDatesVO vo = new InstallmentDatesVO();
					if (null != premiumType) {
						if (premiumType.equals(ContractCst.PREMIUM_TYPE_FLAT)) {
							if (null != item.getItemType()
									&& item.getItemType().equals(ContractCst.CONTRACT_PREMIUM_DEPOSIT_SHEDULE)) {
								if (mainCurrency.equals(item.getCurrency())) {
									vo.setSectionNo(String.valueOf(secNo));
									vo.setInstalDueDate(
											item.getDueDate() == null ? null : formatPDFDate(item.getDueDate()));
									installmentDatesList.add(vo);
								}
							}
						} else if (premiumType.equals(ContractCst.PREMIUM_TYPE_FIXED)
								|| premiumType.equals(ContractCst.PREMIUM_TYPE_SWING)) {
							if (null != item.getItemType()
									&& (item.getItemType().equals(ContractCst.CONTRACT_PREMIUM_DEPOSIT_SHEDULE))) { // artf214636
								if (mainCurrency.equals(item.getCurrency())) {
									vo.setSectionNo(String.valueOf(secNo));
									vo.setInstalDueDate(
											item.getDueDate() == null ? null : formatPDFDate(item.getDueDate()));
									installmentDatesList.add(vo);
								}
							}
						}
					}
				}
			}
		}
		return installmentDatesList;
	}

	// Get Limits and Deductibles
	private LimitsDeductiblesVO getLimitsDeductiblesVO(SectionBO bo, int secNo) throws Exception {
		LimitsDeductiblesVO vo = new LimitsDeductiblesVO();
		vo.setSectionNo(String.valueOf(secNo));
		vo.setSectionName(bo.getSectionName());
		vo.setSectionType(this.getContractCodeDescription(ContractCst.CODE_TABLE_CONT_SHARE_TYPE, bo.getShareType()));
		// Limit(Ind.Loss)
		BigDecimal limitInd = null;
		// Deductible (Ind.Loss)
		BigDecimal deductibleInd = null;
		// Limit (Event)
		BigDecimal limitEvent = null;
		// Deductible (Event)
		BigDecimal deductibleEvent = null;
		// AAl
		BigDecimal aal = null;
		// Limit (Ind.Loss) Our Share
		BigDecimal limitIndOurShare = null;
		// Limit (Event) Our Share
		BigDecimal limitEventOurShare = null;
		// AAL Our Share
		BigDecimal aalOurShare = null;
		if (null != bo.getBusinessBO()) {
			LimitBO limitBO = bo.getBusinessBO().getLimitBO();
			if (null != limitBO && null != limitBO.getLimitType()) {
				if (limitBO.getLimitType().equals(ContractCst.CONTRACT_NONPROPORTIONAL_LIMIT_TYPE_XOL)
						|| limitBO.getLimitType().equals(ContractCst.CONTRACT_NONPROPORTIONAL_LIMIT_TYPE_STOPLOSS)) {
					if (CollectionUtils.isNotEmpty(limitBO.getTRiContLimitItems())) {
						limitInd = null;
						deductibleInd = null;
						aal = null;
						aalOurShare = null;
						limitIndOurShare = null;
						for (TRiContLimitItem item : limitBO.getTRiContLimitItems()) {
							limitInd = item.getLimitLayer() == null ? limitInd
									: ((limitInd == null ? BigDecimal.ZERO : limitInd).add(item.getLimitLayer()));
							deductibleInd = item.getDeductible() == null ? deductibleInd
									: ((deductibleInd == null ? BigDecimal.ZERO : deductibleInd)
											.add(item.getDeductible()));
							aal = item.getAal() == null ? aal
									: ((aal == null ? BigDecimal.ZERO : aal).add(item.getAal()));
							aalOurShare = item.getAalShare() == null
									? (item.getAalWrittenShare() == null ? aalOurShare
											: (aalOurShare == null ? BigDecimal.ZERO : aalOurShare)
													.add(item.getAalWrittenShare()))
									: ((aalOurShare == null ? BigDecimal.ZERO : aalOurShare).add(item.getAalShare()));
							limitIndOurShare = item.getLayerShare() == null
									? (item.getLayerWrittenShare() == null ? limitIndOurShare
											: (limitIndOurShare == null ? BigDecimal.ZERO : limitIndOurShare)
													.add(item.getLayerWrittenShare()))
									: ((limitIndOurShare == null ? BigDecimal.ZERO : limitIndOurShare)
											.add(item.getLayerShare()));
						}
						vo.setLimitInd(limitInd);
						vo.setDeductibleInd(deductibleInd);
						vo.setAal(aal);
						vo.setAalOurShare(aalOurShare);
						vo.setLimitIndOurShare(limitIndOurShare);
					}
					if (CollectionUtils.isNotEmpty(limitBO.getTRiContLimitEvents())) {
						limitEvent = null;
						deductibleEvent = deductibleInd;
						limitEventOurShare = null;
						for (TRiContLimitEvent event : limitBO.getTRiContLimitEvents()) {
							limitEvent = event.getLimitHundred() == null ? limitEvent
									: ((limitEvent == null ? BigDecimal.ZERO : limitEvent)
											.add(event.getLimitHundred()));
							limitEventOurShare = event.getLimitShare() == null
									? (event.getWrittenShare() == null ? limitEventOurShare
											: (limitEventOurShare == null ? BigDecimal.ZERO : limitEventOurShare)
													.add(event.getWrittenShare()))
									: ((limitEventOurShare == null ? BigDecimal.ZERO : limitEventOurShare)
											.add(event.getLimitShare()));
						}
						vo.setLimitEvent(limitEvent);
						vo.setLimitEventOurShare(limitEventOurShare);
						vo.setDeductibleEvent(deductibleInd);
					}
				}
			}
		}
		return vo;
	}

	@Override
	public BigDecimal getExchangeRate(List<CurrencyBO> currencyBOList, String currency, String mainCurrency)
			throws Exception {
		// get map of currency and exchangeRate
		Map<String, BigDecimal> map = conditionDS.getExchangeRateMap(currencyBOList);

		BigDecimal exchangeRate = null;
		if (!map.isEmpty()) {
			Iterator<Entry<String, BigDecimal>> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, BigDecimal> entry = (Entry<String, BigDecimal>) iter.next();
				if (null != currency && entry.getKey().equals(currency)) {
					exchangeRate = entry.getValue();
					break;
				} else {
					exchangeRate = currencyExchangeService.getExchangeRate(currency, mainCurrency);
				}
			}
		} else {
			exchangeRate = currencyExchangeService.getExchangeRate(currency, mainCurrency);
		}
		return exchangeRate;
	}

	private void transCodeName(ContractPDFVO pdfVO, ContractBO bo) throws Exception {
		pdfVO.setContractNature(this.getContractCodeDescription(ContractCst.CODE_TABLE_CONTRACT_NATURE_TYPE,
				pdfVO.getContractNature()));
		pdfVO.setContractCategory(this.getContractCodeDescription(ContractCst.CODE_TABLE_CONTRACT_CATEGORY_TYPE,
				pdfVO.getContractCategory()));
		pdfVO.setContractType(
				this.getContractCodeDescription(ContractCst.CODE_TABLE_CONTRACT_TYPE, pdfVO.getContractType()));
		pdfVO.setLatestStatus(
				this.getContractCodeDescription(ContractCst.CODE_TABLE_CONTRACT_STATUS, pdfVO.getLatestStatus()));
		pdfVO.setAccountingBasis(this.getContractCodeDescription(ContractCst.CODE_TABLE_CONT_ACCOUNT_BASIS_TYPE,
				pdfVO.getAccountingBasis()));
		pdfVO.setAccountFrequency(this.getContractCodeDescription(ContractCst.CODE_TABLE_CONT_ACCOUNT_FRE_TYPE,
				pdfVO.getAccountFrequency()));
		pdfVO.setMainclass(
				this.getContractCodeDescription(ContractCst.CODE_TABLE_CONTRACT_COB_TYPE, pdfVO.getMainclass()));
		pdfVO.setSubclass(
				this.getContractCodeDescription(ContractCst.CODE_TABLE_CONTRACT_SUBCOB_TYPE, pdfVO.getSubclass()));
		pdfVO.setCedentCountry(
				this.getContractCodeDescription(ContractCst.CODE_TABLE_CONTRACT_COUNTRYE, pdfVO.getCedentCountry()));
		pdfVO.setBrokerCountry(
				this.getContractCodeDescription(ContractCst.CODE_TABLE_CONTRACT_COUNTRYE, pdfVO.getBrokerCountry()));

		if (null != bo.getAnalyticsResp()) {
			pdfVO.setAnalyticsResp(userDS.getUserNameByUserId(bo.getAnalyticsResp()));
		}
		if (null != bo.getMarketResp()) {
			pdfVO.setMarketResp(userDS.getUserNameByUserId(bo.getMarketResp()));
		}
		pdfVO.setUnderwritingResp(userDS.getUserNameByUserId(bo.getUnderwriting()));
		pdfVO.setTreatyResp(userDS.getUserNameByUserId(bo.getTreatyOwner()));
		pdfVO.setCreatedBy(userDS.getUserNameByUserId(bo.getCreatedBy()));
		pdfVO.setLastChanged(userDS.getUserNameByUserId(bo.getLastChanged()));

		pdfVO.setCedentName(partnerAPI.loadPartnerNameByPartnerCode(pdfVO.getCedent()));
		pdfVO.setBrokerName(partnerAPI.loadPartnerNameByPartnerCode(pdfVO.getBroker()));
		if (!StringUtils.isNullOrEmpty(pdfVO.getInsured())) {
			pdfVO.setInsured(partnerAPI.loadPartnerNameByPartnerCode(pdfVO.getInsured()));
		}
		if (!StringUtils.isNullOrEmpty(pdfVO.getCoBroker())) {
			pdfVO.setCoBroker(partnerAPI.loadPartnerNameByPartnerCode(pdfVO.getCoBroker()));
		}

	}

	private void setDeleteListForBusinessConditionVO(BusinessConditionVO vo, Long sectionId) throws Exception {
		BusinessConditionBO bo = conditionDS.loadBusinessConditionBO(sectionId);
		if (null != bo) {
			// deleteCurrencyList
			if (CollectionUtils.isNotEmpty(bo.getCurrencyBO())) {
				List<CurrencyVO> deleteCurrencyList = new ArrayList<CurrencyVO>();
				for (CurrencyBO item : bo.getCurrencyBO()) {
					CurrencyVO currencyVO = new CurrencyVO();
					BeanUtils.copyProperties(currencyVO, item);
					boolean isInclude = false;
					if (CollectionUtils.isNotEmpty(vo.getCurrencyList())) {
						for (CurrencyVO itemForLog : vo.getCurrencyList()) {
							if (itemForLog.getCurrencyId().compareTo(item.getCurrencyId()) == 0) {
								isInclude = true;
								break;
							}
						}
					}
					if (!isInclude) {
						deleteCurrencyList.add(currencyVO);
					}
				}
				vo.setDeleteCurrencyList(deleteCurrencyList);
			}
			// deletePremiumList
			if (null != bo.getPremiumBO() && CollectionUtils.isNotEmpty(bo.getPremiumBO().getTRiContPremiumItems())) {
				List<PremiumItemVO> deletePremiumList = new ArrayList<PremiumItemVO>();
				for (TRiContPremiumItem item : bo.getPremiumBO().getTRiContPremiumItems()) {
					PremiumItemVO premiumItemVO = new PremiumItemVO();
					BeanUtils.copyProperties(premiumItemVO, item);
					boolean isInclude = false;
					if (CollectionUtils.isNotEmpty(vo.getPremiumList())) {
						for (PremiumItemVO itemForLog : vo.getPremiumList()) {
							if (itemForLog.getItemId().compareTo(item.getItemId()) == 0) {
								isInclude = true;
								break;
							}
						}
					}
					if (!isInclude) {
						deletePremiumList.add(premiumItemVO);
					}
				}
				vo.setDeletePremiumList(deletePremiumList);
			}
			// deleteLimitItemList
			if (null != bo.getLimitBO() && CollectionUtils.isNotEmpty(bo.getLimitBO().getTRiContLimitItems())) {
				List<LimitItemVO> deleteLimitItemList = new ArrayList<LimitItemVO>();
				for (TRiContLimitItem item : bo.getLimitBO().getTRiContLimitItems()) {
					LimitItemVO limitItemVO = new LimitItemVO();
					BeanUtils.copyProperties(limitItemVO, item);
					boolean isInclude = false;
					if (CollectionUtils.isNotEmpty(vo.getLimitItemList())) {
						for (LimitItemVO itemForLog : vo.getLimitItemList()) {
							if (itemForLog.getItemId().compareTo(item.getItemId()) == 0) {
								isInclude = true;
								break;
							}
						}
					}
					if (!isInclude) {
						deleteLimitItemList.add(limitItemVO);
					}
				}
				vo.setDeleteLimitItemList(deleteLimitItemList);
			}
			// deleteLimitEventList
			if (null != bo.getLimitBO() && CollectionUtils.isNotEmpty(bo.getLimitBO().getTRiContLimitEvents())) {
				List<LimitEventVO> deleteLimitEventList = new ArrayList<LimitEventVO>();
				for (TRiContLimitEvent item : bo.getLimitBO().getTRiContLimitEvents()) {
					LimitEventVO limitEventVO = new LimitEventVO();
					BeanUtils.copyProperties(limitEventVO, item);
					boolean isInclude = false;
					if (CollectionUtils.isNotEmpty(vo.getLimitEventList())) {
						for (LimitEventVO itemForLog : vo.getLimitEventList()) {
							if (itemForLog.getEventId().compareTo(item.getEventId()) == 0) {
								isInclude = true;
								break;
							}
						}
					}
					if (!isInclude) {
						deleteLimitEventList.add(limitEventVO);
					}
				}
				vo.setDeleteLimitEventList(deleteLimitEventList);
			}
			if (null != bo.getDeductionBO()) {
				// deleteDeductionsList
				if (CollectionUtils.isNotEmpty(bo.getDeductionBO().getTRiContDeductionsItems())) {
					List<DeductionsItemVO> deleteDeductionsList = new ArrayList<DeductionsItemVO>();
					for (TRiContDeductionsItem item : bo.getDeductionBO().getTRiContDeductionsItems()) {
						DeductionsItemVO deductionsItemVO = new DeductionsItemVO();
						BeanUtils.copyProperties(deductionsItemVO, item);
						boolean isInclude = false;
						if (CollectionUtils.isNotEmpty(vo.getDeductionsList())) {
							for (DeductionsItemVO itemForLog : vo.getDeductionsList()) {
								if (itemForLog.getDeductionsItemId().compareTo(item.getDeductionsItemId()) == 0) {
									isInclude = true;
									break;
								}
							}
						}
						if (!isInclude) {
							deleteDeductionsList.add(deductionsItemVO);
						}
					}
					vo.setDeleteDeductionsList(deleteDeductionsList);
				}
				// deleteDeductionsAttachComm
				if (null != bo.getDeductionBO().getDeductionComm()
						&& CollectionUtils.isNotEmpty(bo.getDeductionBO().getDeductionComm().getAttachments())) {
					List<DeductionsAttachVO> deleteAttachTableCommList = new ArrayList<DeductionsAttachVO>();
					for (TRiContDeductionsAttach item : bo.getDeductionBO().getDeductionComm().getAttachments()) {
						DeductionsAttachVO deductionsAttachVO = new DeductionsAttachVO();
						BeanUtils.copyProperties(deductionsAttachVO, item);
						boolean isInclude = false;
						if (CollectionUtils.isNotEmpty(vo.getAttachTableCommList())) {
							for (DeductionsAttachVO itemForLog : vo.getAttachTableCommList()) {
								if (itemForLog.getAttachmentId().compareTo(item.getAttachmentId()) == 0) {
									isInclude = true;
									break;
								}
							}
						}
						if (!isInclude) {
							deleteAttachTableCommList.add(deductionsAttachVO);
						}
					}
					vo.setDeleteAttachTableCommList(deleteAttachTableCommList);
				}
				// deleteDeductionsAttachPC
				if (null != bo.getDeductionBO().getDeductionPc()
						&& CollectionUtils.isNotEmpty(bo.getDeductionBO().getDeductionPc().getAttachList())) {
					List<DeductionsAttachVO> deleteDeductionsAttach = new ArrayList<DeductionsAttachVO>();
					for (TRiContDeductionsAttach item : bo.getDeductionBO().getDeductionPc().getAttachList()) {
						DeductionsAttachVO deductionsAttachVO = new DeductionsAttachVO();
						BeanUtils.copyProperties(deductionsAttachVO, item);
						boolean isInclude = false;
						if (CollectionUtils.isNotEmpty(vo.getAttachTablePcList())) {
							for (DeductionsAttachVO itemForLog : vo.getAttachTablePcList()) {
								if (itemForLog.getAttachmentId().compareTo(item.getAttachmentId()) == 0) {
									isInclude = true;
									break;
								}
							}
						}
						if (!isInclude) {
							deleteDeductionsAttach.add(deductionsAttachVO);
						}
					}
					vo.setDeleteAttachTablePcList(deleteDeductionsAttach);
				}
			}
			if (null != bo.getReinBO() && CollectionUtils.isNotEmpty(bo.getReinBO().getTRiContReinstateItems())) {
				List<ReinstateItemVO> deleteReinstateItemList = new ArrayList<ReinstateItemVO>();
				for (TRiContReinstateItem item : bo.getReinBO().getTRiContReinstateItems()) {
					if (CollectionUtils.isNotEmpty(vo.getReinstateList())) {
						ReinstateItemVO reinstateItemVO = new ReinstateItemVO();
						BeanUtils.copyProperties(reinstateItemVO, item);
						boolean isInclude = false;
						if (CollectionUtils.isNotEmpty(vo.getReinstateList())) {
							for (ReinstateItemVO itemForLog : vo.getReinstateList()) {
								if (itemForLog.getItemId().compareTo(item.getItemId()) == 0) {
									isInclude = true;
									break;
								}
							}
						}
						if (!isInclude) {
							deleteReinstateItemList.add(reinstateItemVO);
						}
					}
				}
				vo.setDeleteReinstateItemList(deleteReinstateItemList);
			}
		}
	}

	/**
	 * if the current contract/section/subsection has been inforce then return
	 * true, else return false
	 * 
	 * @param contCompId
	 * @return
	 */
	public boolean checkTheEntityHasBennInforce(Long contCompId) {
		String sql = "SELECT COUNT(1) FROM T_RI_CONTRACT_STRUCTURE_LOG T1, T_RI_CONTRACT_INFO_LOG T2 WHERE T1.OPERATE_ID = T2.OPERATE_ID AND T2.LATEST_STATUS = '"
				+ ContractCst.CONTRACT_STATUS_INFORCE + "' AND T1.CONT_COMP_ID = ?";
		JdbcTemplate jdbc = SpringContextUtils.getJdbcTemplate();
		Object[] param = new Object[] { contCompId };
		return jdbc.queryForObject(sql, param, Integer.class) > 0;
	}

	private String getContractCodeDescription(long codeTable, String codeId) {
		if (!StringUtils.isNullOrEmpty(codeId)) {
			try {
				return codeTableService.findCodeTableValueByCode$CodeTableId(codeId, Long.valueOf(codeTable), null)
						.getDescription();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * format PDF Date
	 * 
	 * @author Chuan.ye
	 * @param date
	 * @retur string
	 */
	public String formatPDFDate(Date date) {
		SimpleDateFormat dateFormater = new SimpleDateFormat("dd.MM.yyyy");
		return dateFormater.format(date);
	}

	/**
	 * format Percent Number
	 * 
	 * @author Chuan.ye
	 * @param objNumber
	 * @retur string
	 */
	public String formatPercentNumber(Object objNumber) {
		if (objNumber == null)
			return "";
		NumberFormat num = NumberFormat.getPercentInstance();
		num.setMaximumIntegerDigits(3);
		num.setMaximumFractionDigits(2);
		return num.format(objNumber);
	}

	private String formatStringWithSlash(String str, boolean isReplace) {
		List<String> list = new ArrayList<String>();
		String[] arrayStr = new String[] {};
		if (str != null) {
			list = DataTypeConvertor.splitStringToListBySlash(str);
		} else {
			return "-";
		}

		boolean isAllNull = true;
		for (String item : list) {
			if (!"null".equals(item)) {
				isAllNull = false;
				break;
			}
		}
		if (isAllNull) {
			return "-";
		}

		if (isReplace) {
			return str.replaceAll("null", "-");
		}
		return str;
	}

	@SuppressWarnings("deprecation")
	public void updateContRetroInfo(Long contCompId) throws Exception {
		// ContCompId artf218424
		List<TRiContRetro> contRetroList = retrocessionDao.getCoveredSectionList(contCompId);
		if (CollectionUtils.isNotEmpty(contRetroList)) {
			for (TRiContRetro cr : contRetroList) {
				RetrocessionItemVO vo = retrocessionDS.generateRetrocession(contCompId);
				// BeanUtils.copyProperties(cr,vo);
				cr.setBriefText(vo.getBriefText());
				cr.setContractNature(vo.getContractNature());
				cr.setRetroSectionName(vo.getRetroSectionName());
				cr.setLimitType(vo.getLimitType());
				retrocessionDao.merge(cr);
			}
		}
	}

}
