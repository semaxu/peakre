package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContLimitEvent;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContLimitEventDao extends BaseDao<TRiContLimitEvent> {
	@Override
	public Class<TRiContLimitEvent> getEntityClass() {
		return TRiContLimitEvent.class;
	}
	
	public TRiContLimitEvent save(TRiContLimitEvent infoVO) {
		if (infoVO.getEventId() != null) {
			TRiContLimitEvent existingOne = this.load(infoVO.getEventId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}