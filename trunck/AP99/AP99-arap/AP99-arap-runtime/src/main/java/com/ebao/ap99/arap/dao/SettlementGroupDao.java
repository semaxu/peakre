package com.ebao.ap99.arap.dao;

import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.ebao.ap99.arap.constant.FinanceTransactionType;
import com.ebao.ap99.arap.entity.SettlementGroup;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class SettlementGroupDao extends BaseDao<SettlementGroup> {

	@Override
	public Class<SettlementGroup> getEntityClass() {
		return SettlementGroup.class;
	}

	public void save(SettlementGroup settlementGroup) throws Exception{
		Assert.isNotNull(settlementGroup);
		this.insertOrUpdate(settlementGroup);
	}
	
	public void saveAll(List<SettlementGroup> settlementGroupList) throws Exception{
		for(SettlementGroup settlementGroup : settlementGroupList){
			this.save(settlementGroup);
		}
	}
	
	/**
	 * query settlement groups with finance transaction type and number.
	 * @param transType @link FinanceTransactionType
	 * @param transId 	finance transaction id, like collectionId, paymentId and offsetId
	 * @return
	 * @throws Exception
	 */
	public List<SettlementGroup> findByTransId(Integer transType, Long transId) throws Exception{
		Assert.isNotNull(transId);
		Assert.isNotNull(transType);
		
		LinkedList<Object> params = new LinkedList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select distinct settle_g.* ");
		sql.append(" from t_ri_bcp_settlement_group settle_g ");
		sql.append(" ,t_ri_bcp_settlement_detail settle ");
		sql.append(" , t_ri_bcp_fee fee ");
		sql.append(" where settle.fee_id = fee.fee_id ");
		sql.append(" and settle.settle_group_id = settle_g.settle_group_id ");
		sql.append(" and settle.suspense_id is null ");
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
		List<SettlementGroup> resultList = this.getJdbcTemplate().query(sql.toString(), params.toArray(new Object[0]),
				new BeanPropertyRowMapper<SettlementGroup>(SettlementGroup.class));
		return resultList;
	}
}
