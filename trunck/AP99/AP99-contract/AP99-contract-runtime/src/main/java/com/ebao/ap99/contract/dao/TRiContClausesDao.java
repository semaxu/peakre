package com.ebao.ap99.contract.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContClauses;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContClausesDao extends BaseDao<TRiContClauses> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContClauses> getEntityClass() {
		return TRiContClauses.class;
	}

	/**
	 * @param contCompId
	 * @return
	 */
	public TRiContClauses loadByContCompId(Long contCompId) {
		final Query query = getEntityManager().createNamedQuery("TRiContClauses.loadByContCompId",
				TRiContClauses.class);
		query.setParameter("contCompId", contCompId);
		TRiContClauses clauseVO = new TRiContClauses();
		try {
			Object obj = query.getSingleResult();
			if (null != obj)
				clauseVO = (TRiContClauses) obj;
		} catch (NoResultException e) {
			logger.debug("There no TRiContClaus entity found for query, the contCompId=" + contCompId);
			return null;
		}
		return clauseVO;
	}

	public TRiContClauses save(TRiContClauses infoVO) {
		if (infoVO.getClausesId() != null) {
			TRiContClauses existingOne = this.load(infoVO.getClausesId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}

}