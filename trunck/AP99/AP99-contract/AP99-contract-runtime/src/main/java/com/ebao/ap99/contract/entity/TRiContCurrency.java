package com.ebao.ap99.contract.entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_CURRENCY database table.
 */
@Entity
@Table(name = "T_RI_CONT_CURRENCY")

@NamedQueries({
		@NamedQuery(name = "TRiContCurrency.findAll", query = "SELECT t FROM TRiContCurrency t WHERE t.isActive != 'N'"),
		@NamedQuery(name = "TRiContCurrency.loadByContCompId", query = "SELECT t FROM TRiContCurrency t WHERE t.contCompId = :contCompId and t.isActive != 'N' ORDER BY t.currencyId "),
		@NamedQuery(name = "TRiContCurrency.loadMainCurrencyList", query = "SELECT t.currencyType FROM TRiContCurrency t WHERE t.mainCurrency = 'true' and t.contCompId = :contCompId and t.isActive != 'N'") })
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContCurrency extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "CURRENCY_ID")
	@XmlTransient
	private Long currencyId;

	@Column(name = "CONT_COMP_ID")
	@XmlTransient
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

	@Column(name = "IS_ACTIVE")
	@XmlTransient
	private String isActive;

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		if (StringUtils.isNullOrEmpty(isActive)) {
			this.isActive = "Y";
		} else {
			this.isActive = isActive;
		}
	}

	public TRiContCurrency() {
		// default Y
		this.isActive = "Y";
	}

	public Long getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
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

	public BigDecimal getCurrencyRate() {
		return this.currencyRate;
	}

	public void setCurrencyRate(BigDecimal currencyRate) {
		this.currencyRate = currencyRate;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getCurrencyId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setCurrencyId(key);
	}

}
