package com.ebao.ap99.arap.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.constant.BankAccountStatus;
import com.ebao.ap99.arap.constant.OperationStatus;
import com.ebao.ap99.arap.dao.BankAccountDao;
import com.ebao.ap99.arap.dao.CollectionDao;
import com.ebao.ap99.arap.dao.PaymentDao;
import com.ebao.ap99.arap.entity.Bank;
import com.ebao.ap99.arap.entity.BankAccount;
import com.ebao.ap99.arap.service.BankAccountService;
import com.ebao.ap99.arap.vo.BankAccountCondition;
import com.ebao.ap99.arap.vo.BankAccountVO;
import com.ebao.ap99.arap.vo.BankDTO;
import com.ebao.ap99.arap.vo.OperationResult;
import com.ebao.ap99.parent.Page;

public class BankAccountServiceImpl implements BankAccountService {
	
	@Autowired
	BankAccountDao bankAccountDao;
	
	@Autowired
	CollectionDao collectionDao;
	
	@Autowired
	PaymentDao paymentDao;
	
	@Autowired
	PaymentDao payentDao;
	
	@Override
	public List<BankAccount> findByCondition(BankAccountCondition condition)
			throws Exception {
		List<BankAccount> result = bankAccountDao.queryByCondition(condition);
		if(CollectionUtils.isNotEmpty(result)){
			for(BankAccount account : result){
				if(isUsed(account.getAccountNo())){
					account.setInUse(BankAccountStatus.IN_USED.getValue());
				}else{
					account.setInUse(account.getStatus());
				}
			}
		}
		return result;
	}

	public Long countByPageCondition(BankAccountCondition condition) throws Exception {
		return bankAccountDao.countByPageCondition(condition);
	}
	
	@Override
	public OperationResult saveBankAccount(BankAccount bankAccount) throws Exception {
		OperationResult result = new OperationResult();
		if(bankAccountDao.hasDuplicateBankAccount(bankAccount)){
			result.setStatus(OperationStatus.FAILED_DATA_EXISTS.getValue());
			result.setMessage("The same bank account exists in the system!");
		}else{
			bankAccountDao.save(bankAccount);
		}
		return result;
	}
	
	/**
	 * 
	 */
	public OperationResult deleteBankAccount(Long accountId) throws Exception{
		OperationResult result = new OperationResult();
		BankAccount account = bankAccountDao.load(accountId);
		if(isUsed(account.getAccountNo())){
			result.setStatus(OperationStatus.FAILED_IN_USED.getValue());
			result.setMessage("The bank account is in-use; the delete operation is denied!");
		}else{
			bankAccountDao.deleteBankAccount(accountId);
		}
		return result;
	}
	
	private boolean isUsed(String accountNo) throws Exception{
		boolean isUsed = collectionDao.isBankAccountUsed(accountNo);
		if(!isUsed){
			isUsed = paymentDao.isBankAccountUsed(accountNo);
		}
		return isUsed;
	}
	
	public BankAccount findBankAccount(Long accountId) throws Exception {
		return bankAccountDao.findBankAccount(accountId);
	}
	
	public List<Map<String, Object>> queryAllBankAccount() throws Exception {
		List<BankAccount> bankAccountList = bankAccountDao.queryAllBankAccount();
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		if (null != bankAccountList && bankAccountList.size() > 0) {
			Map<String, Object> itemMap = null;
			
			for (BankAccount bankAccount : bankAccountList) {
				itemMap = new HashMap<String, Object>();
				
				itemMap.put("id", bankAccount.getAccountNo());
				itemMap.put("text", bankAccount.getAccountName());
				itemMap.put("currencyCode", bankAccount.getCurrencyCode());
				
				resultList.add(itemMap);
			}
		}
		
		return resultList;
	}

	/**
	 * @param currencyCode
	 * @param accountType
	 * @return List<BankAccountVO> bank account list
	 * @throws Exception
	 * @author meiliang.zou
	 */
	public List<BankAccountVO> queryBankAccountByCurrencyCode(String currencyCode, Long accountType) throws Exception {
		List<BankAccount> bankAccountList = bankAccountDao.queryBankAccountByCurrencyCode(currencyCode, accountType);
		
		List<BankAccountVO> list = new ArrayList<BankAccountVO>();
		BankAccountVO bankAccountVO = null;
		
		if (null != bankAccountList && bankAccountList.size() > 0) {
			for (BankAccount bankAccount : bankAccountList) {
				bankAccountVO = new BankAccountVO();
				
				bankAccountVO.setId(bankAccount.getAccountNo());
				bankAccountVO.setText(bankAccount.getAccountName());
				
				list.add(bankAccountVO);
			}
		}
		
		return list;
	}
	
	/**
	 * Query Bank
	 * @param bankDTO search condition
	 * @return Page<Bank>
	 * @author meiliang.zou
	 */
	public Page<Bank> queryBank(BankDTO bankDTO) {
		return bankAccountDao.queryBank(bankDTO);
	}
	
	/**
	 * 
	 * @param bankCode
	 * @return String BankName
	 * @author meiliang.zou
	 */
	public String queryBankByCode(String bankCode) {
		return bankAccountDao.queryBankByCode(bankCode);
	}
}
