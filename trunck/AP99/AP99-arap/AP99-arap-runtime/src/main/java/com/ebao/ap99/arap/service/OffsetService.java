package com.ebao.ap99.arap.service;

import com.ebao.ap99.arap.vo.OffsetModel;

public interface OffsetService {
	
	/**
	 * Submit offset data, below operation will be handled
	 *   save offset base information
	 *   save settlement detail including fee detail and suspense
	 *   calculate multiple currency amount for mark off & offset amount
	 *   adjust balance of corresponding fee
	 * @param model
	 * @return offset number
	 * @throws Exception
	 */
	public String offset(OffsetModel model) throws Exception;
	
	/**
	 * Query offset detail information, including settlement fee and suspense data 
	 * 
	 * @param collectionId
	 * @return
	 * @throws Exception
	 */
	public OffsetModel queryOffsetDetail(Long offsetId) throws Exception;
}
