/**
 * 
 */
package com.ebao.ap99.claim.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author gang.wang
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ReserveInfo {

	@XmlTransient
	private Long reserveId;

	private BigDecimal amountOc;
	@XmlTransient
	private BigDecimal orgAmountOc;
	@XmlTransient
	private BigDecimal amountUsd;
	@XmlTransient
	private BigDecimal orgAmountUsd;
	private String brokerRefer;
	@XmlTransient
	private String refType;
	@XmlTransient
	private String businessDirection;

	private String cedentRefer;
	@XmlTransient
	private BigDecimal exchangeRate;

	private String frontingFlag;

	private String originalCurrency;

	private String postingFlag;

	@XmlTransient
	private String orgPostingFlag;

	@XmlTransient
	private String remark;

	private String reserveType;

	private Long sectionId;
	private Long retroRefSectionId;
	@XmlTransient
	private Long relatedReserveId;
	@XmlTransient
	private String status;

	@XmlTransient
	private Long insertBy;
	@XmlTransient
	private Date insertTime;
	// for xmlImport
	private String contractCode;
	private String sectionName;
	private String sectionType;
	private Long uwYear;
	private Date reinsStarting;
	
	private String retroRefcontractCode;
	private String retroRefsectionName;
	private String retroRefsectionType;
	private Long retroRefuwYear;
	private Date retroRefreinsStarting;
	

	public Long getReserveId() {
		return reserveId;
	}

	public void setReserveId(Long reserveId) {
		this.reserveId = reserveId;
	}

	public void setAmountOc(BigDecimal amountOc) {
		this.amountOc = amountOc;
	}

	// @JsonIgnore
	// public Double getAmountOcDouble() {
	// return amountOc.doubleValue();
	// }

	@JsonIgnore
	public Double getAmountOcDouble() {
		if (null != orgAmountOc) {
			return orgAmountOc.doubleValue();
		} else {
			return 0.00;

		}
		// return orgAmountOc.doubleValue();
	}

	// @JsonIgnore
	// public void setAmountOc(Double amountOc) {
	// this.amountOc = amountOc;
	// }

	public Long getRelatedReserveId() {
		return relatedReserveId;
	}

	public void setRelatedReserveId(Long relatedReserveId) {
		this.relatedReserveId = relatedReserveId;
	}

	public BigDecimal getAmountOc() {
		return amountOc;
	}

	public BigDecimal getAmountUsd() {
		return amountUsd;
	}

	public Double getAmountUsdDouble() {
		if (null != orgAmountUsd) {
			return orgAmountUsd.doubleValue();
		} else {
			return 0.00;

		}
	}

	public void setAmountUsd(BigDecimal amountUsd) {
		this.amountUsd = amountUsd;
	}

	public String getBrokerRefer() {
		return brokerRefer;
	}

	public void setBrokerRefer(String brokerRefer) {
		this.brokerRefer = brokerRefer;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public String getBusinessDirection() {
		return businessDirection;
	}

	public void setBusinessDirection(String businessDirection) {
		this.businessDirection = businessDirection;
	}

	public String getCedentRefer() {
		return cedentRefer;
	}

	public void setCedentRefer(String cedentRefer) {
		this.cedentRefer = cedentRefer;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getFrontingFlag() {
		return frontingFlag;
	}

	public void setFrontingFlag(String frontingFlag) {
		this.frontingFlag = frontingFlag;
	}

	public String getOriginalCurrency() {
		return originalCurrency;
	}

	public void setOriginalCurrency(String originalCurrency) {
		this.originalCurrency = originalCurrency;
	}

	public String getPostingFlag() {
		return postingFlag;
	}

	public void setPostingFlag(String postingFlag) {
		this.postingFlag = postingFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReserveType() {
		return reserveType;
	}

	public void setReserveType(String reserveType) {
		this.reserveType = reserveType;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public Long getRetroRefSectionId() {
		return retroRefSectionId;
	}

	public void setRetroRefSectionId(Long retroRefSectionId) {
		this.retroRefSectionId = retroRefSectionId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getInsertBy() {
		return insertBy;
	}

	public void setInsertBy(Long insertBy) {
		this.insertBy = insertBy;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public BigDecimal getOrgAmountOc() {
		return orgAmountOc;
	}

	public void setOrgAmountOc(BigDecimal orgAmountOc) {
		this.orgAmountOc = orgAmountOc;
	}

	public String getOrgPostingFlag() {
		return orgPostingFlag;
	}

	public void setOrgPostingFlag(String orgPostingFlag) {
		this.orgPostingFlag = orgPostingFlag;
	}

	public BigDecimal getOrgAmountUsd() {
		return orgAmountUsd;
	}

	public void setOrgAmountUsd(BigDecimal orgAmountUsd) {
		this.orgAmountUsd = orgAmountUsd;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getSectionType() {
		return sectionType;
	}

	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}

	public String getRetroRefcontractCode() {
		return retroRefcontractCode;
	}

	public void setRetroRefcontractCode(String retroRefcontractCode) {
		this.retroRefcontractCode = retroRefcontractCode;
	}

	public String getRetroRefsectionName() {
		return retroRefsectionName;
	}

	public void setRetroRefsectionName(String retroRefsectionName) {
		this.retroRefsectionName = retroRefsectionName;
	}

	public String getRetroRefsectionType() {
		return retroRefsectionType;
	}

	public void setRetroRefsectionType(String retroRefsectionType) {
		this.retroRefsectionType = retroRefsectionType;
	}

	

	public Date getReinsStarting() {
		return reinsStarting;
	}

	public void setReinsStarting(Date reinsStarting) {
		this.reinsStarting = reinsStarting;
	}

	

	public Date getRetroRefreinsStarting() {
		return retroRefreinsStarting;
	}

	public void setRetroRefreinsStarting(Date retroRefreinsStarting) {
		this.retroRefreinsStarting = retroRefreinsStarting;
	}

	public Long getUwYear() {
		return uwYear;
	}

	public void setUwYear(Long uwYear) {
		this.uwYear = uwYear;
	}

	public Long getRetroRefuwYear() {
		return retroRefuwYear;
	}

	public void setRetroRefuwYear(Long retroRefuwYear) {
		this.retroRefuwYear = retroRefuwYear;
	}

	
	
	
}
