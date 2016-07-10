package com.ebao.ap99.arap.service;

import com.ebao.ap99.arap.entity.Reverse;
import com.ebao.ap99.arap.vo.OperationResult;
import com.ebao.ap99.arap.vo.ReversalModel;


public interface ReversalService {
	/**
	 * Reverse settlement transactions
	 * Validation rule: 
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public OperationResult reverse(ReversalModel model) throws Exception;
	
	/**
	 * get reverse basic information for view page
	 * @param reverse
	 * @return
	 * @throws Exception
	 */
	public String getReversalViewInfo(Reverse reverse) throws Exception;
}
