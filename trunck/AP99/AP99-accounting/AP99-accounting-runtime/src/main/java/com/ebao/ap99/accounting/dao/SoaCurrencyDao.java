/**
 * 
 */
package com.ebao.ap99.accounting.dao;

import com.ebao.ap99.accounting.entity.TRiSoaCurrency;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * @author 
 *
 */
public class SoaCurrencyDao extends BaseDao<TRiSoaCurrency> {

	@Override
	public Class<TRiSoaCurrency> getEntityClass() {
		return TRiSoaCurrency.class;
	}

}
