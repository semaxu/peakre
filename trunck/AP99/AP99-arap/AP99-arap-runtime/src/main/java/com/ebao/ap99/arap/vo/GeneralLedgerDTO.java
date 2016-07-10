/**
 * 
 */
package com.ebao.ap99.arap.vo;

import com.ebao.ap99.parent.Page;

/**
 * @author meiliang.zou
 *
 */
public class GeneralLedgerDTO extends Page<GeneralLedgerVO>{
	private static final long serialVersionUID = 5726424827258748082L;

	private String glAccount;
	
	private String currency;
	
	private Integer postStatus;
	
	private String documentDateFrom;
	
	private String documentDateTo;
	
	private String postDateFrom;
	
	private String postDateTo;
	
	private int pageNumber = 1;
	private int pageSize = 10;
	private long totalSize = 10;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	/**
	 * @return the glAccount
	 */
	public String getGlAccount() {
		return glAccount;
	}

	/**
	 * @param glAccount the glAccount to set
	 */
	public void setGlAccount(String glAccount) {
		this.glAccount = glAccount;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the postStatus
	 */
	public Integer getPostStatus() {
		return postStatus;
	}

	/**
	 * @param postStatus the postStatus to set
	 */
	public void setPostStatus(Integer postStatus) {
		this.postStatus = postStatus;
	}

	/**
	 * @return the documentDateFrom
	 */
	public String getDocumentDateFrom() {
		return documentDateFrom;
	}

	/**
	 * @param documentDateFrom the documentDateFrom to set
	 */
	public void setDocumentDateFrom(String documentDateFrom) {
		this.documentDateFrom = documentDateFrom;
	}

	/**
	 * @return the documentDateTo
	 */
	public String getDocumentDateTo() {
		return documentDateTo;
	}

	/**
	 * @param documentDateTo the documentDateTo to set
	 */
	public void setDocumentDateTo(String documentDateTo) {
		this.documentDateTo = documentDateTo;
	}

	/**
	 * @return the postDateFrom
	 */
	public String getPostDateFrom() {
		return postDateFrom;
	}

	/**
	 * @param postDateFrom the postDateFrom to set
	 */
	public void setPostDateFrom(String postDateFrom) {
		this.postDateFrom = postDateFrom;
	}

	/**
	 * @return the postDateTo
	 */
	public String getPostDateTo() {
		return postDateTo;
	}

	/**
	 * @param postDateTo the postDateTo to set
	 */
	public void setPostDateTo(String postDateTo) {
		this.postDateTo = postDateTo;
	}
}
