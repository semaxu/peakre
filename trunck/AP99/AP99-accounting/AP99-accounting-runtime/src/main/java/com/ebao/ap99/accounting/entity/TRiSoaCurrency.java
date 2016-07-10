package com.ebao.ap99.accounting.entity;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the T_RI_SOA_CURRENCY database table.
 */
@Entity
@Table(name = "T_RI_SOA_CURRENCY")
@NamedQuery(name = "TRiSoaCurrency.findAll", query = "SELECT t FROM TRiSoaCurrency t")
public class TRiSoaCurrency extends com.ebao.ap99.parent.BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
    @Column(name = "SOA_CURRENCY_ID")
    private Long soaCurrencyId;

    private BigDecimal amount;

    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    //	@Column(name="INSERT_BY")
    //	private BigDecimal insertBy;
    //
    //	@Temporal(TemporalType.DATE)
    //	@Column(name="INSERT_TIME")
    //	private Date insertTime;
    //
    //	@Column(name="UPDATE_BY")
    //	private BigDecimal updateBy;
    //
    //	@Temporal(TemporalType.DATE)
    //	@Column(name="UPDATE_TIME")
    //	private Date updateTime;

    //bi-directional many-to-one association to TRiSoa
    @ManyToOne
    @JoinColumn(name = "SOA_ID")
    private TRiSoa TRiSoa;

    //bi-directional many-to-one association to TRiSoaSection
    @OneToMany(mappedBy = "TRiSoaCurrency", cascade = { CascadeType.ALL })
    private List<TRiSoaSection> TRiSoaSections = new ArrayList<TRiSoaSection>();

    public TRiSoaCurrency() {
    }

    public Long getSoaCurrencyId() {
        return this.soaCurrencyId;
    }

    public void setSoaCurrencyId(Long soaCurrencyId) {
        this.soaCurrencyId = soaCurrencyId;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public TRiSoa getTRiSoa() {
        return this.TRiSoa;
    }

    public void setTRiSoa(TRiSoa TRiSoa) {
        this.TRiSoa = TRiSoa;
    }

    public List<TRiSoaSection> getTRiSoaSections() {
        return this.TRiSoaSections;
    }

    public void setTRiSoaSections(List<TRiSoaSection> TRiSoaSections) {
        this.TRiSoaSections = TRiSoaSections;
    }

    public TRiSoaSection addTRiSoaSection(TRiSoaSection TRiSoaSection) {
        getTRiSoaSections().add(TRiSoaSection);
        TRiSoaSection.setTRiSoaCurrency(this);

        return TRiSoaSection;
    }

    public TRiSoaSection removeTRiSoaSection(TRiSoaSection TRiSoaSection) {
        getTRiSoaSections().remove(TRiSoaSection);
        TRiSoaSection.setTRiSoaCurrency(null);

        return TRiSoaSection;
    }

    @Override
    public Long getPrimaryKey() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPrimaryKey(Long paramLong) {
        // TODO Auto-generated method stub

    }

}
