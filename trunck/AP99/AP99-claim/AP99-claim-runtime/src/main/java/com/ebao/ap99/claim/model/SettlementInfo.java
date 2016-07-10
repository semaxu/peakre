/**
 * 
 */
package com.ebao.ap99.claim.model;

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
public class SettlementInfo {

	@XmlTransient
	private Long settlementId;

	private Date dateOfReceipt;

	private String remark;

	private String settlementName;
	@XmlTransient
	private String status;
	@XmlTransient
	private String refType;

	@XmlTransient
	private Long insertBy;
	@XmlTransient
	private Date insertTime;
	@XmlTransient
	private Long relatedSettlementId;

	// private Double settlementUsdEquivalent;
	//
	// private Double settlementUsdEquivalentRetro;

	private List<SettlementItemInfo> settlementItemList;

	@XmlTransient
	private List<SettlementSummaryInfo> settlementSummary;

	@XmlTransient
	private List<SettlementSummaryInfo> settlementSummaryRetro;
	@XmlTransient
	private long refId;
	@XmlTransient
	private String businessDirection;
	
	
	public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
	}

	public String getBusinessDirection() {
		return businessDirection;
	}

	public void setBusinessDirection(String businessDirection) {
		this.businessDirection = businessDirection;
	}

	public Long getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(Long settlementId) {
		this.settlementId = settlementId;
	}

	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}

	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSettlementName() {
		return settlementName;
	}

	public void setSettlementName(String settlementName) {
		this.settlementName = settlementName;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<SettlementItemInfo> getSettlementItemList() {
		return settlementItemList;
	}

	public void setSettlementItemList(List<SettlementItemInfo> settlementItemList) {
		this.settlementItemList = settlementItemList;
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

	public List<SettlementSummaryInfo> getSettlementSummary() {
		return settlementSummary;
	}

	public void setSettlementSummary(List<SettlementSummaryInfo> settlementSummary) {
		this.settlementSummary = settlementSummary;
	}

	public List<SettlementSummaryInfo> getSettlementSummaryRetro() {
		return settlementSummaryRetro;
	}

	public void setSettlementSummaryRetro(List<SettlementSummaryInfo> settlementSummaryRetro) {
		this.settlementSummaryRetro = settlementSummaryRetro;
	}

	public Long getRelatedSettlementId() {
		return relatedSettlementId;
	}

	public void setRelatedSettlementId(Long relatedSettlementId) {
		this.relatedSettlementId = relatedSettlementId;
	}

	// public Double getSettlementUsdEquivalent() {
	// return settlementUsdEquivalent;
	// }
	//
	// public void setSettlementUsdEquivalent(Double settlementUsdEquivalent) {
	// this.settlementUsdEquivalent = settlementUsdEquivalent;
	// }
	//
	// public Double getSettlementUsdEquivalentRetro() {
	// return settlementUsdEquivalentRetro;
	// }
	//
	// public void setSettlementUsdEquivalentRetro(Double
	// settlementUsdEquivalentRetro) {
	// this.settlementUsdEquivalentRetro = settlementUsdEquivalentRetro;
	// }

}
