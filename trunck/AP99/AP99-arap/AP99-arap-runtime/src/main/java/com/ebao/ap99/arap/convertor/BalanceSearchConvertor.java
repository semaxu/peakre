package com.ebao.ap99.arap.convertor;

import com.ebao.ap99.arap.vo.BalanceSearchDTO;
import com.ebao.ap99.arap.vo.SuspenseQueryCondition;

/*
 * @author ammon.zhou
 * Offset model search
 * */
public class BalanceSearchConvertor {
	public SuspenseQueryCondition modelToSuspenseQueryConditionEntity(BalanceSearchDTO balanceSearchDTO){
		SuspenseQueryCondition condition = new SuspenseQueryCondition();
		condition.setContractIdArray(balanceSearchDTO.getContractId());
		condition.setPartnerCode(balanceSearchDTO.getPartnerCode());
		return condition;
	}
}
