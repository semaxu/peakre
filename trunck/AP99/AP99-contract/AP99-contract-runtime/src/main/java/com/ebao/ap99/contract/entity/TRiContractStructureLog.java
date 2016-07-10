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
 * The persistent class for the T_RI_CONTRACT_STRUCTURE_LOG database table.
 * 
 */

@Entity
@Table(name = "T_RI_CONTRACT_STRUCTURE_LOG")
@NamedQueries({
		@NamedQuery(name = "TRiContractStructureLog.findChildrenIdByParentId", query = "SELECT contCompId FROM TRiContractStructureLog s WHERE s.parentId = :parentId AND s.operateId = :operateId"),
		@NamedQuery(name = "TRiContractStructureLog.findByContCompIdAndOperateId", query = "FROM TRiContractStructureLog s WHERE s.contCompId = :contCompId AND s.operateId = :operateId"),
		@NamedQuery(name = "TRiContractStructureLog.findAll", query = "SELECT t FROM TRiContractStructureLog t") })
public class TRiContractStructureLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Column(name = "FULL_NAME")
	private String fullName;

	private String name;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "PARENT_ID")
	private Long parentId;

	@Column(name = "\"TYPE\"")
	private String type;

	public TRiContractStructureLog() {
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

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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