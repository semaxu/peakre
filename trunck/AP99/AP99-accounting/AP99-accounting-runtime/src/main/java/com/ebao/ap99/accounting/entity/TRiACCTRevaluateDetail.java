package com.ebao.ap99.accounting.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the T_RI_REVALUATE_DETAIL database table.
 * 
 */
@Entity
@Table(name="T_RI_ACCT_REVALUATE_DETAIL")
@NamedQuery(name="TRiACCTRevaluateDetail.findAll", query="SELECT t FROM TRiACCTRevaluateDetail t")
public class TRiACCTRevaluateDetail extends com.ebao.ap99.parent.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="REVALUATION_DETAIL_ID")
	private long revaluationDetailId;

	private BigDecimal balance;

	@Column(name="BIZ_ID")
	private long bizId;

	@Column(name="entry_code")
	private String entryCode;
	
	@Column(name="entry_item")
	private String entryItem;
	
	@Column(name="contract_id")
	private String contractID;
	
	@Column(name="CURRENCY_CODE")
	private String currencyCode;


	@Column(name="\"SIGN\"")
	private Integer sign;
	
    @Column(name = "ITEM_TYPE")
    private String itemType;


	//bi-directional many-to-one association to TRiRevaluate
	@ManyToOne
	@JoinColumn(name="REVALUATION_ID")
	private TRiACCTRevaluate TRiACCTRevaluate;

	public TRiACCTRevaluateDetail() {
	}

	public long getRevaluationDetailId() {
		return this.revaluationDetailId;
	}

	public void setRevaluationDetailId(long revaluationDetailId) {
		this.revaluationDetailId = revaluationDetailId;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public TRiACCTRevaluate getTRiACCTRevaluate() {
		return this.TRiACCTRevaluate;
	}

	public void setTRiACCTRevaluate(TRiACCTRevaluate TRiACCTRevaluate) {
		this.TRiACCTRevaluate = TRiACCTRevaluate;
	}

	public long getBizId() {
		return bizId;
	}

	public void setBizId(long bizId) {
		this.bizId = bizId;
	}

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	@Override
	public Long getPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPrimaryKey(Long paramLong) {
		// TODO Auto-generated method stub
		
	}

	public String getEntryCode() {
		return entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	public String getEntryItem() {
		return entryItem;
	}

	public void setEntryItem(String entryItem) {
		this.entryItem = entryItem;
	}

	public String getContractID() {
		return contractID;
	}

	public void setContractID(String contractID) {
		this.contractID = contractID;
	}
	
	
	
	
	

}