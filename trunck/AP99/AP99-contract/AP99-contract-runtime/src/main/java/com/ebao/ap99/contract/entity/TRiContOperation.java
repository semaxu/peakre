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

/**
 * The persistent class for the T_RI_CONT_OPERATION database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_OPERATION")
@NamedQueries({ @NamedQuery(name = "TRiContOperation.findAll", query = "SELECT t FROM TRiContOperation t"),
		@NamedQuery(name = "TRiContOperation.loadContractForLog", query = "SELECT t FROM TRiContOperation t WHERE t.operateType IN ('1','2','3','6') AND t.contCompId = :contCompId ORDER BY t.operateId DESC"),
		@NamedQuery(name = "TRiContOperation.loadContractForLogByOperateId", query = "SELECT t FROM TRiContOperation t WHERE t.operateType IN ('1','2','3','6') AND t.contCompId = :contCompId AND t.operateId < :operateId ORDER BY t.operateId DESC") })
public class TRiContOperation extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Column(name = "OPERATE_TYPE")
	private String operateType;

	@Column(name = "ENDO_ID")
	private Long endoId;

	@Column(name = "CONTRACT_STATUS")
	private String status;

	public TRiContOperation() {
	}

	public TRiContOperation(Long contCompId, String operateType, Long endoId, String status) {
		super();
		this.contCompId = contCompId;
		this.operateType = operateType;
		this.endoId = endoId;
		this.status = status;
	}

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	// public BigDecimal getInsertBy() {
	// return this.insertBy;
	// }
	//
	// public void setInsertBy(BigDecimal insertBy) {
	// this.insertBy = insertBy;
	// }
	//
	// public Date getInsertTime() {
	// return this.insertTime;
	// }
	//
	// public void setInsertTime(Date insertTime) {
	// this.insertTime = insertTime;
	// }

	public String getOperateType() {
		return this.operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	@Override
	public Long getPrimaryKey() {
		return operateId;
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.operateId = key;
	}

	public Long getEndoId() {
		return endoId;
	}

	public void setEndoId(Long endoId) {
		this.endoId = endoId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}