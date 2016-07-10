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

import com.ebao.ap99.accounting.entity.TRiAcctFee;
import com.ebao.ap99.parent.util.FieldsCollector;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * Date: Jan 19, 2016 7:55:05 PM
 * 
 * @author xiaoyu.yang
 */
public class TRiAcctFeeDao extends BaseDao<TRiAcctFee> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Class<TRiAcctFee> getEntityClass() {
        return TRiAcctFee.class;
    }

    public List<TRiAcctFee> loadByFeeIds(List<Long> feeIds) {
        StringBuilder sqlBuf = new StringBuilder("SELECT t FROM TRiAcctFee t WHERE t.feeId IN (");
        for (Long feeId : feeIds) {
            sqlBuf.append(feeId + ",");
        }
        sqlBuf.deleteCharAt(sqlBuf.length() - 1);
        sqlBuf.append(")");
        Query query = em.createQuery(sqlBuf.toString());

        @SuppressWarnings("unchecked")
        List<TRiAcctFee> list = (List<TRiAcctFee>) query.getResultList();
        return list;
    }

    public List<TRiAcctFee> findList(TRiAcctFee condition) {
        StringBuilder sqlBuf = new StringBuilder("SELECT t FROM TRiAcctFee t WHERE 1=1");
        FieldsCollector.concatSql(condition, sqlBuf);

        Query query = em.createQuery(sqlBuf.toString());
        FieldsCollector.setParams(condition, query);

        @SuppressWarnings("unchecked")
        List<TRiAcctFee> list = (List<TRiAcctFee>) query.getResultList();
        return list;
    }
}
