package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContClaimLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContClaimLogDao extends BaseDao<TRiContClaimLog> {
	@Override
	public Class<TRiContClaimLog> getEntityClass() {
		return TRiContClaimLog.class;
	}
}
