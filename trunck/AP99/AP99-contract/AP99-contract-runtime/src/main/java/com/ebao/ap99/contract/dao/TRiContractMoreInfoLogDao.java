package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContractMoreInfoLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContractMoreInfoLogDao extends BaseDao<TRiContractMoreInfoLog> {
	@Override
	public Class<TRiContractMoreInfoLog> getEntityClass() {
		return TRiContractMoreInfoLog.class;
	}
}