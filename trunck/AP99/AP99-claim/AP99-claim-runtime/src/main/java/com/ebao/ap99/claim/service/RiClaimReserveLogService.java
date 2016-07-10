/**
 * 
 */
package com.ebao.ap99.claim.service;

import java.util.List;

import com.ebao.ap99.claim.entity.TRiClaimReserve;
import com.ebao.ap99.claim.entity.TRiClaimReserveLog;
import com.ebao.ap99.claim.model.ClaimInfo;
import com.ebao.ap99.claim.model.ReserveHistoryInfo;
import com.ebao.ap99.claim.model.ReserveHistoryQuery;

/**
 * @author yujie.zhang
 *
 */
public interface RiClaimReserveLogService {
	void createReserveLog(TRiClaimReserveLog reserveLog);
	TRiClaimReserveLog update(TRiClaimReserveLog reserveLog);
	TRiClaimReserveLog getClaimReserveLog(long reserveLogId);
	void delete(TRiClaimReserveLog reserveLog);
	List<TRiClaimReserveLog> getAll();
	void submitReserveLog(TRiClaimReserve reserve,ClaimInfo claimInfo);
	List<ReserveHistoryInfo> getReserveHistoryList(ReserveHistoryQuery reserveHistoryQuery);

}
