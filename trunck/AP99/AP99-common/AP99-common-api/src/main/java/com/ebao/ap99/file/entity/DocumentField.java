package com.ebao.ap99.file.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CM_DOCUMENT_FIELD database table.
 * 
 */
@Entity
@Table(name="T_RI_CM_DOCUMENT_FIELD")
@NamedQuery(name="TDocumentField.findAll", query="SELECT t FROM DocumentField t")
public class DocumentField  extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DOCUMENT_FIELD_ID")
	private long documentFieldId;

	
	@Column(name="FIELD_MAXLENGTH")
	private Integer fieldMaxlength;

	@Column(name="FIELD_NAME")
	private String fieldName;

	@Column(name="FIELD_NULLABLE")
	private String fieldNullable;

	@Column(name="FIELD_TYPE")
	private String fieldType;
    
    @ManyToOne
    @JoinColumn(name="BUSINESS_TYPE")
    private DocumentRule DocumentRule;
	

    
	
	public DocumentField() {
	}

	public long getDocumentFieldId() {
		return this.documentFieldId;
	}

	public void setDocumentFieldId(long documentFieldId) {
		this.documentFieldId = documentFieldId;
	}


	public Integer getFieldMaxlength() {
		return this.fieldMaxlength;
	}

	public void setFieldMaxlength(Integer fieldMaxlength) {
		this.fieldMaxlength = fieldMaxlength;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldNullable() {
		return this.fieldNullable;
	}

	public void setFieldNullable(String fieldNullable) {
		this.fieldNullable = fieldNullable;
	}

	public String getFieldType() {
		return this.fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

    public DocumentRule getTDocumentRule() {
        return DocumentRule;
    }

    public void setTDocumentRule(DocumentRule documentRule) {
        DocumentRule = documentRule;
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