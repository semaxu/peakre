package com.ebao.ap99.accounting.entity;

import java.io.Serializable;
import java.util.Date;
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

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_ACCT_ESTIMATE database table.
 */
@Entity
@Table(name = "T_RI_ACCT_ESTIMATE")
@NamedQueries({ @NamedQuery(name = "TRiAcctEstimate.findAll", query = "SELECT t FROM TRiAcctEstimate t"),
        @NamedQuery(name = "TRiAcctEstimate.findBySectionIdAndYearQuarter", query = "FROM TRiAcctEstimate t WHERE t.contSectionId = :contSectionId AND t.cedentYear = :cedentYear AND t.cedentQuarter = :cedentQuarter"),
        @NamedQuery(name = "TRiAcctEstimate.findBySectionIdAndPeriod", query = "FROM TRiAcctEstimate t WHERE t.contSectionId = :contSectionId AND t.periodFrom <= :dateOfLoss AND t.periodTo + 1 > :dateOfLoss"),
        @NamedQuery(name = "TRiAcctEstimate.queryForecastSectionIdList", query = "SELECT DISTINCT t.contSectionId FROM TRiAcctEstimate t WHERE t.cedentYear || t.cedentQuarter <= :cedentQuarter AND status IN (1, 2, 6)"),
        @NamedQuery(name = "TRiAcctEstimate.querySectionIdListByContId", query = "SELECT DISTINCT t.contSectionId FROM TRiAcctEstimate t WHERE t.contId = :contId") })

public class TRiAcctEstimate extends BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
    @Column(name = "ESTIMATE_ID")
    private Long estimateId;

    @Column(name = "CONT_ID")
    private Long contId;

    @Column(name = "CONT_SECTION_ID")
    private Long contSectionId;

    @Column(name = "CEDENT_YEAR")
    private Integer cedentYear;

    @Column(name = "CEDENT_QUARTER")
    private Integer cedentQuarter;

    @Column(name = "QUARTER_SEQ")
    private Integer quarterSeq;

    @Column(name = "PERIOD_FROM")
    private Date periodFrom;

    @Column(name = "PERIOD_TO")
    private Date periodTo;

    private String currency;

    private Integer status;

    @OneToMany(mappedBy = "TRiAcctEstimate", cascade = { CascadeType.MERGE })
    private List<TRiAcctEstimateItem> TRiAcctEstimateItems;

    public TRiAcctEstimate() {
    }

    public TRiAcctEstimate(Long contSectionId) {
        this.contSectionId = contSectionId;
    }

    public Long getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(Long estimateId) {
        this.estimateId = estimateId;
    }

    public Long getContId() {
        return contId;
    }

    public void setContId(Long contId) {
        this.contId = contId;
    }

    public Long getContSectionId() {
        return contSectionId;
    }

    public void setContSectionId(Long contSectionId) {
        this.contSectionId = contSectionId;
    }

    public Integer getCedentYear() {
        return cedentYear;
    }

    public void setCedentYear(Integer cedentYear) {
        this.cedentYear = cedentYear;
    }

    public Integer getCedentQuarter() {
        return cedentQuarter;
    }

    public void setCedentQuarter(Integer cedentQuarter) {
        this.cedentQuarter = cedentQuarter;
    }

    public Integer getQuarterSeq() {
        return quarterSeq;
    }

    public void setQuarterSeq(Integer quarterSeq) {
        this.quarterSeq = quarterSeq;
    }

    public Date getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(Date periodFrom) {
        this.periodFrom = periodFrom;
    }

    public Date getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(Date periodTo) {
        this.periodTo = periodTo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TRiAcctEstimateItem> getTRiAcctEstimateItems() {
        return TRiAcctEstimateItems;
    }

    public void setTRiAcctEstimateItems(List<TRiAcctEstimateItem> tRiAcctEstimateItems) {
        TRiAcctEstimateItems = tRiAcctEstimateItems;
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
