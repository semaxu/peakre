package com.ebao.ap99.contract.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContDeductions;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContDeductionsDao extends BaseDao<TRiContDeductions> {

	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContDeductions> getEntityClass() {
		return TRiContDeductions.class;
	}

	/**
	 * @param contCompId
	 * @return
	 */
	public TRiContDeductions loadByContCompId(Long contCompId) {
		final Query query = getEntityManager().createNamedQuery("TRiContDeductions.loadByContCompId",
				TRiContDeductions.class);
		query.setParameter("contCompId", contCompId);
		TRiContDeductions deductionVO = new TRiContDeductions();
		try {
			Object obj = query.getSingleResult();
			if (null != obj)
				deductionVO = (TRiContDeductions) obj;
		} catch (NoResultException e) {
			logger.debug("There no TRiContDeductions entity found for query, the contCompId=" + contCompId);
			return null;
		}
		return deductionVO;
	}

	public TRiContDeductions save(TRiContDeductions infoVO) {
		if (infoVO.getDeductionsId() != null) {
			TRiContDeductions existingOne = this.merge(infoVO);
			this.getEntityManager().flush();
			// TRiContDeductions existingOne =
			// this.load(infoVO.getDeductionsId());
			// infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}
