package com.ebao.ap99.arap.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.constant.ArapType;
import com.ebao.ap99.arap.constant.Constants;
import com.ebao.ap99.arap.constant.ContractCategory;
import com.ebao.ap99.arap.constant.CurrencyConstants;
import com.ebao.ap99.arap.constant.FeeSign;
import com.ebao.ap99.arap.constant.FeeStatus;
import com.ebao.ap99.arap.constant.FinanceConsistants;
import com.ebao.ap99.arap.constant.FinanceTransactionType;
import com.ebao.ap99.arap.constant.PostStatus;
import com.ebao.ap99.arap.constant.YesNoType;
import com.ebao.ap99.arap.dao.BCPTransDao;
import com.ebao.ap99.arap.dao.CollectionDao;
import com.ebao.ap99.arap.dao.FeeDao;
import com.ebao.ap99.arap.dao.OffsetDao;
import com.ebao.ap99.arap.dao.PaymentDao;
import com.ebao.ap99.arap.dao.SettlementDetailDao;
import com.ebao.ap99.arap.dao.SettlementGroupDao;
import com.ebao.ap99.arap.entity.BCPTrans;
import com.ebao.ap99.arap.entity.EntryCode;
import com.ebao.ap99.arap.entity.Fee;
import com.ebao.ap99.arap.entity.SettlementDetail;
import com.ebao.ap99.arap.entity.SettlementGroup;
import com.ebao.ap99.arap.service.CurrencyExchangeService;
import com.ebao.ap99.arap.service.EntryCodeService;
import com.ebao.ap99.arap.service.FeeService;
import com.ebao.ap99.arap.service.GLService;
import com.ebao.ap99.arap.service.SettlementService;
import com.ebao.ap99.arap.vo.BusinessFee;
import com.ebao.ap99.arap.vo.BusinessFeeModel;
import com.ebao.ap99.arap.vo.CreditsAndDebit;
import com.ebao.ap99.arap.vo.EntryItem;
import com.ebao.ap99.arap.vo.FeeQueryCondition;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.ap99.parent.service.BusinessTransTypeDS;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class FeeServiceImpl implements FeeService {

	@Autowired
	FeeDao feeDao;
	
	@Autowired
	CollectionDao collectionDao;
	
	@Autowired
	PaymentDao paymentDao;
	
	@Autowired
	OffsetDao offsetDao;
	
	@Autowired
	SettlementGroupDao settlementGroupDao;
	
	@Autowired
	SettlementDetailDao settlementDetailDao;
	
	@Autowired
	BCPTransDao bcpTransDao;
	
	@Autowired
	BusinessTransTypeDS businessTransTypeDS;
	
	@Autowired
	EntryCodeService entryCodeService;
	
	@Autowired
	CurrencyExchangeService currencyExchangeService;
	
	@Autowired
	SettlementService settlementService;
	
	@Autowired
	GLService glService;
	
	@Autowired
	ContractService contractService;
	
	@Override
	public Integer writeToFinance(List<BusinessFeeModel> bizModelList) throws Exception {
		Integer validStatus = validBusinessModelFee(bizModelList);
		if(validStatus != null){
			return validStatus;
		}
		BCPTrans bcpTrans = newTrans();
		
		List<Fee> financeFeeList = null;
		for(BusinessFeeModel bizModel : bizModelList){
			List<BusinessFee> bizFeeList = bizModel.getFeeList();
			if(CollectionUtils.isEmpty(bizFeeList)){
				continue;
			}
			financeFeeList = new ArrayList<Fee>();
			for(BusinessFee bizFee : bizFeeList){
				//if business premium is zero, it will not be sent to finance model
				if(bizFee.getAmount() != null){
					Fee fee = new Fee();
					EntryCode entryCode = entryCodeService.getByEntryCode(bizFee.getEntryCode());
					// ARAP type
					if(bizFee.isEstimation() ||
							(entryCode.getCashBalance() != null && entryCode.getCashBalance() == 0)){
						fee.setArapType(ArapType.OTHERS.getType());
					}
					//business sign
					fee.setSign(entryCode.getSign());//default sign for Assumed contract
					if(bizModel.getContractCategory() == ContractCategory.RETRO.getType()){
						fee.setSign(fee.getSign() * -1);
					}
					if(!ArapType.OTHERS.getType().equals(fee.getArapType())){
						// ARAP type of fee is base on cash balance type, estimation type and fee sign
						fee.setArapType(fee.getSign()>=0? ArapType.AR.getType() : ArapType.AP.getType());
					}
					
					fee.setAmount(bizFee.getAmount());
					fee.setBalance(bizFee.getAmount());
					fee.setEntryCode(bizFee.getEntryCode());
					fee.setContractCate(bizModel.getContractCategory());
					fee.setBizTransType(bizModel.getBizTransType());
					fee.setBizTransNo(bizModel.getBizTransNo());
					fee.setBizTranId(bizModel.getBizTransId());
					fee.setSpecialSubmit(bizModel.isSpecialSubmitInCutoffPeriod()?YesNoType.YES.getType():YesNoType.NO.getType());
					fee.setBizTransDesc(bizFee.getBizTransDesc());
					fee.setBizRefId(bizFee.getBizRefId());
					fee.setSectionId(bizFee.getSectionId());
					fee.setBizReversalFlag(bizFee.isReversal()?YesNoType.YES.getType():YesNoType.NO.getType());
					if(bizModel.getContractId() != null){
						fee.setContractId(bizModel.getContractId());
						fee.setContractCode(contractService.getContractCodeById(bizModel.getContractId()));
					}
					if(bizFee.getCurrentPeriod() != null){
						fee.setCurPeriod(bizFee.getCurrentPeriod());
					}
					if(bizFee.getTotalPeriod() != null){
						fee.setTotalPeriod(bizFee.getTotalPeriod());
					}
					fee.setCurrencyCode(bizFee.getCurrencyCode());
					if(bizFee.getDueDate() != null){
						fee.setDueDate(bizFee.getDueDate());
					}else{
						fee.setDueDate(DateUtils.truncate(AppContext.getSysDate(), Calendar.DATE));
					}
					fee.setIsEstimation(bizFee.isEstimation()?YesNoType.YES.getType():YesNoType.NO.getType());
					fee.setNeedPost(bizFee.isNeedPost()?YesNoType.YES.getType():YesNoType.NO.getType());
					fee.setPartnerCode(bizModel.getPartnerCode());
					fee.setPostStatus(bizFee.isNeedPost()?PostStatus.WAIT_FOR_POST.getType():null);
					fee.setStatus(FeeStatus.OUTSTANDING.getType());
					fee.setTransDate(AppContext.getSysDate());
					fee.setBizOperatorId(bizFee.getBizOperatorId()==null?bizModel.getBizOperatorId():bizFee.getBizOperatorId());
					fee.setTransId(bcpTrans.getTransId());
					financeFeeList.add(fee);
				}
			}
			if(CollectionUtils.isNotEmpty(financeFeeList)){
				feeDao.saveAll(financeFeeList);
				feeDao.getEntityManager().flush();
				glService.subLedgerWriteDirectly(bcpTrans.getTransId());
			}
		}
		return FinanceConsistants.WRITER_TO_FIN_SUCCESS;
	}
	
	private BCPTrans newTrans() throws Exception{
		BCPTrans bcpTrans = new BCPTrans();
		bcpTransDao.insert(bcpTrans);
		return bcpTrans;
	}
	
	private Integer validBusinessModelFee(List<BusinessFeeModel> modelList) throws Exception{
		if(CollectionUtils.isEmpty(modelList)){
			return FinanceConsistants.WRITER_TO_FIN_NO_DATA;
		}
		for(BusinessFeeModel feeModel : modelList){
			if(feeModel.getContractId() != null){
				if(!contractService.isInforceOrCancelledContract(feeModel.getContractId())){
					throw new Exception("Unsettled contract can not be written to finance, only contract in force or cancelled is allowed. ");
				}
			}
			List<BusinessFee> bizFeeList = feeModel.getFeeList();
			if(bizFeeList != null){
				if(feeModel.getBizTransType() == null){
					throw new Exception("Business transaction type is necessary.");
				}
				for(BusinessFee fee : bizFeeList){
					if(fee.getEntryCode() == null){
						throw new Exception("Entry code is necessary.");
					}else{
						if(entryCodeService.getByEntryCode(fee.getEntryCode()) == null){
							throw new Exception("The Entry code (" + fee.getEntryCode() + ") is invlaid.");
						}
					}
					if(fee.getCurrencyCode() == null){
						throw new Exception("Currency code is necessary.");
					}
					if(fee.getTotalPeriod() != null && fee.getCurrencyCode() == null){
						throw new Exception("Current installement no is necessary if total installements is more then 1.");
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public List<CreditsAndDebit> getFeeByCondition(FeeQueryCondition condition) throws Exception{
		Assert.isNotNull(condition);
		
		List<Fee> feeList = feeDao.getByCondition(condition);
		List<CreditsAndDebit> entryItemGroupList = getSettlementItemEntryList(feeList, condition.getSettleCurrency());
		prepareViewDataOnTransaction(condition.getFinanceTransType(),condition.getSettleDate(), entryItemGroupList, false);
		return entryItemGroupList;
	}
	
	public List<CreditsAndDebit> queryFeeInfoByCondition(FeeQueryCondition condition) throws Exception{
		condition.setQueryMode(true);
		List<CreditsAndDebit> entryItemGroupList = getFeeByCondition(condition);
		if(CollectionUtils.isNotEmpty(entryItemGroupList)){
			List<EntryItem> itemList = null;
			for(CreditsAndDebit entryGroup : entryItemGroupList){
				itemList = entryGroup.getEntryItems();
				if(CollectionUtils.isNotEmpty(itemList)){
					for(EntryItem item : itemList){
						if(item.getFeeId() == null && item.getFullySettle()){
							String relatedFeeIdArray= item.getFeeIdArray();
							if(StringUtils.isNotBlank(relatedFeeIdArray)){
								String[] feeIdArray = relatedFeeIdArray.split(",");
								if(StringUtils.isNotBlank(feeIdArray[0])){
									List<SettlementDetail> fullySettleList = settlementDetailDao.findFullySettleInfoByFeeId(Long.valueOf(feeIdArray[0].trim()));
									if(CollectionUtils.isNotEmpty(fullySettleList)){
										SettlementDetail settleDetail = fullySettleList.get(0);
										if(settleDetail.getCollectionId() != null){
											/*Collection settle = collectionDao.load(settleDetail.getCollectionId());
										item.setSettleTransNo(settle.getReceiptNo());*/
											item.setSettleTransType(FinanceTransactionType.COLLECTION.getType());
										}else if(settleDetail.getPaymentId() != null){
											/*Payment settle = paymentDao.load(settleDetail.getPaymentId());
										item.setSettleTransNo(settle.getPaymentNo());*/
											item.setSettleTransType(FinanceTransactionType.PAYMENT.getType());
										}else if(settleDetail.getOffsetId() != null){
											/*Offset settle = offsetDao.load(settleDetail.getOffsetId());
										item.setSettleTransNo(settle.getOffsetNo());*/
											item.setSettleTransType(FinanceTransactionType.INTERNAL_OFFSET.getType());
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return entryItemGroupList;
	}
	
	/**
	 * Invert sign of amount for payment display
	 * Calculate outstanding amount in settle currency for internal offset 
	 * @param financeTransType
	 * @param settleDate
	 * @param entryItemGroupList
	 * @param isViewMode
	 * @throws Exception
	 */
	private void prepareViewDataOnTransaction(Integer financeTransType, Date settleDate, List<CreditsAndDebit> entryItemGroupList, boolean isViewMode) throws Exception{
		Assert.isNotNull(entryItemGroupList);
		BigDecimal exchangeRate = null;
		BigDecimal osBalanceInSettleCurrency = null;
		List<EntryItem> itemList = null;
		for(CreditsAndDebit entryItemGroup : entryItemGroupList){
			if(financeTransType != null && financeTransType == FinanceTransactionType.PAYMENT.getType()){
				if(entryItemGroup.getAmount() != null){
					entryItemGroup.setAmount(entryItemGroup.getAmount().negate());
				}
				if(entryItemGroup.getConvertedAmount() != null){
					entryItemGroup.setConvertedAmount(entryItemGroup.getConvertedAmount().negate());
				}
			}
			
			itemList = entryItemGroup.getEntryItems();
			if(itemList != null){
				for(EntryItem item : itemList){
					if(financeTransType != null && financeTransType == FinanceTransactionType.PAYMENT.getType()){
						settlementService.feeEntryItemNegate(item);
					}
					if(financeTransType != null && financeTransType == FinanceTransactionType.INTERNAL_OFFSET.getType()){
						exchangeRate = currencyExchangeService.getExchangeRate(item.getCurrency(), item.getSettleCurrency(), settleDate);
						if(item.getOsBalance() != null){
							osBalanceInSettleCurrency = item.getOsBalance().divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE, BigDecimal.ROUND_HALF_UP);
							item.setOsBalanceInSettleCurrency(osBalanceInSettleCurrency);
							if(!isViewMode){//outstanding amount in settle currency is default as settle amount for offset
								item.setSettleAmount(item.getOsBalanceInSettleCurrency());
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Build and group settlement information with fee data, if settlement detail exist in the fee, setup corresponding settlement detail.
	 * 
	 * @param feeList
	 * @param settleCurrencyCode
	 * @return
	 * @throws Exception
	 */
	private List<CreditsAndDebit> getSettlementItemEntryList(List<Fee> feeList, String settleCurrencyCode) throws Exception{
		List<CreditsAndDebit> arapGroupList = new ArrayList<CreditsAndDebit>();
		
		Map<String, CreditsAndDebit> currencyGroupMap = new HashMap<String, CreditsAndDebit>();
		List<String> bizNoSumList = new LinkedList<String>();//display bizNoSummary in sequence
		Map<String, EntryItem> bizNoSumMap = new HashMap<String, EntryItem>();
		Map<String, List<EntryItem>> bizNoDetailMap = new HashMap<String, List<EntryItem>>();
		
		if(feeList != null && feeList.size() > 0){
			 //Group by currency and business transaction no
			 for(Fee fee : feeList){
				 if(currencyGroupMap.get(fee.getCurrencyCode())==null){
					 CreditsAndDebit cd = new CreditsAndDebit();
					 List<EntryItem> itemList = new LinkedList<EntryItem>();
					 cd.setEntryItems(itemList);
					 cd.setCurrencyCode(fee.getCurrencyCode());
					 currencyGroupMap.put(fee.getCurrencyCode(), cd);
				 }
				 if(bizNoDetailMap.get(fee.getBizTransNoAndCurrency()) == null){
					 List<EntryItem> itemList = new LinkedList<EntryItem>();
					 EntryItem item = new EntryItem();
					 BeanUtils.copyPropertiesIngoreNull(item, fee);
					 item.setFeeId(null);//Indicate the entry item is summary record
					 SettlementDetail settlement = fee.getSettleDetail();
					 if(settlement != null){
						 settleCurrencyCode = settlement.getSettleCurrencyCode();
						 item.setContractSelect(Boolean.TRUE);
						 if(settlement.getFullySettle() != null && settlement.getFullySettle() == Constants.FULLYSETTLE_TYPE_1){
							 item.setFullySettle(Boolean.TRUE);
						 }
					 }
					 item.setContractID(fee.getContractId());
					 item.setCurrency(fee.getCurrencyCode());
					 item.setSettleCurrency(settleCurrencyCode);
					 item.setAmountOC(BigDecimal.ZERO);
					 item.setOsBalance(BigDecimal.ZERO);
					 // meiliang.zou add mark-off amount = OS balance
					 item.setContractCode(fee.getContractCode());
					 item.setMarkOffAmount(BigDecimal.ZERO);
					 item.setSettleAmount(BigDecimal.ZERO);
					 //Unique id
					 item.setFeeIdStr(fee.getBizTransNoAndCurrency());
					 // meiliang.zou add parentFeeIdStr
					 item.setParentFeeIdStr(fee.getBizTransNoAndCurrency());
					 
					 itemList.add(item);
					 bizNoSumList.add(fee.getBizTransNoAndCurrency());
					 bizNoSumMap.put(fee.getBizTransNoAndCurrency(), item);
					 bizNoDetailMap.put(fee.getBizTransNoAndCurrency(), itemList);
				 }
				 //build settlement detail info
				 EntryItem itemDtl = getSettlementItemWithFee(fee);
				 itemDtl.setSettleCurrency(settleCurrencyCode);
				 
				 //Set up relation between group fee and corresponding detail fee 
				 itemDtl.setFeeIdStr(fee.getFeeId().toString());
				 itemDtl.setParentFeeIdStr(bizNoSumMap.get(fee.getBizTransNoAndCurrency()).getParentFeeIdStr());
				 
				 EntryItem groupItem = bizNoSumMap.get(fee.getBizTransNoAndCurrency());
				 groupItem.setOsBalance(groupItem.getOsBalance().add(itemDtl.getOsBalance()));
				 groupItem.setAmountOC(groupItem.getAmountOC().add(itemDtl.getAmountOC()));
				 groupItem.setType(FeeSign.getDCName(groupItem.getOsBalance()));
				 groupItem.addFeeIdToArray(itemDtl.getFeeId().toString());//for general query function
				 
				 bizNoDetailMap.get(fee.getBizTransNoAndCurrency()).add(itemDtl);
			 }

			 //Join currency group and group fee detail
			 for(String bizNo : bizNoSumList){
				 EntryItem summFee = bizNoSumMap.get(bizNo);
				 CreditsAndDebit cd = currencyGroupMap.get(summFee.getCurrency());
				 cd.getEntryItems().addAll(bizNoDetailMap.get(bizNo));
			 }
			arapGroupList.addAll(currencyGroupMap.values());
		 }
		 else {
			 arapGroupList.add(new CreditsAndDebit());
		 }
		 
		return arapGroupList;
	}
	
	/**
	 * Build entry item (settlement detail information) using fee data for display
	 * @param fee
	 * @return
	 * @throws Exception
	 */
	private EntryItem getSettlementItemWithFee(Fee fee) throws Exception{
		Assert.isNotNull(fee);
		SettlementDetail settle = fee.getSettleDetail();
		
		EntryItem item = new EntryItem();
		BeanUtils.copyPropertiesIngoreNull(item, fee);
		
		item.setContractCode(null);
		item.setBizTransType(null);
		item.setBizTransNo(null);
		item.setCurrency(fee.getCurrencyCode());
		item.setAmountOC(fee.getAmount().multiply(new BigDecimal(fee.getSign())));
		BigDecimal osBalance = fee.getBalance().multiply(new BigDecimal(fee.getSign()));
		item.setOsBalance(osBalance);
		item.setMarkOffAmount(osBalance);
		//item.setSettleAmount(osBalance);
		if(fee.getBalanceInSettleCurrency() != null){
			item.setOsBalanceInSettleCurrency(fee.getBalanceInSettleCurrency().multiply(new BigDecimal(fee.getSign())));
		}
		item.setDescription(entryCodeService.getByEntryCode(fee.getEntryCode()).getEntryCodeName());
		if(FeeStatus.FULLYSETTLE.getType().equals(fee.getStatus())){
			item.setFullySettle(true);
		}
		if(settle == null){//query outstanding fee for settlement
			osBalance = fee.getBalance().multiply(BigDecimal.valueOf(fee.getSign()));
			item.setOsBalance(osBalance);
			item.setMarkOffAmount(osBalance);
			// meiliang.zou add 2016.5.3
			// item.setSettleDiff(BigDecimal.valueOf(0d).setScale(2));
		}
		if(settle != null){//query settlement(collection/payment/offset) detail
			item.setAmountOC(fee.getAmount());
			item.setOsBalance(settle.getOutstandingAmount());
			item.setMarkOffAmount(settle.getMarkOffAmount());
			item.setExchangeRate(settle.getExchangeRate());
			item.setSettleDiff(settle.getAmountDiff());
			item.setGainLoss(settle.getGainLoss());
			item.setSettleCurrency(settle.getSettleCurrencyCode());
			item.setSettleAmount(settle.getSettleAmount());
		}
		return item;
	}
	
	/**
	 * remove group & unselected records from payment model
	 * @param feeList
	 * @return valid fee list
	 * @throws Exception
	 */
	public List<CreditsAndDebit> filterInvalidFee(List<CreditsAndDebit> feeList) throws Exception{
		List<CreditsAndDebit> validFeeList = new ArrayList<CreditsAndDebit>();
		if(feeList != null){
			for(CreditsAndDebit cd : feeList){
				if(cd.getAmount() != null || FinanceTransactionType.INTERNAL_OFFSET.getType().equals(cd.getSettlmentTransType())){
					List<EntryItem> itemList = cd.getEntryItems();
					if(itemList != null && itemList.size() > 0){
						List<EntryItem> validItemList = new ArrayList<EntryItem>();
						Map<String,EntryItem> parentMap = getParentEntryItem(itemList);
						for(EntryItem item : itemList){
							EntryItem parent = parentMap.get(item.getParentFeeIdStr());
							if(parent == null){
								throw new Exception("Parent of fee item is null.");
							}
							if(parent.isContractSelect() != null && parent.isContractSelect()){
								item.setContractSelect(Boolean.TRUE);
							}else{
								item.setContractSelect(Boolean.FALSE);
							}
							if(parent.getFullySettle() != null){
								item.setFullySettle(parent.getFullySettle());
							}else{
								item.setFullySettle(Boolean.FALSE);
							}
							//only selected credit&debit is valid data
							if(item.isContractSelect() && item.getFeeId() != null
									&& item.getSettleAmount()!= null && item.getSettleAmount().compareTo(BigDecimal.ZERO) != 0){
								validItemList.add(item);
							}
						}
						if(validItemList.size() > 0){
							cd.setEntryItems(validItemList);// replace with valid selected data
							validFeeList.add(cd);
						}
					}
				}
			}
		}
		return validFeeList;
	}
	
	private Map<String,EntryItem> getParentEntryItem(List<EntryItem> itemList) throws Exception{
		Assert.isNotNull(itemList);
		Map<String, EntryItem> parentItemMap = new HashMap<String, EntryItem>();
		for(EntryItem item : itemList){
			if(item.getFeeId() == null){
				parentItemMap.put(item.getFeeIdStr(), item);
			}
		}
		return parentItemMap;
	}
	
	@Override
	public BigDecimal calGainLossInUSD(Long feeId, BigDecimal markOffAmount, String originalCurrency, BigDecimal settleAmount, String settleCurrency, Date settleDate) throws Exception{
		Fee fee = feeDao.load(feeId);
		Assert.isNotNull(fee);
		return calcGainLoss(markOffAmount, originalCurrency, fee.getTransDate(), settleAmount, settleCurrency, settleDate, CurrencyConstants.CURRENCY_USD);
	}
	
	@Override
	public BigDecimal calcGainLoss(BigDecimal originalAmount, String originalCurrency, String gainLossCurrency, Date originalDate, Date settleDate) throws Exception{
		BigDecimal originalAmt
			= currencyExchangeService.exchangeAmount(originalAmount, originalCurrency, gainLossCurrency, originalDate);		
		BigDecimal currentAmt
			= currencyExchangeService.exchangeAmount(originalAmount, originalCurrency, gainLossCurrency, settleDate);
		return currentAmt.subtract(originalAmt);
	}
	
	@Override
	public BigDecimal calcGainLoss(BigDecimal originalAmount, String originalCurrecy, Date originalDate, BigDecimal settleAmount, String settleCurrency, Date settleDate, String gainLossCurrency) throws Exception{
		BigDecimal originalAmt = currencyExchangeService.exchangeAmount(originalAmount, originalCurrecy, gainLossCurrency, originalDate);		
		BigDecimal currentAmt = currencyExchangeService.exchangeAmount(settleAmount, settleCurrency, gainLossCurrency, settleDate);
		return currentAmt.subtract(originalAmt);
	}
	
	@Override
	public BigDecimal convertAmount(BigDecimal amount, String originalCurrency, String settleCurrency, Date transDate) throws Exception{
		BigDecimal result = BigDecimal.ZERO;
		
		BigDecimal exchangeRate = currencyExchangeService.getExchangeRate(originalCurrency, settleCurrency, transDate);
		if (null != amount && exchangeRate != null && exchangeRate.compareTo(result) == 1) {
			result = amount.divide(exchangeRate, CurrencyConstants.DEFAULT_AMOUNT_SCALE, BigDecimal.ROUND_HALF_UP);
		}
		
		return result;
	}
	
	public void adjustFee(Long feeId, BigDecimal adjustAmount, boolean isFullySettle) throws Exception{
		Fee fee = feeDao.load(feeId);
		Assert.isNotNull(fee);
		
		BigDecimal balance = fee.getBalance();
		BigDecimal sign = BigDecimal.valueOf(fee.getSign());
		
		if(isFullySettle){
			balance = BigDecimal.ZERO;
			fee.setStatus(FeeStatus.FULLYSETTLE.getType());
		}else{
			balance = balance.subtract(adjustAmount.multiply(sign));
		}
		//fee.setSign(balance.compareTo(BigDecimal.ZERO) >= 0 ? FeeSign.POSITIVE.getType() : FeeSign.NEGATIVE.getType());
		fee.setBalance(balance);
		if(fee.getBalance().compareTo(BigDecimal.ZERO) != 0){
			fee.setStatus(FeeStatus.OUTSTANDING.getType());
		}
		feeDao.save(fee);
	}
	
	@Override
	public List<CreditsAndDebit> getSettlementFee(Integer settleTransType, Long settleTransId) throws Exception{
		List<CreditsAndDebit> entryItemGroupList = new ArrayList<CreditsAndDebit>();
		
		List<SettlementDetail> settleList = null;
		List<Fee> feeList = null;
		CreditsAndDebit cd = null;
		Date settleDate = null;
		
		List<SettlementGroup> currencyGroupList = settlementGroupDao.findByTransId(settleTransType,settleTransId);
		if(CollectionUtils.isNotEmpty(currencyGroupList)){
			for(SettlementGroup group : currencyGroupList){
				
				settleList = settlementDetailDao.findSettlementFeeByTransId(settleTransType,settleTransId,group.getSettleGroupId());
				if(CollectionUtils.isNotEmpty(settleList)){
					settleDate = settleList.get(0).getSettleDate();
					feeList = buildFeeWithSettlement(settleList);
					List<CreditsAndDebit> feeGroupList = getSettlementItemEntryList(feeList, null);
					if(feeGroupList != null && feeGroupList.size() > 0){
						cd = feeGroupList.get(0);// one currency group mapping to one CreditsAndDebit
						cd.setAmount(group.getAmount());
						cd.setConvertedAmount(group.getConvertedAmount());
						cd.setExchangeRate(group.getExchangeRate());
						entryItemGroupList.add(cd);
					}
				}
			}
		}else{
			settleList = settlementDetailDao.findSettlementFeeByTransId(settleTransType, settleTransId, null);
			if(CollectionUtils.isNotEmpty(settleList)){
				settleDate = settleList.get(0).getSettleDate();
				feeList = buildFeeWithSettlement(settleList);
				entryItemGroupList.addAll(getSettlementItemEntryList(feeList, null));
			}
		}
		prepareViewDataOnTransaction(settleTransType, settleDate, entryItemGroupList, true);
		return entryItemGroupList;
	}
	
	/**
	 * Build fee detail information with settlement data
	 * @param settleList
	 * @return
	 * @throws Exception
	 */
	private List<Fee> buildFeeWithSettlement(List<SettlementDetail> settleList) throws Exception{
		List<Fee> feeList = new ArrayList<Fee>();
		if(settleList != null){
			Fee fee = null;
			for(SettlementDetail settle : settleList){
				fee = new Fee();
				BeanUtils.copyPropertiesIngoreNull(fee, settle);
				fee.setBalance(settle.getOutstandingAmount());
				
				fee.setSettleDetail(settle);
				feeList.add(fee);
			}
		}
		return feeList;
	}
}
