package com.ebao.ap99.contract.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContReinstate;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContReinstateDao extends BaseDao<TRiContReinstate> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContReinstate> getEntityClass() {
		return TRiContReinstate.class;
	}

	public TRiContReinstate loadByContCompId(Long contCompId) {
		final Query query = getEntityManager().createNamedQuery("TRiContReinstate.loadByContCompId",
				TRiContReinstate.class);
		query.setParameter("contCompId", contCompId);
		TRiContReinstate reinstateVO = new TRiContReinstate();
		try {

			Object obj = query.getSingleResult();
			if (null != obj)
				reinstateVO = (TRiContReinstate) obj;
		} catch (NoResultException e) {
			logger.debug("There no TRiContReinstate entity found for query, the contCompId=" + contCompId);
			return null;
		}
		return reinstateVO;
	}

	public TRiContReinstate save(TRiContReinstate infoVO) {
		if (infoVO.getReinId() != null) {
			TRiContReinstate existingOne = this.merge(infoVO);
			this.getEntityManager().flush();
			// TRiContReinstate existingOne = this.load(infoVO.getReinId());
			// infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}