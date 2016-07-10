package com.ebao.ap99.arap.restful;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.constant.OperationStatus;
import com.ebao.ap99.arap.convertor.TransactionReversalConvertor;
import com.ebao.ap99.arap.service.ReversalService;
import com.ebao.ap99.arap.service.SettlementService;
import com.ebao.ap99.arap.vo.OperationResult;
import com.ebao.ap99.arap.vo.ReversalModel;
import com.ebao.ap99.arap.vo.SettlementTransaction;
import com.ebao.ap99.arap.vo.TransactionQueryCondition;
import com.ebao.ap99.arap.vo.TransactionReversalDTO;
import com.ebao.ap99.parent.Page;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

@Path("/transactionReversal")
@Module(com.ebao.ap99.parent.constant.Module.ARAP)
public class TransactionReversalRestfulService {
	private Logger logger = LoggerFactory.getLogger();

	@Autowired
	public TransactionReversalConvertor transactionReversalConvertor;
	
	@Autowired
	public SettlementService settlementService;
	
	@Autowired
	private ReversalService reversalService;
	
	@POST
	@Path("/queryTransactionReversal")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Page<SettlementTransaction> queryTransactionReversal(TransactionReversalDTO condition) throws Exception {
		TransactionQueryCondition convertedCondition = transactionReversalConvertor.modelToQueryConditionEntity(condition);
		
		List<SettlementTransaction> list = null;
		Long total = 0L;
    	try{
    		convertedCondition.setNoReversal(true);
    		list = settlementService.getSettleTransactionByCondition(convertedCondition, condition.getPageIndex(), condition.getCountPerPage());
    		total = settlementService.countSettleTransaction(convertedCondition);
    	}catch(Exception e){
    		logger.error("queryTransactionReversal fail",e);
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
	
	@POST
    @Path("/submitTransactionReversal")
    @Consumes(MediaType.APPLICATION_JSON)
	public OperationResult saveTransactionReversal(ReversalModel reversalModel) throws Exception{
		OperationResult result = new OperationResult();
		try {
			result = reversalService.reverse(reversalModel);
		} catch (Exception e) {
			logger.error("Submit transaction reverse exception", e);
			result.setStatus(OperationStatus.FAILED.getValue());
			result.setMessage(e.getLocalizedMessage());
		}
		return result;
	}

}
