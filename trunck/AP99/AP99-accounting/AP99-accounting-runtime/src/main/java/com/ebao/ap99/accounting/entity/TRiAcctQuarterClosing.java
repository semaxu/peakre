package com.ebao.ap99.accounting.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_ACC_QUARTER_CLOSING database table.
 */
@Entity
@Table(name = "T_RI_ACCT_QUARTER_CLOSING")
@NamedQueries({ @NamedQuery(name = "TRiAcctQuarterClosing.findAll", query = "SELECT t FROM TRiAcctQuarterClosing t"),
        @NamedQuery(name = "TRiAcctQuarterClosing.findCurrentProcessingQuarter", query = "SELECT t FROM TRiAcctQuarterClosing t WHERE closingStatus = 1 or closingStatus = 2") })

public class TRiAcctQuarterClosing extends BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
    @Column(name = "QUARTER_ID")
    private Long quarterId;

    @Column(name = "FINANCIAL_YEAR")
    private Integer financialYear;

    @Column(name = "FINANCIAL_QUARTER")
    private Integer financialQuarter;

    @Column(name = "CLOSING_STATUS")
    private Integer closingStatus;

    @Temporal(TemporalType.DATE)
    @Column(name = "CLOSING_DATE")
    private Date closingDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "START_DATE")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "CLOSED_DATE")
    private Date closedDate;

    public TRiAcctQuarterClosing() {
    }

    public TRiAcctQuarterClosing(Integer closingStatus) {
        this.closingStatus = closingStatus;
    }

    public Long getQuarterId() {
        return quarterId;
    }

    public void setQuarterId(Long quarterId) {
        this.quarterId = quarterId;
    }

    public Integer getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(Integer financialYear) {
        this.financialYear = financialYear;
    }

    public Integer getFinancialQuarter() {
        return financialQuarter;
    }

    public void setFinancialQuarter(Integer financialQuarter) {
        this.financialQuarter = financialQuarter;
    }

    public Integer getClosingStatus() {
        return closingStatus;
    }

    public void setClosingStatus(Integer closingStatus) {
        this.closingStatus = closingStatus;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
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
