package com.ebao.ap99.contract.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DeductionsCommVO {
	private Long commSlidingDetailId;
	private Long deductionsId;
	private BigDecimal firstCal;
	private String lossIncludeIBNR;
	private BigDecimal maximumLossSs;
	private BigDecimal maximumRISs;
	private BigDecimal minimumLossSs;
	private BigDecimal minimumRISs;
	private BigDecimal subsequentCalc;
	private List<DeductionsAttachVO> DeductionsAttach = new ArrayList<DeductionsAttachVO>();
	private List<DeductionsAttachVO> deleteDeductionsAttach = new ArrayList<DeductionsAttachVO>();
	private List<DeductionsCarriedVO> DeductionsCarried = new ArrayList<DeductionsCarriedVO>();
	private String operateType;

	public Long getCommSlidingDetailId() {
		return commSlidingDetailId;
	}

	public void setCommSlidingDetailId(Long commSlidingDetailId) {
		this.commSlidingDetailId = commSlidingDetailId;
	}

	public Long getDeductionsId() {
		return deductionsId;
	}

	public void setDeductionsId(Long deductionsId) {
		this.deductionsId = deductionsId;
	}

	public BigDecimal getFirstCal() {
		return firstCal;
	}

	public void setFirstCal(BigDecimal firstCal) {
		this.firstCal = firstCal;
	}

	public String getLossIncludeIBNR() {
		return lossIncludeIBNR;
	}

	public void setLossIncludeIBNR(String lossIncludeIBNR) {
		this.lossIncludeIBNR = lossIncludeIBNR;
	}

	public BigDecimal getMaximumLossSs() {
		return maximumLossSs;
	}

	public void setMaximumLossSs(BigDecimal maximumLossSs) {
		this.maximumLossSs = maximumLossSs;
	}

	public BigDecimal getMaximumRISs() {
		return maximumRISs;
	}

	public void setMaximumRISs(BigDecimal maximumRISs) {
		this.maximumRISs = maximumRISs;
	}

	public BigDecimal getMinimumLossSs() {
		return minimumLossSs;
	}

	public void setMinimumLossSs(BigDecimal minimumLossSs) {
		this.minimumLossSs = minimumLossSs;
	}

	public BigDecimal getMinimumRISs() {
		return minimumRISs;
	}

	public void setMinimumRISs(BigDecimal minimumRISs) {
		this.minimumRISs = minimumRISs;
	}

	public BigDecimal getSubsequentCalc() {
		return subsequentCalc;
	}

	public void setSubsequentCalc(BigDecimal subsequentCalc) {
		this.subsequentCalc = subsequentCalc;
	}

	public List<DeductionsAttachVO> getDeductionsAttach() {
		return DeductionsAttach;
	}

	public void setDeductionsAttach(List<DeductionsAttachVO> DeductionsAttach) {
		this.DeductionsAttach = DeductionsAttach;
	}

	public List<DeductionsCarriedVO> getDeductionsCarried() {
		return DeductionsCarried;
	}

	public void setDeductionsCarried(List<DeductionsCarriedVO> deductionsCarried) {
		this.DeductionsCarried = deductionsCarried;
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