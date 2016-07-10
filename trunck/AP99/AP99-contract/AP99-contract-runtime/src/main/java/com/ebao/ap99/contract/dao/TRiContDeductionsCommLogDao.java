package com.ebao.ap99.contract.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContDeductionsCommLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContDeductionsCommLogDao extends BaseDao<TRiContDeductionsCommLog> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContDeductionsCommLog> getEntityClass() {
		return TRiContDeductionsCommLog.class;
	}

	/**
	 * 
	 * @param decutionsId
	 * @param operateId
	 * @return
	 * @throws Exception
	 */
	public TRiContDeductionsCommLog loadByDecutionsIdAndOperateId(Long decutionsId, Long operateId) throws Exception {
		final Query query = getEntityManager().createNamedQuery(
				"TRiContDeductionsCommLog.loadByDeductionsIdAndOperateId", TRiContDeductionsCommLog.class);
		TRiContDeductionsCommLog pcVO = null;
		query.setParameter("deductionsId", decutionsId);
		query.setParameter("operateId", operateId);
		try {
			Object obj = query.getSingleResult();
			if (null != obj)
				pcVO = (TRiContDeductionsCommLog) obj;
		} catch (NoResultException e) {
			logger.debug("There no TRiContDeductionsCommLog entity found for query, the operateId=" + operateId);
			return null;
		}
		return pcVO;
	}
}
