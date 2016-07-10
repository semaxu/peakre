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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Where;
import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_OTHER_DEDUCTIONS database table.
 */
@Entity
@Table(name = "T_RI_CONT_deductions_item")
@NamedQuery(name = "TRiContDeductionsItem.findAll", query = "SELECT t FROM TRiContDeductionsItem t WHERE t.isActive != 'N'")
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContDeductionsItem extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "DEDUCTIONS_ITEM_ID")
	@XmlTransient
	private Long deductionsItemId;

	public Long getDeductionsItemId() {
		return deductionsItemId;
	}

	public void setDeductionsItemId(Long deductionsItemId) {
		this.deductionsItemId = deductionsItemId;
	}

	@Column(name = "DEDUCTIONS_ID", insertable = false, updatable = false)
	@XmlTransient
	private Long deductionsId;

	@Column(name = "AMOUNT_OUR_SHARE")
	private BigDecimal amountOurShare;

	@Column(name = "AMOUNT_PERCENT")
	private BigDecimal amountPercent;

	@Column(name = "ITEM")
	private String item;

	@Column(name = "PERCENTAGE")
	private BigDecimal percentage;

	@Column(name = "REMARKS")
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "DEDUCTIONS_ID")
	@Where(clause = "IS_ACTIVE='Y'")
	@XmlTransient
	private TRiContDeductions TRiContDeductions;

	public TRiContDeductions getTRiContDeductions() {
		return TRiContDeductions;
	}

	public void setTRiContDeductions(TRiContDeductions tRiContDeductions) {
		TRiContDeductions = tRiContDeductions;
	}

	@Column(name = "IS_ACTIVE")
	@XmlTransient
	private String isActive;

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

	public TRiContDeductionsItem() {
		// default Y
		this.isActive = "Y";
	}

	public BigDecimal getAmountOurShare() {
		return this.amountOurShare;
	}

	public void setAmountOurShare(BigDecimal amountOurShare) {
		this.amountOurShare = amountOurShare;
	}

	public BigDecimal getAmountPercent() {
		return this.amountPercent;
	}

	public void setAmountPercent(BigDecimal amountPercent) {
		this.amountPercent = amountPercent;
	}

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public BigDecimal getPercentage() {
		return this.percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getDeductionsItemId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setDeductionsItemId(key);

	}

	public Long getDeductionsId() {
		return deductionsId;
	}

	public void setDeductionsId(Long deductionsId) {
		this.deductionsId = deductionsId;
	}
}
