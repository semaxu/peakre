package com.ebao.ap99.contract.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContractInfo;
import com.ebao.ap99.contract.model.ContractVO;
import com.ebao.ap99.parent.util.FieldsCollector;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContractInfoDao extends BaseDao<TRiContractInfo> {
	private static Logger logger = LoggerFactory.getLogger();
	@PersistenceContext
	private EntityManager em;

	@Override
	public Class<TRiContractInfo> getEntityClass() {
		return TRiContractInfo.class;
	}

	public TRiContractInfo save(TRiContractInfo infoVO) {
		if (infoVO.getContCompId() != null) {
			// TRiContractInfo existingOne = this.load(infoVO.getContCompId());
			// if (existingOne != null) {
			// infoVO.syncDataTo(existingOne, false);
			// return existingOne;
			// }
			TRiContractInfo existingOne = this.merge(infoVO);
			this.getEntityManager().flush();
			return existingOne;
		}
		this.getEntityManager().persist(infoVO);
		return infoVO;
	}

	/**
	 * @param contractCode
	 * @param uwYear
	 * @return
	 */
	public List<TRiContractInfo> getEntityByContractCodeUWYear(String contractCode, Long uwYear) {
		final Query query = getEntityManager().createNamedQuery("TRiContractInfo.findByContractCodeUWYear",
				TRiContractInfo.class);
		query.setParameter("contractCode", contractCode);
		query.setParameter("uwYear", uwYear);

		@SuppressWarnings("unchecked")
		List<TRiContractInfo> list = (List<TRiContractInfo>) query.getResultList();
		// if (CollectionUtils.isNotEmpty(list)) {
		// Object obj = query.getSingleResult();
		// if (null != obj)
		// vo = (TRiContractInfo) obj;
		// }
		return list;
	}

	/**
	 * @param contractCode
	 * @param uwYear
	 * @return
	 * @throws Exception
	 */
	public List<TRiContractInfo> getInForceEntityByCodeUWYear(String contractCode, Long uwYear) throws Exception {
		final Query query = getEntityManager().createNamedQuery("TRiContractInfo.findInforceByCodeUWYear",
				TRiContractInfo.class);
		query.setParameter("contractCode", contractCode);
		query.setParameter("uwYear", uwYear);

		@SuppressWarnings("unchecked")
		List<TRiContractInfo> list = (List<TRiContractInfo>) query.getResultList();
		return list;
	}

	/**
	 * @param contractCode
	 * @param uwYear
	 * @return
	 * @throws Exception
	 */
	public TRiContractInfo getInForceAndCancelledEntityByCodeUWYear(String contractCode, Long uwYear) throws Exception {
		TRiContractInfo vo = null;
		final Query query = getEntityManager().createNamedQuery("TRiContractInfo.findInforceAndCancelledByCodeUWYear",
				TRiContractInfo.class);
		query.setParameter("contractCode", contractCode);
		query.setParameter("uwYear", uwYear);

		@SuppressWarnings("unchecked")
		List<TRiContractInfo> list = (List<TRiContractInfo>) query.getResultList();
		if (CollectionUtils.isNotEmpty(list)) {
			Object obj = query.getSingleResult();
			if (null != obj)
				vo = (TRiContractInfo) obj;
		}
		return vo;
	}

	/**
	 * @param contractCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> getUWYearList(String contractCode) {
		final Query query = getEntityManager().createNamedQuery("TRiContractInfo.findUWYearList", Long.class);
		query.setParameter("contractCode", contractCode);
		List<Long> uwYearList = query.getResultList();
		return uwYearList;
	}

	/**
	 * @param contractCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TRiContractInfo> getContractInfoByContractCode(String contractCode) {
		final Query query = getEntityManager().createNamedQuery("TRiContractInfo.getContractInfoByContractCode",
				TRiContractInfo.class);
		query.setParameter("contractCode", contractCode);
		List<TRiContractInfo> infoList = query.getResultList();
		return infoList;
	}

	/**
	 * get linkList by cedentId
	 * 
	 * @param cedentId
	 * @return
	 */
	public List<String> getLinkedListByCedentId(String cedentId) {
		final Query query = getEntityManager().createNamedQuery("TRiContractInfo.getLinkedList", String.class);
		query.setParameter("cedent", cedentId);
		List<String> linkList = query.getResultList();
		return linkList;
	}

	/**
	 * get UWYearList by linkName
	 * 
	 * @param linkName
	 * @return
	 */
	public List<Long> getUWYearListByLinkName(String linkName, String cedent) {
		final Query query = getEntityManager().createNamedQuery("TRiContractInfo.getUWYearListByLinkName", Long.class);
		query.setParameter("linkName", linkName);
		query.setParameter("cedent", cedent);
		List<Long> linkList = query.getResultList();
		return linkList;
	}

	/**
	 * get contractId by
	 * 
	 * @param linkName
	 * @param uwYear
	 * @return
	 */
	public List<Long> getCompIdByLinkNameUWYear(String linkName, Long uwYear, String cedent) {
		final Query query = getEntityManager().createNamedQuery("TRiContractInfo.getCompIdByLinkNameUYCedent",
				Long.class);
		query.setParameter("linkName", linkName);
		query.setParameter("uwYear", uwYear);
		query.setParameter("cedent", cedent);
		List<Long> compIdList = query.getResultList();
		return compIdList;
	}

	public Long getContractCount(ContractVO condition) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String sql = "SELECT count(t) FROM TRiContractInfo t WHERE 1=1";
		Query query = this.getTRiContractInfoQuery(condition, sql);
		Long result = (Long) query.getSingleResult();
		return result;
	}

	/**
	 * search contract by condition and return contract list
	 * 
	 * @param condition
	 * @param start
	 * @param limit
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public List<TRiContractInfo> getCurrentPage(ContractVO condition, int pageIndex, int countPerPage)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		int begin = (pageIndex - 1) * countPerPage;
		String sql = "SELECT t FROM TRiContractInfo t WHERE 1=1";
		Query query = getTRiContractInfoQuery(condition, sql);

		query.setFirstResult(begin);
		query.setMaxResults(countPerPage);

		@SuppressWarnings("unchecked")
		List<TRiContractInfo> list = (List<TRiContractInfo>) query.getResultList();
		return list;
	}

	private Query getTRiContractInfoQuery(ContractVO condition, String sql)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		StringBuilder sqlBuf = new StringBuilder(sql);
		String contractCode = null;
		String contractName = null;
		Date reinsStarting = null;
		Date reinsEnding = null;
		if (StringUtils.isNotBlank(condition.getContractCode())) {
			contractCode = condition.getContractCode();
			condition.setContractCode(null);
		}
		if (StringUtils.isNotBlank(condition.getContractName())) {
			contractName = condition.getContractName();
			condition.setContractName(null);
		}
		if (condition.getReinsStarting() != null) {
			reinsStarting = condition.getReinsStarting();
			condition.setReinsStarting(null);
		}
		if (condition.getReinsEnding() != null) {
			reinsEnding = condition.getReinsEnding();
			condition.setReinsEnding(null);
		}
		FieldsCollector.concatSql(condition, TRiContractInfo.class, "t", sqlBuf);

		if (contractCode != null) {
			sqlBuf.append(" AND upper(t.contractCode) LIKE upper(:contractCode)");
		}
		if (contractName != null) {
			sqlBuf.append(" AND upper(t.contractName) LIKE upper(:contractName)");
		}
		if (reinsStarting != null) {
			sqlBuf.append(" AND to_char(t.reinsStarting, 'yyyy-mm-dd') >= to_char(:reinsStarting, 'yyyy-mm-dd')");
		}
		if (reinsEnding != null) {
			sqlBuf.append(" AND to_char(t.reinsEnding, 'yyyy-mm-dd') <= to_char(:reinsEnding, 'yyyy-mm-dd')");
		}
		if (condition.getCreatedOn() != null) {
			sqlBuf.append(" AND to_char(t.insertTime, 'yyyy-mm-dd') = to_char(:createdOn, 'yyyy-mm-dd')");
		}
		if (condition.getLastChangedOn() != null) {
			sqlBuf.append(" AND to_char(t.updateTime, 'yyyy-mm-dd') = to_char(:lastChangedOn, 'yyyy-mm-dd')");
		}

		if (null != condition.getSearchStatus() && condition.getSearchStatus().size() > 0) {
			sqlBuf.append(" AND t.latestStatus in (:searchStatus)");
		}

		sqlBuf.append(" ORDER BY t.contCompId DESC");
		Query query = em.createQuery(sqlBuf.toString());
		FieldsCollector.setParams(condition, TRiContractInfo.class, query);

		if (contractCode != null) {
			query.setParameter("contractCode", contractCode);
			condition.setContractCode(contractCode);
		}
		if (contractName != null) {
			query.setParameter("contractName", "%" + contractName + "%");
			condition.setContractName(contractName);
		}
		if (reinsStarting != null) {
			query.setParameter("reinsStarting", reinsStarting);
			condition.setReinsStarting(reinsStarting);
		}
		if (reinsEnding != null) {
			query.setParameter("reinsEnding", reinsEnding);
			condition.setReinsEnding(reinsEnding);
		}
		if (condition.getCreatedOn() != null) {
			query.setParameter("createdOn", condition.getCreatedOn());
		}
		if (condition.getLastChangedOn() != null) {
			query.setParameter("lastChangedOn", condition.getLastChangedOn());
		}

		if (null != condition.getSearchStatus() && condition.getSearchStatus().size() > 0) {
			query.setParameter("searchStatus", condition.getSearchStatus());
		}
		return query;
	}

	/**
	 * Get renewal status by renewalId
	 * 
	 * @param renewalId
	 * @return
	 */
	public boolean getRenewalStatusByRenewalId(Long renewalId) {
		boolean renewalStatus = false;
		TRiContractInfo vo = new TRiContractInfo();
		final Query query = getEntityManager().createNamedQuery("TRiContractInfo.findByRenewalId",
				TRiContractInfo.class);
		query.setParameter("renewalId", renewalId);
		try {
			Object obj = query.getSingleResult();
			if (null != obj) {
				renewalStatus = true;
			}
		} catch (Exception e) {
			return false;
		}
		return renewalStatus;
	}

	/**
	 * @param contractCode
	 * @param uwYear
	 * @return
	 * @throws Exception
	 */
	public List<TRiContractInfo> getRetroEntityByCodeUWYear(String contractCode, Long uwYear) throws Exception {
		final Query query = getEntityManager().createNamedQuery("TRiContractInfo.findRetroByCodeUWYear",
				TRiContractInfo.class);
		query.setParameter("contractCode", contractCode);
		query.setParameter("uwYear", uwYear);

		@SuppressWarnings("unchecked")
		List<TRiContractInfo> list = (List<TRiContractInfo>) query.getResultList();
		return list;
	}

	/**
	 * 
	 * @param contractCode
	 * @param uwYear
	 * @param reinsStarting
	 * @return
	 * @throws Exception
	 */
	public TRiContractInfo getValidEntity(String contractCode, Long uwYear, Date reinsStarting) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t ");
		sql.append("  FROM TRiContractInfo t");
		sql.append(" WHERE t.contractCode = :contractCode ");
		sql.append("   AND t.uwYear = :uwYear ");
		if (null != reinsStarting) {
			sql.append("   AND TO_CHAR(t.reinsStarting,'YYYYMMDD') = TO_CHAR(:reinsStarting,'YYYYMMDD') ");
		}
		Query query = em.createQuery(sql.toString());
		query.setParameter("contractCode", contractCode);
		query.setParameter("uwYear", uwYear);
		if (null != reinsStarting) {
			query.setParameter("reinsStarting", reinsStarting);
		}

		TRiContractInfo entity = null;
		try {
			Object obj = query.getSingleResult();
			if (null != obj)
				entity = (TRiContractInfo) obj;
		} catch (NoResultException e) {
			logger.debug("There no TRiContractInfo entity found for query, the contractCode=" + contractCode
					+ ", uwYear=" + uwYear + ", reinsStarting=" + reinsStarting);
			return null;
		}
		return entity;
	}
}
