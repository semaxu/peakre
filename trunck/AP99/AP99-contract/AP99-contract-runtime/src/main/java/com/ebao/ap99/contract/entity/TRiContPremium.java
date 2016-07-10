package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Where;
import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.parent.BaseEntityImpl;


/**
 * The persistent class for the T_RI_CONT_PREMIUM database table.
 */
@Entity
@Table(name = "T_RI_CONT_PREMIUM")
@NamedQueries({
		@NamedQuery(name = "TRiContPremium.findAll", query = "SELECT t FROM TRiContPremium t WHERE t.isActive != 'N'"),
		@NamedQuery(name = "TRiContPremium.loadByContCompId", query = "SELECT t FROM TRiContPremium t WHERE t.contCompId=:contCompId and t.isActive != 'N'")

})
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContPremium extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "PREMIUM_ID")
	@XmlTransient
	private Long premiumId;

	@Column(name = "CONT_COMP_ID")
	@XmlTransient
	private Long contCompId;

	@Column(name = "DEFINED_IN")
	private String definedIn;

	@Column(name = "EPI_TYPE")
	private String epiType;

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

	// bi-directional many-to-one association to TRiContPremiumItem
	@OneToMany(mappedBy = "TRiContPremium", cascade = { CascadeType.ALL })
	@Where(clause = "IS_ACTIVE='Y'")
//	@JsonManagedReference
	private List<TRiContPremiumItem> TRiContPremiumItems = new ArrayList<TRiContPremiumItem>();

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

	public TRiContPremium() {
		// default Y
		this.isActive = "Y";
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

	public Long getPremiumId() {
		return this.premiumId;
	}

	public void setPremiumId(Long premiumId) {
		this.premiumId = premiumId;
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

	public String getEpiType() {
		return this.epiType;
	}

	public void setEpiType(String epiType) {
		this.epiType = epiType;
	}

	public String getPremiumType() {
		return this.premiumType;
	}

	public void setPremiumType(String premiumType) {
		this.premiumType = premiumType;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getPremiumId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setPremiumId(key);
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

	public void setProvisionalRate(BigDecimal provisionalRate) {
		this.provisionalRate = provisionalRate;
	}

	public List<TRiContPremiumItem> getTRiContPremiumItems() {
		return this.TRiContPremiumItems;
	}

	public void setTRiContPremiumItems(List<TRiContPremiumItem> TRiContPremiumItems) {
		this.TRiContPremiumItems = TRiContPremiumItems;
	}

	public TRiContPremiumItem addTRiContPremiumItem(TRiContPremiumItem TRiContPremiumItem) {
		if (getTRiContPremiumItems() == null) {
			setTRiContPremiumItems(new ArrayList<TRiContPremiumItem>());
			getTRiContPremiumItems().add(TRiContPremiumItem);
		} else {
			getTRiContPremiumItems().add(TRiContPremiumItem);
		}
		TRiContPremiumItem.setTRiContPremium(this);

		return TRiContPremiumItem;
	}

	public TRiContPremiumItem removeTRiContPremiumItem(TRiContPremiumItem TRiContPremiumItem) {
		getTRiContPremiumItems().remove(TRiContPremiumItem);
		TRiContPremiumItem.setTRiContPremium(null);

		return TRiContPremiumItem;
	}

}
