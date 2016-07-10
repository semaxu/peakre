/**
 * 
 */
package com.ebao.ap99.claim.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.ebao.ap99.claim.entity.TRiClaimReserveLog;
import com.ebao.ap99.claim.model.ReserveHistoryInfo;
import com.ebao.ap99.claim.model.ReserveHistoryQuery;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author yujie.zhang
 *
 */
public class RiClaimReserveLogDao extends BaseDao<TRiClaimReserveLog> {
	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiClaimReserveLog> getEntityClass() {
		return TRiClaimReserveLog.class;
	}

	public List<ReserveHistoryInfo> getReserveHistory(ReserveHistoryQuery reserveHistoryQuery) {
		List<Object> param = new ArrayList<Object>();
		param.add(reserveHistoryQuery.getRefId());
		param.add(reserveHistoryQuery.getBusinessDirection());

		StringBuilder sql = new StringBuilder();

		sql.append("  select                                                                                       ");
		sql.append("  crl.reserve_his_id as reserveHisId,                                                          ");
		sql.append("  crl.amount_oc as amountOc,                                                                   ");
		sql.append("  crl.amount_usd as amountUsd,                                                                 ");
		sql.append("  crl.broker_refer as brokerRefer,                                                             ");
		sql.append("  crl.business_direction as businessDirection,                                                 ");
		sql.append("  crl.cedent_refer as cedentRefer,                                                             ");
		sql.append("  crl.exchange_rate as  exchangeRate,                                                          ");
		sql.append("  crl.insert_by as insertBy,                                                                   ");
		sql.append("  crl.insert_time as insertTime,                                                               ");
		sql.append("  crl.original_currency as originalCurrency,                                                   ");
		sql.append("  crl.posting_flag as postingFlag,                                                             ");
		sql.append("  crl.remark as remark,                                                                        ");
		sql.append("  crl.reserve_type as reserveType,                                                             ");
		sql.append("  crl.section_id as sectionId,                                                                 ");
		sql.append("  crl.status as status,                                                                        ");
		sql.append("  crl.update_by as updateBy,                                                                   ");
		sql.append("  crl.update_time as updateTime,                                                               ");
		sql.append("  s.uw_year as underwritingYear,                                                               ");
		sql.append("  s.contract_code as ContractCode,                                                             ");
		sql.append("  s.full_name as contractSectionName                                                           ");
		sql.append("  from  t_ri_claim_reserve_log crl,t_ri_claim_reserve cr ,t_ri_claim_section_info s            ");
		sql.append("  where cr.reserve_id=crl.reserve_id                                                           ");
		sql.append("  and cr.ref_id=s.ref_id and crl.business_direction = s.business_direction                     ");
		sql.append("  and cr.section_id=s.section_id                                                               ");
		sql.append("  and cr.ref_id = ?                                                                            ");
		sql.append("  and cr.business_direction=?                                                                  ");

		logger.info("0000" + reserveHistoryQuery.getContractSection() + "0000");

		if (!"".equals(reserveHistoryQuery.getContractSection()) && reserveHistoryQuery.getContractSection() != 0) {
			sql.append("  and s.section_id =?                                                                      ");
			logger.info("0000------------0000");

			param.add(reserveHistoryQuery.getContractSection());
		}
		if (!"".equals(reserveHistoryQuery.getUnderwritingYear()) && reserveHistoryQuery.getUnderwritingYear() != 0) {
			sql.append("  and s.uw_year =?                                                                         ");
			param.add(reserveHistoryQuery.getUnderwritingYear());
		}
		if (StringUtils.isNotBlank(reserveHistoryQuery.getReserveType())) {
			sql.append("  and crl.reserve_type =?                                                                  ");
			param.add(reserveHistoryQuery.getReserveType());
		}
		sql.append("  order by crl.update_time desc                                                                ");

		logger.info(sql.toString());

		Object[] params = new Object[param.size()];
		param.toArray(params);

		List<ReserveHistoryInfo> reserveHistoryList = this.getJdbcTemplate().query(sql.toString(), params,
				new BeanPropertyRowMapper<ReserveHistoryInfo>(ReserveHistoryInfo.class));

		return reserveHistoryList;

	}

	public List<ReserveHistoryInfo> getLatestReserve(ReserveHistoryQuery reserveHistoryQuery) {
		List<Object> param = new ArrayList<Object>();
		param.add(reserveHistoryQuery.getRefId());
		param.add(reserveHistoryQuery.getBusinessDirection());

		StringBuilder sql = new StringBuilder();
		sql.append("  select                                                                              ");
		sql.append("  cr.reserve_id         as reserveId,                                                 ");
		sql.append("  cr.org_amount_oc          as amountOc,                                              ");
		sql.append("  cr.org_amount_usd         as amountUsd,                                             ");
		sql.append("  cr.broker_refer       as brokerRefer,                                               ");
		sql.append("  cr.business_direction as businessDirection,                                         ");
		sql.append("  cr.cedent_refer       as cedentRefer,                                               ");
		sql.append("  cr.exchange_rate      as exchangeRate,                                              ");
		sql.append("  cr.insert_by          as insertBy,                                                  ");
		sql.append("  cr.insert_time        as insertTime,                                                ");
		sql.append("  cr.original_currency  as originalCurrency,                                          ");
		sql.append("  cr.posting_flag       as postingFlag,                                               ");
		sql.append("  cr.remark             as remark,                                                    ");
		sql.append("  cr.reserve_type       as reserveType,                                               ");
		sql.append("  cr.section_id         as sectionId,                                                 ");
		sql.append("  cr.status             as status,                                                    ");
		sql.append("  cr.update_by          as updateBy,                                                  ");
		sql.append("  cr.update_time        as updateTime,                                                ");
		sql.append("  s.uw_year              as underwritingYear,                                         ");
		sql.append("  s.contract_code        as ContractCode,                                             ");
		sql.append("  s.full_name            as contractSectionName                                       ");
		sql.append("  from t_ri_claim_reserve cr ,t_ri_claim_section_info s                               ");
		sql.append("  where  cr.ref_id=s.ref_id                                                           ");
		sql.append("  and cr.section_id=s.section_id and  cr.business_direction=s.business_direction      ");
		sql.append("  and cr.status=1                                                                     ");
		sql.append("  and cr.ref_id =?                                                                    ");
		sql.append("  and cr.business_direction=?                                                         ");

		if (!"".equals(reserveHistoryQuery.getContractSection()) && reserveHistoryQuery.getContractSection() != 0) {
			sql.append("  and s.section_id =?                                                             ");
			param.add(reserveHistoryQuery.getContractSection());
		}

		if (!"".equals(reserveHistoryQuery.getUnderwritingYear()) && reserveHistoryQuery.getUnderwritingYear() != 0) {
			sql.append("  and s.uw_year =?                                                                ");
			param.add(reserveHistoryQuery.getUnderwritingYear());
		}

		if (StringUtils.isNotBlank(reserveHistoryQuery.getReserveType())) {
			sql.append("  and cr.reserve_type =?                                                          ");
			param.add(reserveHistoryQuery.getReserveType());
		}

		sql.append("  order by cr.update_time desc                                                        ");
		logger.info(sql.toString());

		Object[] params = new Object[param.size()];
		param.toArray(params);

		List<ReserveHistoryInfo> reserveList = this.getJdbcTemplate().query(sql.toString(), params,
				new BeanPropertyRowMapper<ReserveHistoryInfo>(ReserveHistoryInfo.class));

		return reserveList;
	}

	public List<ReserveHistoryInfo> getLatestReserveSummary(ReserveHistoryQuery reserveHistoryQuery) {

		List<Object> param = new ArrayList<Object>();
		param.add(reserveHistoryQuery.getRefId());
		param.add(reserveHistoryQuery.getBusinessDirection());

		StringBuilder sql = new StringBuilder();
		sql.append("  select cr.original_currency as originalCurrency,                            ");
		sql.append("  sum(cr.org_amount_oc*b.sign) as amountOc,                                   ");
		sql.append("  sum(cr.org_amount_usd*b.sign) as amountUsd                                  ");
		sql.append("  from t_Ri_Claim_Reserve cr,t_ri_claim_section_info s,t_ri_claim_reserve_type b   ");
		sql.append("  where  cr.reserve_type=b.id                                                ");
		sql.append("  and cr.ref_id=s.ref_id                                                     ");
		sql.append("  and cr.section_id=s.section_id                                             ");
		sql.append("  and cr.status=1 and  cr.business_direction=s.business_direction            ");
		sql.append("  and cr.ref_id =?                                                           ");
		sql.append("  and cr.business_direction=?                                                ");

		if (!"".equals(reserveHistoryQuery.getContractSection()) && reserveHistoryQuery.getContractSection() != 0) {
			sql.append("  and s.section_id =?                                                    ");
			param.add(reserveHistoryQuery.getContractSection());
		}

		if (!"".equals(reserveHistoryQuery.getUnderwritingYear()) && reserveHistoryQuery.getUnderwritingYear() != 0) {
			sql.append("  and s.uw_year =?                                                       ");
			param.add(reserveHistoryQuery.getUnderwritingYear());
		}

		if (StringUtils.isNotBlank(reserveHistoryQuery.getReserveType())) {
			sql.append("  and cr.reserve_type =?                                                 ");
			param.add(reserveHistoryQuery.getReserveType());
		}
		sql.append("  group by cr.original_currency                                               ");
		logger.info(sql.toString());

		Object[] params = new Object[param.size()];
		param.toArray(params);

		List<ReserveHistoryInfo> reserveList = this.getJdbcTemplate().query(sql.toString(), params,
				new BeanPropertyRowMapper<ReserveHistoryInfo>(ReserveHistoryInfo.class));

		return reserveList;
	}

	public List<TreeModel> getUWYear(long claimId, String BusinessDirection) {
		StringBuilder sql = new StringBuilder();

		sql.append("  select       distinct s.uw_year as id,s.uw_year as text                       ");
		sql.append("  from t_ri_claim_reserve_log crl,t_ri_claim_reserve cr ,t_ri_claim_section_info s    ");
		sql.append("  where cr.reserve_id=crl.reserve_id                                                  ");
		sql.append("  and cr.ref_id=s.ref_id                                                              ");
		sql.append("  and cr.section_id=s.section_id                                                      ");
		sql.append("  and cr.ref_id =?                                                                    ");
		sql.append("  and cr.business_direction=?                                                         ");

		// List<ReserveHistoryInfo> reserveList =
		// this.getJdbcTemplate().query(sql.toString(),
		// new Object[] { claimId, BusinessDirection },
		// new
		// BeanPropertyRowMapper<ReserveHistoryInfo>(ReserveHistoryInfo.class));

		List<TreeModel> uwList = this.getJdbcTemplate().query(sql.toString(),
				new Object[] { claimId, BusinessDirection }, new BeanPropertyRowMapper<TreeModel>(TreeModel.class));

		// for (ReserveHistoryInfo re : reserveList) {
		// TreeModel treeModel = new TreeModel();
		// treeModel.setId(re.getUnderwritingYear());
		// treeModel.setText(re.getUnderwritingYear() + "");
		// uwList.add(treeModel);
		// }

		return uwList;
	}

	public List<TreeModel> getContractSection(long claimId, String BusinessDirection) {

		StringBuilder sql = new StringBuilder();

		sql.append("  select    distinct  crl.section_id as id,   s.full_name as text       ");
		sql.append("  from  t_ri_claim_reserve_log crl,t_ri_claim_reserve cr ,t_ri_claim_section_info s           ");
		sql.append("  where cr.reserve_id=crl.reserve_id                                                          ");
		sql.append("  and cr.ref_id=s.ref_id                                                                      ");
		sql.append("  and cr.section_id=s.section_id                                                              ");
		sql.append("  and cr.ref_id =?                                                                            ");
		sql.append("  and cr.business_direction=?                                                                 ");

		// List<ReserveHistoryInfo> reserveList =
		// this.getJdbcTemplate().query(sql.toString(),
		// new Object[] { claimId, BusinessDirection },
		// new
		// BeanPropertyRowMapper<ReserveHistoryInfo>(ReserveHistoryInfo.class));

		List<TreeModel> reserveList = this.getJdbcTemplate().query(sql.toString(),
				new Object[] { claimId, BusinessDirection }, new BeanPropertyRowMapper<TreeModel>(TreeModel.class));

		// for (ReserveHistoryInfo re : reserveList) {
		// TreeModel treeModel = new TreeModel();
		// treeModel.setId(re.getSectionId());
		// treeModel.setText(re.getContractSectionName());
		// csList.add(treeModel);
		// }

		return reserveList;
	}
}
