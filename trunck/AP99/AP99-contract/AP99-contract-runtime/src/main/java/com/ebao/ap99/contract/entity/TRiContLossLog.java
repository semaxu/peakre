package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the T_RI_CONT_LOSS_LOG database table.
 * 
 */
@Entity
@Table(name="T_RI_CONT_LOSS_LOG")
@NamedQuery(name="TRiContLossLog.findAll", query="SELECT t FROM TRiContLossLog t")
public class TRiContLossLog extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="LOG_ID")
	private Long logId;

	@Column(name="CEDENT_PARTICIP")
	private BigDecimal cedentParticip;

	@Column(name="CONT_COMP_ID")
	private Long contCompId;

	@Column(name="FIRST_CALC_AFTER")
	private BigDecimal firstCalcAfter;

	@Column(name="MAX_RATIO")
	private BigDecimal maxRatio;

	@Column(name="MIN_RATIO")
	private BigDecimal minRatio;

	@Column(name="PARTICIP_BASE")
	private String participBase;

	@Column(name="SUB_CALC_EVERY")
	private BigDecimal subCalcEvery;

//	@Column(name="UPDATE_BY")
//	private BigDecimal updateBy;
//
//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_TIME")
//	private Date updateTime;

	@Column(name="LOSS_ID")
	private Long lossId;
	
	@Column(name="OPERATE_ID")
	private Long operateId;

	public TRiContLossLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public BigDecimal getCedentParticip() {
		return this.cedentParticip;
	}

	public void setCedentParticip(BigDecimal cedentParticip) {
		this.cedentParticip = cedentParticip;
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public BigDecimal getFirstCalcAfter() {
		return this.firstCalcAfter;
	}

	public void setFirstCalcAfter(BigDecimal firstCalcAfter) {
		this.firstCalcAfter = firstCalcAfter;
	}


	public BigDecimal getMaxRatio() {
		return this.maxRatio;
	}

	public void setMaxRatio(BigDecimal maxRatio) {
		this.maxRatio = maxRatio;
	}

	public BigDecimal getMinRatio() {
		return this.minRatio;
	}

	public void setMinRatio(BigDecimal minRatio) {
		this.minRatio = minRatio;
	}

	public String getParticipBase() {
		return this.participBase;
	}

	public void setParticipBase(String participBase) {
		this.participBase = participBase;
	}

	public BigDecimal getSubCalcEvery() {
		return this.subCalcEvery;
	}

	public void setSubCalcEvery(BigDecimal subCalcEvery) {
		this.subCalcEvery = subCalcEvery;
	}


	public Long getLossId() {
		return lossId;
	}

	public void setLossId(Long lossId) {
		this.lossId = lossId;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getLogId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setLogId(key);
		
	}

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

}