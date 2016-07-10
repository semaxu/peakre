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
 * Date: Mar 22, 2016 5:32:35 PM
 * 
 * @author weiping.wang
 *
 */
public class ForecastEstimateVO {
	// Section No.
	private String sectionNo;
	// P&E EPI
	private BigDecimal epi;
	// P&E Comm.
	private BigDecimal commission;
	// P&E LR
	private BigDecimal lossRatio;
	// P&E DAC
	private BigDecimal dac;

	public String getSectionNo() {
		return sectionNo;
	}

	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}

	public BigDecimal getEpi() {
		return epi;
	}

	public void setEpi(BigDecimal epi) {
		this.epi = epi;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getLossRatio() {
		return lossRatio;
	}

	public void setLossRatio(BigDecimal lossRatio) {
		this.lossRatio = lossRatio;
	}

	public BigDecimal getDac() {
		return dac;
	}

	public void setDac(BigDecimal dac) {
		this.dac = dac;
	}
}
