package com.ebao.ap99.file.model;

import java.io.Serializable;

/**
 * The persistent class for the T_DOCUMENT_RECORD database table.
 * 
 */
public class DocumentRecord  implements Serializable {
    
	private static final long serialVersionUID = 1L;

	private long documentRecordId;

	private String errContent;

	private String parseResult;

	private String recordContent;

    private long documentId;
    
    
    private int pageIndex;

    private int countPerPage;
    
	public DocumentRecord() {
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

}