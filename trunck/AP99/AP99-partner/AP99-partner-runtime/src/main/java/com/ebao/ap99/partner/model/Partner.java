package com.ebao.ap99.partner.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



public class Partner implements Serializable {
	private static final long serialVersionUID = 1L;

	private long partnerId;
	private String businessPartnerCategory;
	private String tradingName;
	private String businessPartnerId;
    private String status;
    private long creator;
    private Date createDate;
    private String registrationName ;
    private String shortName;
    private String organizationIdType;
    private String businessRegistrationCode;
    private Date dateOfRegistration;
    private Date duedateOfRegistration;
    private String firstName;
    private String lastName;
    private String idType;
    private String idNumber;
    private Date duedateOfIdCertification;
    private Date dateOfBirth;
    private String gender;
    private String country;
    private String city;
    private String disctrict;
    private String street;
    private String number;
    private String building;
    private String floor;
    private String room;
    private String cmsNo;
    private String partnerType;
    private String roleSelect;
    private Long insertBy;
    private Date insertTime;
    private List <String> roleSelectIds;
	private List <Contact> contactTable;
	private List <BankAccount> bankAccountTable;
	private List <AmlCheck> amlCheckTable;
	private List <BrokerAmlCheck> brokerAmlCheckTable;
	private List <Relationship> relationshipTable;
	private List <RetroAmlCheck> retroAmlCheckTable;
	private List <MgaAmlCheck> mgaAmlCheckTable;
	private boolean pending;
    private Date updateTime;
    private String name;
    private int pageIndex;


    private int countPerPage;
    
    private String code;
    private String codeTableId;


	public int getPageIndex() {
		return pageIndex;
	}



	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}



	public int getCountPerPage() {
		return countPerPage;
	}



	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}



	public List<BankAccount> getBankAccountTable() {
		return bankAccountTable;
	}



	public void setBankAccountTable(List<BankAccount> bankAccountTable) {
		this.bankAccountTable = bankAccountTable;
	}





    public List<AmlCheck> getAmlCheckTable() {
        return amlCheckTable;
    }



    public void setAmlCheckTable(List<AmlCheck> amlCheckTable) {
        this.amlCheckTable = amlCheckTable;
    }



    public List<Contact> getContactTable() {
		return contactTable;
	}



	public void setContactTable(List<Contact> contactTable) {
		this.contactTable = contactTable;
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



	public long getPartnerId() {
		return partnerId;
	}



	public void setPartnerId(long partnerId) {
		this.partnerId = partnerId;
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



    public List <Relationship> getRelationshipTable() {
        return relationshipTable;
    }



    public void setRelationshipTable(List <Relationship> relationshipTable) {
        this.relationshipTable = relationshipTable;
    }



    public List<BrokerAmlCheck> getBrokerAmlCheckTable() {
        return brokerAmlCheckTable;
    }



    public void setBrokerAmlCheckTable(List<BrokerAmlCheck> brokerAmlCheckTable) {
        this.brokerAmlCheckTable = brokerAmlCheckTable;
    }



    public List<RetroAmlCheck> getRetroAmlCheckTable() {
        return retroAmlCheckTable;
    }



    public void setRetroAmlCheckTable(List<RetroAmlCheck> retroAmlCheckTable) {
        this.retroAmlCheckTable = retroAmlCheckTable;
    }



    public List<MgaAmlCheck> getMgaAmlCheckTable() {
        return mgaAmlCheckTable;
    }



    public void setMgaAmlCheckTable(List<MgaAmlCheck> mgaAmlCheckTable) {
        this.mgaAmlCheckTable = mgaAmlCheckTable;
    }



    public String getRoleSelect() {
        return roleSelect;
    }



    public void setRoleSelect(String roleSelect) {
        this.roleSelect = roleSelect;
    }



    public List<String> getRoleSelectIds() {
        return roleSelectIds;
    }



    public void setRoleSelectIds(List<String> roleSelectIds) {
        this.roleSelectIds = roleSelectIds;
    }



    public boolean isPending() {
        return pending;
    }



    public void setPending(boolean pending) {
        this.pending = pending;
    }



    public Date getUpdateTime() {
        return updateTime;
    }



    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }



    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public String getCodeTableId() {
		return codeTableId;
	}



	public void setCodeTableId(String codeTableId) {
		this.codeTableId = codeTableId;
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