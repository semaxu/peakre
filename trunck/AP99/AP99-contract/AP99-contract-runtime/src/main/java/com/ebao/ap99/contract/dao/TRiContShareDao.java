package com.ebao.ap99.contract.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContShare;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContShareDao extends BaseDao<TRiContShare> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContShare> getEntityClass() {
		return TRiContShare.class;
	}

	/**
	 * @param contCompId
	 * @return
	 */
	public TRiContShare loadByContCompId(Long contCompId) {
		final Query query = getEntityManager().createNamedQuery("TRiContShare.loadByContCompId", TRiContShare.class);
		query.setParameter("contCompId", contCompId);
		TRiContShare shareVO = new TRiContShare();
		// try {
		// Object obj = query.getSingleResult();
		// if (null != obj)
		// shareVO = (TRiContShare) obj;
		// } catch (NoResultException e) {
		// logger.debug("There no TRiContShare entity found for query, the
		// contCompId=" + contCompId);
		// return null;
		// }
		@SuppressWarnings("unchecked")
		List<TRiContShare> list = query.getResultList();
		if (CollectionUtils.isNotEmpty(list)) {
			shareVO = list.get(0);
		}
		return shareVO;
	}

	/**
	 * 
	 * @param contCompId
	 */
	public void deleteContCompId(Long contCompId) {
		final Query query = getEntityManager().createNamedQuery("TRiContShare.deleteByContCompId");
		query.setParameter("contCompId", contCompId).executeUpdate();
	}

	public TRiContShare save(TRiContShare infoVO) {
		if (infoVO.getShareId() != null) {
			TRiContShare existingOne = this.load(infoVO.getShareId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}
