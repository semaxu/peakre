package com.ebao.ap99.arap.restful;

import java.util.Calendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.constant.CurrencyConstants;
import com.ebao.ap99.arap.constant.FinanceTransactionType;
import com.ebao.ap99.arap.convertor.FeeQueryConditionConvertor;
import com.ebao.ap99.arap.convertor.SettlementDTOConvertor;
import com.ebao.ap99.arap.service.FeeService;
import com.ebao.ap99.arap.service.OffsetService;
import com.ebao.ap99.arap.service.SuspenseService;
import com.ebao.ap99.arap.vo.Balance;
import com.ebao.ap99.arap.vo.BalanceSearchDTO;
import com.ebao.ap99.arap.vo.CreditsAndDebit;
import com.ebao.ap99.arap.vo.FeeQueryCondition;
import com.ebao.ap99.arap.vo.FeeSearchDTO;
import com.ebao.ap99.arap.vo.OffsetDTO;
import com.ebao.ap99.arap.vo.OffsetModel;
import com.ebao.ap99.arap.vo.OffsetViewResult;
import com.ebao.ap99.arap.vo.SettlementReturnResult;
import com.ebao.ap99.arap.vo.SuspenseQueryCondition;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * 
 * @author terry.jiang
 *
 */
@Path("/offset")
@Module(com.ebao.ap99.parent.constant.Module.ARAP)
public class OffsetRestfulService {
	private Logger logger = LoggerFactory.getLogger();
	
	@Autowired
	private SuspenseService suspenseService;
	
	@Autowired
	private FeeService feeService;
	
	@Autowired
	private OffsetService offsetService;
	
	@Autowired
	private FeeQueryConditionConvertor feeQueryConditionConvertor;
	
	@Autowired
	private SettlementDTOConvertor settlementDTOConvertor;
	 
	@POST
    @Path("/querySuspense")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Balance> querySuspense(BalanceSearchDTO balanceSearchDTO) throws Exception {
		List<Balance> balanceList = null;
    	try {
        	SuspenseQueryCondition suspenseCondition = new SuspenseQueryCondition();
        	suspenseCondition.setContractIdArray(balanceSearchDTO.getContractId());
        	suspenseCondition.setPartnerCode(balanceSearchDTO.getPartnerCode());
        	suspenseCondition.setSettleCurrencyCode(CurrencyConstants.CURRENCY_USD);
        	balanceList = suspenseService.getSuspenseByCodition(suspenseCondition);
		} catch (Exception e) {
			logger.error("Query offset suspense exception.", e);
			throw e;
		}
    	return balanceList;
    }
	
	@POST
    @Path("/queryCreditDebit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<CreditsAndDebit> queryCreditDebit(FeeSearchDTO condition) throws Exception {
		List<CreditsAndDebit> creditsAndDebitList = null;
    	try {
    		FeeQueryCondition feeQueryCondition = feeQueryConditionConvertor.convertFeeQueryConditionByDTO(condition);
    		feeQueryCondition.setSettleCurrency(CurrencyConstants.CURRENCY_USD);
    		feeQueryCondition.setSettleDate(DateUtils.truncate(AppContext.getSysDate(),Calendar.DATE));
    		feeQueryCondition.setFinanceTransType(FinanceTransactionType.INTERNAL_OFFSET.getType());
    		feeQueryCondition.setCalcOSAmountInSettleCurrency(true);
    		creditsAndDebitList = feeService.getFeeByCondition(feeQueryCondition);
		} catch (Exception e) {
			logger.error("Query offset credit&debit exception.", e);
			throw e;
		}
    	return creditsAndDebitList;
    }
	
	@POST
    @Path("/submitOffset")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SettlementReturnResult submitOffset(OffsetDTO offsetDTO) throws Exception {
		SettlementReturnResult settlementReturnResult = new SettlementReturnResult();
		OffsetModel offsetModel = settlementDTOConvertor.convertOffsetDTO(offsetDTO);
    	try {
    		offsetModel.setCurrencyCode(CurrencyConstants.CURRENCY_USD);
    		offsetModel.setOffsetDate(AppContext.getSysDate());
    		
    		String offsetNo = offsetService.offset(offsetModel);
    		settlementReturnResult.setSettlementNum(offsetNo);
    		logger.info("Offset No is [" + offsetNo + "]");
		} catch (Exception e) {
			logger.error("Save offset exception.", e);
			throw e;
		}
    	return settlementReturnResult;
    }
	
    @GET
    @Path("/viewOffset/{transId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OffsetViewResult viewOffset(@PathParam("transId") Long transId) throws Exception{
    	logger.debug("transId=>"+transId);
    	OffsetViewResult offsetViewResult = null;
    	
    	try {
    		OffsetModel offsetModel = offsetService.queryOffsetDetail(transId);
    		offsetViewResult = feeQueryConditionConvertor.convertOffsetModelToOffsetViewResult(offsetModel);
    	}
    	catch (Exception e) {
    		logger.error("View Offset Exception.", e);
			throw e;
    	}
    	return offsetViewResult;
    }
}
