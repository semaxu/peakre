package com.ebao.ap99.arap.vo;

import java.io.Serializable;
import java.util.Date;

public class CurrencyExchangeRateCondition implements Serializable {

	private static final long serialVersionUID = 1L;

	private String baseCurrencyCode;
	private String exCurrencyCode;
	private Date validDateFrom;
	private Date validDateTo;
	private Date validDate;
	private Date expiryDateFrom;
	private Date expiryDateTo;
	private Date expiryDate;
	private Integer rateType;
	
	private int pageNumber = 1;
	private int pageSize = 10;
	private long totalSize = 10;

	public String getBaseCurrencyCode() {
		return baseCurrencyCode;
	}

	public void setBaseCurrencyCode(String baseCurrencyCode) {
		this.baseCurrencyCode = baseCurrencyCode;
	}

	public String getExCurrencyCode() {
		return exCurrencyCode;
	}

	public void setExCurrencyCode(String exCurrencyCode) {
		this.exCurrencyCode = exCurrencyCode;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Integer getRateType() {
		return rateType;
	}

	public void setRateType(Integer rateType) {
		this.rateType = rateType;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	/**
	 * @return the validDateFrom
	 */
	public Date getValidDateFrom() {
		return validDateFrom;
	}

	/**
	 * @param validDateFrom the validDateFrom to set
	 */
	public void setValidDateFrom(Date validDateFrom) {
		this.validDateFrom = validDateFrom;
	}

	/**
	 * @return the validDateTo
	 */
	public Date getValidDateTo() {
		return validDateTo;
	}

	/**
	 * @param validDateTo the validDateTo to set
	 */
	public void setValidDateTo(Date validDateTo) {
		this.validDateTo = validDateTo;
	}

	/**
	 * @return the expiryDateFrom
	 */
	public Date getExpiryDateFrom() {
		return expiryDateFrom;
	}

	/**
	 * @param expiryDateFrom the expiryDateFrom to set
	 */
	public void setExpiryDateFrom(Date expiryDateFrom) {
		this.expiryDateFrom = expiryDateFrom;
	}

	/**
	 * @return the expiryDateTo
	 */
	public Date getExpiryDateTo() {
		return expiryDateTo;
	}

	/**
	 * @param expiryDateTo the expiryDateTo to set
	 */
	public void setExpiryDateTo(Date expiryDateTo) {
		this.expiryDateTo = expiryDateTo;
	}
	
}
