package com.ebao.ap99.accounting.model;

import java.math.BigDecimal;



public class SoaSectionItemModel {
	

	private String itemId;

	private BigDecimal amount = BigDecimal.ZERO;

	private String entryCode;


	private BigDecimal percentage;
	
	
    private BigDecimal shareAmount = BigDecimal.ZERO;
	

	private String entryItem;

	
	private String soaSectionId;

	public SoaSectionItemModel() {
		// TODO Auto-generated constructor stub
	}
	


	public String getSoaSectionId() {
		return soaSectionId;
	}


	public void setSoaSectionId(String soaSectionId) {
		this.soaSectionId = soaSectionId;
	}







	public String getItemId() {
		return itemId;
	}



	public void setItemId(String itemId) {
		this.itemId = itemId;
	}



	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}




	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}



	public BigDecimal getShareAmount() {
		return shareAmount;
	}



	public void setShareAmount(BigDecimal shareAmount) {
		this.shareAmount = shareAmount;
	}



	public String getEntryItem() {
		return entryItem;
	}



	public void setEntryItem(String entryItem) {
		this.entryItem = entryItem;
	}



	public String getEntryCode() {
		return entryCode;
	}



	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}	
	

}
