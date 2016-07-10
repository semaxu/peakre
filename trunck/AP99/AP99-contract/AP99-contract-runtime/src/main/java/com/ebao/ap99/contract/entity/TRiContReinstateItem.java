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
 * The persistent class for the T_RI_CONT_REINSTATE_ITEM database table.
 */
@Entity
@Table(name = "T_RI_CONT_REINSTATE_ITEM")
@NamedQuery(name = "TRiContReinstateItem.findAll", query = "SELECT t FROM TRiContReinstateItem t WHERE t.isActive != 'N'")
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContReinstateItem extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "ITEM_ID")
	@XmlTransient
	private Long itemId;

	@Column(name = "REIN_AMOUNT")
	private String reinAmount;

	@Column(name = "REIN_RATE")
	private BigDecimal reinRate;

	@Column(name = "REIN_TIME")
	private String reinTime;

	@Column(name = "REIN_TYPE")
	private String reinType;

	private String reinstate;

	// bi-directional many-to-one association to TRiContReinstate
	@ManyToOne
	@JoinColumn(name = "REIN_ID")
	@Where(clause = "IS_ACTIVE='Y'")
	@XmlTransient
	private TRiContReinstate TRiContReinstate;

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

	public TRiContReinstateItem() {
		// default Y
		this.isActive = "Y";
	}

	public TRiContReinstate getTRiContReinstate() {
		return this.TRiContReinstate;
	}

	public void setTRiContReinstate(TRiContReinstate TRiContReinstate) {
		this.TRiContReinstate = TRiContReinstate;
	}

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getReinAmount() {
		return this.reinAmount;
	}

	public void setReinAmount(String reinAmount) {
		this.reinAmount = reinAmount;
	}

	public BigDecimal getReinRate() {
		return this.reinRate;
	}

	public void setReinRate(BigDecimal reinRate) {
		this.reinRate = reinRate;
	}

	public String getReinTime() {
		return this.reinTime;
	}

	public void setReinTime(String reinTime) {
		this.reinTime = reinTime;
	}

	public String getReinType() {
		return this.reinType;
	}

	public void setReinType(String reinType) {
		this.reinType = reinType;
	}

	public String getReinstate() {
		return this.reinstate;
	}

	public void setReinstate(String reinstate) {
		this.reinstate = reinstate;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getItemId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setItemId(key);
	}

}
