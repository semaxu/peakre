/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.service;

import com.ebao.ap99.contract.model.AdjustmentVO;

/**
 * Date: Mar 24, 2016 11:46:31 PM
 * 
 * @author weiping.wang
 *
 */
public interface ContractAdjustmentDS {

	public AdjustmentVO getAdjustInfo(Long contractId) throws Exception;

	public void saveAdjustment(AdjustmentVO vo) throws Exception;

}
