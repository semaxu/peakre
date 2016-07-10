package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContPremiumLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContPremiumLogDao extends BaseDao<TRiContPremiumLog> {
	@Override
	public Class<TRiContPremiumLog> getEntityClass() {
		return TRiContPremiumLog.class;
	}
}