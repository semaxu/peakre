/**
 * 
 */
package com.ebao.ap99.claim.convertor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.model.ClaimEntryVO;
import com.ebao.ap99.arap.vo.BusinessFee;
import com.ebao.ap99.arap.vo.BusinessFeeModel;
import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.dao.RiClaimEventDao;
import com.ebao.ap99.claim.dao.RiClaimInfoDao;
import com.ebao.ap99.claim.dao.RiClaimSectionDao;
import com.ebao.ap99.claim.dao.RiClaimSettlementItemDao;
import com.ebao.ap99.claim.entity.TRiClaimEvent;
import com.ebao.ap99.claim.entity.TRiClaimInfo;
import com.ebao.ap99.claim.entity.TRiClaimSettlement;
import com.ebao.ap99.claim.entity.TRiClaimSettlementItem;
import com.ebao.ap99.claim.model.SettlementHistoryInfo;
import com.ebao.ap99.claim.model.SettlementHistoryQuery;
import com.ebao.ap99.claim.model.SettlementItemInfo;
import com.ebao.ap99.claim.model.SettlementSummaryInfo;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

/**
 * @author gang.wang
 *
 */
public class SettlementItemInfoConvertor {

	@Autowired
	public RiClaimSettlementItemDao settlementItemDao;
	@Autowired
	private RiClaimSectionDao claimSectionDao;
	@Autowired
	private EntryCodeConvertor entryCodeConvertor;
	@Autowired
	private RiClaimInfoDao claimInfoDao;
	@Autowired
	private RiClaimEventDao eventDao;
	@Autowired
	public ContractService contractService;

	public TRiClaimSettlementItem modelToEntity(SettlementItemInfo settleDetailInfo) {
		TRiClaimSettlementItem entity = new TRiClaimSettlementItem();

		BeanUtils.copyProperties(entity, settleDetailInfo);

		return entity;
	}

	public SettlementItemInfo entityToModel(TRiClaimSettlementItem entity) {
		SettlementItemInfo settleDetailInfo = new SettlementItemInfo();
		BeanUtils.copyProperties(settleDetailInfo, entity);

		return settleDetailInfo;
	}

	public void convertToEntityList(List<SettlementItemInfo> settleDetailInfoList, TRiClaimSettlement settlementEntity,
			String businessDirection) {
		for (SettlementItemInfo settleDetailInfo : settleDetailInfoList) {
			TRiClaimSettlementItem entity = modelToEntity(settleDetailInfo);
			entity.setBusinessDirection(businessDirection);
			settlementEntity.addTRiClaimSettlementItem(entity);
		}
	}

	public List<SettlementItemInfo> entityListToModelList(List<TRiClaimSettlementItem> entityList) {
		List<SettlementItemInfo> settleDetailInfoList = new ArrayList<SettlementItemInfo>();
		entityList.forEach((TRiClaimSettlementItem entity) -> {
			settleDetailInfoList.add(entityToModel(entity));
		});

		return settleDetailInfoList;
	}

	public List<TRiClaimSettlementItem> modelListToEntityList(List<SettlementItemInfo> modelList) {
		List<TRiClaimSettlementItem> settleDetailInfoList = new ArrayList<TRiClaimSettlementItem>();
		modelList.forEach((SettlementItemInfo model) -> {
			settleDetailInfoList.add(modelToEntity(model));
		});

		return settleDetailInfoList;
	}

	// public List<SettlementSummaryInfo>
	// calSettlementItemSummary(List<SettlementHistoryInfo> settlementItemList)
	// {
	//
	// if (null == settlementItemList || settlementItemList.size() == 0)
	// return null;
	//
	// List<SettlementSummaryInfo> settlementItemSummary = new
	// ArrayList<SettlementSummaryInfo>();
	//
	// List<String> currencyList = settlementItemList.stream().filter(r ->
	// r.getOriginalCurrency() != null)
	// .map(r ->
	// r.getOriginalCurrency()).distinct().collect(Collectors.toList());
	//
	// for (String currency : currencyList) {
	// Double amountOneCurrency = settlementItemList.parallelStream()
	// .filter(r -> r.getOriginalCurrency() != null &&
	// r.getOriginalCurrency().equals(currency)).collect(Collectors.summingDouble(SettlementHistoryInfo::getAmountOcDouble));
	// SettlementSummaryInfo summary = new SettlementSummaryInfo();
	// summary.setCurrencyType(currency);
	// summary.setGrossTotal(amountOneCurrency);
	// settlementItemSummary.add(summary);
	// }
	// double
	// reportingSummary=calReportingSettlementSummary(settlementItemList);
	// SettlementSummaryInfo summary = new SettlementSummaryInfo();
	// summary.setCurrencyType("USD Equivalent");
	// summary.setGrossTotal(reportingSummary);
	// settlementItemSummary.add(summary);
	//
	// return settlementItemSummary;
	// }
	//

	public List<SettlementSummaryInfo> calSettlementItemSummary(SettlementHistoryQuery settlementQuery) {
		List<SettlementHistoryInfo> settlementItemList = settlementItemDao.getSettlementSummary(settlementQuery);
		if (null == settlementItemList || settlementItemList.size() == 0)
			return null;

		List<SettlementSummaryInfo> settlementItemSummary = new ArrayList<SettlementSummaryInfo>();
		for (SettlementHistoryInfo se : settlementItemList) {
			SettlementSummaryInfo summary = new SettlementSummaryInfo();
			summary.setCurrencyType(se.getOriginalCurrency());
			summary.setGrossTotal(se.getAmountOc().doubleValue());
			settlementItemSummary.add(summary);
		}
		double reportingSummary = calReportingSettlementSummary(settlementItemList);
		SettlementSummaryInfo summary = new SettlementSummaryInfo();
		summary.setCurrencyType("USD Equivalent");
		summary.setGrossTotal(reportingSummary);
		settlementItemSummary.add(summary);

		return settlementItemSummary;
	}

	public double calReportingSettlementSummary(List<SettlementHistoryInfo> settlementItemList) {
		if (null == settlementItemList || settlementItemList.size() == 0)
			return 0;
		double reportingSummary = 0;

		for (SettlementHistoryInfo rl : settlementItemList) {

			reportingSummary = reportingSummary + rl.getAmountUsd().doubleValue();
		}
		return reportingSummary;

	}

	/**
	 * new settlement approved PostingAccounting
	 * 
	 * @param settlement
	 * @param settlementItem
	 * @return
	 */
	public ClaimEntryVO settlementPostingAccounting(TRiClaimSettlement settlement,
			TRiClaimSettlementItem settlementItem) {
		String refNo;
		Date dateOfLoss;
		Long contractId = claimSectionDao.getContractIdBySectionId(settlementItem.getSectionId().longValue());
		if (settlement.getRefType().equals(ClaimConstant.REF_TYPE_CLAIM)) {
			TRiClaimInfo claimEntity = claimInfoDao.load(settlement.getRefId());
			refNo = claimEntity.getClaimNo();
			dateOfLoss = claimEntity.getDateOfLossFrom();
		} else {
			TRiClaimEvent eventEntity = eventDao.load(settlement.getRefId());
			refNo = eventEntity.getEventCode();
			dateOfLoss = eventEntity.getDateOfLossFrom();
		}

		ClaimEntryVO claimEntry = new ClaimEntryVO();
		claimEntry.setClaimNo(refNo);
		claimEntry.setContractId(contractId);
		claimEntry.setDateOfLoss(dateOfLoss);
		claimEntry.setEntryCode(entryCodeConvertor.conertorEntryCode(settlementItem.getSettlementType(),
				ClaimConstant.BUSI_TYPE_SETTLEMENT, ""));
		claimEntry.setSectionId(contractService.getLevelContCompIdList(settlementItem.getSectionId().longValue(), "2").get(0));
		//contractService.getLevelContCompIdList(reserve.getSectionId(), "2").get(0)
		//settlementItem.getSectionId().longValue()
		claimEntry.setCurrency(settlementItem.getOriginalCurrency());
		claimEntry.setAmount(settlementItem.getAmountOc());
		claimEntry.setBusinessDirection(settlementItem.getBusinessDirection());
		claimEntry.setBizTransType(ClaimConstant.BIZ_CLAIM_SETTLEMENT);
		claimEntry.setRefType(settlement.getRefType());
		claimEntry.setSettlementNo(settlement.getSettlementName());


		return claimEntry;
	}

	/**
	 * change posting PostingAccounting
	 * 
	 * @param settlement
	 * @param settlementItem
	 * @return
	 */
	public ClaimEntryVO settlementChangePostingAccounting(TRiClaimSettlement settlement,
			TRiClaimSettlementItem settlementItem) {
		String refNo;
		Date dateOfLoss;
		Long contractId = claimSectionDao.getContractIdBySectionId(settlementItem.getSectionId().longValue());
		if (settlement.getRefType().equals(ClaimConstant.REF_TYPE_CLAIM)) {
			TRiClaimInfo claimEntity = claimInfoDao.load(settlement.getRefId());
			refNo = claimEntity.getClaimNo();
			dateOfLoss = claimEntity.getDateOfLossFrom();
		} else {
			TRiClaimEvent eventEntity = eventDao.load(settlement.getRefId());
			refNo = eventEntity.getEventCode();
			dateOfLoss = eventEntity.getDateOfLossFrom();
		}


		ClaimEntryVO claimEntry = new ClaimEntryVO();
		claimEntry.setClaimNo(refNo);
		claimEntry.setContractId(contractId);
		claimEntry.setDateOfLoss(dateOfLoss);
		claimEntry.setEntryCode(entryCodeConvertor.conertorEntryCode(settlementItem.getSettlementType(),
				ClaimConstant.BUSI_TYPE_SETTLEMENT, ""));
		//claimEntry.setSectionId(settlementItem.getSectionId().longValue());
		claimEntry.setSectionId(contractService.getLevelContCompIdList(settlementItem.getSectionId().longValue(), "2").get(0));
		claimEntry.setCurrency(settlementItem.getOriginalCurrency());
		claimEntry.setBusinessDirection(settlementItem.getBusinessDirection());
		claimEntry.setBizTransType(ClaimConstant.BIZ_CLAIM_SETTLEMENT);
		claimEntry.setRefType(settlement.getRefType());
		claimEntry.setSettlementNo(settlement.getSettlementName());
		if (settlementItem.getPostingFlag().equals(ClaimConstant.CLAIM_POSTING_FLAG_YES)) {
			// set sendAmount = settlementItem.amountOC
			claimEntry.setAmount(settlementItem.getAmountOc());
		} else if (settlementItem.getPostingFlag().equals(ClaimConstant.CLAIM_POSTING_FLAG_NO)) {
			// set sendAmount= - settlementItem.amountOC
			claimEntry.setAmount(settlementItem.getAmountOc().multiply(new BigDecimal(-1)));
		}

		return claimEntry;
	}

	/**
	 * new settlement approved
	 * 
	 * @param settlement
	 * @param settlementItemList
	 * @return
	 */
	public BusinessFeeModel settlementConvetorPosting(TRiClaimSettlement settlement,
			List<TRiClaimSettlementItem> settlementItemList) {

		Long contractId = claimSectionDao
				.getContractIdBySectionId(settlementItemList.get(0).getSectionId().longValue());

		BusinessFeeModel businessFeeModel = new BusinessFeeModel();
		if (settlement.getBusinessDirection().equals(ClaimConstant.CLAIM_BUSINESS_DIRECTION__FINANCIAL)) {
			businessFeeModel.setContractCategory(ClaimConstant.CONTRACTCATEGORY_ASSUMED);
		} else {
			businessFeeModel.setContractCategory(ClaimConstant.CONTRACTCATEGORY_RETRO);
		}
		businessFeeModel.setBizTransType(ClaimConstant.BIZ_CLAIM_SETTLEMENT);
		businessFeeModel.setBizTransNo(settlement.getSettlementName());
		businessFeeModel.setBizTransId(settlement.getSettlementId());
		businessFeeModel.setContractId(contractId);
		businessFeeModel.setBizOperatorId(AppContext.getAppUser().getUserId());
		// businessFeeModel.setPartnerCode(null);

		List<BusinessFee> businessFeeList = getBusinessFeeList(settlementItemList);
		businessFeeModel.setFeeList(businessFeeList);

		return businessFeeModel;
	}

	public List<BusinessFee> getBusinessFeeList(List<TRiClaimSettlementItem> settlementItemList) {
		List<BusinessFee> businessFeeList = new ArrayList<>();

		for (TRiClaimSettlementItem si : settlementItemList) {
			BusinessFee businessFee = new BusinessFee();
			businessFee.setEntryCode(entryCodeConvertor.conertorEntryCode(si.getSettlementType(),
					ClaimConstant.BUSI_TYPE_SETTLEMENT, ""));
			businessFee.setCurrencyCode(si.getOriginalCurrency());
			businessFee.setDueDate(si.getUpdateTime());
			businessFee.setAmount(si.getAmountOc());
			// businessFee.setCurrentPeriod(0);
			// businessFee.setTotalPeriod(0);
			// businessFee.setEstimation(false);
			// businessFee.setNeedPost(true);
			businessFee.setSectionId(si.getSectionId().longValue());
			businessFee.setBizRefId(si.getSettleDetailId());
			businessFeeList.add(businessFee);
		}

		return businessFeeList;
	}

	/**
	 * change posting
	 * 
	 * @param settlement
	 * @param settlementItemList
	 * @return
	 */
	public BusinessFeeModel settlementChangePosting(TRiClaimSettlement settlement,
			List<TRiClaimSettlementItem> settlementItemList) {

		Long contractId = claimSectionDao
				.getContractIdBySectionId(settlementItemList.get(0).getSectionId().longValue());

		BusinessFeeModel businessFeeModel = new BusinessFeeModel();
		if (settlement.getBusinessDirection().equals(ClaimConstant.CLAIM_BUSINESS_DIRECTION__FINANCIAL)) {
			businessFeeModel.setContractCategory(ClaimConstant.CONTRACTCATEGORY_ASSUMED);
		} else {
			businessFeeModel.setContractCategory(ClaimConstant.CONTRACTCATEGORY_RETRO);
		}
		businessFeeModel.setBizTransType(ClaimConstant.BIZ_CLAIM_SETTLEMENT);
		businessFeeModel.setBizTransNo(settlement.getSettlementName());
		businessFeeModel.setBizTransId(settlement.getSettlementId());
		businessFeeModel.setContractId(contractId);
		// businessFeeModel.setPartnerCode(null);

		List<BusinessFee> businessFeeList = getBusinessFeeListChangePosting(settlementItemList);
		businessFeeModel.setFeeList(businessFeeList);

		return businessFeeModel;
	}

	public List<BusinessFee> getBusinessFeeListChangePosting(List<TRiClaimSettlementItem> settlementItemList) {
		List<BusinessFee> businessFeeList = new ArrayList<>();

		for (TRiClaimSettlementItem si : settlementItemList) {
			BusinessFee businessFee = new BusinessFee();
			businessFee.setEntryCode(entryCodeConvertor.conertorEntryCode(si.getSettlementType(),
					ClaimConstant.BUSI_TYPE_SETTLEMENT, ""));
			businessFee.setCurrencyCode(si.getOriginalCurrency());
			businessFee.setDueDate(si.getUpdateTime());
			// businessFee.setCurrentPeriod(0);
			// businessFee.setTotalPeriod(0);
			// businessFee.setEstimation(false);
			// businessFee.setNeedPost(true);
			businessFee.setSectionId(si.getSectionId().longValue());
			businessFee.setBizRefId(si.getSettleDetailId());
			if (si.getPostingFlag().equals(ClaimConstant.CLAIM_POSTING_FLAG_YES)) {
				// set sendAmount = settlementItem.amountOC
				businessFee.setAmount(si.getAmountOc());
			} else if (si.getPostingFlag().equals(ClaimConstant.CLAIM_POSTING_FLAG_NO)) {
				// set sendAmount= - settlementItem.amountOC
				businessFee.setAmount(si.getAmountOc().multiply(new BigDecimal(-1)));
			}
			businessFeeList.add(businessFee);
		}

		return businessFeeList;
	}
}
