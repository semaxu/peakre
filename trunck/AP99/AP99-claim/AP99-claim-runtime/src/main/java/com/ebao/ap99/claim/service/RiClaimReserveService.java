package com.ebao.ap99.claim.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ebao.ap99.claim.entity.TRiClaimReserve;
//import com.ebao.ap99.claim.model.ClaimInfo;
import com.ebao.ap99.claim.model.ReserveInfo;
import com.ebao.ap99.claim.model.ReserveSummaryInfo;

public interface RiClaimReserveService {
	void createClaimReserve(TRiClaimReserve claiminfo);

	TRiClaimReserve updateClaimReserve(TRiClaimReserve claiminfo);

	TRiClaimReserve getClaimReserve(long claimId);

	void deleteClaimReserve(TRiClaimReserve claiminfo);

	List<TRiClaimReserve> getAllClaimReserves();

	void createClaimReserveList(List<TRiClaimReserve> claiminfoList);

	List<TRiClaimReserve> getReserveList(long claimId);

	List<ReserveSummaryInfo> calcReserveSummary(List<ReserveInfo> reserveList);

//	void submitReserve(TRiClaimReserve reserve, ClaimInfo claimInfo);

//	void dealReserve(ClaimInfo claimInfo);

	BigDecimal calculateUsdAmount(String orgCurrency, BigDecimal orgAmount);

	BigDecimal getExchangeRate(String orgCurrency,String reoprtingCurrency);

	BigDecimal getExchangeRateToReportingCurrency(String reportingCurrency,String orgCurrency, Date date);

	Map<String, String> getAllCurrencyOnClaim(long claimId);

	Map<String, Double> getLatestReserveAmountGroupByCurrency(long claimId);

	Double getReserveUsdEquivalent(long claimId);
	
	void deleteReserveList(List<ReserveInfo> reserveList) throws Exception;

	void dealReserveSubmit(long refId,String reserveUpdateRemark,String reserveUpdateRemarkRetro) throws Exception;
	
	
}
