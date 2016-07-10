package com.ebao.ap99.claim.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.jdbc.core.RowMapper;

import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.entity.TRiClaimSettlement;
import com.ebao.ap99.parent.constant.ReInsuranceCst;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class RiClaimSettlementDao extends BaseDao<TRiClaimSettlement> {

	@Override
	public Class<TRiClaimSettlement> getEntityClass() {
		return TRiClaimSettlement.class;
	}

	public List<TRiClaimSettlement> getOutstandingSettleByRefId(long refId) {
		final Query query = this.getEntityManager().createNamedQuery("TRiClaimSettlement.findOutstandingByRefId",
				this.getEntityClass());
		query.setParameter("refId", refId);
		@SuppressWarnings("unchecked")
		List<TRiClaimSettlement> list = query.getResultList();
		return list;
	}

	public List<TRiClaimSettlement> getNewSettleByRefId(long refId) {
		final Query query = this.getEntityManager().createNamedQuery("TRiClaimSettlement.findNewSettlementByRefId",
				this.getEntityClass());
		query.setParameter("refId", refId);
		@SuppressWarnings("unchecked")
		List<TRiClaimSettlement> list = query.getResultList();
		return list;
	}

	
	// public List<TRiClaimSettlement> getOpenSettleByRefId(long refId) {
	// final Query query =
	// this.getEntityManager().createNamedQuery("TRiClaimSettlement.findOpenSettlementByRefId",
	// this.getEntityClass());
	// query.setParameter("refId", refId);
	// @SuppressWarnings("unchecked")
	// List<TRiClaimSettlement> list = query.getResultList();
	// return list;
	// }

	public List<TRiClaimSettlement> getApprovedSettlementByRefId(long refId) {
		final Query query = this.getEntityManager()
				.createNamedQuery("TRiClaimSettlement.findApprovedSettlementByRefId", this.getEntityClass());
		query.setParameter("refId", refId);
		@SuppressWarnings("unchecked")
		List<TRiClaimSettlement> list = query.getResultList();
		return list;
	}

	public Double getSettlementUsdEquivalent(long refId) {

		Double result = Double.valueOf(0);

/*		select sum(t2.amount_usd * t3.sign) settlement_usd_equivalent
		  from t_ri_claim_settlement      t1,
		       t_ri_claim_settlement_item t2,
		       t_ri_claim_reserve_type          t3
		 where t1.settlement_id = t2.settlement_id
		   and t1.ref_id = ?
		   and t1.status = 3
		   and t2.settlement_type = t3.id
		   and t1.business_direction = 1;*/
		
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(t2.amount_usd * t3.sign) settlement_usd_equivalent ");
		sql.append("from t_ri_claim_settlement t1, t_ri_claim_settlement_item t2, t_ri_claim_reserve_type t3 ");
		sql.append("where t1.settlement_id = t2.settlement_id ");
		sql.append("and t2.settlement_type = t3.id ");
		sql.append("and t1.business_direction = '").append(ReInsuranceCst.CONTRACT_CATEGORY_ASSUME).append("' ");
		sql.append("and t1.status = '").append(ClaimConstant.CLAIM_SETTLEMENT_STATUS__APPROVED).append("' ");
		sql.append("and t1.ref_id = ? ");

		Object[] params = new Object[] { refId };

		@SuppressWarnings({ "unchecked", "rawtypes" })
		List results = this.getJdbcTemplate().query(sql.toString(), params, new RowMapper() {
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Map<String, Double> u = new HashMap();
				u.put("settlement_usd_equivalent", rs.getDouble("settlement_usd_equivalent"));
				return u;
			}
		});

		@SuppressWarnings("unchecked")
		Iterator<Map<String, Double>> it = results.iterator();
		while (it.hasNext()) {
			Map<String, Double> map = it.next();
			result = map.get("settlement_usd_equivalent");
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Double> getApprovedSettleAmountGroupByCurrency(long refId) {

		Map<String, Double> result = new HashMap<String, Double>();

/*		select t2.original_currency currency,
	       sum(t2.amount_oc * t3.sign) approved_settlement_amount
	  from t_ri_claim_settlement      t1,
	       t_ri_claim_settlement_item t2,
	       t_ri_claim_reserve_type          t3
	 where t1.settlement_id = t2.settlement_id
	   and t1.ref_id = ?
	   and t1.status = 3
	   and t2.settlement_type = t3.id
	   and t1.business_direction = 1
	 group by t2.original_currency;*/

		StringBuilder sql = new StringBuilder();
		sql.append("select t2.original_currency currency, sum(t2.amount_oc * t3.sign) approved_settlement_amount ");
		sql.append("from t_ri_claim_settlement t1, t_ri_claim_settlement_item t2, t_ri_claim_reserve_type t3 ");
		sql.append("where t1.settlement_id = t2.settlement_id ");
		sql.append("and t2.settlement_type = t3.id ");
		sql.append("and t1.business_direction = '").append(ReInsuranceCst.CONTRACT_CATEGORY_ASSUME).append("' ");
		sql.append("and t1.status = '").append(ClaimConstant.CLAIM_SETTLEMENT_STATUS__APPROVED).append("' ");
		sql.append("and t1.ref_id = ? ");
		sql.append("group by t2.original_currency ");

		Object[] params = new Object[] { refId };

		List results = this.getJdbcTemplate().query(sql.toString(), params, new RowMapper() {
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Map<String, Double> u = new HashMap();
				u.put(rs.getString("currency"), rs.getDouble("approved_settlement_amount"));
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
}
