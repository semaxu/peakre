package com.ebao.ap99.claim.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the T_SAMPLE_USER database table.
 * 
 */
@Entity
@Table(name="T_SAMPLE_USER")
//@NamedQuery(name="TSampleUser.findAll", query="SELECT t FROM TSampleUser t")
public class TSampleUser extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name="T_SAMPLE_USER_USERID_GENERATOR", sequenceName="S_UID")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="S_UID")
	@Column(name="USER_ID")
	private long userId;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="USER_TYPE")
	private java.math.BigDecimal userType;

	public TSampleUser() {
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public java.math.BigDecimal getUserType() {
		return this.userType;
	}

	public void setUserType(java.math.BigDecimal userType) {
		this.userType = userType;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getUserId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setUserId(key);
	}

}