/**
 * 
 */
package com.ebao.ap99.arap.convertor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ebao.ap99.arap.entity.Payee;
import com.ebao.ap99.arap.vo.Balance;
import com.ebao.ap99.arap.vo.CollectionDTO;
import com.ebao.ap99.arap.vo.CollectionModel;
import com.ebao.ap99.arap.vo.CollectionSearchDTO;
import com.ebao.ap99.arap.vo.CollectionViewResult;
import com.ebao.ap99.arap.vo.FeeQueryCondition;
import com.ebao.ap99.arap.vo.FeeSearchDTO;
import com.ebao.ap99.arap.vo.OffsetModel;
import com.ebao.ap99.arap.vo.OffsetViewResult;
import com.ebao.ap99.arap.vo.PayeeDTO;
import com.ebao.ap99.arap.vo.PaymentDTO;
import com.ebao.ap99.arap.vo.PaymentModel;
import com.ebao.ap99.arap.vo.PaymentSearchDTO;
import com.ebao.ap99.arap.vo.PaymentViewResult;
import com.ebao.ap99.arap.vo.SuspenseQueryCondition;
import com.ebao.ap99.parent.BeanUtils;

/**
 * @author meiliang.zou
 *
 */
public class FeeQueryConditionConvertor {
	
	public FeeQueryCondition convertFeeQueryConditionByDTO(FeeSearchDTO feeSearchDTO) {
		FeeQueryCondition condition = new FeeQueryCondition();

		condition.setBrokerCode(feeSearchDTO.getBrokerCode());
		condition.setPartnerCode(feeSearchDTO.getPartnerCode());
		condition.setClaimNo(feeSearchDTO.getClaimNo());
		condition.setContractIdArray(feeSearchDTO.getContractId());
		condition.setStatementId(feeSearchDTO.getStatementId());
		condition.setSettleCurrency(feeSearchDTO.getSettleCurrencyCode());

		return condition;
	}
	
	public FeeQueryCondition convertFeeQueryConditionByCollectionSearchDTO(CollectionSearchDTO collectionSearchDTO) {
		FeeQueryCondition condition = new FeeQueryCondition();

		condition.setBrokerCode(collectionSearchDTO.getBroker());
		condition.setPartnerCode(collectionSearchDTO.getPartnerCode());
		condition.setClaimNo(collectionSearchDTO.getClaimNumber());
		condition.setContractIdArray(collectionSearchDTO.getContractIds());
		condition.setStatementId(collectionSearchDTO.getStatementId());
		condition.setSettleCurrency(collectionSearchDTO.getCollectionCurrency());

		return condition;
	}
	
	public FeeQueryCondition convertFeeQueryConditionByPaymentSearchDTO(PaymentSearchDTO paymentSearchDTO) {
		FeeQueryCondition condition = new FeeQueryCondition();

		condition.setBrokerCode(paymentSearchDTO.getBroker());
		condition.setPartnerCode(paymentSearchDTO.getPartnerCode());
		condition.setClaimNo(paymentSearchDTO.getClaimNo());
		condition.setContractIdArray(paymentSearchDTO.getContractID());
		condition.setStatementId(paymentSearchDTO.getStatementId());
		
		condition.setSettleCurrency(paymentSearchDTO.getPaymentCurrency());

		return condition;
	}

	public SuspenseQueryCondition convertSuspenseQueryConditionByPaymentSearchDTO(PaymentSearchDTO paymentSearchDTO) {
		SuspenseQueryCondition condition = new SuspenseQueryCondition();

		condition.setBrokerCode(paymentSearchDTO.getBroker());
		condition.setPartnerCode(paymentSearchDTO.getPartnerCode());
		condition.setClaimNo(paymentSearchDTO.getClaimNo());
		condition.setContractIdArray(paymentSearchDTO.getContractID());
		condition.setStatementId(paymentSearchDTO.getStatementId());
		
		condition.setSettleCurrencyCode(paymentSearchDTO.getPaymentCurrency());
		
		return condition;
	}
	
	public CollectionModel convertCollectionDTOToCollectionModel(CollectionDTO collectionDTO) {
		CollectionModel collectionModel = new CollectionModel();
		
		collectionModel.setPayerCode(collectionDTO.getPayer());
		collectionModel.setCollectionDate(collectionDTO.getCollectionDate());
		// meiliang.zou add 2016.3.24
		collectionModel.setExchangeRateReference(collectionDTO.getExchangeRateReference()==null?1:collectionDTO.getExchangeRateReference());
		collectionModel.setPaymentMethod(StringUtils.isBlank(collectionDTO.getPaymentMethod())?null:Integer.valueOf(collectionDTO.getPaymentMethod()));
		collectionModel.setBankCode(collectionDTO.getBank());
		
		collectionModel.setBankCharge(StringUtils.isBlank(collectionDTO.getBankCharge())?null:new BigDecimal(collectionDTO.getBankCharge()));
		collectionModel.setChequeNo(collectionDTO.getChequeNumber());
		collectionModel.setChequeDate(collectionDTO.getChequeDate());
		collectionModel.setChequeHolderName(collectionDTO.getChequeHolderName());
		
		collectionModel.setCollectionCurrency(collectionDTO.getCollectionCurrency());
		collectionModel.setCollectionBankAccountNo(collectionDTO.getCollectToBankAccount());
		collectionModel.setCollectionAmount(StringUtils.isBlank(collectionDTO.getCollectionAmount())?null:new BigDecimal(collectionDTO.getCollectionAmount()));
		collectionModel.setCollectToAccount(collectionDTO.getCollectToBankAccount());
				
		collectionModel.setFeeList(collectionDTO.getCreditsAndDebit());
		
		collectionModel.setBalanceList(collectionDTO.getBalances());
		
		collectionModel.setRemark(collectionDTO.getRemark());
		
		// add net amount
		// meiliang.zou add 2016.5.25 start
		collectionModel.setValueDate(collectionDTO.getValueDate());
		// meiliang.zou add 2016.5.25 end
		
		return collectionModel;
	}
	
	public CollectionViewResult covertCollectionViewResultByCollectionModel(CollectionModel collectionModel) {
		CollectionViewResult collectionViewResult = new CollectionViewResult();
		BeanUtils.copyPropertiesIngoreNull(collectionViewResult, collectionModel);
		collectionViewResult.setPayer(collectionModel.getPayerCode());
		collectionViewResult.setPayerName(collectionModel.getPayerName());
		collectionViewResult.setCollectionDate(collectionModel.getCollectionDate());
		collectionViewResult.setValueDate(collectionModel.getValueDate());
		collectionViewResult.setExchangeRateReference(collectionModel.getExchangeRateReference());
		collectionViewResult.setCollectToBankAccount(collectionModel.getCollectionBankAccountNo());
		collectionViewResult.setPaymentMethod(collectionModel.getPaymentMethod()==null?"":String.valueOf(collectionModel.getPaymentMethod()));
		collectionViewResult.setBank(collectionModel.getBankCode());
		collectionViewResult.setBankCharge(collectionModel.getBankCharge());
		collectionViewResult.setChequeNumber(collectionModel.getChequeNo());
		collectionViewResult.setChequeDate(collectionModel.getChequeDate());
		collectionViewResult.setChequeHolderName(collectionModel.getChequeHolderName());
		collectionViewResult.setCollectionCurrency(collectionModel.getCollectionCurrency());
		collectionViewResult.setCollectionAmount(collectionModel.getCollectionAmount());
		collectionViewResult.setNetAmount(collectionModel.getCollectionAmount().subtract(collectionModel.getBankCharge()==null?BigDecimal.ZERO:collectionModel.getBankCharge()));
		
		collectionViewResult.setCreditsAndDebit(collectionModel.getFeeList());
		collectionViewResult.setBalances(collectionModel.getBalanceList());
		collectionViewResult.setRemark(collectionModel.getRemark());
		
		convertContractCodeForView(collectionViewResult);
		
		return collectionViewResult;
	}
	
	private void convertContractCodeForView(CollectionViewResult result){
		List<Balance> balanceList = result.getBalances();
		if(CollectionUtils.isNotEmpty(balanceList)){
			for(Balance balance : balanceList){
				balance.setContractId(balance.getContractCode());
			}
		}
	}
	
	public PaymentModel convertPaymentDTOToPaymentModel(PaymentDTO paymentDTO) {
		PaymentModel paymentModel = new PaymentModel();
		
		paymentModel.setPaymentCurrency(paymentDTO.getPaymentCurrency());
		paymentModel.setPaidAccount(paymentDTO.getPaidAccount());
		paymentModel.setTotalAmount(StringUtils.isBlank(paymentDTO.getTotalAmount())?new BigDecimal(0):new BigDecimal(paymentDTO.getTotalAmount()));
		paymentModel.setBankCharge(StringUtils.isBlank(paymentDTO.getBankCharge())?new BigDecimal(0):new BigDecimal(paymentDTO.getBankCharge()));
		paymentModel.setPaymentDate(paymentDTO.getPaymentDate());
		
		// meiliang.zou 2016.3.24 add
		paymentModel.setExchangeRateReference(paymentDTO.getExchangeRateReference());
		paymentModel.setPaymentMethod(Integer.valueOf(paymentDTO.getPaymentMethod()));
		paymentModel.setValueDate(paymentDTO.getValueDate());
		paymentModel.setChequeDate(paymentDTO.getChequeDate());
		paymentModel.setChequeHolderName(paymentDTO.getChequeHolderName());
		paymentModel.setChequeNumber(paymentDTO.getChequeNumber());
		
		List<PayeeDTO> payeeDTOList = paymentDTO.getPayee();
		
		List<Payee> payeeEntityList = new ArrayList<Payee>();
		if (null != payeeDTOList && payeeDTOList.size() > 0) {
			Payee payeeEntity = null;
			for (PayeeDTO payeeDTO : payeeDTOList) {
				payeeEntity = new Payee();
				
				payeeEntity.setPartnerCode(payeeDTO.getPayee());
				payeeEntity.setCurrencyCode(payeeDTO.getCurrencyCode());
				payeeEntity.setAmount(StringUtils.isBlank(payeeDTO.getPaymentAmount())?new BigDecimal(0):new BigDecimal(payeeDTO.getPaymentAmount()));
				
				payeeEntityList.add(payeeEntity);
			}
			
			paymentModel.setPayeeList(payeeEntityList);
		}
		else {
			paymentModel.setPayeeList(payeeEntityList);
		}
		
		paymentModel.setFeeList(paymentDTO.getCreditsAndDebit());
		paymentModel.setSuspenseList(paymentDTO.getBalances());
		
		paymentModel.setRemark(paymentDTO.getRemark());
		
		return paymentModel;
	}
	
	public PaymentViewResult convertPaymentModelToPaymentViewResult(PaymentModel paymentModel) {
		PaymentViewResult paymentViewResult = new PaymentViewResult();
		BeanUtils.copyPropertiesIngoreNull(paymentViewResult, paymentModel);
		paymentViewResult.setPaymentId(paymentModel.getPaymentId());
		paymentViewResult.setPaymentCurrency(paymentModel.getPaymentCurrency());
		paymentViewResult.setTotalAmount(paymentModel.getTotalAmount());
		paymentViewResult.setBankCharge(paymentModel.getBankCharge());
		paymentViewResult.setPaidAccount(paymentModel.getPaidAccount());
		paymentViewResult.setPaymentDate(paymentModel.getPaymentDate());
		paymentViewResult.setRemark(paymentModel.getRemark());
		paymentViewResult.setPaymentMethod(paymentModel.getPaymentMethod());
		// meiliang.zou add 2016.3.24
		paymentViewResult.setExchangeRateReference(paymentModel.getExchangeRateReference());
		paymentViewResult.setValueDate(paymentModel.getValueDate());
		paymentViewResult.setChequeDate(paymentModel.getChequeDate());
		paymentViewResult.setChequeHolderName(paymentModel.getChequeHolderName());
		paymentViewResult.setChequeNumber(paymentModel.getChequeNumber());
		
		paymentViewResult.setCreditsAndDebit(paymentModel.getFeeList());
		paymentViewResult.setBalances(paymentModel.getSuspenseList());
		
		List<Payee> payees = paymentModel.getPayeeList();
		List<PayeeDTO> payeeDTOs = new ArrayList<PayeeDTO>();
		PayeeDTO payeeDTO = null;
		for (Payee payee : payees){
			payeeDTO = new PayeeDTO();
			payeeDTO.setPayee(payee.getPartnerCode());
			payeeDTO.setCurrencyCode(payee.getCurrencyCode());
			payeeDTO.setPaymentAmount(payee.getAmount()==null?"":String.valueOf(payee.getAmount()));
			payeeDTOs.add(payeeDTO);
		}
		
		paymentViewResult.setPayee(payeeDTOs);
		
		return paymentViewResult;
	}
	
	public OffsetViewResult convertOffsetModelToOffsetViewResult(OffsetModel offsetModel) {
		OffsetViewResult offsetViewResult = new OffsetViewResult();
		BeanUtils.copyPropertiesIngoreNull(offsetViewResult, offsetModel);
		offsetViewResult.setCreditsAndDebit(offsetModel.getFeeList());
		offsetViewResult.setBalances(offsetModel.getSuspenseList());
		offsetViewResult.setRemark(offsetModel.getRemark());
		offsetViewResult.setRegistrationDate(offsetModel.getRegistrationDate());
		
		return offsetViewResult;
	}
}