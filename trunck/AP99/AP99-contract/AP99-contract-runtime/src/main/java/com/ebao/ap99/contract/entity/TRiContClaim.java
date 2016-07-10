package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.parent.BaseEntityImpl;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * The persistent class for the T_RI_CONT_CLAIM database table.
 */
@Entity
@Table(name = "T_RI_CONT_CLAIM")
@NamedQueries({
		@NamedQuery(name = "TRiContClaim.getPostingFlag", query = "SELECT t.posting FROM TRiContClaim t WHERE t.contCompId=:contCompId and t.isActive != 'N'"),
		@NamedQuery(name = "TRiContClaim.findAll", query = "SELECT t FROM TRiContClaim t WHERE t.isActive != 'N'") })
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContClaim extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CONT_COMP_ID")
	@XmlTransient
	private Long contCompId;

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

	// bi-directional one-to-one association to TRiContractInfo
	@OneToOne
	@JoinColumn(name = "CONT_COMP_ID")
	@XmlTransient
	private TRiContractInfo TRiContractInfo;

	// bi-directional many-to-one association to TRiContClaimItem
	@OneToMany(mappedBy = "TRiContClaim", cascade = { CascadeType.ALL })
	@JsonManagedReference
	private List<TRiContClaimItem> TRiContClaimItems = new ArrayList<TRiContClaimItem>();

	@Column(name = "IS_ACTIVE")
	@XmlTransient
	private String isActive;

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		if (StringUtils.isNullOrEmpty(isActive)) {
			this.isActive = "Y";
		} else {
			this.isActive = isActive;
		}
	}

	public TRiContClaim() {
		// default Y
		this.isActive = "Y";
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
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

	public TRiContractInfo getTRiContractInfo() {
		return TRiContractInfo;
	}

	public void setTRiContractInfo(TRiContractInfo tRiContractInfo) {
		TRiContractInfo = tRiContractInfo;
	}

	public List<TRiContClaimItem> getTRiContClaimItems() {
		return this.TRiContClaimItems;
	}

	public void setTRiContClaimItems(List<TRiContClaimItem> TRiContClaimItems) {
		this.TRiContClaimItems = TRiContClaimItems;
	}

	@Override
	public Long getPrimaryKey() {
		return null;
	}

	@Override
	public void setPrimaryKey(Long paramLong) {
		// TODO Auto-generated method stub
	}

}
