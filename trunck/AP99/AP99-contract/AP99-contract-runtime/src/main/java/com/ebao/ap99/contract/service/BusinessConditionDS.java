package com.ebao.ap99.contract.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ebao.ap99.contract.model.BusinessConditionVO;
import com.ebao.ap99.contract.model.DeductionsCommVO;
import com.ebao.ap99.contract.model.DeductionsPcVO;
import com.ebao.ap99.contract.model.BusinessModel.BusinessConditionBO;
import com.ebao.ap99.contract.model.BusinessModel.CurrencyBO;
import com.ebao.ap99.contract.model.BusinessModel.DeductionsBO;
import com.ebao.ap99.contract.model.BusinessModel.PremiumBO;
import com.ebao.ap99.contract.model.BusinessModel.ShareBO;

/**
 * 
 * @author elvira.du
 *
 */
public interface BusinessConditionDS {
	/**
	 * 
	 * @param vo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void saveBusinessBO(BusinessConditionBO vo) throws IllegalAccessException, InvocationTargetException;

	public void savePcSlidingDetail(DeductionsPcVO vo) throws Exception;

	public void saveCommSlidingDetail(DeductionsCommVO vo) throws Exception;

	/**
	 * 
	 * @param contCompId
	 * @param contractNature
	 * @return BusinessConditionVO
	 */
	public BusinessConditionVO loadBusinessConditionInfo(Long contCompId, String contractNature);

	/**
	 * 
	 * @param contCompId
	 * @param operateType
	 */
	public void backupBusinessConditionInfo(Long contCompId, Long operateId);

	/**
	 * 
	 * @param slidingId
	 * @return
	 */

	public DeductionsPcVO loadDeductionsPcInfo(Long slidingId);

	/**
	 * 
	 * @param slidingId
	 * @return
	 */
	public DeductionsCommVO loadDeductionsCommInfo(Long slidingId) throws Exception;

	/**
	 * 
	 * @param contCompId
	 */
	public void deleteBusinessInfo(Long contCompId);

	/**
	 * 
	 * @param contCompId
	 * @param contractNature
	 * @return
	 */
	public BusinessConditionVO loadParentBusinessCondition(Long contCompId) throws Exception;

	/**
	 * load business condition business model
	 * 
	 * @param contCompId
	 * @return
	 */
	public BusinessConditionBO loadBusinessConditionBO(Long contCompId);

	/**
	 * Get our share from shareBO
	 * 
	 * @param shareBO
	 * @return
	 * @throws Exception
	 */
	public BigDecimal getOurShare(ShareBO shareBO) throws Exception;

	/**
	 * Get map of currency and exchangeRate
	 * 
	 * @param currencyBOList
	 * @return
	 * @throws Exception
	 */
	public Map<String, BigDecimal> getExchangeRateMap(List<CurrencyBO> currencyBOList) throws Exception;

	/**
	 * Get premium from premiumBO
	 * 
	 * @param currencyBOList
	 * @param premiumBO
	 * @return
	 * @throws Exception
	 */
	public BigDecimal getPremium(List<CurrencyBO> currencyBOList, PremiumBO premiumBO) throws Exception;

	/**
	 * Get deductions from deductionsBO
	 * 
	 * @param deductionsBO
	 * @param premium
	 * @return
	 * @throws Exception
	 */
	public BigDecimal getDeductions(DeductionsBO deductionsBO, BigDecimal premium) throws Exception;

	public BigDecimal transAmountToPercentage(BigDecimal amount, BigDecimal premium) throws Exception;

	public DeductionsPcVO loadDeductionsPcInfoForLog(Long deductionsId, Long operateId) throws Exception;

	public DeductionsCommVO loadDeductionsCommInfoForLog(Long deductionsId, Long operateId) throws Exception;

	public BusinessConditionBO convertBusinessConditionBOWithMainCurrency(BusinessConditionBO bo) throws Exception;

}
