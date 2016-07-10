/**
 * 
 */
package com.ebao.ap99.claim.model;

/**
 * @author yujie.zhang
 *
 */
public class ClaimSectionInfo {

	private String brokerName = "";
	private String cedentCountry = "";
	private String cedentName = "";
	private String contractCode = "";
	private String contractId = "";
	private String contractNature = "";
	private String frontingFlag = "";
	private String retrocessionFlag = "";
	private String sectionCode = "";
	private Long sectionId;
	private String sectionName = "";
	private String uwYear = "";
	private String fullName = "";
	private String refType;

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public String getCedentCountry() {
		return cedentCountry;
	}

	public void setCedentCountry(String cedentCountry) {
		this.cedentCountry = cedentCountry;
	}

	public String getCedentName() {
		return cedentName;
	}

	public void setCedentName(String cedentName) {
		this.cedentName = cedentName;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getContractNature() {
		return contractNature;
	}

	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}

	public String getFrontingFlag() {
		return frontingFlag;
	}

	public void setFrontingFlag(String frontingFlag) {
		this.frontingFlag = frontingFlag;
	}

	public String getRetrocessionFlag() {
		return retrocessionFlag;
	}

	public void setRetrocessionFlag(String retrocessionFlag) {
		this.retrocessionFlag = retrocessionFlag;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

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

	public String getUwYear() {
		return uwYear;
	}

	public void setUwYear(String uwYear) {
		this.uwYear = uwYear;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

}
