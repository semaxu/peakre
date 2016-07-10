package com.ebao.ap99.claim.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the T_RI_CLAIM_RESERVE database table.
 * 
 */
@Entity
@Table(name = "T_RI_CLAIM_RESERVE")
@NamedQuery(name = "TRiClaimReserve.findAll", query = "SELECT t FROM TRiClaimReserve t")
@NamedQueries({
		@NamedQuery(name = "TRiClaimReserve.findByRefId", query = " FROM TRiClaimReserve s WHERE s.refId = :refId "),
		@NamedQuery(name = "TRiClaimReserve.findByReserveId", query = " FROM TRiClaimReserve s WHERE s.reserveId = :reserveId "),
		//@NamedQuery(name = "TRiClaimReserve.findByRefIdSectionIdBusinessDirection", query = " FROM TRiClaimReserve s WHERE s.refId = :refId and s.sectionId= :sectionId and businessDirection= :businessDirection"),
		@NamedQuery(name = "TRiClaimReserve.findSectionByRefId", query = " select distinct s.sectionId FROM TRiClaimReserve s WHERE s.refId = :refId "),
		@NamedQuery(name = "TRiClaimReserve.findreservelist", query = " FROM TRiClaimReserve s WHERE s.refId = :refId and s.sectionId = :sectionId and s.businessDirection = :businessDirection and s.status =:status "),
		@NamedQuery(name = "TRiClaimReserve.ifHasClaim", query = "select 1 FROM TRiClaimReserve s WHERE  s.sectionId = :sectionId "),
		@NamedQuery(name = "TRiClaimReserve.getrefIdbySectionId", query = "select distinct refId FROM TRiClaimReserve s WHERE  s.sectionId = :sectionId "),

		})

public class TRiClaimReserve extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")

	@Column(name = "RESERVE_ID")
	private Long reserveId;

	@Column(name = "AMOUNT_OC")
	private BigDecimal amountOc;

	@Column(name = "ORG_AMOUNT_OC")
	private BigDecimal orgAmountOc;

	@Column(name = "AMOUNT_USD")
	private BigDecimal amountUsd;

	@Column(name = "ORG_AMOUNT_USD")
	private BigDecimal orgAmountUsd;

	@Column(name = "BROKER_REFER")
	private String brokerRefer;

	@Column(name = "BUSINESS_DIRECTION")
	private String businessDirection;

	@Column(name = "CEDENT_REFER")
	private String cedentRefer;

	@Column(name = "EXCHANGE_RATE")
	private BigDecimal exchangeRate;

	@Column(name = "FRONTING_FLAG")
	private String frontingFlag;
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

	@Column(name = "ORG_POSTING_FLAG")
	private String orgPostingFlag;

	public String getOrgPostingFlag() {
		return orgPostingFlag;
	}

	public void setOrgPostingFlag(String orgPostingFlag) {
		this.orgPostingFlag = orgPostingFlag;
	}

	private String remark;

	@Column(name = "RESERVE_TYPE")
	private String reserveType;

	@Column(name = "SECTION_ID")
	private Long sectionId;

	@Column(name = "RETRO_REF_SECTION_ID")
	private Long retroRefSectionId;
	
	@Column(name = "RELATED_RESERVE_ID")
	private Long relatedReserveId;

	/**
	 * 0 : new , 1 : inforce
	 */
	private String status;

	// @Column(name="UPDATE_BY")
	// private BigDecimal updateBy;
	//
	// @Temporal(TemporalType.DATE)
	// @Column(name="UPDATE_TIME")
	// private Date updateTime;

	@Column(name = "REF_TYPE")
	private String refType;

	// bi-directional many-to-one association to TRiClaimInfo
	// @ManyToOne
	// @JoinColumn(name = "CLAIM_ID")
	// @JoinColumn(name = "REF_ID", referencedColumnName = "CLAIM_ID")
	// private TRiClaimInfo TRiClaimInfo;

	@Column(name = "REF_ID")
	private Long refId;

	// bi-directional many-to-one association to TRiClaimReserveLog
	@OneToMany(mappedBy = "TRiClaimReserve", cascade = { CascadeType.ALL })
	private List<TRiClaimReserveLog> TRiClaimReserveLogs;

	public TRiClaimReserve() {
	}

	public Long getReserveId() {
		return this.reserveId;
	}

	public void setReserveId(Long reserveId) {
		this.reserveId = reserveId;
	}

	public BigDecimal getAmountOc() {
		return this.amountOc;
	}

	public BigDecimal getOrgAmountOc() {
		return orgAmountOc;
	}

	public void setOrgAmountOc(BigDecimal orgAmountOc) {
		this.orgAmountOc = orgAmountOc;
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

	public BigDecimal getOrgAmountUsd() {
		return orgAmountUsd;
	}

	public void setOrgAmountUsd(BigDecimal orgAmountUsd) {
		this.orgAmountUsd = orgAmountUsd;
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

	public void setCedentRefer(String cedentRefer) {
		this.cedentRefer = cedentRefer;
	}

	public BigDecimal getExchangeRate() {
		return this.exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getFrontingFlag() {
		return this.frontingFlag;
	}

	public void setFrontingFlag(String frontingFlag) {
		this.frontingFlag = frontingFlag;
	}
	//
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

	// public TRiClaimInfo getTRiClaimInfo() {
	// return this.TRiClaimInfo;
	// }

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	// public void setTRiClaimInfo(TRiClaimInfo TRiClaimInfo) {
	// this.TRiClaimInfo = TRiClaimInfo;
	// }

	public Long getRefId() {
		return refId;
	}

	public Long getRelatedReserveId() {
		return relatedReserveId;
	}

	public void setRelatedReserveId(Long relatedReserveId) {
		this.relatedReserveId = relatedReserveId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public List<TRiClaimReserveLog> getTRiClaimReserveLogs() {
		return this.TRiClaimReserveLogs;
	}

	public void setTRiClaimReserveLogs(List<TRiClaimReserveLog> TRiClaimReserveLogs) {
		this.TRiClaimReserveLogs = TRiClaimReserveLogs;
	}

	public TRiClaimReserveLog addTRiClaimReserveLog(TRiClaimReserveLog TRiClaimReserveLog) {
		if (getTRiClaimReserveLogs() == null) {
			setTRiClaimReserveLogs(new ArrayList<TRiClaimReserveLog>());
			getTRiClaimReserveLogs().add(TRiClaimReserveLog);
		} else {
			getTRiClaimReserveLogs().add(TRiClaimReserveLog);
		}
		
		TRiClaimReserveLog.setTRiClaimReserve(this);

		return TRiClaimReserveLog;
	}

	public TRiClaimReserveLog removeTRiClaimReserveLog(TRiClaimReserveLog TRiClaimReserveLog) {
		getTRiClaimReserveLogs().remove(TRiClaimReserveLog);
		TRiClaimReserveLog.setTRiClaimReserve(null);

		return TRiClaimReserveLog;
	}

	@Override
	public Long getPrimaryKey() {
		return null;
	}

	@Override
	public void setPrimaryKey(Long paramLong) {

	}

}