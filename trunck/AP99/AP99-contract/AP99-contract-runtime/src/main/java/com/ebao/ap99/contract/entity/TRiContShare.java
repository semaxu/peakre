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
 * The persistent class for the T_RI_CONT_SHARE database table.
 */
@Entity
@Table(name = "T_RI_CONT_SHARE")
@NamedQueries({
        @NamedQuery(name = "TRiContShare.findAll", query = "SELECT t FROM TRiContShare t WHERE t.isActive != 'N'"),
        @NamedQuery(name = "TRiContShare.loadByContCompId", query = "SELECT t FROM TRiContShare t WHERE t.contCompId = :contCompId and t.isActive != 'N'"),
        @NamedQuery(name = "TRiContShare.deleteByContCompId", query = "delete from TRiContShare t where t.contCompId = :contCompId and t.isActive != 'N'") })
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContShare extends BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
    @Column(name = "SHARE_ID")
    @XmlTransient
    private Long shareId;

    @Column(name = "CEDENT_RETENTION")
    private BigDecimal cedentRetention;

    @Column(name = "CEDED")
    private BigDecimal ceded;

    @Column(name = "COINSURANCE")
    private BigDecimal coinsurance;

    @Column(name = "COMMENTS1")
    private String comments1;

    @Column(name = "COMMENTS2")
    private String comments2;

    @Column(name = "COMMENTS3")
    private String comments3;

    @Column(name = "CONT_COMP_ID")
    @XmlTransient
    private Long contCompId;

    @Column(name = "SHARE_DEFINE")
    private BigDecimal shareDefine;

    @Column(name = "SIGNED_SHARES1")
    private BigDecimal signedShares1;

    @Column(name = "SIGNED_SHARES2")
    private BigDecimal signedShares2;

    @Column(name = "SIGNED_SHARES3")
    private BigDecimal signedShares3;

    @Column(name = "WRITTEN_SHARE1")
    private BigDecimal writtenShare1;

    @Column(name = "WRITTEN_SHARE2")
    private BigDecimal writtenShare2;

    @Column(name = "WRITTEN_SHARE3")
    private BigDecimal writtenShare3;

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

    public TRiContShare() {
        // default Y
        this.isActive = "Y";
    }

    public Long getShareId() {
        return this.shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public BigDecimal getCedentRetention() {
        return this.cedentRetention;
    }

    public void setCedentRetention(BigDecimal cedentRetention) {
        this.cedentRetention = cedentRetention;
    }

    public BigDecimal getCeded() {
        return this.ceded;
    }

    public void setCeded(BigDecimal ceded) {
        this.ceded = ceded;
    }

    public BigDecimal getCoinsurance() {
        return this.coinsurance;
    }

    public void setCoinsurance(BigDecimal coinsurance) {
        this.coinsurance = coinsurance;
    }

    public String getComments1() {
        return this.comments1;
    }

    public void setComments1(String comments1) {
        this.comments1 = comments1;
    }

    public String getComments2() {
        return this.comments2;
    }

    public void setComments2(String comments2) {
        this.comments2 = comments2;
    }

    public String getComments3() {
        return this.comments3;
    }

    public void setComments3(String comments3) {
        this.comments3 = comments3;
    }

    public Long getContCompId() {
        return this.contCompId;
    }

    public void setContCompId(Long contCompId) {
        this.contCompId = contCompId;
    }

    public BigDecimal getShareDefine() {
        return this.shareDefine;
    }

    public void setShareDefine(BigDecimal shareDefine) {
        this.shareDefine = shareDefine;
    }

    public BigDecimal getSignedShares1() {
        return this.signedShares1;
    }

    public void setSignedShares1(BigDecimal signedShares1) {
        this.signedShares1 = signedShares1;
    }

    public BigDecimal getSignedShares2() {
        return this.signedShares2;
    }

    public void setSignedShares2(BigDecimal signedShares2) {
        this.signedShares2 = signedShares2;
    }

    public BigDecimal getSignedShares3() {
        return this.signedShares3;
    }

    public void setSignedShares3(BigDecimal signedShares3) {
        this.signedShares3 = signedShares3;
    }

    public BigDecimal getWrittenShare1() {
        return this.writtenShare1;
    }

    public void setWrittenShare1(BigDecimal writtenShare1) {
        this.writtenShare1 = writtenShare1;
    }

    public BigDecimal getWrittenShare2() {
        return this.writtenShare2;
    }

    public void setWrittenShare2(BigDecimal writtenShare2) {
        this.writtenShare2 = writtenShare2;
    }

    public BigDecimal getWrittenShare3() {
        return this.writtenShare3;
    }

    public void setWrittenShare3(BigDecimal writtenShare3) {
        this.writtenShare3 = writtenShare3;
    }

    @Override
    public Long getPrimaryKey() {
        return this.getShareId();
    }

    @Override
    public void setPrimaryKey(Long key) {
        this.setShareId(key);
    }

}
