package com.ebao.ap99.arap.dao;

import java.util.List;

import javax.persistence.Query;

import com.ebao.ap99.arap.entity.GLExDetail;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class GLExDetailDao extends BaseDao<GLExDetail> {

	@Override
	public Class<GLExDetail> getEntityClass() {
		return GLExDetail.class;
	}
	
	@SuppressWarnings("unchecked")
	public List<GLExDetail> findBySourceTypeAndSourceId(Integer sourceType, Long sourceId) throws Exception{
		Query query = this.getEntityManager().createNamedQuery("GLExDetail.findBySourceTypeAndSourceId");
		query.setParameter("sourceType", sourceType);
		query.setParameter("sourceId", sourceId);
		return query.getResultList();
	}
}
