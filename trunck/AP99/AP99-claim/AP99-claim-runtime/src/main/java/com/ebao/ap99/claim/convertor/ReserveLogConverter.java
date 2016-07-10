/**
 * 
 */
package com.ebao.ap99.claim.convertor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.dao.RiClaimReserveLogDao;
import com.ebao.ap99.claim.entity.TRiClaimReserve;
import com.ebao.ap99.claim.entity.TRiClaimReserveLog;
import com.ebao.ap99.claim.model.ReserveHistoryInfo;
import com.ebao.ap99.claim.model.ReserveHistoryQuery;
import com.ebao.ap99.claim.model.ReserveSummaryInfo;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

/**
 * @author yujie.zhang
 *
 */
public class ReserveLogConverter {
	@Autowired
	private RiClaimReserveLogDao reserveLogDao;

	public TRiClaimReserveLog reserveToLog(TRiClaimReserve reserve) {
		TRiClaimReserveLog log = new TRiClaimReserveLog();
		BeanUtils.copyProperties(log, reserve);
		// try {
		// if (reserveInfo.getInsertTime() != null)
		// entity.setInsertTime(formatter.parse(reserveInfo.getInsertTime()));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		log.setStatus("1");
		return log;
	}
	
//	public List<ReserveSummaryInfo> calcReserveSummary(List<ReserveHistoryInfo> reserveList) {
//
//		if (null == reserveList || reserveList.size() == 0)
//			return null;
//
//		List<ReserveSummaryInfo> reserveSummary = new ArrayList<ReserveSummaryInfo>();
//
//		List<String> currencyList = reserveList.stream().filter(r -> r.getOriginalCurrency() != null)
//				.map(r -> r.getOriginalCurrency()).distinct().collect(Collectors.toList());
//
//		for (String currency : currencyList) {
//			Double amountOneCurrency = reserveList.parallelStream()
//					.filter(r -> r.getOriginalCurrency() != null && r.getOriginalCurrency().equals(currency)).collect(Collectors.summingDouble(ReserveHistoryInfo::getAmountOcDouble));
//			ReserveSummaryInfo summary  = new ReserveSummaryInfo();
//			summary.setCurrencyType(currency);
//			summary.setLossTotal(amountOneCurrency);
//			reserveSummary.add(summary);
//		}
//        double USDSummary=calcUSDReserveSummary(reserveList);
//		ReserveSummaryInfo summary  = new ReserveSummaryInfo();
//		summary.setCurrencyType("USD Equivalent");
//		summary.setLossTotal(USDSummary);
//		reserveSummary.add(summary);
//		
//		return reserveSummary;
//	}
	
	public List<ReserveSummaryInfo> calcReserveSummary(ReserveHistoryQuery reserveHistoryQuery) {
		List<ReserveHistoryInfo> reserveList = reserveLogDao.getLatestReserveSummary(reserveHistoryQuery);
		List<ReserveSummaryInfo> reserveSummary = new ArrayList<ReserveSummaryInfo>();
		for(ReserveHistoryInfo re:reserveList){
			ReserveSummaryInfo summary  = new ReserveSummaryInfo();
			summary.setCurrencyType(re.getOriginalCurrency());
			summary.setLossTotal(re.getAmountOc().doubleValue());
			reserveSummary.add(summary);
		}
		 double USDSummary=calcUSDReserveSummary(reserveList);
			ReserveSummaryInfo summary  = new ReserveSummaryInfo();
			summary.setCurrencyType("USD Equivalent");
			summary.setLossTotal(USDSummary);
			reserveSummary.add(summary);
			
			return reserveSummary;
		
	}
	public double calcUSDReserveSummary(List<ReserveHistoryInfo> reserveList){
		if (null == reserveList || reserveList.size() == 0)
			return 0;
		double USDSummary = 0;
		
		for(ReserveHistoryInfo rl:reserveList){
			
			USDSummary=USDSummary+rl.getAmountUsd().doubleValue();
		}
		return USDSummary;
		
	}
}
