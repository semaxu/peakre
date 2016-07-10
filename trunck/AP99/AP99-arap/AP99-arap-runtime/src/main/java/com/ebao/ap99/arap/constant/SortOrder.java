package com.ebao.ap99.arap.constant;

public enum SortOrder {
	SORT(1),
	REVERSE(2);

	private Integer value;
	
	private SortOrder(Integer value){
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}

