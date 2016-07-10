package com.ebao.ap99.arap.vo;

import com.ebao.ap99.arap.constant.OperationStatus;

public class OperationResult {
	private Integer status = OperationStatus.SUCEESSFUL.getValue();
	
	private String message = "";

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
	
	public void joinMessage(String message) {
		this.setMessage(this.getMessage().concat(" ").concat(message));
	}
	
}
