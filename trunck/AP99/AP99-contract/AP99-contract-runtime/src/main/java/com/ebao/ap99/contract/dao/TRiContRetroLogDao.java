/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ebao.ap99.contract.entity.TRiContRetro;
import com.ebao.ap99.contract.entity.TRiContRetroLog;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * Date: Jan 26, 2016 6:01:53 PM
 * 
 * @author Weiping.Wang
 *
 */
public class TRiContRetroLogDao extends BaseDao<TRiContRetroLog> {
	@PersistenceContext
	private EntityManager em;

	@Override
	public Class<TRiContRetroLog> getEntityClass() {
		return TRiContRetroLog.class;
	}

	public List<TRiContRetro> getRetrocessionListForLog(Long compId, Long operateId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t ");
		sql.append(" FROM TRiContRetroLog t");
		sql.append(" WHERE t.operateId = :operateId ");
		sql.append(" AND t.compId = :compId ");
		sql.append(" ORDER BY t.sequence ASC ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("operateId", operateId);
		query.setParameter("compId", compId);

		@SuppressWarnings("unchecked")
		List<TRiContRetroLog> logList = (List<TRiContRetroLog>) query.getResultList();
		List<TRiContRetro> list = BeanUtils.convertList(logList, TRiContRetro.class);

		return list;
	}

	public List<TRiContRetro> getCoveredSectionListForLog(Long retroCompId, Long operateId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t ");
		sql.append(" FROM TRiContRetroLog t");
		sql.append(" WHERE t.operateId = :operateId ");
		sql.append(" AND t.retroCompId = :retroCompId ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("operateId", operateId);
		query.setParameter("retroCompId", retroCompId);

		@SuppressWarnings("unchecked")
		List<TRiContRetroLog> logList = (List<TRiContRetroLog>) query.getResultList();
		List<TRiContRetro> list = BeanUtils.convertList(logList, TRiContRetro.class);

		return list;
	}

	/**
	 * 
	 * @param compId
	 * @param operateId
	 * @return
	 * @throws Exception
	 */
	public List<TRiContRetroLog> loadRetrocessionListForLog(Long compId, Long operateId) throws Exception {
		final Query query = getEntityManager().createNamedQuery("TRiContRetroLog.loadByCompIdAndOperateId",
				TRiContRetroLog.class);
		query.setParameter("compId", compId);
		query.setParameter("operateId", operateId);
		List<TRiContRetroLog> list = query.getResultList();
		return list;
	}

	/**
	 * 
	 * @param retroCompId
	 * @param operateId
	 * @return
	 * @throws Exception
	 */
	public List<TRiContRetroLog> loadCoveredSectionListForLog(Long retroCompId, Long operateId) throws Exception {
		final Query query = getEntityManager().createNamedQuery("TRiContRetroLog.findByRetroCompIdAndOperateId",
				TRiContRetroLog.class);
		query.setParameter("retroCompId", retroCompId);
		query.setParameter("operateId", operateId);
		List<TRiContRetroLog> list = query.getResultList();
		return list;
	}
}
