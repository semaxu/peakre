package com.ebao.ap99.contract.model;

import java.util.ArrayList;
import java.util.List;

public class LimitVO {

	private Long contCompId;
	private String contractNature;

	// limit
	private long limitId;
	private String amountType;
	private String limitType;
	private String perRisk;
	private String unlimit;
	private List<LimitItemVO> limitQsList = new ArrayList<LimitItemVO>();
	private List<LimitItemVO> limitSurplusList = new ArrayList<LimitItemVO>();
	private List<LimitItemVO> limitStopList = new ArrayList<LimitItemVO>();
	private List<LimitItemVO> limitRegularList = new ArrayList<LimitItemVO>();
	private List<LimitItemVO> limitItemList = new ArrayList<LimitItemVO>();
	private List<LimitEventVO> limitEventList = new ArrayList<LimitEventVO>();
	private List<LimitItemVO> limitStopPerList = new ArrayList<LimitItemVO>();

	public List<LimitItemVO> getLimitItemList() {
		return limitItemList;
	}

	public void setLimitItemList(List<LimitItemVO> limitItemList) {
		this.limitItemList = limitItemList;
	}

	public List<LimitItemVO> getLimitQsList() {
		return limitQsList;
	}

	public void setLimitQsList(List<LimitItemVO> limitQsList) {
		limitItemList.addAll(limitQsList);
		this.limitQsList = limitQsList;
	}

	public List<LimitItemVO> getLimitSurplusList() {
		return limitSurplusList;
	}

	public void setLimitSurplusList(List<LimitItemVO> limitSurplusList) {
		limitItemList.addAll(limitSurplusList);
		this.limitSurplusList = limitSurplusList;
	}

	public List<LimitItemVO> getLimitStopList() {
		return limitStopList;
	}

	public void setLimitStopList(List<LimitItemVO> limitStopList) {
		limitItemList.addAll(limitStopList);
		this.limitStopList = limitStopList;
	}

	public List<LimitItemVO> getLimitStopPerList() {
		return limitStopPerList;
	}

	public void setLimitStopPerList(List<LimitItemVO> limitStopPerList) {
		limitItemList.addAll(limitStopPerList);
		this.limitStopPerList = limitStopPerList;
	}

	public List<LimitItemVO> getLimitRegularList() {
		return limitRegularList;
	}

	public void setLimitRegularList(List<LimitItemVO> limitRegularList) {
		limitItemList.addAll(limitRegularList);
		this.limitRegularList = limitRegularList;
	}

	public List<LimitEventVO> getLimitEventList() {
		return limitEventList;
	}

	public void setLimitEventList(List<LimitEventVO> limitEventList) {
		this.limitEventList = limitEventList;
	}

	public long getLimitId() {
		return limitId;
	}

	public void setLimitId(long limitId) {
		this.limitId = limitId;
	}

	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public String getPerRisk() {
		return perRisk;
	}

	public void setPerRisk(String perRisk) {
		this.perRisk = perRisk;
	}

	public String getUnlimit() {
		return unlimit;
	}

	public void setUnlimit(String unlimit) {
		this.unlimit = unlimit;
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


}
