package com.ebao.ap99.accounting.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SoaNonPropVO {

	private String broke;

	private String cedent;

	private int cedentQuarter;

	private int cedentYear;

	private String contractCode;

	private Date dueDate;

	private Date receivedDate;

	private String soaText;

	private Long uwYear;

	private List<SoaCurrencyNonPropVO> currencies = new ArrayList<SoaCurrencyNonPropVO>();

	private Long taskCreator;
		
	private String contractNature;
	
	private String contractCategory;
	
	private Long contCompId;

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
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

	public int getCedentQuarter() {
		return cedentQuarter;
	}

	public void setCedentQuarter(int cedentQuarter) {
		this.cedentQuarter = cedentQuarter;
	}

	public int getCedentYear() {
		return cedentYear;
	}

	public void setCedentYear(int cedentYear) {
		this.cedentYear = cedentYear;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getSoaText() {
		return soaText;
	}

	public void setSoaText(String soaText) {
		this.soaText = soaText;
	}

	public Long getUwYear() {
		return uwYear;
	}

	public void setUwYear(Long uwYear) {
		this.uwYear = uwYear;
	}

	public List<SoaCurrencyNonPropVO> getCurrencies() {
		return this.currencies;
	}

	public void setCurrencies(List<SoaCurrencyNonPropVO> currencies) {
		this.currencies = currencies;
	}

	public String getContractNature() {
		return contractNature;
	}

	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}

	public Long getTaskCreator() {
		return taskCreator;
	}

	public void setTaskCreator(Long taskCreator) {
		this.taskCreator = taskCreator;
	}

	public String getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(String contractCategory) {
		this.contractCategory = contractCategory;
	}
	
	

}
