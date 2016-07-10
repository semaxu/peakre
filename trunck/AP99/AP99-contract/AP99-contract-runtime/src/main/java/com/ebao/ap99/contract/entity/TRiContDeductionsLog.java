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
 * The persistent class for the T_RI_CONT_DEDUCTIONS_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_DEDUCTIONS_LOG")
@NamedQuery(name = "TRiContDeductionsLog.findAll", query = "SELECT t FROM TRiContDeductionsLog t")
public class TRiContDeductionsLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	private BigDecimal brokerage;

	private BigDecimal brokerage2;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Column(name = "DEFICIT_CARRY_FORWARD")
	private String deficitCarryForward;

	@Column(name = "FIXED_AMOUNT_HUNRED_PERCENT")
	private BigDecimal fixedAmountHunredPercent;

	@Column(name = "FIXED_AMOUNT_OUR_SHARE")
	private BigDecimal fixedAmountOurShare;

	@Column(name = "NUMBER_OF_YEARS")
	private BigDecimal numberOfYears;

	@Column(name = "PERCENT_OF_PREMIUM")
	private BigDecimal percentOfPremium;

	@Column(name = "PROFIT_PERCENTAGE_TYPE")
	private String profitPercentageType;

	@Column(name = "RI_PERCENTAGE")
	private BigDecimal riPercentage;

	@Column(name = "PROFIT_PERCENTAGE")
	private BigDecimal profitPercentage;

	@Column(name = "EXPENSES_PERCENTAGE")
	private BigDecimal expensesPercentage;

	@Column(name = "R_I_COMMISSION")
	private String rICommission;

	@Column(name = "REMARK_PANEL")
	private String remarkPanel;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	public String getrICommission() {
		return rICommission;
	}

	public void setrICommission(String rICommission) {
		this.rICommission = rICommission;
	}

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	@Column(name = "DEDUCTIONS_ID")
	private Long deductionsId;

	public TRiContDeductionsLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public BigDecimal getBrokerage() {
		return this.brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public BigDecimal getBrokerage2() {
		return this.brokerage2;
	}

	public void setBrokerage2(BigDecimal brokerage2) {
		this.brokerage2 = brokerage2;
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getDeficitCarryForward() {
		return this.deficitCarryForward;
	}

	public void setDeficitCarryForward(String deficitCarryForward) {
		this.deficitCarryForward = deficitCarryForward;
	}

	public BigDecimal getFixedAmountHunredPercent() {
		return this.fixedAmountHunredPercent;
	}

	public void setFixedAmountHunredPercent(BigDecimal fixedAmountHunredPercent) {
		this.fixedAmountHunredPercent = fixedAmountHunredPercent;
	}

	public BigDecimal getFixedAmountOurShare() {
		return this.fixedAmountOurShare;
	}

	public void setFixedAmountOurShare(BigDecimal fixedAmountOurShare) {
		this.fixedAmountOurShare = fixedAmountOurShare;
	}

	public BigDecimal getNumberOfYears() {
		return this.numberOfYears;
	}

	public void setNumberOfYears(BigDecimal numberOfYears) {
		this.numberOfYears = numberOfYears;
	}

	public BigDecimal getPercentOfPremium() {
		return this.percentOfPremium;
	}

	public void setPercentOfPremium(BigDecimal percentOfPremium) {
		this.percentOfPremium = percentOfPremium;
	}

	public String getProfitPercentageType() {
		return profitPercentageType;
	}

	public void setProfitPercentageType(String profitPercentageType) {
		this.profitPercentageType = profitPercentageType;
	}

	public BigDecimal getRiPercentage() {
		return riPercentage;
	}

	public void setRiPercentage(BigDecimal riPercentage) {
		this.riPercentage = riPercentage;
	}

	public BigDecimal getProfitPercentage() {
		return profitPercentage;
	}

	public void setProfitPercentage(BigDecimal profitPercentage) {
		this.profitPercentage = profitPercentage;
	}

	public BigDecimal getExpensesPercentage() {
		return expensesPercentage;
	}

	public void setExpensesPercentage(BigDecimal expensesPercentage) {
		this.expensesPercentage = expensesPercentage;
	}

	public String getRICommission() {
		return this.rICommission;
	}

	public void setRICommission(String rICommission) {
		this.rICommission = rICommission;
	}

	public String getRemarkPanel() {
		return this.remarkPanel;
	}

	public void setRemarkPanel(String remarkPanel) {
		this.remarkPanel = remarkPanel;
	}

	public Long getDeductionsId() {
		return deductionsId;
	}

	public void setDeductionsId(Long deductionsId) {
		this.deductionsId = deductionsId;
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