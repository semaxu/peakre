package com.ebao.ap99.file.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_DOCUMENT database table.
 */
@Entity
@Table(name = "T_RI_CM_DOCUMENT")
@NamedQuery(name = "TDocument.findAll", query = "SELECT t FROM TDocument t")
public class TDocument extends BaseEntityImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
    @Column(name = "DOCUMENT_ID")
    private long documentId;

    @Column(name = "BUSINESS_ID")
    private long businessId;

    @Column(name = "BUSINESS_TYPE")
    private String businessType;

    @Column(name = "DOCUMENT_TYPE")
    private String documentType;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "PARA")
    private String para;

    @Column(name = "FILE_URL")
    private String fileUrl;
    
    
    @Column(name = "UPLOAD_PERSON")
    private long uploadPerson;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPLOAD_TIME")
    private Date uploadTime;
    
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "DOCUMENT_TYPE_SELF")
    private String documentTypeSelf;

    public TDocument() {
    }

    public long getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    public long getBusinessId() {
        return this.businessId;
    }

    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }

    public String getBusinessType() {
        return businessType;
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

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public long getUploadPerson() {
        return this.uploadPerson;
    }

    public void setUploadPerson(long uploadPerson) {
        this.uploadPerson = uploadPerson;
    }

    public Date getUploadTime() {
        return this.uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    
    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDocumentTypeSelf() {
		return documentTypeSelf;
	}

	public void setDocumentTypeSelf(String documentTypeSelf) {
		this.documentTypeSelf = documentTypeSelf;
	}

}
