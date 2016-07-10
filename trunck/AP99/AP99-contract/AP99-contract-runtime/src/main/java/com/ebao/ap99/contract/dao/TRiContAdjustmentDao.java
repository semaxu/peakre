/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.contract.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.contract.entity.TRiContAdjustment;
import com.ebao.ap99.contract.entity.TRiContAdjustment;
import com.ebao.ap99.contract.entity.TRiContAdjustmentItem;
import com.ebao.ap99.contract.entity.TRiContAdjustmentItemLog;
import com.ebao.ap99.contract.entity.TRiContAdjustmentLog;
import com.ebao.ap99.parent.BeanUtils;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author weiping.wang
 *
 */
public class TRiContAdjustmentDao extends BaseDao<TRiContAdjustment> {
	private static Logger logger = LoggerFactory.getLogger();

	@Autowired
	private TRiContAdjustmentLogDao adjustmentLogDao;

	@Autowired
	private TRiContAdjustmentItemLogDao adjustmentItemLogDao;

	@Override
	public Class<TRiContAdjustment> getEntityClass() {
		return TRiContAdjustment.class;
	}

	public TRiContAdjustment loadByContCompId(Long contCompId) {
		final Query query = getEntityManager().createNamedQuery("TRiContAdjustment.loadByContCompId",
				TRiContAdjustment.class);
		query.setParameter("contCompId", contCompId);
		TRiContAdjustment adjustmentVO = new TRiContAdjustment();
		try {
			Object obj = query.getSingleResult();
			if (null != obj)
				adjustmentVO = (TRiContAdjustment) obj;
		} catch (NoResultException e) {
			logger.debug("There no TRiContAdjustment entity found for query, the contCompId=" + contCompId);
			return null;
		}
		return adjustmentVO;
	}

	public TRiContAdjustment save(TRiContAdjustment infoVO) {
		if (infoVO.getAdjustmentId() != null) {
			TRiContAdjustment existingOne = this.merge(infoVO);
			this.getEntityManager().flush();
			// TRiContAdjustment existingOne = this.load(infoVO.getLimitId());
			// infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}

	/**
	 * record the supiAdjust info
	 * 
	 * @param entity
	 * @param operateId
	 * @throws Exception
	 */
	public void saveLogForSupiAdjustmentInfo(TRiContAdjustment entity, Long operateId,
			List<TRiContAdjustmentItemLog> needSaveHisList, List<TRiContAdjustmentItem> notBackupList)
					throws Exception {
		TRiContAdjustmentLog logEntity = new TRiContAdjustmentLog();
		BeanUtils.copyProperties(entity, logEntity);
		logEntity.clearPrimaryKeyCascade();
		logEntity.setOperateId(operateId);
		adjustmentLogDao.insert(logEntity);
		List<TRiContAdjustmentItemLog> itemLogList = new ArrayList<TRiContAdjustmentItemLog>();
		List<TRiContAdjustmentItem> itemEntityList = new ArrayList<TRiContAdjustmentItem>();
		if (CollectionUtils.isNotEmpty(notBackupList)) {
			for (TRiContAdjustmentItem item : entity.getTRiContAdjustmentItems()) {
				boolean needBackup = true;
				for (TRiContAdjustmentItem notBackupItem : notBackupList) {
					if (item.getContCompId().equals(notBackupItem.getContCompId())) {
						needBackup = false;
					}
				}
				if (needBackup) {
					itemEntityList.add(item);
				}
			}
			itemLogList = BeanUtils.convertList(itemEntityList, TRiContAdjustmentItemLog.class);
		} else {
			itemLogList = BeanUtils.convertList(entity.getTRiContAdjustmentItems(), TRiContAdjustmentItemLog.class);
		}
		if (CollectionUtils.isNotEmpty(needSaveHisList)) {
			itemLogList.addAll(needSaveHisList);
		}
		for (TRiContAdjustmentItemLog itemLogEntity : itemLogList) {
			if (null == itemLogEntity.getAdjustmentId()) {
				itemLogEntity.setAdjustmentId(entity.getAdjustmentId());
			}
			itemLogEntity.setOperateId(operateId);
			itemLogEntity.clearPrimaryKeyCascade();
			adjustmentItemLogDao.insert(itemLogEntity);
		}
	}
}
