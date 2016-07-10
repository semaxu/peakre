package com.ebao.ap99.arap.constant;

public enum ExchangeRateType {
	GENERAL_RATE(1),
	MONTH_END_RATE(2);
	
	private Integer type;

	private ExchangeRateType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public static boolean isInvalid(Integer value) throws Exception{
		if(value != null & (value == 1 || value == 2)){
			return true;
		}
		return false;
	}
}
