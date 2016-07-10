package com.ebao.ap99.arap.dao;

import com.ebao.ap99.arap.entity.GLBatch;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class GLBatchDao extends BaseDao<GLBatch> {

	@Override
	public Class<GLBatch> getEntityClass() {
		return GLBatch.class;
	}

}
