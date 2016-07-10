package com.ebao.ap99.contract.model;

import java.math.BigDecimal;
import java.util.Date;

public class PremiumItemVO {

	private Long itemId;

	private BigDecimal amount;

	private String currency;

	private String definedIn;

	private Date dueDate;

	private String itemType;

	private Long instalNo;

	private Long instalPayNo;

	private BigDecimal minimumAmount;

	private BigDecimal depositAmount;

	private Date operateDate;

	private BigDecimal ourSignedShare;

	private BigDecimal ourWrittenShare;

	private BigDecimal percentage;

	private Long premiumId;

	public PremiumItemVO(String itemType) {
		this.setItemType(itemType);
	}

	public PremiumItemVO() {

	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getInstalNo() {
		return instalNo;
	}

	public void setInstalNo(Long instalNo) {
		this.instalNo = instalNo;
	}

	public Long getInstalPayNo() {
		return instalPayNo;
	}

	public void setInstalPayNo(Long instalPayNo) {
		this.instalPayNo = instalPayNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDefinedIn() {
		return definedIn;
	}

	public void setDefinedIn(String definedIn) {
		this.definedIn = definedIn;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public BigDecimal getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(BigDecimal minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
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
		return premiumId;
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

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof PremiumItemVO) {
			PremiumItemVO other = (PremiumItemVO) obj;
			return compare(this.itemId, other.itemId) && compare(this.amount, other.amount)
					&& compare(this.currency, other.currency) && compare(this.definedIn, other.definedIn)
					&& compare(this.dueDate, other.dueDate) && compare(this.itemType, other.itemType)
					&& compare(this.instalNo, other.instalNo) && compare(this.instalPayNo, other.instalPayNo)
					&& compare(this.minimumAmount, other.minimumAmount)
					&& compare(this.depositAmount, other.depositAmount) && compare(this.operateDate, other.operateDate)
					&& compare(this.ourSignedShare, other.ourSignedShare)
					&& compare(this.ourWrittenShare, other.ourWrittenShare)
					&& compare(this.percentage, other.percentage) && compare(this.premiumId, other.premiumId);
		}
		return false;
	}

	private boolean compare(Object str1, Object str2) {
		if (null == str1 && null == str2) {
			return true;
		}
		if ((str1 instanceof String && str2 instanceof String)
				|| (str1 instanceof BigDecimal && str2 instanceof BigDecimal)
				|| (str1 instanceof Date && str2 instanceof Date) || (str1 instanceof Long && str2 instanceof Long)) {
			if (null != str1 && null != str2) {
				return str1.equals(str2);
			} else if (null != str1 || null != str2) {
				return false;
			}
		}
		return false;
	}
}
