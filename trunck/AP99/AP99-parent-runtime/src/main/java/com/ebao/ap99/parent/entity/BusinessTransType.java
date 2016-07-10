package com.ebao.ap99.parent.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the T_RI_TRANS_TYPE database table.
 * 
 */
@Entity
@Table(name="T_RI_TRANS_TYPE")
@NamedQuery(name="BusinessTransType.findAll", query="SELECT b FROM BusinessTransType b")
public class BusinessTransType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TRANS_TYPE_ID")
	private Integer transTypeId;

	@Column(name="\"MODULE\"")
	private String module;

	@Column(name="TRANS_TYPE_NAME")
	private String transTypeName;

	public BusinessTransType() {
	}
	
	public Integer getTransTypeId() {
		return transTypeId;
	}

	public void setTransTypeId(Integer transTypeId) {
		this.transTypeId = transTypeId;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getTransTypeName() {
		return this.transTypeName;
	}

	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}

}