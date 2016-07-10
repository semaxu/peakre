package com.ebao.ap99.common.module;

import java.util.ArrayList;
import java.util.List;

public class MenusVO {
	private String icon;
	private String text;
	private String url;
	private List<MenusVO> children = new ArrayList<MenusVO>();
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<MenusVO> getChildren() {
		return children;
	}
	public void setChildren(List<MenusVO> children) {
		this.children = children;
	}
}
