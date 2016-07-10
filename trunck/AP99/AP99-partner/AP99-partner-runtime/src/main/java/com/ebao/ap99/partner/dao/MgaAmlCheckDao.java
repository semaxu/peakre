/**
 * 
 */
package com.ebao.ap99.partner.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.ebao.ap99.partner.entity.TAmlCheck;
import com.ebao.ap99.partner.entity.TMgaAmlCheck;
import com.ebao.ap99.partner.entity.TPartner;
import com.ebao.ap99.partner.model.Partner;
import com.ebao.ap99.partner.model.PartnerQuery;
import com.ebao.unicorn.platform.foundation.context.AppUser;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class MgaAmlCheckDao extends BaseDao<TMgaAmlCheck> {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Class<TMgaAmlCheck> getEntityClass() {
		return TMgaAmlCheck.class;
	}
    
    public List<TMgaAmlCheck> findListByPartner(TPartner partner) {
        StringBuffer sqlBuf = new StringBuffer("SELECT t FROM TMgaAmlCheck t WHERE 1=1");

        if (partner.getPartnerId()!= 0L) {
            sqlBuf.append(" and t.TPartner.partnerId=:partnerId");
        }    

        Query query = em.createQuery(sqlBuf.toString());

        if (partner.getPartnerId() !=0L) {
            query.setParameter("partnerId", partner.getPartnerId());
        }
        @SuppressWarnings("unchecked")
        List<TMgaAmlCheck> list = (List<TMgaAmlCheck>) query.getResultList();
        return list;
    }
    
    public boolean  findAmlCheck(TPartner partner){
        boolean flag = false;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT t.* FROM T_RI_BP_MGA_AML_CHECK t where  add_months(t.check_date ,12) > sysdate  ");
        
        if (partner.getPartnerId()!=0L) {
            sql.append(" and t.partner_id='").append(partner.getPartnerId()).append("'");         
        }

        List<TMgaAmlCheck> amlCheckList = this.getJdbcTemplate().query(sql.toString(), new Object[] {},
                new BeanPropertyRowMapper<TMgaAmlCheck>(TMgaAmlCheck.class));
        if(amlCheckList.size()>0){
            String amlCheckResult = amlCheckList.get(0).getAmlCheckResult();
            if("1".equals(amlCheckResult))
            flag = true;
        }
        return flag;
    }
	
    public boolean  findDeclinedAmlCheck(Partner partner){
        boolean flag = false;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT t.aml_check_result FROM T_RI_BP_MGA_AML_CHECK t where  1 = 1  ");
        
        if (partner.getPartnerId()!=0L) {
            sql.append(" and t.partner_id='").append(partner.getPartnerId()).append("' order by insert_time desc");         
        }

        List<TMgaAmlCheck> amlCheckList = this.getJdbcTemplate().query(sql.toString(), new Object[] {},
                new BeanPropertyRowMapper<TMgaAmlCheck>(TMgaAmlCheck.class));
        if(amlCheckList.size()>0){
            String amlCheckResult = amlCheckList.get(0).getAmlCheckResult();
            if("2".equals(amlCheckResult))
            flag = true;
        }
        return flag;
    } 
    
    public boolean  findPendingAmlCheck(Partner partner){
        boolean flag = true;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT t.* FROM T_RI_BP_MGA_AML_CHECK t where   add_months(t.check_date ,12) > sysdate    ");
        
        if (partner.getPartnerId()!=0L) {
            sql.append(" and t.partner_id='").append(partner.getPartnerId()).append("'");         
        }

        List<TMgaAmlCheck> amlCheckList = this.getJdbcTemplate().query(sql.toString(), new Object[] {},
                new BeanPropertyRowMapper<TMgaAmlCheck>(TMgaAmlCheck.class));
        if(amlCheckList.size()>0){
            String amlCheckResult = amlCheckList.get(0).getAmlCheckResult();
            if("1".equals(amlCheckResult))
            flag = false;
        }
        return flag;
    }
    public String  findDeclinedBy(Partner partner){
        long approvedBy = 0 ;
        String approvedByName = null;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT t.aml_check_result FROM T_RI_BP_MGA_AML_CHECK t where  1 = 1  ");
        
        if (partner.getPartnerId()!=0L) {
            sql.append(" and t.partner_id='").append(partner.getPartnerId()).append("' order by insert_time desc");         
        }

        List<TAmlCheck> amlCheckList = this.getJdbcTemplate().query(sql.toString(), new Object[] {},
                new BeanPropertyRowMapper<TAmlCheck>(TAmlCheck.class));
        if(amlCheckList.size()>0){
            String amlCheckResult = amlCheckList.get(0).getAmlCheckResult();
            if("2".equals(amlCheckResult))
                approvedBy = amlCheckList.get(0).getCheckBy();
                AppUser user = getUserInfo(approvedBy);
                approvedByName = user.getRealName();
        }
        
        return approvedByName;
    } 
    public AppUser getUserInfo(long userId) {

        StringBuilder sql = new StringBuilder();

        sql.append("  select user_id,user_name  from t_pub_user  where User_id=?  ");
        

        AppUser user = new AppUser();

        this.getJdbcTemplate().query(sql.toString(), new Object[] { userId }, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                user.setUserId(rs.getLong("user_id"));
                user.setUserName(rs.getString("user_name"));
                
            }
        });
        return user;

    }
}
