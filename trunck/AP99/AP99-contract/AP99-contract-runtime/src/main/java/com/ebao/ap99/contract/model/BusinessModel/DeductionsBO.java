package com.ebao.ap99.contract.model.BusinessModel;

import com.ebao.ap99.contract.entity.TRiContDeductions;

public class DeductionsBO extends TRiContDeductions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DeductionsPcBO deductionPc;
	private DeductionsCommonBO deductionComm;

	public DeductionsPcBO getDeductionPc() {
		return deductionPc;
	}

	public void setDeductionPc(DeductionsPcBO deductionPc) {
		this.deductionPc = deductionPc;
	}

	public DeductionsCommonBO getDeductionComm() {
		return deductionComm;
	}

	public void setDeductionComm(DeductionsCommonBO deductionComm) {
		this.deductionComm = deductionComm;
	}
}
