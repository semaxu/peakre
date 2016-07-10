package com.ebao.ap99.contract.model.BusinessModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.ebao.ap99.contract.entity.TRiContractInfo;

@XmlAccessorType(XmlAccessType.FIELD)
public class ContractBO extends TRiContractInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ContractMoreInfoBO moreInfoBO = new ContractMoreInfoBO();
	private ContractAreaperilBO areaperilBO = new ContractAreaperilBO();
	// private BusinessConditionBO businessBO = new BusinessConditionBO();
	private ClaimConditionBO claimInfoBO = new ClaimConditionBO();
	private AccountingConditionBO accountingBO = new AccountingConditionBO();
	private CancellationBO cancelBO = new CancellationBO();
	private List<SectionBO> sectionList = new ArrayList<SectionBO>();
	@XmlTransient
	private List<Long> deleteSectionList = new ArrayList<Long>();

	public List<SectionBO> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<SectionBO> sectionBO) {
		this.sectionList = sectionBO;
	}

	// public BusinessConditionBO getBusinessBO() {
	// return businessBO;
	// }
	//
	// public void setBusinessBO(BusinessConditionBO businessBO) {
	// this.businessBO = businessBO;
	// }

	public ContractMoreInfoBO getMoreInfoBO() {
		return moreInfoBO;
	}

	public void setMoreInfoBO(ContractMoreInfoBO moreInfoBO) {
		this.moreInfoBO = moreInfoBO;
	}

	public ClaimConditionBO getClaimInfoBO() {
		return claimInfoBO;
	}

	public void setClaimInfoBO(ClaimConditionBO claimInfoBO) {
		this.claimInfoBO = claimInfoBO;
	}

	public AccountingConditionBO getAccountingBO() {
		return accountingBO;
	}

	public void setAccountingBO(AccountingConditionBO accountingBO) {
		this.accountingBO = accountingBO;
	}

	public CancellationBO getCancelBO() {
		return cancelBO;
	}

	public void setCancelBO(CancellationBO cancelBO) {
		this.cancelBO = cancelBO;
	}

	public ContractAreaperilBO getAreaperilBO() {
		return areaperilBO;
	}

	public void setAreaperilBO(ContractAreaperilBO areaperilBO) {
		this.areaperilBO = areaperilBO;
	}

	public List<Long> getDeleteSectionList() {
		return deleteSectionList;
	}

	public void setDeleteSectionList(List<Long> deleteSectionList) {
		this.deleteSectionList = deleteSectionList;
	}

}
