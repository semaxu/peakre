package com.ebao.ap99.arap.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;

/**
 * The persistent class for the T_RI_BCP_SUSPENSE database table.
 * 
 */
@Entity
@Table(name = "T_RI_BCP_SUSPENSE")
@NamedQuery(name = "Suspense.findAll", query = "SELECT s FROM Suspense s")
public class Suspense extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "SUSPENSE_ID")
	private Long suspenseId;

	@Column(name = "BALANCE")
	private BigDecimal balance;

	@Column(name = "CONTRACT_ID")
	private Long contractId;

	@Column(name = "CURRENCY_CODE")
	private String currencyCode;

	@Column(name = "PARTNER_CODE")
	private String partnerCode;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "SUSPENSE_TYPE")
	private Integer suspenseType;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "GENERATION_DATE")
	private Date generationDate;
	
	@Transient
	private BigDecimal markOffAmount;
	
	@Transient
	private BigDecimal settleAmount;
	
	@Transient
	private BigDecimal outstandingAmount;
	
	@Transient
	private String settleCurrencyCode;
	
	@Transient
	private BigDecimal exchangeRate;
	
	@Transient
	private String contractCode;
	
	@Transient
	private Date settleDate;
	
	public Suspense() {
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getSuspenseId() {
		return suspenseId;
	}

	public void setSuspenseId(Long suspenseId) {
		this.suspenseId = suspenseId;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	
	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSuspenseType() {
		return suspenseType;
	}

	public void setSuspenseType(Integer suspenseType) {
		this.suspenseType = suspenseType;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getSuspenseId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setSuspenseId(key);
	}
	
	public BigDecimal getMarkOffAmount() {
		return markOffAmount;
	}

	public void setMarkOffAmount(BigDecimal markOffAmount) {
		this.markOffAmount = markOffAmount;
	}

	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}

	public String getSettleCurrencyCode() {
		return settleCurrencyCode;
	}

	public void setSettleCurrencyCode(String settleCurrencyCode) {
		this.settleCurrencyCode = settleCurrencyCode;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(BigDecimal outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public Date getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Date getGenerationDate() {
		return generationDate;
	}

	public void setGenerationDate(Date generationDate) {
		this.generationDate = generationDate;
	}
}