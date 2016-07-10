/**
 * 
 */
package com.ebao.ap99.arap.vo;

import com.ebao.ap99.arap.entity.CurrencyExchangeRate;
import com.ebao.ap99.parent.Page;

/**
 * @author meiliang.zou
 *
 */
public class ExchangeRateDTO extends Page<CurrencyExchangeRate> {
	private static final long serialVersionUID = 3156114727696165356L;

	private Long rateId;
	
	private String baseCurrency;

	private String exchangeCurrency;

	private String exchangeRate;

	private String validDateFrom;

	private String validDateTo;
	
	private String validDate;

	private String expireDateFrom;
	
	private String expireDateTo;
	
	private String expireDate;
	
	private String rateType;
	
	private String status;
	
	private Boolean isImport;

	public Long getRateId() {
		return rateId;
	}

	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getExchangeCurrency() {
		return exchangeCurrency;
	}

	public void setExchangeCurrency(String exchangeCurrency) {
		this.exchangeCurrency = exchangeCurrency;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * @return the validDateFrom
	 */
	public String getValidDateFrom() {
		return validDateFrom;
	}

	/**
	 * @param validDateFrom the validDateFrom to set
	 */
	public void setValidDateFrom(String validDateFrom) {
		this.validDateFrom = validDateFrom;
	}

	/**
	 * @return the validDateTo
	 */
	public String getValidDateTo() {
		return validDateTo;
	}

	/**
	 * @param validDateTo the validDateTo to set
	 */
	public void setValidDateTo(String validDateTo) {
		this.validDateTo = validDateTo;
	}

	/**
	 * @return the expireDateFrom
	 */
	public String getExpireDateFrom() {
		return expireDateFrom;
	}

	/**
	 * @param expireDateFrom the expireDateFrom to set
	 */
	public void setExpireDateFrom(String expireDateFrom) {
		this.expireDateFrom = expireDateFrom;
	}

	/**
	 * @return the expireDateTo
	 */
	public String getExpireDateTo() {
		return expireDateTo;
	}

	/**
	 * @param expireDateTo the expireDateTo to set
	 */
	public void setExpireDateTo(String expireDateTo) {
		this.expireDateTo = expireDateTo;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the validDate
	 */
	public String getValidDate() {
		return validDate;
	}

	/**
	 * @param validDate the validDate to set
	 */
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	/**
	 * @return the expireDate
	 */
	public String getExpireDate() {
		return expireDate;
	}

	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @return the isImport
	 */
	public Boolean getIsImport() {
		return isImport;
	}

	/**
	 * @param isImport the isImport to set
	 */
	public void setIsImport(Boolean isImport) {
		this.isImport = isImport;
	}

}
