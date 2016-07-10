package com.ebao.ap99.partner.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;




@Entity
@Table(name="T_RI_BP_PARTNER")
@NamedQueries({
	@NamedQuery(name="TPartner.findAll", query="SELECT t FROM TPartner t"),
	@NamedQuery(name="TPartner.findByBusinessPartnerId", query="SELECT t FROM TPartner t WHERE t.businessPartnerId = :businessPartnerId")
})

public class TPartner extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="PARTNER_ID")
	private long partnerId;

	@Column (name="BUSINESS_PARTNER_CATEGORY")
	private String businessPartnerCategory;
	
	@Column(name="TRADING_NAME")
	private String tradingName;

	@Column(name="BUSINESS_PARTNER_ID")
	private String businessPartnerId;

    @Column (name="STATUS")
    private String status;
    
    @Column (name="CREATOR")
    private long creator;

    @Column (name="CREATE_DATE")
    private Date createDate;
    
    @Column (name="REGISTRATION_NAME")
    private String registrationName ;
    
    @Column (name="SHORT_NAME")
    private String shortName;
    
    @Column (name="ORGANIZATION_ID_TYPE")
    private String organizationIdType;
    
    @Column (name="BUSINESS_REGISTRATION_CODE")
    private String businessRegistrationCode;
    
    @Column (name="DATE_OF_REGISTRATION")
    private Date dateOfRegistration;
    
    @Column (name="DUEDATE_OF_REGISTRATION")
    private Date duedateOfRegistration;
    
    @Column (name="FIRST_NAME")
    private String firstName;
    
    @Column (name="LAST_NAME")
    private String lastName;
    
    @Column (name="ID_TYPE")
    private String idType;
    
    @Column (name="ID_NUMBER")
    private String idNumber;
    
    @Column (name="DUEDATE_OF_ID_CERTIFICATION")
    private Date duedateOfIdCertification;
    
    @Column (name="DATE_OF_BIRTH")
    private Date dateOfBirth;
    
    @Column (name="GENDER")
    private String gender;
    
    @Column (name="COUNTRY")
    private String country;
    
    @Column (name="CITY")
    private String city;
    
    @Column (name="DISCTRICT")
    private String disctrict;

    @Column (name="STREET")
    private String street;
    
    @Column (name="STREET_NUMBER")
    private String number;
    
    @Column (name="BUILDING")
    private String building;
    
    @Column (name="FLOOR")
    private String floor;
    
    @Column (name="ROOM")
    private String room;
    
    @Column (name="CMS_NO")
    private String cmsNo;
    
    @Column (name="PARTNER_TYPE")
    private String partnerType;
    
    @Column (name="ROLE_SELECT")
    private String roleSelect;
    
    @Column (name="FULL_NAME")
    private String fullName;
   
	@OneToMany(mappedBy = "TPartner", cascade = { CascadeType.ALL })
	private List<TContact> TContacts;

	@OneToMany(mappedBy = "TPartner", cascade = { CascadeType.ALL })
	private List<TBankAccount> TBankAccounts;
	
	@OneToMany(mappedBy = "TPartner", cascade = { CascadeType.ALL })
	private List<TAmlCheck> TAmlChecks;
	
    @OneToMany(mappedBy = "TPartner", cascade = { CascadeType.ALL })
    private List<TBrokerAmlCheck> TBrokerAmlChecks;	
    
    @OneToMany(mappedBy = "TPartner", cascade = { CascadeType.ALL })
    private List<TRetroAmlCheck> TRetroAmlChecks;
    
    @OneToMany(mappedBy = "TPartner", cascade = { CascadeType.ALL })
    private List<TMgaAmlCheck> TMgaAmlChecks; 
    
    @OneToMany(mappedBy = "TPartner", cascade = { CascadeType.ALL })
    private List<TRelationship> TRelationships;	
	

    
    
	public TPartner() {
	}



	public String getBusinessPartnerCategory() {
		return businessPartnerCategory;
	}



	public void setBusinessPartnerCategory(String businessPartnerCategory) {
		this.businessPartnerCategory = businessPartnerCategory;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public long getCreator() {
		return creator;
	}



	public void setCreator(long creator) {
		this.creator = creator;
	}



	public Date getCreateDate() {
		return createDate;
	}



	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}



	public String getRegistrationName() {
		return registrationName;
	}



	public void setRegistrationName(String registrationName) {
		this.registrationName = registrationName;
	}



	public String getShortName() {
		return shortName;
	}



	public void setShortName(String shortName) {
		this.shortName = shortName;
	}



	public String getOrganizationIdType() {
		return organizationIdType;
	}



	public void setOrganizationIdType(String organizationIdType) {
		this.organizationIdType = organizationIdType;
	}



	public String getBusinessRegistrationCode() {
		return businessRegistrationCode;
	}



	public void setBusinessRegistrationCode(String businessRegistrationCode) {
		this.businessRegistrationCode = businessRegistrationCode;
	}



	public Date getDateOfRegistration() {
		return dateOfRegistration;
	}



	public void setDateOfRegistration(Date dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}



	public Date getDuedateOfRegistration() {
		return duedateOfRegistration;
	}



	public void setDuedateOfRegistration(Date duedateOfRegistration) {
		this.duedateOfRegistration = duedateOfRegistration;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getIdType() {
		return idType;
	}



	public void setIdType(String idType) {
		this.idType = idType;
	}



	public String getIdNumber() {
		return idNumber;
	}



	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}



	public Date getDuedateOfIdCertification() {
		return duedateOfIdCertification;
	}



	public void setDuedateOfIdCertification(Date duedateOfIdCertification) {
		this.duedateOfIdCertification = duedateOfIdCertification;
	}



	public Date getDateOfBirth() {
		return dateOfBirth;
	}



	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getPartnerType() {
		return partnerType;
	}



	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}



	public long getPartnerId() {
		return partnerId;
	}



	public void setPartnerId(long partnerId) {
		this.partnerId = partnerId;
	}



	public String getTradingName() {
		return tradingName;
	}



	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}



	public String getBusinessPartnerId() {
		return businessPartnerId;
	}



	public void setBusinessPartnerId(String businessPartnerId) {
		this.businessPartnerId = businessPartnerId;
	}
	


	public String getRoleSelect() {
        return roleSelect;
    }



    public void setRoleSelect(String roleSelect) {
        this.roleSelect = roleSelect;
    }



    public List<TContact> getTContacts() {
		return TContacts;
	}



	public void setTContacts(List<TContact> tContacts) {
		TContacts = tContacts;
	}

	public TContact addTContact(TContact TContact) {
		if (getTContacts() == null) {
			setTContacts(new ArrayList<TContact>());
			getTContacts().add(TContact);
		} else {
			getTContacts().add(TContact);
		}
		TContact.setTPartner(this);

		return TContact;
	}

	public TContact removeTContact(TContact TContact) {
		getTContacts().remove(TContact);
		TContact.setTPartner(null);

		return TContact;
	}
	

	public List<TBankAccount> getTBankAccounts() {
		return TBankAccounts;
	}



	public void setTBankAccounts(List<TBankAccount> tBankAccounts) {
		TBankAccounts = tBankAccounts;
	}

	public TBankAccount addTBankAccount(TBankAccount TBankAccount) {
		if (getTBankAccounts() == null) {
			setTBankAccounts(new ArrayList<TBankAccount>());
			getTBankAccounts().add(TBankAccount);
		} else {
			getTBankAccounts().add(TBankAccount);
		}
		TBankAccount.setTPartner(this);

		return TBankAccount;
	}

	public TBankAccount removeTBankAccount(TBankAccount TBankAccount) {
		getTBankAccounts().remove(TBankAccount);
		TBankAccount.setTPartner(null);

		return TBankAccount;
	}
	




    public List<TAmlCheck> getTAmlChecks() {
        return TAmlChecks;
    }



    public void setTAmlChecks(List<TAmlCheck> tAmlChecks) {
        TAmlChecks = tAmlChecks;
    }



    public TAmlCheck addTAmlCheck(TAmlCheck TAmlCheck) {
		if (getTAmlChecks() == null) {
			setTAmlChecks(new ArrayList<TAmlCheck>());
			getTAmlChecks().add(TAmlCheck);
		} else {
			getTAmlChecks().add(TAmlCheck);
		}
		TAmlCheck.setTPartner(this);

		return TAmlCheck;
	}

	public TAmlCheck removeTAMLCheck(TAmlCheck TAmlCheck) {
		getTAmlChecks().remove(TAmlCheck);
		TAmlCheck.setTPartner(null);

		return TAmlCheck;
	}
	

	public List<TBrokerAmlCheck> getTBrokerAmlChecks() {
        return TBrokerAmlChecks;
    }



    public void setTBrokerAmlChecks(List<TBrokerAmlCheck> tBrokerAmlChecks) {
        TBrokerAmlChecks = tBrokerAmlChecks;
    }

    public TBrokerAmlCheck addTBrokerAmlCheck(TBrokerAmlCheck TBrokerAmlCheck) {
        if (getTBrokerAmlChecks() == null) {
            setTBrokerAmlChecks(new ArrayList<TBrokerAmlCheck>());
            getTBrokerAmlChecks().add(TBrokerAmlCheck);
        } else {
            getTBrokerAmlChecks().add(TBrokerAmlCheck);
        }
        TBrokerAmlCheck.setTPartner(this);

        return TBrokerAmlCheck;
    }

    public TBrokerAmlCheck removeTBrokerAMLCheck(TBrokerAmlCheck TBrokerAmlCheck) {
        getTBrokerAmlChecks().remove(TBrokerAmlCheck);
        TBrokerAmlCheck.setTPartner(null);

        return TBrokerAmlCheck;
    }

    public List<TRelationship> getTRelationships() {
        return TRelationships;
    }



    public void setTRelationships(List<TRelationship> tRelationships) {
        TRelationships = tRelationships;
    }

    
    public TRelationship addTRelationship(TRelationship TRelationship) {
        if (getTRelationships() == null) {
            setTRelationships(new ArrayList<TRelationship>());
            getTRelationships().add(TRelationship);
        } else {
            getTRelationships().add(TRelationship);
        }
        TRelationship.setTPartner(this);

        return TRelationship;
    }

    public TRelationship removeTRelationship(TRelationship TRelationship) {
        getTRelationships().remove(TRelationship);
        TRelationship.setTPartner(null);

        return TRelationship;
    }
    

    

    
    public List<TRetroAmlCheck> getTRetroAmlChecks() {
        return TRetroAmlChecks;
    }



    public void setTRetroAmlChecks(List<TRetroAmlCheck> tRetroAmlChecks) {
        TRetroAmlChecks = tRetroAmlChecks;
    }


    public TRetroAmlCheck addTRetroAmlCheck(TRetroAmlCheck TRetroAmlCheck) {
        if (getTRetroAmlChecks() == null) {
            setTRetroAmlChecks(new ArrayList<TRetroAmlCheck>());
            getTRetroAmlChecks().add(TRetroAmlCheck);
        } else {
            getTRetroAmlChecks().add(TRetroAmlCheck);
        }
        TRetroAmlCheck.setTPartner(this);

        return TRetroAmlCheck;
    }

    public TRetroAmlCheck removeTRetroAMLCheck(TRetroAmlCheck TRetroAmlCheck) {
        getTRetroAmlChecks().remove(TRetroAmlCheck);
        TRetroAmlCheck.setTPartner(null);

        return TRetroAmlCheck;
    }
    
    

    public List<TMgaAmlCheck> getTMgaAmlChecks() {
        return TMgaAmlChecks;
    }



    public void setTMgaAmlChecks(List<TMgaAmlCheck> tMgaAmlChecks) {
        TMgaAmlChecks = tMgaAmlChecks;
    }

    public TMgaAmlCheck addTMgaAmlCheck(TMgaAmlCheck TMgaAmlCheck) {
        if (getTMgaAmlChecks() == null) {
            setTMgaAmlChecks(new ArrayList<TMgaAmlCheck>());
            getTMgaAmlChecks().add(TMgaAmlCheck);
        } else {
            getTMgaAmlChecks().add(TMgaAmlCheck);
        }
        TMgaAmlCheck.setTPartner(this);

        return TMgaAmlCheck;
    }

    public TMgaAmlCheck removeTMgaAMLCheck(TMgaAmlCheck TMgaAmlCheck) {
        getTMgaAmlChecks().remove(TMgaAmlCheck);
        TMgaAmlCheck.setTPartner(null);

        return TMgaAmlCheck;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}



    public String getCity() {
        return city;
    }



    public void setCity(String city) {
        this.city = city;
    }



    public String getDisctrict() {
        return disctrict;
    }



    public void setDisctrict(String disctrict) {
        this.disctrict = disctrict;
    }



    public String getStreet() {
        return street;
    }



    public void setStreet(String street) {
        this.street = street;
    }



    public String getNumber() {
        return number;
    }



    public void setNumber(String number) {
        this.number = number;
    }



    public String getBuilding() {
        return building;
    }



    public void setBuilding(String building) {
        this.building = building;
    }



    public String getFloor() {
        return floor;
    }



    public void setFloor(String floor) {
        this.floor = floor;
    }



    public String getRoom() {
        return room;
    }



    public void setRoom(String room) {
        this.room = room;
    }



    public String getCmsNo() {
        return cmsNo;
    }



    public void setCmsNo(String cmsNo) {
        this.cmsNo = cmsNo;
    }
	
	
	
}