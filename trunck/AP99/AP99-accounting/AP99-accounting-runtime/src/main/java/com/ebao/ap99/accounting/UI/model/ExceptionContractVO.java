package com.ebao.ap99.accounting.UI.model;

import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.accounting.model.ExceptionContractRecord;

public class ExceptionContractVO {
	
	
	private List<ExceptionContractRecord> exceptionContractList = new ArrayList<ExceptionContractRecord>();


	public List<ExceptionContractRecord> getExceptionContractList() {
		return exceptionContractList;
	}

	public void setExceptionContractList(List<ExceptionContractRecord> exceptionContractList) {
		this.exceptionContractList = exceptionContractList;
	}


	
	

}
