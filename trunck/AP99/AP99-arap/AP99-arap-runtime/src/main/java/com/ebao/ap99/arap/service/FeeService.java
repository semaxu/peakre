package com.ebao.ap99.arap.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ebao.ap99.arap.vo.BusinessFeeModel;
import com.ebao.ap99.arap.vo.CreditsAndDebit;
import com.ebao.ap99.arap.vo.FeeQueryCondition;


public interface FeeService {
	
	/**
	 * send business fee detail to finance model
	 * @param bizModelList, it can be base on contract / SOA / claim settlement level
	 * @return {@value=FinanceConsistants#WRITER_TO_FIN_XXX}
	 * @throws Exception
	 */
	public Integer writeToFinance(List<BusinessFeeModel> bizModelList) throws Exception;
	
	/**
	 * Query outstanding ARAP detail to be settled with condition, and group item by currency
	 * @param condition
	 * @return 
	 * @throws Exception
	 */
	public List<CreditsAndDebit> getFeeByCondition(FeeQueryCondition condition) throws Exception;
	
	/**
	 * Get settlement detail for query
	 * @param settleTransType
	 * @param settleTransId
	 * @return
	 * @throws Exception
	 */
	public List<CreditsAndDebit> getSettlementFee(Integer settleTransType, Long settleTransId) throws Exception;
	
	/**
	 * Remove group & unselected records from payment model
	 * @param feeList
	 * @return valid fee list
	 * @throws Exception
	 */
	public List<CreditsAndDebit> filterInvalidFee(List<CreditsAndDebit> feeList) throws Exception;
	
	/**
	 * Calculate gain and loss for the amount base on different date
	 * gain&loss for the original amount exists on the difference of defined exchange rate to different dates
	 * 
	 * @param originalAmount
	 * @param originalCurrency
	 * @param gainLossCurrency
	 * @param originalDate
	 * @param settleDate
	 * @return
	 * @throws Exception
	 */
	public BigDecimal calcGainLoss(BigDecimal originalAmount, String originalCurrency, String gainLossCurrency, Date originalDate, Date settleDate) throws Exception;
	
	/**
	 * Calculate gain&loss for the amounts with specified parameters
	 * 
	 * Generally, gain&loss for the original amount exists on the difference of defined exchange rate to different dates, 
	 * however, the actual settlement amount is on the manual exchange rate instead of define exchange rate for some special business scenarios
	 * 
	 * @param originalAmount
	 * @param originalCurrecy
	 * @param originalDate
	 * @param settleAmount
	 * @param settleCurrency
	 * @param settleDate
	 * @param gainLossCurrency
	 * @return
	 * @throws Exception
	 */
	public BigDecimal calcGainLoss(BigDecimal originalAmount, String originalCurrecy, Date originalDate, BigDecimal settleAmount, String settleCurrency, Date settleDate, String gainLossCurrency) throws Exception;
	
	/**
	 * Calculate gain&loss in USD for specified outstanding ARAP base on mark off amount and actual settle amount
	 * 
	 * @param feeId, calculate amount in USD for mark off amount base on actual ARAP generation date
	 * @param markOffAmount, amount in original currency
	 * @param originalCurrency
	 * @param settleAmount, amount in settlement currency
	 * @param settleCurrency
	 * @param settleDate, calculate amount in USD for settlement amount base on actual settle date
	 * @return
	 */
	public BigDecimal calGainLossInUSD(Long feeId, BigDecimal markOffAmount, String originalCurrency, BigDecimal settleAmount, String settleCurrency, Date settleDate) throws Exception;
	
	/**
	 * convert amount in original currency to value in settlement currency base on specified transaction date
	 * @param amount
	 * @param originalCurrency
	 * @param settleCurrency
	 * @param transDate
	 * @return
	 * @throws Exception
	 */
	public BigDecimal convertAmount(BigDecimal amount, String originalCurrency, String settleCurrency, Date transDate) throws Exception;
	
	/**
	 * adjust balance and sign with the ajustAmount,
	 * and fee balance will be changed to zero even if adjustAmount is not equals outstanding balance in case of fully settle
	 * @param feeId
	 * @param adjustAmount
	 * @param isFullySettle
	 * @throws Exception
	 */
	public void adjustFee(Long feeId, BigDecimal adjustAmount, boolean isFullySettle) throws Exception;
	
	/**
	 * Query finance fee information
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<CreditsAndDebit> queryFeeInfoByCondition(FeeQueryCondition condition) throws Exception;
}
