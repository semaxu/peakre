package com.ebao.ap99.claim.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.entity.TRiClaimInfo;
import com.ebao.ap99.claim.entity.TRiClaimReserve;
import com.ebao.ap99.claim.model.ClaimExcelModel;
import com.ebao.ap99.claim.model.ClaimModelForGeneral;
import com.ebao.ap99.claim.model.ClaimRecordsExcelModel;
import com.ebao.ap99.claim.model.ClaimSummaryInfo;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.unicorn.platform.foundation.context.AppUser;
//import com.ebao.ap99.claim.entity.TSampleUser;
//import com.ebao.ap99.claim.model.ClaimQuery;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class RiClaimInfoDao extends BaseDao<TRiClaimInfo> {

	@Override
	public Class<TRiClaimInfo> getEntityClass() {
		return TRiClaimInfo.class;
	}

	public List<TreeModel> getClaimNoList(Long eventId) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimInfo.findByEventId", TRiClaimInfo.class);
		query.setParameter("eventId", eventId);
		@SuppressWarnings("unchecked")
		List<TRiClaimInfo> list = query.getResultList();

		List<TreeModel> claimNos = new ArrayList<>();

		for (TRiClaimInfo l : list) {
			TreeModel codeModel = new TreeModel();
			codeModel.setId(l.getClaimId());
			codeModel.setText(l.getClaimNo());
			claimNos.add(codeModel);

		}
		return claimNos;
	}
	
	
	public List<Long> getClaimIdList(Long eventId) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimInfo.findByEventId", TRiClaimInfo.class);
		query.setParameter("eventId", eventId);
		@SuppressWarnings("unchecked")
		List<TRiClaimInfo> list = query.getResultList();

		List<Long> claimNos = new ArrayList<>();

		for (TRiClaimInfo l : list) {
			claimNos.add(l.getClaimId());
		}
		return claimNos;
	}
	
	
	public String getClaimNo(Long claimId) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimInfo.findClaimNobyclaimId", TRiClaimInfo.class);
		query.setParameter("claimId", claimId);
		@SuppressWarnings("unchecked")
		List<TRiClaimInfo> list = query.getResultList();
		String claimNo = null;
		for (TRiClaimInfo l : list) {
			claimNo =l.getClaimNo();
		}
		return claimNo;
	}
	

	public List<Long> getSectionIdList(long refId){
		final Query query = getEntityManager().createNamedQuery("TRiClaimSectionInfo.findByRefFronting", Long.class);
		query.setParameter("refId", refId);
		query.setParameter("businessDirection", "1");
		query.setParameter("frontingFlag", "1");
		@SuppressWarnings("unchecked")
		List<Long> sectionIdList = query.getResultList();
	    return sectionIdList;
	}
	
	public List<ClaimModelForGeneral> getClaimModel(Long contractId) {

		StringBuilder sql = new StringBuilder();

		sql.append(" select tmp.claim_id as claimid,                                           ");
		sql.append("        tmp.claim_no as claimNo,                                           ");
		sql.append("        s.section_name as sectionName,                                     ");
		sql.append("        tmp.date_of_loss_from as lossStartDate,                            ");
		sql.append("        tmp.cause_of_loss as caseOfLoss,                                   ");
		sql.append("        (select nvl(sum(a.org_amount_usd), 0)                              ");
		sql.append("           from t_Ri_Claim_Reserve a                                       ");
		sql.append("          where a.section_id = tmp.section_id                              ");
		sql.append("            and a.ref_id = tmp.claim_id                                    ");
		sql.append("            and a.business_direction = 1                                   ");
		sql.append("            and a.reserve_type in ('1', '2', '3')) as lossReverse,         ");
		sql.append("        (select nvl(sum(c.amount_usd), 0)                                  ");
		sql.append("           from t_Ri_Claim_Settlement b, t_Ri_Claim_Settlement_Item c      ");
		sql.append("          where b.settlement_id = c.settlement_id                          ");
		sql.append("            and b.ref_id = tmp.claim_id                                    ");
		sql.append("            and b.status = 3                                               ");
		sql.append("            and c.section_id = tmp.section_id                              ");
		sql.append("            and c.business_direction = 1                                   ");
		sql.append("            and c.settlement_type in ('1', '2')) as lossPaid,              ");
		sql.append("        tmp.status                                                         ");
		sql.append("   from (select distinct tr.section_id,                                    ");
		sql.append("                         tc.claim_id,                                      ");
		sql.append("                         tc.claim_no,                                      ");
		sql.append("                         tc.date_of_loss_from,                             ");
		sql.append("                         tc.status,                                        ");
		sql.append("                         tc.cause_of_loss                                  ");
		sql.append("           from t_Ri_Claim_Reserve tr, t_ri_claim_info tc                  ");
		sql.append("          where tc.claim_id = tr.ref_id                                    ");
		sql.append("            and tr.business_direction = 1                                  ");
		sql.append("            and tr.ref_id in (select ref_id                                ");
		sql.append("            from t_Ri_Claim_Section_Info                                   ");
		sql.append("            where contract_id = ?)) tmp,                                   ");
		sql.append("        t_Ri_Claim_Section_Info s                                          ");
		sql.append("  where tmp.claim_id = s.ref_id                                            ");
		sql.append("    and tmp.section_id = s.section_id                                      ");
		sql.append("    and s.business_direction = 1                                           ");
		sql.append("  order by tmp.claim_id desc                                               ");

		List<ClaimModelForGeneral> claimModelForGeneral = this.getJdbcTemplate().query(sql.toString(),
				new Object[] { contractId },
				new BeanPropertyRowMapper<ClaimModelForGeneral>(ClaimModelForGeneral.class));
		return claimModelForGeneral;
	}

	public ClaimSummaryInfo getClaimSummary(Long refId, String BusinessDirection) {
		ClaimSummaryInfo summaryInfo = new ClaimSummaryInfo();
		summaryInfo.setReserveSummary(getSubmitReserveSumary(refId,BusinessDirection));
		summaryInfo.setSettlementSummary(getApprovedSettlementSumary(refId,BusinessDirection));
		summaryInfo.setTotalByCurrency(summaryInfo.getReserveSummary() + summaryInfo.getSettlementSummary());
		return summaryInfo;
	}

	public Double getSubmitReserveSumary(Long refId, String BusinessDirection) {
		StringBuilder sql = new StringBuilder();
		Double result = Double.valueOf(0);

		sql.append(" select nvl(sum(a.org_amount_usd*b.sign),0) as usdAmount  ");
		sql.append(" from t_ri_claim_reserve  a ,t_ri_claim_reserve_type b ");
		sql.append(" where a.reserve_type = b.id  ");
		sql.append(" and a.status ='").append(ClaimConstant.CLAIM_RESERVE_STATUS__INFORCE).append("' ");
		sql.append(" and a.business_direction = ? ");
		sql.append(" and a.ref_id = ? and  a.reserve_type in ('1','2','3') ");

		Object[] params = new Object[] { BusinessDirection,refId };

		@SuppressWarnings({ "unchecked", "rawtypes" })
		List results = this.getJdbcTemplate().query(sql.toString(), params, new RowMapper() {
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Map<String, Double> u = new HashMap();
				u.put("usdAmount", rs.getDouble("usdAmount"));
				return u;
			}
		});

		@SuppressWarnings("unchecked")
		Iterator<Map<String, Double>> it = results.iterator();
		while (it.hasNext()) {
			Map<String, Double> map = it.next();
			result = map.get("usdAmount");
		}
		return result;
	}

	public Double getApprovedSettlementSumary(Long refId, String BusinessDirection) {
		StringBuilder sql = new StringBuilder();
		Double result = Double.valueOf(0);

		sql.append(" select nvl(sum(t2.amount_usd*t3.sign),0) as usdAmount  ");
		sql.append(" from  t_ri_claim_settlement t1 ,t_ri_claim_settlement_item t2,t_ri_claim_reserve_type t3  ");
		sql.append(" where t1.settlement_id = t2.settlement_id ");
		sql.append(" and t2.settlement_type = t3.id ");
		sql.append(" and t1.business_direction = ? ");
		sql.append(" and t1.ref_id = ?  and t2.settlement_type in ('1','2','3') ");
		sql.append(" and t1.status ='").append(ClaimConstant.CLAIM_SETTLEMENT_STATUS__APPROVED).append("' ");

		

//		 and t1.ref_id=13674363
//				 and t1.business_direction=1
//				 and t1.status = 3
		Object[] params = new Object[] { BusinessDirection,refId };

		@SuppressWarnings({ "unchecked", "rawtypes" })
		List results = this.getJdbcTemplate().query(sql.toString(), params, new RowMapper() {
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Map<String, Double> u = new HashMap();
				u.put("usdAmount", rs.getDouble("usdAmount"));
				return u;
			}
		});

		@SuppressWarnings("unchecked")
		Iterator<Map<String, Double>> it = results.iterator();
		while (it.hasNext()) {
			Map<String, Double> map = it.next();
			result = map.get("usdAmount");
		}
		return result;
	}
	
	public List<ClaimExcelModel> getClaimExcelList(Long refId){
		
		StringBuilder sql = new StringBuilder();
//		sql.append("  select t.original_currency as currency,       ");                
//		sql.append("   t.business_direction as category,            ");                
//		sql.append("   'reserve' as busitype,                       ");                
//		sql.append("   t.reserve_type as entryType,                 ");                
//		sql.append("  t.org_amount_oc as amount                     ");                
//		sql.append("  from t_Ri_Claim_Reserve  t                    ");                
//		sql.append("  where t.ref_id=? and t.status='").append(ClaimConstant.CLAIM_RESERVE_STATUS__INFORCE).append("'  ");                
//		sql.append("  union all                                     ");                
//		sql.append("  select 'USD Equivalent' as currency ,         ");                
//		sql.append("  t.business_direction as category,             ");                
//		sql.append("  'reserve' as busitype,                        ");                
//		sql.append("  t.reserve_type as entryType,                  ");                
//		sql.append("  t.org_amount_usd as amount                    ");                
//		sql.append("  from t_Ri_Claim_Reserve  t                    ");                
//		sql.append("  where t.ref_id=? and t.status='").append(ClaimConstant.CLAIM_RESERVE_STATUS__INFORCE).append("'  ");                
//		sql.append("  union all                                     ");                
//		sql.append("  select                                      "); 
//		sql.append("  ti.original_currency as currency,           "); 
//		sql.append("  ti.business_direction as category,          "); 
//		sql.append("   'settlement' as busitype,                  "); 
//		sql.append("  ti.settlement_type as entryType,            "); 
//		sql.append("  ti.amount_oc as amount                      "); 
//		sql.append("  from t_Ri_Claim_Settlement ts,              "); 
//		sql.append("  t_Ri_Claim_Settlement_Item ti               "); 
//		sql.append("  where ts.settlement_id = ti.settlement_id   "); 
//		sql.append("  and ts.ref_id =? and ts.status='").append(ClaimConstant.CLAIM_SETTLEMENT_STATUS__APPROVED).append("' "); 
//		sql.append("  union all                                   "); 
//		sql.append("  select                                      "); 
//		sql.append("  'USD Equivalent'  as currency,              "); 
//		sql.append("  ti.business_direction as category,          "); 
//		sql.append("  'settlement' as busitype,                   "); 
//		sql.append("  ti.settlement_type as entryType,            "); 
//		sql.append("  ti.amount_usd as amount                     "); 
//		sql.append("  from t_Ri_Claim_Settlement ts,              "); 
//		sql.append("  t_Ri_Claim_Settlement_Item ti               "); 
//		sql.append("  where ts.settlement_id = ti.settlement_id   "); 
//		sql.append("  and ts.ref_id =? and ts.status='").append(ClaimConstant.CLAIM_SETTLEMENT_STATUS__APPROVED).append("' "); 
//
//		
		sql.append("   select t.original_currency as currency,                               ");
		sql.append("   case when        t.business_direction ='1'  then 'Financial'          ");
		sql.append("     else 'Retrocession Recovery' end as category,                       ");
		sql.append("   'Submitted Reserve' as busitype,                                      ");
		sql.append("   case when t.reserve_type in ('1','2','3')  then 'Loss Reserve'        ");
		sql.append("     when  t.reserve_type = '4' then 'RIP provision'                     ");
		sql.append("      else r.description end  as    entryType,                           ");
		sql.append("   (t.org_amount_oc*r.sign)  as amount                                  ");
		sql.append("  from t_Ri_Claim_Reserve  t  , t_ri_claim_reserve_type r                ");
		sql.append("  where t.ref_id=? and t.status='").append(ClaimConstant.CLAIM_RESERVE_STATUS__INFORCE).append("'  ");
		sql.append("  and t.reserve_type = r.id                                              ");
		sql.append("                                                                         ");
		sql.append("  union all                                                              ");
		sql.append("  select 'USD Equivalent' as currency ,                                  ");
		sql.append("  case when        t.business_direction ='1'  then 'Financial'           ");
		sql.append("     else 'Retrocession Recovery' end as category,                       ");
		sql.append("   'Submitted Reserve' as busitype,                                      ");
		sql.append("   case when t.reserve_type in ('1','2','3')  then 'Loss Reserve'        ");
		sql.append("     when  t.reserve_type = '4' then 'RIP provision'                     ");
		sql.append("      else r.description end  as    entryType,                           ");
		sql.append("  (t.org_amount_usd*r.sign) as amount                                    ");
		sql.append("  from t_Ri_Claim_Reserve  t  , t_ri_claim_reserve_type r                ");
		sql.append("  where t.ref_id = ? and t.status='").append(ClaimConstant.CLAIM_RESERVE_STATUS__INFORCE).append("'  ");
		sql.append("  and t.reserve_type = r.id                                              ");
		sql.append("                                                                         ");
		sql.append("    union all                                                            ");
		sql.append("  select                                                                 ");
		sql.append("  ti.original_currency as currency,                                      ");
		sql.append("   case when        ti.business_direction ='1'  then 'Financial'         ");
		sql.append("     else 'Retrocession Recovery' end as category,                       ");
		sql.append("   'Approved Settlment' as busitype,                                     ");
		sql.append(" case when ti.settlement_type in ('1','2','3')  then 'Loss Reserve'      ");
		sql.append("     when  ti.settlement_type = '4' then 'RIP provision'                 ");
		sql.append("      else r.description end  as    entryType,                           ");
		sql.append("  (ti.amount_oc*r.sign) as amount                                        ");
		sql.append("  from t_Ri_Claim_Settlement ts,                                         ");
		sql.append("  t_Ri_Claim_Settlement_Item ti, t_ri_claim_reserve_type r               ");
		sql.append("  where ts.settlement_id = ti.settlement_id                              ");
		sql.append(" and  ti.settlement_type = r.id                                          ");
		sql.append("  and ts.ref_id = ? and ts.status='").append(ClaimConstant.CLAIM_SETTLEMENT_STATUS__APPROVED).append("'   ");
		sql.append("                                                                         ");
		sql.append("  union all                                                              ");
		sql.append("  select                                                                 ");
		sql.append("  'USD Equivalent'  as currency,                                         ");
		sql.append("  case when        ti.business_direction ='1'  then 'Financial'          ");
		sql.append("     else 'Retrocession Recovery' end as category,                       ");
		sql.append("  'Approved Settlment' as busitype,                                      ");
		sql.append("  case when ti.settlement_type in ('1','2','3')  then 'Loss Reserve'     ");
		sql.append("     when  ti.settlement_type = '4' then 'RIP provision'                 ");
		sql.append("      else r.description end  as    entryType,                           ");
		sql.append("  (ti.amount_usd*r.sign) as amount                                        ");
		sql.append("  from t_Ri_Claim_Settlement ts,                                         ");
		sql.append("  t_Ri_Claim_Settlement_Item ti  ,t_ri_claim_reserve_type r              ");
		sql.append("  where ts.settlement_id = ti.settlement_id                              ");
		sql.append("  and  ti.settlement_type = r.id                                         ");
		sql.append("  and ts.ref_id = ? and ts.status='").append(ClaimConstant.CLAIM_SETTLEMENT_STATUS__APPROVED).append("'  ");

		
		List<ClaimExcelModel> claimModelList = this.getJdbcTemplate().query(sql.toString(),
				new Object[] { refId, refId,refId,refId },
				new BeanPropertyRowMapper<ClaimExcelModel>(ClaimExcelModel.class));
				
		return claimModelList;
	}
	
	public List<ClaimRecordsExcelModel> getClaimRecordsExcelList(Long refId){
		StringBuilder sql = new StringBuilder();
		sql.append("  select   t2.claim_no as claimno,                                   ");
		sql.append("   t3.full_name as contractsection,                                  ");
		sql.append("   t1.original_currency as currency,                                 ");
		sql.append("   t3.broker_name as broker,                                         ");
		sql.append("   t3.cedent_name as cedent,                                         ");
		sql.append("   t3.section_name as section,                                       ");
		sql.append("   t3.contract_code as contractId,                                   ");
		sql.append("  case when        t1.business_direction ='1'  then 'Financial'      ");
		sql.append("     else 'Retrocession Recovery' end as category,                   ");
		sql.append("   'Submitted Reserve' as busiType,                                  ");
		sql.append("   case when t1.reserve_type in ('1','2','3')  then 'Loss Reserve'   ");
		sql.append("     when  t1.reserve_type = '4' then 'RIP prov'                     ");
		sql.append("     when  t1.reserve_type = '5' then 'Tax prov'                     ");
		sql.append("     when  t1.reserve_type = '6' then 'OS others'                      ");
		sql.append("      else r.description end  as    entryType,                       ");
		sql.append("    (t1.org_amount_oc*r.sign)   as  amount,                          ");
		sql.append("   case when t2.status = '0' then 'open' else 'close' end as status, ");
		sql.append("     t2.update_time as dateOfUpdate                                  ");
		sql.append("   from t_Ri_Claim_Reserve t1 ,t_Ri_Claim_info t2,                   ");
		sql.append("   t_Ri_Claim_Section_Info t3,t_ri_claim_reserve_type r              ");
		sql.append("    where t1.ref_id = t2.claim_id                                    ");
		sql.append("    and t2.claim_id = t3.ref_id                                      ");
		sql.append("    and t1.business_direction = t3.business_direction                ");
		sql.append("    and t1.section_id = t3.section_id                                ");
		sql.append("    and t1.reserve_type = r.id                                       ");
		sql.append("   and t1.ref_id = ?                                                 ");  
		sql.append("   and t1.status <>'").append(ClaimConstant.CLAIM_RESERVE_STATUS__NEW).append("'  ");   
		sql.append("   union all                                                        ");  
		sql.append("  select   t2.claim_no as claimno,                                   ");
		sql.append("   t3.full_name as contractsection,                                  ");
		sql.append("   'USD Equirent' as currency,                                      ");
		sql.append("   t3.broker_name as broker,                                         ");
		sql.append("   t3.cedent_name as cedent,                                         ");
		sql.append("   t3.section_name as section,                                       ");
		sql.append("   t3.contract_code as contractId,                                   ");
		sql.append("  case when        t1.business_direction ='1'  then 'Financial'      ");
		sql.append("     else 'Retrocession Recovery' end as category,                   ");
		sql.append("   'Submitted Reserve' as busiType,                                  ");
		sql.append("   case when t1.reserve_type in ('1','2','3')  then 'Loss Reserve'   ");
		sql.append("     when  t1.reserve_type = '4' then 'RIP prov'                     ");
		sql.append("     when  t1.reserve_type = '5' then 'Tax prov'                     ");
		sql.append("     when  t1.reserve_type = '6' then 'OS others'                      ");
		sql.append("      else r.description end  as    entryType,                       ");
		sql.append("    (t1.org_amount_usd*r.sign)   as  amount,                         ");
		sql.append("   case when t2.status = '0' then 'open' else 'close' end as status, ");
		sql.append("     t2.update_time as dateOfUpdate                                  ");
		sql.append("   from t_Ri_Claim_Reserve t1 ,t_Ri_Claim_info t2,                   ");
		sql.append("   t_Ri_Claim_Section_Info t3,t_ri_claim_reserve_type r              ");
		sql.append("    where t1.ref_id = t2.claim_id                                    ");
		sql.append("    and t2.claim_id = t3.ref_id                                      ");
		sql.append("    and t1.business_direction = t3.business_direction                ");
		sql.append("    and t1.section_id = t3.section_id                                ");
		sql.append("    and t1.reserve_type = r.id                                       ");
		sql.append("   and t1.ref_id = ?                                                 ");  
		sql.append("   and t1.status <>'").append(ClaimConstant.CLAIM_RESERVE_STATUS__NEW).append("'  ");   
		sql.append("   union all                                                        ");  
		sql.append(" select tc.claim_no as claimno,                                     ");
		sql.append(" rf.full_name as contractsection,                                   ");
		sql.append(" ti.original_currency as currency,                                  ");
		sql.append(" rf.broker_name as broker,                                          ");
		sql.append(" rf.cedent_name as cedent,                                          ");
		sql.append(" rf.section_name as section,                                        ");
		sql.append(" rf.contract_code as contractId,                                    ");
		sql.append("  case when  ti.business_direction ='1'  then 'Financial'           ");
		sql.append("   else 'Retrocession Recovery' end as category,                    ");
		sql.append(" 'Approved Settlment' as busiType,                                  ");
		sql.append(" case when ti.settlement_type in ('1','2','3')  then 'Loss Reserve' ");
		sql.append("   when  ti.settlement_type = '4' then 'RIP received'               ");
		sql.append("   when  ti.settlement_type = '5' then 'Tax paid'                   ");
		sql.append("   when  ti.settlement_type = '6' then 'Others paid'                ");
		sql.append("    else r.description end  as    entryType,                        ");
		sql.append("   (ti.amount_oc*r.sign)  as  amount,                               ");
		sql.append(" case when tc.status = '0' then 'open' else 'close' end as status,  ");
		sql.append("   tc.update_time as dateOfUpdate                                   ");
		sql.append(" from t_Ri_Claim_Settlement ts,                                     ");
		sql.append(" t_Ri_Claim_Settlement_Item ti ,                                    ");
		sql.append(" t_Ri_Claim_Section_Info rf,                                        ");
		sql.append(" t_Ri_Claim_Info tc,                                                ");
		sql.append(" t_ri_claim_reserve_type r                                          ");
		sql.append(" where ts.settlement_id = ti.settlement_id                          ");
		sql.append(" and ts.ref_id = rf.ref_id                                          ");
		sql.append(" and ti.section_id = rf.section_id                                  ");
		sql.append(" and ti.business_direction = rf.business_direction                  ");
		sql.append(" and tc.claim_id = ts.ref_id                                        ");
		sql.append(" and ti.settlement_type = r.id                                      ");
		sql.append("   and ts.ref_id = ?                                                ");  
		sql.append("   and ts.status= '").append(ClaimConstant.CLAIM_SETTLEMENT_STATUS__APPROVED).append("' "); 
		sql.append("   union all                                                        ");  
		sql.append(" select tc.claim_no as claimno,                                     ");
		sql.append(" rf.full_name as contractsection,                                   ");
		sql.append(" 'USD Equirent' as currency,                                        ");
		sql.append(" rf.broker_name as broker,                                          ");
		sql.append(" rf.cedent_name as cedent,                                          ");
		sql.append(" rf.section_name as section,                                        ");
		sql.append(" rf.contract_code as contractId,                                    ");
		sql.append("  case when  ti.business_direction ='1'  then 'Financial'           ");
		sql.append("   else 'Retrocession Recovery' end as category,                    ");
		sql.append(" 'Approved Settlment' as busiType,                                  ");
		sql.append(" case when ti.settlement_type in ('1','2','3')  then 'Loss Reserve' ");
		sql.append("   when  ti.settlement_type = '4' then 'RIP received'               ");
		sql.append("   when  ti.settlement_type = '5' then 'Tax paid'                   ");
		sql.append("   when  ti.settlement_type = '6' then 'Others paid'                ");
		sql.append("    else r.description end  as    entryType,                        ");
		sql.append("   (ti.amount_usd*r.sign)  as  amount,                              ");
		sql.append(" case when tc.status = '0' then 'open' else 'close' end as status,  ");
		sql.append("   tc.update_time as dateOfUpdate                                   ");
		sql.append(" from t_Ri_Claim_Settlement ts,                                     ");
		sql.append(" t_Ri_Claim_Settlement_Item ti ,                                    ");
		sql.append(" t_Ri_Claim_Section_Info rf,                                        ");
		sql.append(" t_Ri_Claim_Info tc,                                                ");
		sql.append(" t_ri_claim_reserve_type r                                          ");
		sql.append(" where ts.settlement_id = ti.settlement_id                          ");
		sql.append(" and ts.ref_id = rf.ref_id                                          ");
		sql.append(" and ti.section_id = rf.section_id                                  ");
		sql.append(" and ti.business_direction = rf.business_direction                  ");
		sql.append(" and tc.claim_id = ts.ref_id                                        ");
		sql.append(" and ti.settlement_type = r.id                                      ");
		sql.append("   and ts.ref_id = ?                                                ");  
		sql.append("   and ts.status= '").append(ClaimConstant.CLAIM_SETTLEMENT_STATUS__APPROVED).append("' "); 

		List<ClaimRecordsExcelModel> claimModelRecordsList = this.getJdbcTemplate().query(sql.toString(),
				new Object[] {refId, refId,refId,refId},
				new BeanPropertyRowMapper<ClaimRecordsExcelModel>(ClaimRecordsExcelModel.class));
		
		return claimModelRecordsList;
	}
	
	
	public AppUser getUserInfo(String userName) {

		StringBuilder sql = new StringBuilder();

		sql.append("  select user_id,user_name  from t_pub_user  where user_name=?  ");
		

		AppUser user = new AppUser();

		this.getJdbcTemplate().query(sql.toString(), new Object[] { userName }, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				user.setUserId(rs.getLong("user_id"));
				user.setUserName(rs.getString("user_name"));
				
			}
		});
		return user;

	}
}
