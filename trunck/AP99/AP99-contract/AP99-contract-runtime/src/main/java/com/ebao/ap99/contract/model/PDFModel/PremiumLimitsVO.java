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
 * Date: Mar 22, 2016 5:44:53 PM
 * 
 * @author weiping.wang
 *
 */
public class PremiumLimitsVO {
	private String sectionNo;
	// Text for Section
	private String sectionName;
	// Type
	private String sectionType;
	// 100% Premium (EPI)
	private BigDecimal epi;
	// Maxium Liability / Retention
	private BigDecimal maxLiabilityRetention;
	// QS Ceded % / Lines
	private String qsCeded;
	// Cession Limit
	private BigDecimal cessionLimit;
	// Written Line
	private String writtenLine;
	// Signed Line
	private String signedLine;
	// 100% Premium (Our Share)
	private BigDecimal percentagePremium;
	// Cession Limit (Our Share)
	private BigDecimal ourShareCessionLimit;

	public String getSectionNo() {
		return sectionNo;
	}

	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getSectionType() {
		return sectionType;
	}

	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}

	public BigDecimal getEpi() {
		return epi;
	}

	public void setEpi(BigDecimal epi) {
		this.epi = epi;
	}

	public BigDecimal getMaxLiabilityRetention() {
		return maxLiabilityRetention;
	}

	public void setMaxLiabilityRetention(BigDecimal maxLiabilityRetention) {
		this.maxLiabilityRetention = maxLiabilityRetention;
	}

	public String getQsCeded() {
		return qsCeded;
	}

	public void setQsCeded(String qsCeded) {
		this.qsCeded = qsCeded;
	}

	public BigDecimal getCessionLimit() {
		return cessionLimit;
	}

	public void setCessionLimit(BigDecimal cessionLimit) {
		this.cessionLimit = cessionLimit;
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

	public BigDecimal getPercentagePremium() {
		return percentagePremium;
	}

	public void setPercentagePremium(BigDecimal percentagePremium) {
		this.percentagePremium = percentagePremium;
	}

	public BigDecimal getOurShareCessionLimit() {
		return ourShareCessionLimit;
	}

	public void setOurShareCessionLimit(BigDecimal ourShareCessionLimit) {
		this.ourShareCessionLimit = ourShareCessionLimit;
	}

}
