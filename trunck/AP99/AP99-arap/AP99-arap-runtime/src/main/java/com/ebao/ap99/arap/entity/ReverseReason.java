package com.ebao.ap99.arap.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the T_RI_BCP_REVERSE_REASON database table.
 * 
 */
@Entity
@Table(name="T_RI_BCP_REVERSE_REASON")
@NamedQuery(name="ReverseReason.findAll", query="SELECT r FROM ReverseReason r")
public class ReverseReason implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="REASON_ID")
	private Integer reasonId;

	@Column(name="REASON_NAME")
	private String reasonName;

	public ReverseReason() {
	}

	public Integer getReasonId() {
		return this.reasonId;
	}

	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}

	public String getReasonName() {
		return this.reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

}