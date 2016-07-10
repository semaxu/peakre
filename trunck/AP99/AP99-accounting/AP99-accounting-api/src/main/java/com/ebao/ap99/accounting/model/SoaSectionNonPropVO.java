package com.ebao.ap99.accounting.model;

import java.util.ArrayList;
import java.util.List;



public class SoaSectionNonPropVO {
	
	    private String contractSection;//contract section name
	    
		private Long contCompId;//contract section id
	    
		private String cob;

		private String shareType;
		
		private String uwArea;

		private List<SoaSectionItemNonPropVO> entryItems = new ArrayList<SoaSectionItemNonPropVO>();	
		


	
		public String getContractSection() {
			return contractSection;
		}


		public void setContractSection(String contractSection) {
			this.contractSection = contractSection;
		}

		public List<SoaSectionItemNonPropVO> getEntryItems() {
			return this.entryItems;
		}


		public void setEntryItems(List<SoaSectionItemNonPropVO> entryItems) {
			this.entryItems = entryItems;
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


		public Long getContCompId() {
			return contCompId;
		}


		public void setContCompId(Long contCompId) {
			this.contCompId = contCompId;
		}

}
