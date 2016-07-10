package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_DEDUCTIONS_ATT_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_DEDUCTIONS_ATT_LOG")
@NamedQueries({
		@NamedQuery(name = "TRiContDeductionsAttLog.findAll", query = "SELECT t FROM TRiContDeductionsAttLog t"),
		@NamedQuery(name = "TRiContDeductionsAttLog.loadBySlidingIdAndOperateId", query = "SELECT t FROM TRiContDeductionsAttLog t WHERE t.slidingId=:slidingId and t.operateId = :operateId ") })
public class TRiContDeductionsAttLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "ATTACHMENT_ID")
	private Long attachmentId;

	private BigDecimal commission;

	@Column(name = "LR_FROM")
	private BigDecimal lrFrom;

	@Column(name = "LR_TO")
	private BigDecimal lrTo;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "SLIDING_ID")
	private Long slidingId;

	@Column(name = "\"TYPE\"")
	private String type;

	public TRiContDeductionsAttLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getAttachmentId() {
		return this.attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public BigDecimal getCommission() {
		return this.commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getLrFrom() {
		return this.lrFrom;
	}

	public void setLrFrom(BigDecimal lrFrom) {
		this.lrFrom = lrFrom;
	}

	public BigDecimal getLrTo() {
		return this.lrTo;
	}

	public void setLrTo(BigDecimal lrTo) {
		this.lrTo = lrTo;
	}

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public Long getSlidingId() {
		return this.slidingId;
	}

	public void setSlidingId(Long slidingId) {
		this.slidingId = slidingId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public Long getPrimaryKey() {
		// TODO Auto-generated method stub
		return this.logId;
	}

	@Override
	public void setPrimaryKey(Long paramLong) {
		// TODO Auto-generated method stub
		this.setLogId(paramLong);
	}

}