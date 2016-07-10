package com.ebao.ap99.accounting.UI.model;

import java.math.BigDecimal;

public class SoaSectionItemVO {
	
	
	private String itemId;


	private BigDecimal entryCode;


	private BigDecimal ourShareAmount;
	

	private BigDecimal amount;

	private String entryItem;

	private BigDecimal shareAmount;

	private BigDecimal percentage;
	
	private boolean notToReverseE;
	
	



	public String getEntryItem() {
		return entryItem;
	}



	public void setEntryItem(String entryItem) {
		this.entryItem = entryItem;
	}



	public boolean isNotToReverseE() {
		return notToReverseE;
	}



	public void setNotToReverseE(boolean notToReverseE) {
		this.notToReverseE = notToReverseE;
	}



	public BigDecimal getAmount() {
		return amount;
	}



	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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



	public SoaSectionItemVO() {
		// TODO Auto-generated constructor stub
	}



	public String getItemId() {
		return itemId;
	}



	public void setItemId(String itemId) {
		this.itemId = itemId;
	}



	public BigDecimal getEntryCode() {
		return entryCode;
	}



	public void setEntryCode(BigDecimal entryCode) {
		this.entryCode = entryCode;
	}



	public BigDecimal getOurShareAmount() {
		return ourShareAmount;
	}



	public void setOurShareAmount(BigDecimal ourShareAmount) {
		this.ourShareAmount = ourShareAmount;
	}

}
