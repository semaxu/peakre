package com.ebao.ap99.arap.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import scala.actors.threadpool.Arrays;

import com.ebao.ap99.arap.constant.BizTransactionType;
import com.ebao.ap99.arap.constant.FinanceTransactionType;
import com.ebao.ap99.arap.constant.PostStatus;
import com.ebao.ap99.arap.constant.YesNoType;
import com.ebao.ap99.arap.entity.GLSubLedger;
import com.ebao.ap99.arap.vo.SubLedgerDTO;
import com.ebao.unicorn.platform.data.helper.DataHelper;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class GLSubLedgerDao extends BaseDao<GLSubLedger> {
	@Autowired
	private DataHelper dataHelper;
	
	@Override
	public Class<GLSubLedger> getEntityClass() {
		return GLSubLedger.class;
	}
	
	public void saveAll(List<GLSubLedger> subLedgerList) throws Exception{
		if(CollectionUtils.isNotEmpty(subLedgerList)){
			for(GLSubLedger subLedger : subLedgerList){
				this.insertOrUpdate(subLedger);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<GLSubLedger> getSubLedgers(boolean onlySpecialSubmit) throws Exception{
		StringBuilder sql = new StringBuilder();
		sql.append(" select a  ");
		sql.append(" from GLSubLedger a ");
		sql.append(" where a.generalLedgerId is null ");
		sql.append("       and a.needPost = :needPost ");
		if(onlySpecialSubmit){
			sql.append("       and a.specialSubmit = :specialSubmit ");
		}
		Query query = this.getEntityManager().createQuery(sql.toString(), GLSubLedger.class);
		query.setParameter("needPost", YesNoType.YES.getType());
		if(onlySpecialSubmit){
			query.setParameter("specialSubmit", YesNoType.YES.getType());
		}
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * query sub ledger with specific condition
	 * @param condition
	 * @param pageNo 
	 * 		if pageNo = 0, will return all result
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<GLSubLedger> querySubLedgerItems(SubLedgerDTO condition, int pageNo, int pageSize) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = buildSubLedgerQuerySql(condition, params);
		Query query = this.getEntityManager().createNativeQuery(sql);
		String key;
		for(Iterator<String> ite = params.keySet().iterator();ite.hasNext();){
			key = ite.next();
			query.setParameter(key, params.get(key));
		}
		
		if(pageNo != 0){
			int start = dataHelper.firstPageResult(pageNo, pageSize);
			query.setFirstResult(start);
			query.setMaxResults(pageSize);
		}
		return buildSubLedgerResultList(query.getResultList());
	}
	
	public Long countSubLedgerItems(SubLedgerDTO condition) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from (");
		sql.append(buildSubLedgerQuerySql(condition, params));
		sql.append(")");
		Query query = this.getEntityManager().createNativeQuery(sql.toString());
		String key;
		for(Iterator<String> ite = params.keySet().iterator();ite.hasNext();){
			key = ite.next();
			query.setParameter(key, params.get(key));
		}
		return Long.valueOf(query.getSingleResult().toString());
	}
	
	@SuppressWarnings("unchecked")
	private String buildSubLedgerQuerySql(SubLedgerDTO condition, Map<String, Object> params) throws Exception{
		//String bizTransTypeName = BizTransactionType.getName(condition.getBusinessType());
		boolean isSettlementQuery = false;
		if(condition.getBusinessType() != null){
			if(BizTransactionType.getCategory(condition.getBusinessType()) == BizTransactionType.CATE_ARAP){
				isSettlementQuery = true;
			}
		}
		
		StringBuilder sql = new StringBuilder();
		//==================select clause==================
		sql.append(" select distinct ");
		sql.append(" a.gl_mapping_entry_id ");//0
		sql.append(" , a.fee_id ");//1
		sql.append(" , c.contract_code ");//2
		sql.append(" , decode(a.settle_type,").append(FinanceTransactionType.PAYMENT.getType()).append(",a.amount*-1, a.amount)").append(" amount  ");//3
		sql.append(" , a.currency_code ");//4
		sql.append(" , trunc(a.insert_time) as insert_time  ");//5
		sql.append(" , b.post_date  ");//6
		sql.append(" , nvl(b.post_status,").append(PostStatus.WAIT_FOR_POST.getType()).append(") post_status");//7
		sql.append(" , c.entry_code  ");//8
		sql.append(" , c.trans_date as generation_date ");//9
		sql.append(" , decode(a.settle_type, ").append(buildSubLedgerBizTransTypeQuerySql()).append(", c.biz_trans_type)").append(" biz_trans_type  ");//10
		sql.append(" , c.biz_trans_no  ");//11
		sql.append(" , c.biz_trans_desc  ");//12
		sql.append(" , c.biz_operator_id  ");//13
		sql.append(" , c.is_estimation ");//14
		sql.append(" , a.contract_id ");//15
		sql.append(" , a.settle_type ");//16
		sql.append(" , a.settle_id ");//17
		sql.append(" , a.settle_detail_id ");//18
		//===================table list=================
		sql.append(" from T_RI_GL_SUB_LEDGER a ");
		sql.append("      ,t_ri_gl_general_ledger b ");
		sql.append("      ,t_ri_bcp_fee c ");
		if(isSettlementQuery){
			sql.append("  ,T_RI_BCP_SETTLEMENT_DETAIL stl ");
		}
		if(StringUtils.isNotBlank(condition.getContractID())|| StringUtils.isNotBlank(condition.getuWYear()) || condition.getuWAreaList() != null){
			sql.append("      ,t_ri_contract_info d ");
		}
		if(CollectionUtils.isNotEmpty(condition.getuWAreaList())){
			sql.append("      ,V_CONTRACT_SECTION_RELATION e ");
			sql.append("      ,t_ri_cont_section_info f ");
		}
		//=============where clause=================
		sql.append("      where a.general_ledger_id = b.general_ledger_id(+) ");
		sql.append("      and a.amount is not null ");
		sql.append("      and a.fee_id = c.fee_id(+) ");
		if(isSettlementQuery){
			sql.append("      and a.settle_Detail_Id = stl.settle_Detail_Id ");
		}
		sql.append("      and a.currency_code is not null ");
		if(StringUtils.isNotBlank(condition.getContractID()) || StringUtils.isNotBlank(condition.getuWYear())){
			sql.append("      and a.contract_id = d.cont_comp_id ");
		}
		if(CollectionUtils.isNotEmpty(condition.getuWAreaList())){
			sql.append("      and d.cont_comp_id = e.contract_id ");
			sql.append("      and e.section_id = f.cont_comp_id ");
		}
		if(condition.getGeneralLedgerId() != null){
			sql.append("      and a.general_ledger_id = :generalLedgerId ");
			params.put("generalLedgerId",condition.getGeneralLedgerId());
		}
		if(condition.getBusinessType() != null){
			if(isSettlementQuery){
				if(condition.getBusinessType() == BizTransactionType.ARAP_COLLECTION.getType()){
					sql.append("      and stl.collection_id is not null ");
					sql.append("      and stl.reverse_id is null ");
				}
				if(condition.getBusinessType() == BizTransactionType.ARAP_PAYMENT.getType()){
					sql.append("      and stl.payment_id is not null ");
					sql.append("      and stl.reverse_id is null ");
				}
				if(condition.getBusinessType() == BizTransactionType.ARAP_INTERNAL_OFFSET.getType()){
					sql.append("      and stl.offset_id is not null ");
					sql.append("      and stl.reverse_id is null ");
				}
				if(condition.getBusinessType() == BizTransactionType.ARAP_TRANS_REVERSAL.getType()){
					sql.append("      and stl.reverse_id is not null ");
				}
			}else{
				sql.append("      and c.biz_trans_type = :businessType ");
				params.put("businessType",condition.getBusinessType());
			}
		}
		if(StringUtils.isNotBlank(condition.getBusinessNumber())){
			sql.append("      and c.biz_trans_no = :businessNumber ");
			params.put("businessNumber", condition.getBusinessNumber());
		}
		if(StringUtils.isNotBlank(condition.getCurrency())){
			sql.append("      and a.currency_code = :currency ");
			params.put("currency", condition.getCurrency());
		}
		if(condition.getGenerationDateFrom() != null){
			sql.append("      and c.trans_date >= :generationDateFrom ");
			params.put("generationDateFrom", condition.getGenerationDateFrom());
		}
		if(condition.getGenerationDateTo() != null){
			sql.append("      and c.trans_date <= :generationDateTo ");
			params.put("generationDateTo",condition.getGenerationDateTo());
		}
		if(condition.getPostDateFrom() != null){
			sql.append("      and b.post_date >= :postDateFrom ");
			params.put("postDateFrom", condition.getPostDateFrom());
		}
		if(condition.getPostDateTo() != null){
			sql.append("      and b.post_date <= :postDateTo ");
			params.put("postDateTo", condition.getPostDateTo());
		}
		if(condition.getPostStatus() != null){
			sql.append("      and b.post_status = :postStatus ");
			params.put("postStatus", condition.getPostStatus());
		}
		if(StringUtils.isNotBlank(condition.getContractID())){
			sql.append("      and d.contract_code in ('' ");
			List<String> contractIdList = Arrays.asList(condition.getContractID().split(","));
			int idx = 1;
			for(String contractId : contractIdList){
				if(StringUtils.isNotBlank(contractId)){
					sql.append(" ,:contractCode"+idx);
					params.put("contractCode"+idx, contractId.trim());
					idx++;
				}
			}
			sql.append(" ) ");
		
		}
		if(StringUtils.isNotBlank(condition.getuWYear())){
			sql.append("      and d.uw_year = :uWYear ");
			params.put("uWYear", condition.getuWYear());
		}
		if(CollectionUtils.isNotEmpty(condition.getuWAreaList())){// depends on code table of UW area
			sql.append("      and f.uw_area in ('-99' ");
			List<String> areaList = condition.getuWAreaList();
			for(String area : areaList){
				sql.append(",'").append(area).append("'");
			}
			sql.append(")");
		}
		sql.append("  order by gl_mapping_entry_id desc ");
		return sql.toString();
	}
	
	private String buildSubLedgerBizTransTypeQuerySql() throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(FinanceTransactionType.COLLECTION.getType()).append(",").append(BizTransactionType.ARAP_COLLECTION.getType());
		sql.append(",").append(FinanceTransactionType.PAYMENT.getType()).append(",").append(BizTransactionType.ARAP_PAYMENT.getType());
		sql.append(",").append(FinanceTransactionType.INTERNAL_OFFSET.getType()).append(",").append(BizTransactionType.ARAP_INTERNAL_OFFSET.getType());
		sql.append(",").append(FinanceTransactionType.REVERSAL.getType()).append(",").append(BizTransactionType.ARAP_TRANS_REVERSAL.getType());
		
		return sql.toString();
	}
	
	private List<GLSubLedger> buildSubLedgerResultList(List<Object[]> result) throws Exception{
		List<GLSubLedger> resultList = new ArrayList<GLSubLedger>();
		GLSubLedger subLedger;
		if(CollectionUtils.isNotEmpty(result)){
			for(Object[] row : result){
				subLedger = new GLSubLedger();
				resultList.add(subLedger);
				for(int idx=0; idx<19; idx++){
					if(row[idx] != null){
						switch(idx){
						case 0: subLedger.setGlMappingEntryId(((BigDecimal)row[idx]).longValue()); break;
						case 1: subLedger.setFeeId(((BigDecimal)row[idx]).longValue()); break;
						case 2: subLedger.setContractCode((String)row[idx]); break;
						case 3: subLedger.setAmount((BigDecimal)row[idx]); break;
						case 4: subLedger.setCurrencyCode(row[idx].toString()); break;
						case 5: subLedger.setInsertTime((Date)row[idx]); break;
						case 6: subLedger.setPostDate((Date)row[idx]); break;
						case 7: subLedger.setPostStatus(((BigDecimal)row[idx]).intValue()); break;
						case 8: subLedger.setEntryCode((String)row[idx]); break;
						case 9: subLedger.setGenerationDate((Date)row[idx]); break;
						case 10: subLedger.setBizTransType(((BigDecimal)row[idx]).intValue()); break;
						case 11: subLedger.setBizTransNo((String)row[idx]); break;
						case 12: subLedger.setBizTransDesc((String)row[idx]); break;
						case 13: subLedger.setBizOperatorId(((BigDecimal)row[idx]).longValue()); break;
						case 14: subLedger.setEstimation(((BigDecimal)row[idx]).intValue() == 1?true:false); break;
						case 15: subLedger.setContractId(row[idx] != null?Long.valueOf(String.valueOf(row[idx])):null); break;
						case 16: subLedger.setSettleType(((BigDecimal)row[idx]).intValue()); break;
						case 17: subLedger.setSettleId(((BigDecimal)row[idx]).longValue()); break;
						case 18: subLedger.setSettleDetailId(((BigDecimal)row[idx]).longValue()); break;
						}
					}
				}
			}
			
		}
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public List<GLSubLedger> queryDoubleEntries(Long mappingEntryId) throws Exception{
		Query query = this.getEntityManager().createNamedQuery("GLSubLedger.findByMappingEntryId", GLSubLedger.class);
		query.setParameter("glMappingEntryId", mappingEntryId);
		return query.getResultList();
	}
}
