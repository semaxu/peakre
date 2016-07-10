package com.ebao.ap99.arap.constant;

public enum BizTransactionSubmitType {
	NORMAL(1),
	SPECIAL_SUBMIT(2);
	
	private Integer value;
	
	private BizTransactionSubmitType(Integer value){
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
}
