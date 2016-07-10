package com.ebao.ap99.arap.constant;

public enum ArapType {
	AR(1), AP(2), OTHERS(3);
	
	private Integer type;

	private ArapType(Integer type) {
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
			case 1: return "Debit";
			case 2: return "Credit";
		}
		return null;
	}
}
