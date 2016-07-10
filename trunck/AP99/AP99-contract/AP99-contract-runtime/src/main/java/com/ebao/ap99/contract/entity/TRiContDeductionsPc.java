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
 * The persistent class for the T_RI_CONT_PC_SLIDING_DETAIL database table.
 */
@Entity
@Table(name = "T_RI_CONT_DEDUCTIONS_PC")
@NamedQueries({
        @NamedQuery(name = "TRiContDeductionsPc.findAll", query = "SELECT t FROM TRiContDeductionsPc t WHERE t.isActive != 'N'"),
        @NamedQuery(name = "TRiContDeductionsPc.loadByDeductionsId", query = "SELECT t FROM TRiContDeductionsPc t WHERE t.deductionsId=:deductionsId and t.isActive != 'N'") })
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContDeductionsPc extends BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
    @Column(name = "PC_SLIDING_ID")
    @XmlTransient
    private Long pcSlidingId;

    @Column(name = "DEDUCTIONS_ID")
    @XmlTransient
    private Long deductionsId;

    @Column(name = "MAXIMUM_LOSS_PC")
    private BigDecimal maximumLossPc;

    @Column(name = "MAXIMUM_R_I_PC")
    private BigDecimal maximumRIPc;

    @Column(name = "MINIMUM_LOSS_PC")
    private BigDecimal minimumLossPc;

    @Column(name = "MINIMUM_R_I_PC")
    private BigDecimal minimumRIPc;

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

    public TRiContDeductionsPc() {
        // default Y
        this.isActive = "Y";
    }

    public Long getPcSlidingId() {
        return this.pcSlidingId;
    }

    public void setPcSlidingId(Long pcSlidingId) {
        this.pcSlidingId = pcSlidingId;
    }

    public Long getDeductionsId() {
        return this.deductionsId;
    }

    public void setDeductionsId(Long deductionsId) {
        this.deductionsId = deductionsId;
    }

    public BigDecimal getMaximumLossPc() {
        return this.maximumLossPc;
    }

    public void setMaximumLossPc(BigDecimal maximumLossPc) {
        this.maximumLossPc = maximumLossPc;
    }

    public BigDecimal getMaximumRIPc() {
        return this.maximumRIPc;
    }

    public void setMaximumRIPc(BigDecimal maximumRIPc) {
        this.maximumRIPc = maximumRIPc;
    }

    public BigDecimal getMinimumLossPc() {
        return this.minimumLossPc;
    }

    public void setMinimumLossPc(BigDecimal minimumLossPc) {
        this.minimumLossPc = minimumLossPc;
    }

    public BigDecimal getMinimumRIPc() {
        return this.minimumRIPc;
    }

    public void setMinimumRIPc(BigDecimal minimumRIPc) {
        this.minimumRIPc = minimumRIPc;
    }

    @Override
    public Long getPrimaryKey() {
        return this.getPcSlidingId();
    }

    @Override
    public void setPrimaryKey(Long key) {
        this.setPcSlidingId(key);
    }

}
