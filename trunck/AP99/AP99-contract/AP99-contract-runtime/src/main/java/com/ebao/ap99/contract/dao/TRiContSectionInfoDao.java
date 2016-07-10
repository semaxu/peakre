package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContSectionInfo;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContSectionInfoDao extends BaseDao<TRiContSectionInfo> {
	@Override
	public Class<TRiContSectionInfo> getEntityClass() {
		return TRiContSectionInfo.class;
	}

	public TRiContSectionInfo save(TRiContSectionInfo infoVO) {
		if (infoVO.getContCompId() != null) {
			TRiContSectionInfo existingOne = this.load(infoVO.getContCompId());
			if (existingOne != null) {
				infoVO.syncDataTo(existingOne, false);
				return existingOne;
			}
		}
		this.getEntityManager().persist(infoVO);
		return infoVO;
	}
}
