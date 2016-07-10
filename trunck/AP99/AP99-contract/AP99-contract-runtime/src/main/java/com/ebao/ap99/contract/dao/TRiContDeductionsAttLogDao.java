package com.ebao.ap99.contract.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContDeductionsAttLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContDeductionsAttLogDao extends BaseDao<TRiContDeductionsAttLog> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContDeductionsAttLog> getEntityClass() {
		return TRiContDeductionsAttLog.class;
	}

	public List<TRiContDeductionsAttLog> loadBySlidingIdAndOperateId(Long slidingId, Long operateId) throws Exception {
		final Query query = getEntityManager().createNamedQuery("TRiContDeductionsAttLog.loadBySlidingIdAndOperateId",
				TRiContDeductionsAttLog.class);
		query.setParameter("slidingId", slidingId);
		query.setParameter("operateId", operateId);
		List<TRiContDeductionsAttLog> attachVOLogList = new ArrayList<TRiContDeductionsAttLog>();
		try {
			attachVOLogList = query.getResultList();
		} catch (NoResultException e) {
			logger.debug("There no TRiContDeductionsAttLog entity found for query, the operateId=" + operateId);
		}
		return attachVOLogList;
	}

}
