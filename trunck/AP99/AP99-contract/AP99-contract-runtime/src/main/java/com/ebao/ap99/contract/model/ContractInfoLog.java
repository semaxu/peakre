/**
 * 
 */
package com.ebao.ap99.contract.model;

import java.math.BigDecimal;

/**
 * @author weiping.wang
 *
 */
public class ContractInfoLog {

	private BigDecimal contCompId;
	private BigDecimal operateId;
	private String linkId;
	private String renewalId;
	private String contractCode;
	private String contractName;
	private String brokerRef;
	private String cedentRef;
	private String mainclass;
	private String subclass;
	private String latestStatus;
	private String contractType;
	private String contractNature;
	private String contractCategory;
	private String fronting;
	private String depositAccounting;
	private String reinsStarting;
	private String reinsEnding;
	private BigDecimal uwYear;
	private String inforceDate;
	private BigDecimal cedent;
	private String cedentCountry;
	private BigDecimal mga;
	private BigDecimal broker;
	private BigDecimal coBroker;
	private String insured;
	private String reviewed;
	private BigDecimal underwriting;
	private String analyticsResp;
	private String marketResp;
	private BigDecimal createBy;
	private String createOn;
	private BigDecimal treatyOwner;
	private BigDecimal lastChanged;
	private String lastChangedOn;
	private BigDecimal uwCompany;
	private String remark;
	// private List<ReserveInfo> reserveList;
	// private List<SettlementInfo> settlementList;

	public String getLinkId() {
		return linkId;
	}

	public BigDecimal getContCompId() {
		return contCompId;
	}

	public void setContCompId(BigDecimal contCompId) {
		this.contCompId = contCompId;
	}

	public BigDecimal getOperateId() {
		return operateId;
	}

	public void setOperateId(BigDecimal operateId) {
		this.operateId = operateId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public String getRenewalId() {
		return renewalId;
	}

	public void setRenewalId(String renewalId) {
		this.renewalId = renewalId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getBrokerRef() {
		return brokerRef;
	}

	public void setBrokerRef(String brokerRef) {
		this.brokerRef = brokerRef;
	}

	public String getCedentRef() {
		return cedentRef;
	}

	public void setCedentRef(String cedentRef) {
		this.cedentRef = cedentRef;
	}

	public String getMainclass() {
		return mainclass;
	}

	public void setMainclass(String mainclass) {
		this.mainclass = mainclass;
	}

	public String getSubclass() {
		return subclass;
	}

	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}

	public String getLatestStatus() {
		return latestStatus;
	}

	public void setLatestStatus(String latestStatus) {
		this.latestStatus = latestStatus;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractNature() {
		return contractNature;
	}

	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}

	public String getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(String contractCategory) {
		this.contractCategory = contractCategory;
	}

	public String getFronting() {
		return fronting;
	}

	public void setFronting(String fronting) {
		this.fronting = fronting;
	}

	public String getDepositAccounting() {
		return depositAccounting;
	}

	public void setDepositAccounting(String depositAccounting) {
		this.depositAccounting = depositAccounting;
	}

	public String getReinsStarting() {
		return reinsStarting;
	}

	public void setReinsStarting(String reinsStarting) {
		this.reinsStarting = reinsStarting;
	}

	public String getReinsEnding() {
		return reinsEnding;
	}

	public void setReinsEnding(String reinsEnding) {
		this.reinsEnding = reinsEnding;
	}

	public BigDecimal getUwYear() {
		return uwYear;
	}

	public void setUwYear(BigDecimal uwYear) {
		this.uwYear = uwYear;
	}

	public String getInforceDate() {
		return inforceDate;
	}

	public void setInforceDate(String inforceDate) {
		this.inforceDate = inforceDate;
	}

	public BigDecimal getCedent() {
		return cedent;
	}

	public void setCedent(BigDecimal cedent) {
		this.cedent = cedent;
	}

	public String getCedentCountry() {
		return cedentCountry;
	}

	public void setCedentCountry(String cedentCountry) {
		this.cedentCountry = cedentCountry;
	}

	public BigDecimal getMga() {
		return mga;
	}

	public void setMga(BigDecimal mga) {
		this.mga = mga;
	}

	public BigDecimal getBroker() {
		return broker;
	}

	public void setBroker(BigDecimal broker) {
		this.broker = broker;
	}

	public BigDecimal getCoBroker() {
		return coBroker;
	}

	public void setCoBroker(BigDecimal coBroker) {
		this.coBroker = coBroker;
	}

	public String getInsured() {
		return insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	public String getReviewed() {
		return reviewed;
	}

	public void setReviewed(String reviewed) {
		this.reviewed = reviewed;
	}

	public BigDecimal getUnderwriting() {
		return underwriting;
	}

	public void setUnderwriting(BigDecimal underwriting) {
		this.underwriting = underwriting;
	}

	public String getAnalyticsResp() {
		return analyticsResp;
	}

	public void setAnalyticsResp(String analyticsResp) {
		this.analyticsResp = analyticsResp;
	}

	public String getMarketResp() {
		return marketResp;
	}

	public void setMarketResp(String marketResp) {
		this.marketResp = marketResp;
	}

	public BigDecimal getCreateBy() {
		return createBy;
	}

	public void setCreateBy(BigDecimal createBy) {
		this.createBy = createBy;
	}

	public String getCreateOn() {
		return createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

	public BigDecimal getTreatyOwner() {
		return treatyOwner;
	}

	public void setTreatyOwner(BigDecimal treatyOwner) {
		this.treatyOwner = treatyOwner;
	}

	public BigDecimal getLastChanged() {
		return lastChanged;
	}

	public void setLastChanged(BigDecimal lastChanged) {
		this.lastChanged = lastChanged;
	}

	public String getLastChangedOn() {
		return lastChangedOn;
	}

	public void setLastChangedOn(String lastChangedOn) {
		this.lastChangedOn = lastChangedOn;
	}

	public BigDecimal getUwCompany() {
		return uwCompany;
	}

	public void setUwCompany(BigDecimal uwCompany) {
		this.uwCompany = uwCompany;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
