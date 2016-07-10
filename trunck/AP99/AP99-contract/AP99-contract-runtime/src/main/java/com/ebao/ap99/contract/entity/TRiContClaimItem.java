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
 * The persistent class for the T_RI_CONT_CLAIM_ITEM database table.
 */
@Entity
@Table(name = "T_RI_CONT_CLAIM_ITEM")

@NamedQueries({
		@NamedQuery(name = "TRiContClaimItem.findAll", query = "SELECT t FROM TRiContClaimItem t WHERE t.isActive != 'N'"),
		@NamedQuery(name = "TRiContClaimItem.loadByContCompId", query = "SELECT t FROM TRiContClaimItem t WHERE t.contCompId = :contCompId AND t.isActive != 'N'") })

@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContClaimItem extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "ITEM_ID")
	@XmlTransient
	private Long itemId;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "CATE_ID")
	private String cateId;

	@Column(name = "CONT_COMP_ID", insertable = false, updatable = false)
	@XmlTransient
	private Long contCompId;

	@Column(name = "PERCENTAGE")
	private BigDecimal percentage;

	@Column(name = "IS_CHECK")
	private String isCheck;

	// bi-directional many-to-one association to TRiContClaim
	@ManyToOne
	@JoinColumn(name = "CONT_COMP_ID")
	@XmlTransient
	private TRiContClaim TRiContClaim;

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

	public TRiContClaimItem() {
		// default Y
		this.isActive = "Y";
	}

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
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

	public BigDecimal getPercentage() {
		return this.percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public TRiContClaim getTRiContClaimCondition() {
		return this.TRiContClaim;
	}

	public void setTRiContClaimCondition(TRiContClaim TRiContClaim) {
		this.TRiContClaim = TRiContClaim;
	}

	@Override
	public Long getPrimaryKey() {
		return this.itemId;
	}

	@Override
	public void setPrimaryKey(Long paramLong) {
		this.setItemId(paramLong);
	}

}
