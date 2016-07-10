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

import com.ebao.ap99.contract.entity.TRiContAdjustmentItemLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author weiping.wang
 *
 */
public class TRiContAdjustmentItemLogDao extends BaseDao<TRiContAdjustmentItemLog> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContAdjustmentItemLog> getEntityClass() {
		return TRiContAdjustmentItemLog.class;
	}

	@SuppressWarnings("unchecked")
	public List<TRiContAdjustmentItemLog> loadByContCompIdAndAdjustmentId(Long adjustmentId, Long contCompId) {
		final Query query = getEntityManager().createNamedQuery(
				"TRiContAdjustmentItemLog.loadByContCompIdAndAdjustmentId", TRiContAdjustmentItemLog.class);
		query.setParameter("contCompId", contCompId);
		query.setParameter("adjustmentId", adjustmentId);
		List<TRiContAdjustmentItemLog> list = new ArrayList<TRiContAdjustmentItemLog>();
		try {
			list = query.getResultList();
		} catch (NoResultException e) {
			logger.debug("There no TRiContAdjustmentItemLog list found for query, the contCompId=" + contCompId
					+ ",adjustmentId=" + adjustmentId);
			return list;
		}
		return list;
	}
}