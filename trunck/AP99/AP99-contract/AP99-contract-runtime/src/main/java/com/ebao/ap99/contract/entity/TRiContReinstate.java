package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the T_RI_CONT_REINSTATE database table.
 */
@Entity
@Table(name = "T_RI_CONT_REINSTATE")

@NamedQueries({
		@NamedQuery(name = "TRiContReinstate.findAll", query = "SELECT t FROM TRiContReinstate t WHERE t.isActive != 'N'"),
		@NamedQuery(name = "TRiContReinstate.loadByContCompId", query = "SELECT t FROM TRiContReinstate t WHERE t.contCompId = :contCompId and t.isActive != 'N'") })
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContReinstate extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "REIN_ID")
	@XmlTransient
	private Long reinId;

	@Column(name = "CONT_COMP_ID")
	@XmlTransient
	private Long contCompId;

	@Column(name = "EXCH_CALC")
	private String exchCalc;

	@Column(name = "REIN_CURRENCY")
	private String reinCurrency;

	@Column(name = "REIN_NUM")
	private BigDecimal reinNum;

	@Column(name = "REIN_TYPE")
	private String reinType;

	@OneToMany(mappedBy = "TRiContReinstate", cascade = { CascadeType.ALL })
	@Where(clause = "IS_ACTIVE='Y'")
	private List<TRiContReinstateItem> TRiContReinstateItems;

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

	public TRiContReinstate() {
		// default Y
		this.isActive = "Y";
	}

	public List<TRiContReinstateItem> getTRiContReinstateItems() {
		return this.TRiContReinstateItems;
	}

	public void setTRiContReinstateItems(List<TRiContReinstateItem> TRiContReinstateItems) {
		this.TRiContReinstateItems = TRiContReinstateItems;
	}

	public Long getReinId() {
		return this.reinId;
	}

	public void setReinId(Long reinId) {
		this.reinId = reinId;
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getExchCalc() {
		return this.exchCalc;
	}

	public void setExchCalc(String exchCalc) {
		this.exchCalc = exchCalc;
	}

	public String getReinCurrency() {
		return this.reinCurrency;
	}

	public void setReinCurrency(String reinCurrency) {
		this.reinCurrency = reinCurrency;
	}

	public BigDecimal getReinNum() {
		return this.reinNum;
	}

	public void setReinNum(BigDecimal reinNum) {
		this.reinNum = reinNum;
	}

	public String getReinType() {
		return this.reinType;
	}

	public void setReinType(String reinType) {
		this.reinType = reinType;
	}

	public TRiContReinstateItem addTRiContReinstateItem(TRiContReinstateItem TRiContReinstateItem) {
		getTRiContReinstateItems().add(TRiContReinstateItem);
		TRiContReinstateItem.setTRiContReinstate(this);

		return TRiContReinstateItem;
	}

	public TRiContReinstateItem removeTRiContReinstateItem(TRiContReinstateItem TRiContReinstateItem) {
		getTRiContReinstateItems().remove(TRiContReinstateItem);
		TRiContReinstateItem.setTRiContReinstate(null);

		return TRiContReinstateItem;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getReinId();
	}

	@Override
	public void setPrimaryKey(Long key) {

		this.setReinId(key);
	}

}
