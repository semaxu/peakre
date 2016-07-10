/**
 * 
 */
package com.ebao.ap99.partner.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ebao.ap99.partner.entity.TChangeHistory;
import com.ebao.ap99.partner.entity.TContact;
import com.ebao.ap99.partner.entity.TPartner;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class ChangeHistoryDao extends BaseDao<TChangeHistory> {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Class<TChangeHistory> getEntityClass() {
		return TChangeHistory.class;
	}
    
	   public List<TChangeHistory> findListByPartner(TChangeHistory partner) {
	        StringBuffer sqlBuf = new StringBuffer("SELECT t FROM TChangeHistory t WHERE 1=1");

	        if (partner.getPartnerId()!= 0L) {
	            sqlBuf.append(" and t.partnerId=:partnerId");
	        }    

	        Query query = em.createQuery(sqlBuf.toString());

	        if (partner.getPartnerId() !=0L) {
	            query.setParameter("partnerId", partner.getPartnerId());
	        }
	        @SuppressWarnings("unchecked")
	        List<TChangeHistory> list = (List<TChangeHistory>) query.getResultList();
	        return list;
	    }

	
}
