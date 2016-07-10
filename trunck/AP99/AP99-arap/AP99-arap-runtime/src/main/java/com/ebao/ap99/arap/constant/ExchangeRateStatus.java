package com.ebao.ap99.arap.constant;

public enum ExchangeRateStatus {
	INVALID(0),
	VALID(1),
	DUPLICATE(2),
	DATE_OVER_DUE(3);
	
	private Integer value;

	private ExchangeRateStatus(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer type) {
		this.value = type;
	}
}
