/**
 * 
 */
package com.ebao.ap99.claim.service;

import java.util.List;
import java.util.Map;

import com.ebao.ap99.claim.entity.TRiClaimSettlement;
//import com.ebao.ap99.claim.model.ClaimInfo;
//import com.ebao.ap99.claim.model.SettlementChangePostingModel;
import com.ebao.ap99.claim.model.SettlementInfo;
import com.ebao.ap99.claim.model.SettlementItemInfo;
import com.ebao.ap99.claim.model.SettlementSummaryInfo;

/**
 * @author gang.wang
 *
 */
public interface RiClaimSettlementService {

	List<TRiClaimSettlement> getOutstandingSettleByRefId(long refId);

	// public List<TRiClaimSettlement> getOpenSettleByClaimId(long claimId);
	List<TRiClaimSettlement> getApprovedSettleByRefId(long refId);

	TRiClaimSettlement updateSettlement(TRiClaimSettlement settlement);

//	void dealSettlement(ClaimInfo claimInfo);

	String getSettlementName(long RefId) throws Exception;

	List<SettlementSummaryInfo> calcSettlementSummary(List<SettlementItemInfo> settlementItemList);

	Map<String, Double> getApprovedSettleAmountGroupByCurrency(long refId);

	Double getSettlementUsdEquivalent(long claimId);
	
	TRiClaimSettlement getSettlement(long settlementId);
	void changePosting(SettlementInfo settlementInfo) throws Exception;

	TRiClaimSettlement updateSettlement(TRiClaimSettlement settlement, String type);

	void dealSettlementSubmit(Long refId) throws Exception;
}
