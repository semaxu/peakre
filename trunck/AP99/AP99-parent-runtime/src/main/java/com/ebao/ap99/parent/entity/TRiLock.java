package com.ebao.ap99.parent.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the T_RI_LOCK database table.
 * 
 */
@Entity
@Table(name="T_RI_LOCK")
@NamedQuery(name="TRiLock.findAll", query="SELECT t FROM TRiLock t")
@NamedQueries({
	@NamedQuery(name = "TRiLock.findByResourceIdAndResourceType", query = "  FROM TRiLock s WHERE s.resourceId = :resourceId and s.resourceType =:resourceType and  s.unlockTime is null "),
	})
public class TRiLock extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name="T_RI_LOCK_LOCKID_GENERATOR", sequenceName="U_SID")
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_RI_LOCK_LOCKID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name="LOCK_ID", unique=true, nullable=false, precision=10)
	private long lockId;

	@Column(name="RESOURCE_ID", precision=10)
	private Long resourceId;

	@Column(name="RESOURCE_TYPE", precision=22)
	private Long resourceType;

//	@Column(name="INSERT_BY", precision=10)
//	private BigDecimal insertBy;
//
//	@Temporal(TemporalType.DATE)
//	@Column(name="INSERT_TIME")
//	private Date insertTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LOCK_TIME")
	private Date lockTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UNLOCK_TIME")
	private Date unlockTime;

	public Date getUnlockTime() {
		return unlockTime;
	}

	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}

	@Column(name="LOCKUSER_ID", precision=10)
	private Long lockUserId;

//	@Column(name="UPDATE_BY", precision=10)
//	private BigDecimal updateBy;
//
//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_TIME")
//	private Date updateTime;

	public TRiLock() {
	}

	public long getLockId() {
		return this.lockId;
	}

	public void setLockId(long lockId) {
		this.lockId = lockId;
	}

	

//	public BigDecimal getInsertBy() {
//		return this.insertBy;
//	}
//
//	public void setInsertBy(BigDecimal insertBy) {
//		this.insertBy = insertBy;
//	}
//
//	public Date getInsertTime() {
//		return this.insertTime;
//	}
//
//	public void setInsertTime(Date insertTime) {
//		this.insertTime = insertTime;
//	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getResourceType() {
		return resourceType;
	}

	public void setResourceType(Long resourceType) {
		this.resourceType = resourceType;
	}

	public Date getLockTime() {
		return this.lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}



//	public BigDecimal getUpdateBy() {
//		return this.updateBy;
//	}
//
//	public void setUpdateBy(BigDecimal updateBy) {
//		this.updateBy = updateBy;
//	}
//
//	public Date getUpdateTime() {
//		return this.updateTime;
//	}
//
//	public void setUpdateTime(Date updateTime) {
//		this.updateTime = updateTime;
//	}

	public Long getLockUserId() {
		return lockUserId;
	}

	public void setLockUserId(Long lockUserId) {
		this.lockUserId = lockUserId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Long getPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPrimaryKey(Long arg0) {
		// TODO Auto-generated method stub
		
	}

}