package com.ebao.ap99.contract.model;

import java.math.BigDecimal;

public class DeductionsItemVO {

	private Long deductionsItemId;
	private BigDecimal amountOurShare;
	private BigDecimal amountPercent;
	private String item;
	private BigDecimal percentage;
	private String remarks;
	private Long deductionsId;

	public Long getDeductionsId() {
		return deductionsId;
	}

	public void setDeductionsId(Long deductionsId) {
		this.deductionsId = deductionsId;
	}

	public Long getDeductionsItemId() {
		return deductionsItemId;
	}

	public void setDeductionsItemId(Long deductionsItemId) {
		this.deductionsItemId = deductionsItemId;
	}

	public BigDecimal getAmountOurShare() {
		return amountOurShare;
	}

	public void setAmountOurShare(BigDecimal amountOurShare) {
		this.amountOurShare = amountOurShare;
	}

	public BigDecimal getAmountPercent() {
		return amountPercent;
	}

	public void setAmountPercent(BigDecimal amountPercent) {
		this.amountPercent = amountPercent;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
