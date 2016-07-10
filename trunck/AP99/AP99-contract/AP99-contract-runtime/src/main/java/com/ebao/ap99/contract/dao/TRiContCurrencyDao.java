package com.ebao.ap99.contract.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.ebao.ap99.contract.entity.TRiContCurrency;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContCurrencyDao extends BaseDao<TRiContCurrency> {

	private static Logger logger = LoggerFactory.getLogger();

	@Override
	public Class<TRiContCurrency> getEntityClass() {
		return TRiContCurrency.class;
	}

	/**
	 * 
	 * @param contCompId
	 * @return
	 */
	public List<TRiContCurrency> loadByContCompId(Long contCompId) {
		final Query query = getEntityManager().createNamedQuery("TRiContCurrency.loadByContCompId",
				TRiContCurrency.class);
		query.setParameter("contCompId", contCompId);
		List<TRiContCurrency> currencyList = new ArrayList<TRiContCurrency>();
		try {
			currencyList = (List<TRiContCurrency>) query.getResultList();
		} catch (NoResultException e) {
			logger.debug("There no TRiContCurrency entity found for query, the contCompId=" + contCompId);
		}
		return currencyList;
	}

	public TRiContCurrency save(TRiContCurrency infoVO) {
		if (infoVO.getCurrencyId() != null) {
			// TRiContCurrency existingOne = this.load(infoVO.getCurrencyId());
			// infoVO.syncDataTo(existingOne, false);
			TRiContCurrency existingOne = this.merge(infoVO);
			this.getEntityManager().flush();
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}

	public List<String> loadMainCurrencyList(Long contCompId) {
		List<String> currencyList = new ArrayList<String>();
		final Query query = getEntityManager().createNamedQuery("TRiContCurrency.loadMainCurrencyList", String.class);
		query.setParameter("contCompId", contCompId);
		try {
			currencyList = (List<String>) query.getResultList();
		} catch (NoResultException e) {
			logger.debug("There no TRiContCurrency entity found for query, the contCompId=" + contCompId);
		}
		return currencyList;
	}
}