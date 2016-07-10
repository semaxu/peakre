package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContPremiumItem;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContPremiumItemDao extends BaseDao<TRiContPremiumItem> {
	@Override
	public Class<TRiContPremiumItem> getEntityClass() {
		return TRiContPremiumItem.class;
	}
	
	public TRiContPremiumItem save(TRiContPremiumItem infoVO) {
		if (infoVO.getItemId() != null) {
			TRiContPremiumItem existingOne = this.load(infoVO.getItemId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}