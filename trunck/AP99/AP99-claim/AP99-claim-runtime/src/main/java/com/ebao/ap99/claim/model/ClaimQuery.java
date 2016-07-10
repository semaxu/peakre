/**
 * 
 */
package com.ebao.ap99.claim.model;


import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * @author yujie.zhang
 *
 */
public class ClaimQuery extends BaseDao<ClaimQuery>{
	private String claimNo           = "";
	private String eventId         = "";
	private String claimBranch       = "";
	private String contractID        = "";
	private String underwritingYear            = "";
	private String cedantName        = "";
	private String cedantCountry     = "";
	private String cedantReference   = "";
	private String brokerReference   = "";
	private String dateOfLossFrom;
	private String dateOfLossTo;
	private String causeOfLoss       = "";
	private String status            = "";
	private String taskOwner         = "";
	private String lossDescription           = "";
	private int pageIndex ;
    public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	private int countPerPage;
	
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getClaimBranch() {
		return claimBranch;
	}
	public void setClaimBranch(String claimBranch) {
		this.claimBranch = claimBranch;
	}
	public String getContractID() {
		return contractID;
	}
	public void setContractID(String contractID) {
		this.contractID = contractID;
	}
	
	public String getCedantName() {
		return cedantName;
	}
	public void setCedantName(String cedantName) {
		this.cedantName = cedantName;
	}
	public String getCedantCountry() {
		return cedantCountry;
	}
	public void setCedantCountry(String cedantCountry) {
		this.cedantCountry = cedantCountry;
	}
	public String getCedantReference() {
		return cedantReference;
	}
	public void setCedantReference(String cedantReference) {
		this.cedantReference = cedantReference;
	}
	public String getBrokerReference() {
		return brokerReference;
	}
	public void setBrokerReference(String brokerReference) {
		this.brokerReference = brokerReference;
	}
	
	public String getCauseOfLoss() {
		return causeOfLoss;
	}
	public void setCauseOfLoss(String causeOfLoss) {
		this.causeOfLoss = causeOfLoss;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTaskOwner() {
		return taskOwner;
	}
	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}
	
	public int getCountPerPage() {
		return countPerPage;
	}
	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}
	@Override
	public Class<ClaimQuery> getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getDateOfLossFrom() {
		return dateOfLossFrom;
	}
	public void setDateOfLossFrom(String dateOfLossFrom) {
		this.dateOfLossFrom = dateOfLossFrom;
	}
	public String getDateOfLossTo() {
		return dateOfLossTo;
	}
	public void setDateOfLossTo(String dateOfLossTo) {
		this.dateOfLossTo = dateOfLossTo;
	}
	public String getLossDescription() {
		return lossDescription;
	}
	public void setLossDescription(String lossDescription) {
		this.lossDescription = lossDescription;
	}
	public String getUnderwritingYear() {
		return underwritingYear;
	}
	public void setUnderwritingYear(String underwritingYear) {
		this.underwritingYear = underwritingYear;
	}


}
