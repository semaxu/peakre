package com.ebao.ap99.contract.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.restlet.engine.util.StringUtils;

import com.ebao.ap99.contract.util.ContractCst;
import com.ebao.ap99.parent.DataTypeConvertor;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BusinessConditionVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long contCompId;
	private Long contId;
	private Long parentId;
	private String contractNature;
	private String operateType;

	// premium
	private Long premiumId;
	private String epiType;
	private String premiumType;
	private String definedIn;
	private BigDecimal lossrateFrom;
	private BigDecimal lossrateTo;
	private BigDecimal premiumrateFrom;
	private BigDecimal premiumrateTo;
	private BigDecimal provisionalRate;
	private BigDecimal rate;
	private String premiumRemark;
	private String NoOfInstallment;
	private String NoOfPayment;
	private List<PremiumItemVO> epiList = new ArrayList<PremiumItemVO>();
	private List<PremiumItemVO> terrorismList = new ArrayList<PremiumItemVO>();
	private List<PremiumItemVO> supiList = new ArrayList<PremiumItemVO>();
	private List<PremiumItemVO> floatPremiumList = new ArrayList<PremiumItemVO>();
	private List<PremiumItemVO> fixRateList = new ArrayList<PremiumItemVO>();
	private List<PremiumItemVO> swingRateList = new ArrayList<PremiumItemVO>();

	private List<PremiumItemVO> minimumPremiumList = new ArrayList<PremiumItemVO>();
	private List<PremiumItemVO> depositPremiumList = new ArrayList<PremiumItemVO>();
	private List<PremiumItemVO> depositScheduleList = new ArrayList<PremiumItemVO>();
	private List<PremiumItemVO> deletePremiumList = new ArrayList<PremiumItemVO>();
	@JsonIgnore
	private List<PremiumItemVO> premiumList = new ArrayList<PremiumItemVO>();

	// Deduction
	private Long deductionsId;
	private String deficitCarryForward;
	private BigDecimal fixedAmountHunredPercent;
	private BigDecimal fixedAmountOurShare;
	private BigDecimal numberOfYears;
	private BigDecimal percentOfPremium;
	private String profitPercentageType;
	private BigDecimal riPercentage;
	private BigDecimal profitPercentage;
	private BigDecimal expensesPercentage;
	private String rICommission;
	private String remarkPanel;
	private BigDecimal brokerage;
	private BigDecimal brokerage2;
	private List<DeductionsItemVO> otherDeductionList = new ArrayList<DeductionsItemVO>();
	private List<DeductionsItemVO> deductionsList = new ArrayList<DeductionsItemVO>();
	private List<DeductionsItemVO> deleteDeductionsList = new ArrayList<DeductionsItemVO>();

	// PcSlidingDetail
	private Long pcSlidingId;
	// private Long deductionsId;
	private BigDecimal maximumLossPc;
	private BigDecimal maximumRIPc;
	private BigDecimal minimumLossPc;
	private BigDecimal minimumRIPc;
	private List<DeductionsAttachVO> attachTablePcList = new ArrayList<DeductionsAttachVO>();
	private List<DeductionsAttachVO> deleteAttachTablePcList = new ArrayList<DeductionsAttachVO>();

	// CommSlidingDetail
	private Long commSlidingDetailId;
	private BigDecimal firstCal;
	private String lossIncludeIBNR;
	private BigDecimal maximumLossSs;
	private BigDecimal maximumRISs;
	private BigDecimal minimumLossSs;
	private BigDecimal minimumRISs;
	private BigDecimal subsequentCalc;
	private List<DeductionsAttachVO> attachTableCommList = new ArrayList<DeductionsAttachVO>();
	private List<DeductionsAttachVO> deleteAttachTableCommList = new ArrayList<DeductionsAttachVO>();
	private List<DeductionsCarriedVO> deductionsCarriedList = new ArrayList<DeductionsCarriedVO>();

	// ReserveRebate
	private Long reserveId;
	private String premiumCalcMethod;
	private BigDecimal premiumReserve;
	private BigDecimal interestRate;
	private String lossCalcMethod;
	private BigDecimal lossReserves;
	private BigDecimal noClaimBonus;
	private BigDecimal rebatePercent;
	private BigDecimal lossRatioFrom;
	private BigDecimal lossRatioTo;
	private Date calcDate;
	private BigDecimal expirationYear;
	private BigDecimal expirationMonth;
	private BigDecimal expirationDays;
	private BigDecimal lrCalcYears;
	private String rebateRemark;
	private String adjustedQuarter;

	// Loss
	private Long lossId;
	private BigDecimal cedentParticip;
	private BigDecimal firstCalcAfter;
	private BigDecimal maxRatio;
	private BigDecimal minRatio;
	private String participBase;
	private BigDecimal subCalcEvery;

	// Share
	private Long shareId;
	private BigDecimal cedentRetention;
	private BigDecimal ceded;
	private BigDecimal coinsurance;
	private String comments1;
	private String comments2;
	private String comments3;
	private BigDecimal shareDefine;
	private BigDecimal signedShares1;
	private BigDecimal signedShares2;
	private BigDecimal signedShares3;
	private BigDecimal writtenShare1;
	private BigDecimal writtenShare2;
	private BigDecimal writtenShare3;

	// Clauses
	private Long clausesId;
	private List<String> clausesRequiredList = new ArrayList<String>();
	private List<String> clausesRecommendList = new ArrayList<String>();
	@JsonIgnore
	private String clausesRequired;
	@JsonIgnore
	private String clausesRecommend;

	// Currency
	private List<CurrencyVO> currencyList = new ArrayList<CurrencyVO>();
	private List<CurrencyVO> deleteCurrencyList = new ArrayList<CurrencyVO>();

	// Reinstate
	private Long reinId;
	private String exchCalc;
	private String reinCurrency;
	private BigDecimal reinNum;
	private String reinType;
	private List<ReinstateItemVO> itemList = new ArrayList<ReinstateItemVO>();
	private List<ReinstateItemVO> reinstateSpecificList = new ArrayList<ReinstateItemVO>();
	private List<ReinstateItemVO> reinstateUnlimitedList = new ArrayList<ReinstateItemVO>();
	private List<ReinstateItemVO> reinstateList = new ArrayList<ReinstateItemVO>();
	private List<ReinstateItemVO> deleteReinstateItemList = new ArrayList<ReinstateItemVO>();

	// Limit
	private Long limitId;
	private String amountType;
	private String limitType;
	private String perRisk;
	@JsonIgnore
	private String unlimit;
	private Boolean unlimited;
	private List<LimitItemVO> limitQsList = new ArrayList<LimitItemVO>();
	private List<LimitItemVO> limitSurplusList = new ArrayList<LimitItemVO>();
	private List<LimitItemVO> limitStopList = new ArrayList<LimitItemVO>();
	private List<LimitItemVO> limitRegularList = new ArrayList<LimitItemVO>();
	private List<LimitItemVO> limitStopPerList = new ArrayList<LimitItemVO>();
	private List<LimitItemVO> limitCbList = new ArrayList<LimitItemVO>();

	private List<LimitEventVO> limitEventQsList = new ArrayList<LimitEventVO>();
	private List<LimitEventVO> limitEventSurplusList = new ArrayList<LimitEventVO>();
	private List<LimitEventVO> limitEventXolList = new ArrayList<LimitEventVO>();
	private List<LimitEventVO> limitEventStoplossList = new ArrayList<LimitEventVO>();
	private List<LimitEventVO> limitEventCbList = new ArrayList<LimitEventVO>();

	@JsonIgnore
	private List<LimitItemVO> limitItemList = new ArrayList<LimitItemVO>();
	@JsonIgnore
	private List<LimitEventVO> limitEventList = new ArrayList<LimitEventVO>();
	private List<LimitItemVO> deleteLimitItemList = new ArrayList<LimitItemVO>();
	private List<LimitEventVO> deleteLimitEventList = new ArrayList<LimitEventVO>();
	@JsonIgnore
	private boolean isBCEmpty = true;

	public Boolean getUnlimited() {
		return unlimited;
	}

	public void setUnlimited(Boolean unlimited) {
		this.unlimit = String.valueOf(unlimited);
		this.unlimited = unlimited;
	}

	public List<LimitItemVO> getLimitItemList() {
		return limitItemList;
	}

	public void setLimitItemList(List<LimitItemVO> limitItemList) {
		List<LimitItemVO> limitQsList = new ArrayList<LimitItemVO>();
		List<LimitItemVO> limitSurplusList = new ArrayList<LimitItemVO>();
		List<LimitItemVO> limitStopList = new ArrayList<LimitItemVO>();
		List<LimitItemVO> limitStopPerList = new ArrayList<LimitItemVO>();
		List<LimitItemVO> limitRegularList = new ArrayList<LimitItemVO>();
		List<LimitItemVO> limitCbList = new ArrayList<LimitItemVO>();
		for (LimitItemVO vo : limitItemList) {
			// if
			// (ContractCst.CONTRACT_PROPORTIONAL_LIMIT_TYPE_QS.equals(this.getLimitType()))
			// {
			// limitQsList.add(vo);
			// } else if
			// (ContractCst.CONTRACT_PROPORTIONAL_LIMIT_TYPE_SURPLUS.equals(this.getLimitType()))
			// {
			// limitSurplusList.add(vo);
			// } else if
			// (ContractCst.CONTRACT_NONPROPORTIONAL_LIMIT_TYPE_XOL.equals(this.getLimitType()))
			// {
			// limitRegularList.add(vo);
			// } else if
			// (ContractCst.CONTRACT_NONPROPORTIONAL_LIMIT_TYPE_STOPLOSS.equals(this.getLimitType()))
			// {
			// if(ContractCst.CONTRACT_NONPROPORTIONAL_AMOUNT_TYPE_AMOUNT.equals(this.getAmountType())){
			// limitStopList.add(vo);
			// }else
			// if(ContractCst.CONTRACT_NONPROPORTIONAL_AMOUNT_TYPE_PERCENTAGE.equals(this.getAmountType())){
			// limitStopPerList.add(vo);
			// }
			// }
			if (ContractCst.CONTRACT_LIMIT_QS.equals(vo.getItemType())) {
				limitQsList.add(vo);
			} else if (ContractCst.CONTRACT_LIMIT_SURPLUS.equals(vo.getItemType())) {
				limitSurplusList.add(vo);
			} else if (ContractCst.CONTRACT_LIMIT_XOL.equals(vo.getItemType())) {
				limitRegularList.add(vo);
			} else if (ContractCst.CONTRACT_LIMIT_STOPLOSS_AMOUNT.equals(vo.getItemType())) {
				limitStopList.add(vo);
			} else if (ContractCst.CONTRACT_LIMIT_STOPLOSS_PERCENTAGE.equals(vo.getItemType())) {
				limitStopPerList.add(vo);
			} else if (ContractCst.CONTRACT_LIMIT_COMB.equals(vo.getItemType())) {
				limitCbList.add(vo);
			}
			// CONTRACT_LIMIT_COMB
		}
		this.setLimitQsList(limitQsList);
		this.setLimitSurplusList(limitSurplusList);
		this.setLimitStopList(limitStopList);
		this.setLimitRegularList(limitRegularList);
		this.setLimitStopPerList(limitStopPerList);
		this.setLimitCbList(limitCbList);
		this.limitItemList = limitItemList;
	}

	public List<LimitItemVO> getLimitQsList() {
		return limitQsList;
	}

	public void setLimitQsList(List<LimitItemVO> limitQsList) {
		// if
		// (ContractCst.CONTRACT_PROPORTIONAL_LIMIT_TYPE_QS.equals(this.getLimitType()))
		// {
		limitItemList.addAll(limitQsList);
		// }
		this.limitQsList = limitQsList;
	}

	public List<LimitItemVO> getLimitCbList() {
		return limitCbList;
	}

	public void setLimitCbList(List<LimitItemVO> limitCbList) {

		limitItemList.addAll(limitCbList);
		// }
		// this.limitQsList = limitQsList;
		this.limitCbList = limitCbList;
	}

	public List<LimitEventVO> getLimitEventCbList() {
		return limitEventCbList;
	}

	public void setLimitEventCbList(List<LimitEventVO> limitEventCbList) {
		limitEventList.addAll(limitEventCbList);

		this.limitEventCbList = limitEventCbList;
	}

	public List<LimitItemVO> getLimitSurplusList() {
		return limitSurplusList;
	}

	public void setLimitSurplusList(List<LimitItemVO> limitSurplusList) {
		// if
		// (ContractCst.CONTRACT_PROPORTIONAL_LIMIT_TYPE_SURPLUS.equals(this.getLimitType()))
		// {
		limitItemList.addAll(limitSurplusList);
		// }
		this.limitSurplusList = limitSurplusList;
	}

	public List<LimitItemVO> getLimitStopList() {
		return limitStopList;
	}

	public void setLimitStopList(List<LimitItemVO> limitStopList) {
		// if
		// (ContractCst.CONTRACT_NONPROPORTIONAL_LIMIT_TYPE_STOPLOSS.equals(this.getLimitType()))
		// {
		// if(ContractCst.CONTRACT_NONPROPORTIONAL_AMOUNT_TYPE_AMOUNT.equals(this.getAmountType())){
		limitItemList.addAll(limitStopList);
		// }
		// }
		this.limitStopList = limitStopList;
	}

	public List<LimitItemVO> getLimitStopPerList() {
		return limitStopPerList;
	}

	public void setLimitStopPerList(List<LimitItemVO> limitStopPerList) {
		// if
		// (ContractCst.CONTRACT_NONPROPORTIONAL_LIMIT_TYPE_STOPLOSS.equals(this.getLimitType()))
		// {
		// if(ContractCst.CONTRACT_NONPROPORTIONAL_AMOUNT_TYPE_PERCENTAGE.equals(this.getAmountType())){
		limitItemList.addAll(limitStopPerList);
		// }
		// }
		this.limitStopPerList = limitStopPerList;
	}

	public List<LimitItemVO> getLimitRegularList() {
		return limitRegularList;
	}

	public void setLimitRegularList(List<LimitItemVO> limitRegularList) {
		// if
		// (ContractCst.CONTRACT_NONPROPORTIONAL_LIMIT_TYPE_XOL.equals(this.getLimitType()))
		// {
		limitItemList.addAll(limitRegularList);
		// }
		this.limitRegularList = limitRegularList;
	}

	public List<ReinstateItemVO> getReinstateSpecificList() {
		return reinstateSpecificList;
	}

	public void setReinstateSpecificList(List<ReinstateItemVO> reinstateSpecificList) {
		reinstateList.addAll(reinstateSpecificList);
		this.reinstateSpecificList = reinstateSpecificList;
	}

	public List<ReinstateItemVO> getReinstateUnlimitedList() {
		return reinstateUnlimitedList;
	}

	public void setReinstateUnlimitedList(List<ReinstateItemVO> reinstateUnlimitedList) {
		reinstateList.addAll(reinstateUnlimitedList);
		this.reinstateUnlimitedList = reinstateUnlimitedList;
	}

	public List<ReinstateItemVO> getReinstateList() {
		return reinstateList;
	}

	public List<ReinstateItemVO> getDeleteReinstateItemList() {
		return deleteReinstateItemList;
	}

	public void setDeleteReinstateItemList(List<ReinstateItemVO> deleteReinstateItemList) {
		this.deleteReinstateItemList = deleteReinstateItemList;
	}

	public Long getLimitId() {
		return limitId;
	}

	public void setLimitId(Long limitId) {
		if (null != limitId && limitId != 0L) {
			isBCEmpty = false;
		}
		this.limitId = limitId;
	}

	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public String getPerRisk() {
		return perRisk;
	}

	public void setPerRisk(String perRisk) {
		this.perRisk = perRisk;
	}

	public String getUnlimit() {
		return unlimit;
	}

	public void setUnlimit(String unlimit) {
		this.unlimited = Boolean.parseBoolean(unlimit);
		this.unlimit = unlimit;
	}

	public List<LimitEventVO> getLimitEventList() {
		return limitEventList;
	}

	public void setLimitEventList(List<LimitEventVO> limitEventList) {
		List<LimitEventVO> limitEventQsList = new ArrayList<LimitEventVO>();
		List<LimitEventVO> limitEventSurplusList = new ArrayList<LimitEventVO>();
		List<LimitEventVO> limitEventXolList = new ArrayList<LimitEventVO>();
		List<LimitEventVO> limitEventStoplossList = new ArrayList<LimitEventVO>();
		List<LimitEventVO> limitEventCbList = new ArrayList<LimitEventVO>();

		for (LimitEventVO vo : limitEventList) {
			if (ContractCst.CONTRACT_LIMIT_QS.equals(vo.getItemType())) {
				limitEventQsList.add(vo);
			} else if (ContractCst.CONTRACT_LIMIT_SURPLUS.equals(vo.getItemType())) {
				limitEventSurplusList.add(vo);
			} else if (ContractCst.CONTRACT_LIMIT_XOL.equals(vo.getItemType())) {
				limitEventXolList.add(vo);
			} else if (ContractCst.CONTRACT_LIMIT_STOPLOSS.equals(vo.getItemType())) {
				limitEventStoplossList.add(vo);
			} else if (ContractCst.CONTRACT_LIMIT_COMB.equals(vo.getItemType())) {
				limitEventCbList.add(vo);
			}
		}
		this.setLimitEventQsList(limitEventQsList);
		this.setLimitEventSurplusList(limitEventSurplusList);
		this.setLimitEventXolList(limitEventXolList);
		this.setLimitEventStoplossList(limitEventStoplossList);
		this.setLimitEventCbList(limitEventCbList);
		this.limitEventList = limitEventList;
	}

	public void setReinstateList(List<ReinstateItemVO> reinstateList) {
		List<ReinstateItemVO> reinstateSpecificList = new ArrayList<ReinstateItemVO>();
		List<ReinstateItemVO> reinstateUnlimitedList = new ArrayList<ReinstateItemVO>();
		for (ReinstateItemVO vo : reinstateList) {
			if ("2".equals(vo.getReinType())) {
				reinstateSpecificList.add(vo);
			} else if ("3".equals(vo.getReinType())) {
				reinstateUnlimitedList.add(vo);
			}
		}
		this.setReinstateSpecificList(reinstateSpecificList);
		this.setReinstateUnlimitedList(reinstateUnlimitedList);
		this.reinstateList = reinstateList;
	}

	public Long getClausesId() {
		return clausesId;
	}

	public List<CurrencyVO> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<CurrencyVO> currencyList) {
		if (null != currencyList && currencyList.size() != 0) {
			isBCEmpty = false;
		}
		this.currencyList = currencyList;
	}

	public List<CurrencyVO> getDeleteCurrencyList() {
		return deleteCurrencyList;
	}

	public void setDeleteCurrencyList(List<CurrencyVO> deleteCurrencyList) {
		this.deleteCurrencyList = deleteCurrencyList;
	}

	public List<ReinstateItemVO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ReinstateItemVO> itemList) {
		this.itemList = itemList;
	}

	public Long getReinId() {
		return reinId;
	}

	public void setReinId(Long reinId) {
		if (null != reinId && reinId != 0L) {
			isBCEmpty = false;
		}
		this.reinId = reinId;
	}

	public String getExchCalc() {
		return exchCalc;
	}

	public void setExchCalc(String exchCalc) {
		this.exchCalc = exchCalc;
	}

	public String getReinCurrency() {
		return reinCurrency;
	}

	public void setReinCurrency(String reinCurrency) {
		this.reinCurrency = reinCurrency;
	}

	public BigDecimal getReinNum() {
		return reinNum;
	}

	public void setReinNum(BigDecimal reinNum) {
		this.reinNum = reinNum;
	}

	public String getReinType() {
		return reinType;
	}

	public void setReinType(String reinType) {
		this.reinType = reinType;
	}

	public void setClausesId(Long clausesId) {
		if (null != clausesId && clausesId != 0L) {
			isBCEmpty = false;
		}
		this.clausesId = clausesId;
	}

	public List<String> getClausesRequiredList() {
		return clausesRequiredList;

	}

	public void setClausesRequiredList(List<String> clausesRequiredList) {
		if (null != clausesRequiredList && clausesRequiredList.size() > 0) {
			this.clausesRequired = DataTypeConvertor.parseSelectTreeToString(clausesRequiredList);
		}
		this.clausesRequiredList = clausesRequiredList;
	}

	public List<String> getClausesRecommendList() {
		return clausesRecommendList;
	}

	public void setClausesRecommendList(List<String> clausesRecommendList) {
		if (null != clausesRecommendList && clausesRecommendList.size() > 0) {
			this.clausesRecommend = DataTypeConvertor.parseSelectTreeToString(clausesRecommendList);
		}
		this.clausesRecommendList = clausesRecommendList;
	}

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public Long getContId() {
		return contId;
	}

	public void setContId(Long contId) {
		this.contId = contId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getContractNature() {
		return contractNature;
	}

	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}

	public String getEpiType() {
		return epiType;
	}

	public void setEpiType(String epiType) {
		this.epiType = epiType;
	}

	public String getPremiumType() {
		return premiumType;
	}

	public void setPremiumType(String premiumType) {
		this.premiumType = premiumType;
	}

	public String getDefinedIn() {
		return definedIn;
	}

	public void setDefinedIn(String definedIn) {
		this.definedIn = definedIn;
	}

	public BigDecimal getLossrateFrom() {
		return lossrateFrom;
	}

	public void setLossrateFrom(BigDecimal lossrateFrom) {
		this.lossrateFrom = lossrateFrom;
	}

	public BigDecimal getLossrateTo() {
		return lossrateTo;
	}

	public void setLossrateTo(BigDecimal lossrateTo) {
		this.lossrateTo = lossrateTo;
	}

	public BigDecimal getPremiumrateFrom() {
		return premiumrateFrom;
	}

	public void setPremiumrateFrom(BigDecimal premiumrateFrom) {
		this.premiumrateFrom = premiumrateFrom;
	}

	public BigDecimal getPremiumrateTo() {
		return premiumrateTo;
	}

	public void setPremiumrateTo(BigDecimal premiumrateTo) {
		this.premiumrateTo = premiumrateTo;
	}

	public BigDecimal getProvisionalRate() {
		return provisionalRate;
	}

	public void setProvisionalRate(BigDecimal provisionalRate) {
		this.provisionalRate = provisionalRate;
	}

	public String getPremiumRemark() {
		return premiumRemark;
	}

	public void setPremiumRemark(String premiumRemark) {
		this.premiumRemark = premiumRemark;
	}

	public List<PremiumItemVO> getEpiList() {
		return epiList;
	}

	public void setEpiList(List<PremiumItemVO> epiList) {
		premiumList.addAll(epiList);
		this.epiList = epiList;
	}

	public List<PremiumItemVO> getTerrorismList() {
		return terrorismList;
	}

	public void setTerrorismList(List<PremiumItemVO> terrorismList) {
		premiumList.addAll(terrorismList);
		this.terrorismList = terrorismList;
	}

	public List<PremiumItemVO> getSupiList() {
		return supiList;
	}

	public void setSupiList(List<PremiumItemVO> supiList) {
		premiumList.addAll(supiList);
		this.supiList = supiList;
	}

	public List<PremiumItemVO> getFloatPremiumList() {
		return floatPremiumList;
	}

	public void setFloatPremiumList(List<PremiumItemVO> floatPremiumList) {
		premiumList.addAll(floatPremiumList);
		this.floatPremiumList = floatPremiumList;
	}

	public List<PremiumItemVO> getFixRateList() {
		return fixRateList;
	}

	public void setFixRateList(List<PremiumItemVO> fixRateList) {
		premiumList.addAll(fixRateList);
		this.fixRateList = fixRateList;
	}

	public List<PremiumItemVO> getSwingRateList() {
		return swingRateList;
	}

	public void setSwingRateList(List<PremiumItemVO> swingRateList) {
		premiumList.addAll(swingRateList);
		this.swingRateList = swingRateList;
	}

	public Long getPremiumId() {
		return premiumId;
	}

	public void setPremiumId(Long premiumId) {
		if (null != premiumId && premiumId != 0L) {
			isBCEmpty = false;
		}
		this.premiumId = premiumId;
	}

	public List<PremiumItemVO> getPremiumList() {
		return premiumList;
	}

	public List<PremiumItemVO> getMinimumPremiumList() {
		return minimumPremiumList;
	}

	public void setMinimumPremiumList(List<PremiumItemVO> minimumPremiumList) {
		premiumList.addAll(minimumPremiumList);

		this.minimumPremiumList = minimumPremiumList;
	}

	public List<PremiumItemVO> getDepositPremiumList() {
		return depositPremiumList;
	}

	public void setDepositPremiumList(List<PremiumItemVO> depositPremiumList) {
		premiumList.addAll(depositPremiumList);

		this.depositPremiumList = depositPremiumList;
	}

	public List<PremiumItemVO> getDepositScheduleList() {
		return depositScheduleList;
	}

	public void setDepositScheduleList(List<PremiumItemVO> depositScheduleList) {

		premiumList.addAll(depositScheduleList);

		this.depositScheduleList = depositScheduleList;
	}

	public void setPremiumList(List<PremiumItemVO> premiumList) {
		List<PremiumItemVO> epiList = new ArrayList<PremiumItemVO>();
		List<PremiumItemVO> terrorismList = new ArrayList<PremiumItemVO>();
		List<PremiumItemVO> supiList = new ArrayList<PremiumItemVO>();
		List<PremiumItemVO> floatPremiumList = new ArrayList<PremiumItemVO>();
		List<PremiumItemVO> fixRateList = new ArrayList<PremiumItemVO>();
		List<PremiumItemVO> swingRateList = new ArrayList<PremiumItemVO>();
		// List<PremiumItemVO> minimumList = new ArrayList<PremiumItemVO>();
		// List<PremiumItemVO> depositPremiumList = new
		// ArrayList<PremiumItemVO>();
		// List<PremiumItemVO> depositScheduleList = new
		// ArrayList<PremiumItemVO>();

		List<PremiumItemVO> minimumPremiumList = new ArrayList<PremiumItemVO>();
		List<PremiumItemVO> depositPremiumList = new ArrayList<PremiumItemVO>();
		List<PremiumItemVO> depositScheduleList = new ArrayList<PremiumItemVO>();

		for (PremiumItemVO vo : premiumList) {
			if (ContractCst.CONTRACT_PREMIUM_EPI.equals(vo.getItemType())) {
				epiList.add(vo);
			} else if (ContractCst.CONTRACT_PREMIUM_TERRORISM.equals(vo.getItemType())) {
				terrorismList.add(vo);
			} else if (ContractCst.CONTRACT_PREMIUM_SUPI.equals(vo.getItemType())) {
				supiList.add(vo);
			} else if (ContractCst.CONTRACT_PREMIUM_FLAT.equals(vo.getItemType())) {
				floatPremiumList.add(vo);
			} else if (ContractCst.CONTRACT_PREMIUM_FIXED.equals(vo.getItemType())) {
				fixRateList.add(vo);
			} else if (ContractCst.CONTRACT_PREMIUM_SWING.equals(vo.getItemType())) {
				swingRateList.add(vo);
			} else if (ContractCst.CONTRACT_PREMIUM_MININUM_PREMIUM.equals(vo.getItemType())) {
				minimumPremiumList.add(vo);
			} else if (ContractCst.CONTRACT_PREMIUM_DEPOSIT_PREMIUM.equals(vo.getItemType())) {
				depositPremiumList.add(vo);
			} else if (ContractCst.CONTRACT_PREMIUM_DEPOSIT_SHEDULE.equals(vo.getItemType())) {
				depositScheduleList.add(vo);
			}
		}
		this.setEpiList(epiList);
		this.setSupiList(supiList);
		this.setTerrorismList(terrorismList);
		this.setFloatPremiumList(floatPremiumList);
		this.setFixRateList(fixRateList);
		this.setSwingRateList(swingRateList);
		this.setMinimumPremiumList(minimumPremiumList);
		this.setDepositPremiumList(depositPremiumList);
		this.setDepositScheduleList(depositScheduleList);

		this.premiumList = premiumList;
	}

	public Long getReserveId() {
		return reserveId;
	}

	public void setReserveId(Long reserveId) {
		if (null != reserveId && reserveId != 0L) {
			isBCEmpty = false;
		}
		this.reserveId = reserveId;
	}

	public String getPremiumCalcMethod() {
		return premiumCalcMethod;
	}

	public void setPremiumCalcMethod(String premiumCalcMethod) {
		this.premiumCalcMethod = premiumCalcMethod;
	}

	public String getAdjustedQuarter() {
		return adjustedQuarter;
	}

	public void setAdjustedQuarter(String adjustedQuarter) {
		this.adjustedQuarter = adjustedQuarter;
	}

	public BigDecimal getPremiumReserve() {
		return premiumReserve;
	}

	public void setPremiumReserve(BigDecimal premiumReserve) {
		this.premiumReserve = premiumReserve;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getLossCalcMethod() {
		return lossCalcMethod;
	}

	public void setLossCalcMethod(String lossCalcMethod) {
		this.lossCalcMethod = lossCalcMethod;
	}

	public BigDecimal getLossReserves() {
		return lossReserves;
	}

	public void setLossReserves(BigDecimal lossReserves) {
		this.lossReserves = lossReserves;
	}

	public BigDecimal getNoClaimBonus() {
		return noClaimBonus;
	}

	public void setNoClaimBonus(BigDecimal noClaimBonus) {
		this.noClaimBonus = noClaimBonus;
	}

	public BigDecimal getRebatePercent() {
		return rebatePercent;
	}

	public void setRebatePercent(BigDecimal rebatePercent) {
		this.rebatePercent = rebatePercent;
	}

	public BigDecimal getLossRatioFrom() {
		return lossRatioFrom;
	}

	public void setLossRatioFrom(BigDecimal lossRatioFrom) {
		this.lossRatioFrom = lossRatioFrom;
	}

	public BigDecimal getLossRatioTo() {
		return lossRatioTo;
	}

	public void setLossRatioTo(BigDecimal lossRatioTo) {
		this.lossRatioTo = lossRatioTo;
	}

	public Date getCalcDate() {
		return calcDate;
	}

	public void setCalcDate(Date calcDate) {
		this.calcDate = calcDate;
	}

	public BigDecimal getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(BigDecimal expirationYear) {
		this.expirationYear = expirationYear;
	}

	public BigDecimal getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(BigDecimal expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public BigDecimal getExpirationDays() {
		return expirationDays;
	}

	public void setExpirationDays(BigDecimal expirationDays) {
		this.expirationDays = expirationDays;
	}

	public BigDecimal getLrCalcYears() {
		return lrCalcYears;
	}

	public void setLrCalcYears(BigDecimal lrCalcYears) {
		this.lrCalcYears = lrCalcYears;
	}

	public String getRebateRemark() {
		return rebateRemark;
	}

	public void setRebateRemark(String rebateRemark) {
		this.rebateRemark = rebateRemark;
	}

	public Long getShareId() {
		return shareId;
	}

	public void setShareId(Long shareId) {
		if (null != shareId && shareId != 0L) {
			isBCEmpty = false;
		}
		this.shareId = shareId;
	}

	public BigDecimal getCedentRetention() {
		return cedentRetention;
	}

	public void setCedentRetention(BigDecimal cedentRetention) {
		this.cedentRetention = cedentRetention;
	}

	public BigDecimal getCeded() {
		return ceded;
	}

	public void setCeded(BigDecimal ceded) {
		this.ceded = ceded;
	}

	public BigDecimal getCoinsurance() {
		return coinsurance;
	}

	public void setCoinsurance(BigDecimal coinsurance) {
		this.coinsurance = coinsurance;
	}

	public String getComments1() {
		return comments1;
	}

	public void setComments1(String comments1) {
		this.comments1 = comments1;
	}

	public String getComments2() {
		return comments2;
	}

	public void setComments2(String comments2) {
		this.comments2 = comments2;
	}

	public String getComments3() {
		return comments3;
	}

	public void setComments3(String comments3) {
		this.comments3 = comments3;
	}

	public BigDecimal getShareDefine() {
		return shareDefine;
	}

	public void setShareDefine(BigDecimal shareDefine) {
		this.shareDefine = shareDefine;
	}

	public BigDecimal getSignedShares1() {
		return signedShares1;
	}

	public void setSignedShares1(BigDecimal signedShares1) {
		this.signedShares1 = signedShares1;
	}

	public BigDecimal getSignedShares2() {
		return signedShares2;
	}

	public void setSignedShares2(BigDecimal signedShares2) {
		this.signedShares2 = signedShares2;
	}

	public BigDecimal getSignedShares3() {
		return signedShares3;
	}

	public void setSignedShares3(BigDecimal signedShares3) {
		this.signedShares3 = signedShares3;
	}

	public BigDecimal getWrittenShare1() {
		return writtenShare1;
	}

	public void setWrittenShare1(BigDecimal writtenShare1) {
		this.writtenShare1 = writtenShare1;
	}

	public BigDecimal getWrittenShare2() {
		return writtenShare2;
	}

	public void setWrittenShare2(BigDecimal writtenShare2) {
		this.writtenShare2 = writtenShare2;
	}

	public BigDecimal getWrittenShare3() {
		return writtenShare3;
	}

	public void setWrittenShare3(BigDecimal writtenShare3) {
		this.writtenShare3 = writtenShare3;
	}

	public Long getLossId() {
		return lossId;
	}

	public void setLossId(Long lossId) {
		if (null != lossId && lossId != 0L) {
			isBCEmpty = false;
		}
		this.lossId = lossId;
	}

	public BigDecimal getCedentParticip() {
		return cedentParticip;
	}

	public void setCedentParticip(BigDecimal cedentParticip) {
		this.cedentParticip = cedentParticip;
	}

	public BigDecimal getFirstCalcAfter() {
		return firstCalcAfter;
	}

	public void setFirstCalcAfter(BigDecimal firstCalcAfter) {
		this.firstCalcAfter = firstCalcAfter;
	}

	public BigDecimal getMaxRatio() {
		return maxRatio;
	}

	public void setMaxRatio(BigDecimal maxRatio) {
		this.maxRatio = maxRatio;
	}

	public BigDecimal getMinRatio() {
		return minRatio;
	}

	public void setMinRatio(BigDecimal minRatio) {
		this.minRatio = minRatio;
	}

	public String getParticipBase() {
		return participBase;
	}

	public void setParticipBase(String participBase) {
		this.participBase = participBase;
	}

	public BigDecimal getSubCalcEvery() {
		return subCalcEvery;
	}

	public void setSubCalcEvery(BigDecimal subCalcEvery) {
		this.subCalcEvery = subCalcEvery;
	}

	public Long getDeductionsId() {
		return deductionsId;
	}

	public void setDeductionsId(Long deductionsId) {
		if (null != deductionsId && deductionsId != 0L) {
			isBCEmpty = false;
		}
		this.deductionsId = deductionsId;
	}

	public String getDeficitCarryForward() {
		return deficitCarryForward;
	}

	public void setDeficitCarryForward(String deficitCarryForward) {
		this.deficitCarryForward = deficitCarryForward;
	}

	public BigDecimal getFixedAmountHunredPercent() {
		return fixedAmountHunredPercent;
	}

	public void setFixedAmountHunredPercent(BigDecimal fixedAmountHunredPercent) {
		this.fixedAmountHunredPercent = fixedAmountHunredPercent;
	}

	public BigDecimal getFixedAmountOurShare() {
		return fixedAmountOurShare;
	}

	public void setFixedAmountOurShare(BigDecimal fixedAmountOurShare) {
		this.fixedAmountOurShare = fixedAmountOurShare;
	}

	public BigDecimal getNumberOfYears() {
		return numberOfYears;
	}

	public void setNumberOfYears(BigDecimal numberOfYears) {
		this.numberOfYears = numberOfYears;
	}

	public BigDecimal getPercentOfPremium() {
		return percentOfPremium;
	}

	public void setPercentOfPremium(BigDecimal percentOfPremium) {
		this.percentOfPremium = percentOfPremium;
	}

	public String getProfitPercentageType() {
		return profitPercentageType;
	}

	public void setProfitPercentageType(String profitPercentageType) {
		this.profitPercentageType = profitPercentageType;
	}

	public BigDecimal getRiPercentage() {
		return riPercentage;
	}

	public void setRiPercentage(BigDecimal riPercentage) {
		this.riPercentage = riPercentage;
	}

	public BigDecimal getProfitPercentage() {
		return profitPercentage;
	}

	public void setProfitPercentage(BigDecimal profitPercentage) {
		this.profitPercentage = profitPercentage;
	}

	public BigDecimal getExpensesPercentage() {
		return expensesPercentage;
	}

	public void setExpensesPercentage(BigDecimal expensesPercentage) {
		this.expensesPercentage = expensesPercentage;
	}

	public String getrICommission() {
		return rICommission;
	}

	public void setrICommission(String rICommission) {
		this.rICommission = rICommission;
	}

	public String getRemarkPanel() {
		return remarkPanel;
	}

	public void setRemarkPanel(String remarkPanel) {
		this.remarkPanel = remarkPanel;
	}

	public BigDecimal getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public BigDecimal getBrokerage2() {
		return brokerage2;
	}

	public void setBrokerage2(BigDecimal brokerage2) {
		this.brokerage2 = brokerage2;
	}

	public List<DeductionsItemVO> getOtherDeductionList() {
		return otherDeductionList;
	}

	public void setOtherDeductionList(List<DeductionsItemVO> otherDeductionList) {
		this.otherDeductionList = otherDeductionList;
	}

	public List<DeductionsItemVO> getDeductionsList() {
		return deductionsList;
	}

	public void setDeductionsList(List<DeductionsItemVO> deductionsList) {
		this.deductionsList = deductionsList;
	}

	public Long getPcSlidingId() {
		return pcSlidingId;
	}

	public void setPcSlidingId(Long pcSlidingId) {
		if (null != pcSlidingId && pcSlidingId != 0L) {
			isBCEmpty = false;
		}
		this.pcSlidingId = pcSlidingId;
	}

	public BigDecimal getMaximumLossPc() {
		return maximumLossPc;
	}

	public void setMaximumLossPc(BigDecimal maximumLossPc) {
		this.maximumLossPc = maximumLossPc;
	}

	public BigDecimal getMaximumRIPc() {
		return maximumRIPc;
	}

	public void setMaximumRIPc(BigDecimal maximumRIPc) {
		this.maximumRIPc = maximumRIPc;
	}

	public BigDecimal getMinimumLossPc() {
		return minimumLossPc;
	}

	public void setMinimumLossPc(BigDecimal minimumLossPc) {
		this.minimumLossPc = minimumLossPc;
	}

	public BigDecimal getMinimumRIPc() {
		return minimumRIPc;
	}

	public void setMinimumRIPc(BigDecimal minimumRIPc) {
		this.minimumRIPc = minimumRIPc;
	}

	public List<DeductionsAttachVO> getAttachTablePcList() {
		return attachTablePcList;
	}

	public void setAttachTablePcList(List<DeductionsAttachVO> attachTablePcList) {
		this.attachTablePcList = attachTablePcList;
	}

	public Long getCommSlidingDetailId() {
		return commSlidingDetailId;
	}

	public void setCommSlidingDetailId(Long commSlidingDetailId) {
		if (null != commSlidingDetailId && commSlidingDetailId != 0L) {
			isBCEmpty = false;
		}
		this.commSlidingDetailId = commSlidingDetailId;
	}

	public BigDecimal getFirstCal() {
		return firstCal;
	}

	public void setFirstCal(BigDecimal firstCal) {
		this.firstCal = firstCal;
	}

	public String getLossIncludeIBNR() {
		return lossIncludeIBNR;
	}

	public void setLossIncludeIBNR(String lossIncludeIBNR) {
		this.lossIncludeIBNR = lossIncludeIBNR;
	}

	public BigDecimal getMaximumLossSs() {
		return maximumLossSs;
	}

	public void setMaximumLossSs(BigDecimal maximumLossSs) {
		this.maximumLossSs = maximumLossSs;
	}

	public BigDecimal getMaximumRISs() {
		return maximumRISs;
	}

	public void setMaximumRISs(BigDecimal maximumRISs) {
		this.maximumRISs = maximumRISs;
	}

	public BigDecimal getMinimumLossSs() {
		return minimumLossSs;
	}

	public void setMinimumLossSs(BigDecimal minimumLossSs) {
		this.minimumLossSs = minimumLossSs;
	}

	public BigDecimal getMinimumRISs() {
		return minimumRISs;
	}

	public void setMinimumRISs(BigDecimal minimumRISs) {
		this.minimumRISs = minimumRISs;
	}

	public BigDecimal getSubsequentCalc() {
		return subsequentCalc;
	}

	public void setSubsequentCalc(BigDecimal subsequentCalc) {
		this.subsequentCalc = subsequentCalc;
	}

	public List<DeductionsCarriedVO> getDeductionsCarriedList() {
		return deductionsCarriedList;
	}

	public void setDeductionsCarriedList(List<DeductionsCarriedVO> deductionsCarriedList) {
		this.deductionsCarriedList = deductionsCarriedList;
	}

	public String getClausesRequired() {
		return clausesRequired;
	}

	public void setClausesRequired(String clausesRequired) {
		if (!StringUtils.isNullOrEmpty(clausesRequired)) {
			this.clausesRequiredList = DataTypeConvertor.transStringToSelectTree(clausesRequired);
		}
		this.clausesRequired = clausesRequired;
	}

	public String getClausesRecommend() {
		return clausesRecommend;
	}

	public void setClausesRecommend(String clausesRecommend) {
		if (!StringUtils.isNullOrEmpty(clausesRecommend)) {
			this.clausesRecommendList = DataTypeConvertor.transStringToSelectTree(clausesRecommend);
		}
		this.clausesRecommend = clausesRecommend;
	}

	public List<PremiumItemVO> getDeletePremiumList() {
		return deletePremiumList;
	}

	public void setDeletePremiumList(List<PremiumItemVO> deletePremiumList) {
		this.deletePremiumList = deletePremiumList;
	}

	public boolean isBCEmpty() {
		return isBCEmpty;
	}

	public void setBCEmpty(boolean isBCEmpty) {
		this.isBCEmpty = isBCEmpty;
	}

	public void resetIds() {
		this.shareId = null;
		this.premiumId = null;
		this.limitId = null;
		this.lossId = null;
		this.deductionsId = null;
		this.reserveId = null;
		this.clausesId = null;
		this.reinId = null;
		for (CurrencyVO vo : currencyList) {
			vo.setCurrencyId(null);
			vo.setContCompId(null);
		}
		// Reset Premium List, because the premium list should be convert the
		// view premium list, reduce the reset logic & convert again
		for (PremiumItemVO itemVO : premiumList) {
			itemVO.setItemId(null);
			itemVO.setPremiumId(null);
		}
		// Reset limit List, because the limit list should be convert to view
		// Limit list, reduce the reset logic & convert again
		for (LimitItemVO itemVO : limitItemList) {
			itemVO.setItemId(null);
			itemVO.setLimitId(null);
		}

		for (LimitEventVO itemVO : limitEventList) {
			itemVO.setEventId(null);
			itemVO.setLimitId(null);
		}

		for (ReinstateItemVO itemVO : reinstateList) {
			itemVO.setItemId(null);
			itemVO.setReinId(null);
		}

		for (DeductionsItemVO itemVO : deductionsList) {
			itemVO.setDeductionsId(null);
			itemVO.setDeductionsItemId(null);
		}

		this.pcSlidingId = null;
		this.commSlidingDetailId = null;
	}

	public List<LimitItemVO> getDeleteLimitItemList() {
		return deleteLimitItemList;
	}

	public void setDeleteLimitItemList(List<LimitItemVO> deleteLimitItemList) {
		this.deleteLimitItemList = deleteLimitItemList;
	}

	public List<LimitEventVO> getDeleteLimitEventList() {
		return deleteLimitEventList;
	}

	public void setDeleteLimitEventList(List<LimitEventVO> deleteLimitEventList) {
		this.deleteLimitEventList = deleteLimitEventList;
	}

	public List<DeductionsItemVO> getDeleteDeductionsList() {
		return deleteDeductionsList;
	}

	public void setDeleteDeductionsList(List<DeductionsItemVO> deleteDeductionsList) {
		this.deleteDeductionsList = deleteDeductionsList;
	}

	public String getNoOfInstallment() {
		return NoOfInstallment;
	}

	public void setNoOfInstallment(String noOfInstallment) {
		NoOfInstallment = noOfInstallment;
	}

	public String getNoOfPayment() {
		return NoOfPayment;
	}

	public void setNoOfPayment(String noOfPayment) {
		NoOfPayment = noOfPayment;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public List<LimitEventVO> getLimitEventQsList() {
		return limitEventQsList;
	}

	public void setLimitEventQsList(List<LimitEventVO> limitEventQsList) {
		limitEventList.addAll(limitEventQsList);
		this.limitEventQsList = limitEventQsList;
	}

	public List<LimitEventVO> getLimitEventSurplusList() {
		return limitEventSurplusList;
	}

	public void setLimitEventSurplusList(List<LimitEventVO> limitEventSurplusList) {
		limitEventList.addAll(limitEventSurplusList);
		this.limitEventSurplusList = limitEventSurplusList;
	}

	public List<LimitEventVO> getLimitEventXolList() {
		return limitEventXolList;
	}

	public void setLimitEventXolList(List<LimitEventVO> limitEventXolList) {
		limitEventList.addAll(limitEventXolList);
		this.limitEventXolList = limitEventXolList;
	}

	public List<LimitEventVO> getLimitEventStoplossList() {
		return limitEventStoplossList;
	}

	public void setLimitEventStoplossList(List<LimitEventVO> limitEventStoplossList) {
		limitEventList.addAll(limitEventStoplossList);
		this.limitEventStoplossList = limitEventStoplossList;
	}

	public List<DeductionsAttachVO> getAttachTableCommList() {
		return attachTableCommList;
	}

	public void setAttachTableCommList(List<DeductionsAttachVO> attachTableCommList) {
		this.attachTableCommList = attachTableCommList;
	}

	public List<DeductionsAttachVO> getDeleteAttachTablePcList() {
		return deleteAttachTablePcList;
	}

	public void setDeleteAttachTablePcList(List<DeductionsAttachVO> deleteAttachTablePcList) {
		this.deleteAttachTablePcList = deleteAttachTablePcList;
	}

	public List<DeductionsAttachVO> getDeleteAttachTableCommList() {
		return deleteAttachTableCommList;
	}

	public void setDeleteAttachTableCommList(List<DeductionsAttachVO> deleteAttachTableCommList) {
		this.deleteAttachTableCommList = deleteAttachTableCommList;
	}

}
