/**
 * 
 */
package com.ebao.ap99.partner.dao;

import org.apache.commons.lang.StringUtils;

import com.ebao.ap99.partner.model.PartnerQuery;


public class QuerySqlChangeHistory {

	public String getQueryHistorySql(String  partnerId){
		StringBuilder sql = new StringBuilder();
		sql.append(" select decode(t1.business_partner_category,2,t1.trading_name,1,t1.first_name||' '||t1.last_name) as name, t1.* from t_ri_bp_change_history t1 where 1=1 ");
		
		if (StringUtils.isNotBlank(partnerId)) {
			sql.append(" and t1.partner_id='").append(partnerId).append("'");			
		}
        sql.append(" order by update_time desc ");
		return sql.toString();
		
	}
	

}
