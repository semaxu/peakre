package com.ebao.ap99.arap.vo;

import java.util.List;

public class BusinessFeeModel {
	private Integer contractCategory;//ContractCategory
	private Integer bizTransType;//BizTransactionType
	private String bizTransNo;
	private Long bizTransId;
	private boolean isSpecialSubmitInCutoffPeriod = false;
	private Long contractId;
	private String partnerCode;
	private List<BusinessFee> feeList;
	private Long bizOperatorId;
	
	public Integer getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(Integer contractCategory) {
		this.contractCategory = contractCategory;
	}

	public Integer getBizTransType() {
		return bizTransType;
	}

	public void setBizTransType(Integer bizTransType) {
		this.bizTransType = bizTransType;
	}

	public String getBizTransNo() {
		return bizTransNo;
	}

	public void setBizTransNo(String bizTransNo) {
		this.bizTransNo = bizTransNo;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public List<BusinessFee> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<BusinessFee> feeList) {
		this.feeList = feeList;
	}

	public Long getContractId() {
		return contractId;
	}

	public boolean isSpecialSubmitInCutoffPeriod() {
		return isSpecialSubmitInCutoffPeriod;
	}

	public void setSpecialSubmitInCutoffPeriod(boolean isSpecialSubmitInCutoffPeriod) {
		this.isSpecialSubmitInCutoffPeriod = isSpecialSubmitInCutoffPeriod;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public Long getBizTransId() {
		return bizTransId;
	}

	public void setBizTransId(Long bizTransId) {
		this.bizTransId = bizTransId;
	}

	public Long getBizOperatorId() {
		return bizOperatorId;
	}

	public void setBizOperatorId(Long bizOperatorId) {
		this.bizOperatorId = bizOperatorId;
	}
}
