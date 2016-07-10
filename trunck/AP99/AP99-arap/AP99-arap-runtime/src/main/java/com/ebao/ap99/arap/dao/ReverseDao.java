package com.ebao.ap99.arap.dao;

import java.util.List;

import javax.persistence.Query;

import com.ebao.ap99.arap.entity.Reverse;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class ReverseDao extends BaseDao<Reverse> {

	@Override
	public Class<Reverse> getEntityClass() {
		return Reverse.class;
	}
	
	@SuppressWarnings("unchecked")
	public List<Reverse> getByTransNo(String transNo) throws Exception{
		Query query = this.getEntityManager().createNamedQuery("Reverse.findByTransNo", this.getEntityClass());
		query.setParameter("transNo", transNo);
		return query.getResultList();
	}
}
