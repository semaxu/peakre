/**
 * 
 */
package com.ebao.ap99.arap.vo;

import java.util.Date;
import java.util.List;

import com.ebao.ap99.parent.Page;

/**
 * @author meiliang.zou
 *
 */
public class SubLedgerDTO extends Page<EntryItemInformationVO>{
	private static final long serialVersionUID = -1198860255382496538L;

	private String contractID;
	
	private Integer businessType;
	
	private String businessNumber;
	
	private String currency;
	
	private String uWYear;
	
	private List<String> uWAreaList;
	
	private Date generationDateFrom;
	
	private Date generationDateTo;
	
	private Date postDateFrom;
	
	private Date postDateTo;
	
	private Integer postStatus;
	
	private Long generalLedgerId;

	public String getContractID() {
		return contractID;
	}

	public void setContractID(String contractID) {
		this.contractID = contractID;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getBusinessNumber() {
		return businessNumber;
	}

	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getuWYear() {
		return uWYear;
	}

	public void setuWYear(String uWYear) {
		this.uWYear = uWYear;
	}

	public Date getGenerationDateFrom() {
		return generationDateFrom;
	}

	public void setGenerationDateFrom(Date generationDateFrom) {
		this.generationDateFrom = generationDateFrom;
	}

	public Date getGenerationDateTo() {
		return generationDateTo;
	}

	public void setGenerationDateTo(Date generationDateTo) {
		this.generationDateTo = generationDateTo;
	}

	public Date getPostDateFrom() {
		return postDateFrom;
	}

	public void setPostDateFrom(Date postDateFrom) {
		this.postDateFrom = postDateFrom;
	}

	public Date getPostDateTo() {
		return postDateTo;
	}

	public void setPostDateTo(Date postDateTo) {
		this.postDateTo = postDateTo;
	}

	public Integer getPostStatus() {
		return postStatus;
	}

	public void setPostStatus(Integer postStatus) {
		this.postStatus = postStatus;
	}

	public Long getGeneralLedgerId() {
		return generalLedgerId;
	}

	public void setGeneralLedgerId(Long generalLedgerId) {
		this.generalLedgerId = generalLedgerId;
	}

	public List<String> getuWAreaList() {
		return uWAreaList;
	}

	public void setuWAreaList(List<String> uWAreaList) {
		this.uWAreaList = uWAreaList;
	}
}
