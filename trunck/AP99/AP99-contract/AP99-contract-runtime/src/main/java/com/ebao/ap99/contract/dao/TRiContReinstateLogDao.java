package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContReinstateLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContReinstateLogDao extends BaseDao<TRiContReinstateLog> {
	@Override
	public Class<TRiContReinstateLog> getEntityClass() {
		return TRiContReinstateLog.class;
	}
}