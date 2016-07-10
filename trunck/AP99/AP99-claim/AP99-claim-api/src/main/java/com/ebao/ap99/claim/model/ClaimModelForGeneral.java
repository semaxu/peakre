package com.ebao.ap99.claim.model;

import java.math.BigDecimal;
import java.util.Date;

public class ClaimModelForGeneral {

	private Long claimId;
	private String claimNo;
	private String sectionName;
    private Date lossStartDate;
    private String caseOfLoss;
	private BigDecimal lossReverse;
	private BigDecimal lossPaid;
	private String status;
	
	
	public Long getClaimId() {
		return claimId;
	}
	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public Date getLossStartDate() {
		return lossStartDate;
	}
	public void setLossStartDate(Date lossStartDate) {
		this.lossStartDate = lossStartDate;
	}
	public String getCaseOfLoss() {
		return caseOfLoss;
	}
	public void setCaseOfLoss(String caseOfLoss) {
		this.caseOfLoss = caseOfLoss;
	}
	public BigDecimal getLossReverse() {
		return lossReverse;
	}
	public void setLossReverse(BigDecimal lossReverse) {
		this.lossReverse = lossReverse;
	}
	public BigDecimal getLossPaid() {
		return lossPaid;
	}
	public void setLossPaid(BigDecimal lossPaid) {
		this.lossPaid = lossPaid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
