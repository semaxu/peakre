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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the T_RI_ACCT_FEE database table.
 */
@Entity
@Table(name = "T_RI_ACCT_FEE")
@NamedQuery(name = "TRiAcctFee.findAll", query = "SELECT t FROM TRiAcctFee t")
public class TRiAcctFee extends com.ebao.ap99.parent.BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
    @Column(name = "FEE_ID")
    private long feeId;

    @Column(name = "BUSI_TYPE")
    private Integer busiType;

    @Column(name = "ESTIMATE_ID")
    private Long estimateId;

    //bi-directional many-to-one association to TRiAcctFeeDetail
    @OneToMany(mappedBy = "TRiAcctFee", cascade = { CascadeType.ALL })
    private List<TRiAcctFeeDetail> TRiAcctFeeDetails = new ArrayList<TRiAcctFeeDetail>();

    public TRiAcctFee() {
    }

    public long getFeeId() {
        return this.feeId;
    }

    public void setFeeId(long feeId) {
        this.feeId = feeId;
    }

    public List<TRiAcctFeeDetail> getTRiAcctFeeDetails() {
        return this.TRiAcctFeeDetails;
    }

    public void setTRiAcctFeeDetails(List<TRiAcctFeeDetail> TRiAcctFeeDetails) {
        this.TRiAcctFeeDetails = TRiAcctFeeDetails;
    }

    public TRiAcctFeeDetail addTRiAcctFeeDetail(TRiAcctFeeDetail TRiAcctFeeDetail) {
        getTRiAcctFeeDetails().add(TRiAcctFeeDetail);
        TRiAcctFeeDetail.setTRiAcctFee(this);

        return TRiAcctFeeDetail;
    }

    public TRiAcctFeeDetail removeTRiAcctFeeDetail(TRiAcctFeeDetail TRiAcctFeeDetail) {
        getTRiAcctFeeDetails().remove(TRiAcctFeeDetail);
        TRiAcctFeeDetail.setTRiAcctFee(null);

        return TRiAcctFeeDetail;
    }

    public Integer getBusiType() {
        return busiType;
    }

    public void setBusiType(Integer busiType) {
        this.busiType = busiType;
    }

    public Long getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(Long estimateId) {
        this.estimateId = estimateId;
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
