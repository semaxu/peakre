package com.ebao.ap99.arap.constant;

public enum GlAccountType {
	DEBIT("D"),
	CREDIT("C");
	
	private String value;
	
	private GlAccountType(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
