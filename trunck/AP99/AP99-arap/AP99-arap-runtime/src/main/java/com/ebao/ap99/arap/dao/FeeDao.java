package com.ebao.ap99.arap.dao;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import scala.actors.threadpool.Arrays;

import com.ebao.ap99.arap.constant.BizTransactionType;
import com.ebao.ap99.arap.constant.PostStatus;
import com.ebao.ap99.arap.entity.Fee;
import com.ebao.ap99.arap.vo.FeeQueryCondition;
import com.ebao.ap99.arap.vo.GLInterfaceVO;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.Assert;

public class FeeDao extends BaseDao<Fee> {

	@Override
	public Class<Fee> getEntityClass() {
		return Fee.class;
	}

	public void save(Fee fee) throws Exception{
		Assert.isNotNull(fee);
		this.insertOrUpdate(fee);
	}
	
	public void saveAll(List<Fee> feeList) throws Exception{
		for(Fee fee : feeList){
			save(fee);
		}
	}
	
	public List<GLInterfaceVO> getGlInterfaceFee(Integer... arapTypes) throws Exception{
		LinkedList<Object> params = new LinkedList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select a.sign, a.entry_code, a.contract_id, a.contract_cate, a.currency_code, a.amount,a.fee_id, a.special_sbumt, 0 cash_balance_type ");
		sql.append(",a.biz_trans_type, a.biz_trans_id,a.biz_trans_no, a.trans_date biz_trans_date  ");
		sql.append(" from t_ri_bcp_fee a ");
		sql.append(" where a.need_post = 1 ");
		sql.append(" and a.post_status = ? ");
		params.add(PostStatus.WAIT_FOR_POST.getType());
		
		if(arapTypes.length > 0){
			sql.append(" and a.arap_type in (0");
			for(int i=0; i<arapTypes.length; i++){
				sql.append(",?");
				params.add(arapTypes[i]);
			}
			sql.append(")");
		}
		
		List<GLInterfaceVO> resultList = this.getJdbcTemplate().query(sql.toString(), params.toArray(new Object[0]),
				new BeanPropertyRowMapper<GLInterfaceVO>(GLInterfaceVO.class));
		return resultList;
	}
	
	public List<GLInterfaceVO> getInterfaceFee(Long transId) throws Exception{
		LinkedList<Object> params = new LinkedList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select a.sign, a.entry_code, a.contract_id, a.contract_cate, a.currency_code, a.amount,a.fee_id, a.special_sbumt, 0 cash_balance_type ");
		sql.append(",a.biz_trans_type, a.biz_trans_id,a.biz_trans_no, a.trans_date biz_trans_date  ");
		sql.append(" from t_ri_bcp_fee a ");
		sql.append(" where a.need_post = 1 ");
		sql.append(" and a.post_status = ? ");
		params.add(PostStatus.WAIT_FOR_POST.getType());
		sql.append(" and a.trans_id = ? ");
		params.add(transId);
		
		List<GLInterfaceVO> resultList = this.getJdbcTemplate().query(sql.toString(), params.toArray(new Object[0]),
				new BeanPropertyRowMapper<GLInterfaceVO>(GLInterfaceVO.class));
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Fee> getByCondition(FeeQueryCondition condition) throws Exception{
		LinkedList<Object> params = new LinkedList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select fee.*,contract.contract_code ");
		sql.append("   from t_ri_bcp_fee               fee, ");
		sql.append("        t_ri_contract_info contract ");
		sql.append("   where fee.contract_id = contract.cont_comp_id ");
		sql.append("         and fee.arap_type in (1,2) ");
		if(!condition.isQueryMode()){
			sql.append("         and fee.balance != 0 ");
		}
		/*sql.append("         and fee.status = ? ");
		params.add(FeeStatus.OUTSTANDING.getType());*/
		
		if(StringUtils.isNotBlank(condition.getBrokerCode())){
			sql.append("         and contract.broker = ? ");
			params.add(condition.getBrokerCode());
		}
		if(StringUtils.isNotBlank(condition.getPartnerCode())){
			sql.append("         and contract.cedent = ? ");
			params.add(condition.getPartnerCode());
		}
		if(StringUtils.isNotBlank(condition.getStatementId())){
			sql.append("         and fee.biz_trans_type in (?,?) ");
			params.add(BizTransactionType.STATEMENT.getType());
			params.add(BizTransactionType.IBNR_UPLOAD.getType());
			sql.append("         and fee.biz_trans_no = ? ");
			params.add(condition.getStatementId());
		}
		if(StringUtils.isNotBlank(condition.getClaimNo())){
			sql.append("         and fee.biz_trans_type in (?,?) ");
			params.add(BizTransactionType.CLAIM_RESERVE.getType());
			params.add(BizTransactionType.CLAIM_SETTLEMENT.getType());
			sql.append("         and fee.biz_trans_no = ? ");
			params.add(condition.getClaimNo());
		}
		if(StringUtils.isNotBlank(condition.getContractIdArray())){
			sql.append("         and contract.contract_code in ('' ");
			List<String> contractIdList = Arrays.asList(condition.getContractIdArray().split(","));
			for(String contractId : contractIdList){
				if(StringUtils.isNotBlank(contractId)){
					sql.append(" ,? ");
					params.add(contractId.trim());
				}
			}
			sql.append(" ) ");
		}
		sql.append(" order by fee.currency_code, fee.due_date ");
		List<Fee> resultList = this.getJdbcTemplate().query(sql.toString(), params.toArray(new Object[0]),
				new BeanPropertyRowMapper<Fee>(Fee.class));
		return resultList;
	}
}
