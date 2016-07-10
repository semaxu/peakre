/**
 * 
 */
package com.ebao.ap99.claim.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author gang.wang
 *
 */
public class Settle {

	private BigDecimal amountOc;
	private Long settlementId;
	private BigDecimal amountUsd;
	private String brokerRefer;
	private String businessDirection;
	private Date dateOfPayment;
	private String cedentRefer;
	private String payType;

	private BigDecimal exchangeRate;

	private String frontingFlag;

	private String originalCurrency;

	private String postingFlag;
	private String orgPostingFlag;

	private String remark;

	private String settlementType;

	private Long sectionId;
	private BigDecimal retroRefSectionId;
	private BigDecimal relatedSettleDetailId;
	private Long insertBy;

	private Date insertTime;
	

	public BigDecimal getAmountOc() {
		return this.amountOc;
	}

	public void setAmountOc(BigDecimal amountOc) {
		this.amountOc = amountOc;
	}

	public BigDecimal getAmountUsd() {
		return this.amountUsd;
	}

	public void setAmountUsd(BigDecimal amountUsd) {
		this.amountUsd = amountUsd;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public BigDecimal getRetroRefSectionId() {
		return retroRefSectionId;
	}

	public void setRetroRefSectionId(BigDecimal retroRefSectionId) {
		this.retroRefSectionId = retroRefSectionId;
	}

	public BigDecimal getRelatedSettleDetailId() {
		return relatedSettleDetailId;
	}

	public void setRelatedSettleDetailId(BigDecimal relatedSettleDetailId) {
		this.relatedSettleDetailId = relatedSettleDetailId;
	}

	public String getBrokerRefer() {
		return this.brokerRefer;
	}

	public void setBrokerRefer(String brokerRefer) {
		this.brokerRefer = brokerRefer;
	}

	public String getBusinessDirection() {
		return this.businessDirection;
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


	public Date getDateOfPayment() {
		return this.dateOfPayment;
	}

	public void setDateOfPayment(Date dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}

	public BigDecimal getExchangeRate() {
		return this.exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getFrontingFlag() {
		return this.frontingFlag;
	}

	public void setFrontingFlag(String frontingFlag) {
		this.frontingFlag = frontingFlag;
	}

	public String getOriginalCurrency() {
		return this.originalCurrency;
	}

	public void setOriginalCurrency(String originalCurrency) {
		this.originalCurrency = originalCurrency;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPostingFlag() {
		return this.postingFlag;
	}

	public void setPostingFlag(String postingFlag) {
		this.postingFlag = postingFlag;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getSettlementType() {
		return this.settlementType;
	}

	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}

	public String getOrgPostingFlag() {
		return orgPostingFlag;
	}

	public void setOrgPostingFlag(String orgPostingFlag) {
		this.orgPostingFlag = orgPostingFlag;
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

	public Long getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(Long settlementId) {
		this.settlementId = settlementId;
	}


	

}
