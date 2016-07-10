package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.ebao.ap99.parent.BaseEntityImpl;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * The persistent class for the T_RI_CONT_PRICING_ITEM database table.
 */
@Entity
@Table(name = "T_RI_CONT_PRICING_ITEM")
@NamedQuery(name = "TRiContPricingItem.findAll", query = "SELECT t FROM TRiContPricingItem t")
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContPricingItem extends BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
    @Column(name = "ITEM_ID")
    @XmlTransient
    private Long itemId;

    private BigDecimal brokerage;

    private BigDecimal commission;

    private String currency;

    private BigDecimal epi;

    @Column(name = "LOSS_RATIO")
    private BigDecimal lossRatio;

    @Temporal(TemporalType.DATE)
    @Column(name = "PRICING_DATE")
    private Date pricingDate;

    private BigDecimal taxs;

	private Long underwriter;

    //bi-directional many-to-one association to TRiContPricing
    @ManyToOne
    @JoinColumn(name = "PRICING_ID")
    @JsonManagedReference
    @XmlTransient
    private TRiContPricing TRiContPricing;

    public TRiContPricingItem() {
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getBrokerage() {
        return this.brokerage;
    }

    public void setBrokerage(BigDecimal brokerage) {
        this.brokerage = brokerage;
    }

    public BigDecimal getCommission() {
        return this.commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getEpi() {
        return this.epi;
    }

    public void setEpi(BigDecimal epi) {
        this.epi = epi;
    }

    public BigDecimal getLossRatio() {
        return this.lossRatio;
    }

    public void setLossRatio(BigDecimal lossRatio) {
        this.lossRatio = lossRatio;
    }

    public Date getPricingDate() {
        return this.pricingDate;
    }

    public void setPricingDate(Date pricingDate) {
        this.pricingDate = pricingDate;
    }

    public BigDecimal getTaxs() {
        return this.taxs;
    }

    public void setTaxs(BigDecimal taxs) {
        this.taxs = taxs;
    }

	public Long getUnderwriter() {
		return this.underwriter;
	}

	public void setUnderwriter(Long underwriter) {
		this.underwriter = underwriter;
	}

    public TRiContPricing getTRiContPricing() {
        return this.TRiContPricing;
    }

    public void setTRiContPricing(TRiContPricing TRiContPricing) {
        this.TRiContPricing = TRiContPricing;
    }

    @Override
    public Long getPrimaryKey() {
        return itemId;
    }

    @Override
    public void setPrimaryKey(Long paramLong) {
        this.itemId = paramLong;
    }
}
