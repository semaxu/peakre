/**
 * 
 */
package com.ebao.ap99.partner.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ebao.ap99.partner.entity.TPartner;
import com.ebao.ap99.partner.entity.TRelationship;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class RelationshipDao extends BaseDao<TRelationship> {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Class<TRelationship> getEntityClass() {
		return TRelationship.class;
	}
	
    public List<TRelationship> findListByPartner(TPartner partner) {
        StringBuffer sqlBuf = new StringBuffer("SELECT t FROM TRelationship t WHERE 1=1");

        if (partner.getPartnerId()!= 0L) {
            sqlBuf.append(" and t.TPartner.partnerId=:partnerId");
        }    

        Query query = em.createQuery(sqlBuf.toString());

        if (partner.getPartnerId() !=0L) {
            query.setParameter("partnerId", partner.getPartnerId());
        }
        @SuppressWarnings("unchecked")
        List<TRelationship> list = (List<TRelationship>) query.getResultList();
        return list;
    }
	
}
