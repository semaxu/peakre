package com.ebao.ap99.accounting.UI.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.parent.DataTypeConvertor;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class SoaSection4UpdateVO {
	
	    private String contractSection;
	    
	  //  @JsonIgnore
		private String cob;

		private String shareType;
	//	 @JsonIgnore
		private String uwArea;

		private List<SoaSectionItem4UpdateVO> entryItems = new ArrayList<SoaSectionItem4UpdateVO>();
		
		
		private String soaSectionId;


		private BigDecimal expense;
		
		private BigDecimal premium;
		
		private BigDecimal incurredLoss;
		
		private BigDecimal  cashBalance;
		
		private BigDecimal sectionTotal ;

		
		private String soaCurrencyId;
		
		private String contracCompId;
		
		private Boolean isFullyTransfer;
		
	//	private List<String> coBList = new ArrayList<String>();
		
	//	private List<String> uwAreaList = new ArrayList<String>();
		
//		public List<String> getUwAreaList() {
//			return uwAreaList;
//		}
//
//		public void setUwAreaList(List<String> uwAreaList) {
//			this.uwArea = DataTypeConvertor.parseSelectTreeToString(uwAreaList);
//			this.uwAreaList = uwAreaList;
//		}
//		
//		public List<String> getCoBList() {
//			return coBList;
//		}
//
//		public void setCoBList(List<String> coBList) {
//			this.cob = DataTypeConvertor.parseSelectTreeToString(coBList);
//			this.coBList = coBList;
//		}
		


		
		public String getContractSection() {
			return contractSection;
		}


		public void setContractSection(String contractSection) {
			this.contractSection = contractSection;
		}

		public List<SoaSectionItem4UpdateVO> getEntryItems() {
			return this.entryItems;
		}


		public void setEntryItems(List<SoaSectionItem4UpdateVO> entryItems) {
			this.entryItems = entryItems;
		}


		public String getSoaSectionId() {
			return soaSectionId;
		}


		public void setSoaSectionId(String soaSectionId) {
			this.soaSectionId = soaSectionId;
		}


		public BigDecimal getExpense() {
			return expense;
		}


		public void setExpense(BigDecimal expense) {
			this.expense = expense;
		}
		
		


		public String getSoaCurrencyId() {
			return soaCurrencyId;
		}


		public void setSoaCurrencyId(String soaCurrencyId) {
			this.soaCurrencyId = soaCurrencyId;
		}


		public BigDecimal getPremium() {
			return premium;
		}


		public void setPremium(BigDecimal premium) {
			this.premium = premium;
		}


		public BigDecimal getIncurredLoss() {
			return incurredLoss;
		}


		public void setIncurredLoss(BigDecimal incurredLoss) {
			this.incurredLoss = incurredLoss;
		}


		public String getCob() {
			return cob;
		}

		public void setCob(String cob) {
		//	this.coBList = DataTypeConvertor.transStringToSelectTree(cob);
			this.cob = cob;
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
			this.uwArea = uwArea;
		}


		public String getContracCompId() {
			return contracCompId;
		}


		public void setContracCompId(String contracCompId) {
			this.contracCompId = contracCompId;
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
