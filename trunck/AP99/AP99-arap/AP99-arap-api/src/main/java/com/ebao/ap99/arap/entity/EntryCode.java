package com.ebao.ap99.arap.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the T_RI_ENTRY_CODE database table.
 * 
 */
@Entity
@Table(name = "T_RI_ENTRY_CODE")
@NamedQuery(name = "EntryCode.findAll", query = "SELECT e FROM EntryCode e")
public class EntryCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ENTRY_CODE")
	private String entryCode;

	@Column(name = "DESCRIPTION")
	private String desription;

	@Column(name = "ENTRY_CODE_NAME")
	private String entryCodeName;

	@Column(name = "NEED_POST")
	private Integer needPost;

	@Column(name = "SIGN")
	private Integer sign;
	
	@Column(name = "CASH_BALANCE")
	private Integer cashBalance;
	
	@Column(name = "ESTIMATE_GROUP")
	private String estimateGroup;
	
	@Column(name = "STATEMENT_TYPE")
	private String statementType;

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}
	
	public EntryCode() {
	}

	public String getEntryCode() {
		return this.entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	public String getDesription() {
		return this.desription;
	}

	public void setDesription(String desription) {
		this.desription = desription;
	}

	public String getEntryCodeName() {
		return this.entryCodeName;
	}

	public void setEntryCodeName(String entryCodeName) {
		this.entryCodeName = entryCodeName;
	}

	public Integer getNeedPost() {
		return needPost;
	}

	public void setNeedPost(Integer needPost) {
		this.needPost = needPost;
	}

	public Integer getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(Integer cashBalance) {
		this.cashBalance = cashBalance;
	}

	public String getEstimateGroup() {
		return estimateGroup;
	}

	public void setEstimateGroup(String estimateGroup) {
		this.estimateGroup = estimateGroup;
	}

	public String getStatementType() {
		return statementType;
	}

	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}

}