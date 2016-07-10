package com.ebao.ap99.partner.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ebao.ap99.parent.BaseEntityImpl;


/**
 * The persistent class for the T_AML_CHECK database table.
 * 
 */
@Entity
@Table(name="T_RI_BP_RETRO_AML_CHECK")
@NamedQuery(name="TRetroAmlCheck.findAll", query="SELECT t FROM TRetroAmlCheck t")
public class TRetroAmlCheck extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="AML_CHECK_ID")
	private long amlCheckId;

	@Column(name="AMBEST")
	private String ambest;

	@Column(name="AMBEST_TEXT")
	private String ambestText;

	@Column(name="AML_CHECK")
	private String amlCheck;

	@Column(name="AML_CHECK_RESULT")
	private String amlCheckResult;

	@Column(name="APPROVED_BY")
	private long approvedBy;

	@Column(name="CHECK_BY")
	private long checkBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CHECK_DATE")
	private Date checkDate;
	
    @Column(name="COMMENTS")
	private String comments;

	@Column(name="COMPANY_NAME")
	private String companyName;

	@Column(name="COUNTRY_INCORPORATION")
	private String countryIncorporation;

	@Column(name="FITCH")
	private String fitch;

	@Column(name="FITCH_TEXT")
	private String fitchText;

	 @Column(name="LICENSED")
	private String licensed;

	 @Column(name="MOODYS")
	private String moodys;

	@Column(name="MOODYS_TEXT")
	private String moodysText;

	@Column(name="OTHERS")
	private String others;
	    
	@Column(name="OTHERSTEXT")
	private String OthersText;
	
    
    @Column(name="SANCTIONEDCOUNTRY")
    private String sanctionedCountry;
    
    @ManyToOne
    @JoinColumn(name="PARTNER_ID")
    private TPartner TPartner;

    @Column(name="REMARKS")
	private String remarks;
	

	
	@Column(name="SP")
	private String sp;

	@Column(name="SP_TEXT")
	private String spText;

	@Column(name="CHECK_YEAR")
    private String checkYear;
    
	public TRetroAmlCheck() {
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



    public String getCheckYear() {
        return checkYear;
    }

    public void setCheckYear(String checkYear) {
        this.checkYear = checkYear;
    }

    
    
    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getOthersText() {
        return OthersText;
    }

    public void setOthersText(String othersText) {
        OthersText = othersText;
    }

    
    public String getSanctionedCountry() {
        return sanctionedCountry;
    }

    public void setSanctionedCountry(String sanctionedCountry) {
        this.sanctionedCountry = sanctionedCountry;
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