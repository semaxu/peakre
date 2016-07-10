package com.ebao.ap99.contract.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContDeductionsCarried;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContDeductionsCarriedDao extends BaseDao<TRiContDeductionsCarried> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContDeductionsCarried> getEntityClass() {
		return TRiContDeductionsCarried.class;
	}

	public List<TRiContDeductionsCarried> loadBySlidingId(Long slidingId) {
		final Query query = getEntityManager().createNamedQuery("TRiContDeductionsCarried.loadBySlidingId",
				TRiContDeductionsCarried.class);
		query.setParameter("commSlidingDetailId", slidingId);
		List<TRiContDeductionsCarried> carriedList = new ArrayList<TRiContDeductionsCarried>();
		try {
			carriedList = query.getResultList();
		} catch (NoResultException e) {
			logger.debug("There no TRiContDeductionsAttach entity found for query, the loadBySlidingId=" + slidingId);
		}
		return carriedList;
	}

	public TRiContDeductionsCarried save(TRiContDeductionsCarried infoVO) {
		if (infoVO.getCarriedForwardsId() != null) {
			TRiContDeductionsCarried existingOne = this.load(infoVO.getCarriedForwardsId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}
