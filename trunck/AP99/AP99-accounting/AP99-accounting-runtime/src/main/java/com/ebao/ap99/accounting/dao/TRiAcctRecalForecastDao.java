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
import com.ebao.ap99.accounting.entity.TRiAcctRecalForecast;
import com.ebao.ap99.parent.util.FieldsCollector;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * Date: Mar 10, 2016 10:28:18 AM
 * 
 * @author xiaoyu.yang
 */
public class TRiAcctRecalForecastDao extends BaseDao<TRiAcctRecalForecast> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Class<TRiAcctRecalForecast> getEntityClass() {
        return TRiAcctRecalForecast.class;
    }

    public List<Integer> getPendingEventTypeBySectionId(long sectionId, YearQuarter yearQuarter) {
        final Query query = getEntityManager().createNamedQuery("TRiAcctRecalForecast.getEventTypeBySectionId",
                Integer.class);
        query.setParameter("sectionId", sectionId);
        query.setParameter("calcQuarter", yearQuarter.toString());

        @SuppressWarnings("unchecked")
        List<Integer> list = (List<Integer>) query.getResultList();
        return list;
    }

    /**
     * get event list of SOA/Withdraw
     * 
     * @param yearQuarter
     * @return
     */
    public List<TRiAcctRecalForecast> getEventListofSOA$Withdraw(YearQuarter yearQuarter) {
        final Query query = getEntityManager().createNamedQuery("TRiAcctRecalForecast.getEventOfSOA$Withdraw",
                TRiAcctRecalForecast.class);
        query.setParameter("calcQuarter", yearQuarter.toString());
        @SuppressWarnings("unchecked")
        List<TRiAcctRecalForecast> list = (List<TRiAcctRecalForecast>) query.getResultList();
        return list;
    }

    public List<TRiAcctRecalForecast> findListByCondition(TRiAcctRecalForecast condition) {
        StringBuilder sqlBuf = new StringBuilder("SELECT t FROM TRiAcctRecalForecast t WHERE 1=1");
        FieldsCollector.concatSql(condition, sqlBuf);

        Query query = em.createQuery(sqlBuf.toString());
        FieldsCollector.setParams(condition, query);

        @SuppressWarnings("unchecked")
        List<TRiAcctRecalForecast> list = (List<TRiAcctRecalForecast>) query.getResultList();
        return list;
    }

    /**
     * finish EPI Event : update status to EventStatusEnum.Done(=2)
     * 
     * @param sectionId
     * @param yearQuarter
     * @return
     */
    public int finishEPIEvent(Long sectionId, YearQuarter yearQuarter) {
        Query query = em.createQuery(
                "UPDATE TRiAcctRecalForecast as t SET t.status = 2 WHERE t.eventType = 1 AND t.sectionId = " + sectionId
                        + " AND t.calcQuarter = '" + yearQuarter.toString() + "'");
        int result = query.executeUpdate();
        return result;
    }

    /**
     * finish SOA/Withdraw Event : update status to EventStatusEnum.Done(=2)
     * 
     * @param soaId
     * @param eventType
     * @param yearQuarter
     * @return
     */
    public int finishSOAEvent(Long soaId, Integer eventType, YearQuarter yearQuarter) {
        Query query = em.createQuery("UPDATE TRiAcctRecalForecast as t SET t.status = 2 WHERE t.eventType = "
                + eventType + " AND t.soaId = " + soaId + " AND t.calcQuarter = '" + yearQuarter.toString() + "'");
        int result = query.executeUpdate();
        return result;
    }

}
