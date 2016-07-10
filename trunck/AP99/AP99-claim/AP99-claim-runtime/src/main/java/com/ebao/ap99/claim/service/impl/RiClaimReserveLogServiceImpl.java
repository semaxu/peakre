/**
 * 
 */
package com.ebao.ap99.claim.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.convertor.ReserveLogConverter;
import com.ebao.ap99.claim.dao.RiClaimReserveLogDao;
import com.ebao.ap99.claim.entity.TRiClaimReserve;
import com.ebao.ap99.claim.entity.TRiClaimReserveLog;
import com.ebao.ap99.claim.model.ClaimInfo;
import com.ebao.ap99.claim.model.ReserveHistoryInfo;
import com.ebao.ap99.claim.model.ReserveHistoryQuery;
import com.ebao.ap99.claim.service.RiClaimReserveLogService;

/**
 * @author yujie.zhang
 *
 */
public class RiClaimReserveLogServiceImpl implements RiClaimReserveLogService {
	
	@Autowired
	private RiClaimReserveLogDao reserveLogDao;
	@Autowired
	public ReserveLogConverter reserveLogConverter;
	
	/* (non-Javadoc)
	 * @see com.ebao.ap99.claim.service.RiClaimReserveLogService#creatReserveLog(com.ebao.ap99.claim.entity.TRiClaimReserveLog)
	 */
	@Override
	public void createReserveLog(TRiClaimReserveLog reserveLog) {
		reserveLogDao.insert(reserveLog);

	}

	/* (non-Javadoc)
	 * @see com.ebao.ap99.claim.service.RiClaimReserveLogService#update(com.ebao.ap99.claim.entity.TRiClaimReserveLog)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public TRiClaimReserveLog update(TRiClaimReserveLog reserveLog) {
		return reserveLogDao.merge(reserveLog);
	}

	/* (non-Javadoc)
	 * @see com.ebao.ap99.claim.service.RiClaimReserveLogService#getClaimReserveLog(long)
	 */
	@Override
	public TRiClaimReserveLog getClaimReserveLog(long reserveLogId) {
		return reserveLogDao.load(reserveLogId);
	}

	/* (non-Javadoc)
	 * @see com.ebao.ap99.claim.service.RiClaimReserveLogService#delete(com.ebao.ap99.claim.entity.TRiClaimReserveLog)
	 */
	@Override
	public void delete(TRiClaimReserveLog reserveLog) {
		reserveLogDao.delete(reserveLog);

	}

	/* (non-Javadoc)
	 * @see com.ebao.ap99.claim.service.RiClaimReserveLogService#getAll()
	 */
	@Override
	public List<TRiClaimReserveLog> getAll() {
		return reserveLogDao.loadAll();
	}

	@Override
	public void submitReserveLog(TRiClaimReserve reserve, ClaimInfo claimInfo) {
		TRiClaimReserveLog reserveLog = new TRiClaimReserveLog();
		//TRiClaimReserve reserve = new TRiClaimReserve();

		reserveLog = reserveLogConverter.reserveToLog(reserve);
		if(reserve.getBusinessDirection().equals(ClaimConstant.CLAIM_BUSINESS_DIRECTION__FINANCIAL)){
			reserveLog.setRemark(claimInfo.getReserveUpdateRemark());
		}else if(reserve.getBusinessDirection().equals(ClaimConstant.CLAIM_BUSINESS_DIRECTION__RETROCESSION)){
			reserveLog.setRemark(claimInfo.getReserveUpdateRemarkRetro());

		}
		reserveLog.setTRiClaimReserve(reserve);
		reserveLogDao.insert(reserveLog);

	}

	@Override
	public List<ReserveHistoryInfo> getReserveHistoryList(ReserveHistoryQuery reserveHistoryQuery) {
		return reserveLogDao.getReserveHistory(reserveHistoryQuery);
	}

}
