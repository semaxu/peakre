package com.ebao.ap99.accounting.model;

import java.math.BigDecimal;

public class SoaExportExcelModel {
	
	private String statementNo;
	private String contractNo;
	private String accountText;
	private Integer UWY;
	private String contractSection;
	private String mainSubCOB;
	private String shareType;
	private String cedentCountry;//cendent country
	private Integer cedentYear;
	private String cedentQuarter;
	private String entryCode;
	private String entryItem;
	private String currency;
    private BigDecimal cashBalance = BigDecimal.ZERO;
	private BigDecimal techResult = BigDecimal.ZERO;
	public String getStatementNo() {
		return statementNo;
	}
	public void setStatementNo(String statementNo) {
		this.statementNo = statementNo;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getAccountText() {
		return accountText;
	}
	public void setAccountText(String accountText) {
		this.accountText = accountText;
	}
	public Integer getUWY() {
		return UWY;
	}
	public void setUWY(Integer uWY) {
		UWY = uWY;
	}
	public String getContractSection() {
		return contractSection;
	}
	public void setContractSection(String contractSection) {
		this.contractSection = contractSection;
	}
	public String getMainSubCOB() {
		return mainSubCOB;
	}
	public void setMainSubCOB(String mainSubCOB) {
		this.mainSubCOB = mainSubCOB;
	}
	public String getShareType() {
		return shareType;
	}
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	public String getCedentCountry() {
		return cedentCountry;
	}
	public void setCedentCountry(String cedentCountry) {
		this.cedentCountry = cedentCountry;
	}
	public Integer getCedentYear() {
		return cedentYear;
	}
	public void setCedentYear(Integer cedentYear) {
		this.cedentYear = cedentYear;
	}

	public String getCedentQuarter() {
		return cedentQuarter;
	}
	public void setCedentQuarter(String cedentQuarter) {
		this.cedentQuarter = cedentQuarter;
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
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getCashBalance() {
		return cashBalance;
	}
	public void setCashBalance(BigDecimal cashBalance) {
		this.cashBalance = cashBalance;
	}
	public BigDecimal getTechResult() {
		return techResult;
	}
	public void setTechResult(BigDecimal techResult) {
		this.techResult = techResult;
	}




	
	

}
