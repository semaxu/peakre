package com.ebao.ap99.partner.model;

import java.io.Serializable;

import java.util.Date;


public class MgaAmlCheck  implements Serializable {
    private static final long serialVersionUID = 1L;

    private long amlCheckId;

    private String amlCheck;

    private String amlCheckResult;

    private long approvedBy;

    private String brokerAssociation;

    private String certificateIncorporation;

    private long checkBy;

    private Date checkDate;

    private String checkRemarks;
    
    private String comments;

    private String companyName;

    private String countryIncorporation;

    private String insuranceBody;

    private String isBrokerSanctioned;

    private String isManagementTerrorist;

    private String isDirectorTerrorist;
    
    private String isOwnerTerrorist;

    private String lloydBroker;

    private String namesDirectors;

    private String namesOwners;

    private String namesSeniorManagements;

    private String ownershipStructure;

    private String partnerId;

    private String remarks;

    private String sanctionedCountry;
    
    private Long insertBy;
    
    private Date insertTime;
    
    private String checkYear;

    public MgaAmlCheck() {
    }


    public long getAmlCheckId() {
        return amlCheckId;
    }


    public void setAmlCheckId(long amlCheckId) {
        this.amlCheckId = amlCheckId;
    }


    public String getAmlCheck() {
        return amlCheck;
    }


    public void setAmlCheck(String amlCheck) {
        this.amlCheck = amlCheck;
    }


    public String getAmlCheckResult() {
        return amlCheckResult;
    }


    public void setAmlCheckResult(String amlCheckResult) {
        this.amlCheckResult = amlCheckResult;
    }


    public long getApprovedBy() {
        return approvedBy;
    }


    public void setApprovedBy(long approvedBy) {
        this.approvedBy = approvedBy;
    }


    public String getBrokerAssociation() {
        return brokerAssociation;
    }


    public void setBrokerAssociation(String brokerAssociation) {
        this.brokerAssociation = brokerAssociation;
    }


    public String getCertificateIncorporation() {
        return certificateIncorporation;
    }


    public void setCertificateIncorporation(String certificateIncorporation) {
        this.certificateIncorporation = certificateIncorporation;
    }


    public long getCheckBy() {
        return checkBy;
    }


    public void setCheckBy(long checkBy) {
        this.checkBy = checkBy;
    }


    public Date getCheckDate() {
        return checkDate;
    }


    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }


    public String getCheckRemarks() {
        return checkRemarks;
    }


    public void setCheckRemarks(String checkRemarks) {
        this.checkRemarks = checkRemarks;
    }


    public String getComments() {
        return comments;
    }


    public void setComments(String comments) {
        this.comments = comments;
    }


    public String getCompanyName() {
        return companyName;
    }


    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public String getCountryIncorporation() {
        return countryIncorporation;
    }


    public void setCountryIncorporation(String countryIncorporation) {
        this.countryIncorporation = countryIncorporation;
    }


    public String getInsuranceBody() {
        return insuranceBody;
    }


    public void setInsuranceBody(String insuranceBody) {
        this.insuranceBody = insuranceBody;
    }


    public String getIsBrokerSanctioned() {
        return isBrokerSanctioned;
    }


    public void setIsBrokerSanctioned(String isBrokerSanctioned) {
        this.isBrokerSanctioned = isBrokerSanctioned;
    }


    public String getIsManagementTerrorist() {
        return isManagementTerrorist;
    }


    public void setIsManagementTerrorist(String isManagementTerrorist) {
        this.isManagementTerrorist = isManagementTerrorist;
    }


    public String getIsOwnerTerrorist() {
        return isOwnerTerrorist;
    }


    public void setIsOwnerTerrorist(String isOwnerTerrorist) {
        this.isOwnerTerrorist = isOwnerTerrorist;
    }


    public String getLloydBroker() {
        return lloydBroker;
    }


    public void setLloydBroker(String lloydBroker) {
        this.lloydBroker = lloydBroker;
    }


    public String getNamesDirectors() {
        return namesDirectors;
    }


    public void setNamesDirectors(String namesDirectors) {
        this.namesDirectors = namesDirectors;
    }


    public String getNamesOwners() {
        return namesOwners;
    }


    public void setNamesOwners(String namesOwners) {
        this.namesOwners = namesOwners;
    }


    public String getNamesSeniorManagements() {
        return namesSeniorManagements;
    }


    public void setNamesSeniorManagements(String namesSeniorManagements) {
        this.namesSeniorManagements = namesSeniorManagements;
    }


    public String getOwnershipStructure() {
        return ownershipStructure;
    }


    public void setOwnershipStructure(String ownershipStructure) {
        this.ownershipStructure = ownershipStructure;
    }


    public String getPartnerId() {
        return partnerId;
    }


    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }


    public String getRemarks() {
        return remarks;
    }


    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public String getSanctionedCountry() {
        return sanctionedCountry;
    }


    public void setSanctionedCountry(String sanctionedCountry) {
        this.sanctionedCountry = sanctionedCountry;
    }


    public Long getInsertBy() {
        return insertBy;
    }


    public void setInsertBy(Long insertBy) {
        this.insertBy = insertBy;
    }


    public Date getInsertTime() {
        return insertTime;
    }


    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }


    public String getCheckYear() {
        return checkYear;
    }


    public void setCheckYear(String checkYear) {
        this.checkYear = checkYear;
    }


    public String getIsDirectorTerrorist() {
        return isDirectorTerrorist;
    }


    public void setIsDirectorTerrorist(String isDirectorTerrorist) {
        this.isDirectorTerrorist = isDirectorTerrorist;
    }



}