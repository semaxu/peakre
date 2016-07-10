/**
 * 
 */
package com.ebao.ap99.claim.convertor;


import com.ebao.ap99.claim.entity.TRiClaimSettlement;
import com.ebao.ap99.claim.entity.TRiClaimSettlementLog;
import com.ebao.ap99.claim.model.SettlementLogInfo;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

/**
 * @author yujie.zhang
 *
 */
public class SettlementLogConvertor {
	public TRiClaimSettlementLog settlementToLog(TRiClaimSettlement settlement) {
		TRiClaimSettlementLog log = new TRiClaimSettlementLog();

		BeanUtils.copyProperties(log, settlement);

		return log;
	}
	
	public SettlementLogInfo entityToModel(TRiClaimSettlementLog Settlementlog){
		
		SettlementLogInfo settlementLogInfo = new SettlementLogInfo();
		BeanUtils.copyProperties(settlementLogInfo, Settlementlog);
		
		return settlementLogInfo;
	}
//    public List<SettlementLogInfo> entityToModelList(List<TRiClaimSettlementLog> entityList){
//		
//    	List<SettlementLogInfo> settleLogInfoList = new ArrayList<SettlementLogInfo>();
//		entityList.forEach((TRiClaimSettlementLog entity) -> {
//			settleLogInfoList.add(entityToModel(entity));
//		});
//
//		return settleLogInfoList;
//	}
}
