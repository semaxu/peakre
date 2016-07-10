/**
 * 
 */
package com.ebao.ap99.claim.model;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author gang.wang
 *
 */
public class SettlementSummaryInfo {
	private String currencyType;
	private Double grossTotal = Double.valueOf(0);
	private Double netTotal = Double.valueOf(0);
	private Double lossTotal = Double.valueOf(0);
	private Double aCRTotal = Double.valueOf(0);
	private Double rIPTotal = Double.valueOf(0);
	private Double taxTotal = Double.valueOf(0);
	private Double othersTotal = Double.valueOf(0);

	public SettlementSummaryInfo() {

	}

	public SettlementSummaryInfo(String currency, Map<String, Double> sumOfCurrency) {
		currencyType = currency;

		Set<String> keySet = sumOfCurrency.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			String key = it.next();
			switch (key) {
			// case "1":
			// lossTotal += sumOfCurrency.get(key);
			// break;
			// case "2":
			// lossTotal += sumOfCurrency.get(key);
			case "3":
				setaCRTotal(sumOfCurrency.get(key));
				break;
			case "4":
				rIPTotal = sumOfCurrency.get(key);
				break;
			case "5":
				taxTotal = sumOfCurrency.get(key);
				break;
			case "6":
				othersTotal = sumOfCurrency.get(key);
				break;
			// summary when case in ("1", "2")
			default:
				lossTotal += sumOfCurrency.get(key);

			}

		}

		grossTotal = lossTotal;
		netTotal = lossTotal - rIPTotal + taxTotal + othersTotal;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public Double getGrossTotal() {
		return grossTotal;
	}

	public void setGrossTotal(Double grossTotal) {
		this.grossTotal = grossTotal;
	}

	public Double getNetTotal() {
		return netTotal;
	}

	public void setNetTotal(Double netTotal) {
		this.netTotal = netTotal;
	}

	public Double getaCRTotal() {
		return aCRTotal;
	}

	public void setaCRTotal(Double aCRTotal) {
		this.aCRTotal = aCRTotal;
	}

	public Double getLossTotal() {
		return lossTotal;
	}

	public void setLossTotal(Double lossTotal) {
		this.lossTotal = lossTotal;
	}

	public Double getrIPTotal() {
		return rIPTotal;
	}

	public void setrIPTotal(Double rIPTotal) {
		this.rIPTotal = rIPTotal;
	}

	public Double getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(Double taxTotal) {
		this.taxTotal = taxTotal;
	}

	public Double getOthersTotal() {
		return othersTotal;
	}

	public void setOthersTotal(Double othersTotal) {
		this.othersTotal = othersTotal;
	}

}
