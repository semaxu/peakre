/**
 * 
 */
package com.ebao.ap99.claim.model;

import java.util.Date;

/**
 * @author yujie.zhang
 *
 */
public class SettlementItem {

	private Long settlementItemId ;
	private Date dateOfPayment;
	private String payType;
	
	public Long getSettlementItemId() {
		return settlementItemId;
	}
	public void setSettlementItemId(Long settlementItemId) {
		this.settlementItemId = settlementItemId;
	}
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
	
	
}
