/*
 * @author ammon.zhou
 * 
 **/
package com.ebao.ap99.arap.convertor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.ebao.ap99.arap.vo.TransactionQueryCondition;
import com.ebao.ap99.arap.vo.TransactionReversalDTO;

public class TransactionReversalConvertor {
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	public TransactionQueryCondition modelToQueryConditionEntity(TransactionReversalDTO transactionReversalDTO){
		TransactionQueryCondition condition=new TransactionQueryCondition();
		
		if (null != transactionReversalDTO){
			condition.setTransType(transactionReversalDTO.getTransType()==null?null:Integer.valueOf(transactionReversalDTO.getTransType()));
			condition.setPartnerCode(transactionReversalDTO.getPartnerCode());
			condition.setTransNo(transactionReversalDTO.getSettleNo());
			condition.setSettleDateFrom(transactionReversalDTO.getSettleDateFrom()==null?null:df.format(transactionReversalDTO.getSettleDateFrom()));
			condition.setSettleDateTo(transactionReversalDTO.getSettleDateTo()==null?null:df.format(transactionReversalDTO.getSettleDateTo()));
			condition.setTransDateFrom(transactionReversalDTO.getTransDateFrom()==null?null:df.format(transactionReversalDTO.getTransDateFrom()));
			condition.setTransDateTo(transactionReversalDTO.getTransDateTo()==null?null:df.format(transactionReversalDTO.getTransDateTo()));
			condition.setBrokerCode(transactionReversalDTO.getBroker());
			condition.setFinancePartnerCode(transactionReversalDTO.getPayerPayee());
			condition.setContractIdArray(transactionReversalDTO.getContractId());
			condition.setStatementId(transactionReversalDTO.getStatementId());
			condition.setClaimNo(transactionReversalDTO.getClaimNo());
		}
		return condition;
		
	}
}
