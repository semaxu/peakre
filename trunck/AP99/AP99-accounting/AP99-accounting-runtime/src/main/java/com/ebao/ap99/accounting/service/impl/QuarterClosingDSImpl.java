/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.accounting.constant.ClosingStatusEnum;
import com.ebao.ap99.accounting.dao.TRiAcctEstimateDao;
import com.ebao.ap99.accounting.dao.TRiAcctQuarterClosingDao;
import com.ebao.ap99.accounting.entity.TRiAcctQuarterClosing;
import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.context.AppContext;

/**
 * Date: Jan 5, 2016 8:15:57 PM
 * 
 * @author xiaoyu.yang
 */
public class QuarterClosingDSImpl {

    @Autowired
    private TRiAcctEstimateDao tRiAcctEstimateDao;

    @Autowired
    private TRiAcctQuarterClosingDao tRiAcctQuarterClosingDao;

    @Autowired
    private AccountingServiceImpl accountingServiceImpl;

    @Autowired
    private EstimateDSImpl estimateDSImpl;

    public TRiAcctQuarterClosing loadQuarterClosing() throws Exception {
        accountingServiceImpl.refreshClosingInfo();
        return tRiAcctQuarterClosingDao.getCurrentQuarter();
    }

    public void setCutOffDate(String fnQuarter, Date cutoffDate) throws Exception {
        accountingServiceImpl.refreshClosingInfo();
        if (accountingServiceImpl.currentFinancialQuarter().toString().equals(fnQuarter)) {
            TRiAcctQuarterClosing closingRecord = tRiAcctQuarterClosingDao.findByQuarter(new YearQuarter(fnQuarter));
            closingRecord.setClosingDate(cutoffDate);
            tRiAcctQuarterClosingDao.merge(closingRecord);
            accountingServiceImpl.refreshClosingInfo();
        } else {
            throw new RuntimeException("Time out");
        }
    }

    public void startClosing(String fnQuarter) throws Exception {
        //TODO L: changed to run batch job @ tonight
        accountingServiceImpl.refreshClosingInfo();
        if (accountingServiceImpl.currentFinancialQuarter().toString().equals(fnQuarter)) {
            TRiAcctQuarterClosing closingRecord = tRiAcctQuarterClosingDao.findByQuarter(new YearQuarter(fnQuarter));
            closingRecord.setStartDate(AppContext.getSysDate());
            closingRecord.setClosingStatus(ClosingStatusEnum.Closing.getValue());
            tRiAcctQuarterClosingDao.merge(closingRecord);
            accountingServiceImpl.refreshClosingInfo();
        } else {
            throw new RuntimeException("Time out");
        }
    }

    public void endClosing(String fnQuarter) throws Exception {
        accountingServiceImpl.refreshClosingInfo();
        if (accountingServiceImpl.currentFinancialQuarter().toString().equals(fnQuarter)) {
            YearQuarter currentQuarter = new YearQuarter(fnQuarter);
            TRiAcctQuarterClosing currentQuarterClosing = tRiAcctQuarterClosingDao.findByQuarter(currentQuarter);
            if (currentQuarterClosing.getClosingStatus().equals(ClosingStatusEnum.Closing.getValue())) {
                currentQuarterClosing.setClosedDate(AppContext.getSysDate());
                currentQuarterClosing.setClosingStatus(ClosingStatusEnum.Closed.getValue());
                tRiAcctQuarterClosingDao.merge(currentQuarterClosing);
                openNextQuarter(currentQuarter);
                accountingServiceImpl.refreshClosingInfo();
                //estimateDSImpl.loadSOAofCurrentQuarter();
            } else {
                throw new RuntimeException("Time out");
            }
            tRiAcctEstimateDao.convertEstimatingToEstimated();
        } else {
            throw new RuntimeException("Time out");
        }
    }

    public Page<TRiAcctQuarterClosing> findCurrentPage(Page<TRiAcctQuarterClosing> page) {
        TRiAcctQuarterClosing condition = page.getCondition();
        condition.setClosingStatus(ClosingStatusEnum.Closed.getValue());
        Long results = tRiAcctQuarterClosingDao.getQuarterClosingCount(condition);
        List<TRiAcctQuarterClosing> list = tRiAcctQuarterClosingDao.getCurrentPage(condition, page.getPageIndex(),
                page.getCountPerPage());
        page.setRows(list);
        Long pageCount = results % page.getCountPerPage() == 0 ? results / page.getCountPerPage()
                : results / page.getCountPerPage() + 1;
        page.setPageCount(pageCount);
        if (results < page.getCountPerPage()) {
            page.setCountPerPage(results.intValue());
        }
        return page;
    }

    /**
     * Create a New Record of Next Quarter
     */
    private void openNextQuarter(YearQuarter quarter) {
        TRiAcctQuarterClosing nextQuarterSetting = new TRiAcctQuarterClosing();
        nextQuarterSetting.setClosingStatus(ClosingStatusEnum.Open.getValue());
        YearQuarter nextQuarter = quarter.nextQuarter();
        nextQuarterSetting.setFinancialYear(nextQuarter.getYear());
        nextQuarterSetting.setFinancialQuarter(nextQuarter.getQuarter());

        nextQuarterSetting.setClosingDate(nextQuarter.getClosingDate());
        tRiAcctQuarterClosingDao.insert(nextQuarterSetting);
    }
}
