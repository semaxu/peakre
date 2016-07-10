package com.ebao.ap99.contract.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.ebao.ap99.contract.entity.TRiContSectionInfoLog;
import com.ebao.ap99.contract.model.ContractSectionVO;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContSectionInfoLogDao extends BaseDao<TRiContSectionInfoLog> {
	@PersistenceContext
	private EntityManager em;

	@Override
	public Class<TRiContSectionInfoLog> getEntityClass() {
		return TRiContSectionInfoLog.class;
	}

	public ContractSectionVO getContractSectionVOForLog(Long contCompId, Long operateId) {
		ContractSectionVO sectionVO = new ContractSectionVO();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t ");
		sql.append("  FROM TRiContSectionInfoLog t");
		sql.append(" WHERE t.operateId = :operateId ");
		sql.append("   AND t.contCompId = :contCompId ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("operateId", operateId);
		query.setParameter("contCompId", contCompId);

		@SuppressWarnings("unchecked")
		List<TRiContSectionInfoLog> logList = (List<TRiContSectionInfoLog>) query.getResultList();
		if (CollectionUtils.isNotEmpty(logList)) {
			BeanUtils.copyProperties(logList.get(0), sectionVO);
		}

		return sectionVO;
	}
}