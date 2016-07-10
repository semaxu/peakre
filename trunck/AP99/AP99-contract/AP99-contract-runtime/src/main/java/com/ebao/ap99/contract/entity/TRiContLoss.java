package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_LOSS database table.
 */
@Entity
@Table(name = "T_RI_CONT_LOSS")

@NamedQueries({
        @NamedQuery(name = "TRiContLoss.findAll", query = "SELECT t FROM TRiContLoss t WHERE t.isActive != 'N'"),
        @NamedQuery(name = "TRiContLoss.loadByContCompId", query = "SELECT t FROM TRiContLoss t WHERE t.contCompId = :contCompId and t.isActive != 'N'") })

@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContLoss extends BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
    @Column(name = "LOSS_ID")
    @XmlTransient
    private Long lossId;

    @Column(name = "CEDENT_PARTICIP")
    private BigDecimal cedentParticip;

    @Column(name = "CONT_COMP_ID")
    @XmlTransient
    private Long contCompId;

    @Column(name = "FIRST_CALC_AFTER")
    private BigDecimal firstCalcAfter;

    @Column(name = "MAX_RATIO")
    private BigDecimal maxRatio;

    @Column(name = "MIN_RATIO")
    private BigDecimal minRatio;

    @Column(name = "PARTICIP_BASE")
    private String participBase;

    @Column(name = "SUB_CALC_EVERY")
    private BigDecimal subCalcEvery;

    // @Column(name="UPDATE_BY")
    // private BigDecimal updateBy;
    //
    // @Temporal(TemporalType.DATE)
    // @Column(name="UPDATE_TIME")
    // private Date updateTime;

    @Column(name = "IS_ACTIVE")
    @XmlTransient
    private String isActive;

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        if (StringUtils.isNullOrEmpty(isActive)) {
            this.isActive = "Y";
        } else {
            this.isActive = isActive;
        }
    }

    public TRiContLoss() {
        // default Y
        this.isActive = "Y";
    }

    public Long getLossId() {
        return this.lossId;
    }

    public void setLossId(Long lossId) {
        this.lossId = lossId;
    }

    public BigDecimal getCedentParticip() {
        return this.cedentParticip;
    }

    public void setCedentParticip(BigDecimal cedentParticip) {
        this.cedentParticip = cedentParticip;
    }

    public Long getContCompId() {
        return this.contCompId;
    }

    public void setContCompId(Long contCompId) {
        this.contCompId = contCompId;
    }

    public BigDecimal getFirstCalcAfter() {
        return this.firstCalcAfter;
    }

    public void setFirstCalcAfter(BigDecimal firstCalcAfter) {
        this.firstCalcAfter = firstCalcAfter;
    }

    public BigDecimal getMaxRatio() {
        return this.maxRatio;
    }

    public void setMaxRatio(BigDecimal maxRatio) {
        this.maxRatio = maxRatio;
    }

    public BigDecimal getMinRatio() {
        return this.minRatio;
    }

    public void setMinRatio(BigDecimal minRatio) {
        this.minRatio = minRatio;
    }

    public String getParticipBase() {
        return this.participBase;
    }

    public void setParticipBase(String participBase) {
        this.participBase = participBase;
    }

    public BigDecimal getSubCalcEvery() {
        return this.subCalcEvery;
    }

    public void setSubCalcEvery(BigDecimal subCalcEvery) {
        this.subCalcEvery = subCalcEvery;
    }

    @Override
    public Long getPrimaryKey() {
        return this.getLossId();
    }

    @Override
    public void setPrimaryKey(Long key) {
        this.setLossId(key);

    }

}
