/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.model;

import java.math.BigDecimal;

/**
 * Date: Jan 13, 2016 10:23:18 PM
 * 
 * @author weiping.wang
 *
 */
public class ContractCancel {
	private Long cancelId;
	private Long contCompId;
	private BigDecimal dnocPoliticalDay;
	private BigDecimal dnocPoliticalMonth;
	private BigDecimal dnocSanctionDay;
	private BigDecimal dnocSanctionMonth;
	private BigDecimal dnocWarDay;
	private BigDecimal dnocWarMonth;
	private String pnocAutomatic;
	private BigDecimal pnocCedentDay;
	private BigDecimal pnocCedentMonth;
	private BigDecimal pnocReinsurerDay;
	private BigDecimal pnocReinsurerMonth;
	private ContractInfo contractInfo;

	public Long getCancelId() {
		return cancelId;
	}

	public void setCancelId(Long cancelId) {
		this.cancelId = cancelId;
	}

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public BigDecimal getDnocPoliticalDay() {
		return dnocPoliticalDay;
	}

	public void setDnocPoliticalDay(BigDecimal dnocPoliticalDay) {
		this.dnocPoliticalDay = dnocPoliticalDay;
	}

	public BigDecimal getDnocPoliticalMonth() {
		return dnocPoliticalMonth;
	}

	public void setDnocPoliticalMonth(BigDecimal dnocPoliticalMonth) {
		this.dnocPoliticalMonth = dnocPoliticalMonth;
	}

	public BigDecimal getDnocSanctionDay() {
		return dnocSanctionDay;
	}

	public void setDnocSanctionDay(BigDecimal dnocSanctionDay) {
		this.dnocSanctionDay = dnocSanctionDay;
	}

	public BigDecimal getDnocSanctionMonth() {
		return dnocSanctionMonth;
	}

	public void setDnocSanctionMonth(BigDecimal dnocSanctionMonth) {
		this.dnocSanctionMonth = dnocSanctionMonth;
	}

	public BigDecimal getDnocWarDay() {
		return dnocWarDay;
	}

	public void setDnocWarDay(BigDecimal dnocWarDay) {
		this.dnocWarDay = dnocWarDay;
	}

	public BigDecimal getDnocWarMonth() {
		return dnocWarMonth;
	}

	public void setDnocWarMonth(BigDecimal dnocWarMonth) {
		this.dnocWarMonth = dnocWarMonth;
	}

	public String getPnocAutomatic() {
		return pnocAutomatic;
	}

	public void setPnocAutomatic(String pnocAutomatic) {
		this.pnocAutomatic = pnocAutomatic;
	}

	public BigDecimal getPnocCedentDay() {
		return pnocCedentDay;
	}

	public void setPnocCedentDay(BigDecimal pnocCedentDay) {
		this.pnocCedentDay = pnocCedentDay;
	}

	public BigDecimal getPnocCedentMonth() {
		return pnocCedentMonth;
	}

	public void setPnocCedentMonth(BigDecimal pnocCedentMonth) {
		this.pnocCedentMonth = pnocCedentMonth;
	}

	public BigDecimal getPnocReinsurerDay() {
		return pnocReinsurerDay;
	}

	public void setPnocReinsurerDay(BigDecimal pnocReinsurerDay) {
		this.pnocReinsurerDay = pnocReinsurerDay;
	}

	public BigDecimal getPnocReinsurerMonth() {
		return pnocReinsurerMonth;
	}

	public void setPnocReinsurerMonth(BigDecimal pnocReinsurerMonth) {
		this.pnocReinsurerMonth = pnocReinsurerMonth;
	}

	public ContractInfo getContractInfo() {
		return contractInfo;
	}

	public void setContractInfo(ContractInfo contractInfo) {
		this.contractInfo = contractInfo;
	}
}
