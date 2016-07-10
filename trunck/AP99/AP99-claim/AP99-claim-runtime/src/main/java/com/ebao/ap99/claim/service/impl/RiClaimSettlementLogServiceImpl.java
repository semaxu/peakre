/**
 * 
 */
package com.ebao.ap99.claim.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.convertor.SettlementLogConvertor;
import com.ebao.ap99.claim.dao.RiClaimSettlementLogDao;
import com.ebao.ap99.claim.entity.TRiClaimSettlementLog;
import com.ebao.ap99.claim.model.SettlementLogInfo;
import com.ebao.ap99.claim.service.RiClaimSettlementLogService;
import com.ebao.ap99.parent.BeanUtils;

/**
 * @author yujie.zhang
 *
 */
public class RiClaimSettlementLogServiceImpl implements RiClaimSettlementLogService {

	@Autowired
	private RiClaimSettlementLogDao settlementLogDao;
	@Autowired
	public SettlementLogConvertor settlementLogConvertor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebao.ap99.claim.service.RiClaimSettlementLogService#create(com.ebao.
	 * ap99.claim.entity.TRiClaimSettlementLog)
	 */
	@Override
	public void create(TRiClaimSettlementLog settlementLog) {
		settlementLogDao.insert(settlementLog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebao.ap99.claim.service.RiClaimSettlementLogService#update(com.ebao.
	 * ap99.claim.entity.TRiClaimSettlementLog)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public TRiClaimSettlementLog update(TRiClaimSettlementLog settlementLog) {
		return settlementLogDao.merge(settlementLog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebao.ap99.claim.service.RiClaimSettlementLogService#get(long)
	 */
	@Override
	public TRiClaimSettlementLog get(long settlementLogId) {
		return settlementLogDao.load(settlementLogId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebao.ap99.claim.service.RiClaimSettlementLogService#delete(com.ebao.
	 * ap99.claim.entity.TRiClaimSettlementLog)
	 */
	@Override
	public void delete(TRiClaimSettlementLog settlementLog) {
		settlementLogDao.delete(settlementLog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebao.ap99.claim.service.RiClaimSettlementLogService#getAll(com.ebao.
	 * ap99.claim.entity.TRiClaimSettlementLog)
	 */
	@Override
	public List<TRiClaimSettlementLog> getAll(TRiClaimSettlementLog settlementLog) {
		return settlementLogDao.loadAll();
	}

	@Override
	public List<SettlementLogInfo> getBySettlementId(long settlementId) throws Exception {

		List<TRiClaimSettlementLog> settlementLogList = settlementLogDao.getSettlementLogList(settlementId);
		List<SettlementLogInfo> settlementLogInfo = BeanUtils.convertList(settlementLogList,
				SettlementLogInfo.class);

		//List<SettlementLogInfo> settlementLogInfo = settlementLogConvertor.entityToModelList(settlementLogList);

		return settlementLogInfo;
	}

}
