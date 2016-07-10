package com.ebao.ap99.partner.entity;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="T_RI_BP_BANK_ACCOUNT")
@NamedQuery(name="TBankAccount.findAll", query="SELECT t FROM TBankAccount t")
public class TBankAccount extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="BANK_ACCOUNT_ID")
	private long bankAccountId;


    @Column (name="ACCOUNT_HOLDER_TYPE")
    private String accountHolderType;
    
    @Column (name="ACCOUNT_HOLDER_NAME")
    private String accountHolderName;
    
    @Column (name="BANK_CODE")
    private String bankCode;
    
    @Column (name="ACCOUNT_NUMBER")
    private String accountNumber;
    
    @Column (name="DEBIT_CREDIT")
    private String debitCredit;
    
    @Column (name="ACCOUNT_STATUS")
    private String accountStatus;
    
	@ManyToOne
	@JoinColumn(name="PARTNER_ID")
	private TPartner TPartner;
    
    


	public long getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public String getAccountHolderType() {
		return accountHolderType;
	}

	public void setAccountHolderType(String accountHolderType) {
		this.accountHolderType = accountHolderType;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDebitCredit() {
		return debitCredit;
	}

	public void setDebitCredit(String debitCredit) {
		this.debitCredit = debitCredit;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}


	


	public TPartner getTPartner() {
		return TPartner;
	}

	public void setTPartner(TPartner tPartner) {
		TPartner = tPartner;
	}

	@Override
	public Long getPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPrimaryKey(Long arg0) {
		// TODO Auto-generated method stub
		
	}

}