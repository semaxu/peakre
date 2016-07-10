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

import com.ebao.ap99.accounting.entity.TRiAcctFeeDetail;
import com.ebao.ap99.parent.util.FieldsCollector;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * Date: Jan 19, 2016 7:56:09 PM
 * 
 * @author xiaoyu.yang
 */
public class TRiAcctFeeDetailDao extends BaseDao<TRiAcctFeeDetail> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Class<TRiAcctFeeDetail> getEntityClass() {
        return TRiAcctFeeDetail.class;
    }

    public List<TRiAcctFeeDetail> findList(TRiAcctFeeDetail condition) {
        StringBuilder sqlBuf = new StringBuilder("SELECT t FROM TRiAcctFeeDetail t WHERE 1=1");
        FieldsCollector.concatSql(condition, sqlBuf);

        Query query = em.createQuery(sqlBuf.toString());
        FieldsCollector.setParams(condition, query);

        @SuppressWarnings("unchecked")
        List<TRiAcctFeeDetail> list = (List<TRiAcctFeeDetail>) query.getResultList();
        return list;
    }

}
