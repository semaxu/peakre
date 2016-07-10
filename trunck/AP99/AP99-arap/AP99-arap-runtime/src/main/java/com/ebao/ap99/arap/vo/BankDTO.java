/**
 * 
 */
package com.ebao.ap99.arap.vo;

/**
 * @author meiliang.zou
 *
 */
public class BankDTO {
	private String bankCode;
	private String bankName;
	private String BusinessPartnerId;

    private int pageIndex;

    private int countPerPage;

	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param bankCode
	 *            the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * @return the countPerPage
	 */
	public int getCountPerPage() {
		return countPerPage;
	}

	/**
	 * @param countPerPage the countPerPage to set
	 */
	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}

	/**
	 * @return the businessPartnerId
	 */
	public String getBusinessPartnerId() {
		return BusinessPartnerId;
	}

	/**
	 * @param businessPartnerId the businessPartnerId to set
	 */
	public void setBusinessPartnerId(String businessPartnerId) {
		BusinessPartnerId = businessPartnerId;
	}
    
}
