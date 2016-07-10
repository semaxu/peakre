package com.ebao.ap99.arap.dao;

import java.util.List;

import javax.persistence.Query;

import reactor.util.CollectionUtils;

import com.ebao.ap99.arap.entity.Currency;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class CurrencyDao extends BaseDao<Currency> {

	@Override
	public Class<Currency> getEntityClass() {
		return Currency.class;
	}
	
	public Currency findByCurrencyCode(String currencyCode) throws Exception{
		Query query = this.getEntityManager().createNamedQuery("Currency.findByCurrencyCode", Currency.class);
		query.setParameter("currencyCode", currencyCode);
		@SuppressWarnings("unchecked")
		List<Currency> list = query.getResultList();
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return (Currency)list.get(0);
	}
}
