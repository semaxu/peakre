package com.ebao.ap99.contract.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContDeductionsPc;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContDeductionsPcDao extends BaseDao<TRiContDeductionsPc> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContDeductionsPc> getEntityClass() {
		return TRiContDeductionsPc.class;
	}

	/**
	 * @param decutionsId
	 * @return
	 */
	public TRiContDeductionsPc loadByContCompId(Long decutionsId) {
		final Query query = getEntityManager().createNamedQuery("TRiContDeductionsPc.loadByDeductionsId",
				TRiContDeductionsPc.class);
		query.setParameter("deductionsId", decutionsId);
		TRiContDeductionsPc pcVO = new TRiContDeductionsPc();
		try {
			Object obj = query.getSingleResult();
			if (null != obj)
				pcVO = (TRiContDeductionsPc) obj;
		} catch (NoResultException e) {
			logger.debug("There no TRiContDeductionsPc entity found for query, the loadByDeductionsId=" + decutionsId);
			return null;
		}
		return pcVO;
	}

	public TRiContDeductionsPc save(TRiContDeductionsPc infoVO) {
		if (infoVO.getPcSlidingId() != null) {
			TRiContDeductionsPc existingOne = this.load(infoVO.getPcSlidingId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}
