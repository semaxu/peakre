package com.ebao.ap99.arap.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ebao.ap99.arap.constant.FinanceTransactionType;
import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;

/**
 * The persistent class for the T_RI_BCP_SETTLEMENT_DETAIL database table.
 * 
 */
@Entity
@Table(name = "T_RI_BCP_SETTLEMENT_DETAIL")
@NamedQuery(name = "SettlementDetail.findAll", query = "SELECT s FROM SettlementDetail s")
@NamedQueries({
	@NamedQuery(name = "SettlementDetail.findByCollectionId", query = " FROM SettlementDetail c WHERE c.collectionId = :collectionId"),
	@NamedQuery(name = "SettlementDetail.findByPaymentId", query = " FROM SettlementDetail c WHERE c.paymentId = :paymentId"),
	@NamedQuery(name = "SettlementDetail.findByOffsetId", query = " FROM SettlementDetail c WHERE c.offsetId = :offsetId")
})
public class SettlementDetail extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "SETTLE_DETAIL_ID")
	private Long settleDetailId;

	@Column(name = "OUTSTANDING_AMOUNT")
	private BigDecimal outstandingAmount;
	
	@Column(name = "MARK_OFF_AMOUNT")
	private BigDecimal markOffAmount;

	@Column(name = "COLLECTION_ID")
	private Long collectionId;

	@Column(name = "SETTLE_CURRENCY_CODE")
	private String settleCurrencyCode;

	@Column(name = "SETTLE_AMOUNT")
	private BigDecimal settleAmount;
	
	@Column(name = "CURRENCY_CODE")
	private String currencyCode;
	
	@Column(name = "FEE_ID")
	private Long feeId;

	@Column(name = "GAIN_LOSS")
	private BigDecimal gainLoss;

	@Column(name = "GAIN_LOSS_DIFF")
	private BigDecimal gainLossDiff;
	
	@Column(name = "EXCHANGE_RATE")
	private BigDecimal exchangeRate;
	
	@Column(name = "NEED_POST")
	private Integer needPost;

	@Column(name = "OFFSET_ID")
	private Long offsetId;

	@Column(name = "PAYMENT_ID")
	private Long paymentId;

	@Column(name = "POST_STATUS")
	private Integer postStatus;
	
	@Column(name = "FULLY_SETTLE")
	private Integer fullySettle;

	@Column(name = "REVERSE_ID")
	private Long reverseId;

	@Column(name = "AMOUNT_DIFF")
	private BigDecimal amountDiff;

	@Column(name = "SETTLE_GROUP_ID")
	private Long settleGroupId;

	@Column(name = "SUSPENSE_ID")
	private Long suspenseId;
	
	@Column(name = "SETTLE_DATE")
	private Date settleDate;
	
	@Column(name = "POST_DATE")
	private Date postDate;
	
	@Transient
	private Long contractId;
	
	@Transient
	private String bizTransNo;

	@Transient
	private Integer bizTransType;
	
	@Transient
	private String entryCode;
	
	@Transient
	private BigDecimal amount;// original amount of fee
	
	@Transient
	private Date dueDate;// due date of fee
	
	@Transient
	private BigDecimal balance;
	
	@Transient
	private String contractCode;
	
	@Transient
	private Long transId;
	
	@Transient
	private Integer sign;
	
	public SettlementDetail() {
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getGainLoss() {
		return this.gainLoss;
	}

	public void setGainLoss(BigDecimal gainLoss) {
		this.gainLoss = gainLoss;
	}

	public BigDecimal getGainLossDiff() {
		return this.gainLossDiff;
	}

	public void setGainLossDiff(BigDecimal gainLossDiff) {
		this.gainLossDiff = gainLossDiff;
	}
	
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(BigDecimal outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public BigDecimal getMarkOffAmount() {
		return markOffAmount;
	}

	public void setMarkOffAmount(BigDecimal markOffAmount) {
		this.markOffAmount = markOffAmount;
	}

	public Long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}

	public String getSettleCurrencyCode() {
		return settleCurrencyCode;
	}

	public void setSettleCurrencyCode(String settleCurrencyCode) {
		this.settleCurrencyCode = settleCurrencyCode;
	}

	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}

	public BigDecimal getAmountDiff() {
		return amountDiff;
	}

	public void setAmountDiff(BigDecimal amountDiff) {
		this.amountDiff = amountDiff;
	}

	public Long getSettleDetailId() {
		return settleDetailId;
	}

	public void setSettleDetailId(Long settleDetailId) {
		this.settleDetailId = settleDetailId;
	}

	public Long getFeeId() {
		return feeId;
	}

	public void setFeeId(Long feeId) {
		this.feeId = feeId;
	}

	public Integer getNeedPost() {
		return needPost;
	}

	public void setNeedPost(Integer needPost) {
		this.needPost = needPost;
	}

	public Long getOffsetId() {
		return offsetId;
	}

	public void setOffsetId(Long offsetId) {
		this.offsetId = offsetId;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Integer getPostStatus() {
		return postStatus;
	}

	public void setPostStatus(Integer postStatus) {
		this.postStatus = postStatus;
	}

	public Long getReverseId() {
		return reverseId;
	}

	public void setReverseId(Long reverseId) {
		this.reverseId = reverseId;
	}

	public Long getSettleGroupId() {
		return settleGroupId;
	}

	public void setSettleGroupId(Long settleGroupId) {
		this.settleGroupId = settleGroupId;
	}

	public Long getSuspenseId() {
		return suspenseId;
	}

	public void setSuspenseId(Long suspenseId) {
		this.suspenseId = suspenseId;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getSettleDetailId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setSettleDetailId(key);
	}

	public Integer getFullySettle() {
		return fullySettle;
	}

	public void setFullySettle(Integer fullySettle) {
		this.fullySettle = fullySettle;
	}
	@Transient
	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	@Transient
	public String getBizTransNo() {
		return bizTransNo;
	}

	public void setBizTransNo(String bizTransNo) {
		this.bizTransNo = bizTransNo;
	}
	
	@Transient
	public Integer getBizTransType() {
		return bizTransType;
	}

	public void setBizTransType(Integer bizTransType) {
		this.bizTransType = bizTransType;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	@Transient
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Transient
	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	@Transient
	public String getEntryCode() {
		return entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public Integer getSettleTransType(){
		if(this.getCollectionId() != null){
			return FinanceTransactionType.COLLECTION.getType();
		}
		if(this.getPaymentId() != null){
			return FinanceTransactionType.PAYMENT.getType();
		}
		if(this.getOffsetId() != null){
			return FinanceTransactionType.INTERNAL_OFFSET.getType();
		}
		if(this.getReverseId() != null){
			return FinanceTransactionType.REVERSAL.getType();
		}
		return null;
	}

	public Date getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Long getTransId() {
		return transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}
	
}