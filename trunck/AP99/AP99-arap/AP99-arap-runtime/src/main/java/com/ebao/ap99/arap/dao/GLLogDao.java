package com.ebao.ap99.arap.dao;

import com.ebao.ap99.arap.entity.GLLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class GLLogDao extends BaseDao<GLLog> {
	public final Integer LOG_TYPE_BATCH = 1;
	public final Integer LOG_TYPE_REALTIME = 2;
	public final Integer LOG_TYPE_FILE = 3;
	
	@Override
	public Class<GLLog> getEntityClass() {
		return GLLog.class;
	}
	
	public void log(String message, Integer refType, Long refId) throws Exception{
		GLLog log = new GLLog();
		log.setLogContect(message);
		log.setRefType(refType);
		log.setRefId(refId);
		this.insert(log);
	}
}
