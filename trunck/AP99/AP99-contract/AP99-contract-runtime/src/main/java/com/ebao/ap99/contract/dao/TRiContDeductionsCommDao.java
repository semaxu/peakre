package com.ebao.ap99.contract.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContDeductionsComm;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContDeductionsCommDao extends BaseDao<TRiContDeductionsComm> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContDeductionsComm> getEntityClass() {
		return TRiContDeductionsComm.class;
	}

	/**
	 * 
	 * @param decutionsId
	 * @return
	 */
	public TRiContDeductionsComm loadByContCompId(Long decutionsId) {
		final Query query = getEntityManager().createNamedQuery("TRiContDeductionsComm.loadByDeductionsId",
				TRiContDeductionsComm.class);
		query.setParameter("deductionsId", decutionsId);
		TRiContDeductionsComm commVO = new TRiContDeductionsComm();
		try {
			Object obj = query.getSingleResult();
			if (null != obj)
				commVO = (TRiContDeductionsComm) obj;
		} catch (NoResultException e) {
			logger.debug(
					"There no TRiContDeductionsComm entity found for query, the loadByDeductionsId=" + decutionsId);
			return null;
		}
		return commVO;
	}

	public TRiContDeductionsComm save(TRiContDeductionsComm infoVO) {
		if (infoVO.getCommSlidingDetailId() != null) {
			TRiContDeductionsComm existingOne = this.load(infoVO.getCommSlidingDetailId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}
