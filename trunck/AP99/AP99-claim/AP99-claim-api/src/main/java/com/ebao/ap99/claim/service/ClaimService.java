/**
 * 
 */
package com.ebao.ap99.claim.service;

import java.util.List;

import com.ebao.ap99.claim.model.*;
/**
 * @author yujie.zhang
 *
 */
public interface ClaimService {
     /**
      * 
      * @param contractId
      * @return
      */
	public List<ClaimModelForGeneral>  getClaimListByContractId(Long contractId);
	
	/**
	 * 
	 * @param settlement
	 */
	public void updatePayment(Settlement settlement);
	
	/**
	 * 
	 * @param settlement
	 */
	public void updatePaymentBySettlementId(Settlement settlement);
	
	public boolean ifHasClaim(Long sectionId);

	public List<String> ifExistSection(Long sectionId);
}
