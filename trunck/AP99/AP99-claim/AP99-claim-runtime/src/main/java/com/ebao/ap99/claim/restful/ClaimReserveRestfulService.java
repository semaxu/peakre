/**
 * 
 */
package com.ebao.ap99.claim.restful;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.convertor.ReserveLogConverter;
import com.ebao.ap99.claim.dao.RiClaimReserveLogDao;
import com.ebao.ap99.claim.model.ReserveHistoryInfo;
import com.ebao.ap99.claim.model.ReserveHistoryQuery;
import com.ebao.ap99.claim.service.RiClaimReserveLogService;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author yujie.zhang
 *
 */
@Path("/reserve")
@Module(com.ebao.ap99.parent.constant.Module.CLAIM)
public class ClaimReserveRestfulService {
	private static Logger logger = LoggerFactory.getLogger();
	
	@Autowired
	public RiClaimReserveLogService reserveLogService;
	@Autowired
	private RiClaimReserveLogDao reserveLogDao;
	@Autowired
	private ReserveLogConverter reserveLogConverter;
	@GET
	@Path("/select")
	public Object getReserveCodeTable(@QueryParam("RefId") long ClaimId,
			@QueryParam("BusinessDirection") String BusinessDirection){
		ReserveHistoryQuery reserveHistoryReturn = new ReserveHistoryQuery();
		//UnderwritingYear 
		reserveHistoryReturn.setUWYearList(reserveLogDao.getUWYear(ClaimId, BusinessDirection));
		//ContractSection
		reserveHistoryReturn.setContractSectionList(reserveLogDao.getContractSection(ClaimId, BusinessDirection));
		
		return reserveHistoryReturn;
	}
	
	
	@POST
    @Path("/reserveHistory")
    public Object getReserveList(ReserveHistoryQuery reserveHistoryQuery) {
        

        logger.info("claimQuery.ReserveHistoryQuery");
        //get ReserveHistoryList
		List<ReserveHistoryInfo> reserveHistoryInfoList = reserveLogService.getReserveHistoryList(reserveHistoryQuery);
		reserveHistoryQuery.setReserveHistoryInfo(reserveHistoryInfoList);

		//get LatestReserve
		List<ReserveHistoryInfo>   reserveInfoList  = reserveLogDao.getLatestReserve(reserveHistoryQuery);
		reserveHistoryQuery.setReserveInfo(reserveInfoList);
		
		//get LatestReserve Summary
		reserveHistoryQuery.setReserveSummary(reserveLogConverter.calcReserveSummary(reserveHistoryQuery));
		
		return reserveHistoryQuery;
    }
	
	
	

}
