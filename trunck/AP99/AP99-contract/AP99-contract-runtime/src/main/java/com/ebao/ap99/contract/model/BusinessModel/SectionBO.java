package com.ebao.ap99.contract.model.BusinessModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.ebao.ap99.contract.entity.TRiContRetro;
import com.ebao.ap99.contract.entity.TRiContSectionInfo;
import com.ebao.ap99.parent.DataTypeConvertor;

@XmlAccessorType(XmlAccessType.FIELD)
public class SectionBO extends TRiContSectionInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlTransient
	private Long parentId;
	@XmlTransient
	private String mainCurrency;
	@XmlTransient
	private BigDecimal ourShare;
	@XmlTransient
	private BigDecimal premium;
	@XmlTransient
	private BigDecimal deductions;
	@XmlTransient
	private List<String> coBList = new ArrayList<String>();
	@XmlTransient
	private List<String> loBList = new ArrayList<String>();
	@XmlTransient
	private List<String> currencyList = new ArrayList<String>();
	@XmlTransient
	private List<String> uwAreaList = new ArrayList<String>();
	@XmlTransient
	private List<String> coveredAreaList = new ArrayList<String>();
	private List<SubsectionBO> subsectionList = new ArrayList<SubsectionBO>();
	private List<Long> deleteSubsectionList = new ArrayList<Long>();
	private List<TRiContRetro> retroList = new ArrayList<TRiContRetro>();
	private BusinessConditionBO businessBO = new BusinessConditionBO();
	private PricingEstimateBO pricingBO = new PricingEstimateBO();
//	private boolean validClaim;
//	private boolean validAccount;
	private boolean hasInforce;
	private String endoType;
	
	public boolean isHasInforce() {
		return hasInforce;
	}

	public void setHasInforce(boolean hasInforce) {
		this.hasInforce = hasInforce;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<String> getCoBList() {
		return DataTypeConvertor.transStringToSelectTree(this.getCoB());
	}

	public void setCoBList(List<String> coBList) {
		this.setCoB(DataTypeConvertor.parseSelectTreeToString(coBList));
		this.coBList = coBList;
	}

	public List<String> getLoBList() {
		return DataTypeConvertor.transStringToSelectTree(this.getLoB());
	}

	public void setLoBList(List<String> loBList) {
		this.setLoB(DataTypeConvertor.parseSelectTreeToString(loBList));
		this.loBList = loBList;
	}

	public List<String> getCurrencyList() {
		return DataTypeConvertor.transStringToSelectTree(this.getCurrency());
	}

	public void setCurrencyList(List<String> currencyList) {
		this.setCurrency(DataTypeConvertor.parseSelectTreeToString(currencyList));
		this.currencyList = currencyList;
	}

	public List<String> getUwAreaList() {
		return DataTypeConvertor.transStringToSelectTree(this.getUwArea());
	}

	public void setUwAreaList(List<String> uwAreaList) {
		this.setUwArea(DataTypeConvertor.parseSelectTreeToString(uwAreaList));
		this.uwAreaList = uwAreaList;
	}

	public List<String> getCoveredAreaList() {
		return DataTypeConvertor.transStringToSelectTree(this.getCoveredArea());
	}

	public void setCoveredAreaList(List<String> coveredAreaList) {
		this.setCoveredArea(DataTypeConvertor.parseSelectTreeToString(coveredAreaList));
		this.coveredAreaList = coveredAreaList;
	}

	public List<SubsectionBO> getSubsectionList() {
		return subsectionList;
	}

	public void setSubsectionList(List<SubsectionBO> subsectionList) {
		this.subsectionList = subsectionList;
	}

	public BusinessConditionBO getBusinessBO() {
		return businessBO;
	}

	public void setBusinessBO(BusinessConditionBO businessBO) {
		this.businessBO = businessBO;
	}

	public List<TRiContRetro> getRetroList() {
		return retroList;
	}

	public void setRetroList(List<TRiContRetro> retroList) {
		this.retroList = retroList;
	}

	public PricingEstimateBO getPricingBO() {
		return pricingBO;
	}

	public void setPricingBO(PricingEstimateBO pricingBO) {
		this.pricingBO = pricingBO;
	}

	public String getMainCurrency() {
		return mainCurrency;
	}

	public void setMainCurrency(String mainCurrency) {
		this.mainCurrency = mainCurrency;
	}

	public BigDecimal getOurShare() {
		return ourShare;
	}

	public void setOurShare(BigDecimal ourShare) {
		this.ourShare = ourShare;
	}

	public BigDecimal getPremium() {
		return premium;
	}

	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}

	public BigDecimal getDeductions() {
		return deductions;
	}

	public void setDeductions(BigDecimal deductions) {
		this.deductions = deductions;
	}

//	public boolean isValidClaim() {
//		return validClaim;
//	}
//
//	public void setValidClaim(boolean validClaim) {
//		this.validClaim = validClaim;
//	}
//
//	public boolean isValidAccount() {
//		return validAccount;
//	}
//
//	public void setValidAccount(boolean validAccount) {
//		this.validAccount = validAccount;
//	}

	public String getEndoType() {
		return endoType;
	}

	public void setEndoType(String endoType) {
		this.endoType = endoType;
	}

	public List<Long> getDeleteSubsectionList() {
		return deleteSubsectionList;
	}

	public void setDeleteSubsectionList(List<Long> deleteSubsectionList) {
		this.deleteSubsectionList = deleteSubsectionList;
	}

}
