/**
 * 
 */
package com.ebao.ap99.file.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ebao.ap99.file.entity.TDocument;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class DocumentDao extends BaseDao<TDocument> {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Class<TDocument> getEntityClass() {
		return TDocument.class;
	}

    public List<TDocument> findList(Long businessId) {
        StringBuffer sqlBuf = new StringBuffer("SELECT t FROM TDocument t WHERE 1=1");

        if (businessId != 0L) {
            sqlBuf.append(" and t.businessId=:businessId");
        }    

        Query query = em.createQuery(sqlBuf.toString());

        if (businessId !=0L) {
            query.setParameter("businessId", businessId);
        }
        @SuppressWarnings("unchecked")
        List<TDocument> list = (List<TDocument>) query.getResultList();
        return list;
    }
	
}
