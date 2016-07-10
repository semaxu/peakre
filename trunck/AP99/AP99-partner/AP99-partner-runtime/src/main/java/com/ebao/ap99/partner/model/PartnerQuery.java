package com.ebao.ap99.partner.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



public class PartnerQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	private String partnerId;
	private String businessPartnerCategory;
	private String tradingName;
	private String businessPartnerId;
    private String status;
    private String creator;
    private String createDate;
    private String registrationName ;
    private String shortName;
    private String organizationIdType;
    private String businessRegistrationCode;
    private String dateOfRegistration;
    private String duedateOfRegistration;
    private String firstName;
    private String lastName;
    private String idType;
    private String idNumber;
    private String duedateOfIdCertification;
    private String dateOfBirth;
    private String gender;
    private String country;
    private String partnerType;
    private String roleId;
    private String roleSelect;
    private Date updateTime;
    private String name;
    private boolean pending;
    private List <String> roleSelectIds;

	
	
    public String getRoleSelect() {
        return roleSelect;
    }



    public void setRoleSelect(String roleSelect) {
        this.roleSelect = roleSelect;
    }



    public String getRoleId() {
        return roleId;
    }



    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    private int pageIndex;

    private int countPerPage;


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



	public String getPartnerId() {
		return partnerId;
	}



	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}



	public String getCreator() {
		return creator;
	}



	public void setCreator(String creator) {
		this.creator = creator;
	}



	public String getCreateDate() {
		return createDate;
	}



	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}



	public String getDateOfRegistration() {
		return dateOfRegistration;
	}



	public void setDateOfRegistration(String dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}



	public String getDuedateOfRegistration() {
		return duedateOfRegistration;
	}



	public void setDuedateOfRegistration(String duedateOfRegistration) {
		this.duedateOfRegistration = duedateOfRegistration;
	}



	public String getDuedateOfIdCertification() {
		return duedateOfIdCertification;
	}



	public void setDuedateOfIdCertification(String duedateOfIdCertification) {
		this.duedateOfIdCertification = duedateOfIdCertification;
	}



	public String getDateOfBirth() {
		return dateOfBirth;
	}



	public void setDateOfBirth(String dateOfBirth) {
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



    public List<String> getRoleSelectIds() {
        return roleSelectIds;
    }



    public void setRoleSelectIds(List<String> roleSelectIds) {
        this.roleSelectIds = roleSelectIds;
    }



    public Date getUpdateTime() {
        return updateTime;
    }



    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }



    public boolean getPending() {
        return pending;
    }



    public void setPending(boolean pending) {
        this.pending = pending;
    }



    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }
	

}