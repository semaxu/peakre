/**
 * 
 */
package com.ebao.ap99.claim.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.convertor.ClaimInfoConvertor;
import com.ebao.ap99.claim.dao.RiClaimEventDao;
import com.ebao.ap99.claim.dao.RiClaimInfoDao;
import com.ebao.ap99.claim.dao.RiClaimMessageDao;
import com.ebao.ap99.claim.dao.RiClaimReserveDao;
import com.ebao.ap99.claim.dao.RiClaimSectionDao;
import com.ebao.ap99.claim.dao.RiClaimSettlementDao;
import com.ebao.ap99.claim.entity.TRiClaimEvent;
import com.ebao.ap99.claim.entity.TRiClaimInfo;
import com.ebao.ap99.claim.entity.TRiClaimMessage;
import com.ebao.ap99.claim.entity.TRiClaimReserve;
import com.ebao.ap99.claim.entity.TRiClaimSectionInfo;
import com.ebao.ap99.claim.entity.TRiClaimSettlement;
import com.ebao.ap99.claim.model.ClaimInfo;
import com.ebao.ap99.claim.model.ClaimRecordsExcelModel;
import com.ebao.ap99.claim.model.ClaimSummaryInfo;
import com.ebao.ap99.claim.model.EventInfo;
import com.ebao.ap99.claim.model.ReserveInfo;
import com.ebao.ap99.claim.model.ReserveSummaryInfo;
import com.ebao.ap99.claim.model.SettlementInfo;
import com.ebao.ap99.claim.model.SettlementSummaryInfo;
import com.ebao.ap99.claim.service.RiClaimInfoService;
import com.ebao.ap99.claim.service.RiClaimMessageService;
import com.ebao.ap99.claim.service.RiClaimReserveService;
import com.ebao.ap99.claim.service.RiClaimSettlementService;
import com.ebao.ap99.claim.service.RiEventService;
import com.ebao.ap99.file.service.CalService;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author yujie.zhang
 *
 */
public class RiEventServiceImpl implements RiEventService, CalService {

	private static Logger logger = LoggerFactory.getLogger();
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private RiClaimEventDao eventInfoDao;
//	@Autowired
//	private NumberingService numService;
	@Autowired
	public RiClaimReserveService reserveService;
	@Autowired
	public ClaimInfoConvertor claimInfoConvertor;
	@Autowired
	public RiClaimInfoService claimService;
	@Autowired
	public RiClaimSettlementService settlementService;
	@Autowired
	private RiClaimReserveDao reserveDao;
	@Autowired
	private RiClaimSettlementDao claimSettlementDao;
	
	@Autowired
	private RiClaimMessageDao claimMessageDao;
	@Autowired
	private RiClaimSectionDao sectionDao;
	
	@Autowired
	private  RiClaimMessageService messageService;

	@Autowired
	private RiClaimInfoDao claimInfoDao;
	
	
	@Override
	public void createEvent(TRiClaimEvent eventInfo) {
		Long userId = AppContext.getCurrentUser().getUserId();

		eventInfo.setTaskOwner(userId.toString());
		eventInfoDao.insert(eventInfo);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebao.ap99.claim.service.RiEventService#updateEvent(com.ebao.ap99.
	 * claim.entity.TRiClaimEvent)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public TRiClaimEvent updateEvent(TRiClaimEvent eventInfo) {
		logger.info("TRiClaimEvent save");
		TRiClaimEvent eventEntityReturn = eventInfoDao.merge(eventInfo);
		em.flush();
		List<TRiClaimReserve> reserveList = eventInfo.getTRiClaimReserves();
		if (reserveList != null && reserveList.size() > 0) {
			reserveList.forEach(r -> {
				r.setRefId(eventEntityReturn.getEventId());
				TRiClaimReserve reserveRetrun = reserveDao.merge(r);
				eventEntityReturn.addTRiClaimReserve(reserveRetrun);
			});
		}
		List<TRiClaimSettlement> settlementList = eventInfo.getTRiClaimSettlements();
		if (settlementList != null && settlementList.size() > 0) {
			settlementList.forEach(s -> {
				s.setRefId(eventEntityReturn.getEventId());
				TRiClaimSettlement settlementReturn = claimSettlementDao.merge(s);
				eventEntityReturn.addTRiClaimSettlement(settlementReturn);
			});
		}
		return eventEntityReturn;
	}

	@Override
	public TRiClaimEvent getEvent(long eventId) {
		TRiClaimEvent eventInfo= eventInfoDao.load(eventId);
		List<TRiClaimReserve> reserveList = reserveDao.getReserveList(eventInfo.getEventId());
		eventInfo.setTRiClaimReserves(reserveList);
		List<TRiClaimSettlement> settlementList = claimSettlementDao.getOutstandingSettleByRefId(eventId);
		eventInfo.setTRiClaimSettlements(settlementList);
		return eventInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebao.ap99.claim.service.RiEventService#deleteEvent(com.ebao.ap99.
	 * claim.entity.TRiClaimEvent)
	 */
	@Override
	public void deleteEvent(TRiClaimEvent eventInfo) {
		eventInfoDao.delete(eventInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebao.ap99.claim.service.RiEventService#getAllEvent()
	 */
	@Override
	public List<TRiClaimEvent> getAllEvent() {
		return eventInfoDao.loadAll();
	}

	@Override
	public void calculateReportingCurrencyAmount(EventInfo eventInfo) {
		List<ReserveInfo> reserveListRetro = eventInfo.getReserveListRetro();
		if (reserveListRetro != null && reserveListRetro.size() > 0) {
			reserveListRetro.forEach(r -> {
				if(r.getStatus().equals(ClaimConstant.CLAIM_RESERVE_STATUS__NEW) || !r.getAmountOc().equals(r.getOrgAmountOc()) || !r.getOrgPostingFlag().equals(r.getPostingFlag()) ){

				r.setAmountUsd(reserveService.calculateUsdAmount(r.getOriginalCurrency(), r.getAmountOc()));
				r.setExchangeRate(reserveService.getExchangeRate(r.getOriginalCurrency(), ClaimConstant.REPORTING_CURRENCY));}
			});
		}

		List<SettlementInfo> settleListRetro = eventInfo.getSettlementListRetro();
		if (settleListRetro != null && settleListRetro.size() > 0) {
			settleListRetro.forEach(s -> {
				s.getSettlementItemList().forEach(d -> {
					d.setAmountUsd(reserveService.calculateUsdAmount(d.getOriginalCurrency(), d.getAmountOc()));
					d.setExchangeRate(reserveService.getExchangeRate(d.getOriginalCurrency(), ClaimConstant.REPORTING_CURRENCY));
				});
			});
		}
	}
	
	@Override
	public void calculateSummary(EventInfo eventInfo) {

//		List<Long> claimLists = claimInfoDao.getClaimNoList(EventID);

		EventInfo newInfo = new EventInfo();
		List<ReserveSummaryInfo> reserveSummaryRetro = reserveService
				.calcReserveSummary(eventInfo.getReserveListRetro());
		eventInfo.setReserveSummaryRetro(reserveSummaryRetro);


		List<SettlementInfo> settlementListRetro = eventInfo.getSettlementListRetro();
		if (null != settlementListRetro && settlementListRetro.size() != 0) {
			settlementListRetro.forEach(settle -> {
				List<SettlementSummaryInfo> settlementSummaryRetro = settlementService
						.calcSettlementSummary(settle.getSettlementItemList());
				settle.setSettlementSummaryRetro(settlementSummaryRetro);
			});
		}
		BeanUtils.copyPropertiesIngoreNull(newInfo,eventInfo);
//		newInfo.setClaimNos(null);
	}
	
	private void calculateClaimSummary(ClaimInfo claimInfo) {

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
	}

	private List<ClaimSummaryInfo>  calculateClaimLevelSummary(List<ClaimInfo> claimInfoList,EventInfo eventInfo) {

		List<ClaimSummaryInfo> result = new ArrayList<ClaimSummaryInfo>();
		Double reserveSummary = 0.00;
		Double settlementSummary = 0.00;
		Double reserveRetroSummary = 0.00;
		Double settlementRetroSummary = 0.00;
		ClaimSummaryInfo financial = new ClaimSummaryInfo();
		ClaimSummaryInfo retrocession = new ClaimSummaryInfo ();
		
		for(ClaimInfo claimInfo : claimInfoList){
			// add all financial summary
			financial = claimInfoDao.getClaimSummary(claimInfo.getClaimId(),
					ClaimConstant.CLAIM_BUSINESS_DIRECTION__FINANCIAL);
			reserveSummary = financial.getReserveSummary() + reserveSummary;
			settlementSummary = financial.getSettlementSummary() + settlementSummary;
			// add all retrocession summary
			retrocession = claimInfoDao.getClaimSummary(claimInfo.getClaimId(),
					ClaimConstant.CLAIM_BUSINESS_DIRECTION__RETROCESSION);
			reserveRetroSummary = retrocession.getReserveSummary() + reserveRetroSummary;
			settlementRetroSummary = retrocession.getSettlementSummary() + settlementRetroSummary;
			
		}

		// financial summary
		financial.setReserveSummary(reserveSummary);
		financial.setSettlementSummary(settlementSummary);
		financial.setTotalByCurrency(reserveSummary + settlementSummary);
		financial.setCurrencyType(ClaimConstant.GROSSCLAIM);
		result.add(financial);
		
		// retrocession summary
		retrocession.setReserveSummary(reserveRetroSummary);
		retrocession.setSettlementSummary(settlementRetroSummary);
		retrocession.setTotalByCurrency(reserveRetroSummary + settlementRetroSummary);
		
		// net
		ClaimSummaryInfo claimNet = new ClaimSummaryInfo();
		claimNet.setReserveSummary(financial.getReserveSummary() - retrocession.getReserveSummary());
		claimNet.setSettlementSummary(financial.getSettlementSummary() - retrocession.getSettlementSummary());
		claimNet.setTotalByCurrency(financial.getTotalByCurrency() - retrocession.getTotalByCurrency());
		claimNet.setCurrencyType(ClaimConstant.GROSSBEFOREEVENT);
		result.add(claimNet);

		ClaimSummaryInfo retrocessionEvent = claimInfoDao.getClaimSummary(eventInfo.getEventId(),
				ClaimConstant.CLAIM_BUSINESS_DIRECTION__RETROCESSION);
		retrocessionEvent.setCurrencyType(ClaimConstant.EVENTRETROCESSION);
		result.add(retrocessionEvent);
		
		ClaimSummaryInfo totalNet = new ClaimSummaryInfo();
		totalNet.setReserveSummary(claimNet.getReserveSummary() - retrocessionEvent.getReserveSummary());
		totalNet.setSettlementSummary(claimNet.getSettlementSummary() - retrocessionEvent.getSettlementSummary());
		totalNet.setTotalByCurrency(claimNet.getTotalByCurrency() - retrocessionEvent.getTotalByCurrency());
		totalNet.setCurrencyType(ClaimConstant.TOTALNET);
		result.add(totalNet);
		
        return result;
	}
	
	@Override
	public void calculateEventSummary(EventInfo eventInfo) {

		List<Long> claimIdList = claimInfoDao.getClaimIdList(eventInfo.getEventId());
		List<ClaimInfo> allClaimInfoList = new ArrayList<ClaimInfo>();
//		List<ClaimSummaryInfo> eventSummary = new ArrayList<ClaimSummaryInfo>();
		
		for(Long claimId : claimIdList){
			TRiClaimInfo claimEntity = claimService.getClaim(claimId);
			ClaimInfo claimInfo = claimInfoConvertor.entityToModelCascade(claimEntity);
			calculateClaimSummary(claimInfo);
			allClaimInfoList.add(claimInfo);
		}
		List<ClaimSummaryInfo> result = calculateClaimLevelSummary(allClaimInfoList,eventInfo);
//		BeanUtils.copyPropertiesIngoreNull(eventSummary,result);
		eventInfo.setEventSummary(result);
	}

	@Override
	public List<TRiClaimMessage> createClaimMessage(Long eventId) {
		// TODO Auto-generated method stub
		TRiClaimMessage eventAmountMessage = getEventAmountMessage(eventId);
		List<TRiClaimMessage> claimMessages = getPortfolioTransMessage(eventId);

		
		if (!"".equals(eventAmountMessage.getMessageType())){
			claimMessages.add(eventAmountMessage);
		}else{
			messageService.deleteClaimMessage(eventAmountMessage);
		}
		
		for(TRiClaimMessage cl:claimMessages){
			if (!"".equals(cl.getMessageType()) && cl.getMessageType() != null) {

			messageService.createClaimMessage(cl);
			}
		}
		List<TRiClaimMessage> claimMessageReturn = claimMessageDao.getClaimMessageByRefId(eventId);
	
		return claimMessageReturn;
	}


	public TRiClaimMessage getEventAmountMessage(Long eventId) {

		TRiClaimMessage tRiClaimMessage = claimMessageDao.getClaimMessage(eventId, ClaimConstant.EVENT_AMOUNT_MESSAGE);
		
		if (!"".equals(eventId) && eventId != null ) {
			double evetTotalAmount = getEventAmount(eventId);
			if ("".equals(tRiClaimMessage.getMessageType()) || tRiClaimMessage.getMessageType() == null) {
				if (evetTotalAmount > ClaimConstant.AMOUNT_LEVEL_ONE) {

					tRiClaimMessage.setMessageType(ClaimConstant.EVENT_AMOUNT_MESSAGE);
					tRiClaimMessage.setMessageName("event amount message");
					tRiClaimMessage.setRefId(eventId);
					tRiClaimMessage.setRefType(ClaimConstant.REF_TYPE_EVENT);
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
				}else{
					//TODO
					//delete claimMessage
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
			ClaimSummaryInfo financial = claimInfoDao.getClaimSummary((Long)c.getId(),
					ClaimConstant.CLAIM_BUSINESS_DIRECTION__FINANCIAL);
			double claimAmount = financial.getTotalByCurrency();
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

				String flag = "0";
				if (flag.equals(ClaimConstant.IS_WITHDRAWAL) || flag == ClaimConstant.IS_WITHDRAWAL) {
					TRiClaimMessage tRiClaimMessage = claimMessageDao.getClaimMessageBySectionId(claimId,
							ClaimConstant.PORTFOLIOTRANSFER, s);
					if ("".equals(tRiClaimMessage.getMessageType()) || tRiClaimMessage.getMessageType() == null) {
						TRiClaimSectionInfo sectionInfo = sectionDao.getClaimSectionInfoBySection(s);
						tRiClaimMessage.setMessageType(ClaimConstant.PORTFOLIOTRANSFER);
						tRiClaimMessage.setMessageName("PortfolioTransfer message");
						tRiClaimMessage.setRefId(claimId);
						tRiClaimMessage.setRefType(ClaimConstant.REF_TYPE_EVENT);
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

	@Override
	public <T> List<ClaimRecordsExcelModel> bizProcess(Map model) {
		// Auto-generated method stub
		Long refId = Long.valueOf((String)model.get("RefId"));
		List<ClaimRecordsExcelModel> ClaimRecords = new ArrayList<>();
		List<TreeModel> claimList = claimInfoDao.getClaimNoList(refId);
		for(TreeModel  c:claimList){
			ClaimRecords.addAll(claimInfoDao.getClaimRecordsExcelList((Long)c.getId()));
		}
		return ClaimRecords;
	}

	


}
