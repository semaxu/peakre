package com.ebao.ap99.claim.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.convertor.ClaimInfoConvertor;
import com.ebao.ap99.claim.convertor.ClaimSectionInfoConvertor;
import com.ebao.ap99.claim.dao.RiClaimInfoDao;
import com.ebao.ap99.claim.dao.RiClaimMessageDao;
import com.ebao.ap99.claim.dao.RiClaimReserveDao;
import com.ebao.ap99.claim.dao.RiClaimSectionDao;
import com.ebao.ap99.claim.dao.RiClaimSettlementDao;
import com.ebao.ap99.claim.dao.RiClaimSettlementItemDao;
import com.ebao.ap99.claim.entity.TRiClaimInfo;
import com.ebao.ap99.claim.entity.TRiClaimMessage;
import com.ebao.ap99.claim.entity.TRiClaimReserve;
import com.ebao.ap99.claim.entity.TRiClaimSectionInfo;
import com.ebao.ap99.claim.entity.TRiClaimSettlement;
import com.ebao.ap99.claim.entity.TRiClaimSettlementItem;
import com.ebao.ap99.claim.model.ClaimExcelModel;
import com.ebao.ap99.claim.model.ClaimInfo;

import com.ebao.ap99.claim.model.ClaimSummaryInfo;
import com.ebao.ap99.claim.model.Reserve;
import com.ebao.ap99.claim.model.ReserveInfo;
import com.ebao.ap99.claim.model.ReserveSummaryInfo;
import com.ebao.ap99.claim.model.Settle;
import com.ebao.ap99.claim.model.SettlementInfo;
import com.ebao.ap99.claim.model.SettlementSummaryInfo;
import com.ebao.ap99.claim.service.RiClaimInfoService;
import com.ebao.ap99.claim.service.RiClaimMessageService;
import com.ebao.ap99.claim.service.RiClaimReserveService;
import com.ebao.ap99.claim.service.RiClaimSettlementItemService;
import com.ebao.ap99.claim.service.RiClaimSettlementService;
import com.ebao.ap99.contract.entity.ContractModel;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.file.service.CalService;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.ap99.parent.constant.NumberingFactor;
import com.ebao.ap99.parent.constant.NumberingType;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.unicorn.platform.foundation.numbering.NumberingService;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class RiClaimInfoServiceImpl implements RiClaimInfoService, CalService{

	private static Logger logger = LoggerFactory.getLogger();
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private RiClaimInfoDao claimInfoDao;
	@Autowired
	private RiClaimReserveDao reserveDao;
	@Autowired
	private RiClaimSettlementDao claimSettlementDao;
	@Autowired
	private NumberingService numService;
	@Autowired
	public RiClaimReserveService reserveService;
	@Autowired
	public RiClaimSettlementService settlementService;
	// @Autowired
	// private SettlementInfoConvertor settlementInfoConvertor;
	@Autowired
	private RiClaimMessageDao claimMessageDao;
	@Autowired
	private RiClaimSectionDao sectionDao;

	@Autowired
	private ContractService contractService;
	@Autowired
	public RiClaimSettlementItemService settlementItemService;
	@Autowired
	private RiClaimMessageService messageService;
	@Autowired
	private RiClaimSectionInfoServiceImpl sectionService;
	@Autowired
	public ClaimSectionInfoConvertor sectionInfoConvertor;
	@Autowired
	private RiClaimSettlementItemDao settlementItemDao;
	@Autowired
	private RiClaimSettlementDao settlementDao;

	@Override
	public void createClaimInfo(TRiClaimInfo claiminfo) throws Exception {

		Long userId = AppContext.getCurrentUser().getUserId();
		generateClaimNo(claiminfo);
		// 0 : open
		// claimInfoConvertor.insertTaskOwner(claiminfo);
		claiminfo.setTaskOwner(userId.toString());
		claiminfo.setStatus(ClaimConstant.CLAIM_STATUS__OPEN);
		claimInfoDao.insert(claiminfo);
	}

	private void generateClaimNo(TRiClaimInfo claiminfo) throws Exception {
		if (claiminfo.getClaimNo() == null) {
			Date date = AppContext.getSysDate();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDD");
			String formattedDate = simpleDateFormat.format(date);
			Map<String, String> factors = new HashMap<String, String>();
			factors.put(NumberingFactor.TRANS_YEAR, formattedDate.substring(0, 4));
			factors.put(NumberingFactor.TRANS_MONTH, formattedDate.substring(4, 6));
			factors.put(NumberingFactor.TRANS_DAY, formattedDate.substring(6, 8));

			String claimNumber = "";
			try {
				claimNumber = numService.generateNumber(NumberingType.RI_CLAIM_NUMBER, factors);
			} catch (Exception e) {
				logger.error("generate claim number error.");
				throw new Exception("generate claim number error.", e);
			}

			logger.info("getClaimNo=>" + claimNumber);

			claiminfo.setClaimNo(claimNumber);
		}
	}

	@Override
	public TRiClaimInfo updateClaimInfo(TRiClaimInfo claiminfo) {
		@SuppressWarnings("deprecation")
		TRiClaimInfo entityReturn = claimInfoDao.merge(claiminfo);
		em.flush();
		List<TRiClaimReserve> reserveList = claiminfo.getTRiClaimReserves();
		if (reserveList != null && reserveList.size() > 0) {
			reserveList.forEach(r -> {
				r.setRefId(entityReturn.getClaimId());
				@SuppressWarnings("deprecation")
				TRiClaimReserve reserveRetrun = reserveDao.merge(r);
				entityReturn.addTRiClaimReserve(reserveRetrun);
			});
		}
		List<TRiClaimSettlement> settlementList = claiminfo.getTRiClaimSettlements();
		if (settlementList != null && settlementList.size() > 0) {
			settlementList.forEach(s -> {
				s.setRefId(entityReturn.getClaimId());
				@SuppressWarnings("deprecation")
				TRiClaimSettlement settlementReturn = claimSettlementDao.merge(s);
				entityReturn.addTRiClaimSettlement(settlementReturn);
			});
		}

		return entityReturn;
	}

	@Override
	public TRiClaimInfo getClaim(long claimId) {

		TRiClaimInfo claimInfo = claimInfoDao.load(claimId);
		List<TRiClaimReserve> reserveList = reserveDao.getReserveList(claimInfo.getClaimId());
		claimInfo.setTRiClaimReserves(reserveList);
		List<TRiClaimSettlement> settlementList = claimSettlementDao.getOutstandingSettleByRefId(claimId);
		claimInfo.setTRiClaimSettlements(settlementList);

		return claimInfo;
	}

	@Override
	public void deleteClaimInfo(TRiClaimInfo claiminfo) {
		claimInfoDao.delete(claiminfo);

	}

	@Override
	public List<TRiClaimInfo> getAllClaims() {
		return claimInfoDao.loadAll();
	}

	@Override
	public ClaimInfo getClaimInfoWithPendingSettlement(ClaimInfo claimInfo) {
		// TRiClaimInfo cliamEntity = claimInfoDao.load(claimId);
		// cliamEntity.getTRiClaimReserves();
		// cliamEntity.setTRiClaimSettlements(settlementService.getOutstandingSettleByClaimId(claimId));
		List<SettlementInfo> settlementList = claimInfo.getSettlementList();
		if (settlementList != null) {
			List<SettlementInfo> pendingSettlementList = settlementList.stream()
					.filter(s -> s.getStatus().equals(ClaimConstant.CLAIM_SETTLEMENT_STATUS__PENDING_SUBMIT))
					.collect(Collectors.toList());
			claimInfo.setSettlementList(pendingSettlementList);
		}
		List<SettlementInfo> settlementListRetro = claimInfo.getSettlementListRetro();
		if (settlementListRetro != null) {
			List<SettlementInfo> pendingSettlementListRetro = settlementListRetro.stream()
					.filter(s -> s.getStatus().equals(ClaimConstant.CLAIM_SETTLEMENT_STATUS__PENDING_SUBMIT))
					.collect(Collectors.toList());
			claimInfo.setSettlementListRetro(pendingSettlementListRetro);
		}

		return claimInfo;
	}

	@Override
	public void calculateSummary(ClaimInfo claimInfo) {

		List<ReserveSummaryInfo> reserveSummary = reserveService.calcReserveSummary(claimInfo.getReserveList());
		claimInfo.setReserveSummary(reserveSummary);

		List<ReserveSummaryInfo> reserveSummaryRetro = reserveService
				.calcReserveSummary(claimInfo.getReserveListRetro());
		claimInfo.setReserveSummaryRetro(reserveSummaryRetro);

		List<SettlementInfo> settlementList = claimInfo.getSettlementList();
		if (null != settlementList && settlementList.size() != 0) {
			settlementList.forEach(settle -> {
				List<SettlementSummaryInfo> settlementSummary = settlementService
						.calcSettlementSummary(settle.getSettlementItemList());
				settle.setSettlementSummary(settlementSummary);
			});
		}
		List<SettlementInfo> settlementListRetro = claimInfo.getSettlementListRetro();
		if (null != settlementListRetro && settlementListRetro.size() != 0) {
			settlementListRetro.forEach(settle -> {
				List<SettlementSummaryInfo> settlementSummaryRetro = settlementService
						.calcSettlementSummary(settle.getSettlementItemList());
				settle.setSettlementSummaryRetro(settlementSummaryRetro);
			});
		}

		calculateClaimLevelSummary(claimInfo);
	}

	private void calculateClaimLevelSummary(ClaimInfo claimInfo) {

		List<ClaimSummaryInfo> result = new ArrayList<ClaimSummaryInfo>();

		ClaimSummaryInfo financial = claimInfoDao.getClaimSummary(claimInfo.getClaimId(),
				ClaimConstant.CLAIM_BUSINESS_DIRECTION__FINANCIAL);
		financial.setCurrencyType(ClaimConstant.FINANCIAL);

		result.add(financial);

		ClaimSummaryInfo retrocession = claimInfoDao.getClaimSummary(claimInfo.getClaimId(),
				ClaimConstant.CLAIM_BUSINESS_DIRECTION__RETROCESSION);
		retrocession.setCurrencyType(ClaimConstant.RETROCESSION);
		result.add(retrocession);

		ClaimSummaryInfo totalNet = new ClaimSummaryInfo();
		totalNet.setCurrencyType(ClaimConstant.TOTALNET);
		totalNet.setReserveSummary(financial.getReserveSummary() - retrocession.getReserveSummary());
		totalNet.setSettlementSummary(financial.getSettlementSummary() - retrocession.getSettlementSummary());
		totalNet.setTotalByCurrency(financial.getTotalByCurrency() - retrocession.getTotalByCurrency());
		result.add(totalNet);

		// Map<String, String> currencies =
		// reserveService.getAllCurrencyOnClaim(claimInfo.getClaimId());
		// Map<String, Double> reserveAmountGroupByCurrency = reserveService
		// .getLatestReserveAmountGroupByCurrency(claimInfo.getClaimId());
		// Map<String, Double> approvedSettleAmtGroupByCurrency =
		// settlementService
		// .getApprovedSettleAmountGroupByCurrency(claimInfo.getClaimId());
		//
		// Iterator<String> it = currencies.keySet().iterator();
		//
		// while (it.hasNext()) {
		// String currency = it.next();
		// Double reserveSummary = reserveAmountGroupByCurrency.get(currency);
		// Double settleSummary =
		// approvedSettleAmtGroupByCurrency.get(currency);
		//
		// ClaimSummaryInfo claimSummaryInfo = new ClaimSummaryInfo(currency,
		// reserveSummary, settleSummary);
		// result.add(claimSummaryInfo);
		// }
		//
		// Double reserveUsdEquivalent =
		// reserveService.getReserveUsdEquivalent(claimInfo.getClaimId());
		// Double settleUsdEquivalent =
		// settlementService.getSettlementUsdEquivalent(claimInfo.getClaimId());
		//
		// ClaimSummaryInfo claimSummaryInfo = new ClaimSummaryInfo("USD
		// Equivalent", reserveUsdEquivalent,
		// settleUsdEquivalent);
		// result.add(claimSummaryInfo);
		//
		claimInfo.setTotalLoss(BigDecimal.valueOf(financial.getTotalByCurrency()));

		claimInfo.setClaimSummary(result);
	}

	public void calculateReportingCurrencyAmount(ClaimInfo claimInfo) {

		List<ReserveInfo> reserveList = claimInfo.getReserveList();
		if (reserveList != null && reserveList.size() > 0) {
			reserveList.forEach(r -> {

				if (r.getStatus().equals(ClaimConstant.CLAIM_RESERVE_STATUS__NEW)
						|| !r.getAmountOc().equals(r.getOrgAmountOc())
						|| !r.getOrgPostingFlag().equals(r.getPostingFlag())) {
					logger.info("calculate exchange rate  r.getAmountOc()==" + r.getAmountOc() + "==currency=="
							+ r.getOriginalCurrency());
					r.setAmountUsd(reserveService.calculateUsdAmount(r.getOriginalCurrency(), r.getAmountOc()));
					// getExchangeRate
					r.setExchangeRate(
							reserveService.getExchangeRate(r.getOriginalCurrency(), ClaimConstant.REPORTING_CURRENCY));
				}
			});
		}

		List<SettlementInfo> settleList = claimInfo.getSettlementList();
		if (settleList != null && settleList.size() > 0) {
			settleList.forEach(s -> {
				s.getSettlementItemList().forEach(d -> {
					d.setAmountUsd(reserveService.calculateUsdAmount(d.getOriginalCurrency(), d.getAmountOc()));
					d.setExchangeRate(
							reserveService.getExchangeRate(d.getOriginalCurrency(), ClaimConstant.REPORTING_CURRENCY));

				});
			});
		}

		List<ReserveInfo> reserveListRetro = claimInfo.getReserveListRetro();
		if (reserveListRetro != null && reserveListRetro.size() > 0) {
			reserveListRetro.forEach(r -> {
				if (r.getStatus().equals(ClaimConstant.CLAIM_RESERVE_STATUS__NEW)
						|| !r.getAmountOc().equals(r.getOrgAmountOc())
						|| !r.getOrgPostingFlag().equals(r.getPostingFlag())) {
					logger.info("calculate exchange rate  r.getAmountOc()==" + r.getAmountOc() + "==currency=="
							+ r.getOriginalCurrency());
					r.setAmountUsd(reserveService.calculateUsdAmount(r.getOriginalCurrency(), r.getAmountOc()));
					r.setExchangeRate(
							reserveService.getExchangeRate(r.getOriginalCurrency(), ClaimConstant.REPORTING_CURRENCY));
				}

			});
		}

		List<SettlementInfo> settleListRetro = claimInfo.getSettlementListRetro();
		if (settleListRetro != null && settleListRetro.size() > 0) {
			settleListRetro.forEach(s -> {
				s.getSettlementItemList().forEach(d -> {
					d.setAmountUsd(reserveService.calculateUsdAmount(d.getOriginalCurrency(), d.getAmountOc()));
					d.setExchangeRate(
							reserveService.getExchangeRate(d.getOriginalCurrency(), ClaimConstant.REPORTING_CURRENCY));
				});
			});
		}
	}

	@Override
	public void calculateTotalLoss(ClaimInfo claimInfo) {
		Double reserveUsdEquivalent = reserveService.getReserveUsdEquivalent(claimInfo.getClaimId());
		Double settleUsdEquivalent = settlementService.getSettlementUsdEquivalent(claimInfo.getClaimId());

		ClaimSummaryInfo claimSummaryInfo = new ClaimSummaryInfo("USD Equivalent", reserveUsdEquivalent,
				settleUsdEquivalent);

		claimInfo.setTotalLoss(BigDecimal.valueOf(claimSummaryInfo.getTotalByCurrency()));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void removeRelatedClaim(Long relatedClaim) {
		logger.info("------------------" + relatedClaim);
		TRiClaimInfo claimInfo = claimInfoDao.load(relatedClaim);
		claimInfo.setEventId(null);
		claimInfoDao.merge(claimInfo);
	}

	@Override
	public double claimSummary(Long claimId) {
		ClaimSummaryInfo financial = claimInfoDao.getClaimSummary(claimId,
				ClaimConstant.CLAIM_BUSINESS_DIRECTION__FINANCIAL);
		return financial.getTotalByCurrency();
	}

	@Override
	public List<TRiClaimMessage> createClaimMessage(Long claimId) {
		// double claimTotalAmount = this.claimSummary(claimId);
		TRiClaimMessage claimAmountMessage = getClaimAmountMessage(claimId);
		TRiClaimMessage eventAmountMessage = getEventAmountMessage(claimId);
		List<TRiClaimMessage> claimMessages = getPortfolioTransMessage(claimId);

		if (!"".equals(claimAmountMessage.getMessageType())) {
			claimMessages.add(claimAmountMessage);
		} else {
			messageService.deleteClaimMessage(claimAmountMessage);
		}

		if (!"".equals(eventAmountMessage.getMessageType())) {
			claimMessages.add(eventAmountMessage);
		} else {
			messageService.deleteClaimMessage(eventAmountMessage);
		}

		logger.info("wwww" + claimMessages.size());
		for (TRiClaimMessage cl : claimMessages) {
			if (!"".equals(cl.getMessageType()) && cl.getMessageType() != null) {
				messageService.createClaimMessage(cl);
			}
		}
		List<TRiClaimMessage> claimMessageReturn = claimMessageDao.getClaimMessageByRefId(claimId);

		return claimMessageReturn;
	}

	public TRiClaimMessage getClaimAmountMessage(Long claimId) {

		double claimTotalAmount = this.claimSummary(claimId);
		// TRiClaimMessage tRiClaimMessage = new TRiClaimMessage();
		TRiClaimMessage tRiClaimMessage = claimMessageDao.getClaimMessage(claimId, ClaimConstant.CLAIM_AMOUNT_MESSAGE);
		logger.info("tRiClaimMessage--------------" + tRiClaimMessage);

		if ("".equals(tRiClaimMessage.getMessageType()) || tRiClaimMessage.getMessageType() == null) {

			if (claimTotalAmount > ClaimConstant.AMOUNT_LEVEL_ONE) {
				tRiClaimMessage.setMessageType(ClaimConstant.CLAIM_AMOUNT_MESSAGE);
				tRiClaimMessage.setMessageName("claim amount message");
				tRiClaimMessage.setRefId(claimId);
				tRiClaimMessage.setRefType(ClaimConstant.REF_TYPE_CLAIM);
				tRiClaimMessage.setFlag(ClaimConstant.MESSAGE_UNCHECKED);
				if (claimTotalAmount > ClaimConstant.AMOUNT_LEVEL_TWO) {
					tRiClaimMessage.setMessageDescription(ClaimConstant.AMOUNT_LEVEL_TWO_CLAIM_DESC);
				} else if (claimTotalAmount > ClaimConstant.AMOUNT_LEVEL_ONE) {
					tRiClaimMessage.setMessageDescription(ClaimConstant.AMOUNT_LEVEL_ONE_CLAIM_DESC);
				}
			}
		} else {

			if (claimTotalAmount > ClaimConstant.AMOUNT_LEVEL_TWO) {
				if (!tRiClaimMessage.getMessageDescription().equals(ClaimConstant.AMOUNT_LEVEL_TWO_CLAIM_DESC)) {
					tRiClaimMessage.setMessageDescription(ClaimConstant.AMOUNT_LEVEL_TWO_CLAIM_DESC);
					tRiClaimMessage.setFlag(ClaimConstant.MESSAGE_UNCHECKED);
				}
			} else if (claimTotalAmount > ClaimConstant.AMOUNT_LEVEL_ONE) {
				if (!tRiClaimMessage.getMessageDescription().equals(ClaimConstant.AMOUNT_LEVEL_ONE_CLAIM_DESC)) {
					tRiClaimMessage.setMessageDescription(ClaimConstant.AMOUNT_LEVEL_ONE_CLAIM_DESC);
					tRiClaimMessage.setFlag(ClaimConstant.MESSAGE_UNCHECKED);
				}
			} else {
				//
				// delete claimMessage
				// messageService.deleteClaimMessage(tRiClaimMessage);
				tRiClaimMessage.setMessageType("");

			}
		}
		return tRiClaimMessage;
	}

	public TRiClaimMessage getEventAmountMessage(Long claimId) {

		TRiClaimMessage tRiClaimMessage = claimMessageDao.getClaimMessage(claimId, ClaimConstant.EVENT_AMOUNT_MESSAGE);
		TRiClaimInfo tClaimInfo = claimInfoDao.load(claimId);

		if (!"".equals(tClaimInfo.getEventId()) && tClaimInfo.getEventId() != null) {
			double evetTotalAmount = getEventAmount(tClaimInfo.getEventId());
			if ("".equals(tRiClaimMessage.getMessageType()) || tRiClaimMessage.getMessageType() == null) {
				if (evetTotalAmount > ClaimConstant.AMOUNT_LEVEL_ONE) {

					tRiClaimMessage.setMessageType(ClaimConstant.EVENT_AMOUNT_MESSAGE);
					tRiClaimMessage.setMessageName("event amount message");
					tRiClaimMessage.setRefId(claimId);
					tRiClaimMessage.setRefType(ClaimConstant.REF_TYPE_CLAIM);
					tRiClaimMessage.setFlag(ClaimConstant.MESSAGE_UNCHECKED);
					if (evetTotalAmount > ClaimConstant.AMOUNT_LEVEL_TWO) {
						tRiClaimMessage.setMessageDescription(ClaimConstant.AMOUNT_LEVEL_TWO_EVENT_DESC);
					} else if (evetTotalAmount > ClaimConstant.AMOUNT_LEVEL_ONE) {
						tRiClaimMessage.setMessageDescription(ClaimConstant.AMOUNT_LEVEL_ONE_EVENT_DESC);
					}
				}
			} else {

				if (evetTotalAmount > ClaimConstant.AMOUNT_LEVEL_TWO) {
					if (!tRiClaimMessage.getMessageDescription().equals(ClaimConstant.AMOUNT_LEVEL_TWO_EVENT_DESC)) {
						tRiClaimMessage.setMessageDescription(ClaimConstant.AMOUNT_LEVEL_TWO_EVENT_DESC);
						tRiClaimMessage.setFlag(ClaimConstant.MESSAGE_UNCHECKED);
					}
				} else if (evetTotalAmount > ClaimConstant.AMOUNT_LEVEL_ONE) {
					if (!tRiClaimMessage.getMessageDescription().equals(ClaimConstant.AMOUNT_LEVEL_ONE_EVENT_DESC)) {
						tRiClaimMessage.setMessageDescription(ClaimConstant.AMOUNT_LEVEL_ONE_EVENT_DESC);
						tRiClaimMessage.setFlag(ClaimConstant.MESSAGE_UNCHECKED);
					}
				} else {
					//
					// delete claimMessage
					tRiClaimMessage.setMessageType("");
				}
			}

		}
		return tRiClaimMessage;

	}

	public double getEventAmount(Long eventId) {
		double evetAmount = 0;

		List<TreeModel> claimList = claimInfoDao.getClaimNoList(eventId);

		for (TreeModel c : claimList) {
			double claimAmount = claimSummary((Long)c.getId());
			evetAmount = evetAmount + claimAmount;
		}

		return evetAmount;
	}

	public List<TRiClaimMessage> getPortfolioTransMessage(Long claimId) {
		// TODO
		List<Long> sectionLists = reserveDao.getSectionId(claimId);
		List<TRiClaimMessage> claimMessageLists = new ArrayList<>();
		if (sectionLists.size() > 0) {
			for (Long s : sectionLists) {
				// TODO contract interface get if PortfolioTransfor
				// Long contractId = sectionDao.getContractIdBySectionId(s);
				boolean flag = contractService.isCleanCutContractSection(s);
				// String flag = "0";
				if (flag) {
					TRiClaimMessage tRiClaimMessage = claimMessageDao.getClaimMessageBySectionId(claimId,
							ClaimConstant.PORTFOLIOTRANSFER, s);
					if ("".equals(tRiClaimMessage.getMessageType()) || tRiClaimMessage.getMessageType() == null) {
						TRiClaimSectionInfo sectionInfo = sectionDao.getClaimSectionInfoBySection(s);
						tRiClaimMessage.setMessageType(ClaimConstant.PORTFOLIOTRANSFER);
						tRiClaimMessage.setMessageName("PortfolioTransfer message");
						tRiClaimMessage.setRefId(claimId);
						tRiClaimMessage.setRefType(ClaimConstant.REF_TYPE_CLAIM);
						tRiClaimMessage.setMessageDescription(
								sectionInfo.getFullName() + ClaimConstant.PORTFOLIO_TRANSFER_MESSAGE);
						tRiClaimMessage.setSectionId(s);
						tRiClaimMessage.setFlag(ClaimConstant.MESSAGE_UNCHECKED);
					}

					claimMessageLists.add(tRiClaimMessage);
				}
			}
		}
		return claimMessageLists;

	}
	// public void calculateUsdEquivalent(ClaimInfo claimInfo) {
	// List<ReserveInfo> reserveList = claimInfo.getReserveList();
	// Double reserveUsdEquivalent = reserveList.stream()
	// .collect(Collectors.summingDouble(ReserveInfo::getAmountUsdDouble));
	// claimInfo.setReserveUsdEquivalent(reserveUsdEquivalent);
	//
	// List<ReserveInfo> reserveListRetro = claimInfo.getReserveListRetro();
	// Double reserveUsdEquivalentRetro = reserveListRetro.stream()
	// .collect(Collectors.summingDouble(ReserveInfo::getAmountUsdDouble));
	// claimInfo.setReserveUsdEquivalentRetro(reserveUsdEquivalentRetro);
	//
	// List<SettlementInfo> settleList = claimInfo.getSettlementList();
	// settleList.forEach(s -> {
	// Double settlementUsdEquivalent = s.getDetailInfoList().stream()
	// .collect(Collectors.summingDouble(SettlementItemInfo::getAmountUsdDouble));
	// s.setSettlementUsdEquivalent(settlementUsdEquivalent);
	// });
	//
	// List<SettlementInfo> settleListRetro =
	// claimInfo.getSettlementListRetro();
	// settleListRetro.forEach(s -> {
	// Double settlementUsdEquivalentRetro = s.getDetailInfoList().stream()
	// .collect(Collectors.summingDouble(SettlementItemInfo::getAmountUsdDouble));
	// s.setSettlementUsdEquivalentRetro(settlementUsdEquivalentRetro);
	// });
	// }

	@Override
	public void insertClaimReserve(Long claimId, List<TRiClaimReserve> reserveList) throws Exception {
		List<Long> sectionId = claimInfoDao.getSectionIdList(claimId);
		List<Long> newSectionId = new ArrayList<Long>();

		for (int i = 0; i < sectionId.size(); i++) {
			List<Long> sectionIdList = contractService.getRetrocessionSectionIdList(sectionId.get(i));
			newSectionId.addAll(sectionIdList);
		}
		List<Long> existSectionId = sectionDao.getExistSectionIdList(claimId, "2");
		newSectionId.removeAll(existSectionId);

		for (int y = 0; y < newSectionId.size(); y++) {
			ContractModel contactModel = contractService.getContractInfoByCompId(newSectionId.get(y));
			if (null == contactModel) {
				logger.warn("NO contract section found! section ID=" + newSectionId.get(y));
				continue;
			}
			TRiClaimSectionInfo section = sectionInfoConvertor.modelToEntity(contactModel);
			section.setRefId(claimId);
			section.setRefType("1");
			section.setBusinessDirection("2");
			sectionService.createClaimSectionInfo(section);
		}

		Reserve reserve = new Reserve();
		TRiClaimReserve reserveRetro = new TRiClaimReserve();
		for (int r = 0; r < reserveList.size(); r++) {
			if (sectionId.size() > 0) {
				for (int z = 0; z < sectionId.size(); z++) {
					if (reserveList.get(r).getSectionId().equals(sectionId.get(z))) {

						BeanUtils.copyPropertiesIngoreNull(reserve, reserveList.get(r));
						reserve.setBusinessDirection("2");
						reserve.setRetroRefSectionId(sectionId.get(z));
						reserve.setRelatedReserveId(reserveList.get(r).getReserveId());

						List<Long> sectionIdList = contractService.getRetrocessionSectionIdList(sectionId.get(z));
						for (int y = 0; y < sectionIdList.size(); y++) {
							reserve.setSectionId(sectionIdList.get(y));
						}

						BeanUtils.copyPropertiesIngoreNull(reserveRetro, reserve);
						@SuppressWarnings({ "deprecation", "unused" })
						TRiClaimReserve reserveRetroList = reserveDao.merge(reserveRetro);
					}
				}
			}
		}

	}

	@Override
	public void insertClaimSettle(Long claimId, List<TRiClaimSettlement> settlementList) {
		List<Long> sectionId = claimInfoDao.getSectionIdList(claimId);
		String settlementname = "";
		Settle settle = new Settle();
		TRiClaimSettlementItem settleRetro = new TRiClaimSettlementItem();
		TRiClaimSettlement settlement = new TRiClaimSettlement();
		if (sectionId.size() > 0) {
			for (int i = 0; i < settlementList.size(); i++) {

				List<TRiClaimSettlementItem> settlementItems = settlementItemService
						.getItemList(settlementList.get(i).getSettlementId());

				for (int r = 0; r < settlementItems.size(); r++) {
					for (int z = 0; z < sectionId.size(); z++) {
						Long settleSectionId = settlementItems.get(r).getSectionId().longValue();
						if (settleSectionId.equals(sectionId.get(z))) {
							try {
								settlementname = settlementService.getSettlementName(claimId);
							} catch (Exception e) {
								// Auto-generated catch block
								e.printStackTrace();
							}
							settlement.setBusinessDirection("2");
							settlement.setRefId(claimId);
							settlement.setRefType("1");
							settlement.setStatus("3");
							settlement.setSettlementName(settlementname);

							settlement.setDateOfReceipt(settlementList.get(i).getDateOfReceipt());
							settlement.setRemark(settlementList.get(i).getRemark());
							TRiClaimSettlement newSettlement = settlementDao.merge(settlement);

							BeanUtils.copyPropertiesIngoreNull(settle, settlementItems.get(r));
							settle.setRetroRefSectionId(new BigDecimal(sectionId.get(z)));
							settle.setSettlementId(settlement.getSettlementId());
							settle.setRelatedSettleDetailId(new BigDecimal(settlementItems.get(r).getSettleDetailId()));
							settle.setBusinessDirection("2");
							List<Long> sectionIdList = contractService.getRetrocessionSectionIdList(sectionId.get(z));
							for (int y = 0; y < sectionIdList.size(); y++) {
								settle.setSectionId(sectionIdList.get(y));
							}

							BeanUtils.copyPropertiesIngoreNull(settleRetro, settle);
							TRiClaimSettlementItem newSettleRetro = settlementItemDao.merge(settleRetro);
							newSettlement.addTRiClaimSettlementItem(newSettleRetro);
							newSettlement = settlementDao.merge(newSettlement);
						}
					}
				}
			}
		}

	}

	@Override
	public <T> List<ClaimExcelModel> bizProcess(Map model) {
		// ClaimParam para = (ClaimParam)model;
		// (String)
		String dd = (String) model.get("RefId");
		logger.info(dd.toString());
		Long refId = Long.valueOf((String) model.get("RefId"));

		List<ClaimExcelModel> claimExcelModel = claimInfoDao.getClaimExcelList(refId);

		return claimExcelModel;

	}

	@Override
	public void updateClaimReserve(TRiClaimInfo claimEntity) {
		List<TRiClaimReserve> submitReserve = claimEntity.getTRiClaimReserves();
		List<TRiClaimReserve> retroReserve = new ArrayList<TRiClaimReserve>();
		List<TRiClaimReserve> finaReserve = new ArrayList<TRiClaimReserve>();

		for (int i = 0; i < submitReserve.size(); i++) {
			if (submitReserve.get(i).getBusinessDirection() == "2") {
				retroReserve.add(submitReserve.get(i));
			} else {
				finaReserve.add(submitReserve.get(i));
			}
		}
		if (retroReserve.size() > 0 && finaReserve.size() > 0) {
			for (int x = 0; x < retroReserve.size(); x++) {
				if (null != retroReserve.get(x).getRelatedReserveId()) {
					List<TRiClaimReserve> relatedReserve = reserveDao
							.getRelatedReserveList(retroReserve.get(x).getRelatedReserveId());
					retroReserve.get(x).setAmountOc(relatedReserve.get(0).getAmountOc());
					retroReserve.get(x).setBrokerRefer(relatedReserve.get(0).getBrokerRefer());
					retroReserve.get(x).setCedentRefer(relatedReserve.get(0).getCedentRefer());
					retroReserve.get(x).setPostingFlag(relatedReserve.get(0).getPostingFlag());
					TRiClaimReserve reserveRetroList = reserveDao.merge(retroReserve.get(x));
				}

			}
		}

	}

	@Override
	public TRiClaimInfo copyFinaReserve(TRiClaimInfo claimEntity) throws Exception {
		Long claimId = claimEntity.getClaimId();
		// load fronting section
		List<Long> sectionId = claimInfoDao.getSectionIdList(claimId);
		List<Long> newSectionId = new ArrayList<Long>();

		for (int i = 0; i < sectionId.size(); i++) {
			List<Long> sectionIdList = contractService.getRetrocessionSectionIdList(sectionId.get(i));
			newSectionId.addAll(sectionIdList);
		}
		List<Long> existSectionId = sectionDao.getExistSectionIdList(claimId, "2");
		newSectionId.removeAll(existSectionId);

		for (int y = 0; y < newSectionId.size(); y++) {
			// retro section List
			ContractModel contactModel = contractService.getContractInfoByCompId(newSectionId.get(y));
			if (null == contactModel) {
				logger.warn("NO contract section found! section ID=" + newSectionId.get(y));
				continue;
			}
			TRiClaimSectionInfo section = sectionInfoConvertor.modelToEntity(contactModel);
			section.setRefId(claimId);
			section.setRefType("1");
			section.setBusinessDirection("2");
			sectionService.createClaimSectionInfo(section);
		}
		//
		List<Long> relatedId = new ArrayList<Long>();
		List<Long> reserveId = new ArrayList<Long>();
		List<TRiClaimReserve> reserveList = claimEntity.getTRiClaimReserves();
		List<TRiClaimReserve> allReserve = new ArrayList<TRiClaimReserve>();
		List<TRiClaimReserve> frontReserve = new ArrayList<TRiClaimReserve>();
		List<TRiClaimReserve> retroReserve = new ArrayList<TRiClaimReserve>();
		List<TRiClaimReserve> finaReserve = new ArrayList<TRiClaimReserve>();
		// separate the financial and retro
		if (null != reserveList) {
			for (int r = 0; r < reserveList.size(); r++) {
				if (reserveList.get(r).getBusinessDirection() == "2") {
					retroReserve.add(reserveList.get(r));
				} else {
					finaReserve.add(reserveList.get(r));
				}
			}
		}

		// find the fronting =1 reserve that need to be copyed
		for (int x = 0; x < finaReserve.size(); x++) {
			if (sectionId.size() > 0) {
				for (int z = 0; z < sectionId.size(); z++) {
					if (finaReserve.get(x).getSectionId().equals(sectionId.get(z))) {
						frontReserve.add(finaReserve.get(x));
						reserveId.add(finaReserve.get(x).getReserveId());
					}
				}
			}
		}

		if (reserveId != null && reserveId.size() > 0) {
			// get all retro reserve relatedId
			for (int n = 0; n < retroReserve.size(); n++) {
				relatedId.add(retroReserve.get(n).getRelatedReserveId());
			}

			// compare if has copyed and update else drop
			for (int relatedN = 0; relatedN < retroReserve.size(); relatedN++) {
				int count = 0;
				// update
				if (null != retroReserve.get(relatedN).getRelatedReserveId()) {
					for (int frontN = 0; frontN < frontReserve.size(); frontN++) {
						if (retroReserve.get(relatedN).getRelatedReserveId()
								.equals(frontReserve.get(frontN).getReserveId())) {
							retroReserve.get(relatedN).setRetroRefSectionId(frontReserve.get(frontN).getSectionId());
							retroReserve.get(relatedN).setReserveType(frontReserve.get(frontN).getReserveType());
							retroReserve.get(relatedN)
									.setOriginalCurrency(frontReserve.get(frontN).getOriginalCurrency());
							retroReserve.get(relatedN).setAmountOc(frontReserve.get(frontN).getAmountOc());
							retroReserve.get(relatedN).setCedentRefer(frontReserve.get(frontN).getCedentRefer());
							retroReserve.get(relatedN).setBrokerRefer(frontReserve.get(frontN).getBrokerRefer());
							retroReserve.get(relatedN).setPostingFlag(frontReserve.get(frontN).getPostingFlag());
							List<Long> sectionIdList = contractService
									.getRetrocessionSectionIdList(frontReserve.get(frontN).getSectionId());
							for (int y = 0; y < sectionIdList.size(); y++) {
								retroReserve.get(relatedN).setSectionId(sectionIdList.get(y));
							}
							@SuppressWarnings({ "deprecation", "unused" })
							TRiClaimReserve reserveRetro = reserveDao.merge(retroReserve.get(relatedN));
							count = count + 1;
						}
					}
					// drop
					if (count == 0) {
						reserveDao.delete(reserveDao.merge(retroReserve.get(relatedN)));
						retroReserve.remove(relatedN);
					}
				} else {
					@SuppressWarnings({ "deprecation", "unused" })
					TRiClaimReserve reserveRetro = reserveDao.merge(retroReserve.get(relatedN));
				}
			}
			// the new reserve need to be copyed
			if (relatedId != null && relatedId.size() > 0) {
				reserveId.removeAll(relatedId);
			}

			Reserve reserve = new Reserve();
			TRiClaimReserve reserveRetro = new TRiClaimReserve();
			// copy the new reserve
			for (int idN = 0; idN < reserveId.size(); idN++) {
				for (int i = 0; i < frontReserve.size(); i++) {
					if (null != reserveId.get(idN)) {
						if (reserveId.get(idN).equals(frontReserve.get(i).getReserveId())) {
							BeanUtils.copyPropertiesIngoreNull(reserve, frontReserve.get(i));
							reserve.setBusinessDirection("2");
							reserve.setRetroRefSectionId(frontReserve.get(i).getSectionId());
							reserve.setRelatedReserveId(frontReserve.get(i).getReserveId());

							List<Long> sectionIdList = contractService
									.getRetrocessionSectionIdList(frontReserve.get(i).getSectionId());
							for (int y = 0; y < sectionIdList.size(); y++) {
								reserve.setSectionId(sectionIdList.get(y));
							}
							BeanUtils.copyPropertiesIngoreNull(reserveRetro, reserve);
							@SuppressWarnings({ "deprecation" })
							TRiClaimReserve reserveRetroList = reserveDao.merge(reserveRetro);
							retroReserve.add(reserveRetroList);
						}
					}
				}
			}

		}
		allReserve.addAll(finaReserve);
		allReserve.addAll(retroReserve);

		claimEntity.setTRiClaimReserves(allReserve);
		return claimEntity;
	}

	@Override
	public TRiClaimInfo copyFinaSettle(TRiClaimInfo claimEntity) {
		// init index
		Long claimId = claimEntity.getClaimId();
		List<Long> sectionId = claimInfoDao.getSectionIdList(claimId);
		List<TRiClaimSettlement> settlementList = claimEntity.getTRiClaimSettlements();

		String settlementname = "";
		Settle settle = new Settle();
		TRiClaimSettlement settlement = new TRiClaimSettlement();

		List<TRiClaimSettlement> finaSettlementList = new ArrayList<TRiClaimSettlement>();
		List<TRiClaimSettlement> retroSettlementList = new ArrayList<TRiClaimSettlement>();
		// separate the financial and the retrocession
		for (int index = 0; index < settlementList.size(); index++) {
			if (settlementList.get(index).getBusinessDirection() == "1") {
				finaSettlementList.add(settlementList.get(index));
			} else {
				retroSettlementList.add(settlementList.get(index));
			}
		}

		// get fronting =1 financial settlementItem
		for (TRiClaimSettlement finaSettlement : finaSettlementList) {
			// get this settlement's settlementItemList
			List<TRiClaimSettlementItem> finaSettlementItemList = settlementItemService
					.getItemList(finaSettlement.getSettlementId());
			// get fronting =1 financial settlementItem
			for (TRiClaimSettlementItem finaSettlementItem : finaSettlementItemList) {
				if (sectionId.size() > 0) {
					for (int z = 0; z < sectionId.size(); z++) {
						Long finaSectionId = finaSettlementItem.getSectionId().longValue();
						if (finaSectionId.equals(sectionId.get(z))) {
							List<TRiClaimSettlementItem> relatedItem = settlementItemDao
									.getRelatedSettlementItemList(finaSettlementItem.getSettleDetailId());
							if (null != relatedItem && relatedItem.size() > 0) {
								relatedItem.get(0).setRetroRefSectionId(finaSettlementItem.getSectionId());
								relatedItem.get(0).setSettlementType(finaSettlementItem.getSettlementType());
								relatedItem.get(0).setOriginalCurrency(finaSettlementItem.getOriginalCurrency());
								relatedItem.get(0).setAmountOc(finaSettlementItem.getAmountOc());
								relatedItem.get(0).setCedentRefer(finaSettlementItem.getCedentRefer());
								relatedItem.get(0).setBrokerRefer(finaSettlementItem.getBrokerRefer());
								relatedItem.get(0).setPostingFlag(finaSettlementItem.getPostingFlag());
								List<Long> sectionIdList = contractService
										.getRetrocessionSectionIdList(finaSettlementItem.getSectionId().longValue());
								for (int y = 0; y < sectionIdList.size(); y++) {
									relatedItem.get(0).setSectionId(new BigDecimal(sectionIdList.get(y)));
								}
								@SuppressWarnings({ "deprecation", "unused" })
								TRiClaimSettlementItem settlementItemRetro = settlementItemDao
										.merge(relatedItem.get(0));
							} else {
								int count =0;
								for(TRiClaimSettlement retroSettlement : retroSettlementList){
									if(finaSettlement.getSettlementId().equals(retroSettlement.getRelatedSettlementId())){
										Settle copySettle = new Settle();
										BeanUtils.copyPropertiesIngoreNull(copySettle, finaSettlementItem);
										copySettle.setRetroRefSectionId(finaSettlementItem.getSectionId());
										copySettle.setSettlementId(settlement.getSettlementId());
										copySettle.setRelatedSettleDetailId(new BigDecimal(finaSettlementItem.getSettleDetailId()));
										copySettle.setBusinessDirection("2");
										List<Long> sectionIdList = contractService
												.getRetrocessionSectionIdList(sectionId.get(z));
										for (int y = 0; y < sectionIdList.size(); y++) {
											copySettle.setSectionId(sectionIdList.get(y));
										}
										TRiClaimSettlementItem copySettleRetro = new TRiClaimSettlementItem();
										BeanUtils.copyPropertiesIngoreNull(copySettleRetro, copySettle);
										copySettleRetro.setSectionId(new BigDecimal(settle.getSectionId()));
										TRiClaimSettlementItem newSettleRetro = settlementItemDao.merge(copySettleRetro);
										retroSettlement.addTRiClaimSettlementItem(newSettleRetro);
										retroSettlement = settlementDao.merge(retroSettlement);
										count = count+1;
									}
								}
								if(count ==0){
									try {
										settlementname = settlementService.getSettlementName(claimId);
									} catch (Exception e) {
										// Auto-generated catch block
										e.printStackTrace();
									}
									settlement.setBusinessDirection("2");
									settlement.setRefId(claimId);
									settlement.setRefType("1");
									settlement.setStatus(finaSettlement.getStatus());
									settlement.setSettlementName(settlementname);
									settlement.setRelatedSettlementId(finaSettlement.getSettlementId());
									settlement.setDateOfReceipt(finaSettlement.getDateOfReceipt());
									settlement.setRemark(finaSettlement.getRemark());
									TRiClaimSettlement newSettlement = settlementDao.merge(settlement);

									BeanUtils.copyPropertiesIngoreNull(settle, finaSettlementItem);
									settle.setRetroRefSectionId(finaSettlementItem.getSectionId());
									settle.setSettlementId(settlement.getSettlementId());
									settle.setRelatedSettleDetailId(new BigDecimal(finaSettlementItem.getSettleDetailId()));
									settle.setBusinessDirection("2");
									List<Long> sectionIdList = contractService
											.getRetrocessionSectionIdList(sectionId.get(z));
									for (int y = 0; y < sectionIdList.size(); y++) {
										settle.setSectionId(sectionIdList.get(y));
									}

									TRiClaimSettlementItem settleRetro = new TRiClaimSettlementItem();
									BeanUtils.copyPropertiesIngoreNull(settleRetro, settle);
									settleRetro.setSectionId(new BigDecimal(settle.getSectionId()));
									TRiClaimSettlementItem newSettleRetro = settlementItemDao.merge(settleRetro);
									newSettlement.addTRiClaimSettlementItem(newSettleRetro);
									newSettlement = settlementDao.merge(newSettlement);
								}
							}
						}
					}
				}
			}
		}
		// // get all relatedRetroSettlemntItem
		// List<TRiClaimSettlementItem> relatedSettlementItemList = new
		// ArrayList<TRiClaimSettlementItem>();
		// List<Long> relatedIdList = new ArrayList<Long>();
		// for(TRiClaimSettlement retroSettlement : retroSettlementList){
		// List<TRiClaimSettlementItem> retroSettlementItemList =
		// settlementItemService
		// .getItemList(retroSettlement.getSettlementId());
		// for(TRiClaimSettlementItem retroSettlementItem
		// :retroSettlementItemList){
		// relatedSettlementItemList.add(retroSettlementItem);
		// if(retroSettlementItem.getRelatedSettleDetailId() != null){
		// relatedIdList.add(retroSettlementItem.getRelatedSettleDetailId().longValue());
		// }
		// }
		// }
		// update exist settlementItem
		// for(TRiClaimSettlementItem relatedItem :relatedSettlementItemList){
		// if(!("null").equals(relatedItem.getRelatedSettleDetailId())){
		// int count =0;
		// for(TRiClaimSettlementItem frontItem :frontSettlementItemList){
		// if(relatedItem.getRelatedSettleDetailId().equals(frontItem.getSettleDetailId())){
		// relatedItem.setRetroRefSectionId(frontItem.getSectionId());
		// relatedItem.setSettlementType(frontItem.getSettlementType());
		// relatedItem.setOriginalCurrency(frontItem.getOriginalCurrency());
		// relatedItem.setAmountOc(frontItem.getAmountOc());
		// relatedItem.setCedentRefer(frontItem.getCedentRefer());
		// relatedItem.setBrokerRefer(frontItem.getBrokerRefer());
		// relatedItem.setPostingFlag(frontItem.getPostingFlag());
		// List<Long> sectionIdList = contractService
		// .getRetrocessionSectionIdList(frontItem.getSectionId().longValue());
		// for (int y = 0; y < sectionIdList.size(); y++) {
		// relatedItem.setSectionId(new BigDecimal(sectionIdList.get(y)));
		// }
		// @SuppressWarnings({ "deprecation", "unused" })
		// TRiClaimSettlementItem settlementItemRetro =
		// settlementItemDao.merge(relatedItem);
		// count = count + 1;
		// }
		// }
		// if(count ==0){
		// settlementItemDao.delete(settlementItemDao.merge(relatedItem));
		// relatedSettlementItemList.remove(relatedItem);
		// }
		// }else{
		// @SuppressWarnings({ "deprecation", "unused" })
		// TRiClaimSettlementItem settlementItemRetro =
		// settlementItemDao.merge(relatedItem);
		// }
		// }
		//
		// if (relatedIdList != null && relatedIdList.size() > 0) {
		// settleDetailIdList.removeAll(relatedIdList);
		// }

		return claimEntity;
	}

	// @Override
	// public <T> List bizProcess(T model) {
	// return null;
	// }

	// public <T> List bizProcess(T model) {
	// return null;
	// }

	
}
