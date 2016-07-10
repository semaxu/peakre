package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContReinstateItemLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContReinstateItemLogDao extends BaseDao<TRiContReinstateItemLog> {
	@Override
	public Class<TRiContReinstateItemLog> getEntityClass() {
		return TRiContReinstateItemLog.class;
	}
}