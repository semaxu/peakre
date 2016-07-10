package com.ebao.ap99.accounting.model;

import java.math.BigDecimal;

public class RevaluationDetailModel {
	
	private String contractID;
	
	private String entryCode;

	private String entryItem;
	
	private String currencyOC;

	private BigDecimal amountOC;
	
	private BigDecimal originalRate;
	
	private BigDecimal amountUSD;
	
	private BigDecimal currentRate;
	
	private BigDecimal currentAmountUSD;
	
	private BigDecimal revaluationAmountUSD;
	
	private String filePath;

	public String getContractID() {
		return contractID;
	}

	public void setContractID(String contractID) {
		this.contractID = contractID;
	}

	public String getEntryCode() {
		return entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	public String getEntryItem() {
		return entryItem;
	}

	public void setEntryItem(String entryItem) {
		this.entryItem = entryItem;
	}

	public String getCurrencyOC() {
		return currencyOC;
	}

	public void setCurrencyOC(String currencyOC) {
		this.currencyOC = currencyOC;
	}

	public BigDecimal getAmountOC() {
		return amountOC;
	}

	public void setAmountOC(BigDecimal amountOC) {
		this.amountOC = amountOC;
	}

	public BigDecimal getOriginalRate() {
		return originalRate;
	}

	public void setOriginalRate(BigDecimal originalRate) {
		this.originalRate = originalRate;
	}

	public BigDecimal getAmountUSD() {
		return amountUSD;
	}

	public void setAmountUSD(BigDecimal amountUSD) {
		this.amountUSD = amountUSD;
	}

	public BigDecimal getCurrentRate() {
		return currentRate;
	}

	public void setCurrentRate(BigDecimal currentRate) {
		this.currentRate = currentRate;
	}

	public BigDecimal getCurrentAmountUSD() {
		return currentAmountUSD;
	}

	public void setCurrentAmountUSD(BigDecimal currentAmountUSD) {
		this.currentAmountUSD = currentAmountUSD;
	}

	public BigDecimal getRevaluationAmountUSD() {
		return revaluationAmountUSD;
	}

	public void setRevaluationAmountUSD(BigDecimal revaluationAmountUSD) {
		this.revaluationAmountUSD = revaluationAmountUSD;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	

}
