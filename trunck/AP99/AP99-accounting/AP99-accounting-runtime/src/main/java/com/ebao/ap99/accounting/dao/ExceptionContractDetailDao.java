package com.ebao.ap99.accounting.dao;

import com.ebao.ap99.accounting.entity.TRiAcctExcepContDetail;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class ExceptionContractDetailDao extends BaseDao<TRiAcctExcepContDetail> {

	@Override
	public Class<TRiAcctExcepContDetail> getEntityClass() {
		return TRiAcctExcepContDetail.class;
	}
}
