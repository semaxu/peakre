package com.ebao.ap99.accounting.UI.model;


public class SoaLayerVO {
	
	private long soaId;
	
	private String contractCode;
	
    private Long contractId;
    
    private String contractName;
	
	private Integer cedentYear;

	private Integer cedentQuarter;
		
	private Integer uwYear;
	
	private Integer statementType;
	
	private Integer cedentPeriod;

	public long getSoaId() {
		return soaId;
	}

	public void setSoaId(long soaId) {
		this.soaId = soaId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Integer getCedentYear() {
		return cedentYear;
	}

	public void setCedentYear(Integer cedentYear) {
		this.cedentYear = cedentYear;
	}

	public Integer getCedentQuarter() {
		return cedentQuarter;
	}

	public void setCedentQuarter(Integer cedentQuarter) {
		this.cedentQuarter = cedentQuarter;
	}

	public Integer getUwYear() {
		return uwYear;
	}

	public void setUwYear(Integer uwYear) {
		this.uwYear = uwYear;
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

	public Integer getStatementType() {
		return statementType;
	}

	public void setStatementType(Integer statementType) {
		this.statementType = statementType;
	}

	public Integer getCedentPeriod() {
		return cedentPeriod;
	}

	public void setCedentPeriod(Integer cedentPeriod) {
		this.cedentPeriod = cedentPeriod;
	}


	
	

}
