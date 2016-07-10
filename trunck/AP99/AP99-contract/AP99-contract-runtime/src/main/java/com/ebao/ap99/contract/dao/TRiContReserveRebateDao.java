package com.ebao.ap99.contract.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContReserveRebate;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContReserveRebateDao extends BaseDao<TRiContReserveRebate> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContReserveRebate> getEntityClass() {
		return TRiContReserveRebate.class;
	}

	/**
	 * @param contCompId
	 * @param uwYear
	 * @return
	 */
	public TRiContReserveRebate loadByContCompId(Long contCompId) {
		TRiContReserveRebate vo = new TRiContReserveRebate();
		final Query query = getEntityManager().createNamedQuery("TRiContReserveRebate.loadByContCompId",
				TRiContReserveRebate.class);
		query.setParameter("contCompId", contCompId);
		try {

			Object obj = query.getSingleResult();
			if (null != obj)
				vo = (TRiContReserveRebate) obj;
		} catch (NoResultException e) {
			logger.debug("There no TRiContReserveRebate entity found for query, the contCompId=" + contCompId);
			return null;
		}
		return vo;
	}

	public TRiContReserveRebate save(TRiContReserveRebate infoVO) {
		if (infoVO.getReserveId() != null) {
			TRiContReserveRebate existingOne = this.load(infoVO.getReserveId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}
