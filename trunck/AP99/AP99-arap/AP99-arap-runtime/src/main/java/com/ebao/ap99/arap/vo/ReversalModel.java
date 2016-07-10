package com.ebao.ap99.arap.vo;

import java.util.List;

public class ReversalModel {
	private List<SettlementTransaction> transactionList;
	private Integer reversalReason;
	private String requestedBy;
	private String remark;
	private TransactionReversalDTO condition; 
	
	public List<SettlementTransaction> getTransactionList() {
		return transactionList;
	}
	public void setTransactionList(List<SettlementTransaction> transactionList) {
		this.transactionList = transactionList;
	}
	public Integer getReversalReason() {
		return reversalReason;
	}
	public void setReversalReason(Integer reversalReason) {
		this.reversalReason = reversalReason;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public TransactionReversalDTO getCondition() {
		return condition;
	}
	public void setCondition(TransactionReversalDTO condition) {
		this.condition = condition;
	}
}
