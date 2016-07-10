/**
 * 
 */
package com.ebao.ap99.arap.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author meiliang.zou
 *
 */
public class CollectionViewResult {
	private String payer;
	private String payerName;
	
	private Date collectionDate;
	
	private Date valueDate;
	
	private Integer exchangeRateReference;
	
	private String paymentMethod;
	
	private String bank;
	private String bankName;
	
	private BigDecimal bankCharge;
	
	private String chequeNumber;
	
	private Date chequeDate;
	
	private String chequeHolderName;
	
	private String collectionCurrency;
	
	private String collectToBankAccount;
	
	private BigDecimal collectionAmount;
	
	private BigDecimal netAmount;
	
	private List<CreditsAndDebit> creditsAndDebit;
	
	private List<Balance> balances;
	
	private String remark;
	
	private String transNumber;
	private Integer transStatus;
	private Date operationDate;
	private Integer operatorId;

	/**
	 * @return the payer
	 */
	public String getPayer() {
		return payer;
	}

	/**
	 * @param payer the payer to set
	 */
	public void setPayer(String payer) {
		this.payer = payer;
	}

	/**
	 * @return the collectionDate
	 */
	public Date getCollectionDate() {
		return collectionDate;
	}

	/**
	 * @param collectionDate the collectionDate to set
	 */
	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the bank
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * @param bank the bank to set
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * @return the chequeNumber
	 */
	public String getChequeNumber() {
		return chequeNumber;
	}

	/**
	 * @param chequeNumber the chequeNumber to set
	 */
	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	/**
	 * @return the chequeDate
	 */
	public Date getChequeDate() {
		return chequeDate;
	}

	/**
	 * @param chequeDate the chequeDate to set
	 */
	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	/**
	 * @return the chequeHolderName
	 */
	public String getChequeHolderName() {
		return chequeHolderName;
	}

	/**
	 * @param chequeHolderName the chequeHolderName to set
	 */
	public void setChequeHolderName(String chequeHolderName) {
		this.chequeHolderName = chequeHolderName;
	}

	/**
	 * @return the collectionCurrency
	 */
	public String getCollectionCurrency() {
		return collectionCurrency;
	}

	/**
	 * @param collectionCurrency the collectionCurrency to set
	 */
	public void setCollectionCurrency(String collectionCurrency) {
		this.collectionCurrency = collectionCurrency;
	}

	/**
	 * @return the creditsAndDebit
	 */
	public List<CreditsAndDebit> getCreditsAndDebit() {
		return creditsAndDebit;
	}

	/**
	 * @param creditsAndDebit the creditsAndDebit to set
	 */
	public void setCreditsAndDebit(List<CreditsAndDebit> creditsAndDebit) {
		this.creditsAndDebit = creditsAndDebit;
	}

	/**
	 * @return the balances
	 */
	public List<Balance> getBalances() {
		return balances;
	}

	/**
	 * @param balances the balances to set
	 */
	public void setBalances(List<Balance> balances) {
		this.balances = balances;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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

	public BigDecimal getBankCharge() {
		return bankCharge;
	}

	public void setBankCharge(BigDecimal bankCharge) {
		this.bankCharge = bankCharge;
	}

	public String getCollectToBankAccount() {
		return collectToBankAccount;
	}

	public void setCollectToBankAccount(String collectToBankAccount) {
		this.collectToBankAccount = collectToBankAccount;
	}

	public BigDecimal getCollectionAmount() {
		return collectionAmount;
	}

	public void setCollectionAmount(BigDecimal collectionAmount) {
		this.collectionAmount = collectionAmount;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
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

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
}
