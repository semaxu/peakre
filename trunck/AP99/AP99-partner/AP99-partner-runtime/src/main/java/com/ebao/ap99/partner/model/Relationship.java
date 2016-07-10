package com.ebao.ap99.partner.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


public class Relationship implements Serializable {
	private static final long serialVersionUID = 1L;

	private long relationshipId;
	private String relationshipType;
	private String businessPartnerId;
    private String partnerId;
    private Long insertBy;
    private Date insertTime;
    
    
	
	public Relationship() {
	}




    public long getRelationshipId() {
        return relationshipId;
    }




    public void setRelationshipId(long relationshipId) {
        this.relationshipId = relationshipId;
    }




    public String getRelationshipType() {
        return relationshipType;
    }




    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
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




    public String getBusinessPartnerId() {
        return businessPartnerId;
    }




    public void setBusinessPartnerId(String businessPartnerId) {
        this.businessPartnerId = businessPartnerId;
    }





	
	

}