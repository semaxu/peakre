package com.ebao.ap99.arap.vo;

import java.util.Date;

public class ReceiptInformation {
	public String payer;
	
	public Date collectionDate;
	
	private Date valueDate;
	
	public String paymentMethod;
	
	public String bank;
	
	public String bankCharge;
	
	public String chequeNumber;
	
	public Date chequeDate;
	
	public String chequeHolderName;
	
	public String collectionCurrency;
	
	public String collectToBankAccount;
	
	public String collectionAmount;

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankCharge() {
		return bankCharge;
	}

	public void setBankCharge(String bankCharge) {
		this.bankCharge = bankCharge;
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

	public String getCollectionCurrency() {
		return collectionCurrency;
	}

	public void setCollectionCurrency(String collectionCurrency) {
		this.collectionCurrency = collectionCurrency;
	}

	public String getCollectToBankAccount() {
		return collectToBankAccount;
	}

	public void setCollectToBankAccount(String collectToBankAccount) {
		this.collectToBankAccount = collectToBankAccount;
	}

	public String getCollectionAmount() {
		return collectionAmount;
	}

	public void setCollectionAmount(String collectionAmount) {
		this.collectionAmount = collectionAmount;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	
	
}
