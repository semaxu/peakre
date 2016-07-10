package com.ebao.ap99.arap.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;


/**
 * The persistent class for the T_RI_GL_BATCH database table.
 * 
 */
@Entity
@Table(name="T_RI_GL_BATCH")
@NamedQuery(name="GLBatch.findAll", query="SELECT g FROM GLBatch g")
public class GLBatch extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name="GL_BATCH_ID")
	private Long glBatchId;

	@Column(name="BATCH_TYPE")
	private Integer batchType;

	@Temporal(TemporalType.DATE)
	@Column(name="FINISH_TIME")
	private Date finishTime;

	@Column(name="\"MESSAGE\"")
	private String message;

	@Temporal(TemporalType.DATE)
	@Column(name="START_TIME")
	private Date startTime;

	@Column(name="STATUS")
	private Integer status;

	public GLBatch() {
	}

	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Long getGlBatchId() {
		return glBatchId;
	}

	public void setGlBatchId(Long glBatchId) {
		this.glBatchId = glBatchId;
	}

	public Integer getBatchType() {
		return batchType;
	}

	public void setBatchType(Integer batchType) {
		this.batchType = batchType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getGlBatchId();
	}

	@Override
	public void setPrimaryKey(Long arg0) {
		this.setGlBatchId(arg0);
	}
	
}