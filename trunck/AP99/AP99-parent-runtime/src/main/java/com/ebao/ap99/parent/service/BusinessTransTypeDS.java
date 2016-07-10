package com.ebao.ap99.parent.service;

import com.ebao.ap99.parent.entity.BusinessTransType;

public interface BusinessTransTypeDS {
	
	/**
	 * Get business transaction type model through cache management
	 * @param transType
	 * @return
	 * @throws Exception
	 */
	public BusinessTransType getByTransType(Integer transType) throws Exception;
}
