package com.ebao.ap99.accounting.service;

import java.util.List;

import com.ebao.ap99.parent.model.TreeModel;

public interface ChooseSectionBizService {
	//link by add a section button
	void toChooseSecton(long statementId);
	
	Object loadSectionsInfo(String treeArray) throws Exception;
	
	public List<TreeModel> getContractStructureByCode(String contractCode, Long uwYear, boolean isInforce) throws Exception;

}
