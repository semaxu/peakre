package com.ebao.ap99.contract.entity;

import java.io.Serializable;

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
 * The persistent class for the T_RI_CONTRACT_STATUS_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONTRACT_STATUS_LOG")
@NamedQueries({ @NamedQuery(name = "TRiContractStatusLog.findAll", query = "SELECT t FROM TRiContractStatusLog t"),
		@NamedQuery(name = "TRiContractStatusLog.loadByContractId", query = "SELECT t FROM TRiContractStatusLog t where t.contCompId=:contCompId ORDER BY t.logId desc") })
public class TRiContractStatusLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Column(name = "ENDO_ID")
	private Long endoId;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	private String status;

	public TRiContractStatusLog() {
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

	public Long getEndoId() {
		return this.endoId;
	}

	public void setEndoId(Long endoId) {
		this.endoId = endoId;
	}

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public Long getPrimaryKey() {
		return this.logId;
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setLogId(key);
	}

}