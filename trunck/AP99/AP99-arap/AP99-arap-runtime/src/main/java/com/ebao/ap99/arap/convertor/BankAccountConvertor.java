/**
 * 
 */
package com.ebao.ap99.arap.convertor;

import org.apache.commons.lang.StringUtils;

import com.ebao.ap99.arap.constant.Constants;
import com.ebao.ap99.arap.entity.BankAccount;
import com.ebao.ap99.arap.vo.BankAccountCondition;
import com.ebao.ap99.arap.vo.BankAccountDTO;

/**
 * @author meiliang.zou
 *
 */
public class BankAccountConvertor {
	public BankAccount modelToEntity(BankAccountDTO bankAccountDTO) {
		BankAccount bankAccount = new BankAccount();
		
    	bankAccount.setAccountId(bankAccountDTO.getAccountId()==null?null:Long.valueOf(bankAccountDTO.getAccountId()));
    	bankAccount.setBranchId(StringUtils.isBlank(bankAccountDTO.getBranch())?null:Long.valueOf(bankAccountDTO.getBranch()));
    	bankAccount.setAccountNo(bankAccountDTO.getBankAccountNumber());
    	bankAccount.setAccountName(bankAccountDTO.getBankAccountName());
    	bankAccount.setCurrencyCode(bankAccountDTO.getCurrency());
    	bankAccount.setBankId(StringUtils.isBlank(bankAccountDTO.getBank())?null:Long.valueOf(bankAccountDTO.getBank()));
    	bankAccount.setAccountType(StringUtils.isBlank(bankAccountDTO.getAccountType())?null:Integer.parseInt(bankAccountDTO.getAccountType()));
    	bankAccount.setStatus(bankAccountDTO.getStatus()==null?Constants.BANK_ACCOUNT_STATUS:Integer.parseInt(bankAccountDTO.getStatus()));
    	
		return bankAccount;
	}
	
	public BankAccountCondition modelToQueryConditionEntity(BankAccountDTO bankAccount) {
		BankAccountCondition condition = new BankAccountCondition();
		
        condition.setAccountName(bankAccount.getBankAccountName());
        condition.setAccountNo(bankAccount.getBankAccountNumber());
        condition.setCurrencyCode(bankAccount.getCurrency());
        condition.setBranchId(StringUtils.isBlank(bankAccount.getBranch())?null:Long.valueOf(bankAccount.getBranch()));
        
        condition.setPageNumber(bankAccount.getPageIndex());
		condition.setPageSize(Integer.parseInt(String.valueOf(bankAccount.getCountPerPage())));
		
		return condition;
	}
}
