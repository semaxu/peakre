package com.ebao.ap99.contract.dao;

import java.util.List;

import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContOperation;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContOperationDao extends BaseDao<TRiContOperation> {

	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContOperation> getEntityClass() {
		return TRiContOperation.class;
	}

	public List<TRiContOperation> loadContractForLog(Long contCompId) {
		final Query query = getEntityManager().createNamedQuery("TRiContOperation.loadContractForLog",
				TRiContOperation.class);
		query.setParameter("contCompId", contCompId);
		@SuppressWarnings("unchecked")
		List<TRiContOperation> logList = query.getResultList();
		return logList;
	}

	/**
	 * 
	 * @param contCompId
	 * @return
	 */
	public TRiContOperation loadPreContractOperation(Long contCompId) {
		List<TRiContOperation> operationList = loadContractForLog(contCompId);
		if (null != operationList && operationList.size() > 1) {
			return operationList.get(1);
		} else {
			logger.debug("The contract has no pre operation, cont comp Id=" + contCompId);
			return null;
		}
	}

	public List<TRiContOperation> loadContractForLogByOperateId(Long contractId, Long operateId) throws Exception {
		final Query query = getEntityManager().createNamedQuery("TRiContOperation.loadContractForLogByOperateId",
				TRiContOperation.class);
		query.setParameter("contCompId", contractId);
		query.setParameter("operateId", operateId);
		@SuppressWarnings("unchecked")
		List<TRiContOperation> logList = query.getResultList();
		return logList;
	}
}