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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the T_RI_CONT_PRICING_ITEM_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_PRICING_ITEM_LOG")
@NamedQueries({
		@NamedQuery(name = "TRiContPricingItemLog.loadByPricingId", query = "SELECT t FROM TRiContPricingItemLog t WHERE t.pricingId = :pricingId ORDER BY t.logId DESC"),
		@NamedQuery(name = "TRiContPricingItemLog.loadByPricingIdAndOperateId", query = "SELECT t FROM TRiContPricingItemLog t WHERE t.pricingId = :pricingId AND t.operateId <= :operateId ORDER BY t.logId DESC"),
		@NamedQuery(name = "TRiContPricingItemLog.findAll", query = "SELECT t FROM TRiContPricingItemLog t") })
public class TRiContPricingItemLog extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	private BigDecimal brokerage;

	private BigDecimal commission;

	private String currency;

	private BigDecimal epi;

	@Column(name = "LOSS_RATIO")
	private BigDecimal lossRatio;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Temporal(TemporalType.DATE)
	@Column(name = "PRICING_DATE")
	private Date pricingDate;

	@Column(name = "PRICING_ID")
	private Long pricingId;

	private BigDecimal taxs;

	private Long underwriter;

	@Column(name = "ITEM_ID")
	private Long itemId;

	public TRiContPricingItemLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public BigDecimal getBrokerage() {
		return this.brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public BigDecimal getCommission() {
		return this.commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getEpi() {
		return this.epi;
	}

	public void setEpi(BigDecimal epi) {
		this.epi = epi;
	}

	public BigDecimal getLossRatio() {
		return this.lossRatio;
	}

	public void setLossRatio(BigDecimal lossRatio) {
		this.lossRatio = lossRatio;
	}

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public Date getPricingDate() {
		return this.pricingDate;
	}

	public void setPricingDate(Date pricingDate) {
		this.pricingDate = pricingDate;
	}

	public Long getPricingId() {
		return this.pricingId;
	}

	public void setPricingId(Long pricingId) {
		this.pricingId = pricingId;
	}

	public BigDecimal getTaxs() {
		return this.taxs;
	}

	public void setTaxs(BigDecimal taxs) {
		this.taxs = taxs;
	}

	public Long getUnderwriter() {
		return this.underwriter;
	}

	public void setUnderwriter(Long underwriter) {
		this.underwriter = underwriter;
	}

	@Override
	public Long getPrimaryKey() {
		return logId;
	}

	@Override
	public void setPrimaryKey(Long paramLong) {
		this.logId = paramLong;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
}