package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the T_RI_CONT_CLAUSES_LOG database table.
 * 
 */
@Entity
@Table(name="T_RI_CONT_CLAUSES_LOG")
@NamedQuery(name="TRiContClausesLog.findAll", query="SELECT t FROM TRiContClausesLog t")
public class TRiContClausesLog extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="LOG_ID")
	private Long logId;

	@Column(name="CLAUSES_RECOMMEND")
	private String clausesRecommend;

	@Column(name="CLAUSES_REQUIRED")
	private String clausesRequired;

	@Column(name="CONT_COMP_ID")
	private Long contCompId;
	
	@Column(name="OPERATE_ID")
	private Long operateId;

	@Column(name="CLAUSES_ID")
	private Long clausesId;

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}


	public TRiContClausesLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getClausesRecommend() {
		return this.clausesRecommend;
	}

	public void setClausesRecommend(String clausesRecommend) {
		this.clausesRecommend = clausesRecommend;
	}

	public String getClausesRequired() {
		return this.clausesRequired;
	}

	public void setClausesRequired(String clausesRequired) {
		this.clausesRequired = clausesRequired;
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getLogId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setLogId(key);
		
	}

	public Long getClausesId() {
		return clausesId;
	}

	public void setClausesId(Long clausesId) {
		this.clausesId = clausesId;
	}

}