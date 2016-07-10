package com.ebao.ap99.accounting;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

public class YearQuarterTestCase {

    YearQuarter baseQuarter = new YearQuarter(2015, 1);

    @Test
    public void testConstructor() {
        YearQuarter q1 = new YearQuarter(2015, 4);
        assertEquals(q1.toString(), "2015Q4");

        q1 = new YearQuarter(new Date());
        assertEquals(q1.toString(), "2016Q1");
    }

    @Test
    public void testAdd() {
        YearQuarter q1 = new YearQuarter(2015, 3);
        assertEquals(q1.add(1).toString(), "2015Q4");
        assertEquals(q1.add(2).toString(), "2016Q1");
        assertEquals(q1.add(-2).toString(), "2015Q1");
        assertEquals(q1.add(-3).toString(), "2014Q4");
    }

    @Test
    public void testNextQuarter() {
        assertEquals(this.baseQuarter.nextQuarter().toString(), "2015Q2");
        assertEquals(this.baseQuarter.nextQuarter().nextQuarter().toString(), "2015Q3");
        assertEquals(this.baseQuarter.nextQuarter().nextQuarter().nextQuarter().toString(), "2015Q4");
        assertEquals(this.baseQuarter.nextQuarter().nextQuarter().nextQuarter().nextQuarter().toString(), "2016Q1");
    }

    @Test
    public void testMinus() {
        YearQuarter q = new YearQuarter(2015, 2);
        assertEquals(q.minus(baseQuarter), 1);

        q = new YearQuarter(2016, 2);
        assertEquals(q.minus(baseQuarter), 5);
    }

    @Test
    public void testPOIConstructor() throws ParseException {
        //		String[] pattern = {"yyyyMMdd"};
        //		String dateStart = "20161129";
        //		String dateEnd = "20171231";
        //		List<CedentYearQuarter> qList = CedentYearQuarter.calcQuarterByPOI(DateUtils.parseDate(dateStart, pattern), DateUtils.parseDate(dateEnd, pattern));
        //		for (CedentYearQuarter q: qList)
        //			System.out.println(q);
    }

}
