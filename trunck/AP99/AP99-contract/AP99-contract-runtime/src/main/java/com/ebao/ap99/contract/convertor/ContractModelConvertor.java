/**
 * 
 */
package com.ebao.ap99.contract.convertor;

import com.ebao.ap99.contract.entity.TRiContAccount;
import com.ebao.ap99.contract.entity.TRiContCancel;
import com.ebao.ap99.contract.entity.TRiContClaim;
import com.ebao.ap99.contract.entity.TRiContClaimItem;
import com.ebao.ap99.contract.entity.TRiContractAreaperil;
import com.ebao.ap99.contract.entity.TRiContractInfo;
import com.ebao.ap99.contract.entity.TRiContractMoreInfo;
import com.ebao.ap99.contract.model.ContractClaimConditionItem;
import com.ebao.ap99.contract.model.ContractSectionVO;
import com.ebao.ap99.contract.model.ContractSubsectionVO;
import com.ebao.ap99.contract.model.ContractVO;
import com.ebao.ap99.contract.model.BusinessModel.ContractBO;
import com.ebao.ap99.contract.model.BusinessModel.SectionBO;
import com.ebao.ap99.contract.model.BusinessModel.SubsectionBO;
import com.ebao.ap99.parent.BeanUtils;

/**
 * @author elvira.du
 */
public class ContractModelConvertor {
	/**
	 * 
	 * @param bo
	 * @param vo
	 * @throws Exception
	 */
	public static void convertBOToVO(ContractBO bo, ContractVO vo) throws Exception {
		convertBOAndVO(bo, vo, true);
	}

	/**
	 * 
	 * @param bo
	 * @param vo
	 * @throws Exception
	 */
	public static void convertVOToBO(ContractBO bo, ContractVO vo) throws Exception {
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
	private static void convertBOAndVO(ContractBO bo, ContractVO vo, boolean isOppsite) throws Exception {
		// // Basic Info Copy
		// BeanUtils.copyPropertiesByDirection(bo, vo, isOppsite);
		// More Info copy
		BeanUtils.copyPropertiesByDirection(bo.getMoreInfoBO(), vo, isOppsite);
		// Area&April Info copy
		BeanUtils.copyPropertiesByDirection(bo.getAreaperilBO(), vo, isOppsite);
		// Claim Condition copy
		BeanUtils.copyPropertiesByDirection(bo.getClaimInfoBO(), vo, isOppsite);
		if (isOppsite) {
			vo.setContractClaimConditionItemList(BeanUtils.convertList(bo.getClaimInfoBO().getTRiContClaimItems(),
					ContractClaimConditionItem.class));
		} else {
			bo.getClaimInfoBO().setTRiContClaimItems(
					BeanUtils.convertList(vo.getContractClaimConditionItemList(), TRiContClaimItem.class));
		}
		// Account Condition copy
		BeanUtils.copyPropertiesByDirection(bo.getAccountingBO(), vo, isOppsite);
		// Cancellation Condition copy
		BeanUtils.copyPropertiesByDirection(bo.getCancelBO(), vo, isOppsite);
		// Basic Info Copy
		BeanUtils.copyPropertiesByDirection(bo, vo, isOppsite);
		// Section List copy
		if (isOppsite) {
			vo.setSectionList(BeanUtils.convertList(bo.getSectionList(), ContractSectionVO.class));
			for (int i = 0; i < vo.getSectionList().size(); i++) {
				vo.getSectionList().get(i).setSubsectionList(BeanUtils
						.convertList(bo.getSectionList().get(i).getSubsectionList(), ContractSubsectionVO.class));
			}
		} else {
			bo.setSectionList(BeanUtils.convertList(vo.getSectionList(), SectionBO.class));
			for (int i = 0; i < bo.getSectionList().size(); i++) {
				bo.getSectionList().get(i).setSubsectionList(
						BeanUtils.convertList(vo.getSectionList().get(i).getSubsectionList(), SubsectionBO.class));
			}
		}
	}

	public static void convertBOToContractEntity(ContractBO bo, TRiContractInfo entity) {
		BeanUtils.copyProperties(bo, entity);

		TRiContAccount conditionEntity = new TRiContAccount();
		BeanUtils.copyProperties(bo.getAccountingBO(), conditionEntity);
		conditionEntity.setContCompId(bo.getContCompId());
		entity.setTRiContAccount(conditionEntity);

		TRiContCancel cancelEntity = new TRiContCancel();
		BeanUtils.copyProperties(bo.getCancelBO(), cancelEntity);
		cancelEntity.setContCompId(bo.getContCompId());
		entity.setTRiContCancel(cancelEntity);

		TRiContClaim claimCondition = new TRiContClaim();
		BeanUtils.copyProperties(bo.getClaimInfoBO(), claimCondition);
		claimCondition.setContCompId(bo.getContCompId());
		for (TRiContClaimItem item : claimCondition.getTRiContClaimItems()) {
			if (null != item.getIsCheck() && !item.getIsCheck().equals("true")) {
				item.setPercentage(null);
				item.setAmount(null);
			}
			item.setTRiContClaimCondition(claimCondition);
		}
		entity.setTRiContClaim(claimCondition);

		TRiContractAreaperil areaEntity = new TRiContractAreaperil();
		BeanUtils.copyProperties(bo.getAreaperilBO(), areaEntity);
		areaEntity.setContCompId(bo.getContCompId());
		entity.setTRiContractAreaperil(areaEntity);

		TRiContractMoreInfo moreEntity = new TRiContractMoreInfo();
		BeanUtils.copyProperties(bo.getMoreInfoBO(), moreEntity);
		moreEntity.setContCompId(bo.getContCompId());
		entity.setTRiContractMoreInfo(moreEntity);
	}

}
