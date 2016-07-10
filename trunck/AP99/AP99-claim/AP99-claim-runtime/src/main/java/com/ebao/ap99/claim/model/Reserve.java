/**
 * 
 */
package com.ebao.ap99.claim.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author gang.wang
 *
 */
public class Reserve {

	private BigDecimal amountOc;
	private BigDecimal orgAmountOc;
	private BigDecimal amountUsd;
	private BigDecimal orgAmountUsd;
	private String brokerRefer;
	private String refType;
	private String businessDirection;
	private Long refId;
	private Long relatedReserveId;

	
	private String cedentRefer;

	private BigDecimal exchangeRate;

	private String frontingFlag;

	private String originalCurrency;

	private String postingFlag;
	private String orgPostingFlag;

	private String remark;

	private String reserveType;

	private Long sectionId;
	private Long retroRefSectionId;
	private String status;

	private Long insertBy;

	private Date insertTime;

	public void setAmountOc(BigDecimal amountOc) {
		this.amountOc = amountOc;
	}

//	@JsonIgnore
//	public Double getAmountOcDouble() {
//		return amountOc.doubleValue();
//	}
	
	@JsonIgnore
	public Double getAmountOcDouble() {
		if(null !=orgAmountOc ){
			return orgAmountOc.doubleValue();
		}else{
			return 0.00;
		}
	}
	public Long getRelatedReserveId() {
		return relatedReserveId;
	}

	public void setRelatedReserveId(Long relatedReserveId) {
		this.relatedReserveId = relatedReserveId;
	}

	// @JsonIgnore
	// public void setAmountOc(Double amountOc) {
	// this.amountOc = amountOc;
	// }

	public BigDecimal getAmountOc() {
		return amountOc;
	}

	public BigDecimal getAmountUsd() {
		return amountUsd;
	}

	public Double getAmountUsdDouble() {
		if( null != orgAmountUsd){
			return orgAmountUsd.doubleValue();
		}else{
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

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
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

}
