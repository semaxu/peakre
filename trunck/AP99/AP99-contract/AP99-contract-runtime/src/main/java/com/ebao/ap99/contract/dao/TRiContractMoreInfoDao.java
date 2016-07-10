package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContractMoreInfo;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContractMoreInfoDao extends BaseDao<TRiContractMoreInfo> {
	@Override
	public Class<TRiContractMoreInfo> getEntityClass() {
		return TRiContractMoreInfo.class;
	}
	public TRiContractMoreInfo save(TRiContractMoreInfo infoVO) {
		if (infoVO.getContCompId() != null) {
			TRiContractMoreInfo existingOne = this.load(infoVO.getContCompId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}
