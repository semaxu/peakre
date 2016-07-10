package com.ebao.ap99.arap.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the T_RI_CURRENCY database table.
 * 
 */
@Entity
@Table(name="T_RI_CURRENCY")
@NamedQuery(name="Currency.findAll", query="SELECT c FROM Currency c")
@NamedQueries({
	@NamedQuery(name = "Currency.findByCurrencyCode", query = " FROM Currency c WHERE c.currencyCode = :currencyCode")
})
public class Currency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CURRENCY_CODE")
	private String currencyCode;

	@Column(name="CURRENCY_NAME")
	private String currencyName;

	@Column(name="GL_CURRENCY")
	private Integer glCurrency;

	@Column(name="PEPORTING_CURRENCY")
	private Integer peportingCurrency;

	@Column(name="SYMBOL")
	private String symbol;

	public Currency() {
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return this.currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public Integer getGlCurrency() {
		return glCurrency;
	}

	public void setGlCurrency(Integer glCurrency) {
		this.glCurrency = glCurrency;
	}

	public Integer getPeportingCurrency() {
		return peportingCurrency;
	}

	public void setPeportingCurrency(Integer peportingCurrency) {
		this.peportingCurrency = peportingCurrency;
	}

	public String getSymbol() {
		return this.symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}