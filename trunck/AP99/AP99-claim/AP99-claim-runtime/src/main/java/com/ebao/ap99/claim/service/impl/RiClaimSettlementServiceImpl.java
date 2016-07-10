package com.ebao.ap99.claim.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.AccountingService;
import com.ebao.ap99.accounting.model.ClaimEntryVO;
import com.ebao.ap99.arap.service.FinanceService;
import com.ebao.ap99.arap.vo.BusinessFeeModel;
import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.convertor.SettlementItemInfoConvertor;
import com.ebao.ap99.claim.convertor.SettlementLogConvertor;
import com.ebao.ap99.claim.dao.RiClaimSectionDao;
import com.ebao.ap99.claim.dao.RiClaimSettlementDao;
import com.ebao.ap99.claim.dao.RiClaimSettlementItemDao;
import com.ebao.ap99.claim.entity.TRiClaimSettlement;
import com.ebao.ap99.claim.entity.TRiClaimSettlementItem;
import com.ebao.ap99.claim.entity.TRiClaimSettlementLog;
//import com.ebao.ap99.claim.model.SettlementChangePostingModel;
import com.ebao.ap99.claim.model.SettlementInfo;
import com.ebao.ap99.claim.model.SettlementItemInfo;
import com.ebao.ap99.claim.model.SettlementSummaryInfo;
import com.ebao.ap99.claim.service.RiClaimReserveService;
import com.ebao.ap99.claim.service.RiClaimSettlementItemService;
import com.ebao.ap99.claim.service.RiClaimSettlementLogService;
import com.ebao.ap99.claim.service.RiClaimSettlementService;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.ap99.parent.constant.NumberingFactor;
import com.ebao.ap99.parent.constant.NumberingType;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.numbering.NumberingService;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class RiClaimSettlementServiceImpl implements RiClaimSettlementService {

	private static Logger logger = LoggerFactory.getLogger();
	@Autowired
	private NumberingService numService;
	@Autowired
	private RiClaimSettlementDao claimSettlementDao;
	@Autowired
	private RiClaimSettlementItemDao claimSettlementItemDao;
	@Autowired
	public SettlementLogConvertor settlementLogConvertor;
	@Autowired
	public RiClaimSettlementLogService settlementLogService;
	@Autowired
	public RiClaimSettlementItemService settlementItemService;
	@Autowired
	public SettlementItemInfoConvertor settlementItemInfoConvertor;
	@Autowired
	public FinanceService financeService;

	@Autowired
	public RiClaimReserveService reserveService;

	@Autowired
	public AccountingService accountingService;

	@Autowired
	public RiClaimSectionDao sectionDao;

	@Override
	public List<TRiClaimSettlement> getOutstandingSettleByRefId(long refId) {

		return claimSettlementDao.getOutstandingSettleByRefId(refId);
	}

	// @Override
	// public List<TRiClaimSettlement> getOpenSettleByClaimId(long claimId) {
	// return claimSettlementDao.getOpenSettleByClaimId(claimId);
	// }

	@SuppressWarnings("deprecation")
	@Override
	public TRiClaimSettlement updateSettlement(TRiClaimSettlement settlement, String type) {
		// 2.insert(settlementLog)
		Long userId = AppContext.getCurrentUser().getUserId();
		TRiClaimSettlementLog settlementLog = settlementLogConvertor.settlementToLog(settlement);
		if (type.equals(ClaimConstant.CLAIM_SETTLEMENT__CHANGEPOSTING)
				|| type == ClaimConstant.CLAIM_SETTLEMENT__CHANGEPOSTING) {
			settlementLog.setDesicion("change posting");
			settlementLog.setOperateBy(userId.toString());
			settlementLog.setOperateDate(AppContext.getSysDate());

		} else
			if (type.equals(ClaimConstant.CLAIM_SETTLEMENT__SUBMIT) || type == ClaimConstant.CLAIM_SETTLEMENT__SUBMIT) {
			settlementLog.setDesicion("submit");
			settlementLog.setOperateBy(userId.toString());
			settlementLog.setOperateDate(AppContext.getSysDate());

		} else if (type.equals(ClaimConstant.CLAIM_SETTLEMENT_STATUS__APPROVED)
				|| type == ClaimConstant.CLAIM_SETTLEMENT_STATUS__APPROVED) {
			settlementLog.setDesicion("Approve");
			settlementLog.setOperateBy(userId.toString());
			settlementLog.setOperateDate(AppContext.getSysDate());
		} else if (type.equals(ClaimConstant.CLAIM_SETTLEMENT_STATUS__REJECTED)
				|| type == ClaimConstant.CLAIM_SETTLEMENT_STATUS__REJECTED) {
			settlementLog.setDesicion("Reject");
			settlementLog.setOperateBy(userId.toString());
			settlementLog.setOperateDate(AppContext.getSysDate());
		}
		settlementLog.setTRiClaimSettlement(settlement);
		List<TRiClaimSettlementLog> settlementLogList = new ArrayList<TRiClaimSettlementLog>();
		settlementLogList.add(settlementLog);
		settlement.setTRiClaimSettlementLogs(settlementLogList);

		return claimSettlementDao.merge(settlement);
	}

	@SuppressWarnings("deprecation")
	@Override
	public TRiClaimSettlement updateSettlement(TRiClaimSettlement settlement) {

		return claimSettlementDao.merge(settlement);
	}
	//
	// @Override
	// public void dealSettlement(ClaimInfo claimInfo) {
	//
	// long claimId = claimInfo.getClaimId();
	// List<TRiClaimSettlement> settlements =
	// getOutstandingSettleByRefId(claimId);
	//
	// for (TRiClaimSettlement se : settlements) {
	// long settleId = se.getSettlementId();
	// logger.info("TRiClaimSettlement=============settleId===============" +
	// settleId);
	//
	// // 1.update settlement set status=3, insert settlementLog ,
	// // approve流程后需变更
	// se.setStatus(ClaimConstant.CLAIM_SETTLEMENT_STATUS__APPROVED);
	// updateSettlement(se,ClaimConstant.CLAIM_SETTLEMENT_STATUS__APPROVED);
	//
	// // 2.getSettlementItems by settleId posting_flag=1
	// List<TRiClaimSettlementItem> settlementItems =
	// settlementItemService.getSettlementItemById(settleId);
	// logger.info("TRiClaimSettlement============" + settlementItems.size());
	//
	// if (settlementItems.size() > 0) {
	// // 3.if SettlementItems.size()>0 insert settlementSendtable
	// for (TRiClaimSettlementItem si : settlementItems) {
	// // insert settlementItemSendtable
	// generateFeeforPostingChange(si);
	// }
	//
	// }
	//
	// // 4.update SettlementItems.p_falg = org_p_flag
	// settlementItemService.updateSettlementItemById(settleId);
	//
	// // 5.deal posting method
	// dealPosting(settleId);
	//
	// }
	//
	// }

	@SuppressWarnings("deprecation")
	public String getSettlementName(long RefId) throws Exception {
		//// 2{TRANS_YEAR}6{SEQUENCE}
		Map<String, String> factors = new HashMap<String, String>();
		factors.put(NumberingFactor.TRANS_YEAR, String.valueOf(AppContext.getSysDate().getYear()));

		String settlementNumber = null;
		try {
			settlementNumber = numService.generateNumber(NumberingType.RI_CLAIM_SETTLEMENT_NUMBER, factors);
		} catch (Exception e) {
			logger.error("generate claim number error.");
			throw e;
		}

		logger.info("getsettlementNumber=>" + settlementNumber);
		return settlementNumber;
	}

	@Override
	public List<SettlementSummaryInfo> calcSettlementSummary(List<SettlementItemInfo> settlementItemList) {
		if (null == settlementItemList || settlementItemList.size() == 0)
			return null;

		List<SettlementSummaryInfo> settlementSummary = new ArrayList<SettlementSummaryInfo>();

		List<String> currencyList = settlementItemList.stream().filter(r -> r.getOriginalCurrency() != null)
				.map(r -> r.getOriginalCurrency()).distinct().collect(Collectors.toList());

		for (String currency : currencyList) {
			Map<String, Double> resultOfOneCurrency = settlementItemList.parallelStream()
					.filter(r -> r.getOriginalCurrency() != null && r.getOriginalCurrency().equals(currency))
					.collect(Collectors.groupingBy(SettlementItemInfo::getSettlementType,
							Collectors.summingDouble(SettlementItemInfo::getAmountOcDouble)));

			SettlementSummaryInfo settlementSummaryInfo = new SettlementSummaryInfo(currency, resultOfOneCurrency);
			settlementSummary.add(settlementSummaryInfo);
		}

		Map<String, Double> resultOfUsdEquivalent = settlementItemList.stream()
				.filter(r -> r.getOriginalCurrency() != null)
				.collect(Collectors.groupingBy(SettlementItemInfo::getSettlementType,
						Collectors.summingDouble(SettlementItemInfo::getAmountUsdDouble)));
		SettlementSummaryInfo settlementSummaryInfo = new SettlementSummaryInfo("USD Equivalent",
				resultOfUsdEquivalent);
		settlementSummary.add(settlementSummaryInfo);

		return settlementSummary;
	}

	@Override
	public List<TRiClaimSettlement> getApprovedSettleByRefId(long refId) {
		return claimSettlementDao.getApprovedSettlementByRefId(refId);
	}

	@Override
	public Map<String, Double> getApprovedSettleAmountGroupByCurrency(long claimId) {
		return claimSettlementDao.getApprovedSettleAmountGroupByCurrency(claimId);
	}

	@Override
	public Double getSettlementUsdEquivalent(long claimId) {
		return claimSettlementDao.getSettlementUsdEquivalent(claimId);
	}

	@Override
	public TRiClaimSettlement getSettlement(long settlementId) {
		return claimSettlementDao.load(settlementId);
	}

	@Override
	public void changePosting(SettlementInfo settlementInfo) throws Exception {

		if (settlementInfo != null) {
			settlementInfo.getSettlementItemList().forEach(d -> {
				// String postingFlag = null;
				// List<String> postingList =
				// claimSettlementItemDao.getSettlementPostingFlag(d.getSettleDetailId());
				// for(int index =0;index <postingList.size();index++){
				// postingFlag =postingList.get(index);
				// }
				if (!d.getOrgPostingFlag().equals(d.getPostingFlag())) {
					d.setAmountUsd(reserveService.calculateUsdAmount(d.getOriginalCurrency(), d.getAmountOc()));
					d.setExchangeRate(
							reserveService.getExchangeRate(d.getOriginalCurrency(), ClaimConstant.REPORTING_CURRENCY));
				}
			});
		}

		List<ClaimEntryVO> claimEntryVOList = new ArrayList<>();

		List<TRiClaimSettlementItem> settlementItemList = BeanUtils.convertList(settlementInfo.getSettlementItemList(),
				TRiClaimSettlementItem.class);
		long settlementId = settlementInfo.getSettlementId();

		// update ClaimSettlement ClaimSettlementitems insert
		// ClaimSettlementLog
		TRiClaimSettlement claimSettlement = claimSettlementDao.load(settlementId);
		claimSettlement.setRemark(settlementInfo.getRemark());
		for (TRiClaimSettlementItem s : settlementItemList) {
			s.setTRiClaimSettlement(claimSettlement);
		}
		claimSettlement.setTRiClaimSettlementItems(settlementItemList);
		updateSettlement(claimSettlement, ClaimConstant.CLAIM_SETTLEMENT__CHANGEPOSTING);

		// 2.getSettlementItems by settleId posting_flag ! = org_p_flag , deal
		// PostingChanged settlement
		List<BusinessFeeModel> businessModelList = new ArrayList<>();

		List<TRiClaimSettlementItem> settlementItems = settlementItemService
				.getSettlementItemListPostingFlagChanged(settlementId);
		logger.info("settlementItems.size()============" + settlementItems.size());
		if (settlementItems.size() > 0) {
			BusinessFeeModel businessModel = settlementItemInfoConvertor.settlementChangePosting(claimSettlement,
					settlementItems);
			businessModelList.add(businessModel);
			for (TRiClaimSettlementItem si : settlementItems) {
				// if (si.getSettlementType().equals(ClaimConstant.LOSS)) {
				ClaimEntryVO claimEntryVO = settlementItemInfoConvertor
						.settlementChangePostingAccounting(claimSettlement, si);
				claimEntryVOList.add(claimEntryVO);
				// }
			}
		}

		// 3.update SettlementItems.p_falg = org_p_flag
		settlementItemService.updateSettlementItemById(settlementId);

		logger.info("settlementChangePosting==settlementId==" + settlementId);

		// deal posting
		if (businessModelList.size() > 0) {
			dealPosting(businessModelList);
		}
		// 6.posting Accounting
		if (claimEntryVOList.size() > 0) {
			dealPostingAccounting(claimEntryVOList);
		}

	}

	private void dealPosting(List<BusinessFeeModel> businessModelList) throws Exception {

		// posting reserve
		logger.info("settlementChangePosting====starttime=" + AppContext.getSysDate() + "=businessModelList="
				+ businessModelList.size());
		financeService.writeToFinance(businessModelList);
		logger.info("settlementChangePosting====endtime=" + AppContext.getSysDate());

	}

	@Override
	public void dealSettlementSubmit(Long refId) throws Exception {

		List<BusinessFeeModel> businessModelList = new ArrayList<>();
		List<ClaimEntryVO> claimEntryVOList = new ArrayList<>();

		// long claimId = claimInfo.getClaimId();
		List<TRiClaimSettlement> settlements = claimSettlementDao.getOutstandingSettleByRefId(refId);

		for (TRiClaimSettlement se : settlements) {
			long settleId = se.getSettlementId();
			logger.info("TRiClaimSettlement=============settleId===============" + settleId);

			// update settlement set status=3, insert settlementLog ,
			se.setStatus(ClaimConstant.CLAIM_SETTLEMENT_STATUS__APPROVED);
			updateSettlement(se, ClaimConstant.CLAIM_SETTLEMENT_STATUS__APPROVED);

			// getSettlementItems by settleId posting_flag=1
			List<TRiClaimSettlementItem> settlementItems = settlementItemService.getSettlementItemById(settleId);
			logger.info("TRiClaimSettlement============" + settlementItems.size());

			if (settlementItems.size() > 0) {
				// if SettlementItems.size()>0 insert settlementSendtable
				BusinessFeeModel businessModel = settlementItemInfoConvertor.settlementConvetorPosting(se,
						settlementItems);
				businessModelList.add(businessModel);

				for (TRiClaimSettlementItem si : settlementItems) {
					// if (si.getSettlementType().equals(ClaimConstant.LOSS)) {
					ClaimEntryVO claimEntryVO = settlementItemInfoConvertor.settlementPostingAccounting(se, si);
					claimEntryVOList.add(claimEntryVO);
					// }
				}

			}
			// update SettlementItems.p_falg = org_p_flag
			settlementItemService.updateSettlementItemById(settleId);
		}

		logger.info("settlementChangePosting==refId==" + refId);

		// deal posting
		if (businessModelList.size() > 0) {
			dealPosting(businessModelList);
		}

		// // 6.posting Accounting
		if (claimEntryVOList.size() > 0) {
			dealPostingAccounting(claimEntryVOList);
		}

	}

	public void dealPostingAccounting(List<ClaimEntryVO> claimEntryVOList) throws Exception {
		// posting reserve
		logger.info("dealPostingAccounting====starttime=" + AppContext.getSysDate() + "=claimEntryVOList="
				+ claimEntryVOList.size());
		List<ClaimEntryVO> claimEntryListPosting = new ArrayList<>();
		for (ClaimEntryVO c : claimEntryVOList) {
			if (sectionDao.getContractNatrueBySectionId(c.getSectionId()).equals("2")
					|| sectionDao.getContractNatrueBySectionId(c.getSectionId()) == "2") {
				// claimEntryVO.remove(c);
			} else {
				claimEntryListPosting.add(c);
			}
		}

		if (claimEntryListPosting.size() > 0) {

			try {
				accountingService.afterClaimSubmit(claimEntryListPosting);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new Exception("Posting to accounting failed, please contact the accounting team! ");
			}
		}

		// accountingService.afterClaimSubmit(claimEntryVOList);

		logger.info("dealPostingAccounting====endtime=" + AppContext.getSysDate());

	}
}
