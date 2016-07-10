package com.ebao.ap99.arap.vo;

import java.util.Date;
import java.util.List;

import com.ebao.ap99.parent.Page;

public class TransactionReversalDTO extends Page<SettlementTransaction> {
	private static final long serialVersionUID = 3233082681305298624L;
	private Boolean selected = false;
	private Integer transType;
	private String payerPayee;
	private Date settleDateFrom;
	private Date settleDateTo;
	private String settleNo;
	private Date transDateFrom;
	private Date transDateTo;
	private String broker;
	private String partnerCode;
	private String contractId;
	private String statementId;
	private String claimNo;
	private List<SettlementTransaction> transactionList;
	
	public List<SettlementTransaction> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<SettlementTransaction> transactionList) {
		this.transactionList = transactionList;
	}

	public Integer getTransType() {
		return transType;
	}

	public void setTransType(Integer transType) {
		this.transType = transType;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
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

	public String getStatementId() {
		return statementId;
	}

	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}
	
	public String getPayerPayee() {
		return payerPayee;
	}

	public void setPayerPayee(String payerPayee) {
		this.payerPayee = payerPayee;
	}

	public Date getSettleDateFrom() {
		return settleDateFrom;
	}

	public void setSettleDateFrom(Date settleDateFrom) {
		this.settleDateFrom = settleDateFrom;
	}

	public Date getSettleDateTo() {
		return settleDateTo;
	}

	public void setSettleDateTo(Date settleDateTo) {
		this.settleDateTo = settleDateTo;
	}

	public String getSettleNo() {
		return settleNo;
	}

	public void setSettleNo(String settleNo) {
		this.settleNo = settleNo;
	}

	public Date getTransDateFrom() {
		return transDateFrom;
	}

	public void setTransDateFrom(Date transDateFrom) {
		this.transDateFrom = transDateFrom;
	}

	public Date getTransDateTo() {
		return transDateTo;
	}

	public void setTransDateTo(Date transDateTo) {
		this.transDateTo = transDateTo;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
}
