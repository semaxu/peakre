package com.ebao.ap99.accounting.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.AccountingService;
import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.accounting.constant.ClosingStatusEnum;
import com.ebao.ap99.accounting.dao.TRiAcctEstimateDao;
import com.ebao.ap99.accounting.dao.TRiAcctQuarterClosingDao;
import com.ebao.ap99.accounting.entity.TRiAcctQuarterClosing;
import com.ebao.ap99.accounting.service.impl.EstimateDSImpl;
import com.ebao.ap99.accounting.util.ChangeReasonTool;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.parent.context.AppContext;

public class CutoffJob {

    private Logger logger = LoggerFactory.getLogger(CutoffJob.class);

    @Autowired
    private AccountingService accountingService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private EstimateDSImpl estimateDSImpl;

    @Autowired
    private TRiAcctEstimateDao tRiAcctEstimateDao;

    @Autowired
    private TRiAcctQuarterClosingDao tRiAcctQuarterClosingDao;

    public void jobRun() throws Exception {
        //if no exception treaty start
        //change FN quarter to 'Closing'
        startClosing();
        //Re-calculate Forecast
        YearQuarter fnQuarter = accountingService.currentFinancialQuarter();
        ChangeReasonTool.getChangeReason().set("FY" + fnQuarter.toString() + " cut-off batch job");
        List<Long> sectionIds = queryForecastSection(fnQuarter);
        for (Long sectionId : sectionIds) {
            if (!contractService.isCleanCutContractSection(sectionId)) {
                processCutoff(sectionId);
            }
        }
    }

    private void startClosing() throws Exception {
        TRiAcctQuarterClosing currentQuarter = tRiAcctQuarterClosingDao.getCurrentQuarter();
        currentQuarter.setClosingStatus(ClosingStatusEnum.Closing.getValue());
        if (currentQuarter.getStartDate() == null) {
            currentQuarter.setStartDate(AppContext.getSysDate());
        }
        tRiAcctQuarterClosingDao.insertOrUpdate(currentQuarter);
        accountingService.refreshClosingInfo();
    }

    private List<Long> queryForecastSection(YearQuarter currentFinancialQuarter) {
        List<Long> sectionIdList = tRiAcctEstimateDao.queryForecastSectionIdList(currentFinancialQuarter.getYear(),
                currentFinancialQuarter.getQuarter());
        return sectionIdList;
    }

    private void processCutoff(Long sectionId) {
        try {
            estimateDSImpl.processCutoff(sectionId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(
                    "Error accured during processing cutoff of sectionId:" + sectionId + " ,info:" + e.getMessage());
        }
    }

}
