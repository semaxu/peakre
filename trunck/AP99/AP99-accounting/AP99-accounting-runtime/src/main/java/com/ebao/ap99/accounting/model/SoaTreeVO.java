package com.ebao.ap99.accounting.model;

import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.parent.DataTypeConvertor;
import com.ebao.ap99.parent.TreeVO;

public class SoaTreeVO {
	private Long id;
	private String text;
	private List<String> coBList = new ArrayList<String>(); 
	private String cob;
	private List<String> uwAreaList = new ArrayList<String>();
	private String uwArea;
	private String shareType;
	private List<TreeVO> children = new ArrayList<TreeVO>();
	
	public List<String> getUwAreaList() {
		return uwAreaList;
	}

	public void setUwAreaList(List<String> uwAreaList) {
		this.uwArea = DataTypeConvertor.parseSelectTreeToString(uwAreaList);
		this.uwAreaList = uwAreaList;
	}
	
	

	public String getUwArea() {
		return uwArea;
	}


	public void setUwArea(String uwArea) {
		this.uwArea = uwArea;
	}
	
	public List<String> getCoBList() {
		return coBList;
	}

	public void setCoBList(List<String> coBList) {
		this.cob = DataTypeConvertor.parseSelectTreeToString(coBList);
		this.coBList = coBList;
	}
	
	public String getCob() {
		return cob;
	}

	public void setCob(String cob) {
		this.coBList = DataTypeConvertor.transStringToSelectTree(cob);
		this.cob = cob;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<TreeVO> getChildren() {
		return children;
	}
	public void setChildren(List<TreeVO> children) {
		this.children = children;
	}

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	
}
