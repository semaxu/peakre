package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContCurrencyLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContCurrencyLogDao extends BaseDao<TRiContCurrencyLog> {
	@Override
	public Class<TRiContCurrencyLog> getEntityClass() {
		return TRiContCurrencyLog.class;
	}
}