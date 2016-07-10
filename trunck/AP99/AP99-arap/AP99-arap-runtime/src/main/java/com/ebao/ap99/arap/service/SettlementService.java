package com.ebao.ap99.arap.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ebao.ap99.arap.vo.CreditsAndDebit;
import com.ebao.ap99.arap.vo.EntryItem;
import com.ebao.ap99.arap.vo.SettlementHistory;
import com.ebao.ap99.arap.vo.SettlementTransaction;
import com.ebao.ap99.arap.vo.TransactionQueryCondition;

public interface SettlementService {
	
	/**
	 * Query all normal finance transactions like collection / payment / offset, and the reversed transaction will not be returned. 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<SettlementTransaction> getSettleTransactionByCondition(TransactionQueryCondition condition, Integer pageNo, Integer pageSize) throws Exception;
	
	/**
	 * Query total list of result
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Long countSettleTransaction(TransactionQueryCondition condition) throws Exception;
	
	/**
	 * Generate business number for collection / payment / offset.
	 * @param financeTransType
	 * @return
	 * @see env.xml
	 * @throws Exception
	 */
	public String generateSettleNumber(Integer financeTransType) throws Exception;
	
	/**
	 * Save settlement detail, calculate multiple currency amount, and adjust corresponding fee detail
	 * @param creditDebit
	 * @param financeTransType
	 * @throws Exception
	 */
	public void saveSettlementDetail(CreditsAndDebit creditDebit, Integer financeTransType) throws Exception;
	
	/**
	 * Convert amount with defined GL currency and save exchange detail
	 * @param sourceType
	 * @param sourceId
	 * @param amount
	 * @param currencyCode
	 * @param transDate
	 * @throws Exception
	 */
	public void saveSettlementExchangeDetail(Integer sourceType, Long sourceId, BigDecimal amount, String currencyCode, Date transDate) throws Exception;
	
	/**
	 * Invert amount for special transaction, like payment
	 * @param item
	 * @throws Exception
	 */
	public void feeEntryItemNegate(EntryItem item) throws Exception;
	
	/**
	 * Reversal validation rule, that if some ar/ap of specified finance transaction were processed in subsequence finance settlements 
	 * @param financeTransType
	 * @param transNo finance transaction number
	 * @return
	 * @throws Exception
	 */
	public List<SettlementTransaction> getDependentSettleTransaction(Integer financeTransType, String transNo) throws Exception;
	
	/**
	 * Get finance transaction Id with type and number
	 * @param financeTransType
	 * @param transNo
	 * @return
	 * @throws Exception
	 */
	public Long getTransIdByNo(Integer financeTransType, String transNo) throws Exception;
	
	/**
	 * Reverse detail ARAP and suspenses of specified settlement transaction
	 * @param financeTransType
	 * @param transNo
	 * @param reverseId
	 * @throws Exception
	 */
	public void reverseSettlement(Integer financeTransType, String transNo, Long reverseId) throws Exception;
	
	/**
	 * Get settlement history
	 * @param bizTransType
	 * @param bizTransId
	 * @return
	 * @throws Exception
	 */
	public List<SettlementHistory> getSettlementHistory(Integer bizTransType, Long bizTransId) throws Exception;
	
	/**
	 * Get operator of corresponding finance settlement 
	 * @param financeTransType
	 * @param transId
	 * @return
	 * @throws Exception
	 */
	public Long getSettlementOperator(Integer financeTransType, Long transId) throws Exception;
}
