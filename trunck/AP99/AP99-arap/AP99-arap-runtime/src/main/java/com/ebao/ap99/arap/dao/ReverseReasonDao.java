package com.ebao.ap99.arap.dao;

import com.ebao.ap99.arap.entity.ReverseReason;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class ReverseReasonDao extends BaseDao<ReverseReason> {

	@Override
	public Class<ReverseReason> getEntityClass() {
		return ReverseReason.class;
	}

}
