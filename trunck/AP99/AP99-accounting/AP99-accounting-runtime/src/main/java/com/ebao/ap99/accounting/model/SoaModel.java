package com.ebao.ap99.accounting.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;



@XmlAccessorType(XmlAccessType.FIELD)
public class SoaModel {
	
	@XmlTransient
	private String soaId;
	@XmlTransient
	private String accountLevel;
	@XmlTransient
	private String accountType;
	
	private String bizType;

	private String broke;

	private String cedent;

	private Integer cedentQuarter;

	private Integer cedentYear;

	private String contractCode;
	@XmlTransient
    private Long contractId;
    
    private String contractName;

	private Date dueDate;
	@XmlTransient
	private String entryTask;
	@XmlTransient
	private String mainSoaFlag;

	private Date receivedDate;

	private String remarks;
	@XmlTransient
	private String reviewedBy;
	@XmlTransient
	private String reviewedFlag;
	@XmlTransient
	private String soaStatus;

	private String soaText;

	private Integer uwYear;
	@XmlTransient
	private String withdrawIgnoringCutoffDate;
	@XmlElementWrapper
	@XmlElement(name = "CurrencyInfo")
	private List<SoaCurrencyModel> currencies = new ArrayList<SoaCurrencyModel>();
	@XmlTransient	
	private Integer financialQuarter;
	@XmlTransient
	private Integer financialYear;
	@XmlTransient
	private String taskReleaser;
	@XmlTransient
	private String taskCreator;
	@XmlTransient
	private String isCutOffPeriod;
	@XmlTransient
	private String contractNature;
	@XmlTransient
	private String contractCategory; 
	@XmlTransient
	private String settlementDays; 
	
	private String statementType;
	@XmlTransient
	 private Integer cedentPeriod;

	public String getSoaId() {
		return soaId;
	}

	public void setSoaId(String soaId) {
		this.soaId = soaId;
	}

	public String getAccountLevel() {
		return accountLevel;
	}

	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
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

	public String getEntryTask() {
		return entryTask;
	}

	public void setEntryTask(String entryTask) {
		this.entryTask = entryTask;
	}

	public String getMainSoaFlag() {
		return mainSoaFlag;
	}

	public void setMainSoaFlag(String mainSoaFlag) {
		this.mainSoaFlag = mainSoaFlag;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getReviewedBy() {
		return reviewedBy;
	}

	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	

	public String getSoaStatus() {
		return soaStatus;
	}

	public void setSoaStatus(String soaStatus) {
		this.soaStatus = soaStatus;
	}

	public String getSoaText() {
		return soaText;
	}

	public void setSoaText(String soaText) {
		this.soaText = soaText;
	}

	public String getWithdrawIgnoringCutoffDate() {
		return withdrawIgnoringCutoffDate;
	}

	public void setWithdrawIgnoringCutoffDate(String withdrawIgnoringCutoffDate) {
		this.withdrawIgnoringCutoffDate = withdrawIgnoringCutoffDate;
	}


	public String getTaskReleaser() {
		return taskReleaser;
	}

	public void setTaskReleaser(String taskReleaser) {
		this.taskReleaser = taskReleaser;
	}

	public String getTaskCreator() {
		return taskCreator;
	}

	public void setTaskCreator(String taskCreator) {
		this.taskCreator = taskCreator;
	}

	public List<SoaCurrencyModel> getCurrencies() {
		return this.currencies;
	}

	public void setCurrencies(List<SoaCurrencyModel> currencies) {
		this.currencies = currencies;
	}

	public Integer getCedentQuarter() {
		return cedentQuarter;
	}

	public void setCedentQuarter(Integer cedentQuarter) {
		this.cedentQuarter = cedentQuarter;
	}

	public Integer getCedentYear() {
		return cedentYear;
	}

	public void setCedentYear(Integer cedentYear) {
		this.cedentYear = cedentYear;
	}

	public Integer getUwYear() {
		return uwYear;
	}

	public void setUwYear(Integer uwYear) {
		this.uwYear = uwYear;
	}

	public Integer getFinancialQuarter() {
		return financialQuarter;
	}

	public void setFinancialQuarter(Integer financialQuarter) {
		this.financialQuarter = financialQuarter;
	}

	public Integer getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(Integer financialYear) {
		this.financialYear = financialYear;
	}

	public String getReviewedFlag() {
		return reviewedFlag;
	}

	public void setReviewedFlag(String reviewedFlag) {
		this.reviewedFlag = reviewedFlag;
	}

	public String getIsCutOffPeriod() {
		return isCutOffPeriod;
	}

	public void setIsCutOffPeriod(String isCutOffPeriod) {
		this.isCutOffPeriod = isCutOffPeriod;
	}

	public String getContractNature() {
		return contractNature;
	}

	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(String contractCategory) {
		this.contractCategory = contractCategory;
	}

	public String getSettlementDays() {
		return settlementDays;
	}

	public void setSettlementDays(String settlementDays) {
		this.settlementDays = settlementDays;
	}

	public String getStatementType() {
		return statementType;
	}

	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}

	public Integer getCedentPeriod() {
		return cedentPeriod;
	}

	public void setCedentPeriod(Integer cedentPeriod) {
		this.cedentPeriod = cedentPeriod;
	}

	








	
	

}
