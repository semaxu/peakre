/**
 * 
 */
package com.ebao.ap99.claim.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.ebao.ap99.claim.model.ClaimQuery;

/**
 * @author yujie.zhang
 *
 */
public class ClaimQueryDao {

	@PersistenceContext
	private EntityManager em;

	public Long countForList(ClaimQuery claimQuery) {
		StringBuffer sqlBuf = new StringBuffer("SELECT count(t) FROM TRiClaimInfo t WHERE 1=1");

		if (StringUtils.isNotBlank(claimQuery.getClaimNo())) {
			sqlBuf.append(" and t.claimNo=:claimNo");
		}
		if (StringUtils.isNotBlank(claimQuery.getEventId())) {
			sqlBuf.append(" and t.eventCode=:eventCode");
		}
		if (claimQuery.getClaimBranch() != null) {
			sqlBuf.append(" and t.claimBranch=:claimBranch");
		}
		if (claimQuery.getContractID() != null) {
			sqlBuf.append(
					" and t.claimNo in ( select claimNo from TRiClaimSectionInfo where contractCode=:contractCode) ");
		}
		if (claimQuery.getCedantName() != null) {
			sqlBuf.append(" and t.claimNo in ( select claimNo from TRiClaimSectionInfo where cedentName=:cedentName) ");
		}

		if (claimQuery.getCedantCountry() != null) {
			sqlBuf.append(
					" and t.claimNo in ( select claimNo from TRiClaimSectionInfo where cedentCountry=:cedentCountry) ");
		}
		// if (claimQuery.getCedantReference() != null) {
		// sqlBuf.append(" and t.claimNo in ( select claimNo from
		// TRiClaimSectionInfo where uwYear=:uwYear) ");
		// }
		// if (claimQuery.getBrokerReference() != null) {
		// sqlBuf.append(" and t.claimNo in ( select claimNo from
		// TRiClaimSectionInfo where uwYear=:uwYear) ");
		// }
		// Date of Loss from
		// Date of Loss to

		if (claimQuery.getCauseOfLoss() != null) {
			sqlBuf.append(" and t.causeOfLoss=:causeOfLoss");
		}

		if (claimQuery.getStatus() != null) {
			sqlBuf.append(" and t.status=:status");
		}
		if (claimQuery.getTaskOwner() != null) {
			sqlBuf.append(" and t.taskOwner=:taskOwner");
		}
//		if (claimQuery.getLossDes() != null) {
//			sqlBuf.append(" and t.lossDescription=:lossDescription");
//		}

		Query query = em.createQuery(sqlBuf.toString());

		if (StringUtils.isNotBlank(claimQuery.getClaimNo())) {
			query.setParameter("claimNo", claimQuery.getClaimNo());
		}

		if (StringUtils.isNotBlank(claimQuery.getEventId())) {
			query.setParameter("eventCode", claimQuery.getEventId());
		}
		if (claimQuery.getClaimBranch() != null) {
			query.setParameter("claimBranch", claimQuery.getClaimBranch());
		}
		if (claimQuery.getContractID() != null) {
			query.setParameter("contractCode", claimQuery.getContractID());
		}
		if (claimQuery.getUnderwritingYear() != null) {
			query.setParameter("uwYear", claimQuery.getUnderwritingYear());
		}

		if (claimQuery.getCedantName() != null) {
			query.setParameter("cedentName", claimQuery.getCedantName());
		}

		if (claimQuery.getCedantCountry() != null) {
			query.setParameter("cedentCountry", claimQuery.getCedantCountry());
		}

		// if (claimQuery.getCedantReference() != null) {
		// query.setParameter("cedentReference",
		// claimQuery.getCedantReference());
		// }
		// if (claimQuery.getBrokerReference() != null) {
		// query.setParameter("brokerReference",
		// claimQuery.getBrokerReference());
		// }

		// Date of Loss from
		// Date of Loss to

		if (claimQuery.getCauseOfLoss() != null) {
			query.setParameter("causeOfLoss", claimQuery.getCauseOfLoss());
		}

		if (claimQuery.getStatus() != null) {
			query.setParameter("status", claimQuery.getStatus());
		}

		if (claimQuery.getTaskOwner() != null) {
			query.setParameter("taskOwner", claimQuery.getTaskOwner());
		}
//		if (claimQuery.getLossDes() != null) {
//			query.setParameter("lossDescription", claimQuery.getLossDes());
//		}
		Long result = (Long) query.getSingleResult();
		return result;
	}

	public List<ClaimQuery> findPageList(ClaimQuery claimQuery, int start, int limit) {
		StringBuffer sqlBuf = new StringBuffer("SELECT count(t) FROM TRiClaimInfo t WHERE 1=1");

		if (StringUtils.isNotBlank(claimQuery.getClaimNo())) {
			sqlBuf.append(" and t.claimNo=:claimNo");
		}
		if (StringUtils.isNotBlank(claimQuery.getEventId())) {
			sqlBuf.append(" and t.eventCode=:eventCode");
		}
		if (claimQuery.getClaimBranch() != null) {
			sqlBuf.append(" and t.claimBranch=:claimBranch");
		}
		if (claimQuery.getContractID() != null) {
			sqlBuf.append(
					" and t.claimNo in ( select claimNo from TRiClaimSectionInfo where contractCode=:contractCode) ");
		}
		if (claimQuery.getCedantName() != null) {
			sqlBuf.append(" and t.claimNo in ( select claimNo from TRiClaimSectionInfo where cedentName=:cedentName) ");
		}

		if (claimQuery.getCedantCountry() != null) {
			sqlBuf.append(
					" and t.claimNo in ( select claimNo from TRiClaimSectionInfo where cedentCountry=:cedentCountry) ");
		}
		// if (claimQuery.getCedantReference() != null) {
		// sqlBuf.append(" and t.claimNo in ( select claimNo from
		// TRiClaimSectionInfo where uwYear=:uwYear) ");
		// }
		// if (claimQuery.getBrokerReference() != null) {
		// sqlBuf.append(" and t.claimNo in ( select claimNo from
		// TRiClaimSectionInfo where uwYear=:uwYear) ");
		// }
		// Date of Loss from
		// Date of Loss to

		if (claimQuery.getCauseOfLoss() != null) {
			sqlBuf.append(" and t.causeOfLoss=:causeOfLoss");
		}

		if (claimQuery.getStatus() != null) {
			sqlBuf.append(" and t.status=:status");
		}
		if (claimQuery.getTaskOwner() != null) {
			sqlBuf.append(" and t.taskOwner=:taskOwner");
		}
//		if (claimQuery.getLossDes() != null) {
//			sqlBuf.append(" and t.lossDescription=:lossDescription");
//		}

		Query query = em.createQuery(sqlBuf.toString());

		if (StringUtils.isNotBlank(claimQuery.getClaimNo())) {
			query.setParameter("claimNo", claimQuery.getClaimNo());
		}

		if (StringUtils.isNotBlank(claimQuery.getEventId())) {
			query.setParameter("eventCode", claimQuery.getEventId());
		}
		if (claimQuery.getClaimBranch() != null) {
			query.setParameter("claimBranch", claimQuery.getClaimBranch());
		}
		if (claimQuery.getContractID() != null) {
			query.setParameter("contractCode", claimQuery.getContractID());
		}
		if (claimQuery.getUnderwritingYear() != null) {
			query.setParameter("uwYear", claimQuery.getUnderwritingYear());
		}

		if (claimQuery.getCedantName() != null) {
			query.setParameter("cedentName", claimQuery.getCedantName());
		}

		if (claimQuery.getCedantCountry() != null) {
			query.setParameter("cedentCountry", claimQuery.getCedantCountry());
		}

		// if (claimQuery.getCedantReference() != null) {
		// query.setParameter("cedentReference",
		// claimQuery.getCedantReference());
		// }
		// if (claimQuery.getBrokerReference() != null) {
		// query.setParameter("brokerReference",
		// claimQuery.getBrokerReference());
		// }

		// Date of Loss from
		// Date of Loss to

		if (claimQuery.getCauseOfLoss() != null) {
			query.setParameter("causeOfLoss", claimQuery.getCauseOfLoss());
		}

		if (claimQuery.getStatus() != null) {
			query.setParameter("status", claimQuery.getStatus());
		}

		if (claimQuery.getTaskOwner() != null) {
			query.setParameter("taskOwner", claimQuery.getTaskOwner());
		}
//		if (claimQuery.getLossDes() != null) {
//			query.setParameter("lossDescription", claimQuery.getLossDes());
//		}

		query.setFirstResult(start);
		query.setMaxResults(limit);

		@SuppressWarnings("unchecked")
		List<ClaimQuery> list = (List<ClaimQuery>) query.getResultList();
		return list;
	}
}
