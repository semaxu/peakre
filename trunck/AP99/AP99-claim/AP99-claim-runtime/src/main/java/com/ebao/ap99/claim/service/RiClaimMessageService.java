/**
 * 
 */
package com.ebao.ap99.claim.service;

import java.util.List;

import com.ebao.ap99.claim.entity.TRiClaimMessage;

/**
 * @author yujie.zhang
 *
 */
public interface RiClaimMessageService {

	void createClaimMessage(TRiClaimMessage tRiClaimMessage);
	
	TRiClaimMessage updateClaimMessage(TRiClaimMessage tRiClaimMessage);
	
	
	void deleteClaimMessage(TRiClaimMessage tRiClaimMessage);
	
	
	void updateUncheckMessage(List<Long> messageIdList);
	
	void updateIscheckMessage(List<Long> messageIdList);
}
