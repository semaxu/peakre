package com.ebao.ap99.contract.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.ebao.ap99.contract.entity.TRiContractStructureLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContractStructureLogDao extends BaseDao<TRiContractStructureLog> {

	@Override
	public Class<TRiContractStructureLog> getEntityClass() {
		return TRiContractStructureLog.class;
	}

	public List<Long> getChildrenIdList(Long contCompId, Long operateId) {
		final Query query = getEntityManager().createNamedQuery("TRiContractStructureLog.findChildrenIdByParentId",
				Long.class);
		query.setParameter("parentId", contCompId);
		query.setParameter("operateId", operateId);
		List<Long> list = query.getResultList();
		return list;
	}

	public TRiContractStructureLog loadStructureLog(Long contCompId, Long operateId) throws Exception {
		TRiContractStructureLog entityLog = null;
		final Query query = getEntityManager().createNamedQuery("TRiContractStructureLog.findByContCompIdAndOperateId",
				TRiContractStructureLog.class);
		query.setParameter("contCompId", contCompId);
		query.setParameter("operateId", operateId);
		try {
			Object obj = query.getSingleResult();
			if (null != obj)
				entityLog = (TRiContractStructureLog) obj;
		} catch (NoResultException e) {
			return null;
		}
		return entityLog;
	}
}
