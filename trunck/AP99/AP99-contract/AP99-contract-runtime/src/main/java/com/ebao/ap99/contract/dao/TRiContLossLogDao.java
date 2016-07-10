package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContLossLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContLossLogDao extends BaseDao<TRiContLossLog> {
	@Override
	public Class<TRiContLossLog> getEntityClass() {
		return TRiContLossLog.class;
	}
}