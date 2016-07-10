package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 * The persistent class for the T_RI_CONT_RESERVE_REBATE_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_RESERVE_REBATE_LOG")
@NamedQuery(name = "TRiContReserveRebateLog.findAll", query = "SELECT t FROM TRiContReserveRebateLog t")
public class TRiContReserveRebateLog extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl
		implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Temporal(TemporalType.DATE)
	@Column(name = "CALC_DATE")
	private Date calcDate;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Column(name = "EXPIRATION_DAYS")
	private BigDecimal expirationDays;

	@Column(name = "EXPIRATION_MONTH")
	private BigDecimal expirationMonth;

	@Column(name = "EXPIRATION_YEAR")
	private BigDecimal expirationYear;

	@Column(name = "INTEREST_RATE")
	private BigDecimal interestRate;

	@Column(name = "LOSS_CALC_METHOD")
	private String lossCalcMethod;

	@Column(name = "LOSS_RATIO_FROM")
	private BigDecimal lossRatioFrom;

	@Column(name = "LOSS_RATIO_TO")
	private BigDecimal lossRatioTo;

	@Column(name = "LOSS_RESERVES")
	private BigDecimal lossReserves;

	@Column(name = "LR_CALC_YEARS")
	private BigDecimal lrCalcYears;

	@Column(name = "NO_CLAIM_BONUS")
	private BigDecimal noClaimBonus;

	@Column(name = "PREMIUM_CALC_METHOD")
	private String premiumCalcMethod;

	@Column(name = "PREMIUM_RESERVE")
	private BigDecimal premiumReserve;

	@Column(name = "REBATE_PERCENT")
	private BigDecimal rebatePercent;

	@Column(name = "REBATE_REMARK")
	private String rebateRemark;

	@Column(name = "RESERVE_ID")
	private Long reserveId;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "ADJUSTED_QUARTER")
	private String adjustedQuarter;
	
	public String getAdjustedQuarter() {
		return adjustedQuarter;
	}

	public void setAdjustedQuarter(String adjustedQuarter) {
		this.adjustedQuarter = adjustedQuarter;
	}

	public TRiContReserveRebateLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Date getCalcDate() {
		return this.calcDate;
	}

	public void setCalcDate(Date calcDate) {
		this.calcDate = calcDate;
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public BigDecimal getExpirationDays() {
		return this.expirationDays;
	}

	public void setExpirationDays(BigDecimal expirationDays) {
		this.expirationDays = expirationDays;
	}

	public BigDecimal getExpirationMonth() {
		return this.expirationMonth;
	}

	public void setExpirationMonth(BigDecimal expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public BigDecimal getExpirationYear() {
		return this.expirationYear;
	}

	public void setExpirationYear(BigDecimal expirationYear) {
		this.expirationYear = expirationYear;
	}

	public BigDecimal getInterestRate() {
		return this.interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public String getLossCalcMethod() {
		return this.lossCalcMethod;
	}

	public void setLossCalcMethod(String lossCalcMethod) {
		this.lossCalcMethod = lossCalcMethod;
	}

	public BigDecimal getLossRatioFrom() {
		return this.lossRatioFrom;
	}

	public void setLossRatioFrom(BigDecimal lossRatioFrom) {
		this.lossRatioFrom = lossRatioFrom;
	}

	public BigDecimal getLossRatioTo() {
		return this.lossRatioTo;
	}

	public void setLossRatioTo(BigDecimal lossRatioTo) {
		this.lossRatioTo = lossRatioTo;
	}

	public BigDecimal getLossReserves() {
		return this.lossReserves;
	}

	public void setLossReserves(BigDecimal lossReserves) {
		this.lossReserves = lossReserves;
	}

	public BigDecimal getLrCalcYears() {
		return this.lrCalcYears;
	}

	public void setLrCalcYears(BigDecimal lrCalcYears) {
		this.lrCalcYears = lrCalcYears;
	}

	public BigDecimal getNoClaimBonus() {
		return this.noClaimBonus;
	}

	public void setNoClaimBonus(BigDecimal noClaimBonus) {
		this.noClaimBonus = noClaimBonus;
	}

	public String getPremiumCalcMethod() {
		return this.premiumCalcMethod;
	}

	public void setPremiumCalcMethod(String premiumCalcMethod) {
		this.premiumCalcMethod = premiumCalcMethod;
	}

	public BigDecimal getPremiumReserve() {
		return this.premiumReserve;
	}

	public void setPremiumReserve(BigDecimal premiumReserve) {
		this.premiumReserve = premiumReserve;
	}

	public BigDecimal getRebatePercent() {
		return this.rebatePercent;
	}

	public void setRebatePercent(BigDecimal rebatePercent) {
		this.rebatePercent = rebatePercent;
	}

	public String getRebateRemark() {
		return this.rebateRemark;
	}

	public void setRebateRemark(String rebateRemark) {
		this.rebateRemark = rebateRemark;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getLogId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setLogId(key);
	}

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public Long getReserveId() {
		return reserveId;
	}

	public void setReserveId(Long reserveId) {
		this.reserveId = reserveId;
	}

}