package com.ebao.ap99.arap.service;

import com.ebao.ap99.arap.vo.CollectionModel;

public interface CollectionService {
	
	/**
	 * Submit collection data, below operation will be handled
	 *   save collection base information
	 *   save settlement detail including fee detail and suspense
	 *   calculate multiple currency amount for mark off & collection amount
	 *   adjust balance of corresponding fee
	 * @param model
	 * @return receipt number
	 * @throws Exception
	 */
	public String collection(CollectionModel model) throws Exception;
	
	/**
	 * Query collection detail information, including settlement fee and suspense data 
	 * 
	 * @param collectionId
	 * @return
	 * @throws Exception
	 */
	public CollectionModel queryCollectionDetail(Long collectionId) throws Exception;
	
}
