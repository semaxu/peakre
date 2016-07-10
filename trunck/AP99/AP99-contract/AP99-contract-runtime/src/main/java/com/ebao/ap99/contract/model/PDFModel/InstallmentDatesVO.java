/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.model.PDFModel;

/**
 * Date: Mar 23, 2016 11:22:03 AM
 * 
 * @author weiping.wang
 *
 */
public class InstallmentDatesVO {
	// Section No.
	private String sectionNo;
	// Installment Due Date
	private String instalDueDate;

	public String getSectionNo() {
		return sectionNo;
	}

	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}

	public String getInstalDueDate() {
		return instalDueDate;
	}

	public void setInstalDueDate(String instalDueDate) {
		this.instalDueDate = instalDueDate;
	}

}
