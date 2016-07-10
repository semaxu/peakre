package com.ebao.ap99.arap.dao;

import com.ebao.ap99.arap.entity.EntryCode;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class EntryCodeDao extends BaseDao<EntryCode> {

	@Override
	public Class<EntryCode> getEntityClass() {
		return EntryCode.class;
	}
}
