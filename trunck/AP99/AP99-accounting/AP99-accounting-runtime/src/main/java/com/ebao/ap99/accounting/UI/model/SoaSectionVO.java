package com.ebao.ap99.accounting.UI.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SoaSectionVO {
	

    private String contractSection;
	
	private BigDecimal cob;

	private BigDecimal shareType;

	private BigDecimal uwArea;
	
	private Boolean isFullyTransfer;

	private List<SoaSectionItemVO> entryItems  = new ArrayList();
	
	




	public String getContractSection() {
		return contractSection;
	}



	public void setContractSection(String contractSection) {
		this.contractSection = contractSection;
	}



	public List<SoaSectionItemVO> getEntryItems() {
		return this.entryItems;
	}



	public void setEntryItems(List<SoaSectionItemVO> entryItems) {
		this.entryItems = entryItems;
	}



	public BigDecimal getCob() {
		return cob;
	}



	public void setCob(BigDecimal cob) {
		this.cob = cob;
	}




	public BigDecimal getShareType() {
		return shareType;
	}



	public void setShareType(BigDecimal shareType) {
		this.shareType = shareType;
	}



	public BigDecimal getUwArea() {
		return uwArea;
	}



	public void setUwArea(BigDecimal uwArea) {
		this.uwArea = uwArea;
	}







	public SoaSectionVO() {
		// TODO Auto-generated constructor stub
	}



	public Boolean getIsFullyTransfer() {
		return isFullyTransfer;
	}



	public void setIsFullyTransfer(Boolean isFullyTransfer) {
		this.isFullyTransfer = isFullyTransfer;
	}

}
