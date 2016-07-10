package com.ebao.ap99.accounting.UI.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class SoaCurrency4UpdateVO {

    private String currencyType;
	
	
	private List<SoaSection4UpdateVO> sections = new ArrayList<SoaSection4UpdateVO>();
	
	
	private String soaCurrencyId;

	private BigDecimal amount;

	private String soaId;

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public List<SoaSection4UpdateVO> getSections() {
		return this.sections;
	}

	public void setSections(List<SoaSection4UpdateVO> sections) {
		this.sections = sections;
	}

	public String getSoaCurrencyId() {
		return soaCurrencyId;
	}

	public void setSoaCurrencyId(String soaCurrencyId) {
		this.soaCurrencyId = soaCurrencyId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getSoaId() {
		return soaId;
	}

	public void setSoaId(String soaId) {
		this.soaId = soaId;
	}
	
	
	

}
