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

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_ACCT_ESTIMATE_ITEM database table.
 */
@Entity
@Table(name = "T_RI_ACCT_ESTIMATE_ITEM")
@NamedQuery(name = "TRiAcctEstimateItem.findAll", query = "SELECT t FROM TRiAcctEstimateItem t")
public class TRiAcctEstimateItem extends BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
    @Column(name = "ITEM_ID")
    private Long itemId;

    @Column(name = "ENTRY_CODE")
    private String entryCode;

    private BigDecimal amount;

    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ESTIMATE_ID")
    private TRiAcctEstimate TRiAcctEstimate;

    @OneToMany(mappedBy = "TRiAcctEstimateItem", cascade = { CascadeType.ALL })
    private List<TRiAcctEstimateItemHis> TRiAcctEstimateItemHisList;

    public TRiAcctEstimateItem() {
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public void setEntryCode(String entryCode) {
        this.entryCode = entryCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public TRiAcctEstimate getTRiAcctEstimate() {
        return TRiAcctEstimate;
    }

    public void setTRiAcctEstimate(TRiAcctEstimate tRiAcctEstimate) {
        TRiAcctEstimate = tRiAcctEstimate;
    }

    public List<TRiAcctEstimateItemHis> getTRiAcctEstimateItemHisList() {
        if (TRiAcctEstimateItemHisList == null) {
            TRiAcctEstimateItemHisList = new ArrayList<TRiAcctEstimateItemHis>();
        }
        return TRiAcctEstimateItemHisList;
    }

    public void setTRiAcctEstimateItemHisList(List<TRiAcctEstimateItemHis> tRiAcctEstimateItemHisList) {
        TRiAcctEstimateItemHisList = tRiAcctEstimateItemHisList;
    }

    @Override
    public Long getPrimaryKey() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPrimaryKey(Long key) {
        // TODO Auto-generated method stub

    }

}
