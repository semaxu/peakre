package com.ebao.ap99.accounting.estimation;

import java.util.Date;

import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.unicorn.platform.foundation.utils.json.JSONUtils;

public class CedentYearQuarter extends YearQuarter {
    private Date periodFrom;
    private Date periodTo;

    public CedentYearQuarter(int year, int quarter) {
        super(year, quarter);
    }

    /**
     * Contract quarter always start from UY+Q1
     * 
     * @param fromDate
     * @param toDate
     * @return
     */
    //    public static List<CedentYearQuarter> calcQuarterByPOI(Date fromDate, Date toDate) {
    //
    //        int year = fromDate.getYear() + 1900;
    //
    //        List<CedentYearQuarter> qList = new ArrayList<CedentYearQuarter>();
    //        YearQuarter quarter = null;
    //        Date quarterStart = fromDate;
    //        while (quarterStart.before(toDate)) {
    //            if (quarter == null) {
    //                quarter = new YearQuarter(year, 1);
    //            } else {
    //                quarter = quarter.nextQuarter();
    //            }
    //
    //            CedentYearQuarter curQuarter = new CedentYearQuarter(quarter.getYear(), quarter.getQuarter());
    //
    //            Date quarterEnd = DateUtils.addMonths(quarterStart, 3);
    //            quarterEnd = DateUtils.addDays(quarterEnd, -1);
    //            curQuarter.setPeriodFrom(quarterStart);
    //            curQuarter.setPeriodTo(quarterEnd);
    //
    //            qList.add(curQuarter);
    //
    //            quarterStart = DateUtils.addMonths(quarterStart, 3);
    //        }
    //        return qList;
    //    }

    public Date getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(Date periodFrom) {
        this.periodFrom = periodFrom;
    }

    public Date getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(Date periodTo) {
        this.periodTo = periodTo;
    }

    @Override
    public String toString() {
        return JSONUtils.toJSON(this);
    }

}
