package com.ebao.ap99.accounting.UI.model;

import java.util.ArrayList;
import java.util.List;

public class SoaCurrencyVO {
	

    private String currencyType;
	
	
	private List<SoaSectionVO> sections  = new ArrayList();
	




	public String getCurrencyType() {
		return currencyType;
	}




	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}




	public List<SoaSectionVO> getSections() {
		return this.sections;
	}




	public void setSections(List<SoaSectionVO> sections) {
		this.sections = sections;
	}




	public SoaCurrencyVO() {
		// TODO Auto-generated constructor stub
	}

}
