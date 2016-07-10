package com.ebao.ap99.contract.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ebao.ap99.accounting.AccountingService;
import com.ebao.ap99.accounting.ContractPremiumModel;
import com.ebao.ap99.accounting.SectionPremiumModel;
import com.ebao.ap99.accounting.SoaService;
import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.accounting.model.SoaCurrencyNonPropVO;
import com.ebao.ap99.accounting.model.SoaNonPropVO;
import com.ebao.ap99.accounting.model.SoaSectionItemNonPropVO;
import com.ebao.ap99.accounting.model.SoaSectionNonPropVO;
import com.ebao.ap99.arap.service.CurrencyExchangeService;
import com.ebao.ap99.contract.convertor.PricingEstimateModelConvertor;
import com.ebao.ap99.contract.dao.TRiContCurrencyDao;
import com.ebao.ap99.contract.dao.TRiContDeductionsDao;
import com.ebao.ap99.contract.dao.TRiContOperationDao;
import com.ebao.ap99.contract.dao.TRiContPremiumItemDao;
import com.ebao.ap99.contract.dao.TRiContPricingDao;
import com.ebao.ap99.contract.dao.TRiContPricingItemLogDao;
import com.ebao.ap99.contract.dao.TRiContPricingLogDao;
import com.ebao.ap99.contract.dao.TRiContSectionInfoDao;
import com.ebao.ap99.contract.dao.TRiContractInfoDao;
import com.ebao.ap99.contract.dao.TRiContractStructureDao;
import com.ebao.ap99.contract.entity.TRiContDeductions;
import com.ebao.ap99.contract.entity.TRiContDeductionsItem;
import com.ebao.ap99.contract.entity.TRiContOperation;
import com.ebao.ap99.contract.entity.TRiContPremiumItem;
import com.ebao.ap99.contract.entity.TRiContPricing;
import com.ebao.ap99.contract.entity.TRiContPricingItem;
import com.ebao.ap99.contract.entity.TRiContPricingItemLog;
import com.ebao.ap99.contract.entity.TRiContPricingLog;
import com.ebao.ap99.contract.entity.TRiContSectionInfo;
import com.ebao.ap99.contract.entity.TRiContractInfo;
import com.ebao.ap99.contract.entity.TRiContractStructure;
import com.ebao.ap99.contract.model.PricingEstimateItemVO;
import com.ebao.ap99.contract.model.PricingEstimateVO;
import com.ebao.ap99.contract.model.BusinessModel.BusinessConditionBO;
import com.ebao.ap99.contract.model.BusinessModel.PricingEstimateBO;
import com.ebao.ap99.contract.service.BusinessConditionDS;
import com.ebao.ap99.contract.service.ContractPricingDS;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.contract.util.ContractCst;
import com.ebao.ap99.parent.constant.ReInsuranceCst;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.context.SpringContextUtils;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class ContractPricingDSImpl implements ContractPricingDS {
	private static Logger logger = LoggerFactory.getLogger();
	private String xolSearchCondition = "  FROM T_RI_CONT_PREMIUM_ITEM T1\n" + " INNER JOIN T_RI_CONT_PREMIUM T2\n"
			+ "    ON T1.PREMIUM_ID = T2.PREMIUM_ID\n" + " INNER JOIN T_RI_CONTRACT_STRUCTURE T3\n"
			+ "    ON T3.CONT_COMP_ID = T2.CONT_COMP_ID\n" + "   AND T3.TYPE = 2\n"
			+ "   AND INSTR(CONCAT(IS_WITHDRAW, 'XX'), 'Y') = 0\n" + " INNER JOIN T_RI_CONTRACT_INFO T4\n"
			+ "    ON T4.CONT_COMP_ID = T3.PARENT_ID\n" + "   AND T4.LATEST_STATUS IN (4, 5)\n"
			+ " WHERE T1.ITEM_TYPE IN (9, 10)\n" + "   AND T1.DUE_DATE <= TO_DATE(?, 'YYYY-MM-DD')\n"
			+ "   AND INSTR(CONCAT(POST_SOA, 'XX'), 'Y') = 0";
	@Autowired
	private TRiContPricingDao pricingDao;
	@Autowired
	private TRiContPricingLogDao pricingLogDao;
	@Autowired
	private TRiContPricingItemLogDao pricingItemLogDao;

	@Autowired
	private TRiContractInfoDao contractInfoDao;

	@Autowired
	private TRiContractStructureDao structureDao;

	@Autowired
	private TRiContCurrencyDao currencyDao;

	@Autowired
	private CurrencyExchangeService currencyExchenageService;

	@Autowired
	private TRiContSectionInfoDao sectionDao;

	@Autowired
	private TRiContPremiumItemDao premiumItemDao;

	@Autowired
	private TRiContDeductionsDao deductionsDao;

	@Autowired
	private TRiContOperationDao operationDao;

	@Autowired
	private SoaService soaService;

	@Autowired
	private BusinessConditionDS conditionDS;

	@Autowired
	private AccountingService accountingService;

	@Autowired
	private ContractService contractService;

	@Override
	public PricingEstimateBO loadPricingEstimateBO(Long contCompId) {
		PricingEstimateBO bo = new PricingEstimateBO();
		TRiContPricing entity = pricingDao.loadByContCompId(contCompId);
		BeanUtils.copyProperties(entity, bo);
		if (null != bo.getTRiContPricingItems()) {
			for (TRiContPricingItem item : bo.getTRiContPricingItems()) {
				item.setPricingDate(AppContext.getSysDate());
			}
		}
		return bo;
	}

	public PricingEstimateVO loadPricingVOFromSection(Long contCompId) throws Exception {
		PricingEstimateVO estimateVO = new PricingEstimateVO();
		TRiContSectionInfo section = sectionDao.load(contCompId);
		Long contractId = structureDao.load(contCompId).getParentId();
		estimateVO.setSectionName(section.getSectionName());
		estimateVO.setContCompId(contCompId);
		estimateVO.setParentId(contractId);
		estimateVO.setActualized(ReInsuranceCst.BASE_NO);
		estimateVO.getPricingItemList().add(loadPricingEstimateVOFromSection(contCompId));
		return estimateVO;
	}

	public PricingEstimateItemVO loadPricingEstimateVOFromSection(Long contCompId) throws Exception {
		PricingEstimateItemVO itemVO = new PricingEstimateItemVO();
		itemVO.setPricingDate(AppContext.getSysDate());
		Long contractId = structureDao.load(contCompId).getParentId();
		TRiContractInfo contractInfo = contractInfoDao.load(contractId);
		// Long underwriting = contractInfo.getUnderwriting();
		// itemVO.setUnderwriter(underwriting);
		// initial UserBy
		itemVO.setUnderwriter(AppContext.getCurrentUser().getUserId());
		String mainCurrency = ContractCst.CONTRACT_DEFAULT_CURRENCY;
		List<String> currencyList = currencyDao.loadMainCurrencyList(contCompId);
		if (null != currencyList && currencyList.size() == 1) {
			mainCurrency = currencyList.get(0);
		}
		itemVO.setCurrency(mainCurrency);
		if (ContractCst.CONTRACT_NATURE_PROPORTIONAL.equals(contractInfo.getContractNature())) {
			// Get section level's businessCondition info
			BusinessConditionBO bcBO = conditionDS.loadBusinessConditionBO(contCompId);
			if (null != bcBO) {
				// EPI
				if (bcBO.getPremiumBO() != null) {
					itemVO.setEpi(conditionDS.getPremium(bcBO.getCurrencyBO(), bcBO.getPremiumBO()));
				}

				// Commission & Brokerage & Taxes
				TRiContDeductions deductions = deductionsDao.loadByContCompId(contCompId);
				BigDecimal taxsAndOthers = null;
				if (null != deductions) {
					itemVO.setCommission(deductions.getRiPercentage());
					itemVO.setBrokerage(deductions.getPercentOfPremium() == null ? conditionDS
							.transAmountToPercentage(deductions.getFixedAmountHunredPercent(), itemVO.getEpi())
							: deductions.getPercentOfPremium());
					if (CollectionUtils.isNotEmpty(deductions.getTRiContDeductionsItems())) {
						for (TRiContDeductionsItem item : deductions.getTRiContDeductionsItems()) {
							if (null != item.getPercentage() || null != item.getAmountPercent()) {
								taxsAndOthers = BigDecimal.ZERO;
								break;
							}
						}
						if (null != taxsAndOthers) {
							for (TRiContDeductionsItem deductionItem : deductions.getTRiContDeductionsItems()) {
								BigDecimal percentage = deductionItem.getPercentage() == null ? conditionDS
										.transAmountToPercentage(deductionItem.getAmountPercent(), itemVO.getEpi())
										: deductionItem.getPercentage();
								taxsAndOthers = taxsAndOthers.add(percentage == null ? BigDecimal.ZERO : percentage);
							}
						}
					}
					itemVO.setTaxs(taxsAndOthers);
				}
			}
		}

		itemVO.setLossRatio(BigDecimal.ONE);

		return itemVO;
	}

	@Override
	public PricingEstimateVO savePricingEstimateVO(PricingEstimateVO estimateVO) throws Exception {
		PricingEstimateBO bo = new PricingEstimateBO();
		PricingEstimateModelConvertor.convertVOToBO(bo, estimateVO);
		bo = savePricingEstimateBO(bo);
		PricingEstimateModelConvertor.convertBOToVO(bo, estimateVO);
		return estimateVO;
	}

	@Override
	public PricingEstimateVO savePricingEstimateVOWithCalculate(PricingEstimateVO estimateVO) throws Exception {
		estimateVO = savePricingEstimateVO(estimateVO);
		List<Long> contract = contractService.getLevelContCompIdList(estimateVO.getContCompId(),
				ContractCst.CONTRACT_LEVEL);
		TRiContractInfo contractInfo = contractInfoDao.load(contract.get(0));
		if (null != contractInfo && ContractCst.CONTRACT_NATURE_PROPORTIONAL.equals(contractInfo.getContractNature())) {
			accountingService.afterPricingEstimation(estimateVO.getContCompId());
		}

		TRiContOperation operation = new TRiContOperation(estimateVO.getContCompId(),
				ContractCst.CONTRACT_OPERATE_PRICING_ESTIMATE, null, null);
		operationDao.insert(operation);
		TRiContPricing pricingEntity = pricingDao.load(estimateVO.getPricingId());
		pricingDao.saveLogForPricingInfo(pricingEntity, operation.getOperateId());
		List<Long> adjustmentSectionList = new ArrayList<Long>();
		adjustmentSectionList.add(estimateVO.getContCompId());
		return estimateVO;
	}

	public PricingEstimateBO savePricingEstimateBO(PricingEstimateBO estimateBO) {
		TRiContPricing entity = new TRiContPricing();
		BeanUtils.copyProperties(estimateBO, entity);
		if (CollectionUtils.isNotEmpty(entity.getTRiContPricingItems())) {
			for (TRiContPricingItem items : entity.getTRiContPricingItems()) {
				items.setTRiContPricing(entity);
			}
		}
		entity = pricingDao.save(entity);
		BeanUtils.copyProperties(entity, estimateBO);
		return estimateBO;
	}

	/**
	 * generate premium fee model for inforce contract
	 * 
	 * @param contCompId
	 * @return
	 * @throws Exception
	 */
	public ContractPremiumModel generateAccountingPremiumModel(Long contCompId, Long sectionCompId) throws Exception {
		ContractPremiumModel model = new ContractPremiumModel();
		model.setContractId(contCompId);
		TRiContractInfo info = contractInfoDao.load(contCompId);
		model.setNature(Integer.valueOf(info.getContractNature()));
		model.setType(Integer.valueOf(info.getContractType()));
		model.setPeriodStart(info.getReinsStarting());
		model.setPeriodEnd(info.getReinsEnding());
		List<SectionPremiumModel> sectionPremiumList = new ArrayList<SectionPremiumModel>();
		sectionPremiumList.add(generatePremiumModel(contCompId, sectionCompId));
		model.setSectionPremList(sectionPremiumList);
		return model;
	}

	private SectionPremiumModel generatePremiumModel(Long contCompId, Long sectionId) throws Exception {
		SectionPremiumModel model = new SectionPremiumModel();
		model.setSectionId(sectionId);
		model.setContractId(contCompId);
		String mainCurrency = ContractCst.CONTRACT_DEFAULT_CURRENCY;
		List<String> currencyList = currencyDao.loadMainCurrencyList(sectionId);
		if (null != currencyList && currencyList.size() == 1) {
			mainCurrency = currencyList.get(0);
		}
		model.setCurrency(mainCurrency);
		Map<String, BigDecimal> entryItemMap = generateEntryItems(sectionId, mainCurrency);
		if (null != entryItemMap && !entryItemMap.isEmpty()) {
			model.setFlowItems(generateEntryItems(sectionId, mainCurrency));
			model.setTotalExpenseRate(model.getFlowItems().get("totalExchangeRate"));
			model.setLossRatio(model.getFlowItems().get("lossRatio"));
			// Remove unUsed Entry Item Code
			model.getFlowItems().remove("totalExchangeRate");
			model.getFlowItems().remove("lossRatio");
		}
		return model;
	}

	/**
	 * @param sectionId
	 * @param mainCurrency
	 * @return
	 * @throws Exception
	 */
	private Map<String, BigDecimal> generateEntryItems(Long sectionId, String mainCurrency) throws Exception {
		Map<String, BigDecimal> entryItems = new HashMap<String, BigDecimal>();
		TRiContPricing pricing = pricingDao.loadByContCompId(sectionId);
		if (null != pricing && pricing.getPricingId() != null) {
			BigDecimal premiumFee = BigDecimal.ZERO;
			BigDecimal commission = BigDecimal.ZERO;
			BigDecimal brokerCommission = BigDecimal.ZERO;
			BigDecimal taxFee = BigDecimal.ZERO;
			BigDecimal lossRatio = BigDecimal.ZERO;
			BigDecimal totalExchangeRate = BigDecimal.ZERO;
			List<TRiContPricingItem> premiumItems = pricing.getTRiContPricingItems();
			for (TRiContPricingItem premiumItem : premiumItems) {
				BigDecimal exchangeRate = currencyExchenageService.getExchangeRate(premiumItem.getCurrency(),
						mainCurrency);
				premiumFee = premiumFee.add(premiumItem.getEpi().multiply(exchangeRate));
				commission = commission
						.add(premiumItem.getEpi().multiply(premiumItem.getCommission()).multiply(exchangeRate));
				brokerCommission = brokerCommission
						.add(premiumItem.getEpi().multiply(premiumItem.getBrokerage()).multiply(exchangeRate));
				taxFee = taxFee.add(premiumItem.getEpi().multiply(premiumItem.getTaxs()).multiply(exchangeRate));
				lossRatio = premiumItem.getLossRatio();
			}
			entryItems.put(ContractCst.ENTRY_PROPORTIONAL_PREMIUM_FEE, premiumFee);
			entryItems.put(ContractCst.ENTRY_COMMISSION_FEE, commission);
			entryItems.put(ContractCst.ENTRY_BROKERAGE_FEE, brokerCommission);
			entryItems.put(ContractCst.ENTRY_TAX_EXPENSE_FEE, taxFee);
			if (premiumFee.compareTo(BigDecimal.ZERO) != 0) {
				totalExchangeRate = commission.add(brokerCommission).add(taxFee).divide(premiumFee);
			}
			entryItems.put("totalExchangeRate", totalExchangeRate);
			entryItems.put("lossRatio", lossRatio);
		}
		return entryItems;
	}

	/**
	 * load the soa non-proportional info by date
	 * 
	 * @param date
	 */
	public List<SoaNonPropVO> generateSOANonProportionInfo(Date date) throws Exception {
		List<SoaNonPropVO> soaList = new ArrayList<SoaNonPropVO>();
		List<TRiContractInfo> contracts = this.loadAllNeedPostXOLContract(date);
		YearQuarter yearQuarter = new YearQuarter(date);
		for (TRiContractInfo info : contracts) {
			SoaNonPropVO soaVO = new SoaNonPropVO();
			soaVO.setBroke(info.getBroker());
			soaVO.setCedent(info.getCedent());
			soaVO.setContractCode(info.getContractCode());
			soaVO.setDueDate(date);
			soaVO.setUwYear(info.getUwYear());
			soaVO.setReceivedDate(date);
			soaVO.setContractNature(info.getContractNature());
			soaVO.setTaskCreator(AppContext.getAppUser().getUserId());
			soaVO.setCedentYear(yearQuarter.getYear());
			soaVO.setCedentQuarter(yearQuarter.getQuarter());
			soaVO.setSoaText("Non-Portional MDP Fee");
			SoaCurrencyNonPropVO currencyVO = new SoaCurrencyNonPropVO();
			currencyVO.setCurrencyType(info.getMainCurrency());
			List<Long> sectionIds = structureDao.getChildrenIdList(info.getContCompId());
			for (Long sectionId : sectionIds) {
				SoaSectionNonPropVO propVO = getSectionNonpropVO(date, sectionId, info.getMainCurrency());
				if (null != propVO) {
					currencyVO.getSections().add(propVO);
				}
			}
			soaVO.getCurrencies().add(currencyVO);
			soaList.add(soaVO);
		}
		return soaList;
	}

	public SoaSectionNonPropVO getSectionNonpropVO(Date date, Long sectionId, String mainCurrency) throws Exception {
		List<TRiContPremiumItem> premiumItemList = this.loadCutOffDepositPremium(date, sectionId);
		if (null != premiumItemList && premiumItemList.size() > 0) {
			SoaSectionNonPropVO propVO = new SoaSectionNonPropVO();
			TRiContractStructure sectionStructure = structureDao.load(sectionId);
			propVO.setContractSection(sectionStructure.getFullName());
			propVO.setContCompId(sectionId);
			TRiContSectionInfo sectionInfo = sectionDao.load(sectionId);
			propVO.setShareType(sectionInfo.getShareType());
			// TODO need confirm the cob&uwArea logic
			propVO.setCob(sectionInfo.getCoB());
			propVO.setUwArea(sectionInfo.getUwArea());
			for (TRiContPremiumItem item : premiumItemList) {
				SoaSectionItemNonPropVO entryItem = new SoaSectionItemNonPropVO();
				entryItem.setEntryCode(ContractCst.ENTRY_XOL_PREMIUM_FEE);
				BigDecimal exchangeRate = currencyExchenageService.getExchangeRate(item.getCurrency(), mainCurrency);
				BigDecimal amount = item.getDepositAmount().multiply(exchangeRate);
				entryItem.setAmount(amount);
				// TODO confirm the ShareAmount/Percentage/OurShareAmount
				propVO.getEntryItems().add(entryItem);
			}

			return propVO;
		}
		return null;
	}

	/**
	 * Search the inforce non-proportional contract premium item until the Date
	 * exclude : 1. the section has been with draw 2. the premium has been send
	 * to SOA success
	 * 
	 * @param date
	 * @return
	 */
	public List<TRiContPremiumItem> loadCutOffDepositPremium(Date date, Long sectionId) {
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		List<TRiContPremiumItem> premiumItemList = new ArrayList<TRiContPremiumItem>();
		String sql = "SELECT T1.* \n" + xolSearchCondition + " AND T3.CONT_COMP_ID = ?";
		logger.debug("load the cut-off deposit premium ===" + sql);
		Object[] param = new Object[] { date, sectionId };
		premiumItemList = jt.queryForList(sql, param, TRiContPremiumItem.class);
		return premiumItemList;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public List<TRiContractInfo> loadAllNeedPostXOLContract(Date date) {
		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		List<TRiContractInfo> contracts = new ArrayList<TRiContractInfo>();
		String sql = "SELECT DISTINCT T4.* \n" + xolSearchCondition;
		Object[] param = new Object[] { date };
		contracts = jt.queryForList(sql, param, TRiContractInfo.class);
		return contracts;
	}

	public void transSOAToAccounting(List<SoaNonPropVO> propVOList) {
		for (SoaNonPropVO propVO : propVOList) {
			List<SoaSectionNonPropVO> sections = propVO.getCurrencies().get(0).getSections();
			try {
				soaService.createSoaModelForNonProportionalContract(propVO);
				resetPostSoaFlagBySection(sections, propVO.getReceivedDate(), true);
			} catch (Exception e) {
				resetPostSoaFlagBySection(sections, propVO.getReceivedDate(), false);
			}
		}
	}

	public void resetPostSoaFlagBySection(List<SoaSectionNonPropVO> sections, Date date, Boolean isPost) {
		for (SoaSectionNonPropVO sectionVO : sections) {
			List<TRiContPremiumItem> itemList = loadCutOffDepositPremium(date, sectionVO.getContCompId());
			for (TRiContPremiumItem item : itemList) {
				item.setPostSoa(isPost);
				premiumItemDao.merge(item);
			}
		}
	}

	@Override
	public PricingEstimateBO loadPricingEstimateBOForLog(Long contCompId, Long operateId) throws Exception {
		PricingEstimateBO bo = new PricingEstimateBO();
		TRiContPricingLog entity = pricingLogDao.getLatestPricingForLog(contCompId, operateId);
		if (null != entity) {
			BeanUtils.copyProperties(entity, bo);
			List<TRiContPricingItemLog> itemLogList = pricingItemLogDao
					.getItemLogByPricingIdAndOperateId(entity.getPricingId(), entity.getOperateId());
			if (CollectionUtils.isNotEmpty(itemLogList)) {
				bo.setTRiContPricingItems(
						com.ebao.ap99.parent.BeanUtils.convertList(itemLogList, TRiContPricingItem.class));
			}
		}
		return bo;
	}
}
