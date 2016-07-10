package com.ebao.ap99.contract.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_ADJUSTMENT_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_ADJUSTMENT_LOG")
@NamedQuery(name = "TRiContAdjustmentLog.findAll", query = "SELECT t FROM TRiContAdjustmentLog t")
public class TRiContAdjustmentLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "ADJUSTMENT_ID")
	private Long adjustmentId;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	private String remark;

	public TRiContAdjustmentLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getAdjustmentId() {
		return adjustmentId;
	}

	public void setAdjustmentId(Long adjustmentId) {
		this.adjustmentId = adjustmentId;
	}

	@Override
	public Long getPrimaryKey() {
		return logId;
	}

	@Override
	public void setPrimaryKey(Long logId) {
		this.logId = logId;
	}
}