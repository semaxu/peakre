package com.ebao.ap99.arap.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.constant.CurrencyConstants;
import com.ebao.ap99.arap.constant.ExchangeRateType;
import com.ebao.ap99.arap.dao.CurrencyExchangeRateDao;
import com.ebao.ap99.arap.service.CurrencyExchangeService;
import com.ebao.ap99.arap.util.DateUtil;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {
	@Autowired
	CurrencyExchangeRateDao currencyExchangeDao;

	@Override
	public BigDecimal getExchangeRate(String baseCurrencyCode,
			String exchangeCurrencyCode, Integer rateType, Date date)
			throws Exception {
		Assert.isNotNull(baseCurrencyCode);
		Assert.isNotNull(exchangeCurrencyCode);
		Assert.isNotNull(rateType);
		Assert.isNotNull(date);
		if(baseCurrencyCode.equals(exchangeCurrencyCode)){
			return new BigDecimal(1);
		}
		BigDecimal result = currencyExchangeDao.getExchangeRate(baseCurrencyCode, exchangeCurrencyCode, rateType, date);
		if(result == null){
			BigDecimal base2MiddleRate = currencyExchangeDao.getExchangeRate(baseCurrencyCode, CurrencyConstants.EXCHANGE_BASE_CURRENCY, rateType,date);
			BigDecimal ex2MiddleRate = currencyExchangeDao.getExchangeRate(exchangeCurrencyCode, CurrencyConstants.EXCHANGE_BASE_CURRENCY, rateType, date);
			if(base2MiddleRate == null || ex2MiddleRate == null){
				throw new Exception("There is no exchange rate definition![from currency="+baseCurrencyCode+"] [to currency="+exchangeCurrencyCode+"] [date="+DateUtil.format(date, "YYYYMMDD") + "] [rate type="+rateType+"]");
			}else{
				result = base2MiddleRate.divide(ex2MiddleRate, CurrencyConstants.DEFAULT_RATE_SCALE, BigDecimal.ROUND_HALF_UP);
			}
		}
		return result;
	}
	
	@Override
	public BigDecimal getExchangeRate(String baseCurrencyCode,
			String exchangeCurrencyCode, Date date) throws Exception {
		Assert.isNotNull(baseCurrencyCode);
		Assert.isNotNull(exchangeCurrencyCode);
		Assert.isNotNull(date);
		return getExchangeRate(baseCurrencyCode, exchangeCurrencyCode, ExchangeRateType.GENERAL_RATE.getType(), date);
	}

	@Override
	public BigDecimal getExchangeRate(String baseCurrencyCode,
			String exchangeCurrencyCode) throws Exception {
		Assert.isNotNull(baseCurrencyCode);
		Assert.isNotNull(exchangeCurrencyCode);
		return getExchangeRate(baseCurrencyCode, exchangeCurrencyCode, ExchangeRateType.GENERAL_RATE.getType(), AppContext.getSysDate());
	}

	@Override
	public BigDecimal exchangeAmount(BigDecimal amount, String currencyCode,
			String exchangeCurrencyCode, Integer rateType, Date date)
			throws Exception {
		Assert.isNotNull(amount);
		Assert.isNotNull(currencyCode);
		Assert.isNotNull(exchangeCurrencyCode);
		Assert.isNotNull(rateType);
		Assert.isNotNull(date);
		
		BigDecimal exchangeRate = getExchangeRate(currencyCode, exchangeCurrencyCode, rateType, date);
		BigDecimal amountInSettleCurrency = amount.divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE, BigDecimal.ROUND_HALF_UP);
		
		return amountInSettleCurrency;
	}

	@Override
	public BigDecimal exchangeAmount(BigDecimal amount, String currencyCode,
			String exchangeCurrencyCode) throws Exception {
		return exchangeAmount(amount, currencyCode, exchangeCurrencyCode,ExchangeRateType.GENERAL_RATE.getType(), AppContext.getSysDate());
	}

	@Override
	public BigDecimal exchangeAmount(BigDecimal amount, String currencyCode,
			String exchangeCurrencyCode, Date date) throws Exception {
		return exchangeAmount(amount, currencyCode, exchangeCurrencyCode,ExchangeRateType.GENERAL_RATE.getType(), date);
	}
}
