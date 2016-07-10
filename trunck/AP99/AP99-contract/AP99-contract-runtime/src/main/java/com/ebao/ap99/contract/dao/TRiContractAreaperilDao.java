package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContractAreaperil;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContractAreaperilDao extends BaseDao<TRiContractAreaperil> {
	@Override
	public Class<TRiContractAreaperil> getEntityClass() {
		return TRiContractAreaperil.class;
	}
	
	
	public TRiContractAreaperil save(TRiContractAreaperil infoVO) {
		if (infoVO.getContCompId() != null) {
			TRiContractAreaperil existingOne = this.load(infoVO.getContCompId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}
