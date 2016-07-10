package com.ebao.ap99.arap.dao;

import com.ebao.ap99.arap.entity.GLMappingEntry;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class GLMappingEntryDao extends BaseDao<GLMappingEntry> {

	@Override
	public Class<GLMappingEntry> getEntityClass() {
		return GLMappingEntry.class;
	}

}
