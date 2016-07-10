package com.ebao.ap99.contract.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReinstateVO {

	private Long contCompId;
	private String contractNature;

	// Reinstate
	private long reinId;
	private String exchCalc;
	private String reinCurrency;
	private BigDecimal reinNum;
	private String reinType;
	private List<ReinstateItemVO> itemList = new ArrayList<ReinstateItemVO>();
	private List<ReinstateItemVO> reinstateList = new ArrayList<ReinstateItemVO>();
	
	public List<ReinstateItemVO> getReinstateList() {
		return reinstateList;
	}
	public void setReinstateList(List<ReinstateItemVO> reinstateList) {
		this.reinstateList = reinstateList;
	}
	public Long getContCompId() {
		return contCompId;
	}
	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}
	public String getContractNature() {
		return contractNature;
	}
	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}
	public long getReinId() {
		return reinId;
	}
	public void setReinId(long reinId) {
		this.reinId = reinId;
	}
	public String getExchCalc() {
		return exchCalc;
	}
	public void setExchCalc(String exchCalc) {
		this.exchCalc = exchCalc;
	}
	public String getReinCurrency() {
		return reinCurrency;
	}
	public void setReinCurrency(String reinCurrency) {
		this.reinCurrency = reinCurrency;
	}
	public BigDecimal getReinNum() {
		return reinNum;
	}
	public void setReinNum(BigDecimal reinNum) {
		this.reinNum = reinNum;
	}
	public String getReinType() {
		return reinType;
	}
	public void setReinType(String reinType) {
		this.reinType = reinType;
	}
	public List<ReinstateItemVO> getItemList() {
		return itemList;
	}
	public void setItemList(List<ReinstateItemVO> itemList) {
		this.itemList = itemList;
	}
	
	
}
