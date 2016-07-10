package com.ebao.ap99.arap.service;

import java.util.List;
import java.util.Map;

import com.ebao.ap99.arap.entity.Bank;
import com.ebao.ap99.arap.entity.BankAccount;
import com.ebao.ap99.arap.vo.BankAccountCondition;
import com.ebao.ap99.arap.vo.BankAccountVO;
import com.ebao.ap99.arap.vo.BankDTO;
import com.ebao.ap99.arap.vo.OperationResult;
import com.ebao.ap99.parent.Page;

public interface BankAccountService {
	
	public List<BankAccount> findByCondition(BankAccountCondition condition) throws Exception;
	
	public Long countByPageCondition(BankAccountCondition condition) throws Exception;
	
	/**
	 * 
	 * @param bankAccount
	 * @return
	 * @throws Exception
	 */
	public OperationResult saveBankAccount(BankAccount bankAccount) throws Exception;
	
	/**
	 * 
	 * @param accountId
	 * @return
	 * @throws Exception
	 */
	public OperationResult deleteBankAccount(Long accountId) throws Exception;
	
	public BankAccount findBankAccount(Long accountId) throws Exception;
	
	public List<Map<String, Object>> queryAllBankAccount() throws Exception;
	
	/**
	 * @param currencyCode
	 * @param accountType
	 * @return List<BankAccountVO> bank account list
	 * @throws Exception
	 * @author meiliang.zou
	 */
	public List<BankAccountVO> queryBankAccountByCurrencyCode(String currencyCode, Long accountType) throws Exception;
	
	/**
	 * Query Bank
	 * @param bankDTO search condition
	 * @return Page<Bank>
	 * @author meiliang.zou
	 */
	public Page<Bank> queryBank(BankDTO bankDTO);
	
	/**
	 * 
	 * @param bankCode
	 * @return String BankName
	 * @author meiliang.zou
	 */
	public String queryBankByCode(String bankCode);
}
