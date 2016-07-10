/**
 * 
 */
package com.ebao.ap99.claim.model;

import java.util.Date;


/**
 * @author yujie.zhang
 *
 */
public class SettlementLogInfo {
	
	private long settleLogId;
	private String desicion;
	private long insertBy;
	private Date insertTime;
	private String operateBy;
	private Date operateDate;
	private String remark;
	private Long updateBy;
	private Date updateTime;
	private Long relatedSettlementId;
	public long getSettleLogId() {
		return settleLogId;
	}
	public void setSettleLogId(long settleLogId) {
		this.settleLogId = settleLogId;
	}
	public String getDesicion() {
		return desicion;
	}
	public void setDesicion(String desicion) {
		this.desicion = desicion;
	}
	public long getInsertBy() {
		return insertBy;
	}
	public void setInsertBy(long insertBy) {
		this.insertBy = insertBy;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public String getOperateBy() {
		return operateBy;
	}
	public void setOperateBy(String operateBy) {
		this.operateBy = operateBy;
	}
	public Date getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getRelatedSettlementId() {
		return relatedSettlementId;
	}
	public void setRelatedSettlementId(Long relatedSettlementId) {
		this.relatedSettlementId = relatedSettlementId;
	}
	
}
