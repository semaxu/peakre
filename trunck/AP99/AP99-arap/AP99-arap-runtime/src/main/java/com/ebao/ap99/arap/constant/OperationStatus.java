package com.ebao.ap99.arap.constant;

public enum OperationStatus {
	FAILED(0),
	SUCEESSFUL(1),
	FAILED_DATA_EXISTS(2),
	FAILED_PERIOD_EXISTS(3),
	FAILED_NOT_NULL(4),
	FAILED_IN_USED(5);
	
	private Integer value = 1;
	private OperationStatus(Integer value){
		this.value = value;
	}
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getValue(){
		return value;
	}
}
