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
import javax.persistence.Transient;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;

/**
 * The persistent class for the T_RI_BCP_COLLECTION database table.
 * 
 */
@Entity
@Table(name = "T_RI_BCP_COLLECTION")
@NamedQuery(name = "Collection.findAll", query = "SELECT c FROM Collection c")
@NamedQueries({
	@NamedQuery(name = "Collection.findByReceiptNo", query = " FROM Collection c WHERE c.receiptNo = :receiptNo")
})
public class Collection extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "COLLECTION_ID")
	private Long collectionId;

	@Column(name = "BANK_ACCOUNT_NO")
	private String bankAccountNo;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "BANK_CODE")
	private String bankCode;

	@Temporal(TemporalType.DATE)
	@Column(name = "CHEQUE_DATE")
	private Date chequeDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "TRANS_DATE")
	private Date transDate;

	@Column(name = "CHEQUE_HOLDER_NAME")
	private String chequeHolderName;

	@Column(name = "CHEQUE_NO")
	private String chequeNo;
	
	@Column(name = "BANK_CHARGE")
	private BigDecimal bankCharge;

	@Temporal(TemporalType.DATE)
	@Column(name = "COLLECTION_DATE")
	private Date collectionDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "VALUE_DATE")
	private Date valueDate;

	@Column(name = "CURRENCY_CODE")
	private String currencyCode;

	@Column(name = "PAY_MODE")
	private Integer payMode;

	@Column(name = "PAYER_CODE")
	private String payerCode;
	
	@Transient
	private String payerName;

	@Column(name = "RECEIPT_NO")
	private String receiptNo;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "EXCHANGE_RATE_REF")
	private Integer exchangeRateRef;

	public Collection() {
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBankAccountNo() {
		return this.bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Date getChequeDate() {
		return this.chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public String getChequeHolderName() {
		return this.chequeHolderName;
	}

	public void setChequeHolderName(String chequeHolderName) {
		this.chequeHolderName = chequeHolderName;
	}
	
	public String getPayerCode() {
		return payerCode;
	}

	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}

	public String getChequeNo() {
		return this.chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public Date getCollectionDate() {
		return this.collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public Integer getPayMode() {
		return payMode;
	}

	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}

	public String getReceiptNo() {
		return this.receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getCollectionId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setCollectionId(key);
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

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
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
	
}