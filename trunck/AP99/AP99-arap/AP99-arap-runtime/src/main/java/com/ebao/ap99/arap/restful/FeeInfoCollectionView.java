package com.ebao.ap99.arap.restful;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.convertor.FeeQueryConditionConvertor;
import com.ebao.ap99.arap.service.FeeService;
import com.ebao.ap99.arap.vo.CollectionSearchDTO;
import com.ebao.ap99.arap.vo.CreditsAndDebit;
import com.ebao.ap99.arap.vo.FeeQueryCondition;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

@Path("/viewcollection")
@Module(Module.CLAIM)
public class FeeInfoCollectionView {
private Logger logger = LoggerFactory.getLogger();
    
    @Autowired
    private FeeQueryConditionConvertor feeQueryConditionConvertor;
    
    @Autowired
    private FeeService feeService;
    
    
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
}
