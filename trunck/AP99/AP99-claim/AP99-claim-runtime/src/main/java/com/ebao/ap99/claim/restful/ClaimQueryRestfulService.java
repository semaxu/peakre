/**
 * 
 */
package com.ebao.ap99.claim.restful;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.entity.TRiClaimEvent;
import com.ebao.ap99.claim.entity.TRiClaimInfo;
import com.ebao.ap99.claim.model.ClaimModelForGeneral;
import com.ebao.ap99.claim.model.ClaimQuery;
import com.ebao.ap99.claim.model.EventInfo;
import com.ebao.ap99.claim.convertor.ClaimInfoConvertor;
import com.ebao.ap99.claim.service.ClaimQueryService;
import com.ebao.ap99.claim.service.ClaimService;
import com.ebao.ap99.parent.Page;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author yujie.zhang
 *
 */
@Path("/query")
@Module(com.ebao.ap99.parent.constant.Module.CLAIM)
public class ClaimQueryRestfulService {

	private static Logger logger = LoggerFactory.getLogger();
	@Autowired
	public ClaimQueryService queryService;
	@Autowired
	public ClaimInfoConvertor claimInfoConvertor;
	@Autowired
	public ClaimService claimService;
	@POST
    @Path("/claimquery")
    public Object getClaimPageList(ClaimQuery claimQuery) throws Exception {
        Page<TRiClaimInfo> page = new Page<TRiClaimInfo>();


        logger.info("claimQuery.queryByConditions");
      //  Object claimService;
		page = queryService.getClaimList(claimQuery);
		
        return page;
    }
	
	@POST
    @Path("/eventQuery")
    public Object getEventPageList(EventInfo eventInfo) throws Exception {
        Page<TRiClaimEvent> page = new Page<TRiClaimEvent>();


        logger.info("EventQuery.queryByConditions");
      //  Object EventQuery;
		page = queryService.getEventList(eventInfo);
		
        return page;
    }
	
	@GET
	@Path("/claimInfo/{contractId}")
	public Object getClaimModel(@PathParam("contractId") long contractId) {
		List<ClaimModelForGeneral>  claimModel = claimService.getClaimListByContractId(contractId);
		
		return claimModel;
	}
}
