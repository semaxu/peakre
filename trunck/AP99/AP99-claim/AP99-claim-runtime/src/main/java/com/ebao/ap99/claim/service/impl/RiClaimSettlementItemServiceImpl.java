/**
 * 
 */
package com.ebao.ap99.claim.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.convertor.SettlementItemInfoConvertor;
import com.ebao.ap99.claim.dao.RiClaimSettlementItemDao;
import com.ebao.ap99.claim.entity.TRiClaimSettlementItem;
import com.ebao.ap99.claim.model.Settlement;
import com.ebao.ap99.claim.model.SettlementDetailHistory;
import com.ebao.ap99.claim.model.SettlementHistoryInfo;
import com.ebao.ap99.claim.model.SettlementHistoryQuery;
import com.ebao.ap99.claim.model.SettlementItem;
import com.ebao.ap99.claim.model.SettlementItemInfo;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.ap99.parent.model.TreeModel;

/**
 * @author yujie.zhang
 *
 */
public class RiClaimSettlementItemServiceImpl implements com.ebao.ap99.claim.service.RiClaimSettlementItemService {

	/* (non-Javadoc)
	 * @see com.ebao.ap99.claim.service.RiClaimSettlementItemService#getSettlementItemById(long)
	 */
	@Autowired
	public RiClaimSettlementItemDao settlementItemDao;
	@Autowired
	public SettlementItemInfoConvertor settlementItemInfoConvertor;
	
	@Override
	public List<TRiClaimSettlementItem> getSettlementItemById(long settlementId) {
		List<TRiClaimSettlementItem> list = settlementItemDao.getSettlementItemList(settlementId);
		
		return list;
	}
	
	@Override
	public List<TRiClaimSettlementItem> getItemList(long settlementId) {
		List<TRiClaimSettlementItem> list = settlementItemDao.getItemList(settlementId);
		
		return list;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void updateSettlementItemById(long settlementId) {
		List<TRiClaimSettlementItem>   settlementItems = settlementItemDao.getSettlementItemListById(settlementId);
		for(TRiClaimSettlementItem si : settlementItems){
			si.setOrgPostingFlag(si.getPostingFlag());
			settlementItemDao.merge(si);
		}
	}
	@Override
	public List<TRiClaimSettlementItem> getSettlementItemListPostingFlagChanged(long settlementId) {
		return settlementItemDao.getSettlementItemListPostingFlagChanged(settlementId);
	}
	@Override
	public List<SettlementHistoryInfo> getSettlementItemHistory(SettlementHistoryQuery settlementQuery) {
		return settlementItemDao.getSettlementItemHistory(settlementQuery);
	}
	@Override
	public List<SettlementHistoryInfo> getSettlementItemSummary(SettlementHistoryQuery settlementQuery) {
		return settlementItemDao.getSettlementItemSummary(settlementQuery);
	}
	@Override
	public List<TreeModel> getUWyearList(long refId, String BusinessDirection) {
		return settlementItemDao.getUWYear(refId, BusinessDirection);
	}
	@Override
	public List<TreeModel> getContractSection(long refId, String BusinessDirection) {
		return settlementItemDao.getContractSection(refId, BusinessDirection);
	}
	@Override
	public SettlementDetailHistory getDetailHistory(long settleDetailId) {
		return settlementItemDao.getDetailHistory(settleDetailId);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public TRiClaimSettlementItem updateSettlementItem(TRiClaimSettlementItem settlementItem) {
		return settlementItemDao.merge(settlementItem);
	}
	@Override
	public List<SettlementItemInfo> getSettlementItemListById(long settlementId) throws Exception {
		
//		List<TRiClaimSettlementItem> settlementItemList = settlementItemDao.getSettlementItemListById(settlementId);
//		List<SettlementItemInfo> settlementItemInfo= settlementItemInfoConvertor.entityListToModelList(settlementItemList);
		List<SettlementItemInfo> settlementItemInfo =BeanUtils.convertList(settlementItemDao.getSettlementItemListById(settlementId),SettlementItemInfo.class);
		return settlementItemInfo;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void deleteSettlementItemList(List<SettlementItemInfo> settlementItemList) throws Exception {
		List<TRiClaimSettlementItem> deleteSettlementItemList = BeanUtils.convertList(settlementItemList, TRiClaimSettlementItem.class);
        for(TRiClaimSettlementItem d:deleteSettlementItemList){
        	settlementItemDao.delete(settlementItemDao.merge(d));
        }
	}
	@SuppressWarnings("deprecation")
	@Override
	public void updatePayment(Settlement settlement){
		List<SettlementItem> settlementItem = settlement.getSettlementItem();
		for(SettlementItem s:settlementItem){
			//s.getSettlementItemId();
			TRiClaimSettlementItem tRiClaimSettlementItem = settlementItemDao.load(s.getSettlementItemId());
			tRiClaimSettlementItem.setDateOfPayment(s.getDateOfPayment());
			tRiClaimSettlementItem.setPayType(s.getPayType());
			settlementItemDao.merge(tRiClaimSettlementItem);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updatePaymentBySettlementId(Settlement settlement) {
		List<TRiClaimSettlementItem> list = settlementItemDao.getSettlementItemList(settlement.getSettlementId());
		
		for(TRiClaimSettlementItem l:list){
			l.setDateOfPayment(settlement.getDateOfPayment());
			l.setPayType(settlement.getPayType());
			settlementItemDao.merge(l);
		}
		
	}

}
