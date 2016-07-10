package com.ebao.ap99.partner.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.ebao.ap99.parent.BaseEntityImpl;


/**
 * The persistent class for the T_PARTNER_LOG database table.
 * 
 */
@Entity
@Table(name="T_RI_BP_PARTNER_LOG")
@NamedQuery(name="TPartnerLog.findAll", query="SELECT t FROM TPartnerLog t")
public class TPartnerLog  extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="PARTNER_LOG_ID")
	private long partnerLogId;

	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="LAST_NAME")
	private String lastName;

	@Column(name="PARTNER_ID")
	private long partnerId;

	@Column(name="REGISTRATION_NAME")
	private String registrationName;

	@Column(name="SHORT_NAME")
	private String shortName;

	@Column(name="STATUS")
	private String status;

	@Column(name="TRADING_NAME")
	private String tradingName;

	@Column(name="FULL_NAME")
	private String fullName;
	
	public TPartnerLog() {
	}

	public long getPartnerLogId() {
		return this.partnerLogId;
	}

	public void setPartnerLogId(long partnerLogId) {
		this.partnerLogId = partnerLogId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(long partnerId) {
		this.partnerId = partnerId;
	}

	public String getRegistrationName() {
		return this.registrationName;
	}

	public void setRegistrationName(String registrationName) {
		this.registrationName = registrationName;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTradingName() {
		return this.tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	
	
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public Long getPrimaryKey() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPrimaryKey(Long paramLong) {
        // TODO Auto-generated method stub
        
    }

}