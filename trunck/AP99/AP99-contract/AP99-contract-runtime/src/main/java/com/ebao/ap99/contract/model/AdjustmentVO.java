/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weiping.wang
 *
 */
public class AdjustmentVO {
	private Long adjustmentId;
	private Long contCompId;
	private String operateType;
	private String contractCode;
	private Long uwYear;
	private String contractCategory;
	private String broker;
	private String coBroker;
	private String mga;
	private String remark;
	private List<AdjustmentItemVO> supiSecList = new ArrayList<AdjustmentItemVO>();

	public Long getAdjustmentId() {
		return adjustmentId;
	}

	public void setAdjustmentId(Long adjustmentId) {
		this.adjustmentId = adjustmentId;
	}

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Long getUwYear() {
		return uwYear;
	}

	public void setUwYear(Long uwYear) {
		this.uwYear = uwYear;
	}

	public String getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(String contractCategory) {
		this.contractCategory = contractCategory;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public String getCoBroker() {
		return coBroker;
	}

	public void setCoBroker(String coBroker) {
		this.coBroker = coBroker;
	}

	public String getMga() {
		return mga;
	}

	public void setMga(String mga) {
		this.mga = mga;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<AdjustmentItemVO> getSupiSecList() {
		return supiSecList;
	}

	public void setSupiSecList(List<AdjustmentItemVO> supiSecList) {
		this.supiSecList = supiSecList;
	}

}
