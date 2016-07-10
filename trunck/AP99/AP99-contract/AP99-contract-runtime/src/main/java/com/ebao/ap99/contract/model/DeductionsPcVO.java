package com.ebao.ap99.contract.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DeductionsPcVO {
	private Long pcSlidingId;
	private Long deductionsId;
	private BigDecimal maximumLossPc;
	private BigDecimal maximumRIPc;
	private BigDecimal minimumLossPc;
	private BigDecimal minimumRIPc;
	private List<DeductionsAttachVO> deductionsAttach = new ArrayList<DeductionsAttachVO>();
	private List<DeductionsAttachVO> deleteDeductionsAttach = new ArrayList<DeductionsAttachVO>();
	private String operateType;

	public Long getPcSlidingId() {
		return pcSlidingId;
	}

	public void setPcSlidingId(Long pcSlidingId) {
		this.pcSlidingId = pcSlidingId;
	}

	public Long getDeductionsId() {
		return deductionsId;
	}

	public void setDeductionsId(Long deductionsId) {
		this.deductionsId = deductionsId;
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

	public List<DeductionsAttachVO> getDeductionsAttach() {
		return deductionsAttach;
	}

	public void setDeductionsAttach(List<DeductionsAttachVO> deductionsAttach) {
		this.deductionsAttach = deductionsAttach;
	}

	public List<DeductionsAttachVO> getDeleteDeductionsAttach() {
		return deleteDeductionsAttach;
	}

	public void setDeleteDeductionsAttach(List<DeductionsAttachVO> deleteDeductionsAttach) {
		this.deleteDeductionsAttach = deleteDeductionsAttach;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

}
