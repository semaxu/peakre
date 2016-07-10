package com.ebao.ap99.arap.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import scala.actors.threadpool.Arrays;

import com.ebao.ap99.arap.constant.BizTransactionType;
import com.ebao.ap99.arap.constant.FinanceTransactionType;
import com.ebao.ap99.arap.constant.SettlementStatus;
import com.ebao.ap99.arap.entity.Payment;
import com.ebao.ap99.arap.vo.TransactionQueryCondition;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class PaymentDao extends BaseDao<Payment> {

	@Override
	public Class<Payment> getEntityClass() {
		return Payment.class;
	}

	public void save(Payment payment) throws Exception{
		Assert.isNotNull(payment);
		this.insertOrUpdate(payment);
	}
	
	@SuppressWarnings("unchecked")
	public Payment getByPaymentNo(String paymentNo) throws Exception{
		Query query = this.getEntityManager().createNamedQuery("Payment.findByPaymentNo", Payment.class);
		query.setParameter("paymentNo", paymentNo);
		List<Payment> list = query.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
	public String buildPaymentQuerySQL(TransactionQueryCondition condition, Map<String, Object> params) throws Exception{
		StringBuilder sql = new StringBuilder();
		sql.append(" select distinct a.payment_no as settle_no, ");
		sql.append(FinanceTransactionType.PAYMENT.getType()).append(" as settle_type,");
		sql.append(" a.update_time operate_date ");
		sql.append("   from t_ri_bcp_payment        a, ");
		sql.append("        t_ri_bcp_settlement_detail settle ");
		sql.append("   where a.payment_id = settle.payment_id ");
		sql.append("   and ( ");
		sql.append(buildSQLOnARAP(condition, params));
		sql.append(" 	or ");
		sql.append(buildSQLOnSuspense(condition, params));
		sql.append(" ) ");
		
		if(condition.getTransType() != null && FinanceTransactionType.REVERSAL.getType().compareTo(condition.getTransType())==0){
			sql.append("         and a.status = -1 ");
		}
		if(condition.isNoReversal()){
			sql.append("         and a.status != -1 ");
		}
		if(StringUtils.isNotBlank(condition.getTransNo())){
			sql.append("         and a.payment_no = :p_paymentNo ");
			params.put("p_paymentNo", condition.getTransNo());
		}
		if(StringUtils.isNotBlank(condition.getSettleDateFrom())){
			sql.append("         and a.payment_date >= to_date(:p_settleDateFrom,'DD/MM/YYYY') ");
			params.put("p_settleDateFrom",condition.getSettleDateFrom());
			
		}
		if(StringUtils.isNotBlank(condition.getSettleDateTo())){
			sql.append("         and a.payment_date <= to_date(:p_settleDateTo,'DD/MM/YYYY') ");
			params.put("p_settleDateTo", condition.getSettleDateTo());
		}
		if(StringUtils.isNotBlank(condition.getTransDateFrom())){
			sql.append("         and a.update_time >= to_date(:p_transDateFrom,'DD/MM/YYYY') ");
			params.put("p_transDateFrom", condition.getTransDateFrom());
		}
		if(StringUtils.isNotBlank(condition.getTransDateTo())){
			sql.append("         and trunc(a.update_time) <= to_date(:p_transDateTo,'DD/MM/YYYY') ");
			params.put("p_transDateTo", condition.getTransDateTo());
		}
		return sql.toString();
	}
	
	/**
	 * Build SQL for query settlement that's on credit / debit
	 * @param condition
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String buildSQLOnARAP(TransactionQueryCondition condition, Map<String, Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("   exists ( ");
		sql.append("        select 1 from ");
		sql.append("        t_ri_bcp_fee               fee, ");
		sql.append("        t_ri_bcp_payee             payee, ");
		sql.append("        t_ri_contract_info contract ");
		sql.append("        where settle.fee_id = fee.fee_id ");
		sql.append("         and fee.arap_type in (1,2) ");
		sql.append("         and fee.contract_id = contract.cont_comp_id ");
		sql.append("         and a.payment_id = payee.payment_id ");
		if(StringUtils.isNotBlank(condition.getBrokerCode())){
			sql.append("         and contract.broker = :p_broker1 ");
			params.put("p_broker1", condition.getBrokerCode());
		}
		if(StringUtils.isNotBlank(condition.getPartnerCode())){
			sql.append("         and contract.cedent = :p_cedent1 ");
			params.put("p_cedent1", condition.getPartnerCode());
		}
		if(StringUtils.isNotBlank(condition.getStatementId())){
			sql.append("         and fee.biz_trans_type in (:p_transtype_state1,:p_transtype_state11) ");
			sql.append("         and fee.biz_trans_no = :p_transNo_state1 ");
			params.put("p_transtype_state1", BizTransactionType.STATEMENT.getType());
			params.put("p_transtype_state11", BizTransactionType.IBNR_UPLOAD.getType());
			params.put("p_transNo_state1", condition.getStatementId());
		}
		if(StringUtils.isNotBlank(condition.getFinancePartnerCode())){
			sql.append("         and payee.partner_code = :p_partner_code1 ");
			params.put("p_partner_code1", condition.getFinancePartnerCode());
		}
		if(StringUtils.isNotBlank(condition.getClaimNo())){
			sql.append("         and fee.biz_trans_type in (:p_transType_claim1,:p_transType_claim11) ");
			sql.append("         and fee.biz_trans_no = :p_transNo_claim11 ");
			params.put("p_transType_claim1", BizTransactionType.CLAIM_RESERVE.getType());
			params.put("p_transType_claim11", BizTransactionType.CLAIM_SETTLEMENT.getType());
			params.put("p_transNo_claim11", condition.getClaimNo());
		}
		if(StringUtils.isNotBlank(condition.getContractIdArray())){
			int idx = 1;
			sql.append("         and contract.contract_code in ('' ");
			List<String> contractIdList = Arrays.asList(condition.getContractIdArray().split(","));
			for(String contractId : contractIdList){
				if(StringUtils.isNotBlank(contractId)){
					sql.append(" ,:p_contractCode1_"+idx);
					params.put("p_contractCode1_"+idx, contractId.trim());
					idx++;
				}
			}
			sql.append(" ) ");
		}
		if(StringUtils.isNotBlank(condition.getFeeIdArray())){
			int idx = 1;
			sql.append("         and fee.fee_id in (0 ");
			List<String> feeIdList = Arrays.asList(condition.getFeeIdArray().split(","));
			for(String feeId : feeIdList){
				if(StringUtils.isNotBlank(feeId)){
					sql.append(" ,:p_feeId1_"+idx);
					params.put("p_feeId1_"+idx, feeId.trim());
					idx++;
				}
			}
			sql.append(" ) ");
		}
		sql.append(") ");
		return sql.toString();
	}
	
	/**
	 * Build SQL for query settlement that's only on suspense
	 * @param condition
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String buildSQLOnSuspense(TransactionQueryCondition condition, Map<String, Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(condition.getFeeIdArray()) //FeeIdArray is used for querying settlements with fully settle credit/debit
				||StringUtils.isNotBlank(condition.getClaimNo())
				||StringUtils.isNotBlank(condition.getBrokerCode())
				||StringUtils.isNotBlank(condition.getStatementId())){
			sql.append("   exists (select 1 from dual where 1=2) ");
		}else{
			sql.append("   exists ( ");
			sql.append("        select 1 from ");
			sql.append("        T_RI_BCP_SUSPENSE               sus ");
			if(StringUtils.isNotBlank(condition.getContractIdArray())){
				sql.append("    ,t_ri_contract_info contract ");
			}
			sql.append("         where sus.suspense_id = settle.suspense_id ");
			if(StringUtils.isNotBlank(condition.getContractIdArray())){
				sql.append("         and sus.contract_id = contract.cont_comp_id ");
				sql.append("         and contract.contract_code in ('' ");
				List<String> contractIdList = Arrays.asList(condition.getContractIdArray().split(","));
				int idx = 1;
				for(String contractId : contractIdList){
					if(StringUtils.isNotBlank(contractId)){
						sql.append(" ,:p_contractCode2_"+idx);
						params.put("p_contractCode2_"+idx, contractId.trim());
						idx++;
					}
				}
				sql.append(" ) ");
			}
			if(StringUtils.isNotBlank(condition.getPartnerCode())){
				sql.append("         and sus.partner_code = :p_partner_code2 ");
				params.put("p_partner_code2", condition.getPartnerCode());
			}
			sql.append(") ");
		}
		return sql.toString();
	}
	
	public boolean isBankAccountUsed(String bankAccountNo) throws Exception{
		CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Payment> root = query.from(this.getEntityClass());
		query.select(builder.count(root));
		Expression<Boolean> expression = builder.equal(root.get("fromBankAccountNo"), bankAccountNo);
		query.where(expression);
		Long count = this.getEntityManager().createQuery(query).getSingleResult().longValue();
		return count>0? true:false;
	}
	
	/**
	 * Get related payments in which some ARAP or suspense of specified finance transaction was processed later
	 * @param financeTransType
	 * @param transId
	 * @return
	 * @throws Exception
	 */
	public List<Payment> getDependentTransaction(Integer financeTransType, Long transId, Date transDate) throws Exception{
		Assert.isNotNull(financeTransType);
		Assert.isNotNull(transId);
		
		List<Payment> resultList = new ArrayList<Payment>();
		resultList.addAll(getDependentTransactionOnARAP(financeTransType, transId, transDate));
		//for the suspenses generated in collection, check if any other payment processed them after the collection
		if(FinanceTransactionType.COLLECTION.getType().equals(financeTransType)){
			resultList.addAll(getDependentTransactionOnSuspense(transId));
		}
		return resultList;
	}
	
	private List<Payment> getDependentTransactionOnARAP(Integer financeTransType, Long transId, Date transDate) throws Exception{
		Assert.isNotNull(financeTransType);
		Assert.isNotNull(transId);
		
		LinkedList<Object> params = new LinkedList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select distinct b.*  ");
		sql.append("  from T_RI_BCP_SETTLEMENT_DETAIL a ");
		sql.append("  ,t_ri_bcp_payment b ");
		sql.append("  where b.status = ? ");
		params.add(SettlementStatus.NORMAL.getType());
		sql.append("  and a.payment_id = b.payment_id ");
		sql.append("  and b.insert_time > ? ");
		params.add(transDate);
		if(FinanceTransactionType.PAYMENT.getType().compareTo(financeTransType)==0){
			sql.append("  and b.payment_id != ? ");
			params.add(transId);
		}
		sql.append("  and exists ");
		sql.append("  ( select 1  ");
		sql.append("    from T_RI_BCP_SETTLEMENT_DETAIL ra ");
		sql.append("    where ra.fee_id = a.fee_id ");
		if(FinanceTransactionType.COLLECTION.getType().compareTo(financeTransType)==0){
			sql.append("    and ra.collection_id = ? ");
		}
		if(FinanceTransactionType.PAYMENT.getType().compareTo(financeTransType)==0){
			sql.append("    and ra.payment_id = ? ");
		}
		if(FinanceTransactionType.INTERNAL_OFFSET.getType().compareTo(financeTransType)==0){
			sql.append("    and ra.offset_id = ? ");
		}
		sql.append(" ) ");
		
		params.add(transId);
		
		List<Payment> resultList = this.getJdbcTemplate().query(sql.toString(), params.toArray(new Object[0]),
				new BeanPropertyRowMapper<Payment>(Payment.class));
		return resultList;
	}
	
	//for the suspenses generated in collection, check if any other payment processed them after the collection
	private List<Payment> getDependentTransactionOnSuspense(Long collectionId) throws Exception{
		Assert.isNotNull(collectionId);
		
		LinkedList<Object> params = new LinkedList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select distinct b.*  ");
		sql.append("  from T_RI_BCP_SETTLEMENT_DETAIL a ");
		sql.append("  ,t_ri_bcp_payment b ");
		sql.append("  where b.status = ? ");
		params.add(SettlementStatus.NORMAL.getType());
		sql.append("  and a.suspense_id != null ");
		sql.append("  and a.payment_id != null ");
		sql.append("  and a.payment_id = b.payment_id ");
		sql.append("  and exists ");
		sql.append("  ( select 1  ");
		sql.append("    from T_RI_BCP_SETTLEMENT_DETAIL ra ");
		sql.append("    where ra.suspense_id = a.suspense_id ");
		sql.append("    and ra.collection_id = ?) ");
		params.add(collectionId);
		
		List<Payment> resultList = this.getJdbcTemplate().query(sql.toString(), params.toArray(new Object[0]),
				new BeanPropertyRowMapper<Payment>(Payment.class));
		return resultList;
	}
}
