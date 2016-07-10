package com.ebao.ap99.contract.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_SECTION_INFO database table.
 */
@Entity
@Table(name = "T_RI_CONT_SECTION_INFO")
@NamedQueries({
		@NamedQuery(name = "TRiContSectionInfo.findAll", query = "SELECT t FROM TRiContSectionInfo t WHERE t.isActive != 'N'") })

@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContSectionInfo extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @SequenceGenerator(name="T_RI_CONT_SECTION_INFO_CONTCOMPID_GENERATOR" )
	// @GeneratedValue(strategy=GenerationType.SEQUENCE,
	// generator="T_RI_CONT_SECTION_INFO_CONTCOMPID_GENERATOR")
	@Column(name = "CONT_COMP_ID")
	@XmlTransient
	private Long contCompId;

	@Column(name = "UW_AREA")
	private String uwArea;

	@Column(name = "LOB")
	private String loB;

	@Column(name = "COVERED_AREA")
	private String coveredArea;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "COB")
	private String coB;

	@Column(name = "SECTION_NAME")
	private String sectionName;

	@Column(name = "SHARE_TYPE")
	private String shareType;

	private String covered;

	private String peril;

	// bi-directional one-to-one association to TRiContractStructure
	@OneToOne
	@JoinColumn(name = "CONT_COMP_ID")
	@XmlTransient
	private TRiContractStructure TRiContractStructure;

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

	public TRiContSectionInfo() {
		// default Y
		this.isActive = "Y";
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getUwArea() {
		return uwArea;
	}

	public void setUwArea(String uwArea) {
		this.uwArea = uwArea;
	}

	public String getLoB() {
		return loB;
	}

	public void setLoB(String loB) {
		this.loB = loB;
	}

	public String getCoveredArea() {
		return coveredArea;
	}

	public void setCoveredArea(String coveredArea) {
		this.coveredArea = coveredArea;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCoB() {
		return coB;
	}

	public void setCoB(String coB) {
		this.coB = coB;
	}

	public String getSectionName() {
		return this.sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getShareType() {
		return this.shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public TRiContractStructure getTRiContractStructure() {
		return this.TRiContractStructure;
	}

	public void setTRiContractStructure(TRiContractStructure TRiContractStructure) {
		this.TRiContractStructure = TRiContractStructure;
	}

	public String getCovered() {
		return covered;
	}

	public void setCovered(String covered) {
		this.covered = covered;
	}

	public String getPeril() {
		return peril;
	}

	public void setPeril(String peril) {
		this.peril = peril;
	}

	@Override
	public Long getPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPrimaryKey(Long paramLong) {
		// TODO Auto-generated method stub
	}
}
