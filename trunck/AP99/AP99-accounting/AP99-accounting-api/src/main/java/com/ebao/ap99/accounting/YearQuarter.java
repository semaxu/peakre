package com.ebao.ap99.accounting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class YearQuarter {
    private int  year;
    private int  quarter;
    private Date periodFrom;
    private Date periodTo;

    public YearQuarter(int year, int quarter) {
        assert(quarter < 4 && quarter > 0) : "Quarter must be in (1,2,3,4)";

        this.year = year;
        this.quarter = quarter;
    }

    public YearQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        this.year = cal.get(Calendar.YEAR);
        this.quarter = (cal.get(Calendar.MONTH) / 3) + 1;
        this.periodFrom = cal.getTime();
        cal.add(Calendar.MONTH, 3);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        this.periodTo = cal.getTime();
    }

    /**
     * yyyyQ[1,2,3,4]
     * 
     * @param qtString
     */
    public YearQuarter(String qtString) {
        //TODO: Check format by regular expression
        String[] str = qtString.split("Q");
        this.year = Integer.parseInt(str[0]);
        this.quarter = Integer.parseInt(str[1]);
    }

    public String toString() {
        return this.year + "Q" + this.quarter;
    }

    public Date getClosingDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.year);
        cal.set(Calendar.MONTH, this.quarter * 3 - 1);
        cal.set(Calendar.DAY_OF_MONTH, 5);
        return cal.getTime();
    }

    /**
     * move Quarter by qNumber
     * 
     * @param qNumber
     * @return
     */
    public YearQuarter add(int qNumber) {
        int totalQuarter = this.year * 4 + this.quarter + qNumber;
        int y = (totalQuarter - 1) / 4;
        int q = (totalQuarter % 4) == 0 ? 4 : (totalQuarter % 4);
        return new YearQuarter(y, q);
    }

    public YearQuarter previousQuarter() {
        return this.add(-1);
    }

    public YearQuarter nextQuarter() {
        return this.add(1);
    }

    /**
     * Difference between certain quarter and this.
     * 
     * @param yQuarter
     * @return
     */
    public int minus(YearQuarter yQuarter) {
        return (this.year * 4 + this.quarter) - (yQuarter.year * 4 + yQuarter.quarter);
    }

    public static List<YearQuarter> calcQuarterByPOI(Date fromDate, Date toDate) {
        List<YearQuarter> qList = new ArrayList<YearQuarter>();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(fromDate);
        c2.setTime(toDate);
        //c2.add(Calendar.MONTH, 12);
        while (c1.before(c2)) {
            YearQuarter curQuarter = new YearQuarter(c1.getTime());
            qList.add(curQuarter);
            c1.add(Calendar.MONTH, 3);
        }
        return qList;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof YearQuarter) {
            YearQuarter y = (YearQuarter) obj;
            if (y.year == this.year && y.quarter == this.quarter) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + quarter;
        result = prime * result + year;
        return result;
    }

}
