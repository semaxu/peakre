package com.ebao.ap99.contract.model;

import java.math.BigDecimal;

import org.restlet.engine.util.StringUtils;

public class DeductionsAttachVO {

	private Long attachmentId;
	private Long slidingId;
	private BigDecimal commission;
	private BigDecimal lrFrom;
	private BigDecimal lrTo;
	private String type;
	private String isActive;

	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public Long getSlidingId() {
		return slidingId;
	}

	public void setSlidingId(Long slidingId) {
		this.slidingId = slidingId;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getLrFrom() {
		return lrFrom;
	}

	public void setLrFrom(BigDecimal lrFrom) {
		this.lrFrom = lrFrom;
	}

	public BigDecimal getLrTo() {
		return lrTo;
	}

	public void setLrTo(BigDecimal lrTo) {
		this.lrTo = lrTo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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
}
