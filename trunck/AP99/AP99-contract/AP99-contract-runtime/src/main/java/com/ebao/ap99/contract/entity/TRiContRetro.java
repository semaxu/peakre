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
 * The persistent class for the T_RI_CONT_RETRO database table.
 */
@Entity
@Table(name = "T_RI_CONT_RETRO")
@NamedQueries({
		@NamedQuery(name = "TRiContRetro.findAll", query = "SELECT t FROM TRiContRetro t WHERE t.isActive != 'N'"),
		@NamedQuery(name = "TRiContRetro.findByCompId", query = " FROM TRiContRetro s WHERE s.compId = :compId and s.isActive != 'N' ORDER BY s.sequence ASC "),
		@NamedQuery(name = "TRiContRetro.getRetroSectionIds", query = "SELECT s.retroCompId FROM TRiContRetro s WHERE s.compId = :compId and s.isActive != 'N'"),
		@NamedQuery(name = "TRiContRetro.getAssumeSectionIds", query = "SELECT s.compId FROM TRiContRetro s WHERE s.retroCompId = :compId and s.isActive != 'N'"),
		@NamedQuery(name = "TRiContRetro.findByRetroCompId", query = " FROM TRiContRetro s WHERE s.retroCompId = :retroCompId and s.isActive != 'N'") })
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContRetro extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "RETRO_ID")
	@XmlTransient
	private Long retroId;

	@Column(name = "BRIEF_TEXT")
	private String briefText;

	@Column(name = "CONTRACT_NATURE")
	private String contractNature;

	@Column(name = "LIMIT_TYPE")
	private String limitType;

	@Column(name = "RETRO_SECTION_NAME")
	private String retroSectionName;

	@Column(name = "COMP_ID")
	@XmlTransient
	private Long compId;

	@Column(name = "RETRO_COMP_ID")
	@XmlTransient
	private Long retroCompId;

	@Column(name = "\"SEQUENCE\"")
	private Long sequence;

	@Column(name = "IS_ACTIVE")
	@XmlTransient
	private String isActive;

	@Column(name = "RETRO_CONTRACT_CODE")
	private String retroContractCode;

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

	public TRiContRetro() {
		// default Y
		this.isActive = "Y";
	}

	public Long getRetroId() {
		return this.retroId;
	}

	public void setRetroId(Long retroId) {
		this.retroId = retroId;
	}

	public Long getCompId() {
		return this.compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

	public Long getRetroCompId() {
		return this.retroCompId;
	}

	public void setRetroCompId(Long retroCompId) {
		this.retroCompId = retroCompId;
	}

	public Long getSequence() {
		return this.sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getBriefText() {
		return briefText;
	}

	public void setBriefText(String briefText) {
		this.briefText = briefText;
	}

	public String getContractNature() {
		return contractNature;
	}

	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}

	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public String getRetroSectionName() {
		return retroSectionName;
	}

	public void setRetroSectionName(String retroSectionName) {
		this.retroSectionName = retroSectionName;
	}

	public String getRetroContractCode() {
		return retroContractCode;
	}

	public void setRetroContractCode(String retroContractCode) {
		this.retroContractCode = retroContractCode;
	}

	@Override
	public Long getPrimaryKey() {
		return retroId;
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.retroId = key;
	}
}
