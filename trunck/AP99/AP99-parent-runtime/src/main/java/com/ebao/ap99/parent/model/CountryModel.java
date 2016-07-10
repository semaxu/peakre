/**
 * 
 */
package com.ebao.ap99.parent.model;

/**
 * @author yujie.zhang
 *
 */
public class CountryModel {

	private Long countryId;
	private String countryName;
	private String countryLevel;
	private String ifUse;
	private Long parentId;
	
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryLevel() {
		return countryLevel;
	}
	public void setCountryLevel(String countryLevel) {
		this.countryLevel = countryLevel;
	}
	public String getIfUse() {
		return ifUse;
	}
	public void setIfUse(String ifUse) {
		this.ifUse = ifUse;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}
