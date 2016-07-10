package com.ebao.ap99.arap.dao;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.ebao.ap99.arap.constant.Constants;
import com.ebao.ap99.arap.constant.FinanceConsistants;
import com.ebao.ap99.arap.constant.FinanceTransactionType;
import com.ebao.ap99.arap.constant.GLCashBalanceType;
import com.ebao.ap99.arap.constant.SettlementExchangeSourceType;
import com.ebao.ap99.arap.entity.SettlementDetail;
import com.ebao.ap99.arap.vo.GLInterfaceVO;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class SettlementDetailDao extends BaseDao<SettlementDetail> {

	@Override
	public Class<SettlementDetail> getEntityClass() {
		return SettlementDetail.class;
	}

	public void save(SettlementDetail settlementDetail) throws Exception{
		Assert.isNotNull(settlementDetail);
		this.insertOrUpdate(settlementDetail);
	}
	
	public void saveAll(List<SettlementDetail> settlementDetailList) throws Exception{
		for(SettlementDetail settlementDetail : settlementDetailList){
			this.save(settlementDetail);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<SettlementDetail> getByCollectionId(Long collectionId) throws Exception{
		Query query = this.getEntityManager().createNamedQuery("SettlementDetail.findByCollectionId", this.getEntityClass());
		query.setParameter("collectionId", collectionId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<SettlementDetail> getByPaymentId(Long paymentId) throws Exception{
		Query query = this.getEntityManager().createNamedQuery("SettlementDetail.findByPaymentId", this.getEntityClass());
		query.setParameter("paymentId", paymentId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<SettlementDetail> getByOffsetId(Long offsetId) throws Exception{
		Query query = this.getEntityManager().createNamedQuery("SettlementDetail.findByOffsetId", this.getEntityClass());
		query.setParameter("offsetId", offsetId);
		return query.getResultList();
	}
	
	public List<SettlementDetail> findSettlementFeeByTransId(Integer transType, Long transId, Long settlementGroupId) throws Exception{
		Assert.isNotNull(transId);
		Assert.isNotNull(transType);
		
		LinkedList<Object> params = new LinkedList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select fee.trans_id, fee.balance, fee.sign, fee.entry_code,fee.contract_id,fee.contract_code,fee.biz_trans_type,fee.biz_trans_no,fee.amount*fee.sign amount,fee.due_date,settle.* ");
		sql.append(" from t_ri_bcp_settlement_detail settle ");
		sql.append(" , t_ri_bcp_fee fee ");
		sql.append(" where settle.fee_id = fee.fee_id ");
		sql.append(" and settle.suspense_id is null ");
		sql.append(" and settle.reverse_id is null ");
		if(transType == FinanceTransactionType.COLLECTION.getType()){
			sql.append(" and settle.collection_id = ? ");
			params.add(transId);
		}
		if(transType == FinanceTransactionType.PAYMENT.getType()){
			sql.append(" and settle.payment_id = ? ");
			params.add(transId);
		}
		if(transType == FinanceTransactionType.INTERNAL_OFFSET.getType()){
			sql.append(" and settle.offset_id = ? ");
			params.add(transId);
		}
		if(settlementGroupId != null){
			sql.append(" and settle.settle_group_id = ? ");
			params.add(settlementGroupId);
		}
		sql.append(" order by fee.currency_code, fee.due_date  ");
		//sql.append(" order by fee.contract_id,fee.biz_trans_no, fee.cur_period,fee.fee_id ");
		List<SettlementDetail> resultList = this.getJdbcTemplate().query(sql.toString(), params.toArray(new Object[0]),
				new BeanPropertyRowMapper<SettlementDetail>(SettlementDetail.class));
		return resultList;
	}
	
	public List<SettlementDetail> findFullySettleInfoByFeeId(Long feeId) throws Exception{
		LinkedList<Object> params = new LinkedList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select a.* ");
		sql.append(" from T_RI_BCP_SETTLEMENT_DETAIL a  ");
		sql.append(" where a.fully_settle = 1  ");
		sql.append("       and a.fee_id = ? ");
		sql.append("       order by a.settle_detail_id desc ");
		params.add(feeId);
		
		List<SettlementDetail> resultList = this.getJdbcTemplate().query(sql.toString(), params.toArray(new Object[0]),
				new BeanPropertyRowMapper<SettlementDetail>(SettlementDetail.class));
		return resultList;
	}
	
	public List<GLInterfaceVO> getGlInterfaceCashFee(Integer settleTransType, Integer settleSourceType) throws Exception{
		LinkedList<Object> params = new LinkedList<Object>();
		Integer cashBalanceType = getCashBalanceType(settleTransType,settleSourceType);
		
		StringBuilder sql = new StringBuilder();
		sql.append(buildGLInterfaceSelectSql(settleTransType, settleSourceType, cashBalanceType));
		sql.append(" from t_ri_bcp_settlement_detail a ");
		sql.append("      ,t_ri_bcp_fee b ");
		sql.append(",").append(getSettleTransTableName(settleTransType)).append(" c");
		sql.append("  where a.fee_id = b.fee_id(+) ");
		if(settleSourceType == SettlementExchangeSourceType.SETTLE_DTL_AMOUNT.getType()){
			sql.append("        and a.settle_amount is not null ");
		}
		if(settleSourceType == SettlementExchangeSourceType.SETTLE_DTL_GAINLOSS.getType()){
			sql.append("        and a.gain_loss is not null ");
		}
		if(settleSourceType == SettlementExchangeSourceType.SETTLE_DTL_SETTLE_DIFF.getType()){
			sql.append("        and a.amount_diff is not null ");
		}
		if(FinanceTransactionType.COLLECTION.getType() == settleTransType){
			sql.append("        and a.collection_id = c.collection_id ");
			sql.append("        and a.reverse_id is null ");
		}
		if(FinanceTransactionType.PAYMENT.getType() == settleTransType){
			sql.append("        and a.payment_id = c.payment_id ");
			sql.append("        and a.reverse_id is null ");
		}
		if(FinanceTransactionType.INTERNAL_OFFSET.getType() == settleTransType){
			sql.append("        and a.offset_id = c.offset_id ");
			sql.append("        and a.reverse_id is null ");
		}
		if(FinanceTransactionType.REVERSAL.getType() == settleTransType){
			sql.append("        and a.reverse_id = c.reverse_id ");
		}
		sql.append("        and a.need_post = 1 ");
		sql.append("        and a.post_status = 0 ");
		if(settleSourceType == SettlementExchangeSourceType.SETTLE_DTL_SUSPENSE.getType()){
			sql.append(" and a.suspense_id is not null");
		}else{
			sql.append(" and a.suspense_id is null");
		}
		
		List<GLInterfaceVO> resultList = this.getJdbcTemplate().query(sql.toString(), params.toArray(new Object[0]),
				new BeanPropertyRowMapper<GLInterfaceVO>(GLInterfaceVO.class));
		return resultList;
	}
	
	private Integer getCashBalanceType(Integer settleTransType,Integer settleSourceType) throws Exception{
		Integer cashBalanceType = GLCashBalanceType.NORMAL.getValue();
		if(FinanceTransactionType.COLLECTION.getType() == settleTransType){
			if(settleSourceType == SettlementExchangeSourceType.SETTLE_DTL_SUSPENSE.getType()){
				cashBalanceType = GLCashBalanceType.BALANCE_GENERATION.getValue();
			}
		}
		if(FinanceTransactionType.PAYMENT.getType() == settleTransType){
			if(settleSourceType == SettlementExchangeSourceType.SETTLE_DTL_SUSPENSE.getType()){
				cashBalanceType = GLCashBalanceType.BALANCE_REFUND.getValue();
			}
		}
		if(FinanceTransactionType.INTERNAL_OFFSET.getType() == settleTransType){
			if(settleSourceType == SettlementExchangeSourceType.SETTLE_DTL_SUSPENSE.getType()){
				cashBalanceType = GLCashBalanceType.BALANCE_REFUND.getValue();
			}else{
				cashBalanceType = GLCashBalanceType.INTERNAL_OFFSET.getValue();
			}
		}
		return cashBalanceType;
	}
	
	private String getSettleTransTableName(Integer settleTransType){
		if(FinanceTransactionType.COLLECTION.getType() == settleTransType){
			return "t_ri_bcp_collection";
		}
		if(FinanceTransactionType.PAYMENT.getType() == settleTransType){
			return "t_ri_bcp_payment";
		}
		if(FinanceTransactionType.INTERNAL_OFFSET.getType() == settleTransType){
			return "t_ri_bcp_offset";
		}
		if(FinanceTransactionType.REVERSAL.getType() == settleTransType){
			return "t_ri_bcp_reverse";
		}
		return null;
	}
	
	private String buildGLInterfaceSelectSql(Integer transType, Integer settleSourceType, Integer cashBalanceType) throws Exception{
		StringBuilder sql = new StringBuilder();
		String entryCode = getEntryCodeForGLSettlement(settleSourceType);
		sql.append("select ").append(StringUtils.isBlank(entryCode)? "b.entry_code" : "'"+entryCode+"'").append(" entry_code ");
		sql.append(",b.contract_id, b.contract_cate, b.special_sbumt ");
		sql.append("        ,a.fee_id, a.settle_detail_id ");
		sql.append(", b.biz_trans_type  ");
		sql.append(",").append(settleSourceType).append(" settle_source_type  ");
		if(settleSourceType == SettlementExchangeSourceType.SETTLE_DTL_AMOUNT.getType()
				||settleSourceType == SettlementExchangeSourceType.SETTLE_DTL_SUSPENSE.getType()){
			sql.append("        ,a.settle_amount amount, a.settle_currency_code currency_code ");
		}
		if(settleSourceType == SettlementExchangeSourceType.SETTLE_DTL_GAINLOSS.getType()){
			sql.append("        ,a.gain_loss amount, 'USD' currency_code ");
		}
		if(settleSourceType == SettlementExchangeSourceType.SETTLE_DTL_SETTLE_DIFF.getType()){
			sql.append("        ,a.amount_diff amount, a.currency_code ");
		}
		sql.append(" ,").append(transType).append(" settle_type ");
		if(transType == FinanceTransactionType.COLLECTION.getType()){
			sql.append(" , a.collection_id settle_id ");
		}
		if(transType == FinanceTransactionType.PAYMENT.getType()){
			sql.append(" , a.payment_id settle_id ");
		}
		if(transType == FinanceTransactionType.INTERNAL_OFFSET.getType()){
			sql.append(" , a.offset_id settle_id ");
		}
		if(transType == FinanceTransactionType.REVERSAL.getType()){
			sql.append(" , a.reverse_id settle_id ");
		}
		sql.append(" ,a.reverse_id reverseId ");
		sql.append(" ,a.suspense_id ");
		sql.append(",").append(cashBalanceType).append(" cash_balance_type ");//need to identify balance generation/refund in collection
		return sql.toString();
	}
	
	private String getEntryCodeForGLSettlement(Integer settleSourceType) throws Exception{
		if(settleSourceType == SettlementExchangeSourceType.SETTLE_DTL_SUSPENSE.getType()){
			return FinanceConsistants.GL_ENTRY_CODE_BALANCE;
		}
		if(settleSourceType == SettlementExchangeSourceType.SETTLE_DTL_GAINLOSS.getType()){
			return FinanceConsistants.GL_ENTRY_CODE_GAIN_LOSS;
		}
		if(settleSourceType == SettlementExchangeSourceType.SETTLE_DTL_SETTLE_DIFF.getType()){
			return FinanceConsistants.GL_ENTRY_CODE_SETTLE_DIFF;
		}
		if(settleSourceType == SettlementExchangeSourceType.SETTLE_DTL_REVALUATION.getType()){
			return FinanceConsistants.GL_ENTRY_CODE_REVALUATION;
		}
		return null;
	}
	
	public List<SettlementDetail> getSettlementHistory(Integer bizTransType, Long bizTransId) throws Exception{
		LinkedList<Object> params = new LinkedList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select  a.entry_code, b.settle_amount, b.settle_date, b.settle_currency_code, b.collection_id, b.payment_id, b.offset_id ");
		sql.append(" from t_ri_bcp_fee a ");
		sql.append("      ,t_ri_bcp_settlement_detail b ");
		sql.append("      where a.fee_id= b.fee_id ");
		sql.append("            and a.biz_trans_type = ? ");
		sql.append("            and a.biz_trans_id = ? ");
		sql.append("            and b.reverse_id is null ");
		sql.append("            order by b.settle_date, b.collection_id, b.payment_id, b.offset_id ");
		params.add(bizTransType);
		params.add(bizTransId);
		
		List<SettlementDetail> resultList = this.getJdbcTemplate().query(sql.toString(), params.toArray(new Object[0]),
				new BeanPropertyRowMapper<SettlementDetail>(SettlementDetail.class));
		return resultList;
	}
}
