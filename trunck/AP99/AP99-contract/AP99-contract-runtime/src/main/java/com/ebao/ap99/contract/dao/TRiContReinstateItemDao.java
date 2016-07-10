package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContReinstateItem;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContReinstateItemDao extends BaseDao<TRiContReinstateItem> {
	@Override
	public Class<TRiContReinstateItem> getEntityClass() {
		return TRiContReinstateItem.class;
	}

	public TRiContReinstateItem save(TRiContReinstateItem infoVO) {
		if (infoVO.getItemId() != null) {
			TRiContReinstateItem existingOne = this.load(infoVO.getItemId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}