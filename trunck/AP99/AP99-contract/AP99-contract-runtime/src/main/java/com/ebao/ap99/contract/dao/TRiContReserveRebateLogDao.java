package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContReserveRebateLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContReserveRebateLogDao extends BaseDao<TRiContReserveRebateLog> {
	@Override
	public Class<TRiContReserveRebateLog> getEntityClass() {
		return TRiContReserveRebateLog.class;
	}
}
