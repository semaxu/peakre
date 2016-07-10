/**
 * 
 */
package com.ebao.ap99.claim.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yujie.zhang
 *
 */
public class ClaimRecordsExcelModel {

	//Claim No.	Contract Section	Broker	Cedent	Section	Sub-section	Contract ID	Category	
	//Type	Entry Type	Amount	Status	Date of last update
	
	private String claimNo;
	private String contractSection;
	private String broker;
	private String cedent;
	private String section;
	private String contractId;
	private String category;
	private String busiType;
	private String entryType;
	private BigDecimal amount;
	private String status;
	private Date dateOfUpdate; 
	private String currency;
	
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getContractSection() {
		return contractSection;
	}
	public void setContractSection(String contractSection) {
		this.contractSection = contractSection;
	}
	public String getBroker() {
		return broker;
	}
	public void setBroker(String broker) {
		this.broker = broker;
	}
	public String getCedent() {
		return cedent;
	}
	public void setCedent(String cedent) {
		this.cedent = cedent;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public String getEntryType() {
		return entryType;
	}
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the dateOfUpdate
	 */
	public Date getDateOfUpdate() {
		return dateOfUpdate;
	}
	/**
	 * @param dateOfUpdate the dateOfUpdate to set
	 */
	public void setDateOfUpdate(Date dateOfUpdate) {
		this.dateOfUpdate = dateOfUpdate;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	

}
