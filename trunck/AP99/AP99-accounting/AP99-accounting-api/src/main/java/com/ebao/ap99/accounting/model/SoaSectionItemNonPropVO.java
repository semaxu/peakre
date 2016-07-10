package com.ebao.ap99.accounting.model;

import java.math.BigDecimal;

public class SoaSectionItemNonPropVO {


	private String entryCode;

	private BigDecimal ourShareAmount;
	
	private BigDecimal amount;

	private String entryItem;

	private BigDecimal shareAmount;

	private BigDecimal percentage;

	public BigDecimal getOurShareAmount() {
		return ourShareAmount;
	}

	public void setOurShareAmount(BigDecimal ourShareAmount) {
		this.ourShareAmount = ourShareAmount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getEntryItem() {
		return entryItem;
	}

	public void setEntryItem(String entryItem) {
		this.entryItem = entryItem;
	}

	public BigDecimal getShareAmount() {
		return shareAmount;
	}

	public void setShareAmount(BigDecimal shareAmount) {
		this.shareAmount = shareAmount;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public String getEntryCode() {
		return entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}
	
	
	
}
