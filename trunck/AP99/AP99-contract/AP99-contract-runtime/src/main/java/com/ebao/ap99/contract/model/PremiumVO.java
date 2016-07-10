package com.ebao.ap99.contract.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.contract.util.ContractCst;

public class PremiumVO {

	private Long contCompId;
	private String contractNature;

	// premium
	private Long premiumId;
	private String epiType;
	private String premiumType;
	private String definedIn;
	private BigDecimal lossrateFrom;
	private BigDecimal lossrateTo;
	private BigDecimal premiumrateFrom;
	private BigDecimal premiumrateTo;
	private BigDecimal provisionalRate;
	private BigDecimal rate;
	private String premiumRemark;
	private String NoOfInstallment;
	private String NoOfPayment;
	private List<PremiumItemVO> epiList = new ArrayList<PremiumItemVO>();
	private List<PremiumItemVO> terrorismList = new ArrayList<PremiumItemVO>();
	private List<PremiumItemVO> supiList = new ArrayList<PremiumItemVO>();
	private List<PremiumItemVO> floatPremiumList = new ArrayList<PremiumItemVO>();
	private List<PremiumItemVO> fixRateList = new ArrayList<PremiumItemVO>();
	private List<PremiumItemVO> swingRateList = new ArrayList<PremiumItemVO>();
	// private List<PremiumItemVO> minimumList = new ArrayList<PremiumItemVO>();
	// private List<PremiumItemVO> depositPremiumList = new
	// ArrayList<PremiumItemVO>();
	// private List<PremiumItemVO> depositScheduleList = new
	// ArrayList<PremiumItemVO>();
	private List<PremiumItemVO> premiumList = new ArrayList<PremiumItemVO>();

	private List<PremiumItemVO> minimumPremiumList = new ArrayList<PremiumItemVO>();
	private List<PremiumItemVO> depositPremiumList = new ArrayList<PremiumItemVO>();
	private List<PremiumItemVO> depositScheduleList = new ArrayList<PremiumItemVO>();

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public String getContractNature() {
		return contractNature;
	}

	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}

	public String getEpiType() {
		return epiType;
	}

	public void setEpiType(String epiType) {
		this.epiType = epiType;
	}

	public String getPremiumType() {
		return premiumType;
	}

	public void setPremiumType(String premiumType) {
		this.premiumType = premiumType;
	}

	public String getDefinedIn() {
		return definedIn;
	}

	public void setDefinedIn(String definedIn) {
		this.definedIn = definedIn;
	}

	public BigDecimal getLossrateFrom() {
		return lossrateFrom;
	}

	public void setLossrateFrom(BigDecimal lossrateFrom) {
		this.lossrateFrom = lossrateFrom;
	}

	public BigDecimal getLossrateTo() {
		return lossrateTo;
	}

	public void setLossrateTo(BigDecimal lossrateTo) {
		this.lossrateTo = lossrateTo;
	}

	public BigDecimal getPremiumrateFrom() {
		return premiumrateFrom;
	}

	public void setPremiumrateFrom(BigDecimal premiumrateFrom) {
		this.premiumrateFrom = premiumrateFrom;
	}

	public BigDecimal getPremiumrateTo() {
		return premiumrateTo;
	}

	public void setPremiumrateTo(BigDecimal premiumrateTo) {
		this.premiumrateTo = premiumrateTo;
	}

	public BigDecimal getProvisionalRate() {
		return provisionalRate;
	}

	public void setProvisionalRate(BigDecimal provisionalRate) {
		this.provisionalRate = provisionalRate;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getPremiumRemark() {
		return premiumRemark;
	}

	public void setPremiumRemark(String premiumRemark) {
		this.premiumRemark = premiumRemark;
	}

	public List<PremiumItemVO> getEpiList() {
		return epiList;
	}

	public void setEpiList(List<PremiumItemVO> epiList) {
		premiumList.addAll(epiList);
		this.epiList = epiList;
	}

	public List<PremiumItemVO> getTerrorismList() {
		return terrorismList;
	}

	public void setTerrorismList(List<PremiumItemVO> terrorismList) {
		premiumList.addAll(terrorismList);
		this.terrorismList = terrorismList;
	}

	public List<PremiumItemVO> getSupiList() {
		return supiList;
	}

	public void setSupiList(List<PremiumItemVO> supiList) {
		premiumList.addAll(supiList);
		this.supiList = supiList;
	}

	public List<PremiumItemVO> getFloatPremiumList() {
		return floatPremiumList;
	}

	public void setFloatPremiumList(List<PremiumItemVO> floatPremiumList) {
		premiumList.addAll(floatPremiumList);
		this.floatPremiumList = floatPremiumList;
	}

	public List<PremiumItemVO> getFixRateList() {
		return fixRateList;
	}

	public void setFixRateList(List<PremiumItemVO> fixRateList) {
		premiumList.addAll(fixRateList);
		this.fixRateList = fixRateList;
	}

	public List<PremiumItemVO> getSwingRateList() {
		return swingRateList;
	}

	public void setSwingRateList(List<PremiumItemVO> swingRateList) {
		premiumList.addAll(swingRateList);
		this.swingRateList = swingRateList;
	}

	public List<PremiumItemVO> getMinimumPremiumList() {
		return minimumPremiumList;
	}

	public void setMinimumPremiumList(List<PremiumItemVO> minimumPremiumList) {
		premiumList.addAll(minimumPremiumList);

		this.minimumPremiumList = minimumPremiumList;
	}

	public List<PremiumItemVO> getDepositPremiumList() {
		return depositPremiumList;
	}

	public void setDepositPremiumList(List<PremiumItemVO> depositPremiumList) {
		premiumList.addAll(depositPremiumList);

		this.depositPremiumList = depositPremiumList;
	}

	public List<PremiumItemVO> getDepositScheduleList() {
		return depositScheduleList;
	}

	public void setDepositScheduleList(List<PremiumItemVO> depositScheduleList) {

		premiumList.addAll(depositPremiumList);

		this.depositScheduleList = depositScheduleList;
	}

	public Long getPremiumId() {
		return premiumId;
	}

	public void setPremiumId(Long premiumId) {
		this.premiumId = premiumId;
	}

	public List<PremiumItemVO> getPremiumList() {
		for (PremiumItemVO vo : premiumList) {
			if (ContractCst.CONTRACT_PREMIUM_EPI.equals(vo.getItemType())) {
				this.getEpiList().add(vo);
			} else if (ContractCst.CONTRACT_PREMIUM_TERRORISM.equals(vo.getItemType())) {
				this.getTerrorismList().add(vo);
			} else if (ContractCst.CONTRACT_PREMIUM_SUPI.equals(vo.getItemType())) {
				this.getSupiList().add(vo);
			} else if (ContractCst.CONTRACT_PREMIUM_FLAT.equals(vo.getItemType())) {
				this.getFloatPremiumList().add(vo);
			} else if (ContractCst.CONTRACT_PREMIUM_FIXED.equals(vo.getItemType())) {
				this.getFixRateList().add(vo);
			} else if (ContractCst.CONTRACT_PREMIUM_SWING.equals(vo.getItemType())) {
				this.getSwingRateList().add(vo);
			} else if (ContractCst.CONTRACT_PREMIUM_SWING.equals(vo.getItemType())) {
				swingRateList.add(vo);
			} else if (ContractCst.CONTRACT_PREMIUM_MININUM_PREMIUM.equals(vo.getItemType())) {
				minimumPremiumList.add(vo);
			} else if (ContractCst.CONTRACT_PREMIUM_DEPOSIT_PREMIUM.equals(vo.getItemType())) {
				depositPremiumList.add(vo);
			} else if (ContractCst.CONTRACT_PREMIUM_DEPOSIT_SHEDULE.equals(vo.getItemType())) {
				depositScheduleList.add(vo);
			}
		}
		return premiumList;
	}

	public void setPremiumList(List<PremiumItemVO> premiumList) {
		this.premiumList = premiumList;
	}

	public String getNoOfInstallment() {
		return NoOfInstallment;
	}

	public void setNoOfInstallment(String noOfInstallment) {
		NoOfInstallment = noOfInstallment;
	}

	public String getNoOfPayment() {
		return NoOfPayment;
	}

	public void setNoOfPayment(String noOfPayment) {
		NoOfPayment = noOfPayment;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof PremiumVO) {
			PremiumVO other = (PremiumVO) obj;
			return compare(this.premiumId, other.premiumId) && compare(this.contCompId, other.contCompId)
					&& compare(this.contractNature, other.contractNature) && compare(this.epiType, other.epiType)
					&& compare(this.premiumType, other.premiumType) && compare(this.definedIn, other.definedIn)
					&& compare(this.lossrateFrom, other.lossrateFrom) && compare(this.lossrateTo, other.lossrateTo)
					&& compare(this.premiumrateFrom, other.premiumrateFrom)
					&& compare(this.premiumrateTo, other.premiumrateTo)
					&& compare(this.provisionalRate, other.provisionalRate) && compare(this.rate, other.rate)
					&& compare(this.premiumRemark, other.premiumRemark)
					&& compare(this.NoOfInstallment, other.NoOfInstallment)
					&& compare(this.NoOfPayment, other.NoOfPayment);
		}
		return false;
	}

	private boolean compare(Object str1, Object str2) {
		if (null == str1 && null == str2) {
			return true;
		}
		if ((str1 instanceof String && str2 instanceof String)
				|| (str1 instanceof BigDecimal && str2 instanceof BigDecimal)
				|| (str1 instanceof Long && str2 instanceof Long)) {
			if (null != str1 && null != str2) {
				return str1.equals(str2);
			} else if (null != str1 || null != str2) {
				return false;
			}
		}
		return false;
	}

}
