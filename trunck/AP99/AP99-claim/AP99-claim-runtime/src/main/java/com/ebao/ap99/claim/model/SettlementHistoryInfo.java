/**
 * 
 */
package com.ebao.ap99.claim.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author yujie.zhang
 *
 */
public class SettlementHistoryInfo {
	private long settleDetailId;
	private long settlementId;
	private BigDecimal amountOc;
	private BigDecimal amountUsd;
	private String brokerRefer;
	private String businessDirection;
	private String cedentRefer;
	private BigDecimal exchangeRate;
	private BigDecimal insertBy;
	private Date insertTime;
	private String originalCurrency;
	private String postingFlag;
	private String remark;
	private String settlementType;
	private long sectionId;
	private String status;
	private BigDecimal updateBy;
	private Date updateTime;
	private long underwritingYear;
	private String  contractCode ;
	private String contractSectionName;
	private String settlementName;
	
	
	
	public long getSettleDetailId() {
		return settleDetailId;
	}
	public void setSettleDetailId(long settleDetailId) {
		this.settleDetailId = settleDetailId;
	}
	public long getSettlementId() {
		return settlementId;
	}
	public void setSettlementId(long settlementId) {
		this.settlementId = settlementId;
	}
	public BigDecimal getAmountOc() {
		return amountOc;
	}
	public void setAmountOc(BigDecimal amountOc) {
		this.amountOc = amountOc;
	}
	
	@JsonIgnore
	public Double getAmountOcDouble() {
		return amountOc.doubleValue();
	}
	
	public BigDecimal getAmountUsd() {
		return amountUsd;
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
	public BigDecimal getInsertBy() {
		return insertBy;
	}
	public void setInsertBy(BigDecimal insertBy) {
		this.insertBy = insertBy;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
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
	public String getSettlementType() {
		return settlementType;
	}
	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}
	public long getSectionId() {
		return sectionId;
	}
	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(BigDecimal updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public long getUnderwritingYear() {
		return underwritingYear;
	}
	public void setUnderwritingYear(long underwritingYear) {
		this.underwritingYear = underwritingYear;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getContractSectionName() {
		return contractSectionName;
	}
	public void setContractSectionName(String contractSectionName) {
		this.contractSectionName = contractSectionName;
	}
	public String getSettlementName() {
		return settlementName;
	}
	public void setSettlementName(String settlementName) {
		this.settlementName = settlementName;
	}
	
}
