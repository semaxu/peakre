package com.ebao.ap99.file.model;

import java.io.Serializable;
import java.util.Date;

public class Document implements Serializable {
    private static final long serialVersionUID = 1L;

    private long documentId;

    private long businessId;

    private String businessType;

    private String documentType;

    private String fileName;

    private String filePath;

    private String remarks;

    private long uploadPerson;

    private Date uploadTime;

    private int pageIndex;

    private int countPerPage;
    
    private String documentTypeSelf;
    
    private String processType;
    
    public Document() {
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

	public String getDocumentTypeSelf() {
		return documentTypeSelf;
	}

	public void setDocumentTypeSelf(String documentTypeSelf) {
		this.documentTypeSelf = documentTypeSelf;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}
}
