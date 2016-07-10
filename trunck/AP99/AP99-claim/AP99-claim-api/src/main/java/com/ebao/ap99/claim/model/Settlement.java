/**
 * 
 */
package com.ebao.ap99.claim.model;

import java.util.Date;
import java.util.List;

/**
 * @author yujie.zhang
 *
 */
public class Settlement {

	private long settlementId;
	private List<SettlementItem> settlementItem;
	private Date dateOfPayment;
	private String payType;
	
	public Date getDateOfPayment() {
		return dateOfPayment;
	}
	public void setDateOfPayment(Date dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public long getSettlementId() {
		return settlementId;
	}
	public void setSettlementId(long settlementId) {
		this.settlementId = settlementId;
	}
	public List<SettlementItem> getSettlementItem() {
		return settlementItem;
	}
	public void setSettlementItem(List<SettlementItem> settlementItem) {
		this.settlementItem = settlementItem;
	}
	
	
	
}
