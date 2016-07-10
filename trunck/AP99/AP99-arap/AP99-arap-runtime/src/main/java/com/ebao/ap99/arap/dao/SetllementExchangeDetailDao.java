package com.ebao.ap99.arap.dao;

import java.util.List;

import javax.persistence.Query;

import com.ebao.ap99.arap.entity.SetllementExchangeDetail;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class SetllementExchangeDetailDao extends
		BaseDao<SetllementExchangeDetail> {

	@Override
	public Class<SetllementExchangeDetail> getEntityClass() {
		return SetllementExchangeDetail.class;
	}

	public void save(SetllementExchangeDetail settlementExchangeDetail) throws Exception{
		Assert.isNotNull(settlementExchangeDetail);
		this.insertOrUpdate(settlementExchangeDetail);
	}
	
	@SuppressWarnings("unchecked")
	public List<SetllementExchangeDetail> getBySourceTypeAndSourceId(Integer sourceType, Long sourceId) throws Exception{
		Query query = this.getEntityManager().createNamedQuery("SetllementExchangeDetail.findBySourceTypeAndSourceId", SetllementExchangeDetail.class);
		query.setParameter("sourceType", sourceType);
		query.setParameter("sourceId", sourceId);
		return query.getResultList();
	}
}
