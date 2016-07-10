package com.ebao.ap99.arap.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ebao.ap99.arap.constant.FinanceTransactionType;
import com.ebao.ap99.arap.constant.SettlementStatus;
import com.ebao.ap99.arap.dao.BankAccountDao;
import com.ebao.ap99.arap.dao.BankDao;
import com.ebao.ap99.arap.dao.CollectionDao;
import com.ebao.ap99.arap.dao.ReverseDao;
import com.ebao.ap99.arap.dao.SettlementDetailDao;
import com.ebao.ap99.arap.dao.SettlementGroupDao;
import com.ebao.ap99.arap.dao.SuspenseDao;
import com.ebao.ap99.arap.entity.Bank;
import com.ebao.ap99.arap.entity.Collection;
import com.ebao.ap99.arap.entity.Reverse;
import com.ebao.ap99.arap.entity.SettlementGroup;
import com.ebao.ap99.arap.service.CollectionService;
import com.ebao.ap99.arap.service.FeeService;
import com.ebao.ap99.arap.service.ReversalService;
import com.ebao.ap99.arap.service.SettlementService;
import com.ebao.ap99.arap.service.SuspenseService;
import com.ebao.ap99.arap.util.DateUtil;
import com.ebao.ap99.arap.vo.Balance;
import com.ebao.ap99.arap.vo.CollectionModel;
import com.ebao.ap99.arap.vo.CreditsAndDebit;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.ap99.partner.service.PartnerAPI;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class CollectionServiceImpl implements CollectionService {

	@Autowired
	CollectionDao collectionDao;
	
	@Autowired
	SettlementDetailDao settlementDetailDao;
	
	@Autowired
	SettlementGroupDao settlementGroupDao;
	
	@Autowired
	SuspenseDao suspenseDao;
	
	@Autowired
	ReverseDao reverseDao;
	
	@Autowired
	BankDao bankDao;
	
	@Autowired
	BankAccountDao bankAccountDao;
	
	@Autowired
	SettlementService settlementService;
	
	@Autowired
	SuspenseService suspenseService;
	
	@Autowired
	FeeService feeService;
	
	@Autowired
	ReversalService reversalService;
	
	@Autowired
	PartnerAPI partnerAPI;

	@Override
	@Transactional

	public String collection(CollectionModel model) throws Exception {
		Assert.isNotNull(model);
		
		checkCollectionModel(model);
		
		Long collectionId = saveCollection(model);
		model.setCollectionId(collectionId);
		
		saveCollectionDetail(model);
		
		String receiptNo = settlementService.generateSettleNumber(FinanceTransactionType.COLLECTION.getType());
		Collection collection = collectionDao.load(collectionId);
		collection.setReceiptNo(receiptNo);
		collectionDao.save(collection);
		
		return receiptNo;
	}
	
	/**
	 * Check if the collection model has valid data, and remove unselected records
	 * @param model
	 * @throws Exception
	 */
	private void checkCollectionModel(CollectionModel model) throws Exception{
		List<CreditsAndDebit> validFeeList = feeService.filterInvalidFee(model.getFeeList());
		model.setFeeList(validFeeList);// replace with the currency groups which including valid selected data
		
		List<Balance> balanceList = model.getBalanceList();
		
		if((validFeeList == null || validFeeList.size() == 0)&&(balanceList == null || balanceList.size() == 0)){
			throw new Exception("There is no any valid data for collection.");
		}
	}
	
	/**
	 * save collection summary information only
	 * @param model
	 * @return
	 * @throws Exception
	 */
	private Long saveCollection(CollectionModel model) throws Exception{
		
		Collection collection = new Collection();
		collection.setAmount(model.getCollectionAmount());
		collection.setBankAccountNo(model.getCollectionBankAccountNo());
		collection.setBankCharge(model.getBankCharge());
		collection.setBankCode(model.getBankCode());
		collection.setChequeDate(model.getChequeDate());
		collection.setChequeHolderName(model.getChequeHolderName());
		collection.setChequeNo(model.getChequeNo());
		collection.setCollectionDate(model.getCollectionDate());
		collection.setValueDate(model.getValueDate());
		collection.setCurrencyCode(model.getCollectionCurrency());
		collection.setPayerCode(model.getPayerCode());
		collection.setPayMode(model.getPaymentMethod());
		collection.setRemark(model.getRemark());
		collection.setStatus(SettlementStatus.NORMAL.getType());
		collection.setTransDate(DateUtil.getTimeStamp());
		collection.setExchangeRateRef(model.getExchangeRateReference());
		
		collectionDao.save(collection);
		return collection.getCollectionId();
	}
	
	/**
	 * save collection related data: mark off detail and suspense collection information
	 * @param model
	 * @throws Exception
	 */
	private void saveCollectionDetail(CollectionModel model) throws Exception{
		Assert.isNotNull(model.getFeeList());
		List<CreditsAndDebit> feeGroupList = model.getFeeList();
		if(feeGroupList != null && feeGroupList.size() > 0){
			for(CreditsAndDebit cd : feeGroupList){
				SettlementGroup settleGroup = new SettlementGroup(); 
				settleGroup.setAmount(cd.getAmount());
				settleGroup.setCurrencyCode(model.getCollectionCurrency());
				settleGroup.setConvertedAmount(cd.getConvertedAmount());
				settleGroup.setConvertCurrencyCode(cd.getCurrencyCode());
				settleGroup.setExchangeRate(cd.getExchangeRate());
				settlementGroupDao.save(settleGroup);
				
				cd.setSettlmentTransType(FinanceTransactionType.COLLECTION.getType());
				cd.setSettlementTransDate(model.getCollectionDate());
				cd.setSettlementGroupId(settleGroup.getSettleGroupId());
				cd.setSettlementTransId(model.getCollectionId());
				cd.setSettleCurrencyCode(model.getCollectionCurrency());
				cd.setSettleDate(model.getCollectionDate());
				settlementService.saveSettlementDetail(cd, FinanceTransactionType.COLLECTION.getType());
			}
		}
		if(model.getBalanceList() != null && model.getBalanceList().size() > 0){
			suspenseService.saveCollectionSuspense(model);
		}
	}
	
	@Override
	public CollectionModel queryCollectionDetail(Long collectionId) throws Exception{
		Assert.isNotNull(collectionId);
		CollectionModel model = new CollectionModel();
		
		Collection collection = collectionDao.load(collectionId);
		Assert.isNotNull(collection);
		
		List<CreditsAndDebit> cdList = feeService.getSettlementFee(FinanceTransactionType.COLLECTION.getType(), collectionId);
		List<Balance> balanceList = suspenseService.getSettlementSuspense(FinanceTransactionType.COLLECTION.getType(), collectionId);
		
		BeanUtils.copyPropertiesIngoreNull(model, collection);
		
		List<Reverse> reverseList = reverseDao.getByTransNo(collection.getReceiptNo());
		if(CollectionUtils.isNotEmpty(reverseList)){
			Reverse reverse = reverseList.get(0);
			if(model.getRemark()==null){
				model.setRemark("");
			}
			model.setRemark(model.getRemark()+reversalService.getReversalViewInfo(reverse));
		}
		model.setCollectionAmount(collection.getAmount());
		BigDecimal bankCharge = collection.getBankCharge() == null ? BigDecimal.ZERO : collection.getBankCharge();
		model.setNetAmount(collection.getAmount().subtract(bankCharge));
	    String accountName = "";
		if (StringUtils.isNotBlank(collection.getBankAccountNo())){
			accountName = bankAccountDao.queryAccountNameByAccountNo(collection.getBankAccountNo());
		}
		model.setCollectionBankAccountNo(accountName);
		model.setCollectionCurrency(collection.getCurrencyCode());
		model.setPaymentMethod(collection.getPayMode());
		String bankCode = collection.getBankCode();
		model.setBankCode(bankCode);
		if(StringUtils.isNotBlank(bankCode)){
			Bank bank = bankDao.load(Long.valueOf(bankCode));
			if(bank != null){
				model.setBankName(bank.getBankName());
			}
		}
		model.setExchangeRateReference(collection.getExchangeRateRef());
		model.setTransNumber(collection.getReceiptNo());
		model.setTransStatus(collection.getStatus());
		model.setOperationDate(DateUtils.truncate(collection.getUpdateTime(), Calendar.DATE));
		model.setOperatorId(collection.getInsertBy().intValue());
		model.setPayerCode(collection.getPayerCode());
	    model.setPayerName(getParyerNameByCode(collection.getPayerCode()));
		
		model.setFeeList(cdList);
		model.setBalanceList(balanceList);
		
		return model;
	}
	
	// meiliang.zou modify 2016.4.21
	private String getParyerNameByCode(String payerCode){
		return partnerAPI.loadPartnerNameByPartnerCode(payerCode);
	}
	
}
