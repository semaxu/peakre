package com.ebao.ap99.claim.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the T_RI_CLAIM_INFO database table.
 * 
 */
@Entity
@Table(name = "T_RI_CLAIM_INFO")
@NamedQuery(name="TRiClaimInfo.findAll", query="SELECT t FROM TRiClaimInfo t")
@NamedQueries({
	@NamedQuery(name = "TRiClaimInfo.findByEventId", query = "  FROM TRiClaimInfo s WHERE s.eventId = :eventId "),
	@NamedQuery(name = "TRiClaimInfo.findClaimNobyclaimId", query = "  FROM TRiClaimInfo s WHERE s.claimId = :claimId "),
	})
public class TRiClaimInfo extends com.ebao.unicorn.platform.data.domain.BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name = "CLAIM_ID")
	private Long claimId;

	@Column(name = "CAUSE_OF_LOSS")
	private String causeOfLoss;

	@Column(name = "CLAIM_BRANCH")
	private String claimBranch;

	@Column(name = "CLAIM_NO")
	private String claimNo;

//	@Temporal(TemporalType.DATE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_OF_CREATION")
	private Date dateOfCreation;

	//@Temporal(TemporalType.DATE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_OF_LOSS_FROM")
	private Date dateOfLossFrom;

	//@Temporal(TemporalType.DATE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_OF_LOSS_TO")
	private Date dateOfLossTo;

	//@Temporal(TemporalType.DATE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_OF_REPORT")
	private Date dateOfReport;

	@Column(name = "EVENT_CODE")
	private Long eventId;

	// @Column(name="INSERT_BY")
	// private BigDecimal insertBy;
	//
	// @Temporal(TemporalType.DATE)
	// @Column(name="INSERT_TIME")
	// private Date insertTime;

	@Column(name = "LARGE_LOSS_REMIND_LVL1_FLAG")
	private String largeLossRemindLvl1Flag;

	@Column(name = "LARGE_LOSS_REMIND_LVL2_FLAG")
	private String largeLossRemindLvl2Flag;

	@Column(name = "LOSS_DESCRIPTION")
	private String lossDescription;

	@Column(name = "OPERATING_BY")
	private String operatingBy;

	@Column(name = "PORTFOLIO_TRANSFER_FLAG")
	private String portfolioTransferFlag;

	@Column(name = "PORTFOLIO_TRANSFER_REMIND_FLAG")
	private String portfolioTransferRemindFlag;

	private String remark;

	/**
	 * 0 : open 1 : close
	 */
	private String status;

	@Column(name = "TASK_OWNER")
	private String taskOwner;

	@Column(name = "TOTAL_LOSS")
	private BigDecimal totalLoss;

	@Column(name = "RESERVE_UPDATE_REMARK")
	private String reserveUpdateRemark;

	@Column(name = "RESERVE_UPDATE_REMARK_RETRO")
	private String reserveUpdateRemarkRetro;

	// @Column(name="UPDATE_BY")
	// private BigDecimal updateBy;
	//
	// @Temporal(TemporalType.DATE)
	// @Column(name="UPDATE_TIME")
	// private Date updateTime;

	// bi-directional many-to-one association to TRiClaimReserve
	// @OneToMany(mappedBy="TRiClaimInfo", cascade={CascadeType.PERSIST})
	// @OneToMany(mappedBy = "TRiClaimInfo", cascade = { CascadeType.ALL })
	@Transient
	private List<TRiClaimReserve> TRiClaimReserves;

//	// bi-directional many-to-one association to TRiClaimSectionInfo
//	@OneToMany(mappedBy = "TRiClaimInfo")
//	private List<TRiClaimSectionInfo> TRiClaimSectionInfos;

	// bi-directional many-to-one association to TRiClaimSettlement
	// @OneToMany(mappedBy = "TRiClaimInfo", cascade = { CascadeType.ALL })
	@Transient
	private List<TRiClaimSettlement> TRiClaimSettlements;

	@Transient
	private List<TRiClaimSectionInfo> TRiClaimSectionInfos;
	
	public TRiClaimInfo() {
		// status = "0";
	}

	public Long getClaimId() {
		return this.claimId;
	}

	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}

	public String getCauseOfLoss() {
		return this.causeOfLoss;
	}

	public void setCauseOfLoss(String causeOfLoss) {
		this.causeOfLoss = causeOfLoss;
	}

	public String getClaimBranch() {
		return this.claimBranch;
	}

	public void setClaimBranch(String claimBranch) {
		this.claimBranch = claimBranch;
	}

	public String getClaimNo() {
		return this.claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public Date getDateOfCreation() {
		return this.dateOfCreation;
	}

	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public Date getDateOfLossFrom() {
		return this.dateOfLossFrom;
	}

	public void setDateOfLossFrom(Date dateOfLossFrom) {
		this.dateOfLossFrom = dateOfLossFrom;
	}

	public Date getDateOfLossTo() {
		return this.dateOfLossTo;
	}

	public void setDateOfLossTo(Date dateOfLossTo) {
		this.dateOfLossTo = dateOfLossTo;
	}

	public Date getDateOfReport() {
		return this.dateOfReport;
	}

	public void setDateOfReport(Date dateOfReport) {
		this.dateOfReport = dateOfReport;
	}



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

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
//	public Long getEventId() {
//	return this.eventId;
//}
//
//public void setEventId(Long eventId) {
//	this.eventId = eventId;
//}
	public String getLargeLossRemindLvl1Flag() {
		return this.largeLossRemindLvl1Flag;
	}

	public void setLargeLossRemindLvl1Flag(String largeLossRemindLvl1Flag) {
		this.largeLossRemindLvl1Flag = largeLossRemindLvl1Flag;
	}

	public String getLargeLossRemindLvl2Flag() {
		return this.largeLossRemindLvl2Flag;
	}

	public void setLargeLossRemindLvl2Flag(String largeLossRemindLvl2Flag) {
		this.largeLossRemindLvl2Flag = largeLossRemindLvl2Flag;
	}

	public String getLossDescription() {
		return this.lossDescription;
	}

	public void setLossDescription(String lossDescription) {
		this.lossDescription = lossDescription;
	}

	public String getOperatingBy() {
		return this.operatingBy;
	}

	public void setOperatingBy(String operatingBy) {
		this.operatingBy = operatingBy;
	}

	public String getPortfolioTransferFlag() {
		return this.portfolioTransferFlag;
	}

	public void setPortfolioTransferFlag(String portfolioTransferFlag) {
		this.portfolioTransferFlag = portfolioTransferFlag;
	}

	public String getPortfolioTransferRemindFlag() {
		return this.portfolioTransferRemindFlag;
	}

	public void setPortfolioTransferRemindFlag(String portfolioTransferRemindFlag) {
		this.portfolioTransferRemindFlag = portfolioTransferRemindFlag;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskOwner() {
		return this.taskOwner;
	}

	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}

	public BigDecimal getTotalLoss() {
		return this.totalLoss;
	}

	public void setTotalLoss(BigDecimal totalLoss) {
		this.totalLoss = totalLoss;
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

	public String getReserveUpdateRemark() {
		return reserveUpdateRemark;
	}

	public void setReserveUpdateRemark(String reserveUpdateRemark) {
		this.reserveUpdateRemark = reserveUpdateRemark;
	}

	public String getReserveUpdateRemarkRetro() {
		return reserveUpdateRemarkRetro;
	}

	public void setReserveUpdateRemarkRetro(String reserveUpdateRemarkRetro) {
		this.reserveUpdateRemarkRetro = reserveUpdateRemarkRetro;
	}

	public List<TRiClaimReserve> getTRiClaimReserves() {
		return this.TRiClaimReserves;
	}

	public void setTRiClaimReserves(List<TRiClaimReserve> TRiClaimReserves) {
		this.TRiClaimReserves = TRiClaimReserves;
	}

	public TRiClaimReserve addTRiClaimReserve(TRiClaimReserve TRiClaimReserve) {
		if (getTRiClaimReserves() == null) {
			setTRiClaimReserves(new ArrayList<TRiClaimReserve>());
			getTRiClaimReserves().add(TRiClaimReserve);
		} else {
			getTRiClaimReserves().add(TRiClaimReserve);
		}
		// TRiClaimReserve.setTRiClaimInfo(this);

		return TRiClaimReserve;
	}

	public TRiClaimReserve removeTRiClaimReserve(TRiClaimReserve TRiClaimReserve) {
		getTRiClaimReserves().remove(TRiClaimReserve);
		// TRiClaimReserve.setTRiClaimInfo(null);

		return TRiClaimReserve;
	}

	public List<TRiClaimSectionInfo> getTRiClaimSectionInfos() {
		return this.TRiClaimSectionInfos;
	}

	public void setTRiClaimSectionInfos(List<TRiClaimSectionInfo> TRiClaimSectionInfos) {
		this.TRiClaimSectionInfos = TRiClaimSectionInfos;
	}

	public TRiClaimSectionInfo addTRiClaimSectionInfo(TRiClaimSectionInfo TRiClaimSectionInfo) {
		if (getTRiClaimSectionInfos() == null) {
			setTRiClaimSectionInfos(new ArrayList<TRiClaimSectionInfo>());
			getTRiClaimSectionInfos().add(TRiClaimSectionInfo);
		} else {
			getTRiClaimSectionInfos().add(TRiClaimSectionInfo);
		}
		//getTRiClaimSectionInfos().add(TRiClaimSectionInfo);
		//TRiClaimSectionInfo.setTRiClaimInfo(this);

		return TRiClaimSectionInfo;
	}

	public TRiClaimSectionInfo removeTRiClaimSectionInfo(TRiClaimSectionInfo TRiClaimSectionInfo) {
		getTRiClaimSectionInfos().remove(TRiClaimSectionInfo);
		//TRiClaimSectionInfo.setTRiClaimInfo(null);

		return TRiClaimSectionInfo;
	}

	public List<TRiClaimSettlement> getTRiClaimSettlements() {
		return this.TRiClaimSettlements;
	}

	public void setTRiClaimSettlements(List<TRiClaimSettlement> TRiClaimSettlements) {
		this.TRiClaimSettlements = TRiClaimSettlements;
	}

	public TRiClaimSettlement addTRiClaimSettlement(TRiClaimSettlement TRiClaimSettlement) {
		if (getTRiClaimSettlements() == null) {
			setTRiClaimSettlements(new ArrayList<TRiClaimSettlement>());
			getTRiClaimSettlements().add(TRiClaimSettlement);
		} else {
			getTRiClaimSettlements().add(TRiClaimSettlement);
		}
		// TRiClaimSettlement.setTRiClaimInfo(this);

		return TRiClaimSettlement;

	}

	public TRiClaimSettlement removeTRiClaimSettlement(TRiClaimSettlement TRiClaimSettlement) {
		getTRiClaimSettlements().remove(TRiClaimSettlement);
		// TRiClaimSettlement.setTRiClaimInfo(null);

		return TRiClaimSettlement;
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