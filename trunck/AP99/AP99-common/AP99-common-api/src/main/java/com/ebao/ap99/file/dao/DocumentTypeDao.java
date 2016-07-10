/**
 * 
 */
package com.ebao.ap99.file.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.ebao.ap99.file.entity.DocumentType;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class DocumentTypeDao extends BaseDao<DocumentType> {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Class<DocumentType> getEntityClass() {
		return DocumentType.class;
	}

    public List<DocumentType> findList(String businessType) {
        StringBuffer sqlBuf = new StringBuffer("SELECT t FROM TDocumentCategory t WHERE 1=1");

        if (StringUtils.isNotBlank(businessType)) {
            sqlBuf.append(" and t.businessType=:businessType");
        }    

        Query query = em.createQuery(sqlBuf.toString());

        if (StringUtils.isNotBlank(businessType)) {
            query.setParameter("businessType", businessType);
        }
        @SuppressWarnings("unchecked")
        List<DocumentType> list = (List<DocumentType>) query.getResultList();
        return list;
    }
	
}
