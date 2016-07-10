
package com.ebao.ap99.accounting.dao;

import com.ebao.ap99.accounting.entity.TRiACCTRevaluate;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * @author 
 *
 */
public class RevaluateDao extends BaseDao<TRiACCTRevaluate> {

	@Override
	public Class<TRiACCTRevaluate> getEntityClass() {
		return TRiACCTRevaluate.class;
	}

}
