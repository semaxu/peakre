package com.ebao.ap99.arap.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SettlementTransaction implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean selected = false;
	
	private Long transId;
	
	private String transNo;

	private Integer transType;
	
	private BigDecimal amount;

	private String transCurrency;

	private String paymentMethod;

	private String payerPayee;
	
	private String payerPayeeName;

	private Date cpDate;

	private Date transDate;
	
	private Date insertTime;
	
	private Date updateTime;
	
	private Integer status;
	
	public Integer getTransType() {
		return transType;
	}

	public void setTransType(Integer transType) {
		this.transType = transType;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransCurrency() {
		return transCurrency;
	}

	public void setTransCurrency(String transCurrency) {
		this.transCurrency = transCurrency;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPayerPayee() {
		return payerPayee;
	}

	public void setPayerPayee(String payerPayee) {
		this.payerPayee = payerPayee;
	}

	public Date getCpDate() {
		return cpDate;
	}

	public void setCpDate(Date cpDate) {
		this.cpDate = cpDate;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public Long getTransId() {
		return transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}

	public String getPayerPayeeName() {
		return payerPayeeName;
	}

	public void setPayerPayeeName(String payerPayeeName) {
		this.payerPayeeName = payerPayeeName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
