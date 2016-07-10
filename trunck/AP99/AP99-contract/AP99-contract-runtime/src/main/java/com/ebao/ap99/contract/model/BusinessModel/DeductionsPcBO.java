package com.ebao.ap99.contract.model.BusinessModel;

import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.contract.entity.TRiContDeductionsAttach;
import com.ebao.ap99.contract.entity.TRiContDeductionsPc;

public class DeductionsPcBO extends TRiContDeductionsPc {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TRiContDeductionsAttach> attachList = new ArrayList<TRiContDeductionsAttach>();
	
	public List<TRiContDeductionsAttach> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<TRiContDeductionsAttach> attachList) {
		this.attachList = attachList;
	}
	
}
