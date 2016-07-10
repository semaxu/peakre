package com.ebao.ap99.accounting.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

public class RevaluateInfoModel {
	
	private long revaluationId;
	
	private String revaluateValue ;
	
	private String FNYear;
	
	private String FNQuarter;
	
	private String currencyCode ;
	
	private Date executeDate;
	
	private Date startingData;
	
	private Date finishingData;
	
	private String operator;
	
	private String executeStatus;
	
	private List<RevaluateQuarterInfoModel> revaluateQuarterInfo;

	public List<RevaluateQuarterInfoModel> getRevaluateQuarterInfo() {
		return revaluateQuarterInfo;
	}

	public void setRevaluateQuarterInfo(List<RevaluateQuarterInfoModel> revaluateQuarterInfo) {
		this.revaluateQuarterInfo = revaluateQuarterInfo;
	}



	public String getFNQuarter() {
		return FNQuarter;
	}

	public void setFNQuarter(String fNQuarter) {
		FNQuarter = fNQuarter;
	}

	public long getRevaluationId() {
		return revaluationId;
	}

	public void setRevaluationId(long revaluationId) {
		this.revaluationId = revaluationId;
	}

	public String getRevaluateValue() {
		return revaluateValue;
	}

	public void setRevaluateValue(String revaluateValue) {
		this.revaluateValue = revaluateValue;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public String getFNYear() {
		return FNYear;
	}

	public void setFNYear(String fNYear) {
		FNYear = fNYear;
	}

	public Date getStartingData() {
		return startingData;
	}

	public void setStartingData(Date startingData) {
		this.startingData = startingData;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getFinishingData() {
		return finishingData;
	}

	public void setFinishingData(Date finishingData) {
		this.finishingData = finishingData;
	}

	public String getExecuteStatus() {
		return executeStatus;
	}

	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}
	
	
	
	

}
