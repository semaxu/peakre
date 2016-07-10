package com.ebao.ap99.arap.entity;

import java.math.BigDecimal;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ebao.ap99.arap.constant.PostStatus;
import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;


/**
 * The persistent class for the T_RI_GL_GENERAL_LEDGER database table.
 * 
 */
@Entity
@Table(name="T_RI_GL_GENERAL_LEDGER")
@NamedQuery(name="GLGeneralLedger.findAll", query="SELECT g FROM GLGeneralLedger g")
@NamedQueries({
	@NamedQuery(name = "GLGeneralLedger.findPostPendingDataByPostDate", query = " FROM GLGeneralLedger c WHERE c.postDate = :postDate and c.postStatus != :postStatus")
})
public class GLGeneralLedger extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name="GENERAL_LEDGER_ID")
	private Long generalLedgerId;

	@Column(name="AMOUNT")
	private BigDecimal amount;

	@Column(name="CURRENCY_CODE")
	private String currencyCode;

	@Column(name="DATA_SOURCE_TYPE")
	private Integer dataSourceType;

	@Column(name="EXCHANGE_RATE")
	private BigDecimal exchangeRate;

	@Column(name="GL_ACCOUNT_NO")
	private String glAccountNo;

	@Column(name="GL_ACCOUNT_TYPE")
	private String glAccountType;

	@Column(name="GL_BATCH_ID")
	private Long glBatchId;

	@Column(name="MAIN_TRANSACTION")
	private String mainTransaction;

	@Temporal(TemporalType.DATE)
	@Column(name="POST_DATE")
	private Date postDate;

	@Column(name="POST_STATUS")
	private Integer postStatus = PostStatus.WAIT_FOR_POST.getType();

	@Column(name="REF_GL_ID")
	private Long refGlId;

	@Column(name="SUB_TRANSACTION")
	private String subTransaction;
	
	@Column(name="DOC_DATE")
	private Date docDate;
	
	@Column(name="AMOUNT_DOC_CURRENCY")
	private BigDecimal amountDocCurrency;
	
	@Column(name="DOC_CURRENCY")
	private BigDecimal docCurrency;
	
	@Column(name="ASSIGNMENT")
	private BigDecimal assignment;
	
	@Column(name="VALUE_DATE")
	private Date valueDate;
	
	@Column(name="TEXT_DESC")
	private String textDesc;
	
	@Column(name="REFERENCE")
	private String reference;
	
	@Column(name="FIN_QUARTER")
	private String finQuarter;
	
	@Transient
	private List<GLSubLedger> subLedgerList;
	
	@Transient
	private	List<GLExDetail> exDetailList;
	
	public GLGeneralLedger() {
	}

	public Long getGeneralLedgerId() {
		return this.generalLedgerId;
	}

	public void setGeneralLedgerId(Long generalLedgerId) {
		this.generalLedgerId = generalLedgerId;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getExchangeRate() {
		return this.exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getGlAccountNo() {
		return this.glAccountNo;
	}

	public void setGlAccountNo(String glAccountNo) {
		this.glAccountNo = glAccountNo;
	}

	public String getGlAccountType() {
		return this.glAccountType;
	}

	public void setGlAccountType(String glAccountType) {
		this.glAccountType = glAccountType;
	}

	public String getMainTransaction() {
		return this.mainTransaction;
	}

	public void setMainTransaction(String mainTransaction) {
		this.mainTransaction = mainTransaction;
	}

	public Date getPostDate() {
		return this.postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public String getSubTransaction() {
		return this.subTransaction;
	}

	public void setSubTransaction(String subTransaction) {
		this.subTransaction = subTransaction;
	}

	@Override
	public Long getPrimaryKey() {
		return getGeneralLedgerId();
	}

	@Override
	public void setPrimaryKey(Long arg0) {
		setGeneralLedgerId(arg0);
	}

	public Integer getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(Integer dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	public Long getGlBatchId() {
		return glBatchId;
	}

	public void setGlBatchId(Long glBatchId) {
		this.glBatchId = glBatchId;
	}

	public Integer getPostStatus() {
		return postStatus;
	}

	public void setPostStatus(Integer postStatus) {
		this.postStatus = postStatus;
	}

	public Long getRefGlId() {
		return refGlId;
	}

	public void setRefGlId(Long refGlId) {
		this.refGlId = refGlId;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public BigDecimal getAmountDocCurrency() {
		return amountDocCurrency;
	}

	public void setAmountDocCurrency(BigDecimal amountDocCurrency) {
		this.amountDocCurrency = amountDocCurrency;
	}

	public BigDecimal getDocCurrency() {
		return docCurrency;
	}

	public void setDocCurrency(BigDecimal docCurrency) {
		this.docCurrency = docCurrency;
	}

	public BigDecimal getAssignment() {
		return assignment;
	}

	public void setAssignment(BigDecimal assignment) {
		this.assignment = assignment;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public String getTextDesc() {
		return textDesc;
	}

	public void setTextDesc(String textDesc) {
		this.textDesc = textDesc;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getFinQuarter() {
		return finQuarter;
	}

	public void setFinQuarter(String finQuarter) {
		this.finQuarter = finQuarter;
	}

	public List<GLSubLedger> getSubLedgerList() {
		return subLedgerList;
	}

	public void setSubLedgerList(List<GLSubLedger> subLedgerList) {
		this.subLedgerList = subLedgerList;
	}
	
	public String getUniqueKey(){
		StringBuffer key = new StringBuffer();
		key.append(this.getGlAccountNo());
		key.append("_").append(this.getCurrencyCode());
		key.append("_").append(this.getGlAccountType());
		key.append("_").append(this.getSubTransaction());
		key.append("_").append(this.getMainTransaction());
		key.append("_").append(this.getGlBatchId());
		return key.toString();
	}

	public List<GLExDetail> getExDetailList() {
		return exDetailList;
	}

	public void setExDetailList(List<GLExDetail> exDetailList) {
		this.exDetailList = exDetailList;
	}
}