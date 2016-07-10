package com.ebao.ap99.arap.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class GLInterfaceVO {
	private String entryCode;
	private Long contractId;
	private String contractCode;
	private Integer contractCate;
	private String currencyCode;
	private BigDecimal amount;
	private Integer bizTransType;
	private Long feeId;
	private Long settleDetailId;
	private Long suspenseId;
	private Integer settleType;// finance settlement transaction type
	private Long settleId;// finance settlement transaction ID
	private Long reverseId;
	private Integer specialSubmit;
	private Integer cashBalanceType;
	private Integer sign; //business sign base on entry code and contract category
	private Integer settleSourceType;
	private Long bizTransId;
	private String bizTransNo;
	private Date bizTransDate;
	private Map<String,String> otherFactorMap;
	
	public Integer getBizTransType() {
		return bizTransType;
	}
	public void setBizTransType(Integer bizTransType) {
		this.bizTransType = bizTransType;
	}
	public Long getBizTransId() {
		return bizTransId;
	}
	public void setBizTransId(Long bizTransId) {
		this.bizTransId = bizTransId;
	}
	public Date getBizTransDate() {
		return bizTransDate;
	}
	public void setBizTransDate(Date bizTransDate) {
		this.bizTransDate = bizTransDate;
	}
	public String getEntryCode() {
		return entryCode;
	}
	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}
	public Long getContractId() {
		return contractId;
	}
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	public Integer getContractCate() {
		return contractCate;
	}
	public void setContractCate(Integer contractCate) {
		this.contractCate = contractCate;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Long getFeeId() {
		return feeId;
	}
	public void setFeeId(Long feeId) {
		this.feeId = feeId;
	}
	public Long getSettleDetailId() {
		return settleDetailId;
	}
	public void setSettleDetailId(Long settleDetailId) {
		this.settleDetailId = settleDetailId;
	}
	public Integer getSettleType() {
		return settleType;
	}
	public void setSettleType(Integer settleType) {
		this.settleType = settleType;
	}
	public Long getSettleId() {
		return settleId;
	}
	public void setSettleId(Long settleId) {
		this.settleId = settleId;
	}
	public Long getSuspenseId() {
		return suspenseId;
	}
	public void setSuspenseId(Long suspenseId) {
		this.suspenseId = suspenseId;
	}
	public Integer getSpecialSubmit() {
		return specialSubmit;
	}
	public void setSpecialSubmit(Integer specialSubmit) {
		this.specialSubmit = specialSubmit;
	}
	public Integer getSign() {
		return sign;
	}
	public void setSign(Integer sign) {
		this.sign = sign;
	}
	public Long getReverseId() {
		return reverseId;
	}
	public void setReverseId(Long reverseId) {
		this.reverseId = reverseId;
	}
	public String getNoMappingDefMessage(){
		StringBuffer sb = new StringBuffer();
		sb.append("There is no general account mapping : ")
			.append("entry code[").append(this.getEntryCode()).append("]");
		if(this.feeId != null){
			sb.append(" feeId[").append(this.getFeeId()).append("]");
		}
		if(this.settleDetailId != null){
			sb.append(" settleDetailId[").append(this.getSettleDetailId()).append("]");
		}
		return sb.toString();
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getBizTransNo() {
		return bizTransNo;
	}
	public void setBizTransNo(String bizTransNo) {
		this.bizTransNo = bizTransNo;
	}
	public Integer getCashBalanceType() {
		return cashBalanceType;
	}
	public void setCashBalanceType(Integer cashBalanceType) {
		this.cashBalanceType = cashBalanceType;
	}
	public Map<String,String> getOtherFactorMap() {
		return otherFactorMap;
	}
	public void setOtherFactorMap(Map<String,String> otherFactorMap) {
		this.otherFactorMap = otherFactorMap;
	}
	public Integer getSettleSourceType() {
		return settleSourceType;
	}
	public void setSettleSourceType(Integer settleSourceType) {
		this.settleSourceType = settleSourceType;
	}
	
	
}
