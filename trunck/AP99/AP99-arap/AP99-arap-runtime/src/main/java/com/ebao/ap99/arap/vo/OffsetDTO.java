package com.ebao.ap99.arap.vo;

import java.util.Date;
import java.util.List;

public class OffsetDTO {
	private List<CreditsAndDebit> creditsAndDebit;
	private List<Balance> balances;
	private String remark;
	private String contractId;
	private String partnerCode;
	private OffsetConditionDTO condition;
	
	private Date registrationDate;
	
	public OffsetConditionDTO getCondition() {
		return condition;
	}
	public void setCondition(OffsetConditionDTO condition) {
		this.condition = condition;
	}
	public String getPartnerCode() {
		return partnerCode;
	}
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	public List<CreditsAndDebit> getCreditsAndDebit() {
		return creditsAndDebit;
	}
	public void setCreditsAndDebit(List<CreditsAndDebit> creditsAndDebit) {
		this.creditsAndDebit = creditsAndDebit;
	}
	public List<Balance> getBalances() {
		return balances;
	}
	public void setBalances(List<Balance> balances) {
		this.balances = balances;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
}
