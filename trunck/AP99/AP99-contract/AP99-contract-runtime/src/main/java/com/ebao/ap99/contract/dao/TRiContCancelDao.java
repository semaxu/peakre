package com.ebao.ap99.contract.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContCancel;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContCancelDao extends BaseDao<TRiContCancel> {
	
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContCancel> getEntityClass() {
		return TRiContCancel.class;
	}
	
	public TRiContCancel loadByContCompId(Long contCompId){
		final Query query = getEntityManager().createNamedQuery("TRiContCancel.loadByContCompId", TRiContCancel.class);
		query.setParameter("contCompId", contCompId);
		TRiContCancel cancelVO = new TRiContCancel();
		try{
			Object obj = query.getSingleResult();
			if(null != obj) cancelVO = (TRiContCancel) obj;
		}catch(NoResultException e){
			logger.debug("There no TRiContCancel entity found for query, the contCompId="+contCompId);
		}
		return cancelVO;
	}
}