/**
 * 
 */
package com.ebao.ap99.arap.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author meiliang.zou
 *
 */
public class EntryItemInformationVO {
	private Long mappingEntryId;
	
	private String contractCode;
	
	private String sectionNumber;
	
	private String businessType;
	
	private String businessNumber;
	
	private String businessDescription;
	
	private String entryCode;
	
	private String entryItem;
	
	private String currency;
	
	private BigDecimal amount;
	
	private BigDecimal amountInUSD;
	
	private BigDecimal exchangeRate;
	
	private Date generationDate;
	
	private Date postDate;
	
	private String broker;
	
	private String cedentRetrocessionaire;
	
	private String uWYear;
	
	private String uWArea;
	
	private String indicator;
	
	private Integer postStatus;
	
	private String operator;

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	/**
	 * @return the sectionNumber
	 */
	public String getSectionNumber() {
		return sectionNumber;
	}

	/**
	 * @param sectionNumber the sectionNumber to set
	 */
	public void setSectionNumber(String sectionNumber) {
		this.sectionNumber = sectionNumber;
	}

	/**
	 * @return the businessType
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * @param businessType the businessType to set
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * @return the businessNumber
	 */
	public String getBusinessNumber() {
		return businessNumber;
	}

	/**
	 * @param businessNumber the businessNumber to set
	 */
	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}

	/**
	 * @return the businessDescription
	 */
	public String getBusinessDescription() {
		return businessDescription;
	}

	/**
	 * @param businessDescription the businessDescription to set
	 */
	public void setBusinessDescription(String businessDescription) {
		this.businessDescription = businessDescription;
	}

	/**
	 * @return the entryCode
	 */
	public String getEntryCode() {
		return entryCode;
	}

	/**
	 * @param entryCode the entryCode to set
	 */
	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	/**
	 * @return the entryItem
	 */
	public String getEntryItem() {
		return entryItem;
	}

	/**
	 * @param entryItem the entryItem to set
	 */
	public void setEntryItem(String entryItem) {
		this.entryItem = entryItem;
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
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the amountInUSD
	 */
	public BigDecimal getAmountInUSD() {
		return amountInUSD;
	}

	/**
	 * @param amountInUSD the amountInUSD to set
	 */
	public void setAmountInUSD(BigDecimal amountInUSD) {
		this.amountInUSD = amountInUSD;
	}

	/**
	 * @return the exchangeRate
	 */
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * @param exchangeRate the exchangeRate to set
	 */
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * @return the generationDate
	 */
	public Date getGenerationDate() {
		return generationDate;
	}

	/**
	 * @param generationDate the generationDate to set
	 */
	public void setGenerationDate(Date generationDate) {
		this.generationDate = generationDate;
	}

	/**
	 * @return the postDate
	 */
	public Date getPostDate() {
		return postDate;
	}

	/**
	 * @param postDate the postDate to set
	 */
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

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

	/**
	 * @return the cedentRetrocessionaire
	 */
	public String getCedentRetrocessionaire() {
		return cedentRetrocessionaire;
	}

	/**
	 * @param cedentRetrocessionaire the cedentRetrocessionaire to set
	 */
	public void setCedentRetrocessionaire(String cedentRetrocessionaire) {
		this.cedentRetrocessionaire = cedentRetrocessionaire;
	}

	/**
	 * @return the uWYear
	 */
	public String getuWYear() {
		return uWYear;
	}

	/**
	 * @param uWYear the uWYear to set
	 */
	public void setuWYear(String uWYear) {
		this.uWYear = uWYear;
	}

	/**
	 * @return the uWArea
	 */
	public String getuWArea() {
		return uWArea;
	}

	/**
	 * @param uWArea the uWArea to set
	 */
	public void setuWArea(String uWArea) {
		this.uWArea = uWArea;
	}

	/**
	 * @return the indicator
	 */
	public String getIndicator() {
		return indicator;
	}

	/**
	 * @param indicator the indicator to set
	 */
	public void setIndicator(String indicator) {
		this.indicator = indicator;
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
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getMappingEntryId() {
		return mappingEntryId;
	}

	public void setMappingEntryId(Long mappingEntryId) {
		this.mappingEntryId = mappingEntryId;
	}
	
}
