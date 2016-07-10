package com.ebao.ap99.arap.vo;

import java.math.BigDecimal;
import java.util.Date;

public class FinAccountInfo {
	private Long sectionId;
	private String entryCode;
	private BigDecimal amount;
	private String currencyCode;
	private String finQuarter;
	private String sourceText;
	private Date transDate;
	public String getSourceText() {
		return sourceText;
	}
	public void setSourceText(String sourceText) {
		this.sourceText = sourceText;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	private Long operatorId;
	
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public String getEntryCode() {
		return entryCode;
	}
	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getFinQuarter() {
		return finQuarter;
	}
	public void setFinQuarter(String finQuarter) {
		this.finQuarter = finQuarter;
	}
}
