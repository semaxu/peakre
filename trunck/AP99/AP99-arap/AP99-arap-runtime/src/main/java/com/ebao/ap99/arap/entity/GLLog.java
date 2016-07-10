package com.ebao.ap99.arap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;


/**
 * The persistent class for the T_RI_GL_LOG database table.
 * 
 */
@Entity
@Table(name="T_RI_GL_LOG")
@NamedQuery(name="GLLog.findAll", query="SELECT g FROM GLLog g")
public class GLLog extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name="LOG_ID")
	private Long logId;

	@Column(name="LOG_CONTENT")
	private String logContect;

	@Column(name="REF_ID")
	private Long refId;

	@Column(name="REF_TYPE")
	private Integer refType;

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getLogContect() {
		return logContect;
	}

	public void setLogContect(String logContect) {
		this.logContect = logContect;
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public Integer getRefType() {
		return refType;
	}

	public void setRefType(Integer refType) {
		this.refType = refType;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getLogId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setLogId(key);
	}
}