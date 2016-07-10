package com.ebao.ap99.arap.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the T_RI_BCP_CFG_BANK database table.
 * 
 */
@Entity
@Table(name="T_RI_BCP_CFG_BANK")
@NamedQueries({
	@NamedQuery(name="Bank.findAll", query="SELECT b FROM Bank b"),
	@NamedQuery(name="Bank.findBankNameByCode", query="SELECT t FROM Bank t WHERE t.bankCode = :bankCode")
})
public class Bank implements Serializable {
	private static final long serialVersionUID = 2704170726094730152L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="S_UID")
	@Column(name="BANK_ID")
	private long bankId;

	@Column(name="BANK_CODE")
	private String bankCode;

	@Column(name="BANK_NAME")
	private String bankName;

	@Column(name="BANK_ADDRESS")
	private String bankAddress;

	@Column(name="BENEFICIARY_NAME")
	private String beneficiaryName;
	
	public Bank() {
	}

	/**
	 * @return the bankId
	 */
	public long getBankId() {
		return bankId;
	}


	/**
	 * @param bankId the bankId to set
	 */
	public void setBankId(long bankId) {
		this.bankId = bankId;
	}


	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}


	/**
	 * @param bankCode the bankCode to set
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
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


	/**
	 * @return the bankAddress
	 */
	public String getBankAddress() {
		return bankAddress;
	}


	/**
	 * @param bankAddress the bankAddress to set
	 */
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}


	/**
	 * @return the beneficiaryName
	 */
	public String getBeneficiaryName() {
		return beneficiaryName;
	}


	/**
	 * @param beneficiaryName the beneficiaryName to set
	 */
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
}