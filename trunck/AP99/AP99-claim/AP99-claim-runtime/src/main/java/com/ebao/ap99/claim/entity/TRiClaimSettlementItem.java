package com.ebao.ap99.claim.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the T_RI_CLAIM_SETTLEMENT_ITEM database table.
 * 
 */
@Entity
@Table(name = "T_RI_CLAIM_SETTLEMENT_ITEM")
@NamedQuery(name = "TRiClaimSettlementItem.findAll", query = "SELECT t FROM TRiClaimSettlementItem t")
@NamedQueries({
		@NamedQuery(name = "TRiClaimSettlementItem.findBySettlementIdAndPostingFlag", query = " FROM TRiClaimSettlementItem s WHERE s.TRiClaimSettlement.settlementId = :settlementId and s.postingFlag='1' "),
		@NamedQuery(name = "TRiClaimSettlementItem.findBySettlement", query = " FROM TRiClaimSettlementItem s WHERE s.TRiClaimSettlement.settlementId = :settlementId  "),
		@NamedQuery(name = "TRiClaimSettlementItem.findBySettlementId", query = " FROM TRiClaimSettlementItem s WHERE s.TRiClaimSettlement.settlementId = :settlementId "),
		@NamedQuery(name = "TRiClaimSettlementItem.findPostingFlagBySettlementId", query = " select s.postingFlag FROM TRiClaimSettlementItem s WHERE s.settleDetailId= :settleDetailId "),
		@NamedQuery(name = "TRiClaimSettlementItem.findByRelatedSettlementId", query = " FROM TRiClaimSettlementItem s WHERE s.relatedSettleDetailId = :relatedSettleDetailId "),
		@NamedQuery(name = "TRiClaimSettlementItem.findBySettlementIdAndPostingFlagChange", query = " FROM TRiClaimSettlementItem s WHERE s.TRiClaimSettlement.settlementId = :settlementId  and s.postingFlag <> s.orgPostingFlag "),
//		@NamedQuery(name = "TRiClaimSettlementItem.findsettlelist", query = " FROM TRiClaimSettlementItem s WHERE s.refId = :refId and s.sectionId = :sectionId and s.businessDirection = :businessDirection and s.status =:status "),
})
public class TRiClaimSettlementItem extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl
		implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")

	@Column(name = "SETTLE_DETAIL_ID")
	private long settleDetailId;

	@Column(name = "AMOUNT_OC")
	private BigDecimal amountOc;

	@Column(name = "AMOUNT_USD")
	private BigDecimal amountUsd;

	@Column(name = "BROKER_REFER")
	private String brokerRefer;

	@Column(name = "BUSINESS_DIRECTION")
	private String businessDirection;

	@Column(name = "CEDANT_REFER")
	private String cedentRefer;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_OF_PAYMENT")
	private Date dateOfPayment;

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

	@Column(name = "PAY_TYPE")
	private String payType;

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

	@Column(name = "SECTION_ID")
	private BigDecimal sectionId;

	@Column(name = "RETRO_REF_SECTION_ID")
	private BigDecimal retroRefSectionId;
	
	@Column(name = "Related_Settle_Detail_Id")
	private BigDecimal relatedSettleDetailId;

	@Column(name = "SETTLEMENT_TYPE")
	private String settlementType;

	// @Column(name="UPDATE_BY")
	// private BigDecimal updateBy;
	//
	// @Temporal(TemporalType.DATE)
	// @Column(name="UPDATE_TIME")
	// private Date updateTime;

	// bi-directional many-to-one association to TRiClaimSettlement
	@ManyToOne
	@JoinColumn(name = "SETTLEMENT_ID")
	private TRiClaimSettlement TRiClaimSettlement;

	public TRiClaimSettlementItem() {
	}

	public long getSettleDetailId() {
		return this.settleDetailId;
	}

	public void setSettleDetailId(long settleDetailId) {
		this.settleDetailId = settleDetailId;
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
		return cedentRefer;
	}

	public void setCedentRefer(String cedentRefer) {
		this.cedentRefer = cedentRefer;
	}

	public BigDecimal getRelatedSettleDetailId() {
		return relatedSettleDetailId;
	}

	public void setRelatedSettleDetailId(BigDecimal relatedSettleDetailId) {
		this.relatedSettleDetailId = relatedSettleDetailId;
	}

	public Date getDateOfPayment() {
		return this.dateOfPayment;
	}

	public void setDateOfPayment(Date dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
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

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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

	public BigDecimal getSectionId() {
		return this.sectionId;
	}

	public void setSectionId(BigDecimal sectionId) {
		this.sectionId = sectionId;
	}

	public BigDecimal getRetroRefSectionId() {
		return retroRefSectionId;
	}

	public void setRetroRefSectionId(BigDecimal retroRefSectionId) {
		this.retroRefSectionId = retroRefSectionId;
	}

	public String getSettlementType() {
		return this.settlementType;
	}

	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
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

	public TRiClaimSettlement getTRiClaimSettlement() {
		return this.TRiClaimSettlement;
	}

	public void setTRiClaimSettlement(TRiClaimSettlement TRiClaimSettlement) {
		this.TRiClaimSettlement = TRiClaimSettlement;
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