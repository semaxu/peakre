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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_ACCT_ESTIMATE_ITEM_HIS database table.
 */
@Entity
@Table(name = "T_RI_ACCT_ESTIMATE_ITEM_HIS")
@NamedQueries({
        @NamedQuery(name = "TRiAcctEstimateItemHis.findAll", query = "SELECT t FROM TRiAcctEstimateItemHis t") })

public class TRiAcctEstimateItemHis extends BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
    @Column(name = "HIS_ID")
    private Long hisId;

    @Column(name = "ENTRY_CODE")
    private String entryCode;

    private BigDecimal amount;

    private Integer status;

    @Column(name = "CHANGE_REASON")
    private String changeReason;

    @Column(name = "CALC_FORMULA")
    private String calcFormula;

    @Column(name = "ITEM_ID", insertable = false, updatable = false)
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private TRiAcctEstimateItem TRiAcctEstimateItem;

    public TRiAcctEstimateItemHis() {
    }

    public Long getHisId() {
        return hisId;
    }

    public void setHisId(Long hisId) {
        this.hisId = hisId;
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

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public String getCalcFormula() {
        return calcFormula;
    }

    public void setCalcFormula(String calcFormula) {
        this.calcFormula = calcFormula;
    }

    public TRiAcctEstimateItem getTRiAcctEstimateItem() {
        return TRiAcctEstimateItem;
    }

    public void setTRiAcctEstimateItem(TRiAcctEstimateItem tRiAcctEstimateItem) {
        TRiAcctEstimateItem = tRiAcctEstimateItem;
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
