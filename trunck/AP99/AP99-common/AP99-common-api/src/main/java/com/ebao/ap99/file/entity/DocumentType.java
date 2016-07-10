package com.ebao.ap99.file.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.ap99.parent.BaseEntityImpl;



/**
 * The persistent class for the T_RI_CM_DOCUMENT_CATEGORY database table.
 * 
 */
@Entity
@Table(name="T_RI_CM_DOCUMENT_TYPE")
@NamedQuery(name="TDocumentType.findAll", query="SELECT t FROM DocumentType t")
public class DocumentType  extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="BUSINESS_TYPE")
	private String businessType;

	@Id
	@Column(name="DOCUMENT_TYPE")
	private String documentType;

	@Column(name="TYPE_NAME")
	private String typeName;



	public DocumentType() {
	}

	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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