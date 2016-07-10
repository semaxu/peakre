package com.ebao.ap99.contract.model;

import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.parent.model.TreeModel;

public class LinkContractVO {
	private Long uwYear;
	private String linkName;
	private List<TreeModel> structureVO = new ArrayList<TreeModel>();
	
	public Long getUwYear() {
		return uwYear;
	}
	public void setUwYear(Long uwYear) {
		this.uwYear = uwYear;
	}
	public List<TreeModel> getStructureVO() {
		return structureVO;
	}
	public void setStructureVO(List<TreeModel> structureVO) {
		this.structureVO = structureVO;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	} 

}
