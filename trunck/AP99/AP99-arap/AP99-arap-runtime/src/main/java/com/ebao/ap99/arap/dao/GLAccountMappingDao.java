package com.ebao.ap99.arap.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.ebao.ap99.arap.entity.GLAccountMappingDef;
import com.ebao.ap99.arap.vo.GLInterfaceVO;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class GLAccountMappingDao extends BaseDao<GLAccountMappingDef> {

	@Override
	public Class<GLAccountMappingDef> getEntityClass() {
		return GLAccountMappingDef.class;
	}
	
	@SuppressWarnings("unchecked")
	public List<GLAccountMappingDef> findByEntryCodeAndCashBalanceType(GLInterfaceVO glInterface) throws Exception{
		Query query = this.getEntityManager().createNamedQuery("GLAccountMappingDef.findByEntryCodeAndCashBalanceType", GLAccountMappingDef.class);
		query.setParameter("entryCode", glInterface.getEntryCode());
		query.setParameter("cashBalanceType", glInterface.getCashBalanceType());
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public String getAccountNameByCode(String glAccountNo) throws Exception{
		StringBuilder sql = new StringBuilder();
		sql.append(" select distinct a.description ");
		sql.append(" from T_RI_GL_ACCOUNT_MAPPING_DEF a  ");
		sql.append(" where a.gl_account_no = :gl_account_no ");
		Query query = this.getEntityManager().createNativeQuery(sql.toString());
		query.setParameter("gl_account_no", glAccountNo);
		List<String> resultList = query.getResultList();
		if(CollectionUtils.isNotEmpty(resultList)){
			return resultList.get(0);
		}
		return null;
	}
}
