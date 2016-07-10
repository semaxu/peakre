package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.ebao.ap99.parent.BaseEntityImpl;


/**
 * The persistent class for the T_RI_CONTRACT_AREAPERIL_LOG database table.
 * 
 */
@Entity
@Table(name="T_RI_CONTRACT_AREAPERIL_LOG")
@NamedQuery(name="TRiContractAreaperilLog.findAll", query="SELECT t FROM TRiContractAreaperilLog t")
public class TRiContractAreaperilLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="LOG_ID")
	private Long logId;

	private String covered;

	@Column(name="COVERED_REMARK")
	private String coveredRemark;

	@Column(name="OPERATE_ID")
	private Long operateId;

	private String peril;

	@Column(name="PERIL_REMARK")
	private String perilRemark;

	@Column(name="CONT_COMP_ID")
	private Long contCompId;

	public TRiContractAreaperilLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getCovered() {
		return this.covered;
	}

	public void setCovered(String covered) {
		this.covered = covered;
	}

	public String getCoveredRemark() {
		return this.coveredRemark;
	}

	public void setCoveredRemark(String coveredRemark) {
		this.coveredRemark = coveredRemark;
	}

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public String getPeril() {
		return this.peril;
	}

	public void setPeril(String peril) {
		this.peril = peril;
	}

	public String getPerilRemark() {
		return this.perilRemark;
	}

	public void setPerilRemark(String perilRemark) {
		this.perilRemark = perilRemark;
	}

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	@Override
	public Long getPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPrimaryKey(Long paramLong) {
		// TODO Auto-generated method stub
		
	}

}