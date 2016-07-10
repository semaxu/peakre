package com.ebao.ap99.partner.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.ebao.ap99.parent.BaseEntityImpl;

import java.util.Date;


/**
 * The persistent class for the T_BROKER_AML_CHECK database table.
 * 
 */
@Entity
@Table(name="T_RI_BP_BROKER_AML_CHECK")
@NamedQuery(name="TBrokerAmlCheck.findAll", query="SELECT t FROM TBrokerAmlCheck t")
public class TBrokerAmlCheck extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="AML_CHECK_ID")
	private long amlCheckId;

	@Column(name="AML_CHECK")
	private String amlCheck;

	@Column(name="AML_CHECK_RESULT")
	private String amlCheckResult;

	@Column(name="APPROVED_BY")
	private long approvedBy;

	@Column(name="BROKER_ASSOCIATION")
	private String brokerAssociation;

	@Column(name="CERTIFICATE_INCORPORATION")
	private String certificateIncorporation;

	@Column(name="CHECK_BY")
	private long checkBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CHECK_DATE")
	private Date checkDate;

	@Column(name="CHECK_REMARKS")
	private String checkRemarks;
	
	@Column(name="COMMENTS")
	private String comments;

	@Column(name="COMPANY_NAME")
	private String companyName;

	@Column(name="COUNTRY_INCORPORATION")
	private String countryIncorporation;

	@Column(name="INSURANCE_BODY")
	private String insuranceBody;

	@Column(name="IS_BROKER_SANCTIONED")
	private String isBrokerSanctioned;

	@Column(name="IS_MANAGEMENT_TERRORIST")
	private String isManagementTerrorist;
	
	@Column(name="IS_DIRECTOR_TERRORIST")
	private String isDirectorTerrorist;
	
	@Column(name="IS_OWNER_TERRORIST")
	private String isOwnerTerrorist;

	@Column(name="LLOYD_BROKER")
	private String lloydBroker;

	@Column(name="NAMES_DIRECTORS")
	private String namesDirectors;

	@Column(name="NAMES_OWNERS")
	private String namesOwners;

	@Column(name="NAMES_SENIOR_MANAGEMENTS")
	private String namesSeniorManagements;

	@Column(name="OWNERSHIP_STRUCTURE")
	private String ownershipStructure;

    @ManyToOne
    @JoinColumn(name="PARTNER_ID")
    private TPartner TPartner;

    @Column(name="REMARKS")
	private String remarks;

	@Column(name="SANCTIONED_COUNTRY")
	private String sanctionedCountry;

    @Column(name="CHECK_YEAR")
    private String checkYear;

	public TBrokerAmlCheck() {
	}

	public long getAmlCheckId() {
		return this.amlCheckId;
	}

	public void setAmlCheckId(long amlCheckId) {
		this.amlCheckId = amlCheckId;
	}

	public String getAmlCheck() {
		return this.amlCheck;
	}

	public void setAmlCheck(String amlCheck) {
		this.amlCheck = amlCheck;
	}

	public String getAmlCheckResult() {
		return this.amlCheckResult;
	}

	public void setAmlCheckResult(String amlCheckResult) {
		this.amlCheckResult = amlCheckResult;
	}

	public long getApprovedBy() {
		return this.approvedBy;
	}

	public void setApprovedBy(long approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getBrokerAssociation() {
		return this.brokerAssociation;
	}

	public void setBrokerAssociation(String brokerAssociation) {
		this.brokerAssociation = brokerAssociation;
	}

	public String getCertificateIncorporation() {
		return this.certificateIncorporation;
	}

	public void setCertificateIncorporation(String certificateIncorporation) {
		this.certificateIncorporation = certificateIncorporation;
	}

	public long getCheckBy() {
		return this.checkBy;
	}

	public void setCheckBy(long checkBy) {
		this.checkBy = checkBy;
	}

	public Date getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckRemarks() {
		return this.checkRemarks;
	}

	public void setCheckRemarks(String checkRemarks) {
		this.checkRemarks = checkRemarks;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCountryIncorporation() {
		return this.countryIncorporation;
	}

	public void setCountryIncorporation(String countryIncorporation) {
		this.countryIncorporation = countryIncorporation;
	}



	public String getInsuranceBody() {
		return this.insuranceBody;
	}

	public void setInsuranceBody(String insuranceBody) {
		this.insuranceBody = insuranceBody;
	}

	public String getIsBrokerSanctioned() {
		return this.isBrokerSanctioned;
	}

	public void setIsBrokerSanctioned(String isBrokerSanctioned) {
		this.isBrokerSanctioned = isBrokerSanctioned;
	}

	public String getIsManagementTerrorist() {
		return this.isManagementTerrorist;
	}

	public void setIsManagementTerrorist(String isManagementTerrorist) {
		this.isManagementTerrorist = isManagementTerrorist;
	}

	public String getIsOwnerTerrorist() {
		return this.isOwnerTerrorist;
	}

	public void setIsOwnerTerrorist(String isOwnerTerrorist) {
		this.isOwnerTerrorist = isOwnerTerrorist;
	}

	public String getLloydBroker() {
		return this.lloydBroker;
	}

	public void setLloydBroker(String lloydBroker) {
		this.lloydBroker = lloydBroker;
	}

	public String getNamesDirectors() {
		return this.namesDirectors;
	}

	public void setNamesDirectors(String namesDirectors) {
		this.namesDirectors = namesDirectors;
	}

	public String getNamesOwners() {
		return this.namesOwners;
	}

	public void setNamesOwners(String namesOwners) {
		this.namesOwners = namesOwners;
	}

	public String getNamesSeniorManagements() {
		return this.namesSeniorManagements;
	}

	public void setNamesSeniorManagements(String namesSeniorManagements) {
		this.namesSeniorManagements = namesSeniorManagements;
	}

	public String getOwnershipStructure() {
		return this.ownershipStructure;
	}

	public void setOwnershipStructure(String ownershipStructure) {
		this.ownershipStructure = ownershipStructure;
	}



	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSanctionedCountry() {
		return this.sanctionedCountry;
	}

	public void setSanctionedCountry(String sanctionedCountry) {
		this.sanctionedCountry = sanctionedCountry;
	}



    public String getIsDirectorTerrorist() {
        return isDirectorTerrorist;
    }

    public void setIsDirectorTerrorist(String isDirectorTerrorist) {
        this.isDirectorTerrorist = isDirectorTerrorist;
    }

    public String getCheckYear() {
        return checkYear;
    }

    public void setCheckYear(String checkYear) {
        this.checkYear = checkYear;
    }

    public TPartner getTPartner() {
        return TPartner;
    }

    public void setTPartner(TPartner tPartner) {
        TPartner = tPartner;
    }

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