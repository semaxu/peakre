package com.ebao.ap99.claim.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the T_RI_CLAIM_EVENT database table.
 * 
 */
@Entity
@Table(name="T_RI_CLAIM_EVENT")
@NamedQueries({
	@NamedQuery(name="TRiClaimEvent.findAll", query="SELECT t FROM TRiClaimEvent t"),
	@NamedQuery(name = "TRiClaimEvent.findEventIdByEventCode", query = "  FROM TRiClaimEvent s WHERE s.eventCode = :eventCode "),

	})

public class TRiClaimEvent extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name="T_RI_CLAIM_EVENT_EVENTID_GENERATOR", sequenceName="U_SID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_UID")
	@Column(name="EVENT_ID", unique=true, nullable=false, precision=22)
	private long eventId;

	@Column(name="CAUSE_OF_LOSS", length=20)
	private String causeOfLoss;

//	@Temporal(TemporalType.DATE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_OF_CREATION")
	private Date dateOfCreation;

//	@Temporal(TemporalType.DATE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_OF_LOSS_FROM")
	private Date dateOfLossFrom;

//	@Temporal(TemporalType.DATE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_OF_LOSS_TO")
	private Date dateOfLossTo;

	@Column(name="EVENT_CODE", length=20)
	private String eventCode;

//	@Column(name="INSERT_BY", precision=10)
//	private BigDecimal insertBy;
//
//	@Temporal(TemporalType.DATE)
//	@Column(name="INSERT_TIME")
//	private Date insertTime;

	@Column(name="LARGE_LOSS_REMIND_LVL1_FLAG", length=2)
	private String largeLossRemindLvl1Flag;

	@Column(name="LARGE_LOSS_REMIND_LVL2_FLAG", length=2)
	private String largeLossRemindLvl2Flag;

	@Column(name="LOSS_DESC", length=4000)
	private String lossDesc;

	@Column(name="OPERATING_BY", length=50)
	private String operatingBy;

	@Column(length=4000)
	private String remark;

	@Column(name="TASK_OWNER", length=50)
	private String taskOwner;

	@Column(name="TOTAL_LOSS", precision=20, scale=2)
	private BigDecimal totalLoss;

//	@Column(name="UPDATE_BY", precision=10)
//	private BigDecimal updateBy;
//
//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_TIME")
//	private Date updateTime;
	
	@Column(name = "RESERVE_UPDATE_REMARK_RETRO")
	private String reserveUpdateRemarkRetro;
	
	@Transient
	private List<TRiClaimReserve> TRiClaimReserves;

	@Transient
	private List<TRiClaimSettlement> TRiClaimSettlements;

	@Transient
	private List<TRiClaimSectionInfo> TRiClaimSectionInfos;
	

	public TRiClaimEvent() {
	}

	public long getEventId() {
		return this.eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public String getCauseOfLoss() {
		return this.causeOfLoss;
	}

	public void setCauseOfLoss(String causeOfLoss) {
		this.causeOfLoss = causeOfLoss;
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

	public String getEventCode() {
		return this.eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

//	public BigDecimal getInsertBy() {
//		return this.insertBy;
//	}
//
//	public void setInsertBy(BigDecimal insertBy) {
//		this.insertBy = insertBy;
//	}
//
//	public Date getInsertTime() {
//		return this.insertTime;
//	}
//
//	public void setInsertTime(Date insertTime) {
//		this.insertTime = insertTime;
//	}

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

	public String getLossDesc() {
		return this.lossDesc;
	}

	public void setLossDesc(String lossDesc) {
		this.lossDesc = lossDesc;
	}

	public String getOperatingBy() {
		return this.operatingBy;
	}

	public void setOperatingBy(String operatingBy) {
		this.operatingBy = operatingBy;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	public void setPrimaryKey(Long arg0) {
		// TODO Auto-generated method stub
		
	}

//	public BigDecimal getUpdateBy() {
//		return this.updateBy;
//	}
//
//	public void setUpdateBy(BigDecimal updateBy) {
//		this.updateBy = updateBy;
//	}
//
//	public Date getUpdateTime() {
//		return this.updateTime;
//	}
//
//	public void setUpdateTime(Date updateTime) {
//		this.updateTime = updateTime;
//	}

}