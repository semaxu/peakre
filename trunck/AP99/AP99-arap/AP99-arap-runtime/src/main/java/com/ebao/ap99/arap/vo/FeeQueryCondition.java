package com.ebao.ap99.arap.vo;

import java.io.Serializable;
import java.util.Date;

public class FeeQueryCondition implements Serializable {

	private static final long serialVersionUID = -5429256377910777673L;

	private String partnerCode;
	private String brokerCode;
	private String contractIdArray; // like "a,b,c"
	private String statementId;
	private String claimNo;
	private String settleCurrency;
	private Integer financeTransType;
	private boolean isCalcOSAmountInSettleCurrency = false;//outstanding amount in settle currency
	private Date settleDate;
	private boolean isQueryMode = false;
	
	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getBrokerCode() {
		return brokerCode;
	}

	public void setBrokerCode(String brokerCode) {
		this.brokerCode = brokerCode;
	}

	public String getContractIdArray() {
		return contractIdArray;
	}

	public void setContractIdArray(String contractIdArray) {
		this.contractIdArray = contractIdArray;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	
	public Integer getFinanceTransType() {
		return financeTransType;
	}

	public void setFinanceTransType(Integer financeTransType) {
		this.financeTransType = financeTransType;
	}

	public String getSettleCurrency() {
		return settleCurrency;
	}

	public void setSettleCurrency(String settleCurrency) {
		this.settleCurrency = settleCurrency;
	}

	public boolean isCalcOSAmountInSettleCurrency() {
		return isCalcOSAmountInSettleCurrency;
	}

	public void setCalcOSAmountInSettleCurrency(
			boolean isCalcOSAmountInSettleCurrency) {
		this.isCalcOSAmountInSettleCurrency = isCalcOSAmountInSettleCurrency;
	}

	public Date getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	public boolean isQueryMode() {
		return isQueryMode;
	}

	public void setQueryMode(boolean isQueryMode) {
		this.isQueryMode = isQueryMode;
	}

	public String getStatementId() {
		return statementId;
	}

	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}
}
