package com.ebao.ap99.contract.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_SUBSECTION_INFO_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_SUBSECTION_INFO_LOG")
@NamedQuery(name = "TRiContSubsectionInfoLog.findAll", query = "SELECT t FROM TRiContSubsectionInfoLog t")
public class TRiContSubsectionInfoLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "UW_AREA")
	private String uwArea;

	@Column(name = "LOB")
	private String loB;

	@Column(name = "COVERED_AREA")
	private String coveredArea;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "COB")
	private String coB;

	@Column(name = "SUBSECTION_NAME")
	private String subsectionName;

	private String covered;

	private String peril;

	public TRiContSubsectionInfoLog() {
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

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public String getUwArea() {
		return uwArea;
	}

	public void setUwArea(String uwArea) {
		this.uwArea = uwArea;
	}

	public String getLoB() {
		return loB;
	}

	public void setLoB(String loB) {
		this.loB = loB;
	}

	public String getCoveredArea() {
		return coveredArea;
	}

	public void setCoveredArea(String coveredArea) {
		this.coveredArea = coveredArea;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCoB() {
		return coB;
	}

	public void setCoB(String coB) {
		this.coB = coB;
	}

	public String getSubsectionName() {
		return this.subsectionName;
	}

	public void setSubsectionName(String subsectionName) {
		this.subsectionName = subsectionName;
	}

	@Override
	public Long getPrimaryKey() {
		return this.logId;
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setLogId(key);
	}

	public String getCovered() {
		return covered;
	}

	public void setCovered(String covered) {
		this.covered = covered;
	}

	public String getPeril() {
		return peril;
	}

	public void setPeril(String peril) {
		this.peril = peril;
	}

}