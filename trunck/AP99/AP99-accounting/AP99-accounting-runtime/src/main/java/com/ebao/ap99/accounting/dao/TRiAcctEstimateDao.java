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

import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.accounting.entity.TRiAcctEstimate;
import com.ebao.ap99.parent.util.FieldsCollector;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

import reactor.util.CollectionUtils;

/**
 * Date: Jan 12, 2016 8:17:47 PM
 * 
 * @author xiaoyu.yang
 */
public class TRiAcctEstimateDao extends BaseDao<TRiAcctEstimate> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Class<TRiAcctEstimate> getEntityClass() {
        return TRiAcctEstimate.class;
    }

    public TRiAcctEstimate findBySectionIdAndYearQuarter(long contSectionId, int cedentYear, int cedentQuarter) {
        TRiAcctEstimate estimateEntity = null;
        final Query query = getEntityManager().createNamedQuery("TRiAcctEstimate.findBySectionIdAndYearQuarter",
                TRiAcctEstimate.class);
        query.setParameter("contSectionId", contSectionId);
        query.setParameter("cedentYear", cedentYear);
        query.setParameter("cedentQuarter", cedentQuarter);
        if (query.getResultList().size() != 0) {
            estimateEntity = (TRiAcctEstimate) query.getSingleResult();
        }
        return estimateEntity;
    }

    public TRiAcctEstimate findBySectionIdAndPeriod(long contSectionId, Date dateOfLoss) {
        TRiAcctEstimate estimateEntity = null;
        final Query query = getEntityManager().createNamedQuery("TRiAcctEstimate.findBySectionIdAndPeriod",
                TRiAcctEstimate.class);
        query.setParameter("contSectionId", contSectionId);
        query.setParameter("dateOfLoss", dateOfLoss);
        if (query.getResultList().size() == 1) {
            estimateEntity = (TRiAcctEstimate) query.getSingleResult();
        } else {
            throw new RuntimeException("Can't find only one record from TRiAcctEstimate by sectionId : " + contSectionId
                    + " ,dataOfLoss : " + dateOfLoss.toString());
        }
        return estimateEntity;
    }

    public List<Long> queryForecastSectionIdList(int cedentYear, int cedentQuarter) {
        final Query query = getEntityManager().createNamedQuery("TRiAcctEstimate.queryForecastSectionIdList",
                Long.class);
        query.setParameter("cedentQuarter", cedentYear + "" + cedentQuarter);
        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) query.getResultList();
        return list;
    }

    public List<Long> querySectionIdListByContractId(Long contractId) {
        final Query query = getEntityManager().createNamedQuery("TRiAcctEstimate.querySectionIdListByContId",
                Long.class);
        query.setParameter("contId", contractId);
        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) query.getResultList();
        return list;
    }

    public List<TRiAcctEstimate> findListByCondition(TRiAcctEstimate condition) {
        StringBuilder sqlBuf = new StringBuilder("SELECT t FROM TRiAcctEstimate t WHERE 1=1");
        FieldsCollector.concatSql(condition, sqlBuf);
        sqlBuf.append(" ORDER BY t.quarterSeq");

        Query query = em.createQuery(sqlBuf.toString());
        FieldsCollector.setParams(condition, query);

        @SuppressWarnings("unchecked")
        List<TRiAcctEstimate> list = (List<TRiAcctEstimate>) query.getResultList();
        return list;
    }

    public List<TRiAcctEstimate> listByYearQuarterAndSectionIds(YearQuarter yearQuarter, List<Long> sectionIds) {
        if (CollectionUtils.isEmpty(sectionIds)) {
            return null;
        }
        StringBuilder sqlBuf = new StringBuilder(
                "SELECT t FROM TRiAcctEstimate t WHERE t.cedentYear = " + yearQuarter.getYear()
                        + " AND t.cedentQuarter = " + yearQuarter.getQuarter() + " AND t.contSectionId IN (");
        for (Long sectionId : sectionIds) {
            sqlBuf.append(sectionId + ",");
        }
        sqlBuf.deleteCharAt(sqlBuf.length() - 1);
        sqlBuf.append(")");
        Query query = em.createQuery(sqlBuf.toString());

        @SuppressWarnings("unchecked")
        List<TRiAcctEstimate> list = (List<TRiAcctEstimate>) query.getResultList();
        return list;
    }

    /**
     * update all estimateQuarters from Estimating to Estimated
     * 
     * @throws Exception
     */
    public int convertEstimatingToEstimated() throws Exception {
        Query query = em.createQuery("UPDATE TRiAcctEstimate as t SET t.status = 3 WHERE t.status = 2");
        int result = query.executeUpdate();
        return result;
    }

    /**
     * update estimateQuarter status from Reversed to Actual
     * 
     * @param sectionId
     * @throws Exception
     */
    public int convertReversedToActual(Long sectionId) throws Exception {
        Query query = em.createQuery(
                "UPDATE TRiAcctEstimate as t SET t.status = 5 WHERE t.status = 4 AND t.contSectionId = " + sectionId);
        int result = query.executeUpdate();
        return result;
    }

    public Long saveOrUpdate(TRiAcctEstimate acctEstimate) throws Exception {
        TRiAcctEstimate entityReturn = merge(acctEstimate);
        em.flush();
        return entityReturn.getEstimateId();
    }

    public TRiAcctEstimate save(TRiAcctEstimate estimate) {
        if (estimate.getEstimateId() != null) {
            TRiAcctEstimate existingOne = this.load(estimate.getEstimateId());
            estimate.syncDataTo(existingOne, false);
            return existingOne;
        } else {
            this.getEntityManager().persist(estimate);
            return estimate;
        }
    }

}
