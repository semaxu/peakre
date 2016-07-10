package com.ebao.ap99.contract.entity;

import java.io.Serializable;
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
 * The persistent class for the T_RI_CONT_LIMIT database table.
 */
@Entity
@Table(name = "T_RI_CONT_LIMIT")

@NamedQueries({
		@NamedQuery(name = "TRiContLimit.findAll", query = "SELECT t FROM TRiContLimit t WHERE t.isActive != 'N'"),
		@NamedQuery(name = "TRiContLimit.loadByContCompId", query = "SELECT t FROM TRiContLimit t WHERE t.contCompId = :contCompId and t.isActive != 'N'") })
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContLimit extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LIMIT_ID")
	@XmlTransient
	private Long limitId;

	@Column(name = "AMOUNT_TYPE")
	private String amountType;

	@Column(name = "CONT_COMP_ID")
	@XmlTransient
	private Long contCompId;

	@Column(name = "LIMIT_TYPE")
	private String limitType;

	@Column(name = "PER_RISK")
	private String perRisk;

	private String unlimit;

	// bi-directional many-to-one association to TRiContLimitEvent
	@OneToMany(mappedBy = "TRiContLimit", cascade = { CascadeType.ALL })
	@Where(clause = "IS_ACTIVE='Y'")
	private List<TRiContLimitEvent> TRiContLimitEvents = new ArrayList<TRiContLimitEvent>();

	// bi-directional many-to-one association to TRiContLimitItem
	@OneToMany(mappedBy = "TRiContLimit", cascade = { CascadeType.ALL })
	@Where(clause = "IS_ACTIVE='Y'")
	private List<TRiContLimitItem> TRiContLimitItems = new ArrayList<TRiContLimitItem>();

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

	public TRiContLimit() {
		// default Y
		this.isActive = "Y";
	}

	public Long getLimitId() {
		return this.limitId;
	}

	public void setLimitId(Long limitId) {
		this.limitId = limitId;
	}

	public String getAmountType() {
		return this.amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getLimitType() {
		return this.limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public String getPerRisk() {
		return this.perRisk;
	}

	public void setPerRisk(String perRisk) {
		this.perRisk = perRisk;
	}

	public String getUnlimit() {
		return this.unlimit;
	}

	public void setUnlimit(String unlimit) {
		this.unlimit = unlimit;
	}

	public List<TRiContLimitEvent> getTRiContLimitEvents() {
		return this.TRiContLimitEvents;
	}

	public void setTRiContLimitEvents(List<TRiContLimitEvent> TRiContLimitEvents) {
		this.TRiContLimitEvents = TRiContLimitEvents;
	}

	public TRiContLimitEvent addTRiContLimitEvent(TRiContLimitEvent TRiContLimitEvent) {
		getTRiContLimitEvents().add(TRiContLimitEvent);
		TRiContLimitEvent.setTRiContLimit(this);

		return TRiContLimitEvent;
	}

	public TRiContLimitEvent removeTRiContLimitEvent(TRiContLimitEvent TRiContLimitEvent) {
		getTRiContLimitEvents().remove(TRiContLimitEvent);
		TRiContLimitEvent.setTRiContLimit(null);

		return TRiContLimitEvent;
	}

	public List<TRiContLimitItem> getTRiContLimitItems() {
		return this.TRiContLimitItems;
	}

	public void setTRiContLimitItems(List<TRiContLimitItem> TRiContLimitItems) {
		this.TRiContLimitItems = TRiContLimitItems;
	}

	public TRiContLimitItem addTRiContLimitItem(TRiContLimitItem TRiContLimitItem) {
		getTRiContLimitItems().add(TRiContLimitItem);
		TRiContLimitItem.setTRiContLimit(this);

		return TRiContLimitItem;
	}

	public TRiContLimitItem removeTRiContLimitItem(TRiContLimitItem TRiContLimitItem) {
		getTRiContLimitItems().remove(TRiContLimitItem);
		TRiContLimitItem.setTRiContLimit(null);

		return TRiContLimitItem;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getLimitId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setLimitId(key);
	}

}
