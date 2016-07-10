package com.ebao.ap99.accounting.service;

import com.ebao.ap99.accounting.UI.model.ExceptionContractVO;
import com.ebao.ap99.accounting.model.ExceptionContractRecord;
import com.ebao.ap99.parent.Page;

public interface ExeceptionContractBizService {
	
	void exceptionJobExecute() throws Exception;
	
	
	Page<ExceptionContractRecord> loadExceptionContract(Long FNYear, String FNQuarter);
	
	Page<ExceptionContractRecord> searchExceptionContract(Long FNYear, String FNQuarter);
	
	Page<ExceptionContractRecord> reviewExceptionContract(ExceptionContractVO exceptionContractVO) throws Exception;
	
	

}
