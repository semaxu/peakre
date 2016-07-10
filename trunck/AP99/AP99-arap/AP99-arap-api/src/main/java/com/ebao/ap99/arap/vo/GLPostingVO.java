package com.ebao.ap99.arap.vo;

import java.math.BigDecimal;
import java.util.Date;

public class GLPostingVO {
	private Long bizTranId;
	private Date postDate;
	private Long contractId;
	private Integer contractCate;
	private String entryCode;
	private Integer bizTransType;
	private String bizTransNo;
	private Long sectionId;
	private Long bizRefId;
	private Integer isEstimation;
	private String currencyCode;
	private BigDecimal amount;
	private boolean isSettleTrans;//Is settlement transaction, like collection/payment/internal offset
	public Long getContractId() {
		return contractId;
	}
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	public Integer getContractCate() {
		return contractCate;
	}
	public void setContractCate(Integer contractCate) {
		this.contractCate = contractCate;
	}
	public String getEntryCode() {
		return entryCode;
	}
	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}
	public Long getBizTranId() {
		return bizTranId;
	}
	public void setBizTranId(Long bizTranId) {
		this.bizTranId = bizTranId;
	}
	public Integer getBizTransType() {
		return bizTransType;
	}
	public void setBizTransType(Integer bizTransType) {
		this.bizTransType = bizTransType;
	}
	public String getBizTransNo() {
		return bizTransNo;
	}
	public void setBizTransNo(String bizTransNo) {
		this.bizTransNo = bizTransNo;
	}
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public Long getBizRefId() {
		return bizRefId;
	}
	public void setBizRefId(Long bizRefId) {
		this.bizRefId = bizRefId;
	}
	public Integer getIsEstimation() {
		return isEstimation;
	}
	public void setIsEstimation(Integer isEstimation) {
		this.isEstimation = isEstimation;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public boolean isSettleTrans() {
		return isSettleTrans;
	}
	public void setSettleTrans(boolean isSettleTrans) {
		this.isSettleTrans = isSettleTrans;
	}
}
