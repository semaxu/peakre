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

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_ACCOUNT_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_ACCOUNT_LOG")
@NamedQuery(name = "TRiContAccountLog.findAll", query = "SELECT t FROM TRiContAccountLog t")
public class TRiContAccountLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "ACCOUNT_FREQUENCY")
	private String accountFrequency;

	@Column(name = "ACCOUNTING_BASIS")
	private String accountingBasis;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_FOR_REVIEW")
	private Date dateForReview;

	@Column(name = "EARNING_PATTERN")
	private String earningPattern;

	@Temporal(TemporalType.DATE)
	@Column(name = "FIRST_ACCOUNT_AS_AT_DATE")
	private Date firstAccountAsAtDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "DUE_DATE")
	private Date dueDate;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "PERCENTAGE_OF_PREMIUM")
	private BigDecimal percentageOfPremium;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Column(name = "PORTFOLIO_IN")
	private String portfolioIn;

	@Column(name = "PORTFOLIO_IN_DATA")
	private BigDecimal portfolioInData;

	@Column(name = "PORTFOLIO_OUT")
	private String portfolioOut;

	@Column(name = "PORTFOLIO_OUT_DATA")
	private BigDecimal portfolioOutData;

	@Column(name = "ACCOUNT_DAYS")
	private BigDecimal accountDays;

	@Column(name = "SETTLEMENT_DAYS")
	private BigDecimal settlementDays;

	@Column(name = "ACCOUNT_REMARK")
	private String accountRemark;

	@Column(name = "PREMIUM_IN")
	private BigDecimal premiumIn;

	@Column(name = "PREMIUM_OUT")
	private BigDecimal premiumOut;

	@Column(name = "OUTSTANDING_LOSSES_IN")
	private BigDecimal outstandingLossesIn;

	@Column(name = "OUTSTANDING_LOSSES_OUT")
	private BigDecimal outstandingLossesOut;

	public TRiContAccountLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getAccountFrequency() {
		return this.accountFrequency;
	}

	public void setAccountFrequency(String accountFrequency) {
		this.accountFrequency = accountFrequency;
	}

	public String getAccountingBasis() {
		return this.accountingBasis;
	}

	public void setAccountingBasis(String accountingBasis) {
		this.accountingBasis = accountingBasis;
	}

	public Date getDateForReview() {
		return this.dateForReview;
	}

	public void setDateForReview(Date dateForReview) {
		this.dateForReview = dateForReview;
	}

	public String getEarningPattern() {
		return this.earningPattern;
	}

	public void setEarningPattern(String earningPattern) {
		this.earningPattern = earningPattern;
	}

	public Date getFirstAccountAsAtDate() {
		return this.firstAccountAsAtDate;
	}

	public void setFirstAccountAsAtDate(Date firstAccountAsAtDate) {
		this.firstAccountAsAtDate = firstAccountAsAtDate;
	}

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public BigDecimal getPercentageOfPremium() {
		return this.percentageOfPremium;
	}

	public void setPercentageOfPremium(BigDecimal percentageOfPremium) {
		this.percentageOfPremium = percentageOfPremium;
	}

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getPortfolioIn() {
		return portfolioIn;
	}

	public void setPortfolioIn(String portfolioIn) {
		this.portfolioIn = portfolioIn;
	}

	public BigDecimal getPortfolioInData() {
		return portfolioInData;
	}

	public void setPortfolioInData(BigDecimal portfolioInData) {
		this.portfolioInData = portfolioInData;
	}

	public String getPortfolioOut() {
		return portfolioOut;
	}

	public void setPortfolioOut(String portfolioOut) {
		this.portfolioOut = portfolioOut;
	}

	public BigDecimal getPortfolioOutData() {
		return portfolioOutData;
	}

	public void setPortfolioOutData(BigDecimal portfolioOutData) {
		this.portfolioOutData = portfolioOutData;
	}

	public BigDecimal getAccountDays() {
		return accountDays;
	}

	public void setAccountDays(BigDecimal accountDays) {
		this.accountDays = accountDays;
	}

	public BigDecimal getSettlementDays() {
		return settlementDays;
	}

	public void setSettlementDays(BigDecimal settlementDays) {
		this.settlementDays = settlementDays;
	}

	public String getAccountRemark() {
		return accountRemark;
	}

	public BigDecimal getPremiumIn() {
		return premiumIn;
	}

	public void setPremiumIn(BigDecimal premiumIn) {
		this.premiumIn = premiumIn;
	}

	public BigDecimal getPremiumOut() {
		return premiumOut;
	}

	public void setPremiumOut(BigDecimal premiumOut) {
		this.premiumOut = premiumOut;
	}

	public BigDecimal getOutstandingLossesIn() {
		return outstandingLossesIn;
	}

	public void setOutstandingLossesIn(BigDecimal outstandingLossesIn) {
		this.outstandingLossesIn = outstandingLossesIn;
	}

	public BigDecimal getOutstandingLossesOut() {
		return outstandingLossesOut;
	}

	public void setOutstandingLossesOut(BigDecimal outstandingLossesOut) {
		this.outstandingLossesOut = outstandingLossesOut;
	}

	public void setAccountRemark(String accountRemark) {
		this.accountRemark = accountRemark;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public Long getPrimaryKey() {
		// TODO Auto-generated method stub
		return this.logId;
	}

	@Override
	public void setPrimaryKey(Long paramLong) {
		// TODO Auto-generated method stub
		this.setLogId(paramLong);
	}

}