package com.ebao.ap99.contract.entity;

import java.io.Serializable;
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
 * The persistent class for the T_RI_CONT_CLAIM_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_CLAIM_LOG")
@NamedQuery(name = "TRiContClaimLog.findAll", query = "SELECT t FROM TRiContClaimLog t")
public class TRiContClaimLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "CASH_CALL_ADVICE")
	private String cashCallAdvice;

	@Column(name = "CLAIM_BASIS")
	private String claimBasis;

	@Column(name = "CLAIM_CURRENCY")
	private String claimCurrency;

	@Column(name = "LOSS_ADVICE")
	private String lossAdvice;

	@Column(name = "NO_OF_DAY")
	private Long noOfDay;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	private String posting;

	@Column(name = "PRICES_INDEX")
	private String pricesIndex;

	@Temporal(TemporalType.DATE)
	@Column(name = "RETROACTIVE_DATE")
	private Date retroactiveDate;

	private String specify;

	@Column(name = "SUNSET_CHECK")
	private String sunsetCheck;

	@Temporal(TemporalType.DATE)
	@Column(name = "SUNSET_CLAUSE")
	private Date sunsetClause;

	@Column(name = "WETHER_INDEX")
	private String wetherIndex;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	public TRiContClaimLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getCashCallAdvice() {
		return this.cashCallAdvice;
	}

	public void setCashCallAdvice(String cashCallAdvice) {
		this.cashCallAdvice = cashCallAdvice;
	}

	public String getClaimBasis() {
		return this.claimBasis;
	}

	public void setClaimBasis(String claimBasis) {
		this.claimBasis = claimBasis;
	}

	public String getClaimCurrency() {
		return claimCurrency;
	}

	public void setClaimCurrency(String claimCurrency) {
		this.claimCurrency = claimCurrency;
	}

	public String getLossAdvice() {
		return this.lossAdvice;
	}

	public void setLossAdvice(String lossAdvice) {
		this.lossAdvice = lossAdvice;
	}

	public Long getNoOfDay() {
		return this.noOfDay;
	}

	public void setNoOfDay(Long noOfDay) {
		this.noOfDay = noOfDay;
	}

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public String getPosting() {
		return this.posting;
	}

	public void setPosting(String posting) {
		this.posting = posting;
	}

	public String getPricesIndex() {
		return this.pricesIndex;
	}

	public void setPricesIndex(String pricesIndex) {
		this.pricesIndex = pricesIndex;
	}

	public Date getRetroactiveDate() {
		return this.retroactiveDate;
	}

	public void setRetroactiveDate(Date retroactiveDate) {
		this.retroactiveDate = retroactiveDate;
	}

	public String getSpecify() {
		return this.specify;
	}

	public void setSpecify(String specify) {
		this.specify = specify;
	}

	public String getSunsetCheck() {
		return this.sunsetCheck;
	}

	public void setSunsetCheck(String sunsetCheck) {
		this.sunsetCheck = sunsetCheck;
	}

	public Date getSunsetClause() {
		return this.sunsetClause;
	}

	public void setSunsetClause(Date sunsetClause) {
		this.sunsetClause = sunsetClause;
	}

	public String getWetherIndex() {
		return this.wetherIndex;
	}

	public void setWetherIndex(String wetherIndex) {
		this.wetherIndex = wetherIndex;
	}

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
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