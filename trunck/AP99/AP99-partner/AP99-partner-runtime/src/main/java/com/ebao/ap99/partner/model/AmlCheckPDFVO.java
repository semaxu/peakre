package com.ebao.ap99.partner.model;

import java.io.Serializable;

import java.util.Date;




public class AmlCheckPDFVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private long amlCheckId;

    private String ambest;

    private String ambestText;

    private String amlCheck;

    private String amlCheckResult;

    private long approvedBy;

    private String checkBy;

    private Date checkDate;

    private String comments;

    private String companyName;

    private String countryIncorporation;

    private String fitch;

    private String fitchText;

    private String licensed;

    private String moodys;

    private String moodysText;

    private String remarks;

    private String sanctionedCountry;

    private String sp;

    private String spText;

    private Long insertBy;
    
    private Date insertTime;
    
    private String checkYear;
    
    private String partnerId;

    public AmlCheckPDFVO() {
    }

    public long getAmlCheckId() {
        return this.amlCheckId;
    }

    public void setAmlCheckId(long amlCheckId) {
        this.amlCheckId = amlCheckId;
    }

    public String getAmbest() {
        return this.ambest;
    }

    public void setAmbest(String ambest) {
        this.ambest = ambest;
    }

    public String getAmbestText() {
        return this.ambestText;
    }

    public void setAmbestText(String ambestText) {
        this.ambestText = ambestText;
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

    public String getCheckBy() {
        return this.checkBy;
    }

    public void setCheckBy(String checkBy) {
        this.checkBy = checkBy;
    }

    public Date getCheckDate() {
        return this.checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
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

    public String getFitch() {
        return this.fitch;
    }

    public void setFitch(String fitch) {
        this.fitch = fitch;
    }

    public String getFitchText() {
        return this.fitchText;
    }

    public void setFitchText(String fitchText) {
        this.fitchText = fitchText;
    }



    public String getLicensed() {
        return this.licensed;
    }

    public void setLicensed(String licensed) {
        this.licensed = licensed;
    }

    public String getMoodys() {
        return this.moodys;
    }

    public void setMoodys(String moodys) {
        this.moodys = moodys;
    }

    public String getMoodysText() {
        return this.moodysText;
    }

    public void setMoodysText(String moodysText) {
        this.moodysText = moodysText;
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

    public String getSp() {
        return this.sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public String getSpText() {
        return this.spText;
    }

    public void setSpText(String spText) {
        this.spText = spText;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
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


}