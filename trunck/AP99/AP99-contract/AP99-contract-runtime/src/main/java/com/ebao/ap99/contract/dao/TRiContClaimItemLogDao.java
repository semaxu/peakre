/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.dao;

import com.ebao.ap99.contract.entity.TRiContClaimItemLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * Date: Jan 15, 2016 4:09:10 PM
 * 
 * @author weiping.wang
 *
 */
public class TRiContClaimItemLogDao extends BaseDao<TRiContClaimItemLog> {
	@Override
	public Class<TRiContClaimItemLog> getEntityClass() {
		return TRiContClaimItemLog.class;
	}
}
