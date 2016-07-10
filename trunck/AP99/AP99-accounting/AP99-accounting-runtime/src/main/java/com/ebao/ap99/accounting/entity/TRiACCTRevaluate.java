package com.ebao.ap99.accounting.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the T_RI_REVALUATE database table.
 * 
 */
@Entity
@Table(name="T_RI_ACCT_REVALUATE")
@NamedQueries({
	@NamedQuery(name = "TRiACCTRevaluate.findByFNYearAndFNQuarter", query = "FROM TRiACCTRevaluate s WHERE s.fnQuarter = :fnQuarter ")
 })
public class TRiACCTRevaluate extends com.ebao.ap99.parent.BaseEntityImpl implements Serializable  {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name="REVALUATION_ID")
	private Long revaluationId;

	@Column(name="FN_QUARTER")
	private String fnQuarter;


	@Temporal(TemporalType.DATE)
	@Column(name="POST_DATA")
	private Date postData;

	private String status;
	
	@Column(name="STARTING_TIME")
	private Date startingData;
	
	
	@Column(name="FINISHING_TIME")
	private Date finishingData;
	
	@Column(name="OPERATOR")
	private String operator;
	
	
	@Column(name="OPERATOR_ID")
	private String operatorID;
	
	@Column(name="EXECUTE_STATUS")
	private String executeStatus;
	


	

	//bi-directional many-to-one association to TRiRevaluateDetail
	@OneToMany(mappedBy="TRiACCTRevaluate")
	private List<TRiACCTRevaluateDetail> TRiACCTRevaluateDetails;

	public TRiACCTRevaluate() {
	}

	public Long getRevaluationId() {
		return this.revaluationId;
	}

	public void setRevaluationId(Long revaluationId) {
		this.revaluationId = revaluationId;
	}

	public String getFnQuarter() {
		return this.fnQuarter;
	}

	public void setFnQuarter(String fnQuarter) {
		this.fnQuarter = fnQuarter;
	}

	public Date getPostData() {
		return this.postData;
	}

	public void setPostData(Date postData) {
		this.postData = postData;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<TRiACCTRevaluateDetail> getTRiACCTRevaluateDetails() {
		return this.TRiACCTRevaluateDetails;
	}

	public void setTRiACCTRevaluateDetails(List<TRiACCTRevaluateDetail> TRiACCTRevaluateDetails) {
		this.TRiACCTRevaluateDetails = TRiACCTRevaluateDetails;
	}

	public TRiACCTRevaluateDetail addTRiACCTRevaluateDetail(TRiACCTRevaluateDetail TRiACCTRevaluateDetail) {
		getTRiACCTRevaluateDetails().add(TRiACCTRevaluateDetail);
		TRiACCTRevaluateDetail.setTRiACCTRevaluate(this);

		return TRiACCTRevaluateDetail;
	}

	public TRiACCTRevaluateDetail removeTRiACCTRevaluateDetail(TRiACCTRevaluateDetail TRiACCTRevaluateDetail) {
		getTRiACCTRevaluateDetails().remove(TRiACCTRevaluateDetail);
		TRiACCTRevaluateDetail.setTRiACCTRevaluate(null);

		return TRiACCTRevaluateDetail;
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

	public Date getStartingData() {
		return startingData;
	}

	public void setStartingData(Date startingData) {
		this.startingData = startingData;
	}

	public Date getFinishingData() {
		return finishingData;
	}

	public void setFinishingData(Date finishingData) {
		this.finishingData = finishingData;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorID() {
		return operatorID;
	}

	public void setOperatorID(String operatorID) {
		this.operatorID = operatorID;
	}

	public String getExecuteStatus() {
		return executeStatus;
	}

	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}




	
	
	

}