/**
 * 
 */
package com.ebao.ap99.claim.restful;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.service.FinanceService;
import com.ebao.ap99.arap.vo.SettlementHistory;
import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.convertor.SettlementInfoConvertor;
import com.ebao.ap99.claim.convertor.SettlementItemInfoConvertor;
import com.ebao.ap99.claim.convertor.SettlementLogConvertor;
//import com.ebao.ap99.claim.model.SettlementChangePostingModel;
import com.ebao.ap99.claim.model.SettlementDetailHistory;
import com.ebao.ap99.claim.model.SettlementHistoryInfo;
import com.ebao.ap99.claim.model.SettlementHistoryQuery;
import com.ebao.ap99.claim.model.SettlementInfo;
import com.ebao.ap99.claim.model.SettlementItemInfo;
import com.ebao.ap99.claim.model.SettlementLogInfo;
import com.ebao.ap99.claim.service.RiClaimSettlementItemService;
import com.ebao.ap99.claim.service.RiClaimSettlementLogService;
import com.ebao.ap99.claim.service.RiClaimSettlementService;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author yujie.zhang
 *
 */
@Module(com.ebao.ap99.parent.constant.Module.CLAIM)
@Path("/settlement")
public class ClaimSettlementRestfulService {

	private static Logger logger = LoggerFactory.getLogger();

	@Autowired
	public RiClaimSettlementService settlementService;
	@Autowired
	public SettlementInfoConvertor settlementInfoConvertor;

	@Autowired
	public RiClaimSettlementItemService settlementItemService;
	@Autowired
	public SettlementItemInfoConvertor settlementItemInfoConvertor;
	@Autowired
	public SettlementLogConvertor settlementLogConvertor;
	@Autowired
	public RiClaimSettlementLogService settlementLogService;
	
	@Autowired
	public FinanceService financeService;

	@GET
	@Path("/settlementName")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SettlementInfo getSettlementName(@QueryParam("RefId") long RefId) throws Exception {

		//get SettlementName
		String settlementname = settlementService.getSettlementName(RefId);
		SettlementInfo settlementinforeturn = new SettlementInfo();
		settlementinforeturn.setSettlementName(settlementname);

		return settlementinforeturn;
	}

	@POST
	@Path("/settlementNoList/{RefId}/{BusinessDirection}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TreeModel> getSettlementNoList(@PathParam("RefId") long RefId,
			@PathParam("BusinessDirection") String businessDirection) throws Exception {

		logger.info("RefId=========" + RefId);
		//changePosting get SettlementNoList
		List<TreeModel> settlementListReturn = settlementInfoConvertor
				.getSettlementList(settlementService.getApprovedSettleByRefId(RefId), businessDirection);

		return settlementListReturn;
	}

	@GET
	@Path("/settlementItemList/{SettlementId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SettlementInfo getSettlementItemList(@PathParam("SettlementId") long SettlementId) throws Exception {
		
		logger.info("SettlementId=========" + SettlementId);
		//load settlementItem by SettlementId
		List<SettlementItemInfo> settlementItemInfo = settlementItemService.getSettlementItemListById(SettlementId);
		SettlementInfo settlementInfo = new SettlementInfo();
		settlementInfo.setSettlementItemList(settlementItemInfo);

		return settlementInfo;
	}

	@POST
	@Path("/changePostFlag")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void changePostFlag(SettlementInfo settlementInfo) throws Exception {

		//  changePosting
		settlementService.changePosting(settlementInfo);

	}

	@GET
	@Path("/select")
	public Object getUWYear(@QueryParam("RefId") long RefId,
			@QueryParam("BusinessDirection") String BusinessDirection) {
		SettlementHistoryQuery settlementHistoryReturn = new SettlementHistoryQuery();
		//UnderwritingYear 
		settlementHistoryReturn.setUWYearList(settlementItemService.getUWyearList(RefId, BusinessDirection));
		//ContractSection
		settlementHistoryReturn.setContractSectionList(settlementItemService.getContractSection(RefId, BusinessDirection));

		return settlementHistoryReturn;
	}

	@POST
	@Path("/settlementHistory")
	public Object getSettlementList(SettlementHistoryQuery settlementHistoryQuery) {

		logger.info("claimQuery.settlementHistory");
		//settlementItemHistorylist
		List<SettlementHistoryInfo> settlementItemHistoryList = settlementItemService
				.getSettlementItemHistory(settlementHistoryQuery);
		settlementHistoryQuery.setSettlementItemInfo(settlementItemHistoryList);

		//settlementItemSummary
		List<SettlementHistoryInfo> settlementItemSummary = settlementItemService
				.getSettlementItemSummary(settlementHistoryQuery);
		settlementHistoryQuery.setSettlementItemSummary(settlementItemSummary);
		
		//settlementSummary
		settlementHistoryQuery
				.setSettlementSummary(settlementItemInfoConvertor.calSettlementItemSummary(settlementHistoryQuery));
		

		return settlementHistoryQuery;
	}

	@GET
	@Path("/settlementDetail/{SettleDetailId}")
	public Object getSettlementDetail(@PathParam("SettleDetailId") long SettleDetailId) throws Exception {

		//get settlementDetailHistoryInfo
		SettlementDetailHistory settlementDetailHistory = settlementItemService.getDetailHistory(SettleDetailId);
		
		//get settlementlogList
		long settlementId = settlementDetailHistory.getSettlementId();
		List<SettlementLogInfo> settlementLogList = settlementLogService.getBySettlementId(settlementId);
		settlementDetailHistory.setSettlementLog(settlementLogList);
		
		List<SettlementHistory> settleHistory = financeService.getSettlementHistory(ClaimConstant.BIZ_CLAIM_SETTLEMENT, settlementId);
		settlementDetailHistory.setSettleHistory(settleHistory);
		return settlementDetailHistory;

	}

}
