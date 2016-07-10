package com.ebao.ap99.contract.dao;

import java.util.List;

import javax.persistence.Query;

import com.ebao.ap99.contract.entity.TRiContractStatusLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContractStatusLogDao extends BaseDao<TRiContractStatusLog> {
	@Override
	public Class<TRiContractStatusLog> getEntityClass() {
		return TRiContractStatusLog.class;
	}
	
	public List<TRiContractStatusLog> loadByContCompId(Long contCompId){
		final Query query = getEntityManager().createNamedQuery("TRiContractStatusLog.loadByContractId", TRiContractStatusLog.class);
		query.setParameter("contCompId", contCompId);
		List<TRiContractStatusLog> logList = query.getResultList();
		return logList;
	}
}