/**
 * 
 */
package com.ebao.ap99.file.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ebao.ap99.file.entity.TDocumentRecord;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class DocumentRecordDao extends BaseDao<TDocumentRecord> {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Class<TDocumentRecord> getEntityClass() {
		return TDocumentRecord.class;
	}


	
}
