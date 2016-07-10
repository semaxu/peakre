/**
 * 
 */
package com.ebao.ap99.arap.vo;

import java.util.List;

/**
 * @author meiliang.zou
 *
 */
public class CollectionSearchResult {
	public String paymentMethod;
	
	public List<CreditsAndDebit> creditsAndDebit;
	
	public List<Balance> balance;

	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the creditsAndDebit
	 */
	public List<CreditsAndDebit> getCreditsAndDebit() {
		return creditsAndDebit;
	}

	/**
	 * @param creditsAndDebit the creditsAndDebit to set
	 */
	public void setCreditsAndDebit(List<CreditsAndDebit> creditsAndDebit) {
		this.creditsAndDebit = creditsAndDebit;
	}

	/**
	 * @return the balance
	 */
	public List<Balance> getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(List<Balance> balance) {
		this.balance = balance;
	}
	
	
}
