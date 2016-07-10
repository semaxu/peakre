package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_TERMINATION database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_TERMINATION")
@NamedQueries({
		@NamedQuery(name = "TRiContTermination.findAll", query = "SELECT t FROM TRiContTermination t where t.isActive != 'N'"),
		@NamedQuery(name = "TRiContTermination.findByTerminationId", query = " FROM TRiContTermination s WHERE s.terminationId = :terminationId AND s.isActive != 'N'"),
		@NamedQuery(name = "TRiContTermination.findByContCompId", query = " FROM TRiContTermination s WHERE s.contCompId = :contCompId AND s.isActive != 'N' ORDER BY s.terminationId DESC") })
public class TRiContTermination extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "TERMINATION_ID")
	private Long terminationId;

	@Temporal(TemporalType.DATE)
	@Column(name = "AGREED_DATE")
	private Date agreedDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "COMMUTTED_DATE")
	private Date commuttedDate;

	private String condition;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	private String reason;

	private String remarks;

	@Temporal(TemporalType.DATE)
	@Column(name = "TERMINATED_DATE")
	private Date terminatedDate;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	public TRiContTermination() {
		// default Y
		this.isActive = "Y";
	}

	public Long getTerminationId() {
		return this.terminationId;
	}

	public void setTerminationId(Long terminationId) {
		this.terminationId = terminationId;
	}

	public Date getAgreedDate() {
		return this.agreedDate;
	}

	public void setAgreedDate(Date agreedDate) {
		this.agreedDate = agreedDate;
	}

	public Date getCommuttedDate() {
		return this.commuttedDate;
	}

	public void setCommuttedDate(Date commuttedDate) {
		this.commuttedDate = commuttedDate;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getTerminatedDate() {
		return this.terminatedDate;
	}

	public void setTerminatedDate(Date terminatedDate) {
		this.terminatedDate = terminatedDate;
	}

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

	@Override
	public Long getPrimaryKey() {
		return terminationId;
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.terminationId = key;
	}
}