package com.ebao.ap99.arap.constant;

public enum ContractCategory {
	ASSUMED(1), RETRO(2);

	private Integer type;

	private ContractCategory(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
