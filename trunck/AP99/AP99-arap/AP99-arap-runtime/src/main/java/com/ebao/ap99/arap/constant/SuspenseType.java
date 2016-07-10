package com.ebao.ap99.arap.constant;

public enum SuspenseType {
	CONTRACT_SUSPENSE(1),
	BUSINESS_PARTNER_SUSPENSE(2);
	
	private Integer type;

	private SuspenseType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
