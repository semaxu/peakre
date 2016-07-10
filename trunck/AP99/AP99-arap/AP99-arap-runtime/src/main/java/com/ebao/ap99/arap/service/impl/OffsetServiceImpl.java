package com.ebao.ap99.arap.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ebao.ap99.arap.constant.CurrencyConstants;
import com.ebao.ap99.arap.constant.FinanceTransactionType;
import com.ebao.ap99.arap.constant.SettlementStatus;
import com.ebao.ap99.arap.dao.OffsetDao;
import com.ebao.ap99.arap.dao.ReverseDao;
import com.ebao.ap99.arap.entity.Offset;
import com.ebao.ap99.arap.entity.Reverse;
import com.ebao.ap99.arap.service.CurrencyExchangeService;
import com.ebao.ap99.arap.service.FeeService;
import com.ebao.ap99.arap.service.OffsetService;
import com.ebao.ap99.arap.service.ReversalService;
import com.ebao.ap99.arap.service.SettlementService;
import com.ebao.ap99.arap.service.SuspenseService;
import com.ebao.ap99.arap.vo.Balance;
import com.ebao.ap99.arap.vo.CreditsAndDebit;
import com.ebao.ap99.arap.vo.EntryItem;
import com.ebao.ap99.arap.vo.OffsetModel;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class OffsetServiceImpl implements OffsetService {

	@Autowired
	FeeService feeService;
	
	@Autowired
	SuspenseService suspenseService;
	
	@Autowired
	SettlementService settlementService;
	
	@Autowired
	CurrencyExchangeService exchangeService;
	
	@Autowired
	ReversalService reversalService;
	
	@Autowired
	OffsetDao offsetDao;

	@Autowired
	ReverseDao reverseDao;
	
	@Override
	@Transactional
	/**
	 * Submit offset transaction
	 */
	public String offset(OffsetModel model) throws Exception {
		Assert.isNotNull(model);
		
		checkOffsetModel(model);
		
		calcOtherAmountForOffset(model);
		
		Long offsetId = saveOffset(model);
		model.setOffsetId(offsetId);
		
		saveOffsetDetail(model);
		
		String offsetNo = settlementService.generateSettleNumber(FinanceTransactionType.INTERNAL_OFFSET.getType());
		Offset offset = offsetDao.load(offsetId);
		offset.setOffsetNo(offsetNo);
		offsetDao.save(offset);
		
		return offsetNo;
	}
	
	/**
	 * Check if offset model is valid
	 * @param model
	 * @throws Exception
	 */
	private void checkOffsetModel(OffsetModel model) throws Exception{
		if(CollectionUtils.isNotEmpty(model.getFeeList())){
			for(CreditsAndDebit cd : model.getFeeList()){
				cd.setSettlmentTransType(FinanceTransactionType.INTERNAL_OFFSET.getType());
			}
		}
		List<CreditsAndDebit> validFeeList = feeService.filterInvalidFee(model.getFeeList());
		model.setFeeList(validFeeList);// replace with the currency groups which including valid selected data
		
		if(validFeeList == null || validFeeList.size() == 0){
			throw new Exception("There is no any valid fee for offset.");
		}
		model.setSuspenseList(suspenseService.filterInvalidSuspense(model.getSuspenseList()));
		model.setOffsetAmount(getTotalOffsetAmount(model));
	}
	
	/**
	 * Calculate mark off amount, settle difference for offset entry items
	 * @param model
	 * @throws Exception
	 */
	private void calcOtherAmountForOffset(OffsetModel model) throws Exception{
		List<CreditsAndDebit> cdList = model.getFeeList();
		if(cdList != null){
			BigDecimal markOffAmount = null;
			BigDecimal exchangeRate = null;
			for(CreditsAndDebit cd : cdList){
				List<EntryItem> itemList = cd.getEntryItems();
				if(itemList != null){
					for(EntryItem item : itemList){
						if(item.getFeeId() != null){
							exchangeRate = exchangeService.getExchangeRate(item.getCurrency(), model.getCurrencyCode(), model.getOffsetDate());
							markOffAmount = item.getSettleAmount().multiply(exchangeRate).setScale(CurrencyConstants.DEFAULT_AMOUNT_SCALE, BigDecimal.ROUND_HALF_UP);
							item.setMarkOffAmount(markOffAmount);
							if(item.getFullySettle() !=null && item.getFullySettle().booleanValue()){
								item.setSettleDiff(item.getOsBalance().subtract(markOffAmount));
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Save offset summary information only
	 * @param model
	 * @return
	 * @throws Exception
	 */
	private Long saveOffset(OffsetModel model) throws Exception{
		Offset offset = new Offset();
		offset.setAmount(model.getOffsetAmount());
		offset.setCurrencyCode(model.getCurrencyCode());
		offset.setOffsetDate(model.getOffsetDate());
		offset.setRemark(model.getRemark());
		offset.setStatus(SettlementStatus.NORMAL.getType());
		offset.setRegistrationDate(model.getRegistrationDate());
		offsetDao.save(offset);
		return offset.getOffsetId();
	}
	
	/**
	 * calculate absolute total offset amount
	 * @param model
	 * @return
	 * @throws Exception
	 */
	private BigDecimal getTotalOffsetAmount(OffsetModel model) throws Exception{
		BigDecimal amount = BigDecimal.ZERO;
		List<CreditsAndDebit> feeGroupList = model.getFeeList();
		if(CollectionUtils.isNotEmpty(feeGroupList)){
			List<EntryItem> itemList = null;
			for(CreditsAndDebit feeGroup : feeGroupList){
				itemList = feeGroup.getEntryItems();
				if(itemList != null){
					for(EntryItem item : itemList){
						if(item.getFeeId()!= null 
								&& item.getSettleAmount() != null 
								&& item.getSettleAmount().compareTo(BigDecimal.ZERO) > 0){
							amount = amount.add(item.getSettleAmount());
						}
					}
				}
			}
		}
		
		/*List<Balance> suspenseList = model.getSuspenseList();
		if(CollectionUtils.isNotEmpty(suspenseList)){
			for(Balance suspense : suspenseList){
				if(suspense.getMarkOffAmountInSettleCurrency() != null){
					amount = amount.add(suspense.getMarkOffAmountInSettleCurrency());
				}
			}
		}*/
		return amount;
	}
	/**
	 * save Offset related data: mark off detail of fee and suspense information
	 * @param model
	 * @throws Exception
	 */
	private void saveOffsetDetail(OffsetModel model) throws Exception{
		Assert.isNotNull(model.getFeeList());
		List<CreditsAndDebit> feeGroupList = model.getFeeList();
		for(CreditsAndDebit cd : feeGroupList){
			cd.setSettlmentTransType(FinanceTransactionType.INTERNAL_OFFSET.getType());
			cd.setSettlementTransDate(model.getOffsetDate());
			cd.setSettlementGroupId(null);
			cd.setSettlementTransId(model.getOffsetId());
			cd.setSettleCurrencyCode(model.getCurrencyCode());
			cd.setSettleDate(model.getOffsetDate());
			
			settlementService.saveSettlementDetail(cd, FinanceTransactionType.INTERNAL_OFFSET.getType());
		}
		suspenseService.saveOffsetSuspense(model);
	}

	@Override
	public OffsetModel queryOffsetDetail(Long offsetId) throws Exception {
		Assert.isNotNull(offsetId);
		OffsetModel model = new OffsetModel();
		
		Offset offset = offsetDao.load(offsetId);
		Assert.isNotNull(offset);
		BeanUtils.copyPropertiesIngoreNull(model, offset);
		model.setOffsetAmount(offset.getAmount());
		
		List<Reverse> reverseList = reverseDao.getByTransNo(offset.getOffsetNo());
		if(CollectionUtils.isNotEmpty(reverseList)){
			Reverse reverse = reverseList.get(0);
			if(model.getRemark()==null){
				model.setRemark("");
			}
			model.setRemark(model.getRemark()+reversalService.getReversalViewInfo(reverse));
		}
		
		List<CreditsAndDebit> cdList = feeService.getSettlementFee(FinanceTransactionType.INTERNAL_OFFSET.getType(), offsetId);
		List<Balance> balanceList = suspenseService.getSettlementSuspense(FinanceTransactionType.INTERNAL_OFFSET.getType(), offsetId);
		model.setFeeList(cdList);
		model.setSuspenseList(balanceList);
		model.setTransNumber(offset.getOffsetNo());
		model.setTransStatus(offset.getStatus());
		model.setOperationDate(DateUtils.truncate(offset.getUpdateTime(), Calendar.DATE));
		model.setOperatorId(offset.getInsertBy().intValue());
		return model;
	}

}
