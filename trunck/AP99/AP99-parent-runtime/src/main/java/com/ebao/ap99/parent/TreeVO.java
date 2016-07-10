package com.ebao.ap99.parent;

import java.util.ArrayList;
import java.util.List;

public class TreeVO {
	private Long id;
	private String text;
	private List<TreeVO> children = new ArrayList<TreeVO>();
	
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

}
