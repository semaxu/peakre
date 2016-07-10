/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.model.PDFModel;

/**
 * Date: Mar 22, 2016 5:39:29 PM
 * 
 * @author weiping.wang
 *
 */
public class ResultConditionDepVO {
	// Result Dependent Condition
	private String resultCondition;
	// Section No.
	private String sectionNo;
	// LCF Period
	private String lcfPeriod;
	// Rate (%)
	private String rate;
	// Comm. (%)
	private String comm;
	// L.R. (%)
	private String lr;
	// Mgmt. Exp. (%)
	private String mgmtExp;

	public ResultConditionDepVO() {

	}

	public ResultConditionDepVO(String resultCondition) {
		this.resultCondition = resultCondition;
	}

	public String getResultCondition() {
		return resultCondition;
	}

	public void setResultCondition(String resultCondition) {
		this.resultCondition = resultCondition;
	}

	public String getSectionNo() {
		return sectionNo;
	}

	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}

	public String getLcfPeriod() {
		return lcfPeriod;
	}

	public void setLcfPeriod(String lcfPeriod) {
		this.lcfPeriod = lcfPeriod;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getComm() {
		return comm;
	}

	public void setComm(String comm) {
		this.comm = comm;
	}

	public String getLr() {
		return lr;
	}

	public void setLr(String lr) {
		this.lr = lr;
	}

	public String getMgmtExp() {
		return mgmtExp;
	}

	public void setMgmtExp(String mgmtExp) {
		this.mgmtExp = mgmtExp;
	}

}
