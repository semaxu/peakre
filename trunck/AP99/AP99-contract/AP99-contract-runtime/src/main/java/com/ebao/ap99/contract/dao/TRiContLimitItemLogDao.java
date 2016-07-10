package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContLimitItemLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContLimitItemLogDao extends BaseDao<TRiContLimitItemLog> {
	@Override
	public Class<TRiContLimitItemLog> getEntityClass() {
		return TRiContLimitItemLog.class;
	}
}