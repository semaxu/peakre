package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContDeductionsItemLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContDeductionsItemLogDao extends BaseDao<TRiContDeductionsItemLog> {
	@Override
	public Class<TRiContDeductionsItemLog> getEntityClass() {
		return TRiContDeductionsItemLog.class;
	}
}

