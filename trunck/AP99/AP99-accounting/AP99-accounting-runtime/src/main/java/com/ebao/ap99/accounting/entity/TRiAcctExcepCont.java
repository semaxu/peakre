package com.ebao.ap99.accounting.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the T_RI_ACCT_EXCEP_CONT database table.
 * 
 */
@Entity
@Table(name="T_RI_ACCT_EXCEP_CONT")
@NamedQuery(name="TRiAcctExcepCont.findAll", query="SELECT t FROM TRiAcctExcepCont t")
public class TRiAcctExcepCont  extends com.ebao.ap99.parent.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name="EXCEPTION_ID")
	private long exceptionId;

	@Column(name="JOB_ID")
	private Long jobId;

	private String status;


	//bi-directional many-to-one association to TRiAcctExcepCont
	@OneToMany(mappedBy="TRiAcctExcepCont", cascade = { CascadeType.ALL })
	private List<TRiAcctExcepContDetail> TRiAcctExcepContDetails = new ArrayList<TRiAcctExcepContDetail>();

	public TRiAcctExcepCont() {
	}

	public long getExceptionId() {
		return this.exceptionId;
	}

	public void setExceptionId(long exceptionId) {
		this.exceptionId = exceptionId;
	}
	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<TRiAcctExcepContDetail> getTRiAcctExcepContDetails() {
		return this.TRiAcctExcepContDetails;
	}

	public void setTRiAcctExcepContDetails(List<TRiAcctExcepContDetail> TRiAcctExcepContDetails) {
		this.TRiAcctExcepContDetails = TRiAcctExcepContDetails;
	}

	public TRiAcctExcepContDetail addTRiAcctExcepContDetail(TRiAcctExcepContDetail TRiAcctExcepContDetail) {
		getTRiAcctExcepContDetails().add(TRiAcctExcepContDetail);
		TRiAcctExcepContDetail.setTRiAcctExcepCont(this);

		return TRiAcctExcepContDetail;
	}

	public TRiAcctExcepContDetail removeTRiAcctExcepContDetail(TRiAcctExcepContDetail TRiAcctExcepContDetail) {
		getTRiAcctExcepContDetails().remove(TRiAcctExcepContDetail);
		TRiAcctExcepContDetail.setTRiAcctExcepCont(null);

		return TRiAcctExcepContDetail;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
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
}