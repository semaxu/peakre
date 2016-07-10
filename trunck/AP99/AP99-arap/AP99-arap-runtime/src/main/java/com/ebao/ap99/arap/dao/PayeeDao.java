package com.ebao.ap99.arap.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.ebao.ap99.arap.entity.Payee;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class PayeeDao extends BaseDao<Payee> {

	@Override
	public Class<Payee> getEntityClass() {
		return Payee.class;
	}
	
	@SuppressWarnings("unchecked")
	public List<Payee> getByPaymentId(Long paymentId) throws Exception{
		Query query = this.getEntityManager().createNamedQuery("Payee.findByPaymentId", this.getEntityClass());
		query.setParameter("paymentId", paymentId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public String getPayeeNameByPaymentId(Long paymentId) throws Exception{
		StringBuilder sql = new StringBuilder();
		sql.append(" select DISTINCT B.BUSINESS_PARTNER_NAME ");
		sql.append(" from T_RI_BCP_PAYEE a ");
		sql.append("      ,V_RI_BP_PARTNER B ");
		sql.append("  WHERE A.PARTNER_CODE = B.BUSINESS_PARTNER_ID ");
		sql.append("   AND A.PAYMENT_ID = :paymentId ");
		Query query = this.getEntityManager().createNativeQuery(sql.toString());
		query.setParameter("paymentId", paymentId);
		
		List<String> resultList = query.getResultList();
		return StringUtils.join(resultList, ";");
	}
}
