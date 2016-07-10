package com.ebao.ap99.partner.entity;

import java.io.Serializable;
import javax.persistence.*;



@Entity
@Table(name="T_RI_BP_RELATIONSHIP")
@NamedQuery(name="TRelationship.findAll", query="SELECT t FROM TRelationship t")
public class TRelationship extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="RELATIONSHIP_ID")
	private long relationshipId;

	
	@Column (name="RELATIONSHIP_TYPE")
	private String relationshipType;
	
    
	@Column (name="RELATED_PARTNER_ID")
    private  String businessPartnerId;
	
	
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
    
    




    public String getBusinessPartnerId() {
        return businessPartnerId;
    }



    public void setBusinessPartnerId(String businessPartnerId) {
        this.businessPartnerId = businessPartnerId;
    }






    @ManyToOne
	@JoinColumn(name="PARTNER_ID")
	private TPartner TPartner;
	
	public TRelationship() {
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