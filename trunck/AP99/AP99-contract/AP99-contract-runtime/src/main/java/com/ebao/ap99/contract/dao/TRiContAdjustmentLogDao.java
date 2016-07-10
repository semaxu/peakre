/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.dao;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContAdjustmentLog;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author weiping.wang
 *
 */
public class TRiContAdjustmentLogDao extends BaseDao<TRiContAdjustmentLog> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContAdjustmentLog> getEntityClass() {
		return TRiContAdjustmentLog.class;
	}
}