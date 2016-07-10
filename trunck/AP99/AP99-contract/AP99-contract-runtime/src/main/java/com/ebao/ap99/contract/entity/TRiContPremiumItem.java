package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Where;
import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_PREMIUM_ITEM database table.
 */
@Entity
@Table(name = "T_RI_CONT_PREMIUM_ITEM")
@NamedQuery(name = "TRiContPremiumItem.findAll", query = "SELECT t FROM TRiContPremiumItem t WHERE t.isActive != 'N'")
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContPremiumItem extends BaseEntityImpl implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "ITEM_ID")
	@XmlTransient
	private Long itemId;

	@Column(name = "PREMIUM_ID", insertable = false, updatable = false)
	@XmlTransient
	private Long premiumId;

	private BigDecimal amount;

	private String currency;

	@Column(name = "DEFINED_IN")
	private String definedIn;

	@Temporal(TemporalType.DATE)
	@Column(name = "DUE_DATE")
	private Date dueDate;

	@Column(name = "ITEM_TYPE")
	private String itemType;

	@Column(name = "MINIMUM_AMOUNT")
	private BigDecimal minimumAmount;

	@Column(name = "DEPOSIT_AMOUNT")
	private BigDecimal depositAmount;

	@Temporal(TemporalType.DATE)
	@Column(name = "OPERATE_DATE")
	private Date operateDate;

	@Column(name = "OUR_SIGNED_SHARE")
	private BigDecimal ourSignedShare;

	@Column(name = "OUR_WRITTEN_SHARE")
	private BigDecimal ourWrittenShare;

	@Column(name = "PERCENTAGE")
	private BigDecimal percentage;

	// bi-directional many-to-one association to TRiContPremium
	@ManyToOne
	@JoinColumn(name = "PREMIUM_ID")
	@Where(clause = "IS_ACTIVE='Y'")
	@XmlTransient
	private TRiContPremium TRiContPremium;

	@Column(name = "IS_ACTIVE")
	@XmlTransient
	private String isActive;
	
	@Column(name = "POST_SOA")
	@XmlTransient
	private Boolean postSoa;
	
	@Column(name = "INSTAL_NO")
	private Long instalNo;
	
	@Column(name = "INSTAL_PAY_NO")
	private Long instalPayNo;

	public Long getInstalPayNo() {
		return instalPayNo;
	}

	public void setInstalPayNo(Long instalPayNo) {
		this.instalPayNo = instalPayNo;
	}

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

	public TRiContPremiumItem() {
		// default Y
		this.isActive = "Y";
	}

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getPremiumId() {
		return premiumId;
	}

	public void setPremiumId(Long premiumId) {
		this.premiumId = premiumId;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDefinedIn() {
		return this.definedIn;
	}

	public void setDefinedIn(String definedIn) {
		this.definedIn = definedIn;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public BigDecimal getMinimumAmount() {
		return this.minimumAmount;
	}

	public void setMinimumAmount(BigDecimal minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public Date getOperateDate() {
		return this.operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public BigDecimal getOurSignedShare() {
		return ourSignedShare;
	}

	public void setOurSignedShare(BigDecimal ourSignedShare) {
		this.ourSignedShare = ourSignedShare;
	}

	public BigDecimal getOurWrittenShare() {
		return ourWrittenShare;
	}

	public void setOurWrittenShare(BigDecimal ourWrittenShare) {
		this.ourWrittenShare = ourWrittenShare;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getItemId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setItemId(key);
	}

	public TRiContPremium getTRiContPremium() {
		return this.TRiContPremium;
	}

	public void setTRiContPremium(TRiContPremium TRiContPremium) {
		this.TRiContPremium = TRiContPremium;
	}

	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}

	public Boolean getPostSoa() {
		return postSoa;
	}

	public void setPostSoa(Boolean postSoa) {
		this.postSoa = postSoa;
	}

	public Long getInstalNo() {
		return instalNo;
	}

	public void setInstalNo(Long instalNo) {
		this.instalNo = instalNo;
	}
}
