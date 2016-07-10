package com.ebao.ap99.contract.model;

import java.math.BigDecimal;
import java.util.Date;

public class PricingEstimateItemVO {
	private Long itemId;
	private Long contCompId;
	private BigDecimal brokerage;
	private BigDecimal commission;
	private String currency;
	private BigDecimal epi;
	private BigDecimal lossRatio;
	private Date pricingDate;
	private BigDecimal taxs;
	private Long underwriter;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public BigDecimal getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getEpi() {
		return epi;
	}

	public void setEpi(BigDecimal epi) {
		this.epi = epi;
	}

	public BigDecimal getLossRatio() {
		return lossRatio;
	}

	public void setLossRatio(BigDecimal lossRatio) {
		this.lossRatio = lossRatio;
	}

	public Date getPricingDate() {
		return pricingDate;
	}

	public void setPricingDate(Date pricingDate) {
		this.pricingDate = pricingDate;
	}

	public BigDecimal getTaxs() {
		return taxs;
	}

	public void setTaxs(BigDecimal taxs) {
		this.taxs = taxs;
	}

	public Long getUnderwriter() {
		return underwriter;
	}

	public void setUnderwriter(Long underwriter) {
		this.underwriter = underwriter;
	}

}
