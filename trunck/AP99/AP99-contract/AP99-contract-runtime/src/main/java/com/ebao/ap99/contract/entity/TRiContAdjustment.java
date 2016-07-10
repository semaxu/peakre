package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_ADJUSTMENT database table.
 * 
 */
@Entity
@Table(name = "T_RI_CONT_ADJUSTMENT")
@NamedQueries({ @NamedQuery(name = "TRiContAdjustment.findAll", query = "SELECT t FROM TRiContAdjustment t"),
		@NamedQuery(name = "TRiContAdjustment.loadByContCompId", query = "SELECT t FROM TRiContAdjustment t WHERE t.contCompId = :contCompId AND t.isActive != 'N'") })
public class TRiContAdjustment extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "ADJUSTMENT_ID")
	private Long adjustmentId;

	@Column(name = "CONT_COMP_ID")
	private Long contCompId;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	private String remark;

	// bi-directional many-to-one association to TRiContAdjustmentItem
	@OneToMany(mappedBy = "TRiContAdjustment", cascade = { CascadeType.ALL })
	@Where(clause = "IS_ACTIVE='Y'")
	private List<TRiContAdjustmentItem> TRiContAdjustmentItems = new ArrayList<TRiContAdjustmentItem>();

	public TRiContAdjustment() {
		// default Y
		this.isActive = "Y";
	}

	public Long getAdjustmentId() {
		return this.adjustmentId;
	}

	public void setAdjustmentId(Long adjustmentId) {
		this.adjustmentId = adjustmentId;
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<TRiContAdjustmentItem> getTRiContAdjustmentItems() {
		return this.TRiContAdjustmentItems;
	}

	public void setTRiContAdjustmentItems(List<TRiContAdjustmentItem> TRiContAdjustmentItems) {
		this.TRiContAdjustmentItems = TRiContAdjustmentItems;
	}

	public TRiContAdjustmentItem addTRiContAdjustmentItem(TRiContAdjustmentItem TRiContAdjustmentItem) {
		getTRiContAdjustmentItems().add(TRiContAdjustmentItem);
		TRiContAdjustmentItem.setTRiContAdjustment(this);

		return TRiContAdjustmentItem;
	}

	public TRiContAdjustmentItem removeTRiContAdjustmentItem(TRiContAdjustmentItem TRiContAdjustmentItem) {
		getTRiContAdjustmentItems().remove(TRiContAdjustmentItem);
		TRiContAdjustmentItem.setTRiContAdjustment(null);

		return TRiContAdjustmentItem;
	}

	@Override
	public Long getPrimaryKey() {
		return adjustmentId;
	}

	@Override
	public void setPrimaryKey(Long adjustmentId) {
		this.adjustmentId = adjustmentId;
	}
}