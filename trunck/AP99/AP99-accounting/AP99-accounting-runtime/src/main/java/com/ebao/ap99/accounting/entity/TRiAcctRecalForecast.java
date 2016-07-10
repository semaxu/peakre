package com.ebao.ap99.accounting.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_ACCT_RECAL_FORECAST database table.
 */
@Entity
@Table(name = "T_RI_ACCT_RECAL_FORECAST")
@NamedQueries({ @NamedQuery(name = "TRiAcctRecalForecast.findAll", query = "SELECT t FROM TRiAcctRecalForecast t"),
        @NamedQuery(name = "TRiAcctRecalForecast.getEventOfSOA$Withdraw", query = "SELECT t FROM TRiAcctRecalForecast t WHERE t.eventType IN (2, 3) AND t.status = 1 AND t.calcQuarter = :calcQuarter ORDER BY t.insertTime"),
        @NamedQuery(name = "TRiAcctRecalForecast.getEventTypeBySectionId", query = "SELECT t.eventType FROM TRiAcctRecalForecast t WHERE t.sectionId = :sectionId AND t.calcQuarter = :calcQuarter AND t.status = 1") })

public class TRiAcctRecalForecast extends BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
    @Column(name = "EVENT_ID")
    private Long eventId;

    @Column(name = "EVENT_TYPE")
    private Integer eventType;

    @Column(name = "CALC_QUARTER")
    private String calcQuarter;

    @Column(name = "SECTION_ID")
    private Long sectionId;

    @Column(name = "SOA_ID")
    private Long soaId;

    private Integer status;

    public TRiAcctRecalForecast() {

    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getCalcQuarter() {
        return calcQuarter;
    }

    public void setCalcQuarter(String calcQuarter) {
        this.calcQuarter = calcQuarter;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Long getSoaId() {
        return soaId;
    }

    public void setSoaId(Long soaId) {
        this.soaId = soaId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
