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

import com.ebao.ap99.contract.entity.TRiContractEndo;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * Date: Feb 3, 2016 4:21:24 PM
 * 
 * @author Weiping.Wang
 *
 */
public class TRiContractEndoDao extends BaseDao<TRiContractEndo> {
	@Override
	public Class<TRiContractEndo> getEntityClass() {
		return TRiContractEndo.class;
	}

	public List<TRiContractEndo> getEndorsementList(Long contId) {
		final Query query = getEntityManager().createNamedQuery("TRiContractEndo.findByContCompId",
				TRiContractEndo.class);
		query.setParameter("contCompId", contId);
		@SuppressWarnings("unchecked")
		List<TRiContractEndo> list = query.getResultList();
		return list;
	}

	public TRiContractEndo getEndorsement(Long endoId) {
		final Query query = getEntityManager().createNamedQuery("TRiContractEndo.findByEndoId", TRiContractEndo.class);
		query.setParameter("endoId", endoId);
		TRiContractEndo entity = (TRiContractEndo) query.getSingleResult();
		return entity;
	}

	public TRiContractEndo save(TRiContractEndo infoVO) {
		if (infoVO.getEndoId() != null) {
			TRiContractEndo existingOne = this.load(infoVO.getEndoId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}
}
