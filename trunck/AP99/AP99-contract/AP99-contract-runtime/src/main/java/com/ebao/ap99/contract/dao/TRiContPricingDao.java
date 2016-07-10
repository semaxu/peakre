package com.ebao.ap99.contract.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.contract.entity.TRiContPricing;
import com.ebao.ap99.contract.entity.TRiContPricingItemLog;
import com.ebao.ap99.contract.entity.TRiContPricingLog;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class TRiContPricingDao extends BaseDao<TRiContPricing> {
	private static Logger logger = LoggerFactory.getLogger();
	@Autowired
	private TRiContPricingLogDao pricingLogDao;
	
	@Autowired
	private TRiContPricingItemLogDao pricingItemLogDao;
	@Override
	public Class<TRiContPricing> getEntityClass() {
		return TRiContPricing.class;
	}
	/**
	 * 
	 * @param infoVO
	 * @return
	 */
	public TRiContPricing save(TRiContPricing infoVO) {
		if (infoVO.getPricingId() != null) {
			this.getEntityManager().merge(infoVO);
		} else {
			this.getEntityManager().persist(infoVO);
		}
		
		
		this.getEntityManager().flush();
		return infoVO;
	}
	/**
	 * 
	 * @param contCompId
	 * @return
	 */
	public TRiContPricing loadByContCompId(Long contCompId){
		final Query query = getEntityManager().createNamedQuery("TRiContPricing.loadByContCompId", TRiContPricing.class);
		query.setParameter("contCompId", contCompId);
		TRiContPricing pricingVO = new TRiContPricing();
		try {
			Object obj = query.getSingleResult();
			if (null != obj)
				pricingVO = (TRiContPricing) obj;
		} catch (NoResultException e) {
			logger.debug("There no TRiContLimit entity found for query, the contCompId=" + contCompId);
		}
		return pricingVO;
	}
	/**
	 * if the pricing info has been effective, the need record the log
	 * @param entity
	 * @param operateId
	 * @throws Exception
	 */
	public void saveLogForPricingInfo(TRiContPricing entity, Long operateId) throws Exception{
		TRiContPricingLog logEntity = new TRiContPricingLog();
		BeanUtils.copyProperties(entity, logEntity);
		logEntity.clearPrimaryKeyCascade();
		logEntity.setOperateId(operateId);
		pricingLogDao.insert(logEntity);
		List<TRiContPricingItemLog> itemLogList = BeanUtils.convertList(entity.getTRiContPricingItems(), TRiContPricingItemLog.class);
		for(TRiContPricingItemLog itemLogEntity : itemLogList){
			itemLogEntity.setOperateId(operateId);
			itemLogEntity.setPricingId(entity.getPricingId());
			itemLogEntity.clearPrimaryKeyCascade();
			pricingItemLogDao.insert(itemLogEntity);
		}
	}
}