package com.ebao.ap99.arap.service;

import com.ebao.ap99.arap.vo.PaymentModel;

public interface PaymentService {
	/**
	 * Submit payment data, below operations will be handled
	 *  save payment base information
	 *  save settlement detail including fee detail and suspense
	 *  calculate multiple currency amount for mark off & payment amount of fee & suspense
	 *  adjust balance of corresponding fee and suspense
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String payment(PaymentModel model) throws Exception;
	
	/**
	 * Query payment detail information, including settlement fee and suspense data 
	 * 
	 * @param transId
	 * @return
	 * @throws Exception
	 */
	public PaymentModel queryPaymentDetail(Long transId) throws Exception;
}
