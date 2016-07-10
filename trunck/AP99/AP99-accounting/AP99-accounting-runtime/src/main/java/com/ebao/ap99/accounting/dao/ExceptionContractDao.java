package com.ebao.ap99.accounting.dao;

import com.ebao.ap99.accounting.entity.TRiAcctExcepCont;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class ExceptionContractDao extends BaseDao<TRiAcctExcepCont>{
	@Override
	public Class<TRiAcctExcepCont> getEntityClass() {
		return TRiAcctExcepCont.class;
	}
}
