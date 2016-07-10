package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContLimitItem;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContLimitItemDao extends BaseDao<TRiContLimitItem> {

	@Override
	public Class<TRiContLimitItem> getEntityClass() {
		return TRiContLimitItem.class;
	}

	public TRiContLimitItem save(TRiContLimitItem infoVO) {
		if (infoVO.getItemId() != null) {
			TRiContLimitItem existingOne = this.load(infoVO.getItemId());
			infoVO.syncDataTo(existingOne, true);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}