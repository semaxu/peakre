package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Where;
import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_LIMIT_ITEM database table.
 */
@Entity
@Table(name = "T_RI_CONT_LIMIT_ITEM")
@NamedQueries({
		@NamedQuery(name = "TRiContLimitItem.findAll", query = "SELECT t FROM TRiContLimitItem t WHERE t.isActive != 'N'"),
		// @NamedQuery(name = "TRiContLimitItem.loadByContCompId", query =
		// "SELECT t FROM TRiContLimitItem t WHERE t.contCompId = :contCompId
		// and t.isActive != 'N'") })
})
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContLimitItem extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "ITEM_ID")
	@XmlTransient
	private Long itemId;

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

	@ManyToOne
	@JoinColumn(name = "LIMIT_ID")
	@Where(clause = "IS_ACTIVE='Y'")
	@XmlTransient
	private TRiContLimit TRiContLimit;

	@Column(name = "IS_ACTIVE")
	@XmlTransient
	private String isActive;

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
	private BigDecimal liabilitySignedShare;

	@Column(name = "LIABILITY_WRITTEN_SHARE")
	private BigDecimal liabilityWrittenShare;

	private BigDecimal qs;

	private String remark;

	private BigDecimal retente;

	@Column(name = "SUM_INSURED")
	private BigDecimal sumInsured;

	@Column(name = "ITEM_TYPE")
	private String itemType;

	// @Column(name="OPERATE_ID")
	// private Long operateId;
	//
	// @Column(name="LIMIT_ID")
	// private Long limitId;

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
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
		return liabilitySignedShare;
	}

	public void setLiabilitySignedShare(BigDecimal liabilitySignedShare) {
		this.liabilitySignedShare = liabilitySignedShare;
	}

	public BigDecimal getLiabilityWrittenShare() {
		return liabilityWrittenShare;
	}

	public void setLiabilityWrittenShare(BigDecimal liabilityWrittenShare) {
		this.liabilityWrittenShare = liabilityWrittenShare;
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

	public TRiContLimit getTRiContLimit() {
		return this.TRiContLimit;
	}

	public void setTRiContLimit(TRiContLimit TRiContLimit) {
		this.TRiContLimit = TRiContLimit;
	}

	@Override
	public Long getPrimaryKey() {
		// TODO Auto-generated method stub
		return this.getItemId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		// TODO Auto-generated method stub
		this.setItemId(key);
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		if (StringUtils.isNullOrEmpty(isActive)) {
			this.isActive = "Y";
		} else {
			this.isActive = isActive;
		}
	}

	public TRiContLimitItem() {
		// default Y
		this.isActive = "Y";
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
