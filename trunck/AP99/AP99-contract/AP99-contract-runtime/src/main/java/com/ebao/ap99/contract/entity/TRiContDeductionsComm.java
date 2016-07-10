package com.ebao.ap99.contract.entity;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_COMM_SLIDING_DETAIL database table.
 */
@Entity
@Table(name = "T_RI_CONT_DEDUCTIONS_COMM")
@NamedQueries({
        @NamedQuery(name = "TRiContDeductionsComm.findAll", query = "SELECT t FROM TRiContDeductionsComm t WHERE t.isActive != 'N'"),
        @NamedQuery(name = "TRiContDeductionsComm.loadByDeductionsId", query = "SELECT t FROM TRiContDeductionsComm t WHERE t.deductionsId=:deductionsId and t.isActive != 'N'") })
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContDeductionsComm extends BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "COMM_SLIDING_DETAIL_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
    @XmlTransient
    private Long commSlidingDetailId;

    @Column(name = "DEDUCTIONS_ID")
    @XmlTransient
    private Long deductionsId;

    @Column(name = "FIRST_CAL")
    private BigDecimal firstCal;

    @Column(name = "LOSS_INCLUDE_I_B_N_R")
    private String lossIncludeIBNR;

    @Column(name = "MAXIMUM_LOSS_SS")
    private BigDecimal maximumLossSs;

    @Column(name = "MAXIMUM_R_I_SS")
    private BigDecimal maximumRISs;

    @Column(name = "MINIMUM_LOSS_SS")
    private BigDecimal minimumLossSs;

    @Column(name = "MINIMUM_R_I_SS")
    private BigDecimal minimumRISs;

    @Column(name = "SUBSEQUENT_CALC")
    private BigDecimal subsequentCalc;

    // bi-directional many-to-one association to TRiContDeductionsCarried
    @OneToMany(mappedBy = "TRiContDeductionsComm", cascade = { CascadeType.ALL })
    @XmlTransient
    private List<TRiContDeductionsCarried> TRiContDeductionsCarrieds = new ArrayList<TRiContDeductionsCarried>();

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

    public TRiContDeductionsComm() {
        // default Y
        this.isActive = "Y";
    }

    public Long getCommSlidingDetailId() {
        return this.commSlidingDetailId;
    }

    public void setCommSlidingDetailId(Long commSlidingDetailId) {
        this.commSlidingDetailId = commSlidingDetailId;
    }

    public Long getDeductionsId() {
        return this.deductionsId;
    }

    public void setDeductionsId(Long deductionsId) {
        this.deductionsId = deductionsId;
    }

    public BigDecimal getFirstCal() {
        return this.firstCal;
    }

    public void setFirstCal(BigDecimal firstCal) {
        this.firstCal = firstCal;
    }

    public String getLossIncludeIBNR() {
        return this.lossIncludeIBNR;
    }

    public void setLossIncludeIBNR(String lossIncludeIBNR) {
        this.lossIncludeIBNR = lossIncludeIBNR;
    }

    public BigDecimal getMaximumLossSs() {
        return this.maximumLossSs;
    }

    public void setMaximumLossSs(BigDecimal maximumLossSs) {
        this.maximumLossSs = maximumLossSs;
    }

    public BigDecimal getMaximumRISs() {
        return this.maximumRISs;
    }

    public void setMaximumRISs(BigDecimal maximumRISs) {
        this.maximumRISs = maximumRISs;
    }

    public BigDecimal getMinimumLossSs() {
        return this.minimumLossSs;
    }

    public void setMinimumLossSs(BigDecimal minimumLossSs) {
        this.minimumLossSs = minimumLossSs;
    }

    public BigDecimal getMinimumRISs() {
        return this.minimumRISs;
    }

    public void setMinimumRISs(BigDecimal minimumRISs) {
        this.minimumRISs = minimumRISs;
    }

    public BigDecimal getSubsequentCalc() {
        return this.subsequentCalc;
    }

    public void setSubsequentCalc(BigDecimal subsequentCalc) {
        this.subsequentCalc = subsequentCalc;
    }

    @Override
    public Long getPrimaryKey() {
        return this.getCommSlidingDetailId();
    }

    @Override
    public void setPrimaryKey(Long key) {
        this.setCommSlidingDetailId(key);

    }

    public List<TRiContDeductionsCarried> getTRiContDeductionsCarrieds() {
        return TRiContDeductionsCarrieds;
    }

    public void setTRiContDeductionsCarrieds(List<TRiContDeductionsCarried> tRiContDeductionsCarrieds) {
        TRiContDeductionsCarrieds = tRiContDeductionsCarrieds;
    }

    public TRiContDeductionsCarried addTRiContDeductionsCarried(TRiContDeductionsCarried TRiContDeductionsCarried) {
        getTRiContDeductionsCarrieds().add(TRiContDeductionsCarried);
        TRiContDeductionsCarried.setTRiContDeductionsComm(this);

        return TRiContDeductionsCarried;
    }

    public TRiContDeductionsCarried removeTRiContDeductionsCarried(TRiContDeductionsCarried TRiContDeductionsCarried) {
        getTRiContDeductionsCarrieds().remove(TRiContDeductionsCarried);
        TRiContDeductionsCarried.setTRiContDeductionsComm(null);

        return TRiContDeductionsCarried;
    }
}
