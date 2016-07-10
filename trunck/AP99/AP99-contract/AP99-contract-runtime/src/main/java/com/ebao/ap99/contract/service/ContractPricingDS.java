package com.ebao.ap99.contract.service;

import java.util.Date;
import java.util.List;

import com.ebao.ap99.accounting.ContractPremiumModel;
import com.ebao.ap99.accounting.model.SoaNonPropVO;
import com.ebao.ap99.contract.model.PricingEstimateVO;
import com.ebao.ap99.contract.model.BusinessModel.PricingEstimateBO;

public interface ContractPricingDS {
	/**
	 * load pricing estimate info
	 * 
	 * @param contCompId
	 * @return
	 */
	public PricingEstimateBO loadPricingEstimateBO(Long contCompId);

	/**
	 * save pricing estimate info
	 * 
	 * @param contCompId
	 * @return
	 * @throws Exception
	 */
	public PricingEstimateVO savePricingEstimateVO(PricingEstimateVO estimateVO) throws Exception;

	public PricingEstimateBO savePricingEstimateBO(PricingEstimateBO estimateBO);

	public PricingEstimateVO savePricingEstimateVOWithCalculate(PricingEstimateVO estimateVO) throws Exception;

	/**
	 * generate the contract premium model by contract contCompId & section
	 * contCompId
	 * 
	 * @param contCompId
	 * @param sectionId
	 * @return
	 * @throws Exception
	 */
	public ContractPremiumModel generateAccountingPremiumModel(Long contCompId, Long sectionId) throws Exception;

	/**
	 * load the pricing estimate vo from section info
	 * 
	 * @param contCompId
	 * @return
	 */
	public PricingEstimateVO loadPricingVOFromSection(Long contCompId) throws Exception;

	/**
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List<SoaNonPropVO> generateSOANonProportionInfo(Date date) throws Exception;

	public void transSOAToAccounting(List<SoaNonPropVO> propVOList);

	public PricingEstimateBO loadPricingEstimateBOForLog(Long contCompId, Long operateId) throws Exception;
}
