package com.ebao.ap99.contract.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContDeductionsAttach;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContDeductionsAttachDao extends BaseDao<TRiContDeductionsAttach> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContDeductionsAttach> getEntityClass() {
		return TRiContDeductionsAttach.class;
	}

	public List<TRiContDeductionsAttach> loadBySlidingId(Long slidingId) {
		final Query query = getEntityManager().createNamedQuery("TRiContDeductionsAttach.loadBySlidingId",
				TRiContDeductionsAttach.class);
		query.setParameter("slidingId", slidingId);// ato
		List<TRiContDeductionsAttach> attachVOList = new ArrayList<TRiContDeductionsAttach>();
		try {
			attachVOList = query.getResultList();
		} catch (NoResultException e) {
			logger.debug("There no TRiContDeductionsAttach entity found for query, the loadBySlidingId=" + slidingId);
		}
		return attachVOList;
	}

	public TRiContDeductionsAttach save(TRiContDeductionsAttach infoVO) {
		if (infoVO.getAttachmentId() != null) {
			TRiContDeductionsAttach existingOne = this.merge(infoVO);
			this.getEntityManager().flush();
			// TRiContDeductionsAttach existingOne =
			// this.load(infoVO.getAttachmentId());
			// infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}
