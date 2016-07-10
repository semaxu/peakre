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

import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.accounting.entity.TRiAcctQuarterClosing;
import com.ebao.ap99.parent.util.FieldsCollector;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * Date: Jan 5, 2016 7:35:03 PM
 * 
 * @author xiaoyu.yang
 */
public class TRiAcctQuarterClosingDao extends BaseDao<TRiAcctQuarterClosing> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Class<TRiAcctQuarterClosing> getEntityClass() {
        return TRiAcctQuarterClosing.class;
    }

    public TRiAcctQuarterClosing getCurrentQuarter() {
        TRiAcctQuarterClosing currentQuarter = null;
        try {
            final Query query = getEntityManager().createNamedQuery(
                    "TRiAcctQuarterClosing.findCurrentProcessingQuarter", TRiAcctQuarterClosing.class);

            if (query.getResultList().size() != 0) {
                currentQuarter = (TRiAcctQuarterClosing) query.getSingleResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentQuarter;
    }

    public TRiAcctQuarterClosing findByQuarter(YearQuarter yearQuarter) {
        TRiAcctQuarterClosing condition = new TRiAcctQuarterClosing();
        condition.setFinancialYear(yearQuarter.getYear());
        condition.setFinancialQuarter(yearQuarter.getQuarter());

        return this.findByCondition(condition);
    }

    public TRiAcctQuarterClosing findByCondition(TRiAcctQuarterClosing condition) {
        TRiAcctQuarterClosing quarterClosing = null;
        StringBuilder sqlBuf = new StringBuilder("SELECT t FROM TRiAcctQuarterClosing t WHERE 1=1");
        FieldsCollector.concatSql(condition, sqlBuf);

        Query query = em.createQuery(sqlBuf.toString());
        FieldsCollector.setParams(condition, query);

        if (query.getResultList().size() != 0) {
            quarterClosing = (TRiAcctQuarterClosing) query.getSingleResult();
        }
        return quarterClosing;
    }

    public Long getQuarterClosingCount(TRiAcctQuarterClosing condition) {
        StringBuilder sqlBuf = new StringBuilder("SELECT count(t) FROM TRiAcctQuarterClosing t WHERE 1=1");
        FieldsCollector.concatSql(condition, sqlBuf);

        Query query = em.createQuery(sqlBuf.toString());
        FieldsCollector.setParams(condition, query);

        Long result = (Long) query.getSingleResult();
        return result;
    }

    public List<TRiAcctQuarterClosing> getCurrentPage(TRiAcctQuarterClosing condition, int pageIndex,
                                                      int countPerPage) {
        int begin = (pageIndex - 1) * countPerPage;
        StringBuilder sqlBuf = new StringBuilder("SELECT t FROM TRiAcctQuarterClosing t WHERE 1=1");
        FieldsCollector.concatSql(condition, sqlBuf);
        sqlBuf.append(" ORDER BY financialYear DESC,financialQuarter DESC");

        Query query = em.createQuery(sqlBuf.toString());
        FieldsCollector.setParams(condition, query);
        query.setFirstResult(begin);
        query.setMaxResults(countPerPage);

        @SuppressWarnings("unchecked")
        List<TRiAcctQuarterClosing> list = (List<TRiAcctQuarterClosing>) query.getResultList();
        return list;
    }

}
