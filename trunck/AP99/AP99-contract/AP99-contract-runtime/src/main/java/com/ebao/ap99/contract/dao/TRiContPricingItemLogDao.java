package com.ebao.ap99.contract.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContPricingItemLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContPricingItemLogDao extends BaseDao<TRiContPricingItemLog> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContPricingItemLog> getEntityClass() {
		return TRiContPricingItemLog.class;
	}

	public List<TRiContPricingItemLog> getItemLogByPricingId(Long pricingId) {
		final Query query = getEntityManager().createNamedQuery("TRiContPricingItemLog.loadByPricingId",
				TRiContPricingItemLog.class);
		query.setParameter("pricingId", pricingId);
		List<TRiContPricingItemLog> logList = new ArrayList<TRiContPricingItemLog>();
		try {
			logList = (List<TRiContPricingItemLog>) query.getResultList();
		} catch (NoResultException e) {
			logger.debug("There no TRiContPricingItemLog entity found for query, the pricingId=" + pricingId);
		}
		return logList;

	}

	public List<TRiContPricingItemLog> getItemLogByPricingIdAndOperateId(Long pricingId, Long operateId)
			throws Exception {
		final Query query = getEntityManager().createNamedQuery("TRiContPricingItemLog.loadByPricingIdAndOperateId",
				TRiContPricingItemLog.class);
		query.setParameter("pricingId", pricingId);
		query.setParameter("operateId", operateId);
		List<TRiContPricingItemLog> logList = new ArrayList<TRiContPricingItemLog>();
		try {
			logList = (List<TRiContPricingItemLog>) query.getResultList();
		} catch (NoResultException e) {
			logger.debug("There no TRiContPricingItemLog entity found for query, the pricingId=" + pricingId);
		}
		return logList;

	}
}
