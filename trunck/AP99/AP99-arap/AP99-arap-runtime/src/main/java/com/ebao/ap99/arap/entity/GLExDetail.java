package com.ebao.ap99.arap.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;


/**
 * The persistent class for the T_RI_GL_EX_DETAIL database table.
 * 
 */
@Entity
@Table(name="T_RI_GL_EX_DETAIL")
@NamedQuery(name="GLExDetail.findAll", query="SELECT g FROM GLExDetail g")
@NamedQueries({
	@NamedQuery(name = "GLExDetail.findBySourceTypeAndSourceId", query = " FROM GLExDetail c WHERE c.sourceType = :sourceType and c.sourceId = :sourceId")
})
public class GLExDetail extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name="DETAIL_ID")
	private Long detailId;

	@Column(name="CURRENCY_CODE")
	private String currencyCode;

	@Column(name="AMOUNT")
	private BigDecimal amount = BigDecimal.ZERO;

	@Column(name="EXCHANGE_RATE")
	private BigDecimal exchangeRate;
	
	@Column(name="SOURCE_TYPE")
	private Integer sourceType;
	
	@Column(name="SOURCE_ID")
	private Long sourceId;

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getDetailId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setDetailId(key);
	}
}