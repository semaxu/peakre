package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_ADJUSTMENT_ITEM_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_ADJUSTMENT_ITEM_LOG")
@NamedQueries({
		@NamedQuery(name = "TRiContAdjustmentItemLog.findAll", query = "SELECT t FROM TRiContAdjustmentItemLog t"),
		@NamedQuery(name = "TRiContAdjustmentItemLog.loadByContCompIdAndAdjustmentId", query = "SELECT t FROM TRiContAdjustmentItemLog t WHERE t.contCompId = :contCompId AND t.adjustmentId = :adjustmentId ORDER BY t.operateId DESC, t.itemId ASC") })
public class TRiContAdjustmentItemLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "ITEM_ID")
	private Long itemId;

	@Temporal(TemporalType.DATE)
	@Column(name = "ADJUSTMENT_DATE")
	private Date adjustmentDate;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Column(name = "ADJUSTMENT_ID")
	private Long adjustmentId;

	private BigDecimal amount;

	private String currency;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "USER_BY")
	private Long userBy;
	
	@Column(name = "DATE_AT")
	private Date dateAt;

	public TRiContAdjustmentItemLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Date getAdjustmentDate() {
		return this.adjustmentDate;
	}

	public void setAdjustmentDate(Date adjustmentDate) {
		this.adjustmentDate = adjustmentDate;
	}

	public Long getAdjustmentId() {
		return this.adjustmentId;
	}

	public void setAdjustmentId(Long adjustmentId) {
		this.adjustmentId = adjustmentId;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public Long getUserBy() {
		return this.userBy;
	}

	public void setUserBy(Long userBy) {
		this.userBy = userBy;
	}

	public Date getDateAt() {
		return dateAt;
	}

	public void setDateAt(Date dateAt) {
		this.dateAt = dateAt;
	}

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
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