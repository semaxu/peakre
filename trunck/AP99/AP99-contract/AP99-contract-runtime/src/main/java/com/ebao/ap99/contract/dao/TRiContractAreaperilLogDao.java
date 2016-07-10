package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContractAreaperilLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContractAreaperilLogDao extends BaseDao<TRiContractAreaperilLog> {
	@Override
	public Class<TRiContractAreaperilLog> getEntityClass() {
		return TRiContractAreaperilLog.class;
	}
}
