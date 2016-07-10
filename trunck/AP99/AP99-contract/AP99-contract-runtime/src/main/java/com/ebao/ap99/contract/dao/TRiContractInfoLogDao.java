package com.ebao.ap99.contract.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ebao.ap99.contract.entity.TRiContClaimItemLog;
import com.ebao.ap99.contract.entity.TRiContCurrencyLog;
import com.ebao.ap99.contract.entity.TRiContDeductionsAttLog;
import com.ebao.ap99.contract.entity.TRiContDeductionsCarrLog;
import com.ebao.ap99.contract.entity.TRiContDeductionsItemLog;
import com.ebao.ap99.contract.entity.TRiContLimitEventLog;
import com.ebao.ap99.contract.entity.TRiContLimitItemLog;
import com.ebao.ap99.contract.entity.TRiContPremiumItemLog;
import com.ebao.ap99.contract.entity.TRiContReinstateItemLog;
import com.ebao.ap99.contract.entity.TRiContractInfoLog;
import com.ebao.ap99.contract.model.BusinessConditionVO;
import com.ebao.ap99.contract.model.ContractClaimConditionItem;
import com.ebao.ap99.contract.model.ContractVO;
import com.ebao.ap99.contract.model.CurrencyVO;
import com.ebao.ap99.contract.model.DeductionsAttachVO;
import com.ebao.ap99.contract.model.DeductionsCarriedVO;
import com.ebao.ap99.contract.model.DeductionsItemVO;
import com.ebao.ap99.contract.model.LimitEventVO;
import com.ebao.ap99.contract.model.LimitItemVO;
import com.ebao.ap99.contract.model.PremiumItemVO;
import com.ebao.ap99.contract.model.ReinstateItemVO;
import com.ebao.ap99.contract.util.ContractCst;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.unicorn.platform.foundation.context.SpringContextUtils;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContractInfoLogDao extends BaseDao<TRiContractInfoLog> {
	@PersistenceContext
	private EntityManager em;

	@Override
	public Class<TRiContractInfoLog> getEntityClass() {
		return TRiContractInfoLog.class;
	}

	/**
	 * 
	 * @param contCompId
	 * @param operateId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public ContractVO getContractVOForLog(Long contCompId, Long operateId, String status) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT T1.OPERATE_ID,T1.CONT_COMP_ID,T1.TYPE,T1.PARENT_ID,T1.NAME,T1.FULL_NAME,T2.LINK_NAME,T2.RENEWAL_ID,T2.CONTRACT_CODE,"
						+ "T2.CONTRACT_NAME,T2.BROKER_REF,T2.CEDENT_REF,T2.MAINCLASS,T2.SUBCLASS,T2.LATEST_STATUS,T2.CONTRACT_TYPE,T2.CONTRACT_NATURE,"
						+ "T2.CONTRACT_CATEGORY,T2.FRONTING,T2.DEPOSIT_ACCOUNTING,T2.REINS_STARTING,T2.REINS_ENDING,T2.UW_YEAR,T2.INFORCE_DATE,"
						+ "T2.CEDENT,T2.CEDENT_COUNTRY,T2.MGA,T2.BROKER,T2.CO_BROKER,T2.INSURED,T2.REVIEWED,T2.UNDERWRITING,T2.ANALYTICS_RESP,"
						+ "T2.MARKET_RESP,T2.CREATED_BY,T2.TREATY_OWNER,T2.LAST_CHANGED,T2.UW_COMPANY,T2.REMARK,T2.INSERT_BY,T2.UPDATE_BY,"
						+ "T2.INSERT_TIME,T2.UPDATE_TIME,T2.DOCUMENT_ID,T2.PROTECTION_BASIC,T2.APPIAN_NO,T2.MAIN_CURRENCY,T2.PRICING_REF,"
						+ "T2.LEADER,T2.LEADER_NAME,T2.PORTFOLIO_TRANSFER,T2.REJECT_REASON,T2.MAIN_COVER_AREA,"
						// +
						// "T3.COVERED,T3.COVERED_REMARK,T3.PERIL,T3.PERIL_REMARK,T4.LOB,T4.COB,T4.CURRENCY,T4.UW_AREA,T4.COVERED_AREA,"
						+ "T4.PROTECTION_BASIC,"
						+ "T5.CLAIM_BASIS,T5.RETROACTIVE_DATE,T5.SUNSET_CHECK,T5.SUNSET_CLAUSE,T5.CLAIM_CURRENCY,T5.LOSS_ADVICE,T5.POSTING,"
						+ "T5.CASH_CALL_ADVICE,T5.NO_OF_DAY,T5.WETHER_INDEX,T5.PRICES_INDEX,T5.SPECIFY,"
						+ "T6.ACCOUNTING_BASIS,T6.EARNING_PATTERN,T6.ACCOUNT_FREQUENCY,T6.FIRST_ACCOUNT_AS_AT_DATE,T6.PERCENTAGE_OF_PREMIUM,"
						+ "T6.DATE_FOR_REVIEW,T6.PORTFOLIO_IN,T6.PORTFOLIO_IN_DATA,T6.PORTFOLIO_OUT,T6.PORTFOLIO_OUT_DATA,T6.ACCOUNT_REMARK,"
						+ "T6.ACCOUNT_DAYS,T6.SETTLEMENT_DAYS,T6.OUTSTANDING_LOSSES_IN,T6.OUTSTANDING_LOSSES_OUT,T6.PREMIUM_IN,T6.PREMIUM_OUT,T6.DUE_DATE,"
						+ "T7.PNOC_CEDENT_MONTH,T7.PNOC_CEDENT_DAY,T7.PNOC_REINSURER_MONTH,T7.PNOC_REINSURER_DAY,T7.PNOC_AUTOMATIC,T7.DNOC_WAR_MONTH,T7.DNOC_WAR_DAY,"
						+ "T7.DNOC_POLITICAL_MONTH,T7.DNOC_POLITICAL_DAY,T7.DNOC_SANCTION_MONTH,T7.DNOC_SANCTION_DAY ");
		sql.append("  FROM T_RI_CONTRACT_STRUCTURE_LOG T1 ");
		sql.append(
				"  JOIN T_RI_CONTRACT_INFO_LOG T2 ON T1.cont_comp_id = T2.cont_comp_id AND T1.operate_id = T2.operate_id ");
		// sql.append(" LEFT JOIN T_RI_CONTRACT_AREAPERIL_LOG T3 ON
		// T1.operate_id = T3.operate_id ");
		sql.append("  LEFT JOIN T_RI_CONTRACT_MORE_INFO_LOG T4 ON T1.operate_id = T4.operate_id ");
		sql.append("  LEFT JOIN T_RI_CONT_CLAIM_LOG T5 ON T1.operate_id = T5.operate_id ");
		sql.append("  LEFT JOIN T_RI_CONT_ACCOUNT_LOG T6 ON T1.operate_id = T6.operate_id ");
		sql.append("  LEFT JOIN T_RI_CONT_CANCEL_LOG T7 ON T1.operate_id = T7.operate_id ");
		sql.append(" WHERE T1.cont_comp_id = ?");
		if (status != null) {
			sql.append("   AND T2.latest_status = ?");
		}
		if (operateId != null) {
			sql.append("   AND T1.operate_id = ?");
		} else {
			sql.append("   AND T2.operate_id = (SELECT MAX(operate_id)");
			sql.append("                          FROM T_RI_CONTRACT_INFO_LOG ");
			sql.append("                         WHERE cont_comp_id = ? ");
			if (status != null) {
				sql.append("                           AND latest_status = ?");
			}
			sql.append(" ) ");
		}
		sql.append(" ORDER BY T2.operate_id DESC");
		ContractVO contractVO = new ContractVO();
		List<ContractVO> contractVOList = new ArrayList<ContractVO>();
		if (status != null) {
			if (operateId != null) {
				contractVOList = this.getJdbcTemplate().query(sql.toString(),
						new Object[] { contCompId, status, operateId },
						new BeanPropertyRowMapper<ContractVO>(ContractVO.class));
			} else {
				contractVOList = this.getJdbcTemplate().query(sql.toString(),
						new Object[] { contCompId, status, contCompId, status },
						new BeanPropertyRowMapper<ContractVO>(ContractVO.class));
			}
		} else {
			if (operateId != null) {
				contractVOList = this.getJdbcTemplate().query(sql.toString(), new Object[] { contCompId, operateId },
						new BeanPropertyRowMapper<ContractVO>(ContractVO.class));
			} else {
				contractVOList = this.getJdbcTemplate().query(sql.toString(), new Object[] { contCompId, contCompId },
						new BeanPropertyRowMapper<ContractVO>(ContractVO.class));
			}
		}

		if (CollectionUtils.isNotEmpty(contractVOList)) {
			contractVO = contractVOList.get(0);
		}
		// Get claim item info
		contractVO.setContractClaimConditionItemList(
				this.getContractClaimConditionItemListByOperateId(contCompId, contractVO.getOperateId()));
		return contractVO;
	}

	private List<ContractClaimConditionItem> getContractClaimConditionItemListByOperateId(Long contCompId,
			Long operateId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t ");
		sql.append("  FROM TRiContClaimItemLog t");
		sql.append(" WHERE t.contCompId = :contCompId ");
		sql.append("   AND t.operateId = :operateId ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("contCompId", contCompId);
		query.setParameter("operateId", operateId);

		@SuppressWarnings("unchecked")
		List<TRiContClaimItemLog> logList = (List<TRiContClaimItemLog>) query.getResultList();
		List<ContractClaimConditionItem> contractClaimConditionItemList = BeanUtils.convertList(logList,
				ContractClaimConditionItem.class);
		return contractClaimConditionItemList;
	}

	public BusinessConditionVO getBusinessConditionVOForLog(Long contCompId, Long operateId, String contractNature)
			throws Exception {
		BusinessConditionVO vo = new BusinessConditionVO();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * ");
		sql.append("  FROM T_RI_CONT_SHARE_LOG T1 ");
		sql.append("  LEFT JOIN T_RI_CONT_PREMIUM_LOG T2 ON T1.operate_id = T2.operate_id"
				+ " AND T2.CONT_COMP_ID = T1.CONT_COMP_ID ");
		sql.append("  LEFT JOIN t_Ri_Cont_Limit_Log T3 ON T1.operate_id = T3.operate_id"
				+ " AND T3.CONT_COMP_ID = T1.CONT_COMP_ID ");
		sql.append("  LEFT JOIN t_Ri_Cont_Deductions_Log T4 ON T1.operate_id = T4.operate_id"
				+ " AND T4.CONT_COMP_ID = T1.CONT_COMP_ID ");
		if (contractNature.equals(ContractCst.CONTRACT_NATURE_PROPORTIONAL)) {
			if (this.checkHasCommSliding(contCompId, operateId)) {
				sql.append("  LEFT JOIN t_ri_cont_deductions_comm_log T41 ON T1.operate_id = T41.operate_id"
						+ " AND T41.DEDUCTIONS_ID = T4.DEDUCTIONS_ID ");
			}
			if (this.checkHasPcSliding(contCompId, operateId)) {
				sql.append("  LEFT JOIN t_ri_cont_deductions_pc_log T42 ON T1.operate_id = T42.operate_id"
						+ " AND T42.DEDUCTIONS_ID = T4.DEDUCTIONS_ID ");
			}
		}
		sql.append("  LEFT JOIN t_Ri_Cont_Reserve_Rebate_Log T5 ON T1.operate_id = T5.operate_id"
				+ " AND T5.CONT_COMP_ID = T1.CONT_COMP_ID ");
		sql.append("  LEFT JOIN t_Ri_Cont_Loss_Log T6 ON T1.operate_id = T6.operate_id"
				+ " AND T6.CONT_COMP_ID = T1.CONT_COMP_ID ");
		sql.append("  LEFT JOIN t_Ri_Cont_Clauses_Log T7 ON T1.operate_id = T7.operate_id"
				+ " AND T7.CONT_COMP_ID = T1.CONT_COMP_ID ");
		sql.append("  LEFT JOIN t_Ri_Cont_Reinstate_Log T8 ON T1.operate_id = T8.operate_id"
				+ " AND T8.CONT_COMP_ID = T1.CONT_COMP_ID ");
		sql.append(" WHERE T1.cont_comp_id = ?");
		sql.append("   AND T1.operate_id = ?");
		sql.append(" ORDER BY T1.operate_id DESC");

		List<BusinessConditionVO> bcVOList = this.getJdbcTemplate().query(sql.toString(),
				new Object[] { contCompId, operateId },
				new BeanPropertyRowMapper<BusinessConditionVO>(BusinessConditionVO.class));
		if (CollectionUtils.isNotEmpty(bcVOList)) {
			vo = bcVOList.get(0);
		}
		vo.setCurrencyList(getCurrencyVOListForLog(contCompId, operateId));
		vo.setPremiumList(getPremiumItemVOListForLog(operateId, vo.getPremiumId()));
		vo.setLimitItemList(getLimitItemVOListForLog(operateId, vo.getLimitId()));
		vo.setLimitEventList(getLimitEventVOListForLog(operateId, vo.getLimitId()));
		vo.setDeductionsList(getDeductionsItemVOListForLog(vo.getDeductionsId(), operateId));
		// PC Sliding Detail's Attachment
		vo.getAttachTablePcList().addAll(getDeductionsAttachVOListForLog(vo.getPcSlidingId(), operateId));
		// Comm Sliding Detail's Attachment
		vo.getAttachTableCommList().addAll((getDeductionsAttachVOListForLog(vo.getCommSlidingDetailId(), operateId)));
		vo.setDeductionsCarriedList(getDeductionsCarriedVOListForLog(vo.getCommSlidingDetailId(), operateId));
		vo.setReinstateList(getReinstateItemVOListForLog(vo.getReinId(), operateId));
		return vo;
	}

	private boolean checkHasCommSliding(Long contCompId, Long operateId) throws Exception {
		String sql = "SELECT COUNT(1) FROM T_RI_CONT_DEDUCTIONS_LOG T1, T_RI_CONT_DEDUCTIONS_COMM_LOG T2 WHERE T1.OPERATE_ID = T2.OPERATE_ID"
				+ " AND T1.DEDUCTIONS_ID = T2.DEDUCTIONS_ID AND T1.CONT_COMP_ID = ? AND T1.OPERATE_ID = ? ";
		JdbcTemplate jdbc = SpringContextUtils.getJdbcTemplate();
		Object[] param = new Object[] { contCompId, operateId };
		return jdbc.queryForObject(sql, param, Integer.class) > 0;
	}

	private boolean checkHasPcSliding(Long contCompId, Long operateId) throws Exception {
		String sql = "SELECT COUNT(1) FROM T_RI_CONT_DEDUCTIONS_LOG T1, T_RI_CONT_DEDUCTIONS_PC_LOG T2 WHERE T1.OPERATE_ID = T2.OPERATE_ID"
				+ " AND T1.DEDUCTIONS_ID = T2.DEDUCTIONS_ID AND T1.CONT_COMP_ID = ? AND T1.OPERATE_ID = ? ";
		JdbcTemplate jdbc = SpringContextUtils.getJdbcTemplate();
		Object[] param = new Object[] { contCompId, operateId };
		return jdbc.queryForObject(sql, param, Integer.class) > 0;
	}

	private List<CurrencyVO> getCurrencyVOListForLog(Long contCompId, Long operateId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t ");
		sql.append("  FROM TRiContCurrencyLog t");
		sql.append(" WHERE t.operateId = :operateId ");
		sql.append("   AND t.contCompId = :contCompId ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("operateId", operateId);
		query.setParameter("contCompId", contCompId);

		@SuppressWarnings("unchecked")
		List<TRiContCurrencyLog> currencyLogList = (List<TRiContCurrencyLog>) query.getResultList();
		List<CurrencyVO> currencyVOList = BeanUtils.convertList(currencyLogList, CurrencyVO.class);

		return currencyVOList;
	}

	private List<PremiumItemVO> getPremiumItemVOListForLog(Long operateId, Long premiumId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t ");
		sql.append("  FROM TRiContPremiumItemLog t");
		sql.append(" WHERE t.operateId = :operateId ");
		sql.append("   AND t.premiumId = :premiumId ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("operateId", operateId);
		query.setParameter("premiumId", premiumId);

		@SuppressWarnings("unchecked")
		List<TRiContPremiumItemLog> premiumItemLogList = (List<TRiContPremiumItemLog>) query.getResultList();
		List<PremiumItemVO> premiumItemVOList = BeanUtils.convertList(premiumItemLogList, PremiumItemVO.class);
		return premiumItemVOList;
	}

	private List<LimitItemVO> getLimitItemVOListForLog(Long operateId, Long limitId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t ");
		sql.append("  FROM TRiContLimitItemLog t");
		sql.append(" WHERE t.operateId = :operateId ");
		sql.append("   AND t.limitId = :limitId ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("operateId", operateId);
		query.setParameter("limitId", limitId);

		@SuppressWarnings("unchecked")
		List<TRiContLimitItemLog> limitItemLogList = (List<TRiContLimitItemLog>) query.getResultList();
		List<LimitItemVO> limitItemVOList = BeanUtils.convertList(limitItemLogList, LimitItemVO.class);
		return limitItemVOList;
	}

	private List<LimitEventVO> getLimitEventVOListForLog(Long operateId, Long limitId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t ");
		sql.append("  FROM TRiContLimitEventLog t");
		sql.append(" WHERE t.operateId = :operateId ");
		sql.append("   AND t.limitId = :limitId ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("operateId", operateId);
		query.setParameter("limitId", limitId);

		@SuppressWarnings("unchecked")
		List<TRiContLimitEventLog> limitEventLogList = (List<TRiContLimitEventLog>) query.getResultList();
		List<LimitEventVO> limitEventVOList = BeanUtils.convertList(limitEventLogList, LimitEventVO.class);
		return limitEventVOList;
	}

	private List<DeductionsItemVO> getDeductionsItemVOListForLog(Long deductionsId, Long operateId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t ");
		sql.append("  FROM TRiContDeductionsItemLog t");
		sql.append(" WHERE t.operateId = :operateId ");
		sql.append("   AND t.deductionsId = :deductionsId ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("operateId", operateId);
		query.setParameter("deductionsId", deductionsId);

		@SuppressWarnings("unchecked")
		List<TRiContDeductionsItemLog> deductionsItemLogList = (List<TRiContDeductionsItemLog>) query.getResultList();
		List<DeductionsItemVO> deductionsItemVOList = BeanUtils.convertList(deductionsItemLogList,
				DeductionsItemVO.class);

		return deductionsItemVOList;
	}

	private List<DeductionsAttachVO> getDeductionsAttachVOListForLog(Long slidingId, Long operateId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t ");
		sql.append("  FROM TRiContDeductionsAttLog t");
		sql.append(" WHERE t.operateId = :operateId ");
		sql.append("   AND t.slidingId = :slidingId ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("operateId", operateId);
		query.setParameter("slidingId", slidingId);

		@SuppressWarnings("unchecked")
		List<TRiContDeductionsAttLog> deductionsAttachLogList = (List<TRiContDeductionsAttLog>) query.getResultList();
		List<DeductionsAttachVO> deductionsAttachVOList = BeanUtils.convertList(deductionsAttachLogList,
				DeductionsAttachVO.class);
		return deductionsAttachVOList;
	}

	private List<DeductionsCarriedVO> getDeductionsCarriedVOListForLog(Long slidingId, Long operateId)
			throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t ");
		sql.append("  FROM TRiContDeductionsCarrLog t");
		sql.append(" WHERE t.operateId = :operateId ");
		sql.append("   AND t.commSlidingDetailId = :commSlidingDetailId ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("operateId", operateId);
		query.setParameter("commSlidingDetailId", slidingId);

		@SuppressWarnings("unchecked")
		List<TRiContDeductionsCarrLog> deductionsCarriedLogList = (List<TRiContDeductionsCarrLog>) query
				.getResultList();
		List<DeductionsCarriedVO> deductionsCarriedVOList = BeanUtils.convertList(deductionsCarriedLogList,
				DeductionsCarriedVO.class);
		return deductionsCarriedVOList;
	}

	private List<ReinstateItemVO> getReinstateItemVOListForLog(Long reinId, Long operateId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t ");
		sql.append("  FROM TRiContReinstateItemLog t");
		sql.append(" WHERE t.operateId = :operateId ");
		sql.append("   AND t.reinId = :reinId ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("operateId", operateId);
		query.setParameter("reinId", reinId);

		@SuppressWarnings("unchecked")
		List<TRiContReinstateItemLog> reinstateItemLogList = (List<TRiContReinstateItemLog>) query.getResultList();
		List<ReinstateItemVO> reinstateItemVOList = BeanUtils.convertList(reinstateItemLogList, ReinstateItemVO.class);
		return reinstateItemVOList;
	}

	public TRiContractInfoLog loadContractInfoLog(Long contCompId, Long operateId) throws Exception {
		TRiContractInfoLog entityLog = null;
		final Query query = getEntityManager().createNamedQuery("TRiContractInfoLog.findByContCompIdAndOperateId",
				TRiContractInfoLog.class);
		query.setParameter("contCompId", contCompId);
		query.setParameter("operateId", operateId);
		try {
			Object obj = query.getSingleResult();
			if (null != obj)
				entityLog = (TRiContractInfoLog) obj;
		} catch (NoResultException e) {
			return null;
		}
		return entityLog;
	}
}
