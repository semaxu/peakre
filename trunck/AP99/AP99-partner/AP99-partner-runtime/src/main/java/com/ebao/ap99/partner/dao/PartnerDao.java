/**
 * 
 */
package com.ebao.ap99.partner.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.ebao.ap99.partner.entity.TAmlCheck;
import com.ebao.ap99.partner.entity.TPartner;
import com.ebao.ap99.partner.model.PartnerQuery;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class PartnerDao extends BaseDao<TPartner> {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Class<TPartner> getEntityClass() {
		return TPartner.class;
	}
    
    
	public List<TPartner> loadAllByConditions(Map<String, String> equalConditions, Integer start, Integer limit) {
		final CriteriaBuilder builder = em.getCriteriaBuilder();
		final CriteriaQuery<TPartner> criteriaQuery = builder.createQuery(getEntityClass());
		final Root<TPartner> root = criteriaQuery.from(getEntityClass());
		criteriaQuery.select(root);
		Expression<Boolean> whereClause = null;

		for (Entry<String, String> entry : equalConditions.entrySet()) {
			String name = entry.getKey();
			String keyword = entry.getValue();
			Expression<Boolean> expression = builder.equal(root.get(name),  keyword );
			whereClause = (whereClause == null) ? expression : builder.and(whereClause, expression);
		}

		if (whereClause != null) {
			criteriaQuery.where(whereClause);
		}

		TypedQuery<TPartner> query = em.createQuery(criteriaQuery);
		if (start != null) {
			query.setFirstResult(start);
		}
		if (limit != null) {
			query.setMaxResults(limit);
		}
		return query.getResultList();
	}
	
	
	public List<TPartner> findListByConditions(TPartner partner) {
		StringBuffer sqlBuf = new StringBuffer("SELECT t FROM TPartner t WHERE 1=1");

		if (StringUtils.isNotBlank(partner.getBusinessPartnerId())) {
			sqlBuf.append(" and t.businessPartnerId=:businessPartnerId");
		}
		if (StringUtils.isNotBlank(partner.getBusinessPartnerCategory())) {
			sqlBuf.append(" and t.businessPartnerCategory=:businessPartnerCategory");
		}
		if (partner.getTradingName() != null) {
			sqlBuf.append(" and t.tradingName=:tradingName");
		}
		if (partner.getStatus() != null) {
			sqlBuf.append(" and t.status=:status");
		}
		

		Query query = em.createQuery(sqlBuf.toString());

		if (StringUtils.isNotBlank(partner.getBusinessPartnerId())) {
			query.setParameter("businessPartnerId", partner.getBusinessPartnerId());
		}
		if (StringUtils.isNotBlank(partner.getBusinessPartnerCategory())) {
			query.setParameter("businessPartnerCategory", partner.getBusinessPartnerCategory());
		}
		if (partner.getTradingName() != null) {
			query.setParameter("tradingName", partner.getTradingName());
		}
		if (partner.getStatus() != null) {
			query.setParameter("status", partner.getStatus());
		}
		
		@SuppressWarnings("unchecked")
		List<TPartner> list = (List<TPartner>) query.getResultList();
		return list;
	}
	
	public List<TPartner> findPageByConditions(TPartner partner, int start, int limit) {
		StringBuffer sqlBuf = new StringBuffer("SELECT t FROM TPartner t WHERE 1=1");

		if (StringUtils.isNotBlank(partner.getBusinessPartnerId())) {
			sqlBuf.append(" and t.businessPartnerId=:businessPartnerId");
		}
		if (StringUtils.isNotBlank(partner.getBusinessPartnerCategory())) {
			sqlBuf.append(" and t.businessPartnerCategory=:businessPartnerCategory");
		}
		if (partner.getTradingName() != null) {
			sqlBuf.append(" and t.tradingName=:tradingName");
		}
		if (partner.getStatus() != null) {
			sqlBuf.append(" and t.status=:status");
		}
		

		Query query = em.createQuery(sqlBuf.toString());

		if (StringUtils.isNotBlank(partner.getBusinessPartnerId())) {
			query.setParameter("businessPartnerId", partner.getBusinessPartnerId());
		}
		if (StringUtils.isNotBlank(partner.getBusinessPartnerCategory())) {
			query.setParameter("businessPartnerCategory", partner.getBusinessPartnerCategory());
		}
		if (partner.getTradingName() != null) {
			query.setParameter("tradingName", partner.getTradingName());
		}
		if (partner.getStatus() != null) {
			query.setParameter("status", partner.getStatus());
		}
		
        query.setFirstResult(start);
        query.setMaxResults(limit);
        
		@SuppressWarnings("unchecked")
		List<TPartner> list = (List<TPartner>) query.getResultList();
		return list;
	}
	
	
	public Long countForList(TPartner partner) {
		StringBuffer sqlBuf = new StringBuffer("SELECT count(*) FROM TPartner t WHERE 1=1");

		if (StringUtils.isNotBlank(partner.getBusinessPartnerId())) {
			sqlBuf.append(" and t.businessPartnerId=:businessPartnerId");
		}
		if (StringUtils.isNotBlank(partner.getBusinessPartnerCategory())) {
			sqlBuf.append(" and t.businessPartnerCategory=:businessPartnerCategory");
		}
		if (partner.getTradingName() != null) {
			sqlBuf.append(" and t.tradingName=:tradingName");
		}
		if (partner.getStatus() != null) {
			sqlBuf.append(" and t.status=:status");
		}
		

		Query query = em.createQuery(sqlBuf.toString());

		if (StringUtils.isNotBlank(partner.getBusinessPartnerId())) {
			query.setParameter("businessPartnerId", partner.getBusinessPartnerId());
		}
		if (StringUtils.isNotBlank(partner.getBusinessPartnerCategory())) {
			query.setParameter("businessPartnerCategory", partner.getBusinessPartnerCategory());
		}
		if (StringUtils.isNotBlank(partner.getBusinessPartnerCategory())) {
			query.setParameter("businessPartnerRole", partner.getBusinessPartnerCategory());
		}
		if (partner.getTradingName() != null) {
			query.setParameter("tradingName", partner.getTradingName());
		}
		if (partner.getStatus() != null) {
			query.setParameter("status", partner.getStatus());
		}
		
		Long result = (Long) query.getSingleResult();
        
		return result;
	}
	
    public boolean  checkPartnerId(String businessPartnerId){
        boolean flag = false;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT t.* FROM T_RI_BP_PARTNER t where  ");
        
        if (StringUtils.isNotBlank(businessPartnerId)) {
            sql.append(" t.BUSINESS_PARTNER_ID='").append(businessPartnerId).append("'");         
        }

        List<TPartner> partnerList = this.getJdbcTemplate().query(sql.toString(), new Object[] {},
                new BeanPropertyRowMapper<TPartner>(TPartner.class));
        if(partnerList.size()==1){
            if(partnerList.get(0).getPartnerId()!=0L){
                return false;
            }else{
                return true;
            }
        }
        if(partnerList.size()>1){
            flag = true;
        }
        return flag;
    }
    
    public TPartner loadByBusinessPartnerId(String partnerCode){
    	final Query query = getEntityManager().createNamedQuery("TPartner.findByBusinessPartnerId", TPartner.class);
		query.setParameter("businessPartnerId", partnerCode);
		TPartner partner = new TPartner();
		try {
			Object obj = query.getSingleResult();
			if (null != obj)
				partner = (TPartner)  obj;
		} catch (NoResultException e) {
			
		}
		return partner;
    }
	
}
