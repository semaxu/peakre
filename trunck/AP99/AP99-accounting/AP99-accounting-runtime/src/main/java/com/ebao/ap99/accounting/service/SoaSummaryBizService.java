package com.ebao.ap99.accounting.service;

import java.util.List;

import com.ebao.ap99.accounting.model.SoaCurrencyModel;

public interface SoaSummaryBizService {
	
	//link by Preview of Summary button and Statement Summary button
		List<SoaCurrencyModel> getSoaSummaryInfo(long statementId);
}
