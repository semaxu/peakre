/**
 * 
 */
package com.ebao.ap99.claim.model;

/**
 * @author gang.wang
 *
 */
public class ClaimSummaryInfo {

	private String currencyType;
	private Double reserveSummary = Double.valueOf(0);
	private Double settlementSummary = Double.valueOf(0);
	private Double totalByCurrency = Double.valueOf(0);

	public ClaimSummaryInfo() {

	}

	public ClaimSummaryInfo(String currency, Double reserveSummary, Double settlementSummary) {
		currencyType = currency;
		if (reserveSummary != null)
			this.reserveSummary = reserveSummary;
		if (settlementSummary != null)
			this.settlementSummary = settlementSummary;
		totalByCurrency = this.reserveSummary + this.settlementSummary;

	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public Double getReserveSummary() {
		return reserveSummary;
	}

	public void setReserveSummary(Double reserveSummary) {
		this.reserveSummary = reserveSummary;
	}

	public Double getSettlementSummary() {
		return settlementSummary;
	}

	public void setSettlementSummary(Double settlementSummary) {
		this.settlementSummary = settlementSummary;
	}

	public Double getTotalByCurrency() {
		return totalByCurrency;
	}

	public void setTotalByCurrency(Double totalByCurrency) {
		this.totalByCurrency = totalByCurrency;
	}

}
