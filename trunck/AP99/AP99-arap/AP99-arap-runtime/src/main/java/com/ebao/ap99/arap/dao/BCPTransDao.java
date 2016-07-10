package com.ebao.ap99.arap.dao;

import com.ebao.ap99.arap.entity.BCPTrans;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class BCPTransDao extends BaseDao<BCPTrans> {

	@Override
	public Class<BCPTrans> getEntityClass() {
		return BCPTrans.class;
	}

}
