package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContClausesLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContClausesLogDao extends BaseDao<TRiContClausesLog> {
	@Override
	public Class<TRiContClausesLog> getEntityClass() {
		return TRiContClausesLog.class;
	}
}