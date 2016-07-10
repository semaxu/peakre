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
 * The persistent class for the T_RI_CONTRACT_MORE_INFO_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONTRACT_MORE_INFO_LOG")
@NamedQuery(name = "TRiContractMoreInfoLog.findAll", query = "SELECT t FROM TRiContractMoreInfoLog t")
public class TRiContractMoreInfoLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "LOB")
	private String loB;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Column(name = "COVERED_AREA")
	private String coveredArea;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "PROTECTION_BASIC")
	private String protectionBasic;

	@Column(name = "COB")
	private String coB;

	@Column(name = "UW_AREA")
	private String uwArea;

	private String covered;

	@Column(name = "COVERED_REMARK")
	private String coveredRemark;

	private String peril;

	public TRiContractMoreInfoLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getLoB() {
		return loB;
	}

	public void setLoB(String loB) {
		this.loB = loB;
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getCoveredArea() {
		return this.coveredArea;
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

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public String getProtectionBasic() {
		return this.protectionBasic;
	}

	public void setProtectionBasic(String protectionBasic) {
		this.protectionBasic = protectionBasic;
	}

	public String getCoB() {
		return coB;
	}

	public void setCoB(String coB) {
		this.coB = coB;
	}

	public String getUwArea() {
		return this.uwArea;
	}

	public void setUwArea(String uwArea) {
		this.uwArea = uwArea;
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

	public String getCoveredRemark() {
		return coveredRemark;
	}

	public void setCoveredRemark(String coveredRemark) {
		this.coveredRemark = coveredRemark;
	}

	public String getPeril() {
		return peril;
	}

	public void setPeril(String peril) {
		this.peril = peril;
	}

}