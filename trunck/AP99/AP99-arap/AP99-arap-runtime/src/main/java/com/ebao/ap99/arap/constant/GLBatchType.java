package com.ebao.ap99.arap.constant;

public enum GLBatchType {
	SUB_LEDGER_GENERATION(1),
	GENERAL_LEDGER_GENERATION(2);
	
	private Integer value;
	
	private GLBatchType(Integer value){
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
}
