package com.ebao.ap99.arap.vo;

public class OffsetConditionDTO {
	private String brokerCode;
	private String partnerCode;
	private String contractId;
	private Long statementId;
	private String claimNumber;
	public String getBrokerCode() {
		return brokerCode;
	}
	public void setBrokerCode(String brokerCode) {
		this.brokerCode = brokerCode;
	}
	public String getPartnerCode() {
		return partnerCode;
	}
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public Long getStatementId() {
		return statementId;
	}
	public void setStatementId(Long statementId) {
		this.statementId = statementId;
	}
	public String getClaimNumber() {
		return claimNumber;
	}
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}
	
	
}
