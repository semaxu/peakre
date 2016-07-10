/**
 * 
 */
package com.ebao.ap99.accounting.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.accounting.UI.model.SoaSearchVO;
import com.ebao.ap99.accounting.entity.TRiSoa;
import com.ebao.ap99.accounting.model.SoaEntry;
import com.ebao.unicorn.platform.foundation.context.SpringContextUtils;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * @author
 */
public class SoaDao extends BaseDao<TRiSoa> {

    @Override
    public Class<TRiSoa> getEntityClass() {
        return TRiSoa.class;
    }

    @PersistenceContext
    private EntityManager em;

    public Long getSoaInfoCountByConditions(SoaSearchVO soaSearchVO) {

        StringBuilder sqlBuf = new StringBuilder("SELECT count(s) FROM TRiSoa s WHERE 1=1");

        if (soaSearchVO != null) {
            if (soaSearchVO.getContractCode() != null && !"".equals(soaSearchVO.getContractCode())) {
                sqlBuf.append(" and  s.contractCode =:contractCode ");
            }
            if (soaSearchVO.getContractName() != null && !"".equals(soaSearchVO.getContractName())) {
                sqlBuf.append(" and  s.contractName LIKE:contractName ");
            }
            String soaId = soaSearchVO.getSoaId();
            if (soaId != null && !"".equals(soaId)) {
                sqlBuf.append(" and  s.soaId =:soaId ");
            }
            if (soaSearchVO.getReceivedDate() != null && !"".equals(soaSearchVO.getReceivedDate())) {
                sqlBuf.append(" and  s.receivedDate=:receivedDate ");
            }

            if (soaSearchVO.getCedent() != null && !"".equals(soaSearchVO.getCedent())) {
                sqlBuf.append(" and  s.cedent =:cedent ");
            }
            if (soaSearchVO.getBizType() != null && !"".equals(soaSearchVO.getBizType())) {
                sqlBuf.append(" and  s.bizType =:bizType ");
            }
            if (soaSearchVO.getSoaStatus() != null && !"".equals(soaSearchVO.getSoaStatus())) {
                sqlBuf.append(" and  s.soaStatus =:soaStatus ");
            }
            if (soaSearchVO.getBroke() != null && !"".equals(soaSearchVO.getBroke())) {
                sqlBuf.append(" and  s.broke =:broke ");
            }
            if (soaSearchVO.getCedentYear() != null && soaSearchVO.getCedentYear() != 0
                    && !"".equals(soaSearchVO.getCedentYear())) {
                sqlBuf.append(" and  s.cedentYear =:cedentYear ");
            }
            if (soaSearchVO.getCedentQuarter() != null && soaSearchVO.getCedentQuarter() != 0
                    && !"".equals(soaSearchVO.getCedentQuarter())) {
                sqlBuf.append(" and  s.cedentQuarter =:cedentQuarter ");
            }
            if (soaSearchVO.getCedentPeriod() != null && soaSearchVO.getCedentPeriod() != 0
                    && !"".equals(soaSearchVO.getCedentPeriod())) {
                sqlBuf.append(" and  s.cedentPeriod =:cedentPeriod ");
            }
            if (soaSearchVO.getUwYear() != null && soaSearchVO.getUwYear() != 0
                    && !"".equals(soaSearchVO.getUwYear())) {
                sqlBuf.append(" and  s.uwYear =:uwYear ");
            }

            if (soaSearchVO.getFinancialQuarter() != null && soaSearchVO.getFinancialQuarter() != 0
                    && !"".equals(soaSearchVO.getFinancialQuarter())) {
                sqlBuf.append(" and  s.financialQuarter =:financialQuarter ");
            }

            if (soaSearchVO.getFinancialYear() != null && soaSearchVO.getFinancialYear() != 0
                    && !"".equals(soaSearchVO.getFinancialYear())) {
                sqlBuf.append(" and  s.financialYear =:financialYear ");
            }

            if (soaSearchVO.getTaskReleaser() != null && !"".equals(soaSearchVO.getTaskReleaser())) {
                sqlBuf.append(" and  s.taskreleaser =:taskreleaser ");
            }

            if (soaSearchVO.getTaskCreator() != null && !"".equals(soaSearchVO.getTaskCreator())) {
                sqlBuf.append(" and  s.taskcreator =:taskcreator ");
            }

        }

        final Query query = this.em.createQuery(sqlBuf.toString());

        if (soaSearchVO != null) {
            if (soaSearchVO.getContractCode() != null && !"".equals(soaSearchVO.getContractCode())) {
                query.setParameter("contractCode", soaSearchVO.getContractCode());
            }
            if (soaSearchVO.getContractName() != null && !"".equals(soaSearchVO.getContractName())) {
                query.setParameter("contractName", "%" + soaSearchVO.getContractName() + "%");
            }
            String soaId = soaSearchVO.getSoaId();
            if (soaId != null && !"".equals(soaId)) {
                query.setParameter("soaId", Long.parseLong(soaId));
                sqlBuf.append(" s.soa_id = soaId");
            }
            if (soaSearchVO.getReceivedDate() != null && !"".equals(soaSearchVO.getReceivedDate())) {
                query.setParameter("receivedDate", soaSearchVO.getReceivedDate());
            }
            if (soaSearchVO.getCedent() != null && !"".equals(soaSearchVO.getCedent())) {
                query.setParameter("cedent", soaSearchVO.getCedent());
            }
            if (soaSearchVO.getBizType() != null && !"".equals(soaSearchVO.getBizType())) {
                query.setParameter("bizType", soaSearchVO.getBizType());
            }
            if (soaSearchVO.getSoaStatus() != null && !"".equals(soaSearchVO.getSoaStatus())) {
                query.setParameter("soaStatus", soaSearchVO.getSoaStatus());
            }
            if (soaSearchVO.getBroke() != null && !"".equals(soaSearchVO.getBroke())) {
                query.setParameter("broke", soaSearchVO.getBroke());
            }
            if (soaSearchVO.getCedentYear() != null && soaSearchVO.getCedentYear() != 0
                    && !"".equals(soaSearchVO.getCedentYear())) {
                query.setParameter("cedentYear", Integer.valueOf(String.valueOf(soaSearchVO.getCedentYear())));
            }
            if (soaSearchVO.getCedentQuarter() != null && soaSearchVO.getCedentQuarter() != 0
                    && !"".equals(soaSearchVO.getCedentQuarter())) {
                query.setParameter("cedentQuarter", Integer.valueOf(String.valueOf(soaSearchVO.getCedentQuarter())));
            }
            if (soaSearchVO.getCedentPeriod() != null && soaSearchVO.getCedentPeriod() != 0
                    && !"".equals(soaSearchVO.getCedentPeriod())) {
                query.setParameter("cedentPeriod", Integer.valueOf(String.valueOf(soaSearchVO.getCedentPeriod())));
            }
            if (soaSearchVO.getUwYear() != null && soaSearchVO.getUwYear() != 0
                    && !"".equals(soaSearchVO.getUwYear())) {
                query.setParameter("uwYear", soaSearchVO.getUwYear());
            }

            if (soaSearchVO.getFinancialQuarter() != null && soaSearchVO.getFinancialQuarter() != 0
                    && !"".equals(soaSearchVO.getFinancialQuarter())) {
                query.setParameter("financialQuarter", soaSearchVO.getFinancialQuarter());
            }

            if (soaSearchVO.getFinancialYear() != null && soaSearchVO.getFinancialYear() != 0
                    && !"".equals(soaSearchVO.getFinancialYear())) {
                query.setParameter("financialYear", soaSearchVO.getFinancialYear());
            }

            if (soaSearchVO.getTaskReleaser() != null && !"".equals(soaSearchVO.getTaskReleaser())) {
                query.setParameter("taskreleaser", soaSearchVO.getTaskReleaser());
            }

            if (soaSearchVO.getTaskCreator() != null && !"".equals(soaSearchVO.getTaskCreator())) {
                query.setParameter("taskcreator", soaSearchVO.getTaskCreator());
            }

        }

        Long resultCount = (Long) query.getSingleResult();

        return resultCount;
    }

    public List<TRiSoa> getSoaCurrentPage(SoaSearchVO soaSearchVO, int start, int limit) {

        StringBuilder sqlBuf = new StringBuilder("SELECT s FROM TRiSoa s WHERE 1=1 ");

        if (soaSearchVO != null) {
            if (soaSearchVO.getContractCode() != null && !"".equals(soaSearchVO.getContractCode())) {
                sqlBuf.append(" and  s.contractCode =:contractCode ");
            }
            if (soaSearchVO.getContractName() != null && !"".equals(soaSearchVO.getContractName())) {
                sqlBuf.append(" and  s.contractName LIKE:contractName ");
            }
            String soaId = soaSearchVO.getSoaId();
            if (soaId != null && !"".equals(soaId)) {
                sqlBuf.append(" and  s.soaId =:soaId ");
            }
            if (soaSearchVO.getReceivedDate() != null && !"".equals(soaSearchVO.getReceivedDate())) {
                sqlBuf.append(" and  s.receivedDate=:receivedDate ");
            }

            if (soaSearchVO.getCedent() != null && !"".equals(soaSearchVO.getCedent())) {
                sqlBuf.append(" and  s.cedent =:cedent ");
            }
            if (soaSearchVO.getBizType() != null && !"".equals(soaSearchVO.getBizType())) {
                sqlBuf.append(" and  s.bizType =:bizType ");
            }
            if (soaSearchVO.getSoaStatus() != null && !"".equals(soaSearchVO.getSoaStatus())) {
                sqlBuf.append(" and  s.soaStatus =:soaStatus ");
            }
            if (soaSearchVO.getBroke() != null && !"".equals(soaSearchVO.getBroke())) {
                sqlBuf.append(" and  s.broke =:broke ");
            }
            if (soaSearchVO.getCedentYear() != null && soaSearchVO.getCedentYear() != 0
                    && !"".equals(soaSearchVO.getCedentYear())) {
                sqlBuf.append(" and  s.cedentYear =:cedentYear ");
            }
            if (soaSearchVO.getCedentQuarter() != null && soaSearchVO.getCedentQuarter() != 0
                    && !"".equals(soaSearchVO.getCedentQuarter())) {
                sqlBuf.append(" and  s.cedentQuarter =:cedentQuarter ");
            }
            if (soaSearchVO.getCedentPeriod() != null && soaSearchVO.getCedentPeriod() != 0
                    && !"".equals(soaSearchVO.getCedentPeriod())) {
                sqlBuf.append(" and  s.cedentPeriod =:cedentPeriod ");
            }
            if (soaSearchVO.getUwYear() != null && soaSearchVO.getUwYear() != 0
                    && !"".equals(soaSearchVO.getUwYear())) {
                sqlBuf.append(" and  s.uwYear =:uwYear ");
            }

            if (soaSearchVO.getFinancialQuarter() != null && soaSearchVO.getFinancialQuarter() != 0
                    && !"".equals(soaSearchVO.getFinancialQuarter())) {
                sqlBuf.append(" and  s.financialQuarter =:financialQuarter ");
            }

            if (soaSearchVO.getFinancialYear() != null && soaSearchVO.getFinancialYear() != 0
                    && !"".equals(soaSearchVO.getFinancialYear())) {
                sqlBuf.append(" and  s.financialYear =:financialYear ");
            }

            if (soaSearchVO.getTaskReleaser() != null && !"".equals(soaSearchVO.getTaskReleaser())) {
                sqlBuf.append(" and  s.taskreleaser =:taskreleaser ");
            }

            if (soaSearchVO.getTaskCreator() != null && !"".equals(soaSearchVO.getTaskCreator())) {
                sqlBuf.append(" and  s.taskcreator =:taskcreator ");
            }

            sqlBuf.append(" order by s.soaId desc ");

        }

        final Query query = this.em.createQuery(sqlBuf.toString());

        if (soaSearchVO != null) {
            if (soaSearchVO.getContractCode() != null && !"".equals(soaSearchVO.getContractCode())) {
                query.setParameter("contractCode", soaSearchVO.getContractCode());
            }
            if (soaSearchVO.getContractName() != null && !"".equals(soaSearchVO.getContractName())) {
                query.setParameter("contractName", "%" + soaSearchVO.getContractName() + "%");
            }
            String soaId = soaSearchVO.getSoaId();
            if (soaId != null && !"".equals(soaId)) {
                query.setParameter("soaId", Long.parseLong(soaId));
                sqlBuf.append(" s.soa_id = soaId");
            }
            if (soaSearchVO.getReceivedDate() != null && !"".equals(soaSearchVO.getReceivedDate())) {
                query.setParameter("receivedDate", soaSearchVO.getReceivedDate());
            }
            if (soaSearchVO.getCedent() != null && !"".equals(soaSearchVO.getCedent())) {
                query.setParameter("cedent", soaSearchVO.getCedent());
            }
            if (soaSearchVO.getBizType() != null && !"".equals(soaSearchVO.getBizType())) {
                query.setParameter("bizType", soaSearchVO.getBizType());
            }
            if (soaSearchVO.getSoaStatus() != null && !"".equals(soaSearchVO.getSoaStatus())) {
                query.setParameter("soaStatus", soaSearchVO.getSoaStatus());
            }
            if (soaSearchVO.getBroke() != null && !"".equals(soaSearchVO.getBroke())) {
                query.setParameter("broke", soaSearchVO.getBroke());
            }
            if (soaSearchVO.getCedentYear() != null && soaSearchVO.getCedentYear() != 0
                    && !"".equals(soaSearchVO.getCedentYear())) {
                query.setParameter("cedentYear", Integer.valueOf(String.valueOf(soaSearchVO.getCedentYear())));
            }
            if (soaSearchVO.getCedentQuarter() != null && soaSearchVO.getCedentQuarter() != 0
                    && !"".equals(soaSearchVO.getCedentQuarter())) {
                query.setParameter("cedentQuarter", Integer.valueOf(String.valueOf(soaSearchVO.getCedentQuarter())));
            }
            if (soaSearchVO.getCedentPeriod() != null && soaSearchVO.getCedentPeriod() != 0
                    && !"".equals(soaSearchVO.getCedentPeriod())) {
                query.setParameter("cedentPeriod", Integer.valueOf(String.valueOf(soaSearchVO.getCedentPeriod())));
            }
            if (soaSearchVO.getUwYear() != null && soaSearchVO.getUwYear() != 0
                    && !"".equals(soaSearchVO.getUwYear())) {
                query.setParameter("uwYear", soaSearchVO.getUwYear());
            }

            if (soaSearchVO.getFinancialQuarter() != null && soaSearchVO.getFinancialQuarter() != 0
                    && !"".equals(soaSearchVO.getFinancialQuarter())) {
                query.setParameter("financialQuarter", soaSearchVO.getFinancialQuarter());
            }

            if (soaSearchVO.getFinancialYear() != null && soaSearchVO.getFinancialYear() != 0
                    && !"".equals(soaSearchVO.getFinancialYear())) {
                query.setParameter("financialYear", soaSearchVO.getFinancialYear());
            }

            if (soaSearchVO.getTaskReleaser() != null && !"".equals(soaSearchVO.getTaskReleaser())) {
                query.setParameter("taskreleaser", soaSearchVO.getTaskReleaser());
            }

            if (soaSearchVO.getTaskCreator() != null && !"".equals(soaSearchVO.getTaskCreator())) {
                query.setParameter("taskcreator", soaSearchVO.getTaskCreator());
            }

        }

        if (start != -1 || limit != -1) {
            query.setFirstResult(start);
            query.setMaxResults(limit);
        }

        List<TRiSoa> soaList = query.getResultList();

        return soaList;
    }

    public List<SoaEntry> findSoaEntryListBySectionIdAndFNQuarter(Long sectionId, YearQuarter yearQuarter) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.CEDENT_YEAR,                                                       ");
        sql.append("       A.CEDENT_QUARTER,                                                    ");
        sql.append("       A.CONTRACT_ID,                                                       ");
        sql.append("       C.CONTRAC_COMP_ID AS SECTION_ID,                                     ");
        sql.append("       B.CURRENCY_CODE AS CURRENCY,                                         ");
        sql.append("       D.ENTRY_CODE,                                                        ");
        sql.append("       SUM(D.AMOUNT) AS AMOUNT,                                             ");
        sql.append("       A.SOA_TEXT                                                           ");
        sql.append("  FROM T_RI_SOA A,                                                          ");
        sql.append("       T_RI_SOA_CURRENCY B,                                                 ");
        sql.append("       T_RI_SOA_SECTION C,                                                  ");
        sql.append("       T_RI_SOA_SECTION_ITEM D                                              ");
        sql.append(" WHERE A.SOA_ID = B.SOA_ID                                                  ");
        sql.append("   AND B.SOA_CURRENCY_ID = C.SOA_CURRENCY_ID                                ");
        sql.append("   AND C.SOA_SECTION_ID = D.SOA_SECTION_ID                                  ");
        sql.append("   AND C.CONTRAC_COMP_ID = ?                                                ");
        sql.append("   AND A.FINANCIAL_YEAR = ?                                                 ");
        sql.append("   AND A.FINANCIAL_QUARTER = ?                                              ");
        sql.append(" GROUP BY A.CEDENT_YEAR,                                                    ");
        sql.append("          A.CEDENT_QUARTER,                                                 ");
        sql.append("          A.CONTRACT_ID,                                                    ");
        sql.append("          C.CONTRAC_COMP_ID,                                                ");
        sql.append("          B.CURRENCY_CODE,                                                  ");
        sql.append("          D.ENTRY_CODE,                                                     ");
        sql.append("          A.SOA_TEXT                                                        ");
        sql.append(" ORDER BY A.CONTRACT_ID, C.CONTRAC_COMP_ID, A.CEDENT_YEAR, A.CEDENT_QUARTER ");

        List<SoaEntry> compSectionList = this.getJdbcTemplate().query(sql.toString(),
                new Object[] { sectionId, yearQuarter.getYear(), yearQuarter.getQuarter() },
                new BeanPropertyRowMapper<SoaEntry>(SoaEntry.class));
        return compSectionList;
    }

    public List<SoaEntry> findSoaEntryListBySoaId(Long soaId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.SOA_ID,                                                            ");
        sql.append("       A.CEDENT_YEAR,                                                       ");
        sql.append("       A.CEDENT_QUARTER,                                                    ");
        sql.append("       A.CONTRACT_ID,                                                       ");
        sql.append("       C.CONTRAC_COMP_ID AS SECTION_ID,                                     ");
        sql.append("       B.CURRENCY_CODE AS CURRENCY,                                         ");
        sql.append("       D.ENTRY_CODE,                                                        ");
        sql.append("       SUM(D.AMOUNT) AS AMOUNT,                                             ");
        sql.append("       A.SOA_TEXT                                                           ");
        sql.append("  FROM T_RI_SOA A,                                                          ");
        sql.append("       T_RI_SOA_CURRENCY B,                                                 ");
        sql.append("       T_RI_SOA_SECTION C,                                                  ");
        sql.append("       T_RI_SOA_SECTION_ITEM D                                              ");
        sql.append(" WHERE A.SOA_ID = B.SOA_ID                                                  ");
        sql.append("   AND B.SOA_CURRENCY_ID = C.SOA_CURRENCY_ID                                ");
        sql.append("   AND C.SOA_SECTION_ID = D.SOA_SECTION_ID                                  ");
        sql.append("   AND A.FINANCIAL_YEAR IS NOT NULL                                         ");
        sql.append("   AND A.FINANCIAL_QUARTER IS NOT NULL                                      ");
        sql.append("   AND A.SOA_ID = ?                                                         ");
        sql.append(" GROUP BY A.SOA_ID,                                                         ");
        sql.append("          A.CEDENT_YEAR,                                                    ");
        sql.append("          A.CEDENT_QUARTER,                                                 ");
        sql.append("          A.CONTRACT_ID,                                                    ");
        sql.append("          C.CONTRAC_COMP_ID,                                                ");
        sql.append("          B.CURRENCY_CODE,                                                  ");
        sql.append("          D.ENTRY_CODE,                                                     ");
        sql.append("          A.SOA_TEXT                                                        ");
        sql.append(" ORDER BY A.CONTRACT_ID, C.CONTRAC_COMP_ID, A.CEDENT_YEAR, A.CEDENT_QUARTER ");

        List<SoaEntry> compSectionList = this.getJdbcTemplate().query(sql.toString(), new Object[] { soaId },
                new BeanPropertyRowMapper<SoaEntry>(SoaEntry.class));
        return compSectionList;
    }

    public List<Long> collectSoa4FN() {
        List<Long> sectionIDlist = new ArrayList<Long>();
        JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
        StringBuilder sql = new StringBuilder();
        sql.append("  select ss.soa_id                       ");
        sql.append("    from t_ri_soa s, t_ri_soa_section ss         ");
        sql.append("   where s.soa_status = 2                        ");
        sql.append("     and s.soa_id = ss.soa_id                    ");
        sql.append("     and s.financial_year is null                ");
        sql.append("      and s.financial_quarter is null            ");
        List<Map<String, Object>> result = jt.queryForList(sql.toString());
        for (Map<String, Object> sectionIDResult : result) {
            if (sectionIDResult.get("soa_id") != null) {
                BigDecimal sectionIDBD = (BigDecimal) sectionIDResult.get("soa_id");
                sectionIDlist.add(Long.parseLong(sectionIDBD.toString()));
            }
        }
        return sectionIDlist;
    }

    public void setFNYearAndQuaterBySectionID(Date postDate, List<String> sectionIDReceiveFromFNlist) {
        JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
        StringBuilder setionIDArray = new StringBuilder();
        setionIDArray.append("(");
        for (String sectionID : sectionIDReceiveFromFNlist) {
            setionIDArray.append(sectionID).append(",");
        }
        setionIDArray.append("0)");

        YearQuarter yq = new YearQuarter(postDate);
        String fn = yq.toString();

        StringBuilder sqlBuild = new StringBuilder(" ");
        sqlBuild.append(
                " update t_ri_soa s set s.financial_year = '" + fn.substring(0, 4) + "' , s.financial_quarter = '"
                        + fn.substring(5, 6) + "' where s.soa_id in " + setionIDArray.toString() + " ");
        jt.execute(sqlBuild.toString());

    }

    public Long getSoaInfoCountByConditionsAndStatus(SoaSearchVO soaSearchVO) {

        StringBuilder sqlBuf = new StringBuilder("SELECT count(s) FROM TRiSoa s WHERE 1=1");

        if (soaSearchVO != null) {
            if (soaSearchVO.getContractCode() != null && !"".equals(soaSearchVO.getContractCode())) {
                sqlBuf.append(" and  s.contractCode =:contractCode ");
            }

            sqlBuf.append(" and  s.soaStatus != :soaStatus ");

            if (soaSearchVO.getCedentYear() != null && soaSearchVO.getCedentYear() != 0
                    && !"".equals(soaSearchVO.getCedentYear())) {
                sqlBuf.append(" and  s.cedentYear =:cedentYear ");
            }
            if (soaSearchVO.getCedentQuarter() != null && soaSearchVO.getCedentQuarter() != 0
                    && !"".equals(soaSearchVO.getCedentQuarter())) {
                sqlBuf.append(" and  s.cedentQuarter =:cedentQuarter ");
            }
            if (soaSearchVO.getUwYear() != null && soaSearchVO.getUwYear() != 0
                    && !"".equals(soaSearchVO.getUwYear())) {
                sqlBuf.append(" and  s.uwYear =:uwYear ");
            }

        }

        final Query query = this.em.createQuery(sqlBuf.toString());

        if (soaSearchVO != null) {
            if (soaSearchVO.getContractCode() != null && !"".equals(soaSearchVO.getContractCode())) {
                query.setParameter("contractCode", soaSearchVO.getContractCode());
            }

            query.setParameter("soaStatus", "3");

            if (soaSearchVO.getCedentYear() != null && soaSearchVO.getCedentYear() != 0
                    && !"".equals(soaSearchVO.getCedentYear())) {
                query.setParameter("cedentYear", Integer.valueOf(String.valueOf(soaSearchVO.getCedentYear())));
            }
            if (soaSearchVO.getCedentQuarter() != null && soaSearchVO.getCedentQuarter() != 0
                    && !"".equals(soaSearchVO.getCedentQuarter())) {
                query.setParameter("cedentQuarter", Integer.valueOf(String.valueOf(soaSearchVO.getCedentQuarter())));
            }
            if (soaSearchVO.getUwYear() != null && soaSearchVO.getUwYear() != 0
                    && !"".equals(soaSearchVO.getUwYear())) {
                query.setParameter("uwYear", soaSearchVO.getUwYear());
            }

        }

        Long resultCount = (Long) query.getSingleResult();

        return resultCount;
    }

    public boolean validSoaByContractSectionID(Long sectionID) {
        boolean validFlag = false;
        JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
        StringBuilder sql = new StringBuilder();
        sql.append("  select ss.soa_id                               ");
        sql.append("    from t_ri_soa s, t_ri_soa_section ss         ");
        sql.append("   where s.soa_status in (1,2)                        ");
        sql.append("     and s.soa_id = ss.soa_id                    ");
        sql.append("     and ss.contrac_comp_id = " + sectionID + "      ");

        List<Map<String, Object>> result = jt.queryForList(sql.toString());
        for (Map<String, Object> sectionIDResult : result) {
            if (sectionIDResult.get("soa_id") != null) {
                validFlag = true;
            }
        }
        return validFlag;
    }

}
