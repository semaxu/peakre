/**
 * 
 */
package com.ebao.ap99.arap.vo;

import java.math.BigDecimal;

/**
 * @author meiliang.zou
 *
 */
public class DoubleEntriesVO {
	private String creditDebit;
	
	private String glAccount;
	
	private String glAccountText;
	
	private BigDecimal amount;
	
	private BigDecimal amountInUSD;

	/**
	 * @return the creditDebit
	 */
	public String getCreditDebit() {
		return creditDebit;
	}

	/**
	 * @param creditDebit the creditDebit to set
	 */
	public void setCreditDebit(String creditDebit) {
		this.creditDebit = creditDebit;
	}

	/**
	 * @return the glAccount
	 */
	public String getGlAccount() {
		return glAccount;
	}

	/**
	 * @param glAccount the glAccount to set
	 */
	public void setGlAccount(String glAccount) {
		this.glAccount = glAccount;
	}

	/**
	 * @return the glAccountText
	 */
	public String getGlAccountText() {
		return glAccountText;
	}

	/**
	 * @param glAccountText the glAccountText to set
	 */
	public void setGlAccountText(String glAccountText) {
		this.glAccountText = glAccountText;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the amountInUSD
	 */
	public BigDecimal getAmountInUSD() {
		return amountInUSD;
	}

	/**
	 * @param amountInUSD the amountInUSD to set
	 */
	public void setAmountInUSD(BigDecimal amountInUSD) {
		this.amountInUSD = amountInUSD;
	}
	
	
}
