package com.ebao.ap99.common.dao;

import java.util.List;

import javax.persistence.Query;

import com.ebao.ap99.common.entity.TRiMenus;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiMenusDao extends BaseDao<TRiMenus> {

	@Override
	public Class<TRiMenus> getEntityClass() {
		return TRiMenus.class;
	}
	
	public List<TRiMenus> loadAllMenus(){
		final Query query = getEntityManager().createNamedQuery("TRiMenus.findAll", TRiMenus.class);
		@SuppressWarnings("unchecked")
		List<TRiMenus> menuList = query.getResultList();
		return menuList;
	}
}
