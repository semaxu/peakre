package com.ebao.ap99.partner.entity;

import java.io.Serializable;
import javax.persistence.*;



@Entity
@Table(name="T_RI_BP_CONTACT")
@NamedQuery(name="TContact.findAll", query="SELECT t FROM TContact t")
public class TContact extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="CONTACT_ID")
	private long contactId;

	
	@Column (name="CONTACT_PERSON")
	private String contactPerson;
	
    @Column (name="CONTACT_TITLE")
    private String contactTitle;
    
    @Column (name="EMAIL_ADDRESS")
    private String emailAddress;
    
    @Column (name="TELEPHONE_NUMBER")
    private String telephoneNumber;
    
    @Column (name="REMARK")
    private String remark;
    
	@ManyToOne
	@JoinColumn(name="PARTNER_ID")
	private TPartner TPartner;
	
	public TContact() {
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