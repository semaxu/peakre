package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONTRACT_INFO_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONTRACT_INFO_LOG")
@NamedQueries({ @NamedQuery(name = "TRiContractInfoLog.findAll", query = "SELECT t FROM TRiContractInfoLog t"),
		@NamedQuery(name = "TRiContractInfoLog.findByContCompIdAndOperateId", query = "FROM TRiContractInfoLog s WHERE s.contCompId = :contCompId AND s.operateId = :operateId") })
public class TRiContractInfoLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @SequenceGenerator(name="T_RI_CONTRACT_INFO_LOG_LOGID_GENERATOR" )
	// @GeneratedValue(strategy=GenerationType.SEQUENCE,
	// generator="T_RI_CONTRACT_INFO_LOG_LOGID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "ANALYTICS_RESP")
	private Long analyticsResp;

	private String broker;

	@Column(name = "BROKER_REF")
	private String brokerRef;

	private String cedent;

	@Column(name = "CEDENT_COUNTRY")
	private String cedentCountry;

	@Column(name = "CEDENT_REF")
	private String cedentRef;

	@Column(name = "CO_BROKER")
	private String coBroker;

	@Column(name = "CONTRACT_CATEGORY")
	private String contractCategory;

	@Column(name = "CONTRACT_CODE")
	private String contractCode;

	@Column(name = "CONTRACT_NAME")
	private String contractName;

	@Column(name = "CONTRACT_NATURE")
	private String contractNature;

	@Column(name = "CONTRACT_TYPE")
	private String contractType;

	@Column(name = "DEPOSIT_ACCOUNTING")
	private String depositAccounting;

	private String fronting;

	@Temporal(TemporalType.DATE)
	@Column(name = "INFORCE_DATE")
	private Date inforceDate;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "LAST_CHANGED")
	private Long lastChanged;

	// @Column(name="INSERT_BY")
	// private BigDecimal insertBy;
	//
	// @Temporal(TemporalType.DATE)
	// @Column(name="INSERT_TIME")
	// private Date insertTime;

	private String insured;

	@Column(name = "LATEST_STATUS")
	private String latestStatus;

	@Column(name = "LINK_NAME")
	private String linkName;

	private String mainclass;

	@Column(name = "MARKET_RESP")
	private Long marketResp;

	private String mga;

	@Temporal(TemporalType.DATE)
	@Column(name = "REINS_ENDING")
	private Date reinsEnding;

	@Temporal(TemporalType.DATE)
	@Column(name = "REINS_STARTING")
	private Date reinsStarting;

	private String remark;

	@Column(name = "RENEWAL_ID")
	private Long renewalId;

	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	private String reviewed;

	private String subclass;

	@Column(name = "TREATY_OWNER")
	private Long treatyOwner;

	private Long underwriting;

	// @Column(name="UPDATE_BY")
	// private BigDecimal updateBy;
	//
	// @Temporal(TemporalType.DATE)
	// @Column(name="UPDATE_TIME")
	// private Date updateTime;

	@Column(name = "UW_COMPANY")
	private Long uwCompany;

	@Column(name = "UW_YEAR")
	private Long uwYear;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "PROTECTION_BASIC")
	private String protectionBasic;

	@Column(name = "APPIAN_NO")
	private String appianNo;

	@Column(name = "MAIN_CURRENCY")
	private String mainCurrency;

	@Column(name = "PRICING_REF")
	private String pricingRef;

	@Column(name = "LEADER")
	private String leader;

	@Column(name = "LEADER_NAME")
	private String leaderName;

	@Column(name = "PORTFOLIO_TRANSFER")
	private String portfolioTransfer;

	@Column(name = "REJECT_REASON")
	private String rejectReason;

	@Column(name = "MAIN_COVER_AREA")
	private String mainCoverArea;

	public TRiContractInfoLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getAnalyticsResp() {
		return this.analyticsResp;
	}

	public void setAnalyticsResp(Long analyticsResp) {
		this.analyticsResp = analyticsResp;
	}

	public String getBroker() {
		return this.broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public String getBrokerRef() {
		return this.brokerRef;
	}

	public void setBrokerRef(String brokerRef) {
		this.brokerRef = brokerRef;
	}

	public String getCedent() {
		return this.cedent;
	}

	public void setCedent(String cedent) {
		this.cedent = cedent;
	}

	public String getCedentCountry() {
		return this.cedentCountry;
	}

	public void setCedentCountry(String cedentCountry) {
		this.cedentCountry = cedentCountry;
	}

	public String getCedentRef() {
		return this.cedentRef;
	}

	public void setCedentRef(String cedentRef) {
		this.cedentRef = cedentRef;
	}

	public String getCoBroker() {
		return this.coBroker;
	}

	public void setCoBroker(String coBroker) {
		this.coBroker = coBroker;
	}

	public String getContractCategory() {
		return this.contractCategory;
	}

	public void setContractCategory(String contractCategory) {
		this.contractCategory = contractCategory;
	}

	public String getContractCode() {
		return this.contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractNature() {
		return this.contractNature;
	}

	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}

	public String getContractType() {
		return this.contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getDepositAccounting() {
		return this.depositAccounting;
	}

	public void setDepositAccounting(String depositAccounting) {
		this.depositAccounting = depositAccounting;
	}

	public String getFronting() {
		return this.fronting;
	}

	public void setFronting(String fronting) {
		this.fronting = fronting;
	}

	public Date getInforceDate() {
		return this.inforceDate;
	}

	public void setInforceDate(Date inforceDate) {
		this.inforceDate = inforceDate;
	}

	// public BigDecimal getInsertBy() {
	// return this.insertBy;
	// }
	//
	// public void setInsertBy(BigDecimal insertBy) {
	// this.insertBy = insertBy;
	// }
	//
	// public Date getInsertTime() {
	// return this.insertTime;
	// }
	//
	// public void setInsertTime(Date insertTime) {
	// this.insertTime = insertTime;
	// }

	public String getInsured() {
		return this.insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	public String getLatestStatus() {
		return this.latestStatus;
	}

	public void setLatestStatus(String latestStatus) {
		this.latestStatus = latestStatus;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getMainclass() {
		return this.mainclass;
	}

	public void setMainclass(String mainclass) {
		this.mainclass = mainclass;
	}

	public Long getMarketResp() {
		return this.marketResp;
	}

	public void setMarketResp(Long marketResp) {
		this.marketResp = marketResp;
	}

	public String getMga() {
		return this.mga;
	}

	public void setMga(String mga) {
		this.mga = mga;
	}

	public Date getReinsEnding() {
		return this.reinsEnding;
	}

	public void setReinsEnding(Date reinsEnding) {
		this.reinsEnding = reinsEnding;
	}

	public Date getReinsStarting() {
		return this.reinsStarting;
	}

	public void setReinsStarting(Date reinsStarting) {
		this.reinsStarting = reinsStarting;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getRenewalId() {
		return this.renewalId;
	}

	public void setRenewalId(Long renewalId) {
		this.renewalId = renewalId;
	}

	public String getReviewed() {
		return this.reviewed;
	}

	public void setReviewed(String reviewed) {
		this.reviewed = reviewed;
	}

	public String getSubclass() {
		return this.subclass;
	}

	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}

	public Long getTreatyOwner() {
		return this.treatyOwner;
	}

	public void setTreatyOwner(Long treatyOwner) {
		this.treatyOwner = treatyOwner;
	}

	public Long getUnderwriting() {
		return this.underwriting;
	}

	public void setUnderwriting(Long underwriting) {
		this.underwriting = underwriting;
	}

	// public BigDecimal getUpdateBy() {
	// return this.updateBy;
	// }
	//
	// public void setUpdateBy(BigDecimal updateBy) {
	// this.updateBy = updateBy;
	// }
	//
	// public Date getUpdateTime() {
	// return this.updateTime;
	// }
	//
	// public void setUpdateTime(Date updateTime) {
	// this.updateTime = updateTime;
	// }

	public Long getUwCompany() {
		return this.uwCompany;
	}

	public void setUwCompany(Long uwCompany) {
		this.uwCompany = uwCompany;
	}

	public Long getUwYear() {
		return this.uwYear;
	}

	public void setUwYear(Long uwYear) {
		this.uwYear = uwYear;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getLogId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setLogId(key);
	}

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getProtectionBasic() {
		return protectionBasic;
	}

	public void setProtectionBasic(String protectionBasic) {
		this.protectionBasic = protectionBasic;
	}

	public String getAppianNo() {
		return appianNo;
	}

	public void setAppianNo(String appianNo) {
		this.appianNo = appianNo;
	}

	public String getMainCurrency() {
		return mainCurrency;
	}

	public void setMainCurrency(String mainCurrency) {
		this.mainCurrency = mainCurrency;
	}

	public String getPricingRef() {
		return pricingRef;
	}

	public void setPricingRef(String pricingRef) {
		this.pricingRef = pricingRef;
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getLastChanged() {
		return lastChanged;
	}

	public void setLastChanged(Long lastChanged) {
		this.lastChanged = lastChanged;
	}

	public String getMainCoverArea() {
		return mainCoverArea;
	}

	public void setMainCoverArea(String mainCoverArea) {
		this.mainCoverArea = mainCoverArea;
	}

}