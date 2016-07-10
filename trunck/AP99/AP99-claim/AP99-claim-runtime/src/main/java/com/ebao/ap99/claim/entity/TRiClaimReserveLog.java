package com.ebao.ap99.claim.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the T_RI_CLAIM_RESERVE_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CLAIM_RESERVE_LOG")
@NamedQuery(name = "TRiClaimReserveLog.findAll", query = "SELECT t FROM TRiClaimReserveLog t")

public class TRiClaimReserveLog extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")

	@Column(name = "RESERVE_HIS_ID")
	private long reserveHisId;

	@Column(name = "AMOUNT_OC")
	private BigDecimal amountOc;

	@Column(name = "AMOUNT_USD")
	private BigDecimal amountUsd;

	@Column(name = "BROKER_REFER")
	private String brokerRefer;

	@Column(name = "BUSINESS_DIRECTION")
	private String businessDirection;

	@Column(name = "CEDENT_REFER")
	private String cedentRefer;

	@Column(name = "EXCHANGE_RATE")
	private BigDecimal exchangeRate;
	//
	// @Column(name="INSERT_BY")
	// private BigDecimal insertBy;
	//
	// @Temporal(TemporalType.DATE)
	// @Column(name="INSERT_TIME")
	// private Date insertTime;

	@Column(name = "ORIGINAL_CURRENCY")
	private String originalCurrency;

	@Column(name = "POSTING_FLAG")
	private String postingFlag;

	private String remark;

	@Column(name = "RESERVE_TYPE")
	private String reserveType;

	@Column(name = "SECTION_ID")
	private Long sectionId;

	@Column(name = "RETRO_REF_SECTION_ID")
	private Long retroRefSectionId;
	
	@Column(name = "RELATED_RESERVE_ID")
	private Long relatedReserveId;
	
	private String status;

	// @Column(name="UPDATE_BY")
	// private BigDecimal updateBy;
	//
	// @Temporal(TemporalType.DATE)
	// @Column(name="UPDATE_TIME")
	// private Date updateTime;

	// bi-directional many-to-one association to TRiClaimReserve
	@ManyToOne
	@JoinColumn(name = "RESERVE_ID")
	private TRiClaimReserve TRiClaimReserve;

	public Long getRelatedReserveId() {
		return relatedReserveId;
	}

	public void setRelatedReserveId(Long relatedReserveId) {
		this.relatedReserveId = relatedReserveId;
	}

	public TRiClaimReserveLog() {
	}

	public long getReserveHisId() {
		return this.reserveHisId;
	}

	public void setReserveHisId(long reserveHisId) {
		this.reserveHisId = reserveHisId;
	}

	public BigDecimal getAmountOc() {
		return this.amountOc;
	}

	public void setAmountOc(BigDecimal amountOc) {
		this.amountOc = amountOc;
	}

	public BigDecimal getAmountUsd() {
		return this.amountUsd;
	}

	public void setAmountUsd(BigDecimal amountUsd) {
		this.amountUsd = amountUsd;
	}

	public String getBrokerRefer() {
		return this.brokerRefer;
	}

	public void setBrokerRefer(String brokerRefer) {
		this.brokerRefer = brokerRefer;
	}

	public String getBusinessDirection() {
		return this.businessDirection;
	}

	public void setBusinessDirection(String businessDirection) {
		this.businessDirection = businessDirection;
	}

	public String getCedentRefer() {
		return this.cedentRefer;
	}

	public void setCedentRefer(String cedantRefer) {
		this.cedentRefer = cedantRefer;
	}

	public BigDecimal getExchangeRate() {
		return this.exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	// public BigDecimal getInsertBy() {
	// return this.insertBy;
	// }
	//
	// public void setInsertBy(BigDecimal insertBy) {
	// this.insertBy = insertBy;
	// }
	//
	// public Date getInsertTime() {
	// return this.insertTime;
	// }
	//
	// public void setInsertTime(Date insertTime) {
	// this.insertTime = insertTime;
	// }

	public String getOriginalCurrency() {
		return this.originalCurrency;
	}

	public void setOriginalCurrency(String originalCurrency) {
		this.originalCurrency = originalCurrency;
	}

	public String getPostingFlag() {
		return this.postingFlag;
	}

	public void setPostingFlag(String postingFlag) {
		this.postingFlag = postingFlag;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReserveType() {
		return this.reserveType;
	}

	public void setReserveType(String reserveType) {
		this.reserveType = reserveType;
	}

	public Long getSectionId() {
		return this.sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public Long getRetroRefSectionId() {
		return retroRefSectionId;
	}

	public void setRetroRefSectionId(Long retroRefSectionId) {
		this.retroRefSectionId = retroRefSectionId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// public BigDecimal getUpdateBy() {
	// return this.updateBy;
	// }
	//
	// public void setUpdateBy(BigDecimal updateBy) {
	// this.updateBy = updateBy;
	// }
	//
	// public Date getUpdateTime() {
	// return this.updateTime;
	// }
	//
	// public void setUpdateTime(Date updateTime) {
	// this.updateTime = updateTime;
	// }

	public TRiClaimReserve getTRiClaimReserve() {
		return this.TRiClaimReserve;
	}

	public void setTRiClaimReserve(TRiClaimReserve TRiClaimReserve) {
		this.TRiClaimReserve = TRiClaimReserve;
	}

	@Override
	public Long getPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPrimaryKey(Long paramLong) {
		// TODO Auto-generated method stub

	}

}