package com.ebao.ap99.arap.constant;

public enum SettlementStatus {
	NORMAL(1), REVERSED(-1);

	private Integer type;

	private SettlementStatus(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
