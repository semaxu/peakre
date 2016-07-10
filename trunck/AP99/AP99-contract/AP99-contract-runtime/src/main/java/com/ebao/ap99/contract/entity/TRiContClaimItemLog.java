package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.ebao.ap99.parent.BaseEntityImpl;

import java.math.BigDecimal;


/**
 * The persistent class for the T_RI_CONT_CLAIM_ITEM_LOG database table.
 * 
 */
@Entity
@Table(name="T_RI_CONT_CLAIM_ITEM_LOG")
@NamedQuery(name="TRiContClaimItemLog.findAll", query="SELECT t FROM TRiContClaimItemLog t")
public class TRiContClaimItemLog extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="LOG_ID")
	private Long logId;

	private BigDecimal amount;

	@Column(name="CATE_ID")
	private String cateId;

	@Column(name="CONT_COMP_ID")
	private Long contCompId;

	@Column(name="IS_CHECK")
	private String isCheck;

	@Column(name="OPERATE_ID")
	private Long operateId;

	private BigDecimal percentage;

	@Column(name="ITEM_ID")
	private Long itemId;

	public TRiContClaimItemLog() {
	}

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCateId() {
		return this.cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getIsCheck() {
		return this.isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public Long getOperateId() {
		return this.operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public BigDecimal getPercentage() {
		return this.percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}


	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	@Override
	public Long getPrimaryKey() {
		// TODO Auto-generated method stub
		return this.logId;
	}

	@Override
	public void setPrimaryKey(Long paramLong) {
		// TODO Auto-generated method stub
		this.setLogId(paramLong);
	}

}