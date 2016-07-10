/**
 * 
 */
package com.ebao.ap99.claim.model;

/**
 * @author yujie.zhang
 *
 */
public class ClaimRecordsSummary {

	private String currency;
	private double grossIncurredLoss;
	private double grossRetrocessionRecovery;
	private double netTotal;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getGrossIncurredLoss() {
		return grossIncurredLoss;
	}
	public void setGrossIncurredLoss(double grossIncurredLoss) {
		this.grossIncurredLoss = grossIncurredLoss;
	}
	public double getGrossRetrocessionRecovery() {
		return grossRetrocessionRecovery;
	}
	public void setGrossRetrocessionRecovery(double grossRetrocessionRecovery) {
		this.grossRetrocessionRecovery = grossRetrocessionRecovery;
	}
	public double getNetTotal() {
		return netTotal;
	}
	public void setNetTotal(double netTotal) {
		this.netTotal = netTotal;
	}
	
	
}
