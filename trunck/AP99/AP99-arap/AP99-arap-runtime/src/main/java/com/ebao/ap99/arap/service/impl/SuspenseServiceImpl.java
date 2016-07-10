package com.ebao.ap99.arap.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.constant.CurrencyConstants;
import com.ebao.ap99.arap.constant.PostStatus;
import com.ebao.ap99.arap.constant.SettlementExchangeSourceType;
import com.ebao.ap99.arap.constant.YesNoType;
import com.ebao.ap99.arap.dao.SettlementDetailDao;
import com.ebao.ap99.arap.dao.SuspenseDao;
import com.ebao.ap99.arap.entity.SettlementDetail;
import com.ebao.ap99.arap.entity.Suspense;
import com.ebao.ap99.arap.service.CurrencyExchangeService;
import com.ebao.ap99.arap.service.SettlementService;
import com.ebao.ap99.arap.service.SuspenseService;
import com.ebao.ap99.arap.vo.Balance;
import com.ebao.ap99.arap.vo.CollectionModel;
import com.ebao.ap99.arap.vo.OffsetModel;
import com.ebao.ap99.arap.vo.PaymentModel;
import com.ebao.ap99.arap.vo.SuspenseQueryCondition;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class SuspenseServiceImpl implements SuspenseService {
	
	@Autowired
	SuspenseDao suspenseDao;
	
	@Autowired
	SettlementDetailDao settlementDetailDao;
	
	@Autowired
	SettlementService settlementService;

	@Autowired
	CurrencyExchangeService currencyExchangeService;
	
	@Autowired
	ContractService contractService;
	
	@Override
	public void saveCollectionSuspense(CollectionModel model)
			throws Exception {
		List<Balance> suspenseList = model.getBalanceList();
		if(suspenseList != null){
			for(Balance balance : suspenseList){
				Suspense suspense = new Suspense();
				suspense.setSuspenseType(balance.getSuspenseType());
				suspense.setBalance(balance.getSettleAmount());
				if(StringUtils.isNotBlank(balance.getContractId())){
					suspense.setContractId(Long.valueOf(balance.getContractId()));
				}
				suspense.setCurrencyCode(balance.getSettleCurrencyCode());
				suspense.setPartnerCode(balance.getPartnerCode());
				suspense.setGenerationDate(balance.getGenerationDate());
				suspense.setStatus(YesNoType.YES.getType());
				suspenseDao.save(suspense);
				
				SettlementDetail settle = new SettlementDetail();
				settle.setSuspenseId(suspense.getSuspenseId());
				settle.setCurrencyCode(suspense.getCurrencyCode());
				settle.setOutstandingAmount(suspense.getBalance());
				settle.setSettleCurrencyCode(suspense.getCurrencyCode());
				settle.setSettleAmount(suspense.getBalance());
				settle.setNeedPost(YesNoType.YES.getType());
				settle.setPostStatus(PostStatus.WAIT_FOR_POST.getType());
				settle.setCollectionId(model.getCollectionId());
				settle.setSettleDate(model.getCollectionDate());
				settlementDetailDao.save(settle);
				
				settlementService.saveSettlementExchangeDetail(SettlementExchangeSourceType.SETTLE_DTL_SUSPENSE.getType(), settle.getSettleDetailId()
						, settle.getOutstandingAmount(),settle.getCurrencyCode(), AppContext.getSysDate());
			}
		}
	}

	@Override
	public void savePaymentSuspense(PaymentModel model)
			throws Exception {
		List<Balance> suspenseList = model.getSuspenseList();
		if(suspenseList != null){
			for(Balance balance : suspenseList){
				Suspense suspense = suspenseDao.load(balance.getSuspenseId());
				
				SettlementDetail settle = new SettlementDetail();
				settle.setSuspenseId(suspense.getSuspenseId());
				settle.setCurrencyCode(suspense.getCurrencyCode());
				settle.setOutstandingAmount(balance.getBalanceAmount());
				settle.setMarkOffAmount(balance.getMarkOffAmount());
				settle.setSettleAmount(balance.getSettleAmount());
				settle.setSettleCurrencyCode(balance.getSettleCurrencyCode());
				settle.setExchangeRate(balance.getExchangeRate());
				settle.setNeedPost(YesNoType.YES.getType());
				settle.setPostStatus(PostStatus.WAIT_FOR_POST.getType());
				settle.setPaymentId(model.getPaymentId());
				settle.setSettleDate(model.getPaymentDate());
				settlementDetailDao.save(settle);
				
				settlementService.saveSettlementExchangeDetail(SettlementExchangeSourceType.SETTLE_DTL_SUSPENSE.getType(), settle.getSettleDetailId()
						, settle.getSettleAmount(),settle.getSettleCurrencyCode(), AppContext.getSysDate());
				
				//Adjust balance amount of suspense
				suspense.setBalance(suspense.getBalance().subtract(balance.getMarkOffAmount()));
				suspenseDao.save(suspense);
			}
		}
	}
	
	@Override
	public void adjustBalance(Long suspenseId, BigDecimal adjustAmount) throws Exception{
		Suspense suspense = suspenseDao.load(suspenseId);
		Assert.isNotNull(suspense);
		
		suspense.setBalance(suspense.getBalance().subtract(adjustAmount));
		suspenseDao.save(suspense);
	}

	@Override
	public void saveOffsetSuspense(OffsetModel model)
			throws Exception {
		List<Balance> suspenseList = model.getSuspenseList();
		if(suspenseList != null){
			for(Balance balance : suspenseList){
				Suspense suspense = suspenseDao.load(balance.getSuspenseId());
				
				SettlementDetail settle = new SettlementDetail();
				settle.setOffsetId(model.getOffsetId());
				settle.setSuspenseId(suspense.getSuspenseId());
				settle.setCurrencyCode(suspense.getCurrencyCode());
				settle.setOutstandingAmount(balance.getBalanceAmount());
				//Actual mark off amount for offset is in USD
				settle.setSettleAmount(balance.getMarkOffAmountInSettleCurrency());
				settle.setSettleCurrencyCode(CurrencyConstants.CURRENCY_USD);
				settle.setNeedPost(YesNoType.YES.getType());
				settle.setPostStatus(PostStatus.WAIT_FOR_POST.getType());
				settle.setSettleDate(model.getOffsetDate());
				
				BigDecimal exchangeRate = 
						currencyExchangeService.getExchangeRate(suspense.getCurrencyCode(), balance.getSettleCurrencyCode());
				settle.setExchangeRate(exchangeRate);
				BigDecimal markOffAmountInOriginalCurrency = BigDecimal.ZERO;
				if(balance.getBalanceAmountInSettleCurrency().equals(balance.getMarkOffAmountInSettleCurrency())){//fully settle to suspense
					markOffAmountInOriginalCurrency = balance.getBalanceAmount();
				}else{
					markOffAmountInOriginalCurrency = settle.getSettleAmount().multiply(exchangeRate).setScale(CurrencyConstants.DEFAULT_RATE_SCALE, BigDecimal.ROUND_HALF_UP);
				}
				settle.setMarkOffAmount(markOffAmountInOriginalCurrency);
				settlementDetailDao.save(settle);
				
				settlementService.saveSettlementExchangeDetail(SettlementExchangeSourceType.SETTLE_DTL_SUSPENSE.getType(), settle.getSettleDetailId()
						, settle.getSettleAmount(),settle.getSettleCurrencyCode(), AppContext.getSysDate());
				
				//Adjust balance amount of suspense
				suspense.setBalance(suspense.getBalance().subtract(settle.getMarkOffAmount()));
				suspenseDao.save(suspense);
			}
		}
	}
	
	@Override
	public List<Balance> getSuspenseByCodition(SuspenseQueryCondition condition) throws Exception{
		List<Balance> balanceList = new ArrayList<Balance>();
		List<Suspense> suspenseList = suspenseDao.getByCondition(condition);
		if(suspenseList != null){
			BigDecimal balanceInSettleCurrency = null;
			for(Suspense suspense : suspenseList){
				Balance balance = new Balance();
				BeanUtils.copyPropertiesIngoreNull(balance, suspense);
				balance.setBalanceAmount(suspense.getBalance());
				balance.setBalanceCurrency(suspense.getCurrencyCode());
				balance.setSettleCurrencyCode(condition.getSettleCurrencyCode());
				if(balance.getSettleCurrencyCode() != null){
					balanceInSettleCurrency = currencyExchangeService.exchangeAmount(balance.getBalanceAmount(), balance.getBalanceCurrency(),
							balance.getSettleCurrencyCode(), DateUtils.truncate(AppContext.getSysDate(),Calendar.DATE));
					balance.setBalanceAmountInSettleCurrency(balanceInSettleCurrency);
				}
				balanceList.add(balance);
			}
		}
		
		return balanceList;
	}
	
	@Override
	public List<Balance> getSettlementSuspense(Integer settleTransType, Long settleTransId) throws Exception{
		List<Balance> balanceList = new ArrayList<Balance>();
		List<Suspense> suspenseList = suspenseDao.findSettlementSuspenseByTransId(settleTransType, settleTransId);
		if(suspenseList != null){
			Balance balance = null;
			BigDecimal balanceInSettleCurrency = null;
			for(Suspense suspense : suspenseList){
				balance = new Balance();
				BeanUtils.copyPropertiesIngoreNull(balance, suspense);
				balance.setBalanceAmount(suspense.getOutstandingAmount());
				balance.setBalanceCurrency(suspense.getCurrencyCode());
				// meiliang.zou add start
				//balance.setMarkOffAmount(suspense.getOutstandingAmount());
				//balance.setContractCode(suspense.getContractCode());
				// meiliang.zou add end
				if(suspense.getContractId() != null){
					balance.setContractCode(contractService.getContractCodeById(suspense.getContractId()));
				}
				if(balance.getSettleCurrencyCode() != null){
					balanceInSettleCurrency = currencyExchangeService.exchangeAmount(balance.getBalanceAmount(), balance.getBalanceCurrency(),
																							balance.getSettleCurrencyCode(), balance.getSettleDate());
					balance.setBalanceAmountInSettleCurrency(balanceInSettleCurrency);// outstanding amount of the time in settle currency
					balance.setMarkOffAmountInSettleCurrency(suspense.getSettleAmount());
				}
				balanceList.add(balance);
			}
		}
		return balanceList;
	}
	
	@Override
	public List<Balance> filterInvalidSuspense(List<Balance> suspenseList) throws Exception{
		List<Balance> validSuspenseList = new ArrayList<Balance>();
		if(suspenseList != null && suspenseList.size() > 0){
			for(Balance suspense : suspenseList){
				if(suspense.getSettleAmount() != null 
						|| suspense.getMarkOffAmount() != null
						|| suspense.getMarkOffAmountInSettleCurrency() != null){
					validSuspenseList.add(suspense);
				}
			}
		}
		return validSuspenseList;
	}
}
