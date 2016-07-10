package com.ebao.ap99.arap.constant;


public enum SettlementExchangeSourceType {
	SETTLE_DTL_MARKOFF(1), 
	SETTLE_DTL_AMOUNT(2), 
	SETTLE_DTL_GAINLOSS(3), 
	SETTLE_DTL_GAINLOSS_DIFF(4),
	SETTLE_DTL_SUSPENSE(5),
	SETTLE_DTL_SETTLE_DIFF(6),
	SETTLE_DTL_REVALUATION(7);
	
	private Integer type;
	private static Integer[] allType = {1,2,3,4,5,6};

	private SettlementExchangeSourceType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public static Integer[] getAll(){
		return allType;
	}
}
