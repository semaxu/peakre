/**
 * 
 */
package com.ebao.ap99.claim.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author gang.wang
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SettlementItemInfo {

	@XmlTransient
	private long settleDetailId;

	private BigDecimal amountOc;
	@XmlTransient
	private BigDecimal amountUsd;

	private String brokerRefer;

	@XmlTransient
	private String businessDirection;

	private String cedentRefer;
	@XmlTransient
	private Date dateOfPayment;
	@XmlTransient
	private BigDecimal exchangeRate;
	@XmlTransient
	private String frontingFlag;

	private String originalCurrency;
	@XmlTransient
	private String payType;

	private String postingFlag;

	private String remark;

	private BigDecimal sectionId;

	private BigDecimal retroRefSectionId;
	private String settlementType;
	@XmlTransient
	private BigDecimal relatedSettleDetailId;
	@XmlTransient
	private Long insertBy;
	@XmlTransient
	private Date insertTime;
	@XmlTransient
	private String orgPostingFlag;

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

	

	public long getSettleDetailId() {
		return settleDetailId;
	}

	public void setSettleDetailId(long settleDetailId) {
		this.settleDetailId = settleDetailId;
	}

	public BigDecimal getAmountOc() {
		return amountOc;
	}

	public Double getAmountOcDouble() {
		return amountOc.doubleValue();
	}

	public void setAmountOc(BigDecimal amountOc) {
		this.amountOc = amountOc;
	}

	public BigDecimal getAmountUsd() {
		return amountUsd;
	}

	public Double getAmountUsdDouble() {
		// return this.getSettlementType().equals("4") ?
		// amountUsd.multiply(BigDecimal.valueOf(-1)).doubleValue()
		// : amountUsd.doubleValue();
		//return amountUsd.doubleValue();
		
		if (null != amountUsd) {
			return amountUsd.doubleValue();
		} else {
			return 0.00;

		}
	}

	public void setAmountUsd(BigDecimal amountUsd) {
		this.amountUsd = amountUsd;
	}

	public BigDecimal getRelatedSettleDetailId() {
		return relatedSettleDetailId;
	}

	public void setRelatedSettleDetailId(BigDecimal relatedSettleDetailId) {
		this.relatedSettleDetailId = relatedSettleDetailId;
	}

	public String getBrokerRefer() {
		return brokerRefer;
	}

	public void setBrokerRefer(String brokerRefer) {
		this.brokerRefer = brokerRefer;
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

	public Date getDateOfPayment() {
		return dateOfPayment;
	}

	public void setDateOfPayment(Date dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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

	public BigDecimal getSectionId() {
		return sectionId;
	}

	public void setSectionId(BigDecimal sectionId) {
		this.sectionId = sectionId;
	}

	public BigDecimal getRetroRefSectionId() {
		return retroRefSectionId;
	}

	public void setRetroRefSectionId(BigDecimal retroRefSectionId) {
		this.retroRefSectionId = retroRefSectionId;
	}

	public String getSettlementType() {
		return settlementType;
	}

	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
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

	public String getOrgPostingFlag() {
		return orgPostingFlag;
	}

	public void setOrgPostingFlag(String orgPostingFlag) {
		this.orgPostingFlag = orgPostingFlag;
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

	public Date getRetroRefreinsStarting() {
		return retroRefreinsStarting;
	}

	public void setRetroRefreinsStarting(Date retroRefreinsStarting) {
		this.retroRefreinsStarting = retroRefreinsStarting;
	}

	
}
