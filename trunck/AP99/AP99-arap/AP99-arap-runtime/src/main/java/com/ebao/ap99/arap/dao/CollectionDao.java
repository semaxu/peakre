package com.ebao.ap99.arap.dao;

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
import com.ebao.ap99.arap.entity.Collection;
import com.ebao.ap99.arap.vo.TransactionQueryCondition;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class CollectionDao extends BaseDao<Collection> {
	
	@Override
	public Class<Collection> getEntityClass() {
		return Collection.class;
	}
	
	public void save(Collection collection) throws Exception{
		Assert.isNotNull(collection);
		this.insertOrUpdate(collection);
	}
	
	@SuppressWarnings("unchecked")
	public Collection getByReceiptNo(String receiptNo) throws Exception{
		Query query = this.getEntityManager().createNamedQuery("Collection.findByReceiptNo", Collection.class);
		query.setParameter("receiptNo", receiptNo);
		List<Collection> list = query.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
	public boolean isBankAccountUsed(String bankAccountNo) throws Exception{
		CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Collection> root = query.from(this.getEntityClass());
		query.select(builder.count(root));
		Expression<Boolean> expression = builder.equal(root.get("bankAccountNo"), bankAccountNo);
		query.where(expression);
		Long count = this.getEntityManager().createQuery(query).getSingleResult().longValue();
		return count>0? true:false;
	}
	
	public String buildCollectionQuerySQL(TransactionQueryCondition condition, Map<String, Object> params) throws Exception{
		StringBuilder sql = new StringBuilder();
		sql.append(" select distinct col.receipt_No as settle_no, ");
		sql.append(FinanceTransactionType.COLLECTION.getType()).append(" as settle_type,");
		sql.append(" col.update_time operate_date ");
		sql.append("   from t_ri_bcp_collection        col, ");
		sql.append("        t_ri_bcp_settlement_detail settle ");
		sql.append("   where col.collection_id = settle.collection_id ");
		sql.append("   and ( ");
		sql.append(buildSQLOnARAP(condition, params));
		sql.append(" 	or ");
		sql.append(buildSQLOnSuspense(condition, params));
		sql.append(" ) ");
		
		if(condition.getTransType() != null && FinanceTransactionType.REVERSAL.getType().compareTo(condition.getTransType())==0){
			sql.append("         and col.status = -1 ");
		}
		if(condition.isNoReversal()){
			sql.append("         and col.status != -1 ");
		}
		if(StringUtils.isNotBlank(condition.getFinancePartnerCode())){
			sql.append("         and col.payer_code = :c_payer_code ");
			params.put("c_payer_code", condition.getFinancePartnerCode());
		}
		if(StringUtils.isNotBlank(condition.getTransNo())){
			sql.append("         and col.receipt_no = :c_receipt_no ");
			params.put("c_receipt_no", condition.getTransNo());
		}
		if(StringUtils.isNotBlank(condition.getSettleDateFrom())){
			sql.append("         and col.collection_date >= to_date(:c_settleDateFrom,'DD/MM/YYYY') ");
			params.put("c_settleDateFrom", condition.getSettleDateFrom());
		}
		if(StringUtils.isNotBlank(condition.getSettleDateTo())){
			sql.append("         and col.collection_date <= to_date(:c_settleDateTo,'DD/MM/YYYY') ");
			params.put("c_settleDateTo", condition.getSettleDateTo());
		}
		if(StringUtils.isNotBlank(condition.getTransDateFrom())){
			sql.append("         and col.update_time >= to_date(:c_transDateFrom,'DD/MM/YYYY') ");
			params.put("c_transDateFrom", condition.getTransDateFrom());
		}
		if(StringUtils.isNotBlank(condition.getTransDateTo())){
			sql.append("         and trunc(col.update_time) <= to_date(:c_transDateTo,'DD/MM/YYYY') ");
			params.put("c_transDateTo", condition.getTransDateTo());
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
		sql.append("        t_ri_contract_info contract ");
		sql.append("         where settle.fee_id = fee.fee_id ");
		sql.append("         and fee.arap_type in (1,2) ");
		sql.append("         and fee.contract_id = contract.cont_comp_id ");
		if(StringUtils.isNotBlank(condition.getBrokerCode())){
			sql.append("         and contract.broker = :c_broker1 ");
			params.put("c_broker1", condition.getBrokerCode());
		}
		if(StringUtils.isNotBlank(condition.getPartnerCode())){
			sql.append("         and contract.cedent = :c_cedent1 ");
			params.put("c_cedent1", condition.getPartnerCode());
		}
		if(StringUtils.isNotBlank(condition.getStatementId())){
			sql.append("         and fee.biz_trans_type in (:c_transtype_state1,:c_transtype_state11) ");
			sql.append("         and fee.biz_trans_no = :c_transNo_state1 ");
			params.put("c_transtype_state1", BizTransactionType.STATEMENT.getType());
			params.put("c_transtype_state11", BizTransactionType.IBNR_UPLOAD.getType());
			params.put("c_transNo_state1", condition.getStatementId());
		}
		if(StringUtils.isNotBlank(condition.getClaimNo())){
			sql.append("         and fee.biz_trans_type in (:c_transType_claim1,:c_transType_claim11) ");
			sql.append("         and fee.biz_trans_no = :c_transNo_claim11 ");
			params.put("c_transType_claim1", BizTransactionType.CLAIM_RESERVE.getType());
			params.put("c_transType_claim11", BizTransactionType.CLAIM_SETTLEMENT.getType());
			params.put("c_transNo_claim11", condition.getClaimNo());
		}
		if(StringUtils.isNotBlank(condition.getContractIdArray())){
			int idx = 1;
			sql.append("         and contract.contract_code in ('' ");
			List<String> contractIdList = Arrays.asList(condition.getContractIdArray().split(","));
			for(String contractId : contractIdList){
				if(StringUtils.isNotBlank(contractId)){
					sql.append(" ,:c_contractCode1_"+idx);
					params.put("c_contractCode1_"+idx, contractId.trim());
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
					sql.append(" ,:c_feeId1_"+idx);
					params.put("c_feeId1_"+idx, feeId.trim());
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
						sql.append(" ,:c_contractCode2_"+idx);
						params.put("c_contractCode2_"+idx, contractId.trim());
						idx++;
					}
				}
				sql.append(" ) ");
			}
			if(StringUtils.isNotBlank(condition.getPartnerCode())){
				sql.append("         and sus.partner_code = :c_partner_code2 ");
				params.put("c_partner_code2", condition.getPartnerCode());
			}
			sql.append(") ");
		}
		return sql.toString();
	}
	
	public List<Collection> getDependentTransaction(Integer financeTransType, Long transId, Date transDate) throws Exception{
		Assert.isNotNull(financeTransType);
		Assert.isNotNull(transId);
		
		LinkedList<Object> params = new LinkedList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select distinct b.*  ");
		sql.append("  from T_RI_BCP_SETTLEMENT_DETAIL a ");
		sql.append("  ,t_ri_bcp_collection b ");
		sql.append("  where b.status = ? ");
		params.add(SettlementStatus.NORMAL.getType());
		sql.append("  and a.collection_id = b.collection_id ");
		sql.append("  and b.insert_time > ? ");
		params.add(transDate);
		if(FinanceTransactionType.COLLECTION.getType().compareTo(financeTransType)==0){
			sql.append("  and b.collection_id != ? ");
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
		
		List<Collection> resultList = this.getJdbcTemplate().query(sql.toString(), params.toArray(new Object[0]),
				new BeanPropertyRowMapper<Collection>(Collection.class));
		return resultList;
	}
}
