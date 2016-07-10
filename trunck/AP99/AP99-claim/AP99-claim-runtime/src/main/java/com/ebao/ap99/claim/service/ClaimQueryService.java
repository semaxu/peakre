/**
 * 
 */
package com.ebao.ap99.claim.service;


import java.util.List;
import java.util.Map;

import com.ebao.ap99.claim.entity.TRiClaimEvent;
import com.ebao.ap99.claim.entity.TRiClaimInfo;
import com.ebao.ap99.claim.model.ClaimQuery;
import com.ebao.ap99.claim.model.ClaimRecordsExcelModel;
import com.ebao.ap99.claim.model.EventInfo;
import com.ebao.ap99.parent.Page;

/**
 * @author yujie.zhang
 *
 */
public interface ClaimQueryService {

	//List<TRiClaimInfo> getClaim(ClaimQuery claimquery);
	  Page<TRiClaimInfo> getClaimList(ClaimQuery claimQuery) throws Exception;
	  Page<TRiClaimEvent> getEventList(EventInfo eventInfo) throws Exception;
	  
}
