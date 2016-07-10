package com.ebao.ap99.accounting.model;

import java.math.BigDecimal;

public class RevaluateQuarterInfoModel {
	
	
	private String FNQuarter;

	private BigDecimal revaluateValue = BigDecimal.ZERO;

	public String getFNQuarter() {
		return FNQuarter;
	}

	public void setFNQuarter(String fNQuarter) {
		FNQuarter = fNQuarter;
	}

	public BigDecimal getRevaluateValue() {
		return this.revaluateValue;
	}

	public void setRevaluateValue(BigDecimal revaluateValue) {
		this.revaluateValue = revaluateValue;
	}


	
	

}
