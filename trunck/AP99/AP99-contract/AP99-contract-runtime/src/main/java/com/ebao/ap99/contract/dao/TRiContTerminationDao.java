/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.ebao.ap99.contract.entity.TRiContTermination;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * Date: Mar 16, 2016 5:28:37 PM
 * 
 * @author Weiping.Wang
 *
 */
public class TRiContTerminationDao extends BaseDao<TRiContTermination> {
	@Override
	public Class<TRiContTermination> getEntityClass() {
		return TRiContTermination.class;
	}

	public TRiContTermination getTermination(Long contractId) {
		TRiContTermination entity = new TRiContTermination();
		final Query query = getEntityManager().createNamedQuery("TRiContTermination.findByContCompId",
				TRiContTermination.class);
		query.setParameter("contCompId", contractId);

		@SuppressWarnings("unchecked")
		List<TRiContTermination> terminationList = (List<TRiContTermination>) query.getResultList();
		if (CollectionUtils.isNotEmpty(terminationList)) {
			entity = terminationList.get(0);
		}
		return entity;
	}

	public TRiContTermination save(TRiContTermination infoVO) {
		if (infoVO.getTerminationId() != null) {
			TRiContTermination existingOne = this.load(infoVO.getTerminationId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}

}
