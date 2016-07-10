/**
 * 
 */
package com.ebao.ap99.claim.service;

import java.util.List;

import com.ebao.ap99.claim.entity.TRiClaimSettlementLog;
import com.ebao.ap99.claim.model.SettlementLogInfo;

/**
 * @author yujie.zhang
 *
 */
public interface RiClaimSettlementLogService {
	void create(TRiClaimSettlementLog settlementLog);
	TRiClaimSettlementLog update(TRiClaimSettlementLog settlementLog);
	TRiClaimSettlementLog get(long settlementLogId);
	void delete(TRiClaimSettlementLog settlementLog);
	List<TRiClaimSettlementLog> getAll(TRiClaimSettlementLog settlementLog);
	List<SettlementLogInfo> getBySettlementId(long settlementId) throws Exception;

}
