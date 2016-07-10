package com.ebao.ap99.arap.entity;

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
import javax.persistence.Transient;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;

/**
 * The persistent class for the T_RI_BCP_FEE database table.
 * 
 */
@Entity
@Table(name = "T_RI_BCP_FEE")
@NamedQuery(name = "Fee.findAll", query = "SELECT f FROM Fee f")
public class Fee extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "FEE_ID")
	private Long feeId;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "ARAP_TYPE")
	private Integer arapType;

	@Column(name = "BALANCE")
	private BigDecimal balance;

	@Column(name = "CONTRACT_ID")
	private Long contractId;

	@Column(name = "PARTNER_CODE")
	private String partnerCode;

	@Column(name = "CONTRACT_CATE")
	private Integer contractCate;

	@Column(name = "CUR_PERIOD")
	private Integer curPeriod;

	@Column(name = "TOTAL_PERIOD")
	private Integer totalPeriod;

	@Column(name = "CURRENCY_CODE")
	private String currencyCode;

	@Temporal(TemporalType.DATE)
	@Column(name = "DUE_DATE")
	private Date dueDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "TRANS_DATE")
	private Date transDate;

	@Column(name = "ENTRY_CODE")
	private String entryCode;

	@Column(name = "IS_ESTIMATION")
	private Integer isEstimation;

	@Column(name = "NEED_POST")
	private Integer needPost;

	@Column(name = "POST_STATUS")
	private Integer postStatus;

	@Column(name = "SIGN")
	private Integer sign;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "BIZ_TRANS_NO")
	private String bizTransNo;

	@Column(name = "BIZ_TRANS_TYPE")
	private Integer bizTransType;
	
	@Column(name = "SPECIAL_SBUMT")
	private Integer specialSubmit;
	
	@Column(name = "POST_DATE")
	private Date postDate;
	
	@Column(name = "BIZ_TRANS_ID")
	private Long bizTranId;
	
	@Column(name = "SECTION_ID")
	private Long sectionId;
	
	@Column(name = "BIZ_TRANS_DESC")
	private String bizTransDesc;
	
	@Column(name = "BIZ_REF_ID")
	private Long bizRefId;
	
	@Column(name = "BIZ_OPERATOR_ID")
	private Long bizOperatorId;
	
	@Column(name = "TRANS_ID")
	private Long transId;
	
	@Column(name = "BIZ_REVERSAL_FLAG")
	private Integer bizReversalFlag;
	
	@Column(name = "CONTRACT_CODE")
	private String contractCode;
	
	@Transient
	private SettlementDetail settleDetail;
	
	@Transient
	private BigDecimal balanceInSettleCurrency;
	
	@Transient
	private Date settleDate;
	
	public Fee() {
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getEntryCode() {
		return this.entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	public Long getFeeId() {
		return feeId;
	}

	public void setFeeId(Long feeId) {
		this.feeId = feeId;
	}
	
	public Integer getArapType() {
		return arapType;
	}

	public void setArapType(Integer arapType) {
		this.arapType = arapType;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public Integer getCurPeriod() {
		return curPeriod;
	}

	public void setCurPeriod(Integer curPeriod) {
		this.curPeriod = curPeriod;
	}

	public Integer getTotalPeriod() {
		return totalPeriod;
	}

	public void setTotalPeriod(Integer totalPeriod) {
		this.totalPeriod = totalPeriod;
	}

	public Integer getIsEstimation() {
		return isEstimation;
	}

	public void setIsEstimation(Integer isEstimation) {
		this.isEstimation = isEstimation;
	}

	public Integer getNeedPost() {
		return needPost;
	}

	public void setNeedPost(Integer needPost) {
		this.needPost = needPost;
	}

	public Integer getPostStatus() {
		return postStatus;
	}

	public void setPostStatus(Integer postStatus) {
		this.postStatus = postStatus;
	}

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getContractCate() {
		return contractCate;
	}

	public void setContractCate(Integer contractCate) {
		this.contractCate = contractCate;
	}

	public String getBizTransNo() {
		return bizTransNo;
	}

	public void setBizTransNo(String bizTransNo) {
		this.bizTransNo = bizTransNo;
	}
	
	@Transient
	public String getBizTransNoAndCurrency(){
		//return this.bizTransNo+"-"+this.currencyCode;
		StringBuffer uniqueNo = new StringBuffer();
		uniqueNo.append(this.getCurrencyCode())
				.append("_").append(this.getBizTransNo())
				.append("_").append(this.getBizTransType())
				.append("_").append(this.getTransId());
		return uniqueNo.toString();
	}
	
	public Integer getBizTransType() {
		return bizTransType;
	}

	public void setBizTransType(Integer bizTransType) {
		this.bizTransType = bizTransType;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getFeeId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setFeeId(key);
	}
	
	@Transient
	public SettlementDetail getSettleDetail() {
		return settleDetail;
	}

	public void setSettleDetail(SettlementDetail settleDetail) {
		this.settleDetail = settleDetail;
	}

	public BigDecimal getBalanceInSettleCurrency() {
		return balanceInSettleCurrency;
	}

	public void setBalanceInSettleCurrency(BigDecimal balanceInSettleCurrency) {
		this.balanceInSettleCurrency = balanceInSettleCurrency;
	}

	public Date getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	public Long getBizTranId() {
		return bizTranId;
	}

	public void setBizTranId(Long bizTranId) {
		this.bizTranId = bizTranId;
	}
	
	public Integer getSpecialSubmit() {
		return specialSubmit;
	}

	public void setSpecialSubmit(Integer specialSubmit) {
		this.specialSubmit = specialSubmit;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	
	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public String getBizTransDesc() {
		return bizTransDesc;
	}

	public void setBizTransDesc(String bizTransDesc) {
		this.bizTransDesc = bizTransDesc;
	}

	public Long getBizRefId() {
		return bizRefId;
	}

	public void setBizRefId(Long bizRefId) {
		this.bizRefId = bizRefId;
	}

	public Long getBizOperatorId() {
		return bizOperatorId;
	}

	public void setBizOperatorId(Long bizOperatorId) {
		this.bizOperatorId = bizOperatorId;
	}

	public Long getTransId() {
		return transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Integer getBizReversalFlag() {
		return bizReversalFlag;
	}

	public void setBizReversalFlag(Integer bizReversalFlag) {
		this.bizReversalFlag = bizReversalFlag;
	}
}