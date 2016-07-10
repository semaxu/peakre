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

import com.ebao.ap99.parent.DataTypeConvertor;
import com.ebao.ap99.parent.model.TreeModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author yujie.zhang
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EventInfo {
	
	private Date dateOfCreation;
	private Date dateOfLossFrom ;
	private Date dateOfLossTo ;
	private String eventCode;
	@XmlTransient
	private String largeLossRemindLvl1Flag = "";
	@XmlTransient
	private String largeLossRemindLvl2Flag = "";
	private String lossDesc = "";
	@XmlTransient
	private long operatingBy ;
	private String remark = "";
	private String taskOwner;
	@XmlTransient
	private BigDecimal totalLoss ;
	private String causeOfLoss;
	@XmlTransient
	private long eventId;
	@XmlTransient
	private String contractCode;
	@XmlTransient
	private String cedentName;
	@XmlTransient
	private String claimNo;
	@XmlTransient
	private Long insertBy;
	@XmlTransient
	private Date insertTime;
	
	
	private String reserveUpdateRemarkRetro;
	private List<ReserveInfo> reserveListRetro;
	private List<SettlementInfo> settlementListRetro;
	@XmlTransient
	private List<ReserveInfo> deleteReserveList;
	@XmlTransient
	private List<SettlementItemInfo> deleteSettlementItemList;
	@XmlTransient
	private List<ReserveSummaryInfo> reserveSummaryRetro;
	@XmlTransient
	private List<ClaimSummaryInfo> eventSummary;
	@XmlTransient
	private List<ClaimRecords> claimRecordsList;
	//private String contractCode;
	@XmlTransient
	private Long uwYear;
	@XmlTransient
	private String contractType;
	@XmlTransient
	private String brokerName;
	@XmlTransient
	@JsonIgnore
	private String relatedClaim;
	@XmlTransient
	private List<String> relatedClaimList;
	@XmlTransient
	private List<TreeModel> claimNos;
	@XmlTransient
	private BigDecimal totalIncurredLoss;
	@XmlTransient
	private List<ClaimRecordsSummary> claimRecordsSummary;
	
	

	public String getRelatedClaim() {
		return relatedClaim;
	}

	public void setRelatedClaim(String relatedClaim) {
		this.relatedClaimList = DataTypeConvertor.transStringToSelectTree(relatedClaim);
		this.relatedClaim = relatedClaim;
	}

	public List<String> getRelatedClaimList() {
		return relatedClaimList;
	}

	public void setRelatedClaimList(List<String> relatedClaimList) {
		this.relatedClaim = DataTypeConvertor.parseSelectTreeToString(relatedClaimList);
		this.relatedClaimList = relatedClaimList;
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
	public Long getUwYear() {
		return uwYear;
	}
	public void setUwYear(Long uwYear) {
		this.uwYear = uwYear;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String getBrokerName() {
		return brokerName;
	}
	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}
	
	private int pageIndex ;
	private int countPerPage;

	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getCountPerPage() {
		return countPerPage;
	}
	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getCedentName() {
		return cedentName;
	}
	public void setCedentName(String cedentName) {
		this.cedentName = cedentName;
	}
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getReserveUpdateRemarkRetro() {
		return reserveUpdateRemarkRetro;
	}
	public void setReserveUpdateRemarkRetro(String reserveUpdateRemarkRetro) {
		this.reserveUpdateRemarkRetro = reserveUpdateRemarkRetro;
	}
	public  List<ReserveInfo> getReserveListRetro() {
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
	public List<ReserveSummaryInfo> getReserveSummaryRetro() {
		return reserveSummaryRetro;
	}
	public void setReserveSummaryRetro(List<ReserveSummaryInfo> reserveSummaryRetro) {
		this.reserveSummaryRetro = reserveSummaryRetro;
	}
	
	
	public long getOperatingBy() {
		return operatingBy;
	}
	public void setOperatingBy(long operatingBy) {
		this.operatingBy = operatingBy;
	}
	public BigDecimal getTotalLoss() {
		return totalLoss;
	}
	public void setTotalLoss(BigDecimal totalLoss) {
		this.totalLoss = totalLoss;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getLargeLossRemindLvl1Flag() {
		return largeLossRemindLvl1Flag;
	}
	public void setLargeLossRemindLvl1Flag(String largeLossRemindLvl1Flag) {
		this.largeLossRemindLvl1Flag = largeLossRemindLvl1Flag;
	}
	public String getLargeLossRemindLvl2Flag() {
		return largeLossRemindLvl2Flag;
	}
	public void setLargeLossRemindLvl2Flag(String largeLossRemindLvl2Flag) {
		this.largeLossRemindLvl2Flag = largeLossRemindLvl2Flag;
	}
	public String getLossDesc() {
		return lossDesc;
	}
	public void setLossDesc(String lossDesc) {
		this.lossDesc = lossDesc;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTaskOwner() {
		return taskOwner;
	}
	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}
	
	public String getCauseOfLoss() {
		return causeOfLoss;
	}
	public void setCauseOfLoss(String causeOfLoss) {
		this.causeOfLoss = causeOfLoss;
	}
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	public List<ClaimRecords> getClaimRecordsList() {
		return claimRecordsList;
	}
	public void setClaimRecordsList(List<ClaimRecords> claimRecordsList) {
		this.claimRecordsList = claimRecordsList;
	}
	public BigDecimal getTotalIncurredLoss() {
		return totalIncurredLoss;
	}
	public void setTotalIncurredLoss(BigDecimal totalIncurredLoss) {
		this.totalIncurredLoss = totalIncurredLoss;
	}
	public List<TreeModel> getClaimNos() {
		return claimNos;
	}
	public void setClaimNos(List<TreeModel> claimNos) {
		this.claimNos = claimNos;
	}
	public List<ClaimRecordsSummary> getClaimRecordsSummary() {
		return claimRecordsSummary;
	}
	public void setClaimRecordsSummary(List<ClaimRecordsSummary> claimRecordsSummary) {
		this.claimRecordsSummary = claimRecordsSummary;
	}
	public List<ClaimSummaryInfo> getEventSummary() {
		return eventSummary;
	}
	public void setEventSummary(List<ClaimSummaryInfo> eventSummary) {
		this.eventSummary = eventSummary;
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

	
}
