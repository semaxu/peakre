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

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_RETRO_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_RETRO_LOG")
@NamedQueries({ @NamedQuery(name = "TRiContRetroLog.findAll", query = "SELECT t FROM TRiContRetroLog t"),
		@NamedQuery(name = "TRiContRetroLog.findByRetroCompIdAndOperateId", query = " FROM TRiContRetroLog s WHERE s.retroCompId = :retroCompId and s.operateId = :operateId "),
		@NamedQuery(name = "TRiContRetroLog.loadByCompIdAndOperateId", query = "SELECT t FROM TRiContRetroLog t WHERE t.compId=:compId and t.operateId = :operateId ") })
public class TRiContRetroLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "COMP_ID")
	private Long compId;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "RETRO_COMP_ID")
	private Long retroCompId;

	@Column(name = "RETRO_ID")
	private Long retroId;

	@Column(name = "\"SEQUENCE\"")
	private Long sequence;

	@Column(name = "BRIEF_TEXT")
	private String briefText;

	@Column(name = "CONTRACT_NATURE")
	private String contractNature;

	@Column(name = "LIMIT_TYPE")
	private String limitType;

	@Column(name = "RETRO_SECTION_NAME")
	private String retroSectionName;

	@Column(name = "RETRO_CONTRACT_CODE")
	private String retroContractCode;

	public TRiContRetroLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getCompId() {
		return this.compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public Long getRetroCompId() {
		return this.retroCompId;
	}

	public void setRetroCompId(Long retroCompId) {
		this.retroCompId = retroCompId;
	}

	public Long getRetroId() {
		return this.retroId;
	}

	public void setRetroId(Long retroId) {
		this.retroId = retroId;
	}

	public Long getSequence() {
		return this.sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getBriefText() {
		return briefText;
	}

	public void setBriefText(String briefText) {
		this.briefText = briefText;
	}

	public String getContractNature() {
		return contractNature;
	}

	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}

	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public String getRetroSectionName() {
		return retroSectionName;
	}

	public void setRetroSectionName(String retroSectionName) {
		this.retroSectionName = retroSectionName;
	}

	public String getRetroContractCode() {
		return retroContractCode;
	}

	public void setRetroContractCode(String retroContractCode) {
		this.retroContractCode = retroContractCode;
	}

	@Override
	public Long getPrimaryKey() {
		return logId;
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.logId = key;
	}
}