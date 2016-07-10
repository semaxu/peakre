package com.ebao.ap99.contract.model;

import java.math.BigDecimal;

public class DeductionsCarriedVO {
	private Long carriedForwardsId;
	private Long commSlidingDetailId;
	private String extinction;
	private String name;
	private BigDecimal years;
	private String yn;

	public Long getCarriedForwardsId() {
		return carriedForwardsId;
	}

	public void setCarriedForwardsId(Long carriedForwardsId) {
		this.carriedForwardsId = carriedForwardsId;
	}

	public Long getCommSlidingDetailId() {
		return commSlidingDetailId;
	}

	public void setCommSlidingDetailId(Long commSlidingDetailId) {
		this.commSlidingDetailId = commSlidingDetailId;
	}

	public String getExtinction() {
		return extinction;
	}

	public void setExtinction(String extinction) {
		this.extinction = extinction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getYears() {
		return years;
	}

	public void setYears(BigDecimal years) {
		this.years = years;
	}

	public String getYn() {
		return yn;
	}

	public void setYn(String yn) {
		this.yn = yn;
	}
}
