package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_LIMIT_EVENT_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_LIMIT_EVENT_LOG")
@NamedQuery(name = "TRiContLimitEventLog.findAll", query = "SELECT t FROM TRiContLimitEventLog t")
public class TRiContLimitEventLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

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

	@Column(name = "LIMIT_TYPE")
	private String limitType;

	@Column(name = "LIABILITY_SIGNED_SHARE")
	private BigDecimal liabilitySignedShare;

	@Column(name = "LIABILITY_WRITTEN_SHARE")
	private BigDecimal liabilityWrittenShare;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "EVENT_ID")
	private Long eventId;

	@Column(name = "LIMIT_ID")
	private Long limitId;

	@Column(name = "ITEM_TYPE")
	private String itemType;

	public TRiContLimitEventLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
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

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public BigDecimal getLiabilitySignedShare() {
		return liabilitySignedShare;
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

	@Override
	public Long getPrimaryKey() {
		// TODO Auto-generated method stub
		return this.getLogId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		// TODO Auto-generated method stub
		this.setLogId(key);
	}

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getLimitId() {
		return limitId;
	}

	public void setLimitId(Long limitId) {
		this.limitId = limitId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

}