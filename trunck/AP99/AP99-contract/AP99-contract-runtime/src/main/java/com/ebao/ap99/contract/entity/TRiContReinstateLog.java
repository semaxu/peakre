package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the T_RI_CONT_REINSTATE_LOG database table.
 * 
 */
@Entity
@Table(name="T_RI_CONT_REINSTATE_LOG")
@NamedQuery(name="TRiContReinstateLog.findAll", query="SELECT t FROM TRiContReinstateLog t")
public class TRiContReinstateLog extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="LOG_ID")
	private Long logId;

	@Column(name="CONT_COMP_ID")
	private Long contCompId;

	@Column(name="EXCH_CALC")
	private String exchCalc;


	@Column(name="REIN_CURRENCY")
	private String reinCurrency;

	@Column(name="REIN_NUM")
	private BigDecimal reinNum;

	@Column(name="REIN_TYPE")
	private String reinType;

	@Column(name="REIN_ID")
	private Long reinId;
	
	@Column(name="OPERATE_ID")
	private Long operateId;

	public TRiContReinstateLog() {
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

	public String getExchCalc() {
		return this.exchCalc;
	}

	public void setExchCalc(String exchCalc) {
		this.exchCalc = exchCalc;
	}


	public String getReinCurrency() {
		return this.reinCurrency;
	}

	public void setReinCurrency(String reinCurrency) {
		this.reinCurrency = reinCurrency;
	}

	public BigDecimal getReinNum() {
		return this.reinNum;
	}

	public void setReinNum(BigDecimal reinNum) {
		this.reinNum = reinNum;
	}

	public String getReinType() {
		return this.reinType;
	}

	public void setReinType(String reinType) {
		this.reinType = reinType;
	}

	@Override
	public Long getPrimaryKey() {
		// TODO Auto-generated method stub
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

	public Long getReinId() {
		return reinId;
	}

	public void setReinId(Long reinId) {
		this.reinId = reinId;
	}

}