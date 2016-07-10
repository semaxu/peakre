package com.ebao.ap99.arap.constant;

public enum YesNoType {
	YES(1), NO(0);
	
	private Integer type;

	private YesNoType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
