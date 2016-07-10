/**
 * 
 */
package com.ebao.ap99.claim.service;

import java.util.List;

import com.ebao.ap99.claim.entity.TRiClaimSettlementItem;
import com.ebao.ap99.claim.model.Settlement;
import com.ebao.ap99.claim.model.SettlementDetailHistory;
import com.ebao.ap99.claim.model.SettlementHistoryInfo;
import com.ebao.ap99.claim.model.SettlementHistoryQuery;
import com.ebao.ap99.claim.model.SettlementItemInfo;
import com.ebao.ap99.parent.model.TreeModel;

/**
 * @author yujie.zhang
 *
 */
public interface RiClaimSettlementItemService {

	List<TRiClaimSettlementItem> getSettlementItemById(long settlementId);
	
	void updateSettlementItemById(long settlementId);
	List<TRiClaimSettlementItem> getSettlementItemListPostingFlagChanged(long settlementId);
	List<SettlementItemInfo>  getSettlementItemListById(long settlementId) throws Exception;
	List<SettlementHistoryInfo> getSettlementItemHistory(SettlementHistoryQuery settlementQuery);
	List<SettlementHistoryInfo> getSettlementItemSummary(SettlementHistoryQuery settlementQuery);
	
	List<TreeModel> getUWyearList(long refId,String BusinessDirection);
	List<TreeModel> getContractSection(long refId,String BusinessDirection);
	SettlementDetailHistory getDetailHistory(long settleDetailId);
	TRiClaimSettlementItem updateSettlementItem(TRiClaimSettlementItem settlementItem);
	
	void deleteSettlementItemList(List<SettlementItemInfo> settlementItemList) throws Exception;
	
	void updatePayment(Settlement settlement);
    void updatePaymentBySettlementId(Settlement settlement);

	List<TRiClaimSettlementItem> getItemList(long settlementId);
}
