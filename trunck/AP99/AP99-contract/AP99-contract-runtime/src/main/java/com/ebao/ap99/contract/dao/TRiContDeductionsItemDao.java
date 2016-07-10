package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContDeductionsItem;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContDeductionsItemDao extends BaseDao<TRiContDeductionsItem> {
	@Override
	public Class<TRiContDeductionsItem> getEntityClass() {
		return TRiContDeductionsItem.class;
	}
	
	public TRiContDeductionsItem save(TRiContDeductionsItem infoVO) {
		if (infoVO.getDeductionsItemId() != null) {
			TRiContDeductionsItem existingOne = this.load(infoVO.getDeductionsItemId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}

