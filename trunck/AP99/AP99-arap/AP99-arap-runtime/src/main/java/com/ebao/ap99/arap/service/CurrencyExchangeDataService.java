package com.ebao.ap99.arap.service;

import java.util.List;

import com.ebao.ap99.arap.entity.CurrencyExchangeRate;
import com.ebao.ap99.arap.vo.CurrencyExchangeRateCondition;
import com.ebao.ap99.arap.vo.OperationResult;

public interface CurrencyExchangeDataService {
	
	public void save(CurrencyExchangeRate rate) throws Exception;
	
	/**
	 * If the rate has duplicate data, or valid date and expiry date is overlap other data, don't allow to save
	 * @param rate
	 * @return
	 * @throws Exception
	 */
	public OperationResult saveWithCheck(CurrencyExchangeRate rate) throws Exception;
	
	public void saveAll(List<CurrencyExchangeRate> rateList) throws Exception;
	
	public List<CurrencyExchangeRate> findByCondition(CurrencyExchangeRateCondition condition) throws Exception;
	
	public Long countByCondition(CurrencyExchangeRateCondition condition) throws Exception;
	
	public CurrencyExchangeRate findCurrencyExchangeRateByRateId(long rateId);
	
	public OperationResult deleteExchangeRate(Long rateId) throws Exception;
	
	public OperationResult updateExchangeRate(CurrencyExchangeRate rate) throws Exception;
}
