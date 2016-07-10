package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.ebao.ap99.parent.BaseEntityImpl;

import java.math.BigDecimal;


/**
 * The persistent class for the T_RI_CONT_OTHER_DEDUCTIONS_LOG database table.
 * 
 */
@Entity
@Table(name="T_RI_CONT_DEDUCTIONS_ITEM_LOG")
@NamedQuery(name="TRiContDeductionsItemLog.findAll", query="SELECT t FROM TRiContDeductionsItemLog t")
public class TRiContDeductionsItemLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="LOG_ID")
	private Long logId;
	
	@Column(name="DEDUCTIONS_Item_ID")
	private Long deductionsItemId;
	
	public Long getDeductionsItemId() {
		return deductionsItemId;
	}

	public void setDeductionsItemId(Long deductionsItemId) {
		this.deductionsItemId = deductionsItemId;
	}


	@Column(name="DEDUCTIONS_ID")
	private Long deductionsId;


	public Long getDeductionsId() {
		return deductionsId;
	}

	public void setDeductionsId(Long deductionsId) {
		this.deductionsId = deductionsId;
	}


	@Column(name="AMOUNT_OUR_SHARE")
	private BigDecimal amountOurShare;

	@Column(name="AMOUNT_PERCENT")
	private BigDecimal amountPercent;


	private String item;

	private BigDecimal percentage;

	private String remarks;
	
	@Column(name="OPERATE_ID")
	private Long operateId;


	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public TRiContDeductionsItemLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
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
		return this.getLogId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setLogId(key);
		
	}


}