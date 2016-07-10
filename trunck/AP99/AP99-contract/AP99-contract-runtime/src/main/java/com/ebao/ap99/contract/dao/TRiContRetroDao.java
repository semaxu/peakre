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

import com.ebao.ap99.contract.entity.TRiContRetro;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

/**
 * Date: Jan 15, 2016 4:11:39 PM
 * 
 * @author weiping.wang
 *
 */
public class TRiContRetroDao extends BaseDao<TRiContRetro> {
	@Override
	public Class<TRiContRetro> getEntityClass() {
		return TRiContRetro.class;
	}

	/**
	 * 
	 * @param compId
	 * @return
	 */
	public List<TRiContRetro> getRetrocessionList(Long compId) {
		final Query query = getEntityManager().createNamedQuery("TRiContRetro.findByCompId", TRiContRetro.class);
		query.setParameter("compId", compId);
		List<TRiContRetro> list = query.getResultList();
		return list;
	}

	/**
	 * 
	 * @param retroCompId
	 * @return
	 */
	public List<TRiContRetro> getCoveredSectionList(Long retroCompId) {
		final Query query = getEntityManager().createNamedQuery("TRiContRetro.findByRetroCompId", TRiContRetro.class);
		query.setParameter("retroCompId", retroCompId);
		List<TRiContRetro> list = query.getResultList();
		return list;
	}

	public TRiContRetro save(TRiContRetro infoVO) {
		if (infoVO.getRetroId() != null) {
			TRiContRetro existingOne = this.load(infoVO.getRetroId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}

	/**
	 * get retro sectionid by assume section id
	 * 
	 * @param compId
	 * @return
	 */
	public List<Long> getRetroSectionId(Long compId) {
		final Query query = getEntityManager().createNamedQuery("TRiContRetro.getRetroSectionIds", Long.class);
		query.setParameter("compId", compId);
		List<Long> list = query.getResultList();
		return list;
	}

	/**
	 * get assume sectionid by retro sectionId
	 * 
	 * @param compId
	 * @return
	 */
	public List<Long> getAssumeSectionId(Long compId) {
		final Query query = getEntityManager().createNamedQuery("TRiContRetro.getAssumeSectionIds", Long.class);
		query.setParameter("compId", compId);
		List<Long> list = query.getResultList();
		return list;
	}

}
