package com.ebao.ap99.accounting.batch;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.model.RevaluateInfoModel;
import com.ebao.ap99.accounting.model.RevaluationDetailModel;
import com.ebao.ap99.accounting.model.RevaluationValidModel;
import com.ebao.ap99.accounting.service.impl.RevaluationReportServiceImpl;
import com.ebao.ap99.parent.Page;



public class RevaluationJob {
	
	@Autowired
	private RevaluationReportServiceImpl revaluationReportService;
	public void executeRevaluation() throws Exception {
		 revaluationReportService.executeRevaluation();
	}
	
	public Page<RevaluateInfoModel> revaluationSearch(String FNYear, String FNQuarter,String status) throws ParseException {
		return revaluationReportService.revaluationSearch(FNYear,FNQuarter,status);
	}
	
	public Page<RevaluateInfoModel> revaluationView() {
		return revaluationReportService.revaluationView();
	}
	
	public RevaluationDetailModel revaluationDetailSeach() throws Exception {
	    return	revaluationReportService.revaluationDetailSeach();
	}
	
	public RevaluationValidModel validBefore() throws Exception {
	    return	revaluationReportService.validBefore();
	}
	
	

}
