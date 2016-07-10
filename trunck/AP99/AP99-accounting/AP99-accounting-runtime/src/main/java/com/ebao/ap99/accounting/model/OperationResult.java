package com.ebao.ap99.accounting.model;

import com.ebao.ap99.accounting.util.OperationStatus;

public class OperationResult {
	private Integer status = OperationStatus.SUCEESSFUL.getValue();
	
	private String message;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
