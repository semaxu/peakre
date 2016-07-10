package com.ebao.ap99.claim.entity;

import java.io.Serializable;
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

/**
 * The persistent class for the T_RI_CLAIM_SETTLEMENT_LOG database table.
 * 
 */
@Entity
@Table(name = "T_RI_CLAIM_SETTLEMENT_LOG")
@NamedQuery(name = "TRiClaimSettlementLog.findAll", query = "SELECT t FROM TRiClaimSettlementLog t")
@NamedQueries({
		@NamedQuery(name = "TRiClaimSettlementLog.findBySettlementId", query = " FROM TRiClaimSettlementLog s WHERE s.TRiClaimSettlement.settlementId = :settlementId "), })
public class TRiClaimSettlementLog extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl
		implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")

	@Column(name = "SETTLE_LOG_ID")
	private long settleLogId;

	private String desicion;
	
	@Column(name = "RELATED_SETTLEMENT_ID")
	private Long relatedSettlementId;
	// @Column(name="INSERT_BY")
	// private BigDecimal insertBy;
	//
	// @Temporal(TemporalType.DATE)
	// @Column(name="INSERT_TIME")
	// private Date insertTime;

	@Column(name = "OPERATE_BY")
	private String operateBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "OPERATE_DATE")
	private Date operateDate;

	private String remark;
	//
	// @Column(name="UPDATE_BY")
	// private BigDecimal updateBy;
	//
	// @Temporal(TemporalType.DATE)
	// @Column(name="UPDATE_TIME")
	// private Date updateTime;

	// bi-directional many-to-one association to TRiClaimSettlement
	@ManyToOne
	@JoinColumn(name = "SETTLEMENT_ID")
	private TRiClaimSettlement TRiClaimSettlement;

	public TRiClaimSettlementLog() {
	}

	public long getSettleLogId() {
		return this.settleLogId;
	}

	public void setSettleLogId(long settleLogId) {
		this.settleLogId = settleLogId;
	}

	public String getDesicion() {
		return this.desicion;
	}

	public void setDesicion(String desicion) {
		this.desicion = desicion;
	}
	//
	// public BigDecimal getInsertBy() {
	// return this.insertBy;
	// }
	//
	// public void setInsertBy(BigDecimal insertBy) {
	// this.insertBy = insertBy;
	// }
	//
	// public Date getInsertTime() {
	// return this.insertTime;
	// }
	//
	// public void setInsertTime(Date insertTime) {
	// this.insertTime = insertTime;
	// }

	public String getOperateBy() {
		return this.operateBy;
	}

	public void setOperateBy(String operateBy) {
		this.operateBy = operateBy;
	}

	public Date getOperateDate() {
		return this.operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	// public BigDecimal getUpdateBy() {
	// return this.updateBy;
	// }
	//
	// public void setUpdateBy(BigDecimal updateBy) {
	// this.updateBy = updateBy;
	// }
	//
	// public Date getUpdateTime() {
	// return this.updateTime;
	// }
	//
	// public void setUpdateTime(Date updateTime) {
	// this.updateTime = updateTime;
	// }

	public TRiClaimSettlement getTRiClaimSettlement() {
		return this.TRiClaimSettlement;
	}

	public void setTRiClaimSettlement(TRiClaimSettlement TRiClaimSettlement) {
		this.TRiClaimSettlement = TRiClaimSettlement;
	}

	public Long getRelatedSettlementId() {
		return relatedSettlementId;
	}

	public void setRelatedSettlementId(Long relatedSettlementId) {
		this.relatedSettlementId = relatedSettlementId;
	}

	@Override
	public Long getPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPrimaryKey(Long paramLong) {
		// TODO Auto-generated method stub

	}

}