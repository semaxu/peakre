/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.ebao.ap99.accounting.entity.TRiAcctEstimateItem;
import com.ebao.ap99.parent.util.FieldsCollector;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * Date: Jan 12, 2016 8:18:53 PM
 * 
 * @author xiaoyu.yang
 */
public class TRiAcctEstimateItemDao extends BaseDao<TRiAcctEstimateItem> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Class<TRiAcctEstimateItem> getEntityClass() {
        return TRiAcctEstimateItem.class;
    }

    public List<TRiAcctEstimateItem> findList(TRiAcctEstimateItem condition) {
        StringBuilder sqlBuf = new StringBuilder("SELECT t FROM TRiAcctEstimateItem t WHERE 1=1");
        FieldsCollector.concatSql(condition, sqlBuf);

        Query query = em.createQuery(sqlBuf.toString());
        FieldsCollector.setParams(condition, query);

        @SuppressWarnings("unchecked")
        List<TRiAcctEstimateItem> list = (List<TRiAcctEstimateItem>) query.getResultList();
        return list;
    }

    public List<TRiAcctEstimateItem> findActualItems(Long estimateId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT NULL AS ITEM_ID, T.ENTRY_CODE, T.AMOUNT, 5 AS STATUS          ");
        sql.append("  FROM T_RI_ACCT_ESTIMATE_TRANS T                                    ");
        sql.append(" WHERE T.ESTIMATE_ID = ?                                             ");

        List<TRiAcctEstimateItem> actualItemList = this.getJdbcTemplate().query(sql.toString(),
                new Object[] { estimateId }, new BeanPropertyRowMapper<TRiAcctEstimateItem>(TRiAcctEstimateItem.class));
        return actualItemList;
    }

}
