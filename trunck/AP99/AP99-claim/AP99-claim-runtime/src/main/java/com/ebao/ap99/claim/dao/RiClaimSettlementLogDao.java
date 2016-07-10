/**
 * 
 */
package com.ebao.ap99.claim.dao;

import java.util.List;

import javax.persistence.Query;

import com.ebao.ap99.claim.entity.TRiClaimSettlementLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * @author yujie.zhang
 *
 */
public class RiClaimSettlementLogDao extends BaseDao<TRiClaimSettlementLog> {

	@Override
	public Class<TRiClaimSettlementLog> getEntityClass() {
		return TRiClaimSettlementLog.class;
	}

	public List<TRiClaimSettlementLog> getSettlementLogList(long settlementId) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimSettlementLog.findBySettlementId", TRiClaimSettlementLog.class);
		query.setParameter("settlementId", settlementId);
		@SuppressWarnings("unchecked")
		List<TRiClaimSettlementLog> list = query.getResultList();
		return list;
	}
}
