package com.ebao.ap99.contract.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONTRACT_AREAPERIL database table.
 */
@Entity
@Table(name = "T_RI_CONTRACT_AREAPERIL")
@NamedQuery(name = "TRiContractAreaperil.findAll", query = "SELECT t FROM TRiContractAreaperil t WHERE t.isActive != 'N'")
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContractAreaperil extends BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    // @SequenceGenerator(name="T_RI_CONTRACT_AREAPERIL_CONTCOMPID_GENERATOR" )
    // @GeneratedValue(strategy=GenerationType.SEQUENCE,
    // generator="T_RI_CONTRACT_AREAPERIL_CONTCOMPID_GENERATOR")
    @Column(name = "CONT_COMP_ID")
    @XmlTransient
    private Long contCompId;

    private String covered;

    @Column(name = "COVERED_REMARK")
    private String coveredRemark;

    private String peril;

    @Column(name = "PERIL_REMARK")
    private String perilRemark;

    // bi-directional one-to-one association to TRiContractInfo
    @OneToOne
    @JoinColumn(name = "CONT_COMP_ID")
    @XmlTransient
    private TRiContractInfo TRiContractInfo;

    public TRiContractInfo getTRiContractInfo() {
        return TRiContractInfo;
    }

    public void setTRiContractInfo(TRiContractInfo tRiContractInfo) {
        TRiContractInfo = tRiContractInfo;
    }

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

    public TRiContractAreaperil() {
        // default Y
        this.isActive = "Y";
    }

    public Long getContCompId() {
        return this.contCompId;
    }

    public void setContCompId(Long contCompId) {
        this.contCompId = contCompId;
    }

    public String getCovered() {
        return this.covered;
    }

    public void setCovered(String covered) {
        this.covered = covered;
    }

    public String getCoveredRemark() {
        return this.coveredRemark;
    }

    public void setCoveredRemark(String coveredRemark) {
        this.coveredRemark = coveredRemark;
    }

    public String getPeril() {
        return this.peril;
    }

    public void setPeril(String peril) {
        this.peril = peril;
    }

    public String getPerilRemark() {
        return this.perilRemark;
    }

    public void setPerilRemark(String perilRemark) {
        this.perilRemark = perilRemark;
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
