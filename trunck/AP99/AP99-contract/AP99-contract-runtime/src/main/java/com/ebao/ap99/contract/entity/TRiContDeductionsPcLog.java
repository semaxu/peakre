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

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_PC_SLID_DETAIL_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_DEDUCTIONS_PC_LOG")
@NamedQueries({ @NamedQuery(name = "TRiContDeductionsPcLog.findAll", query = "SELECT t FROM TRiContDeductionsPcLog t "),
		@NamedQuery(name = "TRiContDeductionsPcLog.loadByDeductionsIdAndOperateId", query = "SELECT t FROM TRiContDeductionsPcLog t WHERE t.deductionsId=:deductionsId and t.operateId = :operateId ") })
public class TRiContDeductionsPcLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "DEDUCTIONS_ID")
	private Long deductionsId;

	@Column(name = "PC_SLIDING_ID")
	private Long pcSlidingId;

	@Column(name = "MAXIMUM_LOSS_PC")
	private BigDecimal maximumLossPc;

	@Column(name = "MAXIMUM_R_I_PC")
	private BigDecimal maximumRIPc;

	@Column(name = "MINIMUM_LOSS_PC")
	private BigDecimal minimumLossPc;

	@Column(name = "MINIMUM_R_I_PC")
	private BigDecimal minimumRIPc;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public TRiContDeductionsPcLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getDeductionsId() {
		return this.deductionsId;
	}

	public void setDeductionsId(Long deductionsId) {
		this.deductionsId = deductionsId;
	}

	public BigDecimal getMaximumLossPc() {
		return this.maximumLossPc;
	}

	public void setMaximumLossPc(BigDecimal maximumLossPc) {
		this.maximumLossPc = maximumLossPc;
	}

	public BigDecimal getMaximumRIPc() {
		return this.maximumRIPc;
	}

	public void setMaximumRIPc(BigDecimal maximumRIPc) {
		this.maximumRIPc = maximumRIPc;
	}

	public BigDecimal getMinimumLossPc() {
		return this.minimumLossPc;
	}

	public void setMinimumLossPc(BigDecimal minimumLossPc) {
		this.minimumLossPc = minimumLossPc;
	}

	public BigDecimal getMinimumRIPc() {
		return this.minimumRIPc;
	}

	public void setMinimumRIPc(BigDecimal minimumRIPc) {
		this.minimumRIPc = minimumRIPc;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getLogId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setLogId(logId);
	}

	public Long getPcSlidingId() {
		return pcSlidingId;
	}

	public void setPcSlidingId(Long pcSlidingId) {
		this.pcSlidingId = pcSlidingId;
	}

}