package com.ebao.ap99.arap.convertor;

import com.ebao.ap99.arap.vo.OffsetDTO;
import com.ebao.ap99.arap.vo.OffsetModel;
import com.ebao.ap99.parent.context.AppContext;

public class SettlementDTOConvertor {

	public OffsetModel convertOffsetDTO(OffsetDTO offsetDTO) throws Exception{
		OffsetModel offsetModel = new OffsetModel();
		offsetModel.setFeeList(offsetDTO.getCreditsAndDebit());
		offsetModel.setSuspenseList(offsetDTO.getBalances());
		offsetModel.setOffsetDate(AppContext.getSysDate());
		offsetModel.setRemark(offsetDTO.getRemark());
		offsetModel.setRegistrationDate(offsetDTO.getRegistrationDate());
		
		return offsetModel;
	}
}
