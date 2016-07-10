package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContLimitEventLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContLimitEventLogDao extends BaseDao<TRiContLimitEventLog> {
	@Override
	public Class<TRiContLimitEventLog> getEntityClass() {
		return TRiContLimitEventLog.class;
	}
}