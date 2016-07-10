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

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.ebao.ap99.accounting.model.ActualizationInfoVO;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * Date: Mar 30, 2016 10:07:40 AM
 * 
 * @author xiaoyu.yang
 */
public class ActualizationInfoDao extends BaseDao<ActualizationInfoVO> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Class<ActualizationInfoVO> getEntityClass() {
        return ActualizationInfoVO.class;
    }

    public List<ActualizationInfoVO> findListByCondition(ActualizationInfoVO condition) {
        List<ActualizationInfoVO> list = null;
        StringBuilder sql = new StringBuilder("");

        sql.append("SELECT A.CONT_COMP_ID,                                                      ");
        sql.append("       A.CONTRACT_CODE,                                                     ");
        sql.append("       A.CONTRACT_NAME,                                                     ");
        sql.append("       A.BROKER_REF,                                                        ");
        sql.append("       A.CEDENT_REF,                                                        ");
        sql.append("       A.SUBCLASS,                                                          ");
        sql.append("       A.UW_YEAR,                                                           ");
        sql.append("       MAX(C.ACTUALIZED) AS ACTUALIZED                                      ");
        sql.append("  FROM T_RI_CONTRACT_INFO      A,                                           ");
        sql.append("       T_RI_CONTRACT_STRUCTURE B,                                           ");
        sql.append("       T_RI_CONT_PRICING       C,                                           ");
        sql.append("       T_RI_CONTRACT_ENDO      D                                            ");
        sql.append(" WHERE A.LATEST_STATUS IN ('4','5')                                         ");
        sql.append("   AND B.PARENT_ID = A.CONT_COMP_ID                                         ");
        sql.append("   AND B.CONT_COMP_ID = C.CONT_COMP_ID                                      ");
        if (StringUtils.isNoneEmpty(condition.getContractIds())) {
            String contractIds = condition.getContractIds();
            String[] contractIdList = contractIds.split("\n");
            sql.append("   AND A.CONTRACT_CODE IN (");
            for (int i = 0; i < contractIdList.length; i++) {
                sql.append("'" + contractIdList[i] + "',");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
        }
        if (StringUtils.isNoneEmpty(condition.getBroker())) {
            sql.append("   AND A.BROKER = " + condition.getBroker());
        }
        if (StringUtils.isNoneEmpty(condition.getCedent())) {
            sql.append("   AND A.CEDENT = " + condition.getCedent());
        }
        if (StringUtils.isNoneEmpty(condition.getSubclass())) {
            sql.append("   AND A.SUBCLASS = " + condition.getSubclass());
        }
        if (condition.getUwYear() != null) {
            sql.append("   AND A.UW_YEAR = " + condition.getUwYear());
        }
        if (condition.isExceeding()) {
            sql.append("   AND ADD_MONTHS(A.REINS_ENDING, 24) < SYSDATE                         ");
        } else {
            sql.append("   AND ADD_MONTHS(A.REINS_ENDING, 24) >= SYSDATE                        ");
        }
        sql.append(" GROUP BY A.CONT_COMP_ID,                                                   ");
        sql.append("          A.CONTRACT_CODE,                                                  ");
        sql.append("          A.CONTRACT_NAME,                                                  ");
        sql.append("          A.BROKER_REF,                                                     ");
        sql.append("          A.CEDENT_REF,                                                     ");
        sql.append("          A.SUBCLASS,                                                       ");
        sql.append("          A.UW_YEAR                                                         ");

        list = this.getJdbcTemplate().query(sql.toString(), new Object[] {},
                new BeanPropertyRowMapper<ActualizationInfoVO>(ActualizationInfoVO.class));
        return list;
    }
}
