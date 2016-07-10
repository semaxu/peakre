package com.ebao.ap99.arap.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.time.DateUtils;

import com.ebao.ap99.arap.constant.GlAccountType;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;


/**
 * The persistent class for the T_RI_GL_SUB_LEDGER database table.
 * 
 */
@Entity
@Table(name="T_RI_GL_SUB_LEDGER")
@NamedQuery(name="GLSubLedger.findAll", query="SELECT g FROM GLSubLedger g")
@NamedQueries({
	@NamedQuery(name = "GLSubLedger.findByMappingEntryId", query = " FROM GLSubLedger c WHERE c.glMappingEntryId = :glMappingEntryId ")
})
public class GLSubLedger extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name="SUB_LEDGER_ID")
	private Long subLedgerId;

	@Column(name="AMOUNT")
	private BigDecimal amount;

	@Column(name="NEED_POST")
	private Integer needPost;
	
	@Column(name="SETTLE_TYPE")
	private Integer settleType;
	
	@Column(name="SETTLE_ID")
	private Long settleId;

	@Column(name="CONTRACT_ID")
	private Long contractId;

	@Column(name="CURRENCY_CODE")
	private String currencyCode;

	@Column(name="BIZ_TRANS_TYPE")
	private Integer bizTransType;

	@Column(name="FEE_ID")
	private Long feeId;
	
	@Column(name="SUSPENSE_ID")
	private Long suspenseId;

	@Column(name="GENERAL_LEDGER_ID")
	private Long generalLedgerId;

	@Column(name="GL_ACCOUNT_NO")
	private String glAccountNo;

	@Column(name="GL_ACCOUNT_TYPE")
	private String glAccountType;

	@Column(name="GL_BATCH_ID")
	private Long glBatchId;

	@Column(name="MAIN_TRANSACTION")
	private String mainTransaction;

	@Column(name="SETTLE_DETAIL_ID")
	private Long settleDetailId;
	
	@Column(name="GL_MAPPING_ID")
	private Long glMappingId;
	
	@Column(name="GL_MAPPING_ENTRY_ID")
	private Long glMappingEntryId;

	@Column(name="SUB_TRANSACTION")
	private String subTransaction;
	
	@Column(name="PREDICTED_QUARTER")
	private String predictedQuarter;

	@Column(name="SPECIAL_SUBMIT")
	private Integer specialSubmit;
	
	@Column(name="SETTLE_SOURCE_TYPE")
	private Integer settleSourceType;
	
	@Column(name="DOC_DATE")
	private Date docDate;
	
	@Transient
	private Date postDate;
	
	@Transient
	private Integer postStatus;
	
	@Transient
	private String entryCode;
	
	@Transient
	private Date generationDate;
	
	@Transient
	private String bizTransNo;
	
	@Transient
	private String bizTransDesc;
	
	@Transient
	private Long bizOperatorId;
	
	@Transient
	private boolean isEstimation = false;
	
	@Transient
	private boolean isReversal = false;
	
	@Transient
	private boolean needCalcInGLCurrency = true;
	
	@Transient
	private String contractCode;
	
	@Transient
	private	List<GLExDetail> exDetailList;
	
	public GLSubLedger() {
	}
	
	public Long getSubLedgerId() {
		return subLedgerId;
	}

	public void setSubLedgerId(Long subLedgerId) {
		this.subLedgerId = subLedgerId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getNeedPost() {
		return needPost;
	}

	public void setNeedPost(Integer needPost) {
		this.needPost = needPost;
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

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getFeeId() {
		return feeId;
	}

	public void setFeeId(Long feeId) {
		this.feeId = feeId;
	}

	public Long getGeneralLedgerId() {
		return generalLedgerId;
	}

	public void setGeneralLedgerId(Long generalLedgerId) {
		this.generalLedgerId = generalLedgerId;
	}

	public String getGlAccountNo() {
		return glAccountNo;
	}

	public void setGlAccountNo(String glAccountNo) {
		this.glAccountNo = glAccountNo;
	}

	public String getGlAccountType() {
		return glAccountType;
	}

	public void setGlAccountType(String glAccountType) {
		this.glAccountType = glAccountType;
	}

	public Long getGlBatchId() {
		return glBatchId;
	}

	public void setGlBatchId(Long glBatchId) {
		this.glBatchId = glBatchId;
	}

	public String getMainTransaction() {
		return mainTransaction;
	}

	public void setMainTransaction(String mainTransaction) {
		this.mainTransaction = mainTransaction;
	}

	public Long getSettleDetailId() {
		return settleDetailId;
	}

	public void setSettleDetailId(Long settleDetailId) {
		this.settleDetailId = settleDetailId;
	}

	public String getSubTransaction() {
		return subTransaction;
	}

	public void setSubTransaction(String subTransaction) {
		this.subTransaction = subTransaction;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getSubLedgerId();
	}

	@Override
	public void setPrimaryKey(Long arg0) {
		this.setSubLedgerId(arg0);
	}

	public Long getSuspenseId() {
		return suspenseId;
	}

	public void setSuspenseId(Long suspenseId) {
		this.suspenseId = suspenseId;
	}
	
	public void negativeAccountType(){
		if(GlAccountType.DEBIT.getValue().equals(this.glAccountType)){
			this.setGlAccountType(GlAccountType.CREDIT.getValue());
		}else if(GlAccountType.CREDIT.getValue().equals(this.glAccountType)){
			this.setGlAccountType(GlAccountType.DEBIT.getValue());
		}
	}

	public Integer getSpecialSubmit() {
		return specialSubmit;
	}

	public void setSpecialSubmit(Integer specialSubmit) {
		this.specialSubmit = specialSubmit;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public Long getGlMappingId() {
		return glMappingId;
	}

	public void setGlMappingId(Long glMappingId) {
		this.glMappingId = glMappingId;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public Integer getPostStatus() {
		return postStatus;
	}

	public void setPostStatus(Integer postStatus) {
		this.postStatus = postStatus;
	}

	public String getEntryCode() {
		return entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	public Date getGenerationDate() {
		return generationDate;
	}

	public void setGenerationDate(Date generationDate) {
		this.generationDate = generationDate;
	}
	
	public Integer getBizTransType() {
		return bizTransType;
	}

	public void setBizTransType(Integer bizTransType) {
		this.bizTransType = bizTransType;
	}

	public Integer getSettleSourceType() {
		return settleSourceType;
	}

	public void setSettleSourceType(Integer settleSourceType) {
		this.settleSourceType = settleSourceType;
	}

	public String getBizTransNo() {
		return bizTransNo;
	}

	public void setBizTransNo(String bizTransNo) {
		this.bizTransNo = bizTransNo;
	}

	public String getBizTransDesc() {
		return bizTransDesc;
	}

	public void setBizTransDesc(String bizTransDesc) {
		this.bizTransDesc = bizTransDesc;
	}

	public Long getBizOperatorId() {
		return bizOperatorId;
	}

	public void setBizOperatorId(Long bizOperatorId) {
		this.bizOperatorId = bizOperatorId;
	}

	public boolean isEstimation() {
		return isEstimation;
	}

	public void setEstimation(boolean isEstimation) {
		this.isEstimation = isEstimation;
	}

	public boolean isReversal() {
		return isReversal;
	}

	public void setReversal(boolean isReversal) {
		this.isReversal = isReversal;
	}

	public Long getGlMappingEntryId() {
		return glMappingEntryId;
	}

	public void setGlMappingEntryId(Long glMappingEntryId) {
		this.glMappingEntryId = glMappingEntryId;
	}
	
	public String getGLAccountGroupingKey(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMdd");
		if(this.getDocDate() == null){
			this.setDocDate(DateUtils.truncate(AppContext.getSysDate(),Calendar.DATE));
		}
		return String.join("-", this.getGlAccountNo(), this.getGlAccountType(), this.getCurrencyCode()
				, this.getMainTransaction(), this.getSubTransaction(),simpleDateFormat.format(this.getDocDate()));
	}

	public boolean isNeedCalcInGLCurrency() {
		return needCalcInGLCurrency;
	}

	public void setNeedCalcInGLCurrency(boolean needCalcInGLCurrency) {
		this.needCalcInGLCurrency = needCalcInGLCurrency;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getPredictedQuarter() {
		return predictedQuarter;
	}

	public void setPredictedQuarter(String predictedQuarter) {
		this.predictedQuarter = predictedQuarter;
	}

	public List<GLExDetail> getExDetailList() {
		return exDetailList;
	}

	public void setExDetailList(List<GLExDetail> exDetailList) {
		this.exDetailList = exDetailList;
	}
}