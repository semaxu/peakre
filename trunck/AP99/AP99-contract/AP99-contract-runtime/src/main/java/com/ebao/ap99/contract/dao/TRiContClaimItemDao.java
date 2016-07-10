/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContClaimItem;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * Date: Jan 15, 2016 4:09:10 PM
 * 
 * @author weiping.wang
 *
 */
public class TRiContClaimItemDao extends BaseDao<TRiContClaimItem> {
	
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContClaimItem> getEntityClass() {
		return TRiContClaimItem.class;
	}
	
	public List<TRiContClaimItem> loadByContCompId(Long contCompId){
		final Query query = getEntityManager().createNamedQuery("TRiContClaimItem.loadByContCompId", TRiContClaimItem.class);
		query.setParameter("contCompId", contCompId);
		List<TRiContClaimItem> itemList = new ArrayList<TRiContClaimItem>();
		try{
			itemList = query.getResultList();
		}catch(NoResultException e){
			logger.debug("There no TRiContClaimItem entity found for query, the contCompId="+contCompId);
		}
		return itemList;
	}
	
	public TRiContClaimItem save(TRiContClaimItem infoVO) {
		if (infoVO.getItemId() != null) {
			TRiContClaimItem existingOne = this.load(infoVO.getItemId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}
