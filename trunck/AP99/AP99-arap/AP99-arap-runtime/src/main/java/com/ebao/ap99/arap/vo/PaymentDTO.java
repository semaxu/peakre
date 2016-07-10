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
public class PaymentDTO {
	private String paymentMethod;
	
	private String paymentCurrency;
	
	private String paidAccount;
	
	private String bankCharge;
	
	private Date paymentDate;
	
	private Date valueDate;
	
	private Integer exchangeRateReference;
		
	private String totalAmount;
	
	private List<PayeeDTO> payee;

	private List<CreditsAndDebit> creditsAndDebit;

	private List<Balance> balances;
	
	private String remark;
	
	private PaymentSearchDTO condition;
	
	private String chequeNumber;
	
	private Date chequeDate;
	
	private String chequeHolderName;
	
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
	 * @return the paymentCurrency
	 */
	public String getPaymentCurrency() {
		return paymentCurrency;
	}

	/**
	 * @param paymentCurrency the paymentCurrency to set
	 */
	public void setPaymentCurrency(String paymentCurrency) {
		this.paymentCurrency = paymentCurrency;
	}

	/**
	 * @return the paidAccount
	 */
	public String getPaidAccount() {
		return paidAccount;
	}

	/**
	 * @param paidAccount the paidAccount to set
	 */
	public void setPaidAccount(String paidAccount) {
		this.paidAccount = paidAccount;
	}

	/**
	 * @return the totalAmount
	 */
	public String getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the payee
	 */
	public List<PayeeDTO> getPayee() {
		return payee;
	}

	/**
	 * @param payee the payee to set
	 */
	public void setPayee(List<PayeeDTO> payee) {
		this.payee = payee;
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
	 * @return the paymentDate
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
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

	public PaymentSearchDTO getCondition() {
		return condition;
	}

	public void setCondition(PaymentSearchDTO condition) {
		this.condition = condition;
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
