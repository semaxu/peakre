package com.ebao.ap99.contract.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONTRACT_MORE_INFO database table.
 */
@Entity
@Table(name = "T_RI_CONTRACT_MORE_INFO")
@NamedQuery(name = "TRiContractMoreInfo.findAll", query = "SELECT t FROM TRiContractMoreInfo t WHERE t.isActive != 'N'")
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContractMoreInfo extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @SequenceGenerator(name="T_RI_CONTRACT_MORE_INFO_CONTCOMPID_GENERATOR" )
	// @GeneratedValue(strategy=GenerationType.SEQUENCE,
	// generator="T_RI_CONTRACT_MORE_INFO_CONTCOMPID_GENERATOR")
	@Column(name = "CONT_COMP_ID")
	@XmlTransient
	private Long contCompId;

	@Column(name = "LOB")
	private String loB;

	@Column(name = "COVERED_AREA")
	private String coveredArea;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "PROTECTION_BASIC")
	private String protectionBasic;

	@Column(name = "COB")
	private String coB;

	@Column(name = "UW_AREA")
	private String uwArea;

	private String covered;

	@Column(name = "COVERED_REMARK")
	private String coveredRemark;

	private String peril;

	// bi-directional one-to-one association to TRiContractInfo
	@OneToOne
	@JoinColumn(name = "CONT_COMP_ID")
	@XmlTransient
	private TRiContractInfo TRiContractInfo;

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

	public TRiContractMoreInfo() {
		// default Y
		this.isActive = "Y";
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getLoB() {
		return loB;
	}

	public void setLoB(String loB) {
		this.loB = loB;
	}

	public String getCoveredArea() {
		return this.coveredArea;
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

	public String getProtectionBasic() {
		return this.protectionBasic;
	}

	public void setProtectionBasic(String protectionBasic) {
		this.protectionBasic = protectionBasic;
	}

	public String getCoB() {
		return coB;
	}

	public void setCoB(String coB) {
		this.coB = coB;
	}

	public String getUwArea() {
		return this.uwArea;
	}

	public void setUwArea(String uwArea) {
		this.uwArea = uwArea;
	}

	public TRiContractInfo getTRiContractInfo() {
		return this.TRiContractInfo;
	}

	public void setTRiContractInfo(TRiContractInfo TRiContractInfo) {
		this.TRiContractInfo = TRiContractInfo;
	}

	public String getCovered() {
		return covered;
	}

	public void setCovered(String covered) {
		this.covered = covered;
	}

	public String getCoveredRemark() {
		return coveredRemark;
	}

	public void setCoveredRemark(String coveredRemark) {
		this.coveredRemark = coveredRemark;
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
