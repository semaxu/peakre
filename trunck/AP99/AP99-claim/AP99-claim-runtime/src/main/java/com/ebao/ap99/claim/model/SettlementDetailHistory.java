/**
 * 
 */
package com.ebao.ap99.claim.model;

import java.util.Date;
import java.util.List;

import com.ebao.ap99.arap.vo.SettlementHistory;


/**
 * @author yujie.zhang
 *
 */
public class SettlementDetailHistory {
	
	private Long settlementId;
	private Date dateOfReceipt;
	private String remark;
	private String status;
	private Long insertBy;
	private Date insertTime;
	private Date dateOfPayment;
	private String payType; 
	
	private List<SettlementLogInfo> settlementLog;

	private List<SettlementHistory> settleHistory;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getDateOfPayment() {
		return dateOfPayment;
	}

	public void setDateOfPayment(Date dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}

	public List<SettlementLogInfo> getSettlementLog() {
		return settlementLog;
	}

	public void setSettlementLog(List<SettlementLogInfo> settlementLog) {
		this.settlementLog = settlementLog;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	/**
	 * @return the settleHistory
	 */
	public List<SettlementHistory> getSettleHistory() {
		return settleHistory;
	}

	/**
	 * @param settleHistory the settleHistory to set
	 */
	public void setSettleHistory(List<SettlementHistory> settleHistory) {
		this.settleHistory = settleHistory;
	}

	
	
	
	
}
