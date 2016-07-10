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
 * Date: Jan 21, 2016 4:07:49 PM
 * 
 * @author weiping.wang
 *
 */
public class RetrocessionVO {
	private Long contCompId;
	private Long contId;
	private Long parentId;
	private String contractNature;
	private String contractCategory;
	private String submitType;
	private String operateType;

	private List<RetrocessionItemVO> retrocessionList = new ArrayList<RetrocessionItemVO>();
	private List<RetrocessionItemVO> deleteRetrocessionList = new ArrayList<RetrocessionItemVO>();

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public Long getContId() {
		return contId;
	}

	public void setContId(Long contId) {
		this.contId = contId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getContractNature() {
		return contractNature;
	}

	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}

	public String getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(String contractCategory) {
		this.contractCategory = contractCategory;
	}

	public String getSubmitType() {
		return submitType;
	}

	public void setSubmitType(String submitType) {
		this.submitType = submitType;
	}

	public List<RetrocessionItemVO> getRetrocessionList() {
		return retrocessionList;
	}

	public void setRetrocessionList(List<RetrocessionItemVO> retrocessionList) {
		this.retrocessionList = retrocessionList;
	}

	public List<RetrocessionItemVO> getDeleteRetrocessionList() {
		return deleteRetrocessionList;
	}

	public void setDeleteRetrocessionList(List<RetrocessionItemVO> deleteRetrocessionList) {
		this.deleteRetrocessionList = deleteRetrocessionList;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

}
