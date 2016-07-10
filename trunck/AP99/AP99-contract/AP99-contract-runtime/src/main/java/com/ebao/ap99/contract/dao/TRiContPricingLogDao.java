package com.ebao.ap99.contract.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContPricingLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContPricingLogDao extends BaseDao<TRiContPricingLog> {
	private static Logger logger = LoggerFactory.getLogger();

	@PersistenceContext
	private EntityManager em;

	@Override
	public Class<TRiContPricingLog> getEntityClass() {
		return TRiContPricingLog.class;
	}

	public TRiContPricingLog getPricingForLog(Long sectionId, Long operateId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t ");
		sql.append("  FROM TRiContPricingLog t");
		sql.append(" WHERE t.operateId = :operateId ");
		sql.append("   AND t.contCompId = :sectionId ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("operateId", operateId);
		query.setParameter("sectionId", sectionId);

		TRiContPricingLog pricingVO = null;
		try {
			Object obj = query.getSingleResult();
			if (null != obj)
				pricingVO = (TRiContPricingLog) obj;
		} catch (NoResultException e) {
			logger.debug("There no TRiContPricingLog entity found for query, the operateId=" + operateId);
		}

		return pricingVO;
	}

	/**
	 * 
	 * @param sectionId
	 * @param operateId
	 * @return
	 */
	public TRiContPricingLog getLatestPricingForLog(Long sectionId, Long operateId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t ");
		sql.append("  FROM TRiContPricingLog t");
		sql.append(" WHERE t.operateId < :operateId ");
		sql.append("   AND t.contCompId = :sectionId ");
		sql.append(" ORDER BY t.logId DESC ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("operateId", operateId);
		query.setParameter("sectionId", sectionId);

		TRiContPricingLog pricingVO = null;
		List<TRiContPricingLog> logList = new ArrayList<TRiContPricingLog>();
		try {
			logList = (List<TRiContPricingLog>) query.getResultList();
		} catch (NoResultException e) {
			logger.debug("There no TRiContPricingLog entity found for query, the contCompId=" + sectionId);
		}
		if (CollectionUtils.isNotEmpty(logList)) {
			pricingVO = logList.get(0);
		}

		return pricingVO;
	}
}