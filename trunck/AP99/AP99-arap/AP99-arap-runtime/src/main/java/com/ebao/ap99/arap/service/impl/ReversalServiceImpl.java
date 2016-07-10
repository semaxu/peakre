package com.ebao.ap99.arap.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.constant.ReverseStatus;
import com.ebao.ap99.arap.dao.ReverseDao;
import com.ebao.ap99.arap.dao.ReverseReasonDao;
import com.ebao.ap99.arap.entity.Reverse;
import com.ebao.ap99.arap.entity.ReverseReason;
import com.ebao.ap99.arap.service.ReversalService;
import com.ebao.ap99.arap.service.SettlementService;
import com.ebao.ap99.arap.vo.OperationResult;
import com.ebao.ap99.arap.vo.ReversalModel;
import com.ebao.ap99.arap.vo.SettlementTransaction;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.security.entity.User;
import com.ebao.unicorn.platform.urpmgmt.service.UserService;

public class ReversalServiceImpl implements ReversalService {

	@Autowired
	SettlementService settlementService;
	
	@Autowired
	ReverseDao reverseDao;
	
	@Autowired
	ReverseReasonDao reverseReasonDao;
	
	@Autowired
	UserService userService;
	
	@Override
	public OperationResult reverse(ReversalModel model) throws Exception{
		OperationResult result = new OperationResult();
		
		checkRerversalModel(model, result);
		if(!result.getStatus().equals(ReverseStatus.SUCCESSFUL.getValue())){
			return result;
		}
		
		List<SettlementTransaction> settleTransList = model.getTransactionList();
		checkReverseDependency(settleTransList, result);
		if(!result.getStatus().equals(ReverseStatus.SUCCESSFUL.getValue())){
			return result;
		}
		
		for(SettlementTransaction trans : settleTransList){
			Reverse reverse = saveReverse(model, trans.getTransType(), trans.getTransNo());
			settlementService.reverseSettlement(trans.getTransType(), trans.getTransNo(), reverse.getReverseId());
		}
		result.setStatus(ReverseStatus.SUCCESSFUL.getValue());
		result.setMessage("Reverse successfully");
		return result;
	}
	
	private Reverse saveReverse(ReversalModel model, Integer finTransType, String finTransNo) throws Exception{
		Reverse reverse = new Reverse();
		BeanUtils.copyPropertiesIngoreNull(reverse, model);
		reverse.setTransType(finTransType);
		reverse.setTransNo(finTransNo);
		reverse.setStatus(ReverseStatus.SUCCESSFUL.getValue());
		reverse.setReverseDate(AppContext.getSysDate());
		reverse.setReverseReason(model.getReversalReason());
		if(StringUtils.isNoneBlank(model.getRequestedBy())){
			User user = userService.load(model.getRequestedBy());
			if(user != null){
				reverse.setRequestBy(user.getId());
			}
		}
		reverseDao.insert(reverse);
		return reverse;
	}
	
	private void checkRerversalModel(ReversalModel model, OperationResult result) throws Exception{
		List<SettlementTransaction> settleTrans = model.getTransactionList();
		List<SettlementTransaction> validTransList = null;
		if(CollectionUtils.isNotEmpty(settleTrans)){
			validTransList = new ArrayList<SettlementTransaction>();
			for(SettlementTransaction trans : settleTrans){
				if(trans.isSelected()){
					validTransList.add(trans);
				}
			}
		}
		model.setTransactionList(validTransList);
		if(CollectionUtils.isEmpty(validTransList)){
			result.setStatus(ReverseStatus.FAIL_DEPENSE_PREVIOUS.getValue());
			result.joinMessage("There is no any transaction selected to be reversed");
		}
	}
	
	private void checkReverseDependency(List<SettlementTransaction> settleTransList, OperationResult result) throws Exception {
		for(SettlementTransaction trans : settleTransList){
			List<SettlementTransaction> dependentTrans = settlementService.getDependentSettleTransaction(trans.getTransType(), trans.getTransNo());
			checkTransDependency(trans.getTransNo(), dependentTrans, settleTransList, result);
		}
	}
	
	/**
	 * Check if the dependent transaction will be reversed at this time
	 * @param transNo
	 * @param dependentTransList
	 * @param reversalTransList
	 * @param result
	 * @throws Exception
	 */
	private void checkTransDependency(String transNo, List<SettlementTransaction> dependentTransList, 
							List<SettlementTransaction> reversalTransList, OperationResult result) throws Exception{
		List<SettlementTransaction> realDependentLsit = new ArrayList<SettlementTransaction>();
		if(CollectionUtils.isNotEmpty(dependentTransList)){
			for(SettlementTransaction depTrans : dependentTransList){
				boolean isDependent = true;
				for(SettlementTransaction reversalTrans : reversalTransList){
					if(depTrans.getTransNo().equals(reversalTrans.getTransNo())){
						isDependent = false;
					}
				}
				if(isDependent){
					realDependentLsit.add(depTrans);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(realDependentLsit)) {
			result.setStatus(ReverseStatus.FAIL_DEPENSE_PREVIOUS.getValue());
			result.joinMessage(buildReverseDependencyMessage(transNo, realDependentLsit));
		}
	}

	private String buildReverseDependencyMessage(String transNo,
			List<SettlementTransaction> dependentTrans) throws Exception {
		StringBuffer buff = new StringBuffer();
		buff.append(transNo).append(" depends on below transactions: ");
		for (SettlementTransaction trans : dependentTrans) {
			buff.append(" [Transaction No:").append(trans.getTransNo());
			buff.append(" Transaction Date:").append(trans.getInsertTime());
			buff.append("] ");
		}
		buff.append(" Please reverse them first in sequence. ");
		return buff.toString();
	}
	
	@Override
	public String getReversalViewInfo(Reverse reverse) throws Exception{
		StringBuffer info = new StringBuffer();
		info.append("\r\r==================Reversal Information==================");
		info.append("\rReversal Reason : ");
		if(reverse.getReverseReason() != null){
			ReverseReason reason = reverseReasonDao.load(reverse.getReverseReason());
			info.append(reason == null?"":reason.getReasonName());
		}
		info.append("\rReversal Request By : ");
		if(reverse.getRequestBy() != null){
			User user = userService.load(reverse.getRequestBy());
			info.append(user == null?"":user.getUsername());
		}
		//info.append("\rReversal Date : ").append(reverse.getReverseDate());
		info.append("\rReversal Remarks : ").append(reverse.getRemark()==null?"":reverse.getRemark());
		return info.toString();
	}
}
