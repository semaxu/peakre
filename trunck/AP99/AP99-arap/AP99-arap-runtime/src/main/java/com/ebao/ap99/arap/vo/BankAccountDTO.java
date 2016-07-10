/**
 * 
 */
package com.ebao.ap99.arap.vo;

import com.ebao.ap99.arap.entity.BankAccount;
import com.ebao.ap99.parent.Page;

/**
 * @author meiliang.zou
 *
 */
public class BankAccountDTO extends Page<BankAccount> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2373755134435258557L;

	private Long accountId;
	
	private String bankAccountName;

	private String bankAccountNumber;

	private String currency;

	private String branch;

	private String bank;
	
	private String accountType;
	
	private String status;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
