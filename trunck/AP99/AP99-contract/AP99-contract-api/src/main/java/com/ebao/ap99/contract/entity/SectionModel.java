package com.ebao.ap99.contract.entity;

import java.util.ArrayList;
import java.util.List;

public class SectionModel {
	private Long sectionId;
	private String sectionName;
	private List<String> coB = new ArrayList<String>();
	private List<String> uwArea = new ArrayList<String>();
	private String shareType;
	
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public List<String> getCoB() {
		return coB;
	}
	public void setCoB(List<String> coB) {
		this.coB = coB;
	}
	public List<String> getUwArea() {
		return uwArea;
	}
	public void setUwArea(List<String> uwArea) {
		this.uwArea = uwArea;
	}
	public String getShareType() {
		return shareType;
	}
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	
	
}
