package com.ebao.ap99.arap.service;

import java.math.BigDecimal;
import java.util.List;

import com.ebao.ap99.arap.vo.Balance;
import com.ebao.ap99.arap.vo.CollectionModel;
import com.ebao.ap99.arap.vo.OffsetModel;
import com.ebao.ap99.arap.vo.PaymentModel;
import com.ebao.ap99.arap.vo.SuspenseQueryCondition;

public interface SuspenseService {

	/**
	 * Save suspense receipt
	 * @param collectionModel
	 * @throws Exception
	 */
	public void saveCollectionSuspense(CollectionModel model) throws Exception;
	
	/**
	 * Transfer suspense balance with D/C fee, and adjust suspense balance amount
	 * @param paymentModel
	 * @throws Exception
	 */
	public void savePaymentSuspense(PaymentModel model) throws Exception;
	
	/**
	 *  Offset suspense balance with D/C fee, and adjust suspense balance amount
	 * @param offsetModel
	 * @throws Exception
	 */
	public void saveOffsetSuspense(OffsetModel model) throws Exception;
	
	/**
	 * Query available suspenses with specified criteria
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<Balance> getSuspenseByCodition(SuspenseQueryCondition condition) throws Exception;
	
	/**
	 * Query suspense detail with specified settlement Id.
	 * @param suspenseList
	 * @return
	 * @throws Exception
	 */
	public List<Balance> getSettlementSuspense(Integer settleTransType, Long settleTransId) throws Exception;
	
	/**
	 * Check if the suspense is selected
	 * @param suspenseList
	 * @return valid/selected suspenses
	 * @throws Exception
	 */
	public List<Balance> filterInvalidSuspense(List<Balance> suspenseList) throws Exception;
	
	/**
	 * Adjust balance amount of specified suspense
	 * @param suspenseId
	 * @param adjustAmount
	 * @throws Exception
	 */
	public void adjustBalance(Long suspenseId, BigDecimal adjustAmount) throws Exception;
}
