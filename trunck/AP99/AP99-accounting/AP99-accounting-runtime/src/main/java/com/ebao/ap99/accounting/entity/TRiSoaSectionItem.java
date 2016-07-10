package com.ebao.ap99.accounting.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the T_RI_SOA_SECTION_ITEM database table.
 */
@Entity
@Table(name = "T_RI_SOA_SECTION_ITEM")
@NamedQuery(name = "TRiSoaSectionItem.findAll", query = "SELECT t FROM TRiSoaSectionItem t")
public class TRiSoaSectionItem extends com.ebao.ap99.parent.BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
    @Column(name = "ITEM_ID")
    private Long itemId;

    private BigDecimal amount;

    @Column(name = "ENTRY_CODE")
    private String entryCode;

    @Column(name = "ENTRY_ITEM")
    private String entryItem;

    @Column(name = "SHARE_AMOUNT")
    private BigDecimal shareAmount = BigDecimal.ZERO;

    private BigDecimal percentage = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "SOA_SECTION_ID")
    private TRiSoaSection TRiSoaSection;

    public TRiSoaSectionItem() {
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPercentage() {
        return this.percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public TRiSoaSection getTRiSoaSection() {
        return this.TRiSoaSection;
    }

    public void setTRiSoaSection(TRiSoaSection TRiSoaSection) {
        this.TRiSoaSection = TRiSoaSection;
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

    public BigDecimal getShareAmount() {
        return this.shareAmount;
    }

    public void setShareAmount(BigDecimal shareAmount) {
        this.shareAmount = shareAmount;
    }

    public String getEntryItem() {
        return entryItem;
    }

    public void setEntryItem(String entryItem) {
        this.entryItem = entryItem;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public void setEntryCode(String entryCode) {
        this.entryCode = entryCode;
    }

}
