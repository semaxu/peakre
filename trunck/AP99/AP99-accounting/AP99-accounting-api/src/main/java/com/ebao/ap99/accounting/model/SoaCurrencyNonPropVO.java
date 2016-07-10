package com.ebao.ap99.accounting.model;

import java.util.ArrayList;
import java.util.List;


public class SoaCurrencyNonPropVO {

    private String currencyType;
	
	private List<SoaSectionNonPropVO> sections = new ArrayList<SoaSectionNonPropVO>();
	

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public List<SoaSectionNonPropVO> getSections() {
		return this.sections;
	}

	public void setSections(List<SoaSectionNonPropVO> sections) {
		this.sections = sections;
	}




}
