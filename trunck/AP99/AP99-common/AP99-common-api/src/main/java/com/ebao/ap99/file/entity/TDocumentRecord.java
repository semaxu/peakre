package com.ebao.ap99.file.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_DOCUMENT_RECORD database table.
 * 
 */
@Entity
@Table(name="T_RI_CM_DOCUMENT_RECORD")
@NamedQuery(name="TDocumentRecord.findAll", query="SELECT t FROM TDocumentRecord t")
public class TDocumentRecord extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="DOCUMENT_RECORD_ID")
	private long documentRecordId;

	@Column(name="ERR_CONTENT")
	private String errContent;

	@Column(name="PARSE_RESULT")
	private String parseResult;

	@Column(name="RECORD_CONTENT")
	private String recordContent;

    @Column(name="DOCUMENT_ID")
    private long documentId;
	public TDocumentRecord() {
	}

	public long getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}

	public long getDocumentRecordId() {
		return this.documentRecordId;
	}

	public void setDocumentRecordId(long documentRecordId) {
		this.documentRecordId = documentRecordId;
	}

	public String getErrContent() {
		return this.errContent;
	}

	public void setErrContent(String errContent) {
		this.errContent = errContent;
	}

	public String getParseResult() {
		return this.parseResult;
	}

	public void setParseResult(String parseResult) {
		this.parseResult = parseResult;
	}

	public String getRecordContent() {
		return this.recordContent;
	}

	public void setRecordContent(String recordContent) {
		this.recordContent = recordContent;
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