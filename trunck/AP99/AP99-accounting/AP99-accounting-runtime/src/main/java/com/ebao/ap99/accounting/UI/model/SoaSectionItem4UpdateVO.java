package com.ebao.ap99.accounting.UI.model;

import java.math.BigDecimal;

public class SoaSectionItem4UpdateVO {

	private String itemId;

	private String entryCode;

	private BigDecimal ourShareAmount = BigDecimal.ZERO;
	
	private BigDecimal amount = BigDecimal.ZERO;

	private String entryItem;

	private BigDecimal shareAmount = BigDecimal.ZERO;

	private BigDecimal percentage;
	
	private boolean notToReverseE;
		
	private String soaSectionId;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}



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

	public boolean isNotToReverseE() {
		return notToReverseE;
	}

	public void setNotToReverseE(boolean notToReverseE) {
		this.notToReverseE = notToReverseE;
	}

	public String getSoaSectionId() {
		return soaSectionId;
	}

	public void setSoaSectionId(String soaSectionId) {
		this.soaSectionId = soaSectionId;
	}

	public String getEntryCode() {
		return entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}
	
	
	
}
