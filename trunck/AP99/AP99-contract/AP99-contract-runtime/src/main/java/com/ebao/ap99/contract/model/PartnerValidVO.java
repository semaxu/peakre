package com.ebao.ap99.contract.model;

public class PartnerValidVO {
	private Boolean amlCheckFlag;
	private Boolean partnerValidFlag;
	private String result;
	
	public Boolean getAmlCheckFlag() {
		return amlCheckFlag;
	}
	public void setAmlCheckFlag(Boolean amlCheckFlag) {
		this.amlCheckFlag = amlCheckFlag;
	}
	public Boolean getPartnerValidFlag() {
		return partnerValidFlag;
	}
	public void setPartnerValidFlag(Boolean partnerValidFlag) {
		this.partnerValidFlag = partnerValidFlag;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
