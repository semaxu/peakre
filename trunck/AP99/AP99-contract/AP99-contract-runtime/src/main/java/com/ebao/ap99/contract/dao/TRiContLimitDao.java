package com.ebao.ap99.contract.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContLimit;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContLimitDao extends BaseDao<TRiContLimit> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContLimit> getEntityClass() {
		return TRiContLimit.class;
	}

	/**
	 * @param contCompId
	 * @return
	 */
	public TRiContLimit loadByContCompId(Long contCompId) {
		final Query query = getEntityManager().createNamedQuery("TRiContLimit.loadByContCompId", TRiContLimit.class);
		query.setParameter("contCompId", contCompId);
		TRiContLimit limitVO = null;
		try {
			Object obj = query.getSingleResult();
			if (null != obj)
				limitVO = (TRiContLimit) obj;
		} catch (NoResultException e) {
			logger.debug("There no TRiContLimit entity found for query, the contCompId=" + contCompId);
			return null;
		}
		return limitVO;
	}

	public TRiContLimit save(TRiContLimit infoVO) {
		if (infoVO.getLimitId() != null) {
			TRiContLimit existingOne = this.merge(infoVO);
			this.getEntityManager().flush();
			// TRiContLimit existingOne = this.load(infoVO.getLimitId());
			// infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}