/**
 * 
 */
package com.ebao.ap99.contract.model;

import com.ebao.ap99.contract.entity.TRiContAccount;

/**
 * @author weiping.wang
 */
public class ContractAccountCondition extends TRiContAccount {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private ContractInfo contractInfo;

    public ContractInfo getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(ContractInfo contractInfo) {
        this.contractInfo = contractInfo;
    }

}
