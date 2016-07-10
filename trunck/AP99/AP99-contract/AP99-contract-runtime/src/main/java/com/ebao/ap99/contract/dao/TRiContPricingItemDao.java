package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContPricingItem;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContPricingItemDao extends BaseDao<TRiContPricingItem> {
	
	@Override
	public Class<TRiContPricingItem> getEntityClass() {
		return TRiContPricingItem.class;
	}
	
}
