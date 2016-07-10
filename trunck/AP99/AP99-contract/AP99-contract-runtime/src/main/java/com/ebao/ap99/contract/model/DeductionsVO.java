package com.ebao.ap99.contract.model;

import java.math.BigDecimal;
import java.util.List;

public class DeductionsVO {

	private long contCompId;
	private String contractNature;
	private long deductionsId;
	private String deficitCarryForward;
	private BigDecimal fixedAmountHunredPercent;
	private BigDecimal fixedAmountOurShare;
	private BigDecimal numberOfYears;
	private BigDecimal percentOfPremium;
	private String profitPercentageType;
	private BigDecimal riPercentage;
	private BigDecimal profitPercentage;
	private BigDecimal expensesPercentage;
	private String rICommission;
	private String remarkPanel;
	private BigDecimal brokerage;
	private BigDecimal brokerage2;
	private List<DeductionsItemVO> otherDeductionList;
	private List<DeductionsItemVO> deductionList;

	public long getContCompId() {
		return contCompId;
	}

	public void setContCompId(long contCompId) {
		this.contCompId = contCompId;
	}

	public String getContractNature() {
		return contractNature;
	}

	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}

	public long getDeductionsId() {
		return deductionsId;
	}

	public void setDeductionsId(long deductionsId) {
		this.deductionsId = deductionsId;
	}

	public String getDeficitCarryForward() {
		return deficitCarryForward;
	}

	public void setDeficitCarryForward(String deficitCarryForward) {
		this.deficitCarryForward = deficitCarryForward;
	}

	public BigDecimal getFixedAmountHunredPercent() {
		return fixedAmountHunredPercent;
	}

	public void setFixedAmountHunredPercent(BigDecimal fixedAmountHunredPercent) {
		this.fixedAmountHunredPercent = fixedAmountHunredPercent;
	}

	public BigDecimal getFixedAmountOurShare() {
		return fixedAmountOurShare;
	}

	public void setFixedAmountOurShare(BigDecimal fixedAmountOurShare) {
		this.fixedAmountOurShare = fixedAmountOurShare;
	}

	public BigDecimal getNumberOfYears() {
		return numberOfYears;
	}

	public void setNumberOfYears(BigDecimal numberOfYears) {
		this.numberOfYears = numberOfYears;
	}

	public BigDecimal getPercentOfPremium() {
		return percentOfPremium;
	}

	public void setPercentOfPremium(BigDecimal percentOfPremium) {
		this.percentOfPremium = percentOfPremium;
	}

	public String getProfitPercentageType() {
		return profitPercentageType;
	}

	public void setProfitPercentageType(String profitPercentageType) {
		this.profitPercentageType = profitPercentageType;
	}

	public BigDecimal getRiPercentage() {
		return riPercentage;
	}

	public void setRiPercentage(BigDecimal riPercentage) {
		this.riPercentage = riPercentage;
	}

	public BigDecimal getProfitPercentage() {
		return profitPercentage;
	}

	public void setProfitPercentage(BigDecimal profitPercentage) {
		this.profitPercentage = profitPercentage;
	}

	public BigDecimal getExpensesPercentage() {
		return expensesPercentage;
	}

	public void setExpensesPercentage(BigDecimal percentage3) {
		this.expensesPercentage = percentage3;
	}

	public String getrICommission() {
		return rICommission;
	}

	public void setrICommission(String rICommission) {
		this.rICommission = rICommission;
	}

	public String getRemarkPanel() {
		return remarkPanel;
	}

	public void setRemarkPanel(String remarkPanel) {
		this.remarkPanel = remarkPanel;
	}

	public BigDecimal getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public BigDecimal getBrokerage2() {
		return brokerage2;
	}

	public void setBrokerage2(BigDecimal brokerage2) {
		this.brokerage2 = brokerage2;
	}

	public List<DeductionsItemVO> getOtherDeductionList() {
		return otherDeductionList;
	}

	public void setOtherDeductionList(List<DeductionsItemVO> otherDeductionList) {
		this.otherDeductionList = otherDeductionList;
	}

	public List<DeductionsItemVO> getDeductionList() {
		return deductionList;
	}

	public void setDeductionList(List<DeductionsItemVO> deductionList) {
		this.deductionList = deductionList;
	}
}