package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContShareLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContShareLogDao extends BaseDao<TRiContShareLog> {
	@Override
	public Class<TRiContShareLog> getEntityClass() {
		return TRiContShareLog.class;
	}
}