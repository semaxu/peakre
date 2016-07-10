package com.ebao.ap99.arap.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import scala.actors.threadpool.Arrays;

import com.ebao.ap99.arap.constant.BizTransactionType;
import com.ebao.ap99.arap.constant.FinanceTransactionType;
import com.ebao.ap99.arap.constant.SettlementStatus;
import com.ebao.ap99.arap.entity.Offset;
import com.ebao.ap99.arap.vo.TransactionQueryCondition;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class OffsetDao extends BaseDao<Offset> {

	@Override
	public Class<Offset> getEntityClass() {
		return Offset.class;
	}

	public void save(Offset offset) throws Exception{
		Assert.isNotNull(offset);
		this.insertOrUpdate(offset);
	}
	
	@SuppressWarnings("unchecked")
	public Offset getByOffsetNo(String offsetNo) throws Exception{
		Query query = this.getEntityManager().createNamedQuery("Offset.findByOffsetNo", Offset.class);
		query.setParameter("offsetNo", offsetNo);
		List<Offset> list = query.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public String buildOffsetQuerySQL(TransactionQueryCondition condition, Map<String, Object> params) throws Exception{
		if(StringUtils.isNotBlank(condition.getFinancePartnerCode())){
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append(" select distinct a.offset_no as settle_no, ");
		sql.append(FinanceTransactionType.INTERNAL_OFFSET.getType()).append(" as settle_type,");
		sql.append(" a.update_time operate_date ");
		sql.append("   from t_ri_bcp_offset        a, ");
		sql.append("        t_ri_bcp_settlement_detail settle, ");
		sql.append("        t_ri_bcp_fee               fee, ");
		sql.append("        t_ri_contract_info contract ");
		sql.append("   where a.offset_id = settle.offset_id ");
		sql.append("         and settle.fee_id = fee.fee_id ");
		sql.append("         and fee.arap_type in (1,2) ");
		sql.append("         and fee.contract_id = contract.cont_comp_id ");
		
		if(condition.getTransType() != null && FinanceTransactionType.REVERSAL.getType().compareTo(condition.getTransType())==0){
			sql.append("         and a.status = -1 ");
		}
		if(condition.isNoReversal()){
			sql.append("         and a.status != -1 ");
		}
		if(StringUtils.isNotBlank(condition.getTransNo())){
			sql.append("         and a.offset_no = :o_offset_no ");
			params.put("o_offset_no", condition.getTransNo());
		}
		//No offset result return for collection / payment date condition
		if(StringUtils.isNotBlank(condition.getSettleDateFrom()) || StringUtils.isNotBlank(condition.getSettleDateTo())){
			sql.append("         and 1=2 ");
		}
		if(StringUtils.isNotBlank(condition.getTransDateFrom())){
			sql.append("         and a.update_time >= to_date(:o_transDateFrom,'DD/MM/YYYY') ");
			params.put("o_transDateFrom", condition.getTransDateFrom());
		}
		if(StringUtils.isNotBlank(condition.getTransDateTo())){
			sql.append("         and trunc(a.update_time) <= to_date(:o_transDateTo,'DD/MM/YYYY') ");
			params.put("o_transDateTo", condition.getTransDateTo());
		}
		if(StringUtils.isNotBlank(condition.getBrokerCode())){
			sql.append("         and contract.broker = :o_broker ");
			params.put("o_broker", condition.getBrokerCode());
		}
		if(StringUtils.isNotBlank(condition.getPartnerCode())){
			sql.append("         and contract.cedent = :o_cedent ");
			params.put("o_cedent", condition.getPartnerCode());
		}
		if(StringUtils.isNotBlank(condition.getStatementId())){
			sql.append("         and fee.biz_trans_type in (:o_transtype_state1,:o_transtype_state11) ");
			sql.append("         and fee.biz_trans_no = :o_transNo_state1 ");
			params.put("o_transtype_state1", BizTransactionType.STATEMENT.getType());
			params.put("o_transtype_state11", BizTransactionType.IBNR_UPLOAD.getType());
			params.put("o_transNo_state1", condition.getStatementId());
		}
		if(StringUtils.isNotBlank(condition.getClaimNo())){
			sql.append("         and fee.biz_trans_type in (:o_transType_claim1,:o_transType_claim11) ");
			sql.append("         and fee.biz_trans_no = :o_transNo_claim11 ");
			params.put("o_transType_claim1", BizTransactionType.CLAIM_RESERVE.getType());
			params.put("o_transType_claim11", BizTransactionType.CLAIM_SETTLEMENT.getType());
			params.put("o_transNo_claim11", condition.getClaimNo());
		}
		if(StringUtils.isNotBlank(condition.getContractIdArray())){
			int idx = 1;
			sql.append("         and contract.contract_code in ('' ");
			List<String> contractIdList = Arrays.asList(condition.getContractIdArray().split(","));
			for(String contractId : contractIdList){
				if(StringUtils.isNotBlank(contractId)){
					sql.append(" ,:o_contractCode1_"+idx);
					params.put("o_contractCode1_"+idx, contractId.trim());
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
					sql.append(" ,:o_feeId1_"+idx);
					params.put("o_feeId1_"+idx, feeId.trim());
					idx++;
				}
			}
			sql.append(" ) ");
		}
		return sql.toString();
	}
	
	/**
	 * Get related offsets in which some ARAP or suspense of specified finance transaction was processed later
	 * @param financeTransType
	 * @param transId
	 * @return
	 * @throws Exception
	 */
	public List<Offset> getDependentTransaction(Integer financeTransType, Long transId, Date transDate) throws Exception{
		Assert.isNotNull(financeTransType);
		Assert.isNotNull(transId);
		
		List<Offset> resultList = new ArrayList<Offset>();
		resultList.addAll(getDependentTransactionOnARAP(financeTransType, transId, transDate));
		//for the suspenses generated in collection, check if any other payment processed them after the collection
		if(FinanceTransactionType.COLLECTION.getType().equals(financeTransType)){
			resultList.addAll(getDependentTransactionOnSuspense(financeTransType, transId));
		}
		return resultList;
	}
	
	private List<Offset> getDependentTransactionOnARAP(Integer financeTransType, Long transId, Date transDate) throws Exception{
		Assert.isNotNull(financeTransType);
		Assert.isNotNull(transId);
		
		LinkedList<Object> params = new LinkedList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select distinct b.*  ");
		sql.append("  from T_RI_BCP_SETTLEMENT_DETAIL a ");
		sql.append("  ,t_ri_bcp_offset b ");
		sql.append("  where b.status = ? ");
		params.add(SettlementStatus.NORMAL.getType());
		sql.append("  and a.offset_Id = b.offset_Id ");
		sql.append("  and b.insert_time > ? ");
		params.add(transDate);
		if(FinanceTransactionType.INTERNAL_OFFSET.getType().compareTo(financeTransType)==0){
			sql.append("  and b.offset_Id != ? ");
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
		
		List<Offset> resultList = this.getJdbcTemplate().query(sql.toString(), params.toArray(new Object[0]),
				new BeanPropertyRowMapper<Offset>(Offset.class));
		return resultList;
	}
	
	//for the suspenses generated in collection, check if any other payment processed them after the collection
		private List<Offset> getDependentTransactionOnSuspense(Integer financeTransType, Long transId) throws Exception{
			Assert.isNotNull(financeTransType);
			Assert.isNotNull(transId);
			
			LinkedList<Object> params = new LinkedList<Object>();
			
			StringBuilder sql = new StringBuilder();
			sql.append(" select distinct b.*  ");
			sql.append("  from T_RI_BCP_SETTLEMENT_DETAIL a ");
			sql.append("  ,t_ri_bcp_offset b ");
			sql.append("  where b.status = ? ");
			params.add(SettlementStatus.NORMAL.getType());
			sql.append("  and a.suspense_id != null ");
			sql.append("  and a.offset_id != null ");
			sql.append("  and a.offset_id = b.offset_id ");
			sql.append("  and exists ");
			sql.append("  ( select 1  ");
			sql.append("    from T_RI_BCP_SETTLEMENT_DETAIL ra ");
			sql.append("    where ra.suspense_id = a.suspense_id ");
			sql.append("    and ra.collection_id = ?) ");
			
			params.add(transId);
			
			List<Offset> resultList = this.getJdbcTemplate().query(sql.toString(), params.toArray(new Object[0]),
					new BeanPropertyRowMapper<Offset>(Offset.class));
			return resultList;
		}
}
