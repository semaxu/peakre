package com.ebao.ap99.contract.model.BusinessModel;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessConditionBO {
	@XmlTransient
	private Long contCompId;
	private ShareBO shareBO;
	private PremiumBO premiumBO;
	private List<CurrencyBO> currencyBO;
	private LimitBO limitBO;
	private ReinstatementBO reinBO;
	private DeductionsBO deductionBO;
	private ReserveRebateBO reserveRebateBO;
	private LossBO lossBO;
	private ClausesBO clausesBO;

	public Long getContCompId() {
		return contCompId;
	}

	public void setContCompId(Long contCompId) {
		this.contCompId = contCompId;
	}

	public ShareBO getShareBO() {
		return shareBO;
	}

	public void setShareBO(ShareBO shareBO) {
		this.shareBO = shareBO;
	}

	public PremiumBO getPremiumBO() {
		return premiumBO;
	}

	public void setPremiumBO(PremiumBO premiumBO) {
		this.premiumBO = premiumBO;
	}

	public List<CurrencyBO> getCurrencyBO() {
		return currencyBO;
	}

	public void setCurrencyBO(List<CurrencyBO> currencyBO) {
		this.currencyBO = currencyBO;
	}

	public LimitBO getLimitBO() {
		return limitBO;
	}

	public void setLimitBO(LimitBO limitBO) {
		this.limitBO = limitBO;
	}

	public ReinstatementBO getReinBO() {
		return reinBO;
	}

	public void setReinBO(ReinstatementBO reinBO) {
		this.reinBO = reinBO;
	}

	public DeductionsBO getDeductionBO() {
		return deductionBO;
	}

	public void setDeductionBO(DeductionsBO deductionBO) {
		this.deductionBO = deductionBO;
	}

	public ReserveRebateBO getReserveRebateBO() {
		return reserveRebateBO;
	}

	public void setReserveRebateBO(ReserveRebateBO reserveRebateBO) {
		this.reserveRebateBO = reserveRebateBO;
	}

	public LossBO getLossBO() {
		return lossBO;
	}

	public void setLossBO(LossBO lossBO) {
		this.lossBO = lossBO;
	}

	public ClausesBO getClausesBO() {
		return clausesBO;
	}

	public void setClausesBO(ClausesBO clausesBO) {
		this.clausesBO = clausesBO;
	}
}
