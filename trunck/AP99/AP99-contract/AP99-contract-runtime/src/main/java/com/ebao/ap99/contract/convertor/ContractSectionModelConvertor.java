/**
 * 
 */
package com.ebao.ap99.contract.convertor;

import com.ebao.ap99.contract.model.BusinessConditionVO;
import com.ebao.ap99.contract.model.ContractSectionVO;
import com.ebao.ap99.contract.model.ContractSubsectionVO;
import com.ebao.ap99.contract.model.BusinessModel.BusinessConditionBO;
import com.ebao.ap99.contract.model.BusinessModel.SectionBO;
import com.ebao.ap99.contract.model.BusinessModel.SubsectionBO;
import com.ebao.ap99.parent.BeanUtils;

/**
 * @author weiping.wang
 */
public class ContractSectionModelConvertor {
	/**
	 * 
	 * @param bo
	 * @param vo
	 * @throws Exception
	 */
	public static void convertBOToVO(SectionBO bo, ContractSectionVO vo) throws Exception {
		convertBOAndVO(bo, vo, true);
	}

	/**
	 * 
	 * @param bo
	 * @param vo
	 * @throws Exception
	 */
	public static void convertVOToBO(SectionBO bo, ContractSectionVO vo) throws Exception {
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
	private static void convertBOAndVO(SectionBO bo, ContractSectionVO vo, boolean isOppsite) throws Exception {
		BeanUtils.copyPropertiesByDirection(bo, vo, isOppsite);
		// Section List copy
		if (isOppsite) {
			vo.setSubsectionList(BeanUtils.convertList(bo.getSubsectionList(), ContractSubsectionVO.class));

			BusinessConditionVO bcVO = new BusinessConditionVO();
			BusinessConditionModelConvertor.covertBuinessBOToVO(bo.getBusinessBO(), bcVO);
			vo.setBusinessConditionVO(bcVO);
		} else {
			bo.setSubsectionList(BeanUtils.convertList(vo.getSubsectionList(), SubsectionBO.class));

			BusinessConditionBO bcBO = new BusinessConditionBO();
			if (null != vo.getBusinessConditionVO()) {
				BusinessConditionModelConvertor.convertBusinessVOToBO(bcBO, vo.getBusinessConditionVO());
				bo.setBusinessBO(bcBO);
			}
		}
	}
}
