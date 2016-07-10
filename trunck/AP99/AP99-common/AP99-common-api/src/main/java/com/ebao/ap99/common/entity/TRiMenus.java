package com.ebao.ap99.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;


/**
 * The persistent class for the T_RI_MENUS database table.
 * 
 */
@Entity
@Table(name="T_RI_MENUS")
@NamedQuery(name="TRiMenus.findAll", query="SELECT t FROM TRiMenus t ORDER BY t.sortNo ASC")

public class TRiMenus extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private String description;

	private String icon;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name="MENU_ID")
	private Long menuId;

	@Column(name="MENU_NAME")
	private String text;

	@Column(name="PARENT_ID")
	private Long parentId;

	@Column(name="PERMISSION_CODE")
	private String permissionCode;
	
	@Column(name="SORT_NO")
	private String sortNo;

	private String url;

	public TRiMenus() {
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getPermissionCode() {
		return this.permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public Long getPrimaryKey() {
		return menuId;
	}

	@Override
	public void setPrimaryKey(Long arg0) {
		this.menuId = arg0;
	}

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

}