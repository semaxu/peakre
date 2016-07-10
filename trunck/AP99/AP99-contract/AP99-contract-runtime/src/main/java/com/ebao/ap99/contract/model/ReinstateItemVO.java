package com.ebao.ap99.contract.model;

import java.math.BigDecimal;

public class ReinstateItemVO{
	private Long itemId;

	private String reinAmount;

	private BigDecimal reinRate;

	private String reinTime;

	private String reinType;
	
	private String reinstate;
	
	private Long reinId;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getReinAmount() {
		return reinAmount;
	}

	public void setReinAmount(String reinAmount) {
		this.reinAmount = reinAmount;
	}

	public BigDecimal getReinRate() {
		return reinRate;
	}

	public void setReinRate(BigDecimal reinRate) {
		this.reinRate = reinRate;
	}

	public String getReinTime() {
		return reinTime;
	}

	public void setReinTime(String reinTime) {
		this.reinTime = reinTime;
	}

	public String getReinType() {
		return reinType;
	}

	public void setReinType(String reinType) {
		this.reinType = reinType;
	}

	public String getReinstate() {
		return reinstate;
	}

	public void setReinstate(String reinstate) {
		this.reinstate = reinstate;
	}

	public Long getReinId() {
		return reinId;
	}

	public void setReinId(Long reinId) {
		this.reinId = reinId;
	}

}
