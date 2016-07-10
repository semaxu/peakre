/**
 * 
 */
package com.ebao.ap99.contract.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ebao.ap99.parent.DataTypeConvertor;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author weiping.wang
 *
 */
public class ContractSectionVO {
	private Long contCompId;
	private Long parentId;
	private String shareType;
	private String sectionName;
	private Long operateId;
	private String operateType;

	private List<String> coBList = new ArrayList<String>();
	private List<String> loBList = new ArrayList<String>();
	private List<String> currencyList = new ArrayList<String>();
	private List<String> uwAreaList = new ArrayList<String>();
	private List<String> coveredAreaList = new ArrayList<String>();
	private List<ContractSubsectionVO> subsectionList = new ArrayList<ContractSubsectionVO>();
	private List<Long> deleteSubSections = new ArrayList<Long>();
	private List<String> coveredList = new ArrayList<String>();
	private List<String> perilList = new ArrayList<String>();

	private String contractCategory;
	private String contractNature;
	private String mainCurrency;
	private Date reinsStarting;
	private BigDecimal ourShare;
	private BigDecimal premium;
	private BigDecimal deductions;
	@JsonIgnore
	private String covered;
	@JsonIgnore
	private String peril;
	@JsonIgnore
	private String coB;
	@JsonIgnore
	private String loB;
	@JsonIgnore
	private String currency;
	@JsonIgnore
	private String uwArea;
	@JsonIgnore
	private String coveredArea;
	@JsonIgnore
	private List<RetrocessionItemVO> retrocessionVO = new ArrayList<RetrocessionItemVO>();

	private BusinessConditionVO businessConditionVO;
	// private boolean validClaim;
	// private boolean validAccount;
	private String endoType;
	private boolean hasInforce;

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<String> getCoBList() {
		return coBList;
	}

	public void setCoBList(List<String> coBList) {
		this.coB = DataTypeConvertor.parseSelectTreeToString(coBList);
		this.coBList = coBList;
	}

	public List<String> getLoBList() {
		return loBList;
	}

	public void setLoBList(List<String> loBList) {
		this.loB = DataTypeConvertor.parseSelectTreeToString(loBList);
		this.loBList = loBList;
	}

	public List<String> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<String> currencyList) {
		this.currency = DataTypeConvertor.parseSelectTreeToString(currencyList);
		this.currencyList = currencyList;
	}

	public List<String> getUwAreaList() {
		return uwAreaList;
	}

	public void setUwAreaList(List<String> uwAreaList) {
		this.uwArea = DataTypeConvertor.parseSelectTreeToString(uwAreaList);
		this.uwAreaList = uwAreaList;
	}

	public List<String> getCoveredAreaList() {
		return coveredAreaList;
	}

	public void setCoveredAreaList(List<String> coveredAreaList) {
		this.coveredArea = DataTypeConvertor.parseSelectTreeToString(coveredAreaList);
		this.coveredAreaList = coveredAreaList;
	}

	public List<String> getCoveredList() {
		return coveredList;
	}

	public void setCoveredList(List<String> coveredList) {
		this.covered = DataTypeConvertor.parseSelectTreeToString(coveredList);
		this.coveredList = coveredList;
	}

	public String getCovered() {
		return covered;
	}

	public void setCovered(String covered) {
		this.coveredList = DataTypeConvertor.transStringToSelectTree(covered);
		this.covered = covered;
	}

	public String getPeril() {
		return peril;
	}

	public void setPeril(String peril) {
		this.perilList = DataTypeConvertor.transStringToSelectTree(peril);
		this.peril = peril;
	}

	public List<String> getPerilList() {
		return perilList;
	}

	public void setPerilList(List<String> perilList) {
		this.peril = DataTypeConvertor.parseSelectTreeToString(perilList);
		this.perilList = perilList;
	}

	public String getContractNature() {
		return contractNature;
	}

	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}

	public String getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(String contractCategory) {
		this.contractCategory = contractCategory;
	}

	public String getCoB() {
		return coB;
	}

	public void setCoB(String coB) {
		this.coBList = DataTypeConvertor.transStringToSelectTree(coB);
		this.coB = coB;
	}

	public String getLoB() {
		return loB;
	}

	public void setLoB(String loB) {
		this.loBList = DataTypeConvertor.transStringToSelectTree(loB);
		this.loB = loB;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currencyList = DataTypeConvertor.transStringToSelectTree(currency);
		this.currency = currency;
	}

	public String getUwArea() {
		return uwArea;
	}

	public void setUwArea(String uwArea) {
		this.uwAreaList = DataTypeConvertor.transStringToSelectTree(uwArea);
		this.uwArea = uwArea;
	}

	public String getCoveredArea() {
		return coveredArea;
	}

	public void setCoveredArea(String coveredArea) {
		this.coveredAreaList = DataTypeConvertor.transStringToSelectTree(coveredArea);
		this.coveredArea = coveredArea;
	}

	public List<RetrocessionItemVO> getRetrocessionVO() {
		return retrocessionVO;
	}

	public void setRetrocessionVO(List<RetrocessionItemVO> retrocessionVO) {
		this.retrocessionVO = retrocessionVO;
	}

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public List<ContractSubsectionVO> getSubsectionList() {
		return subsectionList;
	}

	public void setSubsectionList(List<ContractSubsectionVO> subsectionList) {
		this.subsectionList = subsectionList;
	}

	public List<Long> getDeleteSubSections() {
		return deleteSubSections;
	}

	public void setDeleteSubSections(List<Long> deleteSubSections) {
		this.deleteSubSections = deleteSubSections;
	}

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
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

	public BusinessConditionVO getBusinessConditionVO() {
		return businessConditionVO;
	}

	public void setBusinessConditionVO(BusinessConditionVO businessConditionVO) {
		this.businessConditionVO = businessConditionVO;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	// public boolean isValidClaim() {
	// return validClaim;
	// }
	//
	// public void setValidClaim(boolean validClaim) {
	// this.validClaim = validClaim;
	// }
	//
	// public boolean isValidAccount() {
	// return validAccount;
	// }
	//
	// public void setValidAccount(boolean validAccount) {
	// this.validAccount = validAccount;
	// }

	public String getEndoType() {
		return endoType;
	}

	public void setEndoType(String endoType) {
		this.endoType = endoType;
	}

	public boolean isHasInforce() {
		return hasInforce;
	}

	public void setHasInforce(boolean hasInforce) {
		this.hasInforce = hasInforce;
	}

	public Date getReinsStarting() {
		return reinsStarting;
	}

	public void setReinsStarting(Date reinsStarting) {
		this.reinsStarting = reinsStarting;
	}

}
