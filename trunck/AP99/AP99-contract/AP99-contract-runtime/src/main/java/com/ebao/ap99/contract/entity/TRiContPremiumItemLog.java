package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the T_RI_CONT_PREMIUM_ITEM_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_PREMIUM_ITEM_LOG")
@NamedQuery(name = "TRiContPremiumItemLog.findAll", query = "SELECT t FROM TRiContPremiumItemLog t")
public class TRiContPremiumItemLog extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl
		implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	private BigDecimal amount;

	private String currency;

	@Column(name = "DEFINED_IN")
	private String definedIn;

	@Temporal(TemporalType.DATE)
	@Column(name = "DUE_DATE")
	private Date dueDate;

	@Column(name = "ITEM_ID")
	private Long itemId;

	@Column(name = "ITEM_TYPE")
	private String itemType;

	@Column(name = "MINIMUM_AMOUNT")
	private BigDecimal minimumAmount;

	@Column(name = "DEPOSIT_AMOUNT")
	private BigDecimal depositAmount;

	@Temporal(TemporalType.DATE)
	@Column(name = "OPERATE_DATE")
	private Date operateDate;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "OUR_SIGNED_SHARE")
	private BigDecimal ourSignedShare;

	@Column(name = "OUR_WRITTEN_SHARE")
	private BigDecimal ourWrittenShare;

	@Column(name = "PERCENTAGE")
	private BigDecimal percentage;

	@Column(name = "PREMIUM_ID")
	private Long premiumId;
	
	@Column(name = "INSTAL_NO")
	private Long instalNo;
	
	@Column(name = "INSTAL_PAY_NO")
	private Long instalPayNo;

	public Long getInstalPayNo() {
		return instalPayNo;
	}

	public void setInstalPayNo(Long instalPayNo) {
		this.instalPayNo = instalPayNo;
	}

	public TRiContPremiumItemLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDefinedIn() {
		return this.definedIn;
	}

	public void setDefinedIn(String definedIn) {
		this.definedIn = definedIn;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public BigDecimal getMinimumAmount() {
		return this.minimumAmount;
	}

	public void setMinimumAmount(BigDecimal minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public Date getOperateDate() {
		return this.operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public BigDecimal getOurSignedShare() {
		return ourSignedShare;
	}

	public void setOurSignedShare(BigDecimal ourSignedShare) {
		this.ourSignedShare = ourSignedShare;
	}

	public BigDecimal getOurWrittenShare() {
		return ourWrittenShare;
	}

	public void setOurWrittenShare(BigDecimal ourWrittenShare) {
		this.ourWrittenShare = ourWrittenShare;
	}

	public Long getPremiumId() {
		return this.premiumId;
	}

	public void setPremiumId(Long premiumId) {
		this.premiumId = premiumId;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getLogId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setLogId(key);
	}

	public Long getInstalNo() {
		return instalNo;
	}

	public void setInstalNo(Long instalNo) {
		this.instalNo = instalNo;
	}

}