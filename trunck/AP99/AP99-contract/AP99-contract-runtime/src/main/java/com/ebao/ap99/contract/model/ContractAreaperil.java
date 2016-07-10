/**
 * 
 */
package com.ebao.ap99.contract.model;

import com.ebao.ap99.contract.entity.TRiContractAreaperil;

/**
 * @author weiping.wang
 */
public class ContractAreaperil extends TRiContractAreaperil {

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
