package com.ebao.ap99.contract.model;

import java.math.BigDecimal;

public class LimitEventVO {
	private Long eventId;

	private String commRemark;

	private String currency;

	private String event;

	private BigDecimal liability;

	private BigDecimal limitHundred;

	private BigDecimal limitShare;

	private BigDecimal writtenShare;

	public BigDecimal getWrittenShare() {
		return writtenShare;
	}

	public void setWrittenShare(BigDecimal writtenShare) {
		this.writtenShare = writtenShare;
	}

	private String limitType;

	private BigDecimal liabilitySignedShare;

	private BigDecimal liabilityWrittenShare;

	private Long limitId;

	private String itemType;

	public LimitEventVO() {

	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getCommRemark() {
		return commRemark;
	}

	public void setCommRemark(String commRemark) {
		this.commRemark = commRemark;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public BigDecimal getLiability() {
		return liability;
	}

	public void setLiability(BigDecimal liability) {
		this.liability = liability;
	}

	public BigDecimal getLimitHundred() {
		return limitHundred;
	}

	public void setLimitHundred(BigDecimal limitHundred) {
		this.limitHundred = limitHundred;
	}

	public BigDecimal getLimitShare() {
		return limitShare;
	}

	public void setLimitShare(BigDecimal limitShare) {
		this.limitShare = limitShare;
	}

	public String getLimitType() {
		return limitType;
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
