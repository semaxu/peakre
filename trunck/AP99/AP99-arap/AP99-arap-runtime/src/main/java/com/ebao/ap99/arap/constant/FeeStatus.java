package com.ebao.ap99.arap.constant;

public enum FeeStatus {
	OUTSTANDING(0), FULLYSETTLE(1);

	private Integer type;

	private FeeStatus(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
