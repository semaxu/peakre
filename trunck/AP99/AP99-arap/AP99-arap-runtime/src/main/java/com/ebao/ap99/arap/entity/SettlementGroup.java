package com.ebao.ap99.arap.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;

/**
 * The persistent class for the T_RI_BCP_SETTLEMENT_GROUP database table.
 * 
 */
@Entity
@Table(name = "T_RI_BCP_SETTLEMENT_GROUP")
@NamedQuery(name = "SettlementGroup.findAll", query = "SELECT s FROM SettlementGroup s")
public class SettlementGroup extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "SETTLE_GROUP_ID")
	private Long settleGroupId;

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

	public SettlementGroup() {
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

	public Long getSettleGroupId() {
		return settleGroupId;
	}

	public void setSettleGroupId(Long settleGroupId) {
		this.settleGroupId = settleGroupId;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getSettleGroupId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setSettleGroupId(key);
	}

}