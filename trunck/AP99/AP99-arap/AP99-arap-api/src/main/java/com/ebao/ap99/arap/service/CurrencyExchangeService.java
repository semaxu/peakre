package com.ebao.ap99.arap.service;

import java.math.BigDecimal;
import java.util.Date;

public interface CurrencyExchangeService {

	/**
	 * Query exchange rate with specified parameters, exchange base currency <Currencyconstants> is intermediate rate for any base currency and exchange currency.
	 * @param baseCurrencyCode
	 * @param exchangeCurrencyCode
	 * @param rateType 
	 * 			@see ExchangeRateType
	 * @param date
	 * 			The date should be in valid period of exchange rate
	 * @return
	 * @throws Exception
	 */
	public BigDecimal getExchangeRate(String baseCurrencyCode, String exchangeCurrencyCode, Integer rateType, Date date) throws Exception;
	
	/**
	 * Query exchange rate with default general exchange rate type
	 * @param baseCurrencyCode
	 * @param exchangeCurrencyCode
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public BigDecimal getExchangeRate(String baseCurrencyCode, String exchangeCurrencyCode, Date date) throws Exception;
	
	/**
	 * Query exchange rate with default general exchange rate type and current system date
	 * @param baseCurrencyCode
	 * @param exchangeCurrencyCode
	 * @return
	 * @throws Exception
	 */
	public BigDecimal getExchangeRate(String baseCurrencyCode, String exchangeCurrencyCode) throws Exception;
	
	/**
	 * Get amount in exchange currency with specified parameters
	 * @param amount
	 * @param currencyCode
	 * @param exchangeCurrencyCode
	 * @param rateType
	 * 		@see ExchangeRateType
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public BigDecimal exchangeAmount(BigDecimal amount, String currencyCode, String exchangeCurrencyCode, Integer rateType, Date date) throws Exception;
	
	/**
	 * Get amount in exchange currency with general exchange rate type and currency system date as default
	 * @param amount
	 * @param currencyCode
	 * @param exchangeCurrencyCode
	 * @return
	 * @throws Exception
	 */
	public BigDecimal exchangeAmount(BigDecimal amount, String currencyCode, String exchangeCurrencyCode) throws Exception;
	
	/**
	 * Get amount in exchange currency with general exchange rate type as default
	 * @param amount
	 * @param currencyCode
	 * @param exchangeCurrencyCode
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public BigDecimal exchangeAmount(BigDecimal amount, String currencyCode, String exchangeCurrencyCode, Date date) throws Exception;
}
