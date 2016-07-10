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
import org.springframework.jdbc.core.RowMapper;

import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.entity.TRiClaimReserve;
import com.ebao.ap99.claim.entity.TRiClaimSettlementItem;
import com.ebao.ap99.parent.constant.ReInsuranceCst;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class RiClaimReserveDao extends BaseDao<TRiClaimReserve> {


	@Override
	public Class<TRiClaimReserve> getEntityClass() {
		
		return TRiClaimReserve.class;
	}

	public List<TRiClaimReserve> getReserveList(long refId) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimReserve.findByRefId", TRiClaimReserve.class);
		query.setParameter("refId", refId);
		@SuppressWarnings("unchecked")
		List<TRiClaimReserve> list = query.getResultList();
		
		return list;
	}
	public List<TRiClaimReserve> getRelatedReserveList(long reserveId) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimReserve.findByReserveId", TRiClaimReserve.class);
		query.setParameter("reserveId", reserveId);
		@SuppressWarnings("unchecked")
		List<TRiClaimReserve> list = query.getResultList();
		
		return list;
	}
	
//	public List<TRiClaimReserve> getRReserveList(long refId){
//		StringBuilder sql = new StringBuilder();
//		sql.append("select * from t_ri_claim_reserve where ref_id = ? and business_direction = '2' and status ='0'");
//
//		List<TRiClaimReserve> retroReserveList = this.getJdbcTemplate().query(sql.toString(),
//				new Object[] { refId }, new BeanPropertyRowMapper<>(TRiClaimReserve.class));		
//			return retroReserveList;
//	}
//	
	@SuppressWarnings("unchecked")
	public List<TRiClaimReserve> getFinaReserveList(long refId){
		final Query query = getEntityManager().createNamedQuery("TRiClaimSectionInfo.findByRefFronting", Long.class);
		query.setParameter("refId", refId);
		query.setParameter("businessDirection", "1");
		query.setParameter("frontingFlag", "1");

		List<Long> sectionIdList = query.getResultList();
//		List<TRiClaimReserve> currentList =new ArrayList<TRiClaimReserve>();
		List<TRiClaimReserve> allList =new ArrayList<TRiClaimReserve>();
		if(sectionIdList.size() > 0){
			for(int i = 0;i<sectionIdList.size();i++){
				List<TRiClaimReserve> currentList = this.getfReserve(refId, sectionIdList.get(i));
			    allList.addAll(currentList);
			}
			
		}
		return allList;
	}
	
	
	public List<TRiClaimReserve> getfReserve(Long refId,Long sectionId){
		final Query query = getEntityManager().createNamedQuery("TRiClaimReserve.findreservelist", TRiClaimReserve.class);
		query.setParameter("refId", refId);
		query.setParameter("sectionId", sectionId);
		query.setParameter("businessDirection", "1");
		query.setParameter("status", "0");
		List<TRiClaimReserve> list = query.getResultList();

		return list;
	}
	

	
	//findSectionByRefId
	public List<Long> getSectionId(long refId) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimReserve.findSectionByRefId", Long.class);
		query.setParameter("refId", refId);
		@SuppressWarnings("unchecked")
		List<Long> list = query.getResultList();
		return list;
	}
	public List<Long> getSectionIdList(Long refId){
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct section_id from t_ri_claim_reserve where ref_id = ?  ");//and status = '").append(ClaimConstant.CLAIM_RESERVE_STATUS__INFORCE).append("'
		List<Long> sectionList = this.getJdbcTemplate().query(sql.toString(),
				new Object[] { refId },
				new BeanPropertyRowMapper<>(Long.class));
		
		return sectionList;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getAllCurrencyOnClaim(long claimId) {

		Map<String, String> result = new HashMap<String, String>();

		/*
		 * select distinct original_currency from t_ri_claim_reserve where
		 * ref_id = 1644143 union select distinct t2.original_currency from
		 * t_ri_claim_settlement t1, t_ri_claim_settlement_item t2 where
		 * t1.settlement_id = t2.settlement_id and t1.status = 3 and t1.ref_id =
		 * 1644143;
		 */

		StringBuilder sql = new StringBuilder();
		sql.append("select distinct original_currency ");
		sql.append("from t_ri_claim_reserve ");
		sql.append("where ref_id = ? ");
		sql.append("and business_direction = '").append(ReInsuranceCst.CONTRACT_CATEGORY_ASSUME).append("' ");
		sql.append("union ");
		sql.append("select distinct t2.original_currency ");
		sql.append("from t_ri_claim_settlement t1, t_ri_claim_settlement_item t2 ");
		sql.append(" where t1.settlement_id = t2.settlement_id ");
		sql.append(" and t1.status = '").append(ClaimConstant.CLAIM_SETTLEMENT_STATUS__APPROVED).append("' ");
		sql.append(" and t1.business_direction = '").append(ReInsuranceCst.CONTRACT_CATEGORY_ASSUME).append("' ");
		sql.append(" and t1.ref_id = ? ");

		Object[] params = new Object[] { claimId, claimId };

		@SuppressWarnings("rawtypes")
		List results = this.getJdbcTemplate().query(sql.toString(), params, new RowMapper() {
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Map<String, String> u = new HashMap();
				u.put(rs.getString("original_currency"), rs.getString("original_currency"));
				return u;
			}
		});

		Iterator<Map<String, String>> it = results.iterator();
		while (it.hasNext()) {
			Map<String, String> map = it.next();
			result.putAll(map);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Double> getLatestReserveAmountGroupByCurrency(long claimId) {

		Map<String, Double> result = new HashMap<String, Double>();

		/*
		 * select t.original_currency currency, sum(t.amount_oc)
		 * latest_reserve_amount from t_ri_claim_reserve t where t.ref_id =
		 * 1646733 group by t.original_currency;
		 */

		StringBuilder sql = new StringBuilder();
		sql.append("select t1.original_currency currency, sum(t1.amount_oc * t2.sign) latest_reserve_amount ");
		sql.append("from t_ri_claim_reserve t1, t_ri_claim_reserve_type t2 ");
		sql.append("where t1.reserve_type = t2.id ");
		sql.append("and t1.business_direction = '").append(ReInsuranceCst.CONTRACT_CATEGORY_ASSUME).append("' ");
		sql.append("and t1.ref_id = ? ");
		sql.append("group by t1.original_currency ");

		Object[] params = new Object[] { claimId };

		@SuppressWarnings("rawtypes")
		List results = this.getJdbcTemplate().query(sql.toString(), params, new RowMapper() {
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Map<String, Double> u = new HashMap();
				u.put(rs.getString("currency"), rs.getDouble("latest_reserve_amount"));
				return u;
			}
		});

		Iterator<Map<String, Double>> it = results.iterator();
		while (it.hasNext()) {
			Map<String, Double> map = it.next();
			result.putAll(map);
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Double getReserveUsdEquivalent(long claimId) {

		Double result = Double.valueOf(0);

/*		select sum(t1.amount_usd * t2.sign) reserve_usd_equivalent
		  from t_ri_claim_reserve t1, t_ri_claim_reserve_type t2
		 where t1.ref_id = ?
		 and t1.reserve_type = t2.id
		 and t1.business_direction = 1;*/

		StringBuilder sql = new StringBuilder();
		sql.append("select sum(t1.amount_usd * t2.sign) reserve_usd_equivalent ");
		sql.append("from t_ri_claim_reserve t1, t_ri_claim_reserve_type t2 ");
		sql.append("where t1.reserve_type = t2.id ");
		sql.append("and t1.business_direction = '").append(ReInsuranceCst.CONTRACT_CATEGORY_ASSUME).append("' ");
		sql.append("and t1.ref_id = ? ");

		Object[] params = new Object[] { claimId };

		List results = this.getJdbcTemplate().query(sql.toString(), params, new RowMapper() {
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Map<String, Double> u = new HashMap();
				u.put("reserve_usd_equivalent", rs.getDouble("reserve_usd_equivalent"));
				return u;
			}
		});

		Iterator<Map<String, Double>> it = results.iterator();
		while (it.hasNext()) {
			Map<String, Double> map = it.next();
			result = map.get("reserve_usd_equivalent");
		}
		return result;
	}
	//TRiClaimReserve.ifHasClaim
	public boolean getIfHasClaimBySectionId(long sectionId) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimReserve.ifHasClaim", Integer.class);
		query.setParameter("sectionId", sectionId);
		boolean flag = false;
		@SuppressWarnings("unchecked")
		List<Integer> list = query.getResultList();
		if(list.size()>0){
			flag = true;
		}
		return flag;
	}
	
	
	
	public List<Long> getrefIdbySectionId(Long sectionId) {
		final Query query = getEntityManager().createNamedQuery("TRiClaimReserve.getrefIdbySectionId", Long.class);
		query.setParameter("sectionId", sectionId);
		@SuppressWarnings("unchecked")
		List<Long> list = query.getResultList();
		return list;
	}
}
