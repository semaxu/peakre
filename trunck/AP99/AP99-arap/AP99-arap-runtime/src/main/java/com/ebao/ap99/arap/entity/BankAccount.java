package com.ebao.ap99.arap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;


/**
 * The persistent class for the T_RI_BCP_CFG_BANK_ACCOUNT database table.
 * 
 */
@Entity
@Table(name="T_RI_BCP_CFG_BANK_ACCOUNT")
@NamedQuery(name="BankAccount.findAll", query="SELECT b FROM BankAccount b")
public class BankAccount extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="S_UID")
	@Column(name="ACCOUNT_ID")
	private Long accountId;

	@Column(name="ACCOUNT_NAME")
	private String accountName;

	@Column(name="ACCOUNT_NO")
	private String accountNo;

	@Column(name="ACCOUNT_TYPE")
	private int accountType;

	@Column(name="BANK_ID")
	private long bankId;

	@Column(name="BRANCH_ID")
	private long branchId;

	@Column(name="CURRENCY_CODE")
	private String currencyCode;
	
	@Column(name="STATUS")
	private int status;
	
	@Transient
	private int inUse;
	
	public BankAccount() {
	}

	public Long getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return this.accountNo;
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public long getBranchId() {
		return branchId;
	}

	public void setBranchId(long branchId) {
		this.branchId = branchId;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public int getAccountType() {
		return this.accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public Long getPrimaryKey() {
		// TODO Auto-generated method stub
		return this.getBankId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		// TODO Auto-generated method stub
		this.setBankId(key);
	}

	public int getInUse() {
		return inUse;
	}

	public void setInUse(int inUse) {
		this.inUse = inUse;
	}

}