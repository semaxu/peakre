/**
 * 
 */
package com.ebao.ap99.accounting.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ebao.ap99.accounting.entity.TRiSoaSection;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * @author
 */
public class SoaSectionDao extends BaseDao<TRiSoaSection> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Class<TRiSoaSection> getEntityClass() {
        return TRiSoaSection.class;
    }

    public List<TRiSoaSection> findSoaSectionListBySoaId(Long soaId) {
        final Query query = getEntityManager().createNamedQuery("TRiSoaSection.findListBySoaId", TRiSoaSection.class);
        query.setParameter("soaId", soaId);
        @SuppressWarnings("unchecked")
        List<TRiSoaSection> list = (List<TRiSoaSection>) query.getResultList();
        return list;
    }

    public boolean sectionHasNotReversed(Long contracCompId, Integer cendentYear, Integer cendentQuarter)
            throws Exception {
        StringBuilder sql = new StringBuilder("");
        sql.append("  SELECT COUNT(1) AS COUNT                   ");
        sql.append("    FROM T_RI_SOA A, T_RI_SOA_SECTION B      ");
        sql.append("   WHERE A.SOA_ID = B.SOA_ID                 ");
        sql.append("     AND B.IS_REVERSAL = 'Y'                 ");
        sql.append("     AND B.CONTRAC_COMP_ID = ?               ");
        sql.append("     AND A.CEDENT_YEAR = ?                   ");
        sql.append("     AND A.CEDENT_QUARTER = ?                ");
        Map<String, Object> resultMap = this.getJdbcTemplate().queryForMap(sql.toString(), contracCompId, cendentYear,
                cendentQuarter);
        int count = Integer.parseInt(resultMap.get("COUNT").toString());
        return count == 0;
    }
}
