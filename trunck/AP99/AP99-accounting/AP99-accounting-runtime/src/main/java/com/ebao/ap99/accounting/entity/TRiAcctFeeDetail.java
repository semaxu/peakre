package com.ebao.ap99.accounting.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the T_RI_ACCT_FEE_DETAIL database table.
 * 
 */
@Entity
@Table(name="T_RI_ACCT_FEE_DETAIL")
@NamedQuery(name="TRiAcctFeeDetail.findAll", query="SELECT t FROM TRiAcctFeeDetail t")
public class TRiAcctFeeDetail extends com.ebao.ap99.parent.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name="DETAIL_ID")
	private Long detailId;

	private BigDecimal amount;

	@Column(name="BUSI_ID")
	private Long busiId;

	@Column(name="CONT_COMP_ID")
	private Long contCompId;

	private String currency;

	@Column(name="ENTRY_CODE")
	private String entryCode;


	@Temporal(TemporalType.DATE)
	@Column(name="POSTING_DATE")
	private Date postingDate;

	//bi-directional many-to-one association to TRiAcctFee
	@ManyToOne
	@JoinColumn(name="FEE_ID")
	private TRiAcctFee TRiAcctFee;

	public TRiAcctFeeDetail() {
		
	}
	
    public TRiAcctFeeDetail(TRiAcctFee tRiAcctFee) {
        TRiAcctFee = tRiAcctFee;
        tRiAcctFee.getTRiAcctFeeDetails().add(this);
    }

	public Long getDetailId() {
		return this.detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
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

	public String getEntryCode() {
		return this.entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}



	public Date getPostingDate() {
		return this.postingDate;
	}

	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}



	public TRiAcctFee getTRiAcctFee() {
		return this.TRiAcctFee;
	}

	public void setTRiAcctFee(TRiAcctFee TRiAcctFee) {
		this.TRiAcctFee = TRiAcctFee;
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

	public Long getBusiId() {
		return busiId;
	}

	public void setBusiId(Long busiId) {
		this.busiId = busiId;
	}

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

}