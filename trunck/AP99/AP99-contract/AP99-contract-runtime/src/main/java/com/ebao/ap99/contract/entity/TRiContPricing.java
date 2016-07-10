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

import com.ebao.ap99.parent.BaseEntityImpl;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * The persistent class for the T_RI_CONT_PRICING database table.
 */
@Entity
@Table(name = "T_RI_CONT_PRICING")
@NamedQueries({ @NamedQuery(name = "TRiContPricing.findAll", query = "SELECT t FROM TRiContPricing t"),
		@NamedQuery(name = "TRiContPricing.loadByContCompId", query = "SELECT t FROM TRiContPricing t WHERE t.contCompId = :contCompId") })
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContPricing extends BaseEntityImpl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "PRICING_ID")
	@XmlTransient
	private Long pricingId;

	@Column(name = "CONT_COMP_ID")
	@XmlTransient
	private Long contCompId;

	@Column(name = "EARNING_PARTNER")
	private String earningPartner;

	@Column(name = "SECTION_NAME")
	private String sectionName;

	@Column(name = "WRITTEN_PARTNER")
	private String writtenPartner;

	@Column(name = "ACTUALIZED")
	private String actualized;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "IS_ACTIVE")
	@XmlTransient
	private String isActive;

	// bi-directional many-to-one association to TRiContPricingItem
	@OneToMany(mappedBy = "TRiContPricing", cascade = { CascadeType.ALL })
	@JsonManagedReference
	private List<TRiContPricingItem> TRiContPricingItems;

	public TRiContPricing() {
		// default Y
		this.isActive = "Y";
	}

	public Long getPricingId() {
		return this.pricingId;
	}

	public void setPricingId(Long pricingId) {
		this.pricingId = pricingId;
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

	public List<TRiContPricingItem> getTRiContPricingItems() {
		return this.TRiContPricingItems;
	}

	public void setTRiContPricingItems(List<TRiContPricingItem> TRiContPricingItems) {
		this.TRiContPricingItems = TRiContPricingItems;
	}

	public TRiContPricingItem addTRiContPricingItem(TRiContPricingItem TRiContPricingItem) {

		if (getTRiContPricingItems() == null) {
			setTRiContPricingItems(new ArrayList<TRiContPricingItem>());
			getTRiContPricingItems().add(TRiContPricingItem);
		} else {
			getTRiContPricingItems().add(TRiContPricingItem);
		}
		TRiContPricingItem.setTRiContPricing(this);
		return TRiContPricingItem;
	}

	public TRiContPricingItem removeTRiContPricingItem(TRiContPricingItem TRiContPricingItem) {
		getTRiContPricingItems().remove(TRiContPricingItem);
		TRiContPricingItem.setTRiContPricing(null);

		return TRiContPricingItem;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public Long getPrimaryKey() {
		return pricingId;
	}

	@Override
	public void setPrimaryKey(Long paramLong) {
		this.pricingId = paramLong;
	}

	public String getActualized() {
		return actualized;
	}

	public void setActualized(String actualized) {
		this.actualized = actualized;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
