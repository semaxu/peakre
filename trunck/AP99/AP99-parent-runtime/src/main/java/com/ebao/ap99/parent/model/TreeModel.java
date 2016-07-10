package com.ebao.ap99.parent.model;

import java.util.ArrayList;
import java.util.List;

public class TreeModel {
	private Object id;
	private Object text;
	private Object level;

	private List<TreeModel> children = new ArrayList<TreeModel>();
	
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public Object getText() {
		return text;
	}
	public void setText(Object text) {
		this.text = text;
	}
	public List<TreeModel> getChildren() {
		return children;
	}
	public void setChildren(List<TreeModel> children) {
		this.children = children;
	}
	
	public Object getLevel() {
		return level;
	}
	public void setLevel(Object level) {
		this.level = level;
	}

}
