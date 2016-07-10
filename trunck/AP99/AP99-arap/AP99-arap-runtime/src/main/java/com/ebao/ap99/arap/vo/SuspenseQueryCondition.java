package com.ebao.ap99.arap.vo;

public class SuspenseQueryCondition {
	private String partnerCode;
	private String brokerCode;
	private String contractIdArray; // like "a,b,c"
	private String statementId;
	private String claimNo;
	private String settleCurrencyCode;
	
	public String getPartnerCode() {
		return partnerCode;
	}
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	
	public String getSettleCurrencyCode() {
		return settleCurrencyCode;
	}
	public void setSettleCurrencyCode(String settleCurrencyCode) {
		this.settleCurrencyCode = settleCurrencyCode;
	}
	public String getBrokerCode() {
		return brokerCode;
	}
	public void setBrokerCode(String brokerCode) {
		this.brokerCode = brokerCode;
	}
	public String getContractIdArray() {
		return contractIdArray;
	}
	public void setContractIdArray(String contractIdArray) {
		this.contractIdArray = contractIdArray;
	}
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getStatementId() {
		return statementId;
	}
	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}
}
