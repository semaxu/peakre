/**
 * 
 */
package com.ebao.ap99.claim.model;

import java.util.List;

import com.ebao.ap99.parent.model.TreeModel;

/**
 * @author yujie.zhang
 *
 */
public class ReserveHistoryQuery {
	
	private String reserveType ;
	private String contractCode ;
	private long underwritingYear;
	private long contractSection;
	private long refId;
	private String businessDirection;
	private double usdSummary;
	
	private List<ReserveHistoryInfo> reserveHistoryInfo;
	private List<ReserveHistoryInfo> reserveInfo;
	private List<TreeModel> UWYearList;
	private List<TreeModel> contractSectionList;
	private List<ReserveSummaryInfo> reserveSummary;
	
	public List<TreeModel> getUWYearList() {
		return UWYearList;
	}

	public void setUWYearList(List<TreeModel> uWYearList) {
		UWYearList = uWYearList;
	}

	public List<TreeModel> getContractSectionList() {
		return contractSectionList;
	}

	public void setContractSectionList(List<TreeModel> contractSectionList) {
		this.contractSectionList = contractSectionList;
	}

	public String getReserveType() {
		return reserveType;
	}

	public void setReserveType(String reserveType) {
		this.reserveType = reserveType;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public long getUnderwritingYear() {
		return underwritingYear;
	}

	public void setUnderwritingYear(long underwritingYear) {
		this.underwritingYear = underwritingYear;
	}

	public long getContractSection() {
		return contractSection;
	}

	public void setContractSection(long contractSection) {
		this.contractSection = contractSection;
	}

	public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
	}

	public String getBusinessDirection() {
		return businessDirection;
	}

	public void setBusinessDirection(String businessDirection) {
		this.businessDirection = businessDirection;
	}

	public List<ReserveHistoryInfo> getReserveHistoryInfo() {
		return reserveHistoryInfo;
	}

	public void setReserveHistoryInfo(List<ReserveHistoryInfo> reserveHistoryInfo) {
		this.reserveHistoryInfo = reserveHistoryInfo;
	}

	public List<ReserveHistoryInfo> getReserveInfo() {
		return reserveInfo;
	}

	public void setReserveInfo(List<ReserveHistoryInfo> reserveInfo) {
		this.reserveInfo = reserveInfo;
	}

	public List<ReserveSummaryInfo> getReserveSummary() {
		return reserveSummary;
	}

	public void setReserveSummary(List<ReserveSummaryInfo> reserveSummary) {
		this.reserveSummary = reserveSummary;
	}

	public double getUsdSummary() {
		return usdSummary;
	}

	public void setUsdSummary(double usdSummary) {
		this.usdSummary = usdSummary;
	}

	
}
