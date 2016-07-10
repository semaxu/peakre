package com.ebao.ap99.arap.constant;

import java.math.BigDecimal;

public enum FeeSign {
	POSITIVE(1), NEGATIVE(-1);

	private Integer type;

	private FeeSign(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public static String getDCName(BigDecimal amount){
		return amount.compareTo(BigDecimal.ZERO)>=0?"DN":"CN";
	}
}
