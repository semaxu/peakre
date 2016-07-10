package com.ebao.ap99.arap.restful;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.convertor.TransactionReversalConvertor;
import com.ebao.ap99.arap.service.SettlementService;
import com.ebao.ap99.arap.vo.SettlementTransaction;
import com.ebao.ap99.arap.vo.TransactionQueryCondition;
import com.ebao.ap99.arap.vo.TransactionReversalDTO;
import com.ebao.ap99.parent.Page;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;


@Path("/transactionQuery")
@Module(com.ebao.ap99.parent.constant.Module.ARAP)
public class TransactionQueryRestfulService {
	private Logger logger=LoggerFactory.getLogger();
	@Autowired
	public TransactionReversalConvertor transactionReversalConvertor;
	@Autowired
	public SettlementService settlementService;
	
	@POST
	@Path("/queryTransactionQuery")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Page<SettlementTransaction> queryTransaction(TransactionReversalDTO condition) throws Exception {
		
		TransactionQueryCondition convertedCondition = transactionReversalConvertor.modelToQueryConditionEntity(condition);
		
		List<SettlementTransaction> list = null;
		Long total = 0L;
    	try{
    		list = settlementService.getSettleTransactionByCondition(convertedCondition, condition.getPageIndex(), condition.getCountPerPage());
    		total = settlementService.countSettleTransaction(convertedCondition);
    	}catch(Exception e){
    		logger.error("queryGeneralLedger fail",e);
			throw e;
    	}
    	
    	Page<SettlementTransaction> page = new Page<SettlementTransaction>();
    	page.setRows(list);
    	page.setPageIndex(condition.getPageIndex());
        
    	Long pageCount = total % page.getCountPerPage() == 0 ? total / page.getCountPerPage()
				: total / page.getCountPerPage() + 1;
		page.setPageCount(pageCount);
		if (total < page.getCountPerPage()) {
			page.setCountPerPage(total.intValue());
		}
		
    	return page;
	}
}
