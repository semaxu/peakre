package com.ebao.ap99.arap.constant;


public enum GLCashBalanceType {
	ARAP(0), 
	NORMAL(1),
	INTERNAL_OFFSET(2),
	BALANCE_GENERATION(3),
	BALANCE_REFUND(4);

	private Integer value;

	private GLCashBalanceType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
