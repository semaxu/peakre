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

/**
 * The persistent class for the T_RI_CONT_PREMIUM_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_PREMIUM_LOG")
@NamedQuery(name = "TRiContPremiumLog.findAll", query = "SELECT t FROM TRiContPremiumLog t")
public class TRiContPremiumLog extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Column(name = "DEFINED_IN")
	private String definedIn;

	@Column(name = "EPI_TYPE")
	private String epiType;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "PREMIUM_ID")
	private Long premiumId;

	@Column(name = "PREMIUM_TYPE")
	private String premiumType;

	@Column(name = "LOSSRATE_FROM")
	private BigDecimal lossrateFrom;

	@Column(name = "LOSSRATE_TO")
	private BigDecimal lossrateTo;

	@Column(name = "PREMIUMRATE_FROM")
	private BigDecimal premiumrateFrom;

	@Column(name = "PREMIUMRATE_TO")
	private BigDecimal premiumrateTo;

	@Column(name = "PROVISIONAL_RATE")
	private BigDecimal provisionalRate;

	@Column(name = "RATE")
	private BigDecimal rate;

	@Column(name = "PREMIUM_REMARK")
	private String premiumRemark;

	@Column(name = "NO_OF_INSTALLMENT")
	private String noOfInstallment;

	@Column(name = "NO_OF_PAYMENT")
	private String noOfPayment;

	public TRiContPremiumLog() {
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

	public String getDefinedIn() {
		return this.definedIn;
	}

	public void setDefinedIn(String definedIn) {
		this.definedIn = definedIn;
	}

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public Long getPremiumId() {
		return this.premiumId;
	}

	public void setPremiumId(Long premiumId) {
		this.premiumId = premiumId;
	}

	public String getPremiumType() {
		return this.premiumType;
	}

	public void setPremiumType(String premiumType) {
		this.premiumType = premiumType;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getPremiumRemark() {
		return premiumRemark;
	}

	public void setPremiumRemark(String premiumRemark) {
		this.premiumRemark = premiumRemark;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getLogId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setLogId(key);

	}

	public BigDecimal getLossrateFrom() {
		return lossrateFrom;
	}

	public void setLossrateFrom(BigDecimal lossrateFrom) {
		this.lossrateFrom = lossrateFrom;
	}

	public BigDecimal getLossrateTo() {
		return lossrateTo;
	}

	public void setLossrateTo(BigDecimal lossrateTo) {
		this.lossrateTo = lossrateTo;
	}

	public BigDecimal getPremiumrateFrom() {
		return premiumrateFrom;
	}

	public void setPremiumrateFrom(BigDecimal premiumrateFrom) {
		this.premiumrateFrom = premiumrateFrom;
	}

	public BigDecimal getPremiumrateTo() {
		return premiumrateTo;
	}

	public void setPremiumrateTo(BigDecimal premiumrateTo) {
		this.premiumrateTo = premiumrateTo;
	}

	public BigDecimal getProvisionalRate() {
		return provisionalRate;
	}

	public void setProvisionalRate(BigDecimal provisionalRate) {
		this.provisionalRate = provisionalRate;
	}

	public String getEpiType() {
		return epiType;
	}

	public void setEpiType(String epiType) {
		this.epiType = epiType;
	}

	public String getNoOfInstallment() {
		return noOfInstallment;
	}

	public void setNoOfInstallment(String noOfInstallment) {
		this.noOfInstallment = noOfInstallment;
	}

	public String getNoOfPayment() {
		return noOfPayment;
	}

	public void setNoOfPayment(String noOfPayment) {
		this.noOfPayment = noOfPayment;
	}

}