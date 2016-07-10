package com.ebao.ap99.arap.constant;

public enum PayModeType {
	ADVICE(1), CHEQUE(2), BANK_TRANSFER(3);

	private Integer type;

	private PayModeType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public static String getName(Integer modeType){
		switch(modeType){
			case 1: return "Advice";
			case 2: return "Cheque";
			case 3: return "Bank Transfer";
		}
		return null;
	}
}
