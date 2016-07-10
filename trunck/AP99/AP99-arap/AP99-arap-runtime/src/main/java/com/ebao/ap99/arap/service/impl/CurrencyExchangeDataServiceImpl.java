package com.ebao.ap99.arap.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.constant.CurrencyConstants;
import com.ebao.ap99.arap.constant.ExchangeRateStatus;
import com.ebao.ap99.arap.constant.ExchangeRateType;
import com.ebao.ap99.arap.constant.OperationStatus;
import com.ebao.ap99.arap.constant.YesNoType;
import com.ebao.ap99.arap.dao.CurrencyDao;
import com.ebao.ap99.arap.dao.CurrencyExchangeRateDao;
import com.ebao.ap99.arap.entity.CurrencyExchangeRate;
import com.ebao.ap99.arap.service.CurrencyExchangeDataService;
import com.ebao.ap99.arap.vo.CurrencyExchangeRateCondition;
import com.ebao.ap99.arap.vo.OperationResult;
import com.ebao.ap99.file.model.ItemVO;
import com.ebao.ap99.file.model.MessageVO;
import com.ebao.ap99.file.service.BizService;
import com.ebao.unicorn.platform.foundation.utils.Assert;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class CurrencyExchangeDataServiceImpl implements CurrencyExchangeDataService , BizService{
	private static Logger logger = LoggerFactory.getLogger();
	
	@Autowired
	CurrencyDao currencyDao;
	
	@Autowired
	CurrencyExchangeRateDao currencyExchangeDao;
	
	@Override
	public void save(CurrencyExchangeRate rate) throws Exception {
		if(rate.getStatus() == null){
			rate.setStatus(ExchangeRateStatus.VALID.getValue());
		}
		currencyExchangeDao.save(rate);
	}
	
	@Override
	public OperationResult saveWithCheck(CurrencyExchangeRate rate) throws Exception{
		logger.info("CurrencyExchangeRateService.saveWithCheck");
		OperationResult result = new OperationResult();
		if(rate.getStatus() != null && rate.getStatus() == ExchangeRateStatus.VALID.getValue()){
			List<CurrencyExchangeRate> validList = currencyExchangeDao.loadValidData(rate.getBaseCurrencyCode(),rate.getExCurrencyCode(),rate.getRateType());
			for(CurrencyExchangeRate validRate : validList){
				if(rate.getRateId() != null && rate.getRateId() == validRate.getRateId()){
					continue;
				}
				if(rate.getValidDate().compareTo(validRate.getValidDate()) == 0 && rate.getExpiryDate().compareTo(validRate.getExpiryDate()) == 0 
						&& rate.getRate().compareTo(validRate.getRate())==0){
					result.setStatus(OperationStatus.FAILED_DATA_EXISTS.getValue());
					result.setMessage("Save fail : the same exchange rate record exists in database");
					break;
				}
				if(isOverlapDate(rate.getValidDate(),rate.getExpiryDate(), validRate.getValidDate(), validRate.getExpiryDate())){
					result.setStatus(OperationStatus.FAILED_PERIOD_EXISTS.getValue());
					result.setMessage("Save fail : date overlap on the existing data in database");
					break;
				}
				
			}
		}
		if(result.getStatus() == OperationStatus.SUCEESSFUL.getValue()){
			currencyExchangeDao.save(rate);
			result.setMessage("Save successful");
		}
		return result;
	}
	
	@Override
	public OperationResult updateExchangeRate(CurrencyExchangeRate rate) throws Exception{
		logger.info("CurrencyExchangeRateService.saveWithCheck");
		OperationResult result = new OperationResult();
		
		List<CurrencyExchangeRate> validList = currencyExchangeDao.loadValidData(rate.getBaseCurrencyCode(),rate.getExCurrencyCode(),rate.getRateType());
		for(CurrencyExchangeRate validRate : validList){
			if(rate.getRateId() != null && rate.getRateId().compareTo(validRate.getRateId()) == 0){
				continue;
			}
			if(rate.getValidDate().compareTo(validRate.getValidDate()) == 0 && rate.getExpiryDate().compareTo(validRate.getExpiryDate()) == 0 
					&& rate.getRate().compareTo(validRate.getRate())==0){
				result.setStatus(OperationStatus.FAILED_DATA_EXISTS.getValue());
				result.setMessage("The same exchange rate record exists in the system!");
				break;
			}
			if(isOverlapDate(rate.getValidDate(),rate.getExpiryDate(), validRate.getValidDate(), validRate.getExpiryDate())){
				result.setStatus(OperationStatus.FAILED_PERIOD_EXISTS.getValue());
				result.setMessage("The period is overlap other existing data.");
				break;
			}
			
		}
		
		if(result.getStatus() == OperationStatus.SUCEESSFUL.getValue()){
			CurrencyExchangeRate oldExchangeRate = currencyExchangeDao.findCurrencyExchangeRateByRateId(rate.getRateId());
			oldExchangeRate.setBaseCurrencyCode(rate.getBaseCurrencyCode());
			oldExchangeRate.setExCurrencyCode(rate.getExCurrencyCode());
			oldExchangeRate.setRate(rate.getRate());
			oldExchangeRate.setValidDate(rate.getValidDate());
			oldExchangeRate.setExpiryDate(rate.getExpiryDate());
			oldExchangeRate.setRateType(rate.getRateType());
			
			currencyExchangeDao.save(oldExchangeRate);
		}
		
		return result;
	}
	
	@Override
	public void saveAll(List<CurrencyExchangeRate> rateList) throws Exception {
		if(rateList != null){
			checkList(rateList, true);
			int rateType = -1;
			for(CurrencyExchangeRate rate : rateList){
				if (null != rate.getRateType()){
					rateType = rate.getRateType().intValue();
					// rateType in (1, 2) condition meiliang.zou add 2016.5.9
					if (rateType == 1 || rateType == 2){
						save(rate);
					}
				}
			}
		}
	}
	
	private boolean checkList(List<CurrencyExchangeRate> rateList, boolean isThrowException) throws Exception{
		boolean success = true;
		int size = rateList.size();
		ItemVO<CurrencyExchangeRate> vo1;
		ItemVO<CurrencyExchangeRate> vo2;
		// meiliang.zou add 2016.5.16 start
		Date validDate = null, expiryDate = null;
		// meiliang.zou add 2016.5.16 end
		for(int i = 0; i < size ; i++){
			if(!isValidData(rateList.get(i))){
				success = false;
				continue;
			}
			vo1 = rateList.get(i).getImportVO();
			for(int j = i+1; j < size; j++){
				// meiliang.zou add 2016.5.16 start
				validDate = rateList.get(j).getValidDate();
				expiryDate = rateList.get(j).getExpiryDate();
				// check valid and expiry date
				if (validDate.getTime() > expiryDate.getTime()) {
					throw new Exception("Valid Date must not be later than Expiry Date!");
				}
				// meiliang.zou add 2016.5.16 end
				
				vo2 = rateList.get(j).getImportVO();
				if(isDuplicate(rateList.get(i), rateList.get(j))){
					success = false;
					if(isThrowException){
						throw new Exception("Duplicate data is not allowed!");
					}else{
						vo1.setResultFlag(false);
						vo1.setMsg("Check fail: it is duplicate with the row " + vo2.getRowNo());
					}
					continue;
				}
				if(isOverlapDate(rateList.get(i),rateList.get(j))){
					success = false;
					if(isThrowException){
						throw new Exception("The valid date and expiry date is not allowed to be overlap!");
					}else{
						vo1.setResultFlag(false);
						vo1.setMsg("Check fail: date overlap between current row and the row " + vo2.getRowNo());
					}
					continue;
				}
			}
		}
		return success;
	}
	
	private boolean isValidData(CurrencyExchangeRate rate) throws Exception{
		if(StringUtils.isBlank(rate.getBaseCurrencyCode())|| StringUtils.isBlank(rate.getExCurrencyCode())){
			rate.getImportVO().setMsg("Check fail: Currency code is necessary");
			return false;
		}
		if(currencyDao.findByCurrencyCode(rate.getBaseCurrencyCode()) == null){
			rate.getImportVO().setMsg("Check fail: From currency code is invalid");
			return false;
		}
		if(currencyDao.findByCurrencyCode(rate.getExCurrencyCode()) == null){
			rate.getImportVO().setMsg("Check fail: To currency code is invalid");
			return false;
		}
		if(!CurrencyConstants.CURRENCY_USD.equalsIgnoreCase(rate.getExCurrencyCode())){
			rate.getImportVO().setMsg("Check fail: To currency code only can be USD");
			return false;
		}
		if(!ExchangeRateType.isInvalid(rate.getRateType())){
			rate.getImportVO().setMsg("Check fail: Rate type is invalid");
			return false;
		}
		return true;
	}
	private boolean isDuplicate(CurrencyExchangeRate rate1, CurrencyExchangeRate rate2) throws Exception{
		boolean isDuplicate = true;
		if(rate1.getRateId() != null && rate2.getRateId() != null && rate1.getRateId().compareTo(rate2.getRateId())==0){
			return false;
		}
		if(rate1.getBaseCurrencyCode()!=null && rate2.getBaseCurrencyCode() != null & !rate1.getBaseCurrencyCode().equals(rate2.getBaseCurrencyCode())){
			isDuplicate = false;
		}
		if(isDuplicate && rate1.getExCurrencyCode() != null && rate2.getExCurrencyCode() != null && !rate1.getExCurrencyCode().equals(rate2.getExCurrencyCode())){
			isDuplicate = false;
		}
		if(isDuplicate && rate1.getValidDate() != null && rate2.getValidDate() != null && rate1.getValidDate().compareTo(rate2.getValidDate()) != 0){
			isDuplicate = false;
		}
		if(isDuplicate && rate1.getExpiryDate() != null && rate2.getExpiryDate() != null && rate1.getExpiryDate().compareTo(rate2.getExpiryDate()) != 0){
			isDuplicate = false;
		}
		/*if(isDuplicate && rate1.getRate() != null && rate2.getRate() != null && rate1.getRate().compareTo(rate2.getRate()) != 0){
			isDuplicate = false;
		}*/
		if(isDuplicate && rate1.getRateType() != null && rate2.getRateType() != null && rate1.getRateType().compareTo(rate2.getRateType()) != 0){
			isDuplicate = false;
		}
		if(isDuplicate && rate1.getStatus() != null && rate2.getStatus() != null && rate1.getStatus().compareTo(rate2.getStatus()) != 0&&rate1.getStatus()==ExchangeRateStatus.VALID.getValue()){
			isDuplicate = false;
		}
		return isDuplicate;
	}
	
	private boolean isOverlapDate(CurrencyExchangeRate rate1, CurrencyExchangeRate rate2) throws Exception{
		if(rate1.getRateId() != null && rate2.getRateId() != null && rate1.getRateId().compareTo(rate2.getRateId())==0){
			return false;
		}
		if(rate1.getBaseCurrencyCode()!=null && rate2.getBaseCurrencyCode() != null & !rate1.getBaseCurrencyCode().equals(rate2.getBaseCurrencyCode())){
			return false;
		}
		if(rate1.getExCurrencyCode() != null && rate2.getExCurrencyCode() != null && !rate1.getExCurrencyCode().equals(rate2.getExCurrencyCode())){
			return false;
		}
		if(rate1.getRateType() != null && rate2.getRateType() != null && rate1.getRateType().compareTo(rate2.getRateType()) != 0){
			return false;
		}
		return isOverlapDate(rate1.getValidDate(),rate1.getExpiryDate(), rate2.getValidDate(), rate2.getExpiryDate());
	}
	
	private boolean isOverlapDate(Date periodFrom, Date periodTo, Date oPeriodFrom, Date oPeriodTo) throws Exception{
		Assert.isNotNull(periodFrom);
		Assert.isNotNull(periodTo);
		Assert.isNotNull(oPeriodFrom);
		Assert.isNotNull(oPeriodTo);
		
		boolean isOverlap = false;
		if(periodFrom.compareTo(oPeriodFrom) >= 0 && periodFrom.compareTo(oPeriodTo) <= 0){
			isOverlap  = true;
		}
		if(!isOverlap && periodTo.compareTo(oPeriodFrom) >= 0 && periodTo.compareTo(oPeriodTo) <= 0){
			isOverlap = true;
		}
		if(oPeriodFrom.compareTo(periodFrom) >= 0 && oPeriodFrom.compareTo(periodTo) <= 0){
			isOverlap  = true;
		}
		if(!isOverlap && oPeriodTo.compareTo(periodFrom) >= 0 && oPeriodTo.compareTo(periodTo) <= 0){
			isOverlap = true;
		}
		return isOverlap;
	}

	@Override
	public List<CurrencyExchangeRate> findByCondition(
			CurrencyExchangeRateCondition condition) throws Exception {
		return currencyExchangeDao.findByPageCondition(condition);
	}
	
	@Override
	public Long countByCondition(CurrencyExchangeRateCondition condition) throws Exception{
		return currencyExchangeDao.countByPageCondition(condition);
	}

	@Override
	public CurrencyExchangeRate findCurrencyExchangeRateByRateId(long rateId) {
		return currencyExchangeDao.findCurrencyExchangeRateByRateId(rateId);
	}
	
	public OperationResult deleteExchangeRate(Long rateId) throws Exception{
		currencyExchangeDao.deleteExchangeRate(rateId);
		return new OperationResult();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public Object bizProcess(List itemVOs,Long documentId,Long businessId) throws Exception{
		MessageVO resultMsg = new MessageVO();
        if(CollectionUtils.isNotEmpty(itemVOs)){
			List<CurrencyExchangeRate> rateList = buildRateFromVO(itemVOs);
			if(CollectionUtils.isEmpty(rateList)){
				return resultMsg;
			}
			if(!checkList(rateList, false)){
				return resultMsg;
			}
			ItemVO<CurrencyExchangeRate> vo = null;
			for(CurrencyExchangeRate rate : rateList){
				try{
					vo = rate.getImportVO();
					OperationResult result = saveWithCheck(rate);
					if(!OperationStatus.SUCEESSFUL.getValue().equals(result.getStatus())){
						vo.setResultFlag(false);
					}
					vo.setMsg(result.getMessage());
				}catch(Exception e){
					if(vo != null){
						vo.setResultFlag(false);
						vo.setMsg(e.getLocalizedMessage());
					}
				}
			}
        }
        return resultMsg;
    }
    
    private List<CurrencyExchangeRate> buildRateFromVO(List<ItemVO<CurrencyExchangeRate>> itemVOList) {
    	List<CurrencyExchangeRate> resultList = new ArrayList<CurrencyExchangeRate>();
    	if(CollectionUtils.isNotEmpty(itemVOList)){
    		CurrencyExchangeRate rate;
    		for(ItemVO<CurrencyExchangeRate> vo : itemVOList){
    			if(vo.isResultFlag()){
    				try{
    					vo.setMsg("Not save yet");
    					rate = vo.getBizVO();
    					rate.setIsImport(true);
    					rate.setStatus(YesNoType.YES.getType());
    					rate.setImportVO(vo);
    					if(StringUtils.isNotBlank(rate.getBaseCurrencyCode())){
    						rate.setBaseCurrencyCode(rate.getBaseCurrencyCode().trim().toUpperCase());
    					}
    					if(StringUtils.isNotBlank(rate.getExCurrencyCode())){
    						rate.setExCurrencyCode(rate.getExCurrencyCode().trim().toUpperCase());
    					}
    					resultList.add(rate);
    				}catch(Exception e){
    					vo.setMsg(e.getLocalizedMessage());
    					vo.setResultFlag(false);
    				}
    			}
    		}
    		if(resultList.size() != itemVOList.size()){
    			resultList.clear();
    		}
    	}
    	return resultList;
    }
    
}
