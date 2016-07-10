package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContDeductionsLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContDeductionsLogDao extends BaseDao<TRiContDeductionsLog> {
	@Override
	public Class<TRiContDeductionsLog> getEntityClass() {
		return TRiContDeductionsLog.class;
	}
}

