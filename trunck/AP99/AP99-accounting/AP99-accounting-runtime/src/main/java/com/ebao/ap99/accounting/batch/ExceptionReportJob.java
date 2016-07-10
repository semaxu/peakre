package com.ebao.ap99.accounting.batch;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.AccountingService;
import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.accounting.service.impl.ExeceptionContractBizServiceImpl;
import com.ebao.ap99.parent.context.AppContext;

//N days before cut-off
public class ExceptionReportJob {
    @Autowired
    private ExeceptionContractBizServiceImpl execeptionContractBizServiceImpl;
    @Autowired
    private AccountingService                accountingService;

    private static int DAYS_BEFORE_CUT_OFF = 2;

    public void executeExceptionReport() throws Exception {
        YearQuarter quarterClosing = accountingService.currentFinancialQuarter();
        Calendar cal = Calendar.getInstance();
        cal.setTime(AppContext.getSysDate());
        cal.add(Calendar.DATE, DAYS_BEFORE_CUT_OFF);
        //	if (cal.getTime().compareTo(quarterClosing.getClosingDate())>=0) {
        if (true) {
            execeptionContractBizServiceImpl.exceptionJobExecute();
        }
    }

}
