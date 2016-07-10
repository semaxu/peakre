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

import com.ebao.ap99.arap.convertor.FeeQueryConditionConvertor;
import com.ebao.ap99.arap.dao.SettlementDetailDao;
import com.ebao.ap99.arap.service.CollectionService;
import com.ebao.ap99.arap.service.FeeService;
import com.ebao.ap99.arap.vo.CollectionDTO;
import com.ebao.ap99.arap.vo.CollectionModel;
import com.ebao.ap99.arap.vo.CollectionSearchDTO;
import com.ebao.ap99.arap.vo.CollectionViewResult;
import com.ebao.ap99.arap.vo.CreditsAndDebit;
import com.ebao.ap99.arap.vo.FeeQueryCondition;
import com.ebao.ap99.arap.vo.GainOrLossVO;
import com.ebao.ap99.arap.vo.SettlementReturnResult;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.Assert;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author meiliang.zou
 *
 */
@Path("/collection")
@Module(com.ebao.ap99.parent.constant.Module.ARAP)
public class CollectionRestfulService {
    private Logger logger = LoggerFactory.getLogger();
    
    @Autowired
    private FeeQueryConditionConvertor feeQueryConditionConvertor;
    
    @Autowired
    private FeeService feeService;
    
    @Autowired
    private CollectionService collectionService;
    
    @Autowired
    SettlementDetailDao settlementDetailDao;
    
    @POST
    @Path("/queryCollection")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<CreditsAndDebit> queryCollection(CollectionSearchDTO collectionSearchDTO) throws Exception {
    	logger.debug("Broker=>" + collectionSearchDTO.getBroker());
    	logger.debug("PartnerCode=>" + collectionSearchDTO.getPartnerCode());
    	logger.debug("ContractIds=>" + collectionSearchDTO.getContractIds());
    	logger.debug("StatementId=>" + collectionSearchDTO.getStatementId());
    	logger.debug("ClaimNumber=>" + collectionSearchDTO.getClaimNumber());
    	
    	List<CreditsAndDebit> list = null;
    	
    	try {
        	FeeQueryCondition condition = 
        			feeQueryConditionConvertor.convertFeeQueryConditionByCollectionSearchDTO(collectionSearchDTO);
        	
			list = feeService.getFeeByCondition(condition);
		} catch (Exception e) {
			logger.error("queryCollection exception.", e);
			throw e;
		}
    	
    	return list;
    }
    
    @POST
    @Path("/saveCollection")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SettlementReturnResult saveCollection(CollectionDTO collectionDTO) throws Exception {
    	String receiptNo = "";

		  SettlementReturnResult settlementReturnResult = new SettlementReturnResult();
		
    	try {
        	CollectionModel collectionModel = 
        			feeQueryConditionConvertor.convertCollectionDTOToCollectionModel(collectionDTO);
        	
    		
    		receiptNo = collectionService.collection(collectionModel);
    		
    		settlementReturnResult.setSettlementNum(receiptNo);
		} catch (Exception e) {
			logger.error("Save Collection exception.", e);
			throw e;
		}
    	
    	logger.info("Receipt No is [" + receiptNo + "]");
    	
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
    @Path("/viewCollection/{transId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CollectionViewResult viewCollection(@PathParam("transId") Long transId) throws Exception{
    	logger.debug("--transId---" + transId);
    	Assert.isNotNull(transId);

    	CollectionViewResult collectionViewResult = null;
    	try{ 
    		CollectionModel collectionModel = collectionService.queryCollectionDetail(transId);
    		collectionViewResult = feeQueryConditionConvertor.covertCollectionViewResultByCollectionModel(collectionModel);
    	}catch(Exception e){
    		logger.error("viewCollection fail",e);
			throw e;
    	}
    	
    	return collectionViewResult;
    }
}
