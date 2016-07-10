package com.ebao.ap99.arap.vo;

import com.ebao.ap99.parent.Page;

public class TransactionQueryCondition {

	/**
	 * @see FinanceTransactionTypoe
	 */
	private Integer transType;
	private String partnerCode;
	private String transNo;
	private String settleDateFrom;
	private String settleDateTo;
	private String transDateFrom;
	private String transDateTo;
	private String brokerCode;
	private String financePartnerCode; // payer or payee
	private String contractIdArray; // like "a,b,c"
	private String statementId;
	private String claimNo;
	private Integer sortOrder;
	private Boolean selected;
	private boolean noReversal = false;
	private String feeIdArray;// format: xxx,xxx,xxx
	
	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Integer getTransType() {
		return transType;
	}

	public void setTransType(Integer transType) {
		this.transType = transType;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getSettleDateFrom() {
		return settleDateFrom;
	}

	public void setSettleDateFrom(String settleDateFrom) {
		this.settleDateFrom = settleDateFrom;
	}

	public String getSettleDateTo() {
		return settleDateTo;
	}

	public void setSettleDateTo(String settleDateTo) {
		this.settleDateTo = settleDateTo;
	}

	public String getTransDateFrom() {
		return transDateFrom;
	}

	public void setTransDateFrom(String transDateFrom) {
		this.transDateFrom = transDateFrom;
	}

	public String getTransDateTo() {
		return transDateTo;
	}

	public void setTransDateTo(String transDateTo) {
		this.transDateTo = transDateTo;
	}

	public String getBrokerCode() {
		return brokerCode;
	}

	public void setBrokerCode(String brokerCode) {
		this.brokerCode = brokerCode;
	}

	public String getFinancePartnerCode() {
		return financePartnerCode;
	}

	public void setFinancePartnerCode(String financePartnerCode) {
		this.financePartnerCode = financePartnerCode;
	}

	public String getContractIdArray() {
		return contractIdArray;
	}

	public void setContractIdArray(String contractIdArray) {
		this.contractIdArray = contractIdArray;
	}

	public String getStatementId() {
		return statementId;
	}

	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isNoReversal() {
		return noReversal;
	}

	public void setNoReversal(boolean noReversal) {
		this.noReversal = noReversal;
	}

	public String getFeeIdArray() {
		return feeIdArray;
	}

	public void setFeeIdArray(String feeIdArray) {
		this.feeIdArray = feeIdArray;
	}
}
