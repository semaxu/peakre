package com.ebao.ap99.accounting.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_ACCOUNTING_SEGMENT database table.
 */
@Entity
@Table(name = "T_RI_ACCT_SEGMENT")
@NamedQuery(name = "TRiAcctSegment.findAll", query = "SELECT t FROM TRiAcctSegment t")
public class TRiAcctSegment extends BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
    @Column(name = "SEGMENT_ID")
    private Long segmentId;

    private String cob;

    @Column(name = "CONTRACT_NATURE")
    private String contractNature;

    @Column(name = "SEGMENT_CODE")
    private String segmentCode;

    @Column(name = "SEGMENT_NAME")
    private String segmentName;

    private Integer status;

    @Column(name = "CEDENT_COUNTRY")
    private String cedentCountry;

    @Temporal(TemporalType.DATE)
    @Column(name = "UW_YEAR")
    private Date uwYear;

    public TRiAcctSegment() {
    }

    public Long getSegmentId() {
        return this.segmentId;
    }

    public void setSegmentId(Long segmentId) {
        this.segmentId = segmentId;
    }

    public String getCob() {
        return this.cob;
    }

    public void setCob(String cob) {
        this.cob = cob;
    }

    public String getContractNature() {
        return this.contractNature;
    }

    public void setContractNature(String contractNature) {
        this.contractNature = contractNature;
    }

    public String getSegmentCode() {
        return this.segmentCode;
    }

    public void setSegmentCode(String segmentCode) {
        this.segmentCode = segmentCode;
    }

    public String getSegmentName() {
        return this.segmentName;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCedentCountry() {
        return cedentCountry;
    }

    public void setCedentCountry(String cedentCountry) {
        this.cedentCountry = cedentCountry;
    }

    public Date getUwYear() {
        return this.uwYear;
    }

    public void setUwYear(Date uwYear) {
        this.uwYear = uwYear;
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
