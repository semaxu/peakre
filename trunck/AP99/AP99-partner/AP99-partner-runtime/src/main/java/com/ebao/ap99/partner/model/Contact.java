package com.ebao.ap99.partner.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


public class Contact implements Serializable {
	private static final long serialVersionUID = 1L;

	private long contactId;
	private String contactPerson;
    private String contactTitle;
    private String emailAddress;
    private String telephoneNumber;
    private String remark;
    private String address;
    
    private Long insertBy;
    private Date insertTime;
	
	public Contact() {
	}


	public long getContactId() {
		return contactId;
	}




	public void setContactId(long contactId) {
		this.contactId = contactId;
	}




	public String getContactPerson() {
		return contactPerson;
	}




	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}




	public String getContactTitle() {
		return contactTitle;
	}




	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}




	public String getEmailAddress() {
		return emailAddress;
	}


	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


	public String getTelephoneNumber() {
		return telephoneNumber;
	}


	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
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
	
	

}