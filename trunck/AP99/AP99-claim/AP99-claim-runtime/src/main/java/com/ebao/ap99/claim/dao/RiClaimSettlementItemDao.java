/**
 * 
 */
package com.ebao.ap99.claim.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.entity.TRiClaimSettlementItem;
import com.ebao.ap99.claim.model.ReserveHistoryInfo;
import com.ebao.ap99.claim.model.SettlementDetailHistory;
import com.ebao.ap99.claim.model.SettlementHistoryInfo;
import com.ebao.ap99.claim.model.SettlementHistoryQuery;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author gang.wang
 *
 */
public class RiClaimSettlementItemDao extends BaseDao<TRiClaimSettlementItem> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiClaimSettlementItem> getEntityClass() {
		return TRiClaimSettlementItem.class;
	}

	public List<TRiClaimSettlementItem> getSettlementItemList(long settlementId) {
		//long ss = settlementId;
		final Query query = getEntityManager().createNamedQuery(
				"TRiClaimSettlementItem.findBySettlementIdAndPostingFlag", TRiClaimSettlementItem.class);
		query.setParameter("settlementId", settlementId);
		@SuppressWarnings("unchecked")
		List<TRiClaimSettlementItem> list = query.getResultList();
		return list;
	}
	
	public List<TRiClaimSettlementItem> getRelatedSettlementItemList(Long relatedId) {
		//long ss = settlementId;
		final Query query = getEntityManager().createNamedQuery(
				"TRiClaimSettlementItem.findByRelatedSettlementId", TRiClaimSettlementItem.class);
		BigDecimal settleDetailId = new BigDecimal(relatedId);
		query.setParameter("relatedSettleDetailId", settleDetailId);
		@SuppressWarnings("unchecked")
		List<TRiClaimSettlementItem> list = query.getResultList();
		return list;
	}
	
	
	public List<String> getSettlementPostingFlag(long settleDetailId) {
		//long ss = settlementId;
		final Query query = getEntityManager().createNamedQuery(
				"TRiClaimSettlementItem.findPostingFlagBySettlementId", String.class);
		query.setParameter("settleDetailId", settleDetailId);
		@SuppressWarnings("unchecked")
		List<String> postingList = query.getResultList();
		return postingList;
	}
	
	
	public List<TRiClaimSettlementItem> getItemList(long settlementId) {
		//long ss = settlementId;
		final Query query = getEntityManager().createNamedQuery(
				"TRiClaimSettlementItem.findBySettlement", TRiClaimSettlementItem.class);
		query.setParameter("settlementId", settlementId);
		@SuppressWarnings("unchecked")
		List<TRiClaimSettlementItem> list = query.getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<TRiClaimSettlementItem> getFinaSettleList(long refId){
		final Query query = getEntityManager().createNamedQuery("TRiClaimSectionInfo.findByRefFronting", Long.class);
		query.setParameter("refId", refId);
		query.setParameter("businessDirection", "1");
		query.setParameter("frontingFlag", "1");

		List<Long> sectionIdList = query.getResultList();
		List<TRiClaimSettlementItem> allList =new ArrayList<TRiClaimSettlementItem>();
		if(sectionIdList.size() > 0){
			for(int i = 0;i<sectionIdList.size();i++){
				List<TRiClaimSettlementItem> currentList = this.getfSettle(refId, sectionIdList.get(i));
			    allList.addAll(currentList);
			}
			
		}
		return allList;
	}
	
	public List<TRiClaimSettlementItem> getfSettle(Long refId,Long sectionId){
		
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct * from t_Ri_Claim_Settlement_item a,t_Ri_Claim_settlement b") ;
		sql.append("where a.settlement_id =b.settlement_id and b.status = '").append(ClaimConstant.CLAIM_SETTLEMENT_STATUS__PENDING_SUBMIT).append("' ");
		sql.append(" and b.ref_id = ?  and a.section_id = ? ");
		
		List<TRiClaimSettlementItem> sSettleList = this.getJdbcTemplate().query(sql.toString(),
				new Object[] { refId ,sectionId},
				new BeanPropertyRowMapper<>(TRiClaimSettlementItem.class));
		
		return sSettleList;
	}
	
	
	
	
	

	public List<TRiClaimSettlementItem> getSettlementItemListById(long settlementId) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimSettlementItem.findBySettlementId",
				TRiClaimSettlementItem.class);
		query.setParameter("settlementId", settlementId);
		@SuppressWarnings("unchecked")
		List<TRiClaimSettlementItem> list = query.getResultList();
		return list;
	}

	// TRiClaimSettlementItem.findBySettlementIdAndPostingFlagChange
	public List<TRiClaimSettlementItem> getSettlementItemListPostingFlagChanged(long settlementId) {
		final Query query = getEntityManager().createNamedQuery(
				"TRiClaimSettlementItem.findBySettlementIdAndPostingFlagChange", TRiClaimSettlementItem.class);
		query.setParameter("settlementId", settlementId);
		@SuppressWarnings("unchecked")
		List<TRiClaimSettlementItem> list = query.getResultList();
		return list;
	}

	public List<SettlementHistoryInfo> getSettlementItemHistory(SettlementHistoryQuery settlementQuery) {
		List<Object> param = new ArrayList<Object>();
		param.add(settlementQuery.getRefId());
		param.add(settlementQuery.getBusinessDirection());

		StringBuilder sql = new StringBuilder();

		sql.append("  select                                                                                   ");
		sql.append("  csi.settle_detail_id as settleDetailId,                                                  ");
		sql.append("  rcs.settlement_name as settlementname,                                                  ");
		sql.append("  csi.settlement_id as settlementId,                                                       ");
		sql.append("  csi.amount_oc as amountOc,                                                               ");
		sql.append("  csi.amount_usd as amountUsd,                                                             ");
		sql.append("  csi.broker_refer as brokerRefer,                                                         ");
		sql.append("  csi.business_direction as businessDirection,                                             ");
		sql.append("  csi.exchange_rate as  exchangeRate,                                                      ");
		sql.append("  csi.insert_by as insertBy,                                                               ");
		sql.append("  csi.insert_time as insertTime,                                                           ");
		sql.append("  csi.original_currency as originalCurrency,                                               ");
		sql.append("  csi.posting_flag as postingFlag,                                                         ");
		sql.append("  csi.remark as remark,                                                                    ");
		sql.append("  csi.settlement_type as settlementType,                                                   ");
		sql.append("  csi.section_id as sectionId,                                                             ");
		sql.append("  rcs.status as status,                                                                    ");
		sql.append("  csi.update_by as updateBy,                                                               ");
		sql.append("  csi.update_time as updateTime,                                                           ");
		sql.append("  s.uw_year as underwritingYear,                                                           ");
		sql.append("  s.contract_code  as contractCode,                                                        ");
		sql.append("  s.full_name as contractSectionName                                                       ");
		sql.append("  from t_Ri_Claim_Settlement rcs, t_Ri_Claim_Settlement_Item csi,t_Ri_Claim_Section_Info s ");
		sql.append("  where   rcs.ref_Id =s.ref_Id                                                             ");
		sql.append("  and rcs.settlement_id = csi.settlement_id                                                ");
		sql.append("  and csi.section_id = s.section_id                                                        ");
		sql.append("  and csi.business_direction= s.business_direction                                         ");
		sql.append("  and rcs.status = '3'                                                                     ");
		sql.append("  and rcs.ref_Id =?                                                                        ");
		sql.append("  and rcs.business_direction=?                                                             ");

		if (!"".equals(settlementQuery.getContractSection()) && settlementQuery.getContractSection() != 0) {
			sql.append("  and s.section_id =?                                                                 ");
			param.add(settlementQuery.getContractSection());
		}

		if (!"".equals(settlementQuery.getUnderwritingYear()) && settlementQuery.getUnderwritingYear() != 0) {
			sql.append("  and s.uw_year =?                                                                    ");
			param.add(settlementQuery.getUnderwritingYear());
		}

		if (StringUtils.isNotBlank(settlementQuery.getSettlementType())) {
			sql.append("  and csi.settlement_type =?                                                          ");
			param.add(settlementQuery.getSettlementType());
		}
		// meiliang.zou modify csi.update_time desc 2016.5.24 
		sql.append("  order by csi.update_time desc, csi.settle_detail_id                                                          ");
		logger.info(sql.toString());

		Object[] params = new Object[param.size()];
		param.toArray(params);

		List<SettlementHistoryInfo> settlementItemList = this.getJdbcTemplate().query(sql.toString(), params,
				new BeanPropertyRowMapper<SettlementHistoryInfo>(SettlementHistoryInfo.class));

		return settlementItemList;
	}

	public List<SettlementHistoryInfo> getSettlementSummary(SettlementHistoryQuery settlementQuery) {
//getSettlementSummary
		List<Object> param = new ArrayList<Object>();
		param.add(settlementQuery.getRefId());
		param.add(settlementQuery.getBusinessDirection());

		StringBuilder sql = new StringBuilder();
		sql.append(" select                                                                                   ");
		sql.append(" nvl(sum(csi.amount_oc*b.sign),0) as amountOc,                                                   ");
		sql.append(" nvl(sum(csi.amount_usd*b.sign),0) as amountUsd,                                                 ");
		sql.append(" csi.original_currency as originalCurrency                                               ");
        sql.append(" from t_Ri_Claim_Settlement rcs, t_Ri_Claim_Settlement_Item csi,                          ");
        sql.append(" t_Ri_Claim_Section_Info s,t_ri_claim_reserve_type b                                            ");
		sql.append(" where   rcs.ref_Id =s.ref_Id                                                             ");
		sql.append(" and rcs.settlement_id = csi.settlement_id                                                ");
		sql.append(" and csi.section_id = s.section_id                                                        ");
		sql.append(" and csi.business_direction= s.business_direction                                         ");
		sql.append(" and csi.settlement_type=b.id                                                                    ");
		sql.append(" and rcs.status = '3'                                                                     ");
		sql.append("  and rcs.ref_Id =?                                                                       ");
		sql.append("  and rcs.business_direction=?                                                            ");
		if (!"".equals(settlementQuery.getContractSection()) && settlementQuery.getContractSection() != 0) {
			sql.append("  and s.section_id =?                                                                 ");
			param.add(settlementQuery.getContractSection());
		}
		if (!"".equals(settlementQuery.getUnderwritingYear()) && settlementQuery.getUnderwritingYear() != 0) {
			sql.append("  and s.uw_year =?                                                                    ");
			param.add(settlementQuery.getUnderwritingYear());
		}
		if (StringUtils.isNotBlank(settlementQuery.getSettlementType())) {
			sql.append("  and csi.settlement_type =?                                                          ");
			param.add(settlementQuery.getSettlementType());
		}
		sql.append(" group by csi.original_currency                                                           ");

		logger.info(sql.toString());
		Object[] params = new Object[param.size()];
		param.toArray(params);

		List<SettlementHistoryInfo> settlementItemSummaryList = this.getJdbcTemplate().query(sql.toString(), params,
				new BeanPropertyRowMapper<SettlementHistoryInfo>(SettlementHistoryInfo.class));

		return settlementItemSummaryList;
	}

	public List<SettlementHistoryInfo> getSettlementItemSummary(SettlementHistoryQuery settlementQuery) {

		List<Object> param = new ArrayList<Object>();
		param.add(settlementQuery.getRefId());
		param.add(settlementQuery.getBusinessDirection());

		StringBuilder sql = new StringBuilder();
		sql.append(" select                                                                                   ");
		sql.append(" nvl(sum(csi.amount_oc),0) as amountOc,                                                   ");
		sql.append(" nvl(sum(csi.amount_usd),0) as amountUsd,                                                 ");
		sql.append(" csi.original_currency as originalCurrency,                                               ");
		sql.append(" csi.settlement_type as settlementType,                                                   ");
		sql.append(" csi.section_id as sectionId,                                                             ");
		sql.append(" s.uw_year as underwritingYear,                                                           ");
		sql.append(" s.contract_code  as contractCode,                                                        ");
		sql.append(" s.full_name as contractSectionName                                                       ");
		sql.append(" from t_Ri_Claim_Settlement rcs, t_Ri_Claim_Settlement_Item csi,t_Ri_Claim_Section_Info s ");
		sql.append(" where   rcs.ref_Id =s.ref_Id                                                             ");
		sql.append(" and rcs.settlement_id = csi.settlement_id                                                ");
		sql.append(" and csi.section_id = s.section_id                                                        ");
		sql.append(" and csi.business_direction= s.business_direction                                         ");
		sql.append(" and rcs.status = '3'                                                                     ");
		sql.append("  and rcs.ref_Id =?                                                                       ");
		sql.append("  and rcs.business_direction=?                                                            ");
		if (!"".equals(settlementQuery.getContractSection()) && settlementQuery.getContractSection() != 0) {
			sql.append("  and s.section_id =?                                                                 ");
			param.add(settlementQuery.getContractSection());
		}
		if (!"".equals(settlementQuery.getUnderwritingYear()) && settlementQuery.getUnderwritingYear() != 0) {
			sql.append("  and s.uw_year =?                                                                    ");
			param.add(settlementQuery.getUnderwritingYear());
		}
		if (StringUtils.isNotBlank(settlementQuery.getSettlementType())) {
			sql.append("  and csi.settlement_type =?                                                          ");
			param.add(settlementQuery.getSettlementType());
		}
		sql.append(" group by csi.original_currency ,csi.settlement_type,                                     ");
		sql.append(" csi.section_id,s.full_name,s.uw_year,s.contract_code                                     ");

		logger.info(sql.toString());
		Object[] params = new Object[param.size()];
		param.toArray(params);

		List<SettlementHistoryInfo> settlementItemSummaryList = this.getJdbcTemplate().query(sql.toString(), params,
				new BeanPropertyRowMapper<SettlementHistoryInfo>(SettlementHistoryInfo.class));

		return settlementItemSummaryList;
	}
	
	public List<TreeModel> getUWYear(long refId, String BusinessDirection) {

		StringBuilder sql = new StringBuilder();
		sql.append("  select       distinct s.uw_year as underwritingYear   from                                 ");
		sql.append("  t_Ri_Claim_Settlement rcs, t_Ri_Claim_Settlement_Item csi,t_Ri_Claim_Section_Info s        ");
		sql.append("  where rcs.ref_Id =s.ref_Id                                                                 ");
		sql.append("  and rcs.settlement_id = csi.settlement_id                                                  ");
		sql.append("  and csi.section_id = s.section_id                                                          ");
		sql.append("  and csi.business_direction= s.business_direction                                           ");
		sql.append("  and rcs.ref_Id =?                                                                          ");
		sql.append("  and rcs.business_direction=?                                                               ");

		List<ReserveHistoryInfo> reserveList = this.getJdbcTemplate().query(sql.toString(),
				new Object[] { refId, BusinessDirection },
				new BeanPropertyRowMapper<ReserveHistoryInfo>(ReserveHistoryInfo.class));

		List<TreeModel> uwList = new ArrayList<TreeModel>();
		for (ReserveHistoryInfo re : reserveList) {
			TreeModel treeModel = new TreeModel();
			treeModel.setId(re.getUnderwritingYear());
			treeModel.setText(re.getUnderwritingYear() + "");
			uwList.add(treeModel);
		}

		return uwList;
	}

	public List<TreeModel> getContractSection(long refId, String BusinessDirection) {
		StringBuilder sql = new StringBuilder();

		sql.append("  select  distinct s.section_id as sectionId, s.full_name as contractSectionName  from       ");
		sql.append("  t_Ri_Claim_Settlement rcs, t_Ri_Claim_Settlement_Item csi,t_Ri_Claim_Section_Info s        ");
		sql.append("  where rcs.ref_Id =s.ref_Id                                                                 ");
		sql.append("  and rcs.settlement_id = csi.settlement_id                                                  ");
		sql.append("  and csi.section_id = s.section_id                                                          ");
		sql.append(" and csi.business_direction= s.business_direction                                            ");
		sql.append("  and rcs.ref_Id =?                                                                          ");
		sql.append("  and rcs.business_direction=?                                                               ");

		List<ReserveHistoryInfo> reserveList = this.getJdbcTemplate().query(sql.toString(),
				new Object[] { refId, BusinessDirection },
				new BeanPropertyRowMapper<ReserveHistoryInfo>(ReserveHistoryInfo.class));
		List<TreeModel> csList = new ArrayList<TreeModel>();

		for (ReserveHistoryInfo re : reserveList) {
			TreeModel treeModel = new TreeModel();
			treeModel.setId(re.getSectionId());
			treeModel.setText(re.getContractSectionName());
			csList.add(treeModel);
		}

		return csList;
	}

	public SettlementDetailHistory getDetailHistory(long settleDetailId) {

		StringBuilder sql = new StringBuilder();

		sql.append("  select                                                            ");
		sql.append("  csi.settlement_id as settlementId,                                ");
		sql.append("  rcs.date_of_receipt as dateOfReceipt,                             ");
		sql.append("  rcs.remark as remark,                                             ");
		sql.append("  rcs.insert_by as insertBy,                                        ");
		sql.append("  rcs.insert_time as insertTime,                                    ");
		sql.append("  csi.date_of_payment as dateOfPayment,                             ");
		sql.append("  csi.pay_type as payType                                           ");
		sql.append("  from t_Ri_Claim_Settlement rcs, t_Ri_Claim_Settlement_Item csi    ");
		sql.append("  where rcs.settlement_id=csi.settlement_id                         ");
		sql.append("  and csi.settle_detail_id =?                                       ");

		SettlementDetailHistory settlementDetailHistory = new SettlementDetailHistory();

		this.getJdbcTemplate().query(sql.toString(), new Object[] { settleDetailId }, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				settlementDetailHistory.setSettlementId(rs.getLong("settlementId"));
				settlementDetailHistory.setDateOfReceipt(rs.getDate("dateOfReceipt"));
				settlementDetailHistory.setRemark(rs.getString("remark"));
				settlementDetailHistory.setInsertBy(rs.getLong("insertBy"));
				settlementDetailHistory.setInsertTime(rs.getDate("insertTime"));
				settlementDetailHistory.setDateOfPayment(rs.getDate("dateOfPayment"));
			}
		});
		return settlementDetailHistory;

	}

}
