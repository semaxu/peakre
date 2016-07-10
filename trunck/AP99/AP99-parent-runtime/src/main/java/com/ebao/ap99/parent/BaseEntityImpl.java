package com.ebao.ap99.parent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.envers.Audited;

import com.ebao.unicorn.platform.data.domain.BaseEntity;
import com.ebao.unicorn.platform.foundation.context.AppContext;
import com.ebao.unicorn.platform.foundation.utils.CGLibBeanMapHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.sf.cglib.beans.BeanMap;

@MappedSuperclass
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseEntityImpl implements BaseEntity {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Column(name = "INSERT_BY", nullable = false, updatable = false)
    @XmlTransient
    private Long insertBy;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "INSERT_TIME", nullable = false, updatable = false)
    @XmlTransient
    private Date insertTime;

    @JsonIgnore
    @Column(name = "UPDATE_BY", nullable = false)
    @XmlTransient
    private Long updateBy;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_TIME", nullable = false)
    @XmlTransient
    private Date updateTime;

    @Transient
    @XmlTransient
    private BaseEntity tempEntityCopyFrom;

    @Transient
    @XmlTransient
    private Map<String, Object> tempData;

    @JsonIgnore
    @Transient
    @XmlTransient
    private BeanMap cglibBeanMap;

    public BaseEntityImpl() {
        this.tempEntityCopyFrom = null;

        this.tempData = new HashMap();

        this.cglibBeanMap = null;
    }

    @JsonIgnore
    @XmlTransient
    public BaseEntity getTempEntityCopyFrom_() {
        return this.tempEntityCopyFrom;
    }

    @JsonIgnore
    public void setTempEntityCopyFrom_(BaseEntity tempEntityCopyFrom) {
        this.tempEntityCopyFrom = tempEntityCopyFrom;
    }

    @PostLoad
    protected void postLoad() {
    }

    @PrePersist
    protected void prePersist() {
        Long currentUserId = Long.valueOf(AppContext.getCurrentUser().getUserId());

        Date localTimeForCurrentUser = new Date();
        this.insertBy = currentUserId;
        this.insertTime = localTimeForCurrentUser;
        this.updateBy = currentUserId;
        this.updateTime = localTimeForCurrentUser;
    }

    @PreUpdate
    protected void preUpdate() {
        Long currentUserId = Long.valueOf(AppContext.getCurrentUser().getUserId());

        Date localTimeForCurrentUser = new Date();
        this.updateBy = currentUserId;
        this.updateTime = localTimeForCurrentUser;
    }

    public Long getInsertBy() {
        return this.insertBy;
    }

    public void setInsertBy(Long insertBy) {
        this.insertBy = insertBy;
    }

    public Date getInsertTime() {
        return this.insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Long getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public abstract Long getPrimaryKey();

    public abstract void setPrimaryKey(Long paramLong);

    public void clearPrimaryKeyCascade() {
        setPrimaryKey(null);
    }

    public boolean isPropertyExcludedInDataSync_(String propertyName) {
        return (("primaryKey".equals(propertyName)) || ("insertBy".equals(propertyName))
                || ("insertTime".equals(propertyName)) || ("updateBy".equals(propertyName))
                || ("updateTime".equals(propertyName)) || ("CGLibBeanMap_".equals(propertyName))
                || ("tempEntityCopyFrom_".equals(propertyName)));
    }

    public boolean isPropertyIncludedInDataSync_(String propertyName) {
        return false;
    }

    @JsonProperty("TempData")
    public final Map<String, Object> getTempDataMap_() {
        return this.tempData;
    }

    @JsonProperty("TempData")
    public final void setTempDataMap_(Map<String, Object> tempData) {
        this.tempData = tempData;
        if (this.tempData == null)
            this.tempData = new HashMap();
    }

    public final void setTempValue(String key, Object value) {
        this.tempData.put(key, value);
    }

    public final <T> T getTempValue(String key) {
        return (T) this.tempData.get(key);
    }

    @JsonIgnore
    public BeanMap getCGLibBeanMap_() {
        if (this.cglibBeanMap == null) {
            this.cglibBeanMap = CGLibBeanMapHelper.createBeanMap(this);
        }
        return this.cglibBeanMap;
    }

    public void postProcessManualCreatedEntity_() {
    }

    public void postJsonConverter_() {
    }

    public void postDataSync_() {
    }

    public void postEnversLoad_() {
    }

    public boolean isUsingSetterMethodToChangeCollectionPropertyValue_(String propertyName) {
        return false;
    }

    public boolean isPropertyChangableInClientSide(String propertyName) {
        return true;
    }

    public boolean isPropertyReferencePartyModel(String propertyName) {
        return false;
    }
}
