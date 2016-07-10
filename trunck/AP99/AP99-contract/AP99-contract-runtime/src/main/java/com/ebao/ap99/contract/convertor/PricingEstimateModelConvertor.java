package com.ebao.ap99.contract.convertor;

import com.ebao.ap99.contract.entity.TRiContPricingItem;
import com.ebao.ap99.contract.model.PricingEstimateItemVO;
import com.ebao.ap99.contract.model.PricingEstimateVO;
import com.ebao.ap99.contract.model.BusinessModel.PricingEstimateBO;
import com.ebao.ap99.parent.BeanUtils;

public class PricingEstimateModelConvertor {
	/**
	 * 
	 * @param bo
	 * @param vo
	 * @throws Exception
	 */
	public static void convertBOToVO(PricingEstimateBO bo, PricingEstimateVO vo) throws Exception{
		convertBOAndVO(bo,vo,true);
	}
	/**
	 * 
	 * @param bo
	 * @param vo
	 * @throws Exception
	 */
	public static void convertVOToBO(PricingEstimateBO bo, PricingEstimateVO vo) throws Exception{
		convertBOAndVO(bo,vo,false);
	}
	/**
	 * 
	 * @param bo
	 * @param vo
	 * @param isOppsite
	 * @throws Exception
	 */
	private static void convertBOAndVO(PricingEstimateBO bo, PricingEstimateVO vo, boolean isOppsite) throws Exception {
		BeanUtils.copyPropertiesByDirection(bo, vo, isOppsite);
		if(isOppsite){
			vo.setPricingItemList(BeanUtils.convertList(bo.getTRiContPricingItems(), PricingEstimateItemVO.class));
		} else {
			bo.setTRiContPricingItems(BeanUtils.convertList(vo.getPricingItemList(), TRiContPricingItem.class));
		}
	}
}
