package com.ebao.ap99.arap.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.constant.Constants;
import com.ebao.ap99.arap.constant.CurrencyConstants;
import com.ebao.ap99.arap.constant.FinanceTransactionType;
import com.ebao.ap99.arap.constant.PostStatus;
import com.ebao.ap99.arap.constant.SettlementExchangeSourceType;
import com.ebao.ap99.arap.constant.SettlementStatus;
import com.ebao.ap99.arap.constant.SortOrder;
import com.ebao.ap99.arap.constant.YesNoType;
import com.ebao.ap99.arap.dao.CollectionDao;
import com.ebao.ap99.arap.dao.NumberingDao;
import com.ebao.ap99.arap.dao.OffsetDao;
import com.ebao.ap99.arap.dao.PayeeDao;
import com.ebao.ap99.arap.dao.PaymentDao;
import com.ebao.ap99.arap.dao.ReverseDao;
import com.ebao.ap99.arap.dao.SetllementExchangeDetailDao;
import com.ebao.ap99.arap.dao.SettlementDetailDao;
import com.ebao.ap99.arap.dao.SettlementGroupDao;
import com.ebao.ap99.arap.dao.SuspenseDao;
import com.ebao.ap99.arap.entity.Collection;
import com.ebao.ap99.arap.entity.Offset;
import com.ebao.ap99.arap.entity.Payment;
import com.ebao.ap99.arap.entity.Reverse;
import com.ebao.ap99.arap.entity.SetllementExchangeDetail;
import com.ebao.ap99.arap.entity.SettlementDetail;
import com.ebao.ap99.arap.service.CurrencyExchangeService;
import com.ebao.ap99.arap.service.FeeService;
import com.ebao.ap99.arap.service.SettlementService;
import com.ebao.ap99.arap.service.SuspenseService;
import com.ebao.ap99.arap.vo.CreditsAndDebit;
import com.ebao.ap99.arap.vo.EntryItem;
import com.ebao.ap99.arap.vo.SettlementHistory;
import com.ebao.ap99.arap.vo.SettlementTransaction;
import com.ebao.ap99.arap.vo.TransactionQueryCondition;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.ap99.parent.constant.NumberingType;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.ap99.partner.service.PartnerAPI;
import com.ebao.unicorn.platform.data.helper.DataHelper;
import com.ebao.unicorn.platform.foundation.context.AppUser;
import com.ebao.unicorn.platform.foundation.numbering.NumberingFactor;
import com.ebao.unicorn.platform.foundation.numbering.NumberingService;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class SettlementServiceImpl implements SettlementService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	CollectionDao collectionDao;
	
	@Autowired
	PaymentDao paymentDao;
	
	@Autowired
	OffsetDao offsetDao;
	
	@Autowired
	ReverseDao reverseDao;
	
	@Autowired
	SuspenseDao suspenseDao;
	
	@Autowired
	PayeeDao payeeDao;
	
	@Autowired
	SettlementGroupDao settlementGroupDao;
	
	@Autowired
	SettlementDetailDao settlementDetailDao;
	
	@Autowired
	SetllementExchangeDetailDao setllementExchangeDetailDao;
	
	@Autowired
	private NumberingDao numberingDao;
	
	@Autowired
	private NumberingService numService;
	
	@Autowired
	private FeeService feeService;
	
	@Autowired
	private SuspenseService suspenseService;
	
	@Autowired
	private CurrencyExchangeService currencyExchangeService;
	
	@Autowired
	private DataHelper dataHelper;
	
	@Autowired
	PartnerAPI partnerService;
	
	/*@Override
	public List<SettlementTransaction> getSettleTransactionByCondition(
			TransactionQueryCondition condition) throws Exception {
		List<SettlementTransaction> transList = new ArrayList<SettlementTransaction>();
		if(condition.getTransType() == null 
				|| FinanceTransactionType.COLLECTION.getType().compareTo(condition.getTransType())==0
				|| FinanceTransactionType.REVERSAL.getType().compareTo(condition.getTransType())==0){
			List<Collection> list = collectionDao.getByCondition(condition);
			if(list != null && list.size() > 0){
				for(Collection collection : list){
					transList.add(buildCollectionSettleTrans(collection));
				}
			}
		}
		if(condition.getTransType() == null 
				|| FinanceTransactionType.PAYMENT.getType().compareTo(condition.getTransType())==0
				|| FinanceTransactionType.REVERSAL.getType().compareTo(condition.getTransType())==0){
			List<Payment> list = paymentDao.getByCondition(condition);
			if(list != null && list.size() > 0){
				for(Payment payment : list){
					transList.add(buildPaymentSettleTrans(payment));
				}
			}
		}
		if(condition.getTransType() == null 
				|| FinanceTransactionType.INTERNAL_OFFSET.getType().compareTo(condition.getTransType())==0
				|| FinanceTransactionType.REVERSAL.getType().compareTo(condition.getTransType())==0){
			List<Offset> list = offsetDao.getByCondition(condition);
			if(list != null && list.size() > 0){
				for(Offset offset : list){
					transList.add(buildOffsetSettleTrans(offset));
				}
			}
		}
		sortTransactions(transList, condition.getSortOrder());
		return transList;
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SettlementTransaction> getSettleTransactionByCondition(TransactionQueryCondition condition, Integer pageNo, Integer pageSize) throws Exception {
		List<SettlementTransaction> transList = new ArrayList<SettlementTransaction>();
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sql = buildSettleTransactionQuerySQL(condition,params);
		Query query = em.createNativeQuery(sql);
		
		String key;
		for(Iterator<String> ite = params.keySet().iterator();ite.hasNext();){
			key = ite.next();
			query.setParameter(key, params.get(key));
		}
		
		if(pageNo != 0){
			int start = dataHelper.firstPageResult(pageNo, pageSize);
			query.setFirstResult(start);
			query.setMaxResults(pageSize);
		}
		List<Object[]> resultList = query.getResultList();
		if(CollectionUtils.isNotEmpty(resultList)){
			String settleNo;
			Integer settleType;
			Collection collection;
			Offset offset;
			Payment payment;
			for(Object[] result: resultList){
				settleNo = (String)result[0];
				settleType = Integer.valueOf(result[1].toString());
				switch(settleType.intValue()){
				case 1:
					collection = collectionDao.getByReceiptNo(settleNo);
					transList.add(buildCollectionSettleTrans(collection));
					break;
				case 2:
					payment = paymentDao.getByPaymentNo(settleNo);
					transList.add(buildPaymentSettleTrans(payment));
					break;
				case 3:
					offset = offsetDao.getByOffsetNo(settleNo);
					transList.add(buildOffsetSettleTrans(offset));
					break;
				}
			}
		}
		return transList;
	}
	
	@Override
	public Long countSettleTransaction(TransactionQueryCondition condition) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from (");
		sql.append(buildSettleTransactionQuerySQL(condition, params));
		sql.append(")");
		Query query = em.createNativeQuery(sql.toString());
		String key;
		for(Iterator<String> ite = params.keySet().iterator();ite.hasNext();){
			key = ite.next();
			query.setParameter(key, params.get(key));
		}
		return Long.valueOf(query.getSingleResult().toString());
	
	}
	
	
	private String buildSettleTransactionQuerySQL(TransactionQueryCondition condition, Map<String, Object> params) throws Exception{
		StringBuffer querySql = new StringBuffer();
		querySql.append(" select settle_no, settle_type, operate_date from (");
		String sql;
		boolean isUnion = false;
		if(condition.getTransType() == null 
				|| FinanceTransactionType.COLLECTION.getType().compareTo(condition.getTransType())==0
				|| FinanceTransactionType.REVERSAL.getType().compareTo(condition.getTransType())==0){
			sql = collectionDao.buildCollectionQuerySQL(condition, params);
			if(StringUtils.isNotBlank(sql)){
				querySql.append(sql);
				isUnion = true;
			}
		}
		if(condition.getTransType() == null 
				|| FinanceTransactionType.PAYMENT.getType().compareTo(condition.getTransType())==0
				|| FinanceTransactionType.REVERSAL.getType().compareTo(condition.getTransType())==0){
			sql = paymentDao.buildPaymentQuerySQL(condition, params);
			if(StringUtils.isNotBlank(sql)){
				querySql.append(isUnion?" union ":"");
				querySql.append(sql);
				isUnion = true;
			}
		}
		if(condition.getTransType() == null 
				|| FinanceTransactionType.INTERNAL_OFFSET.getType().compareTo(condition.getTransType())==0
				|| FinanceTransactionType.REVERSAL.getType().compareTo(condition.getTransType())==0){
			sql = offsetDao.buildOffsetQuerySQL(condition, params);
			if(StringUtils.isNotBlank(sql)){
				querySql.append(isUnion?" union ":"");
				querySql.append(sql);
				isUnion = true;
			}
		}
		if(!isUnion){//there is no any SQL
			querySql.append("select 0 settle_no, 0 settle_type, sysdate operate_date from dual where 1=2 ");
		}
		querySql.append(") order by operate_date desc");
		return querySql.toString();
	}
	
	@Override
	public Long getTransIdByNo(Integer financeTransType, String transNo) throws Exception{
		Long transId = null;
		if(FinanceTransactionType.COLLECTION.getType().compareTo(financeTransType)==0){
			Collection trans = collectionDao.getByReceiptNo(transNo);
			transId = trans.getCollectionId();
		}
		if(FinanceTransactionType.PAYMENT.getType().compareTo(financeTransType)==0){
			Payment trans = paymentDao.getByPaymentNo(transNo);
			transId = trans.getPaymentId();
		}
		if(FinanceTransactionType.INTERNAL_OFFSET.getType().compareTo(financeTransType)==0){
			Offset trans = offsetDao.getByOffsetNo(transNo);
			transId = trans.getOffsetId();
		}
		return transId;
	}
	
	private Date getTransDateByNo(Integer financeTransType, String transNo) throws Exception{
		Date transDate = null;
		if(FinanceTransactionType.COLLECTION.getType().compareTo(financeTransType)==0){
			Collection trans = collectionDao.getByReceiptNo(transNo);
			transDate = trans.getInsertTime();
		}
		if(FinanceTransactionType.PAYMENT.getType().compareTo(financeTransType)==0){
			Payment trans = paymentDao.getByPaymentNo(transNo);
			transDate = trans.getInsertTime();
		}
		if(FinanceTransactionType.INTERNAL_OFFSET.getType().compareTo(financeTransType)==0){
			Offset trans = offsetDao.getByOffsetNo(transNo);
			transDate = trans.getInsertTime();
		}
		return transDate;
	}
	
	@Override
	public List<SettlementTransaction> getDependentSettleTransaction(Integer financeTransType, String transNo) throws Exception {
		Assert.isNotNull(financeTransType);
		Assert.isNotNull(transNo);
		Long transId = getTransIdByNo(financeTransType, transNo);
		Date transDate = getTransDateByNo(financeTransType, transNo);
				
		List<SettlementTransaction> transList = new ArrayList<SettlementTransaction>();
		
		List<Collection> collectionList = collectionDao.getDependentTransaction(financeTransType, transId, transDate);
		if(!CollectionUtils.isEmpty(collectionList)){
			for(Collection collection : collectionList){
				transList.add(buildCollectionSettleTrans(collection));
			}
		}

		List<Payment> paymentList = paymentDao.getDependentTransaction(financeTransType, transId, transDate);
		if(!CollectionUtils.isEmpty(paymentList)){
			for(Payment payment : paymentList){
				transList.add(buildPaymentSettleTrans(payment));
			}
		}
	
		List<Offset> offsetList = offsetDao.getDependentTransaction(financeTransType, transId, transDate);
		if(!CollectionUtils.isEmpty(offsetList)){
			for(Offset offset : offsetList){
				transList.add(buildOffsetSettleTrans(offset));
			}
		}
	
		List<SettlementTransaction> transResult = distinctTrans(transList);
		sortTransactions(transResult, SortOrder.REVERSE.getValue());
		return transResult;
	}
	
	private List<SettlementTransaction> distinctTrans(List<SettlementTransaction> transList) throws Exception{
		List<SettlementTransaction> result = new ArrayList<SettlementTransaction>();
		List<String> transNoList = new ArrayList<String>();
		
		for(SettlementTransaction trans : transList){
			if(!transNoList.contains(trans.getTransNo())){
				transNoList.add(trans.getTransNo());
				result.add(trans);
			}
		}
		return result;
	}
	
	private SettlementTransaction buildCollectionSettleTrans(Collection collection) throws Exception{
		SettlementTransaction settleTrans = new SettlementTransaction();
		settleTrans.setTransId(collection.getCollectionId());
		BigDecimal bankCharge = collection.getBankCharge();
		if(bankCharge == null){
			bankCharge = BigDecimal.ZERO;
		}
		settleTrans.setAmount(collection.getAmount().subtract(bankCharge));
		settleTrans.setTransCurrency(collection.getCurrencyCode());
		settleTrans.setPayerPayee(collection.getPayerCode());
		settleTrans.setPayerPayeeName(partnerService.loadPartnerNameByPartnerCode(collection.getPayerCode()));
		/*if(collection.getPayMode()!=null){
			settleTrans.setPaymentMethod(PayModeType.getName(collection.getPayMode()));
		}*/
		settleTrans.setPaymentMethod(String.valueOf(collection.getPayMode()));
		settleTrans.setCpDate(collection.getCollectionDate());
		settleTrans.setTransDate(collection.getUpdateTime());
		settleTrans.setTransNo(collection.getReceiptNo());
		settleTrans.setTransType(FinanceTransactionType.COLLECTION.getType());
		//settleTrans.setTransTypeName(FinanceTransactionType.getName(FinanceTransactionType.COLLECTION.getType()));
		settleTrans.setStatus(collection.getStatus());
		settleTrans.setInsertTime(collection.getInsertTime());
		settleTrans.setUpdateTime(collection.getUpdateTime());
		return settleTrans;
	}
	
	private SettlementTransaction buildPaymentSettleTrans(Payment payment) throws Exception{
		SettlementTransaction settleTrans = new SettlementTransaction();
		settleTrans.setTransId(payment.getPaymentId());
		settleTrans.setTransCurrency(payment.getCurrencyCode());
		settleTrans.setAmount(payment.getAmount());
		settleTrans.setPayerPayee(null);
		settleTrans.setPaymentMethod(String.valueOf(payment.getPayMode()));
		settleTrans.setCpDate(payment.getPaymentDate());
		settleTrans.setTransDate(payment.getUpdateTime());
		settleTrans.setTransNo(payment.getPaymentNo());
		settleTrans.setTransType(FinanceTransactionType.PAYMENT.getType());
		//settleTrans.setTransTypeName(FinanceTransactionType.getName(FinanceTransactionType.PAYMENT.getType()));
		settleTrans.setPayerPayeeName(payeeDao.getPayeeNameByPaymentId(payment.getPaymentId()));
		settleTrans.setStatus(payment.getStatus());
		settleTrans.setInsertTime(payment.getInsertTime());
		settleTrans.setUpdateTime(payment.getUpdateTime());
		return settleTrans;
	}

	private SettlementTransaction buildOffsetSettleTrans(Offset offset) throws Exception{
		SettlementTransaction settleTrans = new SettlementTransaction();
		settleTrans.setTransId(offset.getOffsetId());
		settleTrans.setTransDate(offset.getUpdateTime());
		settleTrans.setTransNo(offset.getOffsetNo());
		settleTrans.setTransType(FinanceTransactionType.INTERNAL_OFFSET.getType());
		settleTrans.setAmount(offset.getAmount());
		//settleTrans.setTransTypeName(FinanceTransactionType.getName(FinanceTransactionType.INTERNAL_OFFSET.getType()));
		settleTrans.setStatus(offset.getStatus());
		settleTrans.setInsertTime(offset.getInsertTime());
		settleTrans.setUpdateTime(offset.getUpdateTime());
		return settleTrans;
	}
	
	private void sortTransactions(List<SettlementTransaction> transList, Integer sortOrder) throws Exception{
		if(!CollectionUtils.isEmpty(transList) && sortOrder != null){
			if(sortOrder == SortOrder.SORT.getValue()){
				Collections.sort(transList, new Comparator<SettlementTransaction>(){
					public int compare(SettlementTransaction trans1, SettlementTransaction trans2){
						return trans1.getUpdateTime().compareTo(trans2.getUpdateTime());
					}
				});
			}else if(sortOrder == SortOrder.REVERSE.getValue()){
				Collections.sort(transList, new Comparator<SettlementTransaction>(){
					public int compare(SettlementTransaction trans1, SettlementTransaction trans2){
						return trans2.getUpdateTime().compareTo(trans1.getUpdateTime());
					}
				});
			}
		}
	}
	
	@Override
	public String generateSettleNumber(Integer financeTransType) throws Exception{
		Assert.isNotNull(financeTransType);
		String number = null;
		
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMdd");
		String formattedDate = simpleDateFormat.format(date);
		
		Map<String, String> factors = new HashMap<String, String>();
		factors.put(NumberingFactor.TRANS_YEAR.getValue(), formattedDate.substring(0, 4));
		factors.put(NumberingFactor.TRANS_MONTH.getValue(), formattedDate.substring(4, 6));
		factors.put(NumberingFactor.TRANS_DAY.getValue(), formattedDate.substring(6, 8));
		AppUser user = AppContext.getAppUser();
		String userNameRef = "";
		if(user != null){
			userNameRef = user.getUserName().length()> 3?user.getUserName().substring(0, 3) : user.getUserName();
		}else{
			userNameRef = "adm";
		}
		factors.put(NumberingFactor.REFERENCE_NUMBER.getValue(), userNameRef);
		Long dailySeq = numberingDao.getDailySequence(NumberingType.ARAP_DAILY_SEQUENCE);
		factors.put(NumberingFactor.SEQUENCE.getValue(), StringUtils.leftPad(dailySeq.toString(),Constants.DAILY_SEQUENCE_LENGTH,"0"));
		
		switch(financeTransType){
		case 1 : number = numService.generateNumber(NumberingType.ARAP_COLLECTION_NUMBER, factors);break;
		case 2 : number = numService.generateNumber(NumberingType.ARAP_PAYMENT_NUMBER, factors);break;
		case 3 : number = numService.generateNumber(NumberingType.ARAP_OFFSET_NUMBER, factors);break;
		}
		return number;
	}
	
	@Override
	public void saveSettlementDetail(CreditsAndDebit creditDebit, Integer financeTransType) throws Exception{
		Assert.isNotNull(creditDebit);
		Assert.isNotNull(creditDebit.getEntryItems());
		Assert.isNotNull(creditDebit.getSettlmentTransType());
		Assert.isNotNull(creditDebit.getSettlementTransId());
		Assert.isNotNull(creditDebit.getSettlementTransDate());
		
		boolean needCalcMultipleCurrency = false;
		List<EntryItem> feeItemList = creditDebit.getEntryItems();
		Long financeTransId = creditDebit.getSettlementTransId();
		
		completeSettleAmount(creditDebit);
		
		for(EntryItem feeItem : feeItemList){
		    // ignore summarizing records
		    if (null == feeItem.getFeeId()) {
		        continue;
		    }
		    
			SettlementDetail settle = new SettlementDetail();
			
            switch (financeTransType) {
                case 1://Collection
                    settle.setCollectionId(financeTransId);
                    needCalcMultipleCurrency = true;
                    break;
                case 2://Payment
                    settle.setPaymentId(financeTransId);
        		    //feeEntryItemNegate(feeItem);//Amount of entry item for payment was negate value
                    needCalcMultipleCurrency = true;
                    break;
                case 3://Internal Offset
                    settle.setOffsetId(financeTransId);
                    break;
                case 4://Reverse
                    settle.setReverseId(financeTransId);
                    break;
            }
			settle.setFeeId(feeItem.getFeeId());
			settle.setSettleGroupId(creditDebit.getSettlementGroupId());
			settle.setCurrencyCode(feeItem.getCurrency());
			settle.setOutstandingAmount(feeItem.getOsBalance());
			settle.setMarkOffAmount(feeItem.getMarkOffAmount());
			settle.setAmountDiff(feeItem.getSettleDiff());
			settle.setSettleCurrencyCode(creditDebit.getSettleCurrencyCode());
			settle.setSettleAmount(feeItem.getSettleAmount());
			settle.setGainLoss(feeItem.getGainLoss());
			settle.setNeedPost(feeItem.getNeedPost());
			settle.setExchangeRate(feeItem.getExchangeRate());
			settle.setSettleDate(creditDebit.getSettleDate());
			settle.setFullySettle((feeItem.getFullySettle() != null && feeItem.getFullySettle())?YesNoType.YES.getType():YesNoType.NO.getType());
			settle.setPostStatus(PostStatus.WAIT_FOR_POST.getType());
			settlementDetailDao.save(settle);
			
			if(needCalcMultipleCurrency){
				saveSettlementExchangeDetail(SettlementExchangeSourceType.SETTLE_DTL_MARKOFF.getType(), settle.getSettleDetailId()
						, settle.getMarkOffAmount(),settle.getCurrencyCode(), creditDebit.getSettlementTransDate());
				
				saveSettlementExchangeDetail(SettlementExchangeSourceType.SETTLE_DTL_AMOUNT.getType(), settle.getSettleDetailId()
						, settle.getSettleAmount(),settle.getSettleCurrencyCode(),creditDebit.getSettlementTransDate());
				
				if(settle.getAmountDiff() != null && settle.getAmountDiff().compareTo(BigDecimal.ZERO) != 0){
					saveSettlementExchangeDetail(SettlementExchangeSourceType.SETTLE_DTL_SETTLE_DIFF.getType(), settle.getSettleDetailId()
							, settle.getAmountDiff(),settle.getSettleCurrencyCode(),creditDebit.getSettlementTransDate());
				}
			}
			feeService.adjustFee(feeItem.getFeeId(), feeItem.getMarkOffAmount(), feeItem.getFullySettle());
		}
	}
	
	@Override
	public void reverseSettlement(Integer financeTransType, String transNo, Long reverseId) throws Exception{
		List<SettlementDetail> detailList = getSettlementDetail(financeTransType, transNo);
		
		BigDecimal adjAmount = BigDecimal.ZERO;
		if(CollectionUtils.isNotEmpty(detailList)){
			for(SettlementDetail detail : detailList){
				//Invalid suspense generated by collection that to be reversed
				if(detail.getCollectionId() != null && detail.getSuspenseId() != null){
					suspenseDao.invalidSuspense(detail.getSuspenseId());
				}else if(detail.getCollectionId() == null && detail.getSuspenseId() != null){
					suspenseService.adjustBalance(detail.getSuspenseId(), detail.getMarkOffAmount().negate());
				}
				if(detail.getFeeId() != null){
					adjAmount = detail.getMarkOffAmount().add(detail.getAmountDiff()==null? BigDecimal.ZERO : detail.getAmountDiff());
					feeService.adjustFee(detail.getFeeId(), adjAmount.negate(), false);
				}
				
				SettlementDetail newDetail = detail.cloneEntity();
				newDetail.setReverseId(reverseId);
				resetSettlementDetailForReversal(newDetail);
				settlementDetailDao.save(newDetail);
				
				reverseSettlementExchangeDetail(detail.getSettleDetailId(), newDetail.getSettleDetailId());
			}
			updateReversedSettlement(financeTransType, transNo);
		}
	}
	
	private void reverseSettlementExchangeDetail(Long originalSourceId, Long newSourceId) throws Exception{
		Integer[] allSourceType = SettlementExchangeSourceType.getAll();
		for(int i = 0; i < allSourceType.length; i ++ ){
			List<SetllementExchangeDetail> exDetailList = setllementExchangeDetailDao.getBySourceTypeAndSourceId(allSourceType[i], originalSourceId);
			if(CollectionUtils.isNotEmpty(exDetailList)){
				for(SetllementExchangeDetail exDetail : exDetailList){
					SetllementExchangeDetail newExDetail = exDetail.cloneEntity();
					newExDetail.setExchangeDetailId(null);
					newExDetail.setSourceId(newSourceId);
					newExDetail.setInsertTime(null);
					if(newExDetail.getAmount() != null){
						newExDetail.setAmount(newExDetail.getAmount().negate());
					}
					if(newExDetail.getConvertedAmount() != null){
						newExDetail.setConvertedAmount(newExDetail.getConvertedAmount().negate());
					}
					setllementExchangeDetailDao.save(newExDetail);
				}
			}
		}
	}
	
	private void resetSettlementDetailForReversal(SettlementDetail detail) throws Exception{
		detail.setSettleDetailId(null);
		detail.setPostStatus(PostStatus.WAIT_FOR_POST.getType());
		if(detail.getMarkOffAmount() != null){
			detail.setMarkOffAmount(detail.getMarkOffAmount().negate());
		}
		if(detail.getAmountDiff() != null){
			detail.setAmountDiff(detail.getAmountDiff().negate());
		}
		if(detail.getSettleAmount()!= null){
			detail.setSettleAmount(detail.getSettleAmount().negate());
		}
		if(detail.getGainLoss() != null){
			detail.setGainLoss(detail.getGainLoss().negate());
		}
		if(detail.getGainLossDiff() != null){
			detail.setGainLossDiff(detail.getGainLossDiff().negate());
		}
	}

	private List<SettlementDetail> getSettlementDetail(Integer financeTransType, String transNo) throws Exception{
		List<SettlementDetail> detailList = null;
		
		Long transId = this.getTransIdByNo(financeTransType, transNo);
		if(FinanceTransactionType.COLLECTION.getType().equals(financeTransType)){
			detailList = settlementDetailDao.getByCollectionId(transId);
		}
		if(FinanceTransactionType.PAYMENT.getType().equals(financeTransType)){
			detailList = settlementDetailDao.getByPaymentId(transId);
		}
		if(FinanceTransactionType.INTERNAL_OFFSET.getType().equals(financeTransType)){
			detailList = settlementDetailDao.getByOffsetId(transId);
		}
		return detailList;
	}
	
	private void updateReversedSettlement(Integer financeTransType, String transNo) throws Exception{
		if(FinanceTransactionType.COLLECTION.getType().compareTo(financeTransType)==0){
			Collection trans = collectionDao.getByReceiptNo(transNo);
			trans.setStatus(SettlementStatus.REVERSED.getType());
		}
		if(FinanceTransactionType.PAYMENT.getType().compareTo(financeTransType)==0){
			Payment trans = paymentDao.getByPaymentNo(transNo);
			trans.setStatus(SettlementStatus.REVERSED.getType());
		}
		if(FinanceTransactionType.INTERNAL_OFFSET.getType().compareTo(financeTransType)==0){
			Offset trans = offsetDao.getByOffsetNo(transNo);
			trans.setStatus(SettlementStatus.REVERSED.getType());
		}
	}
	
	/**
	 * Fill difference amount on total and detail settle amounts to the last detail entry.
	 * @param creditDebit
	 * @throws Exception
	 */
	private void completeSettleAmount(CreditsAndDebit creditDebit) throws Exception{
		BigDecimal totalSettleAmount = creditDebit.getAmount();
		if(totalSettleAmount == null || totalSettleAmount.compareTo(BigDecimal.ZERO) == 0){
			return;
		}
		BigDecimal accmulatedSettleAmount = BigDecimal.ZERO;
		BigDecimal diffSettleAmount = BigDecimal.ZERO;
		EntryItem lastItem = null;
		List<EntryItem> entryList = creditDebit.getEntryItems();
		if(entryList != null){
			for(EntryItem item : entryList){
				if(item.getFeeId() != null){
					lastItem = item;
					accmulatedSettleAmount = accmulatedSettleAmount.add(item.getSettleAmount());
				}
			}
			diffSettleAmount = totalSettleAmount.subtract(accmulatedSettleAmount);
			lastItem.setSettleAmount(lastItem.getSettleAmount().add(diffSettleAmount));
			BigDecimal newGainLoss = feeService.calGainLossInUSD(lastItem.getFeeId(), lastItem.getMarkOffAmount(), lastItem.getCurrency(),
																lastItem.getSettleAmount(),creditDebit.getSettleCurrencyCode() , creditDebit.getSettleDate());
			lastItem.setGainLoss(newGainLoss);
		}
	}
	
	/**
	 * Amount enter on payment page has been reverse
	 * @param inverse
	 * @param item
	 * @throws Exception
	 */
	@Override
	public void feeEntryItemNegate(EntryItem item) throws Exception{
		if(item.getOsBalance() != null){
			item.setOsBalance(item.getOsBalance().negate());
		}
		if(item.getMarkOffAmount() != null){
			item.setMarkOffAmount(item.getMarkOffAmount().negate());
		}
		if(item.getAmountOC() != null){
			item.setAmountOC(item.getAmountOC().negate());
		}
		if(item.getSettleDiff() != null){
			item.setSettleDiff(item.getSettleDiff().negate());
		}
		if(item.getSettleAmount() != null){
			item.setSettleAmount(item.getSettleAmount().negate());
		}
		if(item.getGainLoss() != null){
			item.setGainLoss(item.getGainLoss().negate());
		}
	}
	
	@Override
	public void saveSettlementExchangeDetail(Integer sourceType, Long sourceId, BigDecimal amount, String currencyCode, Date transDate) throws Exception{
		String[] convertCurrencyCodeArray = CurrencyConstants.GL_CURRENCY_CODE_ARRAY;
		BigDecimal exchangeRate;
		BigDecimal convertedAmount;
		for(int i = 0; i < convertCurrencyCodeArray.length; i ++){
			String convertCurrencyCode = convertCurrencyCodeArray[i];
			SetllementExchangeDetail settleExDtl = new SetllementExchangeDetail();
			settleExDtl.setSourceType(sourceType);
			settleExDtl.setSourceId(sourceId);
			settleExDtl.setAmount(amount);
			settleExDtl.setCurrencyCode(currencyCode);
			settleExDtl.setConvertCurrencyCode(convertCurrencyCode);
			settleExDtl.setConvertedAmount(amount);
			settleExDtl.setExchangeRate(BigDecimal.ONE);
			
			if(!convertCurrencyCode.equals(currencyCode)){
				exchangeRate = currencyExchangeService.getExchangeRate(currencyCode, convertCurrencyCode, transDate);
				convertedAmount = feeService.convertAmount(amount, currencyCode, convertCurrencyCode, transDate);
				settleExDtl.setConvertCurrencyCode(convertCurrencyCode);
				settleExDtl.setConvertedAmount(convertedAmount);
				settleExDtl.setExchangeRate(exchangeRate);
			}
			setllementExchangeDetailDao.save(settleExDtl);
		}
	}
	
	@Override
	public List<SettlementHistory> getSettlementHistory(Integer bizTransType, Long bizTransId) throws Exception{
		List<SettlementHistory> settleHistoryList = new ArrayList<SettlementHistory>();
		List<SettlementDetail> settleDetailList = settlementDetailDao.getSettlementHistory(bizTransType, bizTransId);
		if(CollectionUtils.isNotEmpty(settleDetailList)){
			for(SettlementDetail settleDetail : settleDetailList){
				SettlementHistory settleHistory = new SettlementHistory();
				settleHistoryList.add(settleHistory);
				BeanUtils.copyPropertiesIngoreNull(settleHistory, settleDetail);
				if(settleDetail.getCollectionId() != null){
					settleHistory.setSettleTransType(FinanceTransactionType.COLLECTION.getType());
				}
				if(settleDetail.getPaymentId() != null){
					settleHistory.setSettleTransType(FinanceTransactionType.PAYMENT.getType());
				}
				if(settleDetail.getOffsetId() != null){
					settleHistory.setSettleTransType(FinanceTransactionType.INTERNAL_OFFSET.getType());
				}
			}
		}
		return settleHistoryList;
	}
	
	@Override
    public Long getSettlementOperator(Integer financeTransType, Long transId) throws Exception{
		Long operator = null;
		if(FinanceTransactionType.COLLECTION.getType().compareTo(financeTransType)==0){
			Collection trans = collectionDao.load(transId);
			operator = trans.getUpdateBy();
		}
		if(FinanceTransactionType.PAYMENT.getType().compareTo(financeTransType)==0){
			Payment trans = paymentDao.load(transId);
			operator = trans.getUpdateBy();
		}
		if(FinanceTransactionType.INTERNAL_OFFSET.getType().compareTo(financeTransType)==0){
			Offset trans = offsetDao.load(transId);
			operator = trans.getUpdateBy();
		}
		if(FinanceTransactionType.REVERSAL.getType().compareTo(financeTransType)==0){
			Reverse trans = reverseDao.load(transId);
			operator = trans.getUpdateBy();
		}
		return operator;
    }
    
}
