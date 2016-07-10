/**
 * 
 */
package com.ebao.ap99.partner.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ebao.ap99.partner.entity.TBankAccount;
import com.ebao.ap99.partner.entity.TContact;
import com.ebao.ap99.partner.entity.TPartner;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class BankAccountDao extends BaseDao<TBankAccount> {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Class<TBankAccount> getEntityClass() {
		return TBankAccount.class;
	}
    
    public List<TBankAccount> findListByPartner(TPartner partner) {
        StringBuffer sqlBuf = new StringBuffer("SELECT t FROM TBankAccount t WHERE 1=1");

        if (partner.getPartnerId()!= 0L) {
            sqlBuf.append(" and t.TPartner.partnerId=:partnerId");
        }    

        Query query = em.createQuery(sqlBuf.toString());

        if (partner.getPartnerId() !=0L) {
            query.setParameter("partnerId", partner.getPartnerId());
        }
        @SuppressWarnings("unchecked")
        List<TBankAccount> list = (List<TBankAccount>) query.getResultList();
        return list;
    }

	
}
