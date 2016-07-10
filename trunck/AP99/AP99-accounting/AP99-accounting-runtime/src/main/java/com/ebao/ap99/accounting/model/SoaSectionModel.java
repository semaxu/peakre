package com.ebao.ap99.accounting.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.ebao.ap99.accounting.entity.TRiSoa;
import com.ebao.ap99.accounting.entity.TRiSoaCurrency;
import com.ebao.ap99.parent.DataTypeConvertor;
import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlAccessorType(XmlAccessType.FIELD)
public class SoaSectionModel {

	private String soaSectionId;
	//@JsonIgnore
	private String cob;

	private String contracCompId;

	private BigDecimal expense = BigDecimal.ZERO;

	private BigDecimal incurredLoss = BigDecimal.ZERO;

	private BigDecimal premium = BigDecimal.ZERO;
	
	private BigDecimal cashBalance = BigDecimal.ZERO;
	
	private BigDecimal sectionTotal = BigDecimal.ZERO;

	private String shareType;
	//@JsonIgnore
	private String uwArea;

	private TRiSoa TRiSoa;

	private TRiSoaCurrency TRiSoaCurrency;
	@XmlElementWrapper
	@XmlElement(name = "EntryItemInfo")
	private List<SoaSectionItemModel> entryItems = new ArrayList<SoaSectionItemModel>();

	private String soaCurrencyId;

	private String contractSection;
	
	private Boolean isFullyTransfer;

	//private List<String> coBList = new ArrayList<String>();
	
	//private List<String> uwAreaList = new ArrayList<String>();

//	public List<String> getCoBList() {
//		return coBList;
//	}
//	
//	public List<String> getUwAreaList() {
//		return uwAreaList;
//	}

//	public void setUwAreaList(List<String> uwAreaList) {
//		this.uwArea = DataTypeConvertor.parseSelectTreeToString(uwAreaList);
//		this.uwAreaList = uwAreaList;
//	}
//	
//	public void setCoBList(List<String> coBList) {
//		this.cob = DataTypeConvertor.parseSelectTreeToString(coBList);
//		this.coBList = coBList;
//	}

	public SoaSectionModel() {
		// TODO Auto-generated constructor stub
	}

	public String getSoaCurrencyId() {
		return soaCurrencyId;
	}

	public void setSoaCurrencyId(String soaCurrencyId) {
		this.soaCurrencyId = soaCurrencyId;
	}

	public String getSoaSectionId() {
		return soaSectionId;
	}

	public void setSoaSectionId(String soaSectionId) {
		this.soaSectionId = soaSectionId;
	}

	public BigDecimal getExpense() {
		return this.expense;
	}

	public void setExpense(BigDecimal expense) {
		this.expense = expense;
	}

	public BigDecimal getIncurredLoss() {
		return this.incurredLoss;
	}

	public void setIncurredLoss(BigDecimal incurredLoss) {
		this.incurredLoss = incurredLoss;
	}
	
	

	public BigDecimal getPremium() {

		return this.premium;
	}

	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}

	public TRiSoa getTRiSoa() {
		return TRiSoa;
	}

	public void setTRiSoa(TRiSoa tRiSoa) {
		TRiSoa = tRiSoa;
	}

	public TRiSoaCurrency getTRiSoaCurrency() {
		return TRiSoaCurrency;
	}

	public void setTRiSoaCurrency(TRiSoaCurrency tRiSoaCurrency) {
		TRiSoaCurrency = tRiSoaCurrency;
	}

	public List<SoaSectionItemModel> getEntryItems() {
		return this.entryItems;
	}

	public void setEntryItems(List<SoaSectionItemModel> entryItems) {
		this.entryItems = entryItems;
	}

	public String getContractSection() {
		return contractSection;
	}

	public void setContractSection(String contractSection) {
		this.contractSection = contractSection;
	}

	public String getCob() {
		return cob;
	}

	public void setCob(String cob) {
	//	this.coBList = DataTypeConvertor.transStringToSelectTree(cob);
		this.cob = cob;
	}

	public String getContracCompId() {
		return contracCompId;
	}

	public void setContracCompId(String contracCompId) {
		this.contracCompId = contracCompId;
	}

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public String getUwArea() {
		return uwArea;
	}

	public void setUwArea(String uwArea) {
	//	this.uwAreaList = DataTypeConvertor.transStringToSelectTree(uwArea);
		this.uwArea = uwArea;
	}

	public BigDecimal getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(BigDecimal cashBalance) {
		this.cashBalance = cashBalance;
	}

	public BigDecimal getSectionTotal() {
		return sectionTotal;
	}

	public void setSectionTotal(BigDecimal sectionTotal) {
		this.sectionTotal = sectionTotal;
	}

	public Boolean getIsFullyTransfer() {
		return isFullyTransfer;
	}

	public void setIsFullyTransfer(Boolean isFullyTransfer) {
		this.isFullyTransfer = isFullyTransfer;
	}
	
	

}
