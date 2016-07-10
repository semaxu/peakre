package com.ebao.ap99.claim.service;

import java.util.List;

import com.ebao.ap99.claim.entity.TRiClaimInfo;
import com.ebao.ap99.claim.entity.TRiClaimMessage;
import com.ebao.ap99.claim.entity.TRiClaimReserve;
import com.ebao.ap99.claim.entity.TRiClaimSettlement;
import com.ebao.ap99.claim.entity.TRiClaimSettlementItem;
import com.ebao.ap99.claim.model.ClaimInfo;

public interface RiClaimInfoService {
	void createClaimInfo(TRiClaimInfo claiminfo) throws Exception;

	TRiClaimInfo updateClaimInfo(TRiClaimInfo claiminfo);

	TRiClaimInfo getClaim(long claimId);

	void deleteClaimInfo(TRiClaimInfo claiminfo);

	List<TRiClaimInfo> getAllClaims();

	ClaimInfo getClaimInfoWithPendingSettlement(ClaimInfo claimInfo);

	void calculateSummary(ClaimInfo claimInfo);

	void calculateReportingCurrencyAmount(ClaimInfo claimInfo);

	void calculateTotalLoss(ClaimInfo claimInfo);
	
	void removeRelatedClaim(Long relatedClaim);

	// void calculateUsdEquivalent(ClaimInfo claimInfo);
    double claimSummary(Long claimId);
    
    List<TRiClaimMessage> createClaimMessage(Long claimId);

//	TRiClaimInfo insertClaimInfo(TRiClaimInfo claiminfo);

//	TRiClaimInfo insertClaimInfo(Long claimId);

	void insertClaimReserve(Long claimId,List<TRiClaimReserve> reserveList) throws Exception;
	
	TRiClaimInfo copyFinaReserve(TRiClaimInfo claimEntity) throws Exception;
	
	TRiClaimInfo copyFinaSettle(TRiClaimInfo claimEntity);
	
	void updateClaimReserve (TRiClaimInfo claimEntity);


	void insertClaimSettle(Long claimId,List<TRiClaimSettlement> settlementList);
}
