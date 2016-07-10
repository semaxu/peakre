package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContAccountLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContAccountLogDao extends BaseDao<TRiContAccountLog> {
	@Override
	public Class<TRiContAccountLog> getEntityClass() {
		return TRiContAccountLog.class;
	}
}
