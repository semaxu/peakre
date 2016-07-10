/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.model.PDFModel;

/**
 * Date: Mar 23, 2016 11:28:16 AM
 * 
 * @author weiping.wang
 *
 */
public class ResultConditionIndepVO {
	// Section No.
	private String sectionNo;
	// Result Independent Condition
	private String resultCondition;
	// Rate (%)
	private String rate;
	// Usage
	private String usage;

	public String getSectionNo() {
		return sectionNo;
	}

	public ResultConditionIndepVO() {

	}

	public ResultConditionIndepVO(String resultCondition) {
		this.resultCondition = resultCondition;
	}

	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}

	public String getResultCondition() {
		return resultCondition;
	}

	public void setResultCondition(String resultCondition) {
		this.resultCondition = resultCondition;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

}
