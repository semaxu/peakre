package com.ebao.ap99.arap.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OffsetModel {
	private Long offsetId;
	private BigDecimal offsetAmount;// absolute amount of credit / debit
	private String remark;
	private Date offsetDate;
	private List<CreditsAndDebit> feeList;
	private List<Balance> suspenseList;
	private String currencyCode;
	private String transNumber;
	private Integer transStatus;
	private Date operationDate;
	private Integer operatorId;
	
	private Date registrationDate;
	
	public String getTransNumber() {
		return transNumber;
	}
	public void setTransNumber(String transNumber) {
		this.transNumber = transNumber;
	}
	public Integer getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(Integer transStatus) {
		this.transStatus = transStatus;
	}
	public Date getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public Long getOffsetId() {
		return offsetId;
	}
	public void setOffsetId(Long offsetId) {
		this.offsetId = offsetId;
	}
	public BigDecimal getOffsetAmount() {
		return offsetAmount;
	}
	public void setOffsetAmount(BigDecimal offsetAmount) {
		this.offsetAmount = offsetAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getOffsetDate() {
		return offsetDate;
	}
	public void setOffsetDate(Date offsetDate) {
		this.offsetDate = offsetDate;
	}
	public List<CreditsAndDebit> getFeeList() {
		return feeList;
	}
	public void setFeeList(List<CreditsAndDebit> feeList) {
		this.feeList = feeList;
	}
	public List<Balance> getSuspenseList() {
		return suspenseList;
	}
	public void setSuspenseList(List<Balance> suspenseList) {
		this.suspenseList = suspenseList;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
}
