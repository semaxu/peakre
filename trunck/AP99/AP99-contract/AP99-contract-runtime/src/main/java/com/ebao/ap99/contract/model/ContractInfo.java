/**
 * 
 */
package com.ebao.ap99.contract.model;

import java.util.Date;

import com.ebao.ap99.contract.entity.TRiContractInfo;

/**
 * @author weiping.wang
 */
public class ContractInfo extends TRiContractInfo {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Date createdOn;
    private Date lastChangedOn;

    private ContractAreaperil        contractAreaperil;
    private ContractAccountCondition contractAccountCondition;
    private ContractMoreInfo         contractMoreInfo;
    private ContractClaimCondition   contractClaimCondition;
    private ContractCancel           contractCancel;

    public ContractAreaperil getContractAreaperil() {
        return contractAreaperil;
    }

    public void setContractAreaperil(ContractAreaperil contractAreaperil) {
        this.contractAreaperil = contractAreaperil;
    }

    public ContractAccountCondition getContractAccountCondition() {
        return contractAccountCondition;
    }

    public void setContractAccountCondition(ContractAccountCondition contractAccountCondition) {
        this.contractAccountCondition = contractAccountCondition;
    }

    public ContractMoreInfo getContractMoreInfo() {
        return contractMoreInfo;
    }

    public void setContractMoreInfo(ContractMoreInfo contractMoreInfo) {
        this.contractMoreInfo = contractMoreInfo;
    }

    public ContractClaimCondition getContractClaimCondition() {
        return contractClaimCondition;
    }

    public void setContractClaimCondition(ContractClaimCondition contractClaimCondition) {
        this.contractClaimCondition = contractClaimCondition;
    }

	public ContractCancel getContractCancel() {
		return contractCancel;
	}

	public void setContractCancel(ContractCancel contractCancel) {
		this.contractCancel = contractCancel;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastChangedOn() {
		return lastChangedOn;
	}

	public void setLastChangedOn(Date lastChangedOn) {
		this.lastChangedOn = lastChangedOn;
	}

}
