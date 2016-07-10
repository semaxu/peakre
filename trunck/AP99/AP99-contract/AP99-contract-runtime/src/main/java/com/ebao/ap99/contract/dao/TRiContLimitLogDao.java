package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContLimitLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContLimitLogDao extends BaseDao<TRiContLimitLog> {
	@Override
	public Class<TRiContLimitLog> getEntityClass() {
		return TRiContLimitLog.class;
	}
}