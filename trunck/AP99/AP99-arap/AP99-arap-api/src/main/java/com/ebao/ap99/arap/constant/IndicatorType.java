package com.ebao.ap99.arap.constant;

public enum IndicatorType {
	ESTIMATION(1)
	,ACTUAL(2)
	,REVERSAL(3);

	private Integer value;
	
	private IndicatorType(Integer value){
		this.setValue(value);
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
