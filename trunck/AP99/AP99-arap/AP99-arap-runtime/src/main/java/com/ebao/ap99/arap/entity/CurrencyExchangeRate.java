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

import com.ebao.ap99.file.model.ItemVO;
import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;

/**
 * The persistent class for the T_RI_BCP_CFG_EXCHANGE_RATE database table.
 * 
 */
@Entity
@Table(name = "T_RI_BCP_CFG_EXCHANGE_RATE")
@NamedQuery(name = "CurrencyExchangeRate.findAll", query = "SELECT c FROM CurrencyExchangeRate c")
public class CurrencyExchangeRate extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "RATE_ID")
	private Long rateId;

	@Column(name = "BASE_CURRENCY_CODE")
	private String baseCurrencyCode;

	@Column(name = "EX_CURRENCY_CODE")
	private String exCurrencyCode;

	@Temporal(TemporalType.DATE)
	@Column(name = "EXPIRY_DATE")
	private Date expiryDate;

	@Column(name = "RATE")
	private  BigDecimal rate;

	@Column(name = "RATE_TYPE")
	private Integer rateType;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "IS_IMPORT")
	private Boolean isImport;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "VALID_DATE")
	private Date validDate;
	
	@Transient
	private ItemVO<CurrencyExchangeRate> importVO;
	
	public CurrencyExchangeRate() {
	}

	public Long getRateId() {
		return this.rateId;
	}

	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}

	public String getBaseCurrencyCode() {
		return this.baseCurrencyCode;
	}

	public void setBaseCurrencyCode(String baseCurrencyCode) {
		this.baseCurrencyCode = baseCurrencyCode;
	}

	public String getExCurrencyCode() {
		return this.exCurrencyCode;
	}

	public void setExCurrencyCode(String exCurrencyCode) {
		this.exCurrencyCode = exCurrencyCode;
	}

	public Date getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Date getValidDate() {
		return this.validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getRateId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setRateId(key);
	}

	public Integer getRateType() {
		return rateType;
	}

	public void setRateType(Integer rateType) {
		this.rateType = rateType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getIsImport() {
		return isImport;
	}

	public void setIsImport(Boolean isImport) {
		this.isImport = isImport;
	}

	public ItemVO<CurrencyExchangeRate> getImportVO() {
		return importVO;
	}

	public void setImportVO(ItemVO<CurrencyExchangeRate> importVO) {
		this.importVO = importVO;
	}

}