package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the T_RI_CONT_REINSTATE_ITEM_LOG database table.
 * 
 */
@Entity
@Table(name="T_RI_CONT_REINSTATE_ITEM_LOG")
@NamedQuery(name="TRiContReinstateItemLog.findAll", query="SELECT t FROM TRiContReinstateItemLog t")
public class TRiContReinstateItemLog extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="LOG_ID")
	private Long logId;

	@Column(name="REIN_AMOUNT")
	private String reinAmount;

	@Column(name="REIN_ID")
	private Long reinId;

	@Column(name="REIN_RATE")
	private BigDecimal reinRate;

	@Column(name="REIN_TIME")
	private String reinTime;

	@Column(name="REIN_TYPE")
	private String reinType;

	private String reinstate;

	@Column(name="ITEM_ID")
	private Long itemId;
	
	@Column(name="OPERATE_ID")
	private Long operateId;

	public TRiContReinstateItemLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getReinAmount() {
		return this.reinAmount;
	}

	public void setReinAmount(String reinAmount) {
		this.reinAmount = reinAmount;
	}

	public Long getReinId() {
		return this.reinId;
	}

	public void setReinId(Long reinId) {
		this.reinId = reinId;
	}

	public BigDecimal getReinRate() {
		return this.reinRate;
	}

	public void setReinRate(BigDecimal reinRate) {
		this.reinRate = reinRate;
	}

	public String getReinTime() {
		return this.reinTime;
	}

	public void setReinTime(String reinTime) {
		this.reinTime = reinTime;
	}

	public String getReinType() {
		return this.reinType;
	}

	public void setReinType(String reinType) {
		this.reinType = reinType;
	}

	public String getReinstate() {
		return this.reinstate;
	}

	public void setReinstate(String reinstate) {
		this.reinstate = reinstate;
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

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

}