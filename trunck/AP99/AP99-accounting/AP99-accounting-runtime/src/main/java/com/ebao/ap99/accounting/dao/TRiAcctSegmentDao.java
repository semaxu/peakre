/**
 * 
 */
package com.ebao.ap99.accounting.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.ebao.ap99.accounting.constant.SegmentStatusEnum;
import com.ebao.ap99.accounting.entity.TRiAcctSegment;
import com.ebao.ap99.parent.util.FieldsCollector;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * @author xiaoyu.yang
 */
public class TRiAcctSegmentDao extends BaseDao<TRiAcctSegment> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Class<TRiAcctSegment> getEntityClass() {
        return TRiAcctSegment.class;
    }

    public TRiAcctSegment findByCondition(TRiAcctSegment condition) {
        TRiAcctSegment quarterClosing = null;
        StringBuilder sqlBuf = new StringBuilder("SELECT t FROM TRiAcctSegment t WHERE 1=1");
        FieldsCollector.concatSql(condition, sqlBuf);

        Query query = em.createQuery(sqlBuf.toString());
        FieldsCollector.setParams(condition, query);

        if (query.getResultList().size() != 0) {
            quarterClosing = (TRiAcctSegment) query.getSingleResult();
        }
        return quarterClosing;
    }

    /**
     * Check for exist valid segment by segment name or three conditions<br>
     * if not exist return true
     * 
     * @param condition
     * @return true
     */
    public boolean sameSegmentExisted(TRiAcctSegment condition) {
        StringBuilder sqlBuf = new StringBuilder(
                "SELECT COUNT(1) FROM TRiAcctSegment t WHERE (t.segmentName=:segmentName OR (t.cob=:cob AND t.contractNature=:contractNature AND t.cedentCountry=:cedentCountry)) AND t.status=:status");
        Query query = em.createQuery(sqlBuf.toString());
        if (StringUtils.isNotBlank(condition.getSegmentName())) {
            query.setParameter("segmentName", condition.getSegmentName());
        }
        if (StringUtils.isNotBlank(condition.getCob())) {
            query.setParameter("cob", condition.getCob());
        }
        if (condition.getContractNature() != null) {
            query.setParameter("contractNature", condition.getContractNature());
        }
        if (condition.getCedentCountry() != null) {
            query.setParameter("cedentCountry", condition.getCedentCountry());
        }
        query.setParameter("status", SegmentStatusEnum.Valid.getValue());

        return (Long) query.getSingleResult() > 0L;
    }

    /**
     * search segment by condition and return the records num
     * 
     * @param condition
     * @return
     */
    public Long getSegmentCount(TRiAcctSegment condition) {
        StringBuilder sqlBuf = new StringBuilder("SELECT count(t) FROM TRiAcctSegment t WHERE 1=1");

        Query query = concatCondition(condition, sqlBuf);

        Long result = (Long) query.getSingleResult();
        return result;
    }

    /**
     * search segment by condition and return segment list
     * 
     * @param condition
     * @param start
     * @param limit
     * @return
     */
    public List<TRiAcctSegment> getCurrentPage(TRiAcctSegment condition, int pageIndex, int countPerPage) {
        int begin = (pageIndex - 1) * countPerPage;
        StringBuilder sqlBuf = new StringBuilder("SELECT t FROM TRiAcctSegment t WHERE 1=1");

        Query query = concatCondition(condition, sqlBuf);

        query.setFirstResult(begin);
        query.setMaxResults(countPerPage);

        @SuppressWarnings("unchecked")
        List<TRiAcctSegment> list = (List<TRiAcctSegment>) query.getResultList();
        return list;
    }

    private Query concatCondition(TRiAcctSegment condition, StringBuilder sqlBuf) {
        if (condition.getSegmentId() != null) {
            sqlBuf.append(" AND t.segmentId=:segmentId");
        }
        if (StringUtils.isNotBlank(condition.getSegmentCode())) {
            sqlBuf.append(" AND t.segmentCode LIKE:segmentCode");
        }
        if (StringUtils.isNotBlank(condition.getSegmentName())) {
            sqlBuf.append(" AND upper(t.segmentName) LIKE upper(:segmentName)");
        }
        if (StringUtils.isNotBlank(condition.getCob())) {
            sqlBuf.append(" AND t.cob=:cob");
        }
        if (condition.getContractNature() != null) {
            sqlBuf.append(" AND t.contractNature=:contractNature");
        }
        if (condition.getStatus() != null) {
            sqlBuf.append(" AND t.status=:status");
        }
        if (condition.getCedentCountry() != null) {
            sqlBuf.append(" AND t.cedentCountry=:cedentCountry");
        }
        if (condition.getUwYear() != null) {
            sqlBuf.append(" AND t.uwYear=:uwYear");
        }
        sqlBuf.append(" order by insertTime desc");

        Query query = em.createQuery(sqlBuf.toString());

        if (condition.getSegmentId() != null) {
            query.setParameter("segmentId", condition.getSegmentId());
        }
        if (StringUtils.isNotBlank(condition.getSegmentCode())) {
            query.setParameter("segmentCode", "%" + condition.getSegmentCode() + "%");
        }
        if (StringUtils.isNotBlank(condition.getSegmentName())) {
            query.setParameter("segmentName", "%" + condition.getSegmentName() + "%");
        }
        if (StringUtils.isNotBlank(condition.getCob())) {
            query.setParameter("cob", condition.getCob());
        }
        if (condition.getContractNature() != null) {
            query.setParameter("contractNature", condition.getContractNature());
        }
        if (condition.getStatus() != null) {
            query.setParameter("status", condition.getStatus());
        }
        if (condition.getCedentCountry() != null) {
            query.setParameter("cedentCountry", condition.getCedentCountry());
        }
        if (condition.getUwYear() != null) {
            query.setParameter("uwYear", condition.getUwYear());
        }
        return query;
    }

}
