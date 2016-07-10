package com.ebao.ap99.arap.vo;

import java.math.BigDecimal;

public class SuspenseDTO {
	public Integer suspenseType;
	
	public Long contractId;
	
	public Long partnerId;
	
	public BigDecimal amount;
	
	public String currencyCode;

	public Integer getSuspenseType() {
		return suspenseType;
	}

	public void setSuspenseType(Integer suspenseType) {
		this.suspenseType = suspenseType;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	
}
