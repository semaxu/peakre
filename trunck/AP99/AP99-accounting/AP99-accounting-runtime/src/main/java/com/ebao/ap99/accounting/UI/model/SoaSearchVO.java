package com.ebao.ap99.accounting.UI.model;

import java.util.Date;

public class SoaSearchVO {
	
	private String soaId;
	
	private String contractCode;
	
    private Long contractId;
    
    private String contractName;
	
	private String cedent;
		
	private Date receivedDate;
	
	private String bizType;
	
	private String soaStatus;
	
	private String broke;
	
	private Integer cedentQuarter;

	private Integer cedentYear;
	
	private boolean mainSoaFlag;
	
	private Integer uwYear;
	
	private Integer financialQuarter;

	private Integer financialYear;
	
	private String taskReleaser;
	
	private String taskCreator;
	
	private Integer cedentPeriod;
	
	private int pageIndex;

    private int countPerPage;

	public String getSoaId() {
		return soaId;
	}

	public void setSoaId(String soaId) {
		this.soaId = soaId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getCedent() {
		return cedent;
	}

	public void setCedent(String cedent) {
		this.cedent = cedent;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getSoaStatus() {
		return soaStatus;
	}

	public void setSoaStatus(String soaStatus) {
		this.soaStatus = soaStatus;
	}

	public String getBroke() {
		return broke;
	}

	public void setBroke(String broke) {
		this.broke = broke;
	}



	public boolean isMainSoaFlag() {
		return mainSoaFlag;
	}

	public void setMainSoaFlag(boolean mainSoaFlag) {
		this.mainSoaFlag = mainSoaFlag;
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

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getCountPerPage() {
		return countPerPage;
	}

	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
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

	public Integer getCedentPeriod() {
		return cedentPeriod;
	}

	public void setCedentPeriod(Integer cedentPeriod) {
		this.cedentPeriod = cedentPeriod;
	}
	
	
	
	


}
