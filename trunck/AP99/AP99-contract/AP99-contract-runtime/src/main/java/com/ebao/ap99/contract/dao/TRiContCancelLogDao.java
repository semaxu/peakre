package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContCancelLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContCancelLogDao extends BaseDao<TRiContCancelLog> {
	@Override
	public Class<TRiContCancelLog> getEntityClass() {
		return TRiContCancelLog.class;
	}
}