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
public class Balance {
	private Integer suspenseType;
	private Long suspenseId;
	private String contractId;
	private String contractCode;
	private String partnerCode;
	private String settleCurrencyCode;
	private BigDecimal settleAmount;
	private BigDecimal balanceAmount;
	private BigDecimal balanceAmountInSettleCurrency;
	private String balanceCurrency;
	private BigDecimal markOffAmount;
	private BigDecimal markOffAmountInSettleCurrency;
	private BigDecimal exchangeRate;
	private Date settleDate;
	private Date generationDate;
	
	public Long getSuspenseId() {
		return suspenseId;
	}

	public void setSuspenseId(Long suspenseId) {
		this.suspenseId = suspenseId;
	}
	
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public BigDecimal getMarkOffAmountInSettleCurrency() {
		return markOffAmountInSettleCurrency;
	}

	public void setMarkOffAmountInSettleCurrency(
			BigDecimal markOffAmountInSettleCurrency) {
		this.markOffAmountInSettleCurrency = markOffAmountInSettleCurrency;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	
	public Integer getSuspenseType() {
		return suspenseType;
	}

	public void setSuspenseType(Integer suspenseType) {
		this.suspenseType = suspenseType;
	}
	
	public String getSettleCurrencyCode() {
		return settleCurrencyCode;
	}

	public void setSettleCurrencyCode(String settleCurrencyCode) {
		this.settleCurrencyCode = settleCurrencyCode;
	}

	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}
	
	public BigDecimal getBalanceAmountInSettleCurrency() {
		return balanceAmountInSettleCurrency;
	}

	public void setBalanceAmountInSettleCurrency(
			BigDecimal balanceAmountInSettleCurrency) {
		this.balanceAmountInSettleCurrency = balanceAmountInSettleCurrency;
	}

	/**
	 * @return the balanceAmount
	 */
	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	/**
	 * @param balanceAmount the balanceAmount to set
	 */
	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	/**
	 * @return the balanceCurrency
	 */
	public String getBalanceCurrency() {
		return balanceCurrency;
	}

	/**
	 * @param balanceCurrency the balanceCurrency to set
	 */
	public void setBalanceCurrency(String balanceCurrency) {
		this.balanceCurrency = balanceCurrency;
	}
	
	/**
	 * @return the exchangeRate
	 */
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * @param exchangeRate the exchangeRate to set
	 */
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getMarkOffAmount() {
		return markOffAmount;
	}

	public void setMarkOffAmount(BigDecimal markOffAmount) {
		this.markOffAmount = markOffAmount;
	}

	public Date getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Date getGenerationDate() {
		return generationDate;
	}

	public void setGenerationDate(Date generationDate) {
		this.generationDate = generationDate;
	}
}
