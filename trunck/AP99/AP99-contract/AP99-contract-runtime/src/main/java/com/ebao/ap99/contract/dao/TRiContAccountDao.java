package com.ebao.ap99.contract.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.ebao.ap99.contract.entity.TRiContAccount;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContAccountDao extends BaseDao<TRiContAccount> {
	@Override
	public Class<TRiContAccount> getEntityClass() {
		return TRiContAccount.class;
	}

	public TRiContAccount save(TRiContAccount infoVO) {
		if (infoVO.getContCompId() != null) {
			TRiContAccount existingOne = this.load(infoVO.getContCompId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}

	public TRiContAccount getEntityByContCompId(Long contCompId) throws Exception {
		TRiContAccount vo = null;
		final Query query = getEntityManager().createNamedQuery("TRiContAccount.findByContCompId",
				TRiContAccount.class);
		query.setParameter("contCompId", contCompId);

		@SuppressWarnings("unchecked")
		List<TRiContAccount> list = (List<TRiContAccount>) query.getResultList();
		if (CollectionUtils.isNotEmpty(list)) {
			Object obj = query.getSingleResult();
			if (null != obj)
				vo = (TRiContAccount) obj;
		}
		return vo;
	}
}
