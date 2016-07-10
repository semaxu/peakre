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
 * The persistent class for the T_RI_CONT_COMM_SLID_DETAIL_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_DEDUCTIONS_COMM_LOG")
@NamedQueries({
		@NamedQuery(name = "TRiContDeductionsCommLog.findAll", query = "SELECT t FROM TRiContDeductionsCommLog t"),
		@NamedQuery(name = "TRiContDeductionsCommLog.loadByDeductionsIdAndOperateId", query = "SELECT t FROM TRiContDeductionsCommLog t WHERE t.deductionsId=:deductionsId and t.operateId = :operateId ") })
public class TRiContDeductionsCommLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "COMM_SLIDING_DETAIL_ID")
	private Long commSlidingDetailId;

	@Column(name = "DEDUCTIONS_ID")
	private Long deductionsId;

	@Column(name = "FIRST_CAL")
	private BigDecimal firstCal;

	@Column(name = "LOSS_INCLUDE_I_B_N_R")
	private String lossIncludeIBNR;

	@Column(name = "MAXIMUM_LOSS_SS")
	private BigDecimal maximumLossSs;

	@Column(name = "MAXIMUM_R_I_SS")
	private BigDecimal maximumRISs;

	@Column(name = "MINIMUM_LOSS_SS")
	private BigDecimal minimumLossSs;

	@Column(name = "MINIMUM_R_I_SS")
	private BigDecimal minimumRISs;

	@Column(name = "SUBSEQUENT_CALC")
	private BigDecimal subsequentCalc;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getCommSlidingDetailId() {
		return commSlidingDetailId;
	}

	public void setCommSlidingDetailId(Long commSlidingDetailId) {
		this.commSlidingDetailId = commSlidingDetailId;
	}

	public Long getDeductionsId() {
		return this.deductionsId;
	}

	public void setDeductionsId(Long deductionsId) {
		this.deductionsId = deductionsId;
	}

	public BigDecimal getFirstCal() {
		return this.firstCal;
	}

	public void setFirstCal(BigDecimal firstCal) {
		this.firstCal = firstCal;
	}

	public String getLossIncludeIBNR() {
		return this.lossIncludeIBNR;
	}

	public void setLossIncludeIBNR(String lossIncludeIBNR) {
		this.lossIncludeIBNR = lossIncludeIBNR;
	}

	public BigDecimal getMaximumLossSs() {
		return this.maximumLossSs;
	}

	public void setMaximumLossSs(BigDecimal maximumLossSs) {
		this.maximumLossSs = maximumLossSs;
	}

	public BigDecimal getMaximumRISs() {
		return this.maximumRISs;
	}

	public void setMaximumRISs(BigDecimal maximumRISs) {
		this.maximumRISs = maximumRISs;
	}

	public BigDecimal getMinimumLossSs() {
		return this.minimumLossSs;
	}

	public void setMinimumLossSs(BigDecimal minimumLossSs) {
		this.minimumLossSs = minimumLossSs;
	}

	public BigDecimal getMinimumRISs() {
		return this.minimumRISs;
	}

	public void setMinimumRISs(BigDecimal minimumRISs) {
		this.minimumRISs = minimumRISs;
	}

	public BigDecimal getSubsequentCalc() {
		return this.subsequentCalc;
	}

	public void setSubsequentCalc(BigDecimal subsequentCalc) {
		this.subsequentCalc = subsequentCalc;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getLogId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setLogId(key);

	}

}