/**
 * 
 */
package com.ebao.ap99.contract.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ebao.ap99.parent.DataTypeConvertor;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author weiping.wang
 */
public class ContractVO {

	// Basic Contract Info
	private Long contCompId;
	private Long endoId;
	private String linkName;
	private Long renewalId;
	private String contractCode;
	private String contractName;
	private String brokerRef;
	private String cedentRef;
	private String mainclass;
	private String subclass;
	private String latestStatus;
	private String beforeStatus;
	private String contractType;
	private String contractNature;
	private String contractCategory;
	private String fronting;
	private String depositAccounting;
	private Date reinsStarting;
	private Date reinsEnding;
	private Long uwYear;
	private Date inforceDate;
	private String cedent;
	private String cedentCountry;
	private String mga;
	private String broker;
	private String coBroker;
	private String insured;
	private String reviewed;
	private Long underwriting;
	private Long analyticsResp;
	private Long marketResp;
	private Long createdBy;
	private Date createdOn;
	private Long treatyOwner;
	private Long lastChanged;
	private Date lastChangedOn;
	private Long uwCompany;
	private String remark;
	private Long operateId;
	private Long documentId;
	private String protectionBasic;
	private String mainCurrency;
	private String appianNo;
	private String pricingRef;
	private String leader;
	private String leaderName;
	private String portfolioTransfer;
	private String rejectReason;
	private String endoType;
	private String attachFilePath;
	private String mainCoverArea;

	// More Contract Info
	private List<String> coBList = new ArrayList<String>();
	private List<String> loBList = new ArrayList<String>();
	private List<String> currencyList = new ArrayList<String>();
	private List<String> uwAreaList = new ArrayList<String>();
	private List<String> coveredAreaList = new ArrayList<String>();
	@JsonIgnore
	private String coB;
	@JsonIgnore
	private String loB;
	@JsonIgnore
	private String currency;
	@JsonIgnore
	private String uwArea;
	@JsonIgnore
	private String coveredArea;

	// Area & Peril
	private List<String> coveredList = new ArrayList<String>();
	@JsonIgnore
	private String covered;
	private String coveredRemark;
	private List<String> perilList = new ArrayList<String>();
	@JsonIgnore
	private String peril;
	private String perilRemark;

	// Accounting Conditions
	private String accountingBasis;
	private String earningPattern;
	private String accountFrequency;
	private Date firstAccountAsAtDate;
	private Date dueDate;
	private BigDecimal percentageOfPremium;
	private Date dateForReview;
	private String portfolioIn;
	private BigDecimal portfolioInData;
	private String portfolioOut;
	private BigDecimal portfolioOutData;
	private BigDecimal premiumIn;
	private BigDecimal premiumOut;
	private BigDecimal outstandingLossesIn;
	private BigDecimal outstandingLossesOut;

	private BigDecimal accountDays;
	private BigDecimal settlementDays;
	private String accountRemark;

	// Claim Conditions
	private String claimBasis;
	private String cashCallAdvice;
	private String claimCurrency;
	private String lossAdvice;
	private Long noOfDay;
	private String posting;
	private Date retroactiveDate;
	private String specify;
	private String sunsetCheck;
	private Date sunsetClause;
	private List<String> pricesIndexList = new ArrayList<String>();
	private List<String> wetherIndexList = new ArrayList<String>();
	@JsonIgnore
	private String pricesIndex;
	@JsonIgnore
	private String wetherIndex;
	private List<ContractClaimConditionItem> contractClaimConditionItemList = new ArrayList<ContractClaimConditionItem>();

	// Cancellation
	private Long cancelId;
	private BigDecimal dnocPoliticalDay;
	private BigDecimal dnocPoliticalMonth;
	private BigDecimal dnocSanctionDay;
	private BigDecimal dnocSanctionMonth;
	private BigDecimal dnocWarDay;
	private BigDecimal dnocWarMonth;
	private String pnocAutomatic;
	private BigDecimal pnocCedentDay;
	private BigDecimal pnocCedentMonth;
	private BigDecimal pnocReinsurerDay;
	private BigDecimal pnocReinsurerMonth;

	// Section table
	private List<ContractSectionVO> sectionList = new ArrayList<ContractSectionVO>();
	private List<Long> deleteSectionList = new ArrayList<Long>();
	private List<ContractStatusLogVO> logList = new ArrayList<ContractStatusLogVO>();

	// Link List
	private List<String> linkDefineList = new ArrayList<String>();
	private List<LinkContractVO> linkList = new ArrayList<LinkContractVO>();

	private int pageIndex;

	private int countPerPage;

	private String operateType;

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public Long getEndoId() {
		return endoId;
	}

	public void setEndoId(Long endoId) {
		this.endoId = endoId;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public Long getRenewalId() {
		return renewalId;
	}

	public void setRenewalId(Long renewalId) {
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

	public String getBeforeStatus() {
		return beforeStatus;
	}

	public void setBeforeStatus(String beforeStatus) {
		this.beforeStatus = beforeStatus;
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

	public Long getUwYear() {
		return uwYear;
	}

	public void setUwYear(Long uwYear) {
		this.uwYear = uwYear;
	}

	public Date getInforceDate() {
		return inforceDate;
	}

	public void setInforceDate(Date inforceDate) {
		this.inforceDate = inforceDate;
	}

	public String getCedent() {
		return cedent;
	}

	public void setCedent(String cedent) {
		this.cedent = cedent;
	}

	public String getCedentCountry() {
		return cedentCountry;
	}

	public void setCedentCountry(String cedentCountry) {
		this.cedentCountry = cedentCountry;
	}

	public String getMga() {
		return mga;
	}

	public void setMga(String mga) {
		this.mga = mga;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
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

	public String getReviewed() {
		return reviewed;
	}

	public void setReviewed(String reviewed) {
		this.reviewed = reviewed;
	}

	public Long getUnderwriting() {
		return underwriting;
	}

	public void setUnderwriting(Long underwriting) {
		this.underwriting = underwriting;
	}

	public Long getAnalyticsResp() {
		return analyticsResp;
	}

	public void setAnalyticsResp(Long analyticsResp) {
		this.analyticsResp = analyticsResp;
	}

	public Long getMarketResp() {
		return marketResp;
	}

	public void setMarketResp(Long marketResp) {
		this.marketResp = marketResp;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getTreatyOwner() {
		return treatyOwner;
	}

	public void setTreatyOwner(Long treatyOwner) {
		this.treatyOwner = treatyOwner;
	}

	public Long getLastChanged() {
		return lastChanged;
	}

	public void setLastChanged(Long lastChanged) {
		this.lastChanged = lastChanged;
	}

	public Date getLastChangedOn() {
		return lastChangedOn;
	}

	public void setLastChangedOn(Date lastChangedOn) {
		this.lastChangedOn = lastChangedOn;
	}

	public Long getUwCompany() {
		return uwCompany;
	}

	public void setUwCompany(Long uwCompany) {
		this.uwCompany = uwCompany;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public List<String> getCoBList() {
		return coBList;
	}

	public void setCoBList(List<String> coBList) {
		this.coB = DataTypeConvertor.parseSelectTreeToString(coBList);
		this.coBList = coBList;
	}

	public List<String> getLoBList() {
		return loBList;
	}

	public void setLoBList(List<String> loBList) {
		this.loB = DataTypeConvertor.parseSelectTreeToString(loBList);
		this.loBList = loBList;
	}

	public List<String> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<String> currencyList) {
		this.currency = DataTypeConvertor.parseSelectTreeToString(currencyList);
		this.currencyList = currencyList;
	}

	public List<String> getUwAreaList() {
		return uwAreaList;
	}

	public void setUwAreaList(List<String> uwAreaList) {
		this.uwArea = DataTypeConvertor.parseSelectTreeToString(uwAreaList);
		this.uwAreaList = uwAreaList;
	}

	public List<String> getCoveredAreaList() {
		return coveredAreaList;
	}

	public void setCoveredAreaList(List<String> coveredAreaList) {
		this.coveredArea = DataTypeConvertor.parseSelectTreeToString(coveredAreaList);
		this.coveredAreaList = coveredAreaList;
	}

	public String getProtectionBasic() {
		return protectionBasic;
	}

	public void setProtectionBasic(String protectionBasic) {
		this.protectionBasic = protectionBasic;
	}

	public List<String> getCoveredList() {
		return coveredList;
	}

	public void setCoveredList(List<String> coveredList) {
		this.covered = DataTypeConvertor.parseSelectTreeToString(coveredList);
		this.coveredList = coveredList;
	}

	public String getCoveredRemark() {
		return coveredRemark;
	}

	public void setCoveredRemark(String coveredRemark) {
		this.coveredRemark = coveredRemark;
	}

	public List<String> getPerilList() {
		return perilList;
	}

	public void setPerilList(List<String> perilList) {
		this.peril = DataTypeConvertor.parseSelectTreeToString(perilList);
		this.perilList = perilList;
	}

	public String getPerilRemark() {
		return perilRemark;
	}

	public void setPerilRemark(String perilRemark) {
		this.perilRemark = perilRemark;
	}

	public String getAccountingBasis() {
		return accountingBasis;
	}

	public BigDecimal getPremiumIn() {
		return premiumIn;
	}

	public void setPremiumIn(BigDecimal premiumIn) {
		this.premiumIn = premiumIn;
	}

	public BigDecimal getPremiumOut() {
		return premiumOut;
	}

	public void setPremiumOut(BigDecimal premiumOut) {
		this.premiumOut = premiumOut;
	}

	public BigDecimal getOutstandingLossesIn() {
		return outstandingLossesIn;
	}

	public void setOutstandingLossesIn(BigDecimal outstandingLossesIn) {
		this.outstandingLossesIn = outstandingLossesIn;
	}

	public BigDecimal getOutstandingLossesOut() {
		return outstandingLossesOut;
	}

	public void setOutstandingLossesOut(BigDecimal outstandingLossesOut) {
		this.outstandingLossesOut = outstandingLossesOut;
	}

	public void setAccountingBasis(String accountingBasis) {
		this.accountingBasis = accountingBasis;
	}

	public String getEarningPattern() {
		return earningPattern;
	}

	public void setEarningPattern(String earningPattern) {
		this.earningPattern = earningPattern;
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

	public BigDecimal getPercentageOfPremium() {
		return percentageOfPremium;
	}

	public void setPercentageOfPremium(BigDecimal percentageOfPremium) {
		this.percentageOfPremium = percentageOfPremium;
	}

	public Date getDateForReview() {
		return dateForReview;
	}

	public void setDateForReview(Date dateForReview) {
		this.dateForReview = dateForReview;
	}

	public String getClaimBasis() {
		return claimBasis;
	}

	public void setClaimBasis(String claimBasis) {
		this.claimBasis = claimBasis;
	}

	public String getCashCallAdvice() {
		return cashCallAdvice;
	}

	public void setCashCallAdvice(String cashCallAdvice) {
		this.cashCallAdvice = cashCallAdvice;
	}

	public String getClaimCurrency() {
		return claimCurrency;
	}

	public void setClaimCurrency(String claimCurrency) {
		this.claimCurrency = claimCurrency;
	}

	public String getLossAdvice() {
		return lossAdvice;
	}

	public void setLossAdvice(String lossAdvice) {
		this.lossAdvice = lossAdvice;
	}

	public Long getNoOfDay() {
		return noOfDay;
	}

	public void setNoOfDay(Long noOfDay) {
		this.noOfDay = noOfDay;
	}

	public String getPosting() {
		return posting;
	}

	public void setPosting(String posting) {
		this.posting = posting;
	}

	public List<String> getPricesIndexList() {
		return pricesIndexList;
	}

	public void setPricesIndexList(List<String> pricesIndexList) {
		this.pricesIndex = DataTypeConvertor.parseSelectTreeToString(pricesIndexList);
		this.pricesIndexList = pricesIndexList;
	}

	public Date getRetroactiveDate() {
		return retroactiveDate;
	}

	public void setRetroactiveDate(Date retroactiveDate) {
		this.retroactiveDate = retroactiveDate;
	}

	public String getSpecify() {
		return specify;
	}

	public void setSpecify(String specify) {
		this.specify = specify;
	}

	public String getSunsetCheck() {
		return sunsetCheck;
	}

	public void setSunsetCheck(String sunsetCheck) {
		this.sunsetCheck = sunsetCheck;
	}

	public Date getSunsetClause() {
		return sunsetClause;
	}

	public void setSunsetClause(Date sunsetClause) {
		this.sunsetClause = sunsetClause;
	}

	public List<String> getWetherIndexList() {
		return wetherIndexList;
	}

	public void setWetherIndexList(List<String> wetherIndexList) {
		this.wetherIndex = DataTypeConvertor.parseSelectTreeToString(wetherIndexList);
		this.wetherIndexList = wetherIndexList;
	}

	public List<ContractClaimConditionItem> getContractClaimConditionItemList() {
		return contractClaimConditionItemList;
	}

	public void setContractClaimConditionItemList(List<ContractClaimConditionItem> contractClaimConditionItemList) {
		this.contractClaimConditionItemList = contractClaimConditionItemList;
	}

	public Long getCancelId() {
		return cancelId;
	}

	public void setCancelId(Long cancelId) {
		this.cancelId = cancelId;
	}

	public BigDecimal getDnocPoliticalDay() {
		return dnocPoliticalDay;
	}

	public void setDnocPoliticalDay(BigDecimal dnocPoliticalDay) {
		this.dnocPoliticalDay = dnocPoliticalDay;
	}

	public BigDecimal getDnocPoliticalMonth() {
		return dnocPoliticalMonth;
	}

	public void setDnocPoliticalMonth(BigDecimal dnocPoliticalMonth) {
		this.dnocPoliticalMonth = dnocPoliticalMonth;
	}

	public BigDecimal getDnocSanctionDay() {
		return dnocSanctionDay;
	}

	public void setDnocSanctionDay(BigDecimal dnocSanctionDay) {
		this.dnocSanctionDay = dnocSanctionDay;
	}

	public BigDecimal getDnocSanctionMonth() {
		return dnocSanctionMonth;
	}

	public void setDnocSanctionMonth(BigDecimal dnocSanctionMonth) {
		this.dnocSanctionMonth = dnocSanctionMonth;
	}

	public BigDecimal getDnocWarDay() {
		return dnocWarDay;
	}

	public void setDnocWarDay(BigDecimal dnocWarDay) {
		this.dnocWarDay = dnocWarDay;
	}

	public BigDecimal getDnocWarMonth() {
		return dnocWarMonth;
	}

	public void setDnocWarMonth(BigDecimal dnocWarMonth) {
		this.dnocWarMonth = dnocWarMonth;
	}

	public String getPnocAutomatic() {
		return pnocAutomatic;
	}

	public void setPnocAutomatic(String pnocAutomatic) {
		this.pnocAutomatic = pnocAutomatic;
	}

	public BigDecimal getPnocCedentDay() {
		return pnocCedentDay;
	}

	public void setPnocCedentDay(BigDecimal pnocCedentDay) {
		this.pnocCedentDay = pnocCedentDay;
	}

	public BigDecimal getPnocCedentMonth() {
		return pnocCedentMonth;
	}

	public void setPnocCedentMonth(BigDecimal pnocCedentMonth) {
		this.pnocCedentMonth = pnocCedentMonth;
	}

	public BigDecimal getPnocReinsurerDay() {
		return pnocReinsurerDay;
	}

	public void setPnocReinsurerDay(BigDecimal pnocReinsurerDay) {
		this.pnocReinsurerDay = pnocReinsurerDay;
	}

	public BigDecimal getPnocReinsurerMonth() {
		return pnocReinsurerMonth;
	}

	public void setPnocReinsurerMonth(BigDecimal pnocReinsurerMonth) {
		this.pnocReinsurerMonth = pnocReinsurerMonth;
	}

	public List<ContractSectionVO> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<ContractSectionVO> sectionList) {
		this.sectionList = sectionList;
	}

	public List<Long> getDeleteSectionList() {
		return deleteSectionList;
	}

	public void setDeleteSectionList(List<Long> deleteSectionList) {
		this.deleteSectionList = deleteSectionList;
	}

	public List<String> getLinkDefineList() {
		return linkDefineList;
	}

	public void setLinkDefineList(List<String> linkDefineList) {
		this.linkDefineList = linkDefineList;
	}

	public List<LinkContractVO> getLinkList() {
		return linkList;
	}

	public void setLinkList(List<LinkContractVO> linkList) {
		this.linkList = linkList;
	}

	public List<ContractStatusLogVO> getLogList() {
		return logList;
	}

	public void setLogList(List<ContractStatusLogVO> logList) {
		this.logList = logList;
	}

	public String getCoB() {
		return coB;
	}

	public void setCoB(String coB) {
		this.coBList = DataTypeConvertor.transStringToSelectTree(coB);
		this.coB = coB;
	}

	public String getLoB() {
		return loB;
	}

	public void setLoB(String loB) {
		this.loBList = DataTypeConvertor.transStringToSelectTree(loB);
		this.loB = loB;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currencyList = DataTypeConvertor.transStringToSelectTree(currency);
		this.currency = currency;
	}

	public String getUwArea() {
		return uwArea;
	}

	public void setUwArea(String uwArea) {
		this.uwAreaList = DataTypeConvertor.transStringToSelectTree(uwArea);
		this.uwArea = uwArea;
	}

	public String getCoveredArea() {
		return coveredArea;
	}

	public void setCoveredArea(String coveredArea) {
		this.coveredAreaList = DataTypeConvertor.transStringToSelectTree(coveredArea);
		this.coveredArea = coveredArea;
	}

	public String getCovered() {
		return covered;
	}

	public void setCovered(String covered) {
		this.coveredList = DataTypeConvertor.transStringToSelectTree(covered);
		this.covered = covered;
	}

	public String getPeril() {
		return peril;
	}

	public void setPeril(String peril) {
		this.perilList = DataTypeConvertor.transStringToSelectTree(peril);
		this.peril = peril;
	}

	public String getPricesIndex() {
		return pricesIndex;
	}

	public void setPricesIndex(String pricesIndex) {
		this.pricesIndexList = DataTypeConvertor.transStringToSelectTree(pricesIndex);
		this.pricesIndex = pricesIndex;
	}

	public String getWetherIndex() {
		return wetherIndex;
	}

	public void setWetherIndex(String wetherIndex) {
		this.wetherIndexList = DataTypeConvertor.transStringToSelectTree(wetherIndex);
		this.wetherIndex = wetherIndex;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getCountPerPage() {
		return countPerPage;
	}

	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getMainCurrency() {
		return mainCurrency;
	}

	public void setMainCurrency(String mainCurrency) {
		this.mainCurrency = mainCurrency;
	}

	public String getAppianNo() {
		return appianNo;
	}

	public void setAppianNo(String appianNo) {
		this.appianNo = appianNo;
	}

	public String getPricingRef() {
		return pricingRef;
	}

	public void setPricingRef(String pricingRef) {
		this.pricingRef = pricingRef;
	}

	public String getPortfolioIn() {
		return portfolioIn;
	}

	public void setPortfolioIn(String portfolioIn) {
		this.portfolioIn = portfolioIn;
	}

	public BigDecimal getPortfolioInData() {
		return portfolioInData;
	}

	public void setPortfolioInData(BigDecimal portfolioInData) {
		this.portfolioInData = portfolioInData;
	}

	public String getPortfolioOut() {
		return portfolioOut;
	}

	public void setPortfolioOut(String portfolioOut) {
		this.portfolioOut = portfolioOut;
	}

	public BigDecimal getPortfolioOutData() {
		return portfolioOutData;
	}

	public void setPortfolioOutData(BigDecimal portfolioOutData) {
		this.portfolioOutData = portfolioOutData;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public String getPortfolioTransfer() {
		return portfolioTransfer;
	}

	public void setPortfolioTransfer(String portfolioTransfer) {
		this.portfolioTransfer = portfolioTransfer;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	private List<String> searchStatus;

	private Boolean isClosingPeriod;

	public Boolean getIsClosingPeriod() {
		return isClosingPeriod;
	}

	public void setIsClosingPeriod(Boolean isClosingPeriod) {
		this.isClosingPeriod = isClosingPeriod;
	}

	public List<String> getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(List<String> searchStatus) {
		this.searchStatus = searchStatus;
	}

	public BigDecimal getAccountDays() {
		return accountDays;
	}

	public void setAccountDays(BigDecimal accountDays) {
		this.accountDays = accountDays;
	}

	public BigDecimal getSettlementDays() {
		return settlementDays;
	}

	public void setSettlementDays(BigDecimal settlementDays) {
		this.settlementDays = settlementDays;
	}

	public String getAccountRemark() {
		return accountRemark;
	}

	public void setAccountRemark(String accountRemark) {
		this.accountRemark = accountRemark;
	}

	public String getEndoType() {
		return endoType;
	}

	public void setEndoType(String endoType) {
		this.endoType = endoType;
	}

	public String getAttachFilePath() {
		return attachFilePath;
	}

	public void setAttachFilePath(String attachFilePath) {
		this.attachFilePath = attachFilePath;
	}

	public String getMainCoverArea() {
		return mainCoverArea;
	}

	public void setMainCoverArea(String mainCoverArea) {
		this.mainCoverArea = mainCoverArea;
	}

}
