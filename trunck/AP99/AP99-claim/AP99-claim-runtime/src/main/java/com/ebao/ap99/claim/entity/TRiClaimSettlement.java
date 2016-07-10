package com.ebao.ap99.claim.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the T_RI_CLAIM_SETTLEMENT database table.
 * 
 */
@Entity
@Table(name = "T_RI_CLAIM_SETTLEMENT")
@NamedQuery(name = "TRiClaimSettlement.findAll", query = "SELECT t FROM TRiClaimSettlement t")
@NamedQueries({
		@NamedQuery(name = "TRiClaimSettlement.findOutstandingByRefId", query = " FROM TRiClaimSettlement s WHERE s.refId = :refId "
				+ "AND s.status in (1,4)"),
//		@NamedQuery(name = "TRiClaimSettlement.findOpenSettlementByClaimId", query = " FROM TRiClaimSettlement s WHERE s.refId = :claimId AND s.status ='1' "),
		@NamedQuery(name = "TRiClaimSettlement.findApprovedSettlementByRefId", query = " FROM TRiClaimSettlement s WHERE s.refId = :refId "
				+ "AND s.status ='3' "), 
		@NamedQuery(name = "TRiClaimSettlement.findNewSettlementByRefId", query = " FROM TRiClaimSettlement s WHERE s.refId = :refId AND s.businessDirection ='1'"
				+ "AND s.status ='1' "),
		})
public class TRiClaimSettlement extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")

	@Column(name = "SETTLEMENT_ID")
	private Long settlementId;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_OF_RECEIPT")
	private Date dateOfReceipt;

	// @Column(name="INSERT_BY")
	// private BigDecimal insertBy;
	//
	// @Temporal(TemporalType.DATE)
	// @Column(name="INSERT_TIME")
	// private Date insertTime;

	@Column(name = "BUSINESS_DIRECTION")
	private String businessDirection;

	private String remark;

	@Column(name = "SETTLEMENT_NAME")
	private String settlementName;

	/**
	 * 1.Pending for submit 2.Pending for approval 3.Approved 4.Rejected
	 */
	private String status;

	// @Column(name="UPDATE_BY")
	// private BigDecimal updateBy;
	//
	// @Temporal(TemporalType.DATE)
	// @Column(name="UPDATE_TIME")
	// private Date updateTime;

	@Column(name = "REF_TYPE")
	private String refType;

	// bi-directional many-to-one association to TRiClaimInfo
	// @ManyToOne
	// @JoinColumn(name = "CLAIM_ID")
	// @JoinColumn(name = "REF_ID", referencedColumnName = "CLAIM_ID")
	// private TRiClaimInfo TRiClaimInfo;

	@Column(name = "REF_ID")
	private Long refId;
	
	@Column(name = "RELATED_SETTLEMENT_ID")
	private Long relatedSettlementId;

	// bi-directional many-to-one association to TRiClaimSettlementItem
	@OneToMany(mappedBy = "TRiClaimSettlement", cascade = { CascadeType.ALL })
	private List<TRiClaimSettlementItem> TRiClaimSettlementItems;

	// bi-directional many-to-one association to TRiClaimSettlementLog
	@OneToMany(mappedBy = "TRiClaimSettlement", cascade = { CascadeType.ALL} )
	private List<TRiClaimSettlementLog> TRiClaimSettlementLogs;

	public TRiClaimSettlement() {
		// status = "1";
	}

	public Long getSettlementId() {
		return this.settlementId;
	}

	public void setSettlementId(Long settlementId) {
		this.settlementId = settlementId;
	}

	public Date getDateOfReceipt() {
		return this.dateOfReceipt;
	}

	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
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

	public String getBusinessDirection() {
		return businessDirection;
	}

	public void setBusinessDirection(String businessDirection) {
		this.businessDirection = businessDirection;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSettlementName() {
		return this.settlementName;
	}

	public void setSettlementName(String settlementName) {
		this.settlementName = settlementName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	//
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

	// public TRiClaimInfo getTRiClaimInfo() {
	// return this.TRiClaimInfo;
	// }

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	// public void setTRiClaimInfo(TRiClaimInfo TRiClaimInfo) {
	// this.TRiClaimInfo = TRiClaimInfo;
	// }

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public List<TRiClaimSettlementItem> getTRiClaimSettlementItems() {
		return this.TRiClaimSettlementItems;
	}

	public void setTRiClaimSettlementItems(List<TRiClaimSettlementItem> TRiClaimSettlementItems) {
		this.TRiClaimSettlementItems = TRiClaimSettlementItems;
	}

	public TRiClaimSettlementItem addTRiClaimSettlementItem(TRiClaimSettlementItem TRiClaimSettlementItem) {
		if (getTRiClaimSettlementItems() == null) {
			setTRiClaimSettlementItems(new ArrayList<TRiClaimSettlementItem>());
			getTRiClaimSettlementItems().add(TRiClaimSettlementItem);
		} else {
			getTRiClaimSettlementItems().add(TRiClaimSettlementItem);
		}
		TRiClaimSettlementItem.setTRiClaimSettlement(this);

		return TRiClaimSettlementItem;
	}

	public TRiClaimSettlementItem removeTRiClaimSettlementItem(TRiClaimSettlementItem TRiClaimSettlementItem) {
		getTRiClaimSettlementItems().remove(TRiClaimSettlementItem);
		TRiClaimSettlementItem.setTRiClaimSettlement(null);

		return TRiClaimSettlementItem;
	}

	public List<TRiClaimSettlementLog> getTRiClaimSettlementLogs() {
		return this.TRiClaimSettlementLogs;
	}

	public void setTRiClaimSettlementLogs(List<TRiClaimSettlementLog> TRiClaimSettlementLogs) {
		this.TRiClaimSettlementLogs = TRiClaimSettlementLogs;
	}

	public TRiClaimSettlementLog addTRiClaimSettlementLog(TRiClaimSettlementLog TRiClaimSettlementLog) {
		if (getTRiClaimSettlementLogs() == null) {
			setTRiClaimSettlementLogs(new ArrayList<TRiClaimSettlementLog>());
			getTRiClaimSettlementLogs().add(TRiClaimSettlementLog);
		} else {
			getTRiClaimSettlementLogs().add(TRiClaimSettlementLog);
		}
		TRiClaimSettlementLog.setTRiClaimSettlement(this);

		return TRiClaimSettlementLog;
	}

	public TRiClaimSettlementLog removeTRiClaimSettlementLog(TRiClaimSettlementLog TRiClaimSettlementLog) {
		getTRiClaimSettlementLogs().remove(TRiClaimSettlementLog);
		TRiClaimSettlementLog.setTRiClaimSettlement(null);

		return TRiClaimSettlementLog;
	}

	public Long getRelatedSettlementId() {
		return relatedSettlementId;
	}

	public void setRelatedSettlementId(Long relatedSettlementId) {
		this.relatedSettlementId = relatedSettlementId;
	}

	@Override
	public Long getPrimaryKey() {
		return null;
	}

	@Override
	public void setPrimaryKey(Long paramLong) {

	}

}