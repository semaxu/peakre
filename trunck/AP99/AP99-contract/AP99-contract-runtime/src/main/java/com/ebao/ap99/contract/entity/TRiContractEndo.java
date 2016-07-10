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
 * The persistent class for the T_RI_CONTRACT_ENDO database table.
 */
@Entity
@Table(name = "T_RI_CONTRACT_ENDO")
@NamedQueries({
		@NamedQuery(name = "TRiContractEndo.findAll", query = "SELECT t FROM TRiContractEndo t where t.isActive != 'N'"),
		@NamedQuery(name = "TRiContractEndo.findByEndoId", query = " FROM TRiContractEndo s WHERE s.endoId = :endoId AND s.isActive != 'N'"),
		@NamedQuery(name = "TRiContractEndo.findByContCompId", query = " FROM TRiContractEndo s WHERE s.contCompId = :contCompId AND s.isActive != 'N' ORDER BY s.endoId") })
public class TRiContractEndo extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "ENDO_ID")
	private Long endoId;

	@Temporal(TemporalType.DATE)
	@Column(name = "AGREED_DATE")
	private Date agreedDate;

	@Column(name = "APPLICABLE_TO")
	private String applicableTo;

	@Column(name = "CHANGE_CONDITION")
	private String changeCondition;

	@Column(name = "CLIENT_ENDO_NO")
	private String clientEndoNo;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name = "EFF_DATE")
	private Date effDate;

	@Column(name = "ENDO_TYPE")
	private String endoType;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "\"SEQUENCE\"")
	private String sequence;

	public TRiContractEndo() {
		// default Y
		this.isActive = "Y";
	}

	public Long getEndoId() {
		return this.endoId;
	}

	public void setEndoId(Long endoId) {
		this.endoId = endoId;
	}

	public Date getAgreedDate() {
		return this.agreedDate;
	}

	public void setAgreedDate(Date agreedDate) {
		this.agreedDate = agreedDate;
	}

	public String getApplicableTo() {
		return this.applicableTo;
	}

	public void setApplicableTo(String applicableTo) {
		this.applicableTo = applicableTo;
	}

	public String getChangeCondition() {
		return this.changeCondition;
	}

	public void setChangeCondition(String changeCondition) {
		this.changeCondition = changeCondition;
	}

	public String getClientEndoNo() {
		return this.clientEndoNo;
	}

	public void setClientEndoNo(String clientEndoNo) {
		this.clientEndoNo = clientEndoNo;
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEffDate() {
		return this.effDate;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	public String getEndoType() {
		return this.endoType;
	}

	public void setEndoType(String endoType) {
		this.endoType = endoType;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		if (StringUtils.isNullOrEmpty(isActive)) {
			this.isActive = "Y";
		} else {
			this.isActive = isActive;
		}
	}

	public String getSequence() {
		return this.sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	@Override
	public Long getPrimaryKey() {
		return endoId;
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.endoId = key;
	}
}
