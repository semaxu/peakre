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
 * Date: Mar 22, 2016 6:07:50 PM
 * 
 * @author weiping.wang
 *
 */
public class PerilsLimitsVO {
	// Section No.
	private String sectionNo;
	// Rank
	private String rank;
	// Event
	private String event;
	// Category
	private String category;
	// Event Limit (100%)
	private BigDecimal percentageEventLimit;
	// Event Limit (Our Share)
	private BigDecimal ourShareEventLimit;

	public String getSectionNo() {
		return sectionNo;
	}

	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getPercentageEventLimit() {
		return percentageEventLimit;
	}

	public void setPercentageEventLimit(BigDecimal percentageEventLimit) {
		this.percentageEventLimit = percentageEventLimit;
	}

	public BigDecimal getOurShareEventLimit() {
		return ourShareEventLimit;
	}

	public void setOurShareEventLimit(BigDecimal ourShareEventLimit) {
		this.ourShareEventLimit = ourShareEventLimit;
	}

}
