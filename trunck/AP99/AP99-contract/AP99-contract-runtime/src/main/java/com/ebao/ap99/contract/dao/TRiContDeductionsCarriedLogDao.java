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

import com.ebao.ap99.contract.entity.TRiContDeductionsCarrLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * Date: Apr 21, 2016 7:39:27 PM
 * 
 * @author Weiping.Wang
 *
 */
public class TRiContDeductionsCarriedLogDao extends BaseDao<TRiContDeductionsCarrLog> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContDeductionsCarrLog> getEntityClass() {
		return TRiContDeductionsCarrLog.class;
	}

	public List<TRiContDeductionsCarrLog> loadBySlidingIdAndOperateId(Long slidingId, Long operateId) throws Exception {
		final Query query = getEntityManager().createNamedQuery("TRiContDeductionsCarrLog.loadBySlidingIdAndOperateId",
				TRiContDeductionsCarrLog.class);
		query.setParameter("commSlidingDetailId", slidingId);
		query.setParameter("operateId", operateId);
		List<TRiContDeductionsCarrLog> carrVOLogList = new ArrayList<TRiContDeductionsCarrLog>();
		try {
			carrVOLogList = query.getResultList();
		} catch (NoResultException e) {
			logger.debug("There no TRiContDeductionsCarrLog entity found for query, the operateId=" + operateId);
		}
		return carrVOLogList;
	}
}
