package com.ebao.ap99.accounting.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * The persistent class for the T_RI_ACCT_EXCEP_CONT_DETAIL database table.
 * 
 */
@Entity
@Table(name="T_RI_ACCT_EXCEP_CONT_DETAIL")
@NamedQueries({
	@NamedQuery(name = "TRiAcctExcepContDetail.findByFNYearAndFNQuarter", query = "FROM TRiAcctExcepContDetail s WHERE s.fnYear = :fnYear and s.fnQuarter = :fnQuarter ")
 })

@XmlAccessorType(XmlAccessType.FIELD)
public class TRiAcctExcepContDetail extends com.ebao.ap99.parent.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name="EXCEPTION_DETAIL_ID")
	private long exceptionDetailId;

	@Column(name="CONT_COMP_ID")
	private long contCompId;

	@Column(name="CONTRACT_ID")
	private String contractCode;

	@Column(name="FN_QUARTER")
	private String fnQuarter;

	@Column(name="FN_YEAR")
	private Long fnYear;

	@Column(name="REVIEWED_FLAG")
	private String reviewedFlag;

	private String status;


	//bi-directional many-to-one association to TRiAcctExcepCont
	@ManyToOne
	@JoinColumn(name="EXCEPTION_ID")
	private TRiAcctExcepCont TRiAcctExcepCont;

	public TRiAcctExcepContDetail() {
	}

	public long getExceptionDetailId() {
		return this.exceptionDetailId;
	}

	public void setExceptionDetailId(long exceptionDetailId) {
		this.exceptionDetailId = exceptionDetailId;
	}

	public String getContractCode() {
		return this.contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getFnQuarter() {
		return this.fnQuarter;
	}

	public void setFnQuarter(String fnQuarter) {
		this.fnQuarter = fnQuarter;
	}

	public String getReviewedFlag() {
		return this.reviewedFlag;
	}

	public void setReviewedFlag(String reviewedFlag) {
		this.reviewedFlag = reviewedFlag;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public long getContCompId() {
		return contCompId;
	}

	public void setContCompId(long contCompId) {
		this.contCompId = contCompId;
	}



	public TRiAcctExcepCont getTRiAcctExcepCont() {
		return TRiAcctExcepCont;
	}

	public void setTRiAcctExcepCont(TRiAcctExcepCont tRiAcctExcepCont) {
		TRiAcctExcepCont = tRiAcctExcepCont;
	}

	public Long getFnYear() {
		return fnYear;
	}

	public void setFnYear(Long fnYear) {
		this.fnYear = fnYear;
	}
}