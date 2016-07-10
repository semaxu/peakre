/**
 * 
 */
package com.ebao.ap99.accounting.dao;

import com.ebao.ap99.accounting.entity.TRiSoaSectionItem;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * @author 
 *
 */
public class SoaSectionItemDao extends BaseDao<TRiSoaSectionItem> {

	@Override
	public Class<TRiSoaSectionItem> getEntityClass() {
		return TRiSoaSectionItem.class;
	}

}
