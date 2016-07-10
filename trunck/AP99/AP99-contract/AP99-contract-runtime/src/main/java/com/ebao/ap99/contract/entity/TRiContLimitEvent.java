package com.ebao.ap99.contract.entity;

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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Where;
import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_LIMIT_EVENT database table.
 */
@Entity
@Table(name = "T_RI_CONT_LIMIT_EVENT")
@NamedQuery(name = "TRiContLimitEvent.findAll", query = "SELECT t FROM TRiContLimitEvent t WHERE t.isActive != 'N'")
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContLimitEvent extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "EVENT_ID")
	@XmlTransient
	private Long eventId;

	@Column(name = "COMM_REMARK")
	private String commRemark;

	private String currency;

	private String event;

	private BigDecimal liability;

	@Column(name = "LIMIT_TOTAL_AMOUNT")
	private BigDecimal limitHundred;

	@Column(name = "LIMIT_SHARE")
	private BigDecimal limitShare;

	@Column(name = "WRITTEN_SHARE")
	private BigDecimal writtenShare;

	public BigDecimal getWrittenShare() {
		return writtenShare;
	}

	public void setWrittenShare(BigDecimal writtenShare) {
		this.writtenShare = writtenShare;
	}

	@Column(name = "LIABILITY_SIGNED_SHARE")
	private BigDecimal liabilitySignedShare;

	@Column(name = "LIMIT_TYPE")
	private String limitType;

	@Column(name = "LIABILITY_WRITTEN_SHARE")
	private BigDecimal liabilityWrittenShare;

	@Column(name = "ITEM_TYPE")
	private String itemType;

	// bi-directional many-to-one association to TRiContLimit
	@ManyToOne
	@JoinColumn(name = "LIMIT_ID")
	@Where(clause = "IS_ACTIVE='Y'")
	@XmlTransient
	private TRiContLimit TRiContLimit;

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

	public TRiContLimitEvent() {
		// default Y
		this.isActive = "Y";
	}

	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getCommRemark() {
		return this.commRemark;
	}

	public void setCommRemark(String commRemark) {
		this.commRemark = commRemark;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getEvent() {
		return this.event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public BigDecimal getLiability() {
		return this.liability;
	}

	public void setLiability(BigDecimal liability) {
		this.liability = liability;
	}

	public BigDecimal getLimitHundred() {
		return this.limitHundred;
	}

	public void setLimitHundred(BigDecimal limitHundred) {
		this.limitHundred = limitHundred;
	}

	public BigDecimal getLimitShare() {
		return this.limitShare;
	}

	public void setLimitShare(BigDecimal limitShare) {
		this.limitShare = limitShare;
	}

	public String getLimitType() {
		return this.limitType;
	}

	public BigDecimal getLiabilitySignedShare() {
		return liabilitySignedShare;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public void setLiabilitySignedShare(BigDecimal liabilitySignedShare) {
		this.liabilitySignedShare = liabilitySignedShare;
	}

	public BigDecimal getLiabilityWrittenShare() {
		return liabilityWrittenShare;
	}

	public void setLiabilityWrittenShare(BigDecimal liabilityWrittenShare) {
		this.liabilityWrittenShare = liabilityWrittenShare;
	}

	public TRiContLimit getTRiContLimit() {
		return this.TRiContLimit;
	}

	public void setTRiContLimit(TRiContLimit TRiContLimit) {
		this.TRiContLimit = TRiContLimit;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getEventId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		// TODO Auto-generated method stub
		this.setEventId(key);
		;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

}
