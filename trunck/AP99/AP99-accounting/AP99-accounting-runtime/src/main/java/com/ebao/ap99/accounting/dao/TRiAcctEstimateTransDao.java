/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.ebao.ap99.accounting.entity.TRiAcctEstimateItem;
import com.ebao.ap99.accounting.entity.TRiAcctEstimateTrans;
import com.ebao.ap99.accounting.model.EntryItemVO;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * Date: Apr 26, 2016 10:06:13 AM
 * 
 * @author xiaoyu.yang
 */
public class TRiAcctEstimateTransDao extends BaseDao<TRiAcctEstimateTrans> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Class<TRiAcctEstimateTrans> getEntityClass() {
        return TRiAcctEstimateTrans.class;
    }

    public List<TRiAcctEstimateTrans> loadTransactionDetail(Long sectionId, Integer cedentYear, Integer cedentQuarter,
                                                            String entryCode) {
        List<TRiAcctEstimateTrans> actualItems = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT A.*                                                             ");
        sql.append("  FROM T_RI_ACCT_ESTIMATE_TRANS A,T_RI_ENTRY_CODE B                    ");
        sql.append(" WHERE A.SECTION_ID = ?                                                ");
        sql.append("   AND A.CEDENT_YEAR = ?                                               ");
        sql.append("   AND A.CEDENT_QUARTER = ?                                            ");
        sql.append("   AND (B.ENTRY_CODE= ? OR B.ESTIMATE_GROUP = ?)                       ");
        sql.append("   AND A.ENTRY_CODE = B.ENTRY_CODE                                     ");

        actualItems = this.getJdbcTemplate().query(sql.toString(),
                new Object[] { sectionId, cedentYear, cedentQuarter, entryCode, entryCode },
                new RowMapper<TRiAcctEstimateTrans>() {
                    public TRiAcctEstimateTrans mapRow(ResultSet arg0, int arg1) throws SQLException {
                        TRiAcctEstimateTrans trans = new TRiAcctEstimateTrans();
                        if (arg0 != null) {
                            trans.setTransId(arg0.getLong("TRANS_ID"));
                            trans.setTransType(arg0.getInt("TRANS_TYPE"));
                            trans.setCedentYear(arg0.getInt("CEDENT_YEAR"));
                            trans.setCedentQuarter(arg0.getInt("CEDENT_QUARTER"));
                            trans.setContId(arg0.getLong("CONT_ID"));
                            trans.setSectionId(arg0.getLong("SECTION_ID"));
                            trans.setEstimateId(arg0.getLong("ESTIMATE_ID"));
                            trans.setEntryCode(arg0.getString("ENTRY_CODE"));
                            trans.setCurrency(arg0.getString("CURRENCY"));
                            trans.setMainCurrency(arg0.getString("MAIN_CURRENCY"));
                            trans.setToMainCurrencyRate(arg0.getBigDecimal("TO_MAIN_CURR_RATE"));
                            trans.setAmount(arg0.getBigDecimal("AMOUNT"));
                            trans.setText(arg0.getString("TEXT"));
                            trans.setSoaId(arg0.getLong("SOA_ID"));
                            trans.setClaimNo(arg0.getString("CLAIM_NO"));
                            trans.setDateOfLoss(arg0.getDate("DATE_OF_LOSS"));
                            trans.setBizDirection(arg0.getInt("BIZ_DIRECTION"));
                            trans.setBizTransType(arg0.getInt("BIZ_TRANS_TYPE"));
                            if (org.apache.commons.lang.StringUtils.isNotBlank(arg0.getString("CALC_FLAG"))
                                    && arg0.getString("CALC_FLAG").equals("Y")) {
                                trans.setCalcFlag(true);
                            } else {
                                trans.setCalcFlag(false);
                            }
                            trans.setInsertBy(arg0.getLong("INSERT_BY"));
                            trans.setInsertTime(arg0.getDate("INSERT_TIME"));
                        }
                        return trans;
                    }
                });
        return actualItems;
    }

    public List<TRiAcctEstimateItem> loadActualItems(long estimateId) {
        List<TRiAcctEstimateItem> actualItems = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT NULL AS ITEM_ID, T.ENTRY_CODE, SUM(T.AMOUNT) AS AMOUNT, 5 AS STATUS   ");
        sql.append("  FROM (SELECT NVL(B.ESTIMATE_GROUP, B.ENTRY_CODE) AS ENTRY_CODE,            ");
        sql.append("               (CASE NVL(B.ESTIMATE_GROUP, B.ENTRY_CODE)                     ");
        sql.append("                 WHEN B.ENTRY_CODE THEN 1                                    ");
        sql.append("                 ELSE -1                                                     ");
        sql.append("               END) * round(A.AMOUNT/A.TO_MAIN_CURR_RATE,2) AS AMOUNT        ");
        sql.append("          FROM T_RI_ACCT_ESTIMATE_TRANS A, T_RI_ENTRY_CODE B                 ");
        sql.append("         WHERE A.ESTIMATE_ID = ?                                             ");
        sql.append("           AND A.ENTRY_CODE = B.ENTRY_CODE) T                                ");
        sql.append(" GROUP BY T.ENTRY_CODE                                                       ");

        actualItems = this.getJdbcTemplate().query(sql.toString(), new Object[] { estimateId },
                new BeanPropertyRowMapper<TRiAcctEstimateItem>(TRiAcctEstimateItem.class));
        return actualItems;
    }

    public int updateCalcFlag(Long sectionId) throws Exception {
        Query query = em
                .createQuery("UPDATE TRiAcctEstimateTrans as t SET t.calcFlag = 'Y' WHERE t.sectionId = " + sectionId);
        int result = query.executeUpdate();
        return result;
    }

    public List<EntryItemVO> loadActualTable(long level, long contCompId, Date queryDate, int quarterSize) {
        List<EntryItemVO> entryItemList = null;
        String quarterStr = generateQuarterStr(quarterSize);
        StringBuilder sql = new StringBuilder();

        sql.append("WITH LATEST_ITEM AS                                                    ");
        sql.append(" (SELECT NVL(B.ESTIMATE_GROUP, B.ENTRY_CODE) AS ENTRY_CODE,            ");
        sql.append("         (CASE NVL(B.ESTIMATE_GROUP, B.ENTRY_CODE)                     ");
        sql.append("           WHEN B.ENTRY_CODE THEN 1                                    ");
        sql.append("           ELSE -1                                                     ");
        sql.append("         END) * round(A.AMOUNT/A.TO_MAIN_CURR_RATE,2) AS AMOUNT,       ");
        sql.append("         A.ESTIMATE_ID                                                 ");
        sql.append("    FROM T_RI_ACCT_ESTIMATE_TRANS A, T_RI_ENTRY_CODE B                 ");
        sql.append("   WHERE A.ENTRY_CODE = B.ENTRY_CODE                                   ");
        if (level == 1) {
            sql.append("         AND A.CONT_ID = ?                                         ");
        } else {
            sql.append("         AND A.SECTION_ID = ?                                      ");
        }
        sql.append("     AND A.INSERT_TIME < ? + 1)                                        ");
        sql.append("SELECT *                                                               ");
        sql.append("  FROM (SELECT LATEST_ITEM.ENTRY_CODE AS ITEM,                         ");
        sql.append("               T1.QUARTER_SEQ,                                         ");
        sql.append("               LATEST_ITEM.AMOUNT                                      ");
        sql.append("          FROM T_RI_ACCT_ESTIMATE T1, LATEST_ITEM                      ");
        sql.append("         WHERE T1.ESTIMATE_ID = LATEST_ITEM.ESTIMATE_ID) A PIVOT(SUM(AMOUNT) FOR QUARTER_SEQ IN("
                + quarterStr + "))");

        entryItemList = this.getJdbcTemplate().query(sql.toString(), new Object[] { contCompId, queryDate },
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

}
