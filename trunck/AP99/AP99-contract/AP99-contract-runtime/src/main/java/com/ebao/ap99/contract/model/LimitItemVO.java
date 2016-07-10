package com.ebao.ap99.contract.model;

import java.math.BigDecimal;

public class LimitItemVO {
	private Long limitId;

	private Long itemId;

	private BigDecimal aad;

	private BigDecimal aal;

	private BigDecimal aalShare;

	private BigDecimal aalWrittenShare;

	public BigDecimal getAalWrittenShare() {
		return aalWrittenShare;
	}

	public void setAalWrittenShare(BigDecimal aalWrittenShare) {
		this.aalWrittenShare = aalWrittenShare;
	}

	private String currency;

	private BigDecimal deductible;

	private BigDecimal deductiblePer;

	private BigDecimal layerShare;

	private BigDecimal layerWrittenShare;

	public BigDecimal getLayerWrittenShare() {
		return layerWrittenShare;
	}

	public void setLayerWrittenShare(BigDecimal layerWrittenShare) {
		this.layerWrittenShare = layerWrittenShare;
	}

	private BigDecimal liability;

	private BigDecimal limitLayer;

	private BigDecimal limitLayerPer;

	private BigDecimal limitMax;

	private BigDecimal limitMin;

	private String limitType;

	private BigDecimal noOfLines;

	private BigDecimal LiabilitySignedShare;

	private BigDecimal LiabilityWrittenShare;

	private BigDecimal qs;

	private String remark;

	private BigDecimal retente;

	private BigDecimal sumInsured;

	private String itemType;

	public LimitItemVO() {

	}

	public Long getLimitId() {
		return limitId;
	}

	public void setLimitId(Long limitId) {
		this.limitId = limitId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getAad() {
		return aad;
	}

	public void setAad(BigDecimal aad) {
		this.aad = aad;
	}

	public BigDecimal getAal() {
		return aal;
	}

	public void setAal(BigDecimal aal) {
		this.aal = aal;
	}

	public BigDecimal getAalShare() {
		return aalShare;
	}

	public void setAalShare(BigDecimal aalShare) {
		this.aalShare = aalShare;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getDeductible() {
		return deductible;
	}

	public void setDeductible(BigDecimal deductible) {
		this.deductible = deductible;
	}

	public BigDecimal getLayerShare() {
		return layerShare;
	}

	public void setLayerShare(BigDecimal layerShare) {
		this.layerShare = layerShare;
	}

	public BigDecimal getLiability() {
		return liability;
	}

	public void setLiability(BigDecimal liability) {
		this.liability = liability;
	}

	public BigDecimal getLimitLayer() {
		return limitLayer;
	}

	public void setLimitLayer(BigDecimal limitLayer) {
		this.limitLayer = limitLayer;
	}

	public BigDecimal getLimitMax() {
		return limitMax;
	}

	public void setLimitMax(BigDecimal limitMax) {
		this.limitMax = limitMax;
	}

	public BigDecimal getLimitMin() {
		return limitMin;
	}

	public void setLimitMin(BigDecimal limitMin) {
		this.limitMin = limitMin;
	}

	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public BigDecimal getNoOfLines() {
		return noOfLines;
	}

	public void setNoOfLines(BigDecimal noOfLines) {
		this.noOfLines = noOfLines;
	}

	public BigDecimal getLiabilitySignedShare() {
		return LiabilitySignedShare;
	}

	public void setLiabilitySignedShare(BigDecimal liabilitySignedShare) {
		LiabilitySignedShare = liabilitySignedShare;
	}

	public BigDecimal getLiabilityWrittenShare() {
		return LiabilityWrittenShare;
	}

	public void setLiabilityWrittenShare(BigDecimal liabilityWrittenShare) {
		LiabilityWrittenShare = liabilityWrittenShare;
	}

	public BigDecimal getQs() {
		return qs;
	}

	public void setQs(BigDecimal qs) {
		this.qs = qs;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getRetente() {
		return retente;
	}

	public void setRetente(BigDecimal retente) {
		this.retente = retente;
	}

	public BigDecimal getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(BigDecimal sumInsured) {
		this.sumInsured = sumInsured;
	}

	public BigDecimal getDeductiblePer() {
		return deductiblePer;
	}

	public void setDeductiblePer(BigDecimal deductiblePer) {
		this.deductiblePer = deductiblePer;
	}

	public BigDecimal getLimitLayerPer() {
		return limitLayerPer;
	}

	public void setLimitLayerPer(BigDecimal limitLayerPer) {
		this.limitLayerPer = limitLayerPer;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

}
