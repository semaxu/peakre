/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.ebao.ap99.accounting.model.IBNRrecord;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * Date: Jan 4, 2016 3:00:16 PM
 * 
 * @author xiaoyu.yang
 */
public class IBNRrecordDao extends BaseDao<IBNRrecord> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Class<IBNRrecord> getEntityClass() {
        return IBNRrecord.class;
    }

    public List<IBNRrecord> findBySegmentId(Long segmentId) {
        StringBuilder eachQuarter = new StringBuilder();
        //        sql.append(
        //                "SELECT * FROM ("
        //                + "SELECT UW_YEAR, "
        //                + "FN_YEAR || 'Q' || FN_QUARTER AS FN_QUARTER, "
        //                + "AMOUNT "
        //                + "FROM T_RI_ACC_IBNR_INFO WHERE SEGMENT_ID = ?) "
        //                + "PIVOT(MAX(AMOUNT) FOR UW_YEAR IN(2013,2014,2015,2016))  "
        //                + "ORDER BY FN_QUARTER DESC");

        eachQuarter.append("SELECT FN_QUARTER");
        Calendar cal = Calendar.getInstance();
        cal.setTime(AppContext.getSysDate());
        int currentYear = cal.get(Calendar.YEAR);
        int uwYear = 2013;
        //add SQL columns for each UW Year
        while (uwYear <= currentYear) {
            eachQuarter.append(",MAX(DECODE(UW_YEAR, " + uwYear + ", AMOUNT, NULL)) UY" + uwYear);
            uwYear++;
        }
        eachQuarter
                .append(" FROM T_RI_ACCT_IBNR_INFO WHERE SEGMENT_ID = ? and status = '1' GROUP BY FN_QUARTER ORDER BY FN_QUARTER DESC");
        List<IBNRrecord> list = this.getJdbcTemplate().query(eachQuarter.toString(),
                new Object[] { segmentId.toString() }, new BeanPropertyRowMapper<IBNRrecord>(IBNRrecord.class));

        //        sql = new StringBuilder("SELECT ");
        //        uwYear = 2013;
        //        while (uwYear <= currentYear) {
        //            sql.append("MAX(DECODE(UW_YEAR, " + uwYear + ", TOTAL, NULL)) UY" + uwYear + ",");
        //            uwYear++;
        //        }
        //        sql.deleteCharAt(sql.length() - 1);
        //        sql.append(" FROM (SELECT UW_YEAR UW_YEAR," + " SUM(AMOUNT) TOTAL"
        //                + " FROM T_RI_ACCT_IBNR_INFO WHERE SEGMENT_ID = ?" + " GROUP BY UW_YEAR)");
        //        List<IbnrRecord> total = this.getJdbcTemplate().query(sql.toString(), new Object[] { segmentId.toString() },
        //                new BeanPropertyRowMapper<IbnrRecord>(IbnrRecord.class));
        //        if (!total.isEmpty()) {
        //            IbnrRecord temp = total.get(0);
        //            temp.setFnQuarter("TOTAL");
        //            list.add(temp);
        //        }
        return list;
    }
}
