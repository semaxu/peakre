package com.ebao.ap99.arap.restful;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.convertor.FeeQueryConditionConvertor;
import com.ebao.ap99.arap.service.FeeService;
import com.ebao.ap99.arap.service.SettlementService;
import com.ebao.ap99.arap.vo.CollectionSearchDTO;
import com.ebao.ap99.arap.vo.CreditsAndDebit;
import com.ebao.ap99.arap.vo.FeeQueryCondition;
import com.ebao.ap99.arap.vo.SettlementTransaction;
import com.ebao.ap99.arap.vo.TransactionQueryCondition;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

@Path("/credit")
@Module(com.ebao.ap99.parent.constant.Module.ARAP)
public class CreditOfGeneralQueryViewRestfulService {
    private Logger logger = LoggerFactory.getLogger();

    @Autowired
    private FeeQueryConditionConvertor feeQueryConditionConvertor;

    @Autowired
    private FeeService feeService;

    @Autowired
    private SettlementService settlementService;

    @POST
    @Path("/queryCredit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<CreditsAndDebit> queryCredit(CollectionSearchDTO collectionSearchDTO) throws Exception {
        List<CreditsAndDebit> list = null;
        try {
            FeeQueryCondition condition = feeQueryConditionConvertor
                    .convertFeeQueryConditionByCollectionSearchDTO(collectionSearchDTO);
            list = feeService.queryFeeInfoByCondition(condition);
        } catch (Exception e) {
            logger.error("crediterror", e);
            throw e;
        }

        return list;
    }

    @POST
    @Path("/queryTransactionHistory/{feeIdArray}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<SettlementTransaction> queryTransactionHistory(@PathParam("feeIdArray") String feeIdArray) {
        logger.debug("feeIdArray=>" + feeIdArray);
        List<SettlementTransaction> list = new ArrayList<SettlementTransaction>();
        TransactionQueryCondition condition = new TransactionQueryCondition();
        condition.setFeeIdArray(feeIdArray);
        try {
            list = settlementService.getSettleTransactionByCondition(condition, 0, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }
}
