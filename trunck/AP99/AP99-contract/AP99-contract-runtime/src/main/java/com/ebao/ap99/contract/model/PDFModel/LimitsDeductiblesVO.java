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
 * Date: Mar 23, 2016 11:24:26 AM
 * 
 * @author weiping.wang
 *
 */
public class LimitsDeductiblesVO {
	// Section No.
	private String sectionNo;
	// Text for Section
	private String sectionName;
	// Type
	private String sectionType;
	// Limit(Ind.Loss)
	private BigDecimal limitInd;
	// Deductible (Ind.Loss)
	private BigDecimal deductibleInd;
	// Limit (Event)
	private BigDecimal limitEvent;
	// Deductible (Event)
	private BigDecimal deductibleEvent;
	// AAl
	private BigDecimal aal;
	// Limit (Ind.Loss) Our Share
	private BigDecimal limitIndOurShare;
	// Limit (Event) Our Share
	private BigDecimal limitEventOurShare;
	// AAL Our Share
	private BigDecimal aalOurShare;

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

	public BigDecimal getLimitInd() {
		return limitInd;
	}

	public void setLimitInd(BigDecimal limitInd) {
		this.limitInd = limitInd;
	}

	public BigDecimal getDeductibleInd() {
		return deductibleInd;
	}

	public void setDeductibleInd(BigDecimal deductibleInd) {
		this.deductibleInd = deductibleInd;
	}

	public BigDecimal getLimitEvent() {
		return limitEvent;
	}

	public void setLimitEvent(BigDecimal limitEvent) {
		this.limitEvent = limitEvent;
	}

	public BigDecimal getDeductibleEvent() {
		return deductibleEvent;
	}

	public void setDeductibleEvent(BigDecimal deductibleEvent) {
		this.deductibleEvent = deductibleEvent;
	}

	public BigDecimal getAal() {
		return aal;
	}

	public void setAal(BigDecimal aal) {
		this.aal = aal;
	}

	public BigDecimal getLimitIndOurShare() {
		return limitIndOurShare;
	}

	public void setLimitIndOurShare(BigDecimal limitIndOurShare) {
		this.limitIndOurShare = limitIndOurShare;
	}

	public BigDecimal getLimitEventOurShare() {
		return limitEventOurShare;
	}

	public void setLimitEventOurShare(BigDecimal limitEventOurShare) {
		this.limitEventOurShare = limitEventOurShare;
	}

	public BigDecimal getAalOurShare() {
		return aalOurShare;
	}

	public void setAalOurShare(BigDecimal aalOurShare) {
		this.aalOurShare = aalOurShare;
	}

}
