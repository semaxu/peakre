/**
 * 
 */
package com.ebao.ap99.arap.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author meiliang.zou
 *
 */
public class EntryItem {
	private Boolean contractSelect = false;

	private Long contractID;
	
	private String contractCode;
	
	private String type;
	
	private Integer bizTransType;
	
	private String bizTransNo;
	
	private String entryCode;
	
	private String description;

	private BigDecimal amountOC;

	private String currency;

	private Date dueDate;
	
	private Date transDate;

	private BigDecimal osBalance;
	
	private BigDecimal OsBalanceInSettleCurrency;

	private String settleCurrency;

	private BigDecimal settleDiff;
	
	private Long feeId;
	
	private String feeIdStr;
	
	private String parentFeeIdStr;
	
	private BigDecimal markOffAmount;
	
	private BigDecimal settleAmount;
	
	private BigDecimal exchangeRate;
	// meiliang.zou 2016.5.3 modify the false of fully settle
	private Boolean fullySettle = false;
	
	private BigDecimal gainLoss;
	
	private Integer needPost;
	
	private Integer settleTransType;
	
	private String settleTransNo;	
	
	private BigDecimal assignedAmount;
	
	private String feeIdArray="";// fee id array to specified fee group item, format: xxx,xxx,xxx

	public Boolean isContractSelect() {
		return contractSelect;
	}

	public void setContractSelect(Boolean contractSelect) {
		this.contractSelect = contractSelect;
	}

	public Long getFeeId() {
		return feeId;
	}

	public void setFeeId(Long feeId) {
		this.feeId = feeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Boolean getContractSelect() {
		return contractSelect;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmountOC() {
		return amountOC;
	}

	public void setAmountOC(BigDecimal amountOC) {
		this.amountOC = amountOC;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getOsBalance() {
		return osBalance;
	}

	public void setOsBalance(BigDecimal osBalance) {
		this.osBalance = osBalance;
	}
	
	public BigDecimal getOsBalanceInSettleCurrency() {
		return OsBalanceInSettleCurrency;
	}

	public void setOsBalanceInSettleCurrency(BigDecimal osBalanceInSettleCurrency) {
		OsBalanceInSettleCurrency = osBalanceInSettleCurrency;
	}

	public BigDecimal getSettleDiff() {
		return settleDiff;
	}

	public void setSettleDiff(BigDecimal settleDiff) {
		this.settleDiff = settleDiff;
	}

	public BigDecimal getMarkOffAmount() {
		return markOffAmount;
	}

	public void setMarkOffAmount(BigDecimal markOffAmount) {
		this.markOffAmount = markOffAmount;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Boolean getFullySettle() {
		return fullySettle;
	}

	public void setFullySettle(Boolean fullySettle) {
		this.fullySettle = fullySettle;
	}

	public BigDecimal getGainLoss() {
		return gainLoss;
	}

	public void setGainLoss(BigDecimal gainLoss) {
		this.gainLoss = gainLoss;
	}

	public Integer getNeedPost() {
		return needPost;
	}

	public void setNeedPost(Integer needPost) {
		this.needPost = needPost;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getFeeIdStr() {
		return feeIdStr;
	}

	public void setFeeIdStr(String feeIdStr) {
		this.feeIdStr = feeIdStr;
	}

	public String getParentFeeIdStr() {
		return parentFeeIdStr;
	}

	public void setParentFeeIdStr(String parentFeeIdStr) {
		this.parentFeeIdStr = parentFeeIdStr;
	}

	public Long getContractID() {
		return contractID;
	}

	public void setContractID(Long contractID) {
		this.contractID = contractID;
	}

	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}

	public String getSettleCurrency() {
		return settleCurrency;
	}

	public void setSettleCurrency(String settleCurrency) {
		this.settleCurrency = settleCurrency;
	}

	public String getEntryCode() {
		return entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	public Integer getSettleTransType() {
		return settleTransType;
	}

	public void setSettleTransType(Integer settleTransType) {
		this.settleTransType = settleTransType;
	}

	public String getSettleTransNo() {
		return settleTransNo;
	}

	public void setSettleTransNo(String settleTransNo) {
		this.settleTransNo = settleTransNo;
	}

	/**
	 * @return the assignedAmount
	 */
	public BigDecimal getAssignedAmount() {
		return assignedAmount;
	}

	/**
	 * @param assignedAmount the assignedAmount to set
	 */
	public void setAssignedAmount(BigDecimal assignedAmount) {
		this.assignedAmount = assignedAmount;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getFeeIdArray() {
		return feeIdArray;
	}

	public void setFeeIdArray(String feeIdArray) {
		this.feeIdArray = feeIdArray;
	}
	
	public void addFeeIdToArray(String feeId) {
		this.setFeeIdArray(this.getFeeIdArray()+","+feeId);
	}
}
