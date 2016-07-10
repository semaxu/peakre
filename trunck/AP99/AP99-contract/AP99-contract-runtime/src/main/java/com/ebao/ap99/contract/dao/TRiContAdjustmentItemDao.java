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

import com.ebao.ap99.contract.entity.TRiContAdjustmentItem;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author weiping.wang
 *
 */
public class TRiContAdjustmentItemDao extends BaseDao<TRiContAdjustmentItem> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContAdjustmentItem> getEntityClass() {
		return TRiContAdjustmentItem.class;
	}

	public List<TRiContAdjustmentItem> loadByContCompId(Long contCompId) throws Exception {
		final Query query = getEntityManager().createNamedQuery("TRiContAdjustmentItem.findByContCompId",
				TRiContAdjustmentItem.class);
		query.setParameter("contCompId", contCompId);
		List<TRiContAdjustmentItem> itemList = new ArrayList<TRiContAdjustmentItem>();
		try {
			itemList = query.getResultList();
		} catch (NoResultException e) {
			logger.debug("There no TRiContAdjustmentItem entity found for query, the contCompId=" + contCompId);
		}
		return itemList;
	}

}