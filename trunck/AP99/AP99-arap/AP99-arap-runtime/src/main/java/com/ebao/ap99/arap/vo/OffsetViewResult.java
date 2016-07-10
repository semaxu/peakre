/**
 * 
 */
package com.ebao.ap99.arap.vo;

import java.util.Date;
import java.util.List;

/**
 * @author meiliang.zou
 *
 */
public class OffsetViewResult {
	private String remark;
	
	private List<CreditsAndDebit> creditsAndDebit;
	
	private List<Balance> balances;
	
	private String transNumber;
	private Integer transStatus;
	private Date operationDate;
	private Integer operatorId;
	
	private Date registrationDate;

	public String getTransNumber() {
		return transNumber;
	}

	public void setTransNumber(String transNumber) {
		this.transNumber = transNumber;
	}

	public Integer getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(Integer transStatus) {
		this.transStatus = transStatus;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the creditsAndDebit
	 */
	public List<CreditsAndDebit> getCreditsAndDebit() {
		return creditsAndDebit;
	}

	/**
	 * @param creditsAndDebit the creditsAndDebit to set
	 */
	public void setCreditsAndDebit(List<CreditsAndDebit> creditsAndDebit) {
		this.creditsAndDebit = creditsAndDebit;
	}

	/**
	 * @return the balances
	 */
	public List<Balance> getBalances() {
		return balances;
	}

	/**
	 * @param balances the balances to set
	 */
	public void setBalances(List<Balance> balances) {
		this.balances = balances;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	
}
