package com.ebao.ap99.arap.vo;

import java.math.BigDecimal;
import java.util.Date;

public class SettlementHistory {
	private Date settleDate;
	private BigDecimal settleAmount;
	private Integer settleTransType;//FinanceTransactionType
	private String settleCurrencyCode;
	private String entryCode;
	
	public Date getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}
	public BigDecimal getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}
	public Integer getSettleTransType() {
		return settleTransType;
	}
	public void setSettleTransType(Integer settleTransType) {
		this.settleTransType = settleTransType;
	}
	public String getSettleCurrencyCode() {
		return settleCurrencyCode;
	}
	public void setSettleCurrencyCode(String settleCurrencyCode) {
		this.settleCurrencyCode = settleCurrencyCode;
	}
	public String getEntryCode() {
		return entryCode;
	}
	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}
}
