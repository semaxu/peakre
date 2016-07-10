package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_ADJUSTMENT_ITEM database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_ADJUSTMENT_ITEM")
@NamedQueries({ @NamedQuery(name = "TRiContAdjustmentItem.findAll", query = "SELECT t FROM TRiContAdjustmentItem t"),
		@NamedQuery(name = "TRiContAdjustmentItem.findByContCompId", query = "SELECT t FROM TRiContAdjustmentItem t WHERE t.contCompId=:contCompId ") })
public class TRiContAdjustmentItem extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "ITEM_ID")
	private Long itemId;

	@Column(name = "ADJUSTMENT_ID", insertable = false, updatable = false)
	private Long adjustmentId;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Temporal(TemporalType.DATE)
	@Column(name = "ADJUSTMENT_DATE")
	private Date adjustmentDate;

	private BigDecimal amount;

	private String currency;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "USER_BY")
	private Long userBy;

	@Column(name = "DATE_AT")
	private Date dateAt;

	// bi-directional many-to-one association to TRiContAdjustment
	@ManyToOne
	@JoinColumn(name = "ADJUSTMENT_ID")
	@Where(clause = "IS_ACTIVE='Y'")
	private TRiContAdjustment TRiContAdjustment;

	public TRiContAdjustmentItem() {
		// default Y
		this.isActive = "Y";
	}

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Date getAdjustmentDate() {
		return this.adjustmentDate;
	}

	public void setAdjustmentDate(Date adjustmentDate) {
		this.adjustmentDate = adjustmentDate;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Long getUserBy() {
		return this.userBy;
	}

	public void setUserBy(Long userBy) {
		this.userBy = userBy;
	}

	public Date getDateAt() {
		return dateAt;
	}

	public void setDateAt(Date dateAt) {
		this.dateAt = dateAt;
	}

	public TRiContAdjustment getTRiContAdjustment() {
		return this.TRiContAdjustment;
	}

	public void setTRiContAdjustment(TRiContAdjustment TRiContAdjustment) {
		this.TRiContAdjustment = TRiContAdjustment;
	}

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public Long getAdjustmentId() {
		return adjustmentId;
	}

	public void setAdjustmentId(Long adjustmentId) {
		this.adjustmentId = adjustmentId;
	}

	@Override
	public Long getPrimaryKey() {
		return itemId;
	}

	@Override
	public void setPrimaryKey(Long itemId) {
		this.itemId = itemId;
	}
}