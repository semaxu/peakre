package com.ebao.ap99.arap.constant;

public enum BankAccountStatus {
	INVALID(0),
	VALID(1),
	IN_USED(2);
	
	private Integer value;
	
	private BankAccountStatus(Integer value){
		this.setValue(value);
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
