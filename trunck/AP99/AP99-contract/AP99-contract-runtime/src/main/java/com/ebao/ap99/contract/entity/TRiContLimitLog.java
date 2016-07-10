package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.ebao.ap99.parent.BaseEntityImpl;


/**
 * The persistent class for the T_RI_CONT_LIMIT_LOG database table.
 * 
 */
@Entity
@Table(name="T_RI_CONT_LIMIT_LOG")
@NamedQuery(name="TRiContLimitLog.findAll", query="SELECT t FROM TRiContLimitLog t")
public class TRiContLimitLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="LOG_ID")
	private Long logId;

	@Column(name="AMOUNT_TYPE")
	private String amountType;

	@Column(name="CONT_COMP_ID")
	private Long contCompId;

	@Column(name="LIMIT_TYPE")
	private String limitType;

	@Column(name="PER_RISK")
	private String perRisk;

	private String unlimit;

	@Column(name="LIMIT_ID")
	private Long limitId;
	
	@Column(name="OPERATE_ID")
	private Long operateId;

	
	public TRiContLimitLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getAmountType() {
		return this.amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getLimitType() {
		return this.limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public String getPerRisk() {
		return this.perRisk;
	}

	public void setPerRisk(String perRisk) {
		this.perRisk = perRisk;
	}

	public String getUnlimit() {
		return this.unlimit;
	}

	public void setUnlimit(String unlimit) {
		this.unlimit = unlimit;
	}
	
	@Override
	public Long getPrimaryKey() {
		// TODO Auto-generated method stub
		return this.getLogId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		// TODO Auto-generated method stub
		this.setLogId(key);
	}

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public Long getLimitId() {
		return limitId;
	}

	public void setLimitId(Long limitId) {
		this.limitId = limitId;
	}

}