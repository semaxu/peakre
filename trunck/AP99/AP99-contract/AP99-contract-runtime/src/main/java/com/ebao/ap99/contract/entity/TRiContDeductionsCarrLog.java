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
 * The persistent class for the T_RI_CONT_DEDUCTIONS_CARR_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_DEDUCTIONS_CARR_LOG")
@NamedQueries({
		@NamedQuery(name = "TRiContDeductionsCarrLog.findAll", query = "SELECT t FROM TRiContDeductionsCarrLog t"),
		@NamedQuery(name = "TRiContDeductionsCarrLog.loadBySlidingIdAndOperateId", query = "SELECT t FROM TRiContDeductionsCarrLog t WHERE t.commSlidingDetailId=:commSlidingDetailId and t.operateId = :operateId ") })
public class TRiContDeductionsCarrLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "CARRIED_FORWARDS_ID")
	private Long carriedForwardsId;

	@Column(name = "COMM_SLIDING_DETAIL_ID")
	private Long commSlidingDetailId;

	private String extinction;

	@Column(name = "NAME_")
	private String name;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "\"YEARS\"")
	private BigDecimal years;

	private String yn;

	public TRiContDeductionsCarrLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getCarriedForwardsId() {
		return this.carriedForwardsId;
	}

	public void setCarriedForwardsId(Long carriedForwardsId) {
		this.carriedForwardsId = carriedForwardsId;
	}

	public Long getCommSlidingDetailId() {
		return this.commSlidingDetailId;
	}

	public void setCommSlidingDetailId(Long commSlidingDetailId) {
		this.commSlidingDetailId = commSlidingDetailId;
	}

	public String getExtinction() {
		return this.extinction;
	}

	public void setExtinction(String extinction) {
		this.extinction = extinction;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public BigDecimal getYears() {
		return this.years;
	}

	public void setYears(BigDecimal years) {
		this.years = years;
	}

	public String getYn() {
		return this.yn;
	}

	public void setYn(String yn) {
		this.yn = yn;
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