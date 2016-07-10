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
public class ClaimRecords {

	private String claimNo;
	private String broker;
	private String cedent;
	private String contractId;
	private String sectionId;
	private BigDecimal lossReverse;
	private BigDecimal lossPaid;
	private BigDecimal retroOS;
	private BigDecimal retroPaid;
	private BigDecimal ripReserve;
	private BigDecimal ripPaid;
	private String status;
    private Date dateOfLostUpdate;
    private Long claimId;
    
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
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
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
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
	public BigDecimal getRetroOS() {
		return retroOS;
	}
	public void setRetroOS(BigDecimal retroOS) {
		this.retroOS = retroOS;
	}
	public BigDecimal getRetroPaid() {
		return retroPaid;
	}
	public void setRetroPaid(BigDecimal retroPaid) {
		this.retroPaid = retroPaid;
	}
	public BigDecimal getRipReserve() {
		return ripReserve;
	}
	public void setRipReserve(BigDecimal ripReserve) {
		this.ripReserve = ripReserve;
	}
	public BigDecimal getRipPaid() {
		return ripPaid;
	}
	public void setRipPaid(BigDecimal ripPaid) {
		this.ripPaid = ripPaid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDateOfLostUpdate() {
		return dateOfLostUpdate;
	}
	public void setDateOfLostUpdate(Date dateOfLostUpdate) {
		this.dateOfLostUpdate = dateOfLostUpdate;
	}
	public Long getClaimId() {
		return claimId;
	}
	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}
	
    
    
}
