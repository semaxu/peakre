/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.restful;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.restlet.resource.ServerResource;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.UI.model.ExceptionContractVO;
import com.ebao.ap99.accounting.batch.CutoffJob;
import com.ebao.ap99.accounting.batch.ExceptionReportJob;
import com.ebao.ap99.accounting.batch.RevaluationJob;
import com.ebao.ap99.accounting.convertor.QuarterClosingConvertor;
import com.ebao.ap99.accounting.entity.TRiAcctQuarterClosing;
import com.ebao.ap99.accounting.model.TRiAcctQuarterClosingVO;
import com.ebao.ap99.accounting.service.impl.ExeceptionContractBizServiceImpl;
import com.ebao.ap99.accounting.service.impl.QuarterClosingDSImpl;
import com.ebao.ap99.accounting.util.AccountingCst;
import com.ebao.ap99.parent.Page;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;

/**
 * Date: Jan 6, 2016 9:49:05 AM
 * 
 * @author xiaoyu.yang
 */
@Module(com.ebao.ap99.parent.constant.Module.ACCOUNTING)
@Path("/closing")
public class ClosingRestfulService extends ServerResource {

    @Autowired
    private QuarterClosingDSImpl quarterClosingDSImpl;

    @Autowired
    private ExeceptionContractBizServiceImpl execeptionContractBizServiceImpl;

    @Autowired
    ExceptionReportJob exceptionReportJob;

    @Autowired
    private RevaluationJob revaluationJob;

    @Autowired
    private CutoffJob cutoffJob;

    /**
     * Get current closingQuarter info
     * 
     * @return
     */
    @GET
    @Path("/load")
    public TRiAcctQuarterClosingVO loadClosingInfo() throws Exception {
        return QuarterClosingConvertor.convertToClosingDTO(quarterClosingDSImpl.loadQuarterClosing());
    }

    /**
     * Get closing History
     * 
     * @param pageIndex
     * @param countPerPage
     * @return ClosingHistory
     */
    @GET
    @Path("/page")
    public Page<TRiAcctQuarterClosingVO> getPageList(@QueryParam("PageIndex") int pageIndex,
                                                     @QueryParam("CountPerPage") int countPerPage) throws Exception {
        Page<TRiAcctQuarterClosingVO> retPage = new Page<TRiAcctQuarterClosingVO>();
        Page<TRiAcctQuarterClosing> page = new Page<TRiAcctQuarterClosing>();
        page.setPageIndex(pageIndex);
        page.setCountPerPage(countPerPage);
        page.setCondition(new TRiAcctQuarterClosing());
        page = quarterClosingDSImpl.findCurrentPage(page);
        retPage = QuarterClosingConvertor.convertToClosingDTOPage(page);
        return retPage;
    }

    /**
     * Set Cut-Off Date of current quarter
     * 
     * @param CutOffDate
     * @throws Exception
     */
    @GET
    @Path("/setCutOffDate")
    public Map<String, String> setCutOffDate(@QueryParam("FnQuarter") String fnQuarter,
                                             @QueryParam("CutoffDate") String cutoffDate) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_FAIL);
        quarterClosingDSImpl.setCutOffDate(fnQuarter, QuarterClosingConvertor.sdf.parse(cutoffDate));
        map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_SUCCESS);
        return map;
    }

    /**
     * Set startDate of current quarter
     */
    @GET
    @Path("/startClosing/{fnQuarter}")
    public Map<String, String> startClosing(@PathParam("fnQuarter") String fnQuarter) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_FAIL);
        quarterClosingDSImpl.startClosing(fnQuarter);
        cutoffJob.jobRun();
        map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_SUCCESS);
        return map;
    }

    /**
     * Set closedDate of current quarter
     * 
     * @throws Exception
     */
    @GET
    @Path("/endClosing/{fnQuarter}")
    public Map<String, String> endClosing(@PathParam("fnQuarter") String fnQuarter) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_FAIL);
        quarterClosingDSImpl.endClosing(fnQuarter);
        map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_SUCCESS);
        return map;
    }

    @GET
    @Path("/exceptionJobExecute")
    public void exceptionJobExecute() throws Exception {
        exceptionReportJob.executeExceptionReport();
    }

    @GET
    @Path("/exceptionSearch/{FNYear}/{FNQuarter}")
    public Object exceptionSearch(@PathParam("FNYear") Long FNYear, @PathParam("FNQuarter") String FNQuarter) {
        return execeptionContractBizServiceImpl.searchExceptionContract(FNYear, FNQuarter);
    }

    @GET
    @Path("/exceptionContractLoad/{FNYear}/{FNQuarter}")
    public Object exceptionContractLoad(@PathParam("FNYear") Long FNYear, @PathParam("FNQuarter") String FNQuarter) {
        return execeptionContractBizServiceImpl.loadExceptionContract(FNYear, FNQuarter);
    }

    @POST
    @Path("/exceptionContractReview")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object exceptionContractReview(ExceptionContractVO exceptionContractVO) throws Exception {
        return execeptionContractBizServiceImpl.reviewExceptionContract(exceptionContractVO);
    }

    @GET
    @Path("/revaluateJobExecute")
    public void revaluateJobExecute() throws Exception {
        //	JobExecution jobExecution =jobLauncher.run(revaluationTaskJob, new JobParameters());
        //	return jobExecution.getStatus();
        revaluationJob.executeRevaluation();
    }

    @GET
    @Path("/revaluationSearch/{FNYear}/{FNQuarter}/{status}")
    public Object revaluationSearch(@PathParam("FNYear") String FNYear, @PathParam("FNQuarter") String FNQuarter,
                                    @PathParam("status") String status) throws ParseException {
        return revaluationJob.revaluationSearch(FNYear, FNQuarter, status);
    }

    @GET
    @Path("/revaluateResultView")
    public Object revaluateResultView() throws Exception {
        return revaluationJob.revaluationView();
    }

    @GET
    @Path("/revaluationDetailSeach")
    public Object revaluationDetailSeach() throws Exception {
        return revaluationJob.revaluationDetailSeach();
    }

    @GET
    @Path("/validBefore")
    public Object validBefore() throws Exception {
        return revaluationJob.validBefore();
    }
}
