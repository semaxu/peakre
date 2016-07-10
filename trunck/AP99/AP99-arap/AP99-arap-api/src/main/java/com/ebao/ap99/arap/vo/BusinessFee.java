package com.ebao.ap99.arap.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.ebao.unicorn.platform.foundation.context.AppContext;

public class BusinessFee {
	private String entryCode;
	private BigDecimal amount;
	private String currencyCode;
	private Integer currentPeriod;
	private Integer totalPeriod;
	private Date dueDate;
	private boolean isEstimation = false;
	private boolean isReversal = false;
	private boolean needPost = true;
	private Long bizRefId;//Any reference id from business module
	private Long sectionId;//Business reference id of fee
	private String bizTransDesc;//Business transaction description
	private Long bizOperatorId;

	public String getEntryCode() {
		return entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Integer getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(Integer currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public Integer getTotalPeriod() {
		return totalPeriod;
	}

	public void setTotalPeriod(Integer totalPeriod) {
		this.totalPeriod = totalPeriod;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isEstimation() {
		return isEstimation;
	}

	public void setEstimation(boolean isEstimation) {
		this.isEstimation = isEstimation;
	}

	public boolean isNeedPost() {
		return needPost;
	}

	public void setNeedPost(boolean needPost) {
		this.needPost = needPost;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public String getBizTransDesc() {
		return bizTransDesc;
	}

	public void setBizTransDesc(String bizTransDesc) {
		this.bizTransDesc = bizTransDesc;
	}

	public Long getBizRefId() {
		return bizRefId;
	}

	public void setBizRefId(Long bizRefId) {
		this.bizRefId = bizRefId;
	}

	public Long getBizOperatorId() {
		return bizOperatorId;
	}

	public void setBizOperatorId(Long bizOperatorId) {
		this.bizOperatorId = bizOperatorId;
	}

	public boolean isReversal() {
		return isReversal;
	}

	public void setReversal(boolean isReversal) {
		this.isReversal = isReversal;
	}
	
}
