package com.ebao.ap99.arap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;


/**
 * The persistent class for the T_RI_BCP_TRANS database table.
 * 
 */
@Entity
@Table(name="T_RI_BCP_TRANS")
@NamedQuery(name="BCPTrans.findAll", query="SELECT b FROM BCPTrans b")
public class BCPTrans extends BaseEntityImpl{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TRANS_ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	private Long transId;

	public BCPTrans() {
	}

	public long getTransId() {
		return this.transId;
	}

	public void setTransId(long transId) {
		this.transId = transId;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getTransId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setTransId(transId);		
	}

}