package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_CANCEL database table.
 */
@Entity
@Table(name = "T_RI_CONT_CANCEL")

@NamedQueries({
        @NamedQuery(name = "TRiContCancel.findAll", query = "SELECT t FROM TRiContCancel t WHERE t.isActive != 'N'"),
        @NamedQuery(name = "TRiContCancel.loadByContCompId", query = "SELECT t FROM TRiContCancel t WHERE t.contCompId = :contCompId AND t.isActive != 'N'") })
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContCancel extends BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CONT_COMP_ID")
    @XmlTransient
    private Long contCompId;

    @Column(name = "DNOC_POLITICAL_DAY")
    private BigDecimal dnocPoliticalDay;

    @Column(name = "DNOC_POLITICAL_MONTH")
    private BigDecimal dnocPoliticalMonth;

    @Column(name = "DNOC_SANCTION_DAY")
    private BigDecimal dnocSanctionDay;

    @Column(name = "DNOC_SANCTION_MONTH")
    private BigDecimal dnocSanctionMonth;

    @Column(name = "DNOC_WAR_DAY")
    private BigDecimal dnocWarDay;

    @Column(name = "DNOC_WAR_MONTH")
    private BigDecimal dnocWarMonth;

    @Column(name = "PNOC_AUTOMATIC")
    private String pnocAutomatic;

    @Column(name = "PNOC_CEDENT_DAY")
    private BigDecimal pnocCedentDay;

    @Column(name = "PNOC_CEDENT_MONTH")
    private BigDecimal pnocCedentMonth;

    @Column(name = "PNOC_REINSURER_DAY")
    private BigDecimal pnocReinsurerDay;

    @Column(name = "PNOC_REINSURER_MONTH")
    private BigDecimal pnocReinsurerMonth;

    @Column(name = "IS_ACTIVE")
    @XmlTransient
    private String isActive;

    // bi-directional one-to-one association to TRiContractInfo
    @OneToOne
    @JoinColumn(name = "CONT_COMP_ID")
    @XmlTransient
    private TRiContractInfo TRiContractInfo;

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

    public TRiContCancel() {
        // default Y
        this.isActive = "Y";
    }

    public BigDecimal getDnocPoliticalDay() {
        return this.dnocPoliticalDay;
    }

    public void setDnocPoliticalDay(BigDecimal dnocPoliticalDay) {
        this.dnocPoliticalDay = dnocPoliticalDay;
    }

    public BigDecimal getDnocPoliticalMonth() {
        return this.dnocPoliticalMonth;
    }

    public void setDnocPoliticalMonth(BigDecimal dnocPoliticalMonth) {
        this.dnocPoliticalMonth = dnocPoliticalMonth;
    }

    public BigDecimal getDnocSanctionDay() {
        return this.dnocSanctionDay;
    }

    public void setDnocSanctionDay(BigDecimal dnocSanctionDay) {
        this.dnocSanctionDay = dnocSanctionDay;
    }

    public BigDecimal getDnocSanctionMonth() {
        return this.dnocSanctionMonth;
    }

    public void setDnocSanctionMonth(BigDecimal dnocSanctionMonth) {
        this.dnocSanctionMonth = dnocSanctionMonth;
    }

    public BigDecimal getDnocWarDay() {
        return this.dnocWarDay;
    }

    public void setDnocWarDay(BigDecimal dnocWarDay) {
        this.dnocWarDay = dnocWarDay;
    }

    public BigDecimal getDnocWarMonth() {
        return this.dnocWarMonth;
    }

    public void setDnocWarMonth(BigDecimal dnocWarMonth) {
        this.dnocWarMonth = dnocWarMonth;
    }

    public String getPnocAutomatic() {
        return this.pnocAutomatic;
    }

    public void setPnocAutomatic(String pnocAutomatic) {
        this.pnocAutomatic = pnocAutomatic;
    }

    public BigDecimal getPnocCedentDay() {
        return this.pnocCedentDay;
    }

    public void setPnocCedentDay(BigDecimal pnocCedentDay) {
        this.pnocCedentDay = pnocCedentDay;
    }

    public BigDecimal getPnocCedentMonth() {
        return this.pnocCedentMonth;
    }

    public void setPnocCedentMonth(BigDecimal pnocCedentMonth) {
        this.pnocCedentMonth = pnocCedentMonth;
    }

    public BigDecimal getPnocReinsurerDay() {
        return this.pnocReinsurerDay;
    }

    public void setPnocReinsurerDay(BigDecimal pnocReinsurerDay) {
        this.pnocReinsurerDay = pnocReinsurerDay;
    }

    public BigDecimal getPnocReinsurerMonth() {
        return this.pnocReinsurerMonth;
    }

    public void setPnocReinsurerMonth(BigDecimal pnocReinsurerMonth) {
        this.pnocReinsurerMonth = pnocReinsurerMonth;
    }

    @Override
    public Long getPrimaryKey() {
        return null;
    }

    @Override
    public void setPrimaryKey(Long key) {
    }

    public Long getContCompId() {
        return contCompId;
    }

    public void setContCompId(Long contCompId) {
        this.contCompId = contCompId;
    }

    public TRiContractInfo getTRiContractInfo() {
        return TRiContractInfo;
    }

    public void setTRiContractInfo(TRiContractInfo tRiContractInfo) {
        TRiContractInfo = tRiContractInfo;
    }

}
