/**
 * 
 */
package com.ebao.ap99.claim.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.dao.RiClaimInfoDao;
import com.ebao.ap99.claim.dao.RiClaimReserveDao;
import com.ebao.ap99.claim.dao.RiClaimSectionDao;
import com.ebao.ap99.claim.model.ClaimModelForGeneral;
import com.ebao.ap99.claim.model.Settlement;
import com.ebao.ap99.claim.service.ClaimService;
import com.ebao.ap99.claim.service.RiClaimSettlementItemService;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author yujie.zhang
 *
 */
public class ClaimServiceImpl implements ClaimService {
	private static Logger logger = LoggerFactory.getLogger();
	@Autowired
	private RiClaimInfoDao claimInfoDao;
	@Autowired
	private RiClaimSectionDao sectionInfoDao;
	@Autowired
	public RiClaimSettlementItemService settlementItemService;
	
	@Autowired
	public RiClaimReserveDao claimReserveDao ;
	@Override
	public List<ClaimModelForGeneral> getClaimListByContractId(Long contractId) {
		logger.info("getClaimListByContractId===contractId=="+contractId);
		return claimInfoDao.getClaimModel(contractId);
	}
	@Override
	public void updatePayment(Settlement settlement) {
		// TODO Auto-generated method stub
		//List<SettlementItem> settlementItem = settlement.getSettlementItem();
		settlementItemService.updatePayment(settlement);
		
	}
	@Override
	public void updatePaymentBySettlementId(Settlement settlement) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean ifHasClaim(Long sectionId) {
		
		return claimReserveDao.getIfHasClaimBySectionId(sectionId);
	}
	@Override
	public List<String> ifExistSection(Long sectionId) {
		List<Long> claimIdList = claimReserveDao.getrefIdbySectionId(sectionId);
		if( null != claimIdList && claimIdList.size()>0){
			List<String> claimNoList = new ArrayList<String>();
			for(Long claimId : claimIdList){
				claimNoList.add(claimInfoDao.getClaimNo(claimId));
			}
			return claimNoList;
		}else{
			return null;
		}
	}

}
