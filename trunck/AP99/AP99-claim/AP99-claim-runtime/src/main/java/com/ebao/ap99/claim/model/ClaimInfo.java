/**
 * 
 */
package com.ebao.ap99.claim.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author gang.wang
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ClaimInfo
// extends AbstractDomainModel
{

	// private static final long serialVersionUID = 1L;

	@XmlTransient
	private Long claimId;

	private String causeOfLoss;
	private String claimBranch;
	private String claimNo;
	private Date dateOfCreation;
	private Date dateOfLossFrom;
	private Date dateOfLossTo;
	private Date dateOfReport;
	@XmlTransient
	private Long eventId;
	@XmlTransient
	private Boolean largeLossRemindLvl1Flag = false;
	@XmlTransient
	private Boolean largeLossRemindLvl2Flag = true;
	private String lossDescription;
	private String operatingBy;
	@XmlTransient
	private String portfolioTransferFlag;
	@XmlTransient
	private Boolean portfolioTransferRemindFlag = false;
	private String remark;
	@XmlTransient
	private String status;
	private String taskOwner;
	private String reserveRemark;
	// for xmlImport
	private String eventCode;
	@XmlTransient
	private BigDecimal totalLoss;
	@XmlTransient
	private Long insertBy;
	@XmlTransient
	private Date insertTime;
	private String reserveUpdateRemark;
	private String reserveUpdateRemarkRetro;
	private List<ReserveInfo> reserveList;
	private List<SettlementInfo> settlementList;
	private List<ReserveInfo> reserveListRetro;
	private List<SettlementInfo> settlementListRetro;
	@XmlTransient
	private List<ReserveSummaryInfo> reserveSummary;
	@XmlTransient
	private List<ReserveInfo> deleteReserveList;
	@XmlTransient
	private List<SettlementItemInfo> deleteSettlementItemList;
	// private Double ReserveUsdEquivalent;
	// private Double ReserveUsdEquivalentRetro;
	@XmlTransient
	private List<ReserveSummaryInfo> reserveSummaryRetro;
	@XmlTransient
	private List<ClaimSummaryInfo> claimSummary;

	public Long getClaimId() {
		return claimId;
	}

	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}

	public String getCauseOfLoss() {
		return causeOfLoss;
	}

	public void setCauseOfLoss(String causeOfLoss) {
		this.causeOfLoss = causeOfLoss;
	}

	public String getClaimBranch() {
		return claimBranch;
	}

	public void setClaimBranch(String claimBranch) {
		this.claimBranch = claimBranch;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public Date getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public Date getDateOfLossFrom() {
		return dateOfLossFrom;
	}

	public void setDateOfLossFrom(Date dateOfLossFrom) {
		this.dateOfLossFrom = dateOfLossFrom;
	}

	public Date getDateOfLossTo() {
		return dateOfLossTo;
	}

	public void setDateOfLossTo(Date dateOfLossTo) {
		this.dateOfLossTo = dateOfLossTo;
	}

	public Date getDateOfReport() {
		return dateOfReport;
	}

	public void setDateOfReport(Date dateOfReport) {
		this.dateOfReport = dateOfReport;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Boolean getLargeLossRemindLvl1Flag() {
		return largeLossRemindLvl1Flag;
	}

	public void setLargeLossRemindLvl1Flag(Boolean largeLossRemindLvl1Flag) {
		this.largeLossRemindLvl1Flag = largeLossRemindLvl1Flag;
	}

	public Boolean getLargeLossRemindLvl2Flag() {
		return largeLossRemindLvl2Flag;
	}

	public void setLargeLossRemindLvl2Flag(Boolean largeLossRemindLvl2Flag) {
		this.largeLossRemindLvl2Flag = largeLossRemindLvl2Flag;
	}

	public String getLossDescription() {
		return lossDescription;
	}

	public void setLossDescription(String lossDescription) {
		this.lossDescription = lossDescription;
	}

	public String getOperatingBy() {
		return operatingBy;
	}

	public void setOperatingBy(String operatingBy) {
		this.operatingBy = operatingBy;
	}

	public String getPortfolioTransferFlag() {
		return portfolioTransferFlag;
	}

	public void setPortfolioTransferFlag(String portfolioTransferFlag) {
		this.portfolioTransferFlag = portfolioTransferFlag;
	}

	public Boolean getPortfolioTransferRemindFlag() {
		return portfolioTransferRemindFlag;
	}

	public void setPortfolioTransferRemindFlag(Boolean portfolioTransferRemindFlag) {
		this.portfolioTransferRemindFlag = portfolioTransferRemindFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getReserveUpdateRemark() {
		return reserveUpdateRemark;
	}

	public void setReserveUpdateRemark(String reserveUpdateRemark) {
		this.reserveUpdateRemark = reserveUpdateRemark;
	}

	public List<ReserveInfo> getReserveList() {
		return reserveList;
	}

	public void setReserveList(List<ReserveInfo> reserveList) {
		this.reserveList = reserveList;
	}

	public List<SettlementInfo> getSettlementList() {
		return settlementList;
	}

	public void setSettlementList(List<SettlementInfo> settlementList) {
		this.settlementList = settlementList;
	}

	public String getReserveRemark() {
		return reserveRemark;
	}

	public void setReserveRemark(String reserveRemark) {
		this.reserveRemark = reserveRemark;
	}

	public String getReserveUpdateRemarkRetro() {
		return reserveUpdateRemarkRetro;
	}

	public void setReserveUpdateRemarkRetro(String reserveUpdateRemarkRetro) {
		this.reserveUpdateRemarkRetro = reserveUpdateRemarkRetro;
	}

	public Long getInsertBy() {
		return insertBy;
	}

	public void setInsertBy(Long insertBy) {
		this.insertBy = insertBy;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public BigDecimal getTotalLoss() {
		return totalLoss;
	}

	public void setTotalLoss(BigDecimal totalLoss) {
		this.totalLoss = totalLoss;
	}

	public List<ReserveInfo> getReserveListRetro() {
		return reserveListRetro;
	}

	public void setReserveListRetro(List<ReserveInfo> reserveListRetro) {
		this.reserveListRetro = reserveListRetro;
	}

	public List<SettlementInfo> getSettlementListRetro() {
		return settlementListRetro;
	}

	public void setSettlementListRetro(List<SettlementInfo> settlementListRetro) {
		this.settlementListRetro = settlementListRetro;
	}

	public List<ReserveSummaryInfo> getReserveSummary() {
		return reserveSummary;
	}

	public void setReserveSummary(List<ReserveSummaryInfo> reserveSummary) {
		this.reserveSummary = reserveSummary;
	}

	public List<ReserveSummaryInfo> getReserveSummaryRetro() {
		return reserveSummaryRetro;
	}

	public void setReserveSummaryRetro(List<ReserveSummaryInfo> reserveSummaryRetro) {
		this.reserveSummaryRetro = reserveSummaryRetro;
	}

	public List<ClaimSummaryInfo> getClaimSummary() {
		return claimSummary;
	}

	public void setClaimSummary(List<ClaimSummaryInfo> claimSummary) {
		this.claimSummary = claimSummary;
	}

	public List<ReserveInfo> getDeleteReserveList() {
		return deleteReserveList;
	}

	public void setDeleteReserveList(List<ReserveInfo> deleteReserveList) {
		this.deleteReserveList = deleteReserveList;
	}

	public List<SettlementItemInfo> getDeleteSettlementItemList() {
		return deleteSettlementItemList;
	}

	public void setDeleteSettlementItemList(List<SettlementItemInfo> deleteSettlementItemList) {
		this.deleteSettlementItemList = deleteSettlementItemList;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	// public Double getReserveUsdEquivalent() {
	// return ReserveUsdEquivalent;
	// }
	//
	// public void setReserveUsdEquivalent(Double reserveUsdEquivalent) {
	// ReserveUsdEquivalent = reserveUsdEquivalent;
	// }
	//
	// public Double getReserveUsdEquivalentRetro() {
	// return ReserveUsdEquivalentRetro;
	// }
	//
	// public void setReserveUsdEquivalentRetro(Double
	// reserveUsdEquivalentRetro) {
	// ReserveUsdEquivalentRetro = reserveUsdEquivalentRetro;
	// }

}
