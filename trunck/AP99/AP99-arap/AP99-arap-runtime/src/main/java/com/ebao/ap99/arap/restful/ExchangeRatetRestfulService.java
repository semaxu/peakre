/**
 * 
 */
package com.ebao.ap99.arap.restful;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.constant.ExchangeRateStatus;
import com.ebao.ap99.arap.convertor.CurrencyExchangeRateConvertor;
import com.ebao.ap99.arap.entity.CurrencyExchangeRate;
import com.ebao.ap99.arap.service.CurrencyExchangeDataService;
import com.ebao.ap99.arap.vo.CurrencyExchangeRateCondition;
import com.ebao.ap99.arap.vo.ExchangeRateDTO;
import com.ebao.ap99.arap.vo.OperationResult;
import com.ebao.ap99.parent.Page;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author meiliang.zou
 *
 */
@Path("/exchangeRate")
@Module(com.ebao.ap99.parent.constant.Module.ARAP)
public class ExchangeRatetRestfulService {
    private Logger logger = LoggerFactory.getLogger();

    @Autowired
    public CurrencyExchangeDataService currencyExchangeDataService;
    
    @Autowired
    public CurrencyExchangeRateConvertor currencyExchangeRateConvertor;
    
    @POST
    @Path("/queryExchangeRate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Page<CurrencyExchangeRate> queryExchangeRate(ExchangeRateDTO exchangeRateDTO) throws Exception {
        logger.debug("BaseCurrency=>" + exchangeRateDTO.getBaseCurrency());
        logger.debug("ExchangeCurrency=>" + exchangeRateDTO.getExchangeCurrency());
        logger.debug("ValidDateFrom=>" + exchangeRateDTO.getValidDateFrom());
        logger.debug("ValidDateTo=>" + exchangeRateDTO.getValidDateTo());
        logger.debug("ExpireDateFrom=>" + exchangeRateDTO.getExpireDateFrom());
        logger.debug("ExpireDateTo=>" + exchangeRateDTO.getExpireDateTo());
        logger.debug("RateType=>" + exchangeRateDTO.getRateType());
        logger.debug("PageCount=>" + exchangeRateDTO.getCountPerPage());
        logger.debug("PageIndex=>" + exchangeRateDTO.getPageIndex());
        
        List<CurrencyExchangeRate> rows = null;

        CurrencyExchangeRateCondition condition = null;
        
        long total = 0;
        try
        {
        	condition = 
            		currencyExchangeRateConvertor.modelToQueryConditionEntity(exchangeRateDTO);
        	
        	rows = currencyExchangeDataService.findByCondition(condition);
        	
        	total = currencyExchangeDataService.countByCondition(condition);
        }
        catch (Exception e) {
        	logger.error("Search exchange rate Exception.\n", e);
			throw e;
        }
        
        Page<CurrencyExchangeRate> page = new Page<CurrencyExchangeRate>();
        page.setRows(rows);
        page.setPageIndex(exchangeRateDTO.getPageIndex());
        
        long countPerPage = exchangeRateDTO.getCountPerPage();
        long pageCount = total % countPerPage == 0 ? (total / countPerPage) : (total / countPerPage + 1);
        
        logger.debug("pages num:" + pageCount);
        page.setPageCount(pageCount);
        page.setCountPerPage(exchangeRateDTO.getCountPerPage());
        
        return page;
    }
    
    @POST
    @Path("/saveExchangeRate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OperationResult saveExchangeRate(ExchangeRateDTO exchangeRateDTO) throws Exception {
        logger.debug("RateId=>" + exchangeRateDTO.getRateId());
        logger.debug("BaseCurrency=>" + exchangeRateDTO.getBaseCurrency());
        logger.debug("ExchangeCurrency=>" + exchangeRateDTO.getExchangeCurrency());
        logger.debug("ExchangeRate=>" + exchangeRateDTO.getExchangeRate());
        logger.debug("ValidDateFrom=>" + exchangeRateDTO.getValidDateFrom());
        logger.debug("ValidDateTo=>" + exchangeRateDTO.getValidDateTo());
        logger.debug("ExpireDateFrom=>" + exchangeRateDTO.getExpireDateFrom());
        logger.debug("ExpireDateTo=>" + exchangeRateDTO.getExpireDateTo());
        logger.debug("RateType=>" + exchangeRateDTO.getRateType());
    	
    	OperationResult operationResult = null;
    	
    	try
        {
            CurrencyExchangeRate rate = currencyExchangeRateConvertor.modelToEntity(exchangeRateDTO);
            rate.setStatus(ExchangeRateStatus.VALID.getValue());
            rate.setIsImport(false);
            
            operationResult = currencyExchangeDataService.saveWithCheck(rate);
        }
        catch (Exception e) {
        	logger.error("Save exchange rate Exception.\n", e);
			throw e;
        }
    	
    	return operationResult;
    }
    
    @POST
    @Path("/updateExchangeRate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OperationResult updateExchangeRate(ExchangeRateDTO exchangeRateDTO) throws Exception {    	
    	OperationResult operationResult = null;
    	
    	try
        {
            CurrencyExchangeRate rate = currencyExchangeRateConvertor.modelToEntity(exchangeRateDTO);
            
            operationResult = currencyExchangeDataService.updateExchangeRate(rate);
        }
        catch (Exception e) {
        	logger.error("Update exchange rate Exception.\n", e);
			throw e;
        }
    	
    	return operationResult;
    }
        
    @POST
    @Path("/invalidExchangeRate")
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public void invalidExchangeRate(ExchangeRateDTO exchangeRateDTO) throws Exception {
    	logger.debug("RateId=>" + exchangeRateDTO.getRateId());
    	
    	try
        {
            CurrencyExchangeRate rate = currencyExchangeDataService.findCurrencyExchangeRateByRateId(exchangeRateDTO.getRateId());
            rate.setStatus(ExchangeRateStatus.INVALID.getValue());
            
            currencyExchangeDataService.save(rate);
        }
        catch (Exception e) {
        	logger.error("invalid exchange rate Exception.\n", e);
			throw e;
        }
    }
    
    @POST
    @Path("/deleteExchangeRate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OperationResult deleteExchangeRate(ExchangeRateDTO exchangeRateDTO) throws Exception {
    	Long rateId = exchangeRateDTO.getRateId();
    	logger.debug("RateId=>" + rateId);
    	
    	OperationResult operationResult = null;
    	try{
    		operationResult = currencyExchangeDataService.deleteExchangeRate(rateId);
    	}catch(Exception e){
    		logger.error("Delete Exchange Rate exception.\n", e);
			throw e;
    	}
    	
    	return operationResult;
    }
}
