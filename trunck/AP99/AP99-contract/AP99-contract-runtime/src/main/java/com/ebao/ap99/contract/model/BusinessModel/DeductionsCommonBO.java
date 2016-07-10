package com.ebao.ap99.contract.model.BusinessModel;

import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.contract.entity.TRiContDeductionsAttach;
import com.ebao.ap99.contract.entity.TRiContDeductionsCarried;
import com.ebao.ap99.contract.entity.TRiContDeductionsComm;

public class DeductionsCommonBO extends TRiContDeductionsComm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TRiContDeductionsCarried> carrieds = new ArrayList<TRiContDeductionsCarried>();
	private List<TRiContDeductionsAttach> attachments = new ArrayList<TRiContDeductionsAttach>();
	
	public List<TRiContDeductionsCarried> getCarrieds() {
		return carrieds;
	}
	public void setCarrieds(List<TRiContDeductionsCarried> carrieds) {
		this.carrieds = carrieds;
	}
	public List<TRiContDeductionsAttach> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<TRiContDeductionsAttach> attachments) {
		this.attachments = attachments;
	}

}
