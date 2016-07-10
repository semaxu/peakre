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
public class GeneralLedgerVO {
	private Long generalLedgerId;
	
	private String glAccount;
	
	private String glAccountText;
	
	private String subTransaction;
	
	private String currency;
	
	private BigDecimal amount;
	
	private BigDecimal amountInUSD;
	
	private String creditDebit;
	
	private Date documentDate;
	
	private Date postDate;
	
	private Integer postStatus;

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
	 * @return the subTransaction
	 */
	public String getSubTransaction() {
		return subTransaction;
	}

	/**
	 * @param subTransaction the subTransaction to set
	 */
	public void setSubTransaction(String subTransaction) {
		this.subTransaction = subTransaction;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
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
	 * @return the documentDate
	 */
	public Date getDocumentDate() {
		return documentDate;
	}

	/**
	 * @param documentDate the documentDate to set
	 */
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	/**
	 * @return the postDate
	 */
	public Date getPostDate() {
		return postDate;
	}

	/**
	 * @param postDate the postDate to set
	 */
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	/**
	 * @return the postStatus
	 */
	public Integer getPostStatus() {
		return postStatus;
	}

	/**
	 * @param postStatus the postStatus to set
	 */
	public void setPostStatus(Integer postStatus) {
		this.postStatus = postStatus;
	}

	public Long getGeneralLedgerId() {
		return generalLedgerId;
	}

	public void setGeneralLedgerId(Long generalLedgerId) {
		this.generalLedgerId = generalLedgerId;
	}
	
}
