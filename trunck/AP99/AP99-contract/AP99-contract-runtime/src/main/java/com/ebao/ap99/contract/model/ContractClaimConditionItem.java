/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Date: Jan 15, 2016 4:07:07 PM
 * 
 * @author weiping.wang
 *
 */
public class ContractClaimConditionItem {
	private Long itemId;
	private String cateId;
	private Long contCompId;
	private BigDecimal percentage;
	private BigDecimal amount;

	@JsonIgnore
	private Boolean isCheckFlag;
	private String isCheck;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getIsCheck() {
		// this.isCheck = String.valueOf(isCheckFlag);
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheckFlag = Boolean.parseBoolean(isCheck);
		this.isCheck = isCheck;
	}

	public Boolean getIsCheckFlag() {
		// this.isCheckFlag = Boolean.parseBoolean(isCheck);
		return isCheckFlag;
	}

	public void setIsCheckFlag(Boolean isCheckFlag) {
		this.isCheck = String.valueOf(isCheckFlag);
		this.isCheckFlag = isCheckFlag;
	}

}
