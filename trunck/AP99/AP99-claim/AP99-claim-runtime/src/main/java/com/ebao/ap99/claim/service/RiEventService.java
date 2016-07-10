/**
 * 
 */
package com.ebao.ap99.claim.service;

import java.util.List;

import com.ebao.ap99.claim.entity.TRiClaimEvent;
import com.ebao.ap99.claim.entity.TRiClaimMessage;
import com.ebao.ap99.claim.model.ClaimInfo;
import com.ebao.ap99.claim.model.EventInfo;

/**
 * @author yujie.zhang
 *
 */
public interface RiEventService {
	void createEvent(TRiClaimEvent eventInfo);

	TRiClaimEvent updateEvent(TRiClaimEvent eventInfo);

	TRiClaimEvent getEvent(long eventId);

	void deleteEvent(TRiClaimEvent eventInfo);
	 void calculateEventSummary(EventInfo eventInfo) ;

	List<TRiClaimEvent> getAllEvent();
	void calculateReportingCurrencyAmount(EventInfo eventInfo);

	void calculateSummary(EventInfo eventInfo);
	List<TRiClaimMessage> createClaimMessage(Long eventId);

//	void calculateClaimSummary(ClaimInfo claimInfo);


	
}
