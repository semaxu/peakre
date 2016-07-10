package com.ebao.ap99.arap.constant;

/**
 * SettlementTransactionType
 * @author terry.jiang
 *
 */
public enum FinanceTransactionType {
	COLLECTION(1), PAYMENT(2), INTERNAL_OFFSET(3), REVERSAL(4);

	private Integer type;

	private FinanceTransactionType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
