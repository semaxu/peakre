package com.ebao.ap99.contract.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.ebao.ap99.contract.entity.TRiContSubsectionInfoLog;
import com.ebao.ap99.contract.model.ContractSubsectionVO;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContSubsectionInfoLogDao extends BaseDao<TRiContSubsectionInfoLog> {
	@PersistenceContext
	private EntityManager em;

	@Override
	public Class<TRiContSubsectionInfoLog> getEntityClass() {
		return TRiContSubsectionInfoLog.class;
	}

	public ContractSubsectionVO getContractSubsectionVOForLog(Long contCompId, Long operateId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t ");
		sql.append("  FROM TRiContSubsectionInfoLog t");
		sql.append(" WHERE t.operateId = :operateId ");
		sql.append("   AND t.contCompId = :contCompId ");
		sql.append(" ORDER BY t.operateId DESC");
		Query query = em.createQuery(sql.toString());
		query.setParameter("operateId", operateId);
		query.setParameter("contCompId", contCompId);

		@SuppressWarnings("unchecked")
		List<ContractSubsectionVO> subsectionVOList = (List<ContractSubsectionVO>) query.getResultList();
		ContractSubsectionVO subsectionVO = new ContractSubsectionVO();
		if (CollectionUtils.isNotEmpty(subsectionVOList)) {
			BeanUtils.copyProperties(subsectionVOList.get(0), subsectionVO);
		}
		return subsectionVO;
	}

}
