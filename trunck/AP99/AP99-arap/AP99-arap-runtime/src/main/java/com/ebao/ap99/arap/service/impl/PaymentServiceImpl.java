package com.ebao.ap99.arap.service.impl;

import java.util.ArrayList;
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
import com.ebao.ap99.arap.dao.PayeeDao;
import com.ebao.ap99.arap.dao.PaymentDao;
import com.ebao.ap99.arap.dao.ReverseDao;
import com.ebao.ap99.arap.dao.SettlementGroupDao;
import com.ebao.ap99.arap.entity.Payee;
import com.ebao.ap99.arap.entity.Payment;
import com.ebao.ap99.arap.entity.Reverse;
import com.ebao.ap99.arap.entity.SettlementGroup;
import com.ebao.ap99.arap.service.FeeService;
import com.ebao.ap99.arap.service.PaymentService;
import com.ebao.ap99.arap.service.ReversalService;
import com.ebao.ap99.arap.service.SettlementService;
import com.ebao.ap99.arap.service.SuspenseService;
import com.ebao.ap99.arap.vo.Balance;
import com.ebao.ap99.arap.vo.CreditsAndDebit;
import com.ebao.ap99.arap.vo.EntryItem;
import com.ebao.ap99.arap.vo.PaymentModel;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentDao paymentDao;
	
	@Autowired
	PayeeDao payeeDao;
	
	@Autowired
	ReverseDao reverseDao;
	
	@Autowired
	SettlementGroupDao settlementGroupDao;
	
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
	
	@Override
	@Transactional
	/**
	 * Submit payment transaction
	 */
	public String payment(PaymentModel model) throws Exception {
		Assert.isNotNull(model);
		
		checkPaymentModel(model);
		
		negatePaymentView(model);
		
		Long paymentId = savePayment(model);
		model.setPaymentId(paymentId);
		
		savePaymentDetail(model);
		
		String paymentNo = settlementService.generateSettleNumber(FinanceTransactionType.PAYMENT.getType());
		Payment payment = paymentDao.load(paymentId);
		payment.setPaymentNo(paymentNo);
		paymentDao.save(payment);
		
		return paymentNo;
	}
	
	/**
	 * Check if payment model is valid
	 * @param model
	 * @throws Exception
	 */
	private void checkPaymentModel(PaymentModel model) throws Exception{
		
		List<CreditsAndDebit> validFeeList = feeService.filterInvalidFee(model.getFeeList());
		model.setFeeList(validFeeList);// replace with the currency groups which including valid selected data
		
		filterSuspense(model);
	}
	
	/**
	 * remove unused suspenses from payment model
	 * @param model
	 * @throws Exception
	 */
	private void filterSuspense(PaymentModel model) throws Exception{
		List<Balance> validSuspenseList = new ArrayList<Balance>();
		List<Balance> suspenseList = model.getSuspenseList();
		if(suspenseList != null && suspenseList.size() > 0){
			for(Balance suspense : suspenseList){
				if(suspense.getSettleAmount() != null 
						|| suspense.getMarkOffAmount() != null
						|| suspense.getMarkOffAmountInSettleCurrency() != null){
					validSuspenseList.add(suspense);
				}
			}
		}
		model.setSuspenseList(validSuspenseList);
	}
	
	/**
	 * Negate sign of amount for payment view
	 * @param model
	 * @throws Exception
	 */
	private void negatePaymentView(PaymentModel model) throws Exception{
		List<CreditsAndDebit> feeGroupList = model.getFeeList();
		if(CollectionUtils.isNotEmpty(feeGroupList)){
			for(CreditsAndDebit feeGroup : feeGroupList){
				feeGroup.setAmount(feeGroup.getAmount().negate());
				feeGroup.setConvertedAmount(feeGroup.getConvertedAmount().negate());
				List<EntryItem> feeEntryList = feeGroup.getEntryItems();
				if(CollectionUtils.isNotEmpty(feeEntryList)){
					for(EntryItem entry : feeEntryList){
						settlementService.feeEntryItemNegate(entry);
					}
				}
			}
		}
	}
	
	/**
	 * Save payment summary information only
	 * @param model
	 * @return
	 * @throws Exception
	 */
	private Long savePayment(PaymentModel model) throws Exception{
		Payment payment = new Payment();
		payment.setAmount(model.getTotalAmount());
		payment.setBankCharge(model.getBankCharge());
		payment.setCurrencyCode(model.getPaymentCurrency());
		payment.setFromBankAccountNo(model.getPaidAccount());
		payment.setPaymentDate(model.getPaymentDate());
		payment.setRemark(model.getRemark());
		payment.setTransDate(AppContext.getSysDate());
		payment.setPayMode(model.getPaymentMethod());//PayModeType.BANK_TRANSFER.getType()
		payment.setStatus(SettlementStatus.NORMAL.getType());
		payment.setExchangeRateRef(model.getExchangeRateReference());
		payment.setValueDate(model.getValueDate());
		payment.setChequeDate(model.getChequeDate());
		payment.setChequeHolderName(model.getChequeHolderName());
		payment.setChequeNo(model.getChequeNumber());
		paymentDao.save(payment);
		return payment.getPaymentId();
	}
	
	/**
	 * save payment related data: mark off detail of fee and suspense information
	 * @param model
	 * @throws Exception
	 */
	private void savePaymentDetail(PaymentModel model) throws Exception{
		Assert.isNotNull(model.getFeeList());
		List<CreditsAndDebit> feeGroupList = model.getFeeList();
		for(CreditsAndDebit cd : feeGroupList){
			SettlementGroup settleGroup = new SettlementGroup(); 
			settleGroup.setAmount(cd.getAmount());
			settleGroup.setCurrencyCode(model.getPaymentCurrency());
			settleGroup.setConvertedAmount(cd.getConvertedAmount());
			settleGroup.setConvertCurrencyCode(cd.getCurrencyCode());
			settleGroup.setExchangeRate(cd.getExchangeRate());
			
			settlementGroupDao.save(settleGroup);
			
			cd.setSettlmentTransType(FinanceTransactionType.PAYMENT.getType());
			cd.setSettlementTransDate(model.getPaymentDate());
			cd.setSettlementGroupId(settleGroup.getSettleGroupId());
			cd.setSettlementTransId(model.getPaymentId());
			cd.setSettleCurrencyCode(model.getPaymentCurrency());
			cd.setSettleDate(model.getPaymentDate());
			
			settlementService.saveSettlementDetail(cd, FinanceTransactionType.PAYMENT.getType());
		}
		suspenseService.savePaymentSuspense(model);
		savePayee(model.getPayeeList(), model.getPaymentId());
	}
	
	private void savePayee(List<Payee> payeeList, Long paymentId) throws Exception{
		if(payeeList != null){
			for(Payee payee : payeeList){
				payee.setPaymentId(paymentId);
				payeeDao.insertOrUpdate(payee);
			}
		}
	}

	@Override
	public PaymentModel queryPaymentDetail(Long paymentId) throws Exception {
		Assert.isNotNull(paymentId);
		PaymentModel model  = new PaymentModel();
		
		Payment payment = paymentDao.load(paymentId);
		Assert.isNotNull(payment);
		
		List<CreditsAndDebit> cdList = feeService.getSettlementFee(FinanceTransactionType.PAYMENT.getType(), paymentId);
		List<Balance> balanceList = suspenseService.getSettlementSuspense(FinanceTransactionType.PAYMENT.getType(), paymentId);
		List<Payee> payeeList = payeeDao.getByPaymentId(paymentId);
		
		BeanUtils.copyPropertiesIngoreNull(model, payment);
		
		List<Reverse> reverseList = reverseDao.getByTransNo(payment.getPaymentNo());
		if(CollectionUtils.isNotEmpty(reverseList)){
			Reverse reverse = reverseList.get(0);
			if(model.getRemark()==null){
				model.setRemark("");
			}
			model.setRemark(model.getRemark()+reversalService.getReversalViewInfo(reverse));
		}		
		
		model.setTotalAmount(payment.getAmount());
		model.setPaymentCurrency(payment.getCurrencyCode());
		// meiliang.zou modify 2016.5.5 start
	    String accountName = "";
		if (StringUtils.isNotBlank(payment.getFromBankAccountNo())){
			accountName = bankAccountDao.queryAccountNameByAccountNo(payment.getFromBankAccountNo());
		}
		model.setPaidAccount(accountName);
		// meiliang.zou modify 2016.5.5 start
		model.setExchangeRateReference(payment.getExchangeRateRef());
		model.setPaymentMethod(payment.getPayMode());
		model.setChequeNumber(payment.getChequeNo());
		model.setTransNumber(payment.getPaymentNo());
		model.setTransStatus(payment.getStatus());
		model.setOperationDate(DateUtils.truncate(payment.getUpdateTime(), Calendar.DATE));
		model.setOperatorId(payment.getInsertBy().intValue());
		model.setFeeList(cdList);
		model.setSuspenseList(balanceList);
		model.setPayeeList(payeeList);
		model.setValueDate(payment.getValueDate());
		return model;
	}
}
