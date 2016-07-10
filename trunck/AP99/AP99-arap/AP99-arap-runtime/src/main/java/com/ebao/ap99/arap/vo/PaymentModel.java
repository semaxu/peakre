package com.ebao.ap99.arap.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ebao.ap99.arap.entity.Payee;

public class PaymentModel {
	private Long paymentId;
	
	private String paymentCurrency;

	private BigDecimal totalAmount;
	
	private BigDecimal bankCharge;
	
	private BigDecimal netAmount;

	private String paidAccount;
	
	private Date paymentDate;
	
	private Date valueDate;

	private String remark;

	private Integer paymentMethod;

	private List<CreditsAndDebit> feeList;

	private List<Balance> suspenseList;
	
	private List<Payee> payeeList;
	
	private Integer exchangeRateReference;
	
	private String transNumber;
	private Integer transStatus;
	private Date operationDate;
	private Integer operatorId;
	
	private String chequeNumber;
	
	private Date chequeDate;
	
	private String chequeHolderName;
		
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

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentCurrency() {
		return paymentCurrency;
	}

	public void setPaymentCurrency(String paymentCurrency) {
		this.paymentCurrency = paymentCurrency;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaidAccount() {
		return paidAccount;
	}

	public void setPaidAccount(String paidAccount) {
		this.paidAccount = paidAccount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public List<Payee> getPayeeList() {
		return payeeList;
	}

	public void setPayeeList(List<Payee> payeeList) {
		this.payeeList = payeeList;
	}

	/**
	 * @return the paymentMethod
	 */
	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the bankCharge
	 */
	public BigDecimal getBankCharge() {
		return bankCharge;
	}

	/**
	 * @param bankCharge the bankCharge to set
	 */
	public void setBankCharge(BigDecimal bankCharge) {
		this.bankCharge = bankCharge;
	}

	/**
	 * @return the exchangeRateReference
	 */
	public Integer getExchangeRateReference() {
		return exchangeRateReference;
	}

	/**
	 * @param exchangeRateReference the exchangeRateReference to set
	 */
	public void setExchangeRateReference(Integer exchangeRateReference) {
		this.exchangeRateReference = exchangeRateReference;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public String getChequeHolderName() {
		return chequeHolderName;
	}

	public void setChequeHolderName(String chequeHolderName) {
		this.chequeHolderName = chequeHolderName;
	}
	
}
