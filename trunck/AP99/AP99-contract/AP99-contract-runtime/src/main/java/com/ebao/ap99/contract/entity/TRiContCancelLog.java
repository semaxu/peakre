package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_CANCEL_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_CANCEL_LOG")
@NamedQuery(name = "TRiContCancelLog.findAll", query = "SELECT t FROM TRiContCancelLog t")
public class TRiContCancelLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "DNOC_POLITICAL_DAY")
	private BigDecimal dnocPoliticalDay;

	@Column(name = "DNOC_POLITICAL_MONTH")
	private BigDecimal dnocPoliticalMonth;

	@Column(name = "DNOC_SANCTION_DAY")
	private BigDecimal dnocSanctionDay;

	@Column(name = "DNOC_SANCTION_MONTH")
	private BigDecimal dnocSanctionMonth;

	@Column(name = "DNOC_WAR_DAY")
	private BigDecimal dnocWarDay;

	@Column(name = "DNOC_WAR_MONTH")
	private BigDecimal dnocWarMonth;

	@Column(name = "PNOC_AUTOMATIC")
	private String pnocAutomatic;

	@Column(name = "PNOC_CEDENT_DAY")
	private BigDecimal pnocCedentDay;

	@Column(name = "PNOC_CEDENT_MONTH")
	private BigDecimal pnocCedentMonth;

	@Column(name = "PNOC_REINSURER_DAY")
	private BigDecimal pnocReinsurerDay;

	@Column(name = "PNOC_REINSURER_MONTH")
	private BigDecimal pnocReinsurerMonth;

	public TRiContCancelLog() {
	}

	@Override
	public Long getPrimaryKey() {
		return logId;
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.logId = key;
	}

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public BigDecimal getDnocPoliticalDay() {
		return dnocPoliticalDay;
	}

	public void setDnocPoliticalDay(BigDecimal dnocPoliticalDay) {
		this.dnocPoliticalDay = dnocPoliticalDay;
	}

	public BigDecimal getDnocPoliticalMonth() {
		return dnocPoliticalMonth;
	}

	public void setDnocPoliticalMonth(BigDecimal dnocPoliticalMonth) {
		this.dnocPoliticalMonth = dnocPoliticalMonth;
	}

	public BigDecimal getDnocSanctionDay() {
		return dnocSanctionDay;
	}

	public void setDnocSanctionDay(BigDecimal dnocSanctionDay) {
		this.dnocSanctionDay = dnocSanctionDay;
	}

	public BigDecimal getDnocSanctionMonth() {
		return dnocSanctionMonth;
	}

	public void setDnocSanctionMonth(BigDecimal dnocSanctionMonth) {
		this.dnocSanctionMonth = dnocSanctionMonth;
	}

	public BigDecimal getDnocWarDay() {
		return dnocWarDay;
	}

	public void setDnocWarDay(BigDecimal dnocWarDay) {
		this.dnocWarDay = dnocWarDay;
	}

	public BigDecimal getDnocWarMonth() {
		return dnocWarMonth;
	}

	public void setDnocWarMonth(BigDecimal dnocWarMonth) {
		this.dnocWarMonth = dnocWarMonth;
	}

	public String getPnocAutomatic() {
		return pnocAutomatic;
	}

	public void setPnocAutomatic(String pnocAutomatic) {
		this.pnocAutomatic = pnocAutomatic;
	}

	public BigDecimal getPnocCedentDay() {
		return pnocCedentDay;
	}

	public void setPnocCedentDay(BigDecimal pnocCedentDay) {
		this.pnocCedentDay = pnocCedentDay;
	}

	public BigDecimal getPnocCedentMonth() {
		return pnocCedentMonth;
	}

	public void setPnocCedentMonth(BigDecimal pnocCedentMonth) {
		this.pnocCedentMonth = pnocCedentMonth;
	}

	public BigDecimal getPnocReinsurerDay() {
		return pnocReinsurerDay;
	}

	public void setPnocReinsurerDay(BigDecimal pnocReinsurerDay) {
		this.pnocReinsurerDay = pnocReinsurerDay;
	}

	public BigDecimal getPnocReinsurerMonth() {
		return pnocReinsurerMonth;
	}

	public void setPnocReinsurerMonth(BigDecimal pnocReinsurerMonth) {
		this.pnocReinsurerMonth = pnocReinsurerMonth;
	}

}