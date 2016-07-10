package com.ebao.ap99.arap.constant;

public enum ReverseStatus {
	SUCCESSFUL(1),
	FAIL_DEPENSE_PREVIOUS(2);

	private Integer value;
	
	private ReverseStatus(Integer value){
		this.setValue(value);
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
