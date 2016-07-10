package com.ebao.ap99.accounting.entity;

import java.io.Serializable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the T_RI_SOA_SECTION database table.
 */
@Entity
@Table(name = "T_RI_SOA_SECTION")
@NamedQueries({ @NamedQuery(name = "TRiSoaSection.findAll", query = "SELECT t FROM TRiSoaSection t"),
        @NamedQuery(name = "TRiSoaSection.findListBySoaId", query = "SELECT t FROM TRiSoaSection t WHERE t.soaId = :soaId") })
public class TRiSoaSection extends com.ebao.ap99.parent.BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
    @Column(name = "SOA_SECTION_ID")
    private Long soaSectionId;

    private String cob;

    @Column(name = "CONTRAC_COMP_ID")
    private Long contracCompId;
    //
    //	private BigDecimal expense;
    //
    //	@Column(name="INCURRED_LOSS")
    //	private BigDecimal incurredLoss;

    //private BigDecimal premium;

    @Column(name = "SHARE_TYPE")
    private String shareType;

    @Column(name = "UW_AREA")
    private String uwArea;

    @Column(name = "CONTRACT_SECTION")
    private String contractSection;

    @Column(name = "SOA_ID", insertable = false, updatable = false)
    private Long soaId;

    @Column(name = "IS_FULLY_TRANSFER")
    @org.hibernate.annotations.Type(type = "yes_no")
    private boolean isFullyTransfer;

    @Column(name = "IS_REVERSAL")
    @org.hibernate.annotations.Type(type = "yes_no")
    private boolean isReversal;

    //bi-directional many-to-one association to TRiSoa
    @ManyToOne
    @JoinColumn(name = "SOA_ID")
    private TRiSoa TRiSoa;

    //bi-directional many-to-one association to TRiSoaCurrency
    @ManyToOne
    @JoinColumn(name = "SOA_CURRENCY_ID")
    private TRiSoaCurrency TRiSoaCurrency;

    //bi-directional many-to-one association to TRiSoaSectionItem
    @OneToMany(mappedBy = "TRiSoaSection", cascade = { CascadeType.ALL })
    private List<TRiSoaSectionItem> TRiSoaSectionItems = new ArrayList<TRiSoaSectionItem>();

    public TRiSoaSection() {
    }

    public Long getSoaSectionId() {
        return this.soaSectionId;
    }

    public void setSoaSectionId(Long soaSectionId) {
        this.soaSectionId = soaSectionId;
    }

    //	public BigDecimal getExpense() {
    //		return this.expense;
    //	}
    //
    //	public void setExpense(BigDecimal expense) {
    //		this.expense = expense;
    //	}
    //
    //	public BigDecimal getIncurredLoss() {
    //		return this.incurredLoss;
    //	}
    //
    //	public void setIncurredLoss(BigDecimal incurredLoss) {
    //		this.incurredLoss = incurredLoss;
    //	}

    //	public BigDecimal getPremium() {
    //		return this.premium;
    //	}
    //
    //	public void setPremium(BigDecimal premium) {
    //		this.premium = premium;
    //	}

    public TRiSoa getTRiSoa() {
        return this.TRiSoa;
    }

    public void setTRiSoa(TRiSoa TRiSoa) {
        this.TRiSoa = TRiSoa;
    }

    public TRiSoaCurrency getTRiSoaCurrency() {
        return this.TRiSoaCurrency;
    }

    public void setTRiSoaCurrency(TRiSoaCurrency TRiSoaCurrency) {
        this.TRiSoaCurrency = TRiSoaCurrency;
    }

    public List<TRiSoaSectionItem> getTRiSoaSectionItems() {
        return this.TRiSoaSectionItems;
    }

    public void setTRiSoaSectionItems(List<TRiSoaSectionItem> TRiSoaSectionItems) {
        this.TRiSoaSectionItems = TRiSoaSectionItems;
    }

    public TRiSoaSectionItem addTRiSoaSectionItem(TRiSoaSectionItem TRiSoaSectionItem) {
        if (getTRiSoaSectionItems().isEmpty()) {
            setTRiSoaSectionItems(new ArrayList<TRiSoaSectionItem>());
        }
        getTRiSoaSectionItems().add(TRiSoaSectionItem);
        TRiSoaSectionItem.setTRiSoaSection(this);
        return TRiSoaSectionItem;
    }

    public TRiSoaSectionItem removeTRiSoaSectionItem(TRiSoaSectionItem TRiSoaSectionItem) {
        if (getTRiSoaSectionItems().isEmpty()) {
            setTRiSoaSectionItems(new ArrayList<TRiSoaSectionItem>());
        }
        getTRiSoaSectionItems().remove(TRiSoaSectionItem);
        TRiSoaSectionItem.setTRiSoaSection(null);

        return TRiSoaSectionItem;
    }

    public String getContractSection() {
        return contractSection;
    }

    public void setContractSection(String contractSection) {
        this.contractSection = contractSection;
        this.contractSection = "contract section";
    }

    public String getCob() {
        return cob;
    }

    public void setCob(String cob) {
        this.cob = cob;
    }

    public Long getContracCompId() {
        return contracCompId;
    }

    public void setContracCompId(Long contracCompId) {
        this.contracCompId = contracCompId;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String getUwArea() {
        return uwArea;
    }

    public void setUwArea(String uwArea) {
        this.uwArea = uwArea;
    }

    public boolean isFullyTransfer() {
        return isFullyTransfer;
    }

    public void setFullyTransfer(boolean isFullyTransfer) {
        this.isFullyTransfer = isFullyTransfer;
    }

    public boolean isReversal() {
        return isReversal;
    }

    public void setReversal(boolean isReversal) {
        this.isReversal = isReversal;
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
