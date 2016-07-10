package com.ebao.ap99.contract.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the T_RI_CONT_PRICING_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_PRICING_LOG")
@NamedQuery(name = "TRiContPricingLog.findAll", query = "SELECT t FROM TRiContPricingLog t")
public class TRiContPricingLog extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Column(name = "EARNING_PARTNER")
	private String earningPartner;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "SECTION_NAME")
	private String sectionName;

	@Column(name = "WRITTEN_PARTNER")
	private String writtenPartner;

	@Column(name = "PRICING_ID")
	private Long pricingId;

	@Column(name = "ACTUALIZED")
	private String actualized;

	@Column(name = "REMARK")
	private String remark;

	public TRiContPricingLog() {
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

	public String getEarningPartner() {
		return this.earningPartner;
	}

	public void setEarningPartner(String earningPartner) {
		this.earningPartner = earningPartner;
	}

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public String getSectionName() {
		return this.sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getWrittenPartner() {
		return this.writtenPartner;
	}

	public void setWrittenPartner(String writtenPartner) {
		this.writtenPartner = writtenPartner;
	}

	@Override
	public Long getPrimaryKey() {
		return logId;
	}

	@Override
	public void setPrimaryKey(Long paramLong) {
		this.logId = paramLong;
	}

	public Long getPricingId() {
		return pricingId;
	}

	public void setPricingId(Long pricingId) {
		this.pricingId = pricingId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActualized() {
		return actualized;
	}

	public void setActualized(String actualized) {
		this.actualized = actualized;
	}

}