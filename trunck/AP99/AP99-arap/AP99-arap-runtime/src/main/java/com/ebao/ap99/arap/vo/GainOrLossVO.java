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
public class GainOrLossVO {
	private Long feeId;
	
	private BigDecimal markOffAmount;
	
	private String originalCurrency;
	
	private BigDecimal settleAmount;
	
	private String settleCurrency;
	
	private Date settleDate;

	/**
	 * @return the feeId
	 */
	public Long getFeeId() {
		return feeId;
	}

	/**
	 * @param feeId the feeId to set
	 */
	public void setFeeId(Long feeId) {
		this.feeId = feeId;
	}

	/**
	 * @return the markOffAmount
	 */
	public BigDecimal getMarkOffAmount() {
		return markOffAmount;
	}

	/**
	 * @param markOffAmount the markOffAmount to set
	 */
	public void setMarkOffAmount(BigDecimal markOffAmount) {
		this.markOffAmount = markOffAmount;
	}

	/**
	 * @return the originalCurrency
	 */
	public String getOriginalCurrency() {
		return originalCurrency;
	}

	/**
	 * @param originalCurrency the originalCurrency to set
	 */
	public void setOriginalCurrency(String originalCurrency) {
		this.originalCurrency = originalCurrency;
	}

	/**
	 * @return the settleAmount
	 */
	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	/**
	 * @param settleAmount the settleAmount to set
	 */
	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}

	/**
	 * @return the settleCurrency
	 */
	public String getSettleCurrency() {
		return settleCurrency;
	}

	/**
	 * @param settleCurrency the settleCurrency to set
	 */
	public void setSettleCurrency(String settleCurrency) {
		this.settleCurrency = settleCurrency;
	}

	/**
	 * @return the settleDate
	 */
	public Date getSettleDate() {
		return settleDate;
	}

	/**
	 * @param settleDate the settleDate to set
	 */
	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}
	
	
}
