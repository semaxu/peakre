package com.ebao.ap99.arap.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CollectionModel {
	private Long collectionId;
	
	private String payerCode;
	private String payerName;
	
	private Date collectionDate;
	
	private Date valueDate;
	
	private Integer exchangeRateReference;

	private String collectToAccount;

	private Integer paymentMethod;

	private String bankCode;
	private String bankName;

	private BigDecimal bankCharge;
	
	private BigDecimal netAmount;

	private String chequeNo;

	private Date chequeDate;

	private String chequeHolderName;

	private String collectionCurrency;

	private BigDecimal collectionAmount;

	private String collectionBankAccountNo;
	
	private String remark;
	
	private String transNumber;
	private Integer transStatus;
	private Date operationDate;
	private Integer operatorId;
	
	private List<CreditsAndDebit> feeList;

	private List<Balance> balanceList;

	public String getPayerCode() {
		return payerCode;
	}

	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getCollectToAccount() {
		return collectToAccount;
	}

	public void setCollectToAccount(String collectToAccount) {
		this.collectToAccount = collectToAccount;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public BigDecimal getBankCharge() {
		return bankCharge;
	}

	public void setBankCharge(BigDecimal bankCharge) {
		this.bankCharge = bankCharge;
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

	public String getCollectionCurrency() {
		return collectionCurrency;
	}

	public void setCollectionCurrency(String collectionCurrency) {
		this.collectionCurrency = collectionCurrency;
	}
	
	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public BigDecimal getCollectionAmount() {
		return collectionAmount;
	}

	public void setCollectionAmount(BigDecimal collectionAmount) {
		this.collectionAmount = collectionAmount;
	}

	public String getCollectionBankAccountNo() {
		return collectionBankAccountNo;
	}

	public void setCollectionBankAccountNo(String collectionBankAccountNo) {
		this.collectionBankAccountNo = collectionBankAccountNo;
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

	public Long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}

	public List<Balance> getBalanceList() {
		return balanceList;
	}

	public void setBalanceList(List<Balance> balanceList) {
		this.balanceList = balanceList;
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

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

}
