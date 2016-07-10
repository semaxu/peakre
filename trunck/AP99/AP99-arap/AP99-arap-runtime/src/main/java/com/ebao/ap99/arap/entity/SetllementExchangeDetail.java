package com.ebao.ap99.arap.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;

/**
 * The persistent class for the T_RI_BCP_SETLLE_EX_DETAIL database table.
 * 
 */
@Entity
@Table(name = "T_RI_BCP_SETLLE_EX_DETAIL")
@NamedQuery(name = "SetllementExchangeDetail.findAll", query = "SELECT s FROM SetllementExchangeDetail s")
@NamedQueries({
	@NamedQuery(name = "SetllementExchangeDetail.findBySourceTypeAndSourceId", query = " FROM SetllementExchangeDetail c WHERE c.sourceType = :sourceType and c.sourceId = :sourceId")
})
public class SetllementExchangeDetail extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "EXCHANGE_DETAIL_ID")
	private Long exchangeDetailId;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "CONVERT_CURRENCY_CODE")
	private String convertCurrencyCode;

	@Column(name = "CONVERTED_AMOUNT")
	private BigDecimal convertedAmount;

	@Column(name = "CURRENCY_CODE")
	private String currencyCode;

	@Column(name = "EXCHANGE_RATE")
	private BigDecimal exchangeRate;

	@Column(name = "SOURCE_TYPE")
	private Integer sourceType;
	
	@Column(name = "SOURCE_ID")
	private Long sourceId;

	public SetllementExchangeDetail() {
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getConvertCurrencyCode() {
		return this.convertCurrencyCode;
	}

	public void setConvertCurrencyCode(String convertCurrencyCode) {
		this.convertCurrencyCode = convertCurrencyCode;
	}

	public BigDecimal getConvertedAmount() {
		return this.convertedAmount;
	}

	public void setConvertedAmount(BigDecimal convertedAmount) {
		this.convertedAmount = convertedAmount;
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getExchangeRate() {
		return this.exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Long getExchangeDetailId() {
		return exchangeDetailId;
	}

	public void setExchangeDetailId(Long exchangeDetailId) {
		this.exchangeDetailId = exchangeDetailId;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getExchangeDetailId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setExchangeDetailId(key);
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
}