/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.ebao.ap99.accounting.entity.TRiAcctEstimateItemHis;
import com.ebao.ap99.accounting.model.EntryItemVO;
import com.ebao.ap99.accounting.model.TechnicalResultVO;
import com.ebao.ap99.parent.util.FieldsCollector;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * Date: Jan 12, 2016 8:19:51 PM
 * 
 * @author xiaoyu.yang
 */
public class TRiAcctEstimateItemHisDao extends BaseDao<TRiAcctEstimateItemHis> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Class<TRiAcctEstimateItemHis> getEntityClass() {
        return TRiAcctEstimateItemHis.class;
    }

    public List<TRiAcctEstimateItemHis> loadBusinessDetails(Long contCompId, String entryCode, int cedentYear,
                                                            int cedentQuarter, int status) {
        List<TRiAcctEstimateItemHis> list = null;
        StringBuilder sql = new StringBuilder("");

        sql.append("SELECT *                                                                                ");
        sql.append("  FROM T_RI_ACCT_ESTIMATE_ITEM_HIS T                                                    ");
        sql.append(" WHERE T.ITEM_ID IN                                                                     ");
        sql.append("       (SELECT B.ITEM_ID                                                                ");
        sql.append("          FROM T_RI_ACCT_ESTIMATE      A,                                               ");
        sql.append("               T_RI_ACCT_ESTIMATE_ITEM B,                                               ");
        sql.append("               T_RI_ENTRY_CODE         C                                                ");
        sql.append("         WHERE A.ESTIMATE_ID = B.ESTIMATE_ID                                            ");
        sql.append("           AND A.CONT_SECTION_ID = ?                                                    ");
        sql.append("           AND A.CEDENT_YEAR = ?                                                        ");
        sql.append("           AND A.CEDENT_QUARTER = ?                                                     ");
        sql.append("           AND (C.ENTRY_CODE = ? OR C.ESTIMATE_GROUP = ?)                               ");
        sql.append("           AND B.ENTRY_CODE = C.ENTRY_CODE                                              ");
        sql.append("           AND B.STATUS = ?)                                                            ");
        sql.append("   AND T.CHANGE_REASON IS NOT NULL                                                      ");
        sql.append(" ORDER BY T.INSERT_TIME,T.ENTRY_CODE                                                    ");

        list = this.getJdbcTemplate().query(sql.toString(),
                new Object[] { contCompId, cedentYear, cedentQuarter, entryCode, entryCode, status },
                new BeanPropertyRowMapper<TRiAcctEstimateItemHis>(TRiAcctEstimateItemHis.class));
        return list;
    }

    public List<TRiAcctEstimateItemHis> findListByCondition(TRiAcctEstimateItemHis condition) {
        StringBuilder sqlBuf = new StringBuilder("SELECT t FROM TRiAcctEstimateItemHis t WHERE 1=1");
        FieldsCollector.concatSql(condition, sqlBuf);

        Query query = em.createQuery(sqlBuf.toString());
        FieldsCollector.setParams(condition, query);

        @SuppressWarnings("unchecked")
        List<TRiAcctEstimateItemHis> list = (List<TRiAcctEstimateItemHis>) query.getResultList();
        return list;
    }

    //    public List<EntryItemVO> loadOverviewTable(long level, long conCompId, Date queryDate, int quarterSize) {
    //        List<EntryItemVO> entryItemList = null;
    //        String quarterStr = generateQuarterStr(quarterSize);
    //        StringBuilder sql = new StringBuilder("");
    //
    //        sql.append("WITH LATEST_ITEM AS                                                             ");
    //        sql.append(" (SELECT A.ITEM_ID,                                                             ");
    //        sql.append("         NVL(B.ESTIMATE_GROUP, B.ENTRY_CODE) AS ENTRY_CODE,                     ");
    //        sql.append("         (CASE NVL(B.ESTIMATE_GROUP, B.ENTRY_CODE)                              ");
    //        sql.append("           WHEN B.ENTRY_CODE THEN 1                                             ");
    //        sql.append("           ELSE -1                                                              ");
    //        sql.append("         END) * A.AMOUNT AS AMOUNT                                              ");
    //        sql.append("    FROM T_RI_ACCT_ESTIMATE_ITEM_HIS A, T_RI_ENTRY_CODE B                       ");
    //        sql.append("   WHERE A.ENTRY_CODE = B.ENTRY_CODE                                            ");
    //        sql.append("     AND HIS_ID IN                                                              ");
    //        //load forecast data whose quarterStatus is forecast or UPR
    //        sql.append("         (SELECT HIS_ID                                                         ");
    //        sql.append("            FROM (SELECT T.HIS_ID,                                              ");
    //        sql.append("                 ROW_NUMBER() OVER(PARTITION BY T.ITEM_ID ORDER BY T.INSERT_TIME DESC) RN ");
    //        sql.append("            FROM T_RI_ACCT_ESTIMATE_ITEM_HIS T                                  ");
    //        sql.append("           WHERE EXISTS                                                         ");
    //        sql.append("           (SELECT 1                                                            ");
    //        sql.append("                    FROM T_RI_ACCT_ESTIMATE T1, T_RI_ACCT_ESTIMATE_ITEM T2      ");
    //        sql.append("                   WHERE T1.ESTIMATE_ID = T2.ESTIMATE_ID                        ");
    //        if (level == 1) {
    //            sql.append("                 AND T1.CONT_ID = ?                                         ");
    //        } else {
    //            sql.append("                 AND T1.CONT_SECTION_ID = ?                                 ");
    //        }
    //        sql.append("                     AND T2.STATUS = 1                                          ");
    //        sql.append("                     AND ((T1.STATUS IN (1, 6) AND                              ");
    //        sql.append("                         T2.ENTRY_CODE NOT IN ('EP01', 'EP02')) OR              ");
    //        sql.append("                         T2.ENTRY_CODE IN ('EP01', 'EP02'))                     ");
    //        sql.append("                     AND T2.INSERT_TIME < ? + 1                                 ");
    //        sql.append("                     AND T.ITEM_ID = T2.ITEM_ID))                               ");
    //        sql.append("          WHERE RN = 1                                                          ");
    //        //load Estimated+Reversal if quarterStatus is not forecast or UPR
    //        sql.append("          UNION                                                                 ");
    //        sql.append("          SELECT HIS_ID                                                         ");
    //        sql.append("            FROM (SELECT T.HIS_ID,                                              ");
    //        sql.append("                 ROW_NUMBER() OVER(PARTITION BY T.ITEM_ID ORDER BY T.INSERT_TIME DESC) RN ");
    //        sql.append("            FROM T_RI_ACCT_ESTIMATE_ITEM_HIS T                                  ");
    //        sql.append("           WHERE EXISTS                                                         ");
    //        sql.append("           (SELECT 1                                                            ");
    //        sql.append("                    FROM T_RI_ACCT_ESTIMATE T1, T_RI_ACCT_ESTIMATE_ITEM T2      ");
    //        sql.append("                   WHERE T1.ESTIMATE_ID = T2.ESTIMATE_ID                        ");
    //        if (level == 1) {
    //            sql.append("                 AND T1.CONT_ID = ?                                         ");
    //        } else {
    //            sql.append("                 AND T1.CONT_SECTION_ID = ?                                 ");
    //        }
    //        sql.append("                     AND ((T1.STATUS IN (2, 3, 4) AND T2.STATUS = 3) OR         ");
    //        sql.append("                         (T1.STATUS = 5 AND T2.STATUS IN (3, 4)))               ");
    //        sql.append("                     AND T2.INSERT_TIME < ? + 1                                 ");
    //        sql.append("                     AND T.ITEM_ID = T2.ITEM_ID))                               ");
    //        sql.append("          WHERE RN = 1)                                                         ");
    //        //load actual data which has been recalculated no include Loss Reserve Items
    //        sql.append("  UNION                                                                         ");
    //        sql.append("  SELECT T.ITEM_ID, T.ENTRY_CODE, SUM(AMOUNT) AS AMOUNT                         ");
    //        sql.append("    FROM (SELECT D.ITEM_ID,                                                     ");
    //        sql.append("                 NVL(B.ESTIMATE_GROUP, B.ENTRY_CODE) AS ENTRY_CODE,             ");
    //        sql.append("                 (CASE NVL(B.ESTIMATE_GROUP, B.ENTRY_CODE)                      ");
    //        sql.append("                   WHEN B.ENTRY_CODE THEN                                       ");
    //        sql.append("                    1                                                           ");
    //        sql.append("                   ELSE                                                         ");
    //        sql.append("                    -1                                                          ");
    //        sql.append("                 END) * ROUND(A.AMOUNT / A.TO_MAIN_CURR_RATE, 2) AS AMOUNT      ");
    //        sql.append("            FROM T_RI_ACCT_ESTIMATE_TRANS A,                                    ");
    //        sql.append("                 T_RI_ENTRY_CODE          B,                                    ");
    //        sql.append("                 T_RI_ACCT_ESTIMATE_ITEM  D                                     ");
    //        sql.append("           WHERE A.ENTRY_CODE = B.ENTRY_CODE                                    ");
    //        if (level == 1) {
    //            sql.append("         AND A.CONT_ID = ?                                                  ");
    //        } else {
    //            sql.append("         AND A.SECTION_ID = ?                                               ");
    //        }
    //        sql.append("             AND D.STATUS = 1                                                   ");
    //        sql.append("             AND A.CALC_FLAG = 'Y'                                              ");
    //        sql.append("             AND A.ESTIMATE_ID = D.ESTIMATE_ID                                  ");
    //        sql.append("             AND A.ENTRY_CODE = D.ENTRY_CODE                                    ");
    //        sql.append("             AND A.ENTRY_CODE NOT IN (4121, 4122)                               ");
    //        sql.append("             AND A.INSERT_TIME < ? + 1) T                                       ");
    //        sql.append("   GROUP BY T.ITEM_ID, T.ENTRY_CODE                                             ");
    //        //load actual data which has been recalculated include Loss Reserve Items
    //        sql.append("  UNION                                                                         ");
    //        sql.append("  SELECT B.ITEM_ID,                                                             ");
    //        sql.append("         '4122' AS ENTRY_CODE,                                                  ");
    //        sql.append("         SUM((CASE A.ENTRY_CODE                                                 ");
    //        sql.append("               WHEN '3000' THEN 1                                               ");
    //        sql.append("               WHEN '3030' THEN 1                                               ");
    //        sql.append("               WHEN '3040' THEN 1                                               ");
    //        sql.append("               WHEN '4122' THEN 1                                               ");
    //        sql.append("               WHEN '4142' THEN 1                                               ");
    //        sql.append("               ELSE -1                                                          ");
    //        sql.append("             END) * ROUND(A.AMOUNT / A.TO_MAIN_CURR_RATE, 2)) AS AMOUNT         ");
    //        sql.append("    FROM T_RI_ACCT_ESTIMATE_TRANS A, T_RI_ACCT_ESTIMATE_ITEM B                  ");
    //        sql.append("   WHERE A.ENTRY_CODE IN (3000, 3030, 3040, 4121, 4122, 4141, 4142)             ");
    //        if (level == 1) {
    //            sql.append("         AND A.CONT_ID = ?                                                  ");
    //        } else {
    //            sql.append("         AND A.SECTION_ID = ?                                               ");
    //        }
    //        sql.append("     AND A.ESTIMATE_ID = B.ESTIMATE_ID                                          ");
    //        sql.append("     AND B.ENTRY_CODE = '4122'                                                  ");
    //        sql.append("     AND B.STATUS = 1                                                           ");
    //        sql.append("     AND A.CALC_FLAG = 'Y'                                                      ");
    //        sql.append("     AND A.INSERT_TIME < ? + 1                                                  ");
    //        sql.append("   GROUP BY B.ITEM_ID)                                                          ");
    //        //handle collection data by each quarter
    //        sql.append("SELECT *                                                                        ");
    //        sql.append("  FROM (SELECT LATEST_ITEM.ENTRY_CODE AS ITEM,                                  ");
    //        sql.append("               T1.QUARTER_SEQ,                                                  ");
    //        sql.append("               LATEST_ITEM.AMOUNT                                               ");
    //        sql.append("          FROM T_RI_ACCT_ESTIMATE      T1,                                      ");
    //        sql.append("               T_RI_ACCT_ESTIMATE_ITEM T2,                                      ");
    //        sql.append("               LATEST_ITEM                                                      ");
    //        sql.append("         WHERE T1.ESTIMATE_ID = T2.ESTIMATE_ID                                  ");
    //        sql.append("           AND T2.ITEM_ID = LATEST_ITEM.ITEM_ID) A PIVOT(SUM(AMOUNT) FOR QUARTER_SEQ IN("
    //                + quarterStr + "))       ");
    //
    //        entryItemList = this.getJdbcTemplate().query(sql.toString(),
    //                new Object[] { conCompId, queryDate, conCompId, queryDate, conCompId, queryDate, conCompId, queryDate },
    //                new BeanPropertyRowMapper<EntryItemVO>(EntryItemVO.class));
    //        return entryItemList;
    //    }

    public List<EntryItemVO> loadViewTable(long level, long conCompId, int estimateStatus, Date queryDate,
                                           int quarterSize) {
        List<EntryItemVO> entryItemList = null;
        String quarterStr = generateQuarterStr(quarterSize);
        StringBuilder sql = new StringBuilder("");

        sql.append("WITH LATEST_ITEM AS                                                       ");
        sql.append(" (SELECT A.ITEM_ID,                                                       ");
        sql.append("         NVL(B.ESTIMATE_GROUP, B.ENTRY_CODE) AS ENTRY_CODE,               ");
        sql.append("         (CASE NVL(B.ESTIMATE_GROUP, B.ENTRY_CODE)                        ");
        sql.append("           WHEN B.ENTRY_CODE THEN 1                                       ");
        sql.append("           ELSE -1                                                        ");
        sql.append("         END) * A.AMOUNT AS AMOUNT                                        ");
        sql.append("    FROM T_RI_ACCT_ESTIMATE_ITEM_HIS A, T_RI_ENTRY_CODE B                 ");
        sql.append("   WHERE A.ENTRY_CODE = B.ENTRY_CODE                                      ");
        sql.append("     AND HIS_ID IN                                                        ");
        sql.append("         (SELECT MAX(T3.HIS_ID)                                           ");
        sql.append("            FROM T_RI_ACCT_ESTIMATE          T1,                          ");
        sql.append("                 T_RI_ACCT_ESTIMATE_ITEM     T2,                          ");
        sql.append("                 T_RI_ACCT_ESTIMATE_ITEM_HIS T3                           ");
        sql.append("           WHERE T1.ESTIMATE_ID = T2.ESTIMATE_ID                          ");
        sql.append("             AND T2.ITEM_ID = T3.ITEM_ID                                  ");
        if (level == 1) {
            sql.append("         AND T1.CONT_ID = ?                                           ");
        } else {
            sql.append("         AND T1.CONT_SECTION_ID = ?                                   ");
        }
        sql.append("             AND T3.STATUS = ?                                            ");
        sql.append("             AND T3.INSERT_TIME < ? + 1                                   ");
        sql.append("           GROUP BY T3.ITEM_ID, T3.ENTRY_CODE, T1.CONT_SECTION_ID))       ");
        sql.append("SELECT *                                                                  ");
        sql.append("  FROM (SELECT LATEST_ITEM.ENTRY_CODE AS ITEM,                            ");
        sql.append("               T1.QUARTER_SEQ,                                            ");
        sql.append("               LATEST_ITEM.AMOUNT                                         ");
        sql.append("          FROM T_RI_ACCT_ESTIMATE      T1,                                ");
        sql.append("               T_RI_ACCT_ESTIMATE_ITEM T2,                                ");
        sql.append("               LATEST_ITEM                                                ");
        sql.append("         WHERE T1.ESTIMATE_ID = T2.ESTIMATE_ID                            ");
        sql.append("           AND T2.ITEM_ID = LATEST_ITEM.ITEM_ID) A PIVOT(SUM(AMOUNT) FOR QUARTER_SEQ IN("
                + quarterStr + "))");

        entryItemList = this.getJdbcTemplate().query(sql.toString(),
                new Object[] { conCompId, estimateStatus, queryDate },
                new BeanPropertyRowMapper<EntryItemVO>(EntryItemVO.class));
        return entryItemList;
    }

    private String generateQuarterStr(int quarterSize) {
        StringBuilder str = new StringBuilder("");
        for (int i = 1; i <= quarterSize; i++) {
            str.append(i + " AS \"CEDENT_Q" + i + "\",");
        }
        str.deleteCharAt(str.length() - 1);
        return str.toString();
    }

    public List<TechnicalResultVO> loadTechnicalResultList(long sectionId) {
        List<TechnicalResultVO> list = null;
        StringBuilder sql = new StringBuilder("");

        sql.append("SELECT DISTINCT TO_CHAR(T3.INSERT_TIME, 'DD/MM/YYYY') AS INSERT_TIME,    ");
        sql.append("       T4.CONTRACT_CODE,                                                 ");
        sql.append("       T3.CHANGE_REASON AS ACCOUNT_TEXT,                                 ");
        sql.append("       T5.SECTION_NAME AS SECTION,                                       ");
        sql.append("       T3.ENTRY_CODE,                                                    ");
        sql.append("       T6.ENTRY_CODE_NAME,                                               ");
        sql.append(
                "          DECODE(T3.STATUS,1,'Forecast',2,'Estimated',3,'Estimation Reversal',4,'Actual') AS INDICATER,");
        sql.append("       T1.CEDENT_YEAR || 'Q' || T1.CEDENT_QUARTER AS CEDENT_QUARTER,     ");
        sql.append("       T1.CURRENCY,                                                      ");
        sql.append("       DECODE(T6.CASH_BALANCE, 1, T3.AMOUNT, 0) AS CASH_BALANCE,         ");
        sql.append("       T3.AMOUNT AS TECHNICAL_RESULT                                     ");
        sql.append("  FROM T_RI_ACCT_ESTIMATE          T1,                                   ");
        sql.append("       T_RI_ACCT_ESTIMATE_ITEM     T2,                                   ");
        sql.append("       T_RI_ACCT_ESTIMATE_ITEM_HIS T3,                                   ");
        sql.append("       T_RI_CONTRACT_INFO          T4,                                   ");
        sql.append("       T_RI_CONT_SECTION_INFO      T5,                                   ");
        sql.append("       T_RI_ENTRY_CODE             T6                                    ");
        sql.append(" WHERE T1.CONT_SECTION_ID = ?                                            ");
        sql.append("   AND T1.ESTIMATE_ID = T2.ESTIMATE_ID                                   ");
        sql.append("   AND T2.ITEM_ID = T3.ITEM_ID                                           ");
        sql.append("   AND T1.CONT_ID = T4.CONT_COMP_ID                                      ");
        sql.append("   AND T1.CONT_SECTION_ID = T5.CONT_COMP_ID                              ");
        sql.append("   AND T3.ENTRY_CODE = T6.ENTRY_CODE                                     ");
        sql.append("   AND T3.CHANGE_REASON IS NOT NULL                                      ");

        list = this.getJdbcTemplate().query(sql.toString(), new Object[] { sectionId },
                new BeanPropertyRowMapper<TechnicalResultVO>(TechnicalResultVO.class));
        return list;

    }

}
