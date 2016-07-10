package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_LIMIT_ITEM_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_LIMIT_ITEM_LOG")
@NamedQuery(name = "TRiContLimitItemLog.findAll", query = "SELECT t FROM TRiContLimitItemLog t")
public class TRiContLimitItemLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "LOG_ID")
	private Long logId;

	private BigDecimal aad;

	private BigDecimal aal;

	@Column(name = "AAL_SHARE")
	private BigDecimal aalShare;

	@Column(name = "AAL_WRITTEN_SHARE")
	private BigDecimal aalWrittenShare;

	public BigDecimal getAalWrittenShare() {
		return aalWrittenShare;
	}

	public void setAalWrittenShare(BigDecimal aalWrittenShare) {
		this.aalWrittenShare = aalWrittenShare;
	}

	private String currency;

	private BigDecimal deductible;

	@Column(name = "DEDUCTIBLE_PER")
	private BigDecimal deductiblePer;

	@Column(name = "LAYER_SHARE")
	private BigDecimal layerShare;

	@Column(name = "LAYER_WRITTEN_SHARE")
	private BigDecimal layerWrittenShare;

	public BigDecimal getLayerWrittenShare() {
		return layerWrittenShare;
	}

	public void setLayerWrittenShare(BigDecimal layerWrittenShare) {
		this.layerWrittenShare = layerWrittenShare;
	}

	private BigDecimal liability;

	@Column(name = "LIMIT_LAYER")
	private BigDecimal limitLayer;

	@Column(name = "LIMIT_LAYER_PER")
	private BigDecimal limitLayerPer;

	@Column(name = "LIMIT_MAX")
	private BigDecimal limitMax;

	@Column(name = "LIMIT_MIN")
	private BigDecimal limitMin;

	@Column(name = "LIMIT_TYPE")
	private String limitType;

	@Column(name = "NO_OF_LINES")
	private BigDecimal noOfLines;

	@Column(name = "LIABILITY_SIGNED_SHARE")
	private BigDecimal LiabilitySignedShare;

	@Column(name = "LIABILITY_WRITTEN_SHARE")
	private BigDecimal LiabilityWrittenShare;

	private BigDecimal qs;

	private String remark;

	private BigDecimal retente;

	@Column(name = "SUM_INSURED")
	private BigDecimal sumInsured;

	@Column(name = "OPERATE_ID")
	private Long operateId;

	@Column(name = "ITEM_ID")
	private Long itemId;

	@Column(name = "LIMIT_ID")
	private Long limitId;

	@Column(name = "ITEM_TYPE")
	private String itemType;

	public TRiContLimitItemLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public BigDecimal getAad() {
		return this.aad;
	}

	public void setAad(BigDecimal aad) {
		this.aad = aad;
	}

	public BigDecimal getAal() {
		return this.aal;
	}

	public void setAal(BigDecimal aal) {
		this.aal = aal;
	}

	public BigDecimal getAalShare() {
		return this.aalShare;
	}

	public void setAalShare(BigDecimal aalShare) {
		this.aalShare = aalShare;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getDeductible() {
		return this.deductible;
	}

	public void setDeductible(BigDecimal deductible) {
		this.deductible = deductible;
	}

	public BigDecimal getLayerShare() {
		return this.layerShare;
	}

	public void setLayerShare(BigDecimal layerShare) {
		this.layerShare = layerShare;
	}

	public BigDecimal getLiability() {
		return this.liability;
	}

	public void setLiability(BigDecimal liability) {
		this.liability = liability;
	}

	public BigDecimal getLimitLayer() {
		return this.limitLayer;
	}

	public void setLimitLayer(BigDecimal limitLayer) {
		this.limitLayer = limitLayer;
	}

	public BigDecimal getLimitMax() {
		return this.limitMax;
	}

	public void setLimitMax(BigDecimal limitMax) {
		this.limitMax = limitMax;
	}

	public BigDecimal getLimitMin() {
		return this.limitMin;
	}

	public void setLimitMin(BigDecimal limitMin) {
		this.limitMin = limitMin;
	}

	public String getLimitType() {
		return this.limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public BigDecimal getNoOfLines() {
		return this.noOfLines;
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
		return this.qs;
	}

	public void setQs(BigDecimal qs) {
		this.qs = qs;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getRetente() {
		return this.retente;
	}

	public void setRetente(BigDecimal retente) {
		this.retente = retente;
	}

	public BigDecimal getSumInsured() {
		return this.sumInsured;
	}

	public void setSumInsured(BigDecimal sumInsured) {
		this.sumInsured = sumInsured;
	}

	@Override
	public Long getPrimaryKey() {
		// TODO Auto-generated method stub
		return this.getLogId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		// TODO Auto-generated method stub
		this.setLogId(key);
	}

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getLimitId() {
		return limitId;
	}

	public void setLimitId(Long limitId) {
		this.limitId = limitId;
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