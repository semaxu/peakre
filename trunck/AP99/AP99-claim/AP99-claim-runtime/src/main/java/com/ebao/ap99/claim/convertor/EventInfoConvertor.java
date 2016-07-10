/**
 * 
 */
package com.ebao.ap99.claim.convertor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.restlet.engine.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.dao.RiClaimEventDao;
import com.ebao.ap99.claim.entity.TRiClaimEvent;
import com.ebao.ap99.claim.entity.TRiClaimReserve;
import com.ebao.ap99.claim.entity.TRiClaimSettlement;
import com.ebao.ap99.claim.model.ClaimInfo;
import com.ebao.ap99.claim.model.ClaimRecords;
import com.ebao.ap99.claim.model.ClaimRecordsSummary;
import com.ebao.ap99.claim.model.EventInfo;
import com.ebao.ap99.claim.model.ReserveInfo;
import com.ebao.ap99.claim.model.SettlementInfo;
import com.ebao.ap99.parent.constant.ReInsuranceCst;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

/**
 * @author yujie.zhang
 *
 */
public class EventInfoConvertor {

	private SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd HH:mm");

	@Autowired
	private ReserveInfoConvertor reserveInfoConvertor;
	@Autowired
	private SettlementInfoConvertor settleInfoConvertor;

	@Autowired
	private RiClaimEventDao eventInfoDao;

	public TRiClaimEvent modelToEntity(EventInfo eventInfo) throws Exception {
		TRiClaimEvent tRiClaimEvent = new TRiClaimEvent();
		BeanUtils.copyProperties(tRiClaimEvent, eventInfo);
//		if (!StringUtils.isNullOrEmpty(eventInfo.getDateOfLossFrom())) {
//			tRiClaimEvent.setDateOfLossFrom((Date) formatter.parse(eventInfo.getDateOfLossFrom().replace("T", " ")));
//		}
//		if (!StringUtils.isNullOrEmpty(eventInfo.getDateOfLossTo())) {
//			tRiClaimEvent.setDateOfLossTo((Date) formatter.parse(eventInfo.getDateOfLossTo().replace("T", " ")));
//		}
//
//		if (!StringUtils.isNullOrEmpty(eventInfo.getDateOfCreation())) {
//			tRiClaimEvent.setDateOfCreation((Date) formatter.parse(eventInfo.getDateOfCreation().replace("T", " ")));
//		}

		return tRiClaimEvent;

	}

	public EventInfo entityToModel(TRiClaimEvent claimEvent) {
		EventInfo eventInfo = new EventInfo();
		BeanUtils.copyProperties(eventInfo, claimEvent);

//		eventInfo.setDateOfLossFrom(formatter.format(claimEvent.getDateOfLossFrom()).replace(" ", "T"));
//		eventInfo.setDateOfLossTo(formatter.format(claimEvent.getDateOfLossTo()).replace(" ", "T"));
//		eventInfo.setDateOfCreation(formatter.format(claimEvent.getDateOfCreation()).replace(" ", "T"));
		
		return eventInfo;

	}

	public TRiClaimEvent modelToEntityCascade(EventInfo eventInfo) throws Exception {
		TRiClaimEvent eventEntity = modelToEntity(eventInfo);

		if (eventInfo.getReserveListRetro() != null && eventInfo.getReserveListRetro().size() != 0) {
			List<TRiClaimReserve> reserveEntitys = reserveInfoConvertor
					.convertToEntityList(eventInfo.getReserveListRetro(), ReInsuranceCst.CONTRACT_CATEGORY_RETRO);
			eventEntity.setTRiClaimReserves(reserveEntitys);
		}
		if (eventInfo.getSettlementListRetro() != null && eventInfo.getSettlementListRetro().size() != 0) {
			List<TRiClaimSettlement> settlementEtitys = settleInfoConvertor.convertToEntityListCascade(
					eventInfo.getSettlementListRetro(), ReInsuranceCst.CONTRACT_CATEGORY_RETRO);
			eventEntity.setTRiClaimSettlements(settlementEtitys);
		}
		return eventEntity;
	}

	public EventInfo entityToModelCascade(TRiClaimEvent eventEntity) {
		EventInfo eventInfo = entityToModel(eventEntity);
		if (eventEntity.getTRiClaimReserves() != null && eventEntity.getTRiClaimReserves().size() != 0) {
			Map<String, List<ReserveInfo>> result = reserveInfoConvertor
					.entityListToModelList(eventEntity.getTRiClaimReserves());
			// eventInfo.setReserveList(result.get("financial"));
			eventInfo.setReserveListRetro(result.get("retro"));
		}
		if (eventEntity.getTRiClaimSettlements() != null && eventEntity.getTRiClaimSettlements().size() != 0) {
			Map<String, List<SettlementInfo>> result = settleInfoConvertor
					.entityListToModelListCascade(eventEntity.getTRiClaimSettlements());
			// eventInfo.setSettlementList(result.get("financial"));
			eventInfo.setSettlementListRetro(result.get("retro"));
		}

		return eventInfo;
	}

	public EventInfo getClaimRecords(EventInfo eventInfo) {

		List<ClaimRecords> claimlists = eventInfoDao.getClaimRecords(eventInfo);

		double lossReserve = 0;
		double lossPaid = 0;
		double retroOs = 0;
		double retroPaid = 0;
		for (ClaimRecords c : claimlists) {
			lossReserve = lossReserve + c.getLossReverse().doubleValue();
			lossPaid = lossPaid + c.getLossPaid().doubleValue();
			retroOs = retroOs + c.getRetroOS().doubleValue();
			retroPaid = retroPaid + c.getRetroPaid().doubleValue();
		}
		ClaimRecords claimSumary = new ClaimRecords();
		claimSumary.setClaimNo("Total");
		claimSumary.setLossReverse(new BigDecimal(lossReserve));
		claimSumary.setLossPaid(new BigDecimal(lossPaid));
		claimSumary.setRetroOS(new BigDecimal(retroOs));
		claimSumary.setRetroPaid(new BigDecimal(retroPaid));
		claimlists.add(claimSumary);
		eventInfo.setClaimRecordsList(claimlists);

		List<ClaimRecordsSummary> claimRecordsSummaryList = new ArrayList<>();
		ClaimRecordsSummary claimRecordsSummary = new ClaimRecordsSummary();
		claimRecordsSummary.setCurrency(ClaimConstant.REPORTING_CURRENCY);
		claimRecordsSummary.setGrossIncurredLoss(lossReserve + lossPaid);
		claimRecordsSummary.setGrossRetrocessionRecovery(retroOs + retroPaid);
		claimRecordsSummary.setNetTotal(lossReserve + lossPaid - retroOs - retroPaid);
		claimRecordsSummaryList.add(claimRecordsSummary);
		eventInfo.setClaimRecordsSummary(claimRecordsSummaryList);

		// eventInfo.setTotalIncurredLoss(new
		// BigDecimal(lossReserve+lossPaid+retroOs+retroPaid));
		eventInfo.setClaimNos(eventInfoDao.getClaimNoList(eventInfo));
		return eventInfo;
	}
	
	public List<ReserveInfo> getAMLReserveList(EventInfo eventInfo){
		List<ReserveInfo> reserveList = new ArrayList<>();
		
	
		if (eventInfo.getReserveListRetro() != null && eventInfo.getReserveListRetro().size() != 0)
			reserveList.addAll(eventInfo.getReserveListRetro());

		return reserveList;
	}
}
