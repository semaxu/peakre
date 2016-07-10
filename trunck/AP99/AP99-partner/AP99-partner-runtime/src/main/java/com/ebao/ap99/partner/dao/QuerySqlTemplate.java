/**
 * 
 */
package com.ebao.ap99.partner.dao;

import org.apache.commons.lang.StringUtils;

import com.ebao.ap99.partner.model.Partner;


public class QuerySqlTemplate {

	public String getQueryPartnerSql(Partner partner){
		StringBuilder sql = new StringBuilder();
		sql.append(" select t1.*,insert_time as sort_time ,decode(t1.business_partner_category,2,t1.short_name,1,t1.first_name||' '||t1.last_name) as Name from t_ri_bp_partner t1 where 1=1 ");
		
		if (StringUtils.isNotBlank(partner.getBusinessPartnerId())) {
			sql.append(" and t1.business_partner_id='").append(partner.getBusinessPartnerId()).append("'");			
		}
		if (StringUtils.isNotBlank(partner.getBusinessPartnerCategory())) {
			sql.append(" and t1.business_partner_category='").append(partner.getBusinessPartnerCategory()).append("'");
		}
	      if (StringUtils.isNotBlank(partner.getCountry())) {
	            sql.append(" and t1.country='").append(partner.getCountry()).append("'");
	        }
		if (StringUtils.isNotBlank(partner.getTradingName())) {
			sql.append(" and (  upper(t1.trading_name) like'%").append(partner.getTradingName().toUpperCase()).append("%'");
			sql.append(" or  upper(t1.short_name) like'%").append(partner.getTradingName().toUpperCase()).append("%'");
			sql.append(" or t1.partner_id in ( select partner_id from t_ri_bp_partner_log t where  upper(t.trading_name) like '%").append(partner.getTradingName().toUpperCase()).append("%'");
			sql.append(" or upper(t.last_name) like '%").append(partner.getTradingName().toUpperCase()).append("%'");
			sql.append(" or upper(t.first_name) like '%").append(partner.getTradingName().toUpperCase()).append("%'");
			sql.append(" or upper(t.short_name) like '%").append(partner.getTradingName().toUpperCase()).append("%'");
			sql.append(" or upper(t.registration_name) like '%").append(partner.getTradingName().toUpperCase()).append("%'");
			sql.append(" or upper(t.full_name) like '%").append(partner.getTradingName().toUpperCase()).append("%' )");
			sql.append(" or upper(t1.last_name) like '%").append(partner.getTradingName().toUpperCase()).append("%'");
			sql.append(" or upper(t1.registration_name) like '%").append(partner.getTradingName().toUpperCase()).append("%'");
			sql.append(" or upper(t1.first_name) like '%").append(partner.getTradingName().toUpperCase()).append("%' )");
		}
		if (StringUtils.isNotBlank(partner.getStatus())) {
		    sql.append(" and t1.status='").append(partner.getStatus()).append("'");
		}
        if(StringUtils.isNotBlank(partner.getRoleSelect())){
            sql.append(" and t1.role_select like '%").append(partner.getRoleSelect()).append("%'");
        }if(partner.isPending()){
            sql.append(" and ( ( t1.role_select like '%1%' and not exists (SELECT t.* FROM T_RI_BP_AML_CHECK t where add_months(t.check_date, 12) > sysdate and aml_check_result = 1 and t.partner_id = t1.partner_id ))");
            sql.append(" or  ( t1.role_select like '%2%' and not exists (SELECT t.* FROM T_RI_BP_BROKER_AML_CHECK t where add_months(t.check_date, 12) > sysdate and aml_check_result = 1 and t.partner_id = t1.partner_id))");
            sql.append(" or  (t1.role_select like '%4%' and not exists (SELECT t.* FROM T_RI_BP_AML_CHECK t where add_months(t.check_date, 12) > sysdate and aml_check_result = 1 and t.partner_id = t1.partner_id))");
            sql.append(" or  (t1.role_select like '%5%' and not exists (SELECT t.* FROM T_RI_BP_AML_CHECK t where add_months(t.check_date, 12) > sysdate and aml_check_result = 1 and t.partner_id = t1.partner_id)))");
        }

        sql.append(" order by sort_time desc ");
		return sql.toString();
		
	}
	

}
