package com.ebao.ap99.accounting.UI.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Soa4UpdateVO {
	
	private String accountLevel;
	
	private String bizType;

	private String broke;

	private String cedent;

	private String cedentQuarter;

	private String cedentYear;

	private String contractCode;
	
    private Long contractId;
    
    private String contractName;

	private String dueDate;

	private String receivedDate;

	private String remarks;

	private String soaText;

	private String uwYear;

	private List<SoaCurrency4UpdateVO> currencies = new ArrayList<SoaCurrency4UpdateVO>();
		
	private String financialYear;
	
	private String soaIdRead;
	
	private String soaId;
	
	private String isCutOffPeriod;
	
	private BigDecimal accountType;

	private BigDecimal entryTask;

	private BigDecimal mainSoaFlag;

	private String reviewedBy;
	
	private String reviewedFlag;

	private BigDecimal soaStatus;

	private BigDecimal withdrawIgnoringCutoffDate;
	
	private BigDecimal financialQuarter;
	
	private BigDecimal taskReleaser;
	
	private BigDecimal taskCreator;
	
	private boolean reversalFlag;
	
	private String contractNature;
	
	private String contractCategory;
	
	private String settlementDays;
	
    private String statementType;
    
    private Integer cedentPeriod;
	

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

	public String getSoaId() {
		return soaId;
	}

	public void setSoaId(String soaId) {
		this.soaId = soaId;
	}

	public BigDecimal getAccountType() {
		return accountType;
	}

	public void setAccountType(BigDecimal accountType) {
		this.accountType = accountType;
	}

	public BigDecimal getEntryTask() {
		return entryTask;
	}

	public void setEntryTask(BigDecimal entryTask) {
		this.entryTask = entryTask;
	}

	public BigDecimal getMainSoaFlag() {
		return mainSoaFlag;
	}

	public void setMainSoaFlag(BigDecimal mainSoaFlag) {
		this.mainSoaFlag = mainSoaFlag;
	}

	public String getReviewedBy() {
		return reviewedBy;
	}

	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}



	public BigDecimal getSoaStatus() {
		return soaStatus;
	}

	public void setSoaStatus(BigDecimal soaStatus) {
		this.soaStatus = soaStatus;
	}

	public BigDecimal getWithdrawIgnoringCutoffDate() {
		return withdrawIgnoringCutoffDate;
	}

	public void setWithdrawIgnoringCutoffDate(BigDecimal withdrawIgnoringCutoffDate) {
		this.withdrawIgnoringCutoffDate = withdrawIgnoringCutoffDate;
	}

	public BigDecimal getFinancialQuarter() {
		return financialQuarter;
	}

	public void setFinancialQuarter(BigDecimal financialQuarter) {
		this.financialQuarter = financialQuarter;
	}

	public BigDecimal getTaskReleaser() {
		return taskReleaser;
	}

	public void setTaskReleaser(BigDecimal taskReleaser) {
		this.taskReleaser = taskReleaser;
	}

	public BigDecimal getTaskCreator() {
		return taskCreator;
	}

	public void setTaskCreator(BigDecimal taskCreator) {
		this.taskCreator = taskCreator;
	}

	public List<SoaCurrency4UpdateVO> getCurrencies() {
		return this.currencies;
	}

	public void setCurrencies(List<SoaCurrency4UpdateVO> currencies) {
		this.currencies = currencies;
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

	public boolean isReversalFlag() {
		return reversalFlag;
	}

	public void setReversalFlag(boolean reversalFlag) {
		this.reversalFlag = reversalFlag;
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
