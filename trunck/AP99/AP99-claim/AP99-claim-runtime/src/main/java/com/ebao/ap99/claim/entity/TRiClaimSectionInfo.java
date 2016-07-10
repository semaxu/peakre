package com.ebao.ap99.claim.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the T_RI_CLAIM_SECTION_INFO database table.
 * 
 */
@Entity
@Table(name = "T_RI_CLAIM_SECTION_INFO")
@NamedQuery(name = "TRiClaimSectionInfo.findAll", query = "SELECT t FROM TRiClaimSectionInfo t")
@NamedQueries({
		@NamedQuery(name = "TRiClaimSectionInfo.findByRefId", query = " FROM TRiClaimSectionInfo s WHERE s.refId = :refId and businessDirection = :businessDirection"),
		@NamedQuery(name = "TRiClaimSectionInfo.findSectionIdByRefId", query = "select s.sectionId FROM TRiClaimSectionInfo s WHERE s.refId = :refId and  s.businessDirection = :businessDirection"),
		@NamedQuery(name = "TRiClaimSectionInfo.findSectionIdByRefIdBusinDirec", query = "select distinct s.sectionId FROM TRiClaimSectionInfo s WHERE s.refId = :refId and  s.businessDirection = :businessDirection"),
		@NamedQuery(name = "TRiClaimSectionInfo.findByRefIdAndSectionId", query = " FROM TRiClaimSectionInfo s WHERE s.refId = :refId and s.sectionId = :sectionId and s.businessDirection = :businessDirection"),
		@NamedQuery(name = "TRiClaimSectionInfo.findByRefSectionId", query = " FROM TRiClaimSectionInfo s WHERE s.sectionId = :sectionId "),
		@NamedQuery(name = "TRiClaimSectionInfo.findByRefFronting", query = "select s.sectionId FROM TRiClaimSectionInfo s WHERE s.refId = :refId and s.businessDirection = :businessDirection and s.frontingFlag = :frontingFlag "),
		@NamedQuery(name = "TRiClaimSectionInfo.findrefIdbySectionId", query = "select distinct s.refId from TRiClaimSectionInfo s where s.sectionId =:sectionId")

})
public class TRiClaimSectionInfo extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @SequenceGenerator(name="T_RI_CLAIM_SECTION_INFO_CLAIMSECTIONID_GENERATOR",
	// sequenceName="U_SID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "CLAIM_SECTION_ID", unique = true, nullable = false, precision = 10)
	private Long claimSectionId;

	@Column(name = "BROKER_NAME", length = 200)
	private String brokerName;

	@Column(name = "CEDENT_COUNTRY", length = 5)
	private String cedentCountry;

	@Column(name = "CEDENT_NAME", length = 200)
	private String cedentName;

	@Column(name = "CONTRACT_CODE", length = 20)
	private String contractCode;

	@Column(name = "CONTRACT_ID", precision = 10)
	private BigDecimal contractId;

	@Column(name = "CONTRACT_NATURE", length = 2)
	private String contractNature;

	@Column(name = "FRONTING_FLAG", length = 2)
	private String frontingFlag;

	@Column(name = "FULL_NAME", length = 400)
	private String fullName;

	// @Column(name="INSERT_BY", precision=10)
	// private BigDecimal insertBy;
	//
	// @Temporal(TemporalType.DATE)
	// @Column(name="INSERT_TIME")
	// private Date insertTime;

	@Column(name = "RETROCESSION_FLAG", length = 2)
	private String retrocessionFlag;

	@Column(name = "SECTION_CODE", length = 20)
	private String sectionCode;

	@Column(name = "SECTION_ID", nullable = false, precision = 10)
	private Long sectionId;

	@Column(name = "SECTION_NAME", length = 100)
	private String sectionName;
	
	public String getSectionLevel() {
		return sectionLevel;
	}

	public void setSectionLevel(String sectionLevel) {
		this.sectionLevel = sectionLevel;
	}

	@Column(name = "SECTION_LEVEL", length = 2)
	private String sectionLevel;
	
	@Column(name = "BUSINESS_DIRECTION", length = 2)
	private String businessDirection;

	// @Column(name="UPDATE_BY", precision=10)
	// private BigDecimal updateBy;
	//
	// @Temporal(TemporalType.DATE)
	// @Column(name="UPDATE_TIME")
	// private Date updateTime;

	public String getBusinessDirection() {
		return businessDirection;
	}

	public void setBusinessDirection(String businessDirection) {
		this.businessDirection = businessDirection;
	}

	@Column(name = "UW_YEAR", precision = 10)
	private BigDecimal uwYear;

	@Column(name = "REF_TYPE")
	private String refType;

	@Column(name = "REF_ID")
	private Long refId;
	
	// bi-directional many-to-one association to TRiClaimInfo
//	@ManyToOne
//	// @JoinColumn(name="CLAIM_ID", nullable=false)
//	@JoinColumn(name = "REF_ID", referencedColumnName = "CLAIM_ID")
//	private TRiClaimInfo TRiClaimInfo;

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public TRiClaimSectionInfo() {
	}

	public Long getClaimSectionId() {
		return this.claimSectionId;
	}

	public void setClaimSectionId(Long claimSectionId) {
		this.claimSectionId = claimSectionId;
	}

	public String getBrokerName() {
		return this.brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public String getCedentCountry() {
		return this.cedentCountry;
	}

	public void setCedentCountry(String cedentCountry) {
		this.cedentCountry = cedentCountry;
	}

	public String getCedentName() {
		return this.cedentName;
	}

	public void setCedentName(String cedentName) {
		this.cedentName = cedentName;
	}

	public String getContractCode() {
		return this.contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public BigDecimal getContractId() {
		return this.contractId;
	}

	public void setContractId(BigDecimal contractId) {
		this.contractId = contractId;
	}

	public String getContractNature() {
		return this.contractNature;
	}

	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}

	public String getFrontingFlag() {
		return this.frontingFlag;
	}

	public void setFrontingFlag(String frontingFlag) {
		this.frontingFlag = frontingFlag;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	// public BigDecimal getInsertBy() {
	// return this.insertBy;
	// }
	//
	// public void setInsertBy(BigDecimal insertBy) {
	// this.insertBy = insertBy;
	// }
	//
	// public Date getInsertTime() {
	// return this.insertTime;
	// }
	//
	// public void setInsertTime(Date insertTime) {
	// this.insertTime = insertTime;
	// }

	public String getRetrocessionFlag() {
		return this.retrocessionFlag;
	}

	public void setRetrocessionFlag(String retrocessionFlag) {
		this.retrocessionFlag = retrocessionFlag;
	}

	public String getSectionCode() {
		return this.sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	public Long getSectionId() {
		return this.sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionName() {
		return this.sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	// public BigDecimal getUpdateBy() {
	// return this.updateBy;
	// }
	//
	// public void setUpdateBy(BigDecimal updateBy) {
	// this.updateBy = updateBy;
	// }
	//
	// public Date getUpdateTime() {
	// return this.updateTime;
	// }
	//
	// public void setUpdateTime(Date updateTime) {
	// this.updateTime = updateTime;
	// }

	public BigDecimal getUwYear() {
		return this.uwYear;
	}

	public void setUwYear(BigDecimal uwYear) {
		this.uwYear = uwYear;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

//	public TRiClaimInfo getTRiClaimInfo() {
//		return this.TRiClaimInfo;
//	}
//
//	public void setTRiClaimInfo(TRiClaimInfo TRiClaimInfo) {
//		this.TRiClaimInfo = TRiClaimInfo;
//	}

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