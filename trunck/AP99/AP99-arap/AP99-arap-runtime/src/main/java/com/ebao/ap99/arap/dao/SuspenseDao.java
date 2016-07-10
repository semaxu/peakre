package com.ebao.ap99.arap.dao;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import scala.actors.threadpool.Arrays;

import com.ebao.ap99.arap.constant.FinanceTransactionType;
import com.ebao.ap99.arap.constant.SuspenseType;
import com.ebao.ap99.arap.constant.YesNoType;
import com.ebao.ap99.arap.entity.Suspense;
import com.ebao.ap99.arap.vo.SuspenseQueryCondition;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class SuspenseDao extends BaseDao<Suspense> {

	@Override
	public Class<Suspense> getEntityClass() {
		return Suspense.class;
	}
	
	public void save(Suspense suspense) throws Exception{
		Assert.isNotNull(suspense);
		this.insertOrUpdate(suspense);
	}
	
	public void saveAll(List<Suspense> suspenseList) throws Exception{
		for(Suspense suspense : suspenseList){
			this.save(suspense);
		}
	}
	
	public List<Suspense> getByCondition(SuspenseQueryCondition condition) throws Exception{
		LinkedList<Object> params = new LinkedList<Object>();
		StringBuffer sql = new StringBuffer();
		String contractSuspenseSql = buildSqlByContractInfo(condition, params);
		String partnerSuspenseSql = buildSqlPartnerInfo(condition, params);
		sql.append(" select * from (");
		sql.append(contractSuspenseSql);
		if(!StringUtils.isBlank(partnerSuspenseSql)){
			sql.append(" union  ");
			sql.append(partnerSuspenseSql);
		}
		sql.append(") order by suspense_id desc");
		List<Suspense> suspenseList = this.getJdbcTemplate().query(sql.toString(), params.toArray(new Object[0]),
				new BeanPropertyRowMapper<Suspense>(Suspense.class));
		return suspenseList;
	}
	
	public void invalidSuspense(Long suspenseId) throws Exception{
		Suspense suspense = this.load(suspenseId);
		if(suspense != null){
			suspense.setStatus(YesNoType.NO.getType());
			this.save(suspense);
		}
	}
	
	@SuppressWarnings("unchecked")
	private String buildSqlByContractInfo(SuspenseQueryCondition condition, LinkedList<Object> params) throws Exception{
		StringBuilder sql = new StringBuilder();
		sql.append(" select distinct s.* ");
		sql.append(", contract.contract_code ");
		sql.append("   from t_ri_bcp_suspense s ");
		sql.append("   ,t_ri_contract_info contract ");
		sql.append("   where s.suspense_type = ? ");
		params.add(SuspenseType.CONTRACT_SUSPENSE.getType());
		sql.append("   and s.balance != 0 ");
		sql.append("   and s.status = ? ");
		params.add(YesNoType.YES.getType());
		sql.append(" and s.contract_id = contract.cont_comp_id ");
		if(StringUtils.isNotBlank(condition.getBrokerCode())){
			sql.append(" and contract.broker = ? ");
			params.add(condition.getBrokerCode());
		}
		if(StringUtils.isNotBlank(condition.getPartnerCode())){
			sql.append(" and s.partner_code = ? ");
			params.add(condition.getPartnerCode());
		}
		if(StringUtils.isNotBlank(condition.getContractIdArray())){
			sql.append(" and contract.contract_code in ('0' ");
			List<String> contractIdList = Arrays.asList(condition.getContractIdArray().split(","));
			for(String contractId : contractIdList){
				if(StringUtils.isNotBlank(contractId)){
					sql.append(" ,? ");
					params.add(contractId.trim());
				}
			}
			sql.append(" ) ");
		}
		return sql.toString();
	}
	
	private String buildSqlPartnerInfo(SuspenseQueryCondition condition, LinkedList<Object> params) throws Exception{
		if(StringUtils.isNotBlank(condition.getContractIdArray())){
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append(" select distinct s.*, null contract_code ");
		sql.append("   from t_ri_bcp_suspense s ");
		sql.append("   where s.suspense_type = ? ");
		params.add(SuspenseType.BUSINESS_PARTNER_SUSPENSE.getType());
		sql.append("   and s.balance != 0 ");
		sql.append("   and s.status = ? ");
		params.add(YesNoType.YES.getType());
		if(StringUtils.isNotBlank(condition.getPartnerCode())){
			sql.append(" and s.partner_code = ? ");
			params.add(condition.getPartnerCode());
		}
		return sql.toString();
	}
	
	public List<Suspense> findSettlementSuspenseByTransId(Integer transType, Long transId) throws Exception{
		Assert.isNotNull(transId);
		Assert.isNotNull(transType);
		
		LinkedList<Object> params = new LinkedList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select settle.settle_amount,settle.settle_currency_code,settle.mark_off_amount, settle.outstanding_amount, settle.exchange_rate, settle.settle_date, suspense.* ");
		sql.append(" from t_ri_bcp_settlement_detail settle ");
		sql.append(" , t_ri_bcp_suspense suspense ");
		sql.append(" where settle.suspense_id = suspense.suspense_id ");
		sql.append(" and settle.suspense_id is not null ");
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
		List<Suspense> resultList = this.getJdbcTemplate().query(sql.toString(), params.toArray(new Object[0]),
				new BeanPropertyRowMapper<Suspense>(Suspense.class));
		return resultList;
	}
}
