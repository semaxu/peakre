package com.ebao.ap99.accounting.UI.model;

import java.util.ArrayList;
import java.util.List;

public class SoaVO {
	

	private String accountLevel;
	
	private String bizType;

	private String broke;

	private String cedent;

	private String cedentQuarter;

	private String cedentYear;

	private String contractCode;

	private String dueDate;

	private String receivedDate;

	private String remarks;

	private String soaText;

	private String uwYear;

	private List<SoaCurrencyVO> currencies = new ArrayList();
		
	private String financialYear;
	
	private String soaIdRead;
	
	






	public String getAccountLevel() {
		return accountLevel;
	}








	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}








	public String getBizType() {
		return bizType;
	}








	public void setBizType(String bizType) {
		this.bizType = bizType;
	}








	public String getBroke() {
		return broke;
	}








	public void setBroke(String broke) {
		this.broke = broke;
	}








	public String getCedent() {
		return cedent;
	}








	public void setCedent(String cedent) {
		this.cedent = cedent;
	}








	public String getCedentQuarter() {
		return cedentQuarter;
	}








	public void setCedentQuarter(String cedentQuarter) {
		this.cedentQuarter = cedentQuarter;
	}








	public String getCedentYear() {
		return cedentYear;
	}








	public void setCedentYear(String cedentYear) {
		this.cedentYear = cedentYear;
	}








	public String getContractCode() {
		return contractCode;
	}








	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}



	public String getDueDate() {
		return dueDate;
	}








	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}








	public String getReceivedDate() {
		return receivedDate;
	}








	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}








	public String getRemarks() {
		return remarks;
	}








	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}








	public String getSoaText() {
		return soaText;
	}








	public void setSoaText(String soaText) {
		this.soaText = soaText;
	}








	public String getUwYear() {
		return uwYear;
	}








	public void setUwYear(String uwYear) {
		this.uwYear = uwYear;
	}














	public String getFinancialYear() {
		return financialYear;
	}








	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}








	public String getSoaIdRead() {
		return soaIdRead;
	}








	public void setSoaIdRead(String soaIdRead) {
		this.soaIdRead = soaIdRead;
	}








	public SoaVO() {
		// TODO Auto-generated constructor stub
	}








	public List<SoaCurrencyVO> getCurrencies() {
		return this.currencies;
	}








	public void setCurrencies(List<SoaCurrencyVO> currencies) {
		this.currencies = currencies;
	}

}
