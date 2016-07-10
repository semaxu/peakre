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
public class PaymentViewResult {
    private Long paymentId;
	
	private String paymentCurrency;

	private BigDecimal totalAmount;
	
	private BigDecimal bankCharge;

	private String paidAccount;
	
	private Date paymentDate;
	
	private Date valueDate;

	private String remark;

	private Integer paymentMethod;
	
	private List<CreditsAndDebit> creditsAndDebit;
	
	private List<Balance> balances;
	
	private List<PayeeDTO> payee;
	
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

	/**
	 * @return the paymentId
	 */
	public Long getPaymentId() {
		return paymentId;
	}

	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
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
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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
