/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.convertor;

import com.ebao.ap99.contract.model.BusinessConditionVO;
import com.ebao.ap99.contract.model.ContractSubsectionVO;
import com.ebao.ap99.contract.model.BusinessModel.BusinessConditionBO;
import com.ebao.ap99.contract.model.BusinessModel.SubsectionBO;
import com.ebao.ap99.parent.BeanUtils;

/**
 * Date: Jan 6, 2016 4:23:24 PM
 * 
 * @author weiping.wang
 */
public class ContractSubsectionModelConvertor {
	/**
	 * 
	 * @param bo
	 * @param vo
	 * @throws Exception
	 */
	public static void convertBOToVO(SubsectionBO bo, ContractSubsectionVO vo) throws Exception {
		convertBOAndVO(bo, vo, true);
	}

	/**
	 * 
	 * @param bo
	 * @param vo
	 * @throws Exception
	 */
	public static void convertVOToBO(SubsectionBO bo, ContractSubsectionVO vo) throws Exception {
		convertBOAndVO(bo, vo, false);
	}

	/**
	 * if isOppsite==true, then copy bo to vo, else copy vo to bo
	 * 
	 * @param bo
	 * @param vo
	 * @param isOppsite
	 * @throws Exception
	 */
	private static void convertBOAndVO(SubsectionBO bo, ContractSubsectionVO vo, boolean isOppsite) throws Exception {
		BeanUtils.copyPropertiesByDirection(bo, vo, isOppsite);
		// BC info copy
		if (isOppsite) {
			BusinessConditionVO bcVO = new BusinessConditionVO();
			BusinessConditionModelConvertor.covertBuinessBOToVO(bo.getBusinessBO(), bcVO);
			vo.setBusinessConditionVO(bcVO);
		} else {
			BusinessConditionBO bcBO = new BusinessConditionBO();
			if (null != vo.getBusinessConditionVO()) {
				BusinessConditionModelConvertor.convertBusinessVOToBO(bcBO, vo.getBusinessConditionVO());
				bo.setBusinessBO(bcBO);
			}
		}
	}
}
