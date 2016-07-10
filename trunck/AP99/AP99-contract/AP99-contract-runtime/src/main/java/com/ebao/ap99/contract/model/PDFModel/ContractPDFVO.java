/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.model.PDFModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Date: Mar 22, 2016 4:15:08 PM
 * 
 * @author weiping.wang
 *
 */
public class ContractPDFVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Treaty number
	private String contractCode;
	// Treaty name
	private String contractName;
	// Treaty Status
	private String latestStatus;
	// Nature of Treaty
	private String contractNature;
	// Line of Business
	private String lineOfBusiness;
	// Currency
	private String mainCurrency;
	// Cedent No.
	private String cedent;
	// Cedent Name
	private String cedentName;
	// Broker No.
	private String broker;
	// Broker Name
	private String brokerName;
	// Underwriting Year
	private Long uwYear;
	// Period Start Date
	private Date reinsStarting;
	// Period End Date
	private Date reinsEnding;
	// Period Duration
	private String periodDuration;
	// Treaty Termination Date
	private Date terminatedDate;
	// Accounting Mode
	private String accountingBasis;
	// Accounting Frequency
	private String accountFrequency;
	// First Accounting Key Date
	private Date firstAccountAsAtDate;
	// End of Accounting Year
	private String accoutingYearEnd;
	// Referencing Treaty
	private String referTreaty;
	// Appian No.
	private String appianNo;
	// Broker Reference
	private String brokerRef;
	// Cedent Reference
	private String cedentRef;
	// Pricing Reference (TWS)
	private String pricingRef;
	// Market Responsible
	private String marketResp;
	// Analytics Responsible
	private String analyticsResp;
	// Underwriting Responsible
	private String underwritingResp;
	// Treaty Responsible
	private String treatyResp;
	// Created By
	private String createdBy;
	// Created On
	private Date createdOn;
	// Last Changed By
	private String lastChanged;
	// Last Changed On
	private Date lastChangedOn;
	// Contract Type
	private String contractType;
	// Contract Category
	private String contractCategory;
	// Co-Broker
	private String coBroker;
	// Insured
	private String insured;
	// Cedent Country
	private String cedentCountry;
	// Main CoB
	private String mainclass;
	// Main Sub CoB
	private String subclass;
	// Deposit%
	private String depositPer;
	// Broker Country
	private String brokerCountry;

	// Treaty Area
	private List<AreaVO> areaList = new ArrayList<AreaVO>();
	// Treaty Clause
	private List<String> clauseList = new ArrayList<String>();
	// Attachment
	private List<AttachmentVO> attachmentList = new ArrayList<AttachmentVO>();
	// Forecasting & Estimates
	private List<ForecastEstimateVO> forcastEstimateList = new ArrayList<ForecastEstimateVO>();
	// Result Dependent Conditions
	private List<ResultConditionDepVO> resultConditionDepList = new ArrayList<ResultConditionDepVO>();
	// Result Independent Conditions
	private List<ResultConditionIndepVO> resultConditionIndepList = new ArrayList<ResultConditionIndepVO>();
	// Premium and Limits
	private List<PremiumLimitsVO> premiumLimitsList = new ArrayList<PremiumLimitsVO>();
	// Perils and Limits
	private List<PerilsLimitsVO> perilsLimitsList = new ArrayList<PerilsLimitsVO>();
	// Installment Dates
	private List<InstallmentDatesVO> installmentDatesList = new ArrayList<InstallmentDatesVO>();
	// Limits and Deductibles
	private List<LimitsDeductiblesVO> limitsDeductiblesList = new ArrayList<LimitsDeductiblesVO>();
	// Premiums
	private List<PremiumsVO> premiumsList = new ArrayList<PremiumsVO>();

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

	public String getLatestStatus() {
		return latestStatus;
	}

	public void setLatestStatus(String latestStatus) {
		this.latestStatus = latestStatus;
	}

	public String getContractNature() {
		return contractNature;
	}

	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}

	public String getLineOfBusiness() {
		return lineOfBusiness;
	}

	public void setLineOfBusiness(String lineOfBusiness) {
		this.lineOfBusiness = lineOfBusiness;
	}

	public String getMainCurrency() {
		return mainCurrency;
	}

	public void setMainCurrency(String mainCurrency) {
		this.mainCurrency = mainCurrency;
	}

	public String getCedent() {
		return cedent;
	}

	public void setCedent(String cedent) {
		this.cedent = cedent;
	}

	public String getCedentName() {
		return cedentName;
	}

	public void setCedentName(String cedentName) {
		this.cedentName = cedentName;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public Long getUwYear() {
		return uwYear;
	}

	public void setUwYear(Long uwYear) {
		this.uwYear = uwYear;
	}

	public Date getReinsStarting() {
		return reinsStarting;
	}

	public void setReinsStarting(Date reinsStarting) {
		this.reinsStarting = reinsStarting;
	}

	public Date getReinsEnding() {
		return reinsEnding;
	}

	public void setReinsEnding(Date reinsEnding) {
		this.reinsEnding = reinsEnding;
	}

	public String getPeriodDuration() {
		return periodDuration;
	}

	public void setPeriodDuration(String periodDuration) {
		this.periodDuration = periodDuration;
	}

	public Date getTerminatedDate() {
		return terminatedDate;
	}

	public void setTerminatedDate(Date terminatedDate) {
		this.terminatedDate = terminatedDate;
	}

	public String getAccountingBasis() {
		return accountingBasis;
	}

	public void setAccountingBasis(String accountingBasis) {
		this.accountingBasis = accountingBasis;
	}

	public String getAccountFrequency() {
		return accountFrequency;
	}

	public void setAccountFrequency(String accountFrequency) {
		this.accountFrequency = accountFrequency;
	}

	public Date getFirstAccountAsAtDate() {
		return firstAccountAsAtDate;
	}

	public void setFirstAccountAsAtDate(Date firstAccountAsAtDate) {
		this.firstAccountAsAtDate = firstAccountAsAtDate;
	}

	public String getAccoutingYearEnd() {
		return accoutingYearEnd;
	}

	public void setAccoutingYearEnd(String accoutingYearEnd) {
		this.accoutingYearEnd = accoutingYearEnd;
	}

	public String getReferTreaty() {
		return referTreaty;
	}

	public void setReferTreaty(String referTreaty) {
		this.referTreaty = referTreaty;
	}

	public String getAppianNo() {
		return appianNo;
	}

	public void setAppianNo(String appianNo) {
		this.appianNo = appianNo;
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

	public String getPricingRef() {
		return pricingRef;
	}

	public void setPricingRef(String pricingRef) {
		this.pricingRef = pricingRef;
	}

	public String getMarketResp() {
		return marketResp;
	}

	public void setMarketResp(String marketResp) {
		this.marketResp = marketResp;
	}

	public String getAnalyticsResp() {
		return analyticsResp;
	}

	public void setAnalyticsResp(String analyticsResp) {
		this.analyticsResp = analyticsResp;
	}

	public String getUnderwritingResp() {
		return underwritingResp;
	}

	public void setUnderwritingResp(String underwritingResp) {
		this.underwritingResp = underwritingResp;
	}

	public String getTreatyResp() {
		return treatyResp;
	}

	public void setTreatyResp(String treatyResp) {
		this.treatyResp = treatyResp;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getLastChanged() {
		return lastChanged;
	}

	public void setLastChanged(String lastChanged) {
		this.lastChanged = lastChanged;
	}

	public Date getLastChangedOn() {
		return lastChangedOn;
	}

	public void setLastChangedOn(Date lastChangedOn) {
		this.lastChangedOn = lastChangedOn;
	}

	public List<AreaVO> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<AreaVO> areaList) {
		this.areaList = areaList;
	}

	public List<String> getClauseList() {
		return clauseList;
	}

	public void setClauseList(List<String> clauseList) {
		this.clauseList = clauseList;
	}

	public List<AttachmentVO> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<AttachmentVO> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<ForecastEstimateVO> getForcastEstimateList() {
		return forcastEstimateList;
	}

	public void setForcastEstimateList(List<ForecastEstimateVO> forcastEstimateList) {
		this.forcastEstimateList = forcastEstimateList;
	}

	public List<ResultConditionDepVO> getResultConditionDepList() {
		return resultConditionDepList;
	}

	public void setResultConditionDepList(List<ResultConditionDepVO> resultConditionDepList) {
		this.resultConditionDepList = resultConditionDepList;
	}

	public List<ResultConditionIndepVO> getResultConditionIndepList() {
		return resultConditionIndepList;
	}

	public void setResultConditionIndepList(List<ResultConditionIndepVO> resultConditionIndepList) {
		this.resultConditionIndepList = resultConditionIndepList;
	}

	public List<PremiumLimitsVO> getPremiumLimitsList() {
		return premiumLimitsList;
	}

	public void setPremiumLimitsList(List<PremiumLimitsVO> premiumLimitsList) {
		this.premiumLimitsList = premiumLimitsList;
	}

	public List<PerilsLimitsVO> getPerilsLimitsList() {
		return perilsLimitsList;
	}

	public void setPerilsLimitsList(List<PerilsLimitsVO> perilsLimitsList) {
		this.perilsLimitsList = perilsLimitsList;
	}

	public List<InstallmentDatesVO> getInstallmentDatesList() {
		return installmentDatesList;
	}

	public void setInstallmentDatesList(List<InstallmentDatesVO> installmentDatesList) {
		this.installmentDatesList = installmentDatesList;
	}

	public List<LimitsDeductiblesVO> getLimitsDeductiblesList() {
		return limitsDeductiblesList;
	}

	public void setLimitsDeductiblesList(List<LimitsDeductiblesVO> limitsDeductiblesList) {
		this.limitsDeductiblesList = limitsDeductiblesList;
	}

	public List<PremiumsVO> getPremiumsList() {
		return premiumsList;
	}

	public void setPremiumsList(List<PremiumsVO> premiumsList) {
		this.premiumsList = premiumsList;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(String contractCategory) {
		this.contractCategory = contractCategory;
	}

	public String getCoBroker() {
		return coBroker;
	}

	public void setCoBroker(String coBroker) {
		this.coBroker = coBroker;
	}

	public String getInsured() {
		return insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	public String getCedentCountry() {
		return cedentCountry;
	}

	public void setCedentCountry(String cedentCountry) {
		this.cedentCountry = cedentCountry;
	}

	public String getSubclass() {
		return subclass;
	}

	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}

	public String getDepositPer() {
		return depositPer;
	}

	public void setDepositPer(String depositPer) {
		this.depositPer = depositPer;
	}

	public String getBrokerCountry() {
		return brokerCountry;
	}

	public void setBrokerCountry(String brokerCountry) {
		this.brokerCountry = brokerCountry;
	}

	public String getMainclass() {
		return mainclass;
	}

	public void setMainclass(String mainclass) {
		this.mainclass = mainclass;
	}

}
