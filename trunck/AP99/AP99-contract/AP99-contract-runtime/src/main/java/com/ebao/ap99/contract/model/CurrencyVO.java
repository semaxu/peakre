package com.ebao.ap99.contract.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CurrencyVO {

	private Long contCompId;
	private String contractNature;

	// Currency
	private Long currencyId;
	private Date currencyDate;
	private String currencyRemarks;
	private String currencyType;
	private String exchangeType;
	@JsonIgnore
	private String mainCurrency;
	private Boolean mainCurrencyType;
	private BigDecimal currencyRate;

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getContractNature() {
		return contractNature;
	}

	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public Date getCurrencyDate() {
		return currencyDate;
	}

	public void setCurrencyDate(Date currencyDate) {
		this.currencyDate = currencyDate;
	}

	public String getCurrencyRemarks() {
		return currencyRemarks;
	}

	public void setCurrencyRemarks(String currencyRemarks) {
		this.currencyRemarks = currencyRemarks;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}

	public String getMainCurrency() {
		return mainCurrency;
	}

	public void setMainCurrency(String mainCurrency) {
		this.mainCurrencyType = Boolean.parseBoolean(mainCurrency);
		this.mainCurrency = mainCurrency;
	}

	public BigDecimal getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(BigDecimal currencyRate) {
		this.currencyRate = currencyRate;
	}

	public Boolean getMainCurrencyType() {
		return mainCurrencyType;
	}

	public void setMainCurrencyType(Boolean mainCurrencyType) {
		this.mainCurrency = String.valueOf(mainCurrencyType);
		this.mainCurrencyType = mainCurrencyType;
	}

}
