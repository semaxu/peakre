/**
 * 
 */
package com.ebao.ap99.arap.vo;

import java.util.Date;
import java.util.List;

/**
 * @author meiliang.zou
 *
 */
public class CollectionDTO {
	
	private String payer;
	private String payerName;
	
	private Date collectionDate;
	private Date valueDate;
	private Integer exchangeRateReference = 1;
	
	private String paymentMethod;
	
	private String bank;
	private String bankName;
	
	private String bankCharge;
	
	private String chequeNumber;
	
	private Date chequeDate;
	
	private String chequeHolderName;
	
	private String collectionCurrency;
	
	private String collectToBankAccount;
	
	private String collectionAmount;
	
	private String netAmount;
	
	private List<CreditsAndDebit> creditsAndDebit;
	
	private List<Balance> balances;
	
	private String remark;
	
	private ReceiptInformation receiptInformation;
	
	private CollectionSearchDTO condition;
	
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
	 * @return the bankCharge
	 */
	public String getBankCharge() {
		return bankCharge;
	}

	/**
	 * @param bankCharge the bankCharge to set
	 */
	public void setBankCharge(String bankCharge) {
		this.bankCharge = bankCharge;
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
	 * @return the collectToBankAccount
	 */
	public String getCollectToBankAccount() {
		return collectToBankAccount;
	}

	/**
	 * @param collectToBankAccount the collectToBankAccount to set
	 */
	public void setCollectToBankAccount(String collectToBankAccount) {
		this.collectToBankAccount = collectToBankAccount;
	}

	/**
	 * @return the collectionAmount
	 */
	public String getCollectionAmount() {
		return collectionAmount;
	}

	/**
	 * @param collectionAmount the collectionAmount to set
	 */
	public void setCollectionAmount(String collectionAmount) {
		this.collectionAmount = collectionAmount;
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
	 * @return the condition
	 */
	public CollectionSearchDTO getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(CollectionSearchDTO condition) {
		this.condition = condition;
	}
	
	/*
	 * @return the List<ReceiptInformation>
	 * */
	public ReceiptInformation getReceiptInformation() {
		return receiptInformation;
	}

	public void setReceiptInformation(ReceiptInformation receiptInformation) {
		this.receiptInformation = receiptInformation;
	}

	/**
	 * @return the netAmount
	 */
	public String getNetAmount() {
		return netAmount;
	}

	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
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

	/**
	 * @return the valueDate
	 */
	public Date getValueDate() {
		return valueDate;
	}

	/**
	 * @param valueDate the valueDate to set
	 */
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	
	
}
