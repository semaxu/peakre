/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.model.PDFModel;

import java.math.BigDecimal;

/**
 * Date: Mar 23, 2016 11:38:28 AM
 * 
 * @author weiping.wang
 *
 */
public class PremiumsVO {
	// Section No.
	private String sectionNo;
	// GNPI
	private BigDecimal gnpi;
	// Rate
	private String rate;
	// (100%) Premium
	private BigDecimal perPremium;
	// Minimum Premium
	private BigDecimal minPremium;
	// Deposit Premium
	private BigDecimal depPremium;
	// Premium (Our Share)
	private BigDecimal PremiumOurShare;
	// Minimum Premium (Our Share)
	private BigDecimal minPremiumOurShare;
	// No. of Reinstatements
	private String reinNo;
	// Written Line
	private String writtenLine;
	// Signed Line
	private String signedLine;

	public String getSectionNo() {
		return sectionNo;
	}

	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}

	public BigDecimal getGnpi() {
		return gnpi;
	}

	public void setGnpi(BigDecimal gnpi) {
		this.gnpi = gnpi;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public BigDecimal getPerPremium() {
		return perPremium;
	}

	public void setPerPremium(BigDecimal perPremium) {
		this.perPremium = perPremium;
	}

	public BigDecimal getMinPremium() {
		return minPremium;
	}

	public void setMinPremium(BigDecimal minPremium) {
		this.minPremium = minPremium;
	}

	public BigDecimal getDepPremium() {
		return depPremium;
	}

	public void setDepPremium(BigDecimal depPremium) {
		this.depPremium = depPremium;
	}

	public BigDecimal getPremiumOurShare() {
		return PremiumOurShare;
	}

	public void setPremiumOurShare(BigDecimal premiumOurShare) {
		PremiumOurShare = premiumOurShare;
	}

	public BigDecimal getMinPremiumOurShare() {
		return minPremiumOurShare;
	}

	public void setMinPremiumOurShare(BigDecimal minPremiumOurShare) {
		this.minPremiumOurShare = minPremiumOurShare;
	}

	public String getReinNo() {
		return reinNo;
	}

	public void setReinNo(String reinNo) {
		this.reinNo = reinNo;
	}

	public String getWrittenLine() {
		return writtenLine;
	}

	public void setWrittenLine(String writtenLine) {
		this.writtenLine = writtenLine;
	}

	public String getSignedLine() {
		return signedLine;
	}

	public void setSignedLine(String signedLine) {
		this.signedLine = signedLine;
	}

}
