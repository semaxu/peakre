/**
 * 
 */
package com.ebao.ap99.partner.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ebao.ap99.partner.entity.TContact;
import com.ebao.ap99.partner.entity.TPartner;
import com.ebao.ap99.partner.entity.TPartnerLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class PartnerLogDao extends BaseDao<TPartnerLog> {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Class<TPartnerLog> getEntityClass() {
		return TPartnerLog.class;
	}
    
	   public List<TPartnerLog> findListByPartner(TPartnerLog partner) {
	        StringBuffer sqlBuf = new StringBuffer("SELECT t FROM TPartnerLog t WHERE 1=1");

	        if (partner.getPartnerId()!= 0L) {
	            sqlBuf.append(" and t.partnerId=:partnerId ");
	        }    
	        sqlBuf.append(" order by t.partnerLogId desc ");
	        Query query = em.createQuery(sqlBuf.toString());

	        if (partner.getPartnerId() !=0L) {
	            query.setParameter("partnerId", partner.getPartnerId());
	        }
	        @SuppressWarnings("unchecked")
	        List<TPartnerLog> list = (List<TPartnerLog>) query.getResultList();
	        return list;
	    }

	
}
