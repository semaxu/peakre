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
 * The persistent class for the T_RI_CONT_ATTACHMENT_TABLE_PC database table.
 */
@Entity
@Table(name = "T_RI_CONT_DEDUCTIONS_ATTACH")
@NamedQueries({
        @NamedQuery(name = "TRiContDeductionsAttach.findAll", query = "SELECT t FROM TRiContDeductionsAttach t WHERE t.isActive != 'N'"),
        @NamedQuery(name = "TRiContDeductionsAttach.loadBySlidingId", query = "SELECT t FROM TRiContDeductionsAttach t WHERE t.slidingId=:slidingId and t.isActive != 'N'")

})
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContDeductionsAttach extends BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
    @Column(name = "ATTACHMENT_ID")
    @XmlTransient
    private Long attachmentId;

    @Column(name = "COMMISSION")
    private BigDecimal commission;

    @Column(name = "LR_FROM")
    private BigDecimal lrFrom;

    @Column(name = "LR_TO")
    private BigDecimal lrTo;

    @Column(name = "SLIDING_ID")
    @XmlTransient
    private Long slidingId;

    @Column(name = "TYPE")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSlidingId() {
        return slidingId;
    }

    public void setSlidingId(Long slidingId) {
        this.slidingId = slidingId;
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

    public TRiContDeductionsAttach() {
        // default Y
        this.isActive = "Y";
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getLrFrom() {
        return lrFrom;
    }

    public void setLrFrom(BigDecimal lrFrom) {
        this.lrFrom = lrFrom;
    }

    public BigDecimal getLrTo() {
        return lrTo;
    }

    public void setLrTo(BigDecimal lrTo) {
        this.lrTo = lrTo;
    }

    public static Long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public Long getPrimaryKey() {
        return this.getAttachmentId();
    }

    @Override
    public void setPrimaryKey(Long key) {
        this.setAttachmentId(key);

    }

}
