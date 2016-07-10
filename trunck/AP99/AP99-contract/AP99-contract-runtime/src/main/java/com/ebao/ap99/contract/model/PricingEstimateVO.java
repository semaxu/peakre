package com.ebao.ap99.contract.model;

import java.util.ArrayList;
import java.util.List;

public class PricingEstimateVO {
	private Long pricingId;

	private Long contCompId;

	private Long parentId;

	private String contractNature;

	private String contractCategory;

	private String earningPartner;

	private String sectionName;

	private String writtenPartner;

	private String isActive;

	private String actualized;

	private String operateType;

	private List<PricingEstimateItemVO> hisList = new ArrayList<PricingEstimateItemVO>();

	private List<PricingEstimateItemVO> pricingItemList = new ArrayList<PricingEstimateItemVO>();
	private String remark;

	public Long getPricingId() {
		return pricingId;
	}

	public void setPricingId(Long pricingId) {
		this.pricingId = pricingId;
	}

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getEarningPartner() {
		return earningPartner;
	}

	public void setEarningPartner(String earningPartner) {
		this.earningPartner = earningPartner;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getWrittenPartner() {
		return writtenPartner;
	}

	public void setWrittenPartner(String writtenPartner) {
		this.writtenPartner = writtenPartner;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public List<PricingEstimateItemVO> getHisList() {
		return hisList;
	}

	public void setHisList(List<PricingEstimateItemVO> hisList) {
		this.hisList = hisList;
	}

	public List<PricingEstimateItemVO> getPricingItemList() {
		return pricingItemList;
	}

	public void setPricingItemList(List<PricingEstimateItemVO> pricingItemList) {
		this.pricingItemList = pricingItemList;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getActualized() {
		return actualized;
	}

	public void setActualized(String actualized) {
		this.actualized = actualized;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getContractNature() {
		return contractNature;
	}

	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(String contractCategory) {
		this.contractCategory = contractCategory;
	}

}
