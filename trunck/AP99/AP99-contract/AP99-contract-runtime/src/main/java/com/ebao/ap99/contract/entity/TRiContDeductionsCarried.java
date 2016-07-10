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

import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_DEDUCTIONS_CARRIED database table.
 */
@Entity
@Table(name = "T_RI_CONT_DEDUCTIONS_CARRIED")
@NamedQueries({
		@NamedQuery(name = "TRiContDeductionsCarried.findAll", query = "SELECT t FROM TRiContDeductionsCarried t WHERE t.isActive != 'N'"),
		@NamedQuery(name = "TRiContDeductionsCarried.loadBySlidingId", query = "SELECT t FROM TRiContDeductionsCarried t WHERE t.commSlidingDetailId = :commSlidingDetailId and t.isActive != 'N'") })
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContDeductionsCarried extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "CARRIED_FORWARDS_ID")
	@XmlTransient
	private Long carriedForwardsId;

	@Column(name = "COMM_SLIDING_DETAIL_ID", insertable = false, updatable = false)
	@XmlTransient
	private Long commSlidingDetailId;

	@Column(name = "EXTINCTION")
	private String extinction;

	@Column(name = "NAME_")
	private String name;

	@Column(name = "\"YEARS\"")
	private BigDecimal years;

	private String yn;

	@ManyToOne
	@JoinColumn(name = "COMM_SLIDING_DETAIL_ID")
	@XmlTransient
	private TRiContDeductionsComm TRiContDeductionsComm;

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

	public TRiContDeductionsCarried() {
		// default Y
		this.isActive = "Y";
	}

	public Long getCarriedForwardsId() {
		return this.carriedForwardsId;
	}

	public void setCarriedForwardsId(Long carriedForwardsId) {
		this.carriedForwardsId = carriedForwardsId;
	}

	public Long getCommSlidingDetailId() {
		return this.commSlidingDetailId;
	}

	public void setCommSlidingDetailId(Long commSlidingDetailId) {
		this.commSlidingDetailId = commSlidingDetailId;
	}

	public String getExtinction() {
		return this.extinction;
	}

	public void setExtinction(String extinction) {
		this.extinction = extinction;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getYears() {
		return this.years;
	}

	public void setYears(BigDecimal years) {
		this.years = years;
	}

	public String getYn() {
		return this.yn;
	}

	public void setYn(String yn) {
		this.yn = yn;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getCarriedForwardsId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setCarriedForwardsId(key);
	}

	public TRiContDeductionsComm getTRiContDeductionsComm() {
		return TRiContDeductionsComm;
	}

	public void setTRiContDeductionsComm(TRiContDeductionsComm tRiContDeductionsComm) {
		TRiContDeductionsComm = tRiContDeductionsComm;
	}

}
