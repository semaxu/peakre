/**
 * 
 */
package com.ebao.ap99.claim.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.constant.ClaimConstant;
import com.ebao.ap99.claim.dao.RiClaimMessageDao;
import com.ebao.ap99.claim.entity.TRiClaimMessage;
import com.ebao.ap99.claim.service.RiClaimMessageService;

/**
 * @author yujie.zhang
 *
 */
public class RiClaimMessageServiceImpl implements RiClaimMessageService {

	@Autowired
	private RiClaimMessageDao claimMessageDao;
	
	@Override
	public void createClaimMessage(TRiClaimMessage tRiClaimMessage) {
		claimMessageDao.insert(tRiClaimMessage);
	}

	@SuppressWarnings("deprecation")
	@Override
	public TRiClaimMessage updateClaimMessage(TRiClaimMessage tRiClaimMessage) {
		return claimMessageDao.merge(tRiClaimMessage);
	}

	@Override
	public void deleteClaimMessage(TRiClaimMessage tRiClaimMessage) {
		// TODO Auto-generated method stub
		claimMessageDao.delete(tRiClaimMessage);
		//TRiClaimMessage claimMessageResturn = claimMessageDao.l
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateUncheckMessage(List<Long> messageIdList) {

		for(Long m:messageIdList){
			TRiClaimMessage claimMessage = claimMessageDao.load(m);
			claimMessage.setFlag(ClaimConstant.MESSAGE_UNCHECKED);
			claimMessageDao.merge(claimMessage);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateIscheckMessage(List<Long> messageIdList) {
		// TODO Auto-generated method stub
		for(Long m:messageIdList){
			TRiClaimMessage claimMessage = claimMessageDao.load(m);
			claimMessage.setFlag(ClaimConstant.MESSAGE_ISCHECKED);
			claimMessageDao.merge(claimMessage);
		}
	}

}
