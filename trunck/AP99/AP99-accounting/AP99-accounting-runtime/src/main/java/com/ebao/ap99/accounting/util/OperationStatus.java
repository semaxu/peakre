package com.ebao.ap99.accounting.util;

public enum OperationStatus {
	SUCEESSFUL(1),
	FAILED(2);
	
	private Integer value;
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
