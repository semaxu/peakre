package com.ebao.ap99.contract.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContPremium;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContPremiumDao extends BaseDao<TRiContPremium> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContPremium> getEntityClass() {
		return TRiContPremium.class;
	}

	/**
	 * @param contCompId
	 * @return
	 */
	public TRiContPremium loadByContCompId(Long contCompId) {
		final Query query = getEntityManager().createNamedQuery("TRiContPremium.loadByContCompId",
				TRiContPremium.class);
		query.setParameter("contCompId", contCompId);
		TRiContPremium premiumVO = new TRiContPremium();
		try {

			Object obj = query.getSingleResult();
			if (null != obj)
				premiumVO = (TRiContPremium) obj;
		} catch (NoResultException e) {
			logger.debug("There no TRiContShare entity found for query, the contCompId=" + contCompId);
			return null;
		}
		return premiumVO;
	}

	public TRiContPremium save(TRiContPremium infoVO) {
		if (infoVO.getPremiumId() != null) {
			// TRiContPremium existingOne = this.load(infoVO.getPremiumId());
			// infoVO.syncDataTo(existingOne, false);
			TRiContPremium existingOne = this.merge(infoVO);
			this.getEntityManager().flush();
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}