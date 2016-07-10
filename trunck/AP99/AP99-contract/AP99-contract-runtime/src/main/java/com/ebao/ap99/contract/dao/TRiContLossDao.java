package com.ebao.ap99.contract.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContLoss;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContLossDao extends BaseDao<TRiContLoss> {

	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContLoss> getEntityClass() {
		return TRiContLoss.class;
	}

	/**
	 * @param contCompId
	 * @return
	 */
	public TRiContLoss loadByContCompId(Long contCompId) {
		final Query query = getEntityManager().createNamedQuery("TRiContLoss.loadByContCompId", TRiContLoss.class);
		query.setParameter("contCompId", contCompId);
		TRiContLoss lossVO = new TRiContLoss();
		try {

			Object obj = query.getSingleResult();
			if (null != obj)
				lossVO = (TRiContLoss) obj;
		} catch (NoResultException e) {
			logger.debug("There no TRiContLoss entity found for query, the contCompId=" + contCompId);
			return null;
		}
		return lossVO;
	}

	public TRiContLoss save(TRiContLoss infoVO) {
		if (infoVO.getLossId() != null) {
			TRiContLoss existingOne = this.load(infoVO.getLossId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}