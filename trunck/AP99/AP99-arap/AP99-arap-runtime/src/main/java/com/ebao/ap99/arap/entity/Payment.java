package com.ebao.ap99.arap.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;

/**
 * The persistent class for the T_RI_BCP_PAYMENT database table.
 * 
 */
@Entity
@Table(name = "T_RI_BCP_PAYMENT")
@NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p")
@NamedQueries({
	@NamedQuery(name = "Payment.findByPaymentNo", query = " FROM Payment c WHERE c.paymentNo = :paymentNo")
})
public class Payment extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "PAYMENT_ID")
	private Long paymentId;

	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@Column(name = "BANK_CHARGE")
	private BigDecimal bankCharge;
	
	@Column(name = "CURRENCY_CODE")
	private String currencyCode;

	@Column(name = "FROM_BANK_ACCOUNT_NO")
	private String fromBankAccountNo;

	@Column(name = "PAY_MODE")
	private Integer payMode;

	@Temporal(TemporalType.DATE)
	@Column(name = "PAYMENT_DATE")
	private Date paymentDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "VALUE_DATE")
	private Date valueDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "TRANS_DATE")
	private Date transDate;

	@Column(name = "PAYMENT_NO")
	private String paymentNo;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "EXCHANGE_RATE_REF")
	private Integer exchangeRateRef;

	@Column(name = "CHEQUE_HOLDER_NAME")
	private String chequeHolderName;

	@Column(name = "CHEQUE_NO")
	private String chequeNo;

	@Temporal(TemporalType.DATE)
	@Column(name = "CHEQUE_DATE")
	private Date chequeDate;

	public Payment() {
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getFromBankAccountNo() {
		return this.fromBankAccountNo;
	}

	public void setFromBankAccountNo(String fromBankAccountNo) {
		this.fromBankAccountNo = fromBankAccountNo;
	}

	public Date getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentNo() {
		return this.paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Integer getPayMode() {
		return payMode;
	}

	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getPaymentId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setPaymentId(key);
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public BigDecimal getBankCharge() {
		return bankCharge;
	}

	public void setBankCharge(BigDecimal bankCharge) {
		this.bankCharge = bankCharge;
	}

	public Integer getExchangeRateRef() {
		return exchangeRateRef;
	}

	public void setExchangeRateRef(Integer exchangeRateRef) {
		this.exchangeRateRef = exchangeRateRef;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public String getChequeHolderName() {
		return chequeHolderName;
	}

	public void setChequeHolderName(String chequeHolderName) {
		this.chequeHolderName = chequeHolderName;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}
	
}