package com.ebao.ap99.contract.entity;

import java.io.Serializable;

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
 * The persistent class for the T_RI_CONT_CLAUSES database table.
 */
@Entity
@Table(name = "T_RI_CONT_CLAUSES")

@NamedQueries({
        @NamedQuery(name = "TRiContClauses.findAll", query = "SELECT t FROM TRiContClauses t WHERE t.isActive != 'N'"),
        @NamedQuery(name = "TRiContClauses.loadByContCompId", query = "SELECT t FROM TRiContClauses t WHERE t.contCompId = :contCompId AND t.isActive != 'N'") })

@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContClauses extends BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
    @Column(name = "CLAUSES_ID")
    @XmlTransient
    private Long clausesId;

    @Column(name = "CLAUSES_RECOMMEND")
    private String clausesRecommend;

    @Column(name = "CLAUSES_REQUIRED")
    private String clausesRequired;

    @Column(name = "CONT_COMP_ID")
    @XmlTransient
    private Long contCompId;

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

    public TRiContClauses() {
        // default Y
        this.isActive = "Y";
    }

    public Long getClausesId() {
        return this.clausesId;
    }

    public void setClausesId(Long clausesId) {
        this.clausesId = clausesId;
    }

    public String getClausesRecommend() {
        return this.clausesRecommend;
    }

    public void setClausesRecommend(String clausesRecommend) {
        this.clausesRecommend = clausesRecommend;
    }

    public String getClausesRequired() {
        return this.clausesRequired;
    }

    public void setClausesRequired(String clausesRequired) {
        this.clausesRequired = clausesRequired;
    }

    public Long getContCompId() {
        return this.contCompId;
    }

    public void setContCompId(Long contCompId) {
        this.contCompId = contCompId;
    }

    @Override
    public Long getPrimaryKey() {
        return this.getClausesId();
    }

    @Override
    public void setPrimaryKey(Long key) {
        this.setClausesId(key);
    }

}
