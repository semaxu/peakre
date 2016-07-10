/**
 * 
 */
package com.ebao.ap99.arap.vo;

/**
 * @author meiliang.zou
 *
 */
public class CollectionSearchDTO {

	private String broker;
	
	private String partnerCode;
	
	private String contractIds;
	
	private String statementId;
	
	private String claimNumber;
	
	private String collectionCurrency;

	/**
	 * @return the broker
	 */
	public String getBroker() {
		return broker;
	}

	/**
	 * @param broker the broker to set
	 */
	public void setBroker(String broker) {
		this.broker = broker;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	/**
	 * @return the contractIds
	 */
	public String getContractIds() {
		return contractIds;
	}

	/**
	 * @param contractIds the contractIds to set
	 */
	public void setContractIds(String contractIds) {
		this.contractIds = contractIds;
	}

	/**
	 * @return the statementId
	 */
	public String getStatementId() {
		return statementId;
	}

	/**
	 * @param statementId the statementId to set
	 */
	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}

	/**
	 * @return the claimNumber
	 */
	public String getClaimNumber() {
		return claimNumber;
	}

	/**
	 * @param claimNumber the claimNumber to set
	 */
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}

	/**
	 * @return the collectionCurrency
	 */
	public String getCollectionCurrency() {
		return collectionCurrency;
	}

	/**
	 * @param collectionCurrency the collectionCurrency to set
	 */
	public void setCollectionCurrency(String collectionCurrency) {
		this.collectionCurrency = collectionCurrency;
	}
	
	
}
