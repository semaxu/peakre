/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.model;

import com.ebao.ap99.contract.entity.TRiContRetro;

/**
 * Date: Jan 15, 2016 1:34:21 PM
 * 
 * @author weiping.wang
 *
 */
public class RetrocessionItemVO extends TRiContRetro {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RetrocessionItemVO() {
		super();
	}

	private String section;
	private String subSection;
	private String underlyingContract;
	private String underlyingSec;
	private String cedent;
	private String broker;
	private Long uWYear;
	private String mainLoB;
	private String operateType;
	// private String contractNature;
	// private String limitType;
	// private String retroSectionName;
	// private String briefText;

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSubSection() {
		return subSection;
	}

	public void setSubSection(String subSection) {
		this.subSection = subSection;
	}

	public String getUnderlyingContract() {
		return underlyingContract;
	}

	public void setUnderlyingContract(String underlyingContract) {
		this.underlyingContract = underlyingContract;
	}

	public String getUnderlyingSec() {
		return underlyingSec;
	}

	public void setUnderlyingSec(String underlyingSec) {
		this.underlyingSec = underlyingSec;
	}

	public String getCedent() {
		return cedent;
	}

	public void setCedent(String cedent) {
		this.cedent = cedent;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public Long getUWYear() {
		return uWYear;
	}

	public void setUWYear(Long uWYear) {
		this.uWYear = uWYear;
	}

	public String getMainLoB() {
		return mainLoB;
	}

	public void setMainLoB(String mainLoB) {
		this.mainLoB = mainLoB;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

}
