/**
 * 
 */
package com.ebao.ap99.claim.service.impl;



import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.ebao.ap99.claim.dao.RiClaimInfoDao;
import com.ebao.ap99.claim.entity.TRiClaimEvent;
import com.ebao.ap99.claim.entity.TRiClaimInfo;
import com.ebao.ap99.claim.model.ClaimExcelModel;
import com.ebao.ap99.claim.model.ClaimQuery;
import com.ebao.ap99.claim.model.EventInfo;
import com.ebao.ap99.claim.model.QuerySqlTemplate;
import com.ebao.ap99.claim.service.ClaimQueryService;
import com.ebao.ap99.file.service.CalService;
import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.PaginationHelper;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author yujie.zhang
 *
 */



public class ClaimQueryServiceImpl implements ClaimQueryService,CalService {
	private static Logger logger = LoggerFactory.getLogger();
	@Autowired
	private RiClaimInfoDao claimInfoDao;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebao.ap99.claim.service.ClaimQueryService#getClaim(com.ebao.ap99.
	 * claim.model.ClaimQuery)
	 */
	@Override
	public Page<TRiClaimInfo> getClaimList(ClaimQuery claimQuery) throws Exception {

		/*
		 * prepare sql sqlCount params rowMapper
		 */
		QuerySqlTemplate sqltmp = new QuerySqlTemplate();		
		String sql = sqltmp.getQueryClaimSql(claimQuery);
		//String sqlCount=sqltmp.getQueryClaimCount(claimQuery);
		Object[] params =(Object[]) sqltmp.getClaimParam(claimQuery);
		BeanPropertyRowMapper<TRiClaimInfo> rowMapper = new BeanPropertyRowMapper<TRiClaimInfo>(TRiClaimInfo.class);
        logger.info("claimQuery.params"+params);

		
        Page<TRiClaimInfo> page = new Page<TRiClaimInfo>();
		PaginationHelper<TRiClaimInfo> ph = new PaginationHelper<TRiClaimInfo>();		
		page = ph.pageHelper(sql, params,claimQuery.getPageIndex(), claimQuery.getCountPerPage(), rowMapper);
		
		return page;
	}

	@Override
	public Page<TRiClaimEvent> getEventList(EventInfo eventInfo) throws Exception {
		/*
		 * prepare sql sqlCount params rowMapper
		 */
		QuerySqlTemplate sqltmp = new QuerySqlTemplate();		
		String sql = sqltmp.getQueryEventSql(eventInfo);
		//String sqlCount=sqltmp.getQueryEventCount(eventInfo);
		Object[] params =(Object[]) sqltmp.getEventParam(eventInfo);
		BeanPropertyRowMapper<TRiClaimEvent> rowMapper = new BeanPropertyRowMapper<TRiClaimEvent>(TRiClaimEvent.class);
        logger.info("claimQuery.params"+params);
		
        Page<TRiClaimEvent> page = new Page<TRiClaimEvent>();
		PaginationHelper<TRiClaimEvent> ph = new PaginationHelper<TRiClaimEvent>();		
		page = ph.pageHelper(sql, params,eventInfo.getPageIndex(), eventInfo.getCountPerPage(), rowMapper);
		
		return page;
	}

//	
//	@Override
//	public <T> List<ClaimRecordsExcelModel> bizProcess(Map model) {
//		// Auto-generated method stub
//		Long refId = Long.valueOf((String)model.get("RefId"));
//		List<ClaimRecordsExcelModel> ClaimRecords = new ArrayList<>();
//		List<TreeModel> claimList = claimInfoDao.getClaimNoList(refId);
//		for(TreeModel  c:claimList){
//			ClaimRecords.addAll(claimInfoDao.getClaimRecordsExcelList(c.getId()));
//		}
//		return ClaimRecords;
//	}
	
	@Override
	public <T> List<ClaimExcelModel> bizProcess(Map model) {
		//ClaimParam para = (ClaimParam)model;
//(String)
	    String dd = (String) model.get("RefId");
		logger.info(dd.toString());
		Long refId = Long.valueOf((String) model.get("RefId"));
		List<ClaimExcelModel> eventExcelModel = claimInfoDao.getClaimExcelList(refId);


		return eventExcelModel;
	}
}
