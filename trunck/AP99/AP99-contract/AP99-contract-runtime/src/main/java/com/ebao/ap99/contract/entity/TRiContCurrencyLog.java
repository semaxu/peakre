package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the T_RI_CONT_CURRENCY_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_CURRENCY_LOG")
@NamedQuery(name = "TRiContCurrencyLog.findAll", query = "SELECT t FROM TRiContCurrencyLog t")
public class TRiContCurrencyLog extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Column(name = "CURRENCY_DATE")
	private Date currencyDate;

	@Column(name = "CURRENCY_REMARKS")
	private String currencyRemarks;

	@Column(name = "CURRENCY_TYPE")
	private String currencyType;

	@Column(name = "EXCHANGE_TYPE")
	private String exchangeType;

	@Column(name = "MAIN_CURRENCY")
	private String mainCurrency;

	@Column(name = "CURRENCY_RATE")
	private BigDecimal currencyRate;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "CURRENCY_ID")
	private Long currencyId;

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public TRiContCurrencyLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public Date getCurrencyDate() {
		return currencyDate;
	}

	public void setCurrencyDate(Date currencyDate) {
		this.currencyDate = currencyDate;
	}

	public String getCurrencyRemarks() {
		return this.currencyRemarks;
	}

	public void setCurrencyRemarks(String currencyRemarks) {
		this.currencyRemarks = currencyRemarks;
	}

	public String getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getExchangeType() {
		return this.exchangeType;
	}

	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}

	public String getMainCurrency() {
		return this.mainCurrency;
	}

	public void setMainCurrency(String mainCurrency) {
		this.mainCurrency = mainCurrency;
	}

	public BigDecimal getRate() {
		return this.currencyRate;
	}

	public void setRate(BigDecimal currencyRate) {
		this.currencyRate = currencyRate;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getLogId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setLogId(key);

	}

	public BigDecimal getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(BigDecimal currencyRate) {
		this.currencyRate = currencyRate;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

}