/**
 * 
 */
package com.ebao.ap99.arap.convertor;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

import com.ebao.ap99.arap.entity.CurrencyExchangeRate;
import com.ebao.ap99.arap.vo.CurrencyExchangeRateCondition;
import com.ebao.ap99.arap.vo.ExchangeRateDTO;

/**
 * @author meiliang.zou
 *
 */
public class CurrencyExchangeRateConvertor {
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	public CurrencyExchangeRate modelToEntity(ExchangeRateDTO exchangeRateDTO) throws ParseException {
		CurrencyExchangeRate rate = new CurrencyExchangeRate();
		
        rate.setRateId(exchangeRateDTO.getRateId()==null?null:Long.valueOf(exchangeRateDTO.getRateId()));
        rate.setBaseCurrencyCode(exchangeRateDTO.getBaseCurrency());
        rate.setExCurrencyCode(exchangeRateDTO.getExchangeCurrency());
        rate.setRate(StringUtils.isBlank(exchangeRateDTO.getExchangeRate())?null:new BigDecimal(exchangeRateDTO.getExchangeRate().trim()));
        
        String validDate = exchangeRateDTO.getValidDate();
        rate.setValidDate(StringUtils.isBlank(validDate) ? null : df.parse(validDate));
        
        String expireDate = exchangeRateDTO.getExpireDate();
        rate.setExpiryDate(StringUtils.isBlank(expireDate) ? null : df.parse(expireDate));
        
        rate.setRateType(StringUtils.isBlank(exchangeRateDTO.getRateType())?null:Integer.parseInt(exchangeRateDTO.getRateType()));
        
        rate.setStatus(StringUtils.isBlank(exchangeRateDTO.getStatus())?null:Integer.valueOf(exchangeRateDTO.getStatus()));
        rate.setIsImport(exchangeRateDTO.getIsImport());
        
		return rate;
	}
	
	public CurrencyExchangeRateCondition modelToQueryConditionEntity(ExchangeRateDTO exchangeRateDTO) throws ParseException {
		CurrencyExchangeRateCondition condition = new CurrencyExchangeRateCondition();
		
		condition.setBaseCurrencyCode(exchangeRateDTO.getBaseCurrency());
        condition.setExCurrencyCode(exchangeRateDTO.getExchangeCurrency());
		
        String validDate = exchangeRateDTO.getValidDate();
        condition.setValidDate(StringUtils.isBlank(validDate) ? null : df.parse(validDate));

        String validDateFrom = exchangeRateDTO.getValidDateFrom();
        condition.setValidDateFrom(StringUtils.isBlank(validDateFrom) ? null : df.parse(validDateFrom));

        String validDateTo = exchangeRateDTO.getValidDateTo();
        condition.setValidDateTo(StringUtils.isBlank(validDateTo) ? null : df.parse(validDateTo));
        
        String expireDate = exchangeRateDTO.getExpireDate();
        condition.setExpiryDate(StringUtils.isBlank(expireDate) ? null : df.parse(expireDate));

        String expireDateFrom = exchangeRateDTO.getExpireDateFrom();
        condition.setExpiryDateFrom(StringUtils.isBlank(expireDateFrom) ? null : df.parse(expireDateFrom));

        String expireDateTo = exchangeRateDTO.getExpireDateTo();
        condition.setExpiryDateTo(StringUtils.isBlank(expireDateTo) ? null : df.parse(expireDateTo));
              
		condition.setRateType(
				StringUtils.isBlank(exchangeRateDTO.getRateType()) ? null : Integer.valueOf(exchangeRateDTO.getRateType()));
		condition.setPageNumber(exchangeRateDTO.getPageIndex());
		condition.setPageSize(Integer.parseInt(String.valueOf(exchangeRateDTO.getCountPerPage())));
		
		return condition;
	}
}
