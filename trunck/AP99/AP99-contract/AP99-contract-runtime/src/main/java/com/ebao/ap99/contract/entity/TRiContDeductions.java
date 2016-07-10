package com.ebao.ap99.contract.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Where;
import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.parent.BaseEntityImpl;

/**
 * The persistent class for the T_RI_CONT_DEDUCTIONS database table.
 */
@Entity
@Table(name = "T_RI_CONT_DEDUCTIONS")

@NamedQueries({
		@NamedQuery(name = "TRiContDeductions.findAll", query = "SELECT t FROM TRiContDeductions t WHERE t.isActive != 'N'"),
		@NamedQuery(name = "TRiContDeductions.loadByContCompId", query = "SELECT t FROM TRiContDeductions t WHERE t.contCompId = :contCompId and t.isActive != 'N'") })
@XmlAccessorType(XmlAccessType.FIELD)
public class TRiContDeductions extends BaseEntityImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_UID")
	@Column(name = "DEDUCTIONS_ID")
	@XmlTransient
	private Long deductionsId;

	@Column(name = "BROKERAGE")
	private BigDecimal brokerage;

	@Column(name = "BROKERAGE2")
	private BigDecimal brokerage2;

	@Column(name = "CONT_COMP_ID")
	@XmlTransient
	private Long contCompId;

	@Column(name = "DEFICIT_CARRY_FORWARD")
	private String deficitCarryForward;

	@Column(name = "FIXED_AMOUNT_HUNRED_PERCENT")
	private BigDecimal fixedAmountHunredPercent;

	@Column(name = "FIXED_AMOUNT_OUR_SHARE")
	private BigDecimal fixedAmountOurShare;

	@Column(name = "NUMBER_OF_YEARS")
	private BigDecimal numberOfYears;

	@Column(name = "PERCENT_OF_PREMIUM")
	private BigDecimal percentOfPremium;

	@Column(name = "PROFIT_PERCENTAGE_TYPE")
	private String profitPercentageType;

	@Column(name = "RI_PERCENTAGE")
	private BigDecimal riPercentage;

	@Column(name = "PROFIT_PERCENTAGE")
	private BigDecimal profitPercentage;

	@Column(name = "EXPENSES_PERCENTAGE")
	private BigDecimal expensesPercentage;

	@Column(name = "R_I_COMMISSION")
	private String rICommission;

	@Column(name = "REMARK_PANEL")
	private String remarkPanel;

	@OneToMany(mappedBy = "TRiContDeductions", cascade = { CascadeType.ALL })
	@Where(clause = "IS_ACTIVE='Y'")
	private List<TRiContDeductionsItem> TRiContDeductionsItems = new ArrayList<TRiContDeductionsItem>();

	// public List<TRiContDeductionsItem> getTRiContDeductionsItems() {
	// return TRiContDeductionsItems;
	// }

	// public void setTRiContDeductionsItems(List<TRiContDeductionsItem>
	// tRiContDeductionsItems) {
	// TRiContDeductionsItems = tRiContDeductionsItems;
	// }

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

	public TRiContDeductions() {
		// default Y
		this.isActive = "Y";
	}

	public Long getDeductionsId() {
		return this.deductionsId;
	}

	public void setDeductionsId(Long deductionsId) {
		this.deductionsId = deductionsId;
	}

	public BigDecimal getBrokerage() {
		return this.brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public BigDecimal getBrokerage2() {
		return this.brokerage2;
	}

	public void setBrokerage2(BigDecimal brokerage2) {
		this.brokerage2 = brokerage2;
	}

	public Long getContCompId() {
		return this.contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getDeficitCarryForward() {
		return this.deficitCarryForward;
	}

	public void setDeficitCarryForward(String deficitCarryForward) {
		this.deficitCarryForward = deficitCarryForward;
	}

	public BigDecimal getFixedAmountHunredPercent() {
		return this.fixedAmountHunredPercent;
	}

	public void setFixedAmountHunredPercent(BigDecimal fixedAmountHunredPercent) {
		this.fixedAmountHunredPercent = fixedAmountHunredPercent;
	}

	public BigDecimal getFixedAmountOurShare() {
		return this.fixedAmountOurShare;
	}

	public void setFixedAmountOurShare(BigDecimal fixedAmountOurShare) {
		this.fixedAmountOurShare = fixedAmountOurShare;
	}

	public BigDecimal getNumberOfYears() {
		return this.numberOfYears;
	}

	public void setNumberOfYears(BigDecimal numberOfYears) {
		this.numberOfYears = numberOfYears;
	}

	public BigDecimal getPercentOfPremium() {
		return this.percentOfPremium;
	}

	public void setPercentOfPremium(BigDecimal percentOfPremium) {
		this.percentOfPremium = percentOfPremium;
	}

	public String getProfitPercentageType() {
		return this.profitPercentageType;
	}

	public void setProfitPercentageType(String profitPercentageType) {
		this.profitPercentageType = profitPercentageType;
	}

	public BigDecimal getRiPercentage() {
		return this.riPercentage;
	}

	public void setRiPercentage(BigDecimal riPercentage) {
		this.riPercentage = riPercentage;
	}

	public BigDecimal getProfitPercentage() {
		return this.profitPercentage;
	}

	public void setProfitPercentage(BigDecimal profitPercentage) {
		this.profitPercentage = profitPercentage;
	}

	public BigDecimal getExpensesPercentage() {
		return this.expensesPercentage;
	}

	public void setExpensesPercentage(BigDecimal expensesPercentage) {
		this.expensesPercentage = expensesPercentage;
	}

	public String getRICommission() {
		return this.rICommission;
	}

	public void setRICommission(String rICommission) {
		this.rICommission = rICommission;
	}

	public String getRemarkPanel() {
		return this.remarkPanel;
	}

	public void setRemarkPanel(String remarkPanel) {
		this.remarkPanel = remarkPanel;
	}

	@Override
	public Long getPrimaryKey() {
		return getDeductionsId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setDeductionsId(key);

	}

	public List<TRiContDeductionsItem> getTRiContDeductionsItems() {
		return this.TRiContDeductionsItems;
	}

	public void setTRiContDeductionsItems(List<TRiContDeductionsItem> TRiContDeductionsItems) {
		this.TRiContDeductionsItems = TRiContDeductionsItems;
	}

	public TRiContDeductionsItem addTRiContDeductionsItem(TRiContDeductionsItem TRiContDeductionsItem) {
		getTRiContDeductionsItems().add(TRiContDeductionsItem);
		TRiContDeductionsItem.setTRiContDeductions(this);

		return TRiContDeductionsItem;
	}

	public TRiContDeductionsItem removeTRiContDeductionsItem(TRiContDeductionsItem TRiContDeductionsItem) {
		getTRiContDeductionsItems().remove(TRiContDeductionsItem);
		TRiContDeductionsItem.setTRiContDeductions(null);

		return TRiContDeductionsItem;
	}

}
