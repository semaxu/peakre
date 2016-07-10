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
public class SettlementHistoryQuery {
	private String settlementType ;
	private String contractCode ;
	private long underwritingYear;
	private long contractSection;
	private long refId;
	private String businessDirection;
	private double usdSummary;
	
	private List<SettlementHistoryInfo> settlementItemInfo;
	private List<SettlementHistoryInfo> settlementItemSummary;
	private List<TreeModel> UWYearList;
	private List<TreeModel> contractSectionList;
	private List<SettlementSummaryInfo> settlementSummary;
	public String getSettlementType() {
		return settlementType;
	}
	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
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
	public double getUsdSummary() {
		return usdSummary;
	}
	public void setUsdSummary(double usdSummary) {
		this.usdSummary = usdSummary;
	}
	public List<SettlementHistoryInfo> getSettlementItemInfo() {
		return settlementItemInfo;
	}
	public void setSettlementItemInfo(List<SettlementHistoryInfo> settlementItemInfo) {
		this.settlementItemInfo = settlementItemInfo;
	}
	public List<SettlementHistoryInfo> getSettlementItemSummary() {
		return settlementItemSummary;
	}
	public void setSettlementItemSummary(List<SettlementHistoryInfo> settlementItemSummary) {
		this.settlementItemSummary = settlementItemSummary;
	}
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
	public List<SettlementSummaryInfo> getSettlementSummary() {
		return settlementSummary;
	}
	public void setSettlementSummary(List<SettlementSummaryInfo> settlementSummary) {
		this.settlementSummary = settlementSummary;
	}
	
	
}
