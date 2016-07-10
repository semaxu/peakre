/**
 * 
 */
package com.ebao.ap99.file.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.ebao.ap99.file.entity.DocumentRule;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class DocumentRuleDao extends BaseDao<DocumentRule> {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Class<DocumentRule> getEntityClass() {
		return DocumentRule.class;
	}

    public List<DocumentRule> findListByConditions(DocumentRule partner) {
        StringBuffer sqlBuf = new StringBuffer("SELECT t FROM TDocumentRule t WHERE 1=1");

        if (StringUtils.isNotBlank(partner.getBusinessType())) {
            sqlBuf.append(" and t.businessType=:businessType");
        }

        Query query = em.createQuery(sqlBuf.toString());

        if (StringUtils.isNotBlank(partner.getBusinessType())) {
            query.setParameter("businessType", partner.getBusinessType());
        }
        
        @SuppressWarnings("unchecked")
        List<DocumentRule> list = (List<DocumentRule>) query.getResultList();
        return list;
    }
	
}
