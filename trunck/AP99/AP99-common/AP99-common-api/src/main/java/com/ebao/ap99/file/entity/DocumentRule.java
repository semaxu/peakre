package com.ebao.ap99.file.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ebao.ap99.parent.BaseEntityImpl;





/**
 * The persistent class for the T_RI_CM_DOCUMENT_RULE database table.
 * 
 */
@Entity
@Table(name="T_RI_CM_DOCUMENT_RULE")
@NamedQuery(name="TDocumentRule.findAll", query="SELECT t FROM DocumentRule t")
public class DocumentRule  extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="BUSINESS_TYPE")
	private String businessType;

	@Column(name="PARSE_CLASS")
	private String parseClass;

	@Column(name="PARSE_SERVICE")
	private String parseService;
	
    @Column(name="PARSE_TYPE")
	private String parseType;

    @Column(name="TEMPLATE")
    private String template;
    
    @Column(name="PROCESS_TYPE")
    private String processType;
    
	@OneToMany(mappedBy = "DocumentRule", cascade = { CascadeType.ALL }, fetch=FetchType.EAGER)
	private List<DocumentField> DocumentFields;
	
	
	public DocumentRule() {
	}

	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getParseClass() {
		return this.parseClass;
	}

	public void setParseClass(String parseClass) {
		this.parseClass = parseClass;
	}

	public String getParseService() {
		return this.parseService;
	}

	public void setParseService(String parseService) {
		this.parseService = parseService;
	}

    public List<DocumentField> getTDocumentFields() {
        return DocumentFields;
    }

    public void setTDocumentFields(List<DocumentField> documentFields) {
        DocumentFields = documentFields;
    }
    

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getParseType() {
        return parseType;
    }

    public void setParseType(String parseType) {
        this.parseType = parseType;
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

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

}