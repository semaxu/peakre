package com.ebao.ap99.contract.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContClaim;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContClaimDao extends BaseDao<TRiContClaim> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContClaim> getEntityClass() {
		return TRiContClaim.class;
	}

	public TRiContClaim save(TRiContClaim infoVO) {
		if (infoVO.getContCompId() != null) {
			TRiContClaim existingOne = this.load(infoVO.getContCompId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}

	public boolean needPostingContractClaim(Long contractId) {
		final Query query = getEntityManager().createNamedQuery("TRiContClaim.getPostingFlag", String.class);
		query.setParameter("contCompId", contractId);
		String posting = "false";
		try {
			Object obj = query.getSingleResult();
			if (null != obj)
				posting = (String) obj;
		} catch (NoResultException e) {
			logger.debug("There no TRiContShare entity found for query, the contCompId=" + contractId);
			return false;
		}
		return Boolean.valueOf(posting);
	}
}
