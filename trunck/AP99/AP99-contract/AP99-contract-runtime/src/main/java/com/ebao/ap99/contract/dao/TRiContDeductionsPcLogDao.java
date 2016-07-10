package com.ebao.ap99.contract.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContDeductionsPcLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContDeductionsPcLogDao extends BaseDao<TRiContDeductionsPcLog> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContDeductionsPcLog> getEntityClass() {
		return TRiContDeductionsPcLog.class;
	}

	/**
	 * 
	 * @param decutionsId
	 * @param operateId
	 * @return
	 * @throws Exception
	 */
	public TRiContDeductionsPcLog loadByDecutionsIdAndOperateId(Long decutionsId, Long operateId) throws Exception {
		final Query query = getEntityManager().createNamedQuery("TRiContDeductionsPcLog.loadByDeductionsIdAndOperateId",
				TRiContDeductionsPcLog.class);
		TRiContDeductionsPcLog pcVO = null;
		query.setParameter("deductionsId", decutionsId);
		query.setParameter("operateId", operateId);
		try {
			Object obj = query.getSingleResult();
			if (null != obj)
				pcVO = (TRiContDeductionsPcLog) obj;
		} catch (NoResultException e) {
			logger.debug("There no TRiContDeductionsPcLog entity found for query, the operateId=" + operateId);
			return null;
		}
		return pcVO;
	}
}
