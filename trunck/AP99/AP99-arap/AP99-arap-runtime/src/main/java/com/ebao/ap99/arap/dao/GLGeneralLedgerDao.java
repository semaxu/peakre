package com.ebao.ap99.arap.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.ebao.ap99.accounting.AccountingService;
import com.ebao.ap99.accounting.YearQuarter;
import com.ebao.ap99.arap.constant.PostStatus;
import com.ebao.ap99.arap.entity.GLGeneralLedger;
import com.ebao.ap99.arap.service.GLService;
import com.ebao.ap99.arap.vo.FinancialItemVO;
import com.ebao.ap99.arap.vo.GeneralLedgerDTO;
import com.ebao.unicorn.platform.data.helper.DataHelper;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class GLGeneralLedgerDao extends BaseDao<GLGeneralLedger> {
	@Autowired
	private DataHelper dataHelper;
	
	@Autowired
	private GLService glService;
	
	@Autowired
	private AccountingService accoutingService;
	
	@Override
	public Class<GLGeneralLedger> getEntityClass() {
		return GLGeneralLedger.class;
	}
	
	public void saveAll(List<GLGeneralLedger> generalLedgerList) throws Exception{
		if(CollectionUtils.isNotEmpty(generalLedgerList)){
			for(GLGeneralLedger generalLedger : generalLedgerList){
				this.insertOrUpdate(generalLedger);
			}
		}
	}

	/**
	 * Get general ledger list with specific conditions
	 * @param condition
	 * @param pageNo
	 * 		  if pageNo = 0, return all records
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<GLGeneralLedger> queryGeneralLedger(GeneralLedgerDTO condition, int pageNo, int pageSize) throws Exception{
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		String sql = buildGeneralLedgerSql(condition, paramsMap);
		Query query = this.getEntityManager().createQuery(sql.toString(), GLGeneralLedger.class);
		String key;
		for(Iterator<String> ite = paramsMap.keySet().iterator();ite.hasNext();){
			key = ite.next();
			query.setParameter(key, paramsMap.get(key));
		}
		if(pageNo != 0){
			int start = dataHelper.firstPageResult(pageNo, pageSize);
			query.setFirstResult(start);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}
	
	public Long countGeneralLedger(GeneralLedgerDTO condition) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select count(a) ");
		sql.append(buildGeneralLedgerSql(condition, params));
		Query query = this.getEntityManager().createQuery(sql.toString());
		String key;
		for(Iterator<String> ite = params.keySet().iterator();ite.hasNext();){
			key = ite.next();
			query.setParameter(key, params.get(key));
		}
		return Long.valueOf(query.getSingleResult().toString());
	}
	
	private String buildGeneralLedgerSql(GeneralLedgerDTO condition,Map<String, Object> paramsMap) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder sql = new StringBuilder();
		sql.append(" from GLGeneralLedger a ");
		sql.append(" where a.currencyCode is not null ");
		if(StringUtils.isNotBlank(condition.getGlAccount())){
			sql.append(" and a.glAccountNo = :glAccountNo ");
			paramsMap.put("glAccountNo", condition.getGlAccount());
		}
		if(StringUtils.isNotBlank(condition.getCurrency())){
			sql.append(" and a.currencyCode = :currencyCode ");
			paramsMap.put("currencyCode", condition.getCurrency());
		}
		if(condition.getPostStatus() != null){
			sql.append(" and a.postStatus = :postStatus ");
			paramsMap.put("postStatus", condition.getPostStatus());
		}
		if(condition.getPostDateFrom() != null){
			sql.append(" and a.postDate >= :postDateFrom ");
			paramsMap.put("postDateFrom", format.parse(condition.getPostDateFrom()));
		}
		if(condition.getPostDateTo() != null){
			sql.append(" and trunc(a.postDate) <= :postDateTo ");
			paramsMap.put("postDateTo", format.parse(condition.getPostDateTo()));
		}
		if(condition.getDocumentDateFrom() != null){
			sql.append(" and a.docDate >= :docDateFrom ");
			paramsMap.put("docDateFrom", format.parse(condition.getDocumentDateFrom()));
		}
		if(condition.getDocumentDateTo() != null){
			sql.append(" and trunc(a.docDate) <= :docDateTo ");
			paramsMap.put("docDateTo", format.parse(condition.getDocumentDateTo()));
		}
		sql.append(" order by a.generalLedgerId desc ");
		return sql.toString();
	}

    public List<String> getFnQuarters(long level, Long conCompId) throws Exception {
        List<String> fnQuarters = null;
        StringBuilder sql = new StringBuilder("");
        YearQuarter currentQuarter = accoutingService.currentFinancialQuarter();
        sql.append("SELECT FIN_QUARTER from (    ");
        sql.append(" select DISTINCT NVL(b.PREDICTED_QUARTER,'").append(currentQuarter.toString()).append("') FIN_QUARTER ");
        sql.append("  FROM T_RI_GL_SUB_LEDGER B, T_RI_BCP_FEE C            ");
        sql.append(" WHERE B.FEE_ID = C.FEE_ID                                                       ");
        sql.append("   AND B.SETTLE_DETAIL_ID IS NULL                                                ");
        sql.append("   AND B.GL_ACCOUNT_TYPE = 'D'                                                   ");
        if (level == 1) {
            sql.append("   AND C.CONTRACT_ID = ?                                                     ");
        } else {
            sql.append("   AND C.SECTION_ID = ?                                                      ");
        }
        sql.append(" ) ORDER BY FIN_QUARTER                                                          ");

        fnQuarters = this.getJdbcTemplate().queryForList(sql.toString(), new Object[] { conCompId }, String.class);
        return fnQuarters;
    }

    public List<FinancialItemVO> getFnView(long level, long conCompId, List<String> fnQuarters) throws Exception{
        List<FinancialItemVO> viewTable = null;
        StringBuilder sql = new StringBuilder("");

        YearQuarter currentQuarter = accoutingService.currentFinancialQuarter();
        sql.append("SELECT ENTRY_CODE AS ITEM,   CURRENCY_CODE,                  ");
        int i = 1;
        for (String fnQuarter : fnQuarters) {
            sql.append("       SUM(DECODE(FIN_QUARTER, '" + fnQuarter + "', AMOUNT, NULL)) CEDENT_Q" + i + ",");
            i++;
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append("  FROM (SELECT C.ENTRY_CODE,                                 ");
        sql.append("               B.AMOUNT * C.SIGN AMOUNT,                     ");
        sql.append("               B.CURRENCY_CODE,                              ");
        sql.append("               NVL(B.PREDICTED_QUARTER,'").append(currentQuarter.toString()).append("') FIN_QUARTER");
        sql.append("          FROM T_RI_GL_SUB_LEDGER     B,                     ");
        sql.append("               T_RI_BCP_FEE           C                      ");
        sql.append("         WHERE B.FEE_ID = C.FEE_ID                           ");
        sql.append("           AND B.SETTLE_DETAIL_ID IS NULL                    ");
        sql.append("           AND B.GL_ACCOUNT_TYPE = 'D'                       ");
        sql.append("           and b.currency_code = c.currency_code             ");
        if (level == 1) {
            sql.append("           AND C.CONTRACT_ID = ?                         ");
        } else {
            sql.append("           AND C.SECTION_ID = ?                          ");
        }
        sql.append("                  )                                          ");
        sql.append(" GROUP BY ENTRY_CODE,CURRENCY_CODE                           ");

        viewTable = this.getJdbcTemplate().query(sql.toString(), new Object[] { conCompId },
                new BeanPropertyRowMapper<FinancialItemVO>(FinancialItemVO.class));
        return viewTable;
    }
    
    @SuppressWarnings("unchecked")
	public List<GLGeneralLedger> getPostPendingData(Date postDate) throws Exception{
    	Query query = this.getEntityManager().createNamedQuery("GLGeneralLedger.findPostPendingDataByPostDate", this.getEntityClass());
		query.setParameter("postDate", postDate);
		query.setParameter("postStatus", PostStatus.POSTED.getType());
		return query.getResultList();
    }
}
