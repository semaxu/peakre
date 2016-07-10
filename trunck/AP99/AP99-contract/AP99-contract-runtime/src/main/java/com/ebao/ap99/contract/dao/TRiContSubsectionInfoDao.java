package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContSubsectionInfo;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContSubsectionInfoDao extends BaseDao<TRiContSubsectionInfo> {
	@Override
	public Class<TRiContSubsectionInfo> getEntityClass() {
		return TRiContSubsectionInfo.class;
	}

	public TRiContSubsectionInfo save(TRiContSubsectionInfo infoVO) {
		if (infoVO.getContCompId() != null) {
			TRiContSubsectionInfo existingOne = this.load(infoVO.getContCompId());
			if (existingOne != null) {
				infoVO.syncDataTo(existingOne, false);
				return existingOne;
			}
		}
		this.getEntityManager().persist(infoVO);
		return infoVO;
	}
}
