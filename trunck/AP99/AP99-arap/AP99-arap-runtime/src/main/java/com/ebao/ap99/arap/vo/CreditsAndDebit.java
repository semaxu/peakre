/**
 * 
 */
package com.ebao.ap99.arap.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author meiliang.zou
 *
 */
public class CreditsAndDebit {
	private Long settleGroupId;
	
	private String currencyCode;
	
	private BigDecimal amount;
	
	private BigDecimal convertedAmount;
	
	private BigDecimal exchangeRate;
	
	private BigDecimal amountAssigned;
	
	private BigDecimal collectedAssignedDiff;
	
	private List<EntryItem> entryItems;
	
	private String settleCurrencyCode;
	
	//public BigDecimal inverse;
	
	//Temporal
	private Integer settlmentTransType;
	private Date settlementTransDate;
	private Long settlementGroupId;
	private Long settlementTransId;
	private Date settleDate;
	
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getConvertedAmount() {
		return convertedAmount;
	}

	public void setConvertedAmount(BigDecimal convertedAmount) {
		this.convertedAmount = convertedAmount;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * @return the entryItems
	 */
	public List<EntryItem> getEntryItems() {
		return entryItems;
	}

	/**
	 * @param entryItems the entryItems to set
	 */
	public void setEntryItems(List<EntryItem> entryItems) {
		this.entryItems = entryItems;
	}

	public Long getSettlementGroupId() {
		return settlementGroupId;
	}

	public void setSettlementGroupId(Long settlementGroupId) {
		this.settlementGroupId = settlementGroupId;
	}

	public Long getSettlementTransId() {
		return settlementTransId;
	}

	public void setSettlementTransId(Long settlementTransId) {
		this.settlementTransId = settlementTransId;
	}

	public Integer getSettlmentTransType() {
		return settlmentTransType;
	}

	public void setSettlmentTransType(Integer settlmentTransType) {
		this.settlmentTransType = settlmentTransType;
	}

	public Date getSettlementTransDate() {
		return settlementTransDate;
	}

	public void setSettlementTransDate(Date settlementTransDate) {
		this.settlementTransDate = settlementTransDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getSettleGroupId() {
		return settleGroupId;
	}

	public void setSettleGroupId(Long settleGroupId) {
		this.settleGroupId = settleGroupId;
	}
	
	/**
	 * @return the settleCurrencyCode
	 */
	public String getSettleCurrencyCode() {
		return settleCurrencyCode;
	}

	/**
	 * @param settleCurrencyCode the settleCurrencyCode to set
	 */
	public void setSettleCurrencyCode(String settleCurrencyCode) {
		this.settleCurrencyCode = settleCurrencyCode;
	}

	public Date getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	/**
	 * @return the amountAssigned
	 */
	public BigDecimal getAmountAssigned() {
		return amountAssigned;
	}

	/**
	 * @param amountAssigned the amountAssigned to set
	 */
	public void setAmountAssigned(BigDecimal amountAssigned) {
		this.amountAssigned = amountAssigned;
	}

	/**
	 * @return the collectedAssignedDiff
	 */
	public BigDecimal getCollectedAssignedDiff() {
		return collectedAssignedDiff;
	}

	/**
	 * @param collectedAssignedDiff the collectedAssignedDiff to set
	 */
	public void setCollectedAssignedDiff(BigDecimal collectedAssignedDiff) {
		this.collectedAssignedDiff = collectedAssignedDiff;
	}
	
}
