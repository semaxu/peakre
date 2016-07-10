/**
 * 
 */
package com.ebao.ap99.arap.restful;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.constant.FinanceTransactionType;
import com.ebao.ap99.arap.convertor.FeeQueryConditionConvertor;
import com.ebao.ap99.arap.service.FeeService;
import com.ebao.ap99.arap.service.GLService;
import com.ebao.ap99.arap.service.PaymentService;
import com.ebao.ap99.arap.service.SuspenseService;
import com.ebao.ap99.arap.vo.Balance;
import com.ebao.ap99.arap.vo.CollectionSearchResult;
import com.ebao.ap99.arap.vo.CreditsAndDebit;
import com.ebao.ap99.arap.vo.FeeQueryCondition;
import com.ebao.ap99.arap.vo.GainOrLossVO;
import com.ebao.ap99.arap.vo.PaymentDTO;
import com.ebao.ap99.arap.vo.PaymentModel;
import com.ebao.ap99.arap.vo.PaymentSearchDTO;
import com.ebao.ap99.arap.vo.PaymentViewResult;
import com.ebao.ap99.arap.vo.SettlementReturnResult;
import com.ebao.ap99.arap.vo.SuspenseQueryCondition;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author meiliang.zou
 *
 */
@Path("/payment")
@Module(com.ebao.ap99.parent.constant.Module.ARAP)
public class PaymentRestfulService {
    private Logger logger = LoggerFactory.getLogger();
    
    @Autowired
    private FeeQueryConditionConvertor feeQueryConditionConvertor;
    
    @Autowired
    private FeeService feeService;
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private SuspenseService suspenseService;
    
    @Autowired
	private GLService glService;
    
    @POST
    @Path("/queryPayment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CollectionSearchResult queryPayment(PaymentSearchDTO paymentSearchDTO) throws Exception {
    	logger.debug("Broker=>" + paymentSearchDTO.getBroker());
    	logger.debug("PartnerCode=>" + paymentSearchDTO.getPartnerCode());
    	logger.debug("ContractID=>" + paymentSearchDTO.getContractID());
    	logger.debug("StatementId=>" + paymentSearchDTO.getStatementId());
    	logger.debug("ClaimNo=>" + paymentSearchDTO.getClaimNo());
    	
    	CollectionSearchResult searchResult = new CollectionSearchResult();
    	
    	try {
    		//glService.subLedgerGeneration();
    		//glService.generalLedgerGeneration();
        	FeeQueryCondition condition = 
        			feeQueryConditionConvertor.convertFeeQueryConditionByPaymentSearchDTO(paymentSearchDTO);
        	
        	condition.setFinanceTransType(FinanceTransactionType.PAYMENT.getType());
        	List<CreditsAndDebit> creditsAndDebitList = feeService.getFeeByCondition(condition);
        	searchResult.setCreditsAndDebit(creditsAndDebitList);
        	
        	SuspenseQueryCondition suspenseQueryCondition = 
        			feeQueryConditionConvertor.convertSuspenseQueryConditionByPaymentSearchDTO(paymentSearchDTO);
        	
        	List<Balance> balanceList = suspenseService.getSuspenseByCodition(suspenseQueryCondition);
        	searchResult.setBalance(balanceList);
		} catch (Exception e) {
			logger.error("queryCollection exception.", e);
			throw e;
		}
    	
    	return searchResult;
    }
    
    @POST
    @Path("/savePayment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SettlementReturnResult savePayment(PaymentDTO paymentDTO) throws Exception {
    	String receiptNo = "";

		SettlementReturnResult settlementReturnResult = new SettlementReturnResult();
		
		PaymentModel paymentModel = feeQueryConditionConvertor.convertPaymentDTOToPaymentModel(paymentDTO);
		
    	try {
    		receiptNo = paymentService.payment(paymentModel);
    		
    		settlementReturnResult.setSettlementNum(receiptNo);
		} catch (Exception e) {
			logger.error("Save Collection exception.", e);
			throw e;
		}
    	
    	logger.debug("Receipt No is [" + receiptNo + "]");
    	
    	return settlementReturnResult;
    }
    
    @POST
    @Path("/calGainLossInUSD")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, BigDecimal> calGainLossInUSD(GainOrLossVO gainOrLossVO) throws Exception {
    	Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
    	
    	Long feeId = gainOrLossVO.getFeeId();
    	BigDecimal markOffAmount = gainOrLossVO.getMarkOffAmount();
    	String originalCurrency = gainOrLossVO.getOriginalCurrency();
    	BigDecimal settleAmount = gainOrLossVO.getSettleAmount();
    	String settleCurrency = gainOrLossVO.getSettleCurrency();
    	Date settleDate = gainOrLossVO.getSettleDate();
    	
    	try {
    		BigDecimal gainOrLoss = feeService.calGainLossInUSD(feeId, markOffAmount, originalCurrency, 
    				settleAmount, settleCurrency, settleDate);
    		
    		map.put("GainOrLoss", gainOrLoss);
		} catch (Exception e) {
			logger.error("calGainOrLoss Failed.", e);
			throw e;
		}
    	
    	return map;
    }
    
    @GET
    @Path("/viewPayment/{transId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PaymentViewResult viewPayment(@PathParam("transId") Long transId) throws Exception{
    	logger.debug("transId=>"+transId);
    	PaymentViewResult paymentViewResult = null;
    	
    	try {
    		PaymentModel model = paymentService.queryPaymentDetail(transId);
    		paymentViewResult = feeQueryConditionConvertor.convertPaymentModelToPaymentViewResult(model);
    	}
    	catch (Exception e) {
    		logger.error("View Payment Exception.", e);
			throw e;
    	}
    	return paymentViewResult;
    }
}
